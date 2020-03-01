package com.apms.bs.dataprase.vo.a49;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsL3Vo_A49_A320 extends AcarsLineDataVo {
	
	private Float epr;
	private Float n1; 
	private Float n2; 
	private Float ff;
	private Float egt;
	private Date time;
	
	public AcarsL3Vo_A49_A320(String str,String transdate,boolean isRep) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		
		if(isRep){
			epr =ReportParseUtil.strToFloat(columns[0], "epr");
			n1 = ReportParseUtil.strToFloat(columns[1], "n1");
			n2 = ReportParseUtil.strToFloat(columns[2], "n2");
		}else{
			epr =ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "epr");
			n1 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "n1");
			n2 = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "n2");
		}
		
		ff = ReportParseUtil.strToFloat(columns[3],"ff");
		egt = ReportParseUtil.strToFloat(columns[4], "egt" );
		
		String timestr = columns[5];
		time = DateUtil.StringToDate(transdate.substring(0, 10) +" "+ timestr, "yyyy-MM-dd HHmmss");
	}
	public Float getN1() {
		return n1;
	}
	public void setN1(Float n1) {
		this.n1 = n1;
	}
	
	public Float getEgt() {
		return egt;
	}
	public void setEgt(Float egt) {
		this.egt = egt;
	}
	public Float getFf() {
		return ff;
	}
	public void setFf(Float ff) {
		this.ff = ff;
	}
	public Float getN2() {
		return n2;
	}
	public void setN2(Float n2) {
		this.n2 = n2;
	}
	public Float getEpr() {
		return epr;
	}
	public void setEpr(Float epr) {
		this.epr = epr;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

}
