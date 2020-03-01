package com.apms.bs.datacompute.vo;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;

public class A24ComputeVo extends DfdComputedVo{
	
	private String esn_1;
	private String esn_2;
	private double div_pf_e1;
	private double div_pf_n1;
	private double pf_div;
	private double div_cot_e1;
	private double div_cot_n1;
	private double cot_div;
	private double div_ri_e1;
	private double div_ri_n1;
	private double ri_div;
	private double div_ro_e1;
	private double div_ro_n1;
	private double ro_div;
	private double div_pbv_e1;
	private double div_pbv_n1;
	private double pbv_div;
	private double div_tw_s1;
	private double div_tw_t1;
	private double tw_div;
	private double div_tp_s1;
	private double div_tp_t1;
	private double tp_div;
	private double div_tpo_s1;
	private double div_tpo_t1;
	private double tpo_div;
	private double div_pd_s1;
	private double div_pd_t1;
	private double pd_div;
	
	private double div_pdmt_l_d1;
	private double div_pdmt_r_d1;
	private double d1_div;
	private double div_sc1_v1; 
	private double div_ckt_f1; 
	private double div_sc2_v1; 
	private double sub_sc2_fwdt;
	private double div_fwdt_f1; 
	private double div_sc3_v1;
	private double sub_sc3_aftt;
	private double div_aftt_f1; 
	private double sc1ckt_div;
	private double div_pdmt_tw1;
	private double div_pdmt_tw2;
	private double pdmt_tw1_div;
	private double pdmt_tw2_div;
	
	//五点均
	private double PF_E1;
	private double PF_N1;
	private double COT_E1;
	private double COT_N1;
	private double RI_E1;
	private double RI_N1;
	private double PBV_E1;
	private double PBV_N1;
	private double TW_S1;
	private double TW_T1;
	private double TP_S1;
	private double TP_T1;
	private double PDMT_L_D1;
	private double PDMT_R_D1;
	private double PRV1_Z1;
	private double PRV2_Z1;
	private double PD_S1;
	private double PD_T1;

	private String modelSeries;//机型系列
	private int ex_count ; //超出门限的参数数量
	
