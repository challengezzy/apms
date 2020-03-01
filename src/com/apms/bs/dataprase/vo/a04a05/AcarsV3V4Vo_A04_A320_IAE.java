package com.apms.bs.dataprase.vo.a04a05;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV3V4Vo_A04_A320_IAE extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private Float vb1; 
	private Float vb2; 
	private Float pha; 
	private Float psb;
	private Float tla;
	private String bvp;
	
	
	public AcarsV3V4Vo_A04_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("S1S2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		vb1 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 2, "vb1");
		vb2 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 2, "vb2");
		pha = ReportParseUtil.strToFloat(columns[2], "pha");
		psb = ReportParseUtil.strToFloat(columns[3], "psb");
		
		tla = ReportParseUtil.strToFloatWithIntPostion(columns[4], 3, "tla");
	
		bvp = getNumberStr(columns[5]);
		

		
	}
	private String getNumberStr(String oldStr){
		String newStr;
		if (oldStr.contains("---") ||  oldStr.contains("XXX"))
		{ 
			newStr = null;
		}else{
			newStr = oldStr;
		}
		
		return newStr;
	}
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getOriginalStr() {
		return originalStr;
	}
	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
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
	public Float getPsb() {
		return psb;
	}
	public void setPsb(Float psb) {
		this.psb = psb;
	}
	public Float getTla() {
		return tla;
	}
	public void setTla(Float tla) {
		this.tla = tla;
	}
	public String getBvp() {
		return bvp;
	}
	public void setBvp(String bvp) {
		this.bvp = bvp;
	}

}
