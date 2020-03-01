package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.bs.intf.omis.OmisFlightScheduleService;


/**
 * 航班计划数据抽取任务
 * @author jerry
 * @date Mar 15, 2014
 */
public class DTOmisFltScheduleExtractTask implements DataTaskExecuteIFC{


	/**
	 * 报文内容解析执行
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String dataModel = task.elementText("DATAMODEL");
		String dayNum = task.elementText("DAYNUM"); //同步数据天数
		
		//如果没有设置，则默认同步7天数据
		if(dayNum == null || dayNum.length() ==0 ){
			dayNum = "7";
		}
		
		OmisFlightScheduleService service = new OmisFlightScheduleService();
		long startTime = System.currentTimeMillis();
		if("2".equals(dataModel)){
			mainThread.logTaskRun("开始执行OMIS历史[T-2]航班数据同步任务..");
			//T-2天历史航班数据同步
			service.extractHistoryFlight();
			
		}else if("1".equals(dataModel)){
			mainThread.logTaskRun("开始执行OMIS当前[T-1至T+7]航班数据同步任务..");
			//T-1天->T+7天航班数据同步
			service.extractCurrentFlight(dayNum);
		}
		
		long endTime = System.currentTimeMillis();
		long cost = (endTime - startTime)/1000;
		mainThread.logTaskRun("开始执行OMIS航班数据同步任务。耗时 " + cost + " s.");
	}

}