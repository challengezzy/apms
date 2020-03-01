package com.apms.bs.dataprase.vo.a49;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsL1Vo_A49_A320 extends AcarsLineDataVo {
	
	private Date open;
	private Date close; 
	private int time; 
	
	public AcarsL1Vo_A49_A320(String str,String transdate) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);
		
		String openstr = columns[0];
		String closestr = columns[1];
		time = ReportParseUtil.strToInteger( columns[2]);
				
		try{
			open = DateUtil.StringToDate(transdate.substring(0, 10) +" "+ openstr, "yyyy-MM-dd HHmmss");
			close = DateUtil.StringToDate(transdate.substring(0, 10) +" "+ closestr, "yyyy-MM-dd HHmmss");
			
		}catch (Exception e) {
			logger.debug("open/close转换为日期异常！");
		}
		
		
	}

	public Date getOpen() {
		return open;
	}

	public void setOpen(Date open) {
		this.open = open;
	}

	public Date getClose() {
		return close;
	}

	public void setClose(Date close) {
		this.close = close;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	

}
