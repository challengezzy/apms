package com.vsms.bs.user.interceptor;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;
import com.apms.ApmsConst;

/**
 * 服务端拦截器实现类
 */
public class BCDeletePubUserBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("xx2");
	}

	/**
	 * 不常用 ，待查证
	 */
	public void doSomething(Map<String, Object> map) throws Exception {
		System.out.println("xx2");
	}

	/**
	 * 待查证
	 */
	public void doSomething(List<Map<String, Object>> dataValueList) throws Exception {
		CommDMO dmo = new CommDMO();
		String loginname=null;
		
		//对象值 {pkvalue=301, flag=delete, table=B_USER, datasource=datasource_default, pkname=ID}
		for(Map<String, Object> dataMap : dataValueList){
			String userid = dataMap.get("pkvalue").toString();
			String delsql1 ="DELETE PUB_USER WHERE LOGINNAME=(SELECT LOGINNAME FROM B_USER BU WHERE BU.ID= "+userid+" )";
			String delsql2 = "DELETE B_USER_SKILL WHERE USERID = " + userid;
			String delsql3 = "DELETE B_USER_CAPACITY WHERE USERID = 1" + userid;
			
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delsql1);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delsql2);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delsql3);
			logger.debug("删除用户userid=["+userid+"]成功！");
		}
		
	}

}
