package com.apms.bs.oxygen.vo;

public class A_DFD_A23_COMPUTE
{
	private String ID;
	private String MSG_NO;
	private String DATE_UTC;
	
	//原始采集数据,标态压力、客舱温度、外界温度
	private Double CKPTT_MID_S13; 
	private Double SAT_MID_S13  ; 
	private Double PRES_MID_S13 ; 
	private Double CKPTT_MID_S46; 
	private Double SAT_MID_S46  ; 
	private Double PRES_MID_S46 ;  
	
	private Double PRES_ST_C15_S13;//S13标态压力
	private Double PRES_ST_C15_S13_ROLL5;
	private String DATETIME_MID_S13;
	private long DATETIME_MID_S13_MIS; //S13 时间 毫秒数
	
	private Double PRES_ST_C15_S46;//S46标态压力
	private Double PRES_ST_C15_S46_ROLL5;
	private String DATETIME_MID_S46;
	private long DATETIME_MID_S46_MIS;
	
	private Double PRES_ST_S46_K;//S46基于时间N点斜率
	private int PRES_ST_S46_K_OUT;
	private int PRES_ST_S46_K_TSMP_ALTER;
	
	private Double DETA_PRES_FWD;//与前一报文掉压率
	private Double DETA_PRES_FWD_ROLL5;
	private int DETA_PRES_FWD_S;//换瓶点标志
	
	private Double DETA_PRESRATE_ST; //本报文掉压率
	private Double DETA_PRESRATE_ST_ROLL5;
	private int DETA_PRESRATE_ST_OUT;
	private String DETA_PRESRATE_REMARK;
	private int DETA_PRESRATE_ST_TSMP_ALTER;
	
	private Double DETA_PRESRATE_STS46H24;//24小时掉压率
	private Double DETA_PRESRATE_STS46H24_ROLL5;
	private String DETA_PRES_REMARKS46H24;
	private int DETA_PRESRATE_H24_TSMP_ALTER;
	
	private int EVENTTYPE;
	private String MARKMEMO;
	private String MARKER;
	private String MARKTIME;
	private Integer	DETA_PRESRATE_ST_POINTTYPE;
	
