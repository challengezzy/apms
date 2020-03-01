package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A13ComputeVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * APU报文计算数据告警
 * @author jerry
 * @date Apr 10, 2013
 */
//STAV1 三个飘点告警
public class AlarmImplA13_01 extends AlarmRuleImplBaseClass{
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		
		A13ComputeVo computeVo = (A13ComputeVo)targetVo.getParam("COMPUTEDVO"); 

		String asn = computeVo.getAsn();
		int v_out = computeVo.getFieldVo_sta().getV_out();
		int v_pointtype = computeVo.getFieldVo_sta().getV_pointtype();
		double sta = computeVo.getFieldVo_sta().getF_value();
		
		int alarmLevel = 3;
		
		if (v_out ==1 && v_pointtype == 0){      //告警条件
 
			String acnum = targetVo.getAcnum();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", DateUtil.getLongDate(targetVo.getDateUtc()));
			paramMap.put("ALARM_LEVEL", alarmLevel+"");
			paramMap.put("ASN", asn+""+ " STA:"+ sta+"");


			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
//			String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,null,alarmLevel);
		}
	}

}
