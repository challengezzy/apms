package com.apms.bs.datatask;

import org.dom4j.Element;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

import com.apms.bs.apu.ApuPdiService;

/**
 * APU的PDI预测值计算
 * @author jerry
 * @date Dec 11, 2016
 */
public class DTApuPdiPredictTask implements DataTaskExecuteIFC{


	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		mainThread.logTaskRun("开始执行APU PDI预测计算任务..");
		
		int periodLength = new Integer(task.elementText("PERIODDAY"));
		int periodNum = new Integer(task.elementText("PERIODNUM"));
		int zeroLimit = new Integer(task.elementText("ZEROLIMIT"));;
		int oldPdiLimit = new Integer(task.elementText("OLDPDILIMIT"));;
		
		mainThread.logTaskRun("计算参数,周期长度["+periodLength+"]天,预测计算["+periodNum+"]个周期");
		
		ApuPdiService service = new ApuPdiService();
		service.computeAllPdiPredict(periodLength,periodNum,zeroLimit,oldPdiLimit);
		
		mainThread.logTaskRun("执行APU PDI预测计算任务结束.");
	}

}