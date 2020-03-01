package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsX1X2Vo_A02_A320 extends AcarsLineDataVo{

	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;// 原始报文，如E12779,09243,08598,20000

	private String wfq;
	private String elev;
	private String aoa;
	private String slp;
	private String cfpg;
	private String civv;

	public AcarsX1X2Vo_A02_A320(String str) throws Exception {
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);

		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 6);
		wfq=ReportParseUtil.getNumberStr(columns[0]);
		elev=ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		aoa=ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		slp=ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		cfpg=ReportParseUtil.strToStrWithIntPostion(columns[4], 2);
		civv = ReportParseUtil.getNumberStr(columns[5]);
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

	public String getWfq() {
		return wfq;
	}

	public void setWfq(String wfq) {
		this.wfq = wfq;
	}

	public String getElev() {
		return elev;
	}

	public void setElev(String elev) {
		this.elev = elev;
	}

	public String getAoa() {
		return aoa;
	}

	public void setAoa(String aoa) {
		this.aoa = aoa;
	}

	public String getSlp() {
		return slp;
	}

	public void setSlp(String slp) {
		this.slp = slp;
	}

	public String getCfpg() {
		return cfpg;
	}

	public void setCfpg(String cfpg) {
		this.cfpg = cfpg;
	}

	public String getCivv() {
		return civv;
	}

	public void setCivv(String civv) {
		this.civv = civv;
	}

}
