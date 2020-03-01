package com.apms.bs.alarm;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.alarm.rule.vo.AlarmRuleParamVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 告警配置服务类，缓存告警对象、告警规则及消息派发
 * @author jerry
 * @date Nov 3, 2014
 */
public class AlarmConfigService {
	
	private CommDMO dmo = new CommDMO();
	private Logger logger = NovaLogger.getLogger(this);
	
	/** 告警规则缓存列表 rulecode, rule对象 */
	private Map<String, AlarmRuleVo> ruleMap = new ConcurrentHashMap<String, AlarmRuleVo>();
	
	/** 监控对象和该对象的规则列表 MonitorObject.code, 监控对象列表 */
	private Map<String, ArrayList<AlarmRuleVo>> moRulesMap = new ConcurrentHashMap<String, ArrayList<AlarmRuleVo>>();
	
	private static AlarmConfigService alarmConfigService;
	
	private AlarmConfigService(){
		
	}
	
	public static AlarmConfigService getInstance(){
		if(alarmConfigService != null)
			return alarmConfigService;
		else
			alarmConfigService = new AlarmConfigService();
		
		return alarmConfigService;
	}
	
	/**
	 * 获取监控对象的所有告警规则
	 * @param monitorObjCode
	 * @return
	 * @throws Exception
	 */
	public ArrayList<AlarmRuleVo> getRuleVos(String monitorObjCode) throws Exception{
		if( moRulesMap.size() < 1 ){
			initMonitorRules();
		}
		
		return moRulesMap.get(monitorObjCode);
	}
	
	/**
	 * 查询告警监控规则的基本SQL
	 * @return
	 */
	private String getRuleVoBasicSql(){
		StringBuilder sqlBuilder = new StringBuilder();		
		sqlBuilder.append("SELECT R.ID RULEID,MO.ID MOID,MO.CODE MOCODE,MO.NAME MONAME,MO.DATAVIEWNAME,MO.DATAVIEWPK,R.CODE RULECODE,");
		sqlBuilder.append(" R.RULETYPE,R.RULEIMPCLASS,R.MSGTEMPLATEID,R.SEVERITY,");
		sqlBuilder.append(" R.VAL_COLUMN,R.VAL_COLUMN_NAME,");
		sqlBuilder.append(" MT.CODE MSGCODE,MT.NAME MSGNAME,MT.SUBJECT MSGSUBJECT,MT.CONTENT MSGCONTENT,");
		sqlBuilder.append(" MT.SMCONTENT,MT.TYPE MSGTYPE");
		sqlBuilder.append(" FROM ALARM_MONITOROBJECT MO,ALARM_RULE R,ALARM_MSGTEMPLATE MT");
		sqlBuilder.append(" WHERE R.MONITOROBJECTID=MO.ID AND MT.ID=R.MSGTEMPLATEID ");
		sqlBuilder.append(" AND R.STATUS=1 ");//激活状态
		
		return sqlBuilder.toString();
	}
	
	private AlarmRuleVo getRuleByHashVo(HashVO ruleVo) throws Exception{
		String mocode = ruleVo.getStringValue("MOCODE");
		String moname = ruleVo.getStringValue("MONAME");
		String rulecode = ruleVo.getStringValue("RULECODE");
		String impClass = ruleVo.getStringValue("RULEIMPCLASS");
		String dataPkField = ruleVo.getStringValue("DATAVIEWPK");
		String dataView = ruleVo.getStringValue("DATAVIEWNAME");
		
		AlarmRuleVo rule = new AlarmRuleVo();
		rule.setMoid(ruleVo.getStringValue("MOID"));
		rule.setMocode(mocode);
		rule.setDataviewName(dataView);
		rule.setDataviewPk(dataPkField);
		rule.setRuleId(ruleVo.getStringValue("RULEID"));
		rule.setRuleCode(rulecode);
		rule.setRuleType(ruleVo.getIntegerValue("RULETYPE"));
		rule.setRuleImplClass(impClass);
		rule.setMsgTemplateId(ruleVo.getStringValue("MSGTEMPLATEID"));
		rule.setMsgSubject(ruleVo.getStringValue("MSGSUBJECT"));
		rule.setMsgContent(ruleVo.getStringValue("MSGCONTENT"));
		rule.setSeverity(ruleVo.getIntegerValue("SEVERITY"));
		rule.setMsgType(ruleVo.getStringValue("MSGTYPE"));
		rule.setSmContent(ruleVo.getStringValue("SMCONTENT"));
		rule.setMoname(moname);
		rule.setVal_column(ruleVo.getStringValue("VAL_COLUMN"));
		rule.setVal_column_name(ruleVo.getStringValue("VAL_COLUMN_NAME"));
		
		//zhangzy 添加规则参数
		String sql = "SELECT ID,RULEID,PARAM_CODE,PARAM_NAME,VALUETYPE,ATTR_TARGETVO,FUN_DEFINE,PARAM_CONST_VALUE" +
				" FROM ALARM_RULE_PARAM T WHERE RULEID="+ rule.getRuleId();
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		
		ArrayList<AlarmRuleParamVo> paramVoList = new ArrayList<AlarmRuleParamVo>();
		for(int i=0;i<vos.length;i++){
			HashVO paramvo = vos[i];
			AlarmRuleParamVo param = new AlarmRuleParamVo();
			param.setId(paramvo.getStringValue("ID"));
			param.setRuleid(paramvo.getStringValue("RULEID"));
			param.setParam_code(paramvo.getStringValue("PARAM_CODE"));
			param.setParam_name(paramvo.getStringValue("PARAM_NAME"));
			param.setValuetype(paramvo.getIntegerValue("VALUETYPE"));
			param.setAttr_targetvo(paramvo.getStringValue("ATTR_TARGETVO"));
			param.setFun_define(paramvo.getStringValue("FUN_DEFINE"));
			param.setParam_const_value(paramvo.getStringValue("PARAM_CONST_VALUE"));
			
			paramVoList.add(param);
		}
		
		rule.setParamVoList(paramVoList);
		
		return rule;
	}
	
