package com.apms.bs.engine.interceptor;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

/**
 * 发动机新增前置拦截器，对小时数进行转换，变为分钟
 */
public class EngineInsertBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
		double tsn = new Double(dataValue.get("TSN_HRS")==null?"0":dataValue.get("TSN_HRS").toString());
		double chkfh = new Double(dataValue.get("CHKFH_HRS")==null?"0":dataValue.get("CHKFH_HRS").toString());
		double time_oninstall = new Double(dataValue.get("INSTFH_HRS")==null?"0":dataValue.get("INSTFH_HRS").toString());
		
		String acid="";
		if (dataValue.get("AIRCRAFTID")!=null){
			acid=dataValue.get("AIRCRAFTID").toString();
		}
		
		dataValue.put("ACNUM", acid);
		dataValue.put("TSN", tsn*60);
		dataValue.put("TIME_ONREPAIRED", chkfh*60);
		dataValue.put("TIME_ONINSTALL", time_oninstall*60);
		
		logger.debug("发动机新增前置拦截器执行完成！");
	
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
