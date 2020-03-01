

package com.apms.bs.datatask;

import java.util.Date;

import org.dom4j.Element;

import com.apms.ApmsConst;
import com.apms.bs.engine.EngineFlightLogService;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

/**
 * 获取发动机飞行日志
 * 自定义数据任务，继承接口DataTaskExecuteIFC
 * @author zhanghl
 *@date Nov 05, 2014
 **/
public class DTEngFlyLogTask implements DataTaskExecuteIFC{

	/**
	 * 任务执行
	 * task:  任务节点定义
	 * mainThread 主任务线程引用
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String startdateStr = task.elementText("STARTDATE");
		String enddateStr = task.elementText("ENDDATE");
		Date startDate = null;
		if(startdateStr != null && startdateStr.length() > 8){
			startDate = DateUtil.StringToDate(startdateStr, "yyyy-MM-dd");
		}
		
		Date endDate = null;
		if(enddateStr != null && enddateStr.length() > 8){
			 endDate = DateUtil.StringToDate(enddateStr, "yyyy-MM-dd");
		}
		
		//送入空的startdate，表示从数据中的最新数据日期开始计算
		
		mainThread.logTaskRun("开始执行[DTEngFlyLogTask]任务，" + "，执行参数： startDate=" +startdateStr+", endDate=" +enddateStr);
		try{
			EngineFlightLogService engFlylogService = new EngineFlightLogService();
			int num = engFlylogService.extractFlightLog(startDate,endDate);
			mainThread.logTaskRun("本次同步和计算【"+num+"】条发动机航段日志.");
		}catch (Exception e) {
			e.printStackTrace();
			mainThread.logTaskRun("发动机航段日志数据提取异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
			new CommDMO().releaseContext(ApmsConst.DS_DEFAULT);
		}
		
		mainThread.logTaskRun("[DTEngFlyLogTask]执行成功！");
		
	}

}