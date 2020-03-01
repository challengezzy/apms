package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsDfdA13Vo_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A13报文,ACWX控制字告警
 * @author jerry
 * @date Apr 1, 2013
 */
public class AlarmImplA13_Acw  extends AlarmImplAcw{
	
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		
		AcarsDfdA13Vo_A320 a13vo = (AcarsDfdA13Vo_A320)targetVo.getParam(DfdVarConst.KEY_BODYVO);
		String acnum = targetVo.getAcnum();
		String acmodel = targetVo.getAcmodel();
		
		//{TIME} 飞机{ACNUM}，报文{RPTNO},{ROWTITLE} {ACWX}控制字编码{ACWCODE}。
		//异常详细内容:	{EXPDETAIL}		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("TIME", DateUtil.getLongDate(targetVo.getDateUtc())); 
		paramMap.put("ACNUM", acnum);
		paramMap.put("RPTNO",ApmsVarConst.RPTNO_A13);
		paramMap.put("ROWTITLE", "");
		
		//控制字3告警
		AcarsAcwVo acw1_n1 = a13vo.getN1().getAcw1Vo();
		AcarsAcwVo acw2_n1 = a13vo.getN1().getAcw2Vo();
		AcarsAcwVo acw1_n2 = a13vo.getN2().getAcw1Vo();
		AcarsAcwVo acw2_n2 = a13vo.getN2().getAcw2Vo();
		AcarsAcwVo acw1_n3 = a13vo.getN3().getAcw1Vo();
		AcarsAcwVo acw2_n3 = a13vo.getN3().getAcw2Vo();
		
		String warningStr1 = getAcwWarning(acw1_n1, acmodel, ApmsVarConst.RPTNO_A13, "ACW1", null);
		String warningStr2 = getAcwWarning(acw2_n1, acmodel, ApmsVarConst.RPTNO_A13, "ACW2", null);
		String warningStr3 = getAcwWarning(acw1_n2, acmodel, ApmsVarConst.RPTNO_A13, "ACW1", null);
		String warningStr4 = getAcwWarning(acw2_n2, acmodel, ApmsVarConst.RPTNO_A13, "ACW2", null);
		String warningStr5 = getAcwWarning(acw1_n3, acmodel, ApmsVarConst.RPTNO_A13, "ACW1", null);
		String warningStr6 = getAcwWarning(acw2_n3, acmodel, ApmsVarConst.RPTNO_A13, "ACW2", null);

		//warningStr如果为null,表示不需要进行告警
		if(warningStr1 != null){
			paramMap.put("ACWX",acw1_n1.getAcwName());
			paramMap.put("ACWCODE",acw1_n1.getAcwVal());
			paramMap.put("EXPDETAIL",warningStr1);
			//对ACW告警内容进行变量替换		
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
			logger.info("告警消息为：【"+newStr+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr);
		}
		
		if(warningStr2 != null){
			paramMap.put("ACWX",acw2_n1.getAcwName());
			paramMap.put("ACWCODE",acw2_n1.getAcwVal());
			paramMap.put("EXPDETAIL",warningStr2);
			//对ACW告警内容进行变量替换		
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
			logger.info("告警消息为：【"+newStr+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr);
		}
		if(warningStr3 != null){
			paramMap.put("ACWX",acw1_n2.getAcwName());
			paramMap.put("ACWCODE",acw1_n2.getAcwVal());
			paramMap.put("EXPDETAIL",warningStr3);
			//对ACW告警内容进行变量替换		
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
			logger.info("告警消息为：【"+newStr+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr);
		}
		if(warningStr4 != null){
			paramMap.put("ACWX",acw2_n2.getAcwName());
			paramMap.put("ACWCODE",acw2_n2.getAcwVal());
			paramMap.put("EXPDETAIL",warningStr4);
			//对ACW告警内容进行变量替换		
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
			logger.info("告警消息为：【"+newStr+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr);
		}
		if(warningStr5 != null){
			paramMap.put("ACWX",acw1_n3.getAcwName());
			paramMap.put("ACWCODE",acw1_n3.getAcwVal());
			paramMap.put("EXPDETAIL",warningStr5);
			//对ACW告警内容进行变量替换		
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
			logger.info("告警消息为：【"+newStr+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr);
		}
		if(warningStr6 != null){
			paramMap.put("ACWX",acw2_n3.getAcwName());
			paramMap.put("ACWCODE",acw2_n3.getAcwVal());
			paramMap.put("EXPDETAIL",warningStr6);
			//对ACW告警内容进行变量替换		
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
			logger.info("告警消息为：【"+newStr+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr);
		}
		
		
	}
	
	

}
