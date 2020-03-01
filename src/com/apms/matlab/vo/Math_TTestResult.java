package com.apms.matlab.vo;

public class Math_TTestResult {
	
	private int diff; //Diff=1 两组数据不一致, diff=0 两组数据一致
	private double significance; //显著性 一般与alpha比较，如果小于alpha那么 这个Diff 结果可信
	
	private String ci; //置信区间，数值在这个范围是可信的

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

	public double getSignificance() {
		return significance;
	}

	public void setSignificance(double significance) {
		this.significance = significance;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

}
