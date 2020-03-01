package com.apms.bs.apu.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

/**
 * APU新增前置拦截器，对小时数进行转换，变为分钟
 */
public class ApuInsertBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
		double totalHour = new Double(dataValue.get("TOTALTIME_HOUR").toString());
		double onInstallHour = new Double(dataValue.get("TIME_ONINSTALL_HOUR").toString());
		double onRepairedHour = new Double(dataValue.get("TIME_ONREPAIRED_HOUR").toString());
		
		dataValue.put("TOTALTIME", totalHour*60);
		dataValue.put("TIME_ONINSTALL", onInstallHour*60);
		dataValue.put("TIME_ONREPAIRED", onRepairedHour*60);
		
		logger.debug("APU新增前置拦截器执行完成！");
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
	}

	/**
	 * 不常用 ，待查证
	 */
	public void doSomething(Map<String, Object> map) throws Exception {
		
	}

	/**
	 * 待查证
	 */
	public void doSomething(List<Map<String, Object>> dataValueList) throws Exception {

	}

}
