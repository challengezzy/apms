package com.apms.bs.dataprase.vo.a25a26a27;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsV1Vo_A25_A320 extends AcarsLineDataVo{
	
	private String mondd_hhmmss;
	private Date monddHHMMSS;
	private String sd;
	private String engai;
	private Integer oilstb;
	
	public AcarsV1Vo_A25_A320(String str,String transdate) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 5);
		
		mondd_hhmmss = transdate + " " + columns[1].substring(0, 2) + ":" + columns[1].substring(2, 4) + ":" + columns[1].substring(4, 6);
		
		try{
			monddHHMMSS = DateUtil.StringToDate(mondd_hhmmss, "yyyy-MM-dd HH:mm:ss");
		}catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}
		sd = columns[2];
		engai = columns[3];
		oilstb = ReportParseUtil.strToInteger(columns[4], "oilstb");
	}
	public String getOriginalStr() {
		return originalStr;
	}
	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
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
	public Integer getOilstb() {
		return oilstb;
	}
	public void setOilstb(Integer oilstb) {
		this.oilstb = oilstb;
	}

}