	//标识斜率的点，使用k和lineValue 展示
	private Double k; //斜率，原数据库中保存的是 psi/ms,应该转换为psi/24h(day) 
	private Double lineValue;	//一元回归线上的起点或终点
	
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
	public String getDATE_UTC() {
		return DATE_UTC;
	}
	public void setDATE_UTC(String dATE_UTC) {
		DATE_UTC = dATE_UTC;
	}
	public Double getPRES_ST_C15_S13() {
		return PRES_ST_C15_S13;
	}
	public void setPRES_ST_C15_S13(Double pRES_ST_C15_S13) {
		PRES_ST_C15_S13 = pRES_ST_C15_S13;
	}
	public Double getPRES_ST_C15_S13_ROLL5() {
		return PRES_ST_C15_S13_ROLL5;
	}
	public void setPRES_ST_C15_S13_ROLL5(Double pRES_ST_C15_S13_ROLL5) {
		PRES_ST_C15_S13_ROLL5 = pRES_ST_C15_S13_ROLL5;
	}
	public String getDATETIME_MID_S13() {
		return DATETIME_MID_S13;
	}
	public void setDATETIME_MID_S13(String dATETIME_MID_S13) {
		DATETIME_MID_S13 = dATETIME_MID_S13;
	}
	public long getDATETIME_MID_S13_MIS() {
		return DATETIME_MID_S13_MIS;
	}
	public void setDATETIME_MID_S13_MIS(long dATETIME_MID_S13_MIS) {
		DATETIME_MID_S13_MIS = dATETIME_MID_S13_MIS;
	}
	public Double getPRES_ST_C15_S46() {
		return PRES_ST_C15_S46;
	}
	public void setPRES_ST_C15_S46(Double pRES_ST_C15_S46) {
		PRES_ST_C15_S46 = pRES_ST_C15_S46;
	}
	public Double getPRES_ST_C15_S46_ROLL5() {
		return PRES_ST_C15_S46_ROLL5;
	}
	public void setPRES_ST_C15_S46_ROLL5(Double pRES_ST_C15_S46_ROLL5) {
		PRES_ST_C15_S46_ROLL5 = pRES_ST_C15_S46_ROLL5;
	}
	public String getDATETIME_MID_S46() {
		return DATETIME_MID_S46;
	}
	public void setDATETIME_MID_S46(String dATETIME_MID_S46) {
		DATETIME_MID_S46 = dATETIME_MID_S46;
	}
	public long getDATETIME_MID_S46_MIS() {
		return DATETIME_MID_S46_MIS;
	}
	public void setDATETIME_MID_S46_MIS(long dATETIME_MID_S46_MIS) {
		DATETIME_MID_S46_MIS = dATETIME_MID_S46_MIS;
	}
	public Double getPRES_ST_S46_K() {
		return PRES_ST_S46_K;
	}
	public void setPRES_ST_S46_K(Double pRES_ST_S46_K) {
		PRES_ST_S46_K = pRES_ST_S46_K;
	}
	public Double getDETA_PRES_FWD() {
		return DETA_PRES_FWD;
	}
	public void setDETA_PRES_FWD(Double dETA_PRES_FWD) {
		DETA_PRES_FWD = dETA_PRES_FWD;
	}
	public Double getDETA_PRES_FWD_ROLL5() {
		return DETA_PRES_FWD_ROLL5;
	}
	public void setDETA_PRES_FWD_ROLL5(Double dETA_PRES_FWD_ROLL5) {
		DETA_PRES_FWD_ROLL5 = dETA_PRES_FWD_ROLL5;
	}
	public Double getDETA_PRESRATE_ST() {
		return DETA_PRESRATE_ST;
	}
	public void setDETA_PRESRATE_ST(Double dETA_PRESRATE_ST) {
		DETA_PRESRATE_ST = dETA_PRESRATE_ST;
	}
	public Double getDETA_PRESRATE_ST_ROLL5() {
		return DETA_PRESRATE_ST_ROLL5;
	}
	public void setDETA_PRESRATE_ST_ROLL5(Double dETA_PRESRATE_ST_ROLL5) {
		DETA_PRESRATE_ST_ROLL5 = dETA_PRESRATE_ST_ROLL5;
	}
	public String getDETA_PRESRATE_REMARK() {
		return DETA_PRESRATE_REMARK;
	}
	public void setDETA_PRESRATE_REMARK(String dETA_PRESRATE_REMARK) {
		DETA_PRESRATE_REMARK = dETA_PRESRATE_REMARK;
	}
	public Double getDETA_PRESRATE_STS46H24() {
		return DETA_PRESRATE_STS46H24;
	}
	public void setDETA_PRESRATE_STS46H24(Double dETA_PRESRATE_STS46H24) {
		DETA_PRESRATE_STS46H24 = dETA_PRESRATE_STS46H24;
	}
	public Double getDETA_PRESRATE_STS46H24_ROLL5() {
		return DETA_PRESRATE_STS46H24_ROLL5;
	}
	public void setDETA_PRESRATE_STS46H24_ROLL5(Double dETA_PRESRATE_STS46H24_ROLL5) {
		DETA_PRESRATE_STS46H24_ROLL5 = dETA_PRESRATE_STS46H24_ROLL5;
	}
	public String getDETA_PRES_REMARKS46H24() {
		return DETA_PRES_REMARKS46H24;
	}
	public void setDETA_PRES_REMARKS46H24(String dETA_PRES_REMARKS46H24) {
		DETA_PRES_REMARKS46H24 = dETA_PRES_REMARKS46H24;
	}
	public String getMARKER() {
		return MARKER;
	}
	
	public void setMARKER(String mARKER) {
		MARKER = mARKER;
	}
	
