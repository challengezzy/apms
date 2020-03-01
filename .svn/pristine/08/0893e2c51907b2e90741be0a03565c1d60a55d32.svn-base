package com.apms.bs;

import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.apu.ApuPdiService;
import com.apms.bs.fueloil.FuelCostService;

/**
 * 远程服务类与FLEX端交互 配置在WEB-INF/flex/remoting-config.xml中，
 * 供APU燃油功能模块UI端远程调用
 * @author jerry
 * @date Nov 14, 2016
 */
public class ApuFuelFormService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	public Map<String, Object> getFuelOilCostData(String begin_date,String end_date
			,String acnum,String asn,boolean isInstalled) throws Exception
	{
		try {
			FuelCostService fuelService = new FuelCostService();
			return fuelService.getFuelOilCostData( begin_date, end_date, acnum,asn,isInstalled);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU燃油运行成本数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public Map<String, Object> getReportFuelAnalyzeData(String begin_date,String end_date ,String acnum,String asn ) throws Exception
	{
		try {
			FuelCostService fuelService = new FuelCostService();
			return fuelService.getReportFuelAnalyzeData( begin_date, end_date, acnum,asn);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU燃油报文成本分析数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public Map<String, Object> getOverhaulCostData(String begin_date,String end_date 
			,String acnum,String asn,boolean isIgnoreBegin ) throws Exception
	{
		try {
			FuelCostService fuelService = new FuelCostService();
			return fuelService.getOverhaulCostData( begin_date, end_date, acnum,asn, isIgnoreBegin);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU大修方案成本分析数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public Map<String, Object> getApuChangeForecastData(Map<String, Object> predictObj ) throws Exception
	{
		try {
			ApuPdiService pdiService = new ApuPdiService();
			return pdiService.getApuChangeForecastData( predictObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU大修方案成本分析数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}

}
