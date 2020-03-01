package com.sep.service.quadrant;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;

public class QuadrantConfService {
	
	public QuadrantConfVo getQuadrant() throws Exception{
		CommDMO dmo = new CommDMO();
		String sql = "SELECT AXIS_X,AXIS_Y,CODE FROM U_QUADRANT_CONF WHERE CODE = 'JOBCARD_QUADRANT'";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		
		HashVO vo = vos[0];
		QuadrantConfVo confvo = new QuadrantConfVo();
		confvo.setAxisX(vo.getDoubleValue("AXIS_X"));
		confvo.setAxisY(vo.getDoubleValue("AXIS_Y"));
		
		return confvo;
	}
	
	/**
	 * 获取风险和频次所在的象限
	 * @param x
	 * @param y
	 * @param confvo
	 * @return
	 */
	public int getQuadrantValue(double x,double y,double axisX,double axisY){
		int qua = 0;
		
		if( x>=axisX && y>= axisY ){
			qua = 1;
		}else if( x<axisX && y>= axisY ){
			qua = 2;
		}else if( x<axisX && y< axisY ){
			qua = 3;
		}else if( x>=axisX && y< axisY ){
			qua = 4;
		}
		
		return qua;
	}

	
}
