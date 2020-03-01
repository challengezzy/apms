package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsS1S2Vo_A04_A320_CFM extends AcarsLineDataVo{
	
	private Float n1; 
	private Float n1c; 
	private Float n2; 
	private Float egt;
	private Float ff;
	private Float p3;
	private Float n1mx;
	
	public AcarsS1S2Vo_A04_A320_CFM(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		n1 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "n1");
		n1c = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "n1c");
		n2 = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "n2");
		egt = ReportParseUtil.strToFloatWithIntPostion(columns[3], 3 ,"egt" );
		
		ff = ReportParseUtil.strToFloat(columns[4],"ff");
		p3 = ReportParseUtil.strToFloatWithIntPostion(columns[5], 3, "p3");
		n1mx = ReportParseUtil.strToFloatWithIntPostion(columns[6], 3, "n1mx");
		
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
	public Float getN1c() {
		return n1c;
	}
	public void setN1c(Float n1c) {
		this.n1c = n1c;
	}
	public Float getN2() {
		return n2;
	}
	public void setN2(Float n2) {
		this.n2 = n2;
	}
	public Float getP3() {
		return p3;
	}
	public void setP3(Float p3) {
		this.p3 = p3;
	}
	public Float getN1mx() {
		return n1mx;
	}
	public void setN1mx(Float n1mx) {
		this.n1mx = n1mx;
	}


	
	
}
