package com.apms.bs.dataprase.vo.a11;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.util.StringUtil;

public class AcarsV1V2Vo_A11_A320_CFM {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String vn; 
	private String vl; 
	private String pha; 
	private String pht;
	private String vc;
	private String vh;
	private String evm;
	
	public AcarsV1V2Vo_A11_A320_CFM(String str){
		originalStr = str;
		logger.debug("V1V2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		vn = getNumberStr(columns[0]);
		vl = getNumberStr(columns[1]);
		pha = getNumberStr(columns[2]);
		pht = getNumberStr(columns[3]);
		vc = getNumberStr(columns[4]);
		vh = getNumberStr(columns[5]);
		evm = getNumberStr(columns[6]);

		
	}
	private String getNumberStr(String oldStr){
		String newStr;
		if (oldStr.contains("--") ||  oldStr.contains("XX"))
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
	public String getVn() {
		return vn;
	}
	public void setVn(String vn) {
		this.vn = vn;
	}
	public String getVl() {
		return vl;
	}
	public void setVl(String vl) {
		this.vl = vl;
	}
	public String getPha() {
		return pha;
	}
	public void setPha(String pha) {
		this.pha = pha;
	}
	public String getPht() {
		return pht;
	}
	public void setPht(String pht) {
		this.pht = pht;
	}
	public String getVc() {
		return vc;
	}
	public void setVc(String vc) {
		this.vc = vc;
	}
	public String getVh() {
		return vh;
	}
	public void setVh(String vh) {
		this.vh = vh;
	}
	public String getEvm() {
		return evm;
	}
	public void setEvm(String evm) {
		this.evm = evm;
	}

	
	
}
