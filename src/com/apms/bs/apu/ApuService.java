package com.apms.bs.apu;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.apu.vo.A_DFD_A13_COMPUTED;
import com.apms.bs.common.HashVoUtil;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.engine.EngineCfdService;
import com.apms.bs.oxygen.vo.A_DFD_RankPoint;
import com.apms.bs.util.DateUtil;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.vo.MathOneFitResult;
import com.apms.matlab.vo.MathOneXPerdictorAeraResult;
import com.apms.vo.ApmsVarConst;


import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.SimpleHashVO;

/**
 * apu相关服务处理
 * @author jerry
 * @date Apr 10, 2013
 */
public class ApuService
{
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO(); 
	
	
	/**
	 * APU计算后报文数据查询
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getApuChartData(String begin_date,String end_date,String acNum,String apusn,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询APU报文(13)计算数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		
		List<A_DFD_A13_COMPUTED> changList = new ArrayList<A_DFD_A13_COMPUTED>();
		List<A_DFD_A13_COMPUTED> allList = new ArrayList<A_DFD_A13_COMPUTED>();//除换瓶点后所有点
		List<A_DFD_A13_COMPUTED> oilChangeList = new ArrayList<A_DFD_A13_COMPUTED>();
		List<A_DFD_A13_COMPUTED> diagnoseExpList = new ArrayList<A_DFD_A13_COMPUTED>();
		
		//斜率点集合
		List<List<A_DFD_RankPoint>> s13RankList = new ArrayList<List<A_DFD_RankPoint>>();
		List<List<A_DFD_RankPoint>> s46RankList = new ArrayList<List<A_DFD_RankPoint>>();
		
		//查询数据对象
		HashVO[] vos = getComputedVos(begin_date, end_date, acNum,apusn);
		//计算数据数组
		List<Double> xarr1 = new ArrayList<Double>(vos.length);
		List<Double> yarr1 = new ArrayList<Double>(vos.length);
		List<Double> xarr2 = new ArrayList<Double>(vos.length);
		List<Double> yarr2 = new ArrayList<Double>(vos.length);
		
		
		for (int i = 0; i < vos.length; i++) {
			HashVO vo = vos[i];
			A_DFD_A13_COMPUTED ac = getComputedPoint(vo);
			
			//add 20161204 判断是否神经网络诊断出的异常类型
			if(ac.getDIAGNOSETYPE() != null && !"正常".equals(ac.getDIAGNOSETYPE()) ){
				diagnoseExpList.add(ac);
			}
			
			//根据点类型不同，加入到不同的数组中
			if(DfdVarConst.CHANGEPOINT_YES == ac.getISCHANGEPOINT()){
				//换瓶点
				changList.add(ac);
				
				if(isRank){
					//1 原先的数据计算
					List<A_DFD_RankPoint> s13Rank = computeRankList(xarr1,yarr1);
					s13RankList.add(s13Rank);
					
					List<A_DFD_RankPoint> s46Rank = computeRankList(xarr2,yarr2);
					s46RankList.add(s46Rank);
					
					//2 新构造一个斜率计算数组
					xarr1 = new ArrayList<Double>(vos.length);
					yarr1 = new ArrayList<Double>(vos.length);
					xarr2 = new ArrayList<Double>(vos.length);
					yarr2 = new ArrayList<Double>(vos.length);
				}
				
				//5点均值，设置连续曲线的断点，形成线段
				ac.setEGTP_V1_COR_ROLL5(null);
				ac.setNPA_V1_COR_ROLL5(null);
				ac.setHOT_OT_MAX_ROLL5(null);				
				ac.setEGTA_ROLL5(null);
				ac.setSTA_ROLL5(null);
				ac.setIGV_ROLL5(null);
				ac.setP2A_ROLL5(null);
				ac.setPT_ROLL5(null);
				ac.setAPR_ROLL5(null);
				ac.setOT_ROLL5(null);
				ac.setWB_ROLL5(null);
				
				oilChangeList.add(ac);
				allList.add(ac);
			}else{
				//滑油系统的更换点
				if(DfdVarConst.CHANGEPOINT_YES == vo.getIntegerValue("ISCHANGEOIL").intValue()){
					ac.setOT_ROLL5(null);
					oilChangeList.add(ac);
				}
				allList.add(ac);
				
			}
//			xarr1.add(new Double(vo.getDateValue("DATETIME_MID_S13").getTime()+""));//
//			yarr1.add(vo.getDoubleValue("PRES_ST_C15_S13"));
//			xarr2.add(new Double(vo.getDateValue("DATETIME_MID_S46").getTime()+""));//X数据为毫秒数
//			yarr2.add(vo.getDoubleValue("PRES_ST_C15_S46"));
		}
		
//		if(isRank){
//			//回归计算
//			List<A_DFD_RankPoint> s13Rank = computeRankList(xarr1,yarr1);
//			s13RankList.add(s13Rank);
//			
//			List<A_DFD_RankPoint> s46Rank = computeRankList(xarr2,yarr2);
//			s46RankList.add(s46Rank);
//		}
		
		if(acNum ==null || acNum.length() == 0){
			acNum = getAcnumByAsn(apusn);
		}
		EngineCfdService cfdService = new EngineCfdService();
		List<Map<String,Object>> warnList = cfdService.getApuWarnList(begin_date, end_date, acNum);
		List<Map<String,Object>> faultList = cfdService.getApuFaultList(begin_date, end_date, acNum);
		List<Map<String,Object>> workList = cfdService.getApuMWork(begin_date, end_date, acNum);
		
		if(allList.size() > 1){
			Map<String,Object> firstObj = HashVoUtil.hashVoToMap(vos[0]);
			Map<String,Object> lastObj = HashVoUtil.hashVoToMap(vos[vos.length-1]);
			
			warnList.add(0, firstObj);
			warnList.add(lastObj);
			
			faultList.add(0, firstObj);
			faultList.add(lastObj);
			
			workList.add(0, firstObj);
			workList.add(lastObj);
		}
		resMap.put("warnList",  warnList);
		resMap.put("faultList", faultList );
		resMap.put("workList", workList );
		
		resMap.put("changList", changList);
		resMap.put("allList",allList);
		resMap.put("oilChangeList", oilChangeList);
		resMap.put("diagnoseExpList",diagnoseExpList);
		
		logger.debug("结束查询APU报文(13)计算数据");
		
		return resMap;
		
	}
	
	/**
	 * N点回归斜率计算
	 * @param xList
	 * @param yList
	 * @return
	 */
	private List<A_DFD_RankPoint> computeRankList(List<Double> xList,List<Double> yList) throws Exception{
		
		List<A_DFD_RankPoint> rank = new ArrayList<A_DFD_RankPoint>();
		int pointCount = xList.size();//点个数
		
		Double[] xarr = new Double[pointCount];
		Double[] yarr = new Double[pointCount];
		xList.toArray(xarr); 
		yList.toArray(yarr);
		
		MatlabFunctionService funService = new MatlabFunctionService();
		//一元回归
		MathOneFitResult oneFit = funService.Math_One_OneFit(xarr,yarr);
		
		//根据回归公式y=kx+b, 计算出第一个点 和第N个点的回归值
//		double y_1 =  oneFit.getK()*xarr[0] + oneFit.getB();
//		double y_n =  oneFit.getK()*xarr[pointCount-1] + oneFit.getB();
		
		//计算预测范围 add by jerry 2013/3/24
		Double[] polyFitArr = new Double[2];
		polyFitArr[0] = oneFit.getK();
		polyFitArr[1] = oneFit.getB();
		//范围预测
		MathOneXPerdictorAeraResult areaResult = funService.Math_OneX_Perdictor_Aera(xarr, yarr, polyFitArr);
		
		for(int i=0;i<pointCount;i++){
			A_DFD_RankPoint point = new A_DFD_RankPoint();
			double y_i =  oneFit.getK()*xarr[i] + oneFit.getB();
			
			point.setK(oneFit.getK());
			point.setX(xarr[i]);
			point.setY(y_i);
			point.setUpValue( (areaResult.getY_result()[i]+areaResult.getDetaValue()[i]));
			point.setDownValue( (areaResult.getY_result()[i]-areaResult.getDetaValue()[i]));
			
			rank.add(point);
		}
		
		logger.debug("一元回归计算完成！");
		
		return rank;
	}
	
