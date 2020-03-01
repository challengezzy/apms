package com.apms.bs.apu.vo;

import java.util.Date;

public class A_DFD_A13_COMPUTED {

	// 红线值
	private Double RL_NA;
	private Double RL_STA;
	private Double RL_IGV;
	private Double RL_EGT_COR;
	private Double RL_PT_COR;
	private Double RL_OTA;
	private Double RL_WB_COR;
	private Double RL_APR_COR;

	private Double RL_OT;
	private Double RL_NPA_COR;
	private Double RL_EGTP_COR;
	private Double RL_EGTP;
	private Double RL_NPA;
	private Double RL_EGTA;

	private String DEALSTATUS;
	private String EVENTTYPE;

	private String ID; // ID
	private String MSG_NO; // ACARS报文编号
	private String ACNUM; // 飞机号
	private Date UTCDATE; // 报文时间
	private int ISCHANGEPOINT; // 是否是换发点0-否,1-是
	private Double TAT; // 外界温度
	private Double ALT; // 气压海拔
	private String ASN; // APU序号
	private Double AHRS; // APU小时(分钟)
	private String AHRS_DATE;// 小时-日期

	private Double AHRS_REPAIRED;// 修后运行小时
	private Double ACYC_REPAIRED;// 修后运行循环

	private Double ACYC; // APU循环
	private Double PDI_OLD; // 性能指数
	private Double PDI_NEW; // 性能指数-新
	private String CODE; // 发送代码
	private Double THITA; // THITA值
	private Double THITA_LCIT; // 标态大气环境修正系数LCIT
	private String ESN_MAX; // APU最大EGT时的发动机序号
	private Double NA_MAX; // APU最大EGT时的转速
	private Double EGTA_MAX; // APU最大EGT时的排气温度
	private Double IGV_MAX; // APU最大EGT时的IGV角度
	private Double P2A_MAX; // APU最大EGT时的压气机进口压力
	private Double LCIT_MAX; // APU最大EGT时的压气机进口温度
	private Double WB_MAX; // APU最大EGT时的引气流量 kg/sec
	private Double PT_MAX; // APU最大EGT时的引气压力
	private Double LCDT_MAX; // APU最大EGT时的负载压气机出口温度
	private Double OTA_MAX; // APU最大EGT时的滑油温度
	private Double GLA_MAX; // APU最大EGT时的发电机负载 %
	private Double SCV_MAX; // APU伺服活门角度 A330 A340
	private Double HOT_MAX; // 高位滑油温度 A330 A340
	private Double STA_V1; // APU自启动时间
	private Double STA_V1_EST_TIME; // 基于STA_V1到达红线值剩余时间（分钟）
	private Double EGTP_V1; // APU自启动EGT峰值
	private Double NPA_V1; // APU自启动EGT峰值的转速 单位%
	private Double OTA_V1; // APU自启动滑油温度 A330 A340 LOT
	private Double LCIT_V1; // APU自启动进气口温度

	private Double EGTA_MAX_COR; // 修正海平面标态EGT温度
	private Double EGTA_MAX_COR_EST_TIME; // 基于EGT_MAX_COR到达红线值剩余时间（分钟）

	private Double P2A_MAX_COR; // 修正海平面标态P2A进口压力，根据LCIT/ALT修正
	private Double PT_MAX_COR; // APU最大EGT时的引气压力修正
	private Double WB_MAX_COR; // APU最大EGT时的引气流量 kg/sec 修正值
	private Double PT_MAX_COR_EST_TIME; // 基于PT_MAX_COR到达红线值剩余时间（分钟）
	private Double APR_MAX; // 修正海平面标态APU 引气出口与进口 的增压比PT_MAX_COR/P2A_MAX_COR
	private Double APR_MAX_EST_TIME; // 基于APR_MAX到达红线值剩余时间（分钟） 红线值 为 3.4
	private Double EGTP_V1_COR; // APU自启动EGT峰值海平面标态修正
	private Double NPA_V1_COR; // 修正NPA数据 NPA/T1 流体近似性
	private Double HOT_OT_MAX; // 高位话有温度 HOT-LCIT 的 温度 A330 A340
	private Double OT_MAX; // APU最大EGT时的OTA-LCIT 温度
	private Double OT_MAX_EST_TIME; // 基于OT_MAX到达红线值剩余时间（分钟） 红线值 为 不同型号APU此参数不同
	private Double OT_V1; // APU自启动时滑油增温OTA_V1-LCIT_V1
	private Double RECDATETIME; // 更新时间

