package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsT1_T2Vo_A25_A320 extends AcarsLineDataVo{
	private Float oip; //
	private Float oit; //
	private Float oiq;
	private Float n2;
	private Float p3;
	private Float oik;
	
	public AcarsT1_T2Vo_A25_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		logger.debug("T1_T2 content: " + originalStr);
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		oip = ReportParseUtil.strToFloat(columns[0],"oip");
		oit = ReportParseUtil.strToFloat(columns[1],"oit");
		
		if(isRep){
			oiq = ReportParseUtil.strToFloat(columns[2], "oiq");
			n2 = ReportParseUtil.strToFloat(columns[3], "n2");
			p3 = ReportParseUtil.strToFloat(columns[4], "p3");
			oik = ReportParseUtil.strToFloat(columns[5], "oik");
		}else{
			oiq = ReportParseUtil.strToFloatWithIntPostion(columns[2], 2,"oiq");
			n2 = ReportParseUtil.strToFloatWithIntPostion(columns[3], 3,"n2");
			p3 = ReportParseUtil.strToFloatWithIntPostion(columns[4], 3,"p3");
			oik = ReportParseUtil.strToFloatWithIntPostion(columns[5], 2,"oik");
		}
		
	}
	
	public Float getOip() {
		return oip;
	}

	public void setOip(Float oip) {
		this.oip = oip;
	}

	public Float getOit() {
		return oit;
	}

	public void setOit(Float oit) {
		this.oit = oit;
	}

	public Float getOiq() {
		return oiq;
	}

	public void setOiq(Float oiq) {
		this.oiq = oiq;
	}

	public Float getN2() {
		return n2;
	}

	public void setN2(Float n2) {
		this.n2 = n2;
	}

	public Float getP3() {
		return p3;
	}

	public void setP3(Float p3) {
		this.p3 = p3;
	}

	public Float getOik() {
		return oik;
	}

	public void setOik(Float oik) {
		this.oik = oik;
	}

}
