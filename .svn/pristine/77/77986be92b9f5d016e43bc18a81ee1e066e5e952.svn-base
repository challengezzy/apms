package com.apms.bs.dataprase.vo.a11;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsS1S2Vo_A11_A320_IAE extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String p25; 
	private String t25; 
	private String p3; 
	private String t3;
	private String p49;
	private String sva;
	
	public AcarsS1S2Vo_A11_A320_IAE(String str) throws Exception{
		originalStr = str;
		logger.debug("S1S2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		p25 = ReportParseUtil.getNumberStr(columns[0]);
		t25 = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		p3 = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		t3 = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		p49 = ReportParseUtil.strToStrWithIntPostion(columns[4], 2);
		sva = ReportParseUtil.getNumberStr(columns[5]);
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
	public String getP49() {
		return p49;
	}
	public void setP49(String p49) {
		this.p49 = p49;
	}
	public String getSva() {
		return sva;
	}
	public void setSva(String sva) {
		this.sva = sva;
	}

	
}
