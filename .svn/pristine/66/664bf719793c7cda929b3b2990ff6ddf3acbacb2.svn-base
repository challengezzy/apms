package com.apms.bs.dataprase.vo.a34;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsS7Vo_A34_A320 extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String wav1_tmr; 
	private String wav2_tmr; 
	private String date_time;
	private Date dateTime;
	private String month;
	//private String transdate;
	
	public AcarsS7Vo_A34_A320(String str, String transdate) throws Exception{
		originalStr = str.substring(2);
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = originalStr ;
		if (originalStr.startsWith(",")){
			tempStr = str.substring(3);
		}
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 4);
		if (columns[0].contains("---") ||  columns[0].contains("XXX")){
			wav1_tmr=null;
		}else {
			wav1_tmr=columns[0].substring(0,3)+"."+columns[0].substring(3,columns[0].length());
		}
		if (columns[1].contains("---") ||  columns[1].contains("XXX")){
			wav2_tmr=null;
		}else {
			wav2_tmr=columns[1].substring(0,3)+"."+columns[1].substring(3,columns[1].length());
		}
		
		try{
			month = DateUtil.abbrMonthToNumber(columns[2].substring(0,3));
			String mondd_hhmmss = transdate.substring(0,4)+"-"+month+"-"+columns[2].substring(3,5)+" "
			+columns[3].substring(0, 2) + ":" + columns[3].substring(2, 4) + ":" + columns[3].substring(4, 6);

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

	public String getWav1_tmr() {
		return wav1_tmr;
	}

	public void setWav1_tmr(String wav1_tmr) {
		this.wav1_tmr = wav1_tmr;
	}

	public String getWav2_tmr() {
		return wav2_tmr;
	}

	public void setWav2_tmr(String wav2_tmr) {
		this.wav2_tmr = wav2_tmr;
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
