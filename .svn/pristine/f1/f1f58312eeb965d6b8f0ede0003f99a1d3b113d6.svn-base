package com.apms.bs.apu;

import java.util.Date;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.apu.vo.ApuAcnumTimeScopeVo;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

public class ApuRunLogCompute {
	
	private static CommDMO dmo = new CommDMO();
	private int foreCastDays = 21;
	
	private static Logger logger = NovaLogger.getLogger(ApuRunLogCompute.class.getClass());
	
	private static String insertRunlog = "insert into l_apu_runlog(ID,APU_MODELID,APU_ID,ASN,ACNUM,MSG_NO,RPTNO,DATATIME," 
		+ "ADD_TIME,ADD_CYCLE,TOTALTIME,TOTALCYCLE,TIME_ONINSTALL,CYCLE_ONINSTALL,TIME_ONREPAIRED,CYCLE_ONREPAIRED,"
		+ "REPAIR_FLAG,INSTALL_TIME,CORRECT_FLAG,COMMENTS,UPDATE_DATE,UPDATE_MAN)" 
		+ " values(S_L_APU_RUNLOG.nextval," 
		+ "?,?,?,?,?,?,?,?,?,?,?," 
		+ "?,?,?,?,?,?,?,?,sysdate,?)";
	
	private static StringBuilder qryApu;
	
	private static StringBuilder insertDaily;
	
	static{
		
		qryApu =new StringBuilder("select id, apumodelid, datatime, totaltime, totalcycle, time_oninstall, cycle_oninstall,");
		qryApu.append("time_onrepaired, cycle_onrepaired, repair_flag, install_time, correctflag, comments,");
		qryApu.append("totaltime,totalcycle,day_predition_loop,day_predition_hour,");
		qryApu.append("(select aircraftsn from b_aircraft a where a.id =aircraftid ) acnum,");
		qryApu.append("update_date, update_man from b_apu where apusn = ? ");
		
		insertDaily = new StringBuilder("insert into l_apu_runlog_daily(");		
		insertDaily.append("ID,APU_MODELID,APUSN,ACNUM,DATE_STR,DATATIME,TOTALTIME,TOTALCYCLE,");
		insertDaily.append("TIME_ONINSTALL,CYCLE_ONINSTALL,TIME_ONREPAIRED,CYCLE_ONREPAIRED,REPAIR_FLAG,INSTALL_TIME,");
		insertDaily.append("CORRECT_FLAG,DAILY_ADDTIME,DAILY_ADDCYCLE,COMPUTESTATUS,COMMENTS,UPDATE_DATE,OIL_NUM");
		insertDaily.append(") values(S_L_APU_RUNLOG_DAILY.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,null)");
	}
	
	/**
	 * 生成APU运行日志数据
	 * @param msgno
	 * @param dateUtc
	 * @param acnum
	 * @param asn
	 * @param ahrs
	 * @param acyc
	 * @param ahrs_inc
	 * @param acyc_inc
	 * @param corFalg
	 * @throws Exception
	 */
	public static void createApuRunlog(String rptno,String msgno,Date dateUtc,ApuAcnumTimeScopeVo scopeVo
			,String ahrs,String acyc,int ahrs_inc,int acyc_inc,String corFalg) throws Exception{
		
		String asn = scopeVo.getApusn();
		String acnum = scopeVo.getAcnum();
		// l_apu_runlog				
		//HashVO[] vos_ar =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qryApu.toString(),asn);
		String apuid=null,apumodelid=null;
		double time_onrepaired, cycle_onrepaired, time_oninstall , cycle_oninstall ;
		String repair_flag;
		String comments=null, update_man=null;
		Date install_time=null;
			
		apuid = scopeVo.getApuId();
		apumodelid = scopeVo.getApuModelId();
		time_oninstall = scopeVo.getTimeOninstall();
		cycle_oninstall = scopeVo.getCycleOninstall();
		time_onrepaired = scopeVo.getTimeOnrepaired();
		cycle_onrepaired = scopeVo.getCycleOnrepaired();
		repair_flag = scopeVo.getRepairFlag();
		install_time = scopeVo.getInstallTime();
		
		update_man = "system";
				
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertRunlog, apumodelid,apuid, asn,acnum, msgno, rptno,
				dateUtc, ahrs_inc, acyc_inc, ahrs,acyc, time_oninstall, cycle_oninstall,
				time_onrepaired, cycle_onrepaired, repair_flag, install_time, corFalg, comments,
				update_man);
		
