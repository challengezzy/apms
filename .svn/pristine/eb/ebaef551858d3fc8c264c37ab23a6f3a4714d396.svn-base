package com.apms.bs.dataprase.vo.a19a21a24;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsVxVo_A19_A320 extends AcarsLineDataVo{
	private Float tat;
	private Float sat; 
	private Integer zcb;
	private Integer zld;
	private Float sc1;
	private Float sc2;
	private Float sc3;
	private Integer rv;
	private String row_str;
	
	public AcarsVxVo_A19_A320(String str, String Vx) throws Exception{
		row_str = Vx;
		originalStr = str;
		
		String tempStr = str.substring(2).trim();
		String[] columns; 
		columns = StringUtil.splitString2Array(tempStr, ",", true);			
		checkFieldsNum(originalStr,columns, 8);
		
		tat = ReportParseUtil.strToFloatWithIntPostion(columns[0], 3, "tat");
		sat = ReportParseUtil.strToFloatWithIntPostion(columns[1], 3, "sat");
		zcb = ReportParseUtil.strToInteger(columns[2], "zcb");
		zld = ReportParseUtil.strToInteger(columns[3], "zld");
		
		sc1 = ReportParseUtil.strToFloatWithIntPostion(columns[4], 2, "sc1");
		sc2 = ReportParseUtil.strToFloatWithIntPostion(columns[5], 2, "sc2");
		sc3 = ReportParseUtil.strToFloatWithIntPostion(columns[6], 2, "sc3");
		rv = ReportParseUtil.strToInteger(columns[7], "rv");
	}
	
	public void insertToTable(String msgno,AcarsHeadVo headVo,int i) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSqlv = "insert into a_dfd_a19_list_v_row(ID,MSG_NO,ROW_STR,ROW_NUM,"
			+ "TAT,SAT,ZCB,ZLD,SC1,SC2,SC3,RV,"
			+ "UPDATE_DATE)" 
			+ " values(S_A_DFD_HEAD.nextval," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,sysdate)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSqlv, msgno,getRow_str(),i+1,
				getTat(),getSat(),getZcb(),getZld(),getSc1(),getSc2(),getSc3(),getRv());
		
	}

	public Float getTat() {
		return tat;
	}

	public void setTat(Float tat) {
		this.tat = tat;
	}

	public Float getSat() {
		return sat;
	}

	public void setSat(Float sat) {
		this.sat = sat;
	}

	public Integer getZcb() {
		return zcb;
	}

	public void setZcb(Integer zcb) {
		this.zcb = zcb;
	}

	public Integer getZld() {
		return zld;
	}

	public void setZld(Integer zld) {
		this.zld = zld;
	}

	public Float getSc1() {
		return sc1;
	}

	public void setSc1(Float sc1) {
		this.sc1 = sc1;
	}

	public Float getSc2() {
		return sc2;
	}

	public void setSc2(Float sc2) {
		this.sc2 = sc2;
	}

	public Float getSc3() {
		return sc3;
	}

	public void setSc3(Float sc3) {
		this.sc3 = sc3;
	}

	public Integer getRv() {
		return rv;
	}

	public void setRv(Integer rv) {
		this.rv = rv;
	}

	public String getRow_str() {
		return row_str;
	}

	public void setRow_str(String row_str) {
		this.row_str = row_str;
	}

}
