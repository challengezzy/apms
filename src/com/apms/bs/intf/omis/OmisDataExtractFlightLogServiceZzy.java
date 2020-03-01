package com.apms.bs.intf.omis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.JDOMException;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

/**
 * OMIS飞行日志采集
 * 
 * @author jerry
 * @date Nov 19, 2014
 */
public class OmisDataExtractFlightLogServiceZzy {

	private Logger logger = NovaLogger.getLogger(this.getClass());

	private CommDMO dmo = new CommDMO();

	/** 格式化时间转换 */
	SimpleDateFormat dtSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String delLog = "delete from l_ac_flightlog where acnum=? and fidate>=? and modifystatus=0";
	private String delLogDy = "delete from l_ac_flightlog_daily where acnum=? and vdfcdate>=?";
	private String uptLogState = "update l_ac_flightlog set computedstatus=0 where acnum=? and fidate>=? and modifystatus=1";
	private String uptOutLink = "update b_apms_outlink set update_time=? where linksys='OMISFLY' AND model=? ";

	private String qryLogByIdSql = "select id,hashval from l_ac_flightlog where fiflbsn=? ";
	/**
	 * 所有飞机飞行日志更新
	 * 
	 * @param baseorgId 飞机属性基地
	 * @param dayoff 向前取数据的天数
	 * @param acsn 机号
	 * @return
	 * @throws Exception
	 */
	public int extractFlightLog(String baseorgId,String dayOff,String acsn) throws Exception {
		int recordCount = 0;
		// 飞机号
		String flyUpdate = "";

		logger.info("采集OMIS库中飞行日志数据开始！");
		// 取得飞机号和更新的起始日期
		StringBuffer queryAcidSql = new StringBuffer();
		queryAcidSql.append("select a.linksys,a.update_time,b.aircraftsn,b.BASEORGID,b.ISFLYLOG");
		queryAcidSql.append(" from b_apms_outlink a,b_aircraft b ");
		queryAcidSql.append("  where a.model=b.aircraftsn and a.linksys='OMISFLY' AND b.stats='1' ");
		queryAcidSql.append("and b.BASEORGID = " +baseorgId);
		if(StringUtils.isNotEmpty(acsn)){
			queryAcidSql.append(" and b.aircraftsn='" + acsn + "' ");
		}
		queryAcidSql.append("");
		queryAcidSql.append(" order by b.aircraftsn ");
		

		HashVO[] vosAcid = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcidSql.toString());
		for (int i = 0; i < vosAcid.length; i++) {// 根据飞机号来取数据
			HashVO voAc = vosAcid[i];
			boolean isComputeFlyLog = voAc.getBooleanValue("ISFLYLOG"); //是否计算飞机日志
			
			if( !isComputeFlyLog ){
				continue;
			}
			
			// 飞机号
			String acnum = voAc.getStringValue("aircraftsn").replaceAll("-", "");
			String acBaseOrgId = voAc.getStringValue("BASEORGID");
			
			//acnum = "B6032";
			
			int dayoff = new Integer(dayOff);
			Date beginDate = voAc.getDateValue("update_time");
			if (beginDate == null) {
				beginDate = DateUtil.toDay(DateUtil.moveDay(new Date(), -dayoff));
			} else {
				beginDate = DateUtil.toDay(DateUtil.moveDay(beginDate, -dayoff));
			}

			// 取得大于等于beginTime1日期之后存在的数据，然后删除本地上大于等于beginTime1日期之后不存在的数据，同时把大于等于beginTime1日期之后的数据更改为待计算状态。
			// 删除本地从开始时间之后的在omis数据库上不存在的数据,修正和初始化和数据不动
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delLogDy, acnum, beginDate);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delLog, acnum, beginDate);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptLogState, acnum, beginDate); // 修改重新计算日期
			dmo.commit(ApmsConst.DS_APMS);

			// 运行飞行日志有的数据
			logger.info("采集[" + acnum + "]OMIS库中" + flyUpdate + "实际飞行日志数据开始！");

			flyUpdate = extractAcFlightLog(acnum, beginDate, acBaseOrgId,dayOff);

			// 计算飞行日志分段日志中的总计数据
			computeFlightLog(acnum, flyUpdate, acBaseOrgId, 0);

			// 计算飞行日志过程
			computeFlightLogDaily(acnum, flyUpdate, acBaseOrgId, 0);

			// 计算飞行日志完成之后的预计飞行时间
			// logger.info("采集OMIS库中" + flyUpdate + "计划飞行日志数据开始！");
			// flyUpdate = extractPredictFlightLog(acnum, flyUpdate, acBaseOrgId);
			//
			// // 计算计划飞行日志数据
			// computeFlightLogPredict(acnum, flyUpdate, acBaseOrgId, 0);

			logger.info("采集OMIS库中" + acnum + "飞行日志数据完成！");
		}
		logger.info("采集OMIS库中飞行日志数据完成！");

		return recordCount;
	}

	/**
	 * 同步单架飞机的飞行日志数据,只同步，不计算
	 * @param acnum 飞机号
	 * @param omisStartPoint
	 * @param baseorgId
	 * @return
	 */
	public String extractAcFlightLog(String acnum, Date beginTime, String baseorgId,String dayoff) throws Exception{

		// 修改逻辑，把数据删除再重新计算
		String omisID;
		String omisAcnum;
		Date omisFlightDate = null;
		String omisFlightNO;
		String omisDepApt;
		String omisArrApt;
		String flbid = "1";
		String global_pk = "";
		String begigTimeSql = DateUtil.toSqlDate(beginTime);
		Date optTime = new Date();

		if(dayoff == null || dayoff.length()==0){
			dayoff = "3";//默认3天
		}

		StringBuilder sb = new StringBuilder();
		sb.append("select * from ( "); //在老AMS中，ID_NO||GLOBAL_PK 作为主键
		sb.append("SELECT CONCAT(F.ID_NO, F.GLOBAL_PK) AS SYS_ID,F.GLOBAL_PK,D.AC_ID AS ACNUM,D.FLT_DATE");
		sb.append(", CONCAT(F.IATA_C, F.FLT_ID) AS FLIGHTNO,F.DEP_APT,F.ARR_APT,F.STD,F.STA");
		sb.append(",F.OUT_TIME, F.OFF_TIME, F.ON_TIME, F.IN_TIME, F.OFF_TIME_LOG, F.ON_TIME_LOG");
		sb.append(",F.FC, F.CON_FC, F.OPERATE_TIME");
		sb.append(" ,dbms_utility.get_hash_value(");
		sb.append(" D.AC_ID||D.FLT_DATE");
		sb.append("||F.IATA_C||F.FLT_ID||F.DEP_APT||F.ARR_APT||F.STD||F.STA");
		sb.append("||F.OUT_TIME||F.OFF_TIME||F.ON_TIME||F.IN_TIME||F.OFF_TIME_LOG||F.ON_TIME_LOG ");
		sb.append("||F.FC||F.CON_FC||F.OPERATE_TIME");
		sb.append(" ,1 ,2000000000 ) content_hashvalue");//做一个hash值
		sb.append(" FROM AIRCHINA.AC_FLY_LOG F, AIRCHINA.AC_FH_FC D ");
		//sb.append(" FROM OMIS_AC_FLY_LOG F, OMIS_AC_FH_FC D ");
		sb.append(" WHERE F.ID_NO = D.ID_NO");
		sb.append(" AND D.AC_ID = '"+ acnum +"'");
		sb.append(" AND D.FLT_DATE >= " + begigTimeSql + "- "+dayoff );//向前推dayoff天
		//sb.append(" AND (F.OPERATE_TIME IS NULL OR F.OPERATE_TIME > " +begigTimeSql + " )");//或者是有更新日期的
		sb.append(" ORDER BY D.FLT_DATE, F.STD ASC");
		sb.append(" ) where rownum < 10000 "); //限制单次的数量
		sb.append("");
		
		HashVO[] logVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_OMIS, sb.toString());
		for (int j = 0; j < logVos.length; j++) {
			HashVO vo = logVos[j];
			
			//判断数据是否有更新，如果无更新，跳过
			String hashvalue = vo.getStringValue("content_hashvalue");
			omisID = vo.getStringValue("SYS_ID");
			omisDepApt = vo.getStringValue("DEP_APT");
			omisArrApt = vo.getStringValue("ARR_APT");
			
			
			HashVO[] oldVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, qryLogByIdSql, omisID);
			if(oldVos.length > 0){
				String curHash = oldVos[0].getStringValue("hashval");
				if(hashvalue.equals(curHash)){ //数据存在并且没有变化，不处理
					continue;
				}
			}
			
			global_pk = vo.getStringValue("GLOBAL_PK");
			omisAcnum = vo.getStringValue("ACNUM");
			omisFlightDate = vo.getDateValue("FLT_DATE");
			omisFlightNO = vo.getStringValue("FLIGHTNO");
			Date omisStd = vo.getDateValue("STD");
			Date omisSta = vo.getDateValue("STA");
			optTime = vo.getDateValue("OPERATE_TIME");
			
			Date off_time = vo.getDateValue("off_time");
			Date on_time = vo.getDateValue("on_time");
			Date on_time_log = vo.getDateValue("on_time_log");
			Date off_time_log = vo.getDateValue("off_time_log");
			Date out_time = vo.getDateValue("out_time");
			Date in_time = vo.getDateValue("in_time");
			
			//判断omisff,omison的取值
			Date omisOff = omisStd;
			Date omisOn = omisSta;//默认
			Date omisOut ;
			Date omisIn ;
			
			if( isDateNotNull(off_time)){
				omisOff = off_time;
			}
			if( isDateNotNull(on_time) ){
				omisOn = on_time;
			}
			
			//有实际人工记录时间时，以记录时间为准
			if(isDateNotNull(on_time_log) ){
				omisOn = on_time_log;
			}
			if( isDateNotNull(off_time_log) ){
				omisOff = off_time_log;
			}
			
			//处理推出和靠桥时间
			if( isDateNotNull(out_time) ){
				omisOut = out_time;
			}else{
				omisOut = DateUtil.moveSecond(omisOff, -5);
			}
			
			if ( isDateNotNull(in_time) ){
				omisIn = in_time;
			}else{
				omisIn = DateUtil.moveSecond(omisOn, 5);
			}
			
			//换算成UTC时间
			omisOut = DateUtil.moveHour( omisOut, -8);
			omisOn = DateUtil.moveHour( omisOn, -8);
			omisOff = DateUtil.moveHour( omisOff, -8);
			omisIn = DateUtil.moveHour( omisIn, -8);

			//计算空中和轮档时间
			int airMin = Math.abs(DateUtil.dateDiff("minute", omisOn,omisOff ));
			int blockMin = Math.abs(DateUtil.dateDiff("minute", omisIn,omisOut  ));
			int omisFc = 1;//起落次数
			if (vo.getStringValue("fc") != null) {
				omisFc = vo.getIntegerValue("fc");
			}
			
			if (oldVos.length > 0) {//已存在数据
				//更新数据
				String sql0 = "update l_ac_flightlog set COMPUTEDSTATUS=0,FIDATE=?,FIFLTNO=?,FIDEPLOC=?"
				+ ",FIARVLOC=?,FIACTDEP=?," + "FIACTTAK=?, FIACTLAD=?,FIACTARV=?,FIACTLG=?"
				+ ",FISTA=2,FIAIR=?,FIBLOCK=?,updatetime=sysdate,hashval=? "
				+ " where fiflbsn=? and MODIFYSTATUS=0";

				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql0, omisFlightDate,omisFlightNO,omisDepApt
						,omisArrApt,omisOut,omisOff,omisOn,omisIn,omisFc,airMin,blockMin, hashvalue,omisID);

				// 如果有存在，则把整天的数据都设置为重新计算的状态
				String sql1 = "update l_ac_flightlog set COMPUTEDSTATUS=0,updatetime=sysdate where FIDATE>=? and acnum=? and COMPUTEDSTATUS<>2";
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1, omisFlightDate,acnum);
				
			} else { //数据新增
				String insertSql = "INSERT into l_ac_flightlog(ID,MODIFYSTATUS,COMPUTEDSTATUS,FIFLBSN,FIFLBID,global_pk" +
					",acnum,BASEORGID,FIDATE,FIFLTNO,FIDEPLOC,FIARVLOC,FIACTDEP,FIACTTAK,FIACTLAD,FIACTARV,FIACTLG" +
					",FISTA,FIAIR,FIBLOCK,UPDATETIME,USERID,hashval)"
					+ " VALUES(S_l_ac_flightlog.nextval,0,0,?,?,? "
					+ ",?,?,?,?,?,?, ?,?,?,?, ?, '2', ?,?,sysdate,0,?) ";
				
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,omisID,flbid,global_pk
						,omisAcnum,baseorgId,omisFlightDate,omisFlightNO,omisDepApt,omisArrApt
						,omisOut,omisOff,omisOn,omisIn, omisFc,airMin, blockMin,hashvalue);

			}

			String sql2 = "update l_ac_flightlog_daily set COMPUTEDSTATUS=0,updatetime=sysdate where vdfcDATE>=? and acnum=? and COMPUTEDSTATUS<>2";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2, omisFlightDate,acnum);
			dmo.commit(ApmsConst.DS_APMS);
			
		}// end for

		if ( optTime == null) {
			optTime = new Date();
		}

		dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptOutLink, optTime, acnum);
		dmo.commit(ApmsConst.DS_APMS);

		return DateUtil.getLongDate(optTime);
	}
	
	/**
	 * 飞机飞行航班日志计算
	 * 
	 * @param voacid
	 * @param startdate
	 * @param baseorgId
	 * @param status
	 * @return
	 */
	public String computeFlightLog(String acnum, String startdate, String baseorgId, int status) {

		long sumAirMin = 0;// 总计
		long sumBlockMin = 0;// 总计
		long sumomisFc = 0;// 总计

		long basicAirMinC = 0;// 当天总计
		long basicBlockMinC = 0;// 当天总计
		long basicomisFcC = 0;// 当天总计
		
		
		try {
			logger.info("计算" + acnum + "的飞行日志分段日志总计开始");
			String sql1 = "select * from (select flair_sum,fiblock_sum,fiactlg_sum from l_ac_flightlog t " +
					"where COMPUTEDSTATUS='1' and t.acnum=? order by fidate desc,fiactdep desc) where rownum=1";
			HashVO[] newsvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql1,acnum);
			// 取得分段日志最近一条的正常数据
			if (newsvos.length > 0) {
				HashVO vewsvo = newsvos[0];
				sumAirMin = vewsvo.getLognValue("flair_sum");// 总计
				sumBlockMin = vewsvo.getLognValue("fiblock_sum");// 总计
				sumomisFc = vewsvo.getLognValue("fiactlg_sum");// 总计
			}
			String sqllog = "select * from l_ac_flightlog t where COMPUTEDSTATUS='0' and acnum=? order by fiactdep";
			HashVO[] logvos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sqllog,acnum);
			
			ArrayList<String> updateSqlList = new ArrayList<String>();
			for (int i = 0; i < logvos.length; i++) {
				logger.debug("计算" + acnum + "的第" + i + "条飞行日志分段日志");
				HashVO logvo = logvos[i];
				String id = logvo.getStringValue("id");
				basicAirMinC = logvo.getLognValue("fiair");
				basicBlockMinC = logvo.getLognValue("fiblock");
				basicomisFcC = logvo.getLognValue("fiactlg");
				sumAirMin += basicAirMinC;// 总计
				sumBlockMin += basicBlockMinC;// 总计
				sumomisFc += basicomisFcC;// 总计
				
				String sql = "update l_ac_flightlog set flair_sum=" + sumAirMin + ",fiblock_sum=" + sumBlockMin 
							+ ",fiactlg_sum=" + sumomisFc + " where id=" +id;
				updateSqlList.add(sql);

				if ( i>0 && i%500 == 0) {
					dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
					dmo.commit(ApmsConst.DS_APMS);
					updateSqlList.clear();
				}
			}
			dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			dmo.commit(ApmsConst.DS_APMS);
			updateSqlList.clear();
			logger.info("计算" + acnum + "的飞行日志分段日志总计结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	public String computeFlightLogDaily(String acnum, String startdate, String baseorgId, int status) throws Exception {
		long basicAir = 0;// 初始
		long basicBlock = 0;// 初始
		long basicFc = 0;// 初始

		long sumAir = 0;// 总计
		long sumBlock = 0;// 总计
		long sumFc = 0;// 总计

		long dayAir = 0;// 当天总计
		long dayBlock = 0;// 当天总计
		long dayFc = 0;// 当天总计
		Date lastDate = DateUtil.StringToDate("2001-01-01", "yyyy-mm-dd");// 最后的日期
		boolean isExistsLog = false;//是否存在daily日志
		int day = 0;

		String lastLogSql = "select * from (select * from l_ac_flightlog_Daily t where COMPUTEDSTATUS='1' and t.acnum=?  order by vdfcdate desc) where rownum=1";
		HashVO[] lastVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, lastLogSql,acnum);
		// 取得飞行日志计算开始日期
		logger.info("取得飞行日志计算开始日期");
		//如果有数据，最最新的统计数据，如果没有取默认值0
		if (lastVos.length > 0) {
			isExistsLog = true;
			HashVO lastvo = lastVos[0];
			
			basicAir = lastvo.getLongValue("vdfcair");
			basicBlock = lastvo.getLongValue("vdfcblock");
			basicFc = lastvo.getLongValue("fiactlg_sum");
			lastDate = lastvo.getDateValue("vdfcdate");
			
		}
		sumAir = basicAir;// 总计
		sumBlock = basicBlock;// 总计
		sumFc = basicFc;// 总计
		
		String isql1 = "insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR" +
				",VDFCBLOCK,FIBLOCK,FIACTLG_SUM,FIACTLG ,COMPUTEDSTATUS,UPDATETIME)" +
				" values (S_l_ac_flightlog_Daily.nextval,?,?,? ,?,?,?,?,?,? ,1,sysdate)";
		
		String usql1 = "update l_ac_flightlog_Daily set VDFCAIR=? ,FIAIR=?,VDFCBLOCK=? ,FIBLOCK=?"
			+ ",FIACTLG_SUM=?,FIACTLG=?,COMPUTEDSTATUS=1,UPDATETIME=sysdate" +
			" where  VDFCDATE=? and acnum= ?";
		
		String logsql = "select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg" +
				",max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum" +
				" from l_ac_flightlog t where computedstatus=0 and acnum=? and fidate>=?" +
				" group by acnum,fidate,baseorgid order by acnum,fidate";
		
		String existsSql = "select id from l_ac_flightlog_Daily where VDFCDATE=? and acnum= ?";
		
		HashVO[] dailyvos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, logsql,acnum,lastDate);
		Date preDate = lastDate;//前一个日期&当前计算的日期
		Date curDate;
		for (int i = 0; i < dailyvos.length; i++) {// /计算日期是否有缺失的日期
			HashVO vocur = dailyvos[i];
			String orgid = vocur.getStringValue("baseorgid");
			
			if(i==0){
				if( isExistsLog){
					preDate = lastDate;
				}else{
					preDate = vocur.getDateValue("fidate");
				}
			}

			curDate = vocur.getDateValue("fidate");
			//从当前记录的日期，向前推是否有空缺的日期,如果有，需要补齐
			day = Math.abs(DateUtil.dateDiff("d", curDate, preDate ));
			if (day > 1) {// 有空缺的日期存在,
				for (int kk = 1; kk < day; kk++) {// 循环写入飞行空日期的飞行日志
					preDate = DateUtil.moveDay(preDate, 1);
					HashVO[] voflylogsum1 = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, existsSql,preDate,acnum);
					if (voflylogsum1.length > 0) {
						logger.info("日期中间有记录，只更新总计数据.");
						dmo.executeUpdateByDS(ApmsConst.DS_APMS, usql1, sumAir,0,sumBlock,0,sumFc,0, preDate,acnum);
					} else {
						logger.info("日期中间有空间2，没有记录！");
						//"insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR" +
						//",VDFCBLOCK,FIBLOCK,FIACTLG_SUM,FIACTLG,COMPUTEDSTATUS,UPDATETIME)" +
						//" values (S_l_ac_flightlog_Daily.nextval,?,?,? ,?,0,?,0,?,0 ,1,sysdate)";
						
						dmo.executeUpdateByDS(ApmsConst.DS_APMS, isql1, orgid, preDate,acnum,sumAir,0,sumBlock,0,sumFc,0 );
					}
				}//end for kk
				dmo.commit(ApmsConst.DS_APMS);
			}
			
			preDate = curDate;
			dayAir = vocur.getLognValue("fiair");
			dayBlock = vocur.getLognValue("fiblock");
			dayFc = vocur.getLognValue("fiactlg");
			sumAir += dayAir;// 总计
			sumBlock += dayBlock;// 总计
			sumFc += dayFc;// 总计
			

			HashVO[] vosums = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, existsSql, curDate,acnum);

			//判断记录是否存在，使用新增或更新
			if (vosums.length > 0) {
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, usql1, sumAir,dayAir,sumBlock,dayBlock,sumFc,dayFc, curDate,acnum);
				logger.info("实际有记录存在3，则修改！");
			} else {
				logger.info("实际没有记录3，则添加！");
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, isql1, orgid, curDate,acnum,sumAir,dayAir,sumBlock,dayBlock,sumFc,dayFc );
			}
			
			//更新log为已计算
			String uptlogSql = "update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate=? and acnum=? and COMPUTEDSTATUS=0";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptlogSql, curDate,acnum);
			
			dmo.commit(ApmsConst.DS_APMS);
			
		}//end for
		
		logger.info("计算" + acnum + "的飞行日志结束");
		
		return "0";
	}
	
	/** 判断时间是否为空 */
	private boolean isDateNotNull(Date dt){
		if( dt == null || "1900-01-01".equals(DateUtil.getDateStr(dt)) ){
			return false;
		}else{
			return true;
		}
	}


	/**
	 * 根据航班计算更新未来预计的飞行日志数据
	 * 
	 * @param voacid
	 * @param omisStartPoint
	 * @param baseorgId
	 * @return
	 */
	public String extractPredictFlightLog(String acnum, String omisStartPoint, String baseorgId) {
		String flogstr = "";
		String queryAcid = "";
		String omisID;
		String omisACNo;
		String omisFlightDate = null;
		String omisFlightNO;
		String omisDeploc;
		String omisArrLoc;
		String omisStd;
		String omisSta;
		String omisOut;
		String omisOff;
		String omisOn;
		String omisIn;
		String Flbid = "1";
		String update = "";
		String global_pk = "";
		int omisFc;
		int AirMin;
		int BlockMin;// '申明读取OMIS数据函数

		long basicAirMin = 0;// 初始
		long basicBlockMin = 0;// 初始

		long basicomisFc = 0;// 初始
		long basicAirMinC = 0;// 当天总计
		long basicBlockMinC = 0;// 当天总计
		long basicomisFcC = 0;// 当天总计
		long curaddtime = 0;// 当前更新增加的时间
		long curaddcycle = 0;// 当前更新增加的循环
		HashVO voflylog2 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try {
			// 取得飞行日志的基础值
			// queryAcid="select * from (select * from l_ac_flightlog_Daily t where STATUS='1' and t.acnum='"+acid+"'   order by vdfcdate desc) where rownum<2";
			queryAcid = "select * from (select *  from l_ac_flightlog t where COMPUTEDSTATUS=1 and acnum='" + acnum + "' order by fidate desc, fiactarv desc) where rownum<2";
			HashVO[] voacid_log = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			if (voacid_log.length > 0) {
				HashVO voacid_log1 = voacid_log[0];

				// 取得数据
				basicAirMin = voacid_log1.getLognValue("flair_sum");
				basicBlockMin = voacid_log1.getLognValue("fiblock_sum");
				basicomisFc = voacid_log1.getLognValue("FIACTLG_SUM");
				update = voacid_log1.getStringValue("fidate");
			} else {

				update = omisStartPoint;
				// 不存在，赋值0
				basicAirMin = 0;
				basicBlockMin = 0;
				basicomisFc = 0;
			}
			// 飞行日志基础数据end
			flogstr = "select F.global_pk AS SYS_ID,F.global_pk AS global_pk,f.AC_ID AS ACNUM,f.FLT_DATE AS FLIGHTDATE,concat(F.iata_c,F.flt_id) as Flightno,F.dep_apt AS DEPLOC,F.arr_apt AS ARRLOC,F.std AS F_STD,F.sta AS F_STA,F.OUT_TIME AS OUTGATE,F.OFF_TIME ,F.ON_TIME,"
					+ "f.IN_TIME ,case  when dep_apt=arr_apt then 50 else 1 end   fc from airchina.flight_information f where flt_date>to_date(substr('"
					+ update.trim()
					+ "',0,10),'yyyy-mm-dd') and ac_id='" + acnum + "' order by flt_date,f.std";
			HashVO[] voflylog = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_OMIS, flogstr);
			for (int j = 0; j < voflylog.length; j++) {
				// if(datestate.equals("2")){//如果是初始化数据，则不执行下面的
				// datestate="1";
				// continue;
				// }
				HashVO voflylog1 = voflylog[j];
				// 不是第一条数据的时候，取前一条数据
				if (j > 0) {
					voflylog2 = voflylog[j - 1];
				} else {// 第一条数据的时候取得相同的数据
					voflylog2 = voflylog[j];
				}

				omisID = voflylog1.getStringValue("sys_id");
				omisACNo = voflylog1.getStringValue("acnum");
				omisFlightDate = voflylog1.getStringValue("flightdate").trim();
				omisFlightNO = voflylog1.getStringValue("flightno");
				omisDeploc = voflylog1.getStringValue("deploc");
				omisArrLoc = voflylog1.getStringValue("arrloc");
				omisStd = voflylog1.getStringValue("f_std");
				omisSta = voflylog1.getStringValue("f_sta");
				// operate_time=voflylog1.getStringValue("operate_time");
				global_pk = voflylog1.getStringValue("global_pk");

				// int dif=Math.abs(DateUtil.dateDiff("d",
				// voflylog1.getDateValue("flightdate"),
				// voflylog2.getDateValue("flightdate")));
				// logger.info("omisFlightDate="+omisFlightDate);

				// logger.info("omisID="+omisID);
				if (voflylog1.getStringValue("off_time") == null || voflylog1.getStringValue("off_time").compareTo("1900-01-01") == 0) { // '没有离港时间时
					omisOff = omisStd;
				} else {
					omisOff = voflylog1.getStringValue("off_time");
				}

				if (voflylog1.getStringValue("on_time") == null || voflylog1.getStringValue("on_time").compareTo("1900-01-01") == 0) { // '没有到达时间时
					omisOn = omisSta;
				} else {
					omisOn = voflylog1.getStringValue("on_time");
				}

				if (voflylog1.getStringValue("outgate") == null || voflylog1.getStringValue("outgate").compareTo("1900-01-01") == 0 || voflylog1.getStringValue("in_time") == null
						|| voflylog1.getStringValue("in_time").compareTo("1900-01-01") == 0) {// '没有离港时间时
					omisOut = DateUtil.dateAdd("n", omisOff, -5);// '*******************时间处理
				} else {
					omisOut = voflylog1.getStringValue("outgate");
				}

				if (voflylog1.getStringValue("outgate") == null || voflylog1.getStringValue("outgate").compareTo("1900-01-01") == 0 || voflylog1.getStringValue("in_time") == null
						|| voflylog1.getStringValue("in_time").compareTo("1900-01-01") == 0) {// '没有离港时间时
					omisIn = DateUtil.dateAdd("n", omisOn, 5);// '*******************时间处理
				} else {
					omisIn = voflylog1.getStringValue("in_time");

				}
				logger.info("计算UTC时间开始");
				// logger.info("omisOut="+omisOut);
				// logger.info("omisOn="+omisOn);
				// logger.info("omisOff="+omisOff);
				// logger.info("omisIn="+omisIn);
				// 'convert utc time
				omisOut = DateUtil.dateAdd("h", omisOut, -8);
				omisOn = DateUtil.dateAdd("h", omisOn, -8);
				omisOff = DateUtil.dateAdd("h", omisOff, -8);
				omisIn = DateUtil.dateAdd("h", omisIn, -8);

				// '------------
				AirMin = Math.abs(DateUtil.dateDiff("minute", sdf.parse(omisOff), sdf.parse(omisOn)));
				BlockMin = Math.abs(DateUtil.dateDiff("minute", sdf.parse(omisOut), sdf.parse(omisIn)));
				logger.info("计算UTC时间结束");
				if (voflylog1.getStringValue("fc") != null) {
					omisFc = voflylog1.getIntegerValue("fc");
				} else {
					omisFc = 1;

				}
				// 同一架飞机同一天
				if (omisFlightDate.equals(voflylog2.getStringValue("flightdate").trim())) {
					// 当天累计
					basicAirMinC += AirMin;
					basicBlockMinC += BlockMin;
					basicomisFcC += omisFc;
				} else {// 同一架飞机不同天
						// 当天累计
					basicAirMinC = AirMin;
					basicBlockMinC = BlockMin;
					basicomisFcC = omisFc;
				}

				// 总数累计
				basicAirMin += AirMin;
				basicBlockMin += BlockMin;
				basicomisFc += omisFc;
				curaddtime += AirMin;
				curaddcycle += omisFc;

				// 修改存在的数据
				// 写入飞行日志开始
				flogstr = "select * from l_ac_flightlog where global_pk='" + global_pk + "'";
				HashVO[] voflylogapms = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, flogstr);
				if (voflylogapms.length > 0) {// 有数据
					// 取得老数据
					// HashVO voflylogapms1=voflylogapms[0];
					// 减去老数据
					// basicAirMin-=voflylogapms1.getIntegerValue("FIAIR");
					// basicBlockMin-=voflylogapms1.getIntegerValue("FIBLOCK");
					// basicomisFc-=voflylogapms1.getIntegerValue("FIACTLG");

					if (omisDeploc != "" && omisArrLoc != "") {
						flogstr = "update l_ac_flightlog set FIFLBSN='" + omisID + "',COMPUTEDSTATUS=2, FIDATE=to_date(substr('" + omisFlightDate + "',1,10),'yyyy-mm-dd'),FIFLTNO='" + omisFlightNO
								+ "',FIDEPLOC='" + omisDeploc + "',FIARVLOC='" + omisArrLoc + "',FIACTDEP=to_date('" + omisOut + "','yyyy-mm-dd hh24:mi:ss')," + "FIACTTAK=to_date('" + omisOff
								+ "','yyyy-mm-dd hh24:mi:ss'), FIACTLAD=to_date('" + omisOn + "','yyyy-mm-dd hh24:mi:ss'),FIACTARV=to_date('" + omisIn + "','yyyy-mm-dd hh24:mi:ss'),FIACTLG=" + omisFc
								+ ", FISTA=2,FIAIR=" + AirMin + ",FIBLOCK=" + BlockMin + ",FLAIR_SUM=" + basicAirMin + ",FIBLOCK_SUM=" + basicBlockMin + ",FIACTLG_SUM=" + basicomisFc
								+ ",updatetime=sysdate where global_pk='" + global_pk + "'";
					} else {
						flogstr = "delete from l_ac_flightlog WHERE global_pk='" + global_pk + "'";
					}
					updateSqlList.add(flogstr);
					// dmo.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);

					// dmo.commit(ApmsConst.DS_APMS);

				} else {
					// 没有
					if (!omisDeploc.equals("") && !omisArrLoc.equals("")) {
						flogstr = "INSERT into l_ac_flightlog(ID,MODIFYSTATUS,COMPUTEDSTATUS,FIFLBSN,FIFLBID,global_pk,acnum,BASEORGID,FIDATE,FIFLTNO,FIDEPLOC,FIARVLOC,FIACTDEP,FIACTTAK,FIACTLAD,FIACTARV,FIACTLG,FISTA,FIAIR,FIBLOCK,FLAIR_SUM,FIBLOCK_SUM,FIACTLG_SUM,UPDATETIME,USERID)"
								+ " VALUES(S_l_ac_flightlog.nextval,0,2,'"
								+ omisID
								+ "','"
								+ Flbid
								+ "','"
								+ global_pk
								+ "','"
								+ omisACNo
								+ "',"
								+ baseorgId
								+ ",to_date(substr('"
								+ omisFlightDate.trim()
								+ "',1,10),'yyyy-mm-dd'),'"
								+ omisFlightNO
								+ "','"
								+ omisDeploc
								+ "','"
								+ omisArrLoc
								+ "',to_date('"
								+ omisOut
								+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
								+ omisOff
								+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
								+ omisOn
								+ "','yyyy-mm-dd hh24:mi:ss'),to_date('"
								+ omisIn
								+ "','yyyy-mm-dd hh24:mi:ss'),'"
								+ omisFc
								+ "',2,"
								+ AirMin
								+ ","
								+ BlockMin + "," + basicAirMin + "," + basicBlockMin + "," + basicomisFc + ",sysdate,0) ";
					} else {
						flogstr = "delete from l_ac_flightlog WHERE global_pk='" + global_pk + "'";
					}
					updateSqlList.add(flogstr);
				}
			}
			// 条件数据更新
			dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			dmo.commit(ApmsConst.DS_APMS);
			if (omisFlightDate == null) {
				omisFlightDate = omisStartPoint;
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回最后的日期
		return omisFlightDate;

	}

	// 飞行日志预计部分计算；在导入预计飞行日志之后进行计算。
	public String computeFlightLogPredict(String acnum, String startdate, String baseorgId, int status) {
		String queryAcid = "";
		long basicAirMin = 0;// 初始
		long basicBlockMin = 0;// 初始

		long basicomisFc = 0;// 初始

		long sumAirMin = 0;// 总计
		long sumBlockMin = 0;// 总计

		long sumomisFc = 0;// 总计

		long basicAirMinC = 0;// 当天总计
		long basicBlockMinC = 0;// 当天总计
		long basicomisFcC = 0;// 当天总计
		Date LastDate = null;// 最后的日期
		String LastDateS = "";
		int d = 0;
		int lastid = 0;
		try {
			queryAcid = "select * from (select * from l_ac_flightlog_Daily t where COMPUTEDSTATUS='1' and t.acnum='" + acnum + "'  order by vdfcdate desc) where rownum<2";
			HashVO[] voacid_log = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			// 取得飞行日志计算开始日期
			logger.info("取得飞行日志计算开始日期");
			if (voacid_log.length > 0) {
				HashVO voacid_log1 = voacid_log[0];

				basicAirMin = voacid_log1.getLongValue("vdfcair");
				basicBlockMin = voacid_log1.getLongValue("vdfcblock");
				basicomisFc = voacid_log1.getLongValue("fiactlg_sum");
				LastDate = voacid_log1.getDateValue("vdfcdate");
				LastDateS = voacid_log1.getStringValue("vdfcdate");
				lastid = voacid_log1.getIntegerValue("id");
				sumAirMin = basicAirMin;// 总计
				sumBlockMin = basicBlockMin;// 总计
				sumomisFc = basicomisFc;// 总计
				queryAcid = "select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=2 and acnum='"
						+ acnum + "' group by acnum,fidate,baseorgid order by acnum,fidate";
				HashVO[] flylogvo = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
				for (int i = 0; i < flylogvo.length; i++) {// /计算日期是否有缺失的日期
					HashVO flylogvo1 = flylogvo[i];
					HashVO flylogvo2 = null;
					basicAirMinC = flylogvo1.getLognValue("fiair");
					basicBlockMinC = flylogvo1.getLognValue("fiblock");
					basicomisFcC = flylogvo1.getLognValue("fiactlg");
					sumAirMin += basicAirMinC;// 总计
					sumBlockMin += basicBlockMinC;// 总计
					sumomisFc += basicomisFcC;// 总计
					if (i == 0) {// 与前一天的数据比较
						d = Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), LastDate));
						if (d > 1) {
							for (int kk1 = 1; kk1 <= d; kk1++) {// 循环写入飞行空日期的飞行日志
								if (kk1 < d) {
									basicAirMinC = 0;
									basicBlockMinC = 0;
									basicomisFcC = 0;
								} else {
									basicAirMinC = flylogvo1.getLognValue("fiair");
									basicBlockMinC = flylogvo1.getLognValue("fiblock");
									basicomisFcC = flylogvo1.getLognValue("fiactlg");
								}
								queryAcid = "select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('" + LastDateS + "',1,10),'yyyy-mm-dd')+" + kk1 + " and acnum= '" + acnum + "'";
								HashVO[] voflylogsum1 = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);

								if (voflylogsum1.length > 0) {

									queryAcid = "update l_ac_flightlog_Daily set VDFCAIR=" + basicAirMin + ",FIAIR=" + basicAirMinC + ",VDFCBLOCK=" + basicBlockMin + ",FIBLOCK=" + basicBlockMinC
											+ ",FIACTLG=" + basicomisFcC + ",FIACTLG_SUM=" + basicomisFc + ",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('" + LastDateS
											+ "',1,10),'yyyy-mm-dd')+" + kk1 + " and acnum= '" + acnum + "'";
									logger.info("预计中间有空间1，又有记录！");
								} else {
									logger.info("预计中间有空间1，没有记录！");
									queryAcid = "insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME)select S_l_ac_flightlog_Daily.nextval,baseorgid,vdfcdate+"
											+ kk1
											+ ",acnum,"
											+ basicAirMin
											+ ","
											+ basicAirMinC
											+ ","
											+ basicBlockMin
											+ ","
											+ basicBlockMinC
											+ ","
											+ basicomisFcC
											+ ","
											+ basicomisFc
											+ ",2,sysdate from l_ac_flightlog_Daily where id=" + lastid;
								}
								dmo.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);

								dmo.commit(ApmsConst.DS_APMS);

							}
						}
					}// 第一条数据与前面的数据比较end

					if (i < flylogvo.length - 1) {// 取得后一条的数据
						flylogvo2 = flylogvo[i + 1];
					} else {
						flylogvo2 = flylogvo[i];
					}
					// 判断当前记录跟后一记录的日期差值
					d = Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
					if (d > 1) {// 有空缺的日期存在
						for (int kk = 0; kk < d; kk++) {// 循环写入飞行空日期的飞行日志
							if (kk > 0) {// 当日期后。
								basicAirMinC = 0;
								basicBlockMinC = 0;
								basicomisFcC = 0;
							} else {
								basicAirMinC = flylogvo1.getLognValue("fiair");
								basicBlockMinC = flylogvo1.getLognValue("fiblock");
								basicomisFcC = flylogvo1.getLognValue("fiactlg");
							}
							queryAcid = "select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('" + flylogvo1.getStringValue("fidate").trim() + "',1,10),'yyyy-mm-dd')+" + kk
									+ " and acnum= '" + acnum + "'";
							HashVO[] voflylogsum1 = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);

							if (voflylogsum1.length > 0) {

								queryAcid = "update l_ac_flightlog_Daily set VDFCAIR=" + sumAirMin + ",FIAIR=" + basicAirMinC + ",VDFCBLOCK=" + sumBlockMin + ",FIBLOCK=" + basicBlockMinC
										+ ",FIACTLG=" + basicomisFcC + ",FIACTLG_SUM=" + sumomisFc + ",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"
										+ flylogvo1.getStringValue("fidate").trim() + "',1,10),'yyyy-mm-dd')+" + kk + " and acnum= '" + acnum + "'";
								logger.info("预计中间有空间2，又有记录！");
							} else {
								logger.info("预计中间有空间2，没有记录！");
								queryAcid = "insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"
										+ flylogvo1.getIntegerValue("baseorgid")
										+ ",to_date(substr('"
										+ flylogvo1.getStringValue("fidate").trim()
										+ "',0,10),'yyyy-mm-dd')+"
										+ kk
										+ ",'"
										+ acnum
										+ "'," + sumAirMin + "," + (basicAirMinC) + "," + sumBlockMin + "," + (basicBlockMinC) + "," + basicomisFcC + "," + sumomisFc + ",2,sysdate)";
							}
							dmo.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);

							dmo.commit(ApmsConst.DS_APMS);

						}
					} else {

						queryAcid = "select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('" + flylogvo1.getStringValue("fidate").trim() + "',1,10),'yyyy-mm-dd') and acnum= '" + acnum
								+ "'";
						HashVO[] voflylogsum1 = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);

						if (voflylogsum1.length > 0) {

							// queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+basicAirMinC+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+acid+"'";
							queryAcid = "update l_ac_flightlog_Daily set VDFCAIR=" + sumAirMin + ",FIAIR=" + basicAirMinC + ",VDFCBLOCK=" + sumBlockMin + ",FIBLOCK=" + basicBlockMinC + ",FIACTLG="
									+ basicomisFcC + ",FIACTLG_SUM=" + sumomisFc + ",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('" + flylogvo1.getStringValue("fidate").trim()
									+ "',1,10),'yyyy-mm-dd') and acnum= '" + acnum + "'";
							logger.info("预计有记录存在3，则修改！");
						} else {
							logger.info("预计没有记录3，则添加！");
							// queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+acid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(basicAirMinC)+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(basicBlockMinC)+","+basicomisFcC+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",1,sysdate)";
							queryAcid = "insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"
									+ flylogvo1.getIntegerValue("baseorgid")
									+ ",to_date(substr('"
									+ flylogvo1.getStringValue("fidate").trim()
									+ "',0,10),'yyyy-mm-dd'),'"
									+ acnum
									+ "',"
									+ sumAirMin
									+ "," + basicAirMinC + "," + sumBlockMin + "," + basicBlockMinC + "," + basicomisFcC + "," + sumomisFc + ",2,sysdate)";
						}
						dmo.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);

						dmo.commit(ApmsConst.DS_APMS);

					}
					// queryAcid="update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate=to_date(substr('"+flylogvo1.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') and acnum='"+flylogvo1.getStringValue("acnum")+"'";
					// dmo.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
					// dmo.commit(ApmsConst.DS_APMS);
				}

			} else {// 数据不存在
				queryAcid = "select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=2 and acnum='"
						+ acnum + "' group by acnum,fidate,baseorgid order by acnum,fidate";
				HashVO[] flylogvo = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
				for (int i = 0; i < flylogvo.length; i++) {// /计算日期是否有缺失的日期
					HashVO flylogvo1 = flylogvo[i];
					HashVO flylogvo2 = null;
					if (i < flylogvo.length - 1) {// 取得后一条的数据
						flylogvo2 = flylogvo[i + 1];
					} else {
						flylogvo2 = flylogvo[i];
					}
					// 判断当前记录跟后一记录的日期差值
					d = Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
					if (d > 1) {// 有空缺的日期存在
						for (int kk = 0; kk < d; kk++) {// 循环写入飞行空日期的飞行日志
							if (kk > 0) {// 当日期后。
								// basicAirMin-=basicAirMinC;
								// basicBlockMin-=basicBlockMinC;
								// basicomisFc-=basicomisFcC;
								basicAirMinC = 0;
								basicBlockMinC = 0;
								basicomisFcC = 0;
							} else {
								basicAirMinC = flylogvo1.getLognValue("fiair");
								basicBlockMinC = flylogvo1.getLognValue("fiblock");
								basicomisFcC = flylogvo1.getLognValue("fiactlg");
							}
							queryAcid = "select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('" + flylogvo1.getStringValue("fidate").trim() + "',1,10),'yyyy-mm-dd')+" + kk
									+ " and acnum= '" + acnum + "'";
							HashVO[] voflylogsum1 = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);

							if (voflylogsum1.length > 0) {

								queryAcid = "update l_ac_flightlog_Daily set VDFCAIR=" + (flylogvo1.getLognValue("flair_sum")) + ",FIAIR=" + basicAirMinC + ",VDFCBLOCK="
										+ (flylogvo1.getLognValue("fiblock_sum")) + ",FIBLOCK=" + basicBlockMinC + ",FIACTLG=" + basicomisFcC + ",FIACTLG_SUM="
										+ (flylogvo1.getLognValue("FIACTLG_sum")) + ",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('" + flylogvo1.getStringValue("fidate").trim()
										+ "',1,10),'yyyy-mm-dd')+" + kk + " and acnum= '" + acnum + "'";
								logger.info("预计中间有空间4，又有记录！");
							} else {
								logger.info("预计中间有空间4，没有记录！");
								queryAcid = "insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"
										+ flylogvo1.getIntegerValue("baseorgid")
										+ ",to_date(substr('"
										+ flylogvo1.getStringValue("fidate").trim()
										+ "',0,10),'yyyy-mm-dd')+"
										+ kk
										+ ",'"
										+ acnum
										+ "',"
										+ (flylogvo1.getLognValue("flair_sum"))
										+ ","
										+ (basicAirMinC)
										+ ","
										+ (flylogvo1.getLognValue("fiblock_sum"))
										+ ","
										+ (basicBlockMinC)
										+ ","
										+ basicomisFcC + "," + (flylogvo1.getLognValue("FIACTLG_sum")) + ",2,sysdate)";
							}
							dmo.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);

							dmo.commit(ApmsConst.DS_APMS);

						}
					} else {
						queryAcid = "select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('" + flylogvo1.getStringValue("fidate").trim() + "',1,10),'yyyy-mm-dd') and acnum= '" + acnum
								+ "'";
						HashVO[] voflylogsum1 = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);

						if (voflylogsum1.length > 0) {

							queryAcid = "update l_ac_flightlog_Daily set VDFCAIR=" + (flylogvo1.getLognValue("flair_sum")) + ",FIAIR=" + flylogvo1.getLognValue("fiair") + ",VDFCBLOCK="
									+ (flylogvo1.getLognValue("fiblock_sum")) + ",FIBLOCK=" + flylogvo1.getLognValue("fiblock") + ",FIACTLG=" + flylogvo1.getLognValue("fiactlg") + ",FIACTLG_SUM="
									+ (flylogvo1.getLognValue("FIACTLG_sum")) + ",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('" + flylogvo1.getStringValue("fidate").trim()
									+ "',1,10),'yyyy-mm-dd') and acnum= '" + acnum + "'";
							logger.info("预计有记录存在5，则修改！");
						} else {
							logger.info("预计没有记录5，则添加！");
							queryAcid = "insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"
									+ flylogvo1.getIntegerValue("baseorgid")
									+ ",to_date(substr('"
									+ flylogvo1.getStringValue("fidate").trim()
									+ "',0,10),'yyyy-mm-dd'),'"
									+ acnum
									+ "',"
									+ (flylogvo1.getLognValue("flair_sum"))
									+ ","
									+ (flylogvo1.getLognValue("fiair"))
									+ ","
									+ (flylogvo1.getLognValue("fiblock_sum"))
									+ ","
									+ (flylogvo1.getLognValue("fiblock")) + "," + flylogvo1.getLognValue("FIACTLG") + "," + (flylogvo1.getLognValue("FIACTLG_sum")) + ",2,sysdate)";
						}
						dmo.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);

						dmo.commit(ApmsConst.DS_APMS);

					}
					// queryAcid="update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate=to_date(substr('"+flylogvo1.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') and acnum='"+flylogvo1.getStringValue("acnum")+"'";
					// dmo.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
					// dmo.commit(ApmsConst.DS_APMS);
				}

			}
			logger.info("计算" + acnum + "的预计飞行日志结束");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "0";
	}

}