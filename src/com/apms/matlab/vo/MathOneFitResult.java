package com.apms.matlab.vo;

public class MathOneFitResult {
	
	//一元回归数据计算，返回y=kx+b, 系统k,b
	
	private double k; //一次项系数k
	
	private double b; //常数项系数k
	
	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

}
