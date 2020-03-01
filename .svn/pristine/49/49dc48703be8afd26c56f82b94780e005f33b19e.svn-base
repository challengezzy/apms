package com.apms.bs.datatask;

import org.dom4j.Element;

import com.apms.bs.intf.ams.AmsPartSwapExtractService;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

/**
 * 从AMS系统中提取APU组件拆装信息
 * @author jerry
 * @date Sep 30, 2016
 */
public class DTAmsPartSwapExtractTask implements DataTaskExecuteIFC{
	
	/**
	 * 报文内容解析执行
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		//String dataModel = task.elementText("DATAMODEL");
		mainThread.logTaskRun("开始执行APU组件拆装同步任务。。。");
		long startTime = System.currentTimeMillis();
		AmsPartSwapExtractService service = new AmsPartSwapExtractService();
		int num = service.extractData();
		long endTime = System.currentTimeMillis();
		long cost = (endTime - startTime)/1000;
		mainThread.logTaskRun("结束执行APU组件拆装同步任务,同步["+num+"]条。耗时 " + cost + " s.");
	}

}
