package com.apms.bs.datatask;
import org.dom4j.Element;

import com.apms.ApmsConst;
import com.apms.bs.intf.oldsys.HistoryDataExtractByzhl;
import com.apms.bs.intf.omis.OmisDataExtractFlightLogService;
import com.apms.bs.intf.omis.OmisDataExtractService;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

/**
 * 获取sqlserver上的发动机的飞行历史日志
 * 自定义数据任务，继承接口DTFlightHistoryDataExtracTask
 *@date Nov 28, 2011
 **/
public class DTFlightHistoryEngineDataExtracTask implements DataTaskExecuteIFC{

	/**
	 * 任务执行
	 * task:  任务节点定义
	 * mainThread 主任务线程引用
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String startDate = task.elementText("STARTDATE");
		String endDate = task.elementText("ENDDATE");
		String acnum = task.elementText("ACNUM");
		String isdailyLogImp = task.elementText("ISDAILYLOGIMP");
		String isLogImp = task.elementText("ISLOGIMP");
		
		if(acnum != null && acnum.length()==0){
			acnum = null;
		}
		mainThread.logTaskRun("开始执行[DTFlightHistoryEngineDataExtracTask]任务，" + "，执行参数：startDate="+startDate+", endDate=" +endDate);
		try{
			HistoryDataExtractByzhl omisService = new HistoryDataExtractByzhl();
			
			if("TRUE".equals(isdailyLogImp)){
				//抽取发动机日志
				mainThread.logTaskRun("开始抽取发动机日志");
				omisService.extractEngFlydaily(startDate, endDate);
			}
			
			if("TRUE".equals(isLogImp)){
				//根据daily和飞行日志生成发动机日志
				mainThread.logTaskRun("开始根据daily和飞行日志生成发动机日志");
				omisService.extractEngFlyLog();
			}
			
			//计算发动机分段表里面的总计数据
			mainThread.logTaskRun("开始计算发动机分段表里面的总计数据");
			omisService.computeEngflylogdata();
			
			//计算发动日志daily的增量
			mainThread.logTaskRun("开始计算发动日志daily的增量");
			omisService.computeEngdata();
			
		}catch (Exception e) {
			mainThread.logTaskRun("发动机飞行日志数据提取异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
			new CommDMO().releaseContext(ApmsConst.DS_DEFAULT);
		}
		
		mainThread.logTaskRun("[DTAcFlylogDataTask]执行成功！");
		
	}
}
