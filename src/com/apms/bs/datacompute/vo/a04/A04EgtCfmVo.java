package com.apms.bs.datacompute.vo.a04;

/**
 * CFM报文EGT相关计算值
 * @author jerry
 * @date Jul 1, 2016
 */
public class A04EgtCfmVo {
	
	private double varexp; //取值表格数据后，回归
	private double theta    ; // 对tat温度修正值   
	private double egtk   ; // A.3.1
	private double egtkm2    ; //A.3.4 
	private double hdegtm    ; // A.4 即为egt_margin
	private double sloatl    ; // A.6 sea level oatl
	private double delta_egtm ; // hgegtm-base_egt,
	private double delta_t3 ;
	private double delta_n2 ;
	private double delta_ff ;
	
	public double getVarexp() {
		return varexp;
	}
	public void setVarexp(double varexp) {
		this.varexp = varexp;
	}
	public double getTheta() {
		return theta;
	}
	public void setTheta(double theta) {
		this.theta = theta;
	}
	public double getEgtk() {
		return egtk;
	}
	public void setEgtk(double egtk) {
		this.egtk = egtk;
	}
	public double getEgtkm2() {
		return egtkm2;
	}
	public void setEgtkm2(double egtkm2) {
		this.egtkm2 = egtkm2;
	}
	
	public double getDelta_egtm() {
		return delta_egtm;
	}
	public void setDelta_egtm(double delta_egtm) {
		this.delta_egtm = delta_egtm;
	}
	public double getHdegtm() {
		return hdegtm;
	}
	public void setHdegtm(double hdegtm) {
		this.hdegtm = hdegtm;
	}
	public double getSloatl() {
		return sloatl;
	}
	public void setSloatl(double sloatl) {
		this.sloatl = sloatl;
	}
	
	public double getDelta_t3() {
		return delta_t3;
	}
	public void setDelta_t3(double delta_t3) {
		this.delta_t3 = delta_t3;
	}
	public double getDelta_n2() {
		return delta_n2;
	}
	public void setDelta_n2(double delta_n2) {
		this.delta_n2 = delta_n2;
	}
	public double getDelta_ff() {
		return delta_ff;
	}
	public void setDelta_ff(double delta_ff) {
		this.delta_ff = delta_ff;
	}
	

}
