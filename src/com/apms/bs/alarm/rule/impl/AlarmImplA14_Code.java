package com.apms.bs.alarm.rule.impl;

import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dataprase.vo.AcarsDfdA14Vo_A320;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dfd.DfdCodeMapVo;
import com.apms.bs.dfd.DfdDataUtil;
import com.apms.bs.dfd.DfdVarConst;

/**
 * APU停车报CODE告警
 * @author jerry
 * @date Mar 21, 2013
 */
public class AlarmImplA14_Code extends AlarmRuleImplBaseClass{

	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception{
		AcarsHeadVo headVo =  (AcarsHeadVo)targetVo.getParam(DfdVarConst.KEY_HEADVO);
		
		DfdCodeMapVo mapVo = DfdDataUtil.getCodeMapVo(targetVo.getRptno(), headVo.getCode(), headVo.getAcid());
		if(mapVo != null){
			mapVo.setMsgno(targetVo.getMsgno());
			mapVo.setDateUtc(headVo.getDateUtc());
			mapVo.setDetailMsg("APU停车A14");
			
			String newStr = TriggerCodeUtil.getCodeAlarmMsg(mapVo, ruleVo);
			msgService.insertDispathAlarmMessage(ruleVo,targetVo, newStr);
		}else{
			
			logger.debug("报文["+targetVo.getRptno()+"]、飞机["+targetVo.getAcnum()+"]未找到对应的CODE告警配置!");
		}
		
	}

}
