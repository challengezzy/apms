package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsF1Vo_A21_A320 extends AcarsLineDataVo {
	private Float ckt;
	private Float fwdt;
	private Float aftt;
	private String hotairpb;

	public AcarsF1Vo_A21_A320(String str) throws Exception {
		originalStr = str;

		String tempStr = str.substring(2);
		String[] columns;
		tempStr = tempStr.trim();
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		
		checkFieldsNum(originalStr, columns, 4);
		
		ckt = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "ckt");
		fwdt = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "fwdt");
		aftt = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "aftt");
		hotairpb = getBooleanStr(columns[3]);
	}
	
	public AcarsF1Vo_A21_A320(String str, String rep) throws Exception {
		originalStr = str;

		String tempStr = str.substring(2);
		String[] columns;
		tempStr = tempStr.trim();
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		
		checkFieldsNum(originalStr, columns, 4);
		
		ckt = ReportParseUtil.strToFloat(columns[0], "ckt");
		fwdt = ReportParseUtil.strToFloat(columns[1], "fwdt");
		aftt = ReportParseUtil.strToFloat(columns[2], "aftt");
		hotairpb = getBooleanStr(columns[3]);
	}
	
	private String getBooleanStr(String str){
		if(str == null || str.contains("XX") || "NOX".equals(str) ){
			return "0";
		}else if( "ONX".equals(str) ) {
			return "1";
		}else{//未知
			return "0";
		}
	}

	public Float getCkt() {
		return ckt;
	}

	public void setCkt(Float ckt) {
		this.ckt = ckt;
	}

	public Float getFwdt() {
		return fwdt;
	}

	public void setFwdt(Float fwdt) {
		this.fwdt = fwdt;
	}

	public Float getAftt() {
		return aftt;
	}

	public void setAftt(Float aftt) {
		this.aftt = aftt;
	}

	public String getHotairpb() {
		return hotairpb;
	}

	public void setHotairpb(String hotairpb) {
		this.hotairpb = hotairpb;
	}
	

}
