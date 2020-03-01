package com.apms.bs.dataprase.vo.a36;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsS0Vo_A36 extends AcarsLineDataVo{
	
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private Float operate_mins;
	private Date  stoptime;
	private String vid;
	
	/**
	 * 报文内容解析
	 * @param str s0行内容
	 * @param transdate 日期
	 * @param 是否seq报文
	 * @throws Exception
	 */
	public AcarsS0Vo_A36(String str,String transdate,boolean isReq) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		String[] columns;
		String tempStr = str.substring(2).trim();
		if(tempStr.endsWith("/")){
			tempStr=tempStr.substring(0, tempStr.length()-1);
			columns= StringUtil.splitString2Array(tempStr, ",", true); 
		}else{
			columns= StringUtil.splitString2Array(tempStr, " ", true); 
		}
		
		
		checkFieldsNum(originalStr,columns, 3);
		
		if( isReq ){ //isReq类新报文自带小数点
			operate_mins = ReportParseUtil.strToFloat(columns[0],  "OPERATE_MINS");
		}else{
			operate_mins = ReportParseUtil.strToFloatWithDecimalPostion(columns[0], 2, "OPERATE_MINS");
		}
		
		
		try{
			String month = DateUtil.abbrMonthToNumber(columns[1].substring(0,3));
			String mondd_hhmmss = transdate.substring(0,4)+"-"+month+"-"+columns[1].substring(3,5)+" "
			+columns[1].substring(5, 7) + ":" + columns[1].substring(7, 9) + ":" + columns[1].substring(9, 11);

			stoptime = DateUtil.StringToDate(mondd_hhmmss, "yyyy-MM-dd HH:mm:ss");
		}catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}
		
		vid = ReportParseUtil.getNumberStr(columns[2]);
	
	}

	public Float getOperate_mins() {
		return operate_mins;
	}

	public Date getStoptime() {
		return stoptime;
	}

	public String getVid() {
		return vid;
	}
	

}
