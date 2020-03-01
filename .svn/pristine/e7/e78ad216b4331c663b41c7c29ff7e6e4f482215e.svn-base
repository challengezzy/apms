package com.apms.bs.datacompute.vo;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;

public class A13ComputeVo extends DfdComputedVo{
    
	private double tat                  ; //外界温度
	private double alt                  ; //气压海拔
	private String asn                  ; //APU序号
	private double ahrs                 ; //APU小时(分钟)
	private double acyc                 ; //APU循环
	private double pdi_old              ; //性能指数
	private double pdi_new              ; //性能指数-新
	private String code                 ; //发送代码
	private double thita                ; //THITA值
	private double thita_lcit           ; //标态大气环境修正系数LCIT
	private String esn_max              ; //APU最大EGT时的发动机序号
	private Double na_max               ; //APU最大EGT时的转速
	private double egta_max             ; //APU最大EGT时的排气温度
	private double igv_max              ; //APU最大EGT时的IGV角度
	private double p2a_max              ; //APU最大EGT时的压气机进口压力
	private double lcit_max             ; //APU最大EGT时的压气机进口温度
	private double wb_max               ; //APU最大EGT时的引气流量 kg/sec
	private double pt_max               ; //APU最大EGT时的引气压力
	private double lcdt_max             ; //APU最大EGT时的负载压气机出口温度
	private double ota_max              ; //APU最大EGT时的滑油温度
	private double gla_max              ; //APU最大EGT时的发电机负载 %
	private double scv_max              ; //APU伺服活门角度 A330 A340
	private double hot_max              ; //高位滑油温度 A330 A340
	private double sta_v1               ; //APU自启动时间
	private double sta_v1_est_time      ; //基于STA_V1到达红线值剩余时间（分钟）
	private double egtp_v1              ; //APU自启动EGT峰值
	private double npa_v1               ; //APU自启动EGT峰值的转速 单位%
	private double ota_v1               ; //APU自启动滑油温度 A330 A340 LOT
	private double lcit_v1              ; //APU自启动进气口温度
	private double egta_max_cor         ; //修正海平面标态EGT温度
	private double egta_max_cor_est_time; //基于EGT_MAX_COR到达红线值剩余时间（分钟）
	private double p2a_max_cor          ; //修正海平面标态P2A进口压力，根据LCIT/ALT修正
	private double wb_max_cor           ; //引气流量修正
	private double pt_max_cor           ; //
	private double pt_max_cor_est_time  ; //基于PT_MAX_COR到达红线值剩余时间（分钟）
	private double apr_max              ; //修正海平面标态APU 引气出口与进口 的增压比PT_MAX_COR/P2A_MAX_COR
	private double apr_max_est_time     ; //基于APR_MAX到达红线值剩余时间（分钟） 红线值 为 3.4
	private double egtp_v1_cor          ; //APU自启动EGT峰值海平面标态修正
	private double npa_v1_cor           ; //修正NPA数据 NPA/T1 流体近似性
	private double hot_ot_max           ; //高位话有温度 HOT-LCIT 的 温度 A330 A340
	private double ot_max               ; //APU最大EGT时的OTA-LCIT 温度
	private double ot_max_est_time      ; //基于OT_MAX到达红线值剩余时间（分钟） 红线值 为 不同型号APU此参数不同
	private double ot_v1                ; //APU自启动时滑油增温OTA_V1-LCIT_V1
	
	private double egtp_v1_cor_roll5;
	private double npa_v1_cor_roll5;
	private double hot_ot_max_roll5;

	
	private int isChangePoint           ;//是否换发点0-否，1-是
	private int isChangeOil             ;//是否是滑油添加点
	private int isChangeStrater    ;//是否是启动机更换点
	
	FieldComputeVo fieldVo_sta; 	
	FieldComputeVo fieldVo_igv;	
	FieldComputeVo fieldVo_egta;	
	FieldComputeVo fieldVo_p2a;	
	FieldComputeVo fieldVo_pt;	
	FieldComputeVo fieldVo_apr;	
	FieldComputeVo fieldVo_ot;
	
