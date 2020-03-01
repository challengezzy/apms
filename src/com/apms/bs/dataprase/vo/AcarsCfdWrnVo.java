package com.apms.bs.dataprase.vo;


public class AcarsCfdWrnVo {
	
	private AcarsHeadVoCfd head;
	private String msg_no;
	private String atano_major;
	public String getAtano_major() {
		return atano_major;
	}
	public void setAtano_major(String atano_major) {
		this.atano_major = atano_major;
	}
	public String getAtano_minor() {
		return atano_minor;
	}
	public void setAtano_minor(String atano_minor) {
		this.atano_minor = atano_minor;
	}
	public String getAtano_sub() {
		return atano_sub;
	}
	public void setAtano_sub(String atano_sub) {
		this.atano_sub = atano_sub;
	}
	private String atano_minor;
	private String atano_sub;
	private String phase;
	private String warn_content;
	private String reporttime;
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
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getWarn_content() {
		return warn_content;
	}
	public void setWarn_content(String warn_content) {
		this.warn_content = warn_content;
	}
	public String getReporttime() {
		return reporttime;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
	
	

	
}
