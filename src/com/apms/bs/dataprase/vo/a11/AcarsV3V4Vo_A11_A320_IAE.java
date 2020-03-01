package com.apms.bs.dataprase.vo.a11;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV3V4Vo_A11_A320_IAE extends AcarsLineDataVo {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String oip; 
	private String oit; 
	private String egtk;
	private String n1k;
	private String n2k;
	private String ffk;
	
	public AcarsV3V4Vo_A11_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		oip = ReportParseUtil.getNumberStr(columns[0]);
		oit = ReportParseUtil.getNumberStr(columns[1]);
		egtk = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		n1k = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		n2k = ReportParseUtil.strToStrWithIntPostion(columns[4], 3);
		ffk = ReportParseUtil.getNumberStr(columns[5]);
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
	public String getOip() {
		return oip;
	}
	public void setOip(String oip) {
		this.oip = oip;
	}
	public String getOit() {
		return oit;
	}
	public void setOit(String oit) {
		this.oit = oit;
	}
	public String getEgtk() {
		return egtk;
	}
	public void setEgtk(String egtk) {
		this.egtk = egtk;
	}
	public String getN1k() {
		return n1k;
	}
	public void setN1k(String n1k) {
		this.n1k = n1k;
	}
	public String getN2k() {
		return n2k;
	}
	public void setN2k(String n2k) {
		this.n2k = n2k;
	}
	public String getFfk() {
		return ffk;
	}
	public void setFfk(String ffk) {
		this.ffk = ffk;
	}


}
