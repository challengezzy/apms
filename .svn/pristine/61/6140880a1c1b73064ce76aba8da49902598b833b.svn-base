package com.apms.bs.alarm.rule;

import java.util.HashMap;
import java.util.Iterator;

public class AlarmRuleUtil {
	
	/**
	 * 合并两个map为一个map对象
	 * @param ruleValueMap
	 * @param targetMap
	 * @return
	 */
	public HashMap<String, Object> mergeMap(HashMap<String, String> ruleValueMap, HashMap<String, Object> targetMap){
		
		HashMap<String, Object> newMap = new HashMap<String, Object>();
		//把两个HashMap合并成一个Map对象
		Iterator<String> iter = ruleValueMap.keySet().iterator();
		while(iter.hasNext()){
			String mapkey = iter.next();
			newMap.put(mapkey, ruleValueMap.get(mapkey));
		}
		
		//把两个HashMap合并成一个Map对象
		Iterator<String> iter2 = targetMap.keySet().iterator();
		while(iter2.hasNext()){
			String mapkey = iter2.next();
			newMap.put(mapkey, targetMap.get(mapkey));
		}
		
		return newMap;
	}

}
