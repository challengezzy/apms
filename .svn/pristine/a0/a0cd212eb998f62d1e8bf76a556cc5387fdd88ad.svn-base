package com.apms.bs.dataprase.vo.a25a26a27;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsR1Vo_A26_A320 extends AcarsLineDataVo{
	
	private String reason; 

	
	public AcarsR1Vo_A26_A320(String str) throws Exception{
		originalStr = str;
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);
		
		if (columns[0].contains("   ")){
			reason = null;
		}else if (columns[1].contains("   ")){
			reason = columns[0];
		}else if (columns[2].contains("   ")){
			reason = columns[0]+" "+columns[1];
		}else {
			reason = columns[0]+" "+columns[1]+" "+columns[2];
		}
	
	}

	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
