package com.apms.bs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.common.HashVoUtil;
import com.apms.bs.flight.AirportMapService;
import com.apms.bs.flight.FlightManageService;
import com.apms.bs.flight.FlightWorkService;
import com.apms.bs.flight.vo.AirportPositionVo;
import com.apms.bs.remind.RemindManageService;
import com.apms.bs.system.LoginService;
import com.apms.bs.system.LoginUserVo;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.SimpleHashVO;

public class FlightFormService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private FlightWorkService workService = new FlightWorkService();
	
	public Map<String,Object> getWorkOrderPlane(String acnum,String flt_date) throws Exception{
		
		Map<String,Object>  resMap = new HashMap<String,Object>();
		
		try{
			String  sumSql = "select sum(MH) MHSUM from AMS_WORKORDERPLANE where acno = '"+acnum +"' and opdy = to_date('"+flt_date+"','yyyy-MM-dd') and FROMMODEL = 'MP'"; 
			String  sumSql2 = "select sum(MH) OTHERSUM from AMS_WORKORDERPLANE where acno ='"+acnum +"' and opdy = to_date('"+flt_date+"','yyyy-MM-dd') and FROMMODEL <> 'MP'";
			String  sql  = "select (select name from B_AIRLINE where id =  AIRLINEID) AIRLINENAME from B_AIRCRAFT where AIRCRAFTSN = '"+acnum+"'";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sumSql);
			HashVO[] vos2 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sumSql2);
			HashVO[] vosTemp = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);

			HashVO vo = null;	
			HashVO vo2 = null; 
			HashVO voTemp = null; 
			Double summh=0.0;
			Double sumother=0.0;
			if(vos != null && vos.length>0){
				vo = vos[0];
				summh = vo.getDoubleValue("MHSUM");
				if(summh == null){
					summh=0.0;
				}
			}
			if(vos2 != null && vos2.length>0 ){
				vo2 = vos2[0];
				sumother = vo2.getDoubleValue("OTHERSUM");
				if(sumother==null){
					sumother=0.0;
				}
			}
			String airLineName="";
			if(vosTemp!= null && vosTemp.length>0) {
				voTemp = vosTemp[0];
				airLineName = voTemp.getStringValue("AIRLINENAME");
			}
			
			String sumAll= String.valueOf(summh+sumother);
			resMap.put("sumMH",sumAll);
			resMap.put("airLineName",airLineName);
			
			String mhSql = "select rownum,w.* from (select OWNERID,CONTEXTCN,PLANSN,WORKORDERSN,MH from AMS_WORKORDERPLANE  " +
						   "where acno = '"+acnum +"' and opdy = to_date('"+flt_date+"','yyyy-MM-dd') and FROMMODEL = 'MP' order by OWNERID) w where rownum<=10";
			String otherSql = "select rownum,w.* from (select CONTEXTCN,PLANSN,WORKORDERSN,MH from AMS_WORKORDERPLANE  " +
							  "where acno = '"+acnum +"' and opdy = to_date('"+flt_date+"','yyyy-MM-dd') and FROMMODEL <> 'MP' order by OWNERID) w";
			String threeDayCompSql = "select rownum,w.* from (select CONTEXTCN,PLANSN,WORKORDERSN,PARTPN,PARTSN,OPDY from AMS_WORKORDERPLANE  " +
									 "where acno = '"+ acnum +"' and COMPLETESTATE=1  and opdy>= to_date('"+flt_date+"','yyyy-MM-dd')-3  and " +
									 "opdy <= to_date('"+flt_date+"','yyyy-MM-dd')  order by OWNERID) w";
			
			HashVO[] vos3 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, mhSql);
			List<Map<String,Object>> mhWorkList = new ArrayList<Map<String,Object>>();
			mhWorkList.addAll(HashVoUtil.hashVosToMapList(vos3));
			
			HashVO[] vos4 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, otherSql);
			List<Map<String,Object>> otherWorkList = new ArrayList<Map<String,Object>>();
			otherWorkList.addAll(HashVoUtil.hashVosToMapList(vos4));
			
			HashVO[] vos5 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, threeDayCompSql);
			List<Map<String,Object>> threeDayCompWorkList = new ArrayList<Map<String,Object>>();
			threeDayCompWorkList.addAll(HashVoUtil.hashVosToMapList(vos5));
			resMap.put("mhWorkList",mhWorkList);
			resMap.put("otherWorkList",otherWorkList);
			resMap.put("threeDayCompWorkList",threeDayCompWorkList);
			
		}catch(Exception  e){
			e.printStackTrace();
			logger.error("查询"+acnum+"飞机航班当天工作任务失败!", e);
			throw e;
		}finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		return resMap;
	}
	
	public Map<String, Object> getLastFlightSynInfo(String aptcode) throws Exception{
		Map<String, Object> synlog = new HashMap<String, Object>();
		try{
			String sql = "select id,result,updatetime,exesecond,num_totalflt,num_dealflt,num_update,num_add,num_cancel,errormsg "
				+ " from f_syn_log t where t.id=(select max(id) from f_syn_log t2 where t2.result=1 )";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			HashVO vo = vos[0];
			synlog.put("updatetime", vo.getDateValue("updatetime"));
			synlog.put("num_totalflt", vo.getIntegerValue("num_totalflt"));
			synlog.put("num_dealflt", vo.getIntegerValue("num_dealflt"));
			synlog.put("num_update", vo.getIntegerValue("num_update"));
			synlog.put("num_add", vo.getIntegerValue("num_add"));
			synlog.put("num_cancel", vo.getIntegerValue("num_cancel"));
			
			//获取航班的重要变动信息,总数和取消数
			String baseSql = "SELECT COUNT(1) AMOUNT FROM V_FLIGHT_IMPORTANTCHANGE T WHERE 1=1 AND ( T.DEP_APT='"+aptcode+"' OR T.ARR_APT='"+aptcode+"')";
			String cancelSql = baseSql + " AND CHANGETYPE=200";
			
			HashVO[] vos1 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, baseSql);
			HashVO basevo = vos1[0];
			synlog.put("change_num_total", basevo.getIntegerValue("AMOUNT"));
			
			HashVO[] vos2 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, cancelSql);
			HashVO cancelvo = vos2[0];
			synlog.put("change_num_cancel", cancelvo.getIntegerValue("AMOUNT"));
			
			logger.info("查询最新航班变动信息成功!");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询最新航班同步信息失败!", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		return synlog;
	}
	
	public Map<String, Object> getLastFlightSynInfo() throws Exception{
		Map<String, Object> synlog = new HashMap<String, Object>();
		try{
			String sql = "select id,result,updatetime,exesecond,num_totalflt,num_dealflt,num_update,num_add,num_cancel,errormsg "
				+ " from f_syn_log t where t.id=(select max(id) from f_syn_log t2 where t2.result=1 )";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			HashVO vo = vos[0];
			synlog.put("updatetime", vo.getDateValue("updatetime"));
			synlog.put("num_totalflt", vo.getIntegerValue("num_totalflt"));
			synlog.put("num_dealflt", vo.getIntegerValue("num_dealflt"));
			synlog.put("num_update", vo.getIntegerValue("num_update"));
			synlog.put("num_add", vo.getIntegerValue("num_add"));
			synlog.put("num_cancel", vo.getIntegerValue("num_cancel"));
			synlog.put("change_num_total", 0);
			synlog.put("change_num_cancel", 0);
			logger.info("查询最新航班变动信息成功_旧版!");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询最新航班同步信息失败!", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		return synlog;
	}
	/**
	 * 工作分配
	 * @param releaseUser
	 * @param maintainUser
	 * @param dutyUser
	 * @param guardianUser
	 * @param handoverUser
	 * @param fltpk_a
	 * @param fltpk_d
	 * @throws Exception
	 */
	//工作分配
	public void flightTaskDispatch(String releaseUser,String maintainUser,String dutyUser
			,String guardianUser,String handoverUser
			,String auto_power,String auto_airsrc,String auto_aircond,String auto_trailer,String auto_snowrm
			,String auto_lift,String airpress_user,String poweron_user,String sign2_user,String area_user
			,String pickupcrewUser,String cleanUser,String sewageUser
			,String fltpk_a,String fltpk_d,String optUser) throws Exception{
		
		workService.flightTaskDispatch(releaseUser, maintainUser, dutyUser, guardianUser, handoverUser
				,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
				,auto_lift,airpress_user,poweron_user,sign2_user,area_user
				,pickupcrewUser,cleanUser,sewageUser, fltpk_a, fltpk_d, optUser);
	}
	
	/**
	 * 航班计划批量确认
	 * @param mapList
	 * @throws Exception
	 */
	public void flightBatchConfirm(List<Map<String,String>> mapList) throws Exception{
		try{
			for(int i=0;i<mapList.size(); i++){
				Map<String, String> map = mapList.get(i);
				String isConfirmed = map.get("ISCONFIRMED");
				String maintainType = map.get("MAINTAINTYPE");
				String maintainColumn = map.get("MAINTAINCOLUMN");
				String isLocked = map.get("ISLOCKED");
				String arrdep_type = map.get("ARRDEP_TYPE");
				String fltpk = map.get("FLTPK");
				String acstop = map.get("ACSTOP");
				String etaStr =map.get("ETA");
				String flightdesc = map.get("FLIGHTDESC");
				String user = map.get("USER");
				String flightno = map.get("FLIGHTNO");
				
				//维护类型不存储空值,航前存储TR相当于为空
				if(maintainType == null || "".equals(maintainType)){
					maintainType = "TR";
				}
				String sql = "UPDATE F_FLIGHT_SCHEDULE T SET T.ISCONFIRMED=?,T.CONFIRMEDTIME=SYSDATE" 
					+","+maintainColumn+"=? ,isLockedin =?,flightdesc=?" ;//工作状态
				
				if("ARR".equals(arrdep_type)){
					if(acstop != null && !"".equals(acstop)){
						sql += ",ac_stop_arr ='" + acstop + "'";
					}
					
					if(etaStr != null && etaStr.length() > 10 ){
						sql += ", eta= to_date('"+etaStr+"','YYYY-MM-DD HH24:MI:SS')";
					}
					
				}else{
					if(acstop != null && !"".equals(acstop)){
						sql += ",ac_stop = '" + acstop + "'";
					}
				}
				
				sql += " WHERE T.FLT_PK=? ";
				
				//航班确认动作记录中航班通报中
				String rptSql = "INSERT INTO F_FLIGHTREPORT(ID,FLIGHTID,FLIGHTNO,TYPE,REPORTER,RECIPIENT,REPORTTIME,REPORTCONTENT,UPDATETIME,UPDATEUSER) "
					+ " VALUES(S_F_FLIGHTREPORT.NEXTVAL,?,?,?,?,?,?,?,sysdate,?)";
				
				String reportContent = "维护["+maintainType+"],锁定["+isLocked+"]";
				if(acstop != null && acstop.length() > 0){
					reportContent += ",机位["+acstop+"]";
				}
				if(flightdesc != null && flightdesc.length() > 0){
					reportContent += ",备注["+flightdesc+"]";
				}
				
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql,isConfirmed, maintainType, isLocked,flightdesc, fltpk);
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, rptSql, fltpk,flightno,"航班确认",user, "system",new Date(),reportContent,user);
				
				dmo.commit(ApmsConst.DS_APMS);
				logger.debug("确认航班fltpk=["+fltpk+"]维护类型信息成功！");
				
			}
			logger.debug("航班计划批量确认成功！");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void flightMaintainTypeConfirm(String arrdep_type,String isConfirmed,String maintainType,String maintainColumn,String isLocked,String fltpk,
			String acstop,String etaStr) throws Exception{
		try{
			//维护类型不存储空值,航前存储TR相当于为空
			if(maintainType == null || "".equals(maintainType)){
				maintainType = "TR";
			}
			String sql = "UPDATE F_FLIGHT_SCHEDULE T SET T.ISCONFIRMED=?,T.CONFIRMEDTIME=SYSDATE" 
				+","+maintainColumn+"=? ,isLockedin =?" ;//工作状态
			
			if("ARR".equals(arrdep_type)){
				if(acstop != null && !"".equals(acstop)){
					sql += ",ac_stop_arr ='" + acstop + "'";
				}
				
				if(etaStr != null && etaStr.length() > 10 ){
					sql += ", eta= to_date('"+etaStr+"','YYYY-MM-DD HH24:MI:SS')";
				}
				
			}else{
				if(acstop != null && !"".equals(acstop)){
					sql += ",ac_stop = '" + acstop + "'";
				}
			}
			
			
			sql += " WHERE T.FLT_PK=? ";
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql,isConfirmed, maintainType, isLocked, fltpk);
			dmo.commit(ApmsConst.DS_APMS);
			logger.debug("确认航班fltpk=["+fltpk+"]维护类型信息成功！");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询最新航班同步信息失败!", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 新增航班状态记录
	 * @throws Exception
	 */
	public void flightACStateAdd(String flightid, String flightno, String state,Date stateTime, String reporter, String flightchgcomment,String user) throws Exception{
		try{
			
			String sql = "Insert INTO f_ac_state(id,flightid,flightno,state,reporter,comments,updatetime,updateuser) " +
					" Values(s_f_ac_state.nextval,?,?,?,?,?,?,?)";
			//判断一下如果最新的状态大于航班计划表中的状态，更新为最大状态
			String updateSql = "update f_flight_schedule t set t.ac_state="+state+" where t.flt_pk="+flightid+" and ac_state < "+state+"";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql);
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql, flightid, flightno, state, reporter, flightchgcomment,stateTime, user);
			
			//同时把工作状态的更新加入到工作通报中
			String reportSql = "insert into f_flightreport(id,flightid,flightno,type,reporter,recipient,reporttime,reportcontent,comments,updatetime,updateuser )";
			reportSql += "values(s_f_flightreport.nextval,?,?,?,?,?,?,(";
			reportSql +="select d.valuecn from b_dictionary d where d.classname='FLIGHTSCHEDULE' AND d.attributename='ACSTATE' and d.value="+state+" and rownum=1";
			reportSql += "),?,sysdate,?)";
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, reportSql, flightid, flightno, "飞机状态", reporter,user, stateTime, flightchgcomment, user);
			
			dmo.commit(ApmsConst.DS_APMS);
			logger.debug("新增航班["+flightid+"]飞机状态["+state+"]信息成功！");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("新增航班["+flightid+"]飞机状态失败!", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 新增工作状态记录
	 * @throws Exception
	 */
	public void flightWorkStateAdd(String flightid, String flightno,String flightid_a,String flightno_a,String state,Date stateTime, String reporter, String comment,String user) throws Exception{
		try{//flightid，flightno主关联航班，flightid_a，flightno_a关联到港航班
			
			String sql = "Insert INTO f_worker_state(id,flightid,flightno,state,reporter,comments,updatetime,updateuser) " +
					" Values(s_f_ac_state.nextval,?,?,?,?,?,?,?)";
			
			//判断一下如果最新的状态大于航班计划表中的状态，更新为最大状态
			String updateSql = "update f_flight_schedule t set workforce_state="+state+" where t.flt_pk="+flightid; 
			//" and workforce_state < "+state+""; 海伟，放开状态更新限制，处理工作完成后再返回的情况
			
			//同时把工作状态的更新加入到工作通报中
			String reportSql = "insert into f_flightreport(id,flightid,flightno,type,reporter,recipient,reporttime,reportcontent,comments,updatetime,updateuser )";
			reportSql += "values(s_f_flightreport.nextval,?,?,?,?,?,?,(";
			reportSql +="select d.valuecn from b_dictionary d where d.classname='FLIGHTSCHEDULE' AND d.attributename='WORKFORCESTATE' and d.value="+state+" and rownum=1";
			reportSql += "),?,sysdate,?)";
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql, flightid, flightno, state, reporter, comment,stateTime, user);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql);
			
			if("20".equals(state)){
				if(flightid_a!=null){
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, reportSql, flightid_a, flightno_a, "工作状态", reporter,user, stateTime, comment, user);
				}else{
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, reportSql, flightid, flightno, "工作状态", reporter,user, stateTime, comment, user);
				}
			}else{
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, reportSql, flightid, flightno, "工作状态", reporter,user, stateTime, comment, user);
			}
			
			dmo.commit(ApmsConst.DS_APMS);
			logger.debug("新增航班["+flightid+"]工作状态["+state+"]信息成功！");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("新增航班["+flightid+"]工作状态失败!", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 航班变动信息批量确认
	 * @param user
	 * @param mapList
	 * @throws Exception
	 */
	public void flightChangeBatchConfirm(String user,List<Map<String,String>> mapList) throws Exception{
		try{
			for(int i=0;i<mapList.size(); i++){
				Map<String, String> map = mapList.get(i);
				String changelogid = map.get("ID");
				String recipient = map.get("RECIPIENT");
				String bdtimeStr = map.get("BDTIME");
				
				Date bdtime = DateUtil.StringToDate(bdtimeStr, "yyyy-MM-dd HH:mm:ss");
				
				flightChangeConfirm(changelogid, recipient, bdtime);
			}
			logger.debug("航班变动信息批量确认成功！");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 变动信息报时确认
	 * @param changeLogId
	 * @param recipient
	 * @param bdtime
	 * @throws Exception
	 */
	public void flightChangeConfirm(String changeLogId,String recipient,Date bdtime) throws Exception{
		try{
			String sql = "update f_flight_changelog c set c.isboardcast=1,recipient=?,boardcasttime=? where c.id=?";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql,recipient,bdtime,changeLogId);
			dmo.commit(ApmsConst.DS_APMS);
			logger.debug("确认报时成功！");
		}catch (Exception e){
			e.printStackTrace();
			logger.error("确认报时失败!", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 更新变动信息并记录报时
	 * @param changeLogId
	 * @param recipient
	 * @param reporter
	 * @param bdtime
	 * @param isrecord
	 * @throws Exception
	 */
	public void flightChangeBoardCast(String changeLogId,String recipient,String reporter,Date bdtime
			,String flightid,String flightno,String reporttype,String reportcontent) throws Exception{
		try{
			String sql = "update f_flight_changelog c set c.isboardcast=1,recipient=?,boardcasttime=? where c.id=?";
			
			String rptSql = "INSERT INTO F_FLIGHTREPORT(ID,FLIGHTID,FLIGHTNO,TYPE,REPORTER,RECIPIENT,REPORTTIME,REPORTCONTENT,UPDATETIME,UPDATEUSER) "
					+ " VALUES(S_F_FLIGHTREPORT.NEXTVAL,?,?,?,?,?,?,?,sysdate,?)";
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql, recipient,bdtime, changeLogId);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, rptSql, flightid,flightno,"航班变动",reporter, recipient,bdtime,reportcontent,reporter);
			
			dmo.commit(ApmsConst.DS_APMS);
			logger.debug("确认报时并通报成功！");
		}catch (Exception e){
			e.printStackTrace();
			logger.error("确认报时并通报失败!", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 航班计划新建
	 * @param user 创建 人
	 * @param flightInfo
	 * @return
	 * @throws Exception
	 */
	public String flightCreate(String user, Map<String, Object> flightInfo) throws Exception{
		try{
			FlightManageService mngService = new FlightManageService();
			String res = mngService.flightCreate(user ,flightInfo);
			
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据模板创建航班计划失败！", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 创建航班通报记录
	 * @param user 创建 人
	 * @param reportInfo 通报信息
	 * @return
	 * @throws Exception
	 */
	public String flightReportAdd(String user, Map<String, Object> reportInfo) throws Exception{
		try{
			FlightManageService mngService = new FlightManageService();
			
			mngService.flightReportAdd(user ,reportInfo);
			
			return "新增航班通报记录成功";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增航班通报记录失败！", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	
	/**
	 * 航班提示信息确认
	 * @param user
	 * @param reportInfo
	 * @throws Exception
	 */
	public void flightRemindConfirm(String user, Map<String, Object> reportInfo) throws Exception{
		try{
			RemindManageService mngService = new RemindManageService();
			
			mngService.flightRemindConfirm(user ,reportInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用RemindManageService.flightRemindConfirm失败！", e);
			throw e;
		} finally {
			new CommDMO().releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public ArrayList<AirportPositionVo> getAirportPositionList(String aptId) throws Exception{
		try{
			logger.debug("获取机场停机位信息..");
			AirportMapService mapservice = new AirportMapService();
			return mapservice.getAirportPositionList(aptId);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取机场停机位信息失败！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/** 获取建筑物信息列表 */
	public ArrayList<Map<String,Object>> getAirportBuildingList(String aptId) throws Exception{
		try{
			String sql = "select id,airportid,code,name,infodesc,imageurl,longitude,latitude,x,y,widthscale,heightscale,rotation,comments ";
			sql += " from b_airport_building";
			sql += " where airportid =  " + aptId;	
			
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS,sql);
			ArrayList<Map<String,Object>> buildingList = new ArrayList<Map<String,Object>>(vos.length);
			
			for(int i=0;i<vos.length;i++)
			{
				Map<String,Object> building = HashVoUtil.hashVoToMapLowerCase(vos[i]);
				buildingList.add(building);
			}
			
			return buildingList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取机坪建筑物位信息失败！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void updateAcstopGeoInfo(String aptid,String stopcode,String x,String y,String rotation) throws Exception{
		try{
			AirportMapService mapservice = new AirportMapService();
			mapservice.updateAcstopGeoInfo(aptid, stopcode, x, y, rotation);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新机位地理信息失败！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 用户登录
	 * @param loginname
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public LoginUserVo doLogin(String loginname, String pwd) throws Exception{
		LoginService service = new LoginService();
		return service.doLogin(loginname, pwd);
	}
    /**
     * 通过飞机号查询DD单信息
     * @param acid
     * @return
     * @throws Exception
     */
	public SimpleHashVO[] getDdInfoByAcId(String acid) throws Exception{
		try{
			String ddQuerySql="select dd_no,defect_rpt,rect,decode(m_flag,0,'否',1,'是',null) m_flag" +
					" from w_dd_info where 1=1" +
					" and srm_code ='1' "+ //只打印放在飞机上的DD单
					" AND ( (finish_sign =0) or (finish_sign =2 and trunc(sysdate) <= TARGET_DATE) )" + //查询未关闭或者保留延期
					" and  ac_id='"+acid+"' order by OPERATE_TIME DESC";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, ddQuerySql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 * 通过航班id查提醒单
	 * @param flightid_a
	 * @param flightid_d
	 * @return
	 * @throws Exception
	 */
	
	public SimpleHashVO[] getRemindInfoByFlightId(String flightid_a,String flightid_d) throws Exception{
		try{
			String defectremindQuerysql="select decode(wd.TIPTYPE,0,'生产运行类',1,'技术提醒类',2,'管理要求类',3,'提醒确认类',4,'要客类',5,'航材接送类') tiptype,wd.reminddesc,wd.feedback "
				+" from w_defectremind_ins wdi,W_DEFECTREMIND wd where wdi.REMINDID=wd.id and wdi.STATUS=0  and wdi.FLIGHTID=any("+flightid_a+","+flightid_d+")";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, defectremindQuerysql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 * 通过航班id查cfd信息
	 * @param flightid_a
	 * @param flightid_d
	 * @return
	 * @throws Exception
	 */
	public SimpleHashVO[] getCFDInfoByFlightId(String flightid_a,String flightid_d) throws Exception{
		try{
			String cfdQuerysql="select '故障' type,fault_source,fault_content" 
				+" from a_cfd_fault acf,a_cfd_head ach where acf.msg_no=ach.msg_no and ach.flightid=any("+flightid_a+","+flightid_d+")"
				+" union all"
				+" select '告警' type,null fault_source,warn_content fault_content" 
				+" from a_cfd_warning acw,a_cfd_head ach where acw.msg_no=ach.msg_no and ach.flightid=any("+flightid_a+","+flightid_d+")";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, cfdQuerysql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 * 通过航班id查cfd故障信息
	 * @param flightid_a
	 * @param flightid_d
	 * @return
	 * @throws Exception
	 */
	public SimpleHashVO[] getFaultCFDInfoByFlightPk(String flight_pk) throws Exception{
		try{
			String cfdQuerysql="select  to_char(af.REPORTTIME, 'HH24:MI:SS') gmt,af.PHASE ph," 
				+" af.ATANO_MAJOR || '-' || af.ATANO_MINOR || '-' || af.ATANO_SUB ata,af.FAULT_SOURCE,af.FAULT_CONTENT from v_a_cfd_fault af where af.flightid="+flight_pk+" order by af.REPORTTIME,af.PHASE";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, cfdQuerysql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 * 通过航班id查cfd告警信息
	 * @param flightid_a
	 * @param flightid_d
	 * @return
	 * @throws Exception
	 */
	public SimpleHashVO[] getWarningCFDInfoByFlightPk(String flight_pk) throws Exception{
		try{
			String cfdQuerysql=" select  to_char(aw.REPORTTIME, 'HH24:MI:SS') gmt," 
				+" aw.PHASE ph,aw.ATANO_MAJOR || '-' || aw.ATANO_MINOR  ata,aw.WARN_CONTENT from v_a_cfd_warning aw where aw.flightid="+flight_pk+" order by aw.REPORTTIME,aw.PHASE";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, cfdQuerysql);
			logger.debug("执行语句："+cfdQuerysql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 * 通过机位号和机场查询该机位的提示信息
	 * @param apcode
	 * @param code_3
	 * @return
	 * @throws Exception
	 */
	public SimpleHashVO[] getACPInfoByAPAndCode_3(String apcode,String code_3) throws Exception{
		try{
			String acPoTipQuerysql="select tipinfo from b_airport_positiontip bapt,b_airport_position bap,b_airport  air"
				+" where bapt.ap_positionid = bap.id and bap.code = '"+apcode+"' and bap.airportid = air.id and  air.code_3='"+code_3+"'";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, acPoTipQuerysql);
			logger.debug("执行语句："+acPoTipQuerysql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void dailyScheduleAdd(String comments,String upodatetime,String updateuser) throws Exception{
		try{
			String sql0="delete from daily_schedule where sch_datestr='"+upodatetime+"'";
			String sql1="insert into daily_schedule(id,sch_date,sch_datestr,comments,authority,updatetime,updateuser)"
				+" values(s_daily_schedule.nextval,to_date('"+upodatetime+"','YYYY-MM-DD'),'"+upodatetime+"','"+comments+"',null,sysdate,'"+updateuser+"')";
			dmo.execAtOnceByDS(ApmsConst.DS_APMS, sql0);
			dmo.execAtOnceByDS(ApmsConst.DS_APMS, sql1);
			dmo.commit(ApmsConst.DS_APMS);
		}catch (Exception e) {
			e.printStackTrace();
			dmo.rollback(ApmsConst.DS_APMS);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		
	}
	public SimpleHashVO[] selectDailySchedule(String month) throws Exception{
		try{
			String sql="select id, sch_date,sch_datestr, comments, authority, updatetime, updateuser from daily_schedule where sch_datestr like '"+month+"%'";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			logger.debug("执行语句："+sql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
}
