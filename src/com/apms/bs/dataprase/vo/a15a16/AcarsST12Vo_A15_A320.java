package com.apms.bs.dataprase.vo.a15a16;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsST12Vo_A15_A320 extends AcarsLineDataVo{
	
	private Float ralt; 
	private Float ralr; 
	private Float ptch; 
	private Float ptcr;
	private Float roll;
	private Float rolr;
	private Float yam; 
	
	public AcarsST12Vo_A15_A320(String str) throws Exception{
		originalStr = str;
		logger.debug("ST content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 7);
		ralt = ReportParseUtil.strToFloat(columns[0], "ralt");

		ralr = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "ralr");
		ptch = ReportParseUtil.strToFloatWithIntPostion(columns[2], 3, "ptch");
		ptcr = ReportParseUtil.strToFloatWithIntPostion(columns[3], 3, "ptcr");
		roll = ReportParseUtil.strToFloatWithIntPostion(columns[4], 3, "roll");
		rolr = ReportParseUtil.strToFloat(columns[5], "rolr");
		yam = ReportParseUtil.strToFloatWithIntPostion(columns[6], 3, "yam");

	}

	public Float getRalt() {
		return ralt;
	}

	public void setRalt(Float ralt) {
		this.ralt = ralt;
	}

	public Float getRalr() {
		return ralr;
	}

	public void setRalr(Float ralr) {
		this.ralr = ralr;
	}

	public Float getPtch() {
		return ptch;
	}

	public void setPtch(Float ptch) {
		this.ptch = ptch;
	}

	public Float getPtcr() {
		return ptcr;
	}

	public void setPtcr(Float ptcr) {
		this.ptcr = ptcr;
	}

	public Float getRoll() {
		return roll;
	}

	public void setRoll(Float roll) {
		this.roll = roll;
	}

	public Float getRolr() {
		return rolr;
	}

	public void setRolr(Float rolr) {
		this.rolr = rolr;
	}

	public Float getYam() {
		return yam;
	}

	public void setYam(Float yam) {
		this.yam = yam;
	}

}
