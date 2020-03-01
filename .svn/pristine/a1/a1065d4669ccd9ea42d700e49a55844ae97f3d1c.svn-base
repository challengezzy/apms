package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsN1_N2Vo_A26_A320 extends AcarsLineDataVo {
	private Float epr;
	private Float eprc;
	private Float egt;
	private Float n1;
	private Float n2;
	private Float ff;
	private Float p125;

	public AcarsN1_N2Vo_A26_A320(String str,boolean isRep) throws Exception {
		originalStr = str;

		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr, columns, 7);

		epr = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "epr");
		eprc = ReportParseUtil.strToFloatWithIntPostion(columns[1], 1, "eprc");
		egt = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "egt");
		
		if(isRep){
			n1 = ReportParseUtil.strToFloat(columns[3], "n1");
			n2 = ReportParseUtil.strToFloat(columns[4], "n2");
		}else{
			n1 = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2, "n1");
			n2 = ReportParseUtil.strToFloatWithIntPostion(columns[4], 2, "n2");
		}
		
		ff = ReportParseUtil.strToFloat(columns[5], "ff");
		p125 = ReportParseUtil.strToFloatWithIntPostion(columns[6], 2, "p125");

	}

	public Float getEpr() {
		return epr;
	}

	public void setEpr(Float epr) {
		this.epr = epr;
	}

	public Float getEprc() {
		return eprc;
	}

	public void setEprc(Float eprc) {
		this.eprc = eprc;
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

	public Float getP125() {
		return p125;
	}

	public void setP125(Float p125) {
		this.p125 = p125;
	}

}
