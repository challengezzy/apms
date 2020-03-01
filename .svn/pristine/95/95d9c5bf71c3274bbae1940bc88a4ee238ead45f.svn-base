package com.apms.bs;

import java.util.Map;
import org.apache.log4j.Logger;
import com.apms.ApmsConst;
import com.apms.bs.aircond.AircondService;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 空调与引气相关的服务类
 * @author jerry
 * @date Dec 16, 2014
 */
public class AirConditionService {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	//A21 空调报文
	public Map<String, Object> getAircondA21ChartData(String begin_date,String end_date,String acNum,Boolean isRank) throws Exception
	{
		try {
			AircondService aircondService = new AircondService();
			return aircondService.getAirCondChartData( begin_date, end_date, acNum,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询空调文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	//A21 空调报文
	public Map<String, Object> getAirbleedA21ChartData(String begin_date,String end_date,String acNum,Boolean isRank) throws Exception
	{
		try {
			AircondService aircondService = new AircondService();
			return aircondService.getAirbleedChartData( begin_date, end_date, acNum,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询空调文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 * A24空调
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @param isRank
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getGndSysA24ChartData(String begin_date,String end_date,String acNum,Boolean isRank) throws Exception
	{
		try {
			AircondService aircondService = new AircondService();
			return aircondService.getGroundChartData( begin_date, end_date, acNum,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询空调文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
}
