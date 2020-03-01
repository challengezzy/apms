package com.apms.bs.dataprase.vo.a50;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsP1Vo_A50_A320 extends AcarsLineDataVo {
	private Float n1set;
	private Float n1var;
	private Float n1dt;
	private Float tmr;
	private Float vibset;
	
	public AcarsP1Vo_A50_A320(String str,boolean isRep) throws Exception{
		originalStr = str;
		String tempStr = str.trim().substring(2);
		String[] columns; 
	    columns = StringUtil.splitString2Array(tempStr, ",", true);
	    checkFieldsNum(originalStr,columns, 5);

	    if(isRep){
	    	n1set =ReportParseUtil.strToFloat(columns[0], "n1set");
		    n1var =ReportParseUtil.strToFloat(columns[1], "n1var");
		    n1dt =ReportParseUtil.strToFloat(columns[2], "n1dt");
		    tmr =ReportParseUtil.strToFloat(columns[3], "tmr");
		    vibset =ReportParseUtil.strToFloat(columns[4], "vibset");
	    }else{
	    	n1set =ReportParseUtil.strToFloatWithIntPostion(columns[0], 2, "n1set");
		    n1var =ReportParseUtil.strToFloatWithIntPostion(columns[1], 1, "n1var");
		    n1dt =ReportParseUtil.strToFloatWithIntPostion(columns[2], 1, "n1dt");
		    tmr =ReportParseUtil.strToFloat(columns[3], "tmr"); //无小数位
		    vibset =ReportParseUtil.strToFloatWithIntPostion(columns[4], 1, "vibset");
	    }
	    
		
	}

	public Float getN1set() {
		return n1set;
	}

	public void setN1set(Float n1set) {
		this.n1set = n1set;
	}

	public Float getN1var() {
		return n1var;
	}

	public void setN1var(Float n1var) {
		this.n1var = n1var;
	}

	public Float getN1dt() {
		return n1dt;
	}

	public void setN1dt(Float n1dt) {
		this.n1dt = n1dt;
	}

	public Float getTmr() {
		return tmr;
	}

	public void setTmr(Float tmr) {
		this.tmr = tmr;
	}

	public Float getVibset() {
		return vibset;
	}

	public void setVibset(Float vibset) {
		this.vibset = vibset;
	}
}
