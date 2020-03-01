package com.apms.bs.dataprase.vo.a15a16;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsE1Vo_A15_A320 extends AcarsLineDataVo{
	private Integer max; 
	private Integer lim; 
	private Integer counts; 
	
	public AcarsE1Vo_A15_A320(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		max = ReportParseUtil.strToInteger(columns[0], "max");
		lim = ReportParseUtil.strToInteger(columns[1], "lin");
		int cou= Integer.valueOf(columns[2])+Integer.valueOf(columns[3])+Integer.valueOf(columns[4])+Integer.valueOf(columns[5])+Integer.valueOf(columns[6]);
		counts = cou;


	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getLim() {
		return lim;
	}

	public void setLim(Integer lim) {
		this.lim = lim;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

}
