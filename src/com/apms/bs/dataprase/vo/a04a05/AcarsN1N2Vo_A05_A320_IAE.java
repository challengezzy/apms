package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsN1N2Vo_A05_A320_IAE extends AcarsLineDataVo{
	
	private String ecw3;
	private String ecw4;
	private String evm;

	
	public AcarsN1N2Vo_A05_A320_IAE(String str) throws Exception{
		originalStr = str;
		//logger.debug("ExNx content: " + originalStr);
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);

		ecw3 =columns[0];
		ecw4 = columns[1];
		evm = columns[2];
	}

	public String getEcw3() {
		return ecw3;
	}
	public void setEcw3(String ecw3) {
		this.ecw3 = ecw3;
	}
	public String getEcw4() {
		return ecw4;
	}
	public void setEcw4(String ecw4) {
		this.ecw4 = ecw4;
	}
	public String getEvm() {
		return evm;
	}
	public void setEvm(String evm) {
		this.evm = evm;
	}

	

}
