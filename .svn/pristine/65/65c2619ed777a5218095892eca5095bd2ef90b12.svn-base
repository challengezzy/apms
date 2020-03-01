package com.apms.bs.dataprase.vo.a10;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsTxVo_A10_A320_IAE extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String p3; 
	private String t3; 
	private String sva; 
	private String baf;
	private String t2;
	private String oit;
	private String ecw5;

	
	public AcarsTxVo_A10_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("Sx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		p3 = ReportParseUtil.strToStrWithIntPostion(columns[0], 3);
		t3 = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		sva = ReportParseUtil.getNumberStr(columns[2]);
		baf = ReportParseUtil.getNumberStr(columns[3]);
		t2 = ReportParseUtil.strToStrWithIntPostion(columns[4], 2);
		oit = ReportParseUtil.getNumberStr(columns[5]);
		ecw5 = ReportParseUtil.getNumberStr(columns[6]);
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
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}
	public String getOit() {
		return oit;
	}
	public void setOit(String oit) {
		this.oit = oit;
	}
	public String getEcw5() {
		return ecw5;
	}
	public void setEcw5(String ecw5) {
		this.ecw5 = ecw5;
	}


}
