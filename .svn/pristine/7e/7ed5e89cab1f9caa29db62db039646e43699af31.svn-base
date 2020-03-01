package com.apms.bs.dataprase.vo.a28a32;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsEC_EEVo_A28A32_A320 extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String esn; //
	private String ehrs; //
	private String ecyc; //
	private String ap;
	
	public AcarsEC_EEVo_A28A32_A320(String str) throws Exception{
		originalStr = str;
		//logger.debug("Nx content: " + originalStr);
		
		String tempStr1 = str.substring(2);
		String columns[];
		if (tempStr1.startsWith(",")){
			String tempStr= tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
			}
		else if (tempStr1.startsWith(" ")){
			String tempStr= tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, " ", true);
		} else{
			String tempStr= tempStr1;
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		}
		checkFieldsNum(originalStr,columns, 4);
		esn = ReportParseUtil.getNumberStr(columns[0 ]);
		ehrs = ReportParseUtil.getNumberStr(columns[1]);
		ecyc = ReportParseUtil.getNumberStr(columns[2]);
		if (columns[3].contains("--")||columns[3].contains("XX"))
		{ 
			ap=null;
		}else{
			ap = columns[3];
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

	public String getEsn() {
		return esn;
	}

	public void setEsn(String esn) {
		this.esn = esn;
	}

	public String getEhrs() {
		return ehrs;
	}

	public void setEhrs(String ehrs) {
		this.ehrs = ehrs;
	}

	public String getEcyc() {
		return ecyc;
	}

	public void setEcyc(String ecyc) {
		this.ecyc = ecyc;
	}

	public String getAp() {
		return ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
	}

}
