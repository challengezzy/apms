package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsCL_CRVo_A27_A320 extends AcarsLineDataVo{
	
	private Float cal_g_a; 
	private Float cal_air; 
	private Float oiqav;
	private Float oiqdt;
	private Float oiqcnt;
	
	public AcarsCL_CRVo_A27_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		//logger.debug("CL_CR content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);
		
		if(isRep){
			cal_g_a = ReportParseUtil.strToFloat(columns[0], "cal_g_a");
			cal_air = ReportParseUtil.strToFloat(columns[1], "cal_air");
			oiqav = ReportParseUtil.strToFloat(columns[2], "oiqav");
			oiqdt = ReportParseUtil.strToFloat(columns[3], "oiqdt");
		}else{
			cal_g_a = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1,"cal_g_a");
			cal_air = ReportParseUtil.strToFloatWithIntPostion(columns[1], 1,"cal_air");
			oiqav = ReportParseUtil.strToFloatWithIntPostion(columns[2], 2,"oiqav");
			oiqdt = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2,"oiqdt");
		}
		
		
		if (columns[4].contains("-") ||  columns[4].contains("X")){
			oiqcnt = 0f;
		}else {
			oiqcnt = ReportParseUtil.strToFloat(columns[4], "oiqcnt");
		}

	}
	
	public Float getCal_g_a() {
		return cal_g_a;
	}

	public void setCal_g_a(Float cal_g_a) {
		this.cal_g_a = cal_g_a;
	}

	public Float getCal_air() {
		return cal_air;
	}

	public void setCal_air(Float cal_air) {
		this.cal_air = cal_air;
	}

	public Float getOiqav() {
		return oiqav;
	}

	public void setOiqav(Float oiqav) {
		this.oiqav = oiqav;
	}

	public Float getOiqdt() {
		return oiqdt;
	}

	public void setOiqdt(Float oiqdt) {
		this.oiqdt = oiqdt;
	}

	public Float getOiqcnt() {
		return oiqcnt;
	}

	public void setOiqcnt(Float oiqcnt) {
		this.oiqcnt = oiqcnt;
	}

}
