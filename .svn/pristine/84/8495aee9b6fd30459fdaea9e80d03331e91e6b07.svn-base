package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

import smartx.framework.common.vo.NovaLogger;

public class AcarsS1S2Vo_A01A02_A320_CFM extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;// 原始报文，如E12779,09243,08598,20000

	private String p25;
	private String t25;
	private String p3;
	private String t3;
	private String t5;
	private String vsv;
	private String vbv;

	public AcarsS1S2Vo_A01A02_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);

		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 7);
		p25 = ReportParseUtil.strToStrWithIntPostion(columns[0], 2);
		t25 = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		p3 = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		t3 = ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		t5 = ReportParseUtil.strToStrWithIntPostion(columns[4], 3);
		vsv = ReportParseUtil.strToStrWithIntPostion(columns[5], 2);
		vbv = ReportParseUtil.strToStrWithIntPostion(columns[6], 2);
	}


	public String getP25() {
		return p25;
	}

	public void setP25(String p25) {
		this.p25 = p25;
	}

	public String getT25() {
		return t25;
	}

	public void setT25(String t25) {
		this.t25 = t25;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public String getT3() {
		return t3;
	}

	public void setT3(String t3) {
		this.t3 = t3;
	}

	public String getT5() {
		return t5;
	}

	public void setT5(String t5) {
		this.t5 = t5;
	}

	public String getVsv() {
		return vsv;
	}

	public void setVsv(String vsv) {
		this.vsv = vsv;
	}

	public String getVbv() {
		return vbv;
	}

	public void setVbv(String vbv) {
		this.vbv = vbv;
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

}
