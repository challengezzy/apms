package com.apms.bs.alarm.interceptors;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

import com.apms.bs.alarm.AlarmConfigService;

public class MonitorObjectChangeAfter implements FormInterceptor {

	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		String mocode = (String)dataValue.get("CODE");
		String moname = (String)dataValue.get("NAME");
		
		AlarmConfigService.getInstance().refreshMoRuleMapByMo(mocode);
		
		logger.debug("告警监控对象[" + moname + "]数据更新后，调用缓存更新信息成功！");
		
	}

	@Override
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSomething(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSomething(List<Map<String, Object>> dataValueList) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
