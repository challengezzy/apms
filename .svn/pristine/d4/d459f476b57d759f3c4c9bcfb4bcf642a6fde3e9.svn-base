package com.apms.bs.oxygen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.oxygen.vo.A_DFD_A23_COMPUTE;
import com.apms.bs.oxygen.vo.A_DFD_RankPoint;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.MathUtil;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.vo.MathOneFitResult;
import com.apms.matlab.vo.MathOneXPerdictorAeraResult;


import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

public class OxygenService
{
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static OxygenService fileService = null;
	
	private CommDMO dmo = new CommDMO(); 
	
	public static OxygenService getInstance(){
		if(fileService != null)
			return fileService;
		
		fileService = new OxygenService();
		
		return fileService;
	}
	
	/**
	 * 氧气报文查询
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOxygenChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询氧气计算数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		
		List<A_DFD_A23_COMPUTE> changList = new ArrayList<A_DFD_A23_COMPUTE>();//所有换瓶点，以下数据去掉换瓶点
		
		List<A_DFD_A23_COMPUTE> allList = new ArrayList<A_DFD_A23_COMPUTE>();//除换瓶点后所有点
		
		List<A_DFD_RankPoint> s13Rank = new ArrayList<A_DFD_RankPoint>();//斜率线的集合,通过换瓶点的空值形成多个线段
		List<A_DFD_RankPoint> s46Rank = new ArrayList<A_DFD_RankPoint>();
		
		//斜率点集合
		List<List<A_DFD_RankPoint>> s13RankList = new ArrayList<List<A_DFD_RankPoint>>();
		List<List<A_DFD_RankPoint>> s46RankList = new ArrayList<List<A_DFD_RankPoint>>();
		
		//查询数据对象
		HashVO[] vos = getA23ComputedVos(begin_date, end_date, acNum);
		//计算数据数组
		List<Double> xarr1 = new ArrayList<Double>(vos.length);
		List<Double> yarr1 = new ArrayList<Double>(vos.length);
		List<Double> xarr2 = new ArrayList<Double>(vos.length);
		List<Double> yarr2 = new ArrayList<Double>(vos.length);
		
		
		for (int i = 0; i < vos.length; i++) {
			HashVO vo = vos[i];
			A_DFD_A23_COMPUTE ac = getA23ComputePoint(vo);
			
			//根据点类型不同，加入到不同的数组中
			if(DfdVarConst.DETAPRESSTATE_CHANGE == vo.getIntegerValue("DETA_PRES_FWD_S").intValue()){
				//换瓶点
				changList.add(ac);
				
				if(isRank && xarr1.size() > 2){
					//1 原先的数据计算
					List<A_DFD_RankPoint> tmpRank13 = MathUtil.computeRankList(xarr1,yarr1);
					s13RankList.add(tmpRank13);//TODO 多段数据，以后可以不用
					
					s13Rank.addAll(tmpRank13);
					A_DFD_RankPoint point13 = new A_DFD_RankPoint();
					point13.setX(new Double(vo.getDateValue("DATETIME_MID_S13").getTime()+""));
					s13Rank.add(point13);
					
					List<A_DFD_RankPoint> tmpRank46 = MathUtil.computeRankList(xarr2,yarr2);
					s46RankList.add(tmpRank46);
					
					s46Rank.addAll(tmpRank46);
					A_DFD_RankPoint point46 = new A_DFD_RankPoint();
					point46.setX(new Double(vo.getDateValue("DATETIME_MID_S46").getTime()+""));
					s46Rank.add(point46);
					
					//关键部分，加入丢失数据点，形成断线
					
					//2 新构造一个斜率计算数组
					xarr1 = new ArrayList<Double>(vos.length);
					yarr1 = new ArrayList<Double>(vos.length);
					xarr2 = new ArrayList<Double>(vos.length);
					yarr2 = new ArrayList<Double>(vos.length);
				}
				
				ac.setPRES_ST_C15_S13_ROLL5(null);
				ac.setPRES_ST_C15_S46_ROLL5(null);
				ac.setDETA_PRES_FWD_ROLL5(null);
				ac.setDETA_PRESRATE_ST_ROLL5(null);
				ac.setDETA_PRESRATE_STS46H24_ROLL5(null);
				allList.add(ac);
			}else{
				allList.add(ac);
			}
			xarr1.add(new Double(vo.getDateValue("DATETIME_MID_S13").getTime()+""));//
			yarr1.add(vo.getDoubleValue("PRES_ST_C15_S13"));
			xarr2.add(new Double(vo.getDateValue("DATETIME_MID_S46").getTime()+""));//X数据为毫秒数
			yarr2.add(vo.getDoubleValue("PRES_ST_C15_S46"));
			
		}
		
		if(isRank && xarr1.size() > 2){
			//回归计算
			List<A_DFD_RankPoint> tmpRank13 = MathUtil.computeRankList(xarr1,yarr1);
			s13RankList.add(tmpRank13);
			
			List<A_DFD_RankPoint> tmpRank46 = MathUtil.computeRankList(xarr2,yarr2);
			s46RankList.add(tmpRank46);
			
			s13Rank.addAll(tmpRank13);
			s46Rank.addAll(tmpRank46);
		}
		
		
		resMap.put("changList", changList);
		resMap.put("allList",allList);
		resMap.put("s13RankList", s13RankList);
		resMap.put("s46RankList", s46RankList);
		
		resMap.put("s13Rank", s13Rank);
		resMap.put("s46Rank", s46Rank);
		
		return resMap;
		
	}
	
	public void pointMarkA23(String msgno,String eventType,String memo,String marker,String marktime) throws Exception{
		
		 String dfStr = "yyyy-MM-dd HH:mm:ss";
		 Date mtime = DateUtil.StringToDate(marktime, dfStr);
		
		String updateSql = "UPDATE A_DFD_A23_COMPUTE T SET T.EVENTTYPE=?,T.MARKMEMO=?,T.MARKER=?,T.MARKTIME=? WHERE T.MSG_NO=?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, eventType,memo,marker,mtime,msgno);
		logger.debug("A23计算数据,MSG_NO=["+msgno+"]事件类型更新！");
	}
	
	public HashVO getA23MarkEvent(String msg_no) throws Exception{
		String qrySql = "SELECT T.ID,T.MSG_NO,T.EVENTTYPE,T.MARKMEMO,T.MARKTIME,T.MARKER FROM A_DFD_A23_COMPUTE T where msg_no=?";
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
	private A_DFD_A23_COMPUTE getA23ComputePoint(HashVO vo){
		A_DFD_A23_COMPUTE ac = new A_DFD_A23_COMPUTE();
		
		ac.setID(vo.getStringValue("ID"));
		ac.setMSG_NO(vo.getStringValue("MSG_NO"));
		ac.setDATE_UTC(vo.getStringValue("DATE_UTC"));
		ac.setPRES_ST_C15_S13(vo.getDoubleValue("PRES_ST_C15_S13"));
		
		//S13、S46标态压力
		ac.setPRES_ST_C15_S13_ROLL5(vo.getDoubleValue("PRES_ST_C15_S13_ROLL5"));
		ac.setDATETIME_MID_S13(vo.getStringValue("DATETIME_MID_S13"));
		ac.setDATETIME_MID_S13_MIS(vo.getDateValue("DATETIME_MID_S13").getTime());//S13 时间 毫秒数
		ac.setPRES_ST_C15_S46(vo.getDoubleValue("PRES_ST_C15_S46"));
		ac.setPRES_ST_C15_S46_ROLL5(vo.getDoubleValue("PRES_ST_C15_S46_ROLL5"));
		ac.setDATETIME_MID_S46(vo.getStringValue("DATETIME_MID_S46"));
		ac.setDATETIME_MID_S46_MIS(vo.getDateValue("DATETIME_MID_S46").getTime());
		
		//S46基于时间N点斜率
		ac.setPRES_ST_S46_K(vo.getDoubleValue("PRES_ST_S46_K")*(1000*3600*24));
		ac.setPRES_ST_S46_K_OUT(vo.getIntegerValue("PRES_ST_S46_K_OUT"));
		ac.setPRES_ST_S46_K_TSMP_ALTER(vo.getIntegerValue("PRES_ST_S46_K_TSMP_ALTER"));
		
		//与前一报文掉压率
		ac.setDETA_PRES_FWD(vo.getDoubleValue("DETA_PRES_FWD"));
		ac.setDETA_PRES_FWD_ROLL5(vo.getDoubleValue("DETA_PRES_FWD_ROLL5"));
		ac.setDETA_PRES_FWD_S(vo.getIntegerValue("DETA_PRES_FWD_S"));
		
		//本报文掉压率
		ac.setDETA_PRESRATE_ST(vo.getDoubleValue("DETA_PRESRATE_ST"));
		ac.setDETA_PRESRATE_ST_ROLL5(vo.getDoubleValue("DETA_PRESRATE_ST_ROLL5"));
		ac.setDETA_PRESRATE_REMARK(vo.getStringValue("DETA_PRESRATE_REMARK"));
		ac.setDETA_PRESRATE_ST_POINTTYPE(vo.getIntegerValue("DETA_PRESRATE_ST_POINTTYPE"));
		ac.setDETA_PRESRATE_ST_OUT(vo.getIntegerValue("DETA_PRESRATE_ST_OUT"));
		ac.setDETA_PRESRATE_ST_TSMP_ALTER(vo.getIntegerValue("DETA_PRESRATE_ST_TSMP_ALTER"));
		
		//24小时掉压率
		ac.setDETA_PRESRATE_STS46H24(vo.getDoubleValue("DETA_PRESRATE_STS46H24"));
		ac.setDETA_PRESRATE_STS46H24_ROLL5(vo.getDoubleValue("DETA_PRESRATE_STS46H24_ROLL5"));
		ac.setDETA_PRES_REMARKS46H24(vo.getStringValue("DETA_PRES_REMARKS46H24"));
		ac.setDETA_PRESRATE_H24_TSMP_ALTER(vo.getIntegerValue("DETA_PRESRATE_H24_TSMP_ALTER"));
		
		//人工标记
		ac.setMARKER(vo.getStringValue("MARKER"));
		ac.setEVENTTYPE(vo.getIntegerValue("EVENTTYPE")==null?0:vo.getIntegerValue("EVENTTYPE"));
		ac.setMARKMEMO(vo.getStringValue("MARKMEMO"));
		ac.setMARKTIME(vo.getStringValue("MARKTIME"));

		ac.setDATETIME_MID_S13(vo.getStringValue("DATETIME_MID_S13"));
		ac.setDATETIME_MID_S46(vo.getStringValue("DATETIME_MID_S46"));
		ac.setPRES_ST_C15_S13(vo.getDoubleValue("PRES_ST_C15_S13"));
		ac.setPRES_ST_C15_S46(vo.getDoubleValue("PRES_ST_C15_S46"));
		ac.setDETA_PRESRATE_ST(vo.getDoubleValue("DETA_PRESRATE_ST"));
		ac.setDETA_PRESRATE_ST_POINTTYPE(vo.getIntegerValue("DETA_PRESRATE_ST_POINTTYPE"));
		
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
	private HashVO[] getA23ComputedVos(String begin_date,String end_date,String acNum) throws Exception{
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("SELECT ID,MSG_NO,DATE_UTC,");
		sb.append("DETA_PRES_FWD,DETA_PRES_FWD_ROLL5,DETA_PRES_FWD_S,");//当前S13标态压力与前一报文标态S46压力差
		sb.append("DATETIME_MID_S13,PRES_ST_C15_S13,PRES_ST_C15_S13_ROLL5,"); //S13标态压力
	    sb.append("DATETIME_MID_S46,PRES_ST_C15_S46,PRES_ST_C15_S46_ROLL5,"); //S46标态压力
	    sb.append("DETA_PRESRATE_ST_TSMP_ALTER,PRES_ST_S46_K_TSMP_ALTER,DETA_PRESRATE_H24_TSMP_ALTER,");//样本T检验
	    sb.append("DETA_PRESRATE_ST,DETA_PRESRATE_ST_ROLL5,DETA_PRESRATE_ST_POINTTYPE,DETA_PRESRATE_ST_OUT,DETA_PRESRATE_MAN,DETA_PRESRATE_REMARK,");//本报文掉压
	    sb.append("DETA_PRESRATE_STS46H24,DETA_PRESRATE_STS46H24_ROLL5,DETA_PRESRATE_ST_S46H24_PTYPE,DETA_PRES_MANS46H24,DETA_PRESRATE_ST_OUTS46H24,DETA_PRES_REMARKS46H24,"); //24小时掉压
	    sb.append("PRES_ST_S46_K,PRES_ST_S46_K_OUT,");
	    sb.append("EVENTTYPE,MARKER,MARKTIME");//事件类型、人工标记
	    sb.append(" FROM A_DFD_A23_COMPUTE T WHERE ACNUM=? AND DATE_UTC>=? AND DATE_UTC<=?");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
	    
		//querySql = querySql+" And DATETIME_MID_S13>=to_date('"+begin_date+"','YYYY-MM-DD hh24:mi:ss')";
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(),acNum, beginDt,endDt);
		
		return vos;
	}
}
