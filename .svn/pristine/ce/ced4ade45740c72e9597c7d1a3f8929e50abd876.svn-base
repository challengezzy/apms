package com.apms.bs.datatask;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;
import com.apms.ApmsConst;
import com.apms.bs.intf.omis.DDInfoExtractService;

/**
 * 向Acars报文接口表新增字段数据填充
 * 
 * @date Nov 28, 2011
 **/
public class DTUpdateAcarsDatAheadOfParsingDataTask implements DataTaskExecuteIFC {

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
		mainThread.logTaskRun("开始执行[向Acars报文接口表新增字段数据填充]任务....");
		try {
			String sql=" update a_acars_telegraph a " 
					+" set a.MODELSERIES=(select m.MODELSERIES from b_aircraft ac, b_aircraft_model m where a.ac_id = ac.aircraftsn and m.id = ac.acmodelid)"
					+" ,a.modelcode=(select m.modelcode from b_aircraft ac, b_aircraft_model m where a.ac_id = ac.aircraftsn and m.id = ac.acmodelid)"
					+" ,a.PREPROCESSINT=0"
					+" where a.trans_time>=sysdate-2";
			
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			dmo.commit(ApmsConst.DS_APMS);
			
			
		} catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			mainThread.logTaskRun("[向Acars报文接口表新增字段数据填充]任务执行异常。" + e.getMessage());
		} finally {

			// 释放数据库连接
			dmo.releaseContext(ApmsConst.DS_APMS);
			dmo.releaseContext(ApmsConst.DS_OMIS);
		}
		mainThread.logTaskRun("[向Acars报文接口表新增字段数据填充]任务执行完成！");

	}
}