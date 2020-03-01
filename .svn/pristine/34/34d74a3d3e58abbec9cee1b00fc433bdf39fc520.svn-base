package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.bs.intf.foc.FocAcstopSynService;

/**
 * 从FOC航班机位信息同步
 * @author jerry
 * @date Jan 18, 2016
 */
public class DTFocAcstopSynTask implements DataTaskExecuteIFC{
	
	/**
	 * 报文内容解析执行
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		//String dataModel = task.elementText("DATAMODEL");
		mainThread.logTaskRun("开始执行FOC航班机位同步任务。。。");
		long startTime = System.currentTimeMillis();
		
		FocAcstopSynService foc = new FocAcstopSynService();
		int num = foc.synAcStopFromFoc();
		
		long endTime = System.currentTimeMillis();
		long cost = (endTime - startTime)/1000;
		mainThread.logTaskRun("结束执行FOC航班机位同步任务,共["+num+"]条。耗时 " + cost + " s.");
	}

}
