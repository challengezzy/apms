package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsT1_T2Vo_A26_A320 extends AcarsLineDataVo{
	
	private Float baf; 
	private Float acc; 
	private Float lp;
	private Float gle;
	private Float pd;
	private Float tn;
	private Float p2;
	private Float t2;
	
	public AcarsT1_T2Vo_A26_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 8);
		
		baf = ReportParseUtil.strToFloat(columns[0], "bak");
		acc = ReportParseUtil.strToFloat(columns[1], "acc");
		lp = ReportParseUtil.strToFloat(columns[2], "lp");
		gle = ReportParseUtil.strToFloat(columns[3], "gle");

		if(isRep){
			pd = ReportParseUtil.strToFloat(columns[4], "pd");
		}else{
			pd = ReportParseUtil.strToFloatWithIntPostion(columns[4], 2, "pd");
		}
		
		tn = ReportParseUtil.strToFloat(columns[5], "tn");
		p2 = ReportParseUtil.strToFloatWithIntPostion(columns[6], 2, "p2");
		t2 = ReportParseUtil.strToFloatWithIntPostion(columns[7], 3, "t2");

	}
	
	public AcarsT1_T2Vo_A26_A320(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 8);
		
		baf = ReportParseUtil.strToFloat(columns[0], "bak");
		acc = ReportParseUtil.strToFloat(columns[1], "acc");
		lp = ReportParseUtil.strToFloat(columns[2], "lp");
		gle = ReportParseUtil.strToFloat(columns[3], "gle");

		
		tn = ReportParseUtil.strToFloat(columns[5], "tn");
		p2 = ReportParseUtil.strToFloatWithIntPostion(columns[6], 2, "p2");
		t2 = ReportParseUtil.strToFloatWithIntPostion(columns[7], 3, "t2");

	}

	public Float getBaf() {
		return baf;
	}

	public void setBaf(Float baf) {
		this.baf = baf;
	}

	public Float getAcc() {
		return acc;
	}

	public void setAcc(Float acc) {
		this.acc = acc;
	}

	public Float getLp() {
		return lp;
	}

	public void setLp(Float lp) {
		this.lp = lp;
	}

	public Float getGle() {
		return gle;
	}

	public void setGle(Float gle) {
		this.gle = gle;
	}

	public Float getPd() {
		return pd;
	}

	public void setPd(Float pd) {
		this.pd = pd;
	}

	public Float getTn() {
		return tn;
	}

	public void setTn(Float tn) {
		this.tn = tn;
	}

	public Float getP2() {
		return p2;
	}

	public void setP2(Float p2) {
		this.p2 = p2;
	}

	public Float getT2() {
		return t2;
	}

	public void setT2(Float t2) {
		this.t2 = t2;
	}

}
