package com.apms.bs.dataprase.vo.a11;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

import smartx.framework.common.vo.NovaLogger;

public class AcarsN1N2Vo_A11_A320_CFM extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	

	private String n1;
	private String n1c;
	private String n2;
	private String egt;
	private String ff;
	private String ps13;

	
	public AcarsN1N2Vo_A11_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		checkFieldsNum(originalStr,columns, 6);
		n1 = ReportParseUtil.getNumberStr(columns[0]);
		n1c = ReportParseUtil.getNumberStr(columns[1]);
		n2 = ReportParseUtil.getNumberStr(columns[2]);
		egt = ReportParseUtil.getNumberStr(columns[3]);
		ff = ReportParseUtil.getNumberStr(columns[4]);
		ps13 = ReportParseUtil.getNumberStr(columns[5]);
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
	public String getPs13() {
		return ps13;
	}
	public void setPs13(String ps13) {
		this.ps13 = ps13;
	}


}
