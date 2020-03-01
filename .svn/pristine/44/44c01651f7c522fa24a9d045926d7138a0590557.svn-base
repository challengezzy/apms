package com.apms.bs.datacompute.vo;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;


public class A40ComputeVo extends DfdComputedVo{
	
	//原值
	private double ff1_avg;
	private double ff2_avg;
	private double pd1_avg;
	private double pd2_avg;
	private double n21_avg;
	private double n22_avg;
	private double ff1_avg_std;
	private double ff2_avg_std;
	private double pd1_avg_std;
	private double pd2_avg_std;
	private double n21_avg_std;
	private double n22_avg_std;
	
	//均值原值标准方差
	FieldComputeVo fieldVo_ff1_avg_std;
	FieldComputeVo fieldVo_ff2_avg_std;
	FieldComputeVo fieldVo_pd1_avg_std;
	FieldComputeVo fieldVo_pd2_avg_std;
	FieldComputeVo fieldVo_n21_avg_std;
	FieldComputeVo fieldVo_n22_avg_std;
	
	//差值
	private double sub_ff1_ff2;
	private double sub_pd1_pd2;
	private double sub_n21_n22;
	private double sub_ff1_ff2_std;
	private double sub_pd1_pd2_std;
	private double sub_n21_n22_std;
	
	public double getFf1_avg_std() {
		return ff1_avg_std;
	}





	public void setFf1_avg_std(double ff1_avg_std) {
		this.ff1_avg_std = ff1_avg_std;
	}





	public double getFf2_avg_std() {
		return ff2_avg_std;
	}





	public void setFf2_avg_std(double ff2_avg_std) {
		this.ff2_avg_std = ff2_avg_std;
	}





	public double getPd1_avg_std() {
		return pd1_avg_std;
	}





	public void setPd1_avg_std(double pd1_avg_std) {
		this.pd1_avg_std = pd1_avg_std;
	}





	public double getPd2_avg_std() {
		return pd2_avg_std;
	}





	public void setPd2_avg_std(double pd2_avg_std) {
		this.pd2_avg_std = pd2_avg_std;
	}





	public double getN21_avg_std() {
		return n21_avg_std;
	}





	public void setN21_avg_std(double n21_avg_std) {
		this.n21_avg_std = n21_avg_std;
	}





	public double getN22_avg_std() {
		return n22_avg_std;
	}





	public void setN22_avg_std(double n22_avg_std) {
		this.n22_avg_std = n22_avg_std;
	}





	public double getSub_ff1_ff2_std() {
		return sub_ff1_ff2_std;
	}





	public void setSub_ff1_ff2_std(double sub_ff1_ff2_std) {
		this.sub_ff1_ff2_std = sub_ff1_ff2_std;
	}





	public double getSub_pd1_pd2_std() {
		return sub_pd1_pd2_std;
	}





	public void setSub_pd1_pd2_std(double sub_pd1_pd2_std) {
		this.sub_pd1_pd2_std = sub_pd1_pd2_std;
	}





	public double getSub_n21_n22_std() {
		return sub_n21_n22_std;
	}





	public void setSub_n21_n22_std(double sub_n21_n22_std) {
		this.sub_n21_n22_std = sub_n21_n22_std;
	}
	//均值差值标准方差
	FieldComputeVo fieldVo_sub_ff1_ff2_std;
	FieldComputeVo fieldVo_sub_pd1_pd2_std;
	FieldComputeVo fieldVo_sub_n21_n22_std;
	
	
	FieldComputeVo fieldVo_ff1_avg;
	FieldComputeVo fieldVo_ff2_avg;
	FieldComputeVo fieldVo_pd1_avg;
	FieldComputeVo fieldVo_pd2_avg;
	FieldComputeVo fieldVo_n21_avg;
	FieldComputeVo fieldVo_n22_avg;
	FieldComputeVo fieldVo_ff1_ff2_avg;
	FieldComputeVo fieldVo_pd1_pd2_avg;
	FieldComputeVo fieldVo_n21_n22_avg;
	
