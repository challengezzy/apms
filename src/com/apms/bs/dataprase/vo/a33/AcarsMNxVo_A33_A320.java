package com.apms.bs.dataprase.vo.a33;


import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsMNxVo_A33_A320 extends AcarsLineDataVo{
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private Float epr;
	private Float n1;
	private Float n2;
	private Float ff;
	private Float egt;
	private String eec_cha;
	private Float eec_cha_egt;
	
	public AcarsMNxVo_A33_A320(String str,boolean isRep) throws Exception{
		originalStr = str;
		//logger.debug("MNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		
		if(isRep){
			epr = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "epr");
			n1 = ReportParseUtil.strToFloat(columns[1], "n1");
			n2 = ReportParseUtil.strToFloat(columns[2], "n2");
			ff = ReportParseUtil.strToFloat(columns[3],"ff");
			egt = ReportParseUtil.strToFloat(columns[4], "egt");
		}else{
			epr = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "epr");
			n1 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 2, "n1");
			n2 = ReportParseUtil.strToFloatWithIntPostion(columns[2], 2, "n2");
			ff = ReportParseUtil.strToFloat(columns[3],"ff");
			egt = ReportParseUtil.strToFloatWithIntPostion(columns[4], 3, "egt");
		}
		
		if(columns.length==7){
			eec_cha=columns[5];
			eec_cha_egt = ReportParseUtil.strToFloatWithIntPostion(columns[6], 3, "eec_cha_egt");
		}
	}

	public String getOriginalStr() {
		return originalStr;
	}

	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
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

	public Float getFf() {
		return ff;
	}

	public void setFf(Float ff) {
		this.ff = ff;
	}

	public Float getEgt() {
		return egt;
	}

	public void setEgt(Float egt) {
		this.egt = egt;
	}

	public String getEec_cha() {
		return eec_cha;
	}

	public void setEec_cha(String eec_cha) {
		this.eec_cha = eec_cha;
	}

	public Float getEec_cha_egt() {
		return eec_cha_egt;
	}

	public void setEec_cha_egt(Float eec_cha_egt) {
		this.eec_cha_egt = eec_cha_egt;
	}

	
}
