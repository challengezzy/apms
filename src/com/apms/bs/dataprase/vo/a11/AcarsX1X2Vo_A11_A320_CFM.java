package com.apms.bs.dataprase.vo.a11;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.util.StringUtil;

public class AcarsX1X2Vo_A11_A320_CFM {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String egtk; 
	private String n1k; 
	private String n2k; 
	private String ffk;
	
	public AcarsX1X2Vo_A11_A320_CFM(String str){
		originalStr = str;
		logger.debug("X1X2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		egtk = getNumberStr(columns[0]);
		n1k = getNumberStr(columns[1]);
		n2k = getNumberStr(columns[2]);
		ffk = getNumberStr(columns[3]);

	}
	private String getNumberStr(String oldStr){
		String newStr;
		if (oldStr.contains("---") ||  oldStr.contains("XXX"))
		{ 
			newStr = null;
		}else{
			newStr = oldStr;
		}
		
		return newStr;
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
	public String getEgtk() {
		return egtk;
	}
	public void setEgtk(String egtk) {
		this.egtk = egtk;
	}
	public String getN1k() {
		return n1k;
	}
	public void setN1k(String n1k) {
		this.n1k = n1k;
	}
	public String getN2k() {
		return n2k;
	}
	public void setN2k(String n2k) {
		this.n2k = n2k;
	}
	public String getFfk() {
		return ffk;
	}
	public void setFfk(String ffk) {
		this.ffk = ffk;
	}

}
