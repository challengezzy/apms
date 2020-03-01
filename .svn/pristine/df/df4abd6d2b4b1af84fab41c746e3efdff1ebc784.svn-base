package com.apms.bs.datatask;

import java.util.Date;

import org.dom4j.Element;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;
import com.apms.ApmsConst;
import com.apms.bs.FlightFormService;

/**
 * 飞机状态添加(深航飞机添加靠桥)数据同步任务
 * 
 * @date Nov 28, 2011
 **/
public class DTAcStateFeedbackDataTask implements DataTaskExecuteIFC {

	/**
	 * @param task
	 *            定义该报文的参数XML
	 * @param mainThread
	 *            执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task, DataTaskExecThread mainThread)
			throws Exception {
		
		CommDMO dmo = new CommDMO();
		mainThread.logTaskRun("开始执行[飞机状态添加(深航飞机添加靠桥)]任务....");
		try {
			//查找深航需要飞机状态添加靠桥状态的数据
			String sql="select flt_pk,flightno from f_flight_schedule f where f.arr_apt = any('PVG', 'SHA') and f.flt_date > trunc(sysdate - 10)"
				+" and f.flightno like 'ZH%' and f.ac_state=60 and sysdate>=ON_TIME+7/24*60";
			HashVO[] vos=dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			FlightFormService fs=new FlightFormService();
			String state="70";
			Date stateTime=new Date();
			String reporter="system";
			String flightchgcomment="自动添加";
			String user="user";
			for(int i=0;i<=vos.length;i++){
				String flightid=vos[i].getStringValue("flt_pk").toString();
				String flightno=vos[i].getStringValue("flightno").toString();
				fs.flightACStateAdd(flightid, flightno, state, stateTime, reporter, flightchgcomment, user);
				mainThread.logTaskRun("航班["+flightno+"]飞机状态添加(深航飞机添加靠桥)成功。" );
			}
		} catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			mainThread.logTaskRun("飞机状态添加(深航飞机添加靠桥)任务执行异常。" + e.getMessage());
		} finally {
			// 释放数据库连接
			dmo.releaseContext(ApmsConst.DS_APMS);
			dmo.releaseContext(ApmsConst.DS_OMIS);
		}
		mainThread.logTaskRun("[飞机状态添加(深航飞机添加靠桥)]任务执行完成！");

	}

}