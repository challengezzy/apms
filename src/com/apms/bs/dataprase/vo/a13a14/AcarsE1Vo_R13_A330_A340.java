package com.apms.bs.dataprase.vo.a13a14; //

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.StringUtil;

import smartx.framework.common.vo.NovaLogger;

public class AcarsE1Vo_R13_A330_A340 {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String asn; //APU序号
	private Integer ahrs; //APU小时
	private Integer acyc; //APU循环
	private String ecid;
	private String acw1;
	
	
	
	public AcarsE1Vo_R13_A330_A340(String str, String acmodel) throws Exception{
		String tempStr;
		originalStr = str;
		//logger.debug("E1 content: " + originalStr);
//		if (acmodel.equals("A330")){
//			tempStr = str.replace("\r", "").trim().substring(3);
//		} else {
//			tempStr = str.replace("\r", "").trim();
//		}
		tempStr = str.replace("\r", "").trim();

		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		asn = columns[0];
		
		ahrs = ReportParseUtil.strToInteger(columns[1], "AHRS") * 60; //小时转换为分钟
		acyc = ReportParseUtil.strToInteger(columns[2], "ACYC");
		ecid = columns[3];
		acw1 = columns[4];
		
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
	public String getAsn() {
		return asn;
	}
	public void setAsn(String asn) {
		this.asn = asn;
	}
	
	public Integer getAhrs() {
		return ahrs;
	}
	public void setAhrs(Integer ahrs) {
		this.ahrs = ahrs;
	}

	public Integer getAcyc() {
		return acyc;
	}

	public void setAcyc(Integer acyc) {
		this.acyc = acyc;
	}

	public String getEcid() {
		return ecid;
	}
	public void setEcid(String ecid) {
		this.ecid = ecid;
	}
	public String getAcw1() {
		return acw1;
	}
	public void setAcw1(String acw1) {
		this.acw1 = acw1;
	}	
}