	/**
	 * 根据查询到的消息对象刷新规则缓存
	 * @param vos
	 */
	private void refreshMoRuleMapByRules(HashVO[] vos) throws Exception{
		for(int i=0;i<vos.length;i++){
			HashVO ruleVo = vos[i];
			
			AlarmRuleVo rule = getRuleByHashVo(ruleVo);
			
			ruleMap.put(rule.getRuleCode(), rule);
			
			//刷新morule缓存 
			refreshMoRuleMapByRule(rule);
		}
	}
	
	private void refreshMoRuleMapByRule(AlarmRuleVo rule){
		//监控对象的规则为空，则表示未进行初始化
		ArrayList<AlarmRuleVo> rules = moRulesMap.get(rule.getMocode());
		if(rules == null){
			rules = new ArrayList<AlarmRuleVo>();
		}
		//判断如果不存在该规则，则添加，如果存在，删除后添加
		for(int i=0;i<rules.size();i++){
			AlarmRuleVo tmpvo = rules.get(i);
			if( tmpvo.getRuleId().equals( rule.getRuleId() ) ){
				//找到原对象则删除，未找到不执行
				rules.remove(i);
				break;
			}
		}
		//添加新对象
		rules.add(rule);
		
		moRulesMap.put(rule.getMocode(), rules);
		
		logger.debug("更新监控对象[" + rule.getMocode() + "]的告警规则[" + rule.getRuleCode() + "]成功!");
		
	}
	
	/**
	 * 初始化所有告警对象及规则到缓存表
	 * @param monitorObjCode
	 * @throws Exception
	 */
	public void initMonitorRules() throws Exception{
		logger.debug("开始初始化所有告警对象及规则到缓存表...");
		
		String sql = getRuleVoBasicSql();
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS,sql.toString());		
		refreshMoRuleMapByRules(vos);
		logger.debug("初始化所有告警对象及规则到缓存表成功！");
		
	}
	
	/**
	 * 根据监控对象，刷新整个监控对象的缓存
	 * @param mocode
	 */
	public void refreshMoRuleMapByMo(String mocode) throws Exception{
		logger.debug("开始刷新告警对象["+mocode+"]及规则到缓存表...");
		
		String sql = getRuleVoBasicSql()+ " AND MO.CODE = ? ";
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS,sql.toString(),mocode);	
		
		//删除已有缓存
		moRulesMap.remove(mocode);
		refreshMoRuleMapByRules(vos);
		
		logger.debug("刷新告警对象["+mocode+"]及规则到缓存表成功！");
	}
	
	/**
	 * 根据告警规则ID刷新缓存
	 * @param msgTemlateCode
	 */
	public void refreshMoRuleMapByRuleId(String ruleid) throws Exception{
		String sql = getRuleVoBasicSql()+ " AND R.ID = ? ";
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS,sql.toString(),ruleid);	
		
		refreshMoRuleMapByRules(vos);
		
		logger.debug("刷新告警规则ID=["+ruleid+"]及对应规则到缓存表成功！");
		refreshMoRuleMapByRules(vos);
	}
	
	/**
	 * 根据消息模板刷新监控缓存，更新消息内容
	 * @param msgTemlateCode
	 */
	public void refreshMoRuleMapByMsg(String msgTemlateId) throws Exception{
		
		String sql = getRuleVoBasicSql()+ " AND MT.ID = ? ";
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS,sql.toString(),msgTemlateId);	
		
		refreshMoRuleMapByRules(vos);
		
		logger.debug("刷新消息模板ID["+msgTemlateId+"]及对应规则到缓存表成功！");
		refreshMoRuleMapByRules(vos);
	}
	
	
	public void deleteRuleCacheByRuleCode(String rulecode) throws Exception{
		//其实是根据数据库内容，刷新缓存
		AlarmRuleVo rule = ruleMap.get(rulecode);
		String mocode = rule.getMocode();
		
		refreshMoRuleMapByMo(mocode);
		ruleMap.remove(rulecode);
		
		logger.debug("删除告警规则["+rulecode+"]缓存成功！");
	}
	
}
