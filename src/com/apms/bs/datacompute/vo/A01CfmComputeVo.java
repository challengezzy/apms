package com.apms.bs.datacompute.vo;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;
import com.apms.bs.datacompute.vo.a04.A04EgtCfmVo;

public class A01CfmComputeVo extends DfdComputedVo {
	
	private String esn_1;
	private String esn_2;
	
	private int isChangePoint1;
	private int isChangePoint2;
	
	private double egt_margin_1;
	private double egt_margin_2;
	private double div_egt_margin1;
	private double div_egt_margin2;
	private double div_egt_1;
	private double div_egt_2;
	private double div_n2_1;
	private double div_n2_2;
	private double div_ff_1;
	private double div_ff_2;
	private double div_t3_1;
	private double div_t3_2;
	private double div_oip_1;
	private double div_oip_2;
	private double div_oit_1;
	private double div_oit_2;
	private double div_p25pt2_1;
	private double div_p25pt2_2;
	private double div_p3p25_1;
	private double div_p3p25_2;
	private double div_vn_1;
	private double div_vn_2;
	private double div_vl_1;
	private double div_vl_2;
	private double div_vc_1;
	private double div_vc_2;
	private double div_vh_1;
	private double div_vh_2;
	FieldComputeVo fieldVo_div_egt_margin;
	FieldComputeVo fieldVo_egt_margin;
	FieldComputeVo fieldVo_egt;
	FieldComputeVo fieldVo_div_ff;
	FieldComputeVo fieldVo_n2_1;
	FieldComputeVo fieldVo_n2_2;

	private A04EgtCfmVo calvo1;
	private A04EgtCfmVo calvo2;
	
	public int getIsChangePoint1() {
		return isChangePoint1;
	}
	public void setIsChangePoint1(int isChangePoint1) {
		this.isChangePoint1 = isChangePoint1;
	}
	public int getIsChangePoint2() {
		return isChangePoint2;
	}
	public void setIsChangePoint2(int isChangePoint2) {
		this.isChangePoint2 = isChangePoint2;
	}
	public FieldComputeVo getFieldVo_n2_2() {
		return fieldVo_n2_2;
	}
	public void setFieldVo_n2_2(FieldComputeVo fieldVo_n2_2) {
		this.fieldVo_n2_2 = fieldVo_n2_2;
	}
	public FieldComputeVo getFieldVo_n2_1() {
		return fieldVo_n2_1;
	}
	public void setFieldVo_n2_1(FieldComputeVo fieldVo_n2) {
		this.fieldVo_n2_1 = fieldVo_n2;
	}
	public FieldComputeVo getFieldVo_div_ff() {
		return fieldVo_div_ff;
	}
	public void setFieldVo_div_ff(FieldComputeVo fieldVo_div_ff) {
		this.fieldVo_div_ff = fieldVo_div_ff;
	}
	public FieldComputeVo getFieldVo_div_egt_margin() {
		return fieldVo_div_egt_margin;
	}
	public void setFieldVo_div_egt_margin(FieldComputeVo fieldVo_div_egt_margin) {
		this.fieldVo_div_egt_margin = fieldVo_div_egt_margin;
	}
	public FieldComputeVo getFieldVo_egt_margin() {
		return fieldVo_egt_margin;
	}
	public void setFieldVo_egt_margin(FieldComputeVo fieldVo_egt_margin) {
		this.fieldVo_egt_margin = fieldVo_egt_margin;
	}
	