	private void pointMarkA23(String msgno,String eventType,String memo,String marker,String marktime) throws Exception{
		
		 String dfStr = "yyyy-MM-dd HH:mm:ss";
		 Date mtime = DateUtil.StringToDate(marktime, dfStr);
		
		String updateSql = "UPDATE A_DFD_A13_COMPUTED T SET T.EVENTTYPE=?,T.MARKMEMO=?,T.MARKER=?,T.MARKTIME=? WHERE T.MSG_NO=?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, eventType,memo,marker,mtime,msgno);
		logger.debug("A23计算数据,MSG_NO=["+msgno+"]事件类型更新！");
	}
	
	private HashVO getA23MarkEvent(String msg_no) throws Exception{
		String qrySql = "SELECT T.ID,T.MSG_NO,T.EVENTTYPE,T.MARKMEMO,T.MARKTIME,T.MARKER FROM A_DFD_A13_COMPUTED T where msg_no=?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrySql, msg_no);
		dmo.releaseContext(ApmsConst.DS_APMS);
		if(vos.length > 0)
			return vos[0];
		else
			return null;
		
		
	}
	
	/**
	 * HashVo数据封装成A_DFD_A23_COMPUTE对象
	 * @param vo
	 * @return
	 */
	private A_DFD_A13_COMPUTED getComputedPoint(HashVO vo){
		A_DFD_A13_COMPUTED ac = new A_DFD_A13_COMPUTED();
		
		ac.setID(vo.getStringValue("ID"));
		ac.setMSG_NO(vo.getStringValue("MSG_NO"));
		ac.setUTCDATE(vo.getDateValue("UTCDATE"));
		ac.setACNUM(vo.getStringValue("ACNUM"));
		
		//神经网络相关
		if(vo.getIntegerValue("DATATYPE")!= null){ //训练数据类型
			ac.setDATATYPE(vo.getIntegerValue("DATATYPE"));
		}
		if(vo.getStringValue("COMMENTS") !=null){
			ac.setCOMMENTS(vo.getStringValue("COMMENTS"));
		}
		
		//神经网络诊断类型
		if(vo.getStringValue("DIAGNOSETYPE")!= null){ //训练数据类型
			ac.setDIAGNOSETYPE(vo.getStringValue("DIAGNOSETYPE"));
		}
		
		ac.setISEXIST(vo.getIntegerValue("ISEXIST").toString());
		ac.setDEALSTATUS(vo.getStringValue("DEALSTATUS"));
		ac.setEVENTTYPE(vo.getStringValue("EVENTTYPE"));
		
		//红线值
		ac.setRL_NA(vo.getDoubleValue("RL_NA"));  
		ac.setRL_STA(vo.getDoubleValue("RL_STA"));
		ac.setRL_OTA(vo.getDoubleValue("RL_OTA"));
		ac.setRL_APR_COR(vo.getDoubleValue("RL_APR_COR"));
		ac.setRL_EGT_COR(vo.getDoubleValue("RL_EGT_COR"));
		ac.setRL_IGV(vo.getDoubleValue("RL_IGV"));
		ac.setRL_PT_COR(vo.getDoubleValue("RL_PT_COR"));
		ac.setRL_WB_COR(vo.getDoubleValue("RL_WB_COR"));
		
		ac.setRL_OT(vo.getDoubleValue("RL_OT"));
		ac.setRL_NPA_COR(vo.getDoubleValue("RL_NPA_COR"));
		ac.setRL_EGTP_COR(vo.getDoubleValue("RL_EGTP_COR"));
		ac.setRL_EGTP(vo.getDoubleValue("RL_EGTP"));
		ac.setRL_NPA(vo.getDoubleValue("RL_NPA"));
		ac.setRL_EGTA(vo.getDoubleValue("RL_EGTA"));
        
		ac.setISCHANGEPOINT(vo.getIntegerValue("ISCHANGEPOINT"));
		ac.setTAT(vo.getDoubleValue("TAT"));
		ac.setALT(vo.getDoubleValue("ALT"));
		ac.setASN(vo.getStringValue("ASN"));
		
		//转换为分钟!!
		ac.setAHRS(vo.getDoubleValue("AHRS"));
		ac.setAHRS_REPAIRED(vo.getDoubleValue("AHRS_REPAIRED"));
		ac.setACYC_REPAIRED(vo.getDoubleValue("ACYC_REPAIRED"));
		
		//设置小时-日期
		ac.setAHRS_DATE(ac.getAHRS()+"_" + DateUtil.getDateStr(ac.getUTCDATE()));
		
		ac.setACYC(vo.getDoubleValue("ACYC"));
		ac.setPDI_OLD(vo.getDoubleValue("PDI_OLD"));
		ac.setPDI_NEW(vo.getDoubleValue("PDI_NEW"));
		ac.setCODE(vo.getStringValue("CODE"));
		ac.setTHITA(vo.getDoubleValue("THITA"));
		ac.setTHITA_LCIT(vo.getDoubleValue("THITA_LCIT"));
		ac.setESN_MAX(vo.getStringValue("ESN_MAX"));
		ac.setNA_MAX(vo.getDoubleValue("NA_MAX"));
		ac.setEGTA_MAX(vo.getDoubleValue("EGTA_MAX"));
		ac.setIGV_MAX(vo.getDoubleValue("IGV_MAX"));
		ac.setP2A_MAX(vo.getDoubleValue("P2A_MAX"));
		ac.setLCIT_MAX(vo.getDoubleValue("LCIT_MAX"));
		ac.setWB_MAX(vo.getDoubleValue("WB_MAX"));
		ac.setPT_MAX(vo.getDoubleValue("PT_MAX"));
		ac.setLCDT_MAX(vo.getDoubleValue("LCDT_MAX"));
		ac.setOTA_MAX(vo.getDoubleValue("OTA_MAX"));
		ac.setGLA_MAX(vo.getDoubleValue("GLA_MAX"));
		ac.setSCV_MAX(vo.getDoubleValue("SCV_MAX"));
		ac.setHOT_MAX(vo.getDoubleValue("HOT_MAX"));
		ac.setSTA_V1(vo.getDoubleValue("STA_V1"));
		ac.setSTA_V1_EST_TIME(vo.getDoubleValue("STA_V1_EST_TIME"));
		ac.setEGTP_V1(vo.getDoubleValue("EGTP_V1"));
		ac.setNPA_V1(vo.getDoubleValue("NPA_V1"));
		ac.setOTA_V1(vo.getDoubleValue("OTA_V1"));
		ac.setLCIT_V1(vo.getDoubleValue("LCIT_V1"));
		
		ac.setEGTA_MAX_COR(vo.getDoubleValue("EGTA_MAX_COR"));
		ac.setEGTA_MAX_COR_EST_TIME(vo.getDoubleValue("EGTA_MAX_COR_EST_TIME"));
		
		ac.setP2A_MAX_COR(vo.getDoubleValue("P2A_MAX_COR"));
		ac.setPT_MAX_COR(vo.getDoubleValue("PT_MAX_COR"));
		ac.setWB_MAX_COR(vo.getDoubleValue("WB_MAX_COR"));
		ac.setPT_MAX_COR_EST_TIME(vo.getDoubleValue("PT_MAX_COR_EST_TIME"));
		ac.setAPR_MAX(vo.getDoubleValue("APR_MAX"));
		ac.setAPR_MAX_EST_TIME(vo.getDoubleValue("APR_MAX_EST_TIME"));
		ac.setEGTP_V1_COR(vo.getDoubleValue("EGTP_V1_COR"));
		ac.setNPA_V1_COR(vo.getDoubleValue("NPA_V1_COR"));
		ac.setHOT_OT_MAX(vo.getDoubleValue("HOT_OT_MAX"));
		ac.setOT_MAX(vo.getDoubleValue("OT_MAX"));
		ac.setOT_MAX_EST_TIME(vo.getDoubleValue("OT_MAX_EST_TIME"));
		ac.setOT_V1(vo.getDoubleValue("OT_V1"));
		
		//5点均值
		ac.setEGTP_V1_COR_ROLL5(vo.getDoubleValue("EGTP_V1_COR_ROLL5"));
		ac.setNPA_V1_COR_ROLL5(vo.getDoubleValue("NPA_V1_COR_ROLL5"));
		ac.setHOT_OT_MAX_ROLL5(vo.getDoubleValue("HOT_OT_MAX_ROLL5"));
		
		//字段计算值
		ac.setEGTA_ROLL5(vo.getDoubleValue("EGTA_ROLL5"));
		ac.setEGTA_POINTTYPE(vo.getDoubleValue("EGTA_POINTTYPE"));
		ac.setSTA_ROLL5(vo.getDoubleValue("STA_ROLL5"));
		ac.setSTA_POINTTYPE(vo.getDoubleValue("STA_POINTTYPE"));
		ac.setIGV_ROLL5(vo.getDoubleValue("IGV_ROLL5"));
		ac.setIGV_POINTTYPE(vo.getDoubleValue("IGV_POINTTYPE"));
		ac.setP2A_ROLL5(vo.getDoubleValue("P2A_ROLL5"));
		ac.setP2A_POINTTYPE(vo.getDoubleValue("P2A_POINTTYPE"));
		ac.setPT_ROLL5(vo.getDoubleValue("PT_ROLL5"));
		ac.setPT_POINTTYPE(vo.getDoubleValue("PT_POINTTYPE"));
		ac.setAPR_ROLL5(vo.getDoubleValue("APR_ROLL5"));
		ac.setAPR_POINTTYPE(vo.getDoubleValue("APR_POINTTYPE"));
		ac.setOT_ROLL5(vo.getDoubleValue("OT_ROLL5"));
		ac.setOT_POINTTYPE(vo.getDoubleValue("OT_POINTTYPE"));
		ac.setWB_ROLL5(vo.getDoubleValue("WB_ROLL5"));
		ac.setWB_POINTTYPE(vo.getDoubleValue("WB_POINTTYPE"));
		
		return ac;
	}
	
