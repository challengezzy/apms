package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsSTVXVo_A05_A320_IAE extends AcarsLineDataVo{
	
	private Float epr; 
	private Float egt; 
	private Float n1; 
	private Float n2;
	private Float ff;
	private Float vb1;
	private Float vb2;

	
	public AcarsSTVXVo_A05_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		epr = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1 ,"epr" );
		egt = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3 ,"egt" );
		n1 = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3 ,"n1" );
		n2 = ReportParseUtil.strToFloatWithIntPostion(columns[3], 3 ,"n2" );
		ff = ReportParseUtil.strToFloat(columns[4] ,"ff" );
		vb1 = ReportParseUtil.strToFloatWithIntPostion(columns[5], 2 ,"vb1" );
		vb2 = ReportParseUtil.strToFloatWithIntPostion(columns[6], 2 ,"vb2" );

	}


	public Float getEpr() {
		return epr;
	}


	public void setEpr(Float epr) {
		this.epr = epr;
	}


	public Float getEgt() {
		return egt;
	}


	public void setEgt(Float egt) {
		this.egt = egt;
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


	public Float getFf() {
		return ff;
	}


	public void setFf(Float ff) {
		this.ff = ff;
	}


	public Float getVb1() {
		return vb1;
	}


	public void setVb1(Float vb1) {
		this.vb1 = vb1;
	}


	public Float getVb2() {
		return vb2;
	}


	public void setVb2(Float vb2) {
		this.vb2 = vb2;
	}
	
}
