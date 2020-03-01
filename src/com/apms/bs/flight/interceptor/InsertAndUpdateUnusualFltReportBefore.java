package com.apms.bs.flight.interceptor;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.cache.ApmsServerCache;
import com.apms.cache.vo.AirCraftVo;
import com.apms.cache.vo.UserVo;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

/**
 * 服务端拦截器实现类
 */
public class InsertAndUpdateUnusualFltReportBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		//添加报告人部门
		UserVo uservo=null;
		String username=null;
		String acnum=null;
		if(dataValue.get("REPORTER")!=null){
			username=dataValue.get("REPORTER").toString();
			uservo= ApmsServerCache.getInstance().getUserVoByName(username);
		}
		if(dataValue.get("ACID")!=null){
			acnum=dataValue.get("ACNUM").toString();
			AirCraftVo airCraftVo= ApmsServerCache.getInstance().getAirCraftVoByAcnum(acnum);
			dataValue.put("ACMODEL", airCraftVo.getAcmodel());
		}
		if(uservo!=null){
			if(uservo.getOrgid_base()!=null){
				String sql="select name from b_organization where id="+uservo.getOrgid_base().toString();
				HashVO vos[]=dmo.getHashVoArrayByDS(ApmsConst.DS_APMS,sql);
				if(vos.length>0){
					String userOrg=vos[0].getStringValue("name");
					dataValue.put("REPORTER_DEPT", userOrg);
				}
			}
		}
		logger.debug("不正常航班报告新增或修改服务器前置拦截器执行完成！");
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
