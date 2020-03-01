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
 * 氧气瓶K值方差超限
 * 
 * @author jerry
 * @date Mar 18, 2013
 */
public class AlarmImplA23_04_K extends AlarmRuleImplBaseClass {
	Logger logger = NovaLogger.getLogger(this.getClass());

	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception {
		// 找到告警消息模板
		A23ComputeVo computeVo = (A23ComputeVo) targetVo.getParam("COMPUTEDVO");

		boolean isAlarm = false;
		int alarmLevel = 0;
		String limit_Value = "";

		double k = computeVo.getPres_st_s46_k();
		k=k*(1000*3600*24);
		computeVo.getPres_mid_s46();
		computeVo.getPres_st_c15_s46();
		computeVo.getPres_st_s46_k_pointtype();

		// TODO 应该是连续3个飘点，进行飘点告警
		//if (computeVo.getPres_st_s46_k_pointtype() == 1 && (k <= -3 && k > -4)) 去掉飘点描述
		if ((computeVo.getPres_st_s46_k_pointtype() == 1) && (k <= -3 && k > -7)) {
			isAlarm = true;
			limit_Value = "(-7,-3)";
			alarmLevel = 3;
		} else if ((computeVo.getPres_st_s46_k_pointtype() == 1) && (k <= -7 && k > -13)) {
			isAlarm = true;
			limit_Value = "(-13,-7)";
			alarmLevel = 2;
		} else if ((computeVo.getPres_st_s46_k_pointtype() == 1) && k <= -13) {
			isAlarm = true;
			limit_Value = "(-*,-13)";
			alarmLevel = 1;
		}

		// {TIME} 飞机{ACNUM}氧气瓶本报文掉压率K方差超限,平均值{K_AVG},当前值{K_CUR},方差{K_STD}
		// 对消息内容 变量进行替换
		if (isAlarm) {
			String acnum = computeVo.getAcnum();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", DateUtil.getLongDate(computeVo.getDate_utc()));
			paramMap.put("K_AVG", computeVo.getPres_st_s46_k_avg() + "");
			paramMap.put("K_CUR", k + "");
			paramMap.put("K_STD", computeVo.getPres_st_s46_k_std() + "");
			paramMap.put("S46_PRESS", computeVo.getPres_mid_s46() + "");
			paramMap.put("LIMIT_VALUE", limit_Value);
			paramMap.put("ALARM_LEVEL", alarmLevel + "");

			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
//			String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());

			logger.info("告警消息为：【" + newStr + "】");

			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr, null, alarmLevel);
		}

	}

}
