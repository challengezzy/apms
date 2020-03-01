package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsEEVo_A26_A320 extends AcarsLineDataVo{
	private String esn; 
	private Float ehrs; 
	private Float ecyc;
	private String ap;
	
	public AcarsEEVo_A26_A320(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 4);
		esn = columns[0];
		ehrs = ReportParseUtil.strToFloat(columns[1], "ehrs");
		ecyc = ReportParseUtil.strToFloat(columns[2], "ecyc");
		ap = columns[3];

	}
	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	
	public Float getEhrs() {
		return ehrs;
	}
	public void setEhrs(Float ehrs) {
		this.ehrs = ehrs;
	}
	public Float getEcyc() {
		return ecyc;
	}
	public void setEcyc(Float ecyc) {
		this.ecyc = ecyc;
	}
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}


}
