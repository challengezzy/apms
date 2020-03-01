package com.sep.service.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;


import com.apms.ApmsConst;

/**
 * 工单录入的后置处理，自动生成任务单
 */
public class WorkorderInsertAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
		String srcOrderSn = (String)dataValue.get("SRCORDERSN");//源头指令号，即例行工卡编号
		String workorderId = dataValue.get("ID").toString();
		String updateUser = (String)dataValue.get("UPDATEUSER");//操作人
		//假如有数据，删除数据日期之后的数据
		
		if(srcOrderSn == null || srcOrderSn.trim().length() == 0){
			//源头指令号为空,不处理
			return;
		}
		
		String qrySql = "SELECT J.ID JOBCARDID,J.AMM_NO,T.ID ROUTINEORDERID,T.ORDERSN SRCORDERSN"
		  + " FROM U_ROUTINEORDER T,U_REL_ORDERCARD R,U_JOBCARD_BASIC J "
		  + " WHERE R.ORDERID = T.ID AND J.ID = R.JOBCARDID AND T.ORDERSN = '"+srcOrderSn+"' ";
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrySql);
		String insertSql = "INSERT INTO U_TASKORDER_RECORD(ID,WORKORDERID,JOBCARDID,AMM_NO,ISTRAINING,WORKSTATE,UPDATETIME,UPDATEUSER)"
					+ " VALUES(S_U_TASKORDER_RECORD.NEXTVAL,?,?,?,0,10,SYSDATE,? )";
		for(int i=0;i<vos.length;i++){
			String jobcardid = vos[i].getStringValue("JOBCARDID");
			String amm_no = vos[i].getStringValue("AMM_NO");
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,workorderId,jobcardid,amm_no, updateUser);
		}
		logger.info("该工单关联的源头指令号有关联A3卡，自动生成["+ vos.length +"]条任务单.");
		dmo.commit(ApmsConst.DS_APMS);	
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		
	}

	/**
	 * 不常用 ，待查证
	 */
	public void doSomething(Map<String, Object> map) throws Exception {
		
	}

	/**
	 * 待查证
	 */
	public void doSomething(List<Map<String, Object>> dataValueList) throws Exception {

	}

}
