package com.apms.bs.dataprase.vo.a07;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsSxVo_A07_A320_IAE extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String n1; 
	private String n2; 
	private String vb1; 
	private String vb2;
	private String pha;
	private String oit;
	private String oip;
	private String psb;
	private String bvp;

	
	public AcarsSxVo_A07_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("N1 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 9);
		n1 = ReportParseUtil.strToStrWithIntPostion(columns[0], 3);
		n2 = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
		vb1 = ReportParseUtil.strToStrWithIntPostion(columns[2], 1);
		vb2 = ReportParseUtil.strToStrWithIntPostion(columns[3], 1);
		pha = ReportParseUtil.getNumberStr(columns[4]);
		oit = ReportParseUtil.getNumberStr(columns[5]);
		oip = ReportParseUtil.getNumberStr(columns[6]);
		psb = ReportParseUtil.getNumberStr(columns[7]);
		bvp = getNumberStr(columns[8]);		
	}
	public static String getNumberStr(String oldStr){
		String newStr;
		if (oldStr == null || oldStr.contains("-") ||  oldStr.contains("X") )
		{ 
			newStr = null;
		}else{
			//如果为负数，字符串中可能包含"N",替换成"-"
			if(oldStr.trim().startsWith("N")){
				oldStr = "-"+oldStr.substring(1);//去年前面的N,加负号
			}
			
			newStr = oldStr.trim();
		}
		
		return newStr;
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
	public String getVb1() {
		return vb1;
	}
	public void setVb1(String vb1) {
		this.vb1 = vb1;
	}
	public String getVb2() {
		return vb2;
	}
	public void setVb2(String vb2) {
		this.vb2 = vb2;
	}
	public String getPha() {
		return pha;
	}
	public void setPha(String pha) {
		this.pha = pha;
	}
	public String getOit() {
		return oit;
	}
	public void setOit(String oit) {
		this.oit = oit;
	}
	public String getOip() {
		return oip;
	}
	public void setOip(String oip) {
		this.oip = oip;
	}
	public String getPsb() {
		return psb;
	}
	public void setPsb(String psb) {
		this.psb = psb;
	}
	public String getBvp() {
		return bvp;
	}
	public void setBvp(String bvp) {
		this.bvp = bvp;
	}

}
