package com.apms.bs.oxygen.vo;

/**
 * 斜率对象
 * @author jerry
 * @date Mar 19, 2013
 */
public class A_DFD_RankPoint{
	
	private Double k;//斜率
	
	private Double b;//截距
	
	private Double x;//X值
	
	private Double y;//Y值
	
	private Double upValue;//上限值
	
	private Double downValue;//下限值

	public Double getK() {
		return k;
	}

	public void setK(Double k) {
		this.k = k;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getB() {
		return b;
	}

	public void setB(Double b) {
		this.b = b;
	}

	public Double getUpValue() {
		return upValue;
	}

	public void setUpValue(Double upValue) {
		this.upValue = upValue;
	}

	public Double getDownValue() {
		return downValue;
	}

	public void setDownValue(Double downValue) {
		this.downValue = downValue;
	}
	
}
