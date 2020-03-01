package com.apms.bs.dataprase.vo.a34;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsS246Vo_A34_A320 extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String pd; 
	private String tpo; 
	private String wav;
	
	public AcarsS246Vo_A34_A320(String str) throws Exception{
		originalStr = str.substring(2);
		//logger.debug("S246 content: " + originalStr);
		
		String tempStr = originalStr;
		if (originalStr.startsWith(",")){
			tempStr = str.substring(3);
		}
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);
		pd = ReportParseUtil.getNumberStr(columns[0]);
		tpo = ReportParseUtil.getNumberStr(columns[1]);
		//wav = ParseUtil.getNumberStr(columns[2]);
		if (columns[2].contains("---")||columns[2].contains("XXX"))
		{ 
			wav=null;
		}else if(columns[2].contains("OPEN")){
			wav = "1";//new Integer(columns[1]).toString();
		}else {
			wav = "0";
		}
		
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
	public String getPd() {
		return pd;
	}
	public void setPd(String pd) {
		this.pd = pd;
	}
	public String getTpo() {
		return tpo;
	}
	public void setTpo(String tpo) {
		this.tpo = tpo;
	}
	public String getWav() {
		return wav;
	}
	public void setWav(String wav) {
		this.wav = wav;
	}

}