	public double getPF_E1() {
		return PF_E1;
	}
	public void setPF_E1(double pF_E1) {
		PF_E1 = pF_E1;
	}
	public double getPF_N1() {
		return PF_N1;
	}
	public void setPF_N1(double pF_N1) {
		PF_N1 = pF_N1;
	}
	public double getCOT_E1() {
		return COT_E1;
	}
	public void setCOT_E1(double cOT_E1) {
		COT_E1 = cOT_E1;
	}
	public double getCOT_N1() {
		return COT_N1;
	}
	public void setCOT_N1(double cOT_N1) {
		COT_N1 = cOT_N1;
	}
	public double getRI_E1() {
		return RI_E1;
	}
	public void setRI_E1(double rI_E1) {
		RI_E1 = rI_E1;
	}
	public double getRI_N1() {
		return RI_N1;
	}
	public void setRI_N1(double rI_N1) {
		RI_N1 = rI_N1;
	}
	public double getPBV_E1() {
		return PBV_E1;
	}
	public void setPBV_E1(double pBV_E1) {
		PBV_E1 = pBV_E1;
	}
	public double getPBV_N1() {
		return PBV_N1;
	}
	public void setPBV_N1(double pBV_N1) {
		PBV_N1 = pBV_N1;
	}
	public double getTW_S1() {
		return TW_S1;
	}
	public void setTW_S1(double tW_S1) {
		TW_S1 = tW_S1;
	}
	public double getTW_T1() {
		return TW_T1;
	}
	public void setTW_T1(double tW_T1) {
		TW_T1 = tW_T1;
	}
	public double getTP_S1() {
		return TP_S1;
	}
	public void setTP_S1(double tP_S1) {
		TP_S1 = tP_S1;
	}
	public double getTP_T1() {
		return TP_T1;
	}
	public void setTP_T1(double tP_T1) {
		TP_T1 = tP_T1;
	}
	public double getPDMT_L_D1() {
		return PDMT_L_D1;
	}
	public void setPDMT_L_D1(double pDMT_L_D1) {
		PDMT_L_D1 = pDMT_L_D1;
	}
	public double getPDMT_R_D1() {
		return PDMT_R_D1;
	}
	public void setPDMT_R_D1(double pDMT_R_D1) {
		PDMT_R_D1 = pDMT_R_D1;
	}
	public double getPRV1_Z1() {
		return PRV1_Z1;
	}
	public void setPRV1_Z1(double pRV1_Z1) {
		PRV1_Z1 = pRV1_Z1;
	}
	public double getPRV2_Z1() {
		return PRV2_Z1;
	}
	public void setPRV2_Z1(double pRV2_Z1) {
		PRV2_Z1 = pRV2_Z1;
	}
	public double getPD_S1() {
		return PD_S1;
	}
	public void setPD_S1(double pD_S1) {
		PD_S1 = pD_S1;
	}
	public double getPD_T1() {
		return PD_T1;
	}
	public void setPD_T1(double pD_T1) {
		PD_T1 = pD_T1;
	}
	public double getDiv_pdmt_tw1() {
		return div_pdmt_tw1;
	}
	public void setDiv_pdmt_tw1(double div_pdmt_tw1) {
		this.div_pdmt_tw1 = div_pdmt_tw1;
	}
	public double getDiv_pdmt_tw2() {
		return div_pdmt_tw2;
	}
	public void setDiv_pdmt_tw2(double div_pdmt_tw2) {
		this.div_pdmt_tw2 = div_pdmt_tw2;
	}
	public double getPdmt_tw1_div() {
		return pdmt_tw1_div;
	}
	public void setPdmt_tw1_div(double pdmt_tw1_div) {
		this.pdmt_tw1_div = pdmt_tw1_div;
	}
	public double getPdmt_tw2_div() {
		return pdmt_tw2_div;
	}
	public void setPdmt_tw2_div(double pdmt_tw2_div) {
		this.pdmt_tw2_div = pdmt_tw2_div;
	}
	public double getSub_sc2_fwdt() {
		return sub_sc2_fwdt;
	}
	public void setSub_sc2_fwdt(double sub_sc2_fwdt) {
		this.sub_sc2_fwdt = sub_sc2_fwdt;
	}
	public double getSub_sc3_aftt() {
		return sub_sc3_aftt;
	}
	public void setSub_sc3_aftt(double sub_sc3_aftt) {
		this.sub_sc3_aftt = sub_sc3_aftt;
	}
	private double sub_sc1_ckt;
	private double sc2fwdt_div;
	private double sc3aftt_div;
	
	FieldComputeVo fieldVo_pf;
	FieldComputeVo fieldVo_cot;
	FieldComputeVo fieldVo_ri;
	FieldComputeVo fieldVo_pbv;
	FieldComputeVo fieldVo_tw;
	FieldComputeVo fieldVo_tp;
	FieldComputeVo fieldVo_sc1ckt;
	FieldComputeVo fieldVo_sc2fwdt;
	FieldComputeVo fieldVo_sc3aftt;
	FieldComputeVo fieldVo_d1;

