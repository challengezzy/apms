package com.apms.bs.alarm.msg;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaServerEnvironment;

import com.apms.ApmsConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.email.EmailService;
import com.apms.bs.sms.ShortMassageHwService;
import com.apms.bs.sysconfig.ApmsSysParamConfService;
import com.apms.vo.ApmsVarConst;

public class AlarmMessageService {
	
	private CommDMO dmo = new CommDMO();
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private String smSender = "1065752581140911";
	
	private boolean isEmailEnable = false;
	private boolean isSmsEnable = false;
	
	/** 插入告警消息Sql */
	private StringBuilder sqlInsertMessage;
	
	/** 插入消息订阅sql */
	private StringBuilder sqlInsertSubscriber;
	
	/** 查询飞机所属基地 */
	private String sqlQryAircraft; 
	
	public AlarmMessageService(){
		String tmp = (String) NovaServerEnvironment.getInstance().get("IS_ALARM_EMAIL");
		if (tmp != null && tmp.toLowerCase().equals("true")) {
			isEmailEnable = true;
		}
		
		String tmp2 = (String) NovaServerEnvironment.getInstance().get("IS_ALARM_SMS");
		if (tmp2 != null && tmp2.toLowerCase().equals("true")) {
			isSmsEnable = true;
		}
		
		sqlInsertMessage = new StringBuilder("INSERT INTO ALARM_MESSAGE( ");
		sqlInsertMessage.append("ID,MSGTEMPLATEID,MONITOBJID,DATAVIEWPK_VALUE,SUBJECT,CONTENT");
		sqlInsertMessage.append(",RPTNO,ACNUM,DEVICESN,RPTDATE,ORGID");//增加飞机、报文、设备相关信息
		sqlInsertMessage.append(",SEVERITY");
		sqlInsertMessage.append(",DISPATHSTATUS,DEALSTATUS,CREATETIME)");
		sqlInsertMessage.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,0,0,sysdate)");
		sqlInsertSubscriber = new StringBuilder("INSERT INTO ALARM_MSGSUBSCRIBER(ID,MESSAGEID,DISPATCHRULEID,USERNAME)");
		sqlInsertSubscriber.append(" VALUES(S_ALARM_MSGSUBSCRIBER.NEXTVAL,?,?,?)");
		
