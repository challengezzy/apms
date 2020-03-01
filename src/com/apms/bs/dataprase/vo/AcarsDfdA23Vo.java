package com.apms.bs.dataprase.vo;

import com.apms.bs.dataprase.vo.a23.AcarsSTempVo;

/**
 * 氧气报文解析对象
 * @author zzy
 *
 */
public class AcarsDfdA23Vo {
	
	private AcarsHeadVo head;	
	private AcarsSTempVo s1;
	private AcarsSTempVo s2;
	private AcarsSTempVo s3;
	private AcarsSTempVo s4;
	private AcarsSTempVo s5;
	private AcarsSTempVo s6;
	private AcarsSTempVo s7;
	
	public void correctS46Date() throws Exception{
		//如果S1时间 大于S5时间，则为跨天飞行，如 23:24:54 --》 2:24:50
		if(s7==null||s1==null||s1.getDateUtc()==null||s7.getDateUtc()==null){
			throw new Exception("原始报文不完整，缺少字段");
		}else if(s1.getDateUtc().getTime() > s7.getDateUtc().getTime()){
			s4.correctDateUtc();
			s5.correctDateUtc();
			s6.correctDateUtc();
			s7.correctDateUtc();
		}
		
		//修正S46的非数字字段为数字
		if(s7 != null){
			s4.correctNumberData(s7);
			s5.correctNumberData(s7);
			s6.correctNumberData(s7);
		}
			
	}
	

	public AcarsHeadVo getHead() {
		return head;
	}

	public void setHead(AcarsHeadVo head) {
		this.head = head;
	}

	public AcarsSTempVo getS1() {
		return s1;
	}
	public void setS1(AcarsSTempVo s1) {
		this.s1 = s1;
	}
	public AcarsSTempVo getS2() {
		return s2;
	}
	public void setS2(AcarsSTempVo s2) {
		this.s2 = s2;
	}
	public AcarsSTempVo getS3() {
		return s3;
	}
	public void setS3(AcarsSTempVo s3) {
		this.s3 = s3;
	}
	public AcarsSTempVo getS4() {
		return s4;
	}
	public void setS4(AcarsSTempVo s4) {
		this.s4 = s4;
	}
	public AcarsSTempVo getS5() {
		return s5;
	}
	public void setS5(AcarsSTempVo s5) {
		this.s5 = s5;
	}
	public AcarsSTempVo getS6() {
		return s6;
	}
	public void setS6(AcarsSTempVo s6) {
		this.s6 = s6;
	}
	public AcarsSTempVo getS7() {
		return s7;
	}
	public void setS7(AcarsSTempVo s7) {
		this.s7 = s7;
	}
	
}
