package com.apms.bs.datatask;

import org.dom4j.Element;

import com.apms.ApmsConst;
import com.apms.bs.neuralnet.NeuralnetAlarmMsgService;

import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

public class DTNeuralNetApuAlarmTask implements DataTaskExecuteIFC{

	/**
	 * @param task
	 *            定义该报文的参数XML
	 * @param mainThread
	 *            执行任务的主线程
	 */
	@Override
	public void dataTaskExec(Element task, DataTaskExecThread mainThread) throws Exception {
		
		mainThread.logTaskRun("开始神经网络诊断数据告警任务 ");
		NeuralnetAlarmMsgService service = new NeuralnetAlarmMsgService();
		try{
			service.ApuDiagnoseDataAlarm();
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
		mainThread.logTaskRun("神经网络诊断数据告警任务执行完毕 ");
	}

}

