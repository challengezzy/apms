package com.apms.bs.dataprase.vo.a48;

import java.util.Date;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsSVo_A48_Iae extends AcarsLineDataVo {
	private Float svadq; //|VSV差分值-vsv 差分值AVG|-3*STD
	private Float svadav; //均值 
	private Float svadvar; //方差
	private Date time; //时间
	
	public AcarsSVo_A48_Iae(String str,String transdate) throws Exception{
		originalStr = str;
		String tempStr = str.replace("\r", "").trim().substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);
	    checkFieldsNum(originalStr,columns, 4);
	    
	    svadq = ReportParseUtil.strToFloatWithDecimalPostion(columns[0], 2, "svadq");
	    svadav = ReportParseUtil.strToFloatWithDecimalPostion(columns[1], 2, "svadav");
	    svadvar = ReportParseUtil.strToFloatWithDecimalPostion(columns[2], 2, "svadvar");
	    
	    try{
	    	if( !columns[3].startsWith("--")){
	    		time = DateUtil.StringToDate(transdate.substring(0, 10) +" "+ columns[3], "yyyy-MM-dd HHmmss");
	    	}
			
		}catch (Exception e) {
			logger.debug("open/close转换为日期异常！");
		}
		
	}

	public Float getSvadq() {
		return svadq;
	}

	public void setSvadq(Float svadq) {
		this.svadq = svadq;
	}

	public Float getSvadav() {
		return svadav;
	}

	public void setSvadav(Float svadav) {
		this.svadav = svadav;
	}

	public Float getSvadvar() {
		return svadvar;
	}

	public void setSvadvar(Float svadvar) {
		this.svadvar = svadvar;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
