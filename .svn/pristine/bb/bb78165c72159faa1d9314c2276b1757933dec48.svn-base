package com.apms.bs.dataprase.vo.a38;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsE1Vo_A38 extends AcarsLineDataVo{
	
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String asn;
	private String ahrs;
	private String acyc;
	private String pfad;
	
	public AcarsE1Vo_A38(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		String[] columns;
		String tempStr = str.substring(2).trim();
		if(tempStr.endsWith("/")){
			tempStr=tempStr.substring(0, tempStr.length()-1);
			columns= StringUtil.splitString2Array(tempStr, ",", true); 
		}else{
			columns= StringUtil.splitString2Array(tempStr, " ", true); 
		}
		
		
		checkFieldsNum(originalStr,columns, 4);
		asn = ReportParseUtil.getNumberStr(columns[0]);
		ahrs = ReportParseUtil.getNumberStr(columns[1]);
		acyc = ReportParseUtil.getNumberStr(columns[2]);
		pfad = ReportParseUtil.getNumberStr(columns[3]);
	
	}
	
	public String getAsn() {
		return asn;
	}
	public void setAsn(String asn) {
		this.asn = asn;
	}
	public String getAhrs() {
		return ahrs;
	}
	public void setAhrs(String ahrs) {
		this.ahrs = ahrs;
	}
	public String getAcyc() {
		return acyc;
	}
	public void setAcyc(String acyc) {
		this.acyc = acyc;
	}
	public String getPfad() {
		return pfad;
	}
	public void setPfad(String pfad) {
		this.pfad = pfad;
	}
	public String getOriginalStr() {
		return originalStr;
	}
	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}

}
