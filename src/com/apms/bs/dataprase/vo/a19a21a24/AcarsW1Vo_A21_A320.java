package com.apms.bs.dataprase.vo.a19a21a24;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsW1Vo_A21_A320 extends AcarsLineDataVo {
	private String pb_wai;
	private String pb_prv1;
	private String pb_prv2;
	private String sw_xfr;

	public AcarsW1Vo_A21_A320(String str) throws Exception {
		originalStr = str;

		String tempStr = str.substring(2);
		String[] columns;
		columns = StringUtil.splitString2Array(tempStr, ",", true);
		checkFieldsNum(originalStr, columns, 4);
		pb_wai = getBooleanStr(columns[0]);

		pb_prv1 = getBooleanStr(columns[1]);
		pb_prv2 = getBooleanStr(columns[2]);

		sw_xfr = columns[3];
		if (sw_xfr == null) {
			sw_xfr = null;
		} else if (sw_xfr.equals("AUTO")) {
			sw_xfr = "2";
		} else if (sw_xfr.equals("SHUT")) {
			sw_xfr = "0";
		} else if (sw_xfr.equals("OPEN")) {
			sw_xfr = "1";
		} else {
			sw_xfr = null;
		}

	}

	private String getBooleanStr(String str) {
		if ("ONX".equals(str)) {
			return "1";
		} else if ("OFF".equals(str)) {
			return "0";
		} else {// 未知
			return "0";
		}
	}

	public String getPb_wai() {
		return pb_wai;
	}

	public void setPb_wai(String pb_wai) {
		this.pb_wai = pb_wai;
	}

	public String getPb_prv1() {
		return pb_prv1;
	}

	public void setPb_prv1(String pb_prv1) {
		this.pb_prv1 = pb_prv1;
	}

	public String getPb_prv2() {
		return pb_prv2;
	}

	public void setPb_prv2(String pb_prv2) {
		this.pb_prv2 = pb_prv2;
	}

	public String getSw_xfr() {
		return sw_xfr;
	}

	public void setSw_xfr(String sw_xfr) {
		this.sw_xfr = sw_xfr;
	}

}
