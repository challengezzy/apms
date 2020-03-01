package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.bs.intf.omis.JXTeleGraphExtractService;
import com.apms.bs.intf.omis.TeleGraphExtractService;

/**
 * 吉祥中间库报文数据采集任务
 *@date Nov 28, 2011
 **/
public class DTJxTeleGraphExtractTask implements DataTaskExecuteIFC{


	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		mainThread.logTaskRun("开始执行中间库报文数据采集任务..");
		JXTeleGraphExtractService service = new JXTeleGraphExtractService();
		service.extractTeleGraph();
		mainThread.logTaskRun("结束执行中间库报文数据采集任务。");
	}

}