package com.apms.matlab.vo;

import com.mathworks.toolbox.javabuilder.webfigures.WebFigure;

/**
 * 小波分析结果
 * @author jerry
 * @date Apr 26, 2013
 */
public class Spwvd_FigureResult {
	
	private double[][] wvd = new double[0][];//分析结果
	
	private WebFigure figure;//抖动图形
	
	private double[] time = new double[0]; //时间轴数据
	
	private double[] freq = new double[0] ;//频率轴数据
	
	private double[] energyPeak = new double[0] ;//每个频率下的能量

	public double[][] getWvd() {
		return wvd;
	}

	public void setWvd(double[][] wvd) {
		this.wvd = wvd;
	}

	public WebFigure getFigure() {
		return figure;
	}

	public void setFigure(WebFigure figure) {
		this.figure = figure;
	}

	public double[] getTime() {
		return time;
	}

	public void setTime(Double[] t) {
		time = new double[t.length];
		for(int i=0;i<time.length;i++){
			time[i] = t[i];
		}
		
	}

	public double[] getFreq() {
		return freq;
	}

	public void setFreq(Double[] f) {
		freq = new double[f.length];
		for(int i=0;i<time.length;i++){
			freq[i] = f[i];
		}
	}

	public double[] getEnergyPeak() {
		return energyPeak;
	}

	public void setEnergyPeak(double[] energyPeak) {
		this.energyPeak = energyPeak;
	}

	public void setTime(double[] time) {
		this.time = time;
	}

	public void setFreq(double[] freq) {
		this.freq = freq;
	}
	
}
