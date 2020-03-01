package com.apms.bs.flight;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.flight.notify.FlightChangeNotifyThread;
import com.apms.bs.flight.vo.FlightColChangeVo;
import com.apms.bs.flight.vo.FlightSynTempVo;
import com.apms.bs.sms.ShortMassageHwService;
import com.apms.bs.util.DateUtil;
import com.apms.cache.ApmsServerCache;
import com.apms.cache.FlightCache;
import com.apms.cache.vo.AirCraftVo;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.bs.NovaDBConnection;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaServerEnvironment;

/**
 * 从接口临时表OMIS_FLIGHT_SCHEDULE同步航班计划数据
 * @author jerry
 * @date Mar 15 ,2014
 */
public class FlightScheduleSynService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	FlightManageService flightMng = new FlightManageService();
	
	private String insertFlightSql;
	
	/** 航班变动记录 insert */
	private StringBuilder inChgLogSql;
	
	public FlightScheduleSynService(){
		StringBuilder inSql = new StringBuilder("INSERT INTO F_FLIGHT_SCHEDULE(ID ");
		inSql.append(" ,FLT_PK ,FLT_DATE ,IATA_C ,CO_SEQ ,FLIGHTNO ,FLT_ID ,FLT_SEQ ,FLT_TASK ,ACNUM ,ACMODEL");
		inSql.append(" ,DEP_APT ,ARR_APT ,AC_STOP ,AC_STOP_ARR ,ALT_APT1 ,ALT_APT2");
		inSql.append(" ,STD ,STA ,ETD ,ETA ,CTD ,CTA");
		inSql.append(" ,OFF_TIME ,ON_TIME ,OUT_TIME ,IN_TIME ,MEMO");
		inSql.append(" ,FLTTYPE_DEP ,FLTTYPE_ARR,ISCONFIRMED ,HASHVALUE");
		inSql.append(" ,AC_STATE ,PRE_FLIGHTID ,NEXT_FLIGHTID ,NEXT_FLIGHTNO");
		inSql.append(" ,ISDELAY ,DELAY_CODE ,DELAY_TIME");
		inSql.append(" ,CANCEL_FLAG ,CANCEL_TYPE ,CANCEL_TIME ,CANCEL_REASON ,CANCEL_SRC");
		inSql.append(" ,AC_OWNER ,AC_TYPE_OLD ,AC_TYPE_MARKET");
		inSql.append(" ,VIP ,VIP_NAME ,PLAN_FLAG,BASEORGID");
		inSql.append(" ,COMMENTS,UPDATETIME ,UPDATEUSER,ISLOCKEDIN,DATASOURCE )VALUES(S_F_FLIGHT_SCHEDULE.NEXTVAL ");
		inSql.append(" ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");//49个字段
		inSql.append(" ,'接口新导入' ,sysdate ,'system', 0,0 )");
		inSql.append(" ");
		
		insertFlightSql = inSql.toString();
		
		inChgLogSql = new StringBuilder();
		inChgLogSql.append("INSERT INTO F_FLIGHT_CHANGELOG(ID");
		inChgLogSql.append(" ,FLIGHTID ,ACNUM ,FLIGHTNO ,FLIGHTDATE ,CHANGETYPE ");
		inChgLogSql.append(" ,CHANGECONTENT ,NEEDBOARDCAST ,ISBOARDCAST ,CHANGETIME");		 
		inChgLogSql.append(" ,UPDATETIME ,UPDATEUSER ) ");
		inChgLogSql.append(" VALUES(S_F_FLIGHT_CHANGELOG.NEXTVAL,?,?,?,?,?,?,?,0,SYSDATE,SYSDATE,'SYSTEM')");
		inChgLogSql.append("");
		
	}
	
	private int totalNum,dealNum, addNum, uptNum,cancelNum;
	
	public String synFlightSchedule(String dayNum) throws Exception{
		
		long startTime = System.currentTimeMillis();
		long endTime,cost;
		
		totalNum = 0;
		dealNum = 0;
		addNum = 0;
		uptNum = 0;
		cancelNum = 0;
		try{
			String exeLogStr =  synFlightScheduleNoTry(dayNum);
			
			endTime = System.currentTimeMillis();
			cost = (endTime - startTime)/1000;
			
			//启动变更日志分析提醒线程
			FlightChangeNotifyThread chgthread = new FlightChangeNotifyThread("HGH");
			chgthread.start();
			
			//记录执行日志
			insertUpdateLog(cost, totalNum, dealNum, addNum, uptNum, cancelNum, 1, null);
			
			return exeLogStr;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("执行航班计划数据同步失败，原因:" + e.toString());
			
			endTime = System.currentTimeMillis();
			cost = (endTime - startTime)/1000;
			//记录执行日志
			insertUpdateLog(cost, totalNum, dealNum, addNum, uptNum, cancelNum, 0, e.toString());
			
			String pname = (String)NovaServerEnvironment.getInstance().get("PROJECT_NAME");
			//数据任务失败，及时发短信告知维护人员
			ShortMassageHwService.getInstance().send( "18916752189", pname+"执行航班计划数据同步失败,请及时查看任务日志");//zzy
			ShortMassageHwService.getInstance().send( "18918557155", pname+"执行航班计划数据同步失败,请及时查看任务日志");//刘海伟
			ShortMassageHwService.getInstance().send( "18658176006", pname+"执行航班计划数据同步失败,请及时查看任务日志");
			
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 同步航班计划
	 * @param dayNum 同步当前至N天的计划数据
	 * @throws Exception
	 */
	private String synFlightScheduleNoTry(String dayNum) throws Exception{
		
		//后续高效率同步策略(暂不实现)：1，提取主键值和Hash值 2,将主键和HASH值写入临时表 3，查询出变动的航班计划记录 4，更新处理
		
		//飞机保障对应关系 0-国航飞机,1,航空公司+航班号 2，航空公司 3，航空公司+机型 
		StringBuilder columnStr = new StringBuilder(""); //共有115个字段
		columnStr.append(" FLT_PK ,FLT_DATE ,IATA_C ,CO_SEQ ,FLT_ID ,FLT_SEQ ,FLT_TASK ,CON_FLT_ID ,AC_ID ,AC_TYPE");
		columnStr.append(" ,P_CABIN_CLASS ,C_CABIN_CLASS ,SERVICE_TYPE ,FLT_TYPE ,ROUTE_TYPE ,MEALS ,DEP_APT");
		columnStr.append(" ,STD ,STA ,ARR_APT ,ETD ,ETD_SRC ,ETA ,ETA_SRC ,OFF_TIME ,OFF_SRC ,ON_TIME ,ON_SRC ,OUT_TIME ,OUT_SRC");
		columnStr.append(" ,IN_TIME ,IN_SRC ,AC_STOP ,VIP ,VIP_NAME ,NO_OIL_LOAD ,ALT_APT1 ,ALT_APT2 ,DELAY_CODE ,DELAY_TIME");
		columnStr.append(" ,SITE_CHECKED ,SEGMENT_TIME ,CFP_FLAG ,CPL_FLAG ,FPL_FLAG ,REL_FLAG ,FLY_STANDARD ,FLY_MAP_STOPFLAG");
		columnStr.append(" ,CANCEL_FLAG ,RELEASE_FLAG ,RELEASE_TIME ,SITA_AD_SEND ,SITA_AA_SEND ,CHANGE_MODE ,MEMO ,TAKEOFF_FUEL");
		columnStr.append(" ,TRIP_FUEL ,ETA_UPD_SOURCE ,REGION ,PLAN_FLAG ,CREATE_SRC ,UPDATE_SRC ,STA_SRC ,FLT_TASK_SRC");
		columnStr.append(" ,AC_SRC ,AC_STOP_SRC ,ALT_APT_SRC ,DELAY_SRC ,CANCEL_SRC ,CREW_OWNER ,CABIN_OWNER ,AC_OWNER");
		columnStr.append(" ,OWNER_SRC ,CODESHARE_FLAG1 ,CODESHARE1_IATA_C ,CODESHARE1_FLT_ID ,CODESHARE_FLAG2 ,CODESHARE2_IATA_C");
		columnStr.append(" ,CODESHARE2_FLT_ID ,CODESHARE_FLAG3 ,CODESHARE3_IATA_C ,CODESHARE3_FLT_ID ,CODESHARE_SRC ,IN_RANGE_TIME");
		columnStr.append(" ,IN_RANGE_SRC ,MEMO_SRC ,OIL_SRC ,SORTIE ,NO_OIL_ACCEPT ,CFP_NO_OIL ,GLOBAL_PK ,CANCEL_REASON ,AC_TYPE_MARKET");
		columnStr.append(" ,EXTEND_SERVICE ,AC_TYPE_OLD ,AC_STOP_ARR ,RELEASE_REGION ,CREW_STANDARD ,OFF_DELAY_STANDARD ,ON_DELAY_STANDARD");
		columnStr.append(" ,FIRST_FLAG ,CANCEL_TYPE ,CANCEL_TIME ,VEHICLE_FLAG ,DELAY_FLAG_RLS ,DELAY_CODE_RLS ,MEMO_RLS ,SOCRLS_FLAG");
		columnStr.append(" ,NO_OIL_LOAD_PRE ,CIP ,WATCH_FLAG ,RELEASE_SEAT ,RLS_REMARK ,TAXY_FUEL ,RLSSEAT_FLAG ,BASEORGID,CTD ");
		
		//查询变化字段的Hash值总和
		StringBuilder hashColumn = new StringBuilder();
		String dtFormat = "'mm-dd hh24:mi'";
		hashColumn.append(" ,dbms_utility.get_hash_value(");
		hashColumn.append(" AC_ID||AC_TYPE"); //执飞飞机
		hashColumn.append(" ||to_char(ON_TIME,"+dtFormat+")||to_char(OFF_TIME,"+dtFormat+")||to_char(OUT_TIME,"+dtFormat+")||to_char(IN_TIME,"+dtFormat+")");
		hashColumn.append(" ||CANCEL_FLAG||CANCEL_TYPE||CANCEL_REASON||CANCEL_TIME"); //取消标识
		hashColumn.append(" ||DEP_APT||ARR_APT||ALT_APT1||ALT_APT2"); //机场、备降机场
		hashColumn.append(" ||AC_STOP||AC_STOP_ARR"); //机位 ,AC_STOP_MOVE 
		hashColumn.append(" ||DELAY_CODE||DELAY_TIME");//延误		
		hashColumn.append(" ||to_char(STD,"+dtFormat+")||to_char(STA,"+dtFormat+")");
	    hashColumn.append(" ||to_char(ETD,"+dtFormat+")||to_char(ETA,"+dtFormat+")");
	    
		hashColumn.append(" ||MEMO"); //备注
		hashColumn.append(" "); //VIP?
		hashColumn.append(" ,1 ,1000000000 ) content_hashvalue");
		
		StringBuilder qrySql = new StringBuilder();
		qrySql.append(" SELECT ").append(columnStr).append(" ");
		qrySql.append(hashColumn);
		qrySql.append(" FROM OMIS_FLIGHT_SCHEDULE F");
		qrySql.append(" WHERE 1=1 AND F.FLT_DATE>=TRUNC(SYSDATE-1) AND F.FLT_DATE<=TRUNC(SYSDATE+"+dayNum+") "); //先只取当天和后一天
		qrySql.append(" AND IATA_C = ANY('CA','ZH' , 'KY' , 'TV') "); //国航(CA),深航(ZH)、大连航(CA)、西藏航(TV),昆明航(KY)
		//qrySql.append(" AND AC_ID IS NOT NULL"); //飞机号不为空,取消的航班，飞机号可能为空
		
		//按经停机场的飞机，进一步查询出这些飞机的所有航班,经过浦东、虹桥、杭州、北京的航班飞机，占97%，此条件可以不要
		//qrySql.append(" AND F.AC_ID IN ( SELECT AC_ID FROM OMIS_FLIGHT_SCHEDULE F2 WHERE F2.IATA_C='CA' ");
		//qrySql.append(" AND (F2.DEP_APT IN ('SHA','PVG','HGH','PEK') OR F2.ARR_APT IN ('SHA','PVG','HGH','PEK') ) )");
		
            
		//必须查出该飞机的所有航班才能判断航前、航后
		//qrySql.append(" AND ( DEP_APT =ANY('PVG','SHA') OR DEP_APT=ANY('PVG','SHA') )");//浦东或虹桥机场
		qrySql.append(""); //CO_SEQ ,分公司代码条件
		qrySql.append("");
		qrySql.append(" ORDER BY AC_ID,CTD"); //按照飞机号、计划起飞时间排序
		
		HashVO[] vosFlt = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS ,qrySql.toString());
		/** 接口查出的fltpk和vo的映射 */
		HashMap<String, HashVO> fltvoMap = new HashMap<String, HashVO>(vosFlt.length);
		
		ArrayList<FlightSynTempVo> synvoList = new ArrayList<FlightSynTempVo>();
		//逐条判断
		for(int i=0;i<vosFlt.length;i++){
			totalNum++;
			HashVO fltvo = vosFlt[i];
			boolean isMaintain = judgeIsMaintain(fltvo);
			if(isMaintain == false){
				continue; //不维护航班，跳过
			}
			
			fltvoMap.put(fltvo.getStringValue("FLT_PK"), fltvo);
			//判断航班维护种类，生成临时表数据
			FlightSynTempVo synvo = judgeFlightType(vosFlt, fltvo, i);
			synvoList.add(synvo);
			dealNum++;
		}
		logger.debug("航班类型数据判断完毕！");
		//更新临时表数据
		synTempTableData(synvoList);
		logger.debug("临时表数据同步完毕");
		
		//处理新增的航班记录
		HashVO[] vosNew = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, getAddFlightsSql());
		for(int i=0;i<vosNew.length;i++){
			HashVO synAddVo = vosNew[i];
			//插入新增的航班数据
			insertNewData(fltvoMap.get(synAddVo.getStringValue("FLT_PK")), synAddVo);
			addNum++;
		}
		logger.debug("新增航班数据处理完毕，共["+vosNew.length+"]条。");
		
		//处理变更的航班记录
		HashVO[] vosChg = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, getChangedFlightsSql());
		for(int i=0;i<vosChg.length;i++){
			HashVO synChgVo = vosChg[i];
			
			//判断航班变更信息项
			judgeAndDealChangeItem(fltvoMap.get(synChgVo.getStringValue("FLT_PK")), synChgVo);
		}
		
		logger.debug("变动航班数据处理完毕，共["+vosChg.length+"]条。");
		
		return "新增["+vosNew.length+"]条，更新["+vosChg.length+"]条";
		
	}
	
	/**
	 * 根据hashvalue的变化，查询出变化的航班信息
	 * @return 查询SQL
	 * @throws Exception
	 */
	private String getChangedFlightsSql() throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.FLT_PK NEW_FLT_PK,T.HASHVALUE NEW_HASHVALUE ,T.FLTTYPE_DEP NEW_FLTTYPE_DEP "); //新的HASHVALUE
		sb.append(" ,T.FLTTYPE_ARR NEW_FLTTYPE_ARR ,T.PRE_FLTPK NEW_PRE_FLTPK ,T.NEXT_FLTPK NEW_NEXT_FLTPK ,T.NEXT_FLTNO NEW_NEXT_FLTNO "); //新的航班类型及判断值
		sb.append(" ,FS.FLT_PK ,FS.ACNUM ,FS.ACMODEL ,FS.DEP_APT ,FS.ARR_APT ,FS.AC_STOP ,FS.AC_STOP_ARR ");//飞机/机场/机位
		sb.append(" ,FS.STD ,FS.STA ,FS.ETD ,FS.ETA "); //计划和预计时间
		sb.append(" ,FS.OFF_TIME ,FS.ON_TIME ,FS.OUT_TIME ,FS.IN_TIME ,FS.MEMO"); //航班时间及备注
		sb.append(" ,FS.FLTTYPE_DEP ,FS.FLTTYPE_ARR ,FS.ISCONFIRMED ,FS.CONFIRMEDTIME ");//维修种类及确认信息
		sb.append(" ,FS.PRE_FLIGHTID ,FS.NEXT_FLIGHTID ,FS.NEXT_FLIGHTNO ");//前后航班信息
		sb.append(" ,FS.UNUSAULFLT_RPTID ,FS.ISDELAY ,FS.DELAY_CODE ,FS.DELAY_TIME ");//延误信息
		sb.append(" ,FS.CANCEL_FLAG ,FS.CANCEL_TYPE ,FS.CANCEL_TIME ,FS.CANCEL_REASON ,FS.CANCEL_SRC ");//取消信息
		sb.append(" ,FS.ALT_APT1 ,FS.ALT_APT2 ");//备降机场
		sb.append(" ,FS.FLT_DATE ,FS.IATA_C ,FS.CO_SEQ ,FS.FLIGHTNO ");
		//此段数据列信息可以不需要 begin
		sb.append(" ,FS.VIP ,FS.VIP_NAME ,FS.PLAN_FLAG ");//VIP
		sb.append(" ,FS.FLT_ID ,FS.FLT_SEQ ,FS.FLT_TASK ");//航班日期等
		sb.append(" ,FS.AC_STATE ,FS.WORKFORCE_STATE ,FS.HASHVALUE ");//飞机及工作状态,hashvalue
		sb.append(" ,FS.AC_OWNER ,FS.AC_TYPE_OLD ,FS.AC_TYPE_MARKET ");//飞机所属
		sb.append(" ,FS.COMMENTS ,FS.UPDATETIME ,FS.UPDATEUSER");
		sb.append(" ,FS.ISLOCKEDIN "); //是否锁定
		sb.append("");		
		//此段数据列信息可以不需要 begin
		
		sb.append(" FROM F_SYN_TEMPTABLE T, F_FLIGHT_SCHEDULE FS");
		sb.append(" WHERE NOT EXISTS (SELECT 1 FROM F_FLIGHT_SCHEDULE S WHERE S.FLT_PK=T.FLT_PK " );
		//sb.append("       		AND (S.HASHVALUE > T.HASHVALUE OR S.HASHVALUE < T.HASHVALUE) )"); //不使用!=符号,为了索引
		//HASH值不等 或者维护种类不相等
		sb.append("			AND ( S.HASHVALUE = T.HASHVALUE AND S.FLTTYPE_DEP = T.FLTTYPE_DEP AND S.FLTTYPE_ARR = T.FLTTYPE_ARR ) ");
		sb.append("");
		sb.append("      )");//not exists 条件结束
		sb.append(" AND FS.FLT_PK=T.FLT_PK ");
		
		return sb.toString();
	}
	
	/**
	 * 判断航班计划数据变动项
	 * @throws Exception
	 */
	private void judgeAndDealChangeItem(HashVO newvo,HashVO oldvo) throws Exception{
		//变动类型：0-航班状态 1 取消、2-飞机更改、3-机场更改、4-维护种类(TR/AF+PF)、5-机位 6-计划时间 7预计时间 8-备注
		logger.debug("处理更新的航班计划数据flt_pk=["+newvo.getStringValue("flt_pk")
				+"],flt_date=["+newvo.getStringValue("flt_date")+"],flt_id=["+newvo.getStringValue("flt_id")+"].");
		
		String fltPk = newvo.getStringValue("flt_pk");
		int chgType = 100; //变更类型
		String chgHead = "";//变更说明
		boolean needNotify = true;
		boolean isFlightEnd = false; //航班是否执行结束
		int acState = 0;
		boolean isHashValueChg = false; //是否是航班内容发生了变化
		StringBuilder chgContent = new StringBuilder();
		
		//判断顺序  取消、航班状态、飞机、机场、计划时间、预计时间、机位、备注(前面的信息变化都可能引起备注变化),重要性从高到低
		
		StringBuilder sql = new StringBuilder("UPDATE F_FLIGHT_SCHEDULE SET UPDATETIME=sysdate,UPDATEUSER='system'");
		StringBuilder colSetSql = new StringBuilder(); //update中set子句
		ArrayList<Object> paramValList = new ArrayList<Object>(40); //占位符的对象
		colSetSql.append(",hashvalue=?");
		paramValList.add(oldvo.getStringValue("new_hashvalue"));
		
		String newCancelFlag = newvo.getStringValue("cancel_flag");
		//首先判断是hashvalue的变化还是flttype的变化
		
		if( !oldvo.getStringValue("new_hashvalue").equals( oldvo.getStringValue("hashvalue")) ){
			isHashValueChg = true;
			
			FlightColChangeVo cancel_flag = new FlightColChangeVo(newvo, oldvo, "取消标识", "cancel_flag");
			colSetSql.append(cancel_flag.getColSetStr());
			cancel_flag.setParamValList(paramValList);
			
			
			//zhangzy 20140928 cancel_flag的变化不全为取消,字典值已使用的有0-正常,1-取消, 3-备降1，4-备降2， 5-返航1， 6-返航2,7-加降1,8-加降2
			if(cancel_flag.isChanged() && "1".equals(newCancelFlag) ){
				//cancel_flag ,cancel_type ,cancel_time ,cancel_reason ,cancel_src
				FlightColChangeVo cancel_type = new FlightColChangeVo(newvo, oldvo, "取消类型", "cancel_type");
				FlightColChangeVo cancel_time = new FlightColChangeVo(newvo, oldvo, "取消时间", "cancel_time" ,1);
				FlightColChangeVo cancel_reason = new FlightColChangeVo(newvo, oldvo, "取消原因", "cancel_reason");
				FlightColChangeVo cancel_src = new FlightColChangeVo(newvo, oldvo, "取消录入源", "cancel_src");
				
				colSetSql.append(cancel_type.getColSetStr());
				cancel_type.setParamValList(paramValList);
				
				colSetSql.append(cancel_src.getColSetStr());
				cancel_src.setParamValList(paramValList);
				
				colSetSql.append(cancel_reason.getColSetStr());
				cancel_reason.setParamValList(paramValList);
				
				colSetSql.append(cancel_time.getColSetStr());
				cancel_time.setParamValList(paramValList);
				
				cancelNum++;
				chgType = 200;
				chgHead = "航班取消";
				chgContent.append(",航班计划【取消】").append(cancel_reason.getChgDesc()).append(cancel_time.getChgDesc());
			}else{
				//6 计划时间变化判断
				FlightColChangeVo std = new FlightColChangeVo(newvo, oldvo, "计划离港", "std" ,1);
				FlightColChangeVo sta = new FlightColChangeVo(newvo, oldvo, "计划到港", "sta" ,1);
				
				colSetSql.append(std.getColSetStr());
				std.setParamValList(paramValList);
				chgContent.append(std.getChgDesc());
				
				colSetSql.append(sta.getColSetStr());
				sta.setParamValList(paramValList);
				chgContent.append(sta.getChgDesc());
				
				//7预计时间变化
				FlightColChangeVo etd = new FlightColChangeVo(newvo, oldvo, "预计离港", "etd" ,1);
				FlightColChangeVo eta = new FlightColChangeVo(newvo, oldvo, "预计到港", "eta" ,1);
				
				colSetSql.append(etd.getColSetStr());
				etd.setParamValList(paramValList);
				chgContent.append(etd.getChgDesc());
				
				colSetSql.append(eta.getColSetStr());
				eta.setParamValList(paramValList);
				chgContent.append(eta.getChgDesc());
				
				//2飞机变化
				FlightColChangeVo acnum = new FlightColChangeVo(newvo, oldvo, "飞机号", "ac_id","acnum");
				colSetSql.append(acnum.getColSetStr());
				acnum.setParamValList(paramValList);
				chgContent.append(acnum.getChgDesc());
				
				//3 机场 dep_apt ,arr_apt 
				FlightColChangeVo dep_apt = new FlightColChangeVo(newvo, oldvo, "起飞机场", "dep_apt");
				colSetSql.append(dep_apt.getColSetStr());
				dep_apt.setParamValList(paramValList);
				chgContent.append(dep_apt.getChgDesc());
				
				FlightColChangeVo arr_apt = new FlightColChangeVo(newvo, oldvo, "到达机场", "arr_apt");
				colSetSql.append(arr_apt.getColSetStr());
				arr_apt.setParamValList(paramValList);
				chgContent.append(arr_apt.getChgDesc());
				
				//5 机位变化 
				FlightColChangeVo ac_stop = new FlightColChangeVo();
				//modify by zhangzy20160115 杭州没有机位信息，不判断机位信息变更
				if( !"HGH".equals(newvo.getStringValue("dep_apt")) ){
					ac_stop = new FlightColChangeVo(newvo, oldvo, "离港机位", "ac_stop");
					colSetSql.append(ac_stop.getColSetStr());
					ac_stop.setParamValList(paramValList);
					chgContent.append(ac_stop.getChgDesc());
					
					
				}
				
				FlightColChangeVo ac_stop_arr = new FlightColChangeVo();
				if( !"HGH".equals(newvo.getStringValue("arr_apt")) ){
					ac_stop_arr = new FlightColChangeVo(newvo, oldvo, "到港机位", "ac_stop_arr");
					colSetSql.append(ac_stop_arr.getColSetStr());
					ac_stop_arr.setParamValList(paramValList);
					chgContent.append(ac_stop_arr.getChgDesc());
				}
				
				
				//0 航班状态变化,off_time ,on_time ,out_time ,in_time
				FlightColChangeVo off_time = new FlightColChangeVo(newvo, oldvo, "起飞时间", "off_time", 1);
				colSetSql.append(off_time.getColSetStr());
				off_time.setParamValList(paramValList);
				chgContent.append(off_time.getChgDesc());
				
				FlightColChangeVo on_time = new FlightColChangeVo(newvo, oldvo, "落地时间", "on_time", 1);
				colSetSql.append(on_time.getColSetStr());
				on_time.setParamValList(paramValList);
				chgContent.append(on_time.getChgDesc());
				
				FlightColChangeVo out_time = new FlightColChangeVo(newvo, oldvo, "推出时间", "out_time", 1);
				colSetSql.append(out_time.getColSetStr());
				out_time.setParamValList(paramValList);
				chgContent.append(out_time.getChgDesc());
				
				FlightColChangeVo in_time = new FlightColChangeVo(newvo, oldvo, "靠桥时间", "in_time", 1);
				colSetSql.append(in_time.getColSetStr());
				in_time.setParamValList(paramValList);
				chgContent.append(in_time.getChgDesc());
				
				//12 备降机场 ,alt_apt1 ,alt_apt2
				FlightColChangeVo alt_apt1 = new FlightColChangeVo(newvo, oldvo, "备降机场1", "alt_apt1");
				colSetSql.append(alt_apt1.getColSetStr());
				alt_apt1.setParamValList(paramValList);
				chgContent.append(alt_apt1.getChgDesc());
				
				FlightColChangeVo alt_apt2 = new FlightColChangeVo(newvo, oldvo, "备降机场2", "alt_apt2");
				colSetSql.append(alt_apt2.getColSetStr());
				alt_apt2.setParamValList(paramValList);
				chgContent.append(alt_apt2.getChgDesc());
				
				//11 航班延误信息变化 delay_code ,delay_time
				FlightColChangeVo delay_code = new FlightColChangeVo(newvo, oldvo, "延误代码", "delay_code");
				colSetSql.append(delay_code.getColSetStr());
				delay_code.setParamValList(paramValList);
				chgContent.append(delay_code.getChgDesc());
				
				FlightColChangeVo delay_time = new FlightColChangeVo(newvo, oldvo, "延误时间", "delay_time");
				colSetSql.append(delay_time.getColSetStr());
				delay_time.setParamValList(paramValList);
				chgContent.append(delay_time.getChgDesc());
				
				if(in_time.isChanged() && in_time.getNewValue() != null){
					isFlightEnd = true; //已经靠桥，判定航班执行结束
				}
				
				if( "3".equals(newCancelFlag) || "4".equals(newCancelFlag) ){
					//航班种类的变化，变为备降
					chgType = 210;
					chgHead = "备降航班";
					chgContent.append( "航班类型改为备降,新值["+newCancelFlag+"]" );
				}else if( "5".equals(newCancelFlag) || "6".equals(newCancelFlag) ){//返航
					chgType = 220;
					chgHead = "返航航班";
					chgContent.append( "航班类型改为返航,新值["+newCancelFlag+"]" );
				}else if( "7".equals(newCancelFlag) || "8".equals(newCancelFlag) ){//加降
					chgType = 220;
					chgHead = "加降航班";
					chgContent.append( "航班类型改为加降,新值["+newCancelFlag+"]" );
				}else if(acnum.isChanged()){
					chgType = 10;
					chgHead = "执飞飞机";
					
					FlightColChangeVo acmodel = new FlightColChangeVo(newvo, oldvo, "机型", "ac_type","acmodel");
					colSetSql.append(acmodel.getColSetStr());
					acmodel.setParamValList(paramValList);
					chgContent.append(acmodel.getChgDesc());
					
				}else if(arr_apt.isChanged() ){
					chgType = 30;
					chgHead = "落地机场";
					//zhangzy 20140928 落地、起飞机场的变更更为重要，且把落地、起飞机场分开来
				}else if(arr_apt.isChanged()){
					chgType = 35;
					chgHead = "起飞机场";
					
				}else if( etd.isChanged() ){
					chgType = 20;
					chgHead = "预计起飞";
					
				}else if( eta.isChanged() ){
					chgType = 25;
					chgHead = "预计到达";
					
				}else if(ac_stop.isChanged() ){
					chgType = 40;
					chgHead = "出港机位";
					
				}else if( ac_stop_arr.isChanged()){
					chgType = 45;
					chgHead = "进港机位";
					
				}else if(std.isChanged()){
					chgType = 50;
					chgHead = "计划起飞";
					
				}else if( sta.isChanged()){
					chgType = 55;
					chgHead = "计划到达";
					
				}else if(delay_code.isChanged() || delay_time.isChanged()){
					chgType = 300;
					chgHead = "延误";
					
					colSetSql.append(",isdelay=1");
					
					//计算延误时间
					if( !delay_time.isChanged() && delay_time.getNewValue() == null && out_time.getNewValue() != null){
						Date date_out = (Date)out_time.getNewValue();
						Date date_std = (Date)std.getNewValue();
						long delay_min = ( date_out.getTime() - date_std.getTime())/ (1000*60); //延误时间以分钟为单位
						
						colSetSql.append(",delay_time=?");
						paramValList.add(delay_min);
						chgContent.append(",延误时间["+delay_min+"]分钟");
					}
					
					String delayreason = FlightCache.getInstance().getDelayReason(delay_code.getNewValStr());
					chgContent.append(",延误原因["+delayreason+"]");
					
					logger.debug("航班flt_pk=["+newvo.getStringValue("flt_pk")+"]延误.");
				}else if(out_time.isChanged() || off_time.isChanged() || on_time.isChanged() || in_time.isChanged() ){
					chgType = 60;
					chgHead = "航班状态";
					needNotify = false;
					
				}else if(alt_apt1.isChanged() || alt_apt2.isChanged()){
					chgType = 70;
					chgHead = "备降机场";
					needNotify = false;
				}
				
				//更新飞机状态,在预计时间、机位、落地机场变更的情况下,同时会有OOOI时间的变更
				if(out_time.isChanged() || off_time.isChanged() || on_time.isChanged() || in_time.isChanged() ){
					acState = judgeAcStateByFlightChgTime(out_time, off_time, on_time, in_time, etd, eta);
					//更新飞机状态
					colSetSql.append(",ac_state=?");
					paramValList.add(acState);
					logger.debug("航班flt_pk=["+newvo.getStringValue("flt_pk")+"]飞机状态更新为["+acState+"]");
					
					//插入航班动态变化日志F_AC_STATE
					insertAcStateLog(fltPk, oldvo.getStringValue("FLIGHTNO"), out_time, off_time, on_time, in_time, etd, eta);
				}
				
				//zhangzy 杭州需求添加，预达时间的变动在正负10分钟内，或者已降落，则不对预达时间蝗变动告警
				if( chgType == 25 ){ //飞机已落地，且变更类型为预计到达
					int diffSec =  Math.abs( eta.getDateDiffSecond()); //变动时间不超过10分钟，也忽略
					if(acState >= FlightConst.ACSTATE_60 || diffSec < 10){
						chgType = 26;
						chgHead = "预计到达(忽略)";
					}					
				}
				
				//更新CTD时间,不使用推出、起飞时间
				if(std.isChanged() || etd.isChanged()){
					Date ctd = null;
					if(etd.getNewValue() != null){
						ctd = (Date)etd.getNewValue();
						
					}else if(std.getNewValue() != null){
						ctd = (Date)std.getNewValue();
					}
					colSetSql.append(",ctd=?");
					paramValList.add(ctd);
					
				}
				
				//更新CTA时间
				if(sta.isChanged() || eta.isChanged()){
					Date cta = null;
					if(eta.getNewValue() != null){
						cta = (Date)eta.getNewValue();
						
					}else if(sta.getNewValue() != null){
						cta = (Date)sta.getNewValue();
					}
					colSetSql.append(",cta=?");
					paramValList.add(cta);
				}

			}
			
			//备注变化
			FlightColChangeVo memo = new FlightColChangeVo(newvo, oldvo, "备注", "memo");
			colSetSql.append(memo.getColSetStr());
			memo.setParamValList(paramValList);
			chgContent.append(memo.getChgDesc());
			
			
			if(chgType == 100 && memo.isChanged()){
				chgType = 100;
				chgHead = "航班备注";
				needNotify = false;
			}
		}
		
		//根据飞机状态判断航班是否完成
		int old_ac_state = oldvo.getIntegerValue("AC_STATE");
		if(old_ac_state >= FlightConst.ACSTATE_70){
			isFlightEnd = true;
		}
		
		String newAcnum = newvo.getStringValue("ac_id");
		String depApt = newvo.getStringValue("dep_apt");
		String arrApt = newvo.getStringValue("arr_apt");
		boolean isDepAtpRel = ApmsServerCache.getInstance().isMaintainAptByCode3(depApt);
		boolean isArrAtpRel = ApmsServerCache.getInstance().isMaintainAptByCode3(arrApt);
		
		boolean isFlttypeChg = false;
		//不锁定、非取消航班、未执飞结束则不判断维护类型zhangzy 20140928
		if( !"1".equals( oldvo.getStringValue("ISLOCKEDIN")) && isFlightEnd == false && !"1".equals(newCancelFlag) ){
			//航班维护类型、前一和后一航班,不更新
			FlightColChangeVo typeDep = new FlightColChangeVo(oldvo, oldvo, "起飞维护", "new_flttype_dep","flttype_dep");
			
			FlightColChangeVo typeArr = new FlightColChangeVo(oldvo, oldvo, "落地维护", "new_flttype_arr","flttype_arr");
			
			if(typeArr.isChanged() ){
				//再进一步判断变化的航班维护类型是否和维护机场有关
				colSetSql.append(typeArr.getColSetStr());
				typeArr.setParamValList(paramValList);
				chgContent.append(typeArr.getChgDesc());
				
				if(isArrAtpRel==true){
					chgType = 450;
					chgHead = "落地维护";
					needNotify = true;
					isFlttypeChg = true;
				}
			}
			
			if(typeDep.isChanged()){
				colSetSql.append(typeDep.getColSetStr());
				typeDep.setParamValList(paramValList);
				chgContent.append(typeDep.getChgDesc());
				
				if(isDepAtpRel == true){
					chgType = 400;
					chgHead = "起飞维护";
					needNotify = true;
					isFlttypeChg = true;
				}
			}
			
			if(isFlttypeChg ){
				//再把确认状态改为未确认
				colSetSql.append(",isconfirmed = 0 ");
				
				//更新该航班的前一航班数据
				colSetSql.append(",pre_flightid=?");
				paramValList.add(newvo.getStringValue("NEW_PRE_FLTPK"));
			}
		}
		
		sql.append(colSetSql.toString());
		sql.append(" WHERE FLT_PK = ?" );
		paramValList.add(fltPk);
		
		//TODO 对于已经人工确认的数据，需要处理维修工作项信息
		
		Object[] paramValArr = new Object[paramValList.size()];
		for(int i=0;i<paramValList.size();i++){
			paramValArr[i] = paramValList.get(i);
		}
		
		if(chgContent.length() == 0){
			chgContent.append(" ");
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql.toString(), paramValArr);
		
		if(isFlttypeChg || isHashValueChg ){
			
			//zhangzy 20140928 如果航班未确认并且起飞时间和当前相差8小时，则不生成变动信息
			String isconfirmed = oldvo.getStringValue("ISCONFIRMED");
			Date newStd = newvo.getDateValue("std");
			int hourDiff = DateUtil.hoursDiff(newStd, new Date());
			if("0".equals(isconfirmed) && hourDiff > 12){
				logger.info("航班信息变动，但是和航班工作无关，不生成变动信息.");
			}else{
				//更新变动的航班记录，并记录到变动记录表中
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, inChgLogSql.toString(),fltPk,newAcnum,oldvo.getStringValue("FLIGHTNO")
						,oldvo.getDateValue("FLT_DATE"), chgType, chgContent.substring(1) ,needNotify);
			}
			logger.info("航班flt_pk=["+newvo.getStringValue("flt_pk")+"],flt_id=["+newvo.getStringValue("flt_id")+"],变动类型["+chgHead+"].");
		}else{
			logger.debug("航班flt_pk=["+newvo.getStringValue("flt_pk")+"],flt_id=["+newvo.getStringValue("flt_id")+"]变化信息和维护机场无关！");
		}
		dmo.commit(ApmsConst.DS_APMS);
		uptNum++;
		
	}
	
	private void insertAcStateLog(String flightPk,String flightno,FlightColChangeVo out_time,FlightColChangeVo off_time,
			FlightColChangeVo on_time,FlightColChangeVo in_time,FlightColChangeVo etd,FlightColChangeVo eta){
		
		int state = judgeAcStateByFlightChgTime(out_time, off_time, on_time, in_time, etd, eta);
		try{
			flightMng.insertAcState(flightPk, flightno, state+"", "OMIS", new Date(), "SYSTEM_INTF", null);
		}catch (Exception e) {
			logger.error("接口插入状态状态日志异常," + e.toString());
			e.printStackTrace();
		}
		
	}
	
	private int judgeAcStateByFlightChgTime(FlightColChangeVo out_time,FlightColChangeVo off_time,
			FlightColChangeVo on_time,FlightColChangeVo in_time,FlightColChangeVo etd,FlightColChangeVo eta){
		//从后面状态倒序判断，如果飞机已靠桥，则肯定已落地
		if( in_time.getNewValue() != null){
			return FlightConst.ACSTATE_70;
			
		}else if( on_time.getNewValue() != null){
			return FlightConst.ACSTATE_60;
			
		}else if( off_time.getNewValue() != null){
			return FlightConst.ACSTATE_40;
			
		}else if( out_time.getNewValue() != null){
			return FlightConst.ACSTATE_30;
			
		}else if( etd.getNewValue() != null || eta.getNewValue() != null ){
			return FlightConst.ACSTATE_06;
			
		}
		
		return FlightConst.ACSTATE_30;
	}
	
	private int judgeAcStateByFlightVo(HashVO vo){
		//从后面状态倒序判断，如果飞机已靠桥，则肯定已落地
		if( vo.getDateValue("in_time") != null){
			return FlightConst.ACSTATE_70;
		}else if( vo.getDateValue("on_time") != null){
			return FlightConst.ACSTATE_60;
		}else if( vo.getDateValue("off_time") != null){
			return FlightConst.ACSTATE_40;
		}else if( vo.getDateValue("out_time") != null){
			return FlightConst.ACSTATE_30;
		}else if( vo.getDateValue("etd") != null || vo.getDateValue("eta") != null ){
			return FlightConst.ACSTATE_06;
		}
		
		return FlightConst.ACSTATE_00;
	}
	
	/**
	 * 清空临时表，重新插入临时表数据
	 * @param synvoList
	 * @throws Exception
	 */
	private void synTempTableData(ArrayList<FlightSynTempVo> synvoList) throws Exception{
		//清空临时表，插入临时表数据 begin
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, "truncate table F_SYN_TEMPTABLE");
		
		NovaDBConnection conn = dmo.getConn(ApmsConst.DS_APMS);
		String sql = "INSERT INTO F_SYN_TEMPTABLE(FLT_PK,HASHVALUE,FLTTYPE_DEP,FLTTYPE_ARR,PRE_FLTPK,NEXT_FLTPK,NEXT_FLTNO) VALUES(?,?,?,?,?,?,?)";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    for(int m=0;m<synvoList.size();m++){
	    	FlightSynTempVo temp = synvoList.get(m);
	    	ps.setObject(1, temp.getFlt_pk());
	    	ps.setObject(2, temp.getHashvalue());
	    	ps.setObject(3, temp.getFlttype_dep());
	    	ps.setObject(4, temp.getFlttype_arr());
	    	ps.setObject(5, temp.getPreFltPk());
	    	ps.setObject(6, temp.getNextFltPk());
	    	ps.setObject(7, temp.getNextFltNo());
	    	
	    	ps.addBatch();
	    	
	    	if(m % 1000 == 0){
	    		ps.executeBatch(); //1000条提交一次
	    		dmo.commit(ApmsConst.DS_APMS);
	    	}
	    }
	    //最后提交一次
	    ps.executeBatch(); 
		dmo.commit(ApmsConst.DS_APMS);
		//临时表数据处理 end
	}
	
	/**
	 * 新增的航班计划入库
	 * @param vo
	 * @param synVo
	 * @throws Exception
	 */
	private void insertNewData(HashVO vo,HashVO synVo) throws Exception{
		//对于需要进行转换的数据，这里处理一下
		String iata_c = vo.getStringValue("iata_c").trim();
		String flt_id = vo.getStringValue("flt_id").trim();
		
		logger.debug("处理新增航班计划数据flt_pk=["+vo.getStringValue("flt_pk")+"],flt_date=["+vo.getStringValue("flt_date")+"],flt_id=["+vo.getStringValue("flt_id")+"].");
		
//		if("10202886".equals(vo.getStringValue("flt_pk"))){
//			System.out.println("断点");
//		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertFlightSql, vo.getStringValue("flt_pk")
				,vo.getDateValue("flt_date"),iata_c,vo.getStringValue("co_seq"),iata_c+flt_id  //,FLT_DATE ,IATA_C ,CO_SEQ ,FLIGHTNO
				,flt_id ,vo.getStringValue("flt_seq"),vo.getStringValue("flt_task")//,FLT_ID ,FLT_SEQ ,FLT_TASK
				,vo.getStringValue("ac_id") ,vo.getStringValue("ac_type") //,ACNUM ,ACMODEL
				,vo.getStringValue("dep_apt"),vo.getStringValue("arr_apt"),vo.getStringValue("ac_stop") //,DEP_APT ,ARR_APT ,AC_STOP
				,vo.getStringValue("ac_stop_arr"),vo.getStringValue("alt_apt1"),vo.getStringValue("alt_apt2")// ,AC_STOP_ARR,ALT_APT1 ,ALT_APT2
				,vo.getDateValue("std"),vo.getDateValue("sta"),vo.getDateValue("etd"),vo.getDateValue("eta")//,STD ,STA ,ETD ,ETA
				,vo.getDateValue("std"),vo.getDateValue("sta") //,CTD,CTA
				,vo.getDateValue("off_time"),vo.getDateValue("on_time"),vo.getDateValue("out_time"),vo.getDateValue("in_time")//,OFF_TIME ,ON_TIME ,OUT_TIME ,IN_TIME 
				,vo.getStringValue("memo"),synVo.getStringValue("flttype_dep"),synVo.getStringValue("flttype_arr") //,MEMO,FLTTYPE_DEP ,FLTTYPE_ARR ,
				,ApmsConst.BOOLEAN_FLASE,synVo.getStringValue("hashvalue") //,ISCONFIRMED ,HASHVALUE
				
				,judgeAcStateByFlightVo(vo) //航班状态
				//上一航班号、下一航班号
				,synVo.getStringValue("pre_fltpk"),synVo.getStringValue("next_fltpk"),synVo.getStringValue("next_fltno")
				
				,ApmsConst.BOOLEAN_FLASE,vo.getStringValue("delay_code"),vo.getStringValue("delay_time") //,ISDELAY ,DELAY_CODE ,DELAY_TIME
				//取消数据项
				,vo.getStringValue("cancel_flag"),vo.getStringValue("cancel_type") //,CANCEL_FLAG ,CANCEL_TYPE 
				,vo.getDateValue("cancel_time"),vo.getStringValue("cancel_reason"),vo.getStringValue("cancel_src") //,CANCEL_TIME ,CANCEL_REASON ,CANCEL_SRC
				,vo.getStringValue("ac_owner"),vo.getStringValue("ac_type_old"),vo.getStringValue("ac_type_market")//,AC_OWNER ,AC_TYPE_OLD ,AC_TYPE_MARKET
				,vo.getStringValue("vip"),vo.getStringValue("vip_name"),vo.getStringValue("plan_flag") //,VIP ,VIP_NAME ,PLAN_FLAG
				,vo.getStringValue("baseorgid") );		
		dmo.commit(ApmsConst.DS_APMS);
		
	}
	
	/**
	 * 判断航班维护种类
	 * @param vosFlt 航班对象列表，已排序
	 * @param voCur 当前航班对象
	 * @param i 当前对象在数组中的序号
	 * @throws Exception
	 */
	private FlightSynTempVo judgeFlightType(HashVO[] vosFlt,HashVO voCur,int i) throws Exception{
		//判断该航班的PF,AF,TR,AF+PF
		
		FlightSynTempVo synvo = new FlightSynTempVo();
		synvo.setFlt_pk(voCur.getStringValue("flt_pk"));
		synvo.setHashvalue(voCur.getStringValue("content_hashvalue"));
		String depType = FlightConst.FLIGHT_TYPE_TR;
		String arrType = FlightConst.FLIGHT_TYPE_TR;
		
		String cancelFlag = voCur.getStringValue("CANCEL_FLAG").trim();
		//如果是取消航班，直接返回
		if( "1".equals(cancelFlag) || "2".equals(cancelFlag) || "D".equals(cancelFlag) || "E".equals(cancelFlag) || "F".equals(cancelFlag)
			|| "G".equals(cancelFlag) || "H".equals(cancelFlag) || "I".equals(cancelFlag)	){
			synvo.setFlttype_dep(depType); //给个默认值
			synvo.setFlttype_arr(arrType);
			return synvo;
		}
		
		//窄体机的判断方式,字段比较顺序飞机号、航班日期
		String cur_acid  = voCur.getStringValue("AC_ID");
		Long cur_fltdate = voCur.getDateValue("FLT_DATE").getTime();
		
//		String fltno = voCur.getStringValue("FLT_ID");
//		if(cur_acid.equals("B5933") && fltno.equals("968")){
//			System.out.println("判断jedge");
//		}
		
		String frametype = FlightConst.AIRFRAMETYPE_NARROW;
		//判断飞机是宽体机还是窄体机 
		AirCraftVo acvo = ApmsServerCache.getInstance().getAirCraftVoByAcnum(cur_acid);
		if(acvo != null){
			if( FlightConst.AIRFRAMETYPE_WIDE.equals(acvo.getFrameType()) ){
				frametype = FlightConst.AIRFRAMETYPE_WIDE;
			}
		}
		
		//D 与前一条航班数据比较，判定起飞前任务
		if(i ==0){
			//D1 第一条数据，肯定是航前
			depType = FlightConst.FLIGHT_TYPE_PF;
		}else{
			HashVO voPre = null;
			
			//查找前一航班数据，循环查找
			int preIdx = i-1;
			String pre_cancelflag;
			
			while(true){
				voPre = vosFlt[preIdx];
				preIdx--;
				
				pre_cancelflag = voPre.getStringValue("CANCEL_FLAG").trim();
				//如果前一航班是取消航班，继续向前找,正常航班则结束
				if( "0".equals(pre_cancelflag) ||"3".equals(pre_cancelflag) ||"4".equals(pre_cancelflag)
						 ||"5".equals(pre_cancelflag) ||"6".equals(pre_cancelflag) ||"7".equals(pre_cancelflag)
						 ||"8".equals(pre_cancelflag) ||"9".equals(pre_cancelflag) ||"A".equals(pre_cancelflag)
						 ||"B".equals(pre_cancelflag) ||"C".equals(pre_cancelflag) ){
					break;
				}
				if( preIdx <= 0){
					break;
				}
			}
			
			String pre_acid  = voPre.getStringValue("AC_ID");
			long pre_fltdate = voPre.getDateValue("FLT_DATE").getTime();
			
			if( !cur_acid.equals(pre_acid) || !"0".equals(pre_cancelflag) ){
				//D2 此航班与前一航班不是同一飞机
				depType = FlightConst.FLIGHT_TYPE_PF;
			}else{
				String pre_apt = voPre.getStringValue("ARR_APT");//前一航班落地机场和本航班起飞机场是否一致 zhangzy 20160701
				String cur_apt = voCur.getStringValue("DEP_APT");
				if(pre_apt.equals(cur_apt)){
					synvo.setPreFltPk(voPre.getStringValue("FLT_PK"));
					//同一飞机的不同班次,再比较日期
					//根据宽体机、窄体机分别判断
					if(frametype.equals(FlightConst.AIRFRAMETYPE_WIDE)){
						//宽体机判断方式,停场时间
						Date arrTime = voPre.getDateValue("STA");
						Date depTime = voCur.getDateValue("STD");
						if(voPre.getDateValue("IN_TIME") != null){
							arrTime = voPre.getDateValue("IN_TIME");
						}else if(voPre.getDateValue("ETA") != null){
							arrTime = voPre.getDateValue("ETA");
						}
						
						if(voCur.getDateValue("ETD") != null){
							depTime = voCur.getDateValue("ETD");
						}
						
						long deff = (depTime.getTime() - arrTime.getTime()) / (1000*60*60) ;
						//大于6小时，拆分，4-6之前为AF+PF
						if(deff >= 6){
							depType = FlightConst.FLIGHT_TYPE_PF;
						}else if(deff >= 4){
							depType = FlightConst.FLIGHT_TYPE_AFPF;
						}else{
							depType = FlightConst.FLIGHT_TYPE_TR;
						}
						
					}else{
						//窄体机判断方式
						if(cur_fltdate == pre_fltdate){
							//D3 同一飞机，同一天
							depType = FlightConst.FLIGHT_TYPE_TR;
						}else{
							//D4 不是同一天,航前
							depType = FlightConst.FLIGHT_TYPE_PF;
						}
					}
					//机场一致 end
				}else{
					//机场不一致
					depType = FlightConst.FLIGHT_TYPE_PF;
					
				}
				
				//同一架飞机end
			}
		}//起飞维护判断结束
		
		
		//A 与后一航班数据比较,判定落地后任务
		if(i == vosFlt.length){ //最后一条数据，肯定是是航后
			
			arrType = FlightConst.FLIGHT_TYPE_AF; //A1
			return synvo;
		}else{
			//查找前一航班数据，循环查找
			int nextIdx = i+1;
			String next_cancelflag;
			HashVO voNext = null;
			
			while(true && nextIdx < vosFlt.length ){
				voNext = vosFlt[nextIdx];
				nextIdx++;
				
				next_cancelflag = voNext.getStringValue("CANCEL_FLAG").trim();
				//如果后一航班是取消航班，继续向后找,正常航班则结束
				if( "0".equals(next_cancelflag) ||"3".equals(next_cancelflag) ||"4".equals(next_cancelflag)
						 ||"5".equals(next_cancelflag) ||"6".equals(next_cancelflag) ||"7".equals(next_cancelflag)
						 ||"8".equals(next_cancelflag) ||"9".equals(next_cancelflag) ||"A".equals(next_cancelflag)
						 ||"B".equals(next_cancelflag) ||"C".equals(next_cancelflag))
				{
					break;
				}
				if( nextIdx >= vosFlt.length ){
					break;
				}
			}
			
			if(voNext != null){
				String next_acid  = voNext.getStringValue("AC_ID");
				long next_fltdate = voNext.getDateValue("FLT_DATE").getTime();
				
				if( !cur_acid.equals(next_acid)){
					//A2 此航班与后一航班不是同一飞机
					arrType = FlightConst.FLIGHT_TYPE_AF;
				}else{
					synvo.setNextFltPk(voNext.getStringValue("FLT_PK"));
					synvo.setNextFltNo(voNext.getStringValue("IATA_C")+voNext.getStringValue("FLT_ID"));
					
					String arr_apt = voCur.getStringValue("ARR_APT");//前一航班落地机场和本航班起飞机场是否一致 zhangzy 20160701
					String next_apt = voNext.getStringValue("DEP_APT");
					if(arr_apt.equals(next_apt)){
						//同一飞机的不同班次,再比较日期
						//根据宽体机、窄体机分别判断
						if(frametype.equals(FlightConst.AIRFRAMETYPE_WIDE)){
							//宽体机判断方式,停场时间
							Date arrTime = voCur.getDateValue("STA");
							Date depTime = voNext.getDateValue("STD");
							if(voCur.getDateValue("IN_TIME") != null){
								arrTime = voCur.getDateValue("IN_TIME");
							}else if(voCur.getDateValue("ETA") != null){
								arrTime = voCur.getDateValue("ETA");
							}
							
							if(voNext.getDateValue("ETD") != null){
								depTime = voNext.getDateValue("ETD");
							}
							
							long deff = (depTime.getTime() - arrTime.getTime()) / (1000*60*60) ;
							//大于6小时拆分,4-6为AF+PF
							if( deff >=6 ){
								arrType = FlightConst.FLIGHT_TYPE_AF;
							}else if(deff >= 4){
								//A21 停场时间大于4小时
								arrType = FlightConst.FLIGHT_TYPE_AFPF;
							}else{
								arrType = FlightConst.FLIGHT_TYPE_TR;
							}
							//宽体机到港维护判断结束
							
						}else{//窄体机的判断条件
							if(cur_fltdate == next_fltdate){
								//A3 同一飞机，同一天,短停
								arrType = FlightConst.FLIGHT_TYPE_TR;
							}else{
								//A4 不是同一天,航后
								arrType = FlightConst.FLIGHT_TYPE_AF;
							}
						}
					//同一机场 end
					}else{
						arrType = FlightConst.FLIGHT_TYPE_AF;
					}
				}//同一飞机end
			}//end next 处理
			
		}
		
		synvo.setFlttype_dep(depType);
		synvo.setFlttype_arr(arrType);
		
		return synvo;
	}
	
	/**
	 * 根据主键值，查询出新增的航班数据
	 * @return
	 * @throws Exception
	 */
	private String getAddFlightsSql() throws Exception{
		StringBuilder sb = new StringBuilder("SELECT FLT_PK,HASHVALUE,FLTTYPE_DEP,FLTTYPE_ARR");
		sb.append(" ,T.PRE_FLTPK  ,T.NEXT_FLTPK  ,T.NEXT_FLTNO ");
		sb.append(" FROM F_SYN_TEMPTABLE T");
		sb.append(" WHERE NOT EXISTS (SELECT 1 FROM F_FLIGHT_SCHEDULE S WHERE S.FLT_PK=T.FLT_PK)" );
		
		return sb.toString();
	}
	

	/**
	 * 根据航班维护映射关系，判断该航班是否需要维护 
	 * 对应关系 0-国航飞机,1,航空公司+航班号 2，航空公司 3，航空公司+机型 
	 * @param fltvo
	 * @return
	 * @throws Exception
	 */
	private boolean judgeIsMaintain(HashVO fltvo) throws Exception{
		
		//飞机保障对应关系 0-国航飞机,1,航空公司+航班号 2，航空公司 3，航空公司+机型 
		String iata_c = fltvo.getStringValue("iata_c").trim();
		String dep_apt = fltvo.getStringValue("dep_apt").trim();
		String arr_apt = fltvo.getStringValue("arr_apt").trim();
		String flt_id = fltvo.getStringValue("flt_id").trim();
		String ac_id = fltvo.getStringValue("ac_id");
		String ac_type = fltvo.getStringValue("ac_type");
		String baseorgid = fltvo.getStringValue("baseorgid");
		
		if(ac_id == null || "".equals(ac_id)){
			return false;
		}
		
		boolean isApOk = false;
		if(ApmsServerCache.getInstance().isMaintainAptByCode3(dep_apt) 
				|| ApmsServerCache.getInstance().isMaintainAptByCode3(arr_apt)) {
			return true;
		}
		
		//判断飞机所属基地,杭州基地的飞机的航班信息也同步
		
		String hghOrgid = "3";//TODO 杭州的判断目前是写死的
		if( hghOrgid.equals(baseorgid) ){
			return true;
		}
		
		//机场+  公司 + (航班号或机型) 均吻合才OK
		if(isApOk == true){
			return true;
		}else{
			return false;
		}
		
	}
	
	/** 插入更新日志， 异常不处理 */
	private void insertUpdateLog(long _exeSecond,int _totalNum,int _dealNum,int _addNum,int _uptNum,int _cancelNum,int _result,String _errMsg){
		StringBuilder sql = new StringBuilder("INSERT INTO F_SYN_LOG ");
		sql.append(" (ID,UPDATETIME,EXESECOND,NUM_TOTALFLT,NUM_DEALFLT,NUM_ADD,NUM_UPDATE,NUM_CANCEL,RESULT,ERRORMSG)");
		sql.append(" VALUES (S_F_SYN_LOG.NEXTVAL,SYSDATE,?,?,?, ?,?,?, ?,?)");
		
		if(_errMsg != null && _errMsg.length() > 2900)
			_errMsg = _errMsg.substring(0, 2900);
		
		try{
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql.toString(),_exeSecond,_totalNum,_dealNum, _addNum,_uptNum, _cancelNum, _result, _errMsg );
			dmo.commit(ApmsConst.DS_APMS);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("插入接口同步日志报错." + e.toString());
		}
		
	}
	
	/**判断两个字符串是否相等,均为null也视为相等*/
	protected boolean strEqual(String str1,String str2){
		if(str1 == null && str2 == null){
			return true;
		}else if(str1 == null && str2 != null) {
			return false;
		}else if(str1 != null && str2 == null) {
			return false;
		}else if(str1.equals(str2)){
			return true;
		}else{
			return false;
		}
	}
	

}