	public FieldComputeVo getFieldVo_pf() {
		return fieldVo_pf;
	}
	public void setFieldVo_pf(FieldComputeVo fieldVo_pf) {
		this.fieldVo_pf = fieldVo_pf;
	}
	public FieldComputeVo getFieldVo_cot() {
		return fieldVo_cot;
	}
	public void setFieldVo_cot(FieldComputeVo fieldVo_cot) {
		this.fieldVo_cot = fieldVo_cot;
	}
	public FieldComputeVo getFieldVo_ri() {
		return fieldVo_ri;
	}
	public void setFieldVo_ri(FieldComputeVo fieldVo_ri) {
		this.fieldVo_ri = fieldVo_ri;
	}
	public FieldComputeVo getFieldVo_pbv() {
		return fieldVo_pbv;
	}
	public void setFieldVo_pbv(FieldComputeVo fieldVo_pbv) {
		this.fieldVo_pbv = fieldVo_pbv;
	}
	public FieldComputeVo getFieldVo_tw() {
		return fieldVo_tw;
	}
	public void setFieldVo_tw(FieldComputeVo fieldVo_tw) {
		this.fieldVo_tw = fieldVo_tw;
	}
	public FieldComputeVo getFieldVo_tp() {
		return fieldVo_tp;
	}
	public void setFieldVo_tp(FieldComputeVo fieldVo_tp) {
		this.fieldVo_tp = fieldVo_tp;
	}
	public FieldComputeVo getFieldVo_sc1ckt() {
		return fieldVo_sc1ckt;
	}
	public void setFieldVo_sc1ckt(FieldComputeVo fieldVo_sc1ckt) {
		this.fieldVo_sc1ckt = fieldVo_sc1ckt;
	}
	public FieldComputeVo getFieldVo_sc2fwdt() {
		return fieldVo_sc2fwdt;
	}
	public void setFieldVo_sc2fwdt(FieldComputeVo fieldVo_sc2fwdt) {
		this.fieldVo_sc2fwdt = fieldVo_sc2fwdt;
	}
	public FieldComputeVo getFieldVo_sc3aftt() {
		return fieldVo_sc3aftt;
	}
	public void setFieldVo_sc3aftt(FieldComputeVo fieldVo_sc3aftt) {
		this.fieldVo_sc3aftt = fieldVo_sc3aftt;
	}
	public FieldComputeVo getFieldVo_d1() {
		return fieldVo_d1;
	}
	public void setFieldVo_d1(FieldComputeVo fieldVo_d1) {
		this.fieldVo_d1 = fieldVo_d1;
	}
	public double getDiv_pdmt_l_d1() {
		return div_pdmt_l_d1;
	}
	public void setDiv_pdmt_l_d1(double div_pdmt_l_d1) {
		this.div_pdmt_l_d1 = div_pdmt_l_d1;
	}
	public double getDiv_pdmt_r_d1() {
		return div_pdmt_r_d1;
	}
	public void setDiv_pdmt_r_d1(double div_pdmt_r_d1) {
		this.div_pdmt_r_d1 = div_pdmt_r_d1;
	}
	public double getD1_div() {
		return d1_div;
	}
	public void setD1_div(double d1_div) {
		this.d1_div = d1_div;
	}
	
