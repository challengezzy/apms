package com.apms.bs.datatask;

import org.dom4j.Element;

import com.apms.bs.intf.oldsys.HistoryDataExtractService;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;


public class DTOldSysDataExtract implements DataTaskExecuteIFC{


	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		//String cityName = task.elementText("CITYNAME");
		//String cityCode = task.elementText("CITYCODE");
		
		mainThread.logTaskRun("开始执行报文数据采集任务..");
		HistoryDataExtractService service = new HistoryDataExtractService();
		service.extractTeleGraph();
	}

}