	/**
	 * 根据时间和机号 查询对应的A23报文计算数据
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	private HashVO[] getComputedVos(String begin_date,String end_date,String acNum,String apusn) throws Exception{
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,T.UTCDATE");
		sb.append(",ISCHANGEPOINT,T.TAT,T.ALT,T.ASN,PDI_OLD,PDI_NEW,T.CODE");
		sb.append(",ISCHANGEOIL,ISCHANGESTRATER");
		
		//告警消息及处理状态
		sb.append(",(SELECT DEALSTATUS FROM ALARM_MESSAGE M WHERE M.DATAVIEWPK_VALUE =T.MSG_NO AND ROWNUM=1) DEALSTATUS");
		sb.append(",(SELECT EVENTTYPE FROM A_DFD_REPORTDEALMEMO M WHERE M.MSG_NO =T.MSG_NO AND ROWNUM=1) EVENTTYPE");
		sb.append(",(select count(1) from l_apu_traindata where msg_no = t.msg_no) ISEXIST");
		
		//神经网络
		sb.append(",(select DATATYPE from l_apu_traindata where msg_no = t.msg_no) DATATYPE");
		sb.append(",(select COMMENTS from l_apu_traindata where msg_no = t.msg_no) COMMENTS");
		sb.append(",(SELECT VALUECN FROM L_APU_DIAGNOSERESULT R,B_DICTIONARY D WHERE R.MSG_NO=T.MSG_NO ");
		sb.append(" AND D.CLASSNAME='L_APU_TRAINDATA' AND D.ATTRIBUTENAME='DATATYPE' AND D.VALUE=R.DATATYPE) DIAGNOSETYPE");
	       
		//APU红线值
		sb.append(",AM.RL_EGT_COR,AM.RL_PT_COR,AM.RL_IGV,AM.RL_WB_COR,AM.RL_APR_COR,AM.RL_NA,AM.RL_STA,AM.RL_OTA,AM.RL_OT,AM.RL_NPA_COR,AM.RL_EGTP_COR,AM.RL_EGTP,AM.RL_NPA,AM.RL_EGTA");//增加了RL_OT，RL_NPA_COR，RL_EGTP_COR，RL_EGTP，RL_NPA，RL_EGTA
		
		sb.append(",AHRS/60 AHRS,ACYC,(L.TOTALTIME-L.TIME_ONREPAIRED)/60 AHRS_REPAIRED,(L.TOTALCYCLE-L.CYCLE_ONREPAIRED) ACYC_REPAIRED");
		
		sb.append(",THITA,THITA_LCIT,ESN_MAX,NA_MAX,EGTA_MAX,IGV_MAX");
		sb.append(",P2A_MAX,LCIT_MAX,WB_MAX,PT_MAX,LCDT_MAX,OTA_MAX");
		sb.append(",GLA_MAX,SCV_MAX,HOT_MAX,STA_V1,STA_V1_EST_TIME");
		sb.append(",EGTP_V1,NPA_V1,OTA_V1,LCIT_V1,EGTA_MAX_COR,EGTA_MAX_COR_EST_TIME");
		sb.append(",EGTP_V1_COR_ROLL5,NPA_V1_COR_ROLL5,HOT_OT_MAX_ROLL5");
		
		//复杂计算值查询
		sb.append(",T1.F_VALUE_ROLL5 EGTA_ROLL5,T1.V_POINTTYPE EGTA_POINTTYPE");//egta计算值
		sb.append(",T2.F_VALUE_ROLL5 STA_ROLL5,T2.V_POINTTYPE STA_POINTTYPE");//STA
		sb.append(",T3.F_VALUE_ROLL5 IGV_ROLL5,T3.V_POINTTYPE IGV_POINTTYPE");//IGV
		sb.append(",T4.F_VALUE_ROLL5 P2A_ROLL5,T4.V_POINTTYPE P2A_POINTTYPE");//P2A
		sb.append(",T5.F_VALUE_ROLL5 PT_ROLL5,T5.V_POINTTYPE PT_POINTTYPE");//PT 
		sb.append(",T6.F_VALUE_ROLL5 APR_ROLL5,T6.V_POINTTYPE APR_POINTTYPE");//APR
		sb.append(",T7.F_VALUE_ROLL5 OT_ROLL5,T7.V_POINTTYPE OT_POINTTYPE");//OT
		sb.append(",T8.F_VALUE_ROLL5 WB_ROLL5,T8.V_POINTTYPE WB_POINTTYPE");//WB
		
		sb.append(",P2A_MAX_COR,PT_MAX_COR,WB_MAX_COR,PT_MAX_COR_EST_TIME");
		sb.append(",APR_MAX,APR_MAX_EST_TIME,EGTP_V1_COR,NPA_V1_COR");
		sb.append(",HOT_OT_MAX,OT_MAX,OT_MAX_EST_TIME,OT_V1,RECDATETIME");
	    //sb.append(",EVENTTYPE,MARKER,MARKTIME");//事件类型、人工标记
	    sb.append(" FROM A_DFD_A13_COMPUTE T,L_APU_RUNLOG L ,B_APU_MODEL AM");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1,A_DFD_FIELD_COMPUTE T2,A_DFD_FIELD_COMPUTE T3,A_DFD_FIELD_COMPUTE T4");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T5,A_DFD_FIELD_COMPUTE T6,A_DFD_FIELD_COMPUTE T7,A_DFD_FIELD_COMPUTE T8");
	    sb.append(" WHERE UTCDATE>=? AND UTCDATE<=?");
	    if(acNum != null){
	    	sb.append(" AND T.ACNUM='"+acNum+"'");
	    }
	    if(apusn != null){
	    	sb.append(" AND T.ASN='"+apusn+"'");
	    }
	    sb.append(" AND L.MSG_NO=T.MSG_NO AND AM.ID= L.APU_MODELID");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='EGTA_MAX_COR'");
	    sb.append(" AND T2.MSG_NO =T.MSG_NO AND T2.F_NAME='STA_V1'");
	    sb.append(" AND T3.MSG_NO =T.MSG_NO AND T3.F_NAME='IGV_MAX'");
	    sb.append(" AND T4.MSG_NO =T.MSG_NO AND T4.F_NAME='P2A_MAX_COR'");
	    sb.append(" AND T5.MSG_NO =T.MSG_NO AND T5.F_NAME='PT_MAX_COR'");
	    sb.append(" AND T6.MSG_NO =T.MSG_NO AND T6.F_NAME='APR_MAX'");
	    sb.append(" AND T7.MSG_NO =T.MSG_NO AND T7.F_NAME='OT_MAX'");
	    sb.append(" AND T8.MSG_NO =T.MSG_NO AND T8.F_NAME='WB_MAX_COR'");
	    
	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	
	
	/**
	 *
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @param asn
	 * @param peirodFormat 日期分析格式  yyyy-mm, yyyy-q,yyyy-ww,yyyy等
	 * @return
	 * @throws Exception
	 */
	public  Map<String, Object> getApuRunStatisInfo(String begin_date,String end_date,String basicID,String acNum,String aputype,String asn,String periodFormat) throws Exception{
		StringBuilder sb = new StringBuilder();
		StringBuilder querySb = new StringBuilder();
		if ((basicID != null)&&(aputype != null)){//根据基地和型号查询
			
			sb.append("SELECT (SELECT AM.MODEL FROM B_APU_MODEL AM  WHERE AM.ID=T.APUMODELID) APUMODEL,");
			sb.append("(SELECT NAME FROM B_ORGANIZATION O WHERE O.ID=T.BASEORGID) BASEORG,");
		    sb.append("T.DATAPERIOD,T.HOUR_SUM,T.CYCLE_SUM");       
		    sb.append(" FROM (SELECT A.APUMODELID,A.BASEORGID,");
		    sb.append("TO_CHAR(L.DATATIME, '"+periodFormat+"') DATAPERIOD,");// --周期格式
		    sb.append("ROUND(SUM(ADD_TIME) / 60, 2) HOUR_SUM,");
		    sb.append("SUM(ADD_CYCLE) CYCLE_SUM");
		    sb.append(" FROM L_APU_RUNLOG L,B_APU A");
		    sb.append(" WHERE 1 = 1 AND A.ID=L.APU_ID");
		           //--AND L.ACNUM = 'B6677', 条件，设置条件型号过滤
		    if(basicID != null){
		    	sb.append(" AND A.BASEORGID='"+basicID+"'");
		    }
		    if(aputype != null){
		    	sb.append(" AND A.APUMODELID='"+aputype+"'");
		    }
		    if(acNum != null){
		    	sb.append(" AND L.ACNUM='"+acNum+"'");
		    }
		    if(asn != null){
		    	sb.append(" AND L.ASN='"+asn+"'");
		    }
		    sb.append(" AND L.DATATIME >= TO_DATE('"+begin_date+"', 'YYYY-MM-DD HH24:MI:SS')");
		    sb.append(" AND L.DATATIME <= TO_DATE('"+end_date+"', 'YYYY-MM-DD HH24:MI:SS')");
		    sb.append(" GROUP BY A.APUMODELID,A.BASEORGID,TO_CHAR(L.DATATIME, '"+periodFormat+"')");// --分组
		    sb.append(" ORDER BY DATAPERIOD ) T ");
		    sb.append(" ORDER BY T.DATAPERIOD");
		    querySb.append(sb.toString());
		 
		}
		else if((basicID != null) && (aputype == null || aputype == "" )){//根据基地查询
			sb.append("SELECT ");
			sb.append("(SELECT NAME FROM B_ORGANIZATION O WHERE O.ID=T.BASEORGID) BASEORG,");
		    sb.append("T.DATAPERIOD,T.HOUR_SUM,T.CYCLE_SUM");       
		    sb.append(" FROM (SELECT A.BASEORGID,");
		    sb.append("TO_CHAR(L.DATATIME, '"+periodFormat+"') DATAPERIOD,");// --周期格式
		    sb.append("ROUND(SUM(ADD_TIME) / 60, 2) HOUR_SUM,");
		    sb.append("SUM(ADD_CYCLE) CYCLE_SUM");
		    sb.append(" FROM L_APU_RUNLOG L,B_APU A");
		    sb.append(" WHERE 1 = 1 AND A.ID=L.APU_ID");
		           //--AND L.ACNUM = 'B6677', 条件，设置条件型号过滤
		    if(basicID != null){
		    	sb.append(" AND A.BASEORGID='"+basicID+"'");
		    }
		    if(acNum != null){
		    	sb.append(" AND L.ACNUM='"+acNum+"'");
		    }
		    if(asn != null){
		    	sb.append(" AND L.ASN='"+asn+"'");
		    }
		    //if(aputype != null){
		    //	sb.append(" AND T.APUMODELID='"+aputype+"'");
		    //}
		    
		    sb.append(" AND L.DATATIME >= TO_DATE('"+begin_date+"', 'YYYY-MM-DD HH24:MI:SS')");
		    sb.append(" AND L.DATATIME <= TO_DATE('"+end_date+"', 'YYYY-MM-DD HH24:MI:SS')");
		    sb.append(" GROUP BY A.BASEORGID,TO_CHAR(L.DATATIME, '"+periodFormat+"')");// --分组
		    sb.append(" ORDER BY DATAPERIOD ) T ");
		    sb.append(" ORDER BY T.DATAPERIOD");
		    querySb.append(sb.toString());
		}
		else if((basicID == null||basicID == "") && (aputype != null)){//根据型号查询
			sb.append("SELECT AM.MODEL,T.DATAPERIOD,T.HOUR_SUM, T.CYCLE_SUM ");       
			sb.append(" FROM (SELECT APU_MODELID,");
			sb.append("TO_CHAR(L.DATATIME, '"+periodFormat+"') DATAPERIOD,");// --周期格式
		    sb.append("ROUND(SUM(ADD_TIME) / 60, 2) HOUR_SUM,");
			sb.append("SUM(ADD_CYCLE) CYCLE_SUM FROM L_APU_RUNLOG L ");
			sb.append(" WHERE 1 = 1");
			 if(aputype != null){
			    	sb.append(" AND L.APU_MODELID='"+aputype+"'");
			    }
			 if(acNum != null){
			    	sb.append(" AND L.ACNUM='"+acNum+"'");
			    }
			 if(asn != null){
			    	sb.append(" AND L.ASN='"+asn+"'");
			   }
		          // --AND L.ACNUM = 'B6677', 条件，设置条件型号过滤
			sb.append(" AND L.DATATIME >= TO_DATE('"+begin_date+"', 'YYYY-MM-DD HH24:MI:SS')");
			sb.append(" AND L.DATATIME <= TO_DATE('"+end_date+"', 'YYYY-MM-DD HH24:MI:SS')");
			   
		    sb.append(" GROUP BY APU_MODELID,TO_CHAR(L.DATATIME, '"+periodFormat+"')");
			sb.append(" ORDER BY DATAPERIOD) T,B_APU_MODEL AM");
			sb.append(" WHERE AM.ID=T.APU_MODELID ORDER BY T.DATAPERIOD");
			querySb.append(sb.toString());
		}
		else{//根据飞机号查询，或是更加apu序号查询
			
		
				sb.append("SELECT ACNUM,ASN");
				sb.append(" ,TO_CHAR(L.DATATIME,'"+periodFormat+"') DATAPERIOD");
				sb.append(" ,ROUND(SUM(ADD_TIME)/60,2) HOUR_SUM,SUM(ADD_CYCLE) CYCLE_SUM");
				sb.append(" FROM  L_APU_RUNLOG L");//,b_apu_org b
				sb.append(" WHERE 1=1 ");//and l.asn=b.apusn 
				if(acNum != null){
			    	sb.append(" AND L.ACNUM='"+acNum+"'");
			    }
			    if(asn != null){
			    	sb.append(" AND L.ASN='"+asn+"'");
			    }
			    sb.append(" AND L.DATATIME >= TO_DATE('"+begin_date+"','YYYY-MM-DD HH24:MI:SS') ");
				sb.append(" AND L.DATATIME <= TO_DATE('"+end_date+"','YYYY-MM-DD HH24:MI:SS')");
				sb.append(" GROUP BY ACNUM,ASN,TO_CHAR(L.DATATIME,'"+periodFormat+"')");
				sb.append(" ORDER BY DATAPERIOD");
				
				querySb.append("SELECT T.ACNUM,T.ASN,T.DATAPERIOD,T.HOUR_SUM,T.CYCLE_SUM ");
				querySb.append(",ROUND(A.TOTALTIME/60,2) TOTALHOUR,A.TOTALCYCLE");
				querySb.append(",ROUND((A.TOTALTIME-A.TIME_ONREPAIRED)/60,2) REPAIREDHOUR,(A.TOTALCYCLE-A.CYCLE_ONREPAIRED) REPAIREDCYCLE");
				querySb.append(" FROM ("+sb.toString()+") T,B_APU A");
				querySb.append(" WHERE A.APUSN=T.ASN");
				querySb.append(" ORDER BY T.DATAPERIOD");
				querySb.append("");
		}
	
		
		
		
		//按区间统计的APU运行小时数
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySb.toString());
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<Map<String, Object>> runStatisList = HashVoUtil.hashVosToMapList(vos);
		
