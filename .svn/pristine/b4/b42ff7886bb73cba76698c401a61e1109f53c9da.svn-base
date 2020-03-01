package com.apms.bs.apu.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

import com.apms.bs.util.MathUtil;

/**
 * APU部件信息更新后，更新APU中最低小时循环
 */
public class ApuLlpUpdateBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		double aputime_oninstall = MathUtil.toDoubleValue(dataValue.get("APUTIME_ONINSTALL"));
		double time_oninstall = MathUtil.toDoubleValue(dataValue.get("TIME_ONINSTALL"));
		double limit_time = MathUtil.toDoubleValue(dataValue.get("LIMIT_TIME"));
		
		
		//单位转换，把小时转换为分钟进行存储
		dataValue.put("APUTIME_ONINSTALL", aputime_oninstall*60);
		dataValue.put("TIME_ONINSTALL", time_oninstall*60);
		dataValue.put("LIMIT_TIME", limit_time*60);
		
		logger.debug("时间数据保存前转换为分钟！");
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
