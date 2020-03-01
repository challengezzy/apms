package com.apms.bs.dataprase.vo.a09;
import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.util.StringUtil;

public class AcarsSTVo_A09_A320_CFM {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String n1; 
	private String n1c; 
	private String n2; 
	private String egt;
	private String ff;
	private String tn;
	
	public AcarsSTVo_A09_A320_CFM(String str){
		originalStr = str;
		////logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		n1 = getNumberStr(columns[0]);
		n1c = getNumberStr(columns[1]);
		n2 = getNumberStr(columns[2]);
		egt = getNumberStr(columns[3]);
		ff = getNumberStr(columns[4]);
		tn = getNumberStr(columns[5]);

		
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
	public String getN1() {
		return n1;
	}
	public void setN1(String n1) {
		this.n1 = n1;
	}
	public String getN1c() {
		return n1c;
	}
	public void setN1c(String n1c) {
		this.n1c = n1c;
	}
	public String getN2() {
		return n2;
	}
	public void setN2(String n2) {
		this.n2 = n2;
	}
	public String getEgt() {
		return egt;
	}
	public void setEgt(String egt) {
		this.egt = egt;
	}
	public String getFf() {
		return ff;
	}
	public void setFf(String ff) {
		this.ff = ff;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}


}
