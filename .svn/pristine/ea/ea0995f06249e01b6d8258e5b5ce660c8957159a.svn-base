package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV3V4Vo_A01A02_A320_IAE extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	

	private String vb1;
	private String vb2;
	private String pha;

	public AcarsV3V4Vo_A01A02_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);
		vb1 = ReportParseUtil.strToStrWithIntPostion(columns[0], 2);
		vb2 = ReportParseUtil.strToStrWithIntPostion(columns[1], 2);
		pha = ReportParseUtil.getNumberStr(columns[2]);
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
	public String getVb1() {
		return vb1;
	}
	public void setVb1(String vb1) {
		this.vb1 = vb1;
	}
	public String getVb2() {
		return vb2;
	}
	public void setVb2(String vb2) {
		this.vb2 = vb2;
	}
	public String getPha() {
		return pha;
	}
	public void setPha(String pha) {
		this.pha = pha;
	}
	
	
	
}