	private double rl_na     ; //红线值-转速         
	private double rl_sta    ; //红线值-自启动时间   
	private double rl_igv    ; //红线值-APU最大EGT时的IGV角度 
	private double rl_egt    ; //红线值-EGT          
	private double rl_egt_cor; //红线值-EGT_CO       
	private double rl_apr    ; //红线值-APR          
	private double rl_apr_cor; //红线值-APR_CO       
	private double rl_wb     ; //红线值-WB           
	private double rl_wb_cor ; //红线值-WB_CO        
	private double rl_pt     ; //红线值-PT           
	private double rl_pt_cor ; //红线值-PT_CO        
	private double rl_ota    ; //红线值-OTA          
	private double rl_ota_cor; //红线值-OTA_COR
	
	//PDI计算值
	private double egt_dnmt; //用于PDI_NEW的EGT分母
	private double pt_dnmt;
	private double sta_dnmt;
	
	private String apuModel;//APU型号

	
	public void insertToTable() throws Exception{
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("insert into a_dfd_a13_compute(");
		sb.append("id,msg_no,acnum,utcdate,tat,alt,asn,ahrs,acyc,");
		sb.append("pdi_old,pdi_new,code,thita,thita_lcit,esn_max,na_max,egta_max,igv_max,p2a_max,");
		sb.append("lcit_max,wb_max,pt_max,lcdt_max,ota_max,gla_max,scv_max,hot_max,sta_v1,sta_v1_est_time,");
		sb.append("egtp_v1,npa_v1,ota_v1,lcit_v1,egta_max_cor,egta_max_cor_est_time,p2a_max_cor,wb_max_cor,pt_max_cor,");
		sb.append("pt_max_cor_est_time,apr_max,apr_max_est_time,egtp_v1_cor,npa_v1_cor,hot_ot_max,");
		sb.append("ot_max,ot_max_est_time,ot_v1,");
		sb.append("egtp_v1_cor_roll5,npa_v1_cor_roll5,hot_ot_max_roll5,");
		sb.append("isChangePoint,isChangeOil,isChangeStrater,recdatetime");
		sb.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), 
				id,msgno,acnum,date_utc,tat,alt,asn,ahrs,acyc,
				pdi_old,pdi_new,code,thita,thita_lcit,esn_max,na_max,egta_max,igv_max,p2a_max,
				lcit_max,wb_max,pt_max,lcdt_max,ota_max,gla_max,scv_max,hot_max,sta_v1,sta_v1_est_time,
				egtp_v1,npa_v1,ota_v1,lcit_v1,egta_max_cor,egta_max_cor_est_time,p2a_max_cor,wb_max_cor,pt_max_cor,
				pt_max_cor_est_time,apr_max,apr_max_est_time,egtp_v1_cor,npa_v1_cor,hot_ot_max,
				ot_max,ot_max_est_time,ot_v1,
				egtp_v1_cor_roll5,npa_v1_cor_roll5,hot_ot_max_roll5,
				isChangePoint,isChangeOil,isChangeStrater
				);
		logger.debug("插入A13 报文计算数据！");
	}
	
	
	public double getEgtp_v1_cor_roll5() {
		return egtp_v1_cor_roll5;
	}


	public void setEgtp_v1_cor_roll5(double egtp_v1_cor_roll5) {
		this.egtp_v1_cor_roll5 = egtp_v1_cor_roll5;
	}


	public double getNpa_v1_cor_roll5() {
		return npa_v1_cor_roll5;
	}


	public void setNpa_v1_cor_roll5(double npa_v1_cor_roll5) {
		this.npa_v1_cor_roll5 = npa_v1_cor_roll5;
	}


	public double getHot_ot_max_roll5() {
		return hot_ot_max_roll5;
	}


	public void setHot_ot_max_roll5(double hot_ot_max_roll5) {
		this.hot_ot_max_roll5 = hot_ot_max_roll5;
	}


	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public double getTat() {
		return tat;
	}

	public void setTat(double tat) {
		this.tat = tat;
	}

	public double getAlt() {
		return alt;
	}

	public void setAlt(double alt) {
		this.alt = alt;
	}

	public String getAsn() {
		return asn;
	}

	public void setAsn(String asn) {
		this.asn = asn;
	}

	public double getAhrs() {
		return ahrs;
	}

	public void setAhrs(double ahrs) {
		this.ahrs = ahrs;
	}

	public double getAcyc() {
		return acyc;
	}

	public void setAcyc(double acyc) {
		this.acyc = acyc;
	}

	public double getPdi_old() {
		return pdi_old;
	}

	public void setPdi_old(double pdi_old) {
		this.pdi_old = pdi_old;
	}

	public double getPdi_new() {
		return pdi_new;
	}

	public void setPdi_new(double pdi_new) {
		this.pdi_new = pdi_new;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getThita() {
		return thita;
	}

	public void setThita(double thita) {
		this.thita = thita;
	}

	public double getThita_lcit() {
		return thita_lcit;
	}

	public void setThita_lcit(double thita_lcit) {
		this.thita_lcit = thita_lcit;
	}

	public String getEsn_max() {
		return esn_max;
	}

	public void setEsn_max(String esn_max) {
		this.esn_max = esn_max;
	}

	public double getNa_max() {
		return na_max;
	}

	public void setNa_max(Double na_max) {
		this.na_max = na_max;
	}

	public Double getEgta_max() {
		return egta_max;
	}

	public void setEgta_max(double egta_max) {
		this.egta_max = egta_max;
	}

	public double getIgv_max() {
		return igv_max;
	}

	public void setIgv_max(double igv_max) {
		this.igv_max = igv_max;
	}

	public double getP2a_max() {
		return p2a_max;
	}

	public void setP2a_max(double p2a_max) {
		this.p2a_max = p2a_max;
	}

	public double getLcit_max() {
		return lcit_max;
	}

	public void setLcit_max(double lcit_max) {
		this.lcit_max = lcit_max;
	}

	public double getWb_max() {
		return wb_max;
	}

	public void setWb_max(double wb_max) {
		this.wb_max = wb_max;
	}

	public double getPt_max() {
		return pt_max;
	}

	public void setPt_max(double pt_max) {
		this.pt_max = pt_max;
	}

	public double getLcdt_max() {
		return lcdt_max;
	}

	public void setLcdt_max(double lcdt_max) {
		this.lcdt_max = lcdt_max;
	}

	public double getOta_max() {
		return ota_max;
	}

	public void setOta_max(double ota_max) {
		this.ota_max = ota_max;
	}

	public double getGla_max() {
		return gla_max;
	}

	public void setGla_max(double gla_max) {
		this.gla_max = gla_max;
	}

	public double getScv_max() {
		return scv_max;
	}

	public void setScv_max(double scv_max) {
		this.scv_max = scv_max;
	}

	public double getHot_max() {
		return hot_max;
	}

	public void setHot_max(double hot_max) {
		this.hot_max = hot_max;
	}

	public double getSta_v1() {
		return sta_v1;
	}

	public void setSta_v1(double sta_v1) {
		this.sta_v1 = sta_v1;
	}

	public double getSta_v1_est_time() {
		return sta_v1_est_time;
	}

	public void setSta_v1_est_time(double sta_v1_est_time) {
		this.sta_v1_est_time = sta_v1_est_time;
	}

	public double getEgtp_v1() {
		return egtp_v1;
	}

	public void setEgtp_v1(double egtp_v1) {
		this.egtp_v1 = egtp_v1;
	}

	public double getNpa_v1() {
		return npa_v1;
	}

	public void setNpa_v1(double npa_v1) {
		this.npa_v1 = npa_v1;
	}

	public double getOta_v1() {
		return ota_v1;
	}

	public void setOta_v1(double ota_v1) {
		this.ota_v1 = ota_v1;
	}

	public double getLcit_v1() {
		return lcit_v1;
	}

	public void setLcit_v1(double lcit_v1) {
		this.lcit_v1 = lcit_v1;
	}

	public double getEgta_max_cor() {
		return egta_max_cor;
	}

	public void setEgta_max_cor(double egta_max_cor) {
		this.egta_max_cor = egta_max_cor;
	}

	public double getEgta_max_cor_est_time() {
		return egta_max_cor_est_time;
	}

	public void setEgta_max_cor_est_time(double egta_max_cor_est_time) {
		this.egta_max_cor_est_time = egta_max_cor_est_time;
	}

	public double getP2a_max_cor() {
		return p2a_max_cor;
	}

	public void setP2a_max_cor(double p2a_max_cor) {
		this.p2a_max_cor = p2a_max_cor;
	}

	public double getPt_max_cor() {
		return pt_max_cor;
	}

	public void setPt_max_cor(double pt_max_cor) {
		this.pt_max_cor = pt_max_cor;
	}

	public double getPt_max_cor_est_time() {
		return pt_max_cor_est_time;
	}

	public void setPt_max_cor_est_time(double pt_max_cor_est_time) {
		this.pt_max_cor_est_time = pt_max_cor_est_time;
	}

	public double getApr_max() {
		return apr_max;
	}

	public void setApr_max(double apr_max) {
		this.apr_max = apr_max;
	}

	public double getApr_max_est_time() {
		return apr_max_est_time;
	}

	public void setApr_max_est_time(double apr_max_est_time) {
		this.apr_max_est_time = apr_max_est_time;
	}

	public double getEgtp_v1_cor() {
		return egtp_v1_cor;
	}

	public void setEgtp_v1_cor(double egtp_v1_cor) {
		this.egtp_v1_cor = egtp_v1_cor;
	}

	
	public double getWb_max_cor() {
		return wb_max_cor;
	}

	public void setWb_max_cor(double wb_max_cor) {
		this.wb_max_cor = wb_max_cor;
	}

	public double getNpa_v1_cor() {
		return npa_v1_cor;
	}

	public void setNpa_v1_cor(double npa_v1_cor) {
		this.npa_v1_cor = npa_v1_cor;
	}

	public double getHot_ot_max() {
		return hot_ot_max;
	}

	public void setHot_ot_max(double hot_ot_max) {
		this.hot_ot_max = hot_ot_max;
	}

	public double getOt_max() {
		return ot_max;
	}

	public void setOt_max(double ot_max) {
		this.ot_max = ot_max;
	}

	public double getOt_max_est_time() {
		return ot_max_est_time;
	}

	public void setOt_max_est_time(double ot_max_est_time) {
		this.ot_max_est_time = ot_max_est_time;
	}

	public double getOt_v1() {
		return ot_v1;
	}

	public void setOt_v1(double ot_v1) {
		this.ot_v1 = ot_v1;
	}

	public int getIsChangePoint() {
		return isChangePoint;
	}

	public void setIsChangePoint(int isChangePoint) {
		this.isChangePoint = isChangePoint;
	}

	public FieldComputeVo getFieldVo_sta() {
		return fieldVo_sta;
	}

	public void setFieldVo_sta(FieldComputeVo fieldVo_sta) {
		this.fieldVo_sta = fieldVo_sta;
	}

	public FieldComputeVo getFieldVo_igv() {
		return fieldVo_igv;
	}

	public void setFieldVo_igv(FieldComputeVo fieldVo_igv) {
		this.fieldVo_igv = fieldVo_igv;
	}

	public FieldComputeVo getFieldVo_egta() {
		return fieldVo_egta;
	}

	public void setFieldVo_egta(FieldComputeVo fieldVo_egta) {
		this.fieldVo_egta = fieldVo_egta;
	}

	public FieldComputeVo getFieldVo_p2a() {
		return fieldVo_p2a;
	}

	public void setFieldVo_p2a(FieldComputeVo fieldVo_p2a) {
		this.fieldVo_p2a = fieldVo_p2a;
	}

	public FieldComputeVo getFieldVo_pt() {
		return fieldVo_pt;
	}

	public void setFieldVo_pt(FieldComputeVo fieldVo_pt) {
		this.fieldVo_pt = fieldVo_pt;
	}

	public FieldComputeVo getFieldVo_apr() {
		return fieldVo_apr;
	}

	public void setFieldVo_apr(FieldComputeVo fieldVo_apr) {
		this.fieldVo_apr = fieldVo_apr;
	}

	public FieldComputeVo getFieldVo_ot() {
		return fieldVo_ot;
	}

	public void setFieldVo_ot(FieldComputeVo fieldVo_ot) {
		this.fieldVo_ot = fieldVo_ot;
	}

	public double getRl_na() {
		return rl_na;
	}

	public void setRl_na(double rl_na) {
		this.rl_na = rl_na;
	}

	public double getRl_sta() {
		return rl_sta;
	}

	public void setRl_sta(double rl_sta) {
		this.rl_sta = rl_sta;
	}

	public double getRl_igv() {
		return rl_igv;
	}

	public void setRl_igv(double rl_igv) {
		this.rl_igv = rl_igv;
	}

	public double getRl_egt() {
		return rl_egt;
	}

	public void setRl_egt(double rl_egt) {
		this.rl_egt = rl_egt;
	}

	public double getRl_egt_cor() {
		return rl_egt_cor;
	}

	public void setRl_egt_cor(double rl_egt_cor) {
		this.rl_egt_cor = rl_egt_cor;
	}

	public double getRl_apr() {
		return rl_apr;
	}

	public void setRl_apr(double rl_apr) {
		this.rl_apr = rl_apr;
	}

	public double getRl_apr_cor() {
		return rl_apr_cor;
	}

	public void setRl_apr_cor(double rl_apr_cor) {
		this.rl_apr_cor = rl_apr_cor;
	}

	public double getRl_wb() {
		return rl_wb;
	}

	public void setRl_wb(double rl_wb) {
		this.rl_wb = rl_wb;
	}

	public double getRl_wb_cor() {
		return rl_wb_cor;
	}

	public void setRl_wb_cor(double rl_wb_cor) {
		this.rl_wb_cor = rl_wb_cor;
	}

	public double getRl_pt() {
		return rl_pt;
	}

	public void setRl_pt(double rl_pt) {
		this.rl_pt = rl_pt;
	}

	public double getRl_pt_cor() {
		return rl_pt_cor;
	}

	public void setRl_pt_cor(double rl_pt_cor) {
		this.rl_pt_cor = rl_pt_cor;
	}

	public double getRl_ota() {
		return rl_ota;
	}

	public void setRl_ota(double rl_ota) {
		this.rl_ota = rl_ota;
	}

	public double getRl_ota_cor() {
		return rl_ota_cor;
	}

	public void setRl_ota_cor(double rl_ota_cor) {
		this.rl_ota_cor = rl_ota_cor;
	}


	public double getEgt_dnmt() {
		return egt_dnmt;
	}


	public void setEgt_dnmt(double egt_dnmt) {
		this.egt_dnmt = egt_dnmt;
	}


	public double getPt_dnmt() {
		return pt_dnmt;
	}


	public void setPt_dnmt(double pt_dnmt) {
		this.pt_dnmt = pt_dnmt;
	}


	public double getSta_dnmt() {
		return sta_dnmt;
	}


	public void setSta_dnmt(double sta_dnmt) {
		this.sta_dnmt = sta_dnmt;
	}


	public int getIsChangeOil() {
		return isChangeOil;
	}


	public void setIsChangeOil(int isChangeOil) {
		this.isChangeOil = isChangeOil;
	}


	public int getIsChangeStrater() {
		return isChangeStrater;
	}


	public void setIsChangeStrater(int isChangeStrater) {
		this.isChangeStrater = isChangeStrater;
	}


	public String getApuModel() {
		return apuModel;
	}


	public void setApuModel(String apuModel) {
		this.apuModel = apuModel;
	}
	
}
