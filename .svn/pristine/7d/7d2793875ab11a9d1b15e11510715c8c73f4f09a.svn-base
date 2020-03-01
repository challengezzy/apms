package com.apms.bs.datatask;

import org.dom4j.Element;
import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;
import com.apms.ApmsConst;

/**
 * 清理数据任务日志
 * 
 * @date Nov 28, 2011
 **/
public class DTClearDataTaskLog implements DataTaskExecuteIFC {

	/**
	 * @param task
	 *            定义该报文的参数XML
	 * @param mainThread
	 *            执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task, DataTaskExecThread mainThread)
			throws Exception {
		String dayStr = "60";
		if (task.elementText("DAY") != null) {
			dayStr = task.elementText("DAY");
		}
		int day = Integer.parseInt(dayStr);
		CommDMO dmo = new CommDMO();
		mainThread.logTaskRun("开始执行[DTClearDataTaskLog]任务....");
		try {
			DTLogUtil dtLogUtil = new DTLogUtil();
			dtLogUtil.clearDataTaskLog(day);
			dmo.commit(ApmsConst.DS_APMS);
		} catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			mainThread.logTaskRun("清理数据任务日志执行异常。" + e.getMessage());
		} finally {

			// 释放数据库连接
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		mainThread.logTaskRun("[DTClearDataTaskLog]任务执行完成！");

	}

}