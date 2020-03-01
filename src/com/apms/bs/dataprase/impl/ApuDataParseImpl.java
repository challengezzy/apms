package com.apms.bs.dataprase.impl;

import java.util.Date;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.apu.ApuRunLogCompute;
import com.apms.bs.apu.ApuSwapLogCacheService;
import com.apms.bs.apu.vo.ApuAcnumTimeScopeVo;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.util.DateUtil;
import com.apms.vo.ApmsVarConst;

public abstract class ApuDataParseImpl  extends ReportContentParseClass{
	
	protected CommDMO dmo = new CommDMO();
    protected boolean  isApuACRight = false;//APU和飞行号对应是否存在
	
    protected int lastAhrs = 0;//上一个运行时间
    protected int lastAcyc = 0;//上一个循环次数
	protected int curAhrs = 0;//当前报小时，值必须在子类中设置
	protected int curAcyc = 0;//当前报文循环，值必须在子类中设置
	
    protected int ahrs_inc=0;
    protected int acyc_inc=0;
    
    protected String asn;//apu序列号，值必须在子类中设置
    protected String corFlag = ApmsVarConst.APU_CORRECT_NO;//修正标识
    
    protected AlarmMonitorTargetVo targetVo;
    protected String monitorObjCode;
    
    protected String insertSql = "insert into a_dfd_a13_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,TAT,ALT,ASN,AHRS,ACYC,AHRS_ADD,ACYC_ADD,PFAD,"
			+ "ESN_N1,ACW1_N1,ACW2_N1,NA_N1,EGTA_N1,IGV_N1,"
			+ "ESN_N2,ACW1_N2,ACW2_N2,NA_N2,EGTA_N2,IGV_N2,"
			+ "ESN_N3,ACW1_N3,ACW2_N3,NA_N3,EGTA_N3,IGV_N3,"
			+ "P2A_S1,LCIT_S1,WB_S1,PT_S1,LCDT_S1,OTA_S1,GLA_S1," 
			+ "P2A_S2,LCIT_S2,WB_S2,PT_S2,LCDT_S2,OTA_S2,GLA_S2,"
			+ "P2A_S3,LCIT_S3,WB_S3,PT_S3,LCDT_S3,OTA_S3,GLA_S3,"
			+ "STA_V1,EGTP_V1,NPA_V1,OTA_V1,LCIT_V1,CORRECT_FLAG,RECDATETIME)" 
			+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
			+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
    
   
    
    
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		
		//代表性变量数据重新初始化
		isApuACRight = false;
		asn = null;
		lastAhrs = 0;// 上一个运行时间
		lastAcyc = 0;// 上一个循环次数
		curAhrs = 0;// 当前报小时，值必须在子类中设置
		curAcyc = 0;// 当前报文循环，值必须在子类中设置
		ahrs_inc = 0;
		acyc_inc = 0;
		corFlag = ApmsVarConst.APU_CORRECT_NO;
		
		//1 解析对象内容，设置解析出数据的基本信息
		praseDataToVo(content,trans_time);
		
		//2 判断ASN和飞行号是否对应, isApuACRight
		ApuAcnumTimeScopeVo scopeVo = checkApuMatch(trans_time);
		
