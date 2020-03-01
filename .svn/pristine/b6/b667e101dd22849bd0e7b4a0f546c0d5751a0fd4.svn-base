package com.apms.bs.engine;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

/**
 * 发动机飞行日志同步与计算
 * 
 * @author jerry
 * @date Jun 11, 2016
 */
public class EngineFlightLogService {

	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO(false);

	/**
	 * 发动机日志信息同步
	 * @param baseOrgId
	 * @param beginDate 如果startDate为空，表示从数据中的最新计算日期开始计算
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public int extractFlightLog(final Date startDate, Date endDate) throws Exception {
		int recordCount = 0;
		Date beginDate;//实际计算开始日期
		
		logger.info("采集发动机飞行日志数据开始！");
		// 取得飞机号和更新的起始日期
		String queryEng = "select id,engsn,acnum,baseorgid,engmodelid,engwing_loc,engloc,status,infodate,tsn,csn"
				+ ",upday,downdy,time_oninstall,cycle_oninstall,time_onrepaired,cycle_onrepaired,datastate" 
				+ ",chkstate" + " from b_engine_info where status in(1,2) and ENGWING_LOC is not null  order by engsn";
		
		HashVO[] engVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryEng);

		for (int i = 0; i < engVos.length; i++) {
			HashVO engVo = engVos[i];
			String engsn = engVo.getStringValue("engsn");
			Date upday = engVo.getDateValue("upday");// 默认取装上日期

			try{
				//如果有一台发动机的更新有异常，断续执行其它发动机的数据更新
				logger.info("采集发动机" + engsn + "飞行日志数据开始！");
//				// 取得最新的发动机正常数据
//				String querystr = "select tsn,csn,fidate from (select tsn,csn,fidate from l_eng_flightlog_daily t " 
//					+ "where computedstatus=1 and engsn=? order by fidate desc) where rownum<2";
//				HashVO[] basicVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, querystr, engsn);
//
//				if (basicVos.length > 0) {
//					HashVO basicVo = basicVos[0];
//					Date fidate = basicVo.getDateValue("fidate");
//					if(startDate == null || DateUtil.dayDiff(startDate, fidate)>0 ){ //没有指定startdate，使用最新的数据进行计算
//						beginDate = fidate;
//					}else{
//						beginDate = startDate;
//					}
//					
//				}else{//如果发动机日志表中没有数据，从拆换中表开始计算
//					beginDate = DateUtil.moveDay(new Date(), -365*20);//向前推20年
//				}
				if(upday != null ){
					//设置数据计算的起始日期为装上日期 --zhangzy 20180816
					beginDate = upday; 
				}else{
					beginDate = DateUtil.moveDay(new Date(), -365*20);//向前推20年
				}
				
				if (endDate == null) {
					endDate = new Date();
				}

				int nums = extractSingleEngFlightLog(engsn, beginDate, endDate);
				recordCount += nums;

				// 预计航段日志
				// Flyupdate=Extract_FlightLog_predict(voacid1,Flyupdate,baseOrgId);

				// logger.info("采集发动机"+Flyupdate+"计划飞行日志数据结束！");
				// Flightlog_predict_compute(voacid1,Flyupdate,baseOrgId,0);

				// 计算预计发动机日志过程
				// Extract_EngFlightLog_predict( voeng1, startDate, endDate,
				// baseOrgId);
			}catch (Exception e) {
				logger.error("********发动机" + engsn + "飞行日志更新异常！！！！！");
				e.printStackTrace();
			}
			
		}
		logger.info("采集发动机飞行日志数据完成！");

		return recordCount;
	}

	/**
	 * 单个发动机飞行日志更新
	 * 
	 * @param engsn
	 * @param baseOrgId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public int extractSingleEngFlightLog(String engsn, Date startDate, Date endDate) throws Exception {
		int recordCount = 0;
		String sql = "";

		// 删除startdate日期之后的数据
		deleteEngineFlightLog(engsn, startDate, endDate);

		sql = "select acnum,engsn,swap_date,swap_action" 
				+ " ,nvl(time_oninstall,0) time_oninstall ,   nvl(cycle_oninstall ,0) cycle_oninstall" 
				+ " ,nvl(time_onrepaired,0) time_onrepaired , nvl(cycle_onrepaired,0) cycle_onrepaired" 
				+ ",eng_model engmoelid,ENGINE_POSTION ENGWING_LOC " 
				+ " from l_eng_swaplog t where engsn = '" + engsn
				+ "' order by engsn,swap_date asc ";
		// 查询拆换日志记录,排序后必须是装-拆-装-拆..
		HashVO[] swapVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
		for (int i = 0; i < swapVos.length; i = i + 2) {
			HashVO stallVo = swapVos[i];
			Date onDate = stallVo.getDateValue("swap_date"); // 装上日期
			Date offDate = DateUtil.moveDay(new Date(), 365 * 100); // 拆下日期，向后无限大
			if ((i + 1) < swapVos.length) { // 有拆的记录
				offDate = swapVos[i + 1].getDateValue("swap_date");
			}
			if (DateUtil.dayDiff(startDate, offDate) > 0) { // 要么是旧日期,要么已被拆下。在startdate后，发动机必须存在
				continue;
			}

			Date beginDate = startDate;
			if (DateUtil.dayDiff(onDate, startDate) > 0) {
				beginDate = onDate; // 取大的日期
			}

			logger.info("采集发动机" + engsn + "分段[" + DateUtil.getDateStr(beginDate) + "]->[" + DateUtil.getDateStr(offDate) + "]飞机日志！");
			// 采集飞行日志有的数据
			extractEngFlightLog(stallVo, engsn, beginDate, offDate);

			// 计算发动机分段总计
			recordCount += computeEngLog(stallVo, beginDate, offDate);
		}

		return recordCount;
	}

	/**
	 * 根据飞行日志同步发动机航段日志数据
	 * 
	 * @param stallEngvo
	 * @param engsn
	 * @param beginDate 分段同步的起点
	 * @param offDate 分段同步的终点
	 * @param baseOrgId
	 * @throws Exception
	 */
	private void extractEngFlightLog(HashVO stallEngvo, String engsn, Date beginDate, Date offDate) throws Exception {
		String acnum = stallEngvo.getStringValue("acnum");
		double time_install = stallEngvo.getDoubleValue("time_oninstall");
		double cycle_install = stallEngvo.getDoubleValue("cycle_oninstall");
		double time_onrepaired = stallEngvo.getDoubleValue("time_onrepaired");
		double cycle_onrepaired = stallEngvo.getDoubleValue("cycle_onrepaired");
		String engmodelid = stallEngvo.getStringValue("engmoelid");
		String wingloc = stallEngvo.getStringValue("ENGWING_LOC");

		String qrySql = "select fiflbsn,global_pk,acnum,fidate,nvl(FIACTLG,0) add_cycle,nvl(FIAIR,0) add_time,'" 
			+ engsn + "' engsn,"+ wingloc +"," + engmodelid + " engmodelid,0 tsn ,0 csn," + time_install + " time_oninstall ,"
			+ cycle_install + " cycle_oninstall," + time_onrepaired + " time_onrepaired," 
			+ cycle_onrepaired + " cycle_onrepaired,0 modifystatus,0 computedstatus,fiactdep " 
			+ "from l_ac_flightlog where acnum='" + acnum
			+ "' and fidate>= " + DateUtil.toSqlDate(beginDate) 
			+ " and fidate<= " + DateUtil.toSqlDate(offDate) + " and computedstatus=1" 
			+ " order by fiactdep";

		String addSql = "insert into l_eng_flightlog(id,fiflbsn,global_pk,acnum,fidate,add_cycle,add_time,engsn,ENGWING_LOC,engmodelid"
				+ ",tsn,csn,time_oninstall,cycle_oninstall,time_onrepaired,cycle_onrepaired,modifystatus,computedstatus,fiactdep,updatetime,userid)"
				+ " values(S_l_eng_flightlog.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,'system')";
		// 批量数据导入
		dmo.executeImportByDS(ApmsConst.DS_APMS, qrySql, getFromCols(18), ApmsConst.DS_APMS, addSql, 1000);
		dmo.commit(ApmsConst.DS_APMS);

		logger.info("采集发动机" + engsn + "分段[" + DateUtil.getDateStr(beginDate) + "]->[" + DateUtil.getDateStr(offDate) + "]飞行日志数据完成！");
		return;
	}

