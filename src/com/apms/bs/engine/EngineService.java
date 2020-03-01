package com.apms.bs.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

public class EngineService {
	protected Logger logger = NovaLogger.getLogger(this.getClass());

	private static EngineService fileService = null;

	private CommDMO dmo = new CommDMO();

	public static EngineService getInstance() {
		if (fileService != null)
			return fileService;

		fileService = new EngineService();

		return fileService;
	}

	/**
	 * 发动机拆换
	 * 
	 * @param oldengsn
	 * @param newengsn
	 * @return
	 */
	public void engChange(Map<String, Object> swapInfo) throws Exception{
		try {
			String sql = "";
			ArrayList<String> updateSqlList = new ArrayList<String>();
			String seqIdRemove = dmo.getSequenceNextValByDS(ApmsConst.DS_APMS, "S_l_eng_swaplog");
			String seqIdAdd = dmo.getSequenceNextValByDS(ApmsConst.DS_APMS, "S_l_eng_swaplog");
			
			String oldEngsn = swapInfo.get("OLDENGSN").toString();
			String newEngsn = swapInfo.get("NEWENGSN").toString();
			String timestr = swapInfo.get("TIME").toString();
			Date swaptime = DateUtil.StringToDate(timestr, "yyyy-MM-dd HH:mm:ss");
			//Date swapdate = DateUtil.StringToDate(DateUtil.getDateStr(swaptime), "yyyy-MM-dd");
			String sqlSwapDate = "to_date('"+DateUtil.getDateStr(swaptime)+"','yyyy-MM-dd')";
			
			sql = "select * from b_engine_info where engsn='" + oldEngsn + "'";
			HashVO engOld;
			HashVO[] engRemoveVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
			engOld = engRemoveVos[0];
			
			sql = "select * from b_engine_info where engsn='" + newEngsn + "'";
			HashVO[] newEngVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
			HashVO engNew = newEngVos[0];
			String acnum = engOld.getStringValue("acnum");
			
			//查询拆换日期前的最后一条航段日志，作为拆下发动机的小时循环
			String qrySql1 = "SELECT TSN,CSN,FIACTDEP FROM (SELECT TSN,CSN,FIACTDEP FROM L_ENG_FLIGHTLOG T" +
				" WHERE ENGSN='"+oldEngsn+"' AND FIACTDEP<"+sqlSwapDate+" ORDER BY FIACTDEP DESC) WHERE ROWNUM=1";
			HashVO[] engLogVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrySql1);
			if(engLogVos.length<1){
				throw new Exception("未拆到拆换日期之前的旧发动机航段日志数据!");
			}
			long oldTsn = engLogVos[0].getLognValue("TSN");
			long oldCsn = engLogVos[0].getLognValue("CSN");
			long tsn_deta = engNew.getLognValue("TSN") - oldTsn;
			long csn_deta = engNew.getLognValue("CSN") - oldCsn;

			// 拆下发动机日志
			sql = "insert into l_eng_swaplog(id,ACNUM,eng_model,engine_postion,engsn,swap_date,swap_date_str" +
					",SWAP_REASON,swap_action,fault_desc,time_oninstall,cycle_oninstall,time_onrepaired,cycle_onrepaired" +
					",TIME_ONREMOVE,CYCLE_ONREMOVE,REPAIR_FLAG,COMMENTS,UPDATE_MAN,UPDATE_TIME,trimvalue)"
					+ "select "+ seqIdRemove+ ",acnum,engmodelid,engwing_loc,engsn,"+sqlSwapDate+",'"+ DateUtil.getDateStr(swaptime)
					+ "','"	+ swapInfo.get("REASON")+ "',0,'"+ swapInfo.get("FAULT_DESC")
					+ "',time_oninstall,cycle_oninstall,time_onrepaired,cycle_onrepaired,"
					+ oldTsn +","+oldCsn+",chkstate,'"
					+ swapInfo.get("COMMENTS")+ "','"+ swapInfo.get("UPDATE_MAN")
					+ "' ,sysdate,trimvalue" +
					" from b_engine_info t,b_aircraft t1 where t.aircraftid=t1.id and engsn='"+oldEngsn+ "'";
			updateSqlList.add(sql);

			// 拆下LLP 日志..............................................
			sql = "insert into l_eng_llp_swaplog(id,engsn,eng_model,eng_swaplog_id,swap_date,swap_date_str,"
					+ "swap_reason,swap_action,fault_desc,part_area,part_name,part_no,part_sn,part_state,engtime_oninstall,engcycle_oninstall,"
					+ "ENGTIME_ONREMOVE,ENGCYCLE_ONREMOVE,TIME_ONINSTALL,CYCLE_ONINSTALL,comments,update_man,update_time)" 
					+ "select s_l_eng_llp_swaplog.nextval,t1.engsn,engmodelid," + seqIdRemove 
					+ ","+sqlSwapDate+",'" + DateUtil.getDateStr(swaptime) + "' ," + "'2',0,'" + swapInfo.get("FAULT_DESC")
					+ "',part_area,part_name,part_no,part_sn,1 part_state,engtime_oninstall,engcycle_oninstall,"
					+ oldTsn +","+oldCsn+",t.TIME_ONINSTALL,t.CYCLE_ONINSTALL"
					+",decode(comments,null,'',comments)||'/'||'"+ swapInfo.get("COMMENTS") 
					+ "','" + swapInfo.get("UPDATE_MAN") + "' update_man,sysdate update_time" +
					" from b_eng_llpparts t,b_engine_info t1 where t.engid=t1.id and t1.engsn='"+oldEngsn+ "'";
			updateSqlList.add(sql);

			// .........................................................

			double status = Double.parseDouble((String) swapInfo.get("REASON"));
			// 拆下发动机
			sql = "update b_engine_info set aircraftid=null,acnum=null,engwing_loc=null,ENGLOC=0,STATUS=" + status + 
					",tsn="+oldTsn+" ,csn="+oldCsn+ ",INFODATE="+sqlSwapDate+
					",DOWNDY="+sqlSwapDate+",update_time=sysdate,update_man='" + swapInfo.get("UPDATE_MAN") + "'" +
					" where engsn='" + oldEngsn + "'";
			updateSqlList.add(sql);

			// 装上发动机
			sql = "update b_engine_info set aircraftid=" + engOld.getIntegerValue("aircraftid") + ",acnum='" + engOld.getStringValue("acnum") 
					+ "',engwing_loc="+ engOld.getIntegerValue("engwing_loc") + ",ENGLOC=1,STATUS=1,upday="+sqlSwapDate
					+",DOWNDY=null,time_oninstall=TSN,cycle_oninstall=CSN,update_man='" + swapInfo.get("UPDATE_MAN") + "'" +
					",update_time=sysdate where engsn='"+ newEngsn + "'";
			updateSqlList.add(sql);

			sql = "insert into l_eng_swaplog(id,ACNUM,eng_model,engine_postion,engsn,swap_date,swap_date_str" +
					",SWAP_REASON,swap_action,fault_desc,time_oninstall,cycle_oninstall,time_onrepaired,cycle_onrepaired" +
					",TIME_ONREMOVE,CYCLE_ONREMOVE,REPAIR_FLAG,COMMENTS,UPDATE_MAN,UPDATE_TIME,trimvalue)"
					+ "select "+ seqIdAdd+ ",t.acnum,engmodelid,engwing_loc,engsn,"+sqlSwapDate+",'"+ DateUtil.getDateStr(swaptime)
					+ "','"+ swapInfo.get("REASON")+ "',1,'"+ swapInfo.get("FAULT_DESC")
					+ "',tsn,csn,time_onrepaired,cycle_onrepaired,null,null,chkstate,'"
					+ swapInfo.get("COMMENTS")+ "','"+ swapInfo.get("UPDATE_MAN") + "',sysdate,trimvalue" +
					" from b_engine_info t,b_aircraft t1 " +
					" where t.aircraftid=t1.id and engsn='" + newEngsn + "'";
			updateSqlList.add(sql);

			// 装上LLP 日志..............................................
			sql = "insert into l_eng_llp_swaplog(id,engsn,eng_model,eng_swaplog_id,swap_date,swap_date_str,"
					+ "swap_reason,swap_action,fault_desc,part_area,part_name,part_no,part_sn,part_state,engtime_oninstall,engcycle_oninstall,"
					+ "TIME_ONINSTALL,CYCLE_ONINSTALL,comments,update_man,update_time)" 
					+ "select s_l_eng_llp_swaplog.nextval,t1.engsn,engmodelid," + seqIdAdd 
					+ ","+sqlSwapDate+",'" + DateUtil.getDateStr(swaptime) + "' ," + "'2',1,'" + swapInfo.get("FAULT_DESC")
					+ "',part_area,part_name,part_no,part_sn,1 part_state,engtime_oninstall,engcycle_oninstall," 
					+ " t.TIME_ONINSTALL,t.CYCLE_ONINSTALL" +
					",decode(comments,null,'',comments)||'/'||'"+ swapInfo.get("COMMENTS") + "','" + swapInfo.get("UPDATE_MAN") + "' update_man,sysdate update_time" +
					" from b_eng_llpparts t,b_engine_info t1 where t.engid=t1.id and t1.engsn='"+ newEngsn + "'";
			updateSqlList.add(sql);
			// .........................................................
			dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			dmo.commit(ApmsConst.DS_APMS);
			//发动机日志处理 zhangzy 2015/10/22
			
			String time_oninstall = engNew.getStringValue("time_oninstall");
			String cycle_oninstall = engNew.getStringValue("cycle_oninstall");
			String time_onrepaired = engNew.getStringValue("time_onrepaired");
			String cycle_onrepaired = engNew.getStringValue("cycle_onrepaired");
			String engmodelId = engNew.getStringValue("engmodelid");
			
			//插入一条新发动机的初始化日志
			String logSql = "insert into l_eng_flightlog(id,fiflbsn,global_pk,acnum,add_cycle,add_time,computedstatus,engsn,engmodelid" +
				",tsn,csn,time_oninstall,modifystatus,cycle_oninstall,time_onrepaired,cycle_onrepaired,fidate,fiactdep)" +
				"select s_l_eng_flightlog.nextval,CONCAT(CONCAT(replace(substr('"+ engOld.getStringValue("infodate")+ "',0,10),'-',''),t1.aircraftsn)" +
				",s_l_eng_flightlog.currval),s_l_eng_flightlog.currval,aircraftsn,0,0,1,engsn,engmodelid,tsn,csn,time_oninstall" +
				",2,cycle_oninstall,time_onrepaired,cycle_onrepaired," + sqlSwapDate + ","+sqlSwapDate+
				" from b_engine_info t,b_aircraft t1" +
				" where t.aircraftid=t1.id and engsn='"+ newEngsn+ "'";
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, logSql);
			
