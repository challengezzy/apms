package com.apms.bs.alarm;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaServerEnvironment;

import com.apms.ApmsConst;
import com.apms.bs.alarm.rule.AlarmRuleFloatingPointImp;
import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.AlarmRuleLimitImp;
import com.apms.bs.alarm.rule.AlarmRuleToggleImp;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dfd.DfdVarConst;

/**
 * 告警数据处理服务类
 * @author jerry
 * @date Mar 21, 2013
 */
public class AlarmComputeService {
	
	//提供方法 ,进行告警计算、告警消息生成
	Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	
	/** 是否进行告警判断 */
	private boolean isAlarmEnable = true;
	
	String tmp = (String) NovaServerEnvironment.getInstance().get("IS_ALARM");
	
	private AlarmComputeService(){

		if (tmp != null && tmp.toLowerCase().equals("false")) {
			isAlarmEnable = false;
		}
	}
	
	private static AlarmComputeService alarmService;
	
	public static AlarmComputeService getInstance(){
		if(alarmService == null){
			alarmService = new AlarmComputeService();
		}
		
		return alarmService;
	}
	
	/**
	 * 对给出的监控对象进行告警计算
	 * @param monitorObjCode 告警监控对象CODE
	 * @param pkValue 主键值
	 * @param obj 关联对象
	 * @throws Exception
	 */
	public void alarmObjectAdd(String monitorObjCode,String pkValue,AlarmMonitorTargetVo targetVo) throws Exception{
		
		if(isAlarmEnable == false){
			logger.debug("系统没有启动告警功能，不进行告警处理！");
			return;
		}
		
		ArrayList<AlarmRuleVo> rules = AlarmConfigService.getInstance().getRuleVos(monitorObjCode);
		
		if(rules == null || rules.size() == 0 ){
			logger.warn("!!!!!!!!未找到监控对象的告警规则类，告警忽略!!!!!!!!");
			return;
		}
		
		for(int i=0;i<rules.size();i++){
			AlarmRuleVo ruleVo = rules.get(i);
			String impClass = ruleVo.getRuleImplClass();
			int ruleType = ruleVo.getRuleType();
			String ruleCode = ruleVo.getRuleCode();
			
			try{
				if( DfdVarConst.ALARMTULE_TYPE_CUSTOM == ruleType ){
					//自定义实现类处理
					AlarmRuleImplBaseClass ruleClass;
					try{
						ruleClass = (AlarmRuleImplBaseClass)Class.forName(impClass).newInstance();
					}catch (Exception e) {
						logger.error("规则["+ruleCode+"]的实现类["+impClass+"]实例化失败!");
						continue;
					}
					if(ruleClass != null){
						ruleClass.alartTrigger(ruleVo,targetVo);//真正触发
					}
					
				}else if( DfdVarConst.ALARMTULE_TYPE_LIMIT == ruleType ){
					//门限值告警
					AlarmRuleLimitImp limtImp = new AlarmRuleLimitImp();
					limtImp.alartTrigger(ruleVo, targetVo);
					
				}else if( DfdVarConst.ALARMTULE_TYPE_FLOATPOINT == ruleType ){
					//飘点告警
					AlarmRuleFloatingPointImp floatImp = new AlarmRuleFloatingPointImp();
					floatImp.alartTrigger(ruleVo, targetVo);
				}else if( DfdVarConst.ALARMTULE_TYPE_TOGGLE == ruleType ){
					//开关告警
					AlarmRuleToggleImp toggleImp = new AlarmRuleToggleImp();
					toggleImp.alartTrigger(ruleVo, targetVo);
				}
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("告警触发异常! " + e.getMessage());
				//TODO 这里就该记录告警触发的信息到一个单独的日志表中
				
			}finally{
				dmo.releaseContext(ApmsConst.DS_APMS);
			}
			
		}//end for
		
	}
	
	
	
}
