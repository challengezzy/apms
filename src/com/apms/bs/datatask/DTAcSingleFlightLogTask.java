
package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.ApmsConst;
import com.apms.bs.intf.omis.OmisDataExtractFlightLogService;

/**
 * 获取单架飞机的飞行日志
 * 自定义数据任务，继承接口DataTaskExecuteIFC
 * @author zhanghl
 *@date Nov 05, 2014
 **/
public class DTAcSingleFlightLogTask implements DataTaskExecuteIFC{

	/**
	 * 任务执行
	 * task:  任务节点定义
	 * mainThread 主任务线程引用
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String acnum = task.elementText("ACNUM");
		String omisstartdate = task.elementText("STARTDATE");
		String baseorgid = task.elementText("BASEORGID");
		
		
		mainThread.logTaskRun("开始执行[DTAcSingleFlightLogTask]任务，" + "，执行参数：acnum="+acnum+", baseorgid=" +baseorgid+", omisstartdate=" +omisstartdate);
		try{
			OmisDataExtractFlightLogService omisService = new OmisDataExtractFlightLogService();
			omisService.extractSingleACFlightLogStart(acnum,omisstartdate, baseorgid);
			
		}catch (Exception e) {
			mainThread.logTaskRun("单架飞机飞行日志数据提取异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
			new CommDMO().releaseContext(ApmsConst.DS_OMIS);
		}
		
		mainThread.logTaskRun("[DTAcSingleFlightLogTask]执行成功！");
		
	}

}