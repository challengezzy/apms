package com.apms.bs.dataprase.vo.a15a16;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsEC_EEVo_A15_A320 extends AcarsLineDataVo{
	private String esn; 
	private Integer ehrs; 
	private String ap; 
	private Float flap;
	private Float slat;
	
	public AcarsEC_EEVo_A15_A320(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);
		esn = columns[0];
		ehrs = ReportParseUtil.strToInteger(columns[1],"ehrs");
		ap = columns[2];
		
		flap = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2, "flap");
		slat = ReportParseUtil.strToFloatWithIntPostion(columns[4], 2, "slat");
	}

	public String getEsn() {
		return esn;
	}

	public void setEsn(String esn) {
		this.esn = esn;
	}

	public Integer getEhrs() {
		return ehrs;
	}

	public void setEhrs(Integer ehrs) {
		this.ehrs = ehrs;
	}

	public String getAp() {
		return ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
	}

	public Float getFlap() {
		return flap;
	}

	public void setFlap(Float flap) {
		this.flap = flap;
	}

	public Float getSlat() {
		return slat;
	}

	public void setSlat(Float slat) {
		this.slat = slat;
	}

}
