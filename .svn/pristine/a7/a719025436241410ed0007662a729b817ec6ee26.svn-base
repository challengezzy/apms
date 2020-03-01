package com.apms.bs.fueloil;

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
 * 燃油成本查询
 * @author jerry
 * @date Nov 15, 2016
 */
public class FuelCostService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO(); 
	
	/**
	 * 燃油成本分析，数据
	 * @param begin_date
	 * @param end_date
	 * @param acnum
	 * @param asn
	 * @param isInstalled 是否从安装后开始计时
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getFuelOilCostData(String begin_date,String end_date
				,String acnum,String asn,boolean isInstalled) throws Exception{
		
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		
		if(asn == null || asn.length() ==0){
			asn = getAsnByAcnum(acnum);
		}
		
		Date beginTime = DateUtil.StringToDate(begin_date, "yyyy-MM-dd HH:mm:ss");
		Date endTime = DateUtil.StringToDate(end_date, "yyyy-MM-dd HH:mm:ss");
		
		int startHour = getStartHour(asn, beginTime);//开始小时 
		int endHour = getEndHour(asn, endTime);//开始小时
		
		String fuelSql = "SELECT L.ASN,R.NAME/100 NAME, ROUND(SUM(L.FUSED*L.PRICE_KG),2) FUELOIL_COST,SUM(L.FUSED) SUM_USED "
			+ " FROM V_APUOIL_HIS L,O_NUMRANGE R "
			+ " WHERE R.TYPE=1 AND L.ASN = ? AND L.AHRS > R.STARTNUM AND L.AHRS <= R.ENDNUM "
			+ " AND L.RPTDATE >= ? AND L.RPTDATE <= ? "
			+ " GROUP BY L.ASN,R.NAME ORDER BY NAME";
		HashVO[] fuelVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, fuelSql, asn, beginTime, endTime);
		
		List<Map<String,Object>> fuelCostList = new ArrayList<Map<String,Object>>();
		fuelCostList.addAll(HashVoUtil.hashVosToMapList(fuelVos));
		resMap.put("fuelCostList", fuelCostList);
		
		String repairCostSql = "SELECT ASN,ACNUM,NAME/100 NAME,SUM_REPAIRCOST,SUM_MHCOST,SUM_TOTALCOST "
			+ " FROM V_APU_REPAIRCOST WHERE ASN = ? ORDER BY NAME";
		HashVO[] repairVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, repairCostSql, asn);
		
		List<Map<String,Object>> repairCostList = new ArrayList<Map<String,Object>>();
		repairCostList.addAll(HashVoUtil.hashVosToMapList(repairVos));
		resMap.put("repairCostList", repairCostList);
		
		String contractCostSql = "SELECT NAME/100 NAME" +
			",(SELECT C.HOURCOST*100 FROM O_APU_HOURMAINCOST C,B_APU A " +
				" WHERE C.APUMODELID=A.APUMODELID AND A.APUSN='"+asn+"') CONTRACT_COST"+
			" ,STARTNUM,ENDNUM FROM O_NUMRANGE R " +
			" WHERE  R.TYPE=1 AND R.STARTNUM >= ? AND R.STARTNUM < ? ORDER BY NAME";
		HashVO[] contractVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, contractCostSql,startHour, endHour);
		
		List<Map<String,Object>> contractCostList = new ArrayList<Map<String,Object>>();
		contractCostList.addAll(HashVoUtil.hashVosToMapList(contractVos));
		resMap.put("contractCostList", contractCostList);
		
		
		return resMap;
	}
	
	/**
	 * APU大修方案成本分析
	 * @param begin_date
	 * @param end_date
	 * @param acnum
	 * @param asn
	 * @param isIgnoreBegin
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOverhaulCostData( String begin_date,String end_date
				,String acnum,String asn,boolean isIgnoreBegin) throws Exception{
		
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		
		if(asn == null || asn.length() ==0){
			asn = getAsnByAcnum(acnum);
		}
		
		Date beginTime = DateUtil.StringToDate(begin_date, "yyyy-MM-dd HH:mm:ss");
		Date endTime = DateUtil.StringToDate(end_date, "yyyy-MM-dd HH:mm:ss");
		
		int startHour = getStartHour(asn, beginTime);//开始小时 
		int endHour = getEndHour(asn, endTime);//开始小时
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT T.ASN,T.RUNMIN/60 RUNHOUR,T.RUNMIN,T.REPAIRCOST,T.UP_DATE,T.DOWN_DATE,");
		sb.append(" T.SERVICECOST*100 SERVICECOST,N.NAME/100 NAME,N.ENDNUM,");
		sb.append(" ROUND((T.REPAIRCOST/(N.ENDNUM-T.TIME_ONINSTALL))*60*100,2) COST_PERHOUR,");
		sb.append(" T.TIME_ONINSTALL,T.CYCLE_ONINSTALL,T.TIME_ONREMOVE,T.CYCLE_ONREMOVE,T.TIME_ONREPAIRED,T.CYCLE_ONREPAIRED");
		sb.append(" FROM V_APU_OVERHAULLOG_ALL T,O_NUMRANGE N");
		sb.append(" WHERE  N.TYPE=1 AND N.STARTNUM >= ? AND N.STARTNUM <= ? ");
		sb.append(" AND N.STARTNUM < T.TIME_ONREMOVE AND N.ENDNUM>T.TIME_ONINSTALL ");//数值范围在一次大修范围内
//		if(isIgnoreBegin){
//			sb.append(" AND N.ENDNUM >= 60000 ");//忽略前100个小时的平均大修成本数据,值太大，数据不好看
//		}
		sb.append(" AND T.ASN = '"+ asn +"'"); 
		sb.append(" ORDER BY T.UP_DATE,N.ENDNUM ");
		sb.append("");
		
		HashVO[] haulVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS,sb.toString(),startHour,endHour);
		List<Map<String,Object>> hualCostList = new ArrayList<Map<String,Object>>();
		hualCostList.addAll(HashVoUtil.hashVosToMapList(haulVos));
		resMap.put("overhaulCostList", hualCostList);
		
		String fuelSql = "SELECT L.ASN,R.NAME/100 NAME, ROUND(SUM(L.FUSED*L.PRICE_KG),2) FUELOIL_COST,SUM(L.FUSED) SUM_USED "
			+ " FROM V_APUOIL_HIS L,O_NUMRANGE R "
			+ " WHERE  R.TYPE=1 AND L.ASN = ? AND L.AHRS > R.STARTNUM AND L.AHRS <= R.ENDNUM "
			+ " AND L.RPTDATE >= ? AND L.RPTDATE <= ? "
			+ " GROUP BY L.ASN,R.NAME ORDER BY NAME";
		HashVO[] fuelVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, fuelSql, asn, beginTime, endTime);
		
		List<Map<String,Object>> fuelCostList = new ArrayList<Map<String,Object>>();
		fuelCostList.addAll(HashVoUtil.hashVosToMapList(fuelVos));
		resMap.put("fuelCostList", fuelCostList);
		
		String repairCostSql = "SELECT ASN,ACNUM,NAME/100 NAME,SUM_REPAIRCOST,SUM_MHCOST,SUM_TOTALCOST "
			+ " FROM V_APU_REPAIRCOST WHERE ASN = ? ORDER BY NAME";
		HashVO[] repairVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, repairCostSql, asn);
		
		List<Map<String,Object>> repairCostList = new ArrayList<Map<String,Object>>();
		repairCostList.addAll(HashVoUtil.hashVosToMapList(repairVos));
		resMap.put("repairCostList", repairCostList);
		
		return resMap;
		
	}
	
	/**
	 * APU燃油成本分析数据
	 * @param begin_date
	 * @param end_date
	 * @param acnum
	 * @param asn
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getReportFuelAnalyzeData(String begin_date,String end_date ,String acnum,String asn) throws Exception{
	
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		
		if(asn == null || asn.length() ==0){
			asn = getAsnByAcnum(acnum);
		}
		Date beginTime = DateUtil.StringToDate(begin_date, "yyyy-MM-dd HH:mm:ss");
		Date endTime = DateUtil.StringToDate(end_date, "yyyy-MM-dd HH:mm:ss");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT L.FUSED, ROUND((FUSED / OPERATE_MINS * 60),2) USED_PERHOUR,");
		//每小时消耗5点均
		sb.append(" ROUND(AVG(FUSED / OPERATE_MINS * 60) OVER( ORDER BY RPTDATE RANGE BETWEEN 4 PRECEDING AND 0 FOLLOWING),2) USED_PERHOUR_AVG5,");
		sb.append(" (SELECT C.PRICE FROM V_FUELOIL_COST C WHERE C.BEGINTIME <=L.RPTDATE AND C.ENDTIME>=L.RPTDATE)*L.FUSED USED_DOLLAR,");
		sb.append(" L.ACNUM,L.ASN,L.AHRS/60 AHRS,L.ACYC,L.RPTDATE");
		sb.append(" FROM A_DFD_A36_LIST L WHERE 1=1 ");
		sb.append(" AND L.RPTDATE >= ? AND L.RPTDATE <= ?");
		sb.append(" AND L.ASN = ?");
		sb.append(" ORDER BY RPTDATE");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),beginTime, endTime, asn);
		
		List<Map<String,Object>> rptDataList = new ArrayList<Map<String,Object>>();
		rptDataList.addAll(HashVoUtil.hashVosToMapList(vos));
		resMap.put("rptDataList", rptDataList);
		
		return resMap;
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
	
	/**
	 * 根据APU运行日志估算对应时间的开始小时和结束小时
	 * @param asn
	 * @param beginTime
	 * @return
	 * @throws Exception
	 */
	private int getStartHour(String asn,Date beginTime) throws Exception{
		int startHour = 0;//结束小时 
		//1, 根据指定的时间范围,查询出需要计算的起始和结束小时
		String sqls = "SELECT * FROM ( "
			+ " SELECT T.ID,ASN,ACNUM,MSG_NO,DATATIME,TOTALTIME,TOTALCYCLE,ROW_NUMBER() OVER(ORDER BY DATATIME) RNUM"
			+ " FROM L_APU_RUNLOG T WHERE ASN = ? AND T.DATATIME >= ? "
			+ " ) WHERE RNUM = 1";
		HashVO[] bvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sqls, asn, beginTime);
		if(bvos.length > 0){
			startHour = bvos[0].getIntegerValue("TOTALTIME");
		}
		
		return startHour;
	}
	
	/**
	 * 根据APU运行日志估算对应时间的开始小时和结束小时
	 * @param asn
	 * @param beginTime
	 * @return
	 * @throws Exception
	 */
	private int getEndHour(String asn,Date endTime) throws Exception{
		int endHour = 600000;//开始小时
		//结束时的小时
		String sqle = "SELECT * FROM ( "
			+ " SELECT T.ID,ASN,ACNUM,MSG_NO,DATATIME,TOTALTIME,TOTALCYCLE,ROW_NUMBER() OVER(ORDER BY DATATIME DESC) RNUM"
			+ " FROM L_APU_RUNLOG T WHERE ASN = ? AND T.DATATIME <= ? "
			+ " ) WHERE RNUM = 1";
		HashVO[] evos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sqle, asn, endTime );
		if(evos.length > 0){
			endHour = evos[0].getIntegerValue("TOTALTIME");
		}
		
		return endHour;
	}

}
