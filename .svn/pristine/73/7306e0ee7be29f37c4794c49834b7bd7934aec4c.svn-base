package com.apms.bs.dataprase.vo.a11;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsT1T2Vo_A11_A320_IAE extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String baf; 
	private String acc; 
	private String lp; 
	private String gle;
	private String pd;
	private String tn;
	private String p2;
	private String t2;
	
	public AcarsT1T2Vo_A11_A320_IAE(String str) throws Exception{
		originalStr = str;
		logger.debug("T1T2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 8);
		baf = ReportParseUtil.getNumberStr(columns[0]);
		acc = ReportParseUtil.getNumberStr(columns[1]);
		lp = ReportParseUtil.getNumberStr(columns[2]);
		gle = ReportParseUtil.getNumberStr(columns[3]);
		pd = ReportParseUtil.getNumberStr(columns[4]);
		tn = ReportParseUtil.strToStrWithIntPostion(columns[5], 3);
		p2 = ReportParseUtil.strToStrWithIntPostion(columns[6], 2);
		t2 = ReportParseUtil.strToStrWithIntPostion(columns[7], 2);
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
	public String getBaf() {
		return baf;
	}
	public void setBaf(String baf) {
		this.baf = baf;
	}
	public String getAcc() {
		return acc;
	}
	public void setAcc(String acc) {
		this.acc = acc;
	}
	public String getLp() {
		return lp;
	}
	public void setLp(String lp) {
		this.lp = lp;
	}
	public String getGle() {
		return gle;
	}
	public void setGle(String gle) {
		this.gle = gle;
	}
	public String getPd() {
		return pd;
	}
	public void setPd(String pd) {
		this.pd = pd;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}

	
}
