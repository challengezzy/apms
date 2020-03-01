package com.apms.bs.dataprase.vo.a13a14; //

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;


public class AcarsE1Vo_A13_A320 extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String asn; //APU序号
	private Integer ahrs; //APU小时
	private Integer acyc; //APU循环
	private String pfad;//APU性能调整参数
	

	public AcarsE1Vo_A13_A320(String str) throws Exception{
		originalStr = str;
		//logger.debug("E1 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 4);
		if(columns.length < 4){
			throw new Exception("E1行数据应该有4个字段，。。。");
		}
		
		asn = ReportParseUtil.getNumberStr(columns[0]);
		if(asn == null)
			throw new Exception("E1行ASN为空！");
			
		if (columns[1].contains("---") ||  columns[1].contains("XXX")){
			ahrs=null;
		}else if(columns[1].contains("EEE")){
			//throw new Exception("E1行中AHRS小时数据异常，值为EEE！");
			ahrs = 0;
		}else {
			ahrs = (new Integer(columns[1]))*60; //由小时转换为分钟
		}
		
		if(columns[2].contains("EEE")){
			///throw new Exception("E1行中ACYC循环数据异常，值为EEE！");
			acyc = 0;
		}else{
			acyc = ReportParseUtil.strToInteger(columns[2], "ACYC");
		}
		
		pfad = ReportParseUtil.getNumberStr(columns[3]);
			
	}
	
	public Integer getAhrs() {
		return ahrs;
	}
	public void setAhrs(Integer ahrs) {
		this.ahrs = ahrs;
	}
	public String getAsn() {
		return asn;
	}
	public void setAsn(String asn) {
		this.asn = asn;
	}
	public Integer getAcyc() {
		return acyc;
	}
	public void setAcyc(Integer acyc) {
		this.acyc = acyc;
	}
	public String getPfad() {
		return pfad;
	}
	public void setPfad(String pfad) {
		this.pfad = pfad;
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
