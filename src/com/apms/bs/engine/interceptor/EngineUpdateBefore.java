package com.apms.bs.engine.interceptor;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;
import smartx.publics.form.bs.service.SmartXFormService;

import com.apms.ApmsConst;

/**
 * 服务端拦截器实现类
 */
public class EngineUpdateBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO(); 
	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		double tsn = new Double(dataValue.get("TSN_HRS")==null?"0":dataValue.get("TSN_HRS").toString());
		double chkfh = new Double(dataValue.get("CHKFH_HRS")==null?"0":dataValue.get("CHKFH_HRS").toString());
		double time_oninstall = new Double(dataValue.get("INSTFH_HRS")==null?"0":dataValue.get("INSTFH_HRS").toString());
		String acid="";
		if (dataValue.get("AIRCRAFTID")!=null){
			acid=dataValue.get("AIRCRAFTID").toString();
		}
		
		
		dataValue.put("ACNUM", acid);
		dataValue.put("TSN", tsn*60);
		dataValue.put("TIME_ONREPAIRED", chkfh*60);
		dataValue.put("TIME_ONINSTALL", time_oninstall*60);
		
		logger.debug("发动机修改前置拦截器执行完成！");
	
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		String sql = "DELETE B_ENG_LLPPARTS WHERE ENGID = ?";
		String sql1="delete from l_eng_flightlog where engsn=?";
		String sql2="delete from l_eng_flightlog_daily where engsn=?";
		String sql3="delete l_eng_swaplog t where t.engsn=? ";
		
		for(Map<String,Object> map : dataValueList){
			String flag = (String) map.get(SmartXFormService.KEYNAME_MODIFYFLAG);
			String engid = map.get("ID").toString();//得到主要值
			String engsn= map.get("ENGSN").toString();//得到发动机编号
			//flag 标记数据要作的处理状态，有insert,update,delete 三种
			if(flag.equalsIgnoreCase("delete")){
				//删除发动机下面所属的LLP组件
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql,engid);
				//删除发动机的航段日志
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1,engsn);
				//删除发动机的飞行日志
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2,engsn);
				
				//删除拆换日志
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql3, engsn);
			}
		}

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