	/**
	 * 计算发动机航段日志
	 * 
	 * @param stallEngvo
	 * @param acnum
	 */
	public int computeEngLog(HashVO stallEngvo, Date beginDate, Date offDate) throws Exception {
		CommDMO dmo = new CommDMO(false);

		String acnum = stallEngvo.getStringValue("acnum");
		String engmodelid = stallEngvo.getStringValue("engmoelid");
		String engsn = stallEngvo.getStringValue("engsn");
		Long time_install = stallEngvo.getLongValue("time_oninstall");
		Long cycle_install = stallEngvo.getLongValue("cycle_oninstall");
		String time_onrepaired = stallEngvo.getStringValue("time_onrepaired");
		String cycle_onrepaired = stallEngvo.getStringValue("cycle_onrepaired");
		Date upday = stallEngvo.getDateValue("swap_date");
		String wingloc = stallEngvo.getStringValue("ENGWING_LOC");

		long newTsn = time_install;
		long newCsn = cycle_install;
		Date lastinfodate = null;// 最新的日志计算日期
		
		String querystr = "select tsn,csn,fidate from (select tsn,csn,fidate from l_eng_flightlog_daily t " 
			+ " where computedstatus=1 and engsn=? order by fidate desc) where rownum<2";
		//查找最新的已计算发动机日志
		HashVO[] oldvos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, querystr, engsn);
		if (oldvos.length > 0) {
			HashVO basicVo = oldvos[0];
			Date fidate = basicVo.getDateValue("fidate");
			//判断日期是在装机之前还是之后
			if( DateUtil.dayDiff(fidate, upday)>0 ){
				newTsn = basicVo.getLognValue("tsn");
				newCsn = basicVo.getLognValue("csn");
				lastinfodate = fidate;
			}else{
				newTsn = time_install;
				newCsn = cycle_install;
				lastinfodate = upday;
			}
		} else {
			//使用装上时的值
			newTsn = time_install;
			newCsn = cycle_install;
			lastinfodate = upday;
		}
		
