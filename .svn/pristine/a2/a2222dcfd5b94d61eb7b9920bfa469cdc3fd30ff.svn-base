package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.datacompute.vo.A13ComputeVo;
import com.apms.bs.datacompute.vo.A23ComputeVo;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.MathUtil;
import com.apms.bs.util.StringUtil;

/**
 * APU报文计算数据告警
 * @author jerry
 * @date Apr 10, 2013
 */

//转轴偏心，导致不平衡，STAV1中 参数GTCP131-9 V_STD大2.5 告警 APS3200 V_STD大于8.5告警 其它大于2
public class AlarmImplA13_02 extends AlarmRuleImplBaseClass{
	Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();	
	@Override
	

	public void alartTrigger(AlarmRuleVo ruleVo,AlarmMonitorTargetVo targetVo) throws Exception{
		A13ComputeVo computeVo = (A13ComputeVo)targetVo.getParam("COMPUTEDVO");	
		String asn = computeVo.getAsn();
		
		String submodel = null;

		StringBuilder sql = new StringBuilder(
				"SELECT A.APUSN, M.SUBMODEL, M.RL_IGV ");
		sql.append(" FROM B_APU A, B_APU_MODEL M WHERE A.APUMODELID = M.ID ");
		sql.append(" AND A.APUSN = '" + asn + "'");

		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS,
					sql.toString());
			submodel = vos[0].getStringValue("SUBMODEL");
		} catch (Exception e) {
			logger.error("A13报文计算时异常:" + e.getMessage());
			throw e;
		}
		double sta_std_limited = 0; 	
		if(submodel.equals("APS3200")){
		sta_std_limited = 8.5; 
		}else if(submodel.equals("GTCP131-9A")){
			sta_std_limited = 2.5; 	
		}else{
			sta_std_limited = 2;
		}
		double sta_limited=50;

		double v_std = computeVo.getFieldVo_sta().getV_std();
		double sta = computeVo.getFieldVo_sta().getF_value();
		int alarmLevel = 3;
		
		if (v_std > sta_std_limited && sta>sta_limited){      //告警条件
 
			String acnum = targetVo.getAcnum();

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("ACNUM", acnum);
			paramMap.put("APUMODEL", submodel+"");
			paramMap.put("TIME", DateUtil.getLongDate(targetVo.getDateUtc()));
			paramMap.put("ALARM_LEVEL", alarmLevel+"");
			paramMap.put("STA", sta+",离散度："+MathUtil.round(v_std, 3));
			paramMap.put("STA_LIMITED", sta_std_limited+"");
			paramMap.put("ASN", asn+"");


			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
//			String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());
			
			logger.info("告警消息为：【"+newStr+"】");
			
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,null,alarmLevel);
		}
	}

}
