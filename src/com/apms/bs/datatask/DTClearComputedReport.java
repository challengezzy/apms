package com.apms.bs.datatask;

import java.util.Date;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;
import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.acarsreport.AcarsRptReparsingService;
import com.apms.bs.util.DateUtil;

/**
 * 清理已计算数据，以便重新计算
 *@date Nov 28, 2011
 **/
public class DTClearComputedReport implements DataTaskExecuteIFC{
	private Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {

		String rptno = task.elementText("REPORTNO");
		String acnum = task.elementText("ACNUM");
		String date_begin = task.elementText("BEGINDATE");
		String date_end = task.elementText("ENDDATE");
		String engineType = task.elementText("ENGINETYPE");

		Date beginDate = null;
		Date endDate = null;
		
		if ( date_begin != null && !date_begin.equals("")){
			try{
				beginDate = DateUtil.StringToDate(date_begin, "yyyy-MM-dd");				
			}catch (Exception e) {
				logger.debug("date_utc转换为日期异常！");
			}
		}
		if ( date_end != null && !date_end.equals("") ){
			try{
				endDate = DateUtil.StringToDate(date_end, "yyyy-MM-dd");
			}catch (Exception e) {
				logger.debug("date_utc转换为日期异常！");
			}
		}
		
		CommDMO dmo = new CommDMO();
		
		if("".equals(rptno))
			rptno = null;
		
		if("".equals(acnum))
			acnum = null;
		
		mainThread.logTaskRun("开始执行[DTClearComputedReport]任务....");
		try{
			AcarsRptReparsingService reparseService = new AcarsRptReparsingService();
			
			reparseService.clearComputedReport(rptno, acnum,engineType,beginDate,endDate);
			dmo.commit(ApmsConst.DS_APMS);
		}catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			mainThread.logTaskRun("报文解析任务执行异常。" + e.getMessage());
		}finally{
			
			//释放数据库连接
			dmo.releaseContext(ApmsConst.DS_APMS);
			dmo.releaseContext(ApmsConst.DS_DEFAULT);
		}
				
		mainThread.logTaskRun("[DTClearComputedReport]任务执行完成！");
		
	}

}