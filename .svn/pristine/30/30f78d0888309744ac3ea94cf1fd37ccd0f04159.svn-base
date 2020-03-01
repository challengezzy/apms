package com.apms.bs.datatask;

import java.util.Date;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import smartx.framework.common.vo.NovaLogger;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.bs.neuralnet.DiagnoseDataSerivce;
import com.apms.bs.util.DateUtil;

public class DTNeuralNetApuDiagnoseDelTask implements DataTaskExecuteIFC {

	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	@Override
	public void dataTaskExec(Element task, DataTaskExecThread mainThread) throws Exception {
		String baseOrgName = task.elementText("BASEORGNAME");
		String apumodel = task.elementText("APUMODEL");
		String acnum = task.elementText("ACNUM");
		String date_begin = task.elementText("BEGINDATE");
		String date_end = task.elementText("ENDDATE");
		
		Date beginDate = null;
		Date endDate = null;
		
		if ( date_begin != null && !date_begin.equals("")){
			try{
				beginDate = DateUtil.StringToDate(date_begin, "yyyy-MM-dd");				
			}catch (Exception e) {
				logger.debug("BEGINDATE转换为日期异常！");
				throw e;
			}
		}
		if (date_end != null && !date_end.equals("")){
			try{
				endDate = DateUtil.StringToDate(date_end, "yyyy-MM-dd");
			}catch (Exception e) {
				logger.debug("ENDDATE转换为日期异常！");
				throw e;
			}
		}
		
		if("".equals(baseOrgName)){
			baseOrgName = null;
		}
		if("".equals(apumodel)){
			apumodel = null;
		}
		if("".equals(acnum)){
			acnum = null;
		}
		
		mainThread.logTaskRun("开始执行APU诊断数据删除任务....");
		DiagnoseDataSerivce service = new DiagnoseDataSerivce();
		int rows = service.deleteApuDiagnoseData(baseOrgName, acnum, apumodel, beginDate, endDate);
		
		mainThread.logTaskRun("结束执行APU诊断数据删除，本次删除数据["+ rows +"]条");
		
	}
	

}
