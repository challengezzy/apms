package com.apms.bs.alarm.rule.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A13ComputeVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dfd.DfdCodeMapVo;
import com.apms.bs.dfd.DfdDataUtil;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.MathUtil;
import com.apms.bs.util.StringUtil;

/**
 * APU_性能衰退探测
 * 
 * @author jerry
 * @date Apr 15, 2013
 */
public class AlarmImplA13_04 extends AlarmRuleImplBaseClass {
	private CommDMO dmo = new CommDMO();

	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo)
			throws Exception {
		double setvalue_a = 10.0;
		double setvalue_b = -0.08;
		A13ComputeVo computeVo = (A13ComputeVo) targetVo.getParam("COMPUTEDVO");

		String asn = computeVo.getAsn();
		String submodel = null;
		double rl_igv;

		StringBuilder sql = new StringBuilder(
				"SELECT A.APUSN, M.SUBMODEL, M.RL_IGV ");
		sql.append(" FROM B_APU A, B_APU_MODEL M WHERE A.APUMODELID = M.ID ");
		sql.append(" AND A.APUSN = '" + asn + "'");

		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS,
					sql.toString());
			submodel = vos[0].getStringValue("SUBMODEL");
			rl_igv = vos[0].getDoubleValue("RL_IGV");
		} catch (Exception e) {
			logger.error("A13报文计算时异常:" + e.getMessage());
			throw e;
		}

		double egta_max_cor = computeVo.getEgta_max_cor();
		double pt_max_cor = computeVo.getPt_max_cor();
		double rl_sta = computeVo.getRl_sta();
		double rl_pt = computeVo.getRl_pt();
		double rl_egt_cor = computeVo.getRl_egt_cor();
		double sta = computeVo.getSta_v1();
		double igv = computeVo.getIgv_max();
		double pt_k_avg = computeVo.getFieldVo_pt().getV_k_avg();
		double egt_k_avg = computeVo.getFieldVo_egta().getV_k_avg();

		double temp1 = computeVo.getFieldVo_egta().getDeta_x_on_estlimitvalue() / 60;
		BigDecimal temp2 = new BigDecimal(temp1);
		double egta_limitvalue = temp2.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();

		temp1 = computeVo.getFieldVo_pt().getDeta_x_on_estlimitvalue() / 60;
		temp2 = new BigDecimal(temp1);
		double pt_limitvalue = temp2.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();

		int alarmLevel = 3;

		if (sta > 50) {
			if ((egta_max_cor + setvalue_a) > rl_egt_cor
					|| (pt_max_cor + setvalue_b) < rl_pt || (sta + 5) > rl_sta
					|| ((igv + 5) > rl_igv && submodel.equals("APS3200"))
					|| ((igv - 5) < rl_igv && submodel.equals("GTCP131-9A"))) { // 告警条件

				String acnum = computeVo.getAcnum();

				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("ACNUM", acnum);
				paramMap.put("TIME",
						DateUtil.getLongDate(computeVo.getDate_utc()));
				paramMap.put("ALARM_LEVEL", alarmLevel + "");
/**
 *             if (egt_k_avg <= 0) {
					paramMap.put(
							"EGTA",
							egta_limitvalue + "（斜率为:"
									+ MathUtil.round((egt_k_avg * 60), 7) + "）");
				} else {
					paramMap.put("EGTA", egta_limitvalue + "");
				}
*/
				paramMap.put(
						"EGTA",
						egta_limitvalue + "（斜率为:"
								+ MathUtil.round((egt_k_avg * 60), 7) + "）");				
/**				if (pt_k_avg >= 0) {
					paramMap.put(
							"PT",
							pt_limitvalue + "（斜率为:"
									+ MathUtil.round((pt_k_avg * 60), 7) + "）");
				} else {
					paramMap.put(
							"PT",
							pt_limitvalue + "");
				}
				**/
				paramMap.put(
						"PT",
						pt_limitvalue + "（斜率为:"
								+ MathUtil.round((pt_k_avg * 60), 7) + "）");
				paramMap.put("ASN", asn + "");

				String newStr = StringUtil.buildExpression(paramMap,
						ruleVo.getMsgContent());
				// String smStr = StringUtil.buildExpression(paramMap,
				// ruleVo.getSmContent());

				logger.info("告警消息为：【" + newStr + "】");

				msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,
						null, alarmLevel);
			}
		}

	}

}
