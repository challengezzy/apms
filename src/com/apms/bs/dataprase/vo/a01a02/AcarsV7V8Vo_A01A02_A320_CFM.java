package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV7V8Vo_A01A02_A320_CFM extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	

	private String oip;
	private String oit;
	private String ecw1	;
	private String ssel;
	
	
	public AcarsV7V8Vo_A01A02_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 4);
		oip=ReportParseUtil.getNumberStr(columns[0]);
		oit=ReportParseUtil.getNumberStr(columns[1]);
		ecw1=ReportParseUtil.getNumberStr(columns[2]);
		ssel=ReportParseUtil.getNumberStr(columns[3]);
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
	public String getEcw1() {
		return ecw1;
	}
	public void setEcw1(String ecw1) {
		this.ecw1 = ecw1;
	}
	public String getSsel() {
		return ssel;
	}
	public void setSsel(String ssel) {
		this.ssel = ssel;
	}

}