	public void insertToTable() throws Exception{
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("INSERT INTO A_DFD_A40_COMPUTE(");
		sb.append("ID,MSG_NO,ACNUM,RPTDATE");
		sb.append(",FF1_AVG,FF2_AVG,N21_AVG,N22_AVG,PD1_AVG,PD2_AVG");
		sb.append(",SUB_FF1_FF2,SUB_PD1_PD2,SUB_N21_N22");
		sb.append(",FF1_AVG_STD,FF2_AVG_STD,PD1_AVG_STD,PD2_AVG_STD,N21_AVG_STD,N22_AVG_STD");
		sb.append(",SUB_FF1_FF2_STD,SUB_PD1_PD2_STD,SUB_N21_N22_STD");
		sb.append(",UPDATE_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");
		sb.append(",SYSDATE)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), 
				id,msgno,acnum,date_utc,
				getFf1_avg(),getFf2_avg(),getN21_avg(),getN22_avg(),getPd1_avg(),getPd2_avg(),
				getSub_ff1_ff2(),getSub_pd1_pd2(),getSub_n21_n22(),
				getFf1_avg_std(),getFf2_avg_std(),getPd1_avg_std(),getPd2_avg_std(),getN21_avg_std(),getN22_avg_std(),
				getSub_ff1_ff2_std(),getSub_pd1_pd2_std(),getSub_n21_n22_std()
			);
	}
	
	
	


	public FieldComputeVo getFieldVo_ff1_avg_std() {
		return fieldVo_ff1_avg_std;
	}





	public void setFieldVo_ff1_avg_std(FieldComputeVo fieldVo_ff1_avg_std) {
		this.fieldVo_ff1_avg_std = fieldVo_ff1_avg_std;
	}





	public FieldComputeVo getFieldVo_ff2_avg_std() {
		return fieldVo_ff2_avg_std;
	}





	public void setFieldVo_ff2_avg_std(FieldComputeVo fieldVo_ff2_avg_std) {
		this.fieldVo_ff2_avg_std = fieldVo_ff2_avg_std;
	}





	public FieldComputeVo getFieldVo_pd1_avg_std() {
		return fieldVo_pd1_avg_std;
	}





	public void setFieldVo_pd1_avg_std(FieldComputeVo fieldVo_pd1_avg_std) {
		this.fieldVo_pd1_avg_std = fieldVo_pd1_avg_std;
	}





	public FieldComputeVo getFieldVo_pd2_avg_std() {
		return fieldVo_pd2_avg_std;
	}





	public void setFieldVo_pd2_avg_std(FieldComputeVo fieldVo_pd2_avg_std) {
		this.fieldVo_pd2_avg_std = fieldVo_pd2_avg_std;
	}





	public FieldComputeVo getFieldVo_n21_avg_std() {
		return fieldVo_n21_avg_std;
	}





	public void setFieldVo_n21_avg_std(FieldComputeVo fieldVo_n21_avg_std) {
		this.fieldVo_n21_avg_std = fieldVo_n21_avg_std;
	}





	public FieldComputeVo getFieldVo_n22_avg_std() {
		return fieldVo_n22_avg_std;
	}





	public void setFieldVo_n22_avg_std(FieldComputeVo fieldVo_n22_avg_std) {
		this.fieldVo_n22_avg_std = fieldVo_n22_avg_std;
	}





	public FieldComputeVo getFieldVo_sub_ff1_ff2_std() {
		return fieldVo_sub_ff1_ff2_std;
	}





	public void setFieldVo_sub_ff1_ff2_std(FieldComputeVo fieldVo_sub_ff1_ff2_std) {
		this.fieldVo_sub_ff1_ff2_std = fieldVo_sub_ff1_ff2_std;
	}





	public FieldComputeVo getFieldVo_sub_pd1_pd2_std() {
		return fieldVo_sub_pd1_pd2_std;
	}





	public void setFieldVo_sub_pd1_pd2_std(FieldComputeVo fieldVo_sub_pd1_pd2_std) {
		this.fieldVo_sub_pd1_pd2_std = fieldVo_sub_pd1_pd2_std;
	}





	public FieldComputeVo getFieldVo_sub_n21_n22_std() {
		return fieldVo_sub_n21_n22_std;
	}





	public void setFieldVo_sub_n21_n22_std(FieldComputeVo fieldVo_sub_n21_n22_std) {
		this.fieldVo_sub_n21_n22_std = fieldVo_sub_n21_n22_std;
	}





	public FieldComputeVo getFieldVo_ff1_ff2_avg() {
		return fieldVo_ff1_ff2_avg;
	}


