package com.apms.bs.dataprase.vo.a06;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsVXVo_A06_A320_IAE extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String eprc; 
	private String t2; 
	private String p2;
	private String mn;
	private String ecw3;
	private String ecw4;
	private String trp;
	
	
	public AcarsVXVo_A06_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		eprc = ReportParseUtil.strToStrWithIntPostion(columns[0], 1);
		t2 = ReportParseUtil.strToStrWithIntPostion(columns[1], 2);
		p2 = ReportParseUtil.strToStrWithIntPostion(columns[2], 2);
		mn = ReportParseUtil.strToStrWithIntPostion(columns[3], 1);
		ecw3 = ReportParseUtil.getNumberStr(columns[4]);
		ecw4 = ReportParseUtil.getNumberStr(columns[5]);
		trp = ReportParseUtil.getNumberStr(columns[6]);
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
	public String getEprc() {
		return eprc;
	}
	public void setEprc(String eprc) {
		this.eprc = eprc;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	public String getMn() {
		return mn;
	}
	public void setMn(String mn) {
		this.mn = mn;
	}
	public String getEcw3() {
		return ecw3;
	}
	public void setEcw3(String ecw3) {
		this.ecw3 = ecw3;
	}
	public String getEcw4() {
		return ecw4;
	}
	public void setEcw4(String ecw4) {
		this.ecw4 = ecw4;
	}
	public String getTrp() {
		return trp;
	}
	public void setTrp(String trp) {
		this.trp = trp;
	}

}
