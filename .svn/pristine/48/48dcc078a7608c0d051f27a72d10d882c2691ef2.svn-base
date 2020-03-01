package com.apms.vo;

import org.simpleframework.xml.Element;

import com.apms.bs.sysconfig.ApuConfVarVo;
import com.apms.bs.sysconfig.EmailConfVo;
import com.apms.bs.sysconfig.EngineConfVarVo;
import com.apms.bs.sysconfig.OilConfVarVo;
import com.apms.bs.sysconfig.OxygenVarVo;
import com.apms.bs.sysconfig.RegressionVarVo;
import com.apms.bs.sysconfig.ShortMessageVo;


public class SysParamConfVo {
	
	@Element(required=true,name="email")
	private EmailConfVo email; //邮件发送配置
	
	@Element(required=true,name="oxygen")
	private OxygenVarVo oxygenVar;
	
	@Element(required=true,name="math_regression")
	private RegressionVarVo regressionVar;
	
	@Element(required=true,name="sms")
	private ShortMessageVo smsVo;
	
	@Element(required=true,name="oil")
	private OilConfVarVo oilVo;
	
	@Element(required=true,name="apu")
	private ApuConfVarVo apuVo;

	@Element(required=true,name="engine")
	private EngineConfVarVo engineVo;
	
	public OilConfVarVo getOilVo() {
		return oilVo;
	}

	public EngineConfVarVo getEngineVo() {
		return engineVo;
	}

	public void setEngineVo(EngineConfVarVo engineVo) {
		this.engineVo = engineVo;
	}

	public void setOilVo(OilConfVarVo oilVo) {
		this.oilVo = oilVo;
	}

	public EmailConfVo getEmail() {
		return email;
	}

	public void setEmail(EmailConfVo email) {
		this.email = email;
	}

	public OxygenVarVo getOxygenVar() {
		return oxygenVar;
	}

	public void setOxygenVar(OxygenVarVo oxygenVar) {
		this.oxygenVar = oxygenVar;
	}

	public RegressionVarVo getRegressionVar() {
		return regressionVar;
	}

	public void setRegressionVar(RegressionVarVo regressionVar) {
		this.regressionVar = regressionVar;
	}

	public ShortMessageVo getSmsVo() {
		return smsVo;
	}

	public void setSmsVo(ShortMessageVo smsVo) {
		this.smsVo = smsVo;
	}

	public ApuConfVarVo getApuVo() {
		return apuVo;
	}

	public void setApuVo(ApuConfVarVo apuVo) {
		this.apuVo = apuVo;
	}

	
}
