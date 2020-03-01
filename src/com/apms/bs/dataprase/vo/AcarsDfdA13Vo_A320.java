package com.apms.bs.dataprase.vo;

import com.apms.bs.dataprase.vo.a13a14.AcarsE1Vo_A13_A320;
import com.apms.bs.dataprase.vo.a13a14.AcarsNxVo_A13_A320;
import com.apms.bs.dataprase.vo.a13a14.AcarsSxVo_A13_A320;
import com.apms.bs.dataprase.vo.a13a14.AcarsV1Vo_A13_A320;

public class AcarsDfdA13Vo_A320 {
	
	private AcarsE1Vo_A13_A320 e1;
	private AcarsNxVo_A13_A320 n1;
	private AcarsNxVo_A13_A320 n2;
	private AcarsNxVo_A13_A320 n3;
	
	private AcarsSxVo_A13_A320 s1;
	private AcarsSxVo_A13_A320 s2;
	private AcarsSxVo_A13_A320 s3;
	
	private AcarsV1Vo_A13_A320 v1;
	private AcarsAcwVo acw;
	
	private int ahrs_inc=0;//小时增量
	private int acyc_inc=0;//循环增量
	
	
	public AcarsE1Vo_A13_A320 getE1() {
		return e1;
	}
	public void setE1(AcarsE1Vo_A13_A320 e1) {
		this.e1 = e1;
	}
	public AcarsNxVo_A13_A320 getN1() {
		return n1;
	}
	public void setN1(AcarsNxVo_A13_A320 n1) {
		this.n1 = n1;
	}
	public AcarsNxVo_A13_A320 getN2() {
		return n2;
	}
	public void setN2(AcarsNxVo_A13_A320 n2) {
		this.n2 = n2;
	}
	public AcarsNxVo_A13_A320 getN3() {
		return n3;
	}
	public void setN3(AcarsNxVo_A13_A320 n3) {
		this.n3 = n3;
	}
	public AcarsSxVo_A13_A320 getS1() {
		return s1;
	}
	public void setS1(AcarsSxVo_A13_A320 s1) {
		this.s1 = s1;
	}
	public AcarsSxVo_A13_A320 getS2() {
		return s2;
	}
	public void setS2(AcarsSxVo_A13_A320 s2) {
		this.s2 = s2;
	}
	public AcarsSxVo_A13_A320 getS3() {
		return s3;
	}
	public void setS3(AcarsSxVo_A13_A320 s3) {
		this.s3 = s3;
	}
	public AcarsV1Vo_A13_A320 getV1() {
		return v1;
	}
	public void setV1(AcarsV1Vo_A13_A320 v1) {
		this.v1 = v1;
	}
	public AcarsAcwVo getAcw() {
		return acw;
	}
	public void setAcw(AcarsAcwVo acw) {
		this.acw = acw;
	}
	public int getAhrs_inc() {
		return ahrs_inc;
	}
	public void setAhrs_inc(int ahrs_inc) {
		this.ahrs_inc = ahrs_inc;
	}
	public int getAcyc_inc() {
		return acyc_inc;
	}
	public void setAcyc_inc(int acyc_inc) {
		this.acyc_inc = acyc_inc;
	}
	
}
