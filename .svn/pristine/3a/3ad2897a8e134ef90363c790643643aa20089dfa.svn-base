package com.apms.bs.dataprase.vo.a13a14;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

import smartx.framework.common.vo.NovaLogger;

public class AcarsNxVo_A14_A320 extends AcarsLineDataVo{
	private String originalStr;
	
	private Float na; //
	private Float egta; //
	private Float ota; //
	private Float igv; //
	private Float wb; //
	private Float lcit; //
	private Float pt; //
	private Float gla; //
	
	public AcarsNxVo_A14_A320(String str) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 8);
		if (columns[0].contains("---")||columns[0].contains("XXX"))
		{ 
			na = 0f;
		}else{
			na = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "na");
			//na = new Integer(columns[0].substring(0,3))+"." + columns[0].substring(3,columns[0].length());
		}
		egta = ReportParseUtil.strToFloat(columns[1] ,"egta");
		ota = ReportParseUtil.strToFloat(columns[2] ,"ota");
		igv = ReportParseUtil.strToFloat(columns[3] ,"igv");
		wb = ReportParseUtil.strToFloat(columns[4] ,"wb");
		lcit = ReportParseUtil.strToFloat(columns[5] ,"lcit");
		if (columns[6].contains("---")||columns[6].contains("XXX"))
		{ 
			pt = null;
		}else{
			pt = ReportParseUtil.strToFloatWithIntPostion(columns[6], 1, "pt");
			//pt = new Integer(columns[6].substring(0,1))+"." + columns[6].substring(1,columns[6].length());
		}
		gla = ReportParseUtil.strToFloat(columns[7] ,"gla");
		
	}
	
	public String getOriginalStr() {
		return originalStr;
	}

	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}

	public Float getNa() {
		return na;
	}

	public void setNa(Float na) {
		this.na = na;
	}

	public Float getEgta() {
		return egta;
	}

	public void setEgta(Float egta) {
		this.egta = egta;
	}

	public Float getOta() {
		return ota;
	}

	public void setOta(Float ota) {
		this.ota = ota;
	}

	public Float getIgv() {
		return igv;
	}

	public void setIgv(Float igv) {
		this.igv = igv;
	}

	public Float getWb() {
		return wb;
	}

	public void setWb(Float wb) {
		this.wb = wb;
	}

	public Float getLcit() {
		return lcit;
	}

	public void setLcit(Float lcit) {
		this.lcit = lcit;
	}

	public Float getPt() {
		return pt;
	}

	public void setPt(Float pt) {
		this.pt = pt;
	}

	public Float getGla() {
		return gla;
	}

	public void setGla(Float gla) {
		this.gla = gla;
	}

	
}
