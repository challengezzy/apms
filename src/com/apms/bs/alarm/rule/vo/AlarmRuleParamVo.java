package com.apms.bs.alarm.rule.vo;

/**
 * 规则参数对象
 * @author jerry
 * @date Nov 20, 2014
 */
public class AlarmRuleParamVo {
	
	private String id;
	private String ruleid;//RULEID        NUMBER(18)    Y                规则适用对象          
	private String param_code;//PARAM_CODE    VARCHAR2(255)                  参数编码             
	private String param_name;//PARAM_NAME    VARCHAR2(255)                  参数含义
	private String param_const_value;//常量值
	private int valuetype;//VALUETYPE   NUMBER(2)  值类型0-常量 1-监控对象属性 2-函数
	private String attr_targetvo;//ATTR_TARGETVO VARCHAR2(255) 变量名称              
	private String fun_define;//FUN_DEFINE 函数定义    
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRuleid() {
		return ruleid;
	}
	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}
	public String getParam_code() {
		return param_code;
	}
	public void setParam_code(String param_code) {
		this.param_code = param_code;
	}
	public String getParam_name() {
		return param_name;
	}
	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}
	public int getValuetype() {
		return valuetype;
	}
	public void setValuetype(int valuetype) {
		this.valuetype = valuetype;
	}
	public String getAttr_targetvo() {
		return attr_targetvo;
	}
	public void setAttr_targetvo(String attr_targetvo) {
		this.attr_targetvo = attr_targetvo;
	}
	public String getFun_define() {
		return fun_define;
	}
	public void setFun_define(String fun_define) {
		this.fun_define = fun_define;
	}
	public String getParam_const_value() {
		return param_const_value;
	}
	public void setParam_const_value(String param_const_value) {
		this.param_const_value = param_const_value;
	}

}
