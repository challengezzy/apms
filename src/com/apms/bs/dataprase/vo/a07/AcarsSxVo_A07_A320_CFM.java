package com.apms.bs.dataprase.vo.a07;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsSxVo_A07_A320_CFM extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String n1; 
	private String n2; 
	private String vn; 
	private String vc;
	private String vh;
	private String vl;
	private String pha;
	private String pht;
	private String oit;
	private String oip;

	
	public AcarsSxVo_A07_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("N1 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 10);
		n1 = ReportParseUtil.strToStrWithIntPostion(columns[0], 3);
		n2 = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		vn = ReportParseUtil.getNumberStr(columns[2]);
		vc = ReportParseUtil.strToStrWithIntPostion(columns[3], 1);
		vh = ReportParseUtil.strToStrWithIntPostion(columns[4], 1);
		vl = ReportParseUtil.getNumberStr(columns[5]);
		pha = ReportParseUtil.getNumberStr(columns[6]);
		pht = ReportParseUtil.getNumberStr(columns[7]);
		oit = ReportParseUtil.getNumberStr(columns[8]);
		oip = ReportParseUtil.getNumberStr(columns[9]);
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
	public String getN1() {
		return n1;
	}
	public void setN1(String n1) {
		this.n1 = n1;
	}
	public String getN2() {
		return n2;
	}
	public void setN2(String n2) {
		this.n2 = n2;
	}
	public String getVn() {
		return vn;
	}
	public void setVn(String vn) {
		this.vn = vn;
	}
	public String getVc() {
		return vc;
	}
	public void setVc(String vc) {
		this.vc = vc;
	}
	public String getVh() {
		return vh;
	}
	public void setVh(String vh) {
		this.vh = vh;
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
	public String getOit() {
		return oit;
	}
	public void setOit(String oit) {
		this.oit = oit;
	}
	public String getOip() {
		return oip;
	}
	public void setOip(String oip) {
		this.oip = oip;
	}

}
