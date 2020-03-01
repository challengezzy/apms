package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsZRVo_A21_A320 extends AcarsLineDataVo {
	private String fav1;
	private String fav2;
	private String hpv1;
	private String hpv2;
	private String prv1;
	private String prv2;

	public AcarsZRVo_A21_A320(String str) throws Exception {
		originalStr = str;

		String tempStr = str.substring(2);
		for (int i = 0; i < 10; i++)
			tempStr = tempStr.replaceAll("  ", " ");
		String[] columns;
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr, columns, 6);

		// FOX,FCX表示1， NFO,NFC表示0
		fav1 = getBooleanStr(columns[0]);
		fav2 = getBooleanStr(columns[1]);
		hpv1 = getBooleanStr(columns[2]);
		hpv2 = getBooleanStr(columns[3]);
		prv1 = getBooleanStr(columns[4]);
		prv2 = getBooleanStr(columns[5]);

	}

	private String getBooleanStr(String str) {
		if (str.equals("FOX") || str.equals("FCX")) {
			return "1";
		} else if (str.equals("NFO") || str.equals("NFC")) {
			return "0";
		} else {
			return "0";
		}
	}

	public String getFav1() {
		return fav1;
	}

	public void setFav1(String fav1) {
		this.fav1 = fav1;
	}

	public String getFav2() {
		return fav2;
	}

	public void setFav2(String fav2) {
		this.fav2 = fav2;
	}

	public String getHpv1() {
		return hpv1;
	}

	public void setHpv1(String hpv1) {
		this.hpv1 = hpv1;
	}

	public String getHpv2() {
		return hpv2;
	}

	public void setHpv2(String hpv2) {
		this.hpv2 = hpv2;
	}

	public String getPrv1() {
		return prv1;
	}

	public void setPrv1(String prv1) {
		this.prv1 = prv1;
	}

	public String getPrv2() {
		return prv2;
	}

	public void setPrv2(String prv2) {
		this.prv2 = prv2;
	}

}
