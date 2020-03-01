package com.apms.bs.datacompute.vo;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;

public class A49IaevComputeVo extends DfdComputedVo {
	
	private String esn_1;
	private String esn_2;
	private int isChangePoint1;
	private int isChangePoint2;
	private double div_egt_1;
	private double div_egt_2;
	private double div_epr_1;
	private double div_epr_2;
	private double div_n1_1;
	private double div_n1_2;
	private double div_n2_1;
	private double div_n2_2;
	private double div_ff_1;
	private double div_ff_2;
	private double div_time_1;
	private double div_time_2;
	
	public void insertToTable() throws Exception{
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("INSERT INTO A_DFD_A49IAEV25_COMPUTE(");
		sb.append("ID, MSG_NO, ACNUM, RPTDATE");
		sb.append(",ESN_1,ESN_2,ISCHANGEPOINT1,ISCHANGEPOINT2");
		sb.append(",DIV_EGT_1, DIV_EGT_2, DIV_EPR_1, DIV_EPR_2");
		sb.append(",DIV_N1_1, DIV_N1_2, DIV_N2_1, DIV_N2_2, DIV_FF_1, DIV_FF_2") ;
		sb.append(",DIV_TIME_1, DIV_TIME_2");
		sb.append(",UPDATETIME) values(?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?");
		sb.append(",?,?,?,?,?,?");
		sb.append(",?,?");
		sb.append(",sysdate)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), id,msgno,acnum,date_utc
				,esn_1,esn_2,isChangePoint1,isChangePoint2
				,div_egt_1, div_egt_2,div_epr_1,div_epr_2
				, div_n1_1, div_n1_2, div_n2_1, div_n2_2, div_ff_1, div_ff_2
				, div_time_1,div_time_2
			);
		
		logger.debug("插入A49Iaev ["+msgno+"] 报文计算数据！");
	}

	public String getEsn_1() {
		return esn_1;
	}

	public void setEsn_1(String esn_1) {
		this.esn_1 = esn_1;
	}

	public String getEsn_2() {
		return esn_2;
	}

	public void setEsn_2(String esn_2) {
		this.esn_2 = esn_2;
	}

	public int getIsChangePoint1() {
		return isChangePoint1;
	}

	public void setIsChangePoint1(int isChangePoint1) {
		this.isChangePoint1 = isChangePoint1;
	}

	public int getIsChangePoint2() {
		return isChangePoint2;
	}

	public void setIsChangePoint2(int isChangePoint2) {
		this.isChangePoint2 = isChangePoint2;
	}

	public double getDiv_egt_1() {
		return div_egt_1;
	}

	public void setDiv_egt_1(double div_egt_1) {
		this.div_egt_1 = div_egt_1;
	}

	public double getDiv_egt_2() {
		return div_egt_2;
	}

	public void setDiv_egt_2(double div_egt_2) {
		this.div_egt_2 = div_egt_2;
	}

	public double getDiv_epr_1() {
		return div_epr_1;
	}

	public void setDiv_epr_1(double div_epr_1) {
		this.div_epr_1 = div_epr_1;
	}

	public double getDiv_epr_2() {
		return div_epr_2;
	}

	public void setDiv_epr_2(double div_epr_2) {
		this.div_epr_2 = div_epr_2;
	}

	public double getDiv_n1_1() {
		return div_n1_1;
	}

	public void setDiv_n1_1(double div_n1_1) {
		this.div_n1_1 = div_n1_1;
	}

	public double getDiv_n1_2() {
		return div_n1_2;
	}

	public void setDiv_n1_2(double div_n1_2) {
		this.div_n1_2 = div_n1_2;
	}

	public double getDiv_n2_1() {
		return div_n2_1;
	}

	public void setDiv_n2_1(double div_n2_1) {
		this.div_n2_1 = div_n2_1;
	}

	public double getDiv_n2_2() {
		return div_n2_2;
	}

	public void setDiv_n2_2(double div_n2_2) {
		this.div_n2_2 = div_n2_2;
	}

	public double getDiv_ff_1() {
		return div_ff_1;
	}

	public void setDiv_ff_1(double div_ff_1) {
		this.div_ff_1 = div_ff_1;
	}

	public double getDiv_ff_2() {
		return div_ff_2;
	}

	public void setDiv_ff_2(double div_ff_2) {
		this.div_ff_2 = div_ff_2;
	}

	public double getDiv_time_1() {
		return div_time_1;
	}

	public void setDiv_time_1(double div_time_1) {
		this.div_time_1 = div_time_1;
	}

	public double getDiv_time_2() {
		return div_time_2;
	}

	public void setDiv_time_2(double div_time_2) {
		this.div_time_2 = div_time_2;
	}
	
}
  