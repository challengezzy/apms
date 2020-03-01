package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV1V2Vo_A04_A320_CFM extends AcarsLineDataVo{
	
	private Float vn; 
	private Float vl; 
	private Float pha; 
	private Float pht;
	private Float vc;
	private Float vh;
	private String evm;
	
	public AcarsV1V2Vo_A04_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("V1V2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		vn = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "vn");
		vl = ReportParseUtil.strToFloatWithIntPostion(columns[1], 1, "vl");

		pha = ReportParseUtil.strToFloat(columns[2],"pha");
		pht = ReportParseUtil.strToFloat(columns[3],"pht");
		vc = ReportParseUtil.strToFloatWithIntPostion(columns[4], 1, "vc");
		vh = ReportParseUtil.strToFloatWithIntPostion(columns[5], 1, "vh");
		evm = columns[6];

		
	}
	
	public Float getPha() {
		return pha;
	}

	public void setPha(Float pha) {
		this.pha = pha;
	}

	public Float getPht() {
		return pht;
	}

	public void setPht(Float pht) {
		this.pht = pht;
	}

	public String getEvm() {
		return evm;
	}

	public void setEvm(String evm) {
		this.evm = evm;
	}

	public Float getVn() {
		return vn;
	}
	public void setVn(Float vn) {
		this.vn = vn;
	}
	public Float getVl() {
		return vl;
	}
	public void setVl(Float vl) {
		this.vl = vl;
	}
	public Float getVc() {
		return vc;
	}
	public void setVc(Float vc) {
		this.vc = vc;
	}
	public Float getVh() {
		return vh;
	}
	public void setVh(Float vh) {
		this.vh = vh;
	}
	
}
