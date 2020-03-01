package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsELVo_A27_A320 extends AcarsLineDataVo{
	
	private Float qt; 
	private Float fh; 
	private Float oilcal;
	private String employid;
	
	public AcarsELVo_A27_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 4);
		
		if(isRep){
			qt = ReportParseUtil.strToFloat(columns[0], "qt");
			fh = ReportParseUtil.strToFloat(columns[1], "fh");
			oilcal = ReportParseUtil.strToFloat(columns[2], "oilcal");
		}else{
			qt = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1,"qt");
			fh = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3,"fh");
			oilcal = ReportParseUtil.strToFloatWithIntPostion(columns[2], 1,"oilcal");
		}
		
		if (columns[3].contains("---") || columns[3].contains("XXX")) {
			employid = null;
		} else {
			employid = columns[3];
		}
	}
	
	public String getEmployid() {
		return employid;
	}

	public void setEmployid(String employid) {
		this.employid = employid;
	}

	public Float getQt() {
		return qt;
	}

	public void setQt(Float qt) {
		this.qt = qt;
	}

	public Float getFh() {
		return fh;
	}

	public void setFh(Float fh) {
		this.fh = fh;
	}

	public Float getOilcal() {
		return oilcal;
	}

	public void setOilcal(Float oilcal) {
		this.oilcal = oilcal;
	}
	
}
