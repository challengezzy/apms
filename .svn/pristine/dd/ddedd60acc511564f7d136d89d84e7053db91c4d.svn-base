package com.apms.bs.dataprase.vo.a06;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsSTVo_A06_A320_IAE extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String epr; 
	private String egt; 
	private String n1;
	private String n2;
	private String ff;
	private String p3;
	private String t3;
	
	
	public AcarsSTVo_A06_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		epr = ReportParseUtil.strToStrWithIntPostion(columns[0], 1);
		egt = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		n1 = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		n2 = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		ff = ReportParseUtil.strToStrWithIntPostion(columns[4], 3);
		p3 = ReportParseUtil.strToStrWithIntPostion(columns[5], 3);
		t3 = ReportParseUtil.strToStrWithIntPostion(columns[6], 3);
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
	public String getP3() {
		return p3;
	}
	public void setP3(String p3) {
		this.p3 = p3;
	}
	public String getT3() {
		return t3;
	}
	public void setT3(String t3) {
		this.t3 = t3;
	}

}
