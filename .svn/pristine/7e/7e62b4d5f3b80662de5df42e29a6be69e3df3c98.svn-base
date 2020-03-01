package com.apms.bs.dataprase.vo.a15a16;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsST34Vo_A15_A320 extends AcarsLineDataVo{
	private Float vrta; //
	private Float lona; //
	private Float lata; //
	
	public AcarsST34Vo_A15_A320(String str) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 3);
		
		vrta = ReportParseUtil.strToFloatWithIntPostion(columns[0], 2, "vrta");
		lona = ReportParseUtil.strToFloatWithIntPostion(columns[1], 2, "lona");
		lata = ReportParseUtil.strToFloatWithIntPostion(columns[2], 2, "lata");
	}

	public Float getVrta() {
		return vrta;
	}

	public void setVrta(Float vrta) {
		this.vrta = vrta;
	}

	public Float getLona() {
		return lona;
	}

	public void setLona(Float lona) {
		this.lona = lona;
	}

	public Float getLata() {
		return lata;
	}

	public void setLata(Float lata) {
		this.lata = lata;
	}

}
