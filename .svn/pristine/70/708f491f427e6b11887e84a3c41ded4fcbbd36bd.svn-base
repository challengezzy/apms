package com.apms.bs.alarm.rule.impl;

import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dfd.DfdCodeMapVo;
import com.apms.bs.dfd.DfdDataUtil;
import com.apms.bs.dfd.DfdVarConst;

/**
 * 空调A19报文CODE告警
 * @author jerry
 * @date Apr 15, 2013
 */
public class AlarmImplA33_Code extends AlarmRuleImplBaseClass{
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception{
		AcarsHeadVo headVo =  (AcarsHeadVo)targetVo.getParam(DfdVarConst.KEY_HEADVO);
		
		DfdCodeMapVo mapVo = DfdDataUtil.getCodeMapVo(targetVo.getRptno(), headVo.getCode(), headVo.getAcid());
		if(mapVo != null){
			mapVo.setMsgno(targetVo.getMsgno());
			mapVo.setDateUtc(headVo.getDateUtc());
			mapVo.setDetailMsg("A33_Alarm");
			
			String newStr = TriggerCodeUtil.getCodeAlarmMsg(mapVo, ruleVo);
			msgService.insertDispathAlarmMessage(ruleVo,targetVo, newStr,newStr);
		}else{
			
			logger.debug("报文["+targetVo.getRptno()+"]、飞机["+targetVo.getAcnum()+"]未找到对应的CODE告警配置!");
		}
		
	}

}
