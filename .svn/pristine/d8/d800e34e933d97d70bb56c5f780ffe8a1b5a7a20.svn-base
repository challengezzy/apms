package com.apms.bs.dataprase.vo.a11;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV1V2Vo_A11_A320_IAE extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String ecw1; 
	private String ecw2; 
	private String evm; 
	private String vb1;
	private String vb2;
	private String pha;
	
	public AcarsV1V2Vo_A11_A320_IAE(String str) throws Exception{
		originalStr = str;
		logger.debug("V1V2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		ecw1 = ReportParseUtil.getNumberStr(columns[0]);
		ecw2 = ReportParseUtil.getNumberStr(columns[1]);
		evm = ReportParseUtil.getNumberStr(columns[2]);
		vb1 = ReportParseUtil.strToStrWithIntPostion(columns[3], 2);
		vb2 = ReportParseUtil.strToStrWithIntPostion(columns[4], 2);
		pha = ReportParseUtil.getNumberStr(columns[5]);
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
	public String getEcw1() {
		return ecw1;
	}
	public void setEcw1(String ecw1) {
		this.ecw1 = ecw1;
	}
	public String getEcw2() {
		return ecw2;
	}
	public void setEcw2(String ecw2) {
		this.ecw2 = ecw2;
	}
	public String getEvm() {
		return evm;
	}
	public void setEvm(String evm) {
		this.evm = evm;
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
