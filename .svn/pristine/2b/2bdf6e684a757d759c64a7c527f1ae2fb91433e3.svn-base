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
 * 空调A19报文CODE告警
 * @author jerry
 * @date Apr 15, 2013
 */
public class AlarmImplA26_Code extends AlarmRuleImplBaseClass{
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception{
		AcarsHeadVo headVo =  (AcarsHeadVo)targetVo.getParam(DfdVarConst.KEY_HEADVO);
		AcarsDfdA26Vo_A320 A26body = (AcarsDfdA26Vo_A320)targetVo.getParam(DfdVarConst.KEY_BODYVO);
		
		int alarmLevel = 0;

		DfdCodeMapVo mapVo = DfdDataUtil.getCodeMapVo(targetVo.getRptno(), headVo.getCode(), headVo.getAcid());
		String codeA26 = headVo.getCode();
		if(codeA26.equals("1000")){
			String acnum = headVo.getAcid();
			Map<String, String> paramMap1 = new HashMap<String, String>();
			
			paramMap1.put("ACNUM", acnum);
			paramMap1.put("TIME", DateUtil.getLongDate(targetVo.getDateUtc()));
			paramMap1.put("REASON", A26body.getR1().getReason());	
			targetVo.setDevicesn(headVo.getEsn());
           
			String newStr1 = StringUtil.buildExpression(paramMap1, ruleVo.getMsgContent());
			String smStr1 = StringUtil.buildExpression(paramMap1, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr1+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr1,smStr1,alarmLevel);
			
			String newStr = TriggerCodeUtil.getCodeAlarmMsg(mapVo, ruleVo);
			msgService.insertDispathAlarmMessage(ruleVo,targetVo, newStr,newStr);
		}else{
			
			logger.debug("报文["+targetVo.getRptno()+"]、飞机["+targetVo.getAcnum()+"]未找到对应的CODE告警配置!");
		}
		
	}

}
