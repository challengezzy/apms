package com.apms.bs.dataprase.vo.a09;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.StringUtil;

public class AcarsN1Vo_A09_A320_IAE {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String e; 
	private String div; 
	private String ref;
	private String k;
	private String ecw3;
	private String ecw4;
	private String para;
	
	public AcarsN1Vo_A09_A320_IAE(String str){
		originalStr = str;
		////logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		e = ReportParseUtil.getNumberStr(columns[0]);
		div = ReportParseUtil.getNumberStr(columns[1]);
		ref = ReportParseUtil.getNumberStr(columns[2]);
		k = ReportParseUtil.getNumberStr(columns[3]);
		ecw3 = ReportParseUtil.getNumberStr(columns[4]);
		ecw4 = ReportParseUtil.getNumberStr(columns[5]);
		para = ReportParseUtil.getNumberStr(columns[6]);
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
	public String getEcw3() {
		return ecw3;
	}
	public void setEcw3(String ecw3) {
		this.ecw3 = ecw3;
	}
	public String getEcw4() {
		return ecw4;
	}
	public void setEcw4(String ecw4) {
		this.ecw4 = ecw4;
	}
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}


}
