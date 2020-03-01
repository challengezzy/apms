package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsECVo_A19_A320 extends AcarsLineDataVo{
	private String esn;
	private Float ehrs;
	private Float ecyc;
	private String ap;
	private Float y1;
	private Float nl;
	
	public AcarsECVo_A19_A320(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(3);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);

		esn = columns[0];
		ehrs = ReportParseUtil.strToFloat(columns[1], "ehrs");
		ecyc = ReportParseUtil.strToFloat(columns[2], "ecyc");
		ap = columns[3];
		y1 = ReportParseUtil.strToFloat(columns[4], "y1");
		nl = ReportParseUtil.strToFloat(columns[5], "nl");
		
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

	public String getAp() {
		return ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
	}

	public Float getY1() {
		return y1;
	}

	public void setY1(Float y1) {
		this.y1 = y1;
	}

	public Float getNl() {
		return nl;
	}

	public void setNl(Float nl) {
		this.nl = nl;
	}

}
