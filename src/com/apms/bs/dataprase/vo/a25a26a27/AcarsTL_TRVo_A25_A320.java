package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsTL_TRVo_A25_A320 extends AcarsLineDataVo{
	
	private Float ehr_g_a; //
	private Float ehr_air; //
	
	public AcarsTL_TRVo_A25_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		logger.debug("TL_TR content: " + originalStr);
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 2);
		
		if(isRep){
			ehr_g_a = ReportParseUtil.strToFloat(columns[0], "ehr_g_a");
			ehr_air = ReportParseUtil.strToFloat(columns[1], "ehr_air");
		}else{
			ehr_g_a = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3,"ehr_g_a");
			ehr_air = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3,"ehr_air");
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(ReportParseUtil.strToFloat("036.22", "ehr_g_a"));
	}

	public Float getEhr_g_a() {
		return ehr_g_a;
	}

	public void setEhr_g_a(Float ehr_g_a) {
		this.ehr_g_a = ehr_g_a;
	}

	public Float getEhr_air() {
		return ehr_air;
	}

	public void setEhr_air(Float ehr_air) {
		this.ehr_air = ehr_air;
	}

}
