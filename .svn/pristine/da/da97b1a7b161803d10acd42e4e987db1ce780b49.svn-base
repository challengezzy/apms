
package com.apms.bs.aircraft.interceptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.ComBoxItemVO;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

import com.apms.ApmsConst;
import com.apms.bs.intf.omis.Javadatediff;
import com.apms.bs.util.DateUtil;
import com.apms.cache.ApmsServerCache;

/**
 * 服务端拦截器实现类,飞机初始化或修改的时候实现
 */
public class AirCraftAdd implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		CommDMO dmo = new CommDMO();
		String acnum="";
		String query="";
		
		
		if (dataValue.get("AIRCRAFTSN")!=null){
			acnum=dataValue.get("AIRCRAFTSN").toString();//飞机号
		}
		else
		{
			acnum="";//飞机号
		}
		
	
		query="select 1 from b_apms_outlink t where model='"+acnum+"' and linksys='OMISFLY'";
		HashVO[] engVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
		if(engVos.length>0){//如果数据存在
			
				
		}
		else{
			if(acnum.length()>0){
			query="insert into b_apms_outlink(id,linksys,model,update_time)values(s_b_apms_outlink.nextval,'OMISFLY','"+acnum+"',sysdate)";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, query);
			dmo.commit(ApmsConst.DS_APMS);	
			//新增飞机列表后刷新下飞机缓存
			ApmsServerCache.getInstance().refreshAirCraftCache();
			}
		}			
		
			
		
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
