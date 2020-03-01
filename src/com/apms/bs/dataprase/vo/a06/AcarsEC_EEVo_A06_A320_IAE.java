package com.apms.bs.dataprase.vo.a06;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsEC_EEVo_A06_A320_IAE extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String esn; 
	private String ehrs; 
	private String ecyc;
	private String ap;
	private String hpt;
	private String sva;
	private String baf;
	private String lp;
	
	public AcarsEC_EEVo_A06_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 8);
		esn = ReportParseUtil.getNumberStr(columns[0]);
		ehrs = ReportParseUtil.getNumberStr(columns[1]);
		ecyc = ReportParseUtil.getNumberStr(columns[2]);
		ap = ReportParseUtil.getNumberStr(columns[3]);
		hpt = ReportParseUtil.getNumberStr(columns[4]);
		sva = ReportParseUtil.getNumberStr(columns[5]);
		baf = ReportParseUtil.getNumberStr(columns[6]);
		lp = getNumberStrLp(columns[7]);

		
	}
	private String getNumberStrLp(String oldStr){
		String newStr;
		if (oldStr.contains("-") ||  oldStr.contains("X"))
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
	public String getHpt() {
		return hpt;
	}
	public void setHpt(String hpt) {
		this.hpt = hpt;
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
	public String getLp() {
		return lp;
	}
	public void setLp(String lp) {
		this.lp = lp;
	}

}
