package com.apms.bs.dataprase.vo;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportParseUtil;

public class AcarsAcwVo {

	// public AcarsE1Vo_A14_A320 acw;
	//
	// public String a=acw.getAcw3();
	
	private String acwName;
	
	private String acwVal;//ACW值

	private String zero20 = "00000000000000000000";
	
	private String bit1;
	private String bit2;
	private String bit3;
	private String bit4;
	private String bit5;
	private String bit6;
	private String bit7;
	private String bit8;
	private String bit9;
	private String bit10;
	private String bit11;
	private String bit12;
	private String bit13;
	private String bit14;
	private String bit15;
	private String bit16;
	private String bit17;
	private String bit18;
	private String bit19;
	private String bit20;
	
	

	public AcarsAcwVo(String acwName,String acwx) {
		this.acwName = acwName;
		acwVal = acwx;
		
		acwx = ReportParseUtil.getNumberStr(acwx);//做数据解析，包含 --,xx的情况改为00
		int temp = Integer.parseInt(acwx, 16);
		//有-1的情况 ，必须转换为0后再进行下一步操作 zhangzy 20170506
		if(temp < 0){
			temp = 0;
		}
		
		String temp2 = Integer.toBinaryString(temp);
		int l = 20 - temp2.length();
		
		String bitStr = zero20.substring(0, l) + temp2;
		
		bit1 = bitStr.substring(19, 20); 
		bit2 = bitStr.substring(18, 19);
		bit3 = bitStr.substring(17, 18);
		bit4 = bitStr.substring(16, 17);
		bit5 = bitStr.substring(15, 16);
		bit6 = bitStr.substring(14, 15);
		bit7 = bitStr.substring(13, 14);
		bit8 = bitStr.substring(12, 13);
		bit9 = bitStr.substring(11, 12);
		bit10 = bitStr.substring(10, 11);
		bit11 = bitStr.substring(9, 10);
		bit12 = bitStr.substring(8, 9);
		bit13 = bitStr.substring(7, 8);
		bit14 = bitStr.substring(6, 7);
		bit15 = bitStr.substring(5, 6);
		bit16 = bitStr.substring(4, 5);
		bit17 = bitStr.substring(3, 4);
		bit18 = bitStr.substring(2, 3);
		bit19 = bitStr.substring(1, 2);
		bit20 = bitStr.substring(0, 1);
	}

	public String getAcwVal() {
		return acwVal;
	}

	public void setAcwVal(String acwVal) {
		this.acwVal = acwVal;
	}

	public String getAcwName() {
		return acwName;
	}
	public void setAcwName(String acwName) {
		this.acwName = acwName;
	}
	public String getZero20() {
		return zero20;
	}
	public void setZero20(String zero20) {
		this.zero20 = zero20;
	}
	public String getBit1() {
		return bit1;
	}
	public void setBit1(String bit1) {
		this.bit1 = bit1;
	}
	public String getBit2() {
		return bit2;
	}
	public void setBit2(String bit2) {
		this.bit2 = bit2;
	}
	public String getBit3() {
		return bit3;
	}
	public void setBit3(String bit3) {
		this.bit3 = bit3;
	}
	public String getBit4() {
		return bit4;
	}
	public void setBit4(String bit4) {
		this.bit4 = bit4;
	}
	public String getBit5() {
		return bit5;
	}
	public void setBit5(String bit5) {
		this.bit5 = bit5;
	}
	public String getBit6() {
		return bit6;
	}
	public void setBit6(String bit6) {
		this.bit6 = bit6;
	}
	public String getBit7() {
		return bit7;
	}
	public void setBit7(String bit7) {
		this.bit7 = bit7;
	}
	public String getBit8() {
		return bit8;
	}
	public void setBit8(String bit8) {
		this.bit8 = bit8;
	}
	public String getBit9() {
		return bit9;
	}
	public void setBit9(String bit9) {
		this.bit9 = bit9;
	}
	public String getBit10() {
		return bit10;
	}
	public void setBit10(String bit10) {
		this.bit10 = bit10;
	}
	public String getBit11() {
		return bit11;
	}
	public void setBit11(String bit11) {
		this.bit11 = bit11;
	}
	public String getBit12() {
		return bit12;
	}
	public void setBit12(String bit12) {
		this.bit12 = bit12;
	}
	public String getBit13() {
		return bit13;
	}
	public void setBit13(String bit13) {
		this.bit13 = bit13;
	}
	public String getBit14() {
		return bit14;
	}



	public void setBit14(String bit14) {
		this.bit14 = bit14;
	}



	public String getBit15() {
		return bit15;
	}



	public void setBit15(String bit15) {
		this.bit15 = bit15;
	}



	public String getBit16() {
		return bit16;
	}



	public void setBit16(String bit16) {
		this.bit16 = bit16;
	}



	public String getBit17() {
		return bit17;
	}



	public void setBit17(String bit17) {
		this.bit17 = bit17;
	}



	public String getBit18() {
		return bit18;
	}



	public void setBit18(String bit18) {
		this.bit18 = bit18;
	}



	public String getBit19() {
		return bit19;
	}



	public void setBit19(String bit19) {
		this.bit19 = bit19;
	}



	public String getBit20() {
		return bit20;
	}



	public void setBit20(String bit20) {
		this.bit20 = bit20;
	}
	
	
	private String insertSql_Acw = "insert into a_dfd_acw_decode(ID,MSG_NO,ACMODEL,ACNUM,RECDATETIME,ACWX,RPTNO,ROWTITLE," 
			+ "BIT1,BIT2,BIT3,BIT4,BIT5,BIT6,BIT7,BIT8,BIT9,BIT10,"
			+ "BIT11,BIT12,BIT13,BIT14,BIT15,BIT16,BIT17,BIT18,BIT19,BIT20,CWVALUE)" 
			+ " values(S_A_DFD_HEAD.nextval,?,?,?,sysdate,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * 
	 * @param msgno 
	 * @param acnum 飞机号
	 * @param acmodel 飞机型号
	 * @param rptno 报文编号
	 * @param rowtitle 行头
	 * @throws Exception
	 */
	public void insertToTable(String msgno,String acnum,String acmodel,String rptno,String rowtitle) throws Exception{
		
		CommDMO dmo = new CommDMO();
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql_Acw, 
				msgno,acmodel,acnum,acwName,rptno,rowtitle,
				bit1,bit2,bit3,bit4,bit5,bit6,bit7,bit8,bit9,bit10,
				bit11,bit12,bit13,bit14,bit15,bit16,bit17,bit18,bit19,bit20,acwVal);
		
	}

}
