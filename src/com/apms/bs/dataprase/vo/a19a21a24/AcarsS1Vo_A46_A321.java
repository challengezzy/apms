package com.apms.bs.dataprase.vo.a19a21a24;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsS1Vo_A46_A321 extends AcarsLineDataVo {
	private float engqt1;
	private float engqt2;
	private Date date_utc;
	
	public AcarsS1Vo_A46_A321(String str,String transdate,boolean isRep) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2).trim();
		String[] columns; 
		columns = StringUtil.splitString2Array(tempStr, ",", true);			
		checkFieldsNum(originalStr,columns, 3);
		
		if(isRep){
			engqt1 = ReportParseUtil.strToFloat(columns[0], "engqt1");
			engqt2 = ReportParseUtil.strToFloat(columns[1], "engqt2");
		}else{
			engqt1 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "engqt1");
			engqt2 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "engqt2");
		}
		
		try {
			String tempTime = transdate.substring(0, 10) +" "+ columns[2].substring(0,2)+":"
			+ columns[2].substring(2,4)+":" + columns[2].substring(4,6);
			date_utc = DateUtil.StringToDate(tempTime, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}
		
	}
	
	public float getEngqt1() {
		return engqt1;
	}

	public void setEngqt1(float engqt1) {
		this.engqt1 = engqt1;
	}

	public float getEngqt2() {
		return engqt2;
	}

	public void setEngqt2(float engqt2) {
		this.engqt2 = engqt2;
	}

	public Date getDate_utc() {
		return date_utc;
	}

	public void setDate_utc(Date date_utc) {
		this.date_utc = date_utc;
	}
	
}
