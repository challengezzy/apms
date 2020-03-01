package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsSTVXVo_A05_A320_CFM extends AcarsLineDataVo{
	
	private String n1; 
	private String n1c; 
	private String n2; 
	private String egt;
	private String ff;
	private String vn;
	private String vc;
	private String vh;
	private String vl;
	
	public AcarsSTVXVo_A05_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 8);
		n1 = getNumberStr(columns[0]);
		n1c = getNumberStr(columns[1]);
		n2 = getNumberStr(columns[2]);
		egt = getNumberStr(columns[3]);
		vn = getNumberStr(columns[4]);
		vc = getNumberStr(columns[5]);
		vh = getNumberStr(columns[6]);
		vl = getNumberStr(columns[7]);

		
	}
	private String getNumberStr(String oldStr){
		String newStr;
		if (oldStr.contains("--") ||  oldStr.contains("XX"))
		{ 
			newStr = null;
		}else{
			newStr = oldStr;
		}
		
		return newStr;
	}
	public String getN1() {
		return n1;
	}
	public void setN1(String n1) {
		this.n1 = n1;
	}
	public String getN1c() {
		return n1c;
	}
	public void setN1c(String n1c) {
		this.n1c = n1c;
	}
	public String getN2() {
		return n2;
	}
	public void setN2(String n2) {
		this.n2 = n2;
	}
	public String getEgt() {
		return egt;
	}
	public void setEgt(String egt) {
		this.egt = egt;
	}
	public String getFf() {
		return ff;
	}
	public void setFf(String ff) {
		this.ff = ff;
	}
	public String getVn() {
		return vn;
	}
	public void setVn(String vn) {
		this.vn = vn;
	}
	public String getVc() {
		return vc;
	}
	public void setVc(String vc) {
		this.vc = vc;
	}
	public String getVh() {
		return vh;
	}
	public void setVh(String vh) {
		this.vh = vh;
	}
	public String getVl() {
		return vl;
	}
	public void setVl(String vl) {
		this.vl = vl;
	}

	
}
