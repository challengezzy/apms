package com.apms.bs.dataprase.vo;


public class AcarsCfdFlrVo {
	
	private AcarsHeadVoCfd head;//报头
	private String msg_no;//消息编号
	private String atano_major;//ATA主章节
	private String atano_minor;//ATA次章节
	private String atano_sub;//ATA子章节
	
	private String phase;//飞行阶段
	private String fault_content;//故障内容
	private String fault_source;//故障源
	
	
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
	public String getFault_content() {
		return fault_content;
	}
	public void setFault_content(String fault_content) {
		this.fault_content = fault_content;
	}
	public String getFault_source() {
		return fault_source;
	}
	public void setFault_source(String fault_source) {
		this.fault_source = fault_source;
	}
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
	public String getReporttime() {
		return reporttime;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
	
	

	
}
