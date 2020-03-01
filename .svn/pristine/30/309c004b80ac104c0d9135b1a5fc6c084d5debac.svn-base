package com.apms.bs.dataprase.vo.a10;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsSxVo_A10_A320_CFM extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String n1; 
	private String n2; 
	private String egt; 
	private String ff;
	private String fmv;
	private String t25;
	private String pd;

	
	public AcarsSxVo_A10_A320_CFM(String str) throws Exception{
		originalStr = str;
		//logger.debug("Sx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		n1 = ReportParseUtil.strToStrWithIntPostion(columns[0], 3);;
		n2 = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);;
		egt = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);;
		ff = ReportParseUtil.getNumberStr(columns[3]);
		fmv = ReportParseUtil.getNumberStr(columns[4]);
		t25 = ReportParseUtil.strToStrWithIntPostion(columns[5], 3);;
		pd = ReportParseUtil.getNumberStr(columns[6]);
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
	public String getEgt() {
		return egt;
	}
	public void setEgt(String egt) {
		this.egt = egt;
	}
	public String getN1() {
		return n1;
	}
	public void setN1(String n1) {
		this.n1 = n1;
	}
	public String getN2() {
		return n2;
	}
	public void setN2(String n2) {
		this.n2 = n2;
	}
	public String getFf() {
		return ff;
	}
	public void setFf(String ff) {
		this.ff = ff;
	}
	public String getT25() {
		return t25;
	}
	public void setT25(String t25) {
		this.t25 = t25;
	}
	public String getPd() {
		return pd;
	}
	public void setPd(String pd) {
		this.pd = pd;
	}
	public String getFmv() {
		return fmv;
	}
	public void setFmv(String fmv) {
		this.fmv = fmv;
	}


}