	public FieldComputeVo getFieldVo_egt() {
		return fieldVo_egt;
	}
	public void setFieldVo_egt(FieldComputeVo fieldVo_egt) {
		this.fieldVo_egt = fieldVo_egt;
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
	public double getEgt_margin_1() {
		return egt_margin_1;
	}
	public void setEgt_margin_1(double egt_margin_1) {
		this.egt_margin_1 = egt_margin_1;
	}
	public double getEgt_margin_2() {
		return egt_margin_2;
	}
	public void setEgt_margin_2(double egt_margin_2) {
		this.egt_margin_2 = egt_margin_2;
	}
	public double getDiv_egt_margin1() {
		return div_egt_margin1;
	}
	public void setDiv_egt_margin1(double div_egt_margin1) {
		this.div_egt_margin1 = div_egt_margin1;
	}
	public double getDiv_egt_margin2() {
		return div_egt_margin2;
	}
	public void setDiv_egt_margin2(double div_egt_margin2) {
		this.div_egt_margin2 = div_egt_margin2;
	}
	public double getDiv_egt_1() {
		return div_egt_1;
	}
	public void setDiv_egt_1(double div_egt_1) {
		this.div_egt_1 = div_egt_1;
	}
	public double getDiv_egt_2() {
		return div_egt_2;
	}
	public void setDiv_egt_2(double div_egt_2) {
		this.div_egt_2 = div_egt_2;
	}
	public double getDiv_n2_1() {
		return div_n2_1;
	}
	public void setDiv_n2_1(double div_n2_1) {
		this.div_n2_1 = div_n2_1;
	}
	public double getDiv_n2_2() {
		return div_n2_2;
	}
	public void setDiv_n2_2(double div_n2_2) {
		this.div_n2_2 = div_n2_2;
	}
	public double getDiv_ff_1() {
		return div_ff_1;
	}
	public void setDiv_ff_1(double div_ff_1) {
		this.div_ff_1 = div_ff_1;
	}
	public double getDiv_ff_2() {
		return div_ff_2;
	}
	public void setDiv_ff_2(double div_ff_2) {
		this.div_ff_2 = div_ff_2;
	}
	public double getDiv_t3_1() {
		return div_t3_1;
	}
	public void setDiv_t3_1(double div_t3_1) {
		this.div_t3_1 = div_t3_1;
	}
	public double getDiv_t3_2() {
		return div_t3_2;
	}
	public void setDiv_t3_2(double div_t3_2) {
		this.div_t3_2 = div_t3_2;
	}
	public double getDiv_oip_1() {
		return div_oip_1;
	}
	public void setDiv_oip_1(double div_oip_1) {
		this.div_oip_1 = div_oip_1;
	}
	public double getDiv_oip_2() {
		return div_oip_2;
	}
	public void setDiv_oip_2(double div_oip_2) {
		this.div_oip_2 = div_oip_2;
	}
	public double getDiv_oit_1() {
		return div_oit_1;
	}
	public void setDiv_oit_1(double div_oit_1) {
		this.div_oit_1 = div_oit_1;
	}
	public double getDiv_oit_2() {
		return div_oit_2;
	}
	public void setDiv_oit_2(double div_oit_2) {
		this.div_oit_2 = div_oit_2;
	}
	public double getDiv_p25pt2_1() {
		return div_p25pt2_1;
	}
	public void setDiv_p25pt2_1(double div_p25pt2_1) {
		this.div_p25pt2_1 = div_p25pt2_1;
	}
	public double getDiv_p25pt2_2() {
		return div_p25pt2_2;
	}
	public void setDiv_p25pt2_2(double div_p25pt2_2) {
		this.div_p25pt2_2 = div_p25pt2_2;
	}
	public double getDiv_p3p25_1() {
		return div_p3p25_1;
	}
	public void setDiv_p3p25_1(double div_p3p25_1) {
		this.div_p3p25_1 = div_p3p25_1;
	}
	public double getDiv_p3p25_2() {
		return div_p3p25_2;
	}
	public void setDiv_p3p25_2(double div_p3p25_2) {
		this.div_p3p25_2 = div_p3p25_2;
	}
	
	
	public double getDiv_vn_1() {
		return div_vn_1;
	}
	public void setDiv_vn_1(double div_vn_1) {
		this.div_vn_1 = div_vn_1;
	}
	public double getDiv_vn_2() {
		return div_vn_2;
	}
	public void setDiv_vn_2(double div_vn_2) {
		this.div_vn_2 = div_vn_2;
	}
	public double getDiv_vl_1() {
		return div_vl_1;
	}
	public void setDiv_vl_1(double div_vl_1) {
		this.div_vl_1 = div_vl_1;
	}
	public double getDiv_vl_2() {
		return div_vl_2;
	}
	public void setDiv_vl_2(double div_vl_2) {
		this.div_vl_2 = div_vl_2;
	}
	public double getDiv_vc_1() {
		return div_vc_1;
	}
	public void setDiv_vc_1(double div_vc_1) {
		this.div_vc_1 = div_vc_1;
	}
	public double getDiv_vc_2() {
		return div_vc_2;
	}
	public void setDiv_vc_2(double div_vc_2) {
		this.div_vc_2 = div_vc_2;
	}
	public double getDiv_vh_1() {
		return div_vh_1;
	}
	public void setDiv_vh_1(double div_vh_1) {
		this.div_vh_1 = div_vh_1;
	}
	public double getDiv_vh_2() {
		return div_vh_2;
	}
	public void setDiv_vh_2(double div_vh_2) {
		this.div_vh_2 = div_vh_2;
	}
	
	public A04EgtCfmVo getCalvo1() {
		return calvo1;
	}
	public void setCalvo1(A04EgtCfmVo calvo1) {
		this.calvo1 = calvo1;
	}
	public A04EgtCfmVo getCalvo2() {
		return calvo2;
	}
	public void setCalvo2(A04EgtCfmVo calvo2) {
		this.calvo2 = calvo2;
	}
	
	
	public void insertToTable() throws Exception{
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("INSERT INTO A_DFD_A01CFM56_COMPUTE(");
		sb.append("ID, MSG_NO, ACNUM, RPTDATE");
		sb.append(",ESN_1,ESN_2,ISCHANGEPOINT1,ISCHANGEPOINT2, EGT_MARGIN_1, EGT_MARGIN_2");
		sb.append(",THETA,VAREXP_1,EGTK_1,EGTKM2_1,HDEGTM_1,SLOATL_1,DELTA_EGTM_1,DELTA_T3_1,DELTA_N2_1,DELTA_FF_1");
		sb.append("      ,VAREXP_2,EGTK_2,EGTKM2_2,HDEGTM_2,SLOATL_2,DELTA_EGTM_2,DELTA_T3_2,DELTA_N2_2,DELTA_FF_2");
		
		sb.append(", DIV_EGT_MARGIN1, DIV_EGT_MARGIN2, DIV_EGT_1, DIV_EGT_2");
		sb.append(", DIV_N2_1, DIV_N2_2, DIV_FF_1, DIV_FF_2, DIV_T3_1, DIV_T3_2, DIV_OIP_1, DIV_OIP_2");
		sb.append(", DIV_OIT_1, DIV_OIT_2, DIV_P25PT2_1, DIV_P25PT2_2, DIV_P3P25_1, DIV_P3P25_2");
		sb.append(",update_date) values(?,?,?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?,?,?,?");
		sb.append("  ,?,?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?");
		sb.append(",?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?");
		sb.append(",sysdate)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), id,msgno,acnum,date_utc
				,esn_1,esn_2,isChangePoint1,isChangePoint2, egt_margin_1, egt_margin_2
				,calvo1.getTheta(),calvo1.getVarexp(),calvo1.getEgtk(),calvo1.getEgtkm2(),calvo1.getHdegtm()
				,calvo1.getSloatl(),calvo1.getDelta_egtm(),calvo1.getDelta_t3(),calvo1.getDelta_n2(),calvo1.getDelta_ff()
				 				  ,calvo2.getVarexp(),calvo2.getEgtk(),calvo2.getEgtkm2(),calvo2.getHdegtm()
				,calvo2.getSloatl(),calvo2.getDelta_egtm(),calvo2.getDelta_t3(),calvo2.getDelta_n2(),calvo2.getDelta_ff()
				, div_egt_margin1, div_egt_margin2, div_egt_1, div_egt_2
				, div_n2_1, div_n2_2, div_ff_1, div_ff_2, div_t3_1, div_t3_2, div_oip_1, div_oip_2
				, div_oit_1, div_oit_2, div_p25pt2_1, div_p25pt2_2, div_p3p25_1, div_p3p25_2
			);
		
		logger.debug("插入A01Cfm ["+msgno+"] 报文计算数据！");
	}
	
}
