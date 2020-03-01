package com.apms.bs.workplan.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

import com.apms.ApmsConst;
import com.apms.vo.ApmsVarConst;

/**
 * 服务端拦截器实现类
 */
public class ACWorkPlanUpdateAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		CommDMO dmo = new CommDMO();
		
		//double weightAdd = new Double(dataValue.get("WEIGHT_ADD").toString());
		//double momentAdd = new Float(dataValue.get("MOMENT_ADD").toString());
		String acnum = dataValue.get("ACNUM").toString();
		
		//后置拦截器，此时状态判断不出是否变化
//		String wpId = dataValue.get("ID").toString();
//		String workState = dataValue.get("WORK_STATE").toString();
		
//		String temp = "SELECT WORK_STATE FROM F_WEIGHT_WORKPLAN WHERE ID= " + wpId;
//		HashVO[] vos1 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, temp);
		//判断未更新状态和更新之前状态，如果一致，则不做处理
//		int beforState = vos1[0].getIntegerValue("WORK_STATE");
//		int curState = ConvertWorkStateToDic(workState);
//		
//		if(beforState == curState){
//			logger.debug("工作计划ID["+wpId+"]状态未变化，不处理!");
//			return;
//		}
//		
//		//状态由未完成-》取消 或 取消-》未完成， 也不需要计算
//		if( (beforState== ApmsVarConst.PLAN_WORKSTATE_NOCOMPLETE && curState == ApmsVarConst.PLAN_WORKSTATE_CANCELED)
//			|| (curState== ApmsVarConst.PLAN_WORKSTATE_NOCOMPLETE && beforState == ApmsVarConst.PLAN_WORKSTATE_CANCELED)
//			){
//			logger.debug("工作计划ID["+wpId+"]状态由未完成-》取消 或 取消-》未完成， 不需要计算!");
//			return;
//		}
		
		//把本次更新结果提交掉，然后进行计算，很重要！！！！
		dmo.commit(ApmsConst.DS_APMS);
		
		//查询该
		String sql1 = "SELECT ID,WEIGHT_BASE,MOMENT_BASE,TO_CHAR(WEIGHT_DATE,'YYYY-MM-DD HH24:mi:ss') WEIGHT_DATE," +
				"MODIFY_POSTION,K_VALUE FROM B_AIRCRAFT T WHERE AIRCRAFTSN = '" + acnum + "'";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql1);
		double weightBase = vos[0].getDoubleValue("WEIGHT_BASE");
		double momentBase = vos[0].getDoubleValue("MOMENT_BASE");
		double modifyPosition = vos[0].getDoubleValue("MODIFY_POSTION");
		double kValue = vos[0].getDoubleValue("K_VALUE");
		String acId = vos[0].getStringValue("ID");
		String weightDateStr = vos[0].getStringValue("WEIGHT_DATE");
		
		double gravity = (momentBase/weightBase-modifyPosition)/kValue;
		//查询当前称重日期之后，所有已完成的工作计划，对重心重新计算,结合称重不需要计算
		String queryPlan = "SELECT WP.ID,W.WEIGHT_ADD,W.MOMENT_ADD,WP.WORK_DATE FROM F_WEIGHT_WORKPLAN WP,B_WEIGHT_WORK W "
			+ " WHERE W.ID=WP.WEIGHT_WORK_ID AND WORK_STATE=1 AND WORK_CLOSE_ATT=0 AND ACNUM="+acId
			+ " AND WORK_DATE >= to_date('"+weightDateStr+"','YYYY-MM-DD HH24:mi:ss') ORDER BY WORK_DATE,WORK_CODE";
		
		ArrayList<String> updateSqlList = new ArrayList<String>();
		HashVO[] wpVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, queryPlan);
		//从基准重心开始计算
		double weightCur = weightBase;
		double momentCur = momentBase;
		for(int i=0;i<wpVos.length;i++){
			HashVO wpvo = wpVos[i];
			String workplanId = wpvo.getStringValue("ID");
			double weightAdd = new Double(wpvo.getStringValue("WEIGHT_ADD"));
			double momentAdd = new Double(wpvo.getStringValue("MOMENT_ADD"));
			//计算计划完成前、后重心
			double weightBefore = weightCur;
			double momentBefore = momentCur;
			double gravityBefore = (momentBefore/weightBefore-modifyPosition)/kValue;
			
			double weightAfter = weightCur + weightAdd;
			double momentAfter = momentCur + momentAdd;
			double gravityAfter = (momentAfter/weightAfter-modifyPosition)/kValue;
			
			String tmpSql = "update f_weight_workplan set weight_before="+weightBefore+",moment_before="+momentBefore+",weight_after="+weightAfter
				+",moment_after="+momentAfter+",gravity_before="+gravityBefore+",gravity_after="+gravityAfter+",gravity_base="+gravity+",WEIGHT_BASE=" +weightBase 
				+" where id=" + workplanId;
			
			updateSqlList.add(tmpSql);
			
			//当前重心已发生改变
			weightCur = weightAfter;
			momentCur = momentAfter;
		}
		
		dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
		double gravityCur = (momentCur/weightCur - modifyPosition) / kValue;
		//更新飞机信息
		String updateSql = "UPDATE B_AIRCRAFT T SET T.WEIGHT_CURRENT="+weightCur+",T.MOMENT_CURRENT="+momentCur
						+",CURRENT_GRAVITY="+gravityCur+" WHERE T.AIRCRAFTSN='"+acnum+"'";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql);
		
		logger.debug("更新飞机重量、力矩属性成功！");
	}
	
//	private int ConvertWorkStateToDic(String workstateName){
//		if("未完成".equals(workstateName)){
//			return ApmsVarConst.PLAN_WORKSTATE_NOCOMPLETE;
//		}else if("完成".equals(workstateName)){
//			return ApmsVarConst.PLAN_WORKSTATE_COMPLETED;
//		}else if("取消".equals(workstateName)){
//			return ApmsVarConst.PLAN_WORKSTATE_CANCELED;
//		}
//		
//		return ApmsVarConst.PLAN_WORKSTATE_NOCOMPLETE;
//	}

	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

	}

}
