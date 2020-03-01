package com.apms.bs;


import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.engine.A49IaeChartDetail;
import com.apms.bs.engine.CfmEngineChartDetail;
import com.apms.bs.engine.EngineService;
import com.apms.bs.engine.IaevEngineChartDetail;

/**
 * 发动机数据查询
 * @author Administrator
 *
 */
public class EnginFormService {
	protected Logger logger = NovaLogger.getLogger(this.getClass());

	//A01 发动机报文
	public Map<String, Object> getEngineA01ChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		try {
			CfmEngineChartDetail engineChartDetail = new CfmEngineChartDetail();
			return engineChartDetail.getEngineA01ChartData( begin_date, end_date, acNum, isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//A04 发动机报文
	public Map<String, Object> getEngineA04ChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		try {
			CfmEngineChartDetail engineChartDetail = new CfmEngineChartDetail();
			return engineChartDetail.getEngineA04ChartData( begin_date, end_date, acNum,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//Cfm发动机滑油报文
	public Map<String, Object> getCfmEngineSildeOilChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		try {
			CfmEngineChartDetail engineChartDetail = new CfmEngineChartDetail();
			return engineChartDetail.getCfmEngineSildeOilChartData( begin_date, end_date, acNum);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//Cfm发动机振动值报文
	public Map<String, Object> getCfmEngineVibrationChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		try {
			CfmEngineChartDetail engineChartDetail = new CfmEngineChartDetail();
			return engineChartDetail.getCfmVibrationChartData( begin_date, end_date, acNum,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	
	
	
	//Iaev A01发动机报文
	public Map<String, Object> getIaevEngineA01ChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		try {
			IaevEngineChartDetail iaevEngineChartDetail = new IaevEngineChartDetail();
			return iaevEngineChartDetail.getEngIaeA01ChartData( begin_date, end_date, acNum, isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//Iaev A04发动机报文
	public Map<String, Object> getIaevEngineA04ChartData(String begin_date,String end_date,String acNum, boolean isRank) throws Exception
	{
		try {
			IaevEngineChartDetail iaevEngineChartDetail = new IaevEngineChartDetail();
			return iaevEngineChartDetail.getEngIaeA04ChartData( begin_date, end_date, acNum, isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//Iaev A49发动机报文图表数据
	public Map<String, Object> getIaeA49ChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		try {
			A49IaeChartDetail chartDetail = new A49IaeChartDetail();
			return chartDetail.getEngineA49ChartData( begin_date, end_date, acNum, isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询A49发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//Iaev 发动机滑油报文
	public Map<String, Object> getIaevEngineSildeOilChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		try {
			IaevEngineChartDetail iaevEngineChartDetail = new IaevEngineChartDetail();
			return iaevEngineChartDetail.getIaevEngIneSildeOilChartData( begin_date, end_date, acNum);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//Iaev 发动机振动值报文
	public Map<String, Object> getIaevEngineVibrationChartData(String begin_date,String end_date,String acNum, boolean isRank) throws Exception
	{
		try {
			IaevEngineChartDetail iaevEngineChartDetail = new IaevEngineChartDetail();
			return iaevEngineChartDetail.getIaevEngineVibrationChartData( begin_date, end_date, acNum, isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//Iaev 单元体性能数据查询
	public Map<String, Object> getIaevUnitComponentChartData(String begin_date,String end_date,String acNum, boolean isRank) throws Exception
	{
		try {
			IaevEngineChartDetail iaevEngineChartDetail = new IaevEngineChartDetail();
			return iaevEngineChartDetail.getEngIneA01UnitChartData( begin_date, end_date, acNum, isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//Iaev A01作动组件及传感器 数据查询
	public Map<String, Object> getIaevA01ActuatorCylinderChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		try {
			IaevEngineChartDetail iaevEngineChartDetail = new IaevEngineChartDetail();
			return iaevEngineChartDetail.getEngIneA01AutuChartData( begin_date, end_date, acNum);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//Iaev A04作动组件及传感器 数据查询
	public Map<String, Object> getIaevA04ActuatorCylinderChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		try {
			IaevEngineChartDetail iaevEngineChartDetail = new IaevEngineChartDetail();
			return iaevEngineChartDetail.getEngIneA04AutuChartData( begin_date, end_date, acNum);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询发动机报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 补录小时循环数据
	 * @param swapInfo
	 * @return
	 * @throws Exception
	 */
	public void addHourCycLog(Map<String, Object> logInfo) throws Exception{
		try{
			EngineService service = new EngineService();
			service.addHourCycLog(logInfo);
			
		} catch (Exception e) {
			new CommDMO().rollback(ApmsConst.DS_APMS);
			e.printStackTrace();
			logger.error("发动机小时循环补录失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
}
