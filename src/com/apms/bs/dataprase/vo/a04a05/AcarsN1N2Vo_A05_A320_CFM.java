package com.apms.bs.dataprase.vo.a04a05;

import com.apms.bs.dataprase.vo.AcarsLineDataVo;
import com.apms.bs.util.StringUtil;

public class AcarsN1N2Vo_A05_A320_CFM extends AcarsLineDataVo{
	
	private String ecw2;
	private String ecw5;
	private String evm;

	
	public AcarsN1N2Vo_A05_A320_CFM(String str) throws Exception{
		originalStr = str;
		
		String tempStr = str.substring(2);
		String[] columns = StringUtil.splitString2Array(tempStr, ",", true); 
		checkFieldsNum(originalStr,columns, 3);

		ecw2 = columns[0];
		ecw5 = columns[1];
		evm = columns[2];

	
	}
	
	public String getEcw2() {
		return ecw2;
	}
	public void setEcw2(String ecw2) {
		this.ecw2 = ecw2;
	}
	public String getEcw5() {
		return ecw5;
	}
	public void setEcw5(String ecw5) {
		this.ecw5 = ecw5;
	}
	public String getEvm() {
		return evm;
	}
	public void setEvm(String evm) {
		this.evm = evm;
	}

	

}
