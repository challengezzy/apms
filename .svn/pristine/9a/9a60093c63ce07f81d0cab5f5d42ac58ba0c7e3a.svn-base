package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.utils.StringUtil;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A21ComputeVo;
import com.apms.bs.datacompute.vo.A24ComputeVo;
import com.apms.bs.util.DateUtil;
import com.apms.vo.ApmsVarConst;

/**
 * 参数池超限个数大于N个
 * @author jerry
 * @date Dec 16, 2014
 */
public class AlarmImplA24_ExtendLimit extends AlarmRuleImplBaseClass{
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception{
		A24ComputeVo a24comVo =  (A24ComputeVo)targetVo.getParam("COMPUTEDVO");
		
		int excount = a24comVo.getEx_count();
		String modelSeries = a24comVo.getModelSeries();
		
		//有3个参数超限的才进行告警
		if(excount >= 3){
			//{ACNUM}在{TIME},空调巡航报文有{EX_COUNT}个超限,分别为{EXDETAIL}
			
			Map<String, String> paramMap = new HashMap<String, String>();
			String alarm_level="2";
			String time=DateUtil.getDateStr(a24comVo.getDate_utc(),"yyMMdd HH:mm");
			String acnum=a24comVo.getAcnum();
			paramMap.put("ALARM_LEVEL", alarm_level);
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", time);
			paramMap.put("EX_COUNT", excount+"");
			
			//查询出具体超限的参数
			CommDMO dmo = new CommDMO();
			StringBuilder sb = new StringBuilder();
			sb.append("select s.msg_no,s.baseorgid,s.acnum,s.rptdate  ");
			sb.append(",s.ex_count,s.ex_count_limit,s.ex_count_div");
			sb.append(",L.N1_E1,L.N2_E1,L.PF_E1,L.COT_E1,L.RI_E1,L.RO_E1,L.PBV_E1,L.FCV_E1  ");
			sb.append(",L.N1_N1,L.N2_N1,L.PF_N1,L.COT_N1,L.RI_N1,L.RO_N1,L.PBV_N1,L.FCV_N1 ");
			sb.append(",L.P3_S1,L.T3_S1,L.TW_S1,L.TP_S1,L.TPO_S1,L.PD_S1,L.ALT_S1,L.PS_S1 ");
			sb.append(",L.P3_T1,L.T3_T1,L.TW_T1,L.TP_T1,L.TPO_T1,L.PD_T1,L.ALT_T1,L.PS_T1 ");
			sb.append(",L.TAT_V1,L.SAT_V1,L.ZCB_V1,L.ZLD_V1,L.SC1_V1,L.SC2_V1,L.SC3_V1,L.RV_V1 ");
			sb.append(",L.PCSW_X1,L.VSCB_X1,L.PDC_X1,L.VF_X1,L.VW_X1,L.VA_X1,L.OVP_X1,L.CPC_X1 ");
			sb.append(",L.PDMT_L_D1,L.PDMT_R_D1,L.CKT_F1,L.FWDT_F1,L.AFTT_F1  " );
			sb.append(",L.CKDUCT_G1,L.FWDUCT_G1,L.AFTDUCT_G1,L.MIXF_G1,L.MIXCAB_G1");
			sb.append(",s.ex_div_pf,s.ex_pf1,s.ex_pf2,s.ex_div_cot,s.ex_cot1,s.ex_cot2,s.ex_div_ri,s.ex_ri1");
			sb.append(",s.ex_ri2,s.ex_div_pbv,s.ex_pbv1,s.ex_pbv2,s.ex_div_tw,s.ex_tw1,s.ex_tw2");
			sb.append(",s.ex_div_tp,s.ex_tp1,s.ex_tp2,s.ex_div_d1,s.ex_d11,s.ex_d12,s.ex_div_prv,s.ex_prv1,s.ex_prv2");
			sb.append(",s.ex_div_pd,s.ex_pd1,s.ex_pd2,s.ex_div_d1_tw1,s.ex_div_d1_tw2,s.ex_mixcab");
			sb.append(",s.ex_div_ck,s.ex_div_fwd,s.ex_div_aft,s.update_date ");
			sb.append("from a_dfd_a24_compute_stat s,a_dfd_a24_list L  where L.msg_no = s.msg_no ");
			sb.append(" and L.msg_no = ?");
			
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), a24comVo.getMsgno());
			
			//生成Detail字段
			StringBuilder detailSb = new StringBuilder();
			String pf_lower = "0.35";
			String pf_upper = "0.6";
			String cot_lower = "80";
			String cot_upper = "180";
			String pbv_lower = "2";
			String pbv_upper = "52";
			
			if( ApmsVarConst.MODELSERIRES_A321.equals(modelSeries) ){
				pf_lower = "0.3";
				pf_upper = "0.55";
				cot_lower = "60";
				cot_upper = "150";
				pbv_lower = "2";
				pbv_upper = "50";
				
			}else if( ApmsVarConst.MODELSERIRES_A320.equals(modelSeries) ){
				pf_lower = "0.35";
				pf_upper = "0.6";
				cot_lower = "80";
				cot_upper = "180";
				pbv_lower = "2";
				pbv_upper = "52";
				
			}else if( ApmsVarConst.MODELSERIRES_A319.equals(modelSeries) ){
				pf_lower = "0.4";
				pf_upper = "0.7";
				cot_lower = "80";
				cot_upper = "190";
				pbv_lower = "2";
				pbv_upper = "55";
			}
			
