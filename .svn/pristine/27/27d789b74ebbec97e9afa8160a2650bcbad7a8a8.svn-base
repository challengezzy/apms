package com.apms.bs.dataprase.vo.a06;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsN1Vo_A06_A320_IAE extends AcarsLineDataVo {

	private String e; 
	private String max; 
	private String lim;
	private String ref;
	private String tol;
	private String ttp;
	private String y1;
	private String y2;
	private String para;
	
	public AcarsN1Vo_A06_A320_IAE(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		checkFieldsNum(originalStr,columns, 9);
		
		e = ReportParseUtil.getNumberStr(columns[0]);
		max = ReportParseUtil.getNumberStr(columns[1]);
		lim = ReportParseUtil.getNumberStr(columns[2]);
		ref = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		tol = ReportParseUtil.getNumberStr(columns[4]);
		ttp = ReportParseUtil.getNumberStr(columns[5]);
		y1 = ReportParseUtil.getNumberStr(columns[6]);
		y2 = ReportParseUtil.getNumberStr(columns[7]);
		para = ReportParseUtil.getNumberStr(columns[8]);
	}
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getOriginalStr() {
		return originalStr;
	}
	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e = e;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getLim() {
		return lim;
	}
	public void setLim(String lim) {
		this.lim = lim;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getTol() {
		return tol;
	}
	public void setTol(String tol) {
		this.tol = tol;
	}
	public String getTtp() {
		return ttp;
	}
	public void setTtp(String ttp) {
		this.ttp = ttp;
	}
	public String getY1() {
		return y1;
	}
	public void setY1(String y1) {
		this.y1 = y1;
	}
	public String getY2() {
		return y2;
	}
	public void setY2(String y2) {
		this.y2 = y2;
	}
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}

}
