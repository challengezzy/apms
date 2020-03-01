package com.apms.matlab.vo;


/**
 * 0,1规划函数的返回结果数据
 * @author jerry
 * @date Jul 1, 2013
 */
public class BintProgResult {
	
	/**
	 * 规划解的X矩阵
	 */
	private double[] x;
	
	/**
	 * 处理结果返回参数 
	 * 1 找到求解.
	 * 0 超过迭代次数，请设定OPTIONS参数
	 * -2 问题无解
	 * -4  搜索节点超过限制数量，请设定OPTIONS参数.
	 * -5 搜索超过限制时间，请设定OPTIONS参数.
	 * -6 在一个节点进行迭代求解线性规划松弛问题的规划求解超过限制，请设定OPTIONS参数.
	 */
	private int exitFlag;//处理结果返回参数 
	
	private String output;//包含的优化信息结构
	
	private double fval; //规则求解的值

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public int getExitFlag() {
		return exitFlag;
	}

	public void setExitFlag(int exitFlag) {
		this.exitFlag = exitFlag;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public double getFval() {
		return fval;
	}

	public void setFval(double fval) {
		this.fval = fval;
	}
}