			detailSb.append( getMsgDetail1(vos[0], "PF1", "EX_PF1", "PF_E1", pf_upper, pf_lower) );
			detailSb.append( getMsgDetail1(vos[0], "COT1", "EX_COT1", "COT_E1", cot_upper, cot_lower) );
			detailSb.append( getMsgDetail1(vos[0], "PBV1", "EX_PBV1", "PBV_E1", pbv_upper, pbv_lower) );
			detailSb.append( getMsgDetail1(vos[0], "RI1", "EX_RI1", "RI_E1", "98", "7") );
			detailSb.append( getMsgDetail1(vos[0], "TW1", "EX_TW1", "TW_S1", "45", "0") );
			detailSb.append( getMsgDetail1(vos[0], "TP1", "EX_TP1", "TP_S1", "35", "-10") );
			detailSb.append( getMsgDetail1(vos[0], "PDMT1", "EX_D11", "PDMT_L_D1", "30", "0") );
			detailSb.append( getMsgDetail1(vos[0], "PD1", "EX_PD1", "PD_S1", "49", "39") );
			
			detailSb.append( getMsgDetail1(vos[0], "PF2", "EX_PF2", "PF_N1", pf_upper, pf_lower) );
			detailSb.append( getMsgDetail1(vos[0], "COT2", "EX_COT2", "COT_N1", cot_upper, cot_lower) );
			detailSb.append( getMsgDetail1(vos[0], "PBV2", "EX_PBV2", "PBV_N1", pf_upper, pf_lower) );
			detailSb.append( getMsgDetail1(vos[0], "RI2", "EX_RI2", "RI_N1", "98", "7") );
			detailSb.append( getMsgDetail1(vos[0], "TW2", "EX_TW2", "TW_T1", "45", "0") );
			detailSb.append( getMsgDetail1(vos[0], "TP2", "EX_TP2", "TP_T1", "35", "-10") );
			detailSb.append( getMsgDetail1(vos[0], "PDMT2", "EX_D12", "PDMT_R_D1", "30", "0") );
			detailSb.append( getMsgDetail1(vos[0], "PD2", "EX_PD2", "PD_T1", "49", "39") );
			detailSb.append( getMsgDetail1(vos[0], "混合总管温度", "EX_MIXCAB", "MIXCAB_G1", "27", "3") );
			
			detailSb.append( getMsgDetail2(vos[0], "驾驶舱预选与区域温度差", "EX_DIV_CK", "SC1_V1","CKT_F1", "5") );
			detailSb.append( getMsgDetail2(vos[0], "前舱预选与区域温度差", "EX_DIV_FWD", "SC1_V1","FWDT_F1", "5") );
			detailSb.append( getMsgDetail2(vos[0], "后舱预选与区域温度差", "EX_DIV_AFT", "SC1_V1","AFTT_F1", "5") );
			detailSb.append( getMsgDetail2(vos[0], "PF左右差", "EX_DIV_PF", "PF_E1","PF_N1", "0.06") );
			detailSb.append( getMsgDetail2(vos[0], "COT左右差", "EX_DIV_COT", "COT_E1","COT_N1", "45") );
			detailSb.append( getMsgDetail2(vos[0], "RI左右差", "EX_DIV_RI", "RI_E1","RI_N1", "10") );
			detailSb.append( getMsgDetail2(vos[0], "PBV左右差", "EX_DIV_PBV", "PBV_E1","PBV_N1", "13") );
			detailSb.append( getMsgDetail2(vos[0], "TW左右差", "EX_DIV_TW", "TW_S1","TW_T1", "8") );
			detailSb.append( getMsgDetail2(vos[0], "TP左右差", "EX_DIV_TP", "TP_S1","TP_T1", "15") );
			detailSb.append( getMsgDetail2(vos[0], "PDMT左右差", "EX_DIV_D1", "PDMT_L_D1","PDMT_L_D1", "1") );
			detailSb.append( getMsgDetail2(vos[0], "PRV左右差", "EX_DIV_PRV", "PIN_PRV_M1","PIN_PRV_M2", "5") );
			detailSb.append( getMsgDetail2(vos[0], "PD左右差", "EX_DIV_PD", "PD_S1","PD_T1", "4") );
			detailSb.append( getMsgDetail2(vos[0], "D11与TW1差", "EX_DIV_D1_TW1", "PDMT_L_D1","TW_S1", "5") );
			detailSb.append( getMsgDetail2(vos[0], "D12与TW2差", "EX_DIV_D1_TW2", "PDMT_R_D1","TW_T1", "5") );
			
			
			
			
			paramMap.put("EXDETAIL", detailSb.toString());
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,newStr,Integer.parseInt(alarm_level));
		}
		
	}
	
	/**
	 * 
	 * @param vo
	 * @param name 中文属性
	 * @param excol 判断字段
	 * @param valcol 取值字段
	 * @param upper 上限
	 * @param lower 下限
	 * @return
	 */
	private String getMsgDetail1(HashVO vo,String name,String excol,String valcol,String upper,String lower){
		String detail = "";
		if( "1".equals(vo.getStringValue(excol)) ){
			String value = vo.getStringValue(valcol);
			
			detail = "["+ name +": "+ value +"|("+lower + "," + upper +")],";
		}
		
		return detail;
	}
	
	/**
	 * 
	 * @param vo
	 * @param name 中文属性
	 * @param excol 判定字段
	 * @param valcol1 值1
	 * @param valcol2 值2
	 * @param limit 差值门限
	 * @return
	 */
	private String getMsgDetail2(HashVO vo,String name,String excol,String valcol1,String valcol2,String limit){
		String detail = "";
		if( "1".equals(vo.getStringValue(excol)) ){
			String value1 = vo.getStringValue(valcol1);
			String value2 = vo.getStringValue(valcol2);
			
			detail = " ["+ name +":("+ value1 + " , " + value2 +")|"+limit+"],";
		}
		return detail;
	}

}
