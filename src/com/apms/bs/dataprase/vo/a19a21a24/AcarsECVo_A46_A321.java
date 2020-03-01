package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsECVo_A46_A321 extends AcarsLineDataVo {
	private String esn; //
	private Float ehrs; //
	private Float ecyc; //
	private Float per;  //
	
	public AcarsECVo_A46_A321(String str,boolean isRep) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns; 
	    columns = StringUtil.splitString2Array(tempStr, ",", true);
	    checkFieldsNum(originalStr,columns, 4);
	    
	    esn = columns[0];
		ehrs = ReportParseUtil.strToFloat(columns[1], "ehrs");
		ecyc = ReportParseUtil.strToFloat(columns[2], "ecyc");
		
		if(isRep){
			per = ReportParseUtil.strToFloat(columns[3], "per");
		}else{
			per = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2, "per");
		}
	}

	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public Float getEhrs() {
		return ehrs;
	}
	public void setEhrs(Float ehrs) {
		this.ehrs = ehrs;
	}
	public Float getEcyc() {
		return ecyc;
	}
	public void setEcyc(Float ecyc) {
		this.ecyc = ecyc;
	}
	public Float getPer() {
		return per;
	}
	public void setPer(Float per) {
		this.per = per;
	}
}
