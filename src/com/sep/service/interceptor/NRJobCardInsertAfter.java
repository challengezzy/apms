package com.sep.service.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import smartx.framework.metadata.vo.ComBoxItemVO;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.framework.metadata.vo.RefItemVO;
import smartx.publics.form.bs.service.FormInterceptor;


import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

/**
 * 工单录入的后置处理，自动生成任务单
 */
public class NRJobCardInsertAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
		
		
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		System.out.println("xxx");
	}

	/**
	 * 不常用 ，待查证
	 */
	public void doSomething(Map<String, Object> map) throws Exception {
		System.out.println("xxx");
	}

	/**
	 * 09号模板新增更新的调用到
	 */
	public void doSomething(List<Map<String, Object>> dataValueList) throws Exception {
		//新增非例行工单时，生成维护工单
		for(Map<String, Object> dataValue : dataValueList){
			String formservice_templetcode = dataValue.get("FORMSERVICE_TEMPLETCODE").toString();
			String flag = (String)dataValue.get("FORMSERVICE_MODIFYFLAG");
			if("T_U_NONROUTINE_JOBCARD".equalsIgnoreCase(formservice_templetcode) && "insert".equals(flag)){
				String cardno = (String)dataValue.get("CARDNO");//指令号
				String acnum = (String)dataValue.get("ACNUM");
				String faultdesc = (String)dataValue.get("FAULTDESC");//故障描述
				String originatingdoc = (String)dataValue.get("ORIGINATINGDOC");//源头指令号
				String station = (String)dataValue.get("STATION");//维修站点
				String groundtime = (String)dataValue.get("GROUNDTIME");//预计停声时间
				String plan_mh = (String)dataValue.get("PLAN_MH");//计划工时
				
				String updateUser = (String)dataValue.get("UPDATEUSER");//操作人
				
				String insertSql = "INSERT INTO U_WORKORDER(ID,WORKORDERSN,SRCORDERSN,ACNUM,CONTEXTCN,OPSITE" +
						",SCHHOUR,MH,STATE,ORDERTYPE,UPDATETIME,UPDATEUSER) " +
						" VALUES(S_U_WORKORDER.NEXTVAL,?,?,?,?,?,?,?,0,2,SYSDATE,?)";
				
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,cardno,originatingdoc,acnum,faultdesc
						,station,groundtime,plan_mh,updateUser);
				
				logger.info("非例行工卡生成维修工单成功.");
				dmo.commit(ApmsConst.DS_APMS);
				
			}
			
		}
	}

}
