package com.apms.bs.dataprase.vo.a10;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsN1Vo_A10_A320_IAE extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String e; 
	private String max; 
	private String lim; 
	private String tol;
	private String ttp;
	private String ttf;
	private String ff;
	private String pd;
	private String sm;

	
	public AcarsN1Vo_A10_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("N1 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 9);
		e = ReportParseUtil.getNumberStr(columns[0]);
		max = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		lim = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		tol = ReportParseUtil.getNumberStr(columns[3]);
		ttp = ReportParseUtil.getNumberStr(columns[4]);
		ttf = ReportParseUtil.getNumberStr(columns[5]);
		ff = ReportParseUtil.getNumberStr(columns[6]);
		pd = ReportParseUtil.getNumberStr(columns[7]);
		sm = ReportParseUtil.getNumberStr(columns[8]);
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
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e = e;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getLim() {
		return lim;
	}
	public void setLim(String lim) {
		this.lim = lim;
	}
	public String getTol() {
		return tol;
	}
	public void setTol(String tol) {
		this.tol = tol;
	}
	public String getTtp() {
		return ttp;
	}
	public void setTtp(String ttp) {
		this.ttp = ttp;
	}
	public String getTtf() {
		return ttf;
	}
	public void setTtf(String ttf) {
		this.ttf = ttf;
	}
	public String getFf() {
		return ff;
	}
	public void setFf(String ff) {
		this.ff = ff;
	}
	public String getPd() {
		return pd;
	}
	public void setPd(String pd) {
		this.pd = pd;
	}
	public String getSm() {
		return sm;
	}
	public void setSm(String sm) {
		this.sm = sm;
	}


}
