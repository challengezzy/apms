package com.apms.bs.wavhealth;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.common.HashVoUtil;
import com.apms.bs.util.DateUtil;

public class WavHealthService {

protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static WavHealthService wavhealthService = null;
	
	private CommDMO dmo = new CommDMO(); 
	
	public static WavHealthService getInstance(){
		if(wavhealthService != null)
			return wavhealthService;
		
		wavhealthService = new WavHealthService();
		
		return wavhealthService;
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
	public Map<String, Object> getWavHealthChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询空调报文(34)计算数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		
		List<Map<String,Object>> allA34List = new ArrayList<Map<String,Object>>();
		//查询 A34数据对象
		HashVO[] voslA34 = getA34ListVos(begin_date, end_date, acNum);
		allA34List.addAll(HashVoUtil.hashVosToMapList(voslA34));

		resMap.put("allA34List", allA34List);
		
		logger.debug("结束查询滑油报文(34)计算数据");
		
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
	private HashVO[] getA34ListVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,DATE_UTC UTCDATE,T.ESN_1,T.ESN_2");
		sb.append(",T.PD_S5,T.PD_S6");
		sb.append(",T.WAV1_TMR_S7,T.WAV1_TMR_COR_S7,T.WAV2_TMR_S7,T.WAV2_TMR_COR_S7");
		
		sb.append(",T1.V_POINTTYPE WAV1_TMR_POINTTYPE,9 REDLINEVALUE");
		sb.append(",T2.V_POINTTYPE WAV2_TMR_POINTTYPE");

	    sb.append(" FROM A_DFD_A34_LIST T");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T2");

	    sb.append(" WHERE DATE_UTC>=? AND DATE_UTC<=?");
	    sb.append(" AND T.WAV1_TMR_S7<100 AND T.WAV2_TMR_S7<100");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='WAV1_TMR_COR_S7'");
	    sb.append(" AND T2.MSG_NO =T.MSG_NO AND T2.F_NAME='WAV2_TMR_COR_S7'");



	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	
}
