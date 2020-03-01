package com.apms.bs.alarm.rule;

import java.util.HashMap;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * 开关告警实现类
 * @author jerry
 * @date Nov 20, 2014
 */
public class AlarmRuleToggleImp extends AlarmRuleImplBaseClass{
	
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		boolean isAlarm=false;//告警开关
		//ruleVo中包含所有的规则内容， targetVo中包含要计算的内容
		HashMap<String, Object> targetMap = targetVo.getParamMap();
		//获取判定字段实际值
		Object obj=targetMap.get(ruleVo.getVal_column());
		//获取规则中配置的所有参数值
		HashMap<String, String> ruleValueMap =  ruleVo.getParamValueMap(targetMap);
		String toggle_type = ruleValueMap.get("TOGGLE_TYPE");//toggle_type=1时，1告警，其它，不告警；toggle_type=0时，0告警，其它，不告警
		if (toggle_type == null) {
			isAlarm = false;
		} else if ("1".equals(toggle_type)) {
			isAlarm = judgeAlarm1(obj);
		} else {
			isAlarm = judgeAlarm0(obj);
		}
		
		if(isAlarm){
			String time=DateUtil.getDateStr(targetVo.getDateUtc(),"yyMMdd HH:mm");
			String acnum=targetVo.getAcnum();
			ruleValueMap.put("ACNUM", acnum);
			ruleValueMap.put("TIME", time);
//			ruleValueMap.put("REAL_VALUE",realValStr);
			ruleValueMap.put("MODULE",ruleVo.getMoname());
			ruleValueMap.put("ATTR_DESC",ruleVo.getVal_column_name());
			//替换变量，生成实际消息
			String newStr = StringUtil.buildExpression(ruleValueMap, targetMap, ruleVo.getMsgContent());
			//消息派发
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,newStr,-1);
		}
	
	}

	private boolean judgeAlarm0(Object obj){
		if(obj==null){
			return false;
		}
		
		String str=(String)obj;
		if("0".equals(str)){
			return true;
		}else{//未知
			return false;
		}
	}
	
	private boolean judgeAlarm1(Object obj){
		if(obj==null){
			return false;
		}
		
		String str=(String)obj;
		if("1".equals(str)){
			return true;
		}else{//未知
			return false;
		}
	}
}
