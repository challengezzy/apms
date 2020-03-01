package com.apms.bs.dataprase.vo.a39;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsEC_EEVo_A39_A320_CFM extends AcarsLineDataVo{
	
	private String esn; 
	private String ehrs; 
	private String ecyc;

	
	public AcarsEC_EEVo_A39_A320_CFM(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);
		esn = ReportParseUtil.getNumberStr(columns[0]);
		ehrs = ReportParseUtil.getNumberStr(columns[1]);
		ecyc = ReportParseUtil.getNumberStr(columns[2]);
	}

	public String getEsn() {
		return esn;
	}
	public void setEsn(String esn) {
		this.esn = esn;
	}
	public String getEhrs() {
		return ehrs;
	}
	public void setEhrs(String ehrs) {
		this.ehrs = ehrs;
	}
	public String getEcyc() {
		return ecyc;
	}
	public void setEcyc(String ecyc) {
		this.ecyc = ecyc;
	}
}
