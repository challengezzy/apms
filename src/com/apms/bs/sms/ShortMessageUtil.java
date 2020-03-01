package com.apms.bs.sms;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

public class ShortMessageUtil {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	/**
	 * 保存要发送的短信到中间表
	 * @param dest
	 * @param content
	 */
	public void saveMsgToMid(String dest,String content,int dealed) throws Exception{
		String sql = "insert into SMS_NOTIFY_MID(ID,UPDATETIME,DEST,MSG,DEALED) ";
		sql += " VALUES(S_SMS_NOTIFY_MID.NEXTVAL,SYSDATE,?,?,?)";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql,dest,content,dealed);
	}

}
