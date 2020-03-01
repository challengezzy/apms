package com.apms.bs.datatask;

import org.dom4j.Element;

import com.apms.bs.flight.FlightScheduleSynService;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

/**
 * 从OMIS接口表中查出出航班计划数据并同步(新增、更新)到F_FLIGHT_SCHEDULE表中
 * @author jerry
 * @date Mar 25, 2014
 */
public class DTFlightScheduleSynchronise implements DataTaskExecuteIFC{

	@Override
	public void dataTaskExec(Element task, DataTaskExecThread mainThread) throws Exception {

		mainThread.logTaskRun("开始进行航班计划数据同步:");
		
		String dayNum = task.elementText("DAYNUM"); //同步数据天数
		//如果没有设置，则默认同步2天数据
		if(dayNum == null || dayNum.length() ==0 ){
			dayNum = "2";
		}
		
		FlightScheduleSynService synService = new FlightScheduleSynService();
		String synLog = synService.synFlightSchedule( dayNum );
		
		mainThread.logTaskRun("航班计划数据同步完成，"+synLog+"！");
		
	}
	
	

}
