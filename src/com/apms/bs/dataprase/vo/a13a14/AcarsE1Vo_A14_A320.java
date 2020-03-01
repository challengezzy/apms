package com.apms.bs.dataprase.vo.a13a14;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

import smartx.framework.common.vo.NovaLogger;

public class AcarsE1Vo_A14_A320 extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String asn; 
	private Integer ahrs; 
	private Integer acyc; 
	private String pfad;
	private String acw3;
	private String acw4;
	
	private AcarsAcwVo acw3Vo;
	private AcarsAcwVo acw4Vo;
	
	public AcarsE1Vo_A14_A320(String str) throws Exception{
		originalStr = str;
		////logger.debug("Nx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		asn = ReportParseUtil.getNumberStr(columns[0]);
		
		if (columns[1].contains("---") ||  columns[1].contains("XXX")){
			ahrs=null;
		}else if(columns[1].contains("EEE")){
			//throw new Exception("E1行中AHRS小时数据异常，值为EEE！");
			ahrs = 0;
		}else {
			ahrs = (new Integer(columns[1]))*60; //由小时转换为分钟
		}
		
		if(columns[2].contains("EEE")){
			//throw new Exception("E1行中AHRS小时数据异常，值为EEE！");
			acyc = 0;
		}else{
			acyc = ReportParseUtil.strToInteger(columns[2], "ACYC");
		}
		
		
		pfad = ReportParseUtil.getNumberStr(columns[3]);
		
		if (columns[4].contains("---") ||  columns[4].contains("XXX")){
			acw3 = "00000";
		}else {
			acw3 = ReportParseUtil.getNumberStr(columns[4]);			
			acw3Vo = new AcarsAcwVo(ApmsVarConst.CW_ACW3,acw3);
		}
		if (columns[5].contains("---") ||  columns[5].contains("XXX")){
			acw4 = "00000";
		}else {
			acw4 = ReportParseUtil.getNumberStr(columns[5]);			
			acw4Vo = new AcarsAcwVo(ApmsVarConst.CW_ACW4,acw4);
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

	public String getAsn() {
		return asn;
	}

	public void setAsn(String asn) {
		this.asn = asn;
	}
	
	
	public Integer getAhrs() {
		return ahrs;
	}

	public void setAhrs(Integer ahrs) {
		this.ahrs = ahrs;
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

	public String getAcw3() {
		return acw3;
	}

	public void setAcw3(String acw3) {
		this.acw3 = acw3;
	}

	public String getAcw4() {
		return acw4;
	}

	public void setAcw4(String acw4) {
		this.acw4 = acw4;
	}

	public AcarsAcwVo getAcw3Vo() {
		return acw3Vo;
	}

	public void setAcw3Vo(AcarsAcwVo acw3Vo) {
		this.acw3Vo = acw3Vo;
	}

	public AcarsAcwVo getAcw4Vo() {
		return acw4Vo;
	}

	public void setAcw4Vo(AcarsAcwVo acw4Vo) {
		this.acw4Vo = acw4Vo;
	}
	
	

}
