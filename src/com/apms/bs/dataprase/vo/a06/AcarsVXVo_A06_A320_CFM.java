package com.apms.bs.dataprase.vo.a06;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsVXVo_A06_A320_CFM extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String t5; 
	private String p25; 
	private String t25; 
	private String p12;
	private String ecw1;
	private String vsv;
	private String vbv;
	
	public AcarsVXVo_A06_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		t5 = ReportParseUtil.strToStrWithIntPostion(columns[0], 3);
		p25 = ReportParseUtil.strToStrWithIntPostion(columns[1], 2);
		t25 = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		p12 = ReportParseUtil.strToStrWithIntPostion(columns[3], 2);
		ecw1 = ReportParseUtil.getNumberStr(columns[4]);
		vsv = ReportParseUtil.strToStrWithIntPostion(columns[5], 2);
		vbv = ReportParseUtil.strToStrWithIntPostion(columns[6], 2);
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
	public String getT5() {
		return t5;
	}
	public void setT5(String t5) {
		this.t5 = t5;
	}
	public String getP25() {
		return p25;
	}
	public void setP25(String p25) {
		this.p25 = p25;
	}
	public String getT25() {
		return t25;
	}
	public void setT25(String t25) {
		this.t25 = t25;
	}
	public String getP12() {
		return p12;
	}
	public void setP12(String p12) {
		this.p12 = p12;
	}
	public String getEcw1() {
		return ecw1;
	}
	public void setEcw1(String ecw1) {
		this.ecw1 = ecw1;
	}
	public String getVsv() {
		return vsv;
	}
	public void setVsv(String vsv) {
		this.vsv = vsv;
	}
	public String getVbv() {
		return vbv;
	}
	public void setVbv(String vbv) {
		this.vbv = vbv;
	}

}

