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
 * 当前S13标态压力与前一报文标态S46压力差状态
 * @author jerry
 *
 */
public class AlarmImplA23_02 extends AlarmRuleImplBaseClass{
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		//找到告警消息模板
		A23ComputeVo computeVo = (A23ComputeVo)targetVo.getParam("COMPUTEDVO"); 
		
		boolean isAlarm = false;
		int alarmLevel = 0;
		String limit_Value="";
		String warning = "";
		computeVo.getDeta_pres_fwd();
		computeVo.getPres_mid_s46();
		computeVo.getPres_st_c15_s46();
		
		//与前一报文标态S46压力差状态
		if(computeVo.getDeta_pres_fwd()>=120){
			alarmLevel=4;
			isAlarm=false;
			warning = "换瓶点！";
		}
		else if(computeVo.getDeta_pres_fwd()>-100 && computeVo.getDeta_pres_fwd()<=-80){
			alarmLevel=3;
			isAlarm=true;
			limit_Value="-100,-80";
			warning = "与前报文比较掉压超"+limit_Value+"";
		}
		else if(computeVo.getDeta_pres_fwd()>-120 && computeVo.getDeta_pres_fwd()<=-100){
			alarmLevel=2;
			isAlarm=true;
			limit_Value="-120,-100";
			warning = "与前报文比较掉压超"+limit_Value+"";
		}
		else if(computeVo.getDeta_pres_fwd()<=-120){
			alarmLevel=1;
			isAlarm=true;
			limit_Value="-*,120";
			warning = "与前报文比较掉压超"+limit_Value+"";
		}
			
			
		// 报文时间：{TIME}，飞机{ACNUM}氧气瓶与前一报文压力差差值为{DETA_PRES_FWD},判定为{DETA_PRES_FWD_DESC}
		// 对消息内容 变量进行替换

		if (isAlarm) {

			String acnum = computeVo.getAcnum();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("DETA_PRES_FWD", computeVo.getDeta_pres_fwd() + "");
			paramMap.put("TIME", DateUtil.getLongDate(computeVo.getDate_utc()));
			paramMap.put("LIMIT_VALUE", limit_Value + "");
			paramMap.put("ALARM_LEVEL", alarmLevel + "");
			paramMap.put("DETA_PRES_FWD_DESC", warning);
			
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
//			String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,null,alarmLevel);

		} else {
			logger.debug("当前S13标态压力与前一报文标态S46压力差状态正常！");
		}
		
	}

}
