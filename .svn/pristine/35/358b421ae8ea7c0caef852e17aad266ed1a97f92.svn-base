package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.ApmsConst;
import com.apms.bs.remind.RemindManageService;

/**
 * 循环生成提示单实例
 * @author jerry
 * @date Apr 23, 2014
 */
public class DTRemindInsCreate implements DataTaskExecuteIFC{

	/**
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {

		//String flightTempName = task.elementText("TEMPLATENAME");
		CommDMO dmo = new CommDMO();
		try{
			RemindManageService remind = new RemindManageService();
			int numAdd = remind.generateRemindIns();
			mainThread.logTaskRun("本次生成提醒信息["+numAdd+"]条!");
		}catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			mainThread.logTaskRun("生成提示信息实例异常。" + e.getMessage());
		}finally{
			
			//释放数据库连接
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
				
		mainThread.logTaskRun("[DTRemindInsCreate]任务执行完成！");
		
	}

}