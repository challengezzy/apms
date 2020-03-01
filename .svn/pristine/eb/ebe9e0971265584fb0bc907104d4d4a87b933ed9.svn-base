package com.apms.bs.alarm.rule;

import java.text.DecimalFormat;
import java.util.HashMap;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * 门限值告警实现类
 * @author jerry
 * @date Nov 20, 2014
 */
public class AlarmRuleLimitImp extends AlarmRuleImplBaseClass{
	
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		boolean isAlarm=false;//告警开关
		Double upperLimit = null;
		Double lowerLimit = null;
		//ruleVo中包含所有的规则内容， targetVo中包含要计算的内容
		
		HashMap<String, Object> targetMap = targetVo.getParamMap();
		//1 获取判定字段实际值
		if(targetMap.get(ruleVo.getVal_column())!=null){
			String strRealVal = targetMap.get(ruleVo.getVal_column())+"";
			DecimalFormat dcmFmt = new DecimalFormat("0.00");
			Double d = new Double(strRealVal);
			String realValStr= dcmFmt.format(d);
			
			//获取规则中配置的所有参数值
			HashMap<String, String> ruleValueMap =  ruleVo.getParamValueMap(targetMap);
			
			//2 获取上下限值
			String strUpperLimit = ruleValueMap.get("LIMIT_UPPER");
			if(strUpperLimit != null){
				upperLimit = new Double(strUpperLimit);
			}
			
			String strLowerLimit = ruleValueMap.get("LIMIT_LOWER");
			if(strLowerLimit != null){
				lowerLimit = new Double(strLowerLimit);
			}
			
			//3 获取门限判定类型
			String typeLimit = ruleValueMap.get("LIMIT_TYPE");
					
			//5 判断是否超出门限值
			String time=DateUtil.getDateStr(targetVo.getDateUtc(),"yyMMdd HH:mm");
			String acnum=targetVo.getAcnum();
			ruleValueMap.put("ACNUM", acnum);
			ruleValueMap.put("TIME", time);
			ruleValueMap.put("REAL_VALUE",realValStr);
			ruleValueMap.put("MODULE",ruleVo.getMoname());
			ruleValueMap.put("ATTR_DESC",ruleVo.getVal_column_name());
			
			if( "大于".equals(typeLimit.trim()) ){
				if(upperLimit==null){
					return ;
				}else if(d.doubleValue()>=upperLimit.doubleValue()){
					
					ruleValueMap.put("UPPER_LOWER",upperLimit+"");
					isAlarm=true;
				}
			}else if( "小于".equals(typeLimit.trim()) ){
				if(lowerLimit==null){
					return ;
				}else if(d.doubleValue()<=lowerLimit.doubleValue()){
					ruleValueMap.put("UPPER_LOWER",lowerLimit+"");
					isAlarm=true;
				}
			}else if( "介于".equals(typeLimit.trim()) ){
				if(lowerLimit==null || upperLimit==null){
					return;
				}else if(d.doubleValue()>=lowerLimit.doubleValue() && d.doubleValue()<=upperLimit.doubleValue()){
					ruleValueMap.put("UPPER_LOWER","("+lowerLimit+","+upperLimit+")");
					isAlarm=true;
				}
			}else if( "超出".equals(typeLimit.trim()) ){
				if(lowerLimit==null || upperLimit==null){
					return;
				}else if(d.doubleValue()<=lowerLimit||d.doubleValue()>=upperLimit.doubleValue()){
					ruleValueMap.put("UPPER_LOWER","("+lowerLimit+","+upperLimit+")");
					isAlarm=true;
				}
			}
			if(isAlarm){
				//6 替换变量，生成实际消息
				String newStr = StringUtil.buildExpression(ruleValueMap, targetMap, ruleVo.getMsgContent());
				//7消息派发
				msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,newStr,-1);
			}
		}
	}
}
