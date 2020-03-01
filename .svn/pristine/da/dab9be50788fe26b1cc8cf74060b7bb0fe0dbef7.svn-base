package com.apms.bs.dataprase.vo.a13a14;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.StringUtil;

import smartx.framework.common.vo.NovaLogger;

public class AcarsNxVo_R14_A330_A340 {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;
	
	private Float na; //
	private Float egta; //
	private Float wf; //
	private Float p2a; //
	private Float lot; //滑油温度，A330中LOT
	private Float scv; //
	private Float hot; //
	private Float igv; //
	private Float wb; //
	private Float lcot; //
	private Float lcit; //
	private Float pt; //
	private Float gla; //
	
	private String nx;
	
	public AcarsNxVo_R14_A330_A340(String str, String acmodel, String Nx) throws Exception{
		nx = Nx;
		String tempStr;
		originalStr = str;
//		//logger.debug("Nx content: " + originalStr);
//		if (acmodel.equals("A330")){
//			tempStr = str.replace("\r", "").trim().substring(3);
//		} else {
//			tempStr = str.replace("\r", "").trim();
//		}
		//在外面区分330和 340的差异,zhangzy 20141030
		tempStr = str.replace("\r", "").trim();
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		
		if (columns[0].contains("---")||columns[0].contains("XXX"))
		{ 
			na = null;
		}else{
			na = ReportParseUtil.strToFloatWithIntPostion(columns[0], 1, "na");
			//na = new Integer(columns[0].substring(0,2))+"." + columns[0].substring(2,columns[0].length());
		}
		
		egta = ReportParseUtil.strToFloat(columns[1] ,"egta");
		wf = ReportParseUtil.strToFloat(columns[2] ,"wf");
		gla = ReportParseUtil.strToFloat(columns[3] ,"gla");
		
//		wb = ReportParseUtil.strToFloat(columns[4] ,"wb");
//		pt = ReportParseUtil.strToFloat(columns[5] ,"PT");
//		p2a = ReportParseUtil.strToFloat(columns[6] ,"p2a");
		//modify by zhangzy 20141030 单位转换
		//WB单位由g/s换算成kg/s
		Float wb_g = ReportParseUtil.strToFloat(columns[4] ,"wb");
		Float wb_kg = wb_g/1000f;
		wb = wb_kg;
		
		//modify by zhangzy 1BAR=14.5PSI,计算单位从PSI换算成BAR
		Float pt_psi = ReportParseUtil.strToFloat(columns[5] ,"pt"); 
		Float pt_bar = pt_psi/14.5f;
		pt = pt_bar; //可能小数位数过多
		
		//P2A 单位从PSI换算成BAR
		Float p2a_psi = ReportParseUtil.strToFloat(columns[6] ,"p2a"); 
		Float p2a_bar = p2a_psi/14.5f;
		p2a = p2a_bar;
		
		lcot = ReportParseUtil.strToFloat(columns[7] ,"lcdt");
		lcit = ReportParseUtil.strToFloat(columns[8] ,"lcit");
		igv = ReportParseUtil.strToFloat(columns[9] ,"igv");
		scv = ReportParseUtil.strToFloat(columns[10] ,"scv");
		lot = ReportParseUtil.strToFloat(columns[11] ,"lot");
		hot = ReportParseUtil.strToFloat(columns[12].replace(":", ""), "hot");	
		
		
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

	public Float getWf() {
		return wf;
	}

	public void setWf(Float wf) {
		this.wf = wf;
	}

	public Float getP2a() {
		return p2a;
	}

	public void setP2a(Float p2a) {
		this.p2a = p2a;
	}

	public Float getLot() {
		return lot;
	}

	public void setLot(Float lot) {
		this.lot = lot;
	}

	public Float getScv() {
		return scv;
	}

	public void setScv(Float scv) {
		this.scv = scv;
	}

	public Float getHot() {
		return hot;
	}

	public void setHot(Float hot) {
		this.hot = hot;
	}

	public Float getIgv() {
		return igv;
	}

	public void setIgv(Float igv) {
		this.igv = igv;
	}

	public Float getWb() {
		return wb;
	}

	public void setWb(Float wb) {
		this.wb = wb;
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

	public Float getPt() {
		return pt;
	}

	public void setPt(Float pt) {
		this.pt = pt;
	}

	public Float getGla() {
		return gla;
	}

	public void setGla(Float gla) {
		this.gla = gla;
	}

	public String getNx() {
		return nx;
	}

	public void setNx(String nx) {
		this.nx = nx;
	}

}