	private Double EGTP_V1_COR_ROLL5;
	private Double NPA_V1_COR_ROLL5;
	private Double HOT_OT_MAX_ROLL5;

	// 字段计算值
	private Double EGTA_ROLL5;
	private Double EGTA_POINTTYPE;
	private Double STA_ROLL5;
	private Double STA_POINTTYPE;
	private Double IGV_ROLL5;
	private Double IGV_POINTTYPE;
	private Double P2A_ROLL5;
	private Double P2A_POINTTYPE;
	private Double PT_ROLL5;
	private Double PT_POINTTYPE;
	private Double APR_ROLL5;
	private Double APR_POINTTYPE;
	private Double OT_ROLL5;
	private Double OT_POINTTYPE;
	private Double WB_ROLL5;
	private Double WB_POINTTYPE;
	
	private String ISEXIST;
	private Integer DATATYPE;
	private String COMMENTS;
	private String DIAGNOSETYPE;

	public String getDIAGNOSETYPE() {
		return DIAGNOSETYPE;
	}

	public void setDIAGNOSETYPE(String dIAGNOSETYPE) {
		DIAGNOSETYPE = dIAGNOSETYPE;
	}

	public Integer getDATATYPE() {
		return DATATYPE;
	}

	public void setDATATYPE(Integer dATATYPE) {
		DATATYPE = dATATYPE;
	}

	public String getCOMMENTS() {
		return COMMENTS;
	}

	public void setCOMMENTS(String cOMMENTS) {
		COMMENTS = cOMMENTS;
	}

	public String getISEXIST() {
		return ISEXIST;
	}

	public void setISEXIST(String iSEXIST) {
		ISEXIST = iSEXIST;
	}

	public Double getRL_OT() {
		return RL_OT;
	}

	public void setRL_OT(Double rL_OT) {
		RL_OT = rL_OT;
	}

	public Double getRL_NPA_COR() {
		return RL_NPA_COR;
	}

	public void setRL_NPA_COR(Double rL_NPA_COR) {
		RL_NPA_COR = rL_NPA_COR;
	}

	public Double getRL_EGTP_COR() {
		return RL_EGTP_COR;
	}

	public void setRL_EGTP_COR(Double rL_EGTP_COR) {
		RL_EGTP_COR = rL_EGTP_COR;
	}

	public Double getRL_EGTP() {
		return RL_EGTP;
	}

	public void setRL_EGTP(Double rL_EGTP) {
		RL_EGTP = rL_EGTP;
	}

	public Double getRL_NPA() {
		return RL_NPA;
	}

	public void setRL_NPA(Double rL_NPA) {
		RL_NPA = rL_NPA;
	}

	public Double getRL_EGTA() {
		return RL_EGTA;
	}

	public void setRL_EGTA(Double rL_EGTA) {
		RL_EGTA = rL_EGTA;
	}


	public String getEVENTTYPE() {
		return EVENTTYPE;
	}

	public void setEVENTTYPE(String eVENTTYPE) {
		EVENTTYPE = eVENTTYPE;
	}

	public String getDEALSTATUS() {
		return DEALSTATUS;
	}

	public void setDEALSTATUS(String dEALSTATUS) {
		DEALSTATUS = dEALSTATUS;
	}

	public Double getEGTP_V1_COR_ROLL5() {
		return EGTP_V1_COR_ROLL5;
	}

