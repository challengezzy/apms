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
 * A21号空调驾驶舱预选和区域差值飘点告警
 */
public class AlarmImplA21_Sc1ckt extends AlarmRuleImplBaseClass{
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception{
		A21ComputeVo a21comVo =  (A21ComputeVo)targetVo.getParam("COMPUTEDVO");
		int v_pointtype= a21comVo.getFieldVo_sc1ckt().getV_pointtype();
		int v_out = a21comVo.getFieldVo_sc1ckt().getV_out();
		if(v_out==1 && v_pointtype==0){//v_out==1 && v_pointtype==0
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
