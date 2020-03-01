package com.apms.bs.alarm.rule.vo;

import java.util.Date;
import java.util.HashMap;

/**
 * 告警监控对象
 * @author jerry
 * @date Apr 3, 2013
 */
public class AlarmMonitorTargetVo {
	
	private String pkValue;//主键值
	
	private String msgno;//消息编号
	
	private Date dateUtc;//报文时间
	
	private String rptno; //报文编号
	
	private String acnum;//飞机号
	private String acmodel;//机型 
	
	private String devicesn;//设备序列号(APU/发动机序号)
	
	//保存其它参数对象
	
	private double ahrs_inc;
	public double getAhrs_inc() {
		return ahrs_inc;
	}
	public void setAhrs_inc(double ahrs_inc) {
		this.ahrs_inc = ahrs_inc;
	}

	private HashMap<String, Object> paramMap = new HashMap<String, Object>();
	
	public HashMap<String, Object> getParamMap() {
		return paramMap;
	}
	public void addParam(String key,Object value){
		paramMap.put(key, value);
	}
	
	public Object getParam(String key){
		return paramMap.get(key);
	}
	
	public void setBasicAttribure(String pkValue,String msgno,Date dateUtc,String rptno,String acnum,String acmodel,String devicesn){
		this.pkValue = pkValue;
		this.msgno = msgno;
		this.dateUtc = dateUtc;
		this.rptno = rptno;
		this.acnum = acnum;
		this.acmodel = acmodel;
		this.devicesn = devicesn;
		
		
	}
	
	public String getPkValue() {
		return pkValue;
	}

	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}

	public String getMsgno() {
		return msgno;
	}

	public void setMsgno(String msgno) {
		this.msgno = msgno;
	}

	public Date getDateUtc() {
		return dateUtc;
	}

	public void setDateUtc(Date dateUtc) {
		this.dateUtc = dateUtc;
	}

	public String getRptno() {
		return rptno;
	}

	public void setRptno(String rptno) {
		this.rptno = rptno;
	}

	public String getAcnum() {
		return acnum;
	}

	public void setAcnum(String acnum) {
		this.acnum = acnum;
	}

	public String getAcmodel() {
		return acmodel;
	}

	public void setAcmodel(String acmodel) {
		this.acmodel = acmodel;
	}

	public String getDevicesn() {
		return devicesn;
	}

	public void setDevicesn(String devicesn) {
		this.devicesn = devicesn;
	}
	
}
