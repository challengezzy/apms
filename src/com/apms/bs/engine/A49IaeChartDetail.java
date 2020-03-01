package com.apms.bs.engine;

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


public class A49IaeChartDetail {
	protected  Logger logger = NovaLogger.getLogger(this.getClass());
	
	private  CommDMO dmo = new CommDMO(); 

	//获取A49 启动活门性能图表数据
	public  Map<String, Object> getEngineA49ChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询发动机报文(A49)数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		//查询A49所有的左右换发点
	    List<Map<String,Object>> allA49LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA49ChangedListVos(begin_date, end_date, acNum,"left");
	    allA49LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA49LeftChangedList", allA49LeftChangedList);
	   
	    List<Map<String,Object>> allA49RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA49ChangedListVos(begin_date, end_date, acNum,"right");
	    allA49RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA49RightChangedList", allA49RightChangedList);
	    
	    //查询 A49数据对象 
		List<Map<String,Object>> allA49List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA49List = getA49ListVos(begin_date, end_date, acNum);
		allA49List.addAll(HashVoUtil.hashVosToMapList(vosA49List));
		resMap.put("allA49List", allA49List);
		
	    //egt margin取04报文中的
		List<Map<String,Object>> temp;
		String allDiv ="DIV_EGT,DIV_N1,DIV_N2,DIV_EPR,DIV_FF,DIV_TIME,TIME_1,TIME_2";
		String[] alldiv = allDiv.split(",");
		for(int i=0;i<alldiv.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA04List = getEngineA49ComputedVos(begin_date, end_date, acNum,alldiv[i].trim());
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA04List));
			String key = "allA49Compu_"+alldiv[i].toLowerCase().trim();
		    resMap.put(key,temp) ;
		}
		
		return resMap;
	}
	
	private  HashVO[] getA49ListVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE,T.MSG_NO");
		sb.append(",T.ESN_1,T.ESN_2"); 
		sb.append(",T.N1_1,T.N1_2,T.N2_1,T.N2_2,T.FF_1,T.FF_2");
		sb.append(",T.EGT_1,T.EGT_2,T.EPR_1,T.EPR_2,T.TIME_1,T.TIME_2");
		sb.append(",EGT_L3,EGT_R3,(EGT_L3-EGT_R3)/2 DIV_EGT3_1, (EGT_R3-EGT_L3)/2 DIV_EGT3_2");
		sb.append(",H.TAT,H.ALT");
		sb.append(" FROM A_DFD_A49IAEV25_LIST T,A_DFD_A49IAEV25_COMPUTE T1,A_DFD_HEAD H ");
	    sb.append(" WHERE T.MSG_NO = T1.MSG_NO AND H.MSG_NO=T.MSG_NO AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	//获取A49 IAE的换发点
	private  HashVO[] getEngineA49ChangedListVos(String begin_date,String end_date,String acNum,String leftOrRight) throws Exception{
		String leftOrRightStr = "";
		if(leftOrRight=="left"){
			leftOrRightStr = " ISCHANGEPOINT1 =1";
		}else if(leftOrRight=="right"){
			leftOrRightStr = " ISCHANGEPOINT2 =1";
		}
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE,T.MSG_NO");
		sb.append(",T.ESN_1,T.ESN_2");
		sb.append(",T.N1_1,T.N1_2,T.N2_1,T.N2_2,T.FF_1,T.FF_2");
		sb.append(",T.EGT_1,T.EGT_2,T.EPR_1,T.EPR_2,T.TIME_1,T.TIME_2");
		sb.append(",H.TAT,H.ALT");
		sb.append(" FROM A_DFD_A49IAEV25_LIST T,A_DFD_A49IAEV25_COMPUTE T1, A_DFD_HEAD H ");
	    sb.append(" WHERE T.MSG_NO = T1.MSG_NO AND H.MSG_NO=T.MSG_NO AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND"+leftOrRightStr);
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY T.MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
	
	
	private HashVO[] getEngineA49ComputedVos (String begin_date,String end_date,String acNum,String param) throws Exception{
		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT T.MSG_NO,T1.V_POINTTYPE,F_VALUE_ROLL5 ROLL5,T1.F_NAME NAME");
		sb.append(",F_VALUE VALUE,-(F_VALUE) VALUE_2");
		sb.append(",T.ESN_1,T.ESN_2,T.ACNUM,T.RPTDATE,T.RPTDATE UTCDATE");
	    sb.append(" FROM A_DFD_A49IAEV25_COMPUTE T,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" WHERE T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"' " );
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='"+param+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}

}

