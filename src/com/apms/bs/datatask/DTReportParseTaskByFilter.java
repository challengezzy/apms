package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.DataParseService;
import com.apms.vo.ApmsVarConst;

/**
 * 报文内容解析任务
 *@date Nov 28, 2011
 **/
public class DTReportParseTaskByFilter implements DataTaskExecuteIFC{

	/**
	 * 报文内容解析执行
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String rptno = task.elementText("REPORTNO");
		String acnum = task.elementText("ACNUM");
		String beginDate = task.elementText("BEGINDATE");
		String endDate = task.elementText("ENDDATE");
		String isOrderBy = task.elementText("ISORDERBY");
		
		if("".equals(rptno))
			rptno = null;
		
		if("".equals(acnum))
			acnum = null;
		
		if("".equals(beginDate))
			beginDate = null;
		
		if("".equals(endDate))
			endDate = null;
		
		boolean isorder = false;
		if(isOrderBy != null || "true".equals(isOrderBy)){
			isorder = true;
		}
		
		String logStr = "";
		mainThread.logTaskRun("开始执行[DTReportParseTask]任务....");
		try{
			DataParseService service = DataParseService.getInstance();
			
			if(ApmsVarConst.RPTNO_A13.equalsIgnoreCase(rptno) || ApmsVarConst.RPTNO_R13.equalsIgnoreCase(rptno)
				|| ApmsVarConst.RPTNO_A14.equalsIgnoreCase(rptno) || ApmsVarConst.RPTNO_R14.equalsIgnoreCase(rptno)
			 ){
				//解析APU报文
				logStr = service.parseApuAcarsReport(acnum,beginDate,endDate);
				
			}else if(ApmsVarConst.RPTNO_WRN.equals(rptno)||ApmsVarConst.RPTNO_FLR.equals(rptno)
					||ApmsVarConst.RPTNO_MPF.equals(rptno)||ApmsVarConst.RPTNO_SMD.equals(rptno)){
				//解析cfd报文
				logStr = service.parseCFDAcarsReport(rptno,acnum,beginDate,endDate);
			}else{
				//解析指定类型和飞机号的报文数据
				logStr = service.parseAcarsReport(null,rptno,acnum,beginDate,endDate,isorder);
			}
			
			mainThread.logTaskRun(logStr);
			
		}catch (Exception e) {
			mainThread.logTaskRun("报文解析任务执行异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
				
		mainThread.logTaskRun("[DTReportParseTask]任务执行完成！");
		
	}
	
	
	public void dataTaskExec2(Element task,DataTaskExecThread mainThread) throws Exception {
		
	}

}