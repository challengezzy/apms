package com.apms.bs.dataprase.vo.a36;

import com.apms.bs.dataprase.ReportParseUtil;
import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsS1Vo_A36 extends AcarsLineDataVo{
	
	private String originalStr;//原始报文，如E12779,09243,08598,20000
	
	private Float fused;
	private Float fused2;
	
	public AcarsS1Vo_A36(String str,boolean isReq) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		String[] columns;
		String tempStr = str.substring(2).trim();
		if(tempStr.endsWith("/")){
			tempStr=tempStr.substring(0, tempStr.length()-1);
			columns= StringUtil.splitString2Array(tempStr, ",", true); 
		}else{
			columns= StringUtil.splitString2Array(tempStr, " ", true); 
		}
		
		checkFieldsNum(originalStr,columns, 2);
		
		String str1 = columns[0].substring(0, columns[0].indexOf("(") );
		String str2 = columns[1].substring(0, columns[1].indexOf("(") );
		
		if( isReq ){//isReq新报文自带小数点
			try{
				fused = ReportParseUtil.strToFloat(str1, "FUSED");
			}catch (Exception e) {}
			
			try{
				fused2 = ReportParseUtil.strToFloat(str2, "FUSED2");
			}catch (Exception e) {}
		}else{
			try {
				fused = ReportParseUtil.strToFloatWithDecimalPostion(str1, 2, "FUSED");
			} catch (Exception e) {
			}
			
			try {
				fused2 = ReportParseUtil.strToFloatWithDecimalPostion(str2, 2, "FUSED2");
			} catch (Exception e) {
			}
		}
		
	
	}

	public Float getFused() {
		return fused;
	}

	public Float getFused2() {
		return fused2;
	}
	
}
