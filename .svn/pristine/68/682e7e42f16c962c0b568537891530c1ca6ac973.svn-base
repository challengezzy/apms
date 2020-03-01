package com.apms.bs.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smartx.framework.common.vo.HashVO;

/**
 *  hashvo 相关的工具类
 * @author Kevin
 *
 */
public class HashVoUtil {
	
	/**
	 * 把hashvo数组转换为MAP对象数组
	 * @param vos
	 * @return
	 */
	public static List<Map<String,Object>> hashVosToMapList(HashVO[] vos){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>(vos.length);
		for(int i=0;i<vos.length;i++){
			Map<String,Object> objMap = new HashMap<String, Object>();
			HashVO vo = vos[i];
			String[] keys = vo.getKeys();
			for(int j=0;j<keys.length;j++){
				String key = keys[j];
				objMap.put(key.toUpperCase(), vo.getObjectValue(key));
			}
			
			mapList.add(objMap);
		}
		
		return mapList;
	}
	
	/**
	 * 把hashvo转换为MAP对象(kev,value)
	 * @param vo
	 * @return
	 */
	public static Map<String, Object> hashVoToMap(HashVO vo) {
		if(vo == null)
			return null;
		
		Map<String, Object> objMap = new HashMap<String, Object>();
		String[] keys = vo.getKeys();
		for (int j = 0; j < keys.length; j++) {
			String key = keys[j];
			objMap.put(key.toUpperCase(), vo.getObjectValue(key));
		}

		return objMap;
	}
	
	/**
	 * 把hashvo转换为MAP对象(kev,value),小写的key
	 * @param vo
	 * @return
	 */
	public static Map<String, Object> hashVoToMapLowerCase(HashVO vo) {
		if(vo == null)
			return null;
		
		Map<String, Object> objMap = new HashMap<String, Object>();
		String[] keys = vo.getKeys();
		for (int j = 0; j < keys.length; j++) {
			String key = keys[j];
			objMap.put(key.toLowerCase(), vo.getObjectValue(key));
		}

		return objMap;
	}

}
