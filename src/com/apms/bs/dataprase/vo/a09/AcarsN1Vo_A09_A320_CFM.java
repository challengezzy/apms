package com.apms.bs.dataprase.vo.a09;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsN1Vo_A09_A320_CFM extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String e; 
	private String div; 
	private String ref; 
	private String k;
	private String ecw1;
	private String ecw2;
	private String psel;
	
	
	public AcarsN1Vo_A09_A320_CFM(String str) throws Exception{
		originalStr = str;
		////logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		e = ReportParseUtil.getNumberStr(columns[0]);
		div = ReportParseUtil.getNumberStr(columns[1]);
		ref = ReportParseUtil.getNumberStr(columns[2]);
		k = ReportParseUtil.getNumberStr(columns[3]);
		ecw1 = ReportParseUtil.getNumberStr(columns[4]);
		ecw2 = ReportParseUtil.getNumberStr(columns[5]);
		psel = ReportParseUtil.getNumberStr(columns[6]);
		
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
	public String getDiv() {
		return div;
	}
	public void setDiv(String div) {
		this.div = div;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
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
	public String getPsel() {
		return psel;
	}
	public void setPsel(String psel) {
		this.psel = psel;
	}


}
