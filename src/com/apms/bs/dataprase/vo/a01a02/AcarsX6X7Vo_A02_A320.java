package com.apms.bs.dataprase.vo.a01a02;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsX6X7Vo_A02_A320 extends AcarsLineDataVo{
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	

	private String thdg;
	private String lonp;
	private String latp;
	private String ws;
	private String wd;
	private String ft;
	private String fd;
	
	public AcarsX6X7Vo_A02_A320(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		if(columns.length==8){
			if("".equals(columns[7])){
				
			}
		}else{
			checkFieldsNum(originalStr,columns, 7);
		}
		thdg= ReportParseUtil.strToStrWithIntPostion(columns[0], 3);
		//经纬度不翻译
		lonp= columns[1];
		latp= columns[2]; 
		ws = ReportParseUtil.getNumberStr(columns[3]);
		wd = ReportParseUtil.getNumberStr(columns[4]);
		ft = ReportParseUtil.getNumberStr(columns[5]);
		fd = ReportParseUtil.strToStrWithIntPostion(columns[6], 1);
	}

	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getOriginalStr() {
		return originalStr;
	}
	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}
	public String getThdg() {
		return thdg;
	}
	public void setThdg(String thdg) {
		this.thdg = thdg;
	}
	public String getLonp() {
		return lonp;
	}
	public void setLonp(String lonp) {
		this.lonp = lonp;
	}
	public String getLatp() {
		return latp;
	}
	public void setLatp(String latp) {
		this.latp = latp;
	}
	public String getWs() {
		return ws;
	}
	public void setWs(String ws) {
		this.ws = ws;
	}
	public String getWd() {
		return wd;
	}
	public void setWd(String wd) {
		this.wd = wd;
	}
	public String getFt() {
		return ft;
	}
	public void setFt(String ft) {
		this.ft = ft;
	}
	public String getFd() {
		return fd;
	}
	public void setFd(String fd) {
		this.fd = fd;
	}

	
}
