package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV3_V4Vo_A26_A320 extends AcarsLineDataVo{
	
	private Float vb1; 
	private Float vb2; 
	private Float pha;
	private Float oik_480;
	private Float oik_0;
	
	public AcarsV3_V4Vo_A26_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);
		
		vb1 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 2, "vb1");
		vb2 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 2, "vb2");
		pha = ReportParseUtil.strToFloat(columns[2], "pha");
		
		if(isRep){
			oik_480 = ReportParseUtil.strToFloat(columns[3], "oik_480");
			oik_0 = ReportParseUtil.strToFloat(columns[4], "oik_0");
		}else{
			oik_480 = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2, "oik_480");
			oik_0 = ReportParseUtil.strToFloatWithIntPostion(columns[4], 2, "oik_0");
		}
		
	
	}
	
	public AcarsV3_V4Vo_A26_A320(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);
		
		vb1 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 2, "vb1");
		vb2 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 2, "vb2");
		pha = ReportParseUtil.strToFloat(columns[2], "pha");
		
	
	}

	public Float getVb1() {
		return vb1;
	}

	public void setVb1(Float vb1) {
		this.vb1 = vb1;
	}

	public Float getVb2() {
		return vb2;
	}

	public void setVb2(Float vb2) {
		this.vb2 = vb2;
	}

	public Float getPha() {
		return pha;
	}

	public void setPha(Float pha) {
		this.pha = pha;
	}

	public Float getOik_480() {
		return oik_480;
	}

	public void setOik_480(Float oik_480) {
		this.oik_480 = oik_480;
	}

	public Float getOik_0() {
		return oik_0;
	}

	public void setOik_0(Float oik_0) {
		this.oik_0 = oik_0;
	}
	
	
}
