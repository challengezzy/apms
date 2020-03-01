package com.apms.bs.sysconfig;

import org.simpleframework.xml.Element;

public class ApuConfVarVo {
	
	@Element(required=false)
	private double oid_change_temp = 0.5;//判断APU滑油更换的滑油温度差值

	public double getOid_change_temp() {
		return oid_change_temp;
	}

	public void setOid_change_temp(double oid_change_temp) {
		this.oid_change_temp = oid_change_temp;
	}
	
	

}
