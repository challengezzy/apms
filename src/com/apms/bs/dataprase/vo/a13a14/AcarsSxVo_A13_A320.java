package com.apms.bs.dataprase.vo.a13a14;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

/**
 * @author Kevin
 *
 */
public class AcarsSxVo_A13_A320 extends AcarsLineDataVo{
private String originalStr;//原始报文，如CE0055,-0379,---,---,6119,272,I71CA2
	
	private Float p2a; 
	private Float lcit; 
	private Float wb; 
	private Float pt; 
	private Float lcdt;
	private Float ota;
	private Float gla;

	
	public AcarsSxVo_A13_A320(String str) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 7);
		p2a = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "p2a");
		lcit = ReportParseUtil.strToFloat(columns[1],"lcit");
		wb = ReportParseUtil.strToFloatWithIntPostion(columns[2], 1, "wb");
		pt = ReportParseUtil.strToFloatWithIntPostion(columns[3], 1, "pt");
		lcdt = ReportParseUtil.strToFloat(columns[4],"lcdt");
		ota = ReportParseUtil.strToFloat(columns[5],"ota");
		gla = ReportParseUtil.strToFloat(columns[6],"gla");
	}


	public String getOriginalStr() {
		return originalStr;
	}


	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}


	public Float getP2a() {
		return p2a;
	}


	public void setP2a(Float p2a) {
		this.p2a = p2a;
	}


	public Float getLcit() {
		return lcit;
	}


	public void setLcit(Float lcit) {
		this.lcit = lcit;
	}


	public Float getWb() {
		return wb;
	}


	public void setWb(Float wb) {
		this.wb = wb;
	}


	public Float getPt() {
		return pt;
	}


	public void setPt(Float pt) {
		this.pt = pt;
	}


	public Float getLcdt() {
		return lcdt;
	}


	public void setLcdt(Float lcdt) {
		this.lcdt = lcdt;
	}


	public Float getOta() {
		return ota;
	}


	public void setOta(Float ota) {
		this.ota = ota;
	}


	public Float getGla() {
		return gla;
	}


	public void setGla(Float gla) {
		this.gla = gla;
	}




}