		String uptLogSql = "update l_eng_flightlog set computedstatus=1,tsn=?,csn=? where id=?";
		String uptEngSql = "update b_engine_info set datastate=0,infodate=?"
				+ ",tsn=?,csn=?,update_time=sysdate,update_man='englog_compute' where engsn=?";
		
		// 增量更新每日日志
		String uptDailySql = "update l_eng_flightlog_daily set computedstatus=1,tsn=?,csn=?" 
				+ ",add_time=add_time+?,add_cycle=add_cycle+? where fidate=? and engsn=?";
		String insertDailySql = "insert into l_eng_flightlog_daily(id,updatetime,fidate,engsn,ENGWING_LOC,engmodelid,tsn,csn"
				+ ",time_oninstall,cycle_oninstall,time_onrepaired,cycle_onrepaired,add_time,add_cycle,acnum,upday,computedstatus)"
				+ " values (s_l_eng_flightlog_daily.nextval,sysdate,?,?,?,?,?  ,?,?,?,? ,?,?,?,?,?,1)";

		String sql = "select nvl(add_time,0) add_time,nvl(add_cycle,0) add_cycle,fidate,id,fiactdep from l_eng_flightlog t" 
				+ " where computedstatus=0 and engsn=? order by fiactdep asc ";

		HashVO[] logVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql, engsn);

		Date curDate = null;
		for (int i = 0; i < logVos.length; i++) {
			HashVO logVo = logVos[i];
			String logId = logVo.getStringValue("ID");
			long addTime = logVo.getLognValue("add_time");
			long addCyc = logVo.getLognValue("add_cycle");

			newTsn = newTsn + addTime;
			newCsn = newCsn + addCyc;

			if (i == 0) {// 第一条数据
				curDate = logVo.getDateValue("fidate");
				//第一次循环，向前推一天
				if ( DateUtil.dayDiff(lastinfodate, curDate) >=0 ) { // 第一次计算，没有之前的数据
					lastinfodate = DateUtil.moveDay(curDate, -1);
				}

			} else {
				lastinfodate = curDate;
				curDate = logVo.getDateValue("fidate");
			}

			int dayDiff = DateUtil.dayDiff(curDate, lastinfodate);
			// 判断dayDiff 是否有变化
			if (dayDiff == 0) {// 同一天
				// 更新每日日志
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptDailySql, newTsn, newCsn, addTime, addCyc, curDate, engsn);
			} else if (dayDiff == 1) {
				// 新的日志
				// fidate,engsn,engmodelid,tsn,csn,time_oninstall,cycle_oninstall,time_onrepaired,cycle_onrepaired
				// ,add_time,add_cycle,acnum,upday
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertDailySql, curDate, engsn,wingloc, engmodelid, newTsn, newCsn, time_install, cycle_install, time_onrepaired, cycle_onrepaired, addTime, addCyc,
						acnum, upday);
			} else if (dayDiff > 1) {
				// 空档时,小时循环不就加上，前面已加上，这里减去
				long tempTsn = newTsn - addTime;
				long tempCsn = newCsn - addCyc;
				// 有断档 循环写入空缺的日期
				for (int m = 1; m < dayDiff; m++) {
					Date engDate = DateUtil.moveDay(lastinfodate, m);

					dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertDailySql, engDate, engsn,wingloc, engmodelid, tempTsn, tempCsn, time_install, cycle_install, time_onrepaired, cycle_onrepaired, 0, 0, acnum,
							upday);
				}
				// 写入当前的数据记录
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertDailySql, curDate, engsn,wingloc, engmodelid, newTsn, newCsn, time_install, cycle_install, time_onrepaired, cycle_onrepaired, addTime, addCyc,
						acnum, upday);
			}
			// 更新航段日志
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptLogSql, newTsn, newCsn, logId);

			// 每100条提交一次
			if (i % 100 == 0) {
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptEngSql, curDate, newTsn, newCsn, engsn);
				dmo.commit(ApmsConst.DS_APMS);
			}
		}

		if (logVos.length > 0) {
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptEngSql, curDate, newTsn, newCsn, engsn);
			dmo.commit(ApmsConst.DS_APMS);
			logger.debug("发动机[" + engsn + "]航段日志计算完成[" + logVos.length + "]条");
		} else {
			logger.debug("发动机[" + engsn + "]未查到需要计算的航段日志");
		}

		return logVos.length;
	}
	
	/**
	 * 删除指定日期之后的发动机日志数据
	 * 
	 * @param engsn
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	public void deleteEngineFlightLog(String engsn, Date startDate, Date endDate) throws Exception {
		logger.debug("删除[" + engsn + "]在[" + DateUtil.getLongDate(startDate) + "]之后的发动机日志");
		// 初始化和补录数据不允许进行DELETE,只删除正常和修正的数据
		String delLogSql = "delete from l_eng_flightlog where engsn=? and fidate>=? and modifystatus=any(0,1) ";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, delLogSql, engsn, startDate);

		String delDailySql = "delete from l_eng_flightlog_daily where engsn=? and fidate>=?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, delDailySql, engsn, startDate);
		dmo.commit(ApmsConst.DS_APMS);

		// 更新该日期之后的数据状态为未计算
		String sql = "update l_eng_flightlog l set l.computedstatus=0 where engsn=? and l.computedstatus>0 " + " and fidate>=?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql, engsn, startDate);

		dmo.commit(ApmsConst.DS_APMS);
	}

	private int[] getFromCols(int length) {
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i + 1;

		return fromcols;
	}

}