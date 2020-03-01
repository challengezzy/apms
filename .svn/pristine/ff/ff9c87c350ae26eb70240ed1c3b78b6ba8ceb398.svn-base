package com.apms.bs.dataprase.vo.a39;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsS2Vo_A39_A320_CFM extends AcarsLineDataVo{
	
	private Double ff1; 
	private Double ff2; 
	private Double n21;
	private Double n22;
	private Double pd1;
	private Double pd2;
	
	public AcarsS2Vo_A39_A320_CFM(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);
		
		ff1 = new Double(ReportParseUtil.strToStrWithIntPostion(columns[0], 5));
		ff2 = new Double(ReportParseUtil.strToStrWithIntPostion(columns[1],5));
		n21 = new Double(ReportParseUtil.strToStrWithIntPostion(columns[2],2));
		n22 = new Double(ReportParseUtil.strToStrWithIntPostion(columns[3],2));
		pd1 = new Double(ReportParseUtil.strToStrWithIntPostion(columns[4],2));
		pd2 = new Double(ReportParseUtil.strToStrWithIntPostion(columns[5],2));
	}

	public Double getFf1() {
		return ff1;
	}

	public void setFf1(Double ff1) {
		this.ff1 = ff1;
	}

	public Double getFf2() {
		return ff2;
	}

	public void setFf2(Double ff2) {
		this.ff2 = ff2;
	}

	public Double getN21() {
		return n21;
	}

	public void setN21(Double n21) {
		this.n21 = n21;
	}

	public Double getN22() {
		return n22;
	}

	public void setN22(Double n22) {
		this.n22 = n22;
	}

	public Double getPd1() {
		return pd1;
	}

	public void setPd1(Double pd1) {
		this.pd1 = pd1;
	}

	public Double getPd2() {
		return pd2;
	}

	public void setPd2(Double pd2) {
		this.pd2 = pd2;
	}

}
