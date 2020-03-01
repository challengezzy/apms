package com.apms.bs.dataprase.vo.a50;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsL1Vo_A50_A320 extends AcarsLineDataVo {
	private String rpm; //
	private String mil; //
	private String deg; //
	private Date time; //
	private String unit; //
	
	public AcarsL1Vo_A50_A320(String str,String transdate,boolean isRep) throws Exception{
		originalStr = str;
		String tempStr = str.trim().substring(2);
		String[] columns; 
	    columns = StringUtil.splitString2Array(tempStr, ",", true);
	    checkFieldsNum(originalStr,columns, 4);
	    
	    if(!columns[0].startsWith("--")){
	    	rpm = columns[0];
	    }
	    
	    if(!columns[1].startsWith("--")){
	    	if(isRep){
	    		mil = columns[1];
	    	}else{
	    		mil = ReportParseUtil.strToStrWithIntPostion(columns[1], 2);
	    	}
	    	
	    }
	    
	    if(!columns[2].startsWith("--")){
	    	deg = columns[2];
	    }
	    try{
	    	if( !columns[3].startsWith("--")){
	    		time = DateUtil.StringToDate(transdate.substring(0, 10) +" "+ columns[3], "yyyy-MM-dd HHmmss");
	    	}
			
		}catch (Exception e) {
			logger.debug("open/close转换为日期异常！");
		}
		
		if(!columns[4].startsWith("--")){
			if(isRep){
				unit = columns[4];
	    	}else{
	    		unit = ReportParseUtil.strToStrWithIntPostion(columns[4], 2);
	    	}
	    }
	    
		
	}

	public String getRpm() {
		return rpm;
	}

	public void setRpm(String rpm) {
		this.rpm = rpm;
	}

	public String getMil() {
		return mil;
	}

	public void setMil(String mil) {
		this.mil = mil;
	}

	public String getDeg() {
		return deg;
	}

	public void setDeg(String deg) {
		this.deg = deg;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
