package com.apms.bs.dataprase.vo.a34;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsS135Vo_A34_A320 extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String pd; 
	private String tpo; 
	private String wav;
	private String date_time;
	private Date dateTime;
	private String month;
	
	public Date getdateTime() {
		return dateTime;
	}
	
	public AcarsS135Vo_A34_A320(String str,String transdate) throws Exception{
		originalStr = str.substring(2);
		//logger.debug("S135 content: " + originalStr);
		
		String tempStr = originalStr;
		if (originalStr.startsWith(",")){
			tempStr = str.substring(3);
		}
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);
		pd = ReportParseUtil.getNumberStr(columns[0]);
		tpo = ReportParseUtil.getNumberStr(columns[1]);
		//wav = ParseUtil.getNumberStr(columns[3]);
		
		if (columns[2].contains("---")||columns[2].contains("XXX"))
		{ 
			wav=null;
		}else if(columns[2].contains("OPEN")){
			wav = "1";//new Integer(columns[1]).toString();
		}else {
			wav = "0";
		}
		try{
			month = DateUtil.abbrMonthToNumber(columns[3].substring(0,3));
			String mondd_hhmmss = transdate.substring(0,4)+"-"+month+"-"+columns[3].substring(3,5)+" "
			+columns[4].substring(0, 2) + ":" + columns[4].substring(2, 4) + ":" + columns[4].substring(4, 6);

			dateTime = DateUtil.StringToDate(mondd_hhmmss, "yyyy-MM-dd HH:mm:ss");
		}catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}
	}
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getOriginalStr() {
		return originalStr;
	}
	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}
	public String getPd() {
		return pd;
	}
	public void setPd(String pd) {
		this.pd = pd;
	}
	public String getTpo() {
		return tpo;
	}
	public void setTpo(String tpo) {
		this.tpo = tpo;
	}
	public String getWav() {
		return wav;
	}
	public void setWav(String wav) {
		this.wav = wav;
	}
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}


}
