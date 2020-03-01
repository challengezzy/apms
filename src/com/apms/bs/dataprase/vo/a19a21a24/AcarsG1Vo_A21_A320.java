package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsG1Vo_A21_A320 extends AcarsLineDataVo{
	private Float ckduct;
	private Float fwduct;
	private Float aftduct;
	private Float mixf;
	private Float mixcab;
	
	public AcarsG1Vo_A21_A320(String str) throws Exception {
		originalStr = str;

		String tempStr = str.substring(2);
		String[] columns; 
		tempStr=tempStr.trim();
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		
		checkFieldsNum(originalStr,columns, 5);
		
		ckduct = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "ckduct");
		fwduct = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "fwduct");
		aftduct = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "aftduct");
		mixf = ReportParseUtil.strToFloatWithIntPostion(columns[3], 3, "mixf");
		mixcab = ReportParseUtil.strToFloatWithIntPostion(columns[4], 3, "mixcab");
	}
	
	public AcarsG1Vo_A21_A320(String str,String rep) throws Exception {
		originalStr = str;

		String tempStr = str.substring(2);
		String[] columns; 
		tempStr=tempStr.trim();
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		
		checkFieldsNum(originalStr,columns, 5);
		
		ckduct = ReportParseUtil.strToFloat(columns[0], "ckduct");
		fwduct = ReportParseUtil.strToFloat(columns[1], "fwduct");
		aftduct = ReportParseUtil.strToFloat(columns[2], "aftduct");
		mixf = ReportParseUtil.strToFloat(columns[3], "mixf");
		mixcab = ReportParseUtil.strToFloat(columns[4], "mixcab");
	}

	public Float getCkduct() {
		return ckduct;
	}

	public void setCkduct(Float ckduct) {
		this.ckduct = ckduct;
	}

	public Float getFwduct() {
		return fwduct;
	}

	public void setFwduct(Float fwduct) {
		this.fwduct = fwduct;
	}

	public Float getAftduct() {
		return aftduct;
	}

	public void setAftduct(Float aftduct) {
		this.aftduct = aftduct;
	}

	public Float getMixf() {
		return mixf;
	}

	public void setMixf(Float mixf) {
		this.mixf = mixf;
	}

	public Float getMixcab() {
		return mixcab;
	}

	public void setMixcab(Float mixcab) {
		this.mixcab = mixcab;
	}
	
}
