package com.apms.bs.dataprase.vo.a49;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsL2Vo_A49_A320 extends AcarsLineDataVo {
	
	private Float epr;
	private Float n1; 
	private Float n2; 
	private Float ff;
	private Float egt;
	private String ctl;
	
	public AcarsL2Vo_A49_A320(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		
		epr =ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "epr");
		n1 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "n1");
		n2 = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "n2");
		ff = ReportParseUtil.strToFloat(columns[3],"ff");
		egt = ReportParseUtil.strToFloatWithIntPostion(columns[4], 3 ,"egt" );
		ctl = columns[5];
	}
	public Float getN1() {
		return n1;
	}
	public void setN1(Float n1) {
		this.n1 = n1;
	}
	
	public Float getEgt() {
		return egt;
	}
	public void setEgt(Float egt) {
		this.egt = egt;
	}
	public Float getFf() {
		return ff;
	}
	public void setFf(Float ff) {
		this.ff = ff;
	}
	public Float getN2() {
		return n2;
	}
	public void setN2(Float n2) {
		this.n2 = n2;
	}
	public Float getEpr() {
		return epr;
	}
	public void setEpr(Float epr) {
		this.epr = epr;
	}
	public String getCtl() {
		return ctl;
	}
	public void setCtl(String ctl) {
		this.ctl = ctl;
	}

}
