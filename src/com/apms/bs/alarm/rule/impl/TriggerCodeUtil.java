package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dfd.DfdCodeMapVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

public class TriggerCodeUtil {
	
	public static String getCodeAlarmMsg(DfdCodeMapVo mapVo,AlarmRuleVo ruleVo){
		
		//消息编号、报文编号、消息时间、CODE、飞机号
		//{TIME},飞机{ACNUM},报文{RPTDESC}产生CODE告警，原因{REASON}
		Map<String, String> paramMap = new HashMap<String, String>();
		String acnum = mapVo.getAcnum();
		paramMap.put("TIME", DateUtil.getLongDate(mapVo.getDateUtc())); 
		paramMap.put("ACNUM", acnum);
		paramMap.put("RPTDESC", mapVo.getDetailMsg());
		paramMap.put("REASON", mapVo.getCode_str());
		
		String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
		
		return newStr;
		
	}
	
	//获取ACW配置信息
	public static void getAcwMap(){
		StringBuilder sb = new StringBuilder();
		
	}

}
