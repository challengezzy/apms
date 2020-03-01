package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV1V2Vo_A01A02_A320_IAE extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	

	private String ecw1;
	private String ecw2;
	private String evm;
	private String oip;
	private String oit;
	private String oiqh;
	
	
	public AcarsV1V2Vo_A01A02_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);

		ecw1 = ReportParseUtil.getNumberStr(columns[0]);
		ecw2 = ReportParseUtil.getNumberStr(columns[1]);
		evm = ReportParseUtil.getNumberStr(columns[2]);
		oip = ReportParseUtil.getNumberStr(columns[3]);
		oit = ReportParseUtil.getNumberStr(columns[4]);
		oiqh = ReportParseUtil.strToStrWithIntPostion(columns[5], 2);
	
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
	public String getOiqh() {
		return oiqh;
	}
	public void setOiqh(String oiqh) {
		this.oiqh = oiqh;
	}
	
	
	

}
