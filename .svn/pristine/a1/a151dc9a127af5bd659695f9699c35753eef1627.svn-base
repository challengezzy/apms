package com.apms.bs;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.AlarmConfigService;
import com.apms.cache.ApmsServerCache;
import com.apms.cache.FlightCache;

/**
 * 初始化缓存服务类
 * @author jerry
 * @date Apr 2, 2014
 */
public class ApmsInitService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	public static boolean isInited = false;

	public ApmsInitService() {
		if( !isInited ){
			init();
		}
	}

	/**
	 * 接口服务类初始化
	 */
	private void init() {
		try{
			//apms缓存
			ApmsServerCache server = ApmsServerCache.getInstance();
			server.refreshCache();
			
			//航班缓存
			FlightCache flightC = FlightCache.getInstance();
			flightC.refreshCache();
			
			//
			AlarmConfigService.getInstance().initMonitorRules();
			
			
			isInited = true;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("******关键初始化服务类[ApmsInitService]初始化失败！！******");
		}
	}
}
