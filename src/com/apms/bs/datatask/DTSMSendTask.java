package com.apms.bs.datatask;

import org.dom4j.Element;

import com.apms.ApmsConst;
import com.apms.bs.sms.SmsOpenMasSender;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

/**
 * 短信发送任务
 * @author
 */
public class DTSMSendTask implements DataTaskExecuteIFC{

	@Override
	public void dataTaskExec(Element task, DataTaskExecThread mainThread) throws Exception {
		CommDMO dmo = new CommDMO();
		try{
			//ShortMassageHwService service = ShortMassageHwService.getInstance();//获取服务实例
			SmsOpenMasSender sender = SmsOpenMasSender.getInstance();
			String sql = "SELECT DEST,MSG,ID FROM SMS_NOTIFY_MID WHERE DEALED = 0";
			String uptSql = "update SMS_NOTIFY_MID set dealed=1 where id= ?";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			
			for(HashVO vo : vos){
				String dest = vo.getStringValue("DEST");
				String content = vo.getStringValue("MSG");
				
				sender.sendMessage(dest, content); //新的发送短信接口
				//service.send(dest, content);//发送短信
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptSql, vo.getStringValue("ID") );
				dmo.commit(ApmsConst.DS_APMS);
			}
			
			mainThread.logTaskRun("本次处理["+vos.length+"]条短信发送");
		}catch (Exception e) {
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
}
