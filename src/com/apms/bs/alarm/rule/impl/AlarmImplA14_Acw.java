package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsDfdA14Vo_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A14报文,ACWX控制字告警
 * @author jerry
 * @date Apr 1, 2013
 */
public class AlarmImplA14_Acw  extends AlarmImplAcw{
	
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		
		AcarsDfdA14Vo_A320 a14vo = (AcarsDfdA14Vo_A320)targetVo.getParam(DfdVarConst.KEY_BODYVO);
		String acnum = targetVo.getAcnum();
		String acmodel = targetVo.getAcmodel();
		
		//{TIME} 飞机{ACNUM}，报文{RPTNO},{ROWTITLE} {ACWX}控制字编码{ACWCODE}。
		//异常详细内容:	{EXPDETAIL}		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("TIME", DateUtil.getLongDate(targetVo.getDateUtc())); 
		paramMap.put("ACNUM", acnum);
		paramMap.put("RPTNO",ApmsVarConst.RPTNO_A14);
		paramMap.put("ROWTITLE", "");
		
		//控制字3告警
		AcarsAcwVo acw3 = a14vo.getE1().getAcw3Vo();
		String warningStr = getAcwWarning(acw3, acmodel, ApmsVarConst.RPTNO_A14, "ACW3", null);
		//warningStr如果为null,表示不需要进行告警
		if(warningStr != null){
			paramMap.put("ACWX",acw3.getAcwName());
			paramMap.put("ACWCODE",acw3.getAcwVal());
			paramMap.put("EXPDETAIL",warningStr);
			//对ACW告警内容进行变量替换		
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
			logger.info("告警消息为：【"+newStr+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr);
		}
		
		
		AcarsAcwVo acw4 = a14vo.getE1().getAcw4Vo(); 
		warningStr = getAcwWarning(acw4, acmodel, ApmsVarConst.RPTNO_A14, "4", null);
		//warningStr如果为null,表示不需要进行告警
		if(warningStr != null){
			paramMap.put("ACWX",acw4.getAcwName());
			paramMap.put("ACWCODE",acw4.getAcwVal());
			paramMap.put("EXPDETAIL",warningStr);
			//对ACW告警内容进行变量替换		
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
			logger.info("告警消息为：【"+newStr+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr);
		}
	}
	
	

}