		//1生成APU每日汇总日志
		//报文当日汇总日志可能存在预测数据或者正常数据
		String dataDate = DateUtil.getDateStr(dateUtc);
		
		//查询已经汇总结果
		StringBuilder qry = new StringBuilder("select ID,DATATIME,DAILY_ADDTIME,DAILY_ADDCYCLE,TOTALTIME,TOTALCYCLE,COMPUTESTATUS ");
		qry.append(" From l_apu_runlog_daily WHERE APUSN=? and ACNUM=?");
		qry.append(" and DATE_STR = ?");
		
		HashVO[] dailyVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qry.toString(),asn,acnum,dataDate);
		if(dailyVos.length > 0){
			String status = dailyVos[0].getStringValue("COMPUTESTATUS");
			String dailyId = dailyVos[0].getStringValue("ID");
			
			int cyc_dailyAdd = acyc_inc;
			int time_dailyAdd = ahrs_inc;
			if("1".equals(status)){
				cyc_dailyAdd = acyc_inc + dailyVos[0].getIntegerValue("DAILY_ADDCYCLE");
				time_dailyAdd = ahrs_inc + dailyVos[0].getIntegerValue("DAILY_ADDTIME");
			}
			
			String updateSql = "update l_apu_runlog_daily set daily_addtime=?,daily_addcycle=?,totaltime=?,totalcycle=?,update_date=sysdate where id=?";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, time_dailyAdd,cyc_dailyAdd,ahrs,acyc,dailyId);
			
		}else{
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertDaily.toString(), 
					apumodelid, asn,acnum,dataDate,dateUtc, ahrs,acyc,
					time_oninstall, cycle_oninstall,time_onrepaired, cycle_onrepaired, repair_flag, install_time,
					corFalg,ahrs_inc,acyc_inc,"1",null);
		}
		
		logger.debug("生成APU小时循环完成！");
			
	}
	
	/**
	 * 更新报文小时循环
	 * @param asn
	 * @param beginTime
	 * @param endTime
	 * @param corHour 正确的小时数
	 * @param corCyc 正确的循环数
	 * @throws Exception
	 */
	public void correctApuReportHourCycle(String asn,Date beginTime,Date endTime,double corHour,double corCyc,String corFlag) throws Exception{
		//查询到开始出错的那一点的APU报文数据，很重要！！！
		String qryStart = "SELECT DATATIME,TOTALTIME,TOTALCYCLE FROM L_APU_RUNLOG L WHERE ASN=? AND DATATIME=? ORDER BY DATATIME";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qryStart,asn,beginTime);
		if(vos.length < 1){
			throw new Exception("开始时间点没有查询到报文数据，无法更新小时循环");
		}
		
		//每次的错误小时、循环值从开始点实时查询，以避免重复执行时，修正值错误的情况
		double errHour = vos[0].getDoubleValue("TOTALTIME");
		double errCyc = vos[0].getDoubleValue("TOTALCYCLE");
		
		double detaHour = corHour - errHour; //正确数值- 错误 值 = 修正值 
		double detaCyc  = corCyc - errCyc;
		
		//更新第一条报文数据的小时、循环增量,其后数据的小时循环增量均为正确数据
		String uptFirstA13 = "UPDATE A_DFD_A13_LIST SET RECDATETIME=SYSDATE,AHRS_ADD=AHRS_ADD+(" + detaHour +"),ACYC_ADD=ACYC_ADD+(" + detaCyc+") WHERE ASN='"+asn+"' AND DATE_UTC=?";
		String uptFirstLog = "UPDATE L_APU_RUNLOG SET UPDATE_DATE=sysdate,ADD_TIME= ADD_TIME+(" + detaHour+"),ADD_CYCLE= ADD_CYCLE+(" + detaCyc+") WHERE ASN='"+asn+"' AND DATATIME=? ";
		
		//1, 修改A13报文状态
		StringBuilder a13Update = new StringBuilder("UPDATE A_DFD_A13_LIST ");
		a13Update.append("SET RECDATETIME=SYSDATE,CORRECT_FLAG="+corFlag+", AHRS=AHRS+(" + detaHour+")");
		a13Update.append(",ACYC=ACYC+(" + detaCyc+")");
		a13Update.append(" WHERE ASN='"+asn+"' AND DATE_UTC >= ? " ); 
		
		//2 修改A14报文
		StringBuilder a14Update = new StringBuilder("UPDATE A_DFD_A14_LIST ");//更新A14解析的报文数据
		a14Update.append("SET RECDATETIME=SYSDATE, AHRS=AHRS+(" + detaHour+")");
		a14Update.append(",ACYC=ACYC+(" + detaCyc+")");
		a14Update.append(" WHERE ASN='"+asn+"' AND RPTDATE >= ? ");
		
		//3,修改运行日志
		StringBuilder runlogUpdate = new StringBuilder("UPDATE L_APU_RUNLOG ");
		runlogUpdate.append("SET UPDATE_DATE=SYSDATE,CORRECT_FLAG="+corFlag+", TOTALTIME= TOTALTIME+(" + detaHour+")");
		runlogUpdate.append(",TOTALCYCLE= TOTALCYCLE+(" + detaCyc+")");
		runlogUpdate.append(" WHERE ASN='"+asn+"' AND LOGTYPE=0 AND DATATIME >= ? ");//只修改系统产生的日志
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,uptFirstA13,beginTime);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,uptFirstLog,beginTime);
				
		if(endTime == null){
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, a13Update.toString(), beginTime);			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, a14Update.toString(), beginTime);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, runlogUpdate.toString(), beginTime);
			
			//表示更正到当前时间，此时对APU中的小时、循环进行更新, 修正完成后统一改成最新报文数据
