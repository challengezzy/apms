package com.apms.bs.dataprase.vo;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

public class AcarsLineDataVo {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	protected String originalStr;//原始报文，如CE0055,-0379,---,---,6119,272,I71CA2
	
	protected void logOriginalStr(){
		logger.debug("linedata: " + originalStr);
	}
	
	/**
	 * 检验本行数据字段的个数是否符合预期,多余的也支持，有最后一个字段为","的情况兼容
	 * @throws Exception
	 */
	protected void checkFieldsNum(String originalStr,String[] columns,int filedsNum) throws Exception{
		if(columns.length < filedsNum){
			throw new Exception("数据行"+originalStr+ "，应该有"+filedsNum+"个字段，解析出"+columns.length+"个字段。");
		}
	}

}
