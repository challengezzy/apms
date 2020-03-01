package com.apms.bs.dataprase.vo.a07;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsN1Vo_A07_A320_IAE extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String e; 
	private String max; 
	private String lim; 
	private String ref;
	private String tol;
	private String ttp;
	private String evm;
	private String para;

	
	public AcarsN1Vo_A07_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("N1 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 8);
		e = ReportParseUtil.getNumberStr(columns[0]);
		max = ReportParseUtil.getNumberStr(columns[1]);
		lim = ReportParseUtil.getNumberStr(columns[2]);
		ref = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		tol = ReportParseUtil.strToStrWithIntPostion(columns[4], 2);
		ttp = ReportParseUtil.getNumberStr(columns[5]);
		evm = ReportParseUtil.getNumberStr(columns[6]);
		para = ReportParseUtil.getNumberStr(columns[7]);		
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
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
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
	public String getEvm() {
		return evm;
	}
	public void setEvm(String evm) {
		this.evm = evm;
	}
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}

}
