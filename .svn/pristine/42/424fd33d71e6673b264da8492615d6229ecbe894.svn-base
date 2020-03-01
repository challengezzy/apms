package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsM1Vo_A21_A320 extends AcarsLineDataVo{
	private Float pin_hpv;
	private Float pin_prv;
	private String opv1;
	private String opv2;
	
	public AcarsM1Vo_A21_A320(String str) throws Exception{
		
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns; 
		columns = StringUtil.splitString2Array(tempStr, ",", true);		
		checkFieldsNum(originalStr,columns, 4);
		
		pin_hpv = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "pin_hpv");
		pin_prv = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "pin_prv");
		
		opv1 = getBooleanStr(columns[2]);
		opv2 = getBooleanStr(columns[3]);
		
	}
	
	public AcarsM1Vo_A21_A320(String str,String rep) throws Exception{
		
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns; 
		columns = StringUtil.splitString2Array(tempStr, ",", true);		
		checkFieldsNum(originalStr,columns, 4);
		
		pin_hpv = ReportParseUtil.strToFloat(columns[0], "pin_hpv");
		pin_prv = ReportParseUtil.strToFloat(columns[1], "pin_prv");
		
		opv1 = getBooleanStr(columns[2]);
		opv2 = getBooleanStr(columns[3]);
		
	}
	
	private String getBooleanStr(String str){
		if(str == null || str.contains("NFO") || "NFC".equals(str) ){
			return "0";
		}else if( "FOX".equals(str) || "FCX".equals(str) ) {
			return "1";
		}else{//未知
			return "0";
		}
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

	public String getOpv1() {
		return opv1;
	}

	public void setOpv1(String opv1) {
		this.opv1 = opv1;
	}

	public String getOpv2() {
		return opv2;
	}

	public void setOpv2(String opv2) {
		this.opv2 = opv2;
	}
	
	
	
}
