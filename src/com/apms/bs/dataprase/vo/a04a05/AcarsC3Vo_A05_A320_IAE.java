package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsC3Vo_A05_A320_IAE extends AcarsLineDataVo{
	
	private String tat;
	private String alt;
	private String mn;
	private String bleedstatus;
	private String apu;
	
	public AcarsC3Vo_A05_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 9);
		tat = ReportParseUtil.strToStrWithIntPostion(columns[0], 3);
		alt = ReportParseUtil.getNumberStr(columns[1]);
		mn= ReportParseUtil.getNumberStr(columns[2]);
		bleedstatus =columns[3]+columns[4]+columns[5]+columns[6]+columns[7];
		apu = ReportParseUtil.getNumberStr(columns[8]);
	}
	
	public String getTat() {
		return tat;
	}
	public void setTat(String tat) {
		this.tat = tat;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getMn() {
		return mn;
	}
	public void setMn(String mn) {
		this.mn = mn;
	}
	public String getBleedstatus() {
		return bleedstatus;
	}
	public void setBleedstatus(String bleedstatus) {
		this.bleedstatus = bleedstatus;
	}
	public String getApu() {
		return apu;
	}
	public void setApu(String apu) {
		this.apu = apu;
	}
	

}
