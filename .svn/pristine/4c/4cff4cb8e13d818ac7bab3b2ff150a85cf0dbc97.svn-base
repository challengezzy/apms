package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsML_NRVo_A27_A320 extends AcarsLineDataVo{
	private Float qdt0; 
	private Float qdt1; 
	private Float qdt2;
	private Float qdt3;
	private Float qdt4;
	
	public AcarsML_NRVo_A27_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);
		
		if(isRep){
			qdt0 = ReportParseUtil.strToFloat(columns[0], "qdt0");
			qdt0 = ReportParseUtil.strToFloat(columns[1], "qdt1");
			qdt0 = ReportParseUtil.strToFloat(columns[2], "qdt2");
			qdt0 = ReportParseUtil.strToFloat(columns[3], "qdt3");
			qdt0 = ReportParseUtil.strToFloat(columns[4], "qdt4");
		}else{
			qdt0 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "qdt0");
			qdt0 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 1, "qdt1");
			qdt0 = ReportParseUtil.strToFloatWithIntPostion(columns[2], 1, "qdt2");
			qdt0 = ReportParseUtil.strToFloatWithIntPostion(columns[3], 1, "qdt3");
			qdt0 = ReportParseUtil.strToFloatWithIntPostion(columns[4], 1, "qdt4");
		}
		
		
	}

	public Float getQdt0() {
		return qdt0;
	}

	public void setQdt0(Float qdt0) {
		this.qdt0 = qdt0;
	}

	public Float getQdt1() {
		return qdt1;
	}

	public void setQdt1(Float qdt1) {
		this.qdt1 = qdt1;
	}

	public Float getQdt2() {
		return qdt2;
	}

	public void setQdt2(Float qdt2) {
		this.qdt2 = qdt2;
	}

	public Float getQdt3() {
		return qdt3;
	}

	public void setQdt3(Float qdt3) {
		this.qdt3 = qdt3;
	}

	public Float getQdt4() {
		return qdt4;
	}

	public void setQdt4(Float qdt4) {
		this.qdt4 = qdt4;
	}
	
}
