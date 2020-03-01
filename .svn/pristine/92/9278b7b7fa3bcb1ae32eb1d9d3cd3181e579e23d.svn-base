package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV1V2Vo_A04_A320_IAE extends AcarsLineDataVo{
	
	private String ecw1; 
	private String ecw2; 
	private String evm; 
	private Float oip;
	private Float oit;
	private Float ft;
	private String o;
	private String f;
	
	public AcarsV1V2Vo_A04_A320_IAE(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 8);
		ecw1 = columns[0];
		ecw2 = columns[1];
		evm = columns[2];
		oip = ReportParseUtil.strToFloat(columns[3] ,"oip" );
		oit = ReportParseUtil.strToFloat(columns[4] ,"oit" );
		ft = ReportParseUtil.strToFloat(columns[5] ,"ft" );
		
		o = columns[6];
		f = columns[7];

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

	public String getEvm() {
		return evm;
	}

	public void setEvm(String evm) {
		this.evm = evm;
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

	public Float getFt() {
		return ft;
	}

	public void setFt(Float ft) {
		this.ft = ft;
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

}
