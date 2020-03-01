package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsX3Vo_A02_A320 extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	

	private String rudd;
	private String rudt;
	private String aill;
	private String ailr;
	private String stab;
	private String roll;
	private String yaw;
	
	public AcarsX3Vo_A02_A320(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		rudd=ReportParseUtil.strToStrWithIntPostion(columns[0], 3);
		rudt=ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		aill=ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
		ailr=ReportParseUtil.strToStrWithIntPostion(columns[3], 3);
		stab=ReportParseUtil.strToStrWithIntPostion(columns[4], 3);
		roll=ReportParseUtil.strToStrWithIntPostion(columns[5], 3);
		yaw=ReportParseUtil.strToStrWithIntPostion(columns[6], 3);
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
	public String getRudd() {
		return rudd;
	}
	public void setRudd(String rudd) {
		this.rudd = rudd;
	}
	public String getRudt() {
		return rudt;
	}
	public void setRudt(String rudt) {
		this.rudt = rudt;
	}
	public String getAill() {
		return aill;
	}
	public void setAill(String aill) {
		this.aill = aill;
	}
	public String getAilr() {
		return ailr;
	}
	public void setAilr(String ailr) {
		this.ailr = ailr;
	}
	public String getStab() {
		return stab;
	}
	public void setStab(String stab) {
		this.stab = stab;
	}
	public String getRoll() {
		return roll;
	}
	public void setRoll(String roll) {
		this.roll = roll;
	}
	public String getYaw() {
		return yaw;
	}
	public void setYaw(String yaw) {
		this.yaw = yaw;
	}
	
	
	
}
