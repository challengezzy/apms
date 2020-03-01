package com.apms.bs.apu.interceptor;

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
public class ApuModelUpdateBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	public ApuModelUpdateBefore() {
	}

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
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
		//TODO 在风格模板09中，需要配置“批量删除拦截器” 才能捕捉到主根据的删除
		CommDMO dmo = new CommDMO();
		//APU删除型号的时候删除相应的LLP构造，LLP部件信息
		String sql1 = "DELETE B_APU_LLP_PARTNUM WHERE APUMODEL_ID = ?";
		String sql2 = "DELETE B_APU_LLP_STRUCT WHERE APUMODEL_ID =?";
		for(Map<String,Object> map : dataValueList){
			if(map.get("flag") == null)//新增时也调用 了些拦截器，此时flag为null，不处理
				break; 
			
			String flag = (String) map.get("flag");
			String modelid = map.get("pkvalue").toString();//得到主要值
			
			//flag 标记数据要作的处理状态，有insert,update,delete 三种
			if(flag.equalsIgnoreCase("delete")){
				//删除APU下面所属的LLP组件
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1,modelid);
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2,modelid);
			}
		}
	}

}
