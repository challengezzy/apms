package com.apms.bs.dataprase.vo.a48;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsS2Vo_A48_Iae extends AcarsLineDataVo {
	private String eprc;
	private String t2;
	private String p2;
	private Float mn; //马赫数，3位都是小数
	private String ecw3;
	private String ecw4;
	private Float trp;
	
	public AcarsS2Vo_A48_Iae(String str,boolean isRep) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
	    checkFieldsNum(originalStr,columns, 7);
	    
	    eprc = ReportParseUtil.strToStrWithIntPostion(columns[1], 1);
	    t2 = ReportParseUtil.strToStrWithIntPostion(columns[1], 3);
	    p2 = ReportParseUtil.strToStrWithIntPostion(columns[2], 2);
		mn = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2)/10; //3位都是小数，留两位后再除10
		ecw3 = ReportParseUtil.strToStrWithIntPostion(columns[4], 3);
		ecw4 = ReportParseUtil.strToStrWithIntPostion(columns[5], 3);
		
		if(isRep){
			trp = ReportParseUtil.strToFloat(columns[6],"trp");
		}else{
			trp = ReportParseUtil.strToFloatWithIntPostion(columns[6],3,"trp");
		}
		
	}

	public String getEprc() {
		return eprc;
	}

	public void setEprc(String eprc) {
		this.eprc = eprc;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public Float getMn() {
		return mn;
	}

	public void setMn(Float mn) {
		this.mn = mn;
	}

	public String getEcw3() {
		return ecw3;
	}

	public void setEcw3(String ecw3) {
		this.ecw3 = ecw3;
	}

	public String getEcw4() {
		return ecw4;
	}

	public void setEcw4(String ecw4) {
		this.ecw4 = ecw4;
	}

	public Float getTrp() {
		return trp;
	}

	public void setTrp(Float trp) {
		this.trp = trp;
	}

}
