package com.apms.bs.dataprase.vo;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class AcarsHeadVo extends AcarsLineDataVo {

	private Logger logger = NovaLogger.getLogger(this.getClass());
	// private String originalStr;// 原始报文，如CCB-6048,DEC03,235247,ZSHC,ZBAA,1701

	private Date transdate;// 报文传送日期 yyyy-mm-dd

	private String acid; // 机号
	private String date_utc; // UTC时间
	private Date dateUtc;// UTC时间
	private String from; // 起飞站
	private String to; // 落地站
	private String flt; // 航班号

	private String ph; // 航段
	private String cnt; // 计数
	private String code; // 触发代码
	private String bleed_status; // 引气活门状态
	private String apu; // APU引气活门

	private String exswpn;

	private String tat; // 总温
	private String alt; // 标准高度
	private String cas; // 计算空速
	private String mn; // 马赫速
	private String gw; // 总重
	private String cg; // 重心
	private String dmu; // DMU版本

	private String prv; //
	private String tiebck; //
	private String mod;
	private String ap1;
	private String ap2;

	private String sys;
	private String esn;
	private String ehrs;
	private String ecyc;
	private String ver;

	public void setCC_320(String dataStr) throws Exception {

		String tempStr1 = dataStr.substring(2).trim();
		String columns[];
		if (tempStr1.startsWith(",")) {
			String tempStr = tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		} else if (tempStr1.startsWith(" ")) {
			String tempStr = tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		} else {
			String tempStr = tempStr1;
			if (tempStr.indexOf(",") != -1) {
				columns = StringUtil.splitString2Array(tempStr, ",", true);
			} else {
				columns = StringUtil.splitString2Array(tempStr, " ", true);// 为了兼容A38报文的解析
			}
		}
		checkFieldsNum(tempStr1, columns, 6);
		
		acid = columns[0].replace("-", ""); // TODO 如果飞机号为""， 直接出异常
		from = columns[3];
		to = columns[4];
		flt = columns[5];

		String monthAbbr = columns[1].substring(0, 3);
		String day = columns[1].substring(3, 5);
		String month = DateUtil.abbrMonthToNumber(monthAbbr);

		// jerry 处理报文日期和传送日期跨年的问题
		int rptYear = DateUtil.getYear(transdate);
		int transMonth = DateUtil.getMonth(transdate) + 1;
		try {
			int rptMonth = new Integer(month);
			if (transMonth < rptMonth) {
				rptYear = rptYear - 1;
			}
		} catch (Exception e) {
			throw new Exception("date_utc中月份字符串转化成int类型时出现异常！");
		}
		date_utc = rptYear + "-" + month + "-" + day + " " + columns[2].substring(0, 2) + ":" + columns[2].substring(2, 4) + ":" + columns[2].substring(4, 6);

		try {
			dateUtc = DateUtil.StringToDate(date_utc, "yyyy-MM-dd HH:mm:ss");
			//zhangzy 不需要转换为北京时间
			//dateUtc = DateUtil.moveSecond(dateUtc, 60*60*8);
		} catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}

	}

	public void setC1_320(String dataStr) throws Exception {
		//logger.debug("setC1_320: " + dataStr);

		String tempStr1 = dataStr.substring(2).trim();
		String columns[];
		if (tempStr1.startsWith(",")) {
			String tempStr = tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		} else if (tempStr1.startsWith(" ")) {
			String tempStr = tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		} else {
			String tempStr = tempStr1;
			if (tempStr.indexOf(",") != -1) {
				columns = StringUtil.splitString2Array(tempStr, ",", true);
			} else {
				columns = StringUtil.splitString2Array(tempStr, " ", true);// 兼容A38
			}
		}
		checkFieldsNum(tempStr1, columns, 9);
		ph = columns[0];
		cnt = ReportParseUtil.strToInteger(columns[1], "CNT", true).toString();
		code = columns[2];
		bleed_status = columns[3] + columns[4] + columns[5] + columns[6] + columns[7];
		apu = columns[8];

	}

	public void setC0_320(String dataStr) {
		String tempStr1 = dataStr.substring(2).trim();

		if (tempStr1.startsWith(",")) {
			String tempStr = tempStr1.substring(1);
			exswpn = tempStr;
		} else if (tempStr1.startsWith(" ")) {
			String tempStr = tempStr1.substring(1);
			exswpn = tempStr;
		} else {
			exswpn = tempStr1;
		}
	}

	public void setCE_320(String dataStr) throws Exception {

		String tempStr1 = dataStr.substring(2).trim();
		String columns[];
		if (tempStr1.startsWith(",")) {
			String tempStr = tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		} else if (tempStr1.startsWith(" ")) {
			String tempStr = tempStr1.substring(1);
			if (tempStr.indexOf(",") != -1) {
				columns = StringUtil.splitString2Array(tempStr, ",", true);
			} else {
				columns = StringUtil.splitString2Array(tempStr, " ", true);
			}
		} else {
			String tempStr = tempStr1;
			if (tempStr.indexOf(",") != -1) {
				columns = StringUtil.splitString2Array(tempStr, ",", true);
			} else {
				columns = StringUtil.splitString2Array(tempStr, " ", true);
			}
		}
		checkFieldsNum(tempStr1, columns, 7);
		if (columns[0].contains("---") || columns[0].contains("XXX")) {
			tat = "25";
		} else {
			tat = ReportParseUtil.getNumberStr(columns[0]);
			tat = tat.substring(0, tat.length() - 1) + "." + tat.substring(tat.length() - 1, tat.length());

		}
		if (columns[1].contains("---") || columns[1].contains("XXX")) {
			alt = "0";
		} else {
			alt = columns[1];
			if (alt.startsWith("N")) {
				alt = "-" + alt.substring(1);// 去年前面的N,加负号
			}
		}
		if (columns[2].contains("---") || columns[2].contains("XXX")) {
			cas = "0";
		} else {
			cas = columns[2];
		}
		if (columns[3].contains("---") || columns[3].contains("XXX")) {
			mn = "0";
		} else {
			mn = new Integer(columns[3]).toString();
		}
		if (columns[4].contains("---") || columns[4].contains("XXX")) {
			gw = "0";
		} else {
			gw = new Integer(columns[4]).toString();
		}
		if (columns[5].contains("---") | columns[5].contains("XXX")) {
			cg = "0";
		} else {
			cg = columns[5].substring(0, 2) + "." + columns[5].substring(2, columns[5].length());
		}
		dmu = columns[6];
		if(dmu!=null){
			if(dmu.endsWith("/")){
				dmu=dmu.substring(0, dmu.length()-1);
			}
		}
		
	}

	public void setEC_320(String dataStr) {

		String tempStr1 = dataStr.substring(2);
		String columns[];
		if (tempStr1.startsWith(",")) {
			String tempStr = tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		} else if (tempStr1.startsWith(" ")) {
			String tempStr = tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		} else {
			String tempStr = tempStr1;
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		}

		dmu = ReportParseUtil.getNumberStr(columns[3]);
		if (columns[4].contains("---") || columns[4].contains("XXX")) {
			ver = null;
		} else {
			ver = columns[4].substring(0, 2) + "." + columns[4].substring(2, columns[4].length());
		}
	}

	public void setC1_330(String dataStr) throws Exception{
		String tempStr = dataStr.replace("\r", "").trim().substring(4);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);

		// B-2387,13APR24,09.24.58
		acid = columns[0].replace("-", "");

		String year = "20" + columns[1].substring(0, 2);
		String month = DateUtil.abbrMonthToNumber(columns[1].substring(2, 5));
		String day = columns[1].substring(5, 7);

		date_utc = year + "-" + month + "-" + day + " " + columns[2].substring(0, 2) + ":" + columns[2].substring(3, 5) + ":" + columns[2].substring(6, 8);

		try {
			dateUtc = DateUtil.StringToDate(date_utc, "yyyy-MM-dd HH:mm:ss");
			//zhangzy 转换为北京时间 
			//dateUtc = DateUtil.moveSecond(dateUtc, 60*60*8);
		} catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}
		from = columns[3];
		to = columns[4];
		flt = columns[5];
		code = columns[6];
		cnt = ReportParseUtil.strToInteger( ReportParseUtil.getNumberStr(columns[7]), "CNT", true).toString();
	}

	public void setC2_330(String dataStr) {
		String tempStr = dataStr.replace("\r", "").trim().substring(3);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);

		prv = ReportParseUtil.getNumberStr(columns[0]);
		ph = ReportParseUtil.getNumberStr(columns[1]);
		tiebck = ReportParseUtil.getNumberStr(columns[2]);
		dmu = ReportParseUtil.getNumberStr(columns[3]);
		mod = ReportParseUtil.getNumberStr(columns[4]);
		ap1 = ReportParseUtil.getNumberStr(columns[5]);
		ap2 = ReportParseUtil.getNumberStr(columns[6]);
	}

	public void setC3_330(String dataStr) {
		String tempStr = dataStr.replace("\r", "").trim().substring(3);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);

		if (columns[0].contains("---") || columns[0].contains("XXX")) {
			tat = "25"; // 外界温度，如果没有值取默认值25
		} else {
			// 330飞机报文中，tat的数据已经包含小数点
			tat = ReportParseUtil.getNumberStr(columns[0]);
			// tat = tat.substring(0,tat.length()-1)+"." +
			// tat.substring(tat.length()-1,tat.length());
		}

		if (columns[1].contains("---") || columns[1].contains("XXX")) {
			alt = "0"; // 海拔，如果没有值取默认值0
		} else {
			alt = ReportParseUtil.getNumberStr(columns[1]);
		}

		mn = ReportParseUtil.getNumberStr(columns[2]);
		sys = ReportParseUtil.getNumberStr(columns[3]);
		bleed_status = columns[4] + " " + columns[5] + " " + columns[6] + " " + columns[7] + " " + columns[8] + " " + columns[9] + " " + columns[10];
		apu = ReportParseUtil.getNumberStr(columns[11]);
	}

	public void setC1_340(String dataStr) {

		String tempStr = dataStr.replace("\r", "").trim().substring(1);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);

		acid = columns[0].replace("-", "");

		// B-2387,13APR24,09.24.58
		String year = "20" + columns[1].substring(0, 2);
		String month = DateUtil.abbrMonthToNumber(columns[1].substring(2, 5));
		String day = columns[1].substring(5, 7);

		date_utc = year + "-" + month + "-" + day + " " + columns[2].substring(0, 2) + ":" + columns[2].substring(3, 5) + ":" + columns[2].substring(6, 8);

		try {
			dateUtc = DateUtil.StringToDate(date_utc, "yyyy-MM-dd HH:mm:ss");
			//zhangzy 转换为北京时间 
			//dateUtc = DateUtil.moveSecond(dateUtc, 60*60*8);
		} catch (Exception e) {
			logger.debug("date_utc转换为日期异常！");
		}
		from = columns[3];
		to = columns[4];
		flt = columns[5].trim();
		code = columns[6];
		cnt = ReportParseUtil.getNumberStr(columns[7]);
	}

	public void setC2_340(String dataStr) {
		String tempStr = dataStr.replace("\r", "").trim();
		// String tempStr = dataStr.substring(3);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);

		prv = columns[0];
		ph = columns[1];
		tiebck = columns[2];
		dmu = columns[3] + columns[4] + columns[5];
		mod = columns[6];
		ap1 = columns[7];
		ap2 = columns[8];
	}

	public void setC3_340(String dataStr) {
		String tempStr = dataStr.replace("\r", "").trim();
		// String tempStr = dataStr.substring(3);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true);

		if (columns[0].contains("---") || columns[0].contains("XXX")) {
			tat = "25";// 默认总温
		} else {
			// 330飞机报文中，tat的数据已经包含小数点
			tat = ReportParseUtil.getNumberStr(columns[0]);
			// tat = tat.substring(0,tat.length()-1)+"." +
			// tat.substring(tat.length()-1,tat.length());
		}
		if (columns[0].contains("---") || columns[0].contains("XXX")) {
			alt = "0";
		} else {
			alt = ReportParseUtil.getNumberStr(columns[1]);
		}

		mn = ReportParseUtil.getNumberStr(columns[2]);
		sys = columns[3];
		bleed_status = columns[4] + " " + columns[5] + " " + columns[6] + " " + columns[7] + " " + columns[8] + " " + columns[9] + " " + columns[10];
		apu = columns[11];
	}

	public String getExswpn() {
		return exswpn;
	}

	public void setExswpn(String exswpn) {
		this.exswpn = exswpn;
	}

	public Date getTransdate() {
		return transdate;
	}

	public void setTransdate(Date transdate) {
		this.transdate = transdate;
	}

	public String getAcid() {
		return acid;
	}

	public void setAcid(String acid) {
		this.acid = acid;
	}

	public String getDate_utc() {
		return date_utc;
	}

	public void setDate_utc(String date_utc) {
		this.date_utc = date_utc;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFlt() {
		return flt;
	}

	public void setFlt(String flt) {
		this.flt = flt;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBleed_status() {
		return bleed_status;
	}

	public void setBleed_status(String bleed_status) {
		this.bleed_status = bleed_status;
	}

	public String getApu() {
		return apu;
	}

	public void setApu(String apu) {
		this.apu = apu;
	}

	public String getTat() {
		return tat;
	}

	public void setTat(String tat) {
		this.tat = tat;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getCas() {
		return cas;
	}

	public void setCas(String cas) {
		this.cas = cas;
	}

	public String getMn() {
		return mn;
	}

	public void setMn(String mn) {
		this.mn = mn;
	}

	public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}

	public String getCg() {
		return cg;
	}

	public void setCg(String cg) {
		this.cg = cg;
	}

	public String getDmu() {
		return dmu;
	}

	public void setDmu(String dmu) {
		this.dmu = dmu;
	}

	public Date getDateUtc() {
		return dateUtc;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getPrv() {
		return prv;
	}

	public void setPrv(String prv) {
		this.prv = prv;
	}

	public String getTiebck() {
		return tiebck;
	}

	public void setTiebck(String tiebck) {
		this.tiebck = tiebck;
	}

	public String getMod() {
		return mod;
	}

	public void setMod(String mod) {
		this.mod = mod;
	}

	public String getAp1() {
		return ap1;
	}

	public void setAp1(String ap1) {
		this.ap1 = ap1;
	}

	public String getAp2() {
		return ap2;
	}

	public void setAp2(String ap2) {
		this.ap2 = ap2;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public void setDateUtc(Date dateUtc) {
		this.dateUtc = dateUtc;
	}

	public String getEsn() {
		return esn;
	}

	public void setEsn(String esn) {
		this.esn = esn;
	}

	public String getEhrs() {
		return ehrs;
	}

	public void setEhrs(String ehrs) {
		this.ehrs = ehrs;
	}

	public String getEcyc() {
		return ecyc;
	}

	public void setEcyc(String ecyc) {
		this.ecyc = ecyc;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

}
