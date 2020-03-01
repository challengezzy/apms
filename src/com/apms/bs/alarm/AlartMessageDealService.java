package com.apms.bs.alarm;

import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.vo.ApmsVarConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

public class AlartMessageDealService {

	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	
	/**
	 * 设置消息状态为已处理
	 * @throws Exception
	 */
	public void setMessageDealed(Map<String, Object> dealInfo) throws Exception{
		String qrySql = "SELECT 1 FROM A_DFD_REPORTDEALMEMO WHERE MSG_NO = ?";
		String insertSql = "INSERT INTO A_DFD_REPORTDEALMEMO(ID,MSG_NO,DEALDESC,DEALER,DEALSTATUS,EVENTTYPE,DEALTIME) VALUES(S_A_DFD_REPORTDEALMEMO.NEXTVAL,?,?,?,?,?,SYSDATE)";
		String updateSql = "UPDATE A_DFD_REPORTDEALMEMO SET DEALSTATUS=1,DEALTIME=SYSDATE,DEALER=?,DEALDESC=?,EVENTTYPE=? WHERE MSG_NO=?";
		
		String updateMsgSql = "UPDATE ALARM_MESSAGE M SET M.DEALSTATUS=1 WHERE M.DATAVIEWPK_VALUE = ?";
		
		String msgno = (String)dealInfo.get("MSG_NO");
		String dealer = (String)dealInfo.get("DEALER");
		String dealdesc = (String)dealInfo.get("DEALDESC");
		String eventtype = (String)dealInfo.get("EVENTTYPE");
		
		//先判断是否已存在处理数据
		HashVO[] tempVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrySql,msgno);
		if(tempVos.length == 0){		
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,dealdesc,dealer,ApmsVarConst.DEALSTATUS_DEALED,eventtype);
		}else{
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, dealer,dealdesc,eventtype,msgno);
		}
		
		//更新消息信息为已处理
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateMsgSql ,msgno);
		dmo.commit(ApmsConst.DS_APMS);
		
		logger.info("更新报文msgno=["+msgno+"]产生的告警消息为已处理！");
	}
	
}
