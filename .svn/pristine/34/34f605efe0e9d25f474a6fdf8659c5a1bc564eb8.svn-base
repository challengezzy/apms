package com.apms.bs.dataprase.vo.a13a14;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

import smartx.framework.common.vo.NovaLogger;

public class AcarsE1Vo_R14_A330_A340 {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String asn; 
	private Integer ahrs; 
	private Integer acyc; 
	private String ecid;
	private String acw2;
	private String acw3;
	
	private AcarsAcwVo acw2Vo;
	private AcarsAcwVo acw3Vo;
	
	public AcarsE1Vo_R14_A330_A340(String str, String acmodel) throws Exception{
		String tempStr;
		originalStr = str;
//		//logger.debug("Nx content: " + originalStr);
//		if (Acmodel.equals("A330")){
//			tempStr = str.replace("\r", "").trim().substring(3);
//		} else {
//			tempStr = str.replace("\r", "").trim();
//		}
		//在外面区分330和 340的差异,zhangzy 20141030
		tempStr = str.replace("\r", "").trim();

		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		
		asn = columns[0];
		
		ahrs = ReportParseUtil.strToInteger(columns[1], "AHRS") * 60; //小时转换为分钟
		acyc = ReportParseUtil.strToInteger(columns[2], "ACYC");
		
		ecid = ReportParseUtil.getNumberStr(columns[3]);
		acw2 = columns[4];
		acw3 = columns[5];
		
		acw2Vo = new AcarsAcwVo(ApmsVarConst.CW_ACW2,acw2);
		acw3Vo = new AcarsAcwVo(ApmsVarConst.CW_ACW3,acw3);
		            
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

	public String getEcid() {
		return ecid;
	}

	public void setEcid(String ecid) {
		this.ecid = ecid;
	}

	public String getAcw2() {
		return acw2;
	}

	public void setAcw2(String acw2) {
		this.acw2 = acw2;
	}

	public String getAcw3() {
		return acw3;
	}

	public void setAcw3(String acw3) {
		this.acw3 = acw3;
	}

	public AcarsAcwVo getAcw2Vo() {
		return acw2Vo;
	}

	public void setAcw2Vo(AcarsAcwVo acw2Vo) {
		this.acw2Vo = acw2Vo;
	}

	public AcarsAcwVo getAcw3Vo() {
		return acw3Vo;
	}

	public void setAcw3Vo(AcarsAcwVo acw3Vo) {
		this.acw3Vo = acw3Vo;
	}


	
	

}
