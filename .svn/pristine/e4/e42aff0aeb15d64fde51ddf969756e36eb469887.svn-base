package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsS1S2Vo_A04_A320_IAE extends AcarsLineDataVo{
	
	private Float epr; 
	private Float egt; 
	private Float n1; 
	private Float n2;
	private Float ff;
	private Float eprt;
	private Float eprc;
	
	public AcarsS1S2Vo_A04_A320_IAE(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		epr = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "epr");
		egt = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3 ,"egt" );
		
		n1 = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "n1");
		n2 = ReportParseUtil.strToFloatWithIntPostion(columns[3], 3, "n2");
		ff = ReportParseUtil.strToFloat(columns[4],"ff");
		eprt = ReportParseUtil.strToFloatWithIntPostion(columns[5], 1, "eprt");
		eprc = ReportParseUtil.strToFloatWithIntPostion(columns[6], 1, "eprc");

		
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


	public Float getEpr() {
		return epr;
	}
	public void setEpr(Float epr) {
		this.epr = epr;
	}
	public Float getN1() {
		return n1;
	}
	public void setN1(Float n1) {
		this.n1 = n1;
	}
	public Float getN2() {
		return n2;
	}
	public void setN2(Float n2) {
		this.n2 = n2;
	}
	public Float getEprt() {
		return eprt;
	}
	public void setEprt(Float eprt) {
		this.eprt = eprt;
	}
	public Float getEprc() {
		return eprc;
	}
	public void setEprc(Float eprc) {
		this.eprc = eprc;
	}

	
}
