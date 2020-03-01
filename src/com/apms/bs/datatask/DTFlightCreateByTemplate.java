package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.ApmsConst;
import com.apms.bs.flight.FlightManageService;

/**
 * 根据计划模板生成航班计划数据
 * @author jerry
 * @date Apr 23, 2014
 */
public class DTFlightCreateByTemplate implements DataTaskExecuteIFC{

	/**
	 * @param task 定义该报文的参数XML
	 * @param mainThread 执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {

		String flightTempName = task.elementText("TEMPLATENAME");
		String dayNumStr = task.elementText("DAYNUM");
		String main_dep = task.elementText("MAIN_DEP");
		String main_arr = task.elementText("MAIN_ARR");
		
		int daynum = new Integer(dayNumStr);
		
		CommDMO dmo = new CommDMO();
		
		if(main_dep == null || "".equals(main_dep) ){
			main_dep = "TR";
		}
		if(main_arr == null || "".equals(main_arr)){
			main_arr = "TR";
		}
		
		try{
			FlightManageService service = new FlightManageService();
			String result = service.flightCreateServer(flightTempName, daynum,main_dep,main_arr);
			mainThread.logTaskRun(result);
		}catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			mainThread.logTaskRun("生成航班计划异常。" + e.getMessage());
		}finally{
			
			//释放数据库连接
			dmo.releaseContext(ApmsConst.DS_APMS);
			dmo.releaseContext(ApmsConst.DS_DEFAULT);
		}
				
		mainThread.logTaskRun("[DTFlightCreateByTemplate]任务执行完成！");
		
	}

}