package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A13ComputeVo;
import com.apms.bs.datacompute.vo.A23ComputeVo;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * APU报文计算数据告警
 * @author jerry
 * @date Apr 10, 2013
 */
//APU 小时循环增量 告警 超过40
public class AlarmImplA13_07 extends AlarmRuleImplBaseClass{
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		
				
		double ahrs_inc = targetVo.getAhrs_inc()/60;

		double setVal = 40.0;
		int alarmLevel = 3;
		
		if (ahrs_inc > setVal || ahrs_inc<0){      //告警条件
 
			String acnum = targetVo.getAcnum();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", DateUtil.getLongDate(targetVo.getDateUtc()));
			paramMap.put("ALARM_LEVEL", alarmLevel+"");
			paramMap.put("LIMITED", setVal+"");
			

			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
//			String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,null,alarmLevel);
		}
	}

}
