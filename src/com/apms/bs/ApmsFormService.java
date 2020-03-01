package com.apms.bs;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.SimpleHashVO;

import com.apms.ApmsConst;
import com.apms.bs.aircraft.interceptor.AircraftMonitorService;
import com.apms.bs.alarm.AlartMessageDealService;
import com.apms.bs.apu.ApuRunLogCompute;
import com.apms.bs.apu.ApuService;
import com.apms.bs.apu.ApuSwapService;
import com.apms.bs.engine.EngineService;
import com.apms.bs.file.FdimuFileService;
import com.apms.bs.file.FileServletURI;
import com.apms.bs.file.FileUploadService;
import com.apms.bs.flight.FlightManageService;
import com.apms.bs.oil.OilService;
import com.apms.bs.oxygen.OxygenChangeService;
import com.apms.bs.oxygen.OxygenService;
import com.apms.bs.user.UserDataVo;
import com.apms.bs.user.UserManageService;
import com.apms.bs.util.DateUtil;
import com.apms.bs.vibration.VibrationService;
import com.apms.bs.wavhealth.WavHealthService;


/**
 * 远程服务类与FLEX端交互 配置在WEB-INF/flex/remoting-config.xml中，供UI端远程调用
 * 
 * @date Dec 1, 2012
 **/
