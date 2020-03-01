package com.apms.bs.datacompute;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.datacompute.vo.A24ComputeVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.bs.util.MathUtil;
import com.apms.vo.ApmsVarConst;

public class A24DataComputeService {
	private String changeMsgno = "1";
	boolean isChangePoint = false;
	boolean isTTest = false;
	
	private Logger logger = NovaLogger.getLogger(this.getClass());// 日志
	
	/**
	 * ECS地面报文计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA24Data(String acnum) throws Exception {

		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,L.RPTDATE,L.CODE,L.ESN_1,L.EHRS_1,L.ECYC_1,L.AP_1,L.Y1_1,L.LIM_1,L.NL_1");
		qrySb.append(",L.ESN_2,L.EHRS_2,L.ECYC_2,L.AP_2,L.Y1_2,L.LIM_2,L.N1_E1,L.N2_E1,L.PF_E1,L.COT_E1,L.RI_E1,L.RO_E1,L.PBV_E1,L.FCV_E1");
		qrySb.append(",L.N1_N1,L.N2_N1,L.PF_N1,L.COT_N1,L.RI_N1,L.RO_N1,L.PBV_N1,L.FCV_N1,L.P3_S1,L.T3_S1,L.TW_S1,L.TP_S1,L.TPO_S1,L.PD_S1,L.ALT_S1,L.PS_S1");
		qrySb.append(",L.P3_T1,L.T3_T1,L.TW_T1,L.TP_T1,L.TPO_T1,L.PD_T1,L.ALT_T1,L.PS_T1,L.TAT_V1,L.SAT_V1,L.ZCB_V1,L.ZLD_V1,L.SC1_V1,L.SC2_V1,L.SC3_V1,L.RV_V1");
		qrySb.append(",L.PCSW_X1,L.VSCB_X1,L.PDC_X1,L.VF_X1,L.VW_X1,L.VA_X1,L.OVP_X1,L.CPC_X1");
		qrySb.append(",(SELECT M.MODELSERIES FROM B_AIRCRAFT_MODEL M,B_AIRCRAFT A WHERE M.ID=A.ACMODELID AND A.AIRCRAFTSN=T.ACNUM) MODELSERIES");
		qrySb.append(",L.CKT_F1,L.FWDT_F1,L.AFTT_F1");
		qrySb.append(",T.RPTNO,T.ACMODEL,L.TAPRV_H1,L.TAV_H1,L.MAINCTL_H1,L.SECDCTL_H1,L.PDMT_L_D1,PDMT_R_D1");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A24_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND T.RPTNO='A24' AND T.STATUS=0"); // and
																					// L.acnum='B6882'

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}
		// 测试条件and
		// qrySb.append(" and t.acnum = 'B6033' ");
		// qrySb.append(" and t.msg_no= 197266838 ");
		// qrySb.append(" and L.rptdate> to_date('2013-03-01','YYYY-MM-DD')");

		qrySb.append(" ORDER BY L.MSG_NO");

		String testSql = "select * from (" + qrySb.toString() + ") where rownum<10000";

		CommDMO dmo = new CommDMO();
		int num = 0;
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");

				try {
					logger.debug("开始计算[" + msgno + "]报文数据");
					A24ComputeVo computedVo = compute24ByHistoryVos(vo);
					AlarmMonitorTargetVo targetVo = DataComputeUtil.getAlarmTargetVo(vo);
					targetVo.setMsgno(msgno);
					targetVo.setDateUtc(computedVo.getDate_utc());
					targetVo.setAcnum(computedVo.getAcnum());
				    targetVo.addParam("COMPUTEDVO", computedVo);

					HashMap<String, Object> targetMap = targetVo.getParamMap();
					// 开关字段添加
					targetMap.put("TAPRV_H1", vo.getAttributeValue("TAPRV_H1"));
					targetMap.put("TAV_H1", vo.getAttributeValue("TAV_H1"));
					targetMap.put("MAINCTL_H1", vo.getAttributeValue("MAINCTL_H1"));
					targetMap.put("SECDCTL_H1", vo.getAttributeValue("SECDCTL_H1"));
					// 差 分门限字段
					targetMap.put("DIV_PDMT_L_D1", computedVo.getDiv_pdmt_l_d1() + "");
					targetMap.put("DIV_PF_E1", computedVo.getDiv_pf_n1() + "");
					targetMap.put("DIV_PD_S1", computedVo.getDiv_pd_s1() + "");
					targetMap.put("DIV_SC1_V1", computedVo.getDiv_sc1_v1() + "");
					targetMap.put("DIV_SC2_V1", computedVo.getDiv_sc2_v1() + "");
					targetMap.put("DIV_SC3_V1", computedVo.getDiv_sc3_v1() + "");
					targetMap.put("DIV_COT_E1", computedVo.getDiv_cot_e1() + "");
					targetMap.put("DIV_RI_E1", computedVo.getDiv_ri_e1() + "");
					targetMap.put("DIV_PBV_E1", computedVo.getDiv_pbv_e1() + "");
					targetMap.put("DIV_TW_S1", computedVo.getDiv_tw_s1() + "");
					targetMap.put("DIV_TP_S1", computedVo.getDiv_tp_s1() + "");
					targetMap.put("DIV_PDMT_TW1", computedVo.getDiv_pdmt_tw1() + "");
					targetMap.put("DIV_PDMT_TW2", computedVo.getDiv_pdmt_tw2() + "");

					// 飘点
					int v_pointtype_sc1ckt = computedVo.getFieldVo_sc1ckt().getV_pointtype();
					int v_out_sc1_ckt = computedVo.getFieldVo_sc1ckt().getV_out();
					targetMap.put("V_POINTTYPE_DIV_SC1_V1", v_pointtype_sc1ckt);
					targetMap.put("V_OUT_DIV_SC1_V1", v_out_sc1_ckt);

					int v_pointtype_pf = computedVo.getFieldVo_pf().getV_pointtype();
					int v_out_pf = computedVo.getFieldVo_pf().getV_out();
					// boolean pf_floatPointFlag=v_out_pf==1 &&
					// v_pointtype_pf==0;
					targetMap.put("V_POINTTYPE_DIV_PF_E1", v_pointtype_pf);
					targetMap.put("V_OUT_DIV_PF_E1", v_out_pf);

					int v_pointtype_Sc2fwdt = computedVo.getFieldVo_sc2fwdt().getV_pointtype();
					int v_out_sc2fwdt = computedVo.getFieldVo_sc2fwdt().getV_out();
					targetMap.put("V_POINTTYPE_DIV_SC2_V1", v_pointtype_Sc2fwdt);
					targetMap.put("V_OUT_DIV_SC2_V1", v_out_sc2fwdt);

					int v_pointtype_sc3aftt = computedVo.getFieldVo_sc3aftt().getV_pointtype();
					int v_out_sc3aftt = computedVo.getFieldVo_sc3aftt().getV_out();
					targetMap.put("V_POINTTYPE_DIV_SC3_V1", v_pointtype_sc3aftt);
					targetMap.put("V_OUT_DIV_SC3_V1", v_out_sc3aftt);

					int v_pointtype_cot = computedVo.getFieldVo_cot().getV_pointtype();
					int v_out_cot = computedVo.getFieldVo_cot().getV_out();
					targetMap.put("V_POINTTYPE_DIV_COT_E1", v_pointtype_cot);
					targetMap.put("V_OUT_DIV_COT_E1", v_out_cot);

					int v_pointtype_ri = computedVo.getFieldVo_ri().getV_pointtype();
					int v_out_ri = computedVo.getFieldVo_ri().getV_out();
					targetMap.put("V_POINTTYPE_DIV_RI_E1", v_pointtype_ri);
					targetMap.put("V_OUT_DIV_RI_E1", v_out_ri);

					int v_pointtype_pbv = computedVo.getFieldVo_pbv().getV_pointtype();
					int v_out_pbv = computedVo.getFieldVo_pbv().getV_out();
					targetMap.put("V_POINTTYPE_DIV_PBV_E1", v_pointtype_pbv);
					targetMap.put("V_OUT_DIV_PBV_E1", v_out_pbv);

					if (computedVo.getFieldVo_d1() != null) {
						int v_pointtype_d1 = computedVo.getFieldVo_d1().getV_pointtype();
						int v_out_d1 = computedVo.getFieldVo_d1().getV_out();
						targetMap.put("V_POINTTYPE_DIV_PDMT_L_D1", v_pointtype_d1);
						targetMap.put("V_OUT_DIV_PDMT_L_D1", v_out_d1);
					}

					int v_pointtype_tw = computedVo.getFieldVo_tw().getV_pointtype();
					int v_out_tw = computedVo.getFieldVo_tw().getV_out();
					targetMap.put("V_POINTTYPE_DIV_TW_S1", v_pointtype_tw);
					targetMap.put("V_OUT_DIV_TW_S1", v_out_tw);

					int v_pointtype_tp = computedVo.getFieldVo_tp().getV_pointtype();
					int v_out_tp = computedVo.getFieldVo_tp().getV_out();
					targetMap.put("V_POINTTYPE_DIV_TP_S1", v_pointtype_tp);
					targetMap.put("V_OUT_DIV_TP_S1", v_out_tp);
					
					// 原值点门限字段
					targetMap.put("PF_N1", vo.getAttributeValue("PF_N1"));
					targetMap.put("PF_E1", vo.getAttributeValue("PF_E1"));
					targetMap.put("COT_E1", vo.getAttributeValue("COT_E1"));
					targetMap.put("COT_N1", vo.getAttributeValue("COT_N1"));
					targetMap.put("RI_E1", vo.getAttributeValue("RI_E1"));
					targetMap.put("RI_N1", vo.getAttributeValue("RI_N1"));
					targetMap.put("PBV_E1", vo.getAttributeValue("PBV_E1"));
					targetMap.put("PBV_N1", vo.getAttributeValue("PBV_N1"));
					targetMap.put("TW_S1", vo.getAttributeValue("TW_S1"));
					targetMap.put("TW_T1", vo.getAttributeValue("TW_T1"));
					targetMap.put("TP_S1", vo.getAttributeValue("TP_S1"));
					targetMap.put("TP_T1", vo.getAttributeValue("TP_T1"));
					targetMap.put("PDMT_L_D1", vo.getAttributeValue("PDMT_L_D1"));
					targetMap.put("PDMT_R_D1", vo.getAttributeValue("PDMT_R_D1"));
					targetMap.put("ZCB_V1", vo.getAttributeValue("ZCB_V1"));
					targetMap.put("SAT_V1", vo.getAttributeValue("SAT_V1"));
					targetMap.put("TAT_V1", vo.getAttributeValue("TAT_V1"));
					targetMap.put("MIXF_G1", vo.getAttributeValue("MIXF_G1"));
					targetMap.put("MIXCAB_G1", vo.getAttributeValue("MIXCAB_G1"));
					targetMap.put("SC1_V1", vo.getAttributeValue("SC1_V1"));
					targetMap.put("CKT_F1", vo.getAttributeValue("CKT_F1"));
					targetMap.put("SC2_V1", vo.getAttributeValue("SC2_V1"));
					targetMap.put("FWDT_F1", vo.getAttributeValue("FWDT_F1"));
					targetMap.put("SC3_V1", vo.getAttributeValue("SC3_V1"));
					targetMap.put("AFTT_F1", vo.getAttributeValue("AFTT_F1"));
					targetMap.put("OVP_X1", vo.getAttributeValue("OVP_X1"));
					targetMap.put("CKDUCT_G1", vo.getAttributeValue("CKDUCT_G1"));
					targetMap.put("FWDUCT_G1", vo.getAttributeValue("FWDUCT_G1"));
					targetMap.put("AFTDUCT_G1", vo.getAttributeValue("AFTDUCT_G1"));
					//飘点告警门限值限制
					Map<String,String> floatPointLimitValueMap=new HashMap<String,String>();
					floatPointLimitValueMap.put("DIV_PF_E1", "0.075");
					floatPointLimitValueMap.put("DIV_COT_E1", "10");
					floatPointLimitValueMap.put("DIV_RI_E1", "20");
					floatPointLimitValueMap.put("DIV_PBV_E1", "10");
					floatPointLimitValueMap.put("DIV_TW_S1", "10");
					floatPointLimitValueMap.put("DIV_TP_S1", "10");
					floatPointLimitValueMap.put("DIV_PDMT_L_D1", "2");
					floatPointLimitValueMap.put("DIV_PD_S1", "1.5");
					floatPointLimitValueMap.put("DIV_SC1_V1", "1.5");//由于差分是差值的1/2，所以3改成1.5
					floatPointLimitValueMap.put("DIV_SC2_V1", "1.5");
					floatPointLimitValueMap.put("DIV_SC3_V1", "1.5");
					floatPointLimitValueMap.put("DIV_PDMT_TW1", "2.5");//由于差分是差值的1/2，所以要除以2,5变成2.5
					floatPointLimitValueMap.put("DIV_PDMT_TW2", "2.5");
					targetMap.put("floatPointLimitValueMap",floatPointLimitValueMap);
					// 此条报文计算完成后，进行告警触发
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A24_COMPUTE, msgno, targetVo);

					// 更新数据状态为已计算
					DataComputeUtil.updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num++;
				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					DataComputeUtil.updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
		} catch (Exception e) {
			logger.error("A24报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}
	
	/**
	 * 根据当前和历史N条记录，计算报文数据
	 * @param curVo 要计算的报文数据
	 * @throws Exception
	 */
	public A24ComputeVo compute24ByHistoryVos(HashVO curVo) throws Exception{
		A24ComputeVo computedVo = new A24ComputeVo();
		
		String rptno = ApmsVarConst.RPTNO_A24;	
		
		DfdFieldCompute fieldComp = new DfdFieldCompute();
		
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		Date date_utc = curVo.getDateValue("RPTDATE");

		String esn_1 = curVo.getStringValue("ESN_1");
		String esn_2 = curVo.getStringValue("ESN_2");
		
		double pf_e1 = curVo.getDoubleValue("PF_E1");
		double pf_n1 = curVo.getDoubleValue("PF_N1");
		double cot_e1 = curVo.getDoubleValue("COT_E1");
		double cot_n1 = curVo.getDoubleValue("COT_N1");
		double ri_e1 = curVo.getDoubleValue("RI_E1");
		double ri_n1 = curVo.getDoubleValue("RI_N1");
		double pbv_e1 = curVo.getDoubleValue("PBV_E1");
		double pbv_n1 = curVo.getDoubleValue("PBV_N1");
		double tw_s1 = curVo.getDoubleValue("TW_S1");
		double tw_t1 = curVo.getDoubleValue("TW_T1");
		double tp_s1 = curVo.getDoubleValue("TP_S1");
		double tp_t1 = curVo.getDoubleValue("TP_T1");	
		double pd_s1 = curVo.getDoubleValue("PD_S1");
		double pd_t1 = curVo.getDoubleValue("PD_T1");
		double pdmt_l_d1 =0;
		Double pdmt_l_d1temp= curVo.getDoubleValue("PDMT_L_D1");
		if(pdmt_l_d1temp!=null){
			pdmt_l_d1= pdmt_l_d1temp;
		}
		
		double pdmt_r_d1 =0;
		Double pdmt_r_d1temp=curVo.getDoubleValue("PDMT_R_D1");
		if(pdmt_r_d1temp!=null){
			pdmt_r_d1=pdmt_r_d1temp;
		}
		double sc1_v1 =0;
		Double sc1_v1temp= curVo.getDoubleValue("SC1_V1");
		if(sc1_v1temp!=null){
			sc1_v1=sc1_v1temp;
		}
		double ckt_f1 =0;
		Double ckt_f1temp= curVo.getDoubleValue("CKT_F1");
		if(ckt_f1temp!=null){
			ckt_f1=ckt_f1temp;
		}
		double sc2_v1 =0;
		Double sc2_v1temp= curVo.getDoubleValue("SC2_V1");
		if(sc2_v1temp!=null){
			sc2_v1=sc2_v1temp;
		}
		double fwdt_f1 =0;
		Double fwdt_f1temp= curVo.getDoubleValue("FWDT_F1");
		if(fwdt_f1temp!=null){
			fwdt_f1=fwdt_f1temp;
		} 
		double sc3_v1 =0;
		Double sc3_v1temp= curVo.getDoubleValue("SC3_V1");
		if(sc3_v1temp!=null){
			sc3_v1=sc3_v1temp;
		}
		double aftt_f1 =0;
		Double aftt_f1temp= curVo.getDoubleValue("AFTT_F1");
		if(aftt_f1temp!=null){
			aftt_f1=aftt_f1temp;
		}
		double ovp_x1Str = curVo.getDoubleValue("OVP_X1");
		
		String modelseries = curVo.getStringValue("MODELSERIES");
		computedVo.setModelSeries(modelseries);
		//计算各个参数是否超限
		int ex_count = limitCompute(msgno);
		computedVo.setEx_count(ex_count);
		
		double div_tw_s1 = MathUtil.deviation(tw_s1,tw_t1);
		computedVo.setDiv_tw_s1(div_tw_s1);
		double div_tw_t1 = MathUtil.deviation(tw_t1,tw_s1);
		computedVo.setDiv_tw_t1(div_tw_t1);
		double tw_div = MathUtil.subtract(tw_s1,tw_t1);
		computedVo.setTw_div(tw_div);
		
		double div_tp_s1 = MathUtil.deviation(tp_s1,tp_t1);
		computedVo.setDiv_tp_s1(div_tp_s1);
		double div_tp_t1 = MathUtil.deviation(tp_t1,tp_s1);
		computedVo.setDiv_tp_t1(div_tp_t1);
		double tp_div = MathUtil.subtract(tp_s1,tp_t1);
		computedVo.setTp_div(tp_div);
		
		double div_pbv_e1 = MathUtil.deviation(pbv_e1,pbv_n1);
		computedVo.setDiv_pbv_e1(div_pbv_e1);
		double div_pbv_n1 = MathUtil.deviation(pbv_n1,pbv_e1);
		computedVo.setDiv_pbv_n1(div_pbv_n1);
		double pbv_div = MathUtil.subtract(pbv_e1,pbv_n1);
		computedVo.setPbv_div(pbv_div);
		
		double div_pf_e1 = MathUtil.deviation(pf_e1,pf_n1);
		computedVo.setDiv_pf_e1(div_pf_e1);
		double div_pf_n1 = MathUtil.deviation(pf_n1,pf_e1);
		computedVo.setDiv_pf_n1(div_pf_n1);
		double pf_div = MathUtil.subtract(pf_e1,pf_n1);
		computedVo.setPf_div(pf_div);
		
		double div_cot_e1 = MathUtil.deviation(cot_e1,cot_n1);
		computedVo.setDiv_cot_e1(div_cot_e1);
		double div_cot_n1 = MathUtil.deviation(cot_n1,cot_e1);
		computedVo.setDiv_cot_n1(div_cot_n1);
		double cot_div = MathUtil.subtract(cot_e1,cot_n1);
		computedVo.setCot_div(cot_div);
		
		double div_pd_s1 = MathUtil.deviation(pd_s1,pd_t1);
		computedVo.setDiv_pd_s1(div_pd_s1);
		double div_pd_t1 = MathUtil.deviation(pd_t1,pd_s1);
		computedVo.setDiv_pd_t1(div_pd_t1);
		double pd_div = MathUtil.subtract(pd_s1,pd_t1);
		computedVo.setPd_div(pd_div);
		
		double div_ri_e1 = MathUtil.deviation(ri_e1,ri_n1);
		computedVo.setDiv_ri_e1(div_ri_e1);
		double div_ri_n1 = MathUtil.deviation(ri_n1,ri_e1);
		computedVo.setDiv_ri_n1(div_ri_n1);
		double ri_div = MathUtil.subtract(ri_e1,ri_n1);
		computedVo.setRi_div(ri_div);
		
		if(sc1_v1temp!=null && ckt_f1temp!=null){
			double div_sc1_v1 = MathUtil.deviation(sc1_v1,ckt_f1);
			computedVo.setDiv_sc1_v1(div_sc1_v1);
			double div_ckt_f1 = MathUtil.deviation(ckt_f1,sc1_v1);
			computedVo.setDiv_ckt_f1(div_ckt_f1);
			double sub_sc1_ckt =MathUtil.subtract(sc1_v1,ckt_f1);
			computedVo.setSub_sc1_ckt(sub_sc1_ckt);
			double sc1ckt_div = MathUtil.subtract(sc1_v1,ckt_f1);
			computedVo.setSc1ckt_div(sc1ckt_div);
		}		
		
		if(sc2_v1temp!=null && fwdt_f1temp!=null){
			double div_sc2_v1 = MathUtil.deviation(sc2_v1,fwdt_f1);
			computedVo.setDiv_sc2_v1(div_sc2_v1);
			double div_fwdt_f1 = MathUtil.deviation(fwdt_f1,sc2_v1);
			computedVo.setDiv_fwdt_f1(div_fwdt_f1);
			double sub_sc2_fwdt =MathUtil.subtract(sc2_v1,fwdt_f1);
			computedVo.setSub_sc2_fwdt(sub_sc2_fwdt);
			double sc2fwdt_div = MathUtil.subtract(sc2_v1,fwdt_f1);
			computedVo.setSc2fwdt_div(sc2fwdt_div);
		}
		
		if(sc3_v1temp!=null&&sc3_v1temp!=null){
			double div_sc3_v1 = MathUtil.deviation(sc3_v1,aftt_f1);
			computedVo.setDiv_sc3_v1(div_sc3_v1);
			double div_aftt_f1 = MathUtil.deviation(aftt_f1,sc3_v1);
			computedVo.setDiv_aftt_f1(div_aftt_f1);
			double sub_sc3_aftt =MathUtil.subtract(sc3_v1,aftt_f1);
			computedVo.setSub_sc3_aftt(sub_sc3_aftt);
			double sc3aftt_div = MathUtil.subtract(sc3_v1,aftt_f1);
			computedVo.setSc3aftt_div(sc3aftt_div);
		}
		
		if(pdmt_l_d1temp!=null && pdmt_r_d1temp!=null){
			double div_pdmt_l_d1 = MathUtil.deviation(pdmt_l_d1,pdmt_r_d1);
			computedVo.setDiv_pdmt_l_d1(div_pdmt_l_d1);
			double div_pdmt_r_d1 = MathUtil.deviation(pdmt_r_d1,pdmt_l_d1);
			computedVo.setDiv_pdmt_r_d1(div_pdmt_r_d1);
			double d1_div = MathUtil.subtract(pdmt_l_d1,pdmt_r_d1);
			computedVo.setD1_div(d1_div);
		}
		
		double div_pdmt_tw1 = MathUtil.deviation(pdmt_l_d1,tw_s1);
		computedVo.setDiv_pdmt_tw1(div_pdmt_tw1);
		double div_pdmt_tw2 = MathUtil.deviation(pdmt_r_d1,tw_t1);
		computedVo.setDiv_pdmt_tw2(div_pdmt_tw2);
		double pdmt_tw1_div = MathUtil.subtract(pdmt_l_d1,tw_s1);
		computedVo.setPdmt_tw1_div(pdmt_tw1_div);
		double pdmt_tw2_div = MathUtil.subtract(pdmt_r_d1,tw_t1);
		computedVo.setPdmt_tw2_div(pdmt_tw2_div);

		computedVo.setEsn_1(esn_1);
		computedVo.setEsn_2(esn_2);
		computedVo.setId(curVo.getStringValue("ID"));
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setDate_utc(date_utc);


		String x_fieldname = "RPTDATE_HOURS";
		long s = computedVo.getDate_utc().getTime();
		
		Double x_value = (double) s/3600000;
		String f_name = "PF_DIV";
		double f_value = computedVo.getPf_div();
		FieldComputeVo VarPFVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, VarPFVo, changeMsgno, isChangePoint, isTTest,true,true);
		VarPFVo.insertToTable();
		
