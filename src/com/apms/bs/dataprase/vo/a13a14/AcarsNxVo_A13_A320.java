package com.apms.bs.dataprase.vo.a13a14;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

import smartx.framework.common.vo.NovaLogger;

public class AcarsNxVo_A13_A320 extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;
	
	private String esn; //
	private String acw1; //
	private String acw2; //
	private Float na; //
	private Float egta; //
	private Float igv; //
	
	private AcarsAcwVo acw1Vo;
	private AcarsAcwVo acw2Vo;
	
	public AcarsNxVo_A13_A320(String str) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 6);
		esn = columns[0];
		acw1= columns[1];
		acw1Vo = new AcarsAcwVo(ApmsVarConst.CW_ACW1,acw1);
		
		acw2= columns[2];
		acw2Vo = new AcarsAcwVo(ApmsVarConst.CW_ACW2,acw2);
		
		egta= ReportParseUtil.strToFloat(columns[4],"egta");
		igv= ReportParseUtil.strToFloat(columns[5],"igv");
		if (columns[3].contains("---")||columns[3].contains("XXX"))
		{ 
			na = null;
		}else{
			na = ReportParseUtil.strToFloatWithIntPostion(columns[3], 3, "na");
			//na = new Integer(columns[3].substring(0,3))+"." + columns[3].substring(3,columns[3].length());
		}
		
		
				
		//logger.debug("Nx content: " + originalStr);
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
	public String getAcw1() {
		return acw1;
	}
	public void setAcw1(String acw1) {
		this.acw1 = acw1;
	}
	public String getAcw2() {
		return acw2;
	}
	public void setAcw2(String acw2) {
		this.acw2 = acw2;
	}
	public Float getNa() {
		return na;
	}
	public void setNa(Float na) {
		this.na = na;
	}
	public Float getEgta() {
		return egta;
	}
	public void setEgta(Float egta) {
		this.egta = egta;
	}
	public Float getIgv() {
		return igv;
	}
	public void setIgv(Float igv) {
		this.igv = igv;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public AcarsAcwVo getAcw1Vo() {
		return acw1Vo;
	}

	public void setAcw1Vo(AcarsAcwVo acw1Vo) {
		this.acw1Vo = acw1Vo;
	}

	public AcarsAcwVo getAcw2Vo() {
		return acw2Vo;
	}

	public void setAcw2Vo(AcarsAcwVo acw2Vo) {
		this.acw2Vo = acw2Vo;
	}
	
	

}
