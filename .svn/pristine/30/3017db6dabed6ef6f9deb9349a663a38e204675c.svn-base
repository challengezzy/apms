package com.apms.bs.dataprase.vo;

import java.util.Date;

import org.apache.log4j.Logger;

import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

import smartx.framework.common.vo.NovaLogger;

public class AcarsHeadVoCfd extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String msg_no;//消息编号
	private String flightid;//航班id
	private String flightno;//航班号
	private String acnum;//飞机号
	private String aptcode3;//航班三字码
	private String val_bjs;//接收站点（BJS）
	private String headtime;//报文头时间
	private String rptseq;//序号（航段中报文序号）
	private String rptno;//报文号
	public AcarsHeadVoCfd(String line,String rptYearStr,String msgno,String rptno) throws Exception{
		String[] lines = StringUtil.splitString2Array(line, "\r\n", true);
		for(int i=0;i<lines.length;i++){
			String tempLine=lines[i];
			tempLine=tempLine.trim();
			if(lines[i].startsWith("FI")){
				String[] arr=tempLine.split(" ");
				
				if('0'==arr[1].charAt(2)&&arr[1].indexOf("/AN")>3){//兼容解析‘CA0’的情况
					this.setFlightno(arr[1].substring(0,arr[1].indexOf("0"))+arr[1].substring(arr[1].indexOf("0")+1,arr[1].indexOf("/AN")));//航班号
				}else{
					this.setFlightno(arr[1].substring(0,arr[1].indexOf("/AN")));//航班号
				}
				
				this.setAcnum(arr[2].replace("-", ""));//飞机号
			}
			if(lines[i].startsWith("DT")){
				String[] arrDt=tempLine.split(" ");
				String hourStr=arrDt[3].substring(2,4);
				String minStr=arrDt[3].substring(4,arrDt[3].length());
				if(Integer.parseInt(hourStr)>=24){
					throw new Exception("报头时间的小时为"+hourStr+",超过了23");
				}
				if(Integer.parseInt(minStr)>=60){
					throw new Exception("报头时间的分钟为"+minStr+",超过了59");
				}
				this.setVal_bjs(arrDt[1]);//接收站
				this.setAptcode3(arrDt[2]);//发生航站（机场三字码）
				int year=Integer.parseInt(rptYearStr.substring(0,4));
			    int month=Integer.parseInt(rptYearStr.substring(rptYearStr.indexOf("-")+1,rptYearStr.length()));
				int date=Integer.parseInt(arrDt[3].substring(0, 2)); 
				int hour=Integer.parseInt(arrDt[3].substring(2, 4));
//				if((hour+8)/24>=1){//报文头时间转换成东八区时间=格林时间+8
//					hour=(hour+8)%24;
//					date=date+1;
//				}else{
//					hour=hour+8;
//				}
//				switch(month){
//					case 1:
//					case 3:
//					case 5:
//					case 7:
//					case 8:
//					case 10:
//					case 12:if(date>31){month=month+1;};
//					case 4:
//					case 6:
//					case 9:
//					case 11:if(date>30){month=month+1;};
//					case 2:	
//					if(year%4==0&&year%100!=0||year%400==0){
//						if(date>29){
//							month=month+1;
//						}
//					}else{
//						if(date>28){
//							month=month+1;
//						}
//					};
//				}
//				if(month>12){
//					year=year+1;
//				}
				String headTimeStrTemp=rptYearStr+"-"+String.valueOf(date)+" "+String.valueOf(hour)+":"+arrDt[3].substring(4,6)+":00";
				Date headTime=DateUtil.moveSecond(DateUtil.StringToDate(headTimeStrTemp, "yyyy-MM-dd HH:mm:ss"),8*60*60);
				String headTimeStr=DateUtil.getDateStr(headTime, "yyyy-MM-dd HH:mm:ss");
				this.setHeadtime(headTimeStr);
				this.setRptseq(arrDt[4].substring(1, 3));//航段中报文个数
			}
		}
		this.setRptno(rptno);
		this.setMsg_no(msgno);
	}
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getMsg_no() {
		return msg_no;
	}
	public void setMsg_no(String msg_no) {
		this.msg_no = msg_no;
	}
	public String getFlightid() {
		return flightid;
	}
	public void setFlightid(String flightid) {
		this.flightid = flightid;
	}
	public String getFlightno() {
		return flightno;
	}
	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}
	public String getAcnum() {
		return acnum;
	}
	public void setAcnum(String acnum) {
		this.acnum = acnum;
	}
	public String getAptcode3() {
		return aptcode3;
	}
	public void setAptcode3(String aptcode3) {
		this.aptcode3 = aptcode3;
	}
	public String getVal_bjs() {
		return val_bjs;
	}
	public void setVal_bjs(String val_bjs) {
		this.val_bjs = val_bjs;
	}
	public String getHeadtime() {
		return headtime;
	}
	public void setHeadtime(String headtime) {
		this.headtime = headtime;
	}
	public String getRptseq() {
		return rptseq;
	}
	public void setRptseq(String rptseq) {
		this.rptseq = rptseq;
	}
	public String getRptno() {
		return rptno;
	}
	public void setRptno(String rptno) {
		this.rptno = rptno;
	}
	
}
