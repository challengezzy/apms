package com.sep.service.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

import com.apms.ApmsConst;
import com.sep.service.quadrant.QuadrantConfService;
import com.sep.service.quadrant.QuadrantConfVo;

/**
 * 服务端拦截器实现类
 */
public class JobcardUpdateAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		double dval = new Double(dataValue.get("DVALUE").toString());
		double dval_old = new Double(dataValue.get("DVALUE_OLD").toString());
		double fval = new Double(dataValue.get("FREQUENCY").toString());
		double fval_old = new Double(dataValue.get("FREQUENCY_OLD").toString());
		
		//判断风险值和频次是否有变更
		if(dval==dval_old && fval==fval_old){
			//无变更，不需处理
			return;
		}
		
		QuadrantConfService quaService = new QuadrantConfService();
		//判断像限是否发生了变化
		QuadrantConfVo confvo = quaService.getQuadrant();
		int quaOld = quaService.getQuadrantValue(dval_old, fval_old, confvo.getAxisX(),confvo.getAxisY());
		int quaNew = quaService.getQuadrantValue(dval, fval,confvo.getAxisX(),confvo.getAxisY());
		
		String inSql = "INSERT INTO U_JOBCARD_QUADRANTHIS(ID,JOBCARDID,AMM_NO,NEWQUADRANT,OLDQUADRANT,REASON,REASONTYPE,UPDATETIME,UPDATEUSER) "
			+ "VALUES(S_U_JOBCARD_QUADRANTHIS.NEXTVAL,?,?,?,?,?,1,SYSDATE,?)";
		
		if(quaNew != quaOld){
			//记录象限变更历史
			String cardid = dataValue.get("ID").toString();
			String ammno = dataValue.get("AMM_NO").toString();
			String uptuser = dataValue.get("UPDATEUSER").toString();
			String reason = "风险或频次变更.  新值：风险["+dval+"]，频次["+fval+"], 原值：风险["+dval_old+"],频次["+fval_old+"].";
			logger.info(ammno + reason);
			CommDMO dmo = new CommDMO();
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, inSql, cardid,ammno,quaNew,quaOld,reason,uptuser);
			dmo.commit(ApmsConst.DS_APMS);
		}

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
