package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsN1N2Vo_A04_A320_CFM extends AcarsLineDataVo{
	
	private String _1; 
	private String _2; 
	private String _3; 
	private String _4;
	private String _5;
	private String _6;
	private String _7;
	private String _8;
	private Float egtm;
	
	public AcarsN1N2Vo_A04_A320_CFM(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 9);
		_1 = ReportParseUtil.getNumberStr(columns[0]);
		_2 = ReportParseUtil.getNumberStr(columns[1]);
		_3 = ReportParseUtil.getNumberStr(columns[2]);
		_4 = ReportParseUtil.getNumberStr(columns[3]);
		_5 = ReportParseUtil.getNumberStr(columns[4]);
		_6 = ReportParseUtil.getNumberStr(columns[5]);
		_7 = ReportParseUtil.getNumberStr(columns[6]);
		_8 = ReportParseUtil.getNumberStr(columns[7]);
		
		//吉祥的数据情况，跳过第 9个字段。最后一个字段为egtm
		egtm = ReportParseUtil.strToFloatWithIntPostion(columns[columns.length-1], 3, "egtm");
		
	}
	
	public String get_1() {
		return _1;
	}
	public void set_1(String _1) {
		this._1 = _1;
	}
	public String get_2() {
		return _2;
	}
	public void set_2(String _2) {
		this._2 = _2;
	}
	public String get_3() {
		return _3;
	}
	public void set_3(String _3) {
		this._3 = _3;
	}
	public String get_4() {
		return _4;
	}
	public void set_4(String _4) {
		this._4 = _4;
	}
	public String get_5() {
		return _5;
	}
	public void set_5(String _5) {
		this._5 = _5;
	}
	public String get_6() {
		return _6;
	}
	public void set_6(String _6) {
		this._6 = _6;
	}
	public String get_7() {
		return _7;
	}
	public void set_7(String _7) {
		this._7 = _7;
	}
	public String get_8() {
		return _8;
	}
	public void set_8(String _8) {
		this._8 = _8;
	}
	public Float getEgtm() {
		return egtm;
	}
	public void setEgtm(Float egtm) {
		this.egtm = egtm;
	}

}
