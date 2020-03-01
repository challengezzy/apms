package com.apms.bs.apu.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;
import smartx.publics.form.bs.service.SmartXFormService;

/**
 * LLP STRUCT删除拦截器，同时删除
 * @author jerry
 * @date May 30, 2013
 */
public class ApuStructDelBefore implements FormInterceptor {

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
		CommDMO dmo = new CommDMO();
		//APU删除型号的时候删除相应的LLP构造，LLP部件信息
		String sql1 = "DELETE B_APU_LLP_PARTNUM WHERE LLP_STRUCT_ID = ?";
		for(Map<String,Object> map : dataValueList){
			//不确定flag的key是什么，取再次
			String flag = (String) map.get(SmartXFormService.KEYNAME_MODIFYFLAG);
			if(flag == null){//新增时也调用 了些拦截器，此时flag为null，不处理
				flag = (String) map.get("flag");
			}
			
			if(flag == null){
				break;
			}
			
			
			String structid = map.get("ID").toString();//得到主要值
			
			//flag 标记数据要作的处理状态，有insert,update,delete 三种
			if(flag.equalsIgnoreCase("delete")){
				//删除APU下面所属的LLP组件
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1,structid);
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