		computedVo.setPF_E1(pf_e1);
		f_name = "PF_E1";
		f_value = computedVo.getPF_E1();
		FieldComputeVo VarPF_E1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarPF_E1Vo, changeMsgno, isChangePoint);
		VarPF_E1Vo.insertToRollNTable();
		
		computedVo.setPF_N1(pf_n1);
		f_name = "PF_N1";
		f_value = computedVo.getPF_N1();
		FieldComputeVo VarPF_N1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarPF_N1Vo, changeMsgno, isChangePoint);
		VarPF_N1Vo.insertToRollNTable();
		
		computedVo.setCOT_E1(cot_e1);
		f_name = "COT_E1";
		f_value = computedVo.getCOT_E1();
		FieldComputeVo VarCOT_E1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarCOT_E1Vo, changeMsgno, isChangePoint);
		VarCOT_E1Vo.insertToRollNTable();
		
		computedVo.setCOT_N1(cot_n1);
		f_name = "COT_N1";
		f_value = computedVo.getCOT_N1();
		FieldComputeVo VarCOT_N1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarCOT_N1Vo, changeMsgno, isChangePoint);
		VarCOT_N1Vo.insertToRollNTable();
		
		computedVo.setRI_E1(ri_e1);
		f_name = "RI_E1";
		f_value = computedVo.getRI_E1();
		FieldComputeVo VarRI_E1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarRI_E1Vo, changeMsgno, isChangePoint);
		VarRI_E1Vo.insertToRollNTable();
		
		computedVo.setRI_N1(ri_n1);
		f_name = "RI_N1";
		f_value = computedVo.getRI_N1();
		FieldComputeVo VarRI_N1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarRI_N1Vo, changeMsgno, isChangePoint);
		VarRI_N1Vo.insertToRollNTable();
		
		computedVo.setPBV_E1(pbv_e1);
		f_name = "PBV_E1";
		f_value = computedVo.getPBV_E1();
		FieldComputeVo VarPBV_E1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarPBV_E1Vo, changeMsgno, isChangePoint);
		VarPBV_E1Vo.insertToRollNTable();
		
		computedVo.setPBV_N1(pbv_n1);
		f_name = "PBV_N1";
		f_value = computedVo.getPBV_N1();
		FieldComputeVo VarPBV_N1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarPBV_N1Vo, changeMsgno, isChangePoint);
		VarPBV_N1Vo.insertToRollNTable();
		
		computedVo.setTW_S1(tw_s1);
		f_name = "TW_S1";
		f_value = computedVo.getTW_S1();
		FieldComputeVo VarTW_S1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarTW_S1Vo, changeMsgno, isChangePoint);
		VarTW_S1Vo.insertToRollNTable();
		
		computedVo.setTW_T1(tw_t1);
		f_name = "TW_T1";
		f_value = computedVo.getTW_T1();
		FieldComputeVo VarTW_T1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarTW_T1Vo, changeMsgno, isChangePoint);
		VarTW_T1Vo.insertToRollNTable();
		
		computedVo.setTP_S1(tp_s1);
		f_name = "TP_S1";
		f_value = computedVo.getTP_S1();
		FieldComputeVo VarTP_S1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarTP_S1Vo, changeMsgno, isChangePoint);
		VarTP_S1Vo.insertToRollNTable();
		
		computedVo.setTP_T1(tp_t1);
		f_name = "TP_T1";
		f_value = computedVo.getTP_T1();
		FieldComputeVo VarTP_T1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarTP_T1Vo, changeMsgno, isChangePoint);
		VarTP_T1Vo.insertToRollNTable();
		
		computedVo.setPDMT_L_D1(pdmt_l_d1);
		f_name = "PDMT_L_D1";
		f_value = computedVo.getPDMT_L_D1();
		FieldComputeVo VarPDMT_L_D1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarPDMT_L_D1Vo, changeMsgno, isChangePoint);
		VarPDMT_L_D1Vo.insertToRollNTable();
		
		computedVo.setPDMT_R_D1(pdmt_r_d1);
		f_name = "PDMT_R_D1";
		f_value = computedVo.getPDMT_R_D1();
		FieldComputeVo VarPDMT_R_D1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarPDMT_R_D1Vo, changeMsgno, isChangePoint);
		VarPDMT_R_D1Vo.insertToRollNTable();
		
		computedVo.setPD_S1(pd_s1);
		f_name = "PD_S1";
		f_value = computedVo.getPD_S1();
		FieldComputeVo VarPD_S1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarPD_S1Vo, changeMsgno, isChangePoint);
		VarPD_S1Vo.insertToRollNTable();
		
		computedVo.setPD_T1(pd_t1);
		f_name = "PD_T1";
		f_value = computedVo.getPD_T1();
		FieldComputeVo VarPD_T1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, VarPD_T1Vo, changeMsgno, isChangePoint);
		VarPD_T1Vo.insertToRollNTable();
		
		f_name = "COT_DIV";
		f_value = computedVo.getCot_div();
		FieldComputeVo VarCOTVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, VarCOTVo, changeMsgno, isChangePoint, isTTest,true,true);
		VarCOTVo.insertToTable();

		f_name = "RI_DIV";
		f_value = computedVo.getRi_div();
		FieldComputeVo VarRIVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, VarRIVo, changeMsgno, isChangePoint, isTTest,true,true);
		VarRIVo.insertToTable();
		
		f_name = "PBV_DIV";
		f_value = computedVo.getPbv_div();
		FieldComputeVo VarPBVVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, VarPBVVo, changeMsgno, isChangePoint, isTTest,true,true);
		VarPBVVo.insertToTable();
		
		f_name = "TW_DIV";
		f_value = computedVo.getTw_div();
		FieldComputeVo VarTWVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, VarTWVo, changeMsgno, isChangePoint, isTTest,true,true);
		VarTWVo.insertToTable();
		
		f_name = "TP_DIV";
		f_value = computedVo.getTp_div();
		FieldComputeVo VarTPVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, VarTPVo, changeMsgno, isChangePoint, isTTest,true,true);
		VarTPVo.insertToTable();
		
		f_name = "D1_DIV";
		f_value = computedVo.getD1_div();
		FieldComputeVo d1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, d1Vo, changeMsgno, isChangePoint, isTTest,true,true);
		d1Vo.insertToTable();
		
		f_name = "SC1CKT_DIV";
		f_value = computedVo.getSc1ckt_div();
		FieldComputeVo sc1cktVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, sc1cktVo, changeMsgno, isChangePoint, isTTest,true,true);
		sc1cktVo.insertToTable();
		
		f_name = "SC2FWDT_DIV";
		f_value = computedVo.getSc2fwdt_div();
		FieldComputeVo sc2fwdtVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, sc2fwdtVo, changeMsgno, isChangePoint, isTTest,true,true);
		sc2fwdtVo.insertToTable();
		
		f_name = "SC3AFTT_DIV";
		f_value = computedVo.getSc3aftt_div();
		FieldComputeVo sc3afttVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, sc3afttVo, changeMsgno, isChangePoint, isTTest,true,true);
		sc3afttVo.insertToTable();
		
		f_name = "OVP_X1";
		f_value = ovp_x1Str;
		FieldComputeVo ovp_x1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, ovp_x1Vo, changeMsgno, isChangePoint, isTTest,true,true);
		ovp_x1Vo.insertToTable();
		
		f_name = "PD_DIV";
		f_value = computedVo.getPd_div();
		FieldComputeVo VarPDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, VarPDVo, changeMsgno, isChangePoint, isTTest,true,true);
		VarPDVo.insertToTable();
		
		f_name = "PDMT_TW1_DIV";
		f_value = computedVo.getPdmt_tw1_div();
		FieldComputeVo VarPdmt_tw1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, VarPdmt_tw1Vo, changeMsgno, isChangePoint, isTTest,true,true);
		VarPdmt_tw1Vo.insertToTable();
		
		f_name = "PDMT_TW2_DIV";
		f_value = computedVo.getPdmt_tw2_div();
		FieldComputeVo VarPdmt_tw2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, VarPdmt_tw2Vo, changeMsgno, isChangePoint, isTTest,true,true);
		VarPdmt_tw2Vo.insertToTable();

	 	computedVo.setFieldVo_pf(VarPFVo);
	 	computedVo.setFieldVo_cot(VarCOTVo);
	 	computedVo.setFieldVo_ri(VarRIVo);
	 	computedVo.setFieldVo_pbv(VarPBVVo);
	 	computedVo.setFieldVo_tw(VarTWVo);
	 	computedVo.setFieldVo_tp(VarTPVo);
	 	computedVo.setFieldVo_pbv(d1Vo);
	 	computedVo.setFieldVo_sc1ckt(sc1cktVo);
	 	computedVo.setFieldVo_sc2fwdt(sc2fwdtVo);
	 	computedVo.setFieldVo_sc3aftt(sc3afttVo);
	 	
		computedVo.insertToTable();
					
		return computedVo;
		
	}

	/**
	 * 计算各个参数数据是否超限值
	 * @param msg_no
	 * @return
	 */
	private int limitCompute(String msg_no) throws Exception{
		CommDMO dmo = new CommDMO();
		Connection conn = dmo.getConn(ApmsConst.DS_APMS).getConn();
		//创建存储过程的对象  
		CallableStatement c=conn.prepareCall("{call P_A24_LIMIT_STATISTIC_1(?,?)}");   
		//给存储过程的第一个参数设置值  
		c.setLong(1,new Long(msg_no).intValue());   
		//注册存储过程的第二个参数 
		c.registerOutParameter(2,java.sql.Types.INTEGER);   
		//执行存储过程  
		c.execute();   
		//得到存储过程的输出参数值  超门限数量
		int ex_count = c.getInt(2);
		 
		return ex_count;
		
	}


}