		//APU基本信息包含在每一条记录中
		//Map<String, Object> apuInfo = HashVoUtil.hashVoToMap(getApuHashVo(asn));
		//resMap.put("apuInfo",apuInfo);
		
		resMap.put("runStatisList", runStatisList);
		
		
		return resMap;
	}
	
	private HashVO getApuHashVo(String asn) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT APUSN ASN,ROUND(A.TOTALTIME/60,2) TOTALHOUR,A.TOTALCYCLE");
		sb.append(",ROUND((A.TOTALTIME-A.TIME_ONREPAIRED)/60,2) REPAIREDHOUR,(A.TOTALCYCLE-A.CYCLE_ONREPAIRED) REPAIREDCYCLE");
		sb.append(" FROM B_APU A");
		sb.append(" WHERE A.APUSN='"+asn+"'");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		if(vos.length > 0){
			return vos[0];
		}else{
			return null;
		}
	 
	
	}
	
	public SimpleHashVO[] getA38ComputeChartData(String begin_date,String end_date,String acNum,String apusn,Boolean isRank) throws Exception{
		try {
			String sql = "SELECT c.MSG_NO, CT5ATP, CPTATP, CWFATP, IGVATP,BDTMAX,c.RPTDATE UTCDATE,"
		        + " F1.V_POINTTYPE CT5ATP_POINTTYPE, F1.F_VALUE_ROLL5 CT5ATP_ROLL5,"
		        + " F2.V_POINTTYPE CPTATP_POINTTYPE, F2.F_VALUE_ROLL5 CPTATP_ROLL5,"
		        + " F3.V_POINTTYPE CWFATP_POINTTYPE, F3.F_VALUE_ROLL5 CWFATP_ROLL5,"
		        + " F4.V_POINTTYPE IGVATP_POINTTYPE, F4.F_VALUE_ROLL5 IGVATP_ROLL5,"
		        + " F5.V_POINTTYPE BDTMAX_POINTTYPE, F5.F_VALUE_ROLL5 BDTMAX_ROLL5,"
		        + " (SELECT COUNT(1) FROM ALARM_MESSAGE A WHERE A.RPTNO='A38' AND A.DATAVIEWPK_VALUE=c.MSG_NO) ALARMNUM,"
		        + " (select RL_EGT_ATP from B_APU_MODEL AM,B_APU A,B_AIRCRAFT AC where AM.ID=A.APUMODELID AND A.AIRCRAFTID=AC.ID AND AC.AIRCRAFTSN='"+acNum+"') RL_EGT_ATP,"
		        + " (select RL_PT_ATP from B_APU_MODEL AM,B_APU A,B_AIRCRAFT AC where AM.ID=A.APUMODELID AND A.AIRCRAFTID=AC.ID AND AC.AIRCRAFTSN='"+acNum+"') RL_PT_ATP,"
		        + " (select RL_WF_ATP from B_APU_MODEL AM,B_APU A,B_AIRCRAFT AC where AM.ID=A.APUMODELID AND A.AIRCRAFTID=AC.ID AND AC.AIRCRAFTSN='"+acNum+"') RL_WF_ATP,"
		        + " (select RL_IGV_ATP from B_APU_MODEL AM,B_APU A,B_AIRCRAFT AC where AM.ID=A.APUMODELID AND A.AIRCRAFTID=AC.ID AND AC.AIRCRAFTSN='"+acNum+"') RL_IGV_ATP,"
		        + " (select RL_BDT_ATP from B_APU_MODEL AM,B_APU A,B_AIRCRAFT AC where AM.ID=A.APUMODELID AND A.AIRCRAFTID=AC.ID AND AC.AIRCRAFTSN='"+acNum+"') RL_BDT_ATP"
		        + " FROM a_dfd_a38_compute c,a_dfd_field_compute f1,a_dfd_field_compute f2,a_dfd_field_compute f3,a_dfd_field_compute f4,a_dfd_field_compute f5"
		        + " WHERE c.RPTDATE>=? AND c.RPTDATE<=?";
		    if(acNum != null){
		    	sql += " AND c.ACNUM='"+acNum+"'";
		    }
		    if(apusn != null){
		    	sql += " AND c.ASN='"+apusn+"'";
		    } 
    		sql += " and c.msg_no = f1.msg_no AND F1.F_NAME='CT5ATP' AND c.msg_no = f2.msg_no AND F2.F_NAME='CPTATP' AND c.msg_no = f3.msg_no AND F3.F_NAME='CWFATP' AND c.msg_no = f4.msg_no AND F4.F_NAME='IGVATP' AND c.msg_no = f5.msg_no AND F5.F_NAME='BDTMAX'";
		      
			String dfStr = "yyyy-MM-dd HH:mm:ss";
		    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
		    Date endDt = DateUtil.StringToDate(end_date, dfStr);
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql,beginDt,endDt);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		} catch (Exception e) {
			logger.error("getSimpleHashVoArrayByDS 错误！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	private String getAcnumByAsn(String asn) throws Exception{
		String acnum = "";
		//0,如果是飞机号，换算成ASN
		String sql = "SELECT APUSN,AIRCRAFTSN ACNUM FROM B_APU A,B_AIRCRAFT T WHERE A.AIRCRAFTID=T.ID  AND A.APUSN=? ";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, asn);
		if(vos.length > 0){
			acnum = vos[0].getStringValue("ACNUM");
		}else{
			throw new Exception("根据APU序号["+asn+"]没有找到对应的飞机号信息！");
		}
		
		if(acnum == null || acnum.length() < 1){
			throw new Exception("根据APU序号["+asn+"]找到的飞机号为空！");
		}
		
		return acnum;
	}
	
}
