package com.apms.bs.alarm.rule.impl;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.alarm.rule.AlarmRuleImplBaseClass;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dataprase.vo.AcarsAcwVo;

public class AlarmImplAcw extends AlarmRuleImplBaseClass{

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	public void alartTrigger(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @param acwVo
	 * @param acmodel
	 * @param rptno
	 * @param acwx
	 * @param rowTitle
	 * @return
	 * @throws Exception
	 */
	public String getAcwWarning(AcarsAcwVo acwVo,String acmodel,String rptno,String acwx,String rowTitle) throws Exception{
		HashVO mapVo = getAcwWarnMap(acmodel, rptno, acwx, rowTitle);
		if(mapVo == null){
			logger.debug("未找到对应的配置，不作ACW告警处理！");
			return null;
		}
		
		StringBuilder acwWarnStr = new StringBuilder();
		boolean isAlarm = false;
		
		if( "1".equals(acwVo.getBit1()) && "1".equals(mapVo.getStringValue("BIT_WARNING1")) ){
			//该BIT需要告警，且当前值为1
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC1"));
			isAlarm = true;
		}
		
		if( "1".equals(acwVo.getBit2()) && "1".equals(mapVo.getStringValue("BIT_WARNING2")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC2"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit3()) && "1".equals(mapVo.getStringValue("BIT_WARNING3")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC3"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit4()) && "1".equals(mapVo.getStringValue("BIT_WARNING4")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC4"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit5()) && "1".equals(mapVo.getStringValue("BIT_WARNING5")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC5"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit6()) && "1".equals(mapVo.getStringValue("BIT_WARNING6")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC6"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit7()) && "1".equals(mapVo.getStringValue("BIT_WARNING7")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC7"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit8()) && "1".equals(mapVo.getStringValue("BIT_WARNING8")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC8"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit9()) && "1".equals(mapVo.getStringValue("BIT_WARNING9")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC9"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit10()) && "1".equals(mapVo.getStringValue("BIT_WARNING10")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC10"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit11()) && "1".equals(mapVo.getStringValue("BIT_WARNING11")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC11"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit12()) && "1".equals(mapVo.getStringValue("BIT_WARNING12")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC12"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit13()) && "1".equals(mapVo.getStringValue("BIT_WARNING13")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC13"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit14()) && "1".equals(mapVo.getStringValue("BIT_WARNING14")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC14"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit15()) && "1".equals(mapVo.getStringValue("BIT_WARNING15")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC15"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit16()) && "1".equals(mapVo.getStringValue("BIT_WARNING16")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC16"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit17()) && "1".equals(mapVo.getStringValue("BIT_WARNING17")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC17"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit18()) && "1".equals(mapVo.getStringValue("BIT_WARNING18")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC18"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit19()) && "1".equals(mapVo.getStringValue("BIT_WARNING19")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC19"));
			isAlarm = true;
		}
		if( "1".equals(acwVo.getBit20()) && "1".equals(mapVo.getStringValue("BIT_WARNING20")) ){
			acwWarnStr.append("\n" + mapVo.getStringValue("BIT_DESC20"));
			isAlarm = true;
		}
		
		if(isAlarm)
			return acwWarnStr.toString();
		else
			return null;
	}
	
	public HashVO getAcwWarnMap(String acmodel,String rptno,String acwx,String rowTitle) throws Exception{
		StringBuilder sb = new StringBuilder("select * from a_dfd_acw_map m ");
		sb.append(" where acmodel='"+acmodel+"'");
		sb.append(" AND RPTNO='"+rptno+"'");
		sb.append(" AND ACWX='"+acwx+"'");
		
		if(rowTitle != null){
			sb.append(" AND ROWTITLE='"+rowTitle+"'"); //行号
		}
		
		CommDMO dmo = new CommDMO();
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		if(vos.length > 0){
			HashVO mapVo = vos[0];
			return mapVo;
			
		}else{
			logger.debug("未找到acmode=["+acmodel+"],rptno=["+rptno+"],acwx=["+acwx+"],rowtitle=["+rowTitle+"]对应的控制字配置!");
			return null;
		}
		
	}

}
