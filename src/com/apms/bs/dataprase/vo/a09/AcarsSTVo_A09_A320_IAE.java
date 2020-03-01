package com.apms.bs.dataprase.vo.a09;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.StringUtil;

public class AcarsSTVo_A09_A320_IAE {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String epr; 
	private String egt; 
	private String n1;
	private String n2;
	private String ff;
	private String tn;
	private String eprc;
	
	
	public AcarsSTVo_A09_A320_IAE(String str){
		originalStr = str;
		////logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		epr = ReportParseUtil.strToStrWithIntPostion(columns[0], 1);
		egt = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		n1 = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		n2 = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		ff = ReportParseUtil.getNumberStr(columns[4]);
		tn = ReportParseUtil.getNumberStr(columns[5]);
		eprc = ReportParseUtil.strToStrWithIntPostion(columns[6], 1);
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
	public String getEpr() {
		return epr;
	}
	public void setEpr(String epr) {
		this.epr = epr;
	}
	public String getEgt() {
		return egt;
	}
	public void setEgt(String egt) {
		this.egt = egt;
	}
	public String getN1() {
		return n1;
	}
	public void setN1(String n1) {
		this.n1 = n1;
	}
	public String getN2() {
		return n2;
	}
	public void setN2(String n2) {
		this.n2 = n2;
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
	public String getEprc() {
		return eprc;
	}
	public void setEprc(String eprc) {
		this.eprc = eprc;
	}


}
