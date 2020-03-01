package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.bs.intf.omis.TeleGraphExtractService;

/**
 * 报文数据采集任务
 *@date Nov 28, 2011
 **/
public class DTTeleGraphExtractTask implements DataTaskExecuteIFC{


	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		//String cityName = task.elementText("CITYNAME");
		//String cityCode = task.elementText("CITYCODE");
		
		mainThread.logTaskRun("开始执行报文数据采集任务..");
		TeleGraphExtractService service = new TeleGraphExtractService();
		service.extractTeleGraph();
		mainThread.logTaskRun("结束执行报文数据采集任务。");
	}

}