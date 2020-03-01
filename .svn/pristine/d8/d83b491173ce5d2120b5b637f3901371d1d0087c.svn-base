package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsCGVo_A25_A320 extends AcarsLineDataVo{
	private String esn; //
	private Float ehrs; //
	private Float ecyc; //
	private Float ehr_g_a;
	
	public AcarsCGVo_A25_A320(String str, boolean isRep) throws Exception{
		originalStr = str;

		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 4);
		esn = ReportParseUtil.getNumberStr(columns[0]);
		ehrs = ReportParseUtil.strToFloat(columns[1], "ehrs");
		ecyc = ReportParseUtil.strToFloat(columns[2], "ecyc");
		
		if(isRep){
			ehr_g_a = ReportParseUtil.strToFloat(columns[3], "ehr_g_a");
		}else{
			ehr_g_a = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2,"ehr_g_a");
		}
	}
	
	public String getOriginalStr() {
		return originalStr;
	}
	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
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
	public Float getEhr_g_a() {
		return ehr_g_a;
	}
	public void setEhr_g_a(Float ehr_g_a) {
		this.ehr_g_a = ehr_g_a;
	}
	

}
