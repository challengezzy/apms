package com.apms.bs.intf.omis;

import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import com.apms.ApmsConst;

/**
 *  OMIS接口中f_flight_delete表数据同步
 */
public class Omis_Flight_DeleteExtractService {

	private Logger logger = NovaLogger.getLogger(this.getClass());

	private CommDMO dmo = new CommDMO();
	

	private String omis_flight_delete_tablename = "AIRCHINA.flight_delete";//"omis_dd_info";
	
	//private String omis_flight_delete_tablename = "omis_flight_delete";//测试表;

	
	private String omis_datssource = ApmsConst.DS_OMIS;//ApmsConst.DS_APMS;
	
	public void extractFlightDeleteInfo() throws Exception {
		try {

			// 接口同步过程
			// 1 查询出最新从OMIS系统同步数据的时间戳OPERATE_TIME
			String operate_time = getMaxOperateTime();
			
			if(operate_time == null){//第一次初始化化情况
				initFlightDeleteData();
			}
			
			// 2 从OMIS中查询所有大于OPERATE_TIME的flight_delete数据
			HashVO[] vos = queryForFlightDeleted(operate_time);
			
			// 插入从OMIS中查询到的新数据
			if(vos.length<=0){
				logger.debug("OMIS的flight_delete表中没有更新的数据。");
			}else{
				String sql=getInsertSql();
				for (int i = 0; i < vos.length; i++) {
					insertIntoOmis_Flight_Delete(vos[i],sql);
				}
			}
			logger.info("抽取flight_delete表数据结束...");
		} catch (Exception e) {
			logger.error("flight_delete表数据抽取异常！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_OMIS);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	private void initFlightDeleteData() throws Exception{
		String querySql="select flt_pk, iata_c, flt_date, co_seq, flt_id, flt_task, ac_id, ac_type, flt_type" //9
			+", route_type, dep_apt, std, sta, arr_apt, etd, eta, off_time, on_time, out_time" //10 
			+", in_time, ac_stop, vip, no_oil_load, alt_apt1, delay_code, cancel_flag" //7 
			+", memo, segment_time, takeoff_fuel, trip_fuel, region, global_pk, sortie, action_date" //8
			+", delete_src, ac_type_market, ac_type_old, ac_stop_arr, release_region, crew_standard, release_flag"//7
			+", off_delay_standard, on_delay_standard, first_flag, cancel_type, cancel_time, cancel_reason, delay_flag_rls" //7    
			+", delay_code_rls, memo_rls, socrls_flag from "+omis_flight_delete_tablename//3
			+" WHERE ACTION_DATE>=TO_DATE('20141001','YYYYMMDD')";
		dmo.executeImportByDS(omis_datssource, querySql.toString(), getFromCols(51), ApmsConst.DS_APMS, getInsertSql(), 3000);
		logger.debug("omis_flight_delete表中没有数据,全量初始化");
	}
	
	public int[] getFromCols(int length){
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i+1;
		
		return fromcols;
	}

	/**
	 * 插入omis_flight_delete表
	 * 
	 * @param vo
	 * @throws Exception 
	 */
	public void insertIntoOmis_Flight_Delete(HashVO vo,String sql) throws Exception{
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,sql,vo.getStringValue("FLT_PK"),vo.getStringValue("IATA_C"),vo.getDateValue("FLT_DATE"),
			      vo.getStringValue("CO_SEQ"),vo.getStringValue("FLT_ID"),vo.getStringValue("FLT_TASK"),vo.getStringValue("AC_ID"),//7
			      vo.getStringValue("AC_TYPE"),vo.getStringValue("FLT_TYPE"),vo.getStringValue("ROUTE_TYPE"),vo.getDateValue("DEP_APT"),
			      vo.getDateValue("STD"),vo.getDateValue("STA"),vo.getStringValue("ARR_APT"),vo.getDateValue("ETD"),//8
			      vo.getDateValue("ETA"),vo.getDateValue("OFF_TIME"),vo.getDateValue("ON_TIME"),vo.getDateValue("OUT_TIME"),
			      vo.getDateValue("IN_TIME"),vo.getStringValue("AC_STOP"),vo.getStringValue("VIP"),vo.getStringValue("NO_OIL_LOAD"),//8
			      vo.getStringValue("ALT_APT1"),vo.getStringValue("DELAY_CODE"),vo.getStringValue("CANCEL_FLAG"),vo.getStringValue("MEMO"),
			      vo.getStringValue("SEGMENT_TIME"),vo.getStringValue("TAKEOFF_FUEL"),vo.getStringValue("TRIP_FUEL"),vo.getStringValue("REGION"),//8
			      vo.getStringValue("GLOBAL_PK"),vo.getStringValue("SORTIE"),vo.getDateValue("ACTION_DATE"),vo.getStringValue("DELETE_SRC"),
			      vo.getStringValue("AC_TYPE_MARKET"),vo.getStringValue("AC_TYPE_OLD"),vo.getStringValue("AC_STOP_ARR"),vo.getStringValue("RELEASE_REGION"),//8
			      vo.getStringValue("CREW_STANDARD"),vo.getStringValue("RELEASE_FLAG"),vo.getStringValue("OFF_DELAY_STANDARD"),vo.getStringValue("ON_DELAY_STANDARD"),
			      vo.getStringValue("FIRST_FLAG"),vo.getStringValue("CANCEL_TYPE"),
			      vo.getDateValue("CANCEL_TIME"),vo.getStringValue("CANCEL_REASON"),vo.getStringValue("DELAY_FLAG_RLS"),vo.getStringValue("DELAY_CODE_RLS"),
			      vo.getStringValue("MEMO_RLS"),vo.getStringValue("SOCRLS_FLAG"));//12
		dmo.commit(ApmsConst.DS_APMS);
		
	}
	
	public String getInsertSql(){
		String sql="insert into OMIS_FLIGHT_DELETE(id,flt_pk, iata_c, flt_date, co_seq, flt_id, flt_task, ac_id, ac_type, flt_type" //10       
			+", route_type, dep_apt, std, sta, arr_apt, etd, eta, off_time, on_time, out_time" //10 
			+", in_time, ac_stop, vip, no_oil_load, alt_apt1, delay_code, cancel_flag" //7 
			+", memo, segment_time, takeoff_fuel, trip_fuel, region, global_pk, sortie, action_date" //8
			+", delete_src, ac_type_market, ac_type_old, ac_stop_arr, release_region, crew_standard, release_flag"//7
			+", off_delay_standard, on_delay_standard, first_flag, cancel_type, cancel_time, cancel_reason, delay_flag_rls" //7    
			+", delay_code_rls, memo_rls, socrls_flag)"//3     
			+"values(S_OMIS_FLIGHT_DELETE.nextval,?,?,?,?,?,?,?,?,?,"   //S_OMIS_FLIGHT_DELETE_TEST.nextval,
			+"?,?,?,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,"
			+"?,?,?)";
		return sql;
	}

	

	/**
	 * 从OMIS中查询所有大于action_date的flight_delete表
	 * 
	 * @return
	 * @throws Exception
	 */
	public HashVO[] queryForFlightDeleted(String timeStr) throws Exception {
		HashVO[] vos;
		String querySql="select flt_pk, iata_c, flt_date, co_seq, flt_id, flt_task, ac_id, ac_type, flt_type" //9
			+", route_type, dep_apt, std, sta, arr_apt, etd, eta, off_time, on_time, out_time" //10 
			+", in_time, ac_stop, vip, no_oil_load, alt_apt1, delay_code, cancel_flag" //7 
			+", memo, segment_time, takeoff_fuel, trip_fuel, region, global_pk, sortie, action_date" //8
			+", delete_src, ac_type_market, ac_type_old, ac_stop_arr, release_region, crew_standard, release_flag"//7
			+", off_delay_standard, on_delay_standard, first_flag, cancel_type, cancel_time, cancel_reason, delay_flag_rls" //7    
			+", delay_code_rls, memo_rls, socrls_flag from "+omis_flight_delete_tablename
			+" where action_date>to_date('"+ timeStr + "','yyyy-MM-dd HH24:MI:SS') "
			+" order by action_date asc  ";
		String sql = "select * from ( "+ querySql+" ) where rownum < 1000";//限制数量
		vos = dmo.getHashVoArrayByDSUnlimitRows(omis_datssource, sql);
		return vos;
	}

	/**
	 * 查询出从OMIS系统中导入的最新的操作时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getMaxOperateTime() throws Exception {
		String operate_time;
		String querySql = "SELECT MAX(ACTION_DATE) operate_time FROM OMIS_FLIGHT_DELETE";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);

		if(vos.length==0){
			operate_time =null;
			}
		else{
				operate_time = vos[0].getStringValue("operate_time");
			}
		
		logger.debug("本地库中的最大操作时间为: " + operate_time);

		return operate_time;
	}

}
