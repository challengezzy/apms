package com.apms.bs.apu.interceptor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.bs.apu.ApuService;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.framework.metadata.vo.RefItemVO;
import smartx.publics.form.bs.service.FormInterceptor;

public class ApuCorUpdateAfter  implements FormInterceptor {
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		double hour = new Double(dataValue.get("CORHOUR").toString());
		double corCyc = new Double(dataValue.get("CORCYCLE").toString());
		double corHour = hour;//数据已经是分钟单位 ，转换为分钟
		
		RefItemVO apu =(RefItemVO)dataValue.get("APU_ID");
		String beginStr = (String)dataValue.get("BEGIN_TIME");		
		String endStr = (String)dataValue.get("END_TIME");
		
		String apusn = (String)dataValue.get("APUSN");
		
		String df = "yyyy-MM-dd HH:mm:ss";
		Date beginTime = DateUtil.StringToDate(beginStr, df);
		Date endTime = null;
		if(endStr != null){
			endTime = DateUtil.StringToDate(endStr, df);
		}
		//更新已解析报文小时循环
		//apuService.correctApuReportHourCycle(apusn, beginTime, endTime,corHour,corCyc);
		//重新解析
		//apuService.recomputeApuReport(apusn, beginTime, endTime);		 //zhangzy 转移到单独的按钮触发
		logger.debug("计算修正的小时、循环数成功！");
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
