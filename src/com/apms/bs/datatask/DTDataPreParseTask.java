package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.bs.dataprase.DataPreParseService;

/**
 * 报文数据预解析处理
 *@date Nov 28, 2011
 **/
public class DTDataPreParseTask implements DataTaskExecuteIFC{


	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		mainThread.logTaskRun("开始执行报文数据预解析任务..");
		
		DataPreParseService preservice = new DataPreParseService();
		//开始执行DFD报文数据同步
		preservice.dfdDataExtract();
		
		//开始执行CFD报文数据同步
		preservice.cfdDataExtract();
		
		mainThread.logTaskRun("执行报文数据预解析任务结束.");
	}

}