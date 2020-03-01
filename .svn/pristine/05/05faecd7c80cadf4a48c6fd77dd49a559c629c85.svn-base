package com.apms.bs.dataprase.vo.a19a21a24;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsExNxVo_A19_A320 extends AcarsLineDataVo {
	private String row_str;
	private Float n1; 
	private Float n2;
	private Float pf;
	private Float cot;
	private Float ri;
	private Float ro;
	private Float pbv;
	private Integer fcv;

	public AcarsExNxVo_A19_A320(String str, String ExNx) throws Exception {
		row_str = ExNx;
		originalStr = str;

		String tempStr = str.substring(2).trim();
		String[] columns;
		if (tempStr.indexOf(",") != -1) {
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		} else {
			tempStr = tempStr.trim();
			columns = StringUtil.splitString2Array(tempStr, " ", true);
		}
		checkFieldsNum(originalStr, columns, 8);
		
		n1 = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "n1");
		n2 = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "n2");
		
		if (columns[2].contains("---") | columns[2].contains("XXX")) {
			pf = null;
		} else {
			pf = ReportParseUtil.strToFloat("0." + columns[2], "pf");;
		}
				
		cot = ReportParseUtil.strToFloat(columns[3], "cot");
		ri = ReportParseUtil.strToFloat(columns[4], "ri");
		ro = ReportParseUtil.strToFloat(columns[5], "ro");
		pbv = ReportParseUtil.strToFloat(columns[6], "pbv");
		
		if (columns[7].contains("-") || columns[7].contains("X")) {
			fcv = null;
		} else {
			fcv = ReportParseUtil.strToInteger(columns[7], "fcw");
		}

	}

	public void insertToTable(String msgno, AcarsHeadVo headVo, int i) throws Exception {
		CommDMO dmo = new CommDMO();
		String insertSqlen = "insert into a_dfd_a19_list_en_row(ID,MSG_NO,ROW_STR,ROW_NUM," + "N1,N2,PF,COT,RI,RO,PBV,FCV," + "UPDATE_DATE)" + " values(S_A_DFD_HEAD.nextval," + "?,?,?,?,?,?,?,?,?,?,"
				+ "?,sysdate)";

		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSqlen, msgno, getRow_str(), i + 1, getN1(), getN2(), getPf(), getCot(), getRi(), getRo(), getPbv(), getFcv());

	}

	public String getRow_str() {
		return row_str;
	}

	public void setRow_str(String row_str) {
		this.row_str = row_str;
	}

	public Float getN1() {
		return n1;
	}

	public void setN1(Float n1) {
		this.n1 = n1;
	}

	public Float getN2() {
		return n2;
	}

	public void setN2(Float n2) {
		this.n2 = n2;
	}

	public Float getPf() {
		return pf;
	}

	public void setPf(Float pf) {
		this.pf = pf;
	}

	public Float getCot() {
		return cot;
	}

	public void setCot(Float cot) {
		this.cot = cot;
	}

	public Float getRi() {
		return ri;
	}

	public void setRi(Float ri) {
		this.ri = ri;
	}

	public Float getRo() {
		return ro;
	}

	public void setRo(Float ro) {
		this.ro = ro;
	}

	public Float getPbv() {
		return pbv;
	}

	public void setPbv(Float pbv) {
		this.pbv = pbv;
	}

	public Integer getFcv() {
		return fcv;
	}

	public void setFcv(Integer fcv) {
		this.fcv = fcv;
	}

}