	public void setFieldVo_ff1_ff2_avg(FieldComputeVo fieldVo_ff1_ff2_avg) {
		this.fieldVo_ff1_ff2_avg = fieldVo_ff1_ff2_avg;
	}


	public FieldComputeVo getFieldVo_pd1_pd2_avg() {
		return fieldVo_pd1_pd2_avg;
	}
	public void setFieldVo_pd1_pd2_avg(FieldComputeVo fieldVo_pd1_pd2_avg) {
		this.fieldVo_pd1_pd2_avg = fieldVo_pd1_pd2_avg;
	}
	public FieldComputeVo getFieldVo_n21_n22_avg() {
		return fieldVo_n21_n22_avg;
	}
	public void setFieldVo_n21_n22_avg(FieldComputeVo fieldVo_n21_n22_avg) {
		this.fieldVo_n21_n22_avg = fieldVo_n21_n22_avg;
	}
	public double getSub_ff1_ff2() {
		return sub_ff1_ff2;
	}
	public void setSub_ff1_ff2(double sub_ff1_ff2) {
		this.sub_ff1_ff2 = sub_ff1_ff2;
	}
	public double getSub_pd1_pd2() {
		return sub_pd1_pd2;
	}
	public void setSub_pd1_pd2(double sub_pd1_pd2) {
		this.sub_pd1_pd2 = sub_pd1_pd2;
	}
	public double getSub_n21_n22() {
		return sub_n21_n22;
	}
	public void setSub_n21_n22(double sub_n21_n22) {
		this.sub_n21_n22 = sub_n21_n22;
	}
	public double getFf1_avg() {
		return ff1_avg;
	}
	public void setFf1_avg(double ff1_avg) {
		this.ff1_avg = ff1_avg;
	}
	public double getFf2_avg() {
		return ff2_avg;
	}
	public void setFf2_avg(double ff2_avg) {
		this.ff2_avg = ff2_avg;
	}
	public double getPd1_avg() {
		return pd1_avg;
	}
	public void setPd1_avg(double pd1_avg) {
		this.pd1_avg = pd1_avg;
	}
	public double getPd2_avg() {
		return pd2_avg;
	}
	public void setPd2_avg(double pd2_avg) {
		this.pd2_avg = pd2_avg;
	}
	public double getN21_avg() {
		return n21_avg;
	}
	public void setN21_avg(double n21_avg) {
		this.n21_avg = n21_avg;
	}
	public double getN22_avg() {
		return n22_avg;
	}
	public void setN22_avg(double n22_avg) {
		this.n22_avg = n22_avg;
	}
	public FieldComputeVo getFieldVo_ff1_avg() {
		return fieldVo_ff1_avg;
	}
	public void setFieldVo_ff1_avg(FieldComputeVo fieldVo_ff1_avg) {
		this.fieldVo_ff1_avg = fieldVo_ff1_avg;
	}
	public FieldComputeVo getFieldVo_ff2_avg() {
		return fieldVo_ff2_avg;
	}
	public void setFieldVo_ff2_avg(FieldComputeVo fieldVo_ff2_avg) {
		this.fieldVo_ff2_avg = fieldVo_ff2_avg;
	}
	public FieldComputeVo getFieldVo_pd1_avg() {
		return fieldVo_pd1_avg;
	}
	public void setFieldVo_pd1_avg(FieldComputeVo fieldVo_pd1_avg) {
		this.fieldVo_pd1_avg = fieldVo_pd1_avg;
	}
	public FieldComputeVo getFieldVo_pd2_avg() {
		return fieldVo_pd2_avg;
	}
	public void setFieldVo_pd2_avg(FieldComputeVo fieldVo_pd2_avg) {
		this.fieldVo_pd2_avg = fieldVo_pd2_avg;
	}
	public FieldComputeVo getFieldVo_n21_avg() {
		return fieldVo_n21_avg;
	}
	public void setFieldVo_n21_avg(FieldComputeVo fieldVo_n21_avg) {
		this.fieldVo_n21_avg = fieldVo_n21_avg;
	}
	public FieldComputeVo getFieldVo_n22_avg() {
		return fieldVo_n22_avg;
	}
	public void setFieldVo_n22_avg(FieldComputeVo fieldVo_n22_avg) {
		this.fieldVo_n22_avg = fieldVo_n22_avg;
	}
	
}
