package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsX4X5Vo_A02_A320 extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	

	private String rsp2;
	private String rsp3;
	private String rsp4;
	private String rsp5;
	private Float flap;
	private Float slat;
	
	public AcarsX4X5Vo_A02_A320(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		rsp2=ReportParseUtil.strToStrWithIntPostion(columns[0], 3);
		rsp3=ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		rsp4=ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		rsp5=ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		flap=ReportParseUtil.strToFloatWithIntPostion(columns[4], 3);
		slat=ReportParseUtil.strToFloatWithIntPostion(columns[5], 3);
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
	public String getRsp2() {
		return rsp2;
	}
	public void setRsp2(String rsp2) {
		this.rsp2 = rsp2;
	}
	public String getRsp3() {
		return rsp3;
	}
	public void setRsp3(String rsp3) {
		this.rsp3 = rsp3;
	}
	public String getRsp4() {
		return rsp4;
	}
	public void setRsp4(String rsp4) {
		this.rsp4 = rsp4;
	}
	public String getRsp5() {
		return rsp5;
	}
	public void setRsp5(String rsp5) {
		this.rsp5 = rsp5;
	}

	public Float getFlap() {
		return flap;
	}

	public void setFlap(Float flap) {
		this.flap = flap;
	}

	public Float getSlat() {
		return slat;
	}

	public void setSlat(Float slat) {
		this.slat = slat;
	}

	

	
	
}
