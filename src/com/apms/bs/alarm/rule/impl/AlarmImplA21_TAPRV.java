package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;
import smartx.framework.common.utils.StringUtil;
import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A21ComputeVo;
import com.apms.bs.util.DateUtil;

/**
 * A21热空气压力调节活门不一致告警
 */
public class AlarmImplA21_TAPRV extends AlarmRuleImplBaseClass{
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception{
		String taprv_h1 =  (String)targetVo.getParam("TAPRV_H1");
		A21ComputeVo a21comVo =  (A21ComputeVo)targetVo.getParam("COMPUTEDVO");
		if("1".equals(taprv_h1)){
			Map<String, String> paramMap = new HashMap<String, String>();
			String alarm_level="2";
			String time=DateUtil.getDateStr(a21comVo.getDate_utc(),"yyyyMMdd HH:mm:ss");
			String acnum=a21comVo.getAcnum();
			paramMap.put("ALARM_LEVEL", alarm_level);
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", time);
			
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,newStr,Integer.parseInt(alarm_level));
		}else{
			
			logger.debug("报文["+targetVo.getRptno()+"]、飞机["+targetVo.getAcnum()+"]未找到对应的CODE告警配置!");
		}
		
	}

}
