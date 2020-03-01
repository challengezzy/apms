package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsECVo_A26_A320 extends AcarsLineDataVo{
	
	private String esn; 
	private String ehrs; 
	private String ecyc;
	private String ap;
	
	public AcarsECVo_A26_A320(String str) throws Exception{
		originalStr = str;
		logger.debug("EC content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 4);
		esn = ReportParseUtil.getNumberStr(columns[0]);
		ehrs = ReportParseUtil.getNumberStr(columns[1]);
		ecyc = ReportParseUtil.getNumberStr(columns[2]);
		ap = ReportParseUtil.getNumberStr(columns[3]);
	}
	
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public String getEhrs() {
		return ehrs;
	}
	public void setEhrs(String ehrs) {
		this.ehrs = ehrs;
	}
	public String getEcyc() {
		return ecyc;
	}
	public void setEcyc(String ecyc) {
		this.ecyc = ecyc;
	}
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}

}
