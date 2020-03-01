package com.apms.bs.alarm.rule;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * 飘点告警实现类
 * @author jerry
 * @date Nov 20, 2014
 */
public class AlarmRuleFloatingPointImp extends AlarmRuleImplBaseClass{
	
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		//ruleVo中包含所有的规则内容， targetVo中包含要计算的内容
		HashMap<String, Object> targetMap = targetVo.getParamMap();
		//获取判定字段实际值
		Object obj=targetMap.get("V_POINTTYPE_"+ruleVo.getVal_column());
		int v_pointtype;
		if(obj==null){
			return;
		}else{
		    v_pointtype=(Integer)obj;
		}
		
		int v_out=(Integer) targetMap.get("V_OUT_"+ruleVo.getVal_column());
		// 获取判定字段实际值
		String strRealVal = (String)targetMap.get(ruleVo.getVal_column());
		DecimalFormat dcmFmt = new DecimalFormat("0.00");
		Double d = new Double(strRealVal);
		String realValStr= dcmFmt.format(d);
		
		@SuppressWarnings("unchecked")
		HashMap<String,String> limitValueMap=(HashMap<String, String>) targetMap.get("floatPointLimitValueMap");
		if(limitValueMap != null){
			String limitValue=limitValueMap.get(ruleVo.getVal_column());
			if(limitValue!=null){
				if(Math.abs(d)<Math.abs(Double.parseDouble(limitValue))){
					return;
				}
			}
		}
		if(v_pointtype==0 && v_out==1){
			
			//获取规则中配置的所有参数值
			HashMap<String, String> ruleValueMap =  ruleVo.getParamValueMap(targetMap);
					
			String time=DateUtil.getDateStr(targetVo.getDateUtc(),"yyMMdd HH:mm");
			String acnum=targetVo.getAcnum();
			ruleValueMap.put("ACNUM", acnum);
			ruleValueMap.put("TIME", time);
			ruleValueMap.put("REAL_VALUE",realValStr);
			ruleValueMap.put("MODULE",ruleVo.getMoname());
			ruleValueMap.put("ATTR_DESC",ruleVo.getVal_column_name());
			//替换变量，生成实际消息
			String newStr = StringUtil.buildExpression(ruleValueMap, targetMap, ruleVo.getMsgContent());
			//消息派发
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,newStr,-1);
		}
	}

}
