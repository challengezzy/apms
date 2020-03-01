package com.apms.bs.datacompute.vo;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;

public class A27ComputeVo extends DfdComputedVo{
	
	private double cl_calga;
	private double cl_calair;
	private double cr_calga;
	private double cr_calair;
	private int oiladd_1;
	private int oiladd_2;
	private double cl_oiqav;
	private double cr_oiqav;
	private String esn_1;
	private String esn_2;
	
	
	public double getCl_calga() {
		return cl_calga;
	}
	public void setCl_calga(double cl_calga) {
		this.cl_calga = cl_calga;
	}
	public double getCl_calair() {
		return cl_calair;
	}
	public void setCl_calair(double cl_calair) {
		this.cl_calair = cl_calair;
	}
	public double getCr_calga() {
		return cr_calga;
	}
	public void setCr_calga(double cr_calga) {
		this.cr_calga = cr_calga;
	}
	public double getCr_calair() {
		return cr_calair;
	}
	public void setCr_calair(double cr_calair) {
		this.cr_calair = cr_calair;
	}	
	public int getOiladd_1() {
		return oiladd_1;
	}
	public void setOiladd_1(int oiladd_1) {
		this.oiladd_1 = oiladd_1;
	}
	public int getOiladd_2() {
		return oiladd_2;
	}
	public void setOiladd_2(int oiladd_2) {
		this.oiladd_2 = oiladd_2;
	}
	public double getCl_oiqav() {
		return cl_oiqav;
	}
	public void setCl_oiqav(double cl_oiqav) {
		this.cl_oiqav = cl_oiqav;
	}
	public double getCr_oiqav() {
		return cr_oiqav;
	}
	public void setCr_oiqav(double cr_oiqav) {
		this.cr_oiqav = cr_oiqav;
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
	
	public void insertToTable() throws Exception{
		//TODO 这里补全对表a_dfd_a27_compute 数据的插入
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("INSERT INTO A_DFD_A27_COMPUTE(");
		sb.append("ID,MSG_NO,ACNUM,DATE_UTC");
		sb.append(",ESN_1,OILADD_ENG1,OIQAV_ENG1,CAL_GA_ENG1,CAL_AIR_ENG1");
		sb.append(",ESN_2,OILADD_ENG2,OIQAV_ENG2,CAL_GA_ENG2,CAL_AIR_ENG2");
		sb.append(",update_date) values(?,?,?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,sysdate)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), 
				id,msgno,acnum,date_utc,
				esn_1,oiladd_1,cl_oiqav,cl_calga,cl_calair,
				esn_2,oiladd_2,cr_oiqav,cr_calga,cr_calair);
		
		logger.debug("插入A27 报文计算数据！");
	}

	
}
