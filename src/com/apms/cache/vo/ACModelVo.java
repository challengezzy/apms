package com.apms.cache.vo;

/**
 * 飞机型号
 * @author jerry
 * @date Mar 22, 2014
 */
public class ACModelVo {
	
	private String id;           //id                
	private String modelcode;    //型号            
	private String modelseries;  //大型号系列           
	private int airframetype; //机体类型(宽/窄),0-窄 1-宽 
	private String commercecode; //商业型号          
	private String fdimusw_id;   //板卡软件版本
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModelcode() {
		return modelcode;
	}
	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}
	public String getModelseries() {
		return modelseries;
	}
	public void setModelseries(String modelseries) {
		this.modelseries = modelseries;
	}
	public int getAirframetype() {
		return airframetype;
	}
	public void setAirframetype(int airframetype) {
		this.airframetype = airframetype;
	}
	public String getCommercecode() {
		return commercecode;
	}
	public void setCommercecode(String commercecode) {
		this.commercecode = commercecode;
	}
	public String getFdimusw_id() {
		return fdimusw_id;
	}
	public void setFdimusw_id(String fdimusw_id) {
		this.fdimusw_id = fdimusw_id;
	}
	
}
