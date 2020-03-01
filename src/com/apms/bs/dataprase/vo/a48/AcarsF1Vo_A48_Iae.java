package com.apms.bs.dataprase.vo.a48;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsF1Vo_A48_Iae extends AcarsLineDataVo {
	
	private int waf;
	private int l;
	private int tk;
	private int xcf;
	
	public AcarsF1Vo_A48_Iae(String str) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
	    checkFieldsNum( originalStr, columns, 4);
	    
	    waf = ReportParseUtil.getBooleanYesStr(columns[0]);
	    l =   ReportParseUtil.getBooleanYesStr(columns[1]);
	    tk =  ReportParseUtil.getBooleanYesStr(columns[2]);
	    xcf = ReportParseUtil.getBooleanYesStr(columns[3]);
	}

	public int getWaf() {
		return waf;
	}

	public void setWaf(int waf) {
		this.waf = waf;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int getTk() {
		return tk;
	}

	public void setTk(int tk) {
		this.tk = tk;
	}

	public int getXcf() {
		return xcf;
	}

	public void setXcf(int xcf) {
		this.xcf = xcf;
	}
	
}
