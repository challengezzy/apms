package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsH1Vo_A21_A320 extends AcarsLineDataVo{
	private String taprv;
	private String tav;
	private String MAINCTL_H1;
	private String SECDCTL_H1;
	
	public AcarsH1Vo_A21_A320(String str) throws Exception {
		originalStr = str;

		String tempStr = str.substring(2);
		String[] columns; 
		tempStr=tempStr.trim();
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr,columns, 4);
		
		taprv = getBoolStr(columns[0]);
		tav = getBoolStr(columns[1]);
		MAINCTL_H1 = getBoolStr(columns[2]);
		SECDCTL_H1 = getBoolStr(columns[3]);
	}

	private String getBoolStr(String str){
		if(str == null || str.contains("XX") || "NOX".equals(str) ){
			return "0";
		}else if( "ONX".equals(str) ) {
			return "1";
		}else{//未知
			return "0";
		}
	}
	
	public String getTaprv() {
		return taprv;
	}

	public void setTaprv(String taprv) {
		this.taprv = taprv;
	}

	public String getTav() {
		return tav;
	}

	public void setTav(String tav) {
		this.tav = tav;
	}

	public String getMAINCTL_H1() {
		return MAINCTL_H1;
	}

	public void setMAINCTL_H1(String mAINCTL_H1) {
		MAINCTL_H1 = mAINCTL_H1;
	}

	public String getSECDCTL_H1() {
		return SECDCTL_H1;
	}

	public void setSECDCTL_H1(String sECDCTL_H1) {
		SECDCTL_H1 = sECDCTL_H1;
	}

}