	public double getDiv_sc1_v1() {
		return div_sc1_v1;
	}
	public void setDiv_sc1_v1(double div_sc1_v1) {
		this.div_sc1_v1 = div_sc1_v1;
	}
	public double getDiv_ckt_f1() {
		return div_ckt_f1;
	}
	public void setDiv_ckt_f1(double div_ckt_f1) {
		this.div_ckt_f1 = div_ckt_f1;
	}
	public double getDiv_sc2_v1() {
		return div_sc2_v1;
	}
	public void setDiv_sc2_v1(double div_sc2_v1) {
		this.div_sc2_v1 = div_sc2_v1;
	}
	public double getDiv_fwdt_f1() {
		return div_fwdt_f1;
	}
	public void setDiv_fwdt_f1(double div_fwdt_f1) {
		this.div_fwdt_f1 = div_fwdt_f1;
	}
	public double getDiv_sc3_v1() {
		return div_sc3_v1;
	}
	public void setDiv_sc3_v1(double div_sc3_v1) {
		this.div_sc3_v1 = div_sc3_v1;
	}
	public double getDiv_aftt_f1() {
		return div_aftt_f1;
	}
	public void setDiv_aftt_f1(double div_aftt_f1) {
		this.div_aftt_f1 = div_aftt_f1;
	}
	public double getSc1ckt_div() {
		return sc1ckt_div;
	}
	public void setSc1ckt_div(double sc1ckt_div) {
		this.sc1ckt_div = sc1ckt_div;
	}
	public double getSc2fwdt_div() {
		return sc2fwdt_div;
	}
	public void setSc2fwdt_div(double sc2fwdt_div) {
		this.sc2fwdt_div = sc2fwdt_div;
	}
	public double getSc3aftt_div() {
		return sc3aftt_div;
	}
	public void setSc3aftt_div(double sc3aftt_div) {
		this.sc3aftt_div = sc3aftt_div;
	}
	public String getEsn_1() {
		return esn_1;
	}
	public void setEsn_1(String esn_1) {
		this.esn_1 = esn_1;
	}
	public String getEsn_2() {
		return esn_2;
	}
	public void setEsn_2(String esn_2) {
		this.esn_2 = esn_2;
	}
	public double getPf_div() {
		return pf_div;
	}
	public void setPf_div(double pf_div) {
		this.pf_div = pf_div;
	}
	public double getCot_div() {
		return cot_div;
	}
	public void setCot_div(double cot_div) {
		this.cot_div = cot_div;
	}
	public double getRi_div() {
		return ri_div;
	}
	public void setRi_div(double ri_div) {
		this.ri_div = ri_div;
	}
	public double getRo_div() {
		return ro_div;
	}
	public void setRo_div(double ro_div) {
		this.ro_div = ro_div;
	}
	public double getPbv_div() {
		return pbv_div;
	}
	public void setPbv_div(double pbv_div) {
		this.pbv_div = pbv_div;
	}
	public double getTw_div() {
		return tw_div;
	}
	public void setTw_div(double tw_div) {
		this.tw_div = tw_div;
	}
	public double getTp_div() {
		return tp_div;
	}
	public void setTp_div(double tp_div) {
		this.tp_div = tp_div;
	}
	public double getTpo_div() {
		return tpo_div;
	}
	public void setTpo_div(double tpo_div) {
		this.tpo_div = tpo_div;
	}
	public double getPd_div() {
		return pd_div;
	}
	public void setPd_div(double pd_div) {
		this.pd_div = pd_div;
	}
	
	
	public double getDiv_pf_e1() {
		return div_pf_e1;
	}
	public void setDiv_pf_e1(double div_pf_e1) {
		this.div_pf_e1 = div_pf_e1;
	}
	public double getDiv_pf_n1() {
		return div_pf_n1;
	}
	public void setDiv_pf_n1(double div_pf_n1) {
		this.div_pf_n1 = div_pf_n1;
	}
	public double getDiv_cot_e1() {
		return div_cot_e1;
	}
	public void setDiv_cot_e1(double div_cot_e1) {
		this.div_cot_e1 = div_cot_e1;
	}
	public double getDiv_cot_n1() {
		return div_cot_n1;
	}
	public void setDiv_cot_n1(double div_cot_n1) {
		this.div_cot_n1 = div_cot_n1;
	}
	public double getDiv_ri_e1() {
		return div_ri_e1;
	}
	public void setDiv_ri_e1(double div_ri_e1) {
		this.div_ri_e1 = div_ri_e1;
	}
	public double getDiv_ri_n1() {
		return div_ri_n1;
	}
	public void setDiv_ri_n1(double div_ri_n1) {
		this.div_ri_n1 = div_ri_n1;
	}
	public double getDiv_ro_e1() {
		return div_ro_e1;
	}
	public void setDiv_ro_e1(double div_ro_e1) {
		this.div_ro_e1 = div_ro_e1;
	}
	public double getDiv_ro_n1() {
		return div_ro_n1;
	}
	public void setDiv_ro_n1(double div_ro_n1) {
		this.div_ro_n1 = div_ro_n1;
	}
	public double getDiv_pbv_e1() {
		return div_pbv_e1;
	}
	public void setDiv_pbv_e1(double div_pbv_e1) {
		this.div_pbv_e1 = div_pbv_e1;
	}
	public double getDiv_pbv_n1() {
		return div_pbv_n1;
	}
	public void setDiv_pbv_n1(double div_pbv_n1) {
		this.div_pbv_n1 = div_pbv_n1;
	}

	public double getDiv_tw_s1() {
		return div_tw_s1;
	}
	public void setDiv_tw_s1(double div_tw_s1) {
		this.div_tw_s1 = div_tw_s1;
	}
	public double getDiv_tw_t1() {
		return div_tw_t1;
	}
	public void setDiv_tw_t1(double div_tw_t1) {
		this.div_tw_t1 = div_tw_t1;
	}
	public double getDiv_tp_s1() {
		return div_tp_s1;
	}
	public void setDiv_tp_s1(double div_tp_s1) {
		this.div_tp_s1 = div_tp_s1;
	}
	public double getDiv_tp_t1() {
		return div_tp_t1;
	}
	public void setDiv_tp_t1(double div_tp_t1) {
		this.div_tp_t1 = div_tp_t1;
	}
	public double getDiv_tpo_s1() {
		return div_tpo_s1;
	}
	public void setDiv_tpo_s1(double div_tpo_s1) {
		this.div_tpo_s1 = div_tpo_s1;
	}
	public double getDiv_tpo_t1() {
		return div_tpo_t1;
	}
	public void setDiv_tpo_t1(double div_tpo_t1) {
		this.div_tpo_t1 = div_tpo_t1;
	}
	public double getDiv_pd_s1() {
		return div_pd_s1;
	}
	public void setDiv_pd_s1(double div_pd_s1) {
		this.div_pd_s1 = div_pd_s1;
	}
	public double getDiv_pd_t1() {
		return div_pd_t1;
	}
	public void setDiv_pd_t1(double div_pd_t1) {
		this.div_pd_t1 = div_pd_t1;
	}
	
	
	
