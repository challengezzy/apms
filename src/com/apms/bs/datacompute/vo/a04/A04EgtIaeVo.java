package com.apms.bs.datacompute.vo.a04;

/**
 * 实例分左发和右发
 * @author jerry
 * @date Jun 25, 2016
 */
public class A04EgtIaeVo {
	
	private double theta       ; // 对tat温度修正值                                 
	private double mn          ; // to_number(h.mn)/1000                            
	private double wai         ; // decode(substr(h.bleed_status, 3, 1), '1',1, 0)  
	private double cai         ; // decode(substr(h.bleed_status, 4, 1), '1',1, 0) 
	
	private double eng_bto     ; // 发动机型号计算修正值bt0 setp4                   
	private double eng_bo    ; // 左发修正值step3                                 
	private double egt_co    ; // egt修正后观察值step 2                           
	private double egt_cal   ; // egt计算值 step6                                 
	private double egt_margin; // egt裕度 step 7                                  
	private double t3_co     ; // t3修正后观察值step 2                            
	private double t3_cal    ; // t3计算值 step6 
	private double t25_co     ; // t3修正后观察值step 2                            
	private double t25_cal    ; // t3计算值 step6
	private double egt_act   ; // egt_actual step1                                
	private double ecs       ; // to_number(substr(h.bleed_status, 1, 2))/100 ecs 
	
	private double delta_egt ;
	private double delta_t3 ;
	private double delta_t25 ;
	private double delta_n1 ;
	private double delta_n2 ;
	private double delta_ff ;
	
	public double getEgt_margin() {
		return egt_margin;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getEng_bto() {
		return eng_bto;
	}

	public void setEng_bto(double eng_bto) {
		this.eng_bto = eng_bto;
	}

	public double getEng_bo() {
		return eng_bo;
	}

	public void setEng_bo(double eng_bo) {
		this.eng_bo = eng_bo;
	}

	public double getEgt_co() {
		return egt_co;
	}

	public void setEgt_co(double egt_co) {
		this.egt_co = egt_co;
	}

	public double getEgt_cal() {
		return egt_cal;
	}

	public void setEgt_cal(double egt_cal) {
		this.egt_cal = egt_cal;
	}

	public double getT3_co() {
		return t3_co;
	}

	public void setT3_co(double t3_co) {
		this.t3_co = t3_co;
	}

	public double getT3_cal() {
		return t3_cal;
	}

	public void setT3_cal(double t3_cal) {
		this.t3_cal = t3_cal;
	}


	public double getEgt_act() {
		return egt_act;
	}

	public void setEgt_act(double egt_act) {
		this.egt_act = egt_act;
	}


	public double getMn() {
		return mn;
	}

	public void setMn(double mn) {
		this.mn = mn;
	}

	public double getWai() {
		return wai;
	}

	public void setWai(double wai) {
		this.wai = wai;
	}

	public double getCai() {
		return cai;
	}

	public void setCai(double cai) {
		this.cai = cai;
	}

	public double getEcs() {
		return ecs;
	}

	public void setEcs(double ecs) {
		this.ecs = ecs;
	}

	public void setEgt_margin(double egt_margin) {
		this.egt_margin = egt_margin;
	}

	public double getT25_co() {
		return t25_co;
	}

	public void setT25_co(double t25_co) {
		this.t25_co = t25_co;
	}

	public double getT25_cal() {
		return t25_cal;
	}

	public void setT25_cal(double t25_cal) {
		this.t25_cal = t25_cal;
	}

	public double getDelta_egt() {
		return delta_egt;
	}

	public void setDelta_egt(double delta_egt) {
		this.delta_egt = delta_egt;
	}

	public double getDelta_t3() {
		return delta_t3;
	}

	public void setDelta_t3(double delta_t3) {
		this.delta_t3 = delta_t3;
	}

	public double getDelta_t25() {
		return delta_t25;
	}

	public void setDelta_t25(double delta_t25) {
		this.delta_t25 = delta_t25;
	}

	public double getDelta_n1() {
		return delta_n1;
	}

	public void setDelta_n1(double delta_n1) {
		this.delta_n1 = delta_n1;
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
