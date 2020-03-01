package com.apms.bs.dataprase.vo.a28a32;

import org.apache.log4j.Logger;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

import smartx.framework.common.vo.NovaLogger;

public class AcarsNxVo_A28A32_A320 extends AcarsLineDataVo{
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private String ir_z; 
	private String ir_y; 
	private String nvalue;
	
	public AcarsNxVo_A28A32_A320(String str, String nx) throws Exception{
		nvalue=nx;
		originalStr = str;
		logger.debug("EE content: " + originalStr);
		
		String tempStr1 = str.substring(2);
		String columns[];
		if (tempStr1.startsWith(",")){
			String tempStr= tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
			}
		else if (tempStr1.startsWith(" ")){
			String tempStr= tempStr1.substring(1);
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		} else{
			String tempStr= tempStr1;
			columns = StringUtil.splitString2Array(tempStr, ",", true);
		}
		checkFieldsNum(originalStr,columns, 2);
		if (columns[0].contains("---") ||  columns[0].contains("XXX")){
			ir_z = null;
		}else {
			ir_z = columns[0].substring(0,3)+"." + columns[0].substring(3,columns[0].length());
		}
		if (columns[1].contains("---") ||  columns[1].contains("XXX")){
			ir_y = null;
		}else {
			ir_y = columns[1].substring(0,3)+"." + columns[1].substring(3,columns[1].length());
		}


	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getOriginalStr() {
		return originalStr;
	}

	public void setOriginalStr(String originalStr) {
		this.originalStr = originalStr;
	}

	public String getIr_z() {
		return ir_z;
	}

	public void setIr_z(String ir_z) {
		this.ir_z = ir_z;
	}

	public String getIr_y() {
		return ir_y;
	}

	public void setIr_y(String ir_y) {
		this.ir_y = ir_y;
	}

	public String getNvalue() {
		return nvalue;
	}

	public void setNvalue(String nvalue) {
		this.nvalue = nvalue;
	}

}
