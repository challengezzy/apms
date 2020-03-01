package com.apms.bs.dataprase.vo;


public class AcarsCfdMpfVo {
	
	private AcarsHeadVoCfd head;//报头
	private String msg_no;//消息编号
	private String aptcode4_dep;//起飞四字石马
	private String aptcode4_arr;//落地四字码
	private String iswarning;//是否有告警
	private String isfault;//是否有故障
	private String reportcontent;//故障内容
	private String reporttime;//报文时间
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
	public String getAptcode4_dep() {
		return aptcode4_dep;
	}
	public void setAptcode4_dep(String aptcode4_dep) {
		this.aptcode4_dep = aptcode4_dep;
	}
	public String getAptcode4_arr() {
		return aptcode4_arr;
	}
	public void setAptcode4_arr(String aptcode4_arr) {
		this.aptcode4_arr = aptcode4_arr;
	}
	public String getIswarning() {
		return iswarning;
	}
	public void setIswarning(String iswarning) {
		this.iswarning = iswarning;
	}
	public String getIsfault() {
		return isfault;
	}
	public void setIsfault(String isfault) {
		this.isfault = isfault;
	}
	public String getReportcontent() {
		return reportcontent;
	}
	public void setReportcontent(String reportcontent) {
		this.reportcontent = reportcontent;
	}
	public String getReporttime() {
		return reporttime;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
	
}
