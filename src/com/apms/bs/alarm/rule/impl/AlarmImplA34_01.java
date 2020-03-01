package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A13ComputeVo;
import com.apms.bs.datacompute.vo.A34ParsedVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dfd.DfdCodeMapVo;
import com.apms.bs.dfd.DfdDataUtil;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * 空调A19报文CODE告警
 * 
 * @author jerry
 * @date Apr 15, 2013
 */
// EGTA_MAX_COR 飘点大于3告警 确定向上还是向下飘点
public class AlarmImplA34_01 extends AlarmRuleImplBaseClass {

	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo)
			throws Exception {

		A34ParsedVo parsedVo = (A34ParsedVo) targetVo.getParam("COMPUTEDVO");

		String wav1_tmr = parsedVo.getWav1_tmr_s7();
		String wav2_tmr = parsedVo.getWav2_tmr_s7();
		int alarmLevelNum = 3;
		String alarmLevel = "严重级别";
		if (Double.parseDouble(wav1_tmr) > 999) { // 告警条件

			String acnum = parsedVo.getAcnum();
			alarmLevel = "非常严重级别";
			String output = "左活门关闭时间";
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", DateUtil.getLongDate(parsedVo.getDate_utc()));
			paramMap.put("ALARM_LEVEL", alarmLevel + "");
			paramMap.put("OUTPUT", output + Double.parseDouble(wav1_tmr) + "s");
			targetVo.setDevicesn(parsedVo.getEsn_1());

			String newStr = StringUtil.buildExpression(paramMap,
					ruleVo.getMsgContent());
			// String smStr = StringUtil.buildExpression(paramMap,
			// ruleVo.getSmContent());

			logger.info("告警消息为：【" + newStr + "】");

			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,
					newStr, alarmLevelNum);
		} else if (Double.parseDouble(wav1_tmr) > 9) { // 告警条件

			String acnum = parsedVo.getAcnum();
			String output = "左活门关闭时间";
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", DateUtil.getLongDate(parsedVo.getDate_utc()));
			paramMap.put("ALARM_LEVEL", alarmLevel + "");
			paramMap.put("OUTPUT", output + Double.parseDouble(wav1_tmr) + "s");
			targetVo.setDevicesn(parsedVo.getEsn_1());

			String newStr = StringUtil.buildExpression(paramMap,
					ruleVo.getMsgContent());
			// String smStr = StringUtil.buildExpression(paramMap,
			// ruleVo.getSmContent());

			logger.info("告警消息为：【" + newStr + "】");

			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,
					null, alarmLevelNum);
		}
		if (Double.parseDouble(wav2_tmr) > 999) { // 告警条件

			String acnum = parsedVo.getAcnum();
			alarmLevel = "非常严重级别";
			String output = "右活门关闭时间";
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", DateUtil.getLongDate(parsedVo.getDate_utc()));
			paramMap.put("ALARM_LEVEL", alarmLevel + "");
			paramMap.put("OUTPUT", output+ Double.parseDouble(wav2_tmr) + "s");
			targetVo.setDevicesn(parsedVo.getEsn_2());

			String newStr = StringUtil.buildExpression(paramMap,
					ruleVo.getMsgContent());
			// String smStr = StringUtil.buildExpression(paramMap,
			// ruleVo.getSmContent());

			logger.info("告警消息为：【" + newStr + "】");

			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,
					newStr, alarmLevelNum);
		} else if (Double.parseDouble(wav2_tmr) > 9) { // 告警条件

			String acnum = parsedVo.getAcnum();

			String output = "右活门关闭时间";
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("TIME", DateUtil.getLongDate(parsedVo.getDate_utc()));
			paramMap.put("ALARM_LEVEL", alarmLevel + "");
			paramMap.put("OUTPUT", output + Double.parseDouble(wav2_tmr) + "s");
			targetVo.setDevicesn(parsedVo.getEsn_2());

			String newStr = StringUtil.buildExpression(paramMap,
					ruleVo.getMsgContent());
			// String smStr = StringUtil.buildExpression(paramMap,
			// ruleVo.getSmContent());

			logger.info("告警消息为：【" + newStr + "】");

			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,
					newStr, alarmLevelNum);
		}

	}

}
