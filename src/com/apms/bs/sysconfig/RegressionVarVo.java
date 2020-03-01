package com.apms.bs.sysconfig;

import org.simpleframework.xml.Element;


/**
 * 回归计算相关变量设置
 * @author jerry
 * @date Mar 26, 2013
 */
public class RegressionVarVo {
	
	@Element
	private int numberOfPoints = 30;//计算报文时点个数
	
	@Element
	private int numberOfStdToAlarm = 2;//超过N个STD值时，进行告警
	
	@Element
	private Double alpha = 0.95;//样本T检查，置信区间
	
	@Element
	private int diffValuePercent = 50;//数据差值，允许范围

	public int getNumberOfPoints() {
		return numberOfPoints;
	}

	public void setNumberOfPoints(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints;
	}

	public int getNumberOfStdToAlarm() {
		return numberOfStdToAlarm;
	}

	public void setNumberOfStdToAlarm(int numberOfStdToAlarm) {
		this.numberOfStdToAlarm = numberOfStdToAlarm;
	}

	public Double getAlpha() {
		return alpha;
	}

	public void setAlpha(Double alpha) {
		this.alpha = alpha;
	}

	public int getDiffValuePercent() {
		return diffValuePercent;
	}

	public void setDiffValuePercent(int diffValuePercent) {
		this.diffValuePercent = diffValuePercent;
	}
	
}