		AcarsParseResult parseRes;
		if (isApuACRight){
			//小时循环数调整
			correctHourCycle();
			//重新设置
			resetHourCycle();
			
			//3 插入数据入库
			insertParseData();
			
			//4 生成APU小时、循环数据
			dealApuRunLog(scopeVo);
			
			//5 触发APU的相关告警
			targetVo = getAlarmTargetVo();
			targetVo.setDevicesn(asn);
			targetVo.setAhrs_inc(ahrs_inc);
			setAlarmDataBody();
			//告警触发
			AlarmComputeService.getInstance().alarmObjectAdd(monitorObjCode, msgno, targetVo);
			
			parseRes = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		}else {
			if(asn==null)
			{
				throw new Exception("APU序列号为空！");
			}
			if(asn.indexOf("XX") > 0){
				parseRes = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_DEVERR,"APU设备序号号采集异常！");
			}else{
				parseRes = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_WAIT,"APU与飞机号不匹配，以后解析");
			}
		}
		
		if (ahrs_inc > 40 || ahrs_inc < 0){
			targetVo = getAlarmTargetVo();
			targetVo.setDevicesn(asn);
			setAlarmDataBody();
			AlarmComputeService.getInstance().alarmObjectAdd(monitorObjCode, msgno, targetVo);


		}
		
		return parseRes;
	}
	
	public void correctHourCycle() throws Exception{
		Date rptDate = headVo.getDateUtc();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT C.HOUR_CORRECT,C.CYCLE_CORRECT FROM APU_HOURCYC_CORRECT C,B_APU A WHERE A.ID=C.APU_ID AND A.APUSN=? ");
		sb.append(" AND C.BEGIN_TIME<=? AND C.END_TIME IS NULL   ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT C.HOUR_CORRECT,C.CYCLE_CORRECT FROM APU_HOURCYC_CORRECT C,B_APU A WHERE A.ID=C.APU_ID AND A.APUSN=?");
		sb.append(" AND C.BEGIN_TIME<=? AND C.END_TIME > ?        ");//时间是报文数据正确后的时间
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS,sb.toString(),asn,rptDate,asn,rptDate,rptDate);
		if(vos.length > 0){
			corFlag = ApmsVarConst.APU_CORRECT_YES;
			
			int corH = vos[0].getIntegerValue("HOUR_CORRECT");
			int corC = vos[0].getIntegerValue("CYCLE_CORRECT");
			
			
			curAhrs = curAhrs + corH;
			curAcyc = curAcyc + corC;
			ahrs_inc = ahrs_inc + corH;
			acyc_inc = acyc_inc + corC;
		}else{
			corFlag = ApmsVarConst.APU_CORRECT_NO;
		}
	}
	
	/**
	 * 转换报文数据为对象
	 * @param gStr
	 * @param transdate
	 * @throws Exception
	 */
	public abstract void praseDataToVo(String gStr,String transdate) throws Exception;
	
	/**
	 * 数据入库
	 * @throws Exception
	 */
	public abstract void insertParseData() throws Exception;
	
	/**
	 * 重新设置小时循环为修正值
	 * @throws Exception
	 */
	public abstract void resetHourCycle() throws Exception;
	
	/**
	 * 设备告警时用到的一些关键信息
	 * @throws Exception
	 */
	public abstract void setAlarmDataBody() throws Exception;
	
	public ApuAcnumTimeScopeVo checkApuMatch(String trans_time) throws Exception{
		Date rptTime = DateUtil.StringToDate(trans_time, "yyyy-MM-dd HH:mm:ss");
		
		//从拆的记录中，取中报文时间时对应的机号,不在乎是当前还是历史
		ApuAcnumTimeScopeVo scopeVo = ApuSwapLogCacheService.getInstance().getScopeVoApuTime(asn, rptTime);
		
		if(scopeVo!=null  && headVo.getAcid().equals(scopeVo.getAcnum()) ){
			String sql = "select TOTALTIME,TOTALCYCLE,APUSN from b_apu b where apusn=?"; 
			HashVO[] vo_apus =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql ,asn);
			if(vo_apus.length > 0){
				isApuACRight = true;
				lastAhrs = vo_apus[0].getIntegerValue("TOTALTIME");
				lastAcyc = vo_apus[0].getIntegerValue("TOTALCYCLE");
				
				//zhangzy 存在报文中小时循环为0的情况，如果小时循环为0，取值量小时循环2,1,然后叠加最新的小时循环，作为 当前报文的小时循环
				if(curAhrs <= 0){
					ahrs_inc = 120;
					curAhrs = lastAhrs + ahrs_inc;
				}else{ //正常的情况 
					ahrs_inc = curAhrs - lastAhrs;
				}
				
				if(curAcyc <=0 ){
					acyc_inc = 1;
					curAcyc = lastAcyc + acyc_inc;
				}else{//正常的循环
					acyc_inc = curAcyc - lastAcyc;
				}
				
			}else{
				//APU数据都没有，当然是不匹配
				isApuACRight = false;
			}
			
		}else{
			isApuACRight = false;
		}
		
//		//判断ASN和飞行号是否对应,只能查当前APU和飞机号的关系，不能查历史
//		String check_apu ="select AIRCRAFTSN,TOTALTIME,TOTALCYCLE,APUSN from b_apu b, b_aircraft t " +
//				"where b.aircraftid =t.id and aircraftsn=? and apusn=?";
//		HashVO[] vo_apus =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, check_apu, headVo.getAcid(),asn);
//		if(vo_apus.length > 0){
//			isApuACRight = true;
//			lastAhrs = vo_apus[0].getIntegerValue("TOTALTIME");
//			lastAcyc = vo_apus[0].getIntegerValue("TOTALCYCLE");
//			
//			ahrs_inc = curAhrs - lastAhrs;
//			acyc_inc = curAcyc - lastAcyc;
//			
//		}else{
//			isApuACRight = false;
//		}
		
		return scopeVo;
	}
	
	
	public void dealApuRunLog(ApuAcnumTimeScopeVo scopeVo) throws Exception{
		
		//更新APU信息
		String updateSql ="UPDATE B_APU A SET A.TOTALTIME=?,A.TOTALCYCLE =?,DATATIME=?,CORRECTFLAG=? WHERE A.APUSN=?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, curAhrs,curAcyc,headVo.getDateUtc(),corFlag,asn);
		
		//生成APU运行日志数据
		ApuRunLogCompute.createApuRunlog(rptno,msgno, headVo.getDateUtc(), scopeVo, curAhrs+"", curAcyc+"", ahrs_inc, acyc_inc,corFlag);
	
	}

}
