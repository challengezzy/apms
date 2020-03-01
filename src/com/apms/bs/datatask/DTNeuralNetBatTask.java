package com.apms.bs.datatask;

import org.dom4j.Element;

import com.apms.bs.neuralnet.NeuralNetTrainService;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

public class DTNeuralNetBatTask implements DataTaskExecuteIFC{

	/**
	 * @param task
	 *            定义该报文的参数XML
	 * @param mainThread
	 *            执行任务的主线程
	 */
	@Override
	public void dataTaskExec(Element task, DataTaskExecThread mainThread) throws Exception {
		// TODO Auto-generated method stub
		String batFile = task.elementText("BATFILE");
		String module = task.elementText("MODULE"); //模块 APU/ENGINE/AIRCONDITION
		String type = task.elementText("TYPE"); //类型： 样本训练/数据诊断
		mainThread.logTaskRun("开始神经网络任务,模块["+module+"],类型["+type+"],执行文件["+batFile+"].");
		NeuralNetTrainService service = new NeuralNetTrainService();
		service.neuralNetBatExec(batFile);
		
		mainThread.logTaskRun("神经网络任务执行完毕,模块["+module+"],类型["+type+"]。");
	}

}
