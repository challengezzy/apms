package com.apms.bs.datatask;

import org.dom4j.Element;

import com.apms.ApmsConst;
import com.apms.bs.intf.omis.OmisDataExtractService;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

/**
 * 获取航班飞行计划日志
 * 自定义数据任务，继承接口DataTaskExecuteIFC
 * zhangzy20160119 不再使用
 *@date Nov 28, 2011
 **/
public class DTExtractFlightScheduler implements DataTaskExecuteIFC{

	/**
	 * 任务执行
	 * task:  任务节点定义
	 * mainThread 主任务线程引用
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String iata_c = task.elementText("PARAM1");
		String co_seq = task.elementText("PARAM2");
		String baseorgid = task.elementText("PARAM3");
		String datenum = task.elementText("PARAM4");
		mainThread.logTaskRun("开始执行[DTExtractFlyLog]任务，" + "，执行参数：param1="+iata_c+", param2=" +co_seq+", param3=" +baseorgid+", param4=" +datenum);
		try{
			
			OmisDataExtractService omisService = new OmisDataExtractService();
			omisService.extractFlightSchedule(iata_c,co_seq,baseorgid,datenum);
			omisService.computeAircraftflyplan();
			
		}catch (Exception e) {
			mainThread.logTaskRun("航班数据提取异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
			new CommDMO().releaseContext(ApmsConst.DS_DEFAULT);
		}
		
		mainThread.logTaskRun("[DTExtractFlyLog]执行成功！");
		
	}

}