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
import com.sep.service.quadrant.QuadrantConfService;

/**
 * 象限配置
 */
public class QuadrantUpdateAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		double x  = new Double(dataValue.get("AXIS_X").toString());
		double x_old = new Double(dataValue.get("OLDX").toString());
		double y = new Double(dataValue.get("AXIS_Y").toString());
		double y_old = new Double(dataValue.get("OLDY").toString());
		String uptuser = dataValue.get("UPDATEUSER").toString();
		
		//判断风险值和频次是否有变更
		if(x==x_old && y==y_old){
			//无变更，不需处理
			return;
		}
		
		//象限的临界值发生了变化,查找象限变化的工卡
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM (SELECT T.ID,T.AMM_NO,");
		sb.append("(CASE WHEN DVALUE>="+x_old+" AND FREQUENCY>= "+y_old+" THEN 1");
		sb.append(" WHEN DVALUE< "+x_old+" AND FREQUENCY>= "+y_old+" THEN 2 ");
		sb.append(" WHEN DVALUE< "+x_old+" AND FREQUENCY< "+y_old+" THEN 3 ");
		sb.append(" WHEN DVALUE>="+x_old+" AND FREQUENCY< "+y_old+" THEN 4 END) QUADRANT_OLD,");
		sb.append("(CASE WHEN DVALUE>="+x+" AND FREQUENCY>= "+y+" THEN 1");
		sb.append(" WHEN DVALUE< "+x+" AND FREQUENCY>= "+y+" THEN 2 ");
		sb.append(" WHEN DVALUE< "+x+" AND FREQUENCY<  "+y+" THEN 3 ");
		sb.append(" WHEN DVALUE>="+x+" AND FREQUENCY<  "+y+" THEN 4 END) QUADRANT_NEW,");
		sb.append("T.DVALUE,T.FREQUENCY FROM U_JOBCARD_BASIC T) WHERE QUADRANT_OLD != QUADRANT_NEW ");
		
		String inSql = "INSERT INTO U_JOBCARD_QUADRANTHIS(ID,JOBCARDID,AMM_NO,NEWQUADRANT,OLDQUADRANT,REASON,REASONTYPE,UPDATETIME,UPDATEUSER) "
			+ "VALUES(S_U_JOBCARD_QUADRANTHIS.NEXTVAL,?,?,?,?,?,2,SYSDATE,?)";
		
		CommDMO dmo = new CommDMO();
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		
		QuadrantConfService quaService = new QuadrantConfService();
		String reason = "象限临界值变更. 临界新值：风险["+x+"],频次["+y+"],临界原值：风险["+x_old+"],频次["+y_old+"]";
		
		for(HashVO vo : vos){
			//记录象限变更历史
			String cardid = vo.getStringValue("ID");
			String ammno = vo.getStringValue("AMM_NO");
			double dval = vo.getDoubleValue("DVALUE");
			double fval = vo.getDoubleValue("FREQUENCY");
			
			int quaOld = quaService.getQuadrantValue(dval, fval,x_old,y_old);
			int quaNew = quaService.getQuadrantValue(dval, fval,x,y);
			logger.info(ammno + reason);
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, inSql, cardid,ammno,quaNew,quaOld,reason,uptuser);
		}
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