		sqlQryAircraft = "SELECT ID,AIRCRAFTSN,BASEORGID,ACMODELID,FSN,MSN FROM B_AIRCRAFT WHERE AIRCRAFTSN=?";
		
	}
	
	public void insertDispathAlarmMessage(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo,String content) throws Exception{
		insertDispathAlarmMessage(ruleVo, targetVo, content, null);
	}
	
	public void insertDispathAlarmMessage(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo,String content,int faultLevel) throws Exception{
		insertDispathAlarmMessage(ruleVo, targetVo, content, null, faultLevel);
	}
	
	//不指定告警级别
	public void insertDispathAlarmMessage(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo,String content,String smsContent) throws Exception{
		insertDispathAlarmMessage(ruleVo, targetVo, content, smsContent, -1);
	}
	
	public void insertNeuralNetAlarmMessage(HashVO drVo,HashVO msgTemplteVO, String content) throws Exception{
		String msgTemplteId = msgTemplteVO.getIntegerValue("ID").toString();
		String msgTemplteType = msgTemplteVO.getStringValue("TYPE");
		String messageId = dmo.getSequenceNextValByDS(ApmsConst.DS_APMS, "S_ALARM_MESSAGE");
		//获取飞机所属基地
		HashVO acvo = getACVo(drVo.getStringValue("ACNUM"));
		String baseOrgId = acvo.getStringValue("BASEORGID");
		try{
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sqlInsertMessage.toString(),
					messageId,msgTemplteId,null,drVo.getStringValue("MSG_NO"),msgTemplteVO.getStringValue("SUBJECT"),content
					,drVo.getStringValue("RPTNO"),drVo.getStringValue("ACNUM"),drVo.getStringValue("ASN"),
					drVo.getDateValue("DATE_UTC"),baseOrgId,2);
		}catch (Exception e) {
			logger.error("生成告警消息失败，信息内容messageTemplateid="+msgTemplteId+
					",msgno="+drVo.getStringValue("MSG_NO")+",baseorgid="+baseOrgId+",severity="+2);
		}
		
		EmailService mailService = new EmailService();
		smSender = ApmsSysParamConfService.getInstance().getConfVo().getSmsVo().getSender();				
		//生成消息派发信息
	
		HashVO[] users = getAlarmUsers(msgTemplteId,drVo.getStringValue("ACNUM"),true,false);
		logger.debug("消息messageTemplateid="+msgTemplteId +"查询到["+users.length+"]条派发人员！");
		for(int i=0;i<users.length;i++){
			HashVO vo = users[i];
			String loginName = vo.getStringValue("LOGINNAME");
			String mobile = vo.getStringValue("MOBILE");
			String email = vo.getStringValue("EMAIL");			
			String isEmail = vo.getStringValue("ISEMAIL");
			String isSms = vo.getStringValue("ISSMS");
			String dispatchruleId = vo.getStringValue("DISPATCHRULEID");
			
			try{
				if("1".equals(isEmail) && email != null 
						&& (ApmsVarConst.MSGTYPE_MAIL.equals(msgTemplteType) || ApmsVarConst.MSGTYPE_MAILSMS.equals(msgTemplteType))){
					if( isEmailEnable ){
						mailService.sendMail(email, msgTemplteVO.getStringValue("SUBJECT"), content);
					}
				}else if("1".equals(isSms) && mobile != null
						&& (ApmsVarConst.MSGTYPE_SMS.equals(msgTemplteType) || ApmsVarConst.MSGTYPE_MAILSMS.equals(msgTemplteType))){
					if( isSmsEnable ){
						ShortMassageHwService.getInstance().send(smSender, mobile, content);
					}
				}
				//生成系统派发
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sqlInsertSubscriber.toString(), messageId,dispatchruleId,loginName);
				
				logger.debug("告警消息["+messageId+"]生成成功");
			}catch (Exception e) {
				logger.error("发送短信或EMail消息异常："+ e.toString());
			}
			
		}
	}

	/**
	 * 生成告警消息并派发
	 * @param ruleVo 告警规则对象
	 * @param targetVo 告警目标对象
	 * @param content 告警内容
	 * @param smsContent 短信内容
	 * @param faultLevel 故障级别, -1表示使用规则模板中级别
	 * @throws Exception
	 */
	public void insertDispathAlarmMessage(AlarmRuleVo ruleVo, AlarmMonitorTargetVo targetVo,String content,String smsContent,int faultLevel) throws Exception{

		int severity = ruleVo.getSeverity();
		if(faultLevel > 0){
			severity = faultLevel;
		}
		
		String msgTemplateId = ruleVo.getMsgTemplateId();
		String monitorObjId = ruleVo.getMoid();
		String acnum = targetVo.getAcnum();
		
		//获取飞机所属基地
		HashVO acvo = getACVo(acnum);
		String baseOrgId = acvo.getStringValue("BASEORGID");
		
		String messageId = dmo.getSequenceNextValByDS(ApmsConst.DS_APMS, "S_ALARM_MESSAGE");
		try{
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sqlInsertMessage.toString(),messageId,msgTemplateId,monitorObjId,targetVo.getMsgno(),ruleVo.getMsgSubject(),content
					,targetVo.getRptno(),targetVo.getAcnum(),targetVo.getDevicesn(),targetVo.getDateUtc(),baseOrgId,severity);
		}catch (Exception e) {
			logger.error("生成告警消息失败，信息内容messageTemplateid="+msgTemplateId+",monitorObjid="+monitorObjId
					+",msgno="+targetVo.getMsgno()+",baseorgid="+baseOrgId+",severity="+severity);
		}
		
		
		EmailService mailService = new EmailService();
		smSender = ApmsSysParamConfService.getInstance().getConfVo().getSmsVo().getSender();
		
		String msgType = ruleVo.getMsgType();
		
		//生成消息派发信息
		HashVO[] users = getAlarmUsers(msgTemplateId, acnum,true,false);
		logger.debug("消息messageTemplateid="+msgTemplateId+"查询到["+users.length+"]条派发人员！");
		for(int i=0;i<users.length;i++){
			HashVO vo = users[i];
			String loginName = vo.getStringValue("LOGINNAME");
			String mobile = vo.getStringValue("MOBILE");
			String email = vo.getStringValue("EMAIL");			
			String isEmail = vo.getStringValue("ISEMAIL");
			String isSms = vo.getStringValue("ISSMS");
			String dispatchruleId = vo.getStringValue("DISPATCHRULEID");
			
			try{
				if("1".equals(isEmail) && email != null 
						&& (ApmsVarConst.MSGTYPE_MAIL.equals(msgType) || ApmsVarConst.MSGTYPE_MAILSMS.equals(msgType))){
					if( isEmailEnable ){
						mailService.sendMail(email, ruleVo.getMsgSubject(), content);
					}
				}
				if("1".equals(isSms) && mobile != null
						&& (ApmsVarConst.MSGTYPE_SMS.equals(msgType) || ApmsVarConst.MSGTYPE_MAILSMS.equals(msgType))){
					if( isSmsEnable ){
						ShortMassageHwService.getInstance().send(smSender, mobile, smsContent);
					}
				}
				//生成系统派发
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sqlInsertSubscriber.toString(), messageId,dispatchruleId,loginName);
				
				logger.debug("告警消息["+messageId+"]生成成功");
			}catch (Exception e) {
				logger.error("发送短信或EMail消息异常："+ e.toString());
			}
			
		}
		
	}
	
	public HashVO getACVo(String acnum) throws Exception{
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sqlQryAircraft,acnum);
		if(vos.length > 0){
			return vos[0];
		}else{
			return null;
		}
	}
	
	/**
	 * 根据飞机号和消息模板查找消息订阅人员
	 * @param msgTemplateId
	 * @param acnum
	 * @return
	 * @throws Exception
	 */
	public HashVO[] getMsgAlarmUsers(String msgTemplateId,String acnum) throws Exception{
		return getAlarmUsers(msgTemplateId, acnum, false, false);
	}
	
	/**
	 * 根据飞机号和消息模板查找需要发送EMAIL人员
	 * @param msgTemplateId
	 * @param acnum
	 * @return
	 * @throws Exception
	 */
	public HashVO[] getEmailAlarmUsers(String msgTemplateId,String acnum,boolean isEmail,boolean isEms) throws Exception{
		return getAlarmUsers(msgTemplateId, acnum, false, true);
	}
	
	/**
	 * 根据飞机号和消息模板查找需要发送短信人员
	 * @param msgTemplateId
	 * @param acnum
	 * @return
	 * @throws Exception
	 */
	public HashVO[] getSmsAlarmUsers(String msgTemplateId,String acnum) throws Exception{
		return getAlarmUsers(msgTemplateId, acnum, false, true);
	}
	/**
	 * 根据飞机号和消息模板查找需要发送消息人员
	 * @param msgTemplateId
	 * @param acnum
	 * @param isEmail 只发邮件
	 * @param isSms 只发短信
	 * @return
	 * @throws Exception
	 */
	public HashVO[] getAlarmUsers(String msgTemplateId,String acnum,boolean isEmail,boolean isSms) throws Exception{
		StringBuilder sqlBuidler = new StringBuilder("SELECT ");
		sqlBuidler.append(" ID,NAME,LOGINNAME,MOBILE,EMAIL,NO,BASEORGID,ORGID,ISEMAIL,ISSMS,MSGTEMPLATEID,DISPATCHRULEID");
		sqlBuidler.append(" FROM V_MSGTEMPLATE_USERORG"); //判断飞机和人员在同一基地，基地限制
		sqlBuidler.append(" WHERE  MSGTEMPLATEID=? AND ACNUM=?");
		if(isEmail)
			sqlBuidler.append(" AND ISEMAIL=1");
		if(isSms)
			sqlBuidler.append(" AND ISSMS=1");
		sqlBuidler.append(" UNION");
		sqlBuidler.append(" SELECT ID,NAME,LOGINNAME,MOBILE,EMAIL,NO,BASEORGID,ORGID,ISEMAIL,ISSMS,MSGTEMPLATEID,DISPATCHRULEID");
		sqlBuidler.append(" FROM V_MSGTEMPLATE_USER");//无基地限制
		sqlBuidler.append(" WHERE MSGTEMPLATEID =?");
		sqlBuidler.append("");
		if(isEmail)
			sqlBuidler.append(" AND ISEMAIL=1");
		if(isSms)
			sqlBuidler.append(" AND ISSMS=1");
		HashVO[] users = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sqlBuidler.toString(), msgTemplateId,acnum,msgTemplateId);
		return users;
	}
}
