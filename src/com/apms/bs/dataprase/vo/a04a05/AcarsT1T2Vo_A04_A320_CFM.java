package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsT1T2Vo_A04_A320_CFM extends AcarsLineDataVo{
	
	private Float t3; 
	private Float t25; 
	private Float t12; 
	private Float p0;
	private Float t5;
	private Float vsv;
	private Float vbv;
	
	public AcarsT1T2Vo_A04_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		
		t3 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "t3");
		t25 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "t25");
		t12 = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "t12");
		p0 = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2, "p0");
		t5 = ReportParseUtil.strToFloatWithIntPostion(columns[4], 3, "t5");
		vsv = ReportParseUtil.strToFloatWithIntPostion(columns[5], 2, "vsv");
		vbv = ReportParseUtil.strToFloatWithIntPostion(columns[6], 2, "vbv");
		
	}

	public Float getT3() {
		return t3;
	}

	public void setT3(Float t3) {
		this.t3 = t3;
	}

	public Float getT25() {
		return t25;
	}

	public void setT25(Float t25) {
		this.t25 = t25;
	}

	public Float getT12() {
		return t12;
	}

	public void setT12(Float t12) {
		this.t12 = t12;
	}

	public Float getP0() {
		return p0;
	}

	public void setP0(Float p0) {
		this.p0 = p0;
	}

	public Float getT5() {
		return t5;
	}

	public void setT5(Float t5) {
		this.t5 = t5;
	}

	public Float getVsv() {
		return vsv;
	}

	public void setVsv(Float vsv) {
		this.vsv = vsv;
	}

	public Float getVbv() {
		return vbv;
	}

	public void setVbv(Float vbv) {
		this.vbv = vbv;
	}
	
	
}
