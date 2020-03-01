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
import com.apms.bs.datacompute.vo.A21ComputeVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.bs.util.MathUtil;
import com.apms.vo.ApmsVarConst;

public class A21DataComputeService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());// 日志
	
	/**
	 * ECS巡航报文计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA21Data(String acnum) throws Exception {
		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,L.RPTDATE,L.CODE,L.ESN_1,L.EHRS_1,L.ECYC_1,");
		qrySb.append(" L.AP_1,L.Y1_1,L.LIM_1,L.NL_1,L.ESN_2,L.EHRS_2,L.ECYC_2,L.AP_2,L.Y1_2,L.LIM_2,L.N1_E1,L.N2_E1,L.PF_E1");
		qrySb.append(" ,L.COT_E1,L.RI_E1,L.RO_E1,L.PBV_E1,L.FCV_E1,L.N1_N1,L.N2_N1,L.PF_N1,L.COT_N1,L.RI_N1,L.RO_N1,L.PBV_N1,L.FCV_N1");
		qrySb.append(" ,L.P3_S1,L.T3_S1,L.TW_S1,L.TP_S1,L.TPO_S1,L.PD_S1,L.ALT_S1,L.PS_S1,L.P3_T1,L.T3_T1,L.TW_T1,L.TP_T1,L.TPO_T1,L.PD_T1,L.ALT_T1");
		qrySb.append(" ,L.PS_T1,L.TAT_V1,L.SAT_V1,L.ZCB_V1,L.ZLD_V1,L.SC1_V1,L.SC2_V1,L.SC3_V1,L.RV_V1,L.PCSW_X1,L.VSCB_X1,L.PDC_X1,L.VF_X1");
		qrySb.append(" ,L.VW_X1,L.VA_X1,L.OVP_X1,L.CPC_X1,L.PB_WAI_W1,L.PB_PRV1_W1,L.PB_PRV2_W1,L.SW_XFR_W1,L.PIN_HPV_M1,L.PIN_PRV_M1");
		qrySb.append(" ,L.OPV1_M1,L.PIN_HPV_M2,L.PIN_PRV_M2,L.OPV2_M1,L.FAV1_Z1,L.FAV2_Z1,L.HPV1_Z1,L.HPV2_Z1,L.PRV1_Z1,L.PRV2_Z1");
		qrySb.append(" ,L.FAV1_R1,L.FAV2_R1,L.HPV1_R1,L.HPV2_R1,L.PRV1_R1,L.PRV2_R1");
		qrySb.append(",T.RPTNO,T.ACMODEL");
		qrySb.append(",(SELECT M.MODELSERIES FROM B_AIRCRAFT_MODEL M,B_AIRCRAFT A WHERE M.ID=A.ACMODELID AND A.AIRCRAFTSN=T.ACNUM) MODELSERIES");
		qrySb.append(",L.PDMT_L_D1,L.PDMT_R_D1");
		qrySb.append(",L.CKT_F1,L.FWDT_F1,L.AFTT_F1,L.CKDUCT_G1,L.FWDUCT_G1,L.AFTDUCT_G1,L.MIXF_G1,L.MIXCAB_G1");
		qrySb.append(",L.TAPRV_H1,L.TAV_H1,L.MAINCTL_H1,L.SECDCTL_H1");
		qrySb.append("  FROM A_DFD_HEAD T,A_DFD_A21_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND T.RPTNO='A21' AND T.STATUS=0");// and
		qrySb.append(" AND T.PH = '06'"); //只解析巡航段的A21报文

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}

		// 测试条件
		// qrySb.append(" and t.acnum = 'B6677' ");//B6031
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
					A21ComputeVo computedVo = compute21ByHistoryVos(vo);
					AlarmMonitorTargetVo targetVo = DataComputeUtil.getAlarmTargetVo(vo);
					targetVo.setMsgno(msgno);
					targetVo.setDateUtc(computedVo.getDate_utc());
					targetVo.setAcnum(computedVo.getAcnum());
					targetVo.setDateUtc(computedVo.getDate_utc());
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
					targetMap.put("DIV_SC1_V1", computedVo.getDiv_sc1_v1() + "");
					targetMap.put("DIV_SC2_V1", computedVo.getDiv_sc2_v1() + "");
					targetMap.put("DIV_SC3_V1", computedVo.getDiv_sc3_v1() + "");
					targetMap.put("DIV_COT_E1", computedVo.getDiv_cot_e1() + "");
					targetMap.put("DIV_RI_E1", computedVo.getDiv_ri_e1() + "");
					targetMap.put("DIV_PBV_E1", computedVo.getDiv_pbv_e1() + "");
					targetMap.put("DIV_TW_S1", computedVo.getDiv_tw_s1() + "");
					targetMap.put("DIV_TP_S1", computedVo.getDiv_tp_s1() + "");
					targetMap.put("DIV_PD_S1", computedVo.getDiv_pd_s1() + ""); // A21引气
					targetMap.put("DIV_TP_S1", computedVo.getDiv_tp_s1() + "");
					targetMap.put("DIV_PIN_PRV_M1", computedVo.getDiv_pin_prv_m1() + "");
					targetMap.put("DIV_TPO_S1", computedVo.getDiv_tpo_s1() + "");
					targetMap.put("DIV_N1_E1", computedVo.getDiv_n1_e1() + "");
					targetMap.put("DIV_N2_E1", computedVo.getDiv_n2_e1() + "");
					targetMap.put("DIV_P3_S1", computedVo.getDiv_p3_s1() + "");
					targetMap.put("DIV_T3_S1", computedVo.getDiv_t3_s1() + "");
					targetMap.put("DIV_PDMT_TW1", computedVo.getDiv_pdmt_tw1() + "");
					targetMap.put("DIV_PDMT_TW2", computedVo.getDiv_pdmt_tw2() + "");
					//差值门限告警
					targetMap.put("SUB_SC1_V1", computedVo.getSub_sc1_ckt() + "");
					targetMap.put("SUB_SC2_V1", computedVo.getSub_sc2_fwdt() + "");
					targetMap.put("SUB_SC3_V1", computedVo.getSub_sc3_aftt() + "");
					targetMap.put("SUB_PDMT_TW1", computedVo.getPdmt_tw1_div() + "");
					targetMap.put("SUB_PDMT_TW1", computedVo.getPdmt_tw2_div() + "");
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

					int v_pointtype_pd = computedVo.getFieldVo_pd().getV_pointtype();
					int v_out_pd = computedVo.getFieldVo_pd().getV_out();
					targetMap.put("V_POINTTYPE_DIV_PD_S1", v_pointtype_pd);
					targetMap.put("V_OUT_DIV_PD_S1", v_out_pd);

					int v_pointtype_prv = computedVo.getFieldVo_pin_prv().getV_pointtype();
					int v_out_prv = computedVo.getFieldVo_pin_prv().getV_out();
					targetMap.put("V_POINTTYPE_DIV_PIN_PRV_M1", v_pointtype_prv);
					targetMap.put("V_OUT_DIV_PIN_PRV_M1", v_out_prv);

					int v_pointtype_tpo = computedVo.getFieldVo_tpo().getV_pointtype();
					int v_out_tpo = computedVo.getFieldVo_tpo().getV_out();
					targetMap.put("V_POINTTYPE_DIV_TPO_S1", v_pointtype_tpo);
					targetMap.put("V_OUT_DIV_TPO_S1", v_out_tpo);

					int v_pointtype_n1 = computedVo.getFieldVo_n1().getV_pointtype();
					int v_out_n1 = computedVo.getFieldVo_n1().getV_out();
					targetMap.put("V_POINTTYPE_DIV_N1_E1", v_pointtype_n1);
					targetMap.put("V_OUT_DIV_N1_E1", v_out_n1);

					int v_pointtype_n2 = computedVo.getFieldVo_n2().getV_pointtype();
					int v_out_n2 = computedVo.getFieldVo_n2().getV_out();
					targetMap.put("V_POINTTYPE_DIV_N2_E1", v_pointtype_n2);
					targetMap.put("V_OUT_DIV_N2_E1", v_out_n2);

					int v_pointtype_p3 = computedVo.getFieldVo_p3().getV_pointtype();
					int v_out_p3 = computedVo.getFieldVo_n2().getV_out();
					targetMap.put("V_POINTTYPE_DIV_P3_S1", v_pointtype_p3);
					targetMap.put("V_OUT_DIV_P3_S1", v_out_p3);

					int v_pointtype_t3 = computedVo.getFieldVo_t3().getV_pointtype();
					int v_out_t3 = computedVo.getFieldVo_n2().getV_out();
					targetMap.put("V_POINTTYPE_DIV_T3_S1", v_pointtype_t3);
					targetMap.put("V_OUT_DIV_T3_S1", v_out_t3);
					
					int v_pointtype_pdmt_tw1 = computedVo.getFieldVo_pdmt_tw1().getV_pointtype();
					int v_pdmt_tw1 = computedVo.getFieldVo_pdmt_tw1().getV_out();
					targetMap.put("V_POINTTYPE_DIV_PDMT_TW1", v_pointtype_pdmt_tw1);
					targetMap.put("V_OUT_DIV_PDMT_TW1", v_pdmt_tw1);
					
					int v_pointtype_pdmt_tw2 = computedVo.getFieldVo_pdmt_tw2().getV_pointtype();
					int v_pdmt_tw2 = computedVo.getFieldVo_pdmt_tw2().getV_out();
					targetMap.put("V_POINTTYPE_DIV_PDMT_TW2", v_pointtype_pdmt_tw2);
					targetMap.put("V_OUT_DIV_PDMT_TW2", v_pdmt_tw2);
					
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
					targetMap.put("PD_S1", vo.getAttributeValue("PD_S1")); // A21引气
					targetMap.put("PD_T1", vo.getAttributeValue("PD_T1"));
					targetMap.put("PIN_PRV_M1", vo.getAttributeValue("PIN_PRV_M1"));
					targetMap.put("PIN_PRV_M2", vo.getAttributeValue("PIN_PRV_M2"));
					targetMap.put("TPO_S1", vo.getAttributeValue("TPO_S1"));
					targetMap.put("TPO_T1", vo.getAttributeValue("TPO_T1"));
					targetMap.put("N1_E1", vo.getAttributeValue("N1_E1"));
					targetMap.put("N1_N1", vo.getAttributeValue("N1_N1"));
					targetMap.put("N2_E1", vo.getAttributeValue("N2_E1"));
					targetMap.put("N2_N1", vo.getAttributeValue("N2_N1"));
					targetMap.put("N2_E1", vo.getAttributeValue("N2_E1"));
					targetMap.put("P3_S1", vo.getAttributeValue("P3_S1"));
					targetMap.put("P3_T1", vo.getAttributeValue("P3_T1"));
					targetMap.put("T3_S1", vo.getAttributeValue("T3_S1"));
					targetMap.put("T3_T1", vo.getAttributeValue("T3_T1"));
					
					//飘点告警门限值限制
					Map<String,String> floatPointLimitValueMap=new HashMap<String,String>();
					floatPointLimitValueMap.put("DIV_PF_E1", "0.075");
					floatPointLimitValueMap.put("DIV_COT_E1", "10");
					floatPointLimitValueMap.put("DIV_RI_E1", "5");
					floatPointLimitValueMap.put("DIV_PBV_E1", "5");
					floatPointLimitValueMap.put("DIV_TW_S1", "5");
					floatPointLimitValueMap.put("DIV_TP_S1", "10");
					floatPointLimitValueMap.put("DIV_PDMT_L_D1", "2");
					floatPointLimitValueMap.put("DIV_PIN_PRV_M1", "5");
					floatPointLimitValueMap.put("DIV_PD_S1", "1.5");
					floatPointLimitValueMap.put("DIV_SC1_V1", "1.5");
					floatPointLimitValueMap.put("DIV_SC2_V1", "1.5");
					floatPointLimitValueMap.put("DIV_SC3_V1", "1.5");
					floatPointLimitValueMap.put("DIV_TPO_S1", "5");
					floatPointLimitValueMap.put("DIV_N1_E1", "0.5");
					floatPointLimitValueMap.put("DIV_N2_E1", "0.5");
					floatPointLimitValueMap.put("DIV_P3_S1", "2");
					floatPointLimitValueMap.put("DIV_T3_S1", "5");
					floatPointLimitValueMap.put("DIV_PDMT_TW1", "2.5");//由于差分是差值的1/2，所以要除以2,5变成2.5
					floatPointLimitValueMap.put("DIV_PDMT_TW2", "2.5");
					
					targetMap.put("floatPointLimitValueMap",floatPointLimitValueMap);
					// 此条报文计算完成后，进行告警触发
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A21_COMPUTE, msgno, targetVo);

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
			logger.error("A21报文计算时异常:" + e.getMessage());
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
	public A21ComputeVo compute21ByHistoryVos(HashVO curVo) throws Exception{
		A21ComputeVo computedVo = new A21ComputeVo();
		
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		Date date_utc = curVo.getDateValue("RPTDATE");

		String esn_1 = curVo.getStringValue("ESN_1");
		String esn_2 = curVo.getStringValue("ESN_2");
		double n1_e1 = curVo.getDoubleValue("N1_E1");
		double n1_n1 = curVo.getDoubleValue("N1_N1");
		double n2_e1 = curVo.getDoubleValue("N2_E1");
		double n2_n1 = curVo.getDoubleValue("N2_N1");
		double pf_e1 = curVo.getDoubleValue("PF_E1");
		double pf_n1 = curVo.getDoubleValue("PF_N1");
		double cot_e1 = curVo.getDoubleValue("COT_E1");
		double cot_n1 = curVo.getDoubleValue("COT_N1");
		double ri_e1 = curVo.getDoubleValue("RI_E1");
		double ri_n1 = curVo.getDoubleValue("RI_N1");
		double ro_e1 = curVo.getDoubleValue("RO_E1");
		double ro_n1 = curVo.getDoubleValue("RO_N1");
		double pbv_e1 = curVo.getDoubleValue("PBV_E1");
		double pbv_n1 = curVo.getDoubleValue("PBV_N1");
		double p3_s1 = curVo.getDoubleValue("P3_S1");
		double p3_t1 = curVo.getDoubleValue("P3_T1");
		double t3_s1 = curVo.getDoubleValue("T3_S1");
		double t3_t1 = curVo.getDoubleValue("T3_T1");
		double tw_s1 = curVo.getDoubleValue("TW_S1");
		double tw_t1 = curVo.getDoubleValue("TW_T1");
		double tp_s1 = curVo.getDoubleValue("TP_S1");
		double tp_t1 = curVo.getDoubleValue("TP_T1");
		double tpo_s1 = curVo.getDoubleValue("TPO_S1");
		double tpo_t1 = curVo.getDoubleValue("TPO_T1");
		double pd_s1 = curVo.getDoubleValue("PD_S1");
		double pd_t1 = curVo.getDoubleValue("PD_T1");
		double pin_prv_m1 = curVo.getDoubleValue("PIN_PRV_M1");
		double pin_prv_n1 = curVo.getDoubleValue("PIN_PRV_M2");
		double ovp_x1Str = curVo.getDoubleValue("OVP_X1");
		
		String modelseries = curVo.getStringValue("MODELSERIES");
		computedVo.setModelSeries(modelseries);
		
		//计算各个参数是否超限
		int ex_count = limitCompute(msgno);
		computedVo.setEx_count(ex_count);
		
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
		double mixf_g1 =0;
		Double mixf_g1temp= curVo.getDoubleValue("MIXF_G1");
		if(mixf_g1temp!=null){
			mixf_g1=mixf_g1temp;
		}
		double mixcab_g1 =0;
		Double mixcab_g1temp= curVo.getDoubleValue("MIXCAB_G1");
		if(mixcab_g1temp!=null){
			mixcab_g1=mixcab_g1temp;
		}
		
		computedVo.setEsn_1(esn_1);
		computedVo.setEsn_2(esn_2);
		
		if(mixf_g1temp!=null&&mixcab_g1temp!=null){
			double div_mixf_g1 = MathUtil.deviation(mixf_g1,mixcab_g1);
			computedVo.setDiv_mixf_g1(div_mixf_g1);
			double div_mixcab_g1 = MathUtil.deviation(mixcab_g1,mixf_g1);
			computedVo.setDiv_mixcab_g1(div_mixcab_g1);
			double mixf_mixcab_div = MathUtil.subtract(mixf_g1,mixcab_g1);
			computedVo.setMixf_mixcab_div(mixf_mixcab_div);
		}
		
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
		
		double div_n1_e1 = MathUtil.deviation(n1_e1,n1_n1);
		computedVo.setDiv_n1_e1(div_n1_e1);
		double div_n1_n1 = MathUtil.deviation(n1_n1,n1_e1);
		computedVo.setDiv_n1_n1(div_n1_n1);
		double n1_div = MathUtil.subtract(n1_e1,n1_n1);
		computedVo.setN1_div(n1_div);
		
		double div_n2_e1 = MathUtil.deviation(n2_e1,n2_n1);
		computedVo.setDiv_n2_e1(div_n2_e1);
		double div_n2_n1 = MathUtil.deviation(n2_n1,n2_e1);
		computedVo.setDiv_n2_n1(div_n2_n1);
		double n2_div = MathUtil.subtract(n2_e1,n2_n1);
		computedVo.setN2_div(n2_div);
		
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
		
		double div_ri_e1 = MathUtil.deviation(ri_e1,ri_n1);
		computedVo.setDiv_ri_e1(div_ri_e1);
		double div_ri_n1 = MathUtil.deviation(ri_n1,ri_e1);
		computedVo.setDiv_ri_n1(div_ri_n1);
		double ri_div = MathUtil.subtract(ri_e1,ri_n1);
		computedVo.setRi_div(ri_div);
		
		double div_ro_e1 = MathUtil.deviation(ro_e1,ro_n1);
		computedVo.setDiv_ro_e1(div_ro_e1);
		double div_ro_n1 = MathUtil.deviation(ro_n1,ro_e1);
		computedVo.setDiv_ro_n1(div_ro_n1);
		double ro_div = MathUtil.subtract(ro_e1,ro_n1);
		computedVo.setRo_div(ro_div);
		
		double div_pbv_e1 = MathUtil.deviation(pbv_e1,pbv_n1);
		computedVo.setDiv_pbv_e1(div_pbv_e1);
		double div_pbv_n1 = MathUtil.deviation(pbv_n1,pbv_e1);
		computedVo.setDiv_pbv_n1(div_pbv_n1);
		double pbv_div = MathUtil.subtract(pbv_e1,pbv_n1);
		computedVo.setPbv_div(pbv_div);
		
		double div_p3_s1 = MathUtil.deviation(p3_s1,p3_t1);
		computedVo.setDiv_p3_s1(div_p3_s1);
		double div_p3_t1 = MathUtil.deviation(p3_t1,p3_s1);
		computedVo.setDiv_p3_t1(div_p3_t1);
		double p3_div = MathUtil.subtract(p3_s1,p3_t1);
		computedVo.setP3_div(p3_div);
		
		double div_t3_s1 = MathUtil.deviation(t3_s1,t3_t1);
		computedVo.setDiv_t3_s1(div_t3_s1);
		double div_t3_t1 = MathUtil.deviation(t3_t1,t3_s1);
		computedVo.setDiv_t3_t1(div_t3_t1);
		double t3_div = MathUtil.subtract(t3_s1,t3_t1);
		computedVo.setT3_div(t3_div);
		
		double div_tw_s1 = MathUtil.deviation(tw_s1,tw_t1);
		computedVo.setDiv_tw_s1(div_tw_s1);
		double div_tw_t1 = MathUtil.deviation(tw_t1,tw_s1);
		computedVo.setDiv_tw_t1(div_tw_t1);
		double tw_div = MathUtil.subtract(tw_s1,tw_t1);
		computedVo.setTw_div(tw_div);
		
		double div_pdmt_tw1 = MathUtil.deviation(pdmt_l_d1,tw_s1);
		computedVo.setDiv_pdmt_tw1(div_pdmt_tw1);
		double div_pdmt_tw2 = MathUtil.deviation(pdmt_r_d1,tw_t1);
		computedVo.setDiv_pdmt_tw2(div_pdmt_tw2);
		double pdmt_tw1_div = MathUtil.subtract(pdmt_l_d1,tw_s1);
		computedVo.setPdmt_tw1_div(pdmt_tw1_div);
		double pdmt_tw2_div = MathUtil.subtract(pdmt_r_d1,tw_t1);
		computedVo.setPdmt_tw2_div(pdmt_tw2_div);
		
		double div_tp_s1 = MathUtil.deviation(tp_s1,tp_t1);
		computedVo.setDiv_tp_s1(div_tp_s1);
		double div_tp_t1 = MathUtil.deviation(tp_t1,tp_s1);
		computedVo.setDiv_tp_t1(div_tp_t1);
		double tp_div = MathUtil.subtract(tp_s1,tp_t1);
		computedVo.setTp_div(tp_div);
		
		double div_tpo_s1 = MathUtil.deviation(tpo_s1,tpo_t1);
		computedVo.setDiv_tpo_s1(div_tpo_s1);
		double div_tpo_t1 = MathUtil.deviation(tpo_t1,tpo_s1);
		computedVo.setDiv_tpo_t1(div_tpo_t1);
		double tpo_div = MathUtil.subtract(tpo_s1,tpo_t1);
		computedVo.setTpo_div(tpo_div);
		
		double div_pd_s1 = MathUtil.deviation(pd_s1,pd_t1);
		computedVo.setDiv_pd_s1(div_pd_s1);
		double div_pd_t1 = MathUtil.deviation(pd_t1,pd_s1);
		computedVo.setDiv_pd_t1(div_pd_t1);
		double pd_div = MathUtil.subtract(pd_s1,pd_t1);
		computedVo.setPd_div(pd_div);
		
		
		double div_pin_prv_m1 = MathUtil.deviation(pin_prv_m1,pin_prv_n1);
		computedVo.setDiv_pin_prv_m1(div_pin_prv_m1);
		double div_pin_prv_m2 = MathUtil.deviation(pin_prv_n1,pin_prv_m1);
		computedVo.setDiv_pin_prv_m2(div_pin_prv_m2);
		double pin_prv_div = MathUtil.subtract(pin_prv_m1,pin_prv_n1);
		computedVo.setPin_prv_div(pin_prv_div);
				
		String rptno = ApmsVarConst.RPTNO_A21;
		
		computedVo.setId(curVo.getStringValue("ID"));
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setDate_utc(date_utc);
		
		String changeMsgno = "1";
		boolean isChangePoint = false;
		boolean isTTest = false;
		boolean isRankTTest = false;
		//获取该报文的前一报文信息		
		DfdFieldCompute fieldComp = new DfdFieldCompute();
		
		//设置20*2 = 40个回归点
		fieldComp.setPointsN(20);
		//对油量做方差计算、样本T检验
		
		String x_fieldname = "RPTDATE_HOURS";
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(computedVo.getDate_utc());
		long s = computedVo.getDate_utc().getTime();
		
		Double x_value = (double) s/3600000;
		String f_name = "N1_DIV";
		double f_value = computedVo.getN1_div();
		
		FieldComputeVo varN1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varN1Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		
		varN1Vo.insertToTable();
		
		computedVo.setPF_E1(pf_e1);
		f_name = "PF_E1";
		f_value = computedVo.getPF_E1();
		FieldComputeVo varPF_E1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPF_E1Vo, changeMsgno, isChangePoint);
		varPF_E1Vo.insertToRollNTable();
		
		computedVo.setPF_N1(pf_n1);
		f_name = "PF_N1";
		f_value = computedVo.getPF_N1();
		FieldComputeVo varPF_N1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPF_N1Vo, changeMsgno, isChangePoint);
		varPF_N1Vo.insertToRollNTable();
		
		computedVo.setCOT_E1(cot_e1);
		f_name = "COT_E1";
		f_value = computedVo.getCOT_E1();
		FieldComputeVo varCOT_E1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varCOT_E1Vo, changeMsgno, isChangePoint);
		varCOT_E1Vo.insertToRollNTable();
		
		computedVo.setCOT_N1(cot_n1);
		f_name = "COT_N1";
		f_value = computedVo.getCOT_N1();
		FieldComputeVo varCOT_N1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varCOT_N1Vo, changeMsgno, isChangePoint);
		varCOT_N1Vo.insertToRollNTable();
		
		computedVo.setRI_E1(ri_e1);
		f_name = "RI_E1";
		f_value = computedVo.getRI_E1();
		FieldComputeVo varRI_E1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varRI_E1Vo, changeMsgno, isChangePoint);
		varRI_E1Vo.insertToRollNTable();
		
		computedVo.setRI_N1(ri_n1);
		f_name = "RI_N1";
		f_value = computedVo.getRI_N1();
		FieldComputeVo varRI_N1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varRI_N1Vo, changeMsgno, isChangePoint);
		varRI_N1Vo.insertToRollNTable();
		
		computedVo.setPBV_E1(pbv_e1);
		f_name = "PBV_E1";
		f_value = computedVo.getPBV_E1();
		FieldComputeVo varPBV_E1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPBV_E1Vo, changeMsgno, isChangePoint);
		varPBV_E1Vo.insertToRollNTable();
		
		computedVo.setPBV_N1(pbv_n1);
		f_name = "PBV_N1";
		f_value = computedVo.getPBV_N1();
		FieldComputeVo varPBV_N1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPBV_N1Vo, changeMsgno, isChangePoint);
		varPBV_N1Vo.insertToRollNTable();
		
		computedVo.setTW_S1(tw_s1);
		f_name = "TW_S1";
		f_value = computedVo.getTW_S1();
		FieldComputeVo varTW_S1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varTW_S1Vo, changeMsgno, isChangePoint);
		varTW_S1Vo.insertToRollNTable();
		
		computedVo.setTW_T1(tw_t1);
		f_name = "TW_T1";
		f_value = computedVo.getTW_T1();
		FieldComputeVo varTW_T1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varTW_T1Vo, changeMsgno, isChangePoint);
		varTW_T1Vo.insertToRollNTable();
		
		computedVo.setTP_S1(tp_s1);
		f_name = "TP_S1";
		f_value = computedVo.getTP_S1();
		FieldComputeVo varTP_S1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varTP_S1Vo, changeMsgno, isChangePoint);
		varTP_S1Vo.insertToRollNTable();
		
		computedVo.setTP_T1(tp_t1);
		f_name = "TP_T1";
		f_value = computedVo.getTP_T1();
		FieldComputeVo varTP_T1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varTP_T1Vo, changeMsgno, isChangePoint);
		varTP_T1Vo.insertToRollNTable();
		
		computedVo.setPDMT_L_D1(pdmt_l_d1);
		f_name = "PDMT_L_D1";
		f_value = computedVo.getPDMT_L_D1();
		FieldComputeVo varPDMT_L_D1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPDMT_L_D1Vo, changeMsgno, isChangePoint);
		varPDMT_L_D1Vo.insertToRollNTable();
		
		computedVo.setPDMT_R_D1(pdmt_r_d1);
		f_name = "PDMT_R_D1";
		f_value = computedVo.getPDMT_R_D1();
		FieldComputeVo varPDMT_R_D1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPDMT_R_D1Vo, changeMsgno, isChangePoint);
		varPDMT_R_D1Vo.insertToRollNTable();
		
		computedVo.setPRV1_Z1(pin_prv_m1);
		f_name = "PRV1_Z1";
		f_value = computedVo.getPRV1_Z1();
		FieldComputeVo varPRV1_Z1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPRV1_Z1Vo, changeMsgno, isChangePoint);
		varPRV1_Z1Vo.insertToRollNTable();
		
		computedVo.setPRV2_Z1(pin_prv_n1);
		f_name = "PRV2_Z1";
		f_value = computedVo.getPRV2_Z1();
		FieldComputeVo varPRV2_Z1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPRV2_Z1Vo, changeMsgno, isChangePoint);
		varPRV2_Z1Vo.insertToRollNTable();
		
		computedVo.setPD_S1(pd_s1);
		f_name = "PD_S1";
		f_value = computedVo.getPD_S1();
		FieldComputeVo varPD_S1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPD_S1Vo, changeMsgno, isChangePoint);
		varPD_S1Vo.insertToRollNTable();
		
		computedVo.setPD_T1(pd_t1);
		f_name = "PD_T1";
		f_value = computedVo.getPD_T1();
		FieldComputeVo varPD_T1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varPD_T1Vo, changeMsgno, isChangePoint);
		varPD_T1Vo.insertToRollNTable();
		
		
//		computedVo.setTPO_S1(tpo_s1);
		f_name = "TPO_S1";
		f_value = tpo_s1;
		FieldComputeVo varTPO_S1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varTPO_S1Vo, changeMsgno, isChangePoint);
		varTPO_S1Vo.insertToRollNTable();
		
//		computedVo.setTPO_T1(tpo_t1);
		f_name = "TPO_T1";
		f_value = tpo_t1;
		FieldComputeVo varTPO_T1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, varTPO_T1Vo, changeMsgno, isChangePoint);
		varTPO_T1Vo.insertToRollNTable();

		f_name = "N2_DIV";
		f_value = computedVo.getN2_div();
		FieldComputeVo varN2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varN2Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varN2Vo.insertToTable();
		
		f_name = "PF_DIV";
		f_value = computedVo.getPf_div();
		FieldComputeVo varPFVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPFVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPFVo.insertToTable();
		
		f_name = "COT_DIV";
		f_value = computedVo.getCot_div();
		FieldComputeVo varCOTVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varCOTVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varCOTVo.insertToTable();

		f_name = "RI_DIV";
		f_value = computedVo.getRi_div();
		FieldComputeVo varRIVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varRIVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varRIVo.insertToTable();
		
		f_name = "RO_DIV";
		f_value = computedVo.getRo_div();
		FieldComputeVo varROVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varROVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varROVo.insertToTable();
		
		f_name = "PBV_DIV";
		f_value = computedVo.getPbv_div();
		FieldComputeVo varPBVVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPBVVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPBVVo.insertToTable();
		
		f_name = "P3_DIV";
		f_value = computedVo.getP3_div();
		FieldComputeVo varP3Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varP3Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varP3Vo.insertToTable();
		
		f_name = "T3_DIV";
		f_value = computedVo.getT3_div();
		FieldComputeVo varT3Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varT3Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varT3Vo.insertToTable();

		f_name = "TW_DIV";
		f_value = computedVo.getTw_div();
		FieldComputeVo varTWVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varTWVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varTWVo.insertToTable();
		
		f_name = "TP_DIV";
		f_value = computedVo.getTp_div();
		FieldComputeVo varTPVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varTPVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varTPVo.insertToTable();
		
		f_name = "TPO_DIV";
		f_value = computedVo.getTpo_div();
		FieldComputeVo varTPOVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varTPOVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varTPOVo.insertToTable();
		
		f_name = "PD_DIV";
		f_value = computedVo.getPd_div();
		FieldComputeVo varPDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPDVo.insertToTable();
		
		f_name = "PDMT_TW1_DIV";
		f_value = computedVo.getPdmt_tw1_div();
		FieldComputeVo varPdmt_tw1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPdmt_tw1Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPdmt_tw1Vo.insertToTable();
		
		f_name = "PDMT_TW2_DIV";
		f_value = computedVo.getPdmt_tw2_div();
		FieldComputeVo varPdmt_tw2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPdmt_tw2Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPdmt_tw2Vo.insertToTable();
		
		
		f_name = "PIN_PRV_DIV";
		f_value = computedVo.getPin_prv_div();
		FieldComputeVo varPRVVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPRVVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPRVVo.insertToTable();
		
		f_name = "D1_DIV";
		f_value = computedVo.getD1_div();
		FieldComputeVo d1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, d1Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		d1Vo.insertToTable();
		
		f_name = "SC1CKT_DIV";
		f_value = computedVo.getSc1ckt_div();
		FieldComputeVo sc1cktVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, sc1cktVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		sc1cktVo.insertToTable();
		
		f_name = "SC2FWDT_DIV";
		f_value = computedVo.getSc2fwdt_div();
		FieldComputeVo sc2fwdtVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, sc2fwdtVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		sc2fwdtVo.insertToTable();
		
		f_name = "SC3AFTT_DIV";
		f_value = computedVo.getSc3aftt_div();
		FieldComputeVo sc3afttVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, sc3afttVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		sc3afttVo.insertToTable();
		
		f_name = "MIXF_MIXCAB_DIV";
		f_value = computedVo.getMixf_mixcab_div();
		FieldComputeVo mixf_mixcabVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, mixf_mixcabVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		mixf_mixcabVo.insertToTable();
		
		f_name = "OVP_X1";
		f_value = ovp_x1Str;
		FieldComputeVo ovp_x1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, ovp_x1Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		ovp_x1Vo.insertToTable();
		
	 	computedVo.setFieldVo_n1(varN1Vo);
	 	computedVo.setFieldVo_n2(varN2Vo);
	 	computedVo.setFieldVo_pf(varPFVo);
	 	computedVo.setFieldVo_cot(varCOTVo);
	 	computedVo.setFieldVo_ri(varRIVo);
	 	computedVo.setFieldVo_ro(varROVo);
	 	computedVo.setFieldVo_pbv(varPBVVo);
	 	computedVo.setFieldVo_p3(varP3Vo);
	 	computedVo.setFieldVo_t3(varT3Vo);
	 	computedVo.setFieldVo_tw(varTWVo);
	 	computedVo.setFieldVo_tp(varTPVo);
	 	computedVo.setFieldVo_tpo(varTPOVo);
	 	computedVo.setFieldVo_pd(varPDVo);
	 	computedVo.setFieldVo_pin_prv(varPRVVo);
	 	computedVo.setFieldVo_sc1ckt(sc1cktVo);
	 	computedVo.setFieldVo_sc2fwdt(sc2fwdtVo);
	 	computedVo.setFieldVo_sc3aftt(sc3afttVo);
	 	computedVo.setFieldVo_sc3aftt(mixf_mixcabVo);
	 	computedVo.setFieldVo_pdmt_tw1(varPdmt_tw1Vo);
	 	computedVo.setFieldVo_pdmt_tw2(varPdmt_tw2Vo);
	 	
	 	//计算PDI指数 pdi_ac = 0.1789*(RI_E1-7)/23+0.2938*(200-COT_E1)/60+0.4779*((PBV_E1-21)/27)+0.0493*(0.65-PF_E1)/0.2
		double pdi_ac1 = pdiAc_compute(modelseries, ri_e1, cot_e1, pbv_e1, pf_e1);
		double pdi_ac2 = pdiAc_compute(modelseries, ri_n1, cot_n1, pbv_n1, pf_n1);
		
		double pdi_ac1_avg5 = pdiAc_compute(modelseries, varRI_E1Vo.getF_value_roll5(), varCOT_E1Vo.getF_value_roll5(), varPBV_E1Vo.getF_value_roll5(), varPF_E1Vo.getF_value_roll5() );
		double pdi_ac2_avg5 = pdiAc_compute(modelseries, varRI_N1Vo.getF_value_roll5(), varCOT_N1Vo.getF_value_roll5(), varPBV_N1Vo.getF_value_roll5(), varPF_N1Vo.getF_value_roll5() );
	 	
		computedVo.setPdi_ac1(pdi_ac1);
		computedVo.setPdi_ac1_avg5(pdi_ac1_avg5);
		computedVo.setPdi_ac2(pdi_ac2);
		computedVo.setPdi_ac2_avg5(pdi_ac2_avg5);
		
		//对PDI进行回归计算,PDI
		fieldComp.setRollN(10);//A21空调巡航趋势中显示PDI的十点均，这段代码放在最后面，因为改变了计算的均值点数
		f_name = "PDI_AC1";
		f_value = pdi_ac1;
		FieldComputeVo fieldVo_pdiac1 = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, fieldVo_pdiac1, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		fieldVo_pdiac1.insertToTable();
		
		f_name = "PDI_AC2";
		f_value = pdi_ac2;
		FieldComputeVo fieldVo_pdiac2 = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, fieldVo_pdiac2, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		fieldVo_pdiac2.insertToTable();
		
		computedVo.setFieldVo_pdiac1(fieldVo_pdiac1);
		computedVo.setFieldVo_pdiac2(fieldVo_pdiac2);
		
	 	//数据入库
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
		CallableStatement c=conn.prepareCall("{call P_A21_LIMIT_STATISTIC_1(?,?)}");   
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
	
	//根据机型及参数计算空调的PDI指数
	private double pdiAc_compute(String modelSeries,double ri,double cot,double pbv,double pf){
	    double a = 0.299552870258665 ; //旧系数0.1789 ;
		double b = 0.523579554723328 ; //旧系数 0.2938 ;
		double c = 0.120746476644363 ; //旧系数 0.4779 ;
		double d = 0.0561210983736435; //旧系数 0.0493 ;
		double ri_base = 7, ri_divid = 23;
		double cot_base = 200, cot_divid = 60;
		double pbv_base = 21, pbv_divid=27;
		double pf_base = 0.65, pf_divid=0.2; 
		
		
		if( ApmsVarConst.MODELSERIRES_A321.equals(modelSeries) ){
			//0.1789*(RI_E1-7)/23+0.2938*(200-COT_E1)/60+0.4779*((PBV_E1-21)/27)+0.0493*(0.65-PF_E1)/0.2
			ri_base = 7;
			ri_divid = 23;
			cot_base = 200;
			cot_divid = 60;
			pbv_base = 21;
			pbv_divid = 27;
			pf_base = 0.65;
			pf_divid = 0.2;
			
		}else if( ApmsVarConst.MODELSERIRES_A320.equals(modelSeries) ){
			//0.1789*(RI_E1-7)/22+0.2938*(195-COT_E1)/75+0.4779*((PBV_E1-21)/24)+0.0493*(0.60-PF_E1)/0.2
			ri_base = 7;
			ri_divid = 22;
			cot_base = 195;
			cot_divid = 75;
			pbv_base = 21;
			pbv_divid = 24;
			pf_base = 0.6;
			pf_divid = 0.2;
		}else if( ApmsVarConst.MODELSERIRES_A319.equals(modelSeries) ){
			//0.1789*(RI_E1-7)/20+0.2938*(185-COT_E1)/85+0.4779*((PBV_E1-20)/20)+0.0493*(0.55-PF_E1)/0.15
			ri_base = 7;
			ri_divid = 20;
			cot_base = 185;
			cot_divid = 85;
			pbv_base = 20;
			pbv_divid = 20;
			pf_base = 0.55;
			pf_divid = 0.15;
		}
		
		//PDI计算公式 pdi_ac = 0.1789*(RI_E1-7)/23+0.2938*(200-COT_E1)/60+0.4779*((PBV_E1-21)/27)+0.0493*(0.65-PF_E1)/0.2
		double pdiAc = a*(ri - ri_base)/ri_divid + b*(cot_base - cot)/cot_divid + c*(pbv - pbv_base)/pbv_divid + d*(pf_base - pf)/pf_divid ;
		
		return pdiAc;
	}
	
	
	

}