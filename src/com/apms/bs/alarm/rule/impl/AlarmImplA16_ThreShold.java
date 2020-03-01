package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dataprase.vo.AcarsDfdA15Vo_A320;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dataprase.vo.a15a16.AcarsST12Vo_A15_A320;
import com.apms.bs.dataprase.vo.a15a16.AcarsST34Vo_A15_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * 16号报废重着落告警
 * @author jerry
 * @date Apr 10, 2013
 */
public class AlarmImplA16_ThreShold extends AlarmRuleImplBaseClass{

	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception{
		AcarsHeadVo headVo =  (AcarsHeadVo)targetVo.getParam(DfdVarConst.KEY_HEADVO);
		AcarsDfdA15Vo_A320 a15vo = (AcarsDfdA15Vo_A320)targetVo.getParam(DfdVarConst.KEY_BODYVO);
		targetVo.setDevicesn(headVo.getEsn());
		
		AcarsST12Vo_A15_A320 s2 =  a15vo.getS2();
		AcarsST34Vo_A15_A320 s3 =  a15vo.getS3();
		
		//head value
		String acnum = headVo.getAcid();
		String rptDate= headVo.getDate_utc();
		String flightNoStr= headVo.getFlt();
		
		//s2
		double maxFlyWeight = getMaxFlyWeight(headVo.getAcid());
		Float gwNum= StringUtil.StringToFloat(headVo.getGw())*10;
//		Float raltNum= s2.getRalt();
		Float ralrNum= s2.getRalr();
//		Float ptchNum= s2.getPtch();
//		Float ptcrNum= s2.getPtcr();
//		Float rollNum= s2.getRoll();
//		Float rolrNum= s2.getRolr();
//		Float yawNum= s2.getYam();
	    //s3
		Float vrtaNum= s3.getVrta();
//		Float lonaNum= s3.getLona();
//		Float lataNum= s3.getLata();
		//告警内容
        String waringStr ="";
		boolean isAlarm = false;
		int alarmLevel = 0;
		String limit_value="";
		String act_Value="";
		
		
		//TODO 计算门限值,16号报文是超过门限值时，进行告警
		if(ApmsVarConst.RPTNO_A16.equals(targetVo.getRptno())){
			logger.debug("这里进行门限计算并告警");
			
			if (gwNum<=maxFlyWeight){
				//小于等于最大着落重量
				 if ((vrtaNum>=2.6 && vrtaNum<2.86)){
//				 if ((vrtaNum>=0.6 && vrtaNum<2.86) || (Math.abs(ralrNum)<14 && Math.abs(ralrNum)>=10)){
					 waringStr="硬着落<hard landing>"; 
					isAlarm=true;
					alarmLevel=1;
					limit_value="GW<MLW,Vrta[2.6,2.86)";
					act_Value="VRTA:"+vrtaNum+";"+"RALR:"+ralrNum+";"+"GW:"+gwNum+";";
					
				 }else if((vrtaNum>=2.86) ){
					 waringStr="严重硬着落<Severe hard landing>";
					isAlarm=true;
					alarmLevel=1;
					limit_value="GW<MLW,Vrta[2.86,*)";
					act_Value="VRTA:"+vrtaNum+";"+"RALR:"+ralrNum+";"+"GW:"+gwNum+";";
				 }
			}else{
				//大于最大着落重量
				if((vrtaNum>=1.7 && vrtaNum<2.6)){
//				if((vrtaNum>=0.6 && vrtaNum<2.6) ||(Math.abs(ralrNum)<13 && Math.abs(ralrNum)>=6)){
					waringStr="超重硬着落<Hard overweight landing>";
					limit_value="GW<MLW,Vrta[1.7,2.6)";
					isAlarm=true;
					alarmLevel=1;
					act_Value="VRTA:"+vrtaNum+";"+"RALR:"+ralrNum+";"+"GW:"+gwNum+";";
					
				}else if((vrtaNum>=2.6)){
					waringStr="严重超重硬着落<Severe hard overweight landing>";
					isAlarm=true;
					alarmLevel=1;
					limit_value="GW>MLW,Vrta[2.6,*)";
					act_Value="VRTA:"+vrtaNum+";"+"RALR:"+ralrNum+";"+"GW:"+gwNum+";";
				}
				
			}
//			if (Math.abs(ptcrNum)>10){
//				waringStr="严重超重硬着落<Severe hard overweight landing>";
//				isAlarm=true;
//				alarmLevel=1;
//				limit_value="PTCR>10";
//				act_Value="PTCR:"+ptcrNum+";";
//			}
			
			
			if(isAlarm){
				//HashVO msgTemplate = msgService.getMsgTemplateById(msgTemplateId);
				
				//告警等级:{ALARM_LEVEL},报文时间：{TIME} .飞机：{ACNUM},航班号{FLIGHT_NO}发生{REASON}.门限:{LIMIT_VALUE}.
				


				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("ALARM_LEVEL", alarmLevel+"");
				paramMap.put("TIME", rptDate);
				paramMap.put("ACNUM", acnum);
				paramMap.put("FLIGHT_NO", flightNoStr);
				paramMap.put("REASON", waringStr);
				paramMap.put("LIMIT_VALUE", limit_value);
				paramMap.put("ACT_VALUE", act_Value);
				
				String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());
				String smStr = StringUtil.buildExpression(paramMap, ruleVo.getSmContent());
				
				logger.info("告警消息为：【"+newStr+"】");
				
				msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr,smStr,alarmLevel);
				
			}else{
				logger.debug("没有探测到不正常着落！");
			}
//				GW (参考 15 号报文  CE  行) 
//				RALR(参考 15 号报文  S2  行) 
//				VRTA(参考 15 号报文  S3  行) 

//			10 Ft/s =/ <RALR<14 Ft/s (6 Ft/s =/ <RALR<13 Ft/s)或2.6g =/<VRTA<2.86g (1.7g=/<VRTA<2.6g) //硬/重着陆事件处理  
//			RALR>/=14 Ft/s （13 Ft/s）或 VRTA>/=2.86g  (2.6g) // 严重硬/重着陆事件处理

			
			//下列情况告警：
//				PTCR> 10deg/s （S2行）
//				ROLR > 5deg/s   （S2行）
//				LATA > 0.2g  （S3 和 S4  行，取最大绝对值）

		}
		
	}
	
	public Double getMaxFlyWeight(String acnum) throws Exception{
		Double maxFlyWeight = 0.0;
		CommDMO dmo = new CommDMO();
		
		String sql = "select MAX_FLYWEIGHT from b_aircraft where aircraftsn = '"+acnum+"'";
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		if(vos.length > 0){
			maxFlyWeight = vos[0].getDoubleValue("MAX_FLYWEIGHT");
			if (maxFlyWeight == null){
				maxFlyWeight = 0.0;
			}
		}
		
		
		
		return maxFlyWeight;
	}

}
