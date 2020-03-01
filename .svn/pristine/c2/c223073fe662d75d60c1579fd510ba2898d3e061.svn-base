package com.apms.bs.alarm.rule.impl;

import java.math.BigDecimal;
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
 *  OTA滑油红线告警
 * @author jerry
 * @date Apr 15, 2013
 */
// OTA滑油红线告警
public class AlarmImplA13_05 extends AlarmRuleImplBaseClass{
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception{
		double setvalue = 6.0;
		A13ComputeVo computeVo = (A13ComputeVo)targetVo.getParam("COMPUTEDVO"); 
		
		String asn = computeVo.getAsn();
		double  ota = computeVo.getOta_max();
		double rl_ota = computeVo.getRl_ota();
		
		double temp1 = computeVo.getFieldVo_ot().getDeta_x_on_estlimitvalue()/60;
		BigDecimal temp2 = new BigDecimal(temp1);
		double limitY = temp2.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		
		int alarmLevel = 3;
		
		if ( (ota + setvalue)> rl_ota ){      //告警条件
			
			String acnum = computeVo.getAcnum();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", DateUtil.getLongDate(computeVo.getDate_utc()));
			paramMap.put("ALARM_LEVEL", alarmLevel+"");
			paramMap.put("LIMITY", limitY+"");
			paramMap.put("ASN", asn+"");


			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
//			String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,null,alarmLevel);
		}
		
		
	}

}
