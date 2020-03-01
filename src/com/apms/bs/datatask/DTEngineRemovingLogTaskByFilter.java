
package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.ApmsConst;
import com.apms.bs.intf.oldsys.HistoryDataExtractByzhl;

/**
 * 发动机拆换测试任务
 *@date Nov 28, 2011
 **/
public class DTEngineRemovingLogTaskByFilter implements DataTaskExecuteIFC{

	/**
	 * 发动机拆换日志执行
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		//String rptno = task.elementText("REPORTNO");
		//String acnum = task.elementText("ACNUM");
		//执行的时候 包含begindate，不包含enddate
		String beginDate = task.elementText("BEGINDATE");
		String endDate = task.elementText("ENDDATE");
		
		
		if("".equals(beginDate))
			beginDate = null;
		
		if("".equals(endDate))
			endDate = null;
		
		String logStr = "";
		mainThread.logTaskRun("开始执行[DTEngineRemovingLogTaskByFilter]任务....");
		try{
			//DataParseService service = DataParseService.getInstance();
			HistoryDataExtractByzhl fromsqldata= new HistoryDataExtractByzhl();
			
			fromsqldata.extractAcFlyLog(beginDate,endDate,null);//飞行日志分段表
			
			fromsqldata.extractAcFlydaily(beginDate,endDate,null);//飞行日志daily
			
			fromsqldata.extractEngFlydaily(beginDate,endDate);//发动机DAILY
			
			fromsqldata.computeAcflydata();//计算飞行日志daily的增量
			
			fromsqldata.computeEngdata();//计算发动日志daily的增量
			
			fromsqldata.computeAcflylogdata();//计算飞行日志的分段总计
			
			fromsqldata.computeEngflylogdata();//计算发动机分段表里面的总计数据
		
			mainThread.logTaskRun(logStr);
			
		}catch (Exception e) {
			mainThread.logTaskRun("发动机拆换日志执行异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
			new CommDMO().releaseContext(ApmsConst.DS_DEFAULT);
		}
				
		mainThread.logTaskRun("[DTReportParseTask]任务执行完成！");
		
	}
	
	
	public void dataTaskExec2(Element task,DataTaskExecThread mainThread) throws Exception {
		
	}

}