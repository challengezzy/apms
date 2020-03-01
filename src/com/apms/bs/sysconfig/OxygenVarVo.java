package com.apms.bs.sysconfig;

import org.simpleframework.xml.Element;

/**
 * 氧气相关设置变量
 * @author jerry
 * @date Mar 26, 2013
 */
public class OxygenVarVo {
	
	@Element
	private int decompress_threshold_lower = -150;//泄压门限下限
	
	@Element
	private int decompress_threshold_upper = 150;//泄压门限上限	
	
	@Element
	private int oxyPressLower = 1600;//氧气瓶压力下限
	
	
	public int getDecompress_threshold_lower() {
		return decompress_threshold_lower;
	}
	public void setDecompress_threshold_lower(int decompress_threshold_lower) {
		this.decompress_threshold_lower = decompress_threshold_lower;
	}
	public int getDecompress_threshold_upper() {
		return decompress_threshold_upper;
	}
	public void setDecompress_threshold_upper(int decompress_threshold_upper) {
		this.decompress_threshold_upper = decompress_threshold_upper;
	}
	public int getOxyPressLower() {
		return oxyPressLower;
	}
	public void setOxyPressLower(int oxyPressLower) {
		this.oxyPressLower = oxyPressLower;
	}
	
	

}
