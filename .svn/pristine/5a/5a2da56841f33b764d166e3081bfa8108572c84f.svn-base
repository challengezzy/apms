package com.apms.bs.datatask;

import org.dom4j.Element;

import com.apms.bs.intf.ams.AmsWorkPlaneExtractService;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

/**
 * 从AMS系统中提取维修工作信息
 * @author jerry
 * @date Jan 18, 2016
 */
public class DTAmsWorkExtractTask implements DataTaskExecuteIFC{
	
	/**
	 * 报文内容解析执行
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		//String dataModel = task.elementText("DATAMODEL");
		mainThread.logTaskRun("开始执行AMS维修工作同步任务。。。");
		long startTime = System.currentTimeMillis();
		AmsWorkPlaneExtractService service = new AmsWorkPlaneExtractService();
		int num = service.extractWorkPlan();
		long endTime = System.currentTimeMillis();
		long cost = (endTime - startTime)/1000;
		mainThread.logTaskRun("结束执行AMS维修工作同步任务,同步["+num+"]条。耗时 " + cost + " s.");
	}

}
