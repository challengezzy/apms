package com.apms.mobile;

public class DefectRemindVo {
	
	private String flt_no;//航班号
	private String acnum;//飞机号
	private String risklevel;//提示级别
	private String status;//状态
	private String reminddesc;//提示信息描述
	public String getFlt_no() {
		return flt_no;
	}
	public void setFlt_no(String flt_no) {
		this.flt_no = flt_no;
	}
	public String getAcnum() {
		return acnum;
	}
	public void setAcnum(String acnum) {
		this.acnum = acnum;
	}
	public String getRisklevel() {
		return risklevel;
	}
	public void setRisklevel(String risklevel) {
		this.risklevel = risklevel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReminddesc() {
		return reminddesc;
	}
	public void setReminddesc(String reminddesc) {
		this.reminddesc = reminddesc;
	}
}
