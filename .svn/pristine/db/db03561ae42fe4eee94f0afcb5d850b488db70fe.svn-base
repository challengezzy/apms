package com.apms.bs.dataprase.vo.a11;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.util.StringUtil;

public class AcarsT1T2Vo_A11_A320_CFM {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String hpt; 
	private String lpt; 
	private String gle; 
	private String pd;
	private String tn;
	private String pt2;
	
	public AcarsT1T2Vo_A11_A320_CFM(String str){
		originalStr = str;
		logger.debug("T1T2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		hpt = getNumberStr(columns[0]);
		lpt = getNumberStr(columns[1]);
		gle = getNumberStr(columns[2]);
		pd = getNumberStr(columns[3]);
		tn = getNumberStr(columns[4]);
		pt2 = getNumberStr(columns[5]);

		
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
	public String getHpt() {
		return hpt;
	}
	public void setHpt(String hpt) {
		this.hpt = hpt;
	}
	public String getLpt() {
		return lpt;
	}
	public void setLpt(String lpt) {
		this.lpt = lpt;
	}
	public String getGle() {
		return gle;
	}
	public void setGle(String gle) {
		this.gle = gle;
	}
	public String getPd() {
		return pd;
	}
	public void setPd(String pd) {
		this.pd = pd;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	public String getPt2() {
		return pt2;
	}
	public void setPt2(String pt2) {
		this.pt2 = pt2;
	}

	
}
