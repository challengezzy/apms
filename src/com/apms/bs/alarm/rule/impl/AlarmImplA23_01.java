package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A23ComputeVo;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * 氧气压力阈值门限告警
 * @author jerry
 * @date Mar 21, 2013
 */
public class AlarmImplA23_01 extends AlarmRuleImplBaseClass{
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		
		A23ComputeVo computeVo = (A23ComputeVo)targetVo.getParam("COMPUTEDVO"); 
		
		boolean isAlarm = false;
		int alarmLevel = 0;
		
		//判断isAlarm,level
				
		computeVo.getPres_mid_s46();
		computeVo.getPres_st_c15_s46();
		String limit_value="";
		if ((computeVo.getPres_mid_s46() >= 2450 || computeVo.getPres_mid_s46() <= 1500)
				|| (computeVo.getPres_st_c15_s46() >= 2450 || computeVo
						.getPres_st_c15_s46() <= 1500)) {
			alarmLevel = 1;
			isAlarm = true;
			limit_value = "2450,1500";
		} else if ((computeVo.getPres_mid_s46() >= 2400 || computeVo
				.getPres_mid_s46() <= 1600)
				|| (computeVo.getPres_st_c15_s46() >= 2400 || computeVo
						.getPres_st_c15_s46() <= 1600)) {
			alarmLevel = 2;
			isAlarm = false;
			limit_value = "2400,1600";
		} else if ((computeVo.getPres_mid_s46() >= 2350 || computeVo
				.getPres_mid_s46() <= 1750)
				|| (computeVo.getPres_st_c15_s46() >= 2350 || computeVo
						.getPres_st_c15_s46() <= 1750)) {
			alarmLevel = 3;
			isAlarm = false;
			limit_value = "2350,1750";
		}

		
		
		if(isAlarm){
			//HashVO msgTemplate = msgService.getMsgTemplateById(msgTemplateId);
			
			//告警等级:{ALARM_LEVEL},报文时间：{TIME} .飞机：{ACNUM}氧气瓶压力为实际{S46_PRESS},标态15度{S46ST_PRESS}，低于门限{LIMIT_VALUE}。
			
			String acnum = computeVo.getAcnum();
			Double s46_st_press = computeVo.getPres_st_c15_s46();
			Double s46_press =computeVo.getPres_mid_s46();
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("S46_PRESS", s46_press+"");
			paramMap.put("S46ST_PRESS", s46_st_press+"");
			paramMap.put("LIMIT_VALUE", limit_value);
			paramMap.put("TIME", DateUtil.getLongDate(computeVo.getDate_utc()));
			paramMap.put("ALARM_LEVEL", alarmLevel+"");
           
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
//			String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,null,alarmLevel);
			
		}else{
			logger.debug("氧气瓶压力正常！");
		}
		
	}

}
