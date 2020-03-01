package com.apms.bs.dataprase.vo.a13a14;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

/**
 * @author Kevin
 *
 */
public class AcarsV1Vo_A13_A320 extends AcarsLineDataVo{
private String originalStr;//原始报文，如CE0055,-0379,---,---,6119,272,I71CA2
	
	private Float sta; 
	private Float egtp; 
	private Float npa; 
	private Float ota; 
	private Float lcit; 

	
	public AcarsV1Vo_A13_A320(String str) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		if(columns.length==6){
			if("".equals(columns[5])){
				
			}
		}else{
			checkFieldsNum(originalStr,columns,5);
		}
		sta = ReportParseUtil.strToFloat(columns[0] ,"sta");
		egtp = ReportParseUtil.strToFloat(columns[1] ,"egtp");
		ota = ReportParseUtil.strToFloat(columns[3] ,"ota");
		lcit = ReportParseUtil.strToFloat(columns[4],"lcit");

		if (columns[2].contains("---")||columns[2].contains("XXX"))
		{ 
			npa= null;
		}else{
			npa = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "pt");
			//npa = new Integer(columns[2].substring(0,3))+"." + columns[2].substring(3,columns[2].length());
		}
		
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

	public Float getOta() {
		return ota;
	}

	public void setOta(Float ota) {
		this.ota = ota;
	}

	public Float getLcit() {
		return lcit;
	}


	public void setLcit(Float lcit) {
		this.lcit = lcit;
	}

}

