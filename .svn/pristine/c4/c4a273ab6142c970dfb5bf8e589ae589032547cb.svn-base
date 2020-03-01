package com.apms.bs.dataprase.vo.a13a14;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.StringUtil;

import smartx.framework.common.vo.NovaLogger;

public class AcarsNxVo_R13_A330_A340 {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;
	
	private String esn; //
	private Float egta; //
	private Float gla; //
	private Float wb;
	private Float pt;
	private Float lcot;
	private Float lcit;
	private Float p2a;
	private Float igv;
	private Float scv;
	private Float lot;
	private Float hot;
	
	public AcarsNxVo_R13_A330_A340(String str, String acmodel) throws Exception{
		String tempStr;
		originalStr = str;
		//在外面区分330和 340的差异,zhangzy 20141030
		tempStr = str.replace("\r", "").trim();
		
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		
		esn = columns[0];
		if (columns[1].contains("---")|columns[1].contains("XXX"))
		{ 
			egta= 0f;
		}else{
			egta = ReportParseUtil.strToFloat(columns[1],"egta");
		}
		gla = ReportParseUtil.strToFloat(columns[2],"egta");
		
		try{
			//WB单位由g/s换算成kg/s
			Float wb_g = ReportParseUtil.strToFloat(columns[3] ,"wb");
			Float wb_kg = wb_g/1000f;
			wb = wb_kg;
			
			//modify by zhangzy 1BAR=14.5PSI,计算单位从PSI换算成BAR
			Float pt_psi = ReportParseUtil.strToFloat(columns[4] ,"pt"); 
			Float pt_bar = pt_psi/14.5f;
			pt = pt_bar; //可能小数位数过多？
			
			//P2A 单位从PSI换算成BAR
			Float p2a_psi = ReportParseUtil.strToFloat(columns[5] ,"p2a"); 
			Float p2a_bar = p2a_psi/14.5f;
			p2a = p2a_bar;
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("WB或PT或P2A单位换算失败pt=["+columns[3]+"],["+columns[4]+"],["+columns[5]+"]");
		}
		
		lcot = ReportParseUtil.strToFloat(columns[6] ,"lcdt"); 
		lcit = ReportParseUtil.strToFloat(columns[7] ,"lcit");
		igv = ReportParseUtil.strToFloat(columns[8] ,"igv");
		scv = ReportParseUtil.strToFloat(columns[9] ,"scv");
		lot = ReportParseUtil.strToFloat(columns[10] ,"lot");
		hot = ReportParseUtil.strToFloat(columns[11] ,"hot");
		
		
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

	public Float getEgta() {
		return egta;
	}

	public void setEgta(Float egta) {
		this.egta = egta;
	}

	public Float getGla() {
		return gla;
	}

	public void setGla(Float gla) {
		this.gla = gla;
	}

	public Float getWb() {
		return wb;
	}

	public void setWb(Float wb) {
		this.wb = wb;
	}

	public Float getPt() {
		return pt;
	}

	public void setPt(Float pt) {
		this.pt = pt;
	}

	public Float getP2a() {
		return p2a;
	}

	public void setP2a(Float p2a) {
		this.p2a = p2a;
	}

	public Float getLcot() {
		return lcot;
	}

	public void setLcot(Float lcot) {
		this.lcot = lcot;
	}

	public Float getLcit() {
		return lcit;
	}

	public void setLcit(Float lcit) {
		this.lcit = lcit;
	}

	public Float getIgv() {
		return igv;
	}

	public void setIgv(Float igv) {
		this.igv = igv;
	}

	public Float getScv() {
		return scv;
	}

	public void setScv(Float scv) {
		this.scv = scv;
	}

	public Float getLot() {
		return lot;
	}

	public void setLot(Float lot) {
		this.lot = lot;
	}

	public Float getHot() {
		return hot;
	}

	public void setHot(Float hot) {
		this.hot = hot;
	}

	
}
