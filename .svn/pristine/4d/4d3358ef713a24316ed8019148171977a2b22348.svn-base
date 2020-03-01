package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsCF_CGVo_A27_A320 extends AcarsLineDataVo{
	private String esn; //
	private Float ehrs; //
	private Float ecyc; //

	
	public AcarsCF_CGVo_A27_A320(String str) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);
		esn = ReportParseUtil.getNumberStr(columns[0]);

		ehrs = ReportParseUtil.strToFloat(columns[1], "ehrs");
		ecyc = ReportParseUtil.strToFloat(columns[2], "ecyc");
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

}
