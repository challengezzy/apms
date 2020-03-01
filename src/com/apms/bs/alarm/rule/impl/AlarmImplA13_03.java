package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

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
import com.apms.bs.util.StringUtil;

/**
 * EGTA_MAX_COR 飘点大于3告警 确定向上还是向下飘点
 * @author jerry
 * @date Apr 15, 2013
 */
// EGTA_MAX_COR 飘点大于3告警 确定向上还是向下飘点
public class AlarmImplA13_03 extends AlarmRuleImplBaseClass{
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception{
		
		A13ComputeVo computeVo = (A13ComputeVo)targetVo.getParam("COMPUTEDVO"); 
		
		String asn = computeVo.getAsn();
		int v_out = computeVo.getFieldVo_egta().getV_out();
		int v_pointtype = computeVo.getFieldVo_egta().getV_pointtype();
		double f_value = computeVo.getFieldVo_egta().getF_value();
		double avg = computeVo.getFieldVo_egta().getV_avg();
		
		int alarmLevel = 3;
		
		if (v_out==1 && v_pointtype==0){      //告警条件
			
			String acnum = computeVo.getAcnum();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", DateUtil.getLongDate(computeVo.getDate_utc()));
			paramMap.put("ALARM_LEVEL", alarmLevel+"");
			if (f_value >= avg){
				alarmLevel = 3;
				String output = "EGT跃升,涡轮性能下降或压气机间隙变小效率提升提升";
				paramMap.put("OUTPUT", output+"");
				paramMap.put("ASN", asn+"");

			}else {
				alarmLevel = 1;
				String output = "EGT下降，涡轮叶片位移或压气机损伤效率下降";
				paramMap.put("OUTPUT", output+"");
				paramMap.put("ASN", asn+"");

			}

			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
//			String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,null,alarmLevel);
		}
		
		
	}

}
