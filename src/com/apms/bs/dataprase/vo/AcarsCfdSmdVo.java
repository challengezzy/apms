package com.apms.bs.dataprase.vo;


public class AcarsCfdSmdVo {
	
	private AcarsHeadVoCfd head;//报头
	private String msg_no;//消息编号
	private String apusn;
	private String hours;
	private String attempts;
	private String cycles;
	private String oillevel;
	private String reporttime;//报文时间
	
	public String getApusn() {
		return apusn;
	}
	public void setApusn(String apusn) {
		this.apusn = apusn;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getAttempts() {
		return attempts;
	}
	public void setAttempts(String attempts) {
		this.attempts = attempts;
	}
	public String getCycles() {
		return cycles;
	}
	public void setCycles(String cycles) {
		this.cycles = cycles;
	}
	public String getOillevel() {
		return oillevel;
	}
	public void setOillevel(String oillevel) {
		this.oillevel = oillevel;
	}
	public AcarsHeadVoCfd getHead() {
		return head;
	}
	public void setHead(AcarsHeadVoCfd head) {
		this.head = head;
	}
	public String getMsg_no() {
		return msg_no;
	}
	public void setMsg_no(String msg_no) {
		this.msg_no = msg_no;
	}
	public String getReporttime() {
		return reporttime;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
	
}
