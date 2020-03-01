package com.apms.bs.aircond;

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
 * 滑油相关服务入口
 * @author jerry
 * @date May 8, 2013
 */
public class AircondService {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static AircondService aircondService = null;
	
	private CommDMO dmo = new CommDMO(); 
	
	public static AircondService getInstance(){
		if(aircondService != null)
			return aircondService;
		
		aircondService = new AircondService();
		
		return aircondService;
	}
	
	/**
	 * 把hashvo数组转换为MAP对象数组
	 * @param vos
	 * @return
	 */
	public List<Map<String,Object>> hashVoToMap(HashVO[] vos){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>(vos.length);
		for(int i=0;i<vos.length;i++){
			Map<String,Object> objMap = new HashMap<String, Object>();
			HashVO vo = vos[i];
			String[] keys = vo.getKeys();
			for(int j=0;j<keys.length;j++){
				String key = keys[j];
				objMap.put(key.toUpperCase(), vo.getObjectValue(key));
			}
			
			mapList.add(objMap);
		}
		
		return mapList;
	}

	/**
	 * APU计算后报文数据查询
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAirCondChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询空调报文(21)计算数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		//查询 A21数据对象
		List<Map<String,Object>> allA21Compu_pf = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_pf = getA21ComputedVos(begin_date, end_date, acNum,"PF_DIV");
		allA21Compu_pf.addAll(HashVoUtil.hashVosToMapList(vosA21_pf));
		resMap.put("allA21Compu_pf", allA21Compu_pf);
		
		List<Map<String,Object>> allA21Compu_cot = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_cot = getA21ComputedVos(begin_date, end_date, acNum,"COT_DIV");
		allA21Compu_cot.addAll(HashVoUtil.hashVosToMapList(vosA21_cot));
		resMap.put("allA21Compu_cot", allA21Compu_cot);
		
		List<Map<String,Object>> allA21Compu_ri = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA21_ri = getA21ComputedVos(begin_date, end_date, acNum,"RI_DIV");
	    allA21Compu_ri.addAll(HashVoUtil.hashVosToMapList(vosA21_ri));
	    resMap.put("allA21Compu_ri", allA21Compu_ri);
		
	    List<Map<String,Object>> allA21Compu_pbv = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA21_pbv = getA21ComputedVos(begin_date, end_date, acNum,"PBV_DIV");
	    allA21Compu_pbv.addAll(HashVoUtil.hashVosToMapList(vosA21_pbv));
	    resMap.put("allA21Compu_pbv", allA21Compu_pbv);
		
		List<Map<String,Object>> allA21Compu_p3 = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA21_p3 = getA21ComputedVos(begin_date, end_date, acNum,"P3_DIV");
	    allA21Compu_p3.addAll(HashVoUtil.hashVosToMapList(vosA21_p3));
	    resMap.put("allA21Compu_p3", allA21Compu_p3);
		
		List<Map<String,Object>> allA21Compu_tw = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA21_tw = getA21ComputedVos(begin_date, end_date, acNum,"TW_DIV");
	    allA21Compu_tw.addAll(HashVoUtil.hashVosToMapList(vosA21_tw));
	    resMap.put("allA21Compu_tw", allA21Compu_tw);
		
	    List<Map<String,Object>> allA21Compu_tp = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_tp = getA21ComputedVos(begin_date, end_date, acNum,"TP_DIV");
		allA21Compu_tp.addAll(HashVoUtil.hashVosToMapList(vosA21_tp));
		resMap.put("allA21Compu_tp", allA21Compu_tp);
		
		List<Map<String,Object>> allA21Compu_tpo = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_tpo = getA21ComputedVos(begin_date, end_date, acNum,"TPO_DIV");
		allA21Compu_tpo.addAll(HashVoUtil.hashVosToMapList(vosA21_tpo));
		resMap.put("allA21Compu_tpo", allA21Compu_tpo);
		
		List<Map<String,Object>> allA21Compu_pd = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_pd = getA21ComputedVos(begin_date, end_date, acNum,"PD_DIV");
		allA21Compu_pd.addAll(HashVoUtil.hashVosToMapList(vosA21_pd));
		resMap.put("allA21Compu_pd", allA21Compu_pd);
		
		List<Map<String,Object>> allA21Compu_d1 = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_d1 = getA21ComputedVos(begin_date, end_date, acNum,"D1_DIV");
		allA21Compu_d1.addAll(HashVoUtil.hashVosToMapList(vosA21_d1));
		resMap.put("allA21Compu_d1", allA21Compu_d1);
		
		List<Map<String,Object>> allA21Compu_sc2fwdt = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_sc2fwdt = getA21ComputedVos(begin_date, end_date, acNum,"SC2FWDT_DIV");
		allA21Compu_sc2fwdt.addAll(HashVoUtil.hashVosToMapList(vosA21_sc2fwdt));
		resMap.put("allA21Compu_sc2fwdt", allA21Compu_sc2fwdt);
		
		List<Map<String,Object>> allA21Compu_sc3aftt = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_sc3aftt = getA21ComputedVos(begin_date, end_date, acNum,"SC3AFTT_DIV");
		allA21Compu_sc3aftt.addAll(HashVoUtil.hashVosToMapList(vosA21_sc3aftt));
		resMap.put("allA21Compu_sc3aftt", allA21Compu_sc3aftt);
		
		List<Map<String,Object>> allA21Compu_ovp = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_ovp = getA21ComputedVos(begin_date, end_date, acNum,"OVP_X1");
		allA21Compu_ovp.addAll(HashVoUtil.hashVosToMapList(vosA21_ovp));
		resMap.put("allA21Compu_ovp", allA21Compu_ovp);
		
		List<Map<String,Object>> allA21Compu_sc1ckt = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_sc1ckt = getA21ComputedVos(begin_date, end_date, acNum,"SC1CKT_DIV");
		allA21Compu_sc1ckt.addAll(HashVoUtil.hashVosToMapList(vosA21_sc1ckt));
		resMap.put("allA21Compu_sc1ckt", allA21Compu_sc1ckt);
		
		List<Map<String,Object>> allA21Compu_prv = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_prv = getA21ComputedVos(begin_date, end_date, acNum,"PIN_PRV_DIV");
		allA21Compu_prv.addAll(HashVoUtil.hashVosToMapList(vosA21_prv));
		resMap.put("allA21Compu_prv", allA21Compu_prv);
		
		List<Map<String,Object>> allA21Compu_tw1 = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_tw1 = getA21ComputedVos(begin_date, end_date, acNum,"PDMT_TW1_DIV");
		allA21Compu_tw1.addAll(HashVoUtil.hashVosToMapList(vosA21_tw1));
		resMap.put("allA21Compu_tw1", allA21Compu_tw1);
		
		List<Map<String,Object>> allA21Compu_tw2 = new ArrayList<Map<String,Object>>();
		HashVO[] vosA21_tw2 = getA21ComputedVos(begin_date, end_date, acNum,"PDMT_TW2_DIV");
		allA21Compu_tw2.addAll(HashVoUtil.hashVosToMapList(vosA21_tw2));
		resMap.put("allA21Compu_tw2", allA21Compu_tw2);

		List<Map<String,Object>> allA21List = new ArrayList<Map<String,Object>>();
		//查询 A21数据对象
		HashVO[] voslA21 = getA21ListVos(begin_date, end_date, acNum);
		allA21List.addAll(HashVoUtil.hashVosToMapList(voslA21));

		resMap.put("allA21List", allA21List);
		
		logger.debug("结束查询滑油报文(21)计算数据");
		
		return resMap;
		
	}
	
	public Map<String, Object> getAirbleedChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询空调报文(21)计算数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		
		List<Map<String,Object>> allA21Compu = new ArrayList<Map<String,Object>>();
		//查询 A21数据对象
		HashVO[] vosA21= getA21AirbleedComputedVos(begin_date, end_date, acNum);
		allA21Compu.addAll(HashVoUtil.hashVosToMapList(vosA21));

		resMap.put("allA21Compu", allA21Compu);
		

		List<Map<String,Object>> allA21List = new ArrayList<Map<String,Object>>();
		//查询 A21数据对象
		HashVO[] voslA21 = getAirbleedA21ListVos(begin_date, end_date, acNum);
		allA21List.addAll(HashVoUtil.hashVosToMapList(voslA21));

		resMap.put("allA21List", allA21List);
		
		logger.debug("结束查询滑油报文(21)计算数据");
		
		return resMap;
		
	}

	public Map<String, Object> getGroundChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询空调报文(24)计算数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		
		//查询 A24数据对象
	    List<Map<String,Object>> allA24Compu_pf = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_pf = getA24ComputedVos(begin_date, end_date, acNum,"PF_DIV");
	    allA24Compu_pf.addAll(HashVoUtil.hashVosToMapList(vosA24_pf));
	    resMap.put("allA24Compu_pf", allA24Compu_pf);
	    
	    List<Map<String,Object>> allA24Compu_cot = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_cot = getA24ComputedVos(begin_date, end_date, acNum,"COT_DIV");
	    allA24Compu_cot.addAll(HashVoUtil.hashVosToMapList(vosA24_cot));
	    resMap.put("allA24Compu_cot", allA24Compu_cot);
	    
	    List<Map<String,Object>> allA24Compu_ri = new ArrayList<Map<String,Object>>();
        HashVO[] vosA24_ri = getA24ComputedVos(begin_date, end_date, acNum,"RI_DIV");
        allA24Compu_ri.addAll(HashVoUtil.hashVosToMapList(vosA24_ri));
        resMap.put("allA24Compu_ri", allA24Compu_ri);
	    
        List<Map<String,Object>> allA24Compu_pbv = new ArrayList<Map<String,Object>>();
        HashVO[] vosA24_pbv = getA24ComputedVos(begin_date, end_date, acNum,"PBV_DIV");
        allA24Compu_pbv.addAll(HashVoUtil.hashVosToMapList(vosA24_pbv));
        resMap.put("allA24Compu_pbv", allA24Compu_pbv);
	    
	    
	    List<Map<String,Object>> allA24Compu_tw = new ArrayList<Map<String,Object>>();
        HashVO[] vosA24_tw = getA24ComputedVos(begin_date, end_date, acNum,"TW_DIV");
        allA24Compu_tw.addAll(HashVoUtil.hashVosToMapList(vosA24_tw));
        resMap.put("allA24Compu_tw", allA24Compu_tw);
	    
        List<Map<String,Object>> allA24Compu_tp = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_tp = getA24ComputedVos(begin_date, end_date, acNum,"TP_DIV");
	    allA24Compu_tp.addAll(HashVoUtil.hashVosToMapList(vosA24_tp));
	    resMap.put("allA24Compu_tp", allA24Compu_tp);
	    
	    
	    List<Map<String,Object>> allA24Compu_pd = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_pd = getA24ComputedVos(begin_date, end_date, acNum,"PD_DIV");
	    allA24Compu_pd.addAll(HashVoUtil.hashVosToMapList(vosA24_pd));
	    resMap.put("allA24Compu_pd", allA24Compu_pd);
	    
	    List<Map<String,Object>> allA24Compu_d1 = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_d1 = getA24ComputedVos(begin_date, end_date, acNum,"D1_DIV");
	    allA24Compu_d1.addAll(HashVoUtil.hashVosToMapList(vosA24_d1));
	    resMap.put("allA24Compu_d1", allA24Compu_d1);
	    
	    List<Map<String,Object>> allA24Compu_sc2fwdt = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_sc2fwdt = getA24ComputedVos(begin_date, end_date, acNum,"SC2FWDT_DIV");
	    allA24Compu_sc2fwdt.addAll(HashVoUtil.hashVosToMapList(vosA24_sc2fwdt));
	    resMap.put("allA24Compu_sc2fwdt", allA24Compu_sc2fwdt);
	    
	    List<Map<String,Object>> allA24Compu_sc3aftt = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_sc3aftt = getA24ComputedVos(begin_date, end_date, acNum,"SC3AFTT_DIV");
	    allA24Compu_sc3aftt.addAll(HashVoUtil.hashVosToMapList(vosA24_sc3aftt));
	    resMap.put("allA24Compu_sc3aftt", allA24Compu_sc3aftt);
	    
	    List<Map<String,Object>> allA24Compu_ovp = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_ovp = getA24ComputedVos(begin_date, end_date, acNum,"OVP_X1");
	    allA24Compu_ovp.addAll(HashVoUtil.hashVosToMapList(vosA24_ovp));
	    resMap.put("allA24Compu_ovp", allA24Compu_ovp);
	    
	    List<Map<String,Object>> allA24Compu_sc1ckt = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_sc1ckt = getA24ComputedVos(begin_date, end_date, acNum,"SC1CKT_DIV");
	    allA24Compu_sc1ckt.addAll(HashVoUtil.hashVosToMapList(vosA24_sc1ckt));
	    resMap.put("allA24Compu_sc1ckt", allA24Compu_sc1ckt);
	    
	    
	    List<Map<String,Object>> allA24Compu_tw1 = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_tw1 = getA24ComputedVos(begin_date, end_date, acNum,"PDMT_TW1_DIV");
	    allA24Compu_tw1.addAll(HashVoUtil.hashVosToMapList(vosA24_tw1));
	    resMap.put("allA24Compu_tw1", allA24Compu_tw1);
	    
	    List<Map<String,Object>> allA24Compu_tw2 = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA24_tw2 = getA24ComputedVos(begin_date, end_date, acNum,"PDMT_TW2_DIV");
	    allA24Compu_tw2.addAll(HashVoUtil.hashVosToMapList(vosA24_tw2));
	    resMap.put("allA24Compu_tw2", allA24Compu_tw2);

		List<Map<String,Object>> allA24List = new ArrayList<Map<String,Object>>();
		HashVO[] voslA24 = getA24ListVos(begin_date, end_date, acNum);
		allA24List.addAll(HashVoUtil.hashVosToMapList(voslA24));

		resMap.put("allA24List", allA24List);
		
		logger.debug("结束查询滑油报文(24)计算数据");
		
		return resMap;
		
	}
	
	/**
	 * 根据时间和机号 查询对应的滑油状态报文计算数据
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	private HashVO[] getAirbleedA21ListVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,RPTDATE UTCDATE");
		sb.append(",T.N1_E1,T.N2_E1,T.N1_N1,T.N2_N1,T.P3_S1,T.T3_S1,T.TPO_S1");
		sb.append(",T.P3_T1,T.T3_T1,T.TPO_T1,T.PD_T1,T.PD_S1");
		sb.append(",T.PIN_PRV_M1,T.PIN_PRV_M2");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PRV1_Z1') PRV1_Z1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PRV2_Z1') PRV2_Z1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PD_S1') PD_S1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PD_T1') PD_T1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'TPO_S1') TPO_S1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'TPO_T1') TPO_T1_ROLL5");
		
	    sb.append(" FROM A_DFD_A21_LIST T,A_DFD_HEAD H");
	    sb.append(" WHERE RPTDATE>=? AND RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");

	    sb.append(" AND T.MSG_NO=H.MSG_NO AND H.PH='06'");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	/**
	 * 根据时间和机号 查询对应的滑油状态报文计算数据
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	private HashVO[] getA21ListVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,RPTDATE UTCDATE");
		sb.append(",T.ESN_1,T.PF_E1,T.COT_E1,T.RI_E1,T.RI_E1,T.PBV_E1");
		sb.append(",T.TW_S1,T.TP_S1,T.PD_S1");
		sb.append(",T.ESN_2,T.PF_N1,T.COT_N1,T.RI_N1,T.RI_N1,T.PBV_N1");
		sb.append(",T.TW_T1,T.TP_T1,T.PD_T1");
		sb.append(",T.PIN_HPV_M1,T.PIN_HPV_M2,T.PIN_PRV_M1,T.PIN_PRV_M2");
		sb.append(",T.PDMT_L_D1,T.PDMT_R_D1");//D1 
		sb.append(",T.TAT_V1,T.SAT_V1,T.ZCB_V1");//客舱高度，总温，静温
		sb.append(",T.MIXF_G1,T.MIXCAB_G1");//混合总管(驾驶舱、客舱)温度
		sb.append(",T.SC1_V1,T.CKT_F1");//驾驶舱预选温度， 区域温度
		sb.append(",T.SC2_V1,T.FWDT_F1");
		sb.append(",T.SC3_V1,T.AFTT_F1,T.OVP_X1");
		sb.append(",T.CKDUCT_G1,T.FWDUCT_G1,T.AFTDUCT_G1");
		sb.append(",(SELECT COUNT(1) FROM ALARM_MESSAGE A WHERE A.RPTNO='A21' AND A.DATAVIEWPK_VALUE=T.MSG_NO) ALARMNUM");
		
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PF_E1') PF_E1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PF_N1') PF_N1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'COT_E1') COT_E1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'COT_N1') COT_N1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'RI_E1') RI_E1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'RI_N1') RI_N1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PBV_E1') PBV_E1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PBV_N1') PBV_N1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'TW_S1') TW_S1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'TW_T1') TW_T1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'TP_S1') TP_S1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'TP_T1') TP_T1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PDMT_L_D1') PDMT_L_D1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PDMT_R_D1') PDMT_R_D1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PRV1_Z1') PRV1_Z1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PRV2_Z1') PRV2_Z1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PD_S1') PD_S1_ROLL5");
		sb.append(",(SELECT R1.F_VALUE_ROLL_N FROM A_DFD_FIELD_NROLL R1 WHERE R1.MSG_NO=T.MSG_NO AND R1.F_NAME = 'PD_T1') PD_T1_ROLL5");
		
	    sb.append(" FROM A_DFD_A21_LIST T,A_DFD_HEAD H");
	    sb.append(" WHERE RPTDATE>=? AND RPTDATE<=?");
	    sb.append(" AND T.MSG_NO=H.MSG_NO AND H.PH='06' AND T.ACNUM='"+acNum+"'");

	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	private HashVO[] getA21ComputedVos(String begin_date,String end_date,String acNum,String param) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,RPTDATE UTCDATE");
		sb.append(",T.ESN_1,T.DIV_PF_E1,T.DIV_COT_E1,T.DIV_RI_E1,T.DIV_PBV_E1");
		sb.append(",T.DIV_TW_S1,T.DIV_TP_S1,T.DIV_PD_S1");
		sb.append(",T.ESN_2,T.DIV_PF_N1,T.DIV_COT_N1,T.DIV_RI_N1,T.DIV_PBV_N1");
		sb.append(",T.DIV_TW_T1,T.DIV_TP_T1,T.DIV_PD_T1");
		sb.append(",T.DIV_PIN_PRV_M1,T.DIV_PIN_PRV_M2");
		sb.append(",T.DIV_PDMT_L_D1,T.DIV_PDMT_R_D1");//D1差值
		sb.append(",T.DIV_SC1_V1,T.DIV_CKT_F1");
		sb.append(",T.DIV_SC2_V1,T.DIV_FWDT_F1");
		sb.append(",T.DIV_SC3_V1,T.DIV_AFTT_F1");
		sb.append(",T.DIV_MIXF_G1,T.DIV_MIXCAB_G1");
		sb.append(",T.SUB_SC1CKT,T.SUB_SC2FWDT,T.SUB_SC3AFTT");
		sb.append(",T.DIV_PDMT_TW1,T.DIV_PDMT_TW2");
		sb.append(",T.SUB_PDMT_TW1,T.SUB_PDMT_TW2");
		
		sb.append(",T1.V_POINTTYPE,T1.V_OUT,T1.F_VALUE_ROLL5,T1.V_K,T1.V_STD");
		sb.append(",(select count(1) from alarm_message a where a.DATAVIEWPK_VALUE=T.MSG_NO) ALARMNUM");
	    sb.append(" FROM A_DFD_A21_COMPUTE T,A_DFD_HEAD H");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");
	    
	    sb.append(" WHERE RPTDATE>=? AND RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"' AND T.MSG_NO=H.MSG_NO AND H.PH='06'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='"+param+"'");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	private HashVO[] getA21AirbleedComputedVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,RPTDATE UTCDATE");
		sb.append(",T.ESN_1,T.DIV_N1_E1,T.DIV_N2_E1,T.DIV_PF_E1,T.DIV_COT_E1,T.DIV_RI_E1,T.DIV_RO_E1,T.DIV_PBV_E1");
		sb.append(",T.DIV_P3_S1,T.DIV_T3_S1,T.DIV_TW_S1,T.DIV_TP_S1,T.DIV_TPO_S1,T.DIV_PD_S1");
		sb.append(",T.ESN_2,T.DIV_N1_N1,T.DIV_N2_N1,T.DIV_PF_N1,T.DIV_COT_N1,T.DIV_RI_N1,T.DIV_RO_N1,T.DIV_PBV_N1");
		sb.append(",T.DIV_P3_T1,T.DIV_T3_T1,T.DIV_TW_T1,T.DIV_TP_T1,T.DIV_TPO_T1,T.DIV_PD_T1");
		sb.append(",T.DIV_PIN_PRV_M1,T.DIV_PIN_PRV_M2");
		sb.append(",T.DIV_PDMT_L_D1,T.DIV_PDMT_R_D1");//D1差值
		sb.append(",T.DIV_SC1_V1,T.DIV_CKT_F1");
		sb.append(",T.DIV_SC2_V1,T.DIV_FWDT_F1");
		sb.append(",T.DIV_SC3_V1,T.DIV_AFTT_F1");
		sb.append(",T.DIV_MIXF_G1,T.DIV_MIXCAB_G1");
		sb.append(",T.SUB_SC1CKT,T.SUB_SC2FWDT,T.SUB_SC3AFTT");
		sb.append(",T.DIV_PDMT_TW1,T.DIV_PDMT_TW2");
		
		sb.append(",T1.V_POINTTYPE N1_POINTTYPE,T1.F_VALUE_ROLL5 N1_ROLL5,(select count(1) from alarm_message a where a.DATAVIEWPK_VALUE=T1.MSG_NO) N1_ALARMNUM");
		sb.append(",T2.V_POINTTYPE N2_POINTTYPE,T2.F_VALUE_ROLL5 N2_ROLL5,(select count(1) from alarm_message a where a.DATAVIEWPK_VALUE=T2.MSG_NO) N2_ALARMNUM");
		sb.append(",T3.V_POINTTYPE P3_POINTTYPE,T3.F_VALUE_ROLL5 P3_ROLL5,(select count(1) from alarm_message a where a.DATAVIEWPK_VALUE=T3.MSG_NO) P3_ALARMNUM");
		sb.append(",T4.V_POINTTYPE T3_POINTTYPE,T4.F_VALUE_ROLL5 T3_ROLL5,(select count(1) from alarm_message a where a.DATAVIEWPK_VALUE=T4.MSG_NO) T3_ALARMNUM");
		sb.append(",T5.V_POINTTYPE PD_POINTTYPE,T5.F_VALUE_ROLL5 PD_ROLL5,(select count(1) from alarm_message a where a.DATAVIEWPK_VALUE=T5.MSG_NO) PD_ALARMNUM");
		sb.append(",T6.V_POINTTYPE TPO_POINTTYPE,T6.F_VALUE_ROLL5 TPO_ROLL5,(select count(1) from alarm_message a where a.DATAVIEWPK_VALUE=T6.MSG_NO) TPO_ALARMNUM");
		sb.append(",T7.V_POINTTYPE PIN_PRV_POINTTYPE,T7.F_VALUE_ROLL5 PIN_PRV_ROLL5,(select count(1) from alarm_message a where a.DATAVIEWPK_VALUE=T7.MSG_NO) PIN_PRV_ALARMNUM");
		
	    sb.append(" FROM A_DFD_A21_COMPUTE T,A_DFD_HEAD H");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T2");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T3");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T4");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T5");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T6");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T7");
	    
	    
	    
	    sb.append(" WHERE RPTDATE>=? AND RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"' AND T.MSG_NO=H.MSG_NO AND H.PH='06'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='N1_DIV'");//22
	    sb.append(" AND T2.MSG_NO =T.MSG_NO AND T2.F_NAME='N2_DIV'");//22
	    sb.append(" AND T3.MSG_NO =T.MSG_NO AND T3.F_NAME='P3_DIV'");//22
	    sb.append(" AND T4.MSG_NO =T.MSG_NO AND T4.F_NAME='T3_DIV'");//22
	    sb.append(" AND T5.MSG_NO =T.MSG_NO AND T5.F_NAME='PD_DIV'");//22
	    sb.append(" AND T6.MSG_NO =T.MSG_NO AND T6.F_NAME='TPO_DIV'");//22
	    sb.append(" AND T7.MSG_NO =T.MSG_NO AND T7.F_NAME='PIN_PRV_DIV'");//22
	    
	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	
	private HashVO[] getA24ListVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,RPTDATE UTCDATE");
		sb.append(",T.ESN_1,T.PF_E1,T.COT_E1,T.RI_E1,T.RI_E1,T.RO_E1,T.PBV_E1");
		sb.append(",T.TW_S1,T.TP_S1,T.TPO_S1,T.PD_S1");
		sb.append(",T.ESN_2,T.PF_N1,T.COT_N1,T.RI_N1,T.RI_N1,T.RO_N1,T.PBV_N1");
		sb.append(",T.TW_T1,T.TP_T1,T.TPO_T1,T.PD_T1");
		sb.append(",T.PDMT_L_D1,T.PDMT_R_D1");//D1 
		sb.append(",T.TAT_V1,T.SAT_V1,T.ZCB_V1");//客舱高度，总温，静温
		sb.append(",T.MIXF_G1,T.MIXCAB_G1");//混合总管(驾驶舱、客舱)温度
		sb.append(",T.SC1_V1,T.CKT_F1");//驾驶舱预选温度， 区域温度
		sb.append(",T.SC2_V1,T.FWDT_F1");
		sb.append(",T.SC3_V1,T.AFTT_F1,T.OVP_X1");
		sb.append(",T.CKDUCT_G1,T.FWDUCT_G1,T.AFTDUCT_G1");
		sb.append(",(SELECT COUNT(1) FROM ALARM_MESSAGE A WHERE A.RPTNO='A24' AND A.DATAVIEWPK_VALUE=T.MSG_NO) ALARMNUM");
		sb.append(",R1.F_VALUE_ROLL_N PF_E1_ROLL5");
		sb.append(",R2.F_VALUE_ROLL_N PF_N1_ROLL5");
		sb.append(",R3.F_VALUE_ROLL_N COT_E1_ROLL5");
		sb.append(",R4.F_VALUE_ROLL_N COT_N1_ROLL5");
		sb.append(",R5.F_VALUE_ROLL_N RI_E1_ROLL5");
		sb.append(",R6.F_VALUE_ROLL_N RI_N1_ROLL5");
		sb.append(",R7.F_VALUE_ROLL_N PBV_E1_ROLL5");
		sb.append(",R8.F_VALUE_ROLL_N PBV_N1_ROLL5");
		sb.append(",R9.F_VALUE_ROLL_N TW_S1_ROLL5");
		sb.append(",R10.F_VALUE_ROLL_N TW_T1_ROLL5");
		sb.append(",R11.F_VALUE_ROLL_N TP_S1_ROLL5");
		sb.append(",R12.F_VALUE_ROLL_N TP_T1_ROLL5");
		sb.append(",R13.F_VALUE_ROLL_N PDMT_L_D1_ROLL5");
		sb.append(",R14.F_VALUE_ROLL_N PDMT_R_D1_ROLL5");
		sb.append(",R15.F_VALUE_ROLL_N PD_S1_ROLL5");
		sb.append(",R16.F_VALUE_ROLL_N PD_T1_ROLL5");
		
		sb.append(" FROM A_DFD_A24_LIST T");
	    sb.append(" ,A_DFD_FIELD_NROLL R1");
	    sb.append(" ,A_DFD_FIELD_NROLL R2");
	    sb.append(" ,A_DFD_FIELD_NROLL R3");
	    sb.append(" ,A_DFD_FIELD_NROLL R4");
	    sb.append(" ,A_DFD_FIELD_NROLL R5");
	    sb.append(" ,A_DFD_FIELD_NROLL R6");
	    sb.append(" ,A_DFD_FIELD_NROLL R7");
	    sb.append(" ,A_DFD_FIELD_NROLL R8");
	    sb.append(" ,A_DFD_FIELD_NROLL R9");
	    sb.append(" ,A_DFD_FIELD_NROLL R10");
	    sb.append(" ,A_DFD_FIELD_NROLL R11");
	    sb.append(" ,A_DFD_FIELD_NROLL R12");
	    sb.append(" ,A_DFD_FIELD_NROLL R13");
	    sb.append(" ,A_DFD_FIELD_NROLL R14");
	    sb.append(" ,A_DFD_FIELD_NROLL R15");
	    sb.append(" ,A_DFD_FIELD_NROLL R16");
	    
	    sb.append(" WHERE RPTDATE>=? AND RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");

	    sb.append(" AND R1.MSG_NO =T.MSG_NO AND R1.F_NAME='PF_E1'");
	    sb.append(" AND R2.MSG_NO =T.MSG_NO AND R2.F_NAME='PF_N1'");
	    sb.append(" AND R3.MSG_NO =T.MSG_NO AND R3.F_NAME='COT_E1'");
	    sb.append(" AND R4.MSG_NO =T.MSG_NO AND R4.F_NAME='COT_N1'");
	    sb.append(" AND R5.MSG_NO =T.MSG_NO AND R5.F_NAME='RI_E1'");
	    sb.append(" AND R6.MSG_NO =T.MSG_NO AND R6.F_NAME='RI_N1'");
	    sb.append(" AND R7.MSG_NO =T.MSG_NO AND R7.F_NAME='PBV_E1'");
	    sb.append(" AND R8.MSG_NO =T.MSG_NO AND R8.F_NAME='PBV_N1'");
	    sb.append(" AND R9.MSG_NO =T.MSG_NO AND R9.F_NAME='TW_S1'");
	    sb.append(" AND R10.MSG_NO =T.MSG_NO AND R10.F_NAME='TW_T1'");
	    sb.append(" AND R11.MSG_NO =T.MSG_NO AND R11.F_NAME='TP_S1'");
	    sb.append(" AND R12.MSG_NO =T.MSG_NO AND R12.F_NAME='TP_T1'");
	    sb.append(" AND R13.MSG_NO =T.MSG_NO AND R13.F_NAME='PDMT_L_D1'");
	    sb.append(" AND R14.MSG_NO =T.MSG_NO AND R14.F_NAME='PDMT_R_D1'");
	    sb.append(" AND R15.MSG_NO =T.MSG_NO AND R15.F_NAME='PD_S1'");
	    sb.append(" AND R16.MSG_NO =T.MSG_NO AND R16.F_NAME='PD_T1'");
	    
	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	private HashVO[] getA24ComputedVos(String begin_date,String end_date,String acNum,String param) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,RPTDATE UTCDATE");
		sb.append(",T.ESN_1,T.DIV_PF_E1,T.DIV_COT_E1,T.DIV_RI_E1,T.DIV_RO_E1,T.DIV_PBV_E1");
		sb.append(",T.DIV_TW_S1,T.DIV_TP_S1,T.DIV_TPO_S1,T.DIV_PD_S1");
		sb.append(",T.ESN_2,T.DIV_PF_N1,T.DIV_COT_N1,T.DIV_RI_N1,T.DIV_RO_N1,T.DIV_PBV_N1");
		sb.append(",T.DIV_TW_T1,T.DIV_TP_T1,T.DIV_TPO_T1,T.DIV_PD_T1");
		sb.append(",T.DIV_PDMT_L_D1,T.DIV_PDMT_R_D1");
		sb.append(",T.DIV_SC1_V1,T.DIV_SC2_V1,T.DIV_SC3_V1");
		sb.append(",T.SUB_SC1CKT,T.SUB_SC2FWDT,T.SUB_SC3AFTT");
		sb.append(",T.DIV_PDMT_TW1,T.DIV_PDMT_TW2");
		sb.append(",T.SUB_PDMT_TW1,T.SUB_PDMT_TW2");
		
		sb.append(",T1.V_POINTTYPE,T1.V_OUT,T1.F_VALUE_ROLL5,T1.V_K,T1.V_STD");
		sb.append(",(select count(1) from alarm_message a where a.DATAVIEWPK_VALUE=T.MSG_NO) ALARMNUM");
	    sb.append(" FROM A_DFD_A24_COMPUTE T");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");

	    sb.append(" WHERE RPTDATE>=? AND RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='"+param+"'");

	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	

}
