package com.apms.bs.dataprase.vo.a13a14;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.StringUtil;

/**
 * @author Kevin
 *
 */
public class AcarsV1Vo_R13_A330_A340 {
private String originalStr;//原始报文，如CE0055,-0379,---,---,6119,272,I71CA2
	
	private Float sta;
	private Float egtp;
	private Float npa;
	private Float lot;
	private Float lcit;
	
	public AcarsV1Vo_R13_A330_A340(String str, String acmodel) throws Exception{
		originalStr = str;
//		if (acmodel.equals("A330")){
//			tempStr = str.replace("\r", "").trim().substring(3);
//			
//		} else {
//			tempStr = str.replace("\r", "");
//		}
		//在外面区分330和 340的差异,zhangzy 20141030
		String tempStr = str.replace("\r", "").trim();
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		
		sta = ReportParseUtil.strToFloat(columns[0], "sta");
		if (columns[1].contains("---") | columns[1].contains("XXX")) {
			egtp = null;
		} else {
			egtp = ReportParseUtil.strToFloat(columns[1], "egtp");
		}
		npa =  ReportParseUtil.strToFloat(columns[2], "npa");
		if (columns[3].contains("---") | columns[3].contains("XXX")) {
			lot = null;
		} else {
			lot = ReportParseUtil.strToFloat(columns[3], "lot");
		}

		lcit = ReportParseUtil.strToFloat(columns[4].replace(":", ""),"lcit"); ;
	}


	public String getOriginalStr() {
		return originalStr;
	}


	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}


	public Float getSta() {
		return sta;
	}


	public void setSta(Float sta) {
		this.sta = sta;
	}


	public Float getEgtp() {
		return egtp;
	}


	public void setEgtp(Float egtp) {
		this.egtp = egtp;
	}


	public Float getNpa() {
		return npa;
	}


	public void setNpa(Float npa) {
		this.npa = npa;
	}

	public Float getLot() {
		return lot;
	}

	public void setLot(Float lot) {
		this.lot = lot;
	}

	public Float getLcit() {
		return lcit;
	}


	public void setLcit(Float lcit) {
		this.lcit = lcit;
	}

}

