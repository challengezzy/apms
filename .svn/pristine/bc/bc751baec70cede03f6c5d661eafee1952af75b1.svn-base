package com.apms.bs.neuralnet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.alarm.msg.AlarmMessageService;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;


public class NeuralnetAlarmMsgService {
	private CommDMO dmo = new CommDMO();
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	public void ApuDiagnoseDataAlarm() throws Exception{
		String sql= "SELECT L.MSG_NO,H.RPTNO,ASN,L.ACNUM, APUMODEL, H.DATE_UTC, L.STATUS, (SELECT D.VALUEEN FROM B_DICTIONARY D " +
					" WHERE D.CLASSNAME='L_APU_TRAINDATA' AND D.ATTRIBUTENAME='DATATYPE' AND D.VALUE = L.DATATYPE ) DATATYPE, " +
					" CORRECTFLAG, ISALERTED, COMMENTS, UPDATETIME, UPDATEUSER FROM L_APU_DIAGNOSERESULT L,A_DFD_HEAD H WHERE " +
					" L.MSG_NO=H.MSG_NO AND L.STATUS= 0 AND ROWNUM <= 3000";
		
		
		String sql2 = "SELECT L.ID, CODE, NAME, SUBJECT, CONTENT, TYPE, SMCONTENT, " +
					"UPDATE_DATE, UPDATE_MAN, APPMODULE, ATA FROM ALARM_MSGTEMPLATE L WHERE CODE='A13_APU神经网络告警'";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		HashVO[] msgTemplteVO = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql2);
		
		String updateSql = "UPDATE L_APU_DIAGNOSERESULT SET ISALERTED = ?,STATUS =?,UPDATETIME=sysdate,UPDATEUSER='system' WHERE MSG_NO=?";
		
		
		String content = msgTemplteVO[0].getStringValue("CONTENT");
		for(int i=0; i<vos.length; i++){
			if("正常".equals(vos[i].getStringValue("DATATYPE"))){
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql,9,1,vos[i].getStringValue("MSG_NO"));
				
			}else{
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql,1,1,vos[i].getStringValue("MSG_NO"));
				Map<String, String> contentMap =  new HashMap<String,String>();
//				告警：{ALARM_LEVEL},时间：{TIME} ，{ACNUM},APU发生{DATATYPE}异常
				contentMap.put("ALARM_LEVEL","一般");
				contentMap.put("TIME",DateUtil.getDateStr(vos[i].getDateValue("DATE_UTC"),"yyyy-MM-dd HH:mm:ss") );
				contentMap.put("ACNUM",vos[i].getStringValue("ACNUM") );
				contentMap.put("DATATYPE",vos[i].getStringValue("DATATYPE"));
				content = StringUtil.buildExpression(contentMap, content);

				AlarmMessageService alarmMessageService = new AlarmMessageService();
				alarmMessageService.insertNeuralNetAlarmMessage(vos[i], msgTemplteVO[0], content);
			}
		}
		dmo.commit(ApmsConst.DS_APMS);

	}
}
