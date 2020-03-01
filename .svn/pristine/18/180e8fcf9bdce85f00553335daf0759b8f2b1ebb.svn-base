package com.apms.bs.apu.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

public class ApuCorUpdateBefore  implements FormInterceptor {
	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		double errHour = new Double(dataValue.get("ERRHOUR").toString());
		double errCyc = new Double(dataValue.get("ERRCYCLE").toString());
		double corHour = new Double(dataValue.get("CORHOUR").toString());
		double corCyc = new Double(dataValue.get("CORCYCLE").toString());
		
		double hour_move = (corHour - errHour)*60; //正确数值- 错误 值 = 修正值 
		double cyc_move = corCyc - errCyc;
		
		dataValue.put("ERRHOUR", errHour*60);
		dataValue.put("CORHOUR", corHour*60);
		dataValue.put("HOUR_CORRECT", hour_move);
		dataValue.put("CYCLE_CORRECT", cyc_move);
		
		logger.debug("计算修正的小时、循环数成功！");
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