public class ApmsFormService {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	
	/**
	 * 通用文件上传入口
	 */
	public void uploadFile(byte[] bytes,String fileName,String downloadurl,String tableName,String fdimuSwId
			,String uploadUser,String recordTimeVal,String maintenanceReportVal,String commentVal) throws Exception {
		try {
			FileUploadService fileUploadService = FileUploadService.getInstance();
			fileUploadService.fileUpload(bytes, fileName,downloadurl, tableName, fdimuSwId, uploadUser,recordTimeVal,maintenanceReportVal,commentVal);
			logger.info("附件【" + fileName + "】上传成功！");
		} catch (Exception e) {
			logger.error("上传附件文件失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	

	/**
	 * 通用文件下载入口
	 */
	public void deleteFile(String id,String url,String filename) throws Exception {
		try {
			FileUploadService fileUploadService = FileUploadService.getInstance();
			fileUploadService.fileDelete(id, url, filename);
			logger.info("附件【" + filename + "】删除成功！");
		} catch (Exception e) {
			logger.error("删除附件文件失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**********************以下为单用     以后可优化  开始  **************************/
	
	public void uploadFdimuFile(byte[] bytes,String fileName,String tableName,String fdimuSwId,String uploadUser) throws Exception {
		try {
			FdimuFileService fileService = FdimuFileService.getInstance();
			fileService.swFileUpload(bytes, fileName, tableName, fdimuSwId, uploadUser);
			logger.info("附件【" + fileName + "】上传成功！");
		} catch (Exception e) {
			logger.error("上传附件文件失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void deleteFdimuFile(String id,String url,String filename) throws Exception {
		try {
			FdimuFileService fileService = FdimuFileService.getInstance();
			fileService.deleteFileUpload(id,url,filename);
			logger.info("附件【" + filename + "】删除成功！");
		} catch (Exception e) {
			logger.error("删除附件文件失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void deleteAircraftWeightFile(String id,String url,String filename) throws Exception {
		try {
			FdimuFileService fileService = FdimuFileService.getInstance();
			fileService.deleteFileUploadOfAircraftWeight(id, url, filename);
			logger.info("附件【" + filename + "】删除成功！");
		} catch (Exception e) {
			logger.error("删除附件文件失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void engineDeleteFdimuFile(String id,String url,String filename) throws Exception {
		try {
			FdimuFileService fileService = FdimuFileService.getInstance();
			fileService.engineDeleteFileUpload(id, url, filename);
			logger.info("附件【" + filename + "】删除成功！");
		} catch (Exception e) {
			logger.error("删除附件文件失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void apuUploadFdimuFile(byte[] bytes,String fileName,String tableName,String fdimuSwId,String uploadUser,String recordTimeVal,String maintenanceReportVal,String commentVal) throws Exception {
		try {
			FdimuFileService fileService = FdimuFileService.getInstance();
			fileService.apuFileUpload(bytes, fileName, tableName, fdimuSwId, uploadUser,recordTimeVal,maintenanceReportVal,commentVal);
			logger.info("附件【" + fileName + "】上传成功！");
		} catch (Exception e) {
			logger.error("上传附件文件失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 飞机负载均衡上传文件
	 * @param bytes
	 * @param fileName
	 * @param tableName
	 * @param fdimuSwId
	 * @param uploadUser
	 * @param recordTimeVal
	 * @param maintenanceReportVal
	 * @param commentVal
	 * @throws Exception
	 */
	public void aircraftWeightUploadFile(byte[] bytes,String fileName,String tableName,String fdimuSwId,String uploadUser,String recordTimeVal,String maintenanceReportVal,String commentVal) throws Exception {
		try {
			FdimuFileService fileService = FdimuFileService.getInstance();
			fileService.aircraftFileUpload(bytes, fileName, tableName, fdimuSwId, uploadUser,recordTimeVal,maintenanceReportVal,commentVal);
			logger.info("附件【" + fileName + "】上传成功！");
		} catch (Exception e) {
			logger.error("上传附件文件失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void engineUploadFdimuFile(byte[] bytes,String fileName,String tableName,String fdimuSwId,String uploadUser,String recordTimeVal,String maintenanceReportVal,String commentVal) throws Exception {
		try {
			FdimuFileService fileService = FdimuFileService.getInstance();
			fileService.engineFileUpload(bytes, fileName, tableName, fdimuSwId, uploadUser,recordTimeVal,maintenanceReportVal,commentVal);
			logger.info("附件【" + fileName + "】上传成功！");
		} catch (Exception e) {
			logger.error("上传附件文件失败!", e);
			throw e;
		}finally{
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}

	public String downloadFdimuFile(String fileContentId, String fileName) throws Exception {
		try {
			FdimuFileService fileService = FdimuFileService.getInstance();
			String fileUrl = fileService.swFileDownload(fileContentId);
			logger.info("附件【" + fileName + "】生成文件成功！");

			FileServletURI fsu = new FileServletURI();
			String downLoadUri = fsu.getDownLoadURI(fileUrl, fileName);

			return downLoadUri;
		} catch (Exception e) {
			logger.error("下载附件文件失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**********************以下为单用     以后可优化  结束  **************************/

	/**
	 * 根据SQL语句查询数据
	 * @param ds
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public SimpleHashVO[] getSimpleHashVoArrayByDS(String ds, String sql) throws Exception {
		CommDMO dmo = new CommDMO();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(ds, sql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		} catch (Exception e) {
			logger.error("getSimpleHashVoArrayByDS 错误！", e);
			throw e;
		} finally {
			dmo.releaseContext(ds);
		}
	}
	/**
	 * 氧气报文查询
	 * @param begin_date
	 * @param end_date
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOxygenChartData(String begin_date,String end_date,String acNum,Boolean isRank) throws Exception
	{
		try {
			OxygenService service = new OxygenService();
			return service.getOxygenChartData( begin_date, end_date, acNum,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询氧气报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public Map<String, Object> getOxyBottlePreChangeData(Map<String, Object> predictObj) throws Exception{
		try{
			OxygenChangeService service = new OxygenChangeService();
			return service.changePredict(predictObj);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("23号报文数据点事件更新失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//23号报文数据点事件更新
	public void pointMarkA23(String msgno,String eventType,String memo,String marker,String marktime) throws Exception{
		try{
			OxygenService service = new OxygenService();
			service.pointMarkA23(msgno, eventType, memo, marker, marktime);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("23号报文数据点事件更新失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//APU报文(A13)计算后数据查询
	public Map<String, Object> getApuChartData(String begin_date,String end_date,String acNum,String apusn,Boolean isRank) throws Exception
	{
		try {
			 ApuService apuService = new ApuService();
			return apuService.getApuChartData( begin_date, end_date, acNum,apusn,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	
	
	//滑油状态报文A25计算数据查询(图形展示)
	public Map<String, Object> getOilA25ChartData(String begin_date,String end_date,String acNum,Boolean isRank) throws Exception
	{
		try {
			OilService oilService = new OilService();//TODO 测试用
			return oilService.getOilStatusChartData( begin_date, end_date, acNum,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public Map<String, Object> getOilDayAddChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		try {
			OilService oilService = new OilService();
			return oilService.getOilDayAddChartData( begin_date, end_date, acNum);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	//A28A32抖动报文 查询频率
	public Map<String, Object> getVibrationFreqChartData(String begin_date,String end_date,String acNum,String dimension) throws Exception
	{
		try {
			VibrationService  vibrationService = new VibrationService();
			return vibrationService.getA28FreqChartData( begin_date, end_date, acNum,dimension);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询A28A32报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	//A34大翼防冰监控
	public Map<String, Object> getWavHealthA34ChartData(String begin_date,String end_date,String acNum,Boolean isRank) throws Exception
	{
		try {
			WavHealthService  wavhealthService = new WavHealthService();
			return wavhealthService.getWavHealthChartData( begin_date, end_date, acNum,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询A34报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	//发动机拆换
	public String engChange(Map<String, Object> swapInfo) throws Exception{
		try{
			//EngineSwapInfoVo engRemove = (EngineSwapInfoVo)param;
			EngineService engch = new EngineService();
			System.out.println(swapInfo.get("oldengsn"));
			engch.engChange(swapInfo);
			return "发动机拆换处理OK";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发动机拆换处理失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	//APU拆换
	public String swapApu(Map<String, Object> swapInfo) throws Exception{
		try{
			ApuSwapService swapService = new ApuSwapService();
			swapService.swapApu(swapInfo);
			return "APU拆换日志记录成功！";
		} catch (Exception e) {
			new CommDMO().rollback(ApmsConst.DS_APMS);
			e.printStackTrace();
			logger.error("APU拆换异常!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public Map<String, Object> getApuRunStatisInfo(String begin_date,String end_date,String basicID ,String acNum,String aputype,String asn,String periodFormat) throws Exception{
		try{
			ApuService apuService = new ApuService();
			return apuService.getApuRunStatisInfo(begin_date, end_date,basicID, acNum,aputype, asn, periodFormat);
			
		} catch (Exception e) {
			new CommDMO().rollback(ApmsConst.DS_APMS);
			e.printStackTrace();
			logger.error("APU拆换异常!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 执行APU小时循环修正
	 * @param asn
	 * @param dataValue
	 * @throws Exception
	 */
	public void correctApuHourCyc(String asn,Map<String, Object> dataValue) throws Exception {
		try{
			ApuRunLogCompute runlog = new ApuRunLogCompute();
			
			double hour = new Double(dataValue.get("CORHOUR").toString());//正确 的小时和循环
			double corCyc = new Double(dataValue.get("CORCYCLE").toString());
			double corHour = hour*60;//转换为分钟
			String corFlag = (String)dataValue.get("CORFLAG"); //修正标识：0-修正恢复， 1-修正
			
			String beginStr = (String)dataValue.get("BEGIN_TIME");		
			String endStr = (String)dataValue.get("END_TIME");
			
			String df = "yyyy-MM-dd HH:mm:ss";
			Date beginTime = DateUtil.StringToDate(beginStr, df);
			Date endTime = null;
			if(endStr != null){
				endTime = DateUtil.StringToDate(endStr, df);
			}
			//更新已解析报文小时循环
			runlog.correctApuReportHourCycle(asn, beginTime, endTime,corHour,corCyc,corFlag);
			
			//重新解析
			runlog.recomputeApuReport(asn, beginTime, endTime);	
			
			new CommDMO().commit(ApmsConst.DS_APMS);
			logger.debug("计算修正的小时、循环数成功！");
		} catch (Exception e) {
			new CommDMO().rollback(ApmsConst.DS_APMS);
			e.printStackTrace();
			logger.error("计算修正的小时、循环数异常!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 
	 * @param swapInfo
	 * @return
	 * @throws Exception
	 */
	public String updateAlarmMessageStatus(Map<String, Object> dealInfo) throws Exception{
		try{
			AlartMessageDealService msgDealService = new AlartMessageDealService();
			msgDealService.setMessageDealed(dealInfo);
			
			return "告警消息处理异常！";
		} catch (Exception e) {
			new CommDMO().rollback(ApmsConst.DS_APMS);
			logger.error("告警消息处理异常!", e);
			e.printStackTrace();
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 * 查询人员排班	
	 * @return
	 * @throws Exception
	 */
	public SimpleHashVO[] getCrewSchedulingUser(String airportname ,String orgid_linename,String username,String startDate,String endDate) throws Exception{
		
			UserManageService ums = new UserManageService();
			return ums.getCrewSchedulingUser(airportname,orgid_linename,username,startDate,endDate);
		
	}
	
	public SimpleHashVO[] selectFlightForOrglineEveryday(String startDay,String endDay) throws Exception{
		FlightManageService flightManageService=new  FlightManageService();
		return flightManageService.selectFlightForOrglineEveryday(startDay,endDay);
	}
	
	public SimpleHashVO[] queryForAcnumByAcmodel(String code_3,String orgline,String startDate,String endDate) throws Exception{
		FlightManageService flightManageService=new  FlightManageService();
		return flightManageService.queryForAcnumByAcmodel(code_3,orgline,startDate,endDate);
	}
	/**
	 * 保存人员排班
	 * */
	public String saveCrewSchedulingUser(Date startDate,int dateNum,List<UserDataVo> users,Date updateTime,String updateUser) throws Exception {
		UserManageService ums = new UserManageService();
		return ums.saveCrewSchedulingUser(startDate,dateNum,users,updateTime,updateUser);
	}
	
	/**
	 * 查询Apu和发动机附件管理中的附件类型
	 * @return
	 * @throws Exception
	 */
	public SimpleHashVO[] selectFileTypeFromDictionary() throws Exception{
		FdimuFileService fileService = FdimuFileService.getInstance();
		return fileService.selectFileTypeFromDictionary();
	}
	
	public SimpleHashVO[] queryForAcnum(String code_3) throws Exception{
		AircraftMonitorService aircraftMonitorService=new AircraftMonitorService();
		return aircraftMonitorService.queryForAcnum(code_3);
	}
	
	public SimpleHashVO[] getReportMsg(String ds, String sql) throws Exception {
		CommDMO dmo = new CommDMO();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(ds, sql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		} catch (Exception e) {
			logger.error("getSimpleHashVoArrayByDS 错误！", e);
			throw e;
		} finally {
			dmo.releaseContext(ds);
		}
	}
	
	public SimpleHashVO[] getA38ComputeChartData(String begin_date,String end_date,String acNum,String apusn,Boolean isRank) throws Exception{
			
		try {
			ApuService apuService = new ApuService();
			return apuService.getA38ComputeChartData( begin_date, end_date, acNum,apusn,isRank);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU报文数据失败!", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
}
