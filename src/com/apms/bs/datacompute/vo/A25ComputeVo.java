package com.apms.bs.datacompute.vo;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;

public class A25ComputeVo extends DfdComputedVo{
	
	private String esn_1              ; //左发动机序号
	private double oiq1_z3            ; //左发滑油量，做K值回归 对K值做2DETA 对K值独立样本T检验 （从加油点开始算） 
	private double cf_ehrs            ; //左发小时数  
	private double deta_oiq1_fwd      ; //左发滑油添加量   
	private int    deta_oiq1_fwd_s; //左发滑油添加点标记
	private double deta_oiq1_fwdalarm ; //左发滑油添加告警 
	private double deta_oiq1_fwdrate  ; //OIQ1_消耗率滑油消耗率,做方差和样本T检验
	private int is_oiq1_addrpt;        //是否有A27报文
	private String esn_2              ; //右发动机序号
	private double oiq2_z3            ; //右发滑油量，做K值回归 对K值做2DETA 对K值独立样本T检验 （从加油点开始算） 
	private double cg_ehrs            ; //右发小时数  
	private double deta_oiq2_fwd      ; //右发滑油添加量   
	private int    deta_oiq2_fwd_s; //左发滑油添加点标记
	private double deta_oiq2_fwdalarm ; //右发滑油添加告警 
	private double deta_oiq2_fwdrate  ; //OIQ1_消耗率滑油消耗率  
	private int is_oiq2_addrpt;        //是否有A27报文
	private int cnt; 


	public String getEsn_1() {
		return esn_1;
	}
	public void setEsn_1(String esn_1) {
		this.esn_1 = esn_1;
	}
	public double getOiq1_z3() {
		return oiq1_z3;
	}
	public void setOiq1_z3(double oiq1_z3) {
		this.oiq1_z3 = oiq1_z3;
	}
	public double getCf_ehrs() {
		return cf_ehrs;
	}
	public void setCf_ehrs(double cf_ehrs) {
		this.cf_ehrs = cf_ehrs;
	}
	public double getDeta_oiq1_fwd() {
		return deta_oiq1_fwd;
	}
	public void setDeta_oiq1_fwd(double deta_oiq1_fwd) {
		this.deta_oiq1_fwd = deta_oiq1_fwd;
	}
	public double getDeta_oiq1_fwdalarm() {
		return deta_oiq1_fwdalarm;
	}
	public void setDeta_oiq1_fwdalarm(double deta_oiq1_fwdalarm) {
		this.deta_oiq1_fwdalarm = deta_oiq1_fwdalarm;
	}
	public double getDeta_oiq1_fwdrate() {
		return deta_oiq1_fwdrate;
	}
	public void setDeta_oiq1_fwdrate(double deta_oiq1_fwdrate) {
		this.deta_oiq1_fwdrate = deta_oiq1_fwdrate;
	}
	public String getEsn_2() {
		return esn_2;
	}
	public void setEsn_2(String esn_2) {
		this.esn_2 = esn_2;
	}
	public double getOiq2_z3() {
		return oiq2_z3;
	}
	public void setOiq2_z3(double oiq2_z3) {
		this.oiq2_z3 = oiq2_z3;
	}
	public double getCg_ehrs() {
		return cg_ehrs;
	}
	public void setCg_ehrs(double cg_ehrs) {
		this.cg_ehrs = cg_ehrs;
	}
	public double getDeta_oiq2_fwd() {
		return deta_oiq2_fwd;
	}
	public void setDeta_oiq2_fwd(double deta_oiq2_fwd) {
		this.deta_oiq2_fwd = deta_oiq2_fwd;
	}
	public double getDeta_oiq2_fwdalarm() {
		return deta_oiq2_fwdalarm;
	}
	public void setDeta_oiq2_fwdalarm(double deta_oiq2_fwdalarm) {
		this.deta_oiq2_fwdalarm = deta_oiq2_fwdalarm;
	}
	public double getDeta_oiq2_fwdrate() {
		return deta_oiq2_fwdrate;
	}
	public void setDeta_oiq2_fwdrate(double deta_oiq2_fwdrate) {
		this.deta_oiq2_fwdrate = deta_oiq2_fwdrate;
	}
	
	public int getDeta_oiq1_fwd_s() {
		return deta_oiq1_fwd_s;
	}
	public void setDeta_oiq1_fwd_s(int deta_oiq1_fwd_s) {
		this.deta_oiq1_fwd_s = deta_oiq1_fwd_s;
	}
	public int getDeta_oiq2_fwd_s() {
		return deta_oiq2_fwd_s;
	}
	public void setDeta_oiq2_fwd_s(int deta_oiq2_fwd_s) {
		this.deta_oiq2_fwd_s = deta_oiq2_fwd_s;
	}
	
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	
	public int getIs_oiq1_addrpt() {
		return is_oiq1_addrpt;
	}
	public void setIs_oiq1_addrpt(int is_oiq1_addrpt) {
		this.is_oiq1_addrpt = is_oiq1_addrpt;
	}
	public int getIs_oiq2_addrpt() {
		return is_oiq2_addrpt;
	}
	public void setIs_oiq2_addrpt(int is_oiq2_addrpt) {
		this.is_oiq2_addrpt = is_oiq2_addrpt;
	}
	public void insertToTable() throws Exception{
		//TODO 这里补全对表a_dfd_a25_compute 数据的插入
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("insert into a_dfd_a25_compute(");
		sb.append("id,msg_no,acnum,date_utc");
		sb.append(",esn_1,oiq1_z3,cf_ehrs,deta_oiq1_fwd,deta_oiq1_fwd_s,deta_oiq1_fwdalarm,deta_oiq1_fwdrate,is_oiq1_addrpt");
		sb.append(",esn_2,oiq2_z3,cg_ehrs,deta_oiq2_fwd,deta_oiq2_fwd_s,deta_oiq2_fwdalarm,deta_oiq2_fwdrate,is_oiq2_addrpt");
		sb.append(",update_date) values(?,?,?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?,?,?,?,sysdate)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), 
				id,msgno,acnum,date_utc,
				esn_1,oiq1_z3,cf_ehrs,deta_oiq1_fwd,deta_oiq1_fwd_s,deta_oiq1_fwdalarm,deta_oiq1_fwdrate,is_oiq1_addrpt,
				esn_2,oiq2_z3,cg_ehrs,deta_oiq2_fwd,deta_oiq2_fwd_s,deta_oiq2_fwdalarm,deta_oiq2_fwdrate,is_oiq2_addrpt);
		
		logger.debug("插入A25 报文计算数据！");
	}

}
