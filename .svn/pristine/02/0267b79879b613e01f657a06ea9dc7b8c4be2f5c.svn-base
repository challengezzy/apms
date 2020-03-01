package com.apms.bs.apu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.common.HashVoUtil;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * APU概览数据服务
 * 
 * @author jerry
 * @date Dec 20, 2016
 */
public class ApuOverviewDataService {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	private CommDMO dmo = new CommDMO();

	public Map<String, Object> getOverviewData(String begin_date, String end_date, String acnum, String asn) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>(64);

		if (asn == null || asn.length() == 0) {
			asn = getAsnByAcnum(acnum);
		}
		
		if(acnum ==null || acnum.length() == 0){
			acnum = getAcnumByAsn(asn);
		}
		
		Map<String, Object> basicInfo = getApuBasicInfo(asn);
		resMap.put("basicInfo", basicInfo);
		
		Date beginTime = DateUtil.StringToDate(begin_date, "yyyy-MM-dd HH:mm:ss");
		Date endTime = DateUtil.StringToDate(end_date, "yyyy-MM-dd HH:mm:ss");
		
		//小时循环和燃油运行情况
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ASN,ROUND(SUM(ADD_TIME)/60,3) SUM_HOUR,SUM(ADD_CYCLE) SUM_CYCLE");
		sb.append(" FROM L_APU_RUNLOG L WHERE ASN=? AND L.DATATIME >=? and L.DATATIME <=? ");
		sb.append(" GROUP BY ASN");
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),asn,beginTime,endTime );
		double sumHour = 0;//运行时间
		double sumCycle = 0;//运行循环
		if(vos.length > 0){
			sumHour = vos[0].getDoubleValue("SUM_HOUR");
			sumCycle = vos[0].getDoubleValue("SUM_CYCLE");
		}
		basicInfo.put("SUM_HOUR", sumHour);
		basicInfo.put("SUM_CYCLE", sumCycle);
		
		//燃油消耗
		sb = new StringBuilder();
		sb.append("SELECT L.ASN, ROUND(SUM(L.FUSED*L.PRICE_KG),2) FUELOIL_COST,SUM(L.FUSED) SUM_FUELUSED");
		sb.append(",ROUND(SUM(L.FUSED)/SUM(L.OPERATE_MINS)*60,3) PERHOUR_FUELUSED");
		sb.append(" FROM V_APUOIL_HIS L WHERE L.ASN = ? AND L.RPTDATE >= ? AND L.RPTDATE <= ? ");
		sb.append(" GROUP BY L.ASN");
		vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),asn,beginTime,endTime );
		if(vos.length > 0){
			basicInfo.put("SUM_FUELUSED", vos[0].getDoubleValue("SUM_FUELUSED"));
			basicInfo.put("PERHOUR_FUELUSED", vos[0].getDoubleValue("PERHOUR_FUELUSED"));
		}else{
			basicInfo.put("SUM_FUELUSED", 0);
			basicInfo.put("PERHOUR_FUELUSED", 0);
		}
		
		//查询告警次数
		sb = new StringBuilder();
		sb.append("SELECT COUNT(1) AMOUNT FROM A_CFD_WARNING T,A_CFD_HEAD H");
		sb.append(" WHERE H.MSG_NO=T.MSG_NO AND ATANO_MAJOR ='49' AND H.ACNUM = ? ");
		sb.append(" AND H.HEADTIME >= ? AND H.HEADTIME <= ? ");
		vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),acnum,beginTime,endTime );
		double cfdWarnNum = 0;
		double cfdWarn100Hour = 0;
		if(vos.length > 0){
			cfdWarnNum = vos[0].getDoubleValue("AMOUNT");
			if(sumHour > 0){
				cfdWarn100Hour = cfdWarnNum/sumHour;
			}
		}
		DecimalFormat df = new DecimalFormat("0.000");
		Map<String, Object> cfdInfo = new HashMap<String, Object>();
		cfdInfo.put("CFDWARNNUM", cfdWarnNum );
		cfdInfo.put("CFDWARN100HOUR", df.format(cfdWarn100Hour) );
		
		//查询CFD故障次数
		sb = new StringBuilder();
		sb.append("SELECT COUNT(1) AMOUNT FROM A_CFD_FAULT T,A_CFD_HEAD H");
		sb.append(" WHERE H.MSG_NO=T.MSG_NO AND ATANO_MAJOR ='49' AND H.ACNUM = ? ");
		sb.append(" AND H.HEADTIME >= ? AND H.HEADTIME <= ? ");
		vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),acnum,beginTime,endTime );
		double cfdFaultNum = 0;
		double cfdFault100Hour = 0;
		if(vos.length > 0){
			cfdFaultNum = vos[0].getDoubleValue("AMOUNT");
			if(sumHour > 0){
				cfdFault100Hour = cfdFaultNum/sumHour;
			}
		}
		cfdInfo.put("CFDFAULTNUM", cfdFaultNum );
		cfdInfo.put("CFDFAULT100HOUR", df.format(cfdFault100Hour) );
		resMap.put("cfdInfo", cfdInfo);
		
		resMap.put("most3FaultList", getCfdFaultMost3(acnum, beginTime, endTime));
		resMap.put("most3WarntList", getCfdWarningMost3(acnum, beginTime, endTime));
		
		//查询该APU型号的故障拆发运行信息
		String apuModel = getModelByAsn(asn);
		resMap.put("faultRunStatisticsList", getFaultRunStatistics(apuModel));
		
		sb = new StringBuilder();
		sb.append("SELECT T.APUMODEL,ROUND(AVG(T.RUNHOUR),3) AVGHOUR  FROM V_APU_FUALTSWAP T ");
		sb.append(" WHERE T.APUMODEL=? GROUP BY T.APUMODEL ");
		vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),apuModel );
		double modelAvgHour = 0;
		if(vos.length > 0){
			modelAvgHour = vos[0].getDoubleValue("AVGHOUR");
		}
		basicInfo.put("MODELAVGHOUR", modelAvgHour);
		basicInfo.put("APUMODEL", apuModel);
		
		//小时循环运行统计
		resMap.put("weekRunStatisticsList", getWeekRunStatistics(asn, beginTime,endTime));
		resMap.put("workMhSumList", getWorkMhSumList(asn, beginTime,endTime));
		
		//分别统计燃油成本、小修成本、和包修成本
		resMap.put("costTypeSumList", getCostTypeSumList(asn, beginTime, endTime));
		
		return resMap;
	}
	
	private List<Map<String,Object>> getCostTypeSumList(String asn,Date beginTime,Date endTime) throws Exception{
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>(3);
		
		String sql = "SELECT ROUND(SUM(L.FUSED*L.PRICE_KG),2) COST FROM V_APUOIL_HIS L " +
					" WHERE L.ASN = ? AND L.RPTDATE >= ? AND L.RPTDATE <= ?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql,asn, beginTime, endTime );
		Double oilcost = vos[0].getDoubleValue("COST");
		if(oilcost == null){
			oilcost = new Double(0);
		}
		
		Map<String,Object> objMap = new HashMap<String, Object>();
		objMap.put("COST", oilcost);
		objMap.put("COSTTYPE", "燃油成本");
		listMap.add(objMap);
		
		sql = "SELECT ROUND(SUM(TOTALCOST),2) COST " +
				" FROM V_APU_REPAIR_LOG WHERE ASN=? AND REPAIRDATE >=? AND REPAIRDATE <=?";
		vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql,asn, beginTime, endTime );
		Double repairCost = vos[0].getDoubleValue("COST");
		if(repairCost == null){
			repairCost = new Double(0);
		}
		
		objMap = new HashMap<String, Object>();
		objMap.put("COST", repairCost);
		objMap.put("COSTTYPE", "维修成本");
		listMap.add(objMap);
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT SUM(L.ADD_TIME*C.HOURCOST/60) COST ");
		sb.append(" FROM L_APU_RUNLOG L,B_APU A,O_APU_HOURMAINCOST C ");
		sb.append(" WHERE C.APUMODELID=A.APUMODELID AND A.APUSN=L.ASN");
		sb.append("  AND L.ASN = ? AND L.DATATIME >=? and L.DATATIME <=? ");
		sb.append("");
		vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString() ,asn, beginTime, endTime );
		Double haulCost = vos[0].getDoubleValue("COST");
		if(haulCost == null){
			haulCost = new Double(0);
		}
		
		objMap = new HashMap<String, Object>();
		objMap.put("COST", haulCost);
		objMap.put("COSTTYPE", "大修成本");
		listMap.add(objMap);
		
		return listMap;
	}
	
	/**
	 * 获取出现频率前3位的告警列表
	 */
	private List<Map<String,Object>> getWorkMhSumList(String asn,Date beginTime,Date endTime) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT NRWORK,SUM(MH) MH_SUM FROM V_APU_REPAIR_LOG T");
		sb.append(" WHERE ASN = ? AND REPAIRDATE >= ? AND REPAIRDATE <= ? ");
		sb.append(" GROUP BY NRWORK ");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),asn, beginTime, endTime );
		
		List<Map<String,Object>> listMap = HashVoUtil.hashVosToMapList(vos);
		
		//缺失的数据用0补充
		boolean isHaveNr = false;
		boolean isHaveR = false;
		for(Map<String, Object> obj : listMap){
			if("例行".equals(obj.get("NRWORK")) ){
				isHaveR = true;
			}else if("非例行".equals(obj.get("NRWORK")) ){
				isHaveNr = true;
			}
		}
		
		if(!isHaveNr){
			Map<String,Object> objMap = new HashMap<String, Object>();
			objMap.put("MH_SUM", "0");
			objMap.put("NRWORK", "非例行");
			
			listMap.add(objMap);
		}
		
		if(!isHaveR){
			Map<String,Object> objMap = new HashMap<String, Object>();
			objMap.put("MH_SUM", "0");
			objMap.put("NRWORK", "例行");
			listMap.add(objMap);
		}
		
		return listMap;
	}
	
	/**
	 * 获取APU按周的运行数据
	 * @param asn
	 * @return
	 * @throws Exception
	 */
	private List<Map<String,Object>> getWeekRunStatistics(String asn,Date beginTime,Date endTime) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT TO_CHAR(T.DATATIME,'YYYY-WW') PERIODSTR,SUM(ADD_TIME)/60 HOUR_SUM,SUM(ADD_CYCLE) CYCLE_SUM");
		sb.append(" FROM L_APU_RUNLOG T  ");
		sb.append(" WHERE T.ASN = ? AND T.DATATIME >= ? AND T.DATATIME <= ? ");
		sb.append("  GROUP BY TO_CHAR(T.DATATIME,'YYYY-WW')");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),asn, beginTime, endTime );
		
		List<Map<String,Object>> listMap = HashVoUtil.hashVosToMapList(vos);
		
		return listMap;
	}
	
	/**
	 * 根据型号统计APU故障拆下前的运行时间分布图
	 * @param apuModel
	 * @return
	 * @throws Exception
	 */
	private List<Map<String,Object>> getFaultRunStatistics(String apuModel) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT T.APUMODEL,N.NAME HOURSCOPE,COUNT(1) AMOUNT");
		sb.append(" FROM V_APU_FUALTSWAP T,O_NUMRANGE N ");
		sb.append("WHERE N.STARTNUM <= T.RUNHOUR AND N.ENDNUM >= T.RUNHOUR");
		sb.append(" AND N.TYPE = 1000 AND T.APUMODEL=? ");
		sb.append("  GROUP BY T.APUMODEL,N.NAME ORDER BY T.APUMODEL,N.NAME");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),apuModel );
		
		List<Map<String,Object>> listMap = HashVoUtil.hashVosToMapList(vos);
		
		return listMap;
	}
	
	/**
	 * 获取出现频率前3位的告警列表
	 */
	private List<Map<String,Object>> getCfdFaultMost3(String acnum,Date beginTime,Date endTime) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM (");
		sb.append(" SELECT T.FAULT_CONTENT,COUNT(1) AMOUNT FROM A_CFD_FAULT T,A_CFD_HEAD H");
		sb.append(" WHERE H.MSG_NO=T.MSG_NO AND ATANO_MAJOR ='49' AND H.ACNUM = ?");
		sb.append("   AND H.HEADTIME >= ? AND H.HEADTIME <= ? ");
		sb.append(" GROUP BY FAULT_CONTENT ORDER BY AMOUNT DESC ");
		sb.append(" ) WHERE ROWNUM <=3 ");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),acnum, beginTime, endTime );
		
		List<Map<String,Object>> listMap = HashVoUtil.hashVosToMapList(vos);
		
		return listMap;
	}
	
	/**
	 * 获取出现频率前3位的故障列表
	 */
	private List<Map<String,Object>> getCfdWarningMost3(String acnum,Date beginTime,Date endTime) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM (");
		sb.append(" SELECT T.WARN_CONTENT,COUNT(1) AMOUNT FROM A_CFD_WARNING T,A_CFD_HEAD H");
		sb.append(" WHERE H.MSG_NO=T.MSG_NO AND ATANO_MAJOR ='49' AND H.ACNUM = ?");
		sb.append("   AND H.HEADTIME >= ? AND H.HEADTIME <= ? ");
		sb.append(" GROUP BY WARN_CONTENT ORDER BY AMOUNT DESC ");
		sb.append(" ) WHERE ROWNUM <=3 ");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),acnum, beginTime, endTime );
		
		List<Map<String,Object>> listMap = HashVoUtil.hashVosToMapList(vos);
		
		return listMap;
	}
	
	private String getModelByAsn(String asn) throws Exception{
		String apuModel = "";
		//0,如果是飞机号，换算成ASN
		String sql = "SELECT AM.SUBMODEL,A.APUSN FROM B_APU A,B_APU_MODEL AM WHERE AM.ID=A.APUMODELID AND A.APUSN = ?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, asn);
		if(vos.length > 0){
			apuModel = vos[0].getStringValue("SUBMODEL");
		}else{
			throw new Exception("根据飞机号["+asn+"]没有找到对应的APU型号信息！");
		}
		
		if(asn == null || asn.length() < 1){
			throw new Exception("根据飞机号["+asn+"]找到的APU型号为空！");
		}
		
		return apuModel;
	}
	

	/**
	 * 根据飞机号查当前APU
	 * @param acnum
	 * @return
	 * @throws Exception
	 */
	private String getAsnByAcnum(String acnum) throws Exception{
		String asn = "";
		//0,如果是飞机号，换算成ASN
		String sql = "SELECT APUSN,AIRCRAFTSN ACNU FROM B_APU A,B_AIRCRAFT T WHERE A.AIRCRAFTID=T.ID AND T.AIRCRAFTSN=?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, acnum);
		if(vos.length > 0){
			asn = vos[0].getStringValue("APUSN");
		}else{
			throw new Exception("根据飞机号["+acnum+"]没有找到对应的APU序号信息！");
		}
		
		if(asn == null || asn.length() < 1){
			throw new Exception("根据飞机号["+acnum+"]找到的APU序号为空！");
		}
		
		return asn;
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
	
	private Map<String, Object> getApuBasicInfo(String asn) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T.APUSN,T.BASEORGNAME,T.ACNUM,T.APUMODEL,T.FUNCSTATUS_CN,");
		sb.append(" T.DATATIME,T.DATATIME_STR,");
		sb.append(" T.FILENUM,T.TOTALTIME_HOUR,T.TOTALCYCLE,T.TSI,T.CSI,T.TSR,T.CSR,POSITION_CN,T.ID");
		sb.append(" FROM V_APU_OVERVIEW T");
		sb.append(" WHERE APUSN = '"+asn+"'");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString() );
		
		if(vos.length < 1){
			throw new Exception("根据APU序号["+ asn +"]未查到APU的基础信息");
		}
		HashVO vo = vos[0];
		
		Map<String, Object> apuMap = HashVoUtil.hashVoToMap(vo);
		
		return apuMap;
	}
}
