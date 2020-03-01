package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsV5Vo_A26_A320 extends AcarsLineDataVo{
	private Float oipl; 
	private Float oitl; 
	private Float oiql;
	private Float oiqckl;
	
	public AcarsV5Vo_A26_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		logger.debug("V5 content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 4);
		
		oipl = ReportParseUtil.strToFloat(columns[0] ,"oipl");
		oitl = ReportParseUtil.strToFloat(columns[1] ,"oitl");
		
		if(isRep){
			oiql = ReportParseUtil.strToFloat(columns[2], "oiql");
			oiqckl = ReportParseUtil.strToFloat(columns[3], "oiqckl");
		}else{
			oiql = ReportParseUtil.strToFloatWithIntPostion(columns[2], 2, "oiql");
			oiqckl = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2, "oiqckl");
		}
		
	}

	public Float getOipl() {
		return oipl;
	}

	public void setOipl(Float oipl) {
		this.oipl = oipl;
	}

	public Float getOitl() {
		return oitl;
	}

	public void setOitl(Float oitl) {
		this.oitl = oitl;
	}

	public Float getOiql() {
		return oiql;
	}

	public void setOiql(Float oiql) {
		this.oiql = oiql;
	}

	public Float getOiqckl() {
		return oiqckl;
	}

	public void setOiqckl(Float oiqckl) {
		this.oiqckl = oiqckl;
	}

}
