package com.apms.bs.dataprase.vo.a48;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsL1Vo_A48_Iae extends AcarsLineDataVo {
	private String epr;
	private String egt;
	private String n1;
	private String n2;
	private String ff;
	private String t2;
	private String p2;
	
	public AcarsL1Vo_A48_Iae(String str) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
	    checkFieldsNum(originalStr,columns, 7);
	    
	    epr = ReportParseUtil.strToStrWithIntPostion(columns[0], 1);
		egt = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		n1 = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		n2 = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		ff = ReportParseUtil.getNumberStr(columns[5]);
		p2 = ReportParseUtil.strToStrWithIntPostion(columns[5], 2);
		t2 = ReportParseUtil.strToStrWithIntPostion(columns[6], 3);
		
	}

	public String getEpr() {
		return epr;
	}

	public void setEpr(String epr) {
		this.epr = epr;
	}

	public String getEgt() {
		return egt;
	}

	public void setEgt(String egt) {
		this.egt = egt;
	}

	public String getN1() {
		return n1;
	}

	public void setN1(String n1) {
		this.n1 = n1;
	}

	public String getN2() {
		return n2;
	}

	public void setN2(String n2) {
		this.n2 = n2;
	}

	public String getFf() {
		return ff;
	}

	public void setFf(String ff) {
		this.ff = ff;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}
}
