package com.apms.bs.dataprase.vo.a25a26a27;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsAL_ARVo_A27_A320 extends AcarsLineDataVo{
	private String oiladd; 
	private Date time; 
	private Float prad;
	private Float _20s;
	private Float oiqst;
	private Float oiqold;
	private String hhmmss;
	
	public AcarsAL_ARVo_A27_A320(String str, String trans_date, boolean isRep) throws Exception{
		originalStr = str;
		//logger.debug("AL_AR content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		if (columns[0].contains("NIL") ||  columns[0].contains("XXX")){
			oiladd="0";
		}else {
			oiladd= "1";
		}
		
		if (columns[1].contains("000000")){
			time=null;
		}else {
			try{
				hhmmss= trans_date.substring(0,10) + " " + columns[1].substring(0, 2) + ":" + columns[1].substring(2, 4) + ":" + columns[1].substring(4, 6);
				
				time = DateUtil.StringToDate(hhmmss, "yyyy-MM-dd HH:mm:ss");
				
			}catch (Exception e) {
				logger.debug("date_utc转换为日期异常！");
			}
		}
		prad = ReportParseUtil.strToFloat(columns[2],"prad");
		
		if(isRep){
			_20s = ReportParseUtil.strToFloat(columns[3], "_20s");
			oiqst = ReportParseUtil.strToFloat(columns[4], "oiqst");
			oiqold = ReportParseUtil.strToFloat(columns[5], "oiqold");
		}else{
			_20s = ReportParseUtil.strToFloatWithIntPostion(columns[3], 2,"_20s");
			oiqst = ReportParseUtil.strToFloatWithIntPostion(columns[4], 2,"oiqst");
			oiqold = ReportParseUtil.strToFloatWithIntPostion(columns[5], 2,"oiqold");
		}
		
		
	}	
	
	public String getOiladd() {
		return oiladd;
	}

	public void setOiladd(String oiladd) {
		this.oiladd = oiladd;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getHhmmss() {
		return hhmmss;
	}

	public void setHhmmss(String hhmmss) {
		this.hhmmss = hhmmss;
	}

	public Float getPrad() {
		return prad;
	}

	public void setPrad(Float prad) {
		this.prad = prad;
	}

	public Float get_20s() {
		return _20s;
	}

	public void set_20s(Float _20s) {
		this._20s = _20s;
	}

	public Float getOiqst() {
		return oiqst;
	}

	public void setOiqst(Float oiqst) {
		this.oiqst = oiqst;
	}

	public Float getOiqold() {
		return oiqold;
	}

	public void setOiqold(Float oiqold) {
		this.oiqold = oiqold;
	}

}
