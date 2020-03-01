package com.apms.bs.sms;

import java.util.List;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 短信发送服务
 * 使用华为短信发送网关，数据库接口
 *
 */
public class ShortMassageHwService {
	
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private static ShortMassageHwService smService = null;
	
	public ShortMassageHwService(){
		
	}
	
	public static ShortMassageHwService getInstance(){
		if(smService == null)
			smService = new ShortMassageHwService();
		
		return smService;
	}
	
	/**
	 * 短信发送
	 * @param destAddr 收信人手机号
	 * @param msgContent 短信内容
	 * @throws Exception
	 */
	public void send(String destAddr,String msgContent) throws Exception{
		String orgAddr = "02120225660";
		try{
//			insertSMToDb(orgAddr,destAddr,msgContent);
//			dmo.commit(ApmsConst.DS_SMDB);
			
			//modify zhangzy 20170108 保持原接口不使用，使用openmas接口
			SmsOpenMasSender sender = SmsOpenMasSender.getInstance();
			sender.sendMessage(destAddr, msgContent); //新的发送短信接口
			
			logger.debug("发送短信到["+destAddr+"]短信接口成功！");
			
			//是否要记录短信发送情况到apms表中？
		}catch (Exception e) {
			logger.error("短信发送异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_SMDB);
		}
		
	}
	
	/**
	 * 短信发送
	 * @param orgAddr 发信人手机号
	 * @param destAddr 收信人手机号
	 * @param msgContent 短信内容
	 * @throws Exception
	 */
	public void send(String orgAddr,String destAddr,String msgContent) throws Exception{
		try{
			insertSMToDb(orgAddr,destAddr,msgContent);
			dmo.commit(ApmsConst.DS_SMDB);
			logger.debug("发送短信到["+destAddr+"]短信接口成功！");
			
			//是否要记录短信发送情况到apms表中？
		}catch (Exception e) {
			logger.error("短信发送异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_SMDB);
		}
		
	}
	
	/**
	 * 短信发送(群发)
	 * @param orgAddr
	 * @param destAddrList
	 * @param msgContent
	 * @throws Exception
	 */
	public void send(String orgAddr,List<String> destAddrList,String msgContent) throws Exception{
		logger.debug("开始群发短信:");
		try{
			//遍历进行短信发送
			for(int i=0;i<destAddrList.size();i++){
				send(orgAddr,destAddrList.get(i),msgContent);
			}
			
			logger.debug("群发完成！");
		}catch (Exception e) {
			logger.error("短信发送异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_SMDB);
		}
		
	}
	
	private void insertSMToDb(String orgAddr,String destAddr,String msgContent) throws Exception{
		String insertSql = "insert into tbl_SMSendTask (CreatorID,SmSendedNum,OperationType,SendType, "
			+ " OrgAddr,DestAddr,SM_Content,SendTime,NeedStateReport,ServiceID,FeeType,FeeCode,SMType,MessageID,"
			+ " DestAddrType,SubTime,TaskStatus,SendLevel,SendState,TryTimes) values ( "
			+ " '0000',0,'WAS',1,'"+orgAddr+"','"+destAddr+"','"+msgContent+"',getdate(),1, "
			+ " 'TZJ0010101','01','0',0,'0',0,getdate(),0,0,0,0)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_SMDB, insertSql);
	}

}
