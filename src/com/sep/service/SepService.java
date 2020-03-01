package com.sep.service;

import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.sep.file.SepDataService;

public class SepService {
	protected Logger logger = NovaLogger.getLogger(this.getClass());

	public Map<String, Object> getA3CardData(String cardId) throws Exception{
		try {
			SepDataService sepDataService =new SepDataService();
			Map<String, Object> map = sepDataService.getA3CardData(cardId);
			logger.info("获取工卡信息ID【" + cardId + "】成功！");
			return map;
		} catch (Exception e) {
			logger.error("获取工卡信息失败!", e);
			throw e;
		}
	}
	
	public Map<String, Object> getNrCardData(String cardId) throws Exception{
		try {
			SepDataService sepDataService =new SepDataService();
			Map<String, Object> map = sepDataService.getNrCardData(cardId);
			logger.info("获取非例行工单打印信息ID【" + cardId + "】成功！");
			return map;
		} catch (Exception e) {
			logger.error("获取非例行工单打印信息失败!", e);
			throw e;
		}
	}
	
}
