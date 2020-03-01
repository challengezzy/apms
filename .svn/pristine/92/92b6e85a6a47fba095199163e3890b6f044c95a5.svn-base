package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsS1_S2Vo_A26_A320 extends AcarsLineDataVo{
	
	private Float p25; 
	private Float t25; 
	private Float p3;
	private Float t3;
	private Float p49;
	private Float sva;
	
	public AcarsS1_S2Vo_A26_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		logger.debug("S1_S2 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		
		p25 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 2,"p25");
		t25 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3,"t25");
		
		if(isRep){
			p3 = ReportParseUtil.strToFloat(columns[2], "p3");
		}else{
			p3 = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3,"p3");
		}
		
		t3 = ReportParseUtil.strToFloatWithIntPostion(columns[3], 3,"t3");
		p49 = ReportParseUtil.strToFloatWithIntPostion(columns[4], 2,"p49");
		sva = ReportParseUtil.strToFloat(columns[5] ,"detq");
		
	}
	
	public Float getP25() {
		return p25;
	}

	public void setP25(Float p25) {
		this.p25 = p25;
	}

	public Float getT25() {
		return t25;
	}

	public void setT25(Float t25) {
		this.t25 = t25;
	}

	public Float getP3() {
		return p3;
	}

	public void setP3(Float p3) {
		this.p3 = p3;
	}

	public Float getT3() {
		return t3;
	}

	public void setT3(Float t3) {
		this.t3 = t3;
	}

	public Float getP49() {
		return p49;
	}

	public void setP49(Float p49) {
		this.p49 = p49;
	}

	public Float getSva() {
		return sva;
	}

	public void setSva(Float sva) {
		this.sva = sva;
	}
	

}
