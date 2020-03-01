package com.apms.bs.dataprase.vo.a09;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.StringUtil;

public class AcarsEC_EEVo_A09_A320_IAE {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String esn; 
	private String ehrs; 
	private String ecyc;
	private String ap;
	private String sva;
	private String baf;
	
	
	public AcarsEC_EEVo_A09_A320_IAE(String str){
		originalStr = str;
	//	//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		esn = ReportParseUtil.getNumberStr(columns[0]);
		ehrs = ReportParseUtil.getNumberStr(columns[1]);
		ecyc = ReportParseUtil.getNumberStr(columns[2]);
		ap = ReportParseUtil.getNumberStr(columns[3]);
		sva = ReportParseUtil.getNumberStr(columns[4]);
		baf = ReportParseUtil.getNumberStr(columns[5]);
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
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public String getEhrs() {
		return ehrs;
	}
	public String getEcyc() {
		return ecyc;
	}
	public void setEcyc(String ecyc) {
		this.ecyc = ecyc;
	}
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}
	public String getSva() {
		return sva;
	}
	public void setSva(String sva) {
		this.sva = sva;
	}
	public String getBaf() {
		return baf;
	}
	public void setBaf(String baf) {
		this.baf = baf;
	}
	public void setEhrs(String ehrs) {
		this.ehrs = ehrs;
	}


}