	public void insertToTable() throws Exception{
		//TODO 这里补全对表a_dfd_a21_compute 数据的插入
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("INSERT INTO A_DFD_A24_COMPUTE(");
		sb.append("ID,MSG_NO,ACNUM,RPTDATE");
		sb.append(",ESN_1,ESN_2");
		sb.append(",PF_DIV,DIV_PF_E1,DIV_PF_N1,COT_DIV,DIV_COT_E1,DIV_COT_N1");
		sb.append(",RI_DIV,DIV_RI_E1,DIV_RI_N1,RO_DIV,DIV_RO_E1,DIV_RO_N1");
		sb.append(",PBV_DIV,DIV_PBV_E1,DIV_PBV_N1");
		sb.append(",TW_DIV,DIV_TW_S1,DIV_TW_T1,TP_DIV,DIV_TP_S1,DIV_TP_T1");
		sb.append(",TPO_DIV,DIV_TPO_S1,DIV_TPO_T1,PD_DIV,DIV_PD_S1,DIV_PD_T1");
		sb.append(",DIV_PDMT_L_D1,DIV_PDMT_R_D1,D1_DIV,DIV_SC1_V1,DIV_CKT_F1,SC1CKT_DIV");
		sb.append(",DIV_SC2_V1,DIV_FWDT_F1,SC2FWDT_DIV,DIV_SC3_V1,DIV_AFTT_F1,SC3_AFTT_DIV");
		sb.append(",SUB_SC1CKT,SUB_SC2FWDT,SUB_SC3AFTT,DIV_PDMT_TW1,DIV_PDMT_TW2,SUB_PDMT_TW1,SUB_PDMT_TW2");
		sb.append(",update_date) values(?,?,?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?,?,?,?,?,?,sysdate)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), 
				id,msgno,acnum,date_utc,
				getEsn_1(),getEsn_2(),
				getPf_div(),getDiv_pf_e1(),getDiv_pf_n1(),getCot_div(),getDiv_cot_e1(),getDiv_cot_n1(),
				getRi_div(),getDiv_ri_e1(),getDiv_ri_n1(),getRo_div(),getDiv_ro_e1(),getDiv_ro_n1(),
				getPbv_div(),getDiv_pbv_e1(),getDiv_pbv_n1(),
				getTw_div(),getDiv_tw_s1(),getDiv_tw_t1(),getTp_div(),getDiv_tp_s1(),getDiv_tp_t1(),
				getTpo_div(),getDiv_tpo_s1(),getDiv_tpo_t1(),getPd_div(),getDiv_pd_s1(),getDiv_pd_t1(),
				getDiv_pdmt_l_d1(),getDiv_pdmt_r_d1(),getD1_div(),getDiv_sc1_v1(),getDiv_ckt_f1(),getSc1ckt_div(),
				getDiv_sc2_v1(),getDiv_fwdt_f1(),getSc2fwdt_div(),getDiv_sc3_v1(),getDiv_aftt_f1(),getSc3aftt_div(),
				getSub_sc1_ckt(),getSub_sc2_fwdt(),getSub_sc3_aftt(),getDiv_pdmt_tw1(),getDiv_pdmt_tw2(),getPdmt_tw1_div(),getPdmt_tw2_div()
				);
		
		logger.debug("插入A24 报文计算数据！");
	}
	public void setSub_sc1_ckt(double sub_sc1_ckt) {
		this.sub_sc1_ckt = sub_sc1_ckt;
	}
	public double getSub_sc1_ckt() {
		return sub_sc1_ckt;
	}
	public void setEx_count(int ex_count) {
		this.ex_count = ex_count;
	}
	public int getEx_count() {
		return ex_count;
	}
	public void setModelSeries(String modelSeries) {
		this.modelSeries = modelSeries;
	}
	public String getModelSeries() {
		return modelSeries;
	}

	
}
