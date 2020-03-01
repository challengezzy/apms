package com.apms.bs.alarm.interceptors;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;
import smartx.publics.form.bs.service.SmartXFormService;
import smartx.publics.metadata.MetadataTempletUtil;

import com.apms.bs.alarm.AlarmConfigService;

public class AlarmRuleChangeAfter implements FormInterceptor {

	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		String ruleid = dataValue.get("ID").toString();
		String rulename = (String)dataValue.get("NAME");
		
		AlarmConfigService.getInstance().refreshMoRuleMapByRuleId(ruleid);
		
		logger.debug("告警监控规则[" + rulename + "]数据更新后，调用缓存更新信息成功！");
		
	}

	@Override
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		
		//规则删除后，更新缓存
		for (Map<String, Object> dataValue : dataValueList) {
			String flag = (String) dataValue.get(SmartXFormService.KEYNAME_MODIFYFLAG);
			//String ruleid = (String)dataValue.get("ID");
			String rulecode = (String)dataValue.get("CODE");
			if ("delete".equalsIgnoreCase(flag.trim())){
				AlarmConfigService.getInstance().deleteRuleCacheByRuleCode(rulecode);
			}
		}
		
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
