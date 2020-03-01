package com.apms.bs.flight.interceptor;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.apms.ApmsConst;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

/**
 * 服务端拦截器实现类
 */
public class InsertAirportDeviceBeforeAndUpdate implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
		//新增设备是工作梯的机场设备时也向工作梯表中插入一条数据
		CommDMO dmo = new CommDMO();
		String workladderid=dataValue.get("ID").toString();
		String platform_height=null;
		if(dataValue.get("PLATFORM_HEIGHT")!=null){
			platform_height=dataValue.get("PLATFORM_HEIGHT").toString();
		}
		String heightdesc=null;
		if(dataValue.get("HEIGHTDESC")!=null){
			heightdesc=dataValue.get("HEIGHTDESC").toString();
		}
		String platform_area=null;
		if(dataValue.get("PLATFORM_AREA")!=null){
			platform_area=dataValue.get("PLATFORM_AREA").toString();
		}
		String isguardbar;
		if("是".equals(dataValue.get("ISGUARDBAR").toString())){
			isguardbar="1";
		}else{
			isguardbar="0";
		}
		if(dataValue.get("FORMSERVICE_MODIFYFLAG").toString().equals("insert")){//新增时的操作
			String insertSql="INSERT INTO B_WORKLADDER(ID,PLATFORM_HEIGHT,HEIGHTDESC,PLATFORM_AREA,ISGUARDBAR) VALUES(?,?,?,?,?)";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,workladderid,platform_height,heightdesc,platform_area,isguardbar);
			dmo.commit(ApmsConst.DS_APMS);
		}else if(dataValue.get("FORMSERVICE_MODIFYFLAG").toString().equals("update")){//更新时的操作
			String updateSql="UPDATE B_WORKLADDER SET PLATFORM_HEIGHT=?,HEIGHTDESC=?,PLATFORM_AREA=?,ISGUARDBAR=? WHERE ID=?";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql,platform_height,heightdesc,platform_area,isguardbar,workladderid);
		}
		logger.debug("工作梯管理新增前置拦截器执行完成！");
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
