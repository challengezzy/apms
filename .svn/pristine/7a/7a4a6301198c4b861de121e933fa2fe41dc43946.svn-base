package com.apms.bs.alarm.rule.vo;

import java.util.ArrayList;
import java.util.HashMap;

import com.apms.bs.dfd.DfdVarConst;

/**
 * 告警规则配置对象,表示告警规则配置信息
 * @author jerry
 * @date Mar 19, 2013
 */
public class AlarmRuleVo {
		
	private String moid; //告警监控对象ID
	private String mocode; //监控对象code
	private String moname;//监控对象名称
	private String dataviewName; //监控数据对象对应的表或视图名
	private String dataviewPk; //数据主键列
	
	private String ruleId; //规则编码
	private String ruleCode; //规则编码
	private int ruleType; //规则类型 自定义实现类或 通用计算
	private String ruleImplClass; //规则实现类路径
	
	private String val_column;//判定字段
	private String val_column_name;//判定字段名称
	
	private String msgTemplateId;//消息模板ID
	private String msgSubject;//消息模板主题
	private String msgContent;//消息模板内容
	private String msgType;//消息类型
	private String smContent;//短信内容模板
	
	private int severity;
	
	private ArrayList<AlarmRuleParamVo> paramVoList = new ArrayList<AlarmRuleParamVo>();
	
	private ArrayList<String> paramKeyList = new ArrayList<String>();
	
	private HashMap<String, AlarmRuleParamVo> paramMap = new HashMap<String, AlarmRuleParamVo>();
	
	/**
	 * 根据参数key获取param_value的值,返回Object对象，实际上大部分均为string，兼容需要其它对象的情况
	 * @param paramKey
	 * @param targetMap
	 * @return
	 */
	public Object getParamValue( String paramKey, HashMap<String, Object> targetMap){
		AlarmRuleParamVo paramvo = paramMap.get(paramKey);
		if(paramvo == null ){
			return null; //未找到参数对象
		}
		
		if(paramvo.getValuetype() == DfdVarConst.ALARMTULEPARAM_TYPE_CONST){
			//常量值
			return (Object)paramvo.getParam_const_value();
			
		}else if(paramvo.getValuetype() == DfdVarConst.ALARMTULEPARAM_TYPE_TARGETVO){
			//值取自目标对象
			
			String attr_targetvo = paramvo.getAttr_targetvo();
			Object value = targetMap.get(attr_targetvo);
			
			return value;
			
		}else if(paramvo.getValuetype() == DfdVarConst.ALARMTULEPARAM_TYPE_FUN){
			return null;//函数暂时不支持
		}
		
		return null;
	}
	
	/**
	 * 得到该规则的所有参数值
	 * @param targetMap
	 * @return
	 */
	public HashMap<String, String> getParamValueMap(HashMap<String, Object> targetMap){
		HashMap<String, String> pvalueMap = new HashMap<String, String>();
		for(int i=0;i<paramKeyList.size();i++){
			String paramcode = paramKeyList.get(i);
			Object valueObj = getParamValue(paramcode, targetMap);
			String value = null;
			if(valueObj != null){ //将值对象转换为字符串形式
				value = valueObj.toString();
			}
			
			pvalueMap.put(paramcode, value);
		}
		
		return pvalueMap;
	}
	
	public void setParamVoList(ArrayList<AlarmRuleParamVo> paramVoList) {
		this.paramVoList = paramVoList;
		
		//设置key list
		for(int i=0;i<paramVoList.size(); i++){
			String paramcode = paramVoList.get(i).getParam_code();
			paramKeyList.add(paramcode);
			paramMap.put(paramcode, paramVoList.get(i));
		}
	}
	
	
	
	public String getVal_column() {
		return val_column;
	}

	public void setVal_column(String val_column) {
		this.val_column = val_column;
	}

	public String getVal_column_name() {
		return val_column_name;
	}

	public void setVal_column_name(String val_column_name) {
		this.val_column_name = val_column_name;
	}

	public ArrayList<String> getParamKeyList() {
		return paramKeyList;
	}
	public ArrayList<AlarmRuleParamVo> getParamVoList() {
		return paramVoList;
	}
	
	public int getSeverity() {
		return severity;
	}
	
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public String getMoid() {
		return moid;
	}
	public void setMoid(String moid) {
		this.moid = moid;
	}
	public String getDataviewName() {
		return dataviewName;
	}
	public void setDataviewName(String dataviewName) {
		this.dataviewName = dataviewName;
	}
	public String getDataviewPk() {
		return dataviewPk;
	}
	public void setDataviewPk(String dataviewPk) {
		this.dataviewPk = dataviewPk;
	}
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public int getRuleType() {
		return ruleType;
	}
	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}
	public String getRuleImplClass() {
		return ruleImplClass;
	}
	public void setRuleImplClass(String ruleImplClass) {
		this.ruleImplClass = ruleImplClass;
	}
	public String getMsgTemplateId() {
		return msgTemplateId;
	}
	public void setMsgTemplateId(String msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	} 
	
	public String getMsgSubject() {
		return msgSubject;
	}
	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSmContent() {
		return smContent;
	}
	public void setSmContent(String smContent) {
		this.smContent = smContent;
	}
	public String getMocode() {
		return mocode;
	}
	public void setMocode(String mocode) {
		this.mocode = mocode;
	}

	public void setMoname(String moname) {
		this.moname = moname;
	}

	public String getMoname() {
		return moname;
	}
	
}