	public void setEGTP_V1_COR_ROLL5(Double eGTP_V1_COR_ROLL5) {
		EGTP_V1_COR_ROLL5 = eGTP_V1_COR_ROLL5;
	}

	public Double getNPA_V1_COR_ROLL5() {
		return NPA_V1_COR_ROLL5;
	}

	public void setNPA_V1_COR_ROLL5(Double nPA_V1_COR_ROLL5) {
		NPA_V1_COR_ROLL5 = nPA_V1_COR_ROLL5;
	}

	public Double getHOT_OT_MAX_ROLL5() {
		return HOT_OT_MAX_ROLL5;
	}

	public void setHOT_OT_MAX_ROLL5(Double hOT_OT_MAX_ROLL5) {
		HOT_OT_MAX_ROLL5 = hOT_OT_MAX_ROLL5;
	}

	public Double getWB_ROLL5() {
		return WB_ROLL5;
	}

	public void setWB_ROLL5(Double wB_ROLL5) {
		WB_ROLL5 = wB_ROLL5;
	}

	public Double getWB_POINTTYPE() {
		return WB_POINTTYPE;
	}

	public void setWB_POINTTYPE(Double wB_POINTTYPE) {
		WB_POINTTYPE = wB_POINTTYPE;
	}

	public Double getRL_NA() {
		return RL_NA;
	}

	public void setRL_NA(Double rL_NA) {
		RL_NA = rL_NA;
	}

	public Double getRL_STA() {
		return RL_STA;
	}

	public void setRL_STA(Double rL_STA) {
		RL_STA = rL_STA;
	}

	public Double getRL_OTA() {
		return RL_OTA;
	}

	public void setRL_OTA(Double rL_OTA) {
		RL_OTA = rL_OTA;
	}

	public Double getSTA_ROLL5() {
		return STA_ROLL5;
	}

	public void setSTA_ROLL5(Double sTA_ROLL5) {
		STA_ROLL5 = sTA_ROLL5;
	}

	public Double getSTA_POINTTYPE() {
		return STA_POINTTYPE;
	}

	public void setSTA_POINTTYPE(Double sTA_POINTTYPE) {
		STA_POINTTYPE = sTA_POINTTYPE;
	}

	public Double getIGV_ROLL5() {
		return IGV_ROLL5;
	}

	public void setIGV_ROLL5(Double iGV_ROLL5) {
		IGV_ROLL5 = iGV_ROLL5;
	}

	public Double getIGV_POINTTYPE() {
		return IGV_POINTTYPE;
	}

	public void setIGV_POINTTYPE(Double iGV_POINTTYPE) {
		IGV_POINTTYPE = iGV_POINTTYPE;
	}

	public Double getP2A_ROLL5() {
		return P2A_ROLL5;
	}

	public void setP2A_ROLL5(Double p2a_ROLL5) {
		P2A_ROLL5 = p2a_ROLL5;
	}

	public Double getP2A_POINTTYPE() {
		return P2A_POINTTYPE;
	}

	public void setP2A_POINTTYPE(Double p2a_POINTTYPE) {
		P2A_POINTTYPE = p2a_POINTTYPE;
	}

	public Double getPT_ROLL5() {
		return PT_ROLL5;
	}

	public void setPT_ROLL5(Double pT_ROLL5) {
		PT_ROLL5 = pT_ROLL5;
	}

	public Double getPT_POINTTYPE() {
		return PT_POINTTYPE;
	}

	public void setPT_POINTTYPE(Double pT_POINTTYPE) {
		PT_POINTTYPE = pT_POINTTYPE;
	}

	public Double getAPR_ROLL5() {
		return APR_ROLL5;
	}

	public void setAPR_ROLL5(Double aPR_ROLL5) {
		APR_ROLL5 = aPR_ROLL5;
	}

	public Double getAPR_POINTTYPE() {
		return APR_POINTTYPE;
	}

	public void setAPR_POINTTYPE(Double aPR_POINTTYPE) {
		APR_POINTTYPE = aPR_POINTTYPE;
	}

