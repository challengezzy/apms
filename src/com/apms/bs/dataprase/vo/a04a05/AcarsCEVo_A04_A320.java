package com.apms.bs.dataprase.vo.a04a05;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.StringUtil;

public class AcarsCEVo_A04_A320 {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private Float tat; 
	private String alt; 
	
	public AcarsCEVo_A04_A320(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		tat = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "tat");
		alt = ReportParseUtil.getNumberStr(columns[1]);	
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
	
	public Float getTat() {
		return tat;
	}
	public void setTat(Float tat) {
		this.tat = tat;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}


	
}
