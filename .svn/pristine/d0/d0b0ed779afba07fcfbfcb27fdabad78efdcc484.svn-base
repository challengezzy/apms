package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsKO_KIVo_A25_A320 extends AcarsLineDataVo{
	private Float oik1; 
	private Float oik2; 
	
	public AcarsKO_KIVo_A25_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 2);
		
		if(isRep){
			oik1 = ReportParseUtil.strToFloat(columns[0], "oik1");
			oik2 = ReportParseUtil.strToFloat(columns[1], "oik2");
		}else{
			oik1 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 2,"oik1");
			oik2 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 2,"oik2");
		}
		
	}
	
	public Float getOik1() {
		return oik1;
	}

	public void setOik1(Float oik1) {
		this.oik1 = oik1;
	}

	public Float getOik2() {
		return oik2;
	}

	public void setOik2(Float oik2) {
		this.oik2 = oik2;
	}

}
