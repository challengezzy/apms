package com.apms.bs.dataprase.vo.a48;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsL2Vo_A48_Iae extends AcarsLineDataVo {
	private String t25;
	private String p25;
	private String p3;
	private String t3;
	private String p49;
	private String sva;
	private String ctl;
	
	public AcarsL2Vo_A48_Iae(String str) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
	    checkFieldsNum(originalStr,columns, 3);
	    
	    t25 = ReportParseUtil.strToStrWithIntPostion(columns[0], 3);
	    p25 = ReportParseUtil.strToStrWithIntPostion(columns[1], 2);
		p3 = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		t3 = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		p49 = ReportParseUtil.strToStrWithIntPostion(columns[4], 2);
		sva = ReportParseUtil.getNumberStr(columns[5]);
		ctl = columns[6];
		
	}

	public String getT25() {
		return t25;
	}

	public void setT25(String t25) {
		this.t25 = t25;
	}

	public String getP25() {
		return p25;
	}

	public void setP25(String p25) {
		this.p25 = p25;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public String getT3() {
		return t3;
	}

	public void setT3(String t3) {
		this.t3 = t3;
	}

	public String getP49() {
		return p49;
	}

	public void setP49(String p49) {
		this.p49 = p49;
	}

	public String getSva() {
		return sva;
	}

	public void setSva(String sva) {
		this.sva = sva;
	}

	public String getCtl() {
		return ctl;
	}

	public void setCtl(String ctl) {
		this.ctl = ctl;
	}

}
