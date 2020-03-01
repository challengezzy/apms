package com.apms.bs.aircraft.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

/**
 * 飞机飞行日志新增前置拦截器，对小时数进行转换，变为分钟
 */
public class FlightLogInsertBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {

		double airsum = new Double(dataValue.get("FLAIR_SUM_HOUR") == null ? "0" : dataValue.get("FLAIR_SUM_HOUR").toString());
		double airblocksum = new Double(dataValue.get("FIBLOCK_SUM_HOUR") == null ? "0" : dataValue.get("FIBLOCK_SUM_HOUR").toString());

		String acid = "";
		if (dataValue.get("ACNUM") != null) {
			acid = dataValue.get("ACNUM").toString();
		}

		dataValue.put("ACNUM", acid);
		dataValue.put("FLAIR_SUM", airsum * 60);
		dataValue.put("FIBLOCK_SUM", airblocksum * 60);

		logger.debug("飞行日志新增前置拦截器执行完成！");
	}

	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		String d = "";
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