//			String updateSql ="UPDATE B_APU A SET TOTALTIME= TOTALTIME+(" + detaHour+"),TOTALCYCLE= TOTALCYCLE+(" + detaCyc+") WHERE A.APUSN=?";
//			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, asn);
//			logger.debug("更新ASN["+asn+"]数据从["+DateUtil.getLongDate(beginTime)+"]开始到目前的小时循环修正信息成功");
		}else{
			//结束时间不为空，情况处理
			a13Update.append(" AND DATE_UTC < ?");
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, a13Update.toString(), beginTime,endTime);
			
			a14Update.append(" AND RPTDATE < ?");
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, a14Update.toString(), beginTime,endTime);
			
			runlogUpdate.append(" AND DATATIME < ?");
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, runlogUpdate.toString(), beginTime,endTime);
			
			
			//找到ENDTIME 之后的第一条报文,修正之，修正值 由加 变减
			String qryEnd = "SELECT MIN(MSG_NO) MSG_NO FROM A_DFD_A13_LIST WHERE ASN=? AND DATE_UTC=?";//第一条正确报文
			HashVO[] tvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qryEnd, asn,endTime);
			if(vos.length > 0){ //查到报文修正后第一条报文
				String corFstMsgno = tvos[0].getStringValue("MSG_NO");
				StringBuilder updateFst = new StringBuilder("UPDATE A_DFD_A13_LIST ");
				updateFst.append(" SET AHRS_ADD= AHRS_ADD-(" + detaHour +  "),ACYC_ADD= ACYC_ADD-(" + detaCyc+")");
				updateFst.append(" WHERE MSG_NO = "+corFstMsgno);
				
				//更新修正后的第一条数据
				String uptCORfStLog = "UPDATE L_APU_RUNLOG SET UPDATE_DATE=sysdate,ADD_TIME= ADD_TIME-(" + detaHour+"),ADD_CYCLE=ADD_CYCLE-(" + detaCyc
					+") WHERE ASN='"+asn+"' AND MSG_NO= "+corFstMsgno;
				
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateFst.toString());
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptCORfStLog );
			}
			
			logger.debug("更新ASN["+asn+"]数据从["+DateUtil.getLongDate(beginTime)+"]开始到["+DateUtil.getLongDate(endTime)+"]结束的小时循环修正信息成功");
		}
		
		//更新APU小时循环为最新数据
		updateApuHourCycLatest(asn);
		
		//5重新生成每日运行日志
		updateApuDailyRunlog(asn, beginTime, endTime);
		
	}
	
	public void updateApuHourCycLatest(String asn) throws Exception{
		String sql = "select datatime,totaltime,totalcycle,correct_flag from l_apu_runlog l where l.msg_no=(select max(msg_no) from l_apu_runlog where asn='"+asn+"')";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		if(vos.length > 0){
			HashVO vo = vos[0];
			double totaltime = vo.getDoubleValue("totaltime");
			double totalcycle = vo.getDoubleValue("totalcycle");
			String corFlag = vo.getStringValue("correct_flag");
			Date datatime = vo.getDateValue("datatime");
			
			String updateSql ="UPDATE B_APU A SET A.TOTALTIME=?,A.TOTALCYCLE =?,CORRECTFLAG=?,DATATIME=?,UPDATE_DATE=sysdate WHERE A.APUSN=?";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, totaltime,totalcycle,corFlag,datatime,asn);
			logger.debug("更新APU小时循环数据为最新报文数据成功！");
		}
		
	}
	
	public void updateApuDailyRunlog(String asn,Date beginTime,Date endTime) throws Exception{
		//4删除每日日志，重新生成
		StringBuilder delDailySql = new StringBuilder("DELETE L_APU_RUNLOG_DAILY WHERE APUSN='"+asn+"'");
		delDailySql.append(" AND DATATIME >= TRUNC(?)");
		
		//删除现在日志
		if(endTime == null){
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delDailySql.toString(), beginTime);
			
		}else{
			delDailySql.append(" AND DATATIME < TRUNC(?) + 1");
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delDailySql.toString(), beginTime,endTime);
		}
		
		StringBuilder insertsql = new StringBuilder();
		insertsql.append("insert into l_apu_runlog_daily("); 
		insertsql.append(" ID,APU_MODELID,APUSN,ACNUM,DATE_STR,DATATIME,TOTALTIME,TOTALCYCLE, "); 
		insertsql.append(" TIME_ONINSTALL,CYCLE_ONINSTALL,TIME_ONREPAIRED,CYCLE_ONREPAIRED,REPAIR_FLAG,INSTALL_TIME,"); 
		insertsql.append(" CORRECT_FLAG,DAILY_ADDTIME,DAILY_ADDCYCLE,COMPUTESTATUS,UPDATE_DATE)  "); 
		insertsql.append("SELECT S_L_APU_RUNLOG_DAILY.NEXTVAL,l1.APU_MODELID,l1.asn,l1.acnum, ");
		insertsql.append(" to_char(datatime,'YYYY-MM-DD') data_str,l1.datatime,l1.totaltime,l1.totalcycle,             ");
		insertsql.append(" time_oninstall,cycle_oninstall,time_onrepaired, cycle_onrepaired, repair_flag, install_time,");
		insertsql.append(" l1.correct_flag, DAILYADDTIME add_time,DAILYADDCYC add_cycle,1 COMPUTESTATUS,sysdate  ");
		insertsql.append(" FROM L_APU_RUNLOG L1,(  ");
		insertsql.append("	SELECT MAX(MSG_NO) MSG_NO,SUM(ADD_TIME) DAILYADDTIME,SUM(ADD_CYCLE) DAILYADDCYC");
		insertsql.append("	FROM L_APU_RUNLOG WHERE ASN= '"+asn+"' AND MSG_NO IS NOT NULL");
		insertsql.append(" AND DATATIME >=  TRUNC(TO_DATE('"+DateUtil.getDateStr(beginTime)+"', 'YYYY-MM-DD'))");//从开始点算
		if(endTime != null ){
			insertsql.append(" AND DATATIME <  TRUNC(TO_DATE('"+DateUtil.getDateStr(endTime)+"', 'YYYY-MM-DD')+1)");//到结束点
		}
		
		insertsql.append("  GROUP BY TO_CHAR(DATATIME,'YYYY-MM-DD') ORDER BY TO_CHAR(DATATIME,'YYYY-MM-DD')");
		insertsql.append(") L2 WHERE L2.MSG_NO=L1.MSG_NO");

		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertsql.toString());
		
		logger.debug("更新ASN["+asn+"]数据从["+DateUtil.getLongDate(beginTime)+"]开始到["+endTime+"]结束的每日运行日志信息成功");
	}
	
	//APU 对一段时间的APU 数据删除重新计算
	public void recomputeApuReport(String asn,Date beginTime,Date endTime) throws Exception{
		//删除已计算数据 
		StringBuilder delField = new StringBuilder("DELETE A_DFD_FIELD_COMPUTE F ");
		delField.append(" WHERE EXISTS(SELECT 1 FROM A_DFD_A13_LIST L WHERE F.MSG_NO=L.MSG_NO AND L.ASN=? AND L.DATE_UTC>=?)");
		
		//删除计算表数据
		StringBuilder delCom = new StringBuilder("DELETE A_DFD_A13_COMPUTE WHERE ASN=? AND UTCDATE>=?");
		
		//更新HEAD状态为未解析
		StringBuilder updateHead = new StringBuilder("UPDATE A_DFD_HEAD H SET H.STATUS=0 WHERE EXISTS");
		updateHead.append("(SELECT 1 FROM A_DFD_A13_LIST L WHERE L.MSG_NO=H.MSG_NO AND L.ASN=? AND L.DATE_UTC>=?)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,delField.toString(),asn,beginTime);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,delCom.toString(),asn,beginTime);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,updateHead.toString(),asn,beginTime);
		
		logger.debug("设置ASN["+asn+"]数据从["+DateUtil.getLongDate(beginTime)+"]开始，数据状态为未计算，进行重新计算！");
	}
	
	/**
	 * 初始化APU日志时，如果是拆换新装APU，APU数据时间为拆换时间
	 * @param apusn
	 * @param swapTime
	 * @throws Exception
	 */
	public void initApuRunLog(String apusn,Date swapTime) throws Exception{
		//新增APU时，初始APU运行日志
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO L_APU_RUNLOG(ID,APU_MODELID,APU_ID,ASN,LOGTYPE,ACNUM,DATATIME, ");                                              
		sb.append("   ADD_TIME,ADD_CYCLE,TOTALTIME,TOTALCYCLE,TIME_ONINSTALL,CYCLE_ONINSTALL,TIME_ONREPAIRED,CYCLE_ONREPAIRED,");
		sb.append("   REPAIR_FLAG,INSTALL_TIME,CORRECT_FLAG,COMMENTS,UPDATE_DATE,UPDATE_MAN) ");
		sb.append("SELECT S_L_APU_RUNLOG.NEXTVAL,apumodelid,ID,apusn,1 LOGTYPE, ");
		sb.append("   (select aircraftsn from b_aircraft a where a.id =aircraftid ) acnum, ");
		//如果是拆换新装APU，APU数据时间为拆换时间
		if(swapTime != null){
			sb.append(" TO_DATE('"+DateUtil.getLongDate(swapTime)+"', 'YYYY-MM-DD hh24:mi:ss') datatime,"); //拆换初始化
		}else{
			sb.append(" datatime,"); //新建初始化
		}
		sb.append("   0 add_time,0 add_cycle,totaltime,totalcycle,time_oninstall,cycle_oninstall,time_onrepaired, cycle_onrepaired, ");
		sb.append("   repair_flag, install_time, correctflag, comments,SYSDATE,UPDATE_MAN  ");
		sb.append("FROM B_APU WHERE APUSN='"+apusn+"' ");
		
		StringBuilder dailySb = new StringBuilder();
		dailySb.append("insert into l_apu_runlog_daily(		"); 
		dailySb.append("		ID,APU_MODELID,APUSN,ACNUM,DATE_STR,DATATIME,TOTALTIME,TOTALCYCLE, "); 
		dailySb.append("		TIME_ONINSTALL,CYCLE_ONINSTALL,TIME_ONREPAIRED,CYCLE_ONREPAIRED,REPAIR_FLAG,INSTALL_TIME,"); 
		dailySb.append("		CORRECT_FLAG,DAILY_ADDTIME,DAILY_ADDCYCLE,COMPUTESTATUS,UPDATE_DATE)  "); 
		dailySb.append("SELECT S_L_APU_RUNLOG_DAILY.NEXTVAL,apumodelid,apusn,  "); 
		dailySb.append("       (select aircraftsn from b_aircraft a where a.id =aircraftid ) acnum,    ");
		//如果是拆换新装APU，APU数据时间为拆换时间
		if(swapTime != null){
			dailySb.append(" '"+DateUtil.getDateStr(swapTime)+"' data_str,TO_DATE('"+DateUtil.getLongDate(swapTime)+"', 'YYYY-MM-DD hh24:mi:ss') datatime,");
		}else{
			dailySb.append(" to_char(datatime,'YYYY-MM-DD') data_str,datatime,");
		}
		dailySb.append("      totaltime,totalcycle,  "); 
		dailySb.append("      time_oninstall,cycle_oninstall,time_onrepaired, cycle_onrepaired, repair_flag, install_time, "); 
		dailySb.append("       correctflag, 0 add_time,0 add_cycle,1 COMPUTESTATUS,sysdate  "); 
		dailySb.append("FROM B_APU WHERE APUSN='"+apusn+"'"); 

		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, dailySb.toString());
		logger.debug("APU新增时，第一条运行日志初始化成功！");
	}
	
	//生成未来N天预测数据
	public void forecastRunLogDaily(String apusn) throws Exception{
		//1,删除当前及之后的归整日志
		String delSql = "delete l_apu_runlog_daily d where apusn=? and d.datatime > sysdate";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, delSql, apusn);
		
		//1, 历史数据重新计算
		HashVO[] vos_ar = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qryApu.toString(),apusn);
		String apumodelid=null, time_oninstall=null, cycle_oninstall=null;
		String time_onrepaired=null, cycle_onrepaired=null, repair_flag=null, correct_flag=null;
		
		Date install_time=null;
		int preDailyCyc,preDailyTime;
		
		if(vos_ar.length >0){
			HashVO vv1 = vos_ar[0];			
			apumodelid = vv1.getStringValue("apumodelid");
			time_oninstall = vv1.getStringValue("time_oninstall");
			cycle_oninstall = vv1.getStringValue("cycle_oninstall");
			time_onrepaired = vv1.getStringValue("time_onirepaired");
			cycle_onrepaired = vv1.getStringValue("cycle_onrepaired");
			repair_flag = vv1.getStringValue("repair_flag");
			install_time = vv1.getDateValue("install_time");
			correct_flag = vv1.getStringValue("correctflag");
			preDailyTime = vv1.getIntegerValue("day_predition_hour");
			preDailyCyc = vv1.getIntegerValue("day_predition_loop");
			String acnum = vv1.getStringValue("acnum");
			int totalTime = vv1.getIntegerValue("totaltime");
			int totalCyc = vv1.getIntegerValue("totalcycle");
			
			Date now = DateUtil.StringToDate(DateUtil.getDateStr(new Date()), "yyyy-MM-dd");
			String comments = DateUtil.getDateStr(now)+" 预测生成";
			
			for(int i=1;i<=foreCastDays;i++){
				int ahrs = totalTime + preDailyTime*60*i;
				int acyc = totalCyc + preDailyCyc*i;
				Date preDate = DateUtil.moveDay(now, i);
				
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertDaily.toString(), 
						apumodelid, apusn,acnum,DateUtil.getDateStr(preDate),preDate, ahrs,acyc,
						time_oninstall, cycle_oninstall,time_onrepaired, cycle_onrepaired, repair_flag, install_time,
						correct_flag,preDailyTime,preDailyCyc,"1",comments);
			}
			dmo.commit(ApmsConst.DS_APMS);
		
			logger.debug("APU["+apusn+"]预测数据生成成功!");
		}else{
			logger.debug("没有查到指定的APU["+apusn+"]信息!");
		}
			
	}
	
}
