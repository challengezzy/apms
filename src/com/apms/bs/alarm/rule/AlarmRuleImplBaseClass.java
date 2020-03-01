package com.apms.bs.alarm.rule;

import org.apache.log4j.Logger;

import com.apms.bs.alarm.msg.AlarmMessageService;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import smartx.framework.common.vo.NovaLogger;

/**
 * 告警规则计算基类
 * @author zzy
 *
 */
abstract public class AlarmRuleImplBaseClass {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());	
	protected AlarmMessageService msgService = new AlarmMessageService();
	
	protected String msgSubject;
	
	protected String msgContent;
	
	
	/**
	 * 告警触发计算
	 * @param monitorObj 被监控对象
	 */
	abstract public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception;

}
