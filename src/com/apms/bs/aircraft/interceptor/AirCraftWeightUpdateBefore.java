package com.apms.bs.aircraft.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

/**
 * 服务端拦截器实现类
 */
public class AirCraftWeightUpdateBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		CommDMO dmo = new CommDMO();
		
		double weightBase = new Double(dataValue.get("WEIGHT_BASE").toString());
		double momentBase = new Double(dataValue.get("MOMENT_BASE").toString());
		
		double modifyPosition = new Double(dataValue.get("MODIFY_POSTION").toString());
		double kValue = new Double(dataValue.get("K_VALUE").toString());
		String acId = dataValue.get("ID").toString();
		
		String weightDateStr = dataValue.get("WEIGHT_DATE").toString();
		Date weightDate = DateUtil.StringToDate(weightDateStr, "yyyy-MM-dd HH:mm:ss");
		
		//基准重心计算gravity= (力臂RC-MODIFY_POSTION)/K_VALUE(K值),力臂RC= MOMENT_BASE/WEIGHT_BASE
		
		double gravity = (momentBase/weightBase-modifyPosition)/kValue;
		
		//查询当前称重日期之后，所有已完成的工作计划，对重心重新计算
		String temp = "SELECT WP.ID,W.WEIGHT_ADD,W.MOMENT_ADD,WP.WORK_DATE FROM F_WEIGHT_WORKPLAN WP,B_WEIGHT_WORK W "
			+ " WHERE W.ID=WP.WEIGHT_WORK_ID AND WORK_STATE=1 AND ACNUM="+acId
			+ " AND WORK_DATE > to_date('"+weightDateStr+"','YYYY-MM-DD HH24:mi:ss') ORDER BY WORK_DATE,WORK_CODE";
		
		ArrayList<String> updateSqlList = new ArrayList<String>();
		HashVO[] wpVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, temp);
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
				+",moment_after="+momentAfter+",gravity_before="+gravityBefore+",gravity_after="+gravityAfter+",gravity_base="+gravity
				+" where id=" + workplanId;
			
			updateSqlList.add(tmpSql);
			
			//当前重心已发生改变
			weightCur = weightAfter;
			momentCur = momentAfter;
		}
		double gravityCur = (momentCur/weightCur - modifyPosition) / kValue;
		
		//更新当前重心信息
		dataValue.put("WEIGHT_CURRENT", weightCur);
		dataValue.put("MOMENT_CURRENT", momentCur);
		dataValue.put("CURRENT_GRAVITY", gravityCur);
		
		dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
		logger.debug("飞机当前重心更新完成！");
	}
	
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
