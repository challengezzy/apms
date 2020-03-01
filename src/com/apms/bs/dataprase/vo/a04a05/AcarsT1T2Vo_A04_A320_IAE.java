package com.apms.bs.dataprase.vo.a04a05;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsT1T2Vo_A04_A320_IAE extends AcarsLineDataVo{
	
	private Float p3; 
	private Float t3; 
	private Float gle; 
	private Float p2;
	private Float t2;
	private Float p49;
		
	public AcarsT1T2Vo_A04_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("S1S2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		p3 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "p3");
		t3 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "t3");
		gle = ReportParseUtil.strToFloatWithIntPostion(columns[2], 1, "gle");
		p2 = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2, "p2");
		t2 = ReportParseUtil.strToFloatWithIntPostion(columns[4], 3, "t2");
		p49 = ReportParseUtil.strToFloatWithIntPostion(columns[5], 2, "p49");

		
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
	
	public Float getT3() {
		return t3;
	}

	public void setT3(Float t3) {
		this.t3 = t3;
	}

	public Float getT2() {
		return t2;
	}

	public void setT2(Float t2) {
		this.t2 = t2;
	}

	public Float getP3() {
		return p3;
	}
	public void setP3(Float p3) {
		this.p3 = p3;
	}
	public Float getGle() {
		return gle;
	}
	public void setGle(Float gle) {
		this.gle = gle;
	}
	public Float getP2() {
		return p2;
	}
	public void setP2(Float p2) {
		this.p2 = p2;
	}
	public Float getP49() {
		return p49;
	}
	public void setP49(Float p49) {
		this.p49 = p49;
	}
	

}
