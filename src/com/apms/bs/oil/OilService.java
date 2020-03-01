package com.apms.bs.oil;

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
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.oil.vo.A_DFD_A25_COMPUTED;
import com.apms.bs.util.DateUtil;

/**
 * 滑油相关服务入口
 * @author jerry
 * @date May 8, 2013
 */
public class OilService {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static OilService oilService = null;
	
	private CommDMO dmo = new CommDMO(); 
	
	public static OilService getInstance(){
		if(oilService != null)
			return oilService;
		
		oilService = new OilService();
		
		return oilService;
	}
	
	public Map<String, Object> getOilDayAddChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询滑油每日添加量计算数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		
		resMap.put("oilAddList", getOilAddList(begin_date, end_date, acNum));
		
		logger.debug("结束查询滑油添加数据");
		
		return resMap;
		
	}
	
	
	public List<Map<String, Object>> getOilAddList(String begin_date,String end_date,String acNum) throws Exception{
		String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		StringBuilder sb = new StringBuilder("SELECT ACNUM,L.ESN_1,L.ESN_2,TO_CHAR(L.DATE_UTC,'YYYY-MM-DD HH24:MI:SS') OILADD_DAYSTR,TO_CHAR(L.DATE_UTC,'YYYY-MM-DD') OILADD_DAY");
		sb.append(",SUM( DECODE(L.OILADD_ENG1,0,0,L.OIQAV_ENG1)) OIQAV1_DAY");
		sb.append(", SUM( DECODE(L.OILADD_ENG2,0,0,L.OIQAV_ENG2)) OIQAV2_DAY");
		sb.append(" FROM A_DFD_A27_LIST L");
		sb.append(" WHERE L.DATE_UTC >= ? AND L.DATE_UTC <= ? AND ACNUM=?");
		sb.append(" AND (L.OILADD_ENG1 =1 OR L.OILADD_ENG2 =1) "); //左发或右发 有滑油添加标记
		sb.append(" GROUP BY L.ACNUM,L.ESN_1,L.ESN_2,TO_CHAR(L.DATE_UTC,'YYYY-MM-DD'),TO_CHAR(L.DATE_UTC, 'YYYY-MM-DD HH24:MI:SS')");
		sb.append(" ORDER BY OILADD_DAY");
		sb.append("");
		sb.append("");
		
		//查询数据对象
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt,acNum);
		
		List<Map<String, Object>> oilAddList = HashVoUtil.hashVosToMapList(vos);
		
		return oilAddList;
	}
	
	
	
	/**
	 * APU计算后报文数据查询
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOilStatusChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询滑油报文(25)计算数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		
		List<Map<String,Object>> changList1 = new ArrayList<Map<String,Object>>();//左发
		List<Map<String,Object>> changList2 = new ArrayList<Map<String,Object>>();//右发
		List<Map<String,Object>> allList = new ArrayList<Map<String,Object>>();//所有点
		
		List<Map<String,Object>> allA27List1 = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> allA27List2 = new ArrayList<Map<String,Object>>();


		//查询数据对象
		HashVO[] vos = getComputedVos(begin_date, end_date, acNum);

		for (int i = 0; i < vos.length; i++) {
			HashVO vo = vos[i];
			Map<String,Object> ac = HashVoUtil.hashVoToMap(vo);
			int isAdd1 = new Integer(ac.get("DETA_OIQ1_FWD_S").toString());
			int isAdd2 = new Integer(ac.get("DETA_OIQ2_FWD_S").toString());
			//应该有左发、右发两个信息
			if(DfdVarConst.CHANGEPOINT_YES == isAdd1 || DfdVarConst.CHANGEPOINT_YES == isAdd2){
				//左发新滑油添加点
				if(DfdVarConst.CHANGEPOINT_YES == isAdd1){
					changList1.add(ac);
				}
				if(DfdVarConst.CHANGEPOINT_YES == isAdd2){
					changList2.add(ac);
				}
				
				ac.put("OIQ1_RATE_ROLL5",null);
				ac.put("OIQ2_RATE_ROLL5",null);
				allList.add(ac);
			}else{
				allList.add(ac);
			}
		}
		
		//查询 A27数据对象
		HashVO[] vos1A27 = getA27ComputedVos1(begin_date, end_date, acNum);
		HashVO[] vos2A27 = getA27ComputedVos2(begin_date, end_date, acNum);

		allA27List1.addAll(HashVoUtil.hashVosToMapList(vos1A27));
		allA27List2.addAll(HashVoUtil.hashVosToMapList(vos2A27));

		resMap.put("changList1", changList1);
		resMap.put("changList2", changList2);
		resMap.put("allList",allList);
		
		//获取滑油添加数据
		resMap.put("oilAddList", getOilAddList(begin_date, end_date, acNum));
		resMap.put("allA27List1", allA27List1);
		resMap.put("allA27List2", allA27List2);

		
		logger.debug("结束查询滑油报文(25)计算数据");
		
		return resMap;
		
	}
	
	/**
	 * HashVo数据封装成A_DFD_A23_COMPUTE对象
	 * @param vo
	 * @return
	 */
	private A_DFD_A25_COMPUTED getComputedPoint(HashVO vo){
		A_DFD_A25_COMPUTED ac = new A_DFD_A25_COMPUTED();
		
		ac.setMSG_NO(vo.getStringValue("MSG_NO"));
		ac.setUTCDATE(vo.getDateValue("UTCDATE"));
		ac.setACNUM(vo.getStringValue("ACNUM"));
		
		ac.setESN_1(vo.getStringValue("ESN_1"));
		ac.setESN_2(vo.getStringValue("ESN_2"));
		//设置左、右发滑油相关数据
		ac.setOIQ1_Z3(vo.getDoubleValue("OIQ1_Z3"));
		ac.setDETA_OIQ1_FWD_S(vo.getIntegerValue("DETA_OIQ1_FWD_S"));
		ac.setDETA_OIQ1_FWDRATE(vo.getDoubleValue("DETA_OIQ1_FWDRATE"));
		ac.setOIQ1_RATE_ROLL5(vo.getDoubleValue("OIQ1_RATE_ROLL5"));
		
		ac.setOIQ2_Z3(vo.getDoubleValue("OIQ2_Z3"));
		ac.setDETA_OIQ2_FWD_S(vo.getIntegerValue("DETA_OIQ2_FWD_S"));
		ac.setDETA_OIQ2_FWDRATE(vo.getDoubleValue("DETA_OIQ2_FWDRATE"));
		ac.setOIQ2_RATE_ROLL5(vo.getDoubleValue("OIQ2_RATE_ROLL5"));
		
		
		return ac;
	}
	
	
	
	/**
	 * 根据时间和机号 查询对应的滑油状态报文计算数据
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	private HashVO[] getComputedVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,DATE_UTC UTCDATE");
		sb.append(",T.ESN_1,T.OIQ1_Z3,T.CF_EHRS,T.DETA_OIQ1_FWD,T.DETA_OIQ1_FWD_S,T.DETA_OIQ1_FWDALARM,T.IS_OIQ1_ADDRPT,T.DETA_OIQ1_FWDRATE");
		sb.append(",T.ESN_2,T.OIQ2_Z3,T.CG_EHRS,T.DETA_OIQ2_FWD,T.DETA_OIQ2_FWD_S,T.DETA_OIQ2_FWDALARM,T.IS_OIQ2_ADDRPT,T.DETA_OIQ2_FWDRATE");
		
		//复杂计算值查询
		sb.append(",T1.F_VALUE_ROLL5 OIQ1_RATE_ROLL5");//左发滑油消耗率5点均
		sb.append(",T2.F_VALUE_ROLL5 OIQ2_RATE_ROLL5");//发滑油消耗率5点均
		sb.append(",T1.V_POINTTYPE OIQ1_RATE_POINTTYPE");//左发 飘点
		sb.append(",T2.V_POINTTYPE OIQ2_RATE_POINTTYPE");//右发 飘点
		
	    sb.append(" FROM A_DFD_A25_COMPUTE T");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T2");
	    sb.append(" WHERE DATE_UTC>=? AND DATE_UTC<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='DETA_OIQ1_FWDRATE'");
	    sb.append(" AND T2.MSG_NO =T.MSG_NO AND T2.F_NAME='DETA_OIQ2_FWDRATE'");

	    
	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	private HashVO[] getA27ComputedVos1(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,DATE_UTC UTCDATE");
		sb.append(",T.ESN_1,T.OILADD_ENG1,T.CAL_GA_ENG1,T.CAL_AIR_ENG1");
		
		//复杂计算值查询
		sb.append(",T1.F_VALUE_ROLL5 GA_ENG1_ROLL5");//左发滑油消耗率5点均  地面加空中
		sb.append(",T2.F_VALUE_ROLL5 AIR_ENG1_ROLL5");//右发滑油消耗率5点均  空中
		sb.append(",T1.V_POINTTYPE GA_ENG1_POINTTYPE");//左发飘点
		sb.append(",T2.V_POINTTYPE AIR_ENG1_POINTTYPE");//右发飘点
		
	    sb.append(" FROM A_DFD_A27_COMPUTE T");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T2");
	    sb.append(" WHERE DATE_UTC>=? AND DATE_UTC<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='CAL_GA_ENG1'");
	    sb.append(" AND T2.MSG_NO =T.MSG_NO AND T2.F_NAME='CAL_AIR_ENG1'");
	    
	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	private HashVO[] getA27ComputedVos2(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,DATE_UTC UTCDATE");
		sb.append(",T.ESN_2,T.OILADD_ENG2,T.CAL_GA_ENG2,T.CAL_AIR_ENG2");
		
		//复杂计算值查询
		sb.append(",T1.F_VALUE_ROLL5 GA_ENG2_ROLL5");//发滑油消耗率5点均 地面加空中
		sb.append(",T2.F_VALUE_ROLL5 AIR_ENG2_ROLL5");//右发滑油消耗率5点均  空中
		sb.append(",T1.V_POINTTYPE GA_ENG2_POINTTYPE");//左发飘点
		sb.append(",T2.V_POINTTYPE AIR_ENG2_POINTTYPE");//右发飘点
		
	    sb.append(" FROM A_DFD_A27_COMPUTE T");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T2");
	    sb.append(" WHERE DATE_UTC>=? AND DATE_UTC<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='CAL_GA_ENG2'");
	    sb.append(" AND T2.MSG_NO =T.MSG_NO AND T2.F_NAME='CAL_AIR_ENG2'");
	    
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
