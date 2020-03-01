package com.apms.bs.dataprase.vo.a11;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.util.StringUtil;

public class AcarsV3V4Vo_A11_A320_CFM {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String oip; 
	private String oit; 
	private String ecw1;
	private String ssel;
	
	public AcarsV3V4Vo_A11_A320_CFM(String str){
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		oip = getNumberStr(columns[0]);
		oit = getNumberStr(columns[1]);
		ecw1 = getNumberStr(columns[2]);
		ssel = getNumberStr(columns[3]);

		
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
	public String getOip() {
		return oip;
	}
	public void setOip(String oip) {
		this.oip = oip;
	}
	public String getOit() {
		return oit;
	}
	public void setOit(String oit) {
		this.oit = oit;
	}
	public String getEcw1() {
		return ecw1;
	}
	public void setEcw1(String ecw1) {
		this.ecw1 = ecw1;
	}
	public String getSsel() {
		return ssel;
	}
	public void setSsel(String ssel) {
		this.ssel = ssel;
	}


}