			//先删除可能存在的daily日志
			String delSql = "delete l_eng_flightlog_daily t where t.engsn='"+newEngsn+"' and t.acnum='"+acnum+"' and fidate="+sqlSwapDate;
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delSql);
			
			//写入发动机daily日志
			String dailyLogSql = "insert into l_eng_flightlog_daily(id,upday,fidate,acnum,engsn,engmodelid,tsn" +
				",csn,time_oninstall,cycle_oninstall,add_cycle,add_time,time_onrepaired,cycle_onrepaired,computedstatus,updatetime)"
				+ "select s_l_eng_flightlog_daily.nextval,upday,"+sqlSwapDate+",aircraftsn,engsn,engmodelid,tsn" +
				",csn,time_oninstall,cycle_oninstall,0 add_time,0 add_cycle,time_onrepaired,cycle_onrepaired,1 computedstatus,sysdate" +
				" from b_engine_info t,b_aircraft t1 where t.aircraftid=t1.id and engsn='"+newEngsn+"'";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, dailyLogSql);
			
			//更新当前时间点之后的日志数据为新发动机日志数据				
			String updateSql1 = "update l_eng_flightlog l set tsn=tsn+("+tsn_deta+"),csn=csn+("+csn_deta+")"+
				",time_oninstall="+time_oninstall+",time_onrepaired="+time_onrepaired+
				" ,engsn='"+newEngsn+"',engmodelid="+engmodelId+",cycle_oninstall="+cycle_oninstall+"" +
				",cycle_onrepaired="+cycle_onrepaired+",updatetime=sysdate"+
				" where acnum='"+acnum+"' and engsn='"+oldEngsn+"' and fidate > " + sqlSwapDate + "";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql1);
			
			//更新daily日志数据为新数据
			String updateSql2 = "update l_eng_flightlog_daily l set tsn=tsn+("+tsn_deta+"),csn=csn+("+csn_deta+")"+
				",time_oninstall="+time_oninstall+",time_onrepaired="+time_onrepaired+
				" ,engsn='"+newEngsn+"',engmodelid="+engmodelId+",cycle_oninstall="+cycle_oninstall+"" +
				",cycle_onrepaired="+cycle_onrepaired+",updatetime=sysdate,UPDAY="+sqlSwapDate+
				" where acnum='"+acnum+"' and engsn='"+oldEngsn+"' and fidate > " + sqlSwapDate + "";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql2);
			
			dmo.commit(ApmsConst.DS_APMS);
			
			//发动机日志处理 zhangzy 2015/10/22 END
			
			// 拆下发动机同时记录拆换记录，写入快照表

			// 装上发动机
			// 拆下发动机后其附件处理
			fileDealAfterSwap(swapInfo, seqIdRemove);
		} catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			logger.error("拆换发动机失败!",e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

	}

	public void fileDealAfterSwap(Map<String, Object> swapInfo, String removeLogId) throws Exception {
		CommDMO dmo = new CommDMO();
		String insertSql = " update b_fileinfo a " + " set a.origin_table='L_ENG_SWAPLOG',a.origin_id=?,a.comments='发动机拆换后该文件的关联转移至该发动机的拆换日志' "
				+ " where a.origin_id=(select c.id from b_engine_info c where c.engsn=?) ";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, removeLogId, swapInfo.get("OLDENGSN"));
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("拆换后附件处理成功！");
	}
	
	/**
	 * 发动机日志补录，处理没有飞航班但是有小时循环的情况
	 * @param addObj
	 * @throws Exception
	 */
	public void addHourCycLog(Map<String, Object> addObj) throws Exception{
		String updateUser = addObj.get("UPDATEUSER").toString();
		String time_str = addObj.get("DATETIME").toString();
		Date datetime = DateUtil.StringToDate(time_str, "yyyy-MM-dd HH:mm:ss");
		double hour = new Double(addObj.get("HOUR").toString());
		double minute = hour*60;
		double cycle = new Double(addObj.get("CYCLE").toString());
		String engsn = addObj.get("ESN").toString();
		String sqlDateStr = "to_date('"+DateUtil.getDateStr(datetime)+"','yyyy-MM-dd')";
		String sqlTimeStr = "to_date('"+time_str+"','yyyy-mm-dd HH24:MI:SS')";
		
		//找到补录时间之前的最新的总小时、循环
		String sql = "select fiactdep,tsn,csn from l_eng_flightlog t where engsn = '"+engsn+"' and fiactdep="
			+ "(select max(fiactdep) from l_eng_flightlog t2 where t2.engsn='"+engsn+"' "
			 + "and t2.fiactdep < "+sqlTimeStr+" )" ;
		
		double oldtsn = 0;
		double oldcsn = 0;
		
		HashVO[] engflylogs = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
		//添加发动机航段日志
		if (engflylogs.length > 0) {
			oldtsn = engflylogs[0].getDoubleValue("tsn");
			oldcsn = engflylogs[0].getDoubleValue("csn");
		} 
		
		String fiflbsn = UUID.randomUUID().toString().replace("-", "");
		String addSql = "insert into l_eng_flightlog(id,fiflbsn,global_pk,acnum,add_cycle,add_time,computedstatus" 
				+ ",engsn,engmodelid,tsn,csn,ENGWING_LOC" 
				+ ",time_oninstall,modifystatus,cycle_oninstall,time_onrepaired,cycle_onrepaired,fidate,fiactdep,userid)"
				+ "select s_l_eng_flightlog.nextval,'" +fiflbsn+"',s_l_eng_flightlog.currval,aircraftsn" 
				+","+cycle+","+minute+",3,engsn,engmodelid," + oldtsn+","+oldcsn+",ENGWING_LOC"
				+",time_oninstall,3 datastate,cycle_oninstall,time_onrepaired,cycle_onrepaired," 
				+ sqlDateStr + "," +sqlTimeStr + ",'"+updateUser+"'"
				+ " from b_engine_info t,b_aircraft t1 where t.aircraftid=t1.id and engsn='" + engsn + "'";

		dmo.executeUpdateByDS(ApmsConst.DS_APMS, addSql);
		
		//查找每日日志
		sql = "select t.id from l_eng_flightlog_daily t where t.engsn='"+engsn+"' and t.fidate=" + sqlDateStr;
		HashVO[] engflylogdailys = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
		if (engflylogdailys.length > 0) {
			HashVO engflylogdaily1 = engflylogdailys[0];
			sql = "update l_eng_flightlog_daily set add_cycle=add_cycle+" + cycle + ",add_time=add_time+" + minute 
					+ ",computedstatus='3' where id=" + engflylogdaily1.getStringValue("id");
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		} else {
			sql = "insert into l_eng_flightlog_daily(id,upday,fidate,acnum,engsn,engmodelid,tsn,csn,ENGWING_LOC"
				+",time_oninstall,cycle_oninstall,add_cycle,add_time,time_onrepaired,cycle_onrepaired,computedstatus)"
				+ "select s_l_eng_flightlog_daily.nextval,upday,"+sqlDateStr+" fidate,aircraftsn,engsn,engmodelid"
				+"," + oldtsn+","+oldcsn+",ENGWING_LOC,time_oninstall,cycle_oninstall,"+cycle+","+minute
				+",time_onrepaired,cycle_onrepaired,DATASTATE computedstatus " +
				" from b_engine_info t,b_aircraft t1 where t.aircraftid=t1.id and engsn='"+ engsn + "'";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		}
		
		//更新当前数据日期之后的总时间、总循环
		String upt1 = "update l_eng_flightlog t set tsn=tsn+"+minute+",csn=csn+"+cycle+" where engsn='"+engsn+"' and fiactdep>="+sqlTimeStr;
		String upt2 = "update l_eng_flightlog_daily t set tsn=tsn+"+minute+",csn=csn+"+cycle+" where engsn='"+engsn+"' and fidate>="+sqlDateStr;
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, upt1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, upt2);
		
		dmo.commit(ApmsConst.DS_APMS);
		
		logger.debug("数据补录时，增加日志数据完成！");
		
	}
	
}
