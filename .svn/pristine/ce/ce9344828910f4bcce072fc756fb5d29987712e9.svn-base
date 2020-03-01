package com.apms.bs.apu.vo;

import java.util.Date;

/**
 * APU装机时间段
 * @author jerry
 * @date Apr 23, 2017
 */
public class ApuAcnumTimeScopeVo {

	private String acnum;
	private String apusn;
	private Date installTime;
	private Date removetime;
	
	private double timeOninstall;
	private double cycleOninstall;
	private double timeOnrepaired;
	private double cycleOnrepaired;
	
	private String apuId;
	private String apuModelId;
	private String repairFlag;
	
	/**
	 * 判断时间是否在一对装拆的范围内
	 * @param time
	 * @return
	 */
	public boolean isInScope(Date time){
		boolean isIn = false;
		if(time.before(removetime) && time.after(installTime) ){
			isIn = true;
		}
		
		return isIn;
	}
	
	public String getAcnum() {
		return acnum;
	}
	public void setAcnum(String acnum) {
		this.acnum = acnum;
	}
	public String getApusn() {
		return apusn;
	}
	public void setApusn(String apusn) {
		this.apusn = apusn;
	}
	public Date getInstallTime() {
		return installTime;
	}
	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}
	public Date getRemovetime() {
		return removetime;
	}
	public void setRemovetime(Date removetime) {
		this.removetime = removetime;
	}

	public double getTimeOninstall() {
		return timeOninstall;
	}

	public void setTimeOninstall(double timeOninstall) {
		this.timeOninstall = timeOninstall;
	}

	public double getCycleOninstall() {
		return cycleOninstall;
	}

	public void setCycleOninstall(double cycleOninstall) {
		this.cycleOninstall = cycleOninstall;
	}

	public double getTimeOnrepaired() {
		return timeOnrepaired;
	}

	public void setTimeOnrepaired(double timeOnrepaired) {
		this.timeOnrepaired = timeOnrepaired;
	}

	public double getCycleOnrepaired() {
		return cycleOnrepaired;
	}

	public void setCycleOnrepaired(double cycleOnrepaired) {
		this.cycleOnrepaired = cycleOnrepaired;
	}

	public String getApuId() {
		return apuId;
	}

	public void setApuId(String apuId) {
		this.apuId = apuId;
	}

	public String getApuModelId() {
		return apuModelId;
	}

	public void setApuModelId(String apuModelId) {
		this.apuModelId = apuModelId;
	}

	public String getRepairFlag() {
		return repairFlag;
	}

	public void setRepairFlag(String repairFlag) {
		this.repairFlag = repairFlag;
	}
	
	
}
