
package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.ApmsConst;
import com.apms.bs.intf.omis.OmisDataExtractFlightLogServiceZzy;

/**
 * 获取飞机飞行日志
 * 自定义数据任务，继承接口DataTaskExecuteIFC
 *@date Nov 28, 2011
 **/
public class DTAcFlylogDataTask implements DataTaskExecuteIFC{

	/**
	 * 任务执行
	 * task:  任务节点定义
	 * mainThread 主任务线程引用
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String baseorgid = task.elementText("BASEORGID");//基地ID
		String omisstartdate = task.elementText("OMISSTARTDATE");
		String dayOff = task.elementText("DAYOFF");//向前取数据的天数
		String acnum = task.elementText("ACNUM");
		
		mainThread.logTaskRun("开始执行[DTAcFlylogDataTask]任务，" + ",参数：dayOff="+dayOff+",BASEORGID=" +baseorgid+", OMISSTARTDATE=" +omisstartdate);
		try{
			OmisDataExtractFlightLogServiceZzy omisService = new OmisDataExtractFlightLogServiceZzy();
			omisService.extractFlightLog(baseorgid,dayOff,acnum);
			
		}catch (Exception e) {
			e.printStackTrace();
			mainThread.logTaskRun("飞机飞行日志数据提取异常。" + e.getMessage());
		}finally{
			//释放数据库连接
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
			new CommDMO().releaseContext(ApmsConst.DS_OMIS);
		}
		
		mainThread.logTaskRun("[DTAcFlylogDataTask]执行成功！");
		
	}

}