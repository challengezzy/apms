package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsX1X2Vo_A04_A320_CFM extends AcarsLineDataVo{
	
	private Float pt2; 
	private Float ft; 
	private Float hpt; 
	private Float lpt;
	private Float ralt;
	
	public AcarsX1X2Vo_A04_A320_CFM(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);
		pt2 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 2, "pt2");
		ft = ReportParseUtil.strToFloat(columns[1], "ft");
		hpt = ReportParseUtil.strToFloat(columns[2], "hpt");
		
		lpt = ReportParseUtil.strToFloat(columns[3], "lpt");
		ralt = ReportParseUtil.strToFloat(columns[4], "ralt");
		
	}
	public Float getPt2() {
		return pt2;
	}
	public void setPt2(Float pt2) {
		this.pt2 = pt2;
	}
	public Float getFt() {
		return ft;
	}
	public void setFt(Float ft) {
		this.ft = ft;
	}
	public Float getHpt() {
		return hpt;
	}
	public void setHpt(Float hpt) {
		this.hpt = hpt;
	}
	public Float getLpt() {
		return lpt;
	}
	public void setLpt(Float lpt) {
		this.lpt = lpt;
	}
	public Float getRalt() {
		return ralt;
	}
	public void setRalt(Float ralt) {
		this.ralt = ralt;
	}
	
}
