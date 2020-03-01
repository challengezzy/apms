package com.apms.bs.flight.interceptor;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.apms.cache.ApmsServerCache;
import com.apms.cache.vo.UserVo;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

/**
 * 服务端拦截器实现类
 */
public class InsertFaultInfoBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
		String username;
		UserVo uservo=null;
		if(dataValue.get("CREATOR")!=null){
			username=dataValue.get("CREATOR").toString();
			uservo= ApmsServerCache.getInstance().getUserVoByName(username);
		}
		if(uservo!=null){
			if(uservo.getOrgid_line()!=null){
				dataValue.put("CREATOR_DEPT", uservo.getOrgid_line().toString());
			}
		}
		logger.debug("故障信息新增前置拦截器执行完成！");
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
