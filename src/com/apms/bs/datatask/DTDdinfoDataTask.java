package com.apms.bs.datatask;

import org.dom4j.Element;
import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;
import com.apms.ApmsConst;
import com.apms.bs.intf.omis.DDInfoExtractService;

/**
 * DD单数据同步任务
 * 
 * @date Nov 28, 2011
 **/
public class DTDdinfoDataTask implements DataTaskExecuteIFC {

	/**
	 * @param task
	 *            定义该报文的参数XML
	 * @param mainThread
	 *            执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task, DataTaskExecThread mainThread)
			throws Exception {
		
		CommDMO dmo = new CommDMO();
		mainThread.logTaskRun("开始执行[DD单数据同步]任务....");
		try {
			DDInfoExtractService ddInfo = new DDInfoExtractService();
			ddInfo.extractDDInfo();
			dmo.commit(ApmsConst.DS_APMS);
		} catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			mainThread.logTaskRun("DD单数据同步执行异常。" + e.getMessage());
		} finally {

			// 释放数据库连接
			dmo.releaseContext(ApmsConst.DS_APMS);
			dmo.releaseContext(ApmsConst.DS_OMIS);
		}
		mainThread.logTaskRun("[DD单数据同步]任务执行完成！");

	}

}