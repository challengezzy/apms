package com.apms.bs.apu.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.ComBoxItemVO;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;
import smartx.publics.form.bs.service.SmartXFormService;

import com.apms.ApmsConst;
import com.apms.bs.apu.ApuSwapService;

/**
 * 服务端拦截器实现类
 */
public class ApuUpdateBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		double totalHour = new Double(dataValue.get("TOTALTIME_HOUR").toString());
		double onInstallHour = new Double(dataValue.get("TIME_ONINSTALL_HOUR").toString());
		double onRepairedHour = new Double(dataValue.get("TIME_ONREPAIRED_HOUR").toString());
		
		dataValue.put("TOTALTIME", totalHour*60);
		dataValue.put("TIME_ONINSTALL", onInstallHour*60);
		dataValue.put("TIME_ONREPAIRED", onRepairedHour*60);
		
		CommDMO dmo = new CommDMO();
		String apuid = dataValue.get("ID").toString();
		String querySql = "select 1 from l_apu_swaplog t where t.apuid =? and t.swap_action = 1";
		HashVO[] logs = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql, apuid);
		
		if(logs.length < 1){
			String apusn = dataValue.get("APUSN").toString();
			ComBoxItemVO positionItem = (ComBoxItemVO)dataValue.get("POSITION");
			//在翼的APU,判断并进行初始化拆换日志
			if("1".equalsIgnoreCase(positionItem.getId()) ){
				//初始化一条拆换记录中装上信息
				new ApuSwapService().initApuInstallSwapLog(apusn);
			}
		}
		
		logger.debug("APU更新前置拦截器，修改执行完成！");
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		CommDMO dmo = new CommDMO();
		String sql = "DELETE B_APU_LLPPARTS WHERE APUID = ?";
		String delSwapLog = "DELETE L_APU_SWAPLOG S WHERE S.APUID = ?";
		for(Map<String,Object> map : dataValueList){
			String flag = (String) map.get(SmartXFormService.KEYNAME_MODIFYFLAG);
			String apuid = map.get("ID").toString();//得到主要值
			
			//flag 标记数据要作的处理状态，有insert,update,delete 三种
			if(flag.equalsIgnoreCase("delete")){
				//删除APU下面所属的LLP组件
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql,apuid);
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, delSwapLog, apuid);
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
