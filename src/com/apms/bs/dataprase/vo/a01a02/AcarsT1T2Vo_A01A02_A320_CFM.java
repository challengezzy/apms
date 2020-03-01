package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsT1T2Vo_A01A02_A320_CFM extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	private String hpt;
	private String lpt;
	private String gle;
	private String pd;
	private String tn;
	private String pt2;
	private String oiqh;
	
	public AcarsT1T2Vo_A01A02_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		hpt = ReportParseUtil.getNumberStr(columns[0]);
		lpt = ReportParseUtil.getNumberStr(columns[1]);
		gle = ReportParseUtil.getNumberStr(columns[2]);
		pd =  ReportParseUtil.getNumberStr(columns[3]);
		tn = ReportParseUtil.getNumberStr(columns[4]);
		pt2 = ReportParseUtil.strToStrWithIntPostion(columns[5], 2);
		oiqh = ReportParseUtil.strToStrWithIntPostion(columns[6], 2);
	}	
	public String getPt2() {
		return pt2;
	}
	public void setPt2(String pt2) {
		this.pt2 = pt2;
	}
	public String getOiqh() {
		return oiqh;
	}
	public void setOiqh(String oiqh) {
		this.oiqh = oiqh;
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
	public String getHpt() {
		return hpt;
	}
	public void setHpt(String hpt) {
		this.hpt = hpt;
	}
	public String getLpt() {
		return lpt;
	}
	public void setLpt(String lpt) {
		this.lpt = lpt;
	}
	public String getGle() {
		return gle;
	}
	public void setGle(String gle) {
		this.gle = gle;
	}
	public String getPd() {
		return pd;
	}
	public void setPd(String pd) {
		this.pd = pd;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	

}
