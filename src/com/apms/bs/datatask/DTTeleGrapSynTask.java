package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.bs.intf.outer.IntfTeleGraphSynService;

/**
 * 报文数据系统间同步
 * @author jerry
 * @date May 13, 2017
 */
public class DTTeleGrapSynTask implements DataTaskExecuteIFC{


	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		mainThread.logTaskRun("开始执行报文数据同步任务..");
		IntfTeleGraphSynService service = new IntfTeleGraphSynService();
		service.extractTeleGraph();
		mainThread.logTaskRun("结束执行报文数据同步任务。");
	}

}