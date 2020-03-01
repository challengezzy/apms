package com.apms.bs.dataprase.vo.a25a26a27;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsZ4_Z5Vo_A27_A320 extends AcarsLineDataVo{
	
	private Float oiq1; 
	private Float oiq2; 
	private String transdate;
	private String mondd_hhmmss;
	private Date monddHHMMSS;
	private String yymmdd;
	
	public AcarsZ4_Z5Vo_A27_A320(String str, String transdate, boolean isRep) throws Exception{
		originalStr = str;
		String trans_date = transdate;
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 4);
		
		if(isRep){
			oiq1 = ReportParseUtil.strToFloat(columns[0], "oiq1");
			oiq2 = ReportParseUtil.strToFloat(columns[1], "oiq2");
		}else{
			oiq1 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 2, "oiq1");
			oiq2 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 2, "oiq2");
		}
		
		String  monthAbbr = columns[2].substring(0,3);
		try{
			String month = DateUtil.abbrMonthToNumber(monthAbbr);
			mondd_hhmmss = trans_date.substring(0,4)+"-"+month + "-"+columns[2].substring(3,5)+ " "+columns[3].substring(0, 2) + ":" + columns[3].substring(2, 4) + ":" + columns[3].substring(4, 6);
			monddHHMMSS = DateUtil.StringToDate(mondd_hhmmss, "yyyy-MM-dd HH:mm:ss");
			
			yymmdd = trans_date.substring(0,4) + month + columns[2].substring(3,5);

		}catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}
		
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


	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
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

	public String getYymmdd() {
		return yymmdd;
	}

	public void setYymmdd(String yymmdd) {
		this.yymmdd = yymmdd;
	}

	
	
}
