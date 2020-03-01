package com.apms.bs.vibration;

import com.apms.matlab.vo.FftCountResult;
import com.apms.matlab.vo.Spwvd_FigureResult;

public class VibrationFigureVo {
	
	private Spwvd_FigureResult spwvdFigure;
	
	private FftCountResult fftResult;
	
	private String resultDesc;
	
	private boolean spwvdOk = false; //计算是否成功

	public Spwvd_FigureResult getSpwvdFigure() {
		return spwvdFigure;
	}

	public void setSpwvdFigure(Spwvd_FigureResult spwvdFigure) {
		this.spwvdFigure = spwvdFigure;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public boolean isSpwvdOk() {
		return spwvdOk;
	}

	public void setSpwvdOk(boolean spwvdOk) {
		this.spwvdOk = spwvdOk;
	}

	public FftCountResult getFftResult() {
		return fftResult;
	}

	public void setFftResult(FftCountResult fftResult) {
		this.fftResult = fftResult;
	}

}
