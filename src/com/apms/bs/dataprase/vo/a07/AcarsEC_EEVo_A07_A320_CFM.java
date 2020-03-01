package com.apms.bs.dataprase.vo.a07;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsEC_EEVo_A07_A320_CFM extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String esn; 
	private String ehrs; 
	private String ert;
	private String ecyc;
	private String ap;
	private String ecw1;
	
	public AcarsEC_EEVo_A07_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("EC_EE content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		esn = ReportParseUtil.getNumberStr(columns[0]);
		ehrs = ReportParseUtil.getNumberStr(columns[1]);
		ert = ReportParseUtil.getNumberStr(columns[2]);
		ecyc = ReportParseUtil.getNumberStr(columns[3]);
		ap = ReportParseUtil.getNumberStr(columns[4]);
		ecw1 = ReportParseUtil.getNumberStr(columns[5]);
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
	public void setEhrs(String ehrs) {
		this.ehrs = ehrs;
	}
	public String getErt() {
		return ert;
	}
	public void setErt(String ert) {
		this.ert = ert;
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
	public String getEcw1() {
		return ecw1;
	}
	public void setEcw1(String ecw1) {
		this.ecw1 = ecw1;
	}

}
