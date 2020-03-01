package com.apms.bs.dfd;

import java.util.Date;

public class DfdCodeMapVo {
	
	private String id      ; //系统编号       
	private String acmodel ; //飞机型号       
	private String code    ; //报文CODE数值   
	private String code_str; //CODE内容 OR 含义 
	private String isalarm ; //是否告警0-不告警，1-告警    
	private String rptno   ; //报文编号
	private String acnum   ;
	
	private Date dateUtc;//报文时间
	private String msgno;//消息编号
	private String detailMsg;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	public String getAcmodel() {
//		return acmodel;
//	}
//	public void setAcmodel(String acmodel) {
//		this.acmodel = acmodel;
//	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode_str() {
		return code_str;
	}
	public void setCode_str(String code_str) {
		this.code_str = code_str;
	}
	public String getIsalarm() {
		return isalarm;
	}
	public void setIsalarm(String isalarm) {
		this.isalarm = isalarm;
	}
	public String getRptno() {
		return rptno;
	}
	public void setRptno(String rptno) {
		this.rptno = rptno;
	}
	public Date getDateUtc() {
		return dateUtc;
	}
	public void setDateUtc(Date dateUtc) {
		this.dateUtc = dateUtc;
	}
	public String getMsgno() {
		return msgno;
	}
	public void setMsgno(String msgno) {
		this.msgno = msgno;
	}
	public String getDetailMsg() {
		return detailMsg;
	}
	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}
	public String getAcnum() {
		return acnum;
	}
	public void setAcnum(String acnum) {
		this.acnum = acnum;
	}
	
}
