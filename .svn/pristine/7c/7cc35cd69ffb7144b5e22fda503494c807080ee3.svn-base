package com.apms.bs;

import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.apu.ApuOverviewDataService;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 远程服务类与FLEX端交互 配置在WEB-INF/flex/remoting-config.xml中，供APU功能模块UI端远程调用
 * @author jerry
 * @date Nov 19, 2014
 */
public class ApuFormService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	public Map<String, Object> getOverviewData(String begin_date,String end_date,String acnum,String asn) throws Exception
	{
		try {
			ApuOverviewDataService service = new ApuOverviewDataService();
			return service.getOverviewData( begin_date, end_date, acnum,asn);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU概览数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}

}
