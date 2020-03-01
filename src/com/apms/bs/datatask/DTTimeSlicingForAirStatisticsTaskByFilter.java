package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.ApmsConst;

/**
 * 对时间进行切分任务
 * @author jerry
 * @date Sep 29, 2014
 */
public class DTTimeSlicingForAirStatisticsTaskByFilter implements DataTaskExecuteIFC{

	/**
	 * 报文内容解析执行
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		CommDMO dmo = new CommDMO();
//		String hour = task.elementText("hour");
//		
//		if("".equals(hour)){
//			hour ="1";
//		}
		mainThread.logTaskRun("开始执行[DTTimeSlicingForAirStatisticsTask]任务....");
		try{
			dmo.callProcedureByDS(ApmsConst.DS_APMS, "statistics_period_create", null);
			String logStr = "生成统计飞机数量的切割时间任务执行完毕";
			mainThread.logTaskRun(logStr);
			
		}catch (Exception e) {
			mainThread.logTaskRun("生成统计飞机数量的切割时间任务执行异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
				
		mainThread.logTaskRun("[DTTimeSlicingForAirStatisticsTask]任务执行完成！");
		
	}
	
	
	public void dataTaskExec2(Element task,DataTaskExecThread mainThread) throws Exception {
		
	}

}