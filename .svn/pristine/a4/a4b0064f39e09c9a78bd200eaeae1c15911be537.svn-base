package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A23ComputeVo;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

/**
 * 本报文掉压率飘点告警,连续出现3次时，把这3个飘点改为实点, 发告警
 * @author jerry
 * @date Mar 18, 2013
 */
public class AlarmImplA23_03 extends AlarmRuleImplBaseClass{
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		//找到告警消息模板
		A23ComputeVo computeVo = (A23ComputeVo)targetVo.getParam("COMPUTEDVO"); 
		
		boolean isAlarm = false;
		int alarmLevel = 0;
		String limit_Value="";
		computeVo.getDeta_pres_fwd();
		computeVo.getPres_mid_s46();
		computeVo.getPres_st_c15_s46();
		double k=computeVo.getPres_st_s46_k();
		k=k*(1000*3600*24);
		//连续3个飘点，进行飘点告警,且S46K值小于-10

		if ((computeVo.getDeta_presrate_st_pointtype() == DfdVarConst.POINTTYPE_FLOAT
				&& computeVo.getDeta_presrate_st_pointtype_1() == DfdVarConst.POINTTYPE_FLOAT
				&& computeVo.getDeta_presrate_st_pointtype_2() == DfdVarConst.POINTTYPE_FLOAT && k <= -25)) {
			limit_Value = "3POINT,-25";
			alarmLevel = 2;
			isAlarm = true;
		} else if (k < -15 && k > -30) {
			limit_Value = "-30,-15";
			alarmLevel = 2;
			isAlarm = true;
		} else if (k <= -30) {
			limit_Value = "-*,-30";
			alarmLevel = 1;
			isAlarm = true;
		}

		if (isAlarm) {
			// {TIME}
			// 飞机{ACNUM}氧气瓶本报文掉压率为[DETA_PRESRATE],N点平均为[DETA_PRESRATE_AVG]判定为飘点,已连续出现3个飘点！
			// 对消息内容 变量进行替换
			String acnum = computeVo.getAcnum();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("DETA_PRESRATE", computeVo.getDeta_presrate_st() + "");
			paramMap.put("DETA_PRESRATE_AVG",computeVo.getDeta_presrate_st_avg() + "");
			paramMap.put("LIMIT_VALUE", limit_Value);
			paramMap.put("K_VALUE", k+"");
			paramMap.put("TIME", DateUtil.getLongDate(computeVo.getDate_utc()));
			paramMap.put("ALARM_LEVEL", alarmLevel+"");

			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
//			String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,null,alarmLevel);

		}

	}
		
		
		


}
