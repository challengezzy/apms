package com.apms.bs.dataprase.vo.a25a26a27;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsSWZVo_A25_A320 extends AcarsLineDataVo{
	private Float oiq1; 
	private Float oiq2; 
	
	private String mondd_hhmmss;
	private Date monddHHMMSS;
	private String sd;
	private String engai;
	private String month;
	
	public AcarsSWZVo_A25_A320(String str,String transdate, boolean isRep) throws Exception{
		originalStr = str;
		logger.debug("SWZ content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		
		if(isRep){
			oiq1 = ReportParseUtil.strToFloat(columns[0], "oiq1");
			oiq2 = ReportParseUtil.strToFloat(columns[1], "oiq2");
		}else{
			oiq1 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 2,"oiq1");
			oiq2 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 2,"oiq2");
		}
		
		try{
			month = DateUtil.abbrMonthToNumber(columns[2].substring(0,3));
			mondd_hhmmss = transdate.substring(0,4)+"-"+month+"-"+columns[2].substring(3,5)+" "
			+columns[3].substring(0, 2) + ":" + columns[3].substring(2, 4) + ":" + columns[3].substring(4, 6);

			monddHHMMSS = DateUtil.StringToDate(mondd_hhmmss, "yyyy-MM-dd HH:mm:ss");
		}catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}
		sd = ReportParseUtil.getNumberStr(columns[4]);
		engai = ReportParseUtil.getNumberStr(columns[5]);

	}
	
	public Float getOiq1() {
		return oiq1;
	}

	public void setOiq1(Float oiq1) {
		this.oiq1 = oiq1;
	}

	public Float getOiq2() {
		return oiq2;
	}

	public void setOiq2(Float oiq2) {
		this.oiq2 = oiq2;
	}

	public String getMondd_hhmmss() {
		return mondd_hhmmss;
	}
	public void setMondd_hhmmss(String mondd_hhmmss) {
		this.mondd_hhmmss = mondd_hhmmss;
	}
	public Date getMonddHHMMSS() {
		return monddHHMMSS;
	}
	public void setMonddHHMMSS(Date monddHHMMSS) {
		this.monddHHMMSS = monddHHMMSS;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public String getEngai() {
		return engai;
	}
	public void setEngai(String engai) {
		this.engai = engai;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
}
