package com.apms.bs.dataprase.vo.a39;

import java.util.Date;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsS1Vo_A39_A320_LIST extends AcarsLineDataVo{
	
	private Date starttime; 
	private Date endtime; 
	private double interval;
	
	public AcarsS1Vo_A39_A320_LIST(Date date ,String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);
		starttime=strToDateStr(date,columns[0]);
		endtime=strToDateStr(date,columns[1]);
		interval=Double.parseDouble(columns[2]);
	}

	private Date strToDateStr(Date date,String str){
		
		String datestr=DateUtil.getDateStr(date).substring(0,4);
		String month=str.substring(0,3);
		String monthstr="01";
		if("JAN".equals(month)){
			monthstr="01";
		}else if("FEB".equals(month)){
			monthstr="02";
		}else if("MAR".equals(month)){
			monthstr="03";
		}else if("APR".equals(month)){
			monthstr="04";
		}else if("MAY".equals(month)){
			monthstr="05";
		}else if("JUN".equals(month)){
			monthstr="06";
		}else if("JUL".equals(month)){
			monthstr="07";
		}else if("AUG".equals(month)){
			monthstr="08";
		}else if("SEP".equals(month)){
			monthstr="09";
		}else if("OCT".equals(month)){
			monthstr="10";
		}else if("NOV".equals(month)){
			monthstr="11";
		}else if("DEC".equals(month)){
			monthstr="12";
		}
		datestr+=monthstr+str.substring(3,5)+" "+str.substring(5,str.length());
		Date d=DateUtil.StringToDate(datestr,"yyyyMMdd HHmmss");
		return d;
		
	}
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}
	
}
