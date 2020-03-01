package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A25ComputeVo;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;
import com.espertech.esper.type.StringValue;

/**
 * A25_滑油变化量异常
 * 有滑油添加，但无A27报文
 * @author jerry
 * @date Mar 21, 2013
 */
public class AlarmImplA25_01 extends AlarmRuleImplBaseClass{
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		
		A25ComputeVo computeVo = (A25ComputeVo)targetVo.getParam("COMPUTEDVO"); 
		
		boolean isAlarm1 = false; //左发告警
		boolean isAlarm2 = false; //右发告警
		int alarmLevel = 0;
		String deta_oiq1 = null;
		String deta_oiq2 = null;
		
		//判断isAlarm,level
				
				
		
		computeVo.getDeta_oiq1_fwdalarm();
		computeVo.getDeta_oiq2_fwdalarm();
		if (computeVo.getDeta_oiq1_fwdalarm() == 1){
			isAlarm1 = true;
			alarmLevel = 3;
			deta_oiq1 = Double.toString(computeVo.getDeta_oiq1_fwd());
			targetVo.setDevicesn(computeVo.getEsn_1());
		}
		if (computeVo.getDeta_oiq2_fwdalarm() == 1){
			isAlarm2 = true;
			alarmLevel = 3;
			deta_oiq2 = Double.toString(computeVo.getDeta_oiq2_fwd());
			targetVo.setDevicesn(computeVo.getEsn_2());

		}
		
		if(isAlarm1){
			//HashVO msgTemplate = msgService.getMsgTemplateById(msgTemplateId);
			
			//告警等级:{ALARM_LEVEL},报文时间：{TIME} .飞机：{ACNUM}
			
			String acnum = computeVo.getAcnum();
			Map<String, String> paramMap1 = new HashMap<String, String>();
			paramMap1.put("ACNUM", acnum);
			paramMap1.put("TIME", DateUtil.getLongDate(computeVo.getDate_utc()));
			paramMap1.put("ALARM_LEVEL", alarmLevel+"");
			paramMap1.put("DETAOIL", deta_oiq1);
			paramMap1.put("ESN_SEQ", "左发动机");
           
			String newStr1 = StringUtil.buildExpression(paramMap1, ruleVo.getMsgContent());
			String smStr1 = StringUtil.buildExpression(paramMap1, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr1+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr1,smStr1,alarmLevel);

		}else{
			logger.debug("A25滑油左发正常！");
		}
		if(isAlarm2){
			String acnum = computeVo.getAcnum();
			Map<String, String> paramMap2 = new HashMap<String, String>();
			paramMap2.put("ACNUM", acnum);
			paramMap2.put("TIME", DateUtil.getLongDate(computeVo.getDate_utc()));
			paramMap2.put("ALARM_LEVEL", alarmLevel+"");
			paramMap2.put("DETAOIL", deta_oiq2);
			paramMap2.put("ESN_SEQ", "右发动机");
			
			String newStr2 = StringUtil.buildExpression(paramMap2, ruleVo.getMsgContent());
//			String smStr2 = StringUtil.buildExpression(paramMap2, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr2+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr2,null,alarmLevel);
		}else{
			logger.debug("A25滑油右发正常！");

		}		
		
	}

}
