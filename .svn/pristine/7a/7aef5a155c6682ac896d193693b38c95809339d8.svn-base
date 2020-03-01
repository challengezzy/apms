package com.apms.bs.dataprase.vo.a48;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsS3Vo_A48_Iae extends AcarsLineDataVo {
	private Float sva;
	private Float baf;
	private String t25;
	private String p25;
	private String ctl;
	
	public AcarsS3Vo_A48_Iae(String str,boolean isRep) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
	    checkFieldsNum(originalStr,columns, 5);
	    
	    if(isRep){
	    	sva = ReportParseUtil.strToFloat(columns[0],"sva");
		    baf = ReportParseUtil.strToFloat(columns[1],"baf");
	    }else{
	    	sva = ReportParseUtil.strToFloatWithIntPostion(columns[0],2,"sva");
		    baf = ReportParseUtil.strToFloatWithIntPostion(columns[1],3,"baf");
	    }
	    
	    t25 = ReportParseUtil.strToStrWithIntPostion(columns[2], 3);
	    p25 = ReportParseUtil.strToStrWithIntPostion(columns[3], 2);
		ctl = columns[4];
		
	}

	public Float getSva() {
		return sva;
	}

	public void setSva(Float sva) {
		this.sva = sva;
	}

	public Float getBaf() {
		return baf;
	}

	public void setBaf(Float baf) {
		this.baf = baf;
	}

	public String getT25() {
		return t25;
	}

	public void setT25(String t25) {
		this.t25 = t25;
	}

	public String getP25() {
		return p25;
	}

	public void setP25(String p25) {
		this.p25 = p25;
	}

	public String getCtl() {
		return ctl;
	}

	public void setCtl(String ctl) {
		this.ctl = ctl;
	}

}
