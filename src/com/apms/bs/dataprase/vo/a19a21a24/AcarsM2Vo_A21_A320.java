package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsM2Vo_A21_A320 extends AcarsLineDataVo{
	private Float pin_hpv;
	private Float pin_prv;

	public AcarsM2Vo_A21_A320(String str) throws Exception{
		
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns; 
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 2);	
		
		pin_hpv = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "pin_hpv");
		pin_prv = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "pin_prv");
	}
	
	public AcarsM2Vo_A21_A320(String str,String rep) throws Exception{
		
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns; 
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 2);	
		
		pin_hpv = ReportParseUtil.strToFloat(columns[0], "pin_hpv");
		pin_prv = ReportParseUtil.strToFloat(columns[1], "pin_prv");
	}

	public Float getPin_hpv() {
		return pin_hpv;
	}

	public void setPin_hpv(Float pin_hpv) {
		this.pin_hpv = pin_hpv;
	}

	public Float getPin_prv() {
		return pin_prv;
	}

	public void setPin_prv(Float pin_prv) {
		this.pin_prv = pin_prv;
	}
	
}
