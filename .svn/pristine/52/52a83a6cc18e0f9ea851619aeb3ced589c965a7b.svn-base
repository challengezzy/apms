

package com.apms.bs.datatask;

import java.util.Date;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.ApmsConst;
import com.apms.bs.engine.EngineFlightLogService;
import com.apms.bs.util.DateUtil;

/**
 * 获取单架发动机的飞行日志
 * 自定义数据任务，继承接口DataTaskExecuteIFC
 * @author zhanghl
 *@date Nov 05, 2014
 **/
public class DTEngSingleFlyLogTask implements DataTaskExecuteIFC{

	/**
	 * 任务执行
	 * task:  任务节点定义
	 * mainThread 主任务线程引用
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String engsn = task.elementText("ENGSN");
		String startdateStr = task.elementText("STARTDATE");
		String enddateStr = task.elementText("ENDDATE");
		
		Date startDate = DateUtil.StringToDate(startdateStr, "yyyy-MM-dd");
		Date endDate = DateUtil.StringToDate(enddateStr, "yyyy-MM-dd");
		
		if(DateUtil.dayDiff(endDate, startDate) < 0 ){
			mainThread.logTaskRun("结束日期"+enddateStr+"必须大于开始日期"+startdateStr+"！");
		}
		
		mainThread.logTaskRun("开始执行[DTEngSingleFlyLogTask]任务，" + "，执行参数：ENGSN="+engsn
				+", STARTDATE=" +startdateStr+", ENDDATE=" +enddateStr);
		try{
			EngineFlightLogService engFlylogService = new EngineFlightLogService();
			engFlylogService.extractSingleEngFlightLog(engsn,startDate,endDate);
			
		}catch (Exception e) {
			mainThread.logTaskRun("单台发动机飞行日志数据同步和计算异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
			new CommDMO().releaseContext(ApmsConst.DS_DEFAULT);
		}
		
		mainThread.logTaskRun("单台发动机【"+engsn+"】日志同步执行成功！");
		
	}

}