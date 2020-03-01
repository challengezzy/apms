package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dataprase.vo.AcarsDfdA26Vo_A320;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dfd.DfdCodeMapVo;
import com.apms.bs.dfd.DfdDataUtil;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * A48_CODE性能告警
 * 
 * @author jerry
 * @date Sep 26, 2016
 */
public class AlarmImplA48_Code extends AlarmRuleImplBaseClass {

	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception {
		AcarsHeadVo headVo = (AcarsHeadVo) targetVo.getParam(DfdVarConst.KEY_HEADVO);
		int alarmLevel = 0;

		DfdCodeMapVo mapVo = DfdDataUtil.getCodeMapVo(targetVo.getRptno(), headVo.getCode(), headVo.getAcid());
		
		String acnum = headVo.getAcid();
		Map<String, String> paramMap1 = new HashMap<String, String>();

		paramMap1.put("ACNUM", acnum);
		paramMap1.put("CODE", headVo.getCode());
		paramMap1.put("TIME", DateUtil.getLongDate(targetVo.getDateUtc()));
		paramMap1.put("REASON", mapVo.getCode_str() );
		targetVo.setDevicesn(headVo.getEsn());

		String newStr1 = StringUtil.buildExpression(paramMap1, ruleVo.getMsgContent());
		String smStr1 = StringUtil.buildExpression(paramMap1, ruleVo.getSmContent());

		logger.info("告警消息为：【" + newStr1 + "】");

		msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr1, smStr1, alarmLevel);

	}

}