	public Double getOT_ROLL5() {
		return OT_ROLL5;
	}

	public void setOT_ROLL5(Double oT_ROLL5) {
		OT_ROLL5 = oT_ROLL5;
	}

	public Double getOT_POINTTYPE() {
		return OT_POINTTYPE;
	}

	public void setOT_POINTTYPE(Double oT_POINTTYPE) {
		OT_POINTTYPE = oT_POINTTYPE;
	}

	public Double getRL_EGT_COR() {
		return RL_EGT_COR;
	}

	public void setRL_EGT_COR(Double rL_EGT_COR) {
		RL_EGT_COR = rL_EGT_COR;
	}

	public Double getRL_PT_COR() {
		return RL_PT_COR;
	}

	public void setRL_PT_COR(Double rL_PT_COR) {
		RL_PT_COR = rL_PT_COR;
	}

	public Double getRL_IGV() {
		return RL_IGV;
	}

	public void setRL_IGV(Double rL_IGV) {
		RL_IGV = rL_IGV;
	}

	public Double getRL_WB_COR() {
		return RL_WB_COR;
	}

	public void setRL_WB_COR(Double rL_WB_COR) {
		RL_WB_COR = rL_WB_COR;
	}

	public Double getRL_APR_COR() {
		return RL_APR_COR;
	}

	public void setRL_APR_COR(Double rL_APR_COR) {
		RL_APR_COR = rL_APR_COR;
	}

	public Double getAHRS_REPAIRED() {
		return AHRS_REPAIRED;
	}

	public void setAHRS_REPAIRED(Double aHRS_REPAIRED) {
		AHRS_REPAIRED = aHRS_REPAIRED;
	}

	public Double getACYC_REPAIRED() {
		return ACYC_REPAIRED;
	}

	public void setACYC_REPAIRED(Double aCYC_REPAIRED) {
		ACYC_REPAIRED = aCYC_REPAIRED;
	}

	public String getAHRS_DATE() {
		return AHRS_DATE;
	}

	public void setAHRS_DATE(String aHRS_DATE) {
		AHRS_DATE = aHRS_DATE;
	}

	public Double getEGTA_ROLL5() {
		return EGTA_ROLL5;
	}

	public void setEGTA_ROLL5(Double eGTA_ROLL5) {
		EGTA_ROLL5 = eGTA_ROLL5;
	}

	public Double getEGTA_POINTTYPE() {
		return EGTA_POINTTYPE;
	}

