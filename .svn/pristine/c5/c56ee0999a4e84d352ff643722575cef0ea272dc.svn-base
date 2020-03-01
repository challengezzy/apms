package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsD1Vo_A21_A320 extends AcarsLineDataVo{
	private Float pdmt_l; 
	private Float pdmt_r;
	
	public AcarsD1Vo_A21_A320(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns; 
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 2);
		
		pdmt_l = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "pdmt_l");
		pdmt_r = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "pdmt_r");
	}
	
	public AcarsD1Vo_A21_A320(String str, String rep) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns; 
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 2);
		
		pdmt_l = ReportParseUtil.strToFloat(columns[0], "pdmt_l");
		pdmt_r = ReportParseUtil.strToFloat(columns[1], "pdmt_r");
	}

	public Float getPdmt_l() {
		return pdmt_l;
	}

	public void setPdmt_l(Float pdmt_l) {
		this.pdmt_l = pdmt_l;
	}

	public Float getPdmt_r() {
		return pdmt_r;
	}

	public void setPdmt_r(Float pdmt_r) {
		this.pdmt_r = pdmt_r;
	}

}
