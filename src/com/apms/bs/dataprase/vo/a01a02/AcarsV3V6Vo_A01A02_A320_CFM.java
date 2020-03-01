package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV3V6Vo_A01A02_A320_CFM extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	

	private String vn;
	private String vl;
	private String pha;
	private String pht;
	private String n1;

	
	public AcarsV3V6Vo_A01A02_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);
		vn=ReportParseUtil.strToStrWithIntPostion(columns[0], 1);
		vl=ReportParseUtil.strToStrWithIntPostion(columns[1], 1);
		pha=ReportParseUtil.getNumberStr(columns[2]);
		pht=ReportParseUtil.getNumberStr(columns[3]);
		n1=ReportParseUtil.getNumberStr(columns[4]);
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
	
	
	public String getVn() {
		return vn;
	}
	public void setVn(String vn) {
		this.vn = vn;
	}
	public String getVl() {
		return vl;
	}
	public void setVl(String vl) {
		this.vl = vl;
	}
	public String getPha() {
		return pha;
	}
	public void setPha(String pha) {
		this.pha = pha;
	}
	public String getPht() {
		return pht;
	}
	public void setPht(String pht) {
		this.pht = pht;
	}
	public String getN1() {
		return n1;
	}
	public void setN1(String n1) {
		this.n1 = n1;
	}

}