	public void setEGTA_POINTTYPE(Double eGTA_POINTTYPE) {
		EGTA_POINTTYPE = eGTA_POINTTYPE;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getMSG_NO() {
		return MSG_NO;
	}

	public void setMSG_NO(String mSG_NO) {
		MSG_NO = mSG_NO;
	}

	public String getACNUM() {
		return ACNUM;
	}

	public void setACNUM(String aCNUM) {
		ACNUM = aCNUM;
	}

	public Date getUTCDATE() {
		return UTCDATE;
	}

	public void setUTCDATE(Date uTCDATE) {
		UTCDATE = uTCDATE;
	}

	public int getISCHANGEPOINT() {
		return ISCHANGEPOINT;
	}

	public void setISCHANGEPOINT(int iSCHANGEPOINT) {
		ISCHANGEPOINT = iSCHANGEPOINT;
	}

	public Double getTAT() {
		return TAT;
	}

	public void setTAT(Double tAT) {
		TAT = tAT;
	}

	public Double getALT() {
		return ALT;
	}

	public void setALT(Double aLT) {
		ALT = aLT;
	}

	public String getASN() {
		return ASN;
	}

	public void setASN(String aSN) {
		ASN = aSN;
	}

	public Double getAHRS() {
		return AHRS;
	}

	public void setAHRS(Double aHRS) {
		AHRS = aHRS;
	}

	public Double getACYC() {
		return ACYC;
	}

	public void setACYC(Double aCYC) {
		ACYC = aCYC;
	}

	public Double getPDI_OLD() {
		return PDI_OLD;
	}

	public void setPDI_OLD(Double pDI_OLD) {
		PDI_OLD = pDI_OLD;
	}

	public Double getPDI_NEW() {
		return PDI_NEW;
	}

	public void setPDI_NEW(Double pDI_NEW) {
		PDI_NEW = pDI_NEW;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public Double getTHITA() {
		return THITA;
	}

	public void setTHITA(Double tHITA) {
		THITA = tHITA;
	}

	public Double getTHITA_LCIT() {
		return THITA_LCIT;
	}

	public void setTHITA_LCIT(Double tHITA_LCIT) {
		THITA_LCIT = tHITA_LCIT;
	}

	public String getESN_MAX() {
		return ESN_MAX;
	}

	public void setESN_MAX(String eSN_MAX) {
		ESN_MAX = eSN_MAX;
	}

	public Double getNA_MAX() {
		return NA_MAX;
	}

	public void setNA_MAX(Double nA_MAX) {
		NA_MAX = nA_MAX;
	}

	public Double getEGTA_MAX() {
		return EGTA_MAX;
	}

	public void setEGTA_MAX(Double eGTA_MAX) {
		EGTA_MAX = eGTA_MAX;
	}

	public Double getIGV_MAX() {
		return IGV_MAX;
	}

	public void setIGV_MAX(Double iGV_MAX) {
		IGV_MAX = iGV_MAX;
	}

	public Double getP2A_MAX() {
		return P2A_MAX;
	}

	public void setP2A_MAX(Double p2a_MAX) {
		P2A_MAX = p2a_MAX;
	}

	public Double getLCIT_MAX() {
		return LCIT_MAX;
	}

	public void setLCIT_MAX(Double lCIT_MAX) {
		LCIT_MAX = lCIT_MAX;
	}

	public Double getWB_MAX() {
		return WB_MAX;
	}

	public void setWB_MAX(Double wB_MAX) {
		WB_MAX = wB_MAX;
	}

	public Double getPT_MAX() {
		return PT_MAX;
	}

	public void setPT_MAX(Double pT_MAX) {
		PT_MAX = pT_MAX;
	}

	public Double getLCDT_MAX() {
		return LCDT_MAX;
	}

	public void setLCDT_MAX(Double lCDT_MAX) {
		LCDT_MAX = lCDT_MAX;
	}

	public Double getOTA_MAX() {
		return OTA_MAX;
	}

	public void setOTA_MAX(Double oTA_MAX) {
		OTA_MAX = oTA_MAX;
	}

	public Double getGLA_MAX() {
		return GLA_MAX;
	}

	public void setGLA_MAX(Double gLA_MAX) {
		GLA_MAX = gLA_MAX;
	}

	public Double getSCV_MAX() {
		return SCV_MAX;
	}

	public void setSCV_MAX(Double sCV_MAX) {
		SCV_MAX = sCV_MAX;
	}

	public Double getHOT_MAX() {
		return HOT_MAX;
	}

	public void setHOT_MAX(Double hOT_MAX) {
		HOT_MAX = hOT_MAX;
	}

	public Double getSTA_V1() {
		return STA_V1;
	}

	public void setSTA_V1(Double sTA_V1) {
		STA_V1 = sTA_V1;
	}

	public Double getSTA_V1_EST_TIME() {
		return STA_V1_EST_TIME;
	}

	public void setSTA_V1_EST_TIME(Double sTA_V1_EST_TIME) {
		STA_V1_EST_TIME = sTA_V1_EST_TIME;
	}

	public Double getEGTP_V1() {
		return EGTP_V1;
	}

	public void setEGTP_V1(Double eGTP_V1) {
		EGTP_V1 = eGTP_V1;
	}

	public Double getNPA_V1() {
		return NPA_V1;
	}

	public void setNPA_V1(Double nPA_V1) {
		NPA_V1 = nPA_V1;
	}

	public Double getOTA_V1() {
		return OTA_V1;
	}

	public void setOTA_V1(Double oTA_V1) {
		OTA_V1 = oTA_V1;
	}

	public Double getLCIT_V1() {
		return LCIT_V1;
	}

	public void setLCIT_V1(Double lCIT_V1) {
		LCIT_V1 = lCIT_V1;
	}

	public Double getEGTA_MAX_COR() {
		return EGTA_MAX_COR;
	}

	public void setEGTA_MAX_COR(Double eGTA_MAX_COR) {
		EGTA_MAX_COR = eGTA_MAX_COR;
	}

	public Double getEGTA_MAX_COR_EST_TIME() {
		return EGTA_MAX_COR_EST_TIME;
	}

	public void setEGTA_MAX_COR_EST_TIME(Double eGTA_MAX_COR_EST_TIME) {
		EGTA_MAX_COR_EST_TIME = eGTA_MAX_COR_EST_TIME;
	}

	public Double getP2A_MAX_COR() {
		return P2A_MAX_COR;
	}

	public void setP2A_MAX_COR(Double p2a_MAX_COR) {
		P2A_MAX_COR = p2a_MAX_COR;
	}

	public Double getPT_MAX_COR() {
		return PT_MAX_COR;
	}

	public void setPT_MAX_COR(Double pT_MAX_COR) {
		PT_MAX_COR = pT_MAX_COR;
	}

	public Double getWB_MAX_COR() {
		return WB_MAX_COR;
	}

	public void setWB_MAX_COR(Double wB_MAX_COR) {
		WB_MAX_COR = wB_MAX_COR;
	}

	public Double getPT_MAX_COR_EST_TIME() {
		return PT_MAX_COR_EST_TIME;
	}

	public void setPT_MAX_COR_EST_TIME(Double pT_MAX_COR_EST_TIME) {
		PT_MAX_COR_EST_TIME = pT_MAX_COR_EST_TIME;
	}

	public Double getAPR_MAX() {
		return APR_MAX;
	}

	public void setAPR_MAX(Double aPR_MAX) {
		APR_MAX = aPR_MAX;
	}

	public Double getAPR_MAX_EST_TIME() {
		return APR_MAX_EST_TIME;
	}

	public void setAPR_MAX_EST_TIME(Double aPR_MAX_EST_TIME) {
		APR_MAX_EST_TIME = aPR_MAX_EST_TIME;
	}

	public Double getEGTP_V1_COR() {
		return EGTP_V1_COR;
	}

	public void setEGTP_V1_COR(Double eGTP_V1_COR) {
		EGTP_V1_COR = eGTP_V1_COR;
	}

	public Double getNPA_V1_COR() {
		return NPA_V1_COR;
	}

	public void setNPA_V1_COR(Double nPA_V1_COR) {
		NPA_V1_COR = nPA_V1_COR;
	}

	public Double getHOT_OT_MAX() {
		return HOT_OT_MAX;
	}

	public void setHOT_OT_MAX(Double hOT_OT_MAX) {
		HOT_OT_MAX = hOT_OT_MAX;
	}

	public Double getOT_MAX() {
		return OT_MAX;
	}

	public void setOT_MAX(Double oT_MAX) {
		OT_MAX = oT_MAX;
	}

	public Double getOT_MAX_EST_TIME() {
		return OT_MAX_EST_TIME;
	}

	public void setOT_MAX_EST_TIME(Double oT_MAX_EST_TIME) {
		OT_MAX_EST_TIME = oT_MAX_EST_TIME;
	}

	public Double getOT_V1() {
		return OT_V1;
	}

	public void setOT_V1(Double oT_V1) {
		OT_V1 = oT_V1;
	}

	public Double getRECDATETIME() {
		return RECDATETIME;
	}

	public void setRECDATETIME(Double rECDATETIME) {
		RECDATETIME = rECDATETIME;
	}
}