	public int getPRES_ST_S46_K_TSMP_ALTER() {
		return PRES_ST_S46_K_TSMP_ALTER;
	}
	public void setPRES_ST_S46_K_TSMP_ALTER(int pRES_ST_S46_K_TSMP_ALTER) {
		PRES_ST_S46_K_TSMP_ALTER = pRES_ST_S46_K_TSMP_ALTER;
	}
	public int getDETA_PRESRATE_ST_TSMP_ALTER() {
		return DETA_PRESRATE_ST_TSMP_ALTER;
	}
	public void setDETA_PRESRATE_ST_TSMP_ALTER(int dETA_PRESRATE_ST_TSMP_ALTER) {
		DETA_PRESRATE_ST_TSMP_ALTER = dETA_PRESRATE_ST_TSMP_ALTER;
	}
	public int getDETA_PRESRATE_H24_TSMP_ALTER() {
		return DETA_PRESRATE_H24_TSMP_ALTER;
	}
	public void setDETA_PRESRATE_H24_TSMP_ALTER(int dETA_PRESRATE_H24_TSMP_ALTER) {
		DETA_PRESRATE_H24_TSMP_ALTER = dETA_PRESRATE_H24_TSMP_ALTER;
	}
	public int getPRES_ST_S46_K_OUT() {
		return PRES_ST_S46_K_OUT;
	}
	public void setPRES_ST_S46_K_OUT(int pRES_ST_S46_K_OUT) {
		PRES_ST_S46_K_OUT = pRES_ST_S46_K_OUT;
	}
	public int getDETA_PRESRATE_ST_OUT() {
		return DETA_PRESRATE_ST_OUT;
	}
	public void setDETA_PRESRATE_ST_OUT(int dETA_PRESRATE_ST_OUT) {
		DETA_PRESRATE_ST_OUT = dETA_PRESRATE_ST_OUT;
	}
	public Double getCKPTT_MID_S13() {
		return CKPTT_MID_S13;
	}
	public void setCKPTT_MID_S13(Double cKPTT_MID_S13) {
		CKPTT_MID_S13 = cKPTT_MID_S13;
	}
	public Double getSAT_MID_S13() {
		return SAT_MID_S13;
	}
	public void setSAT_MID_S13(Double sAT_MID_S13) {
		SAT_MID_S13 = sAT_MID_S13;
	}
	public Double getPRES_MID_S13() {
		return PRES_MID_S13;
	}
	public void setPRES_MID_S13(Double pRES_MID_S13) {
		PRES_MID_S13 = pRES_MID_S13;
	}
	public Double getCKPTT_MID_S46() {
		return CKPTT_MID_S46;
	}
	public void setCKPTT_MID_S46(Double cKPTT_MID_S46) {
		CKPTT_MID_S46 = cKPTT_MID_S46;
	}
	public Double getSAT_MID_S46() {
		return SAT_MID_S46;
	}
	public void setSAT_MID_S46(Double sAT_MID_S46) {
		SAT_MID_S46 = sAT_MID_S46;
	}
	public Double getPRES_MID_S46() {
		return PRES_MID_S46;
	}
	public void setPRES_MID_S46(Double pRES_MID_S46) {
		PRES_MID_S46 = pRES_MID_S46;
	}
	public int getDETA_PRES_FWD_S() {
		return DETA_PRES_FWD_S;
	}
	public void setDETA_PRES_FWD_S(int dETA_PRES_FWD_S) {
		DETA_PRES_FWD_S = dETA_PRES_FWD_S;
	}
	public int getEVENTTYPE() {
		return EVENTTYPE;
	}
	public void setEVENTTYPE(int eVENTTYPE) {
		EVENTTYPE = eVENTTYPE;
	}
	public String getMARKMEMO() {
		return MARKMEMO;
	}
	public void setMARKMEMO(String mARKMEMO) {
		MARKMEMO = mARKMEMO;
	}
	public String getMARKTIME() {
		return MARKTIME;
	}
	public void setMARKTIME(String mARKTIME) {
		MARKTIME = mARKTIME;
	}
	public Integer getDETA_PRESRATE_ST_POINTTYPE() {
		return DETA_PRESRATE_ST_POINTTYPE;
	}
	public void setDETA_PRESRATE_ST_POINTTYPE(Integer dETA_PRESRATE_ST_POINTTYPE) {
		DETA_PRESRATE_ST_POINTTYPE = dETA_PRESRATE_ST_POINTTYPE;
	}
	public Double getK() {
		return k;
	}
	public void setK(Double k) {
		this.k = k;
	}
	public Double getLineValue() {
		return lineValue;
	}
	public void setLineValue(Double lineValue) {
		this.lineValue = lineValue;
	}
	
}
