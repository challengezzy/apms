package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsPPVo_A27_A320 extends AcarsLineDataVo{
	private Integer _27tmr; //
	private Integer cktmr; //
	private Integer endtmr; //
	private Float detq; //
	private Float endtq; //
	private Integer oiqext; //
	
	public AcarsPPVo_A27_A320(String str, boolean isRep) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 6);
		
		_27tmr = ReportParseUtil.strToInteger(columns[0], "_27tmr");
		cktmr = ReportParseUtil.strToInteger(columns[1], "cktmr");
		endtmr = ReportParseUtil.strToInteger(columns[2], "endtmr");
		
		if(isRep){
			detq = ReportParseUtil.strToFloat(columns[3], "detq");
			endtq = ReportParseUtil.strToFloat(columns[4], "endtq");
		}else{
			detq = ReportParseUtil.strToFloatWithIntPostion(columns[3], 1,"detq");
			endtq = ReportParseUtil.strToFloatWithIntPostion(columns[4], 1,"endtq");
		}
		
		oiqext = ReportParseUtil.strToInteger(columns[5], "oiqext");
	}

	public Integer get_27tmr() {
		return _27tmr;
	}

	public void set_27tmr(Integer _27tmr) {
		this._27tmr = _27tmr;
	}

	public Integer getCktmr() {
		return cktmr;
	}

	public void setCktmr(Integer cktmr) {
		this.cktmr = cktmr;
	}

	public Integer getEndtmr() {
		return endtmr;
	}

	public void setEndtmr(Integer endtmr) {
		this.endtmr = endtmr;
	}

	public Float getDetq() {
		return detq;
	}

	public void setDetq(Float detq) {
		this.detq = detq;
	}

	public Float getEndtq() {
		return endtq;
	}

	public void setEndtq(Float endtq) {
		this.endtq = endtq;
	}

	public Integer getOiqext() {
		return oiqext;
	}

	public void setOiqext(Integer oiqext) {
		this.oiqext = oiqext;
	}

}
