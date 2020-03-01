package com.apms.bs.datacompute.vo;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;


public class A38ComputeVo extends DfdComputedVo{
	
	//原值
	private String asn;
	private double ct5atp;
	private double cptatp;
	private double cwfatp;
	private double igvatp;
	private double bdtmax;
	
	//均值原值标准方差
	FieldComputeVo fieldVo_ct5atp;
	FieldComputeVo fieldVo_cptatp;
	FieldComputeVo fieldVo_cwfatp;
	FieldComputeVo fieldVo_igvatp;
	FieldComputeVo fieldVo_bdtmax; 
	
	public void insertToTable() throws Exception{
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("INSERT INTO A_DFD_A38_COMPUTE(");
		sb.append("ID,MSG_NO,ACNUM,ASN,RPTDATE");
		sb.append(",CT5ATP,CPTATP,CWFATP,IGVATP,BDTMAX");
		sb.append(",UPDATE_DATE) values(?,?,?,?,?,?,?,?,?,?");
		sb.append(",SYSDATE)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), 
				id,msgno,acnum,getAsn(),date_utc,getCt5atp(),getCptatp(),getCwfatp(),getIgvatp(),getBdtmax()
			);
	}

	public String getAsn() {
		return asn;
	}

	public void setAsn(String asn) {
		this.asn = asn;
	}

	public double getCt5atp() {
		return ct5atp;
	}

	public void setCt5atp(double ct5atp) {
		this.ct5atp = ct5atp;
	}

	public double getCptatp() {
		return cptatp;
	}

	public void setCptatp(double cptatp) {
		this.cptatp = cptatp;
	}

	public double getCwfatp() {
		return cwfatp;
	}

	public void setCwfatp(double cwfatp) {
		this.cwfatp = cwfatp;
	}

	public double getIgvatp() {
		return igvatp;
	}

	public void setIgvatp(double igvatp) {
		this.igvatp = igvatp;
	}

	public double getBdtmax() {
		return bdtmax;
	}

	public void setBdtmax(double bdtmax) {
		this.bdtmax = bdtmax;
	}

	public FieldComputeVo getFieldVo_ct5atp() {
		return fieldVo_ct5atp;
	}

	public void setFieldVo_ct5atp(FieldComputeVo fieldVo_ct5atp) {
		this.fieldVo_ct5atp = fieldVo_ct5atp;
	}

	public FieldComputeVo getFieldVo_cptatp() {
		return fieldVo_cptatp;
	}

	public void setFieldVo_cptatp(FieldComputeVo fieldVo_cptatp) {
		this.fieldVo_cptatp = fieldVo_cptatp;
	}

	public FieldComputeVo getFieldVo_cwfatp() {
		return fieldVo_cwfatp;
	}

	public void setFieldVo_cwfatp(FieldComputeVo fieldVo_cwfatp) {
		this.fieldVo_cwfatp = fieldVo_cwfatp;
	}

	public FieldComputeVo getFieldVo_igvatp() {
		return fieldVo_igvatp;
	}

	public void setFieldVo_igvatp(FieldComputeVo fieldVo_igvatp) {
		this.fieldVo_igvatp = fieldVo_igvatp;
	}

	public FieldComputeVo getFieldVo_bdtmax() {
		return fieldVo_bdtmax;
	}

	public void setFieldVo_bdtmax(FieldComputeVo fieldVo_bdtmax) {
		this.fieldVo_bdtmax = fieldVo_bdtmax;
	}

	
}
