package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV3V4Vo_A04_A320_CFM extends AcarsLineDataVo{
	
	private Float oip; 
	private Float oit; 
	private String o; 
	private String f;
	private String ecw1;
	private String ecw2;
	private String psel;
	
	public AcarsV3V4Vo_A04_A320_CFM(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		
		oip = ReportParseUtil.strToFloat(columns[0] ,"oip" );
		oit = ReportParseUtil.strToFloat(columns[1] ,"oit" );
		o = columns[2];
		f = columns[3];
		ecw1 = columns[4];
		ecw2 = columns[5];
		psel = columns[6];

	}
	
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o = o;
	}
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public String getEcw1() {
		return ecw1;
	}
	public void setEcw1(String ecw1) {
		this.ecw1 = ecw1;
	}
	public String getEcw2() {
		return ecw2;
	}
	public void setEcw2(String ecw2) {
		this.ecw2 = ecw2;
	}
	public String getPsel() {
		return psel;
	}
	public void setPsel(String psel) {
		this.psel = psel;
	}

	public Float getOip() {
		return oip;
	}

	public void setOip(Float oip) {
		this.oip = oip;
	}

	public Float getOit() {
		return oit;
	}

	public void setOit(Float oit) {
		this.oit = oit;
	}

	
}
