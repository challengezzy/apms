package com.apms.bs.intf.omis;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

/**
 * OMIS接口中的航班计划数据同步，只同步，不做复杂的逻辑处理。将OMIS中航班计划接口中的数据全部同步过来
 * 航班计划分两个表存储：
 * OMIS_FLIGHT_SCHEDULE_HIS：存储历史的航班计划数据T-2之前
 * OMIS_FLIGHT_SCHEDULE ：存储最新的航班计划数据 T-2 至 T+7
 * @author jerry
 * @date Mar 15, 2014
 */
public class OmisFlightScheduleService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private String omis_fltsch_table = "airchina.flight_information"; //OMIS中表，正式部署时修改airchina.flight_information,omis_flight_information
	private String omis_ds = ApmsConst.DS_OMIS; //OMIS数据源，正式部署时修改
	
	/**
	 * 同步T-1到T+daynum 天 内数据
	 * @param dayNum
	 * @throws Exception
	 */
	public void extractCurrentFlight(String dayNum) throws Exception{
		try{
			//清空表数据
			String toTable = "OMIS_FLIGHT_SCHEDULE";
			//清空表数据
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, "TRUNCATE TABLE " + toTable);
			//禁用表索引
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, "call drop_ind_table_proc('"+ toTable +"')");	
			
			//同步计划数据
			extractOmisToFlightSch(1,dayNum);
			
			//恢复表索引
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, "call recover_ind_table_proc('"+ toTable +"')");
			///执行表分析
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, "ANALYZE TABLE "+toTable+" COMPUTE STATISTICS");
			
			//更新飞机的所属基地 
			//TODO 数据多了可能会影响性能
			String sql = "update omis_flight_schedule t set t.baseorgid=(select baseorgid from b_aircraft ac where ac.aircraftsn=t.ac_id )";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			
			//zhangzy 20160404 杭州基地在上站飞机未起飞前，预达时间不准确，应该清空.之前的做法也是忽略预达时间
			String uptsql = "update omis_flight_schedule s set s.eta = null"
				+" where s.off_time is null and eta is not null and arr_apt = 'HGH' ";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptsql);
			dmo.commit(ApmsConst.DS_APMS);
			
		}catch (Exception e) {
			logger.error("同步OMIS中航班历史数据失败，异常原因:" +e.toString());
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		
	}
	
	/**同步T-2天前的历史数据 */
	public void extractHistoryFlight() throws Exception{
		try{
			//删除T-2天的历史数据(如果有的话)，再同步
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, "DELETE OMIS_FLIGHT_SCHEDULE_HIS WHERE FLT_DATE = trunc(SYSDATE)-2");
			
			dmo.commit(ApmsConst.DS_APMS);
			
			//同步数据
			extractOmisToFlightSch(2,"-1");
		}catch (Exception e) {
			logger.error("同步OMIS中航班T-2天数据失败，异常原因:" +e.toString());
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	
	
	/**
	 * 从OMIS系统抽取航班计划到APMS系统
	 * @param model 1-抽取最新数据 2-抽取历史数据 T-1天前
	 * @param dayNum 未来天数
	 * @throws Exception
	 */
	private void extractOmisToFlightSch(int model,String dayNum) throws Exception{
		try{
			logger.info("从OMIS接口表中同步航班计划数据开始...");
			
			String fltTable = "";
			String dateFilter = "";
			
			//判断是抽取历史数据还是当前数据
			if(model == 2){
				dateFilter = " AND FLT_DATE = trunc(SYSDATE)-2 "; //T-2天内的数据
				fltTable = "OMIS_FLIGHT_SCHEDULE_HIS";
				logger.info("本次同步历史航班数据！");
			}else{
				dateFilter = " AND FLT_DATE > SYSDATE-2 AND FLT_DATE < SYSDATE+ "+ dayNum + " "; //T-1到T+7天内的数据
				fltTable = "OMIS_FLIGHT_SCHEDULE";
				logger.info("本次同步当前航班计划数据！");
			}
			
			StringBuilder columnStr = new StringBuilder(""); //共有115个字段
			columnStr.append(" flt_pk,flt_date,iata_c");
			//columnStr.append(" ,trim(co_seq) co_seq");//去掉空格
			columnStr.append(" ,flt_id,flt_seq,con_flt_id,ac_id,ac_type");
			columnStr.append(" ,p_cabin_class,c_cabin_class,service_type,route_type,meals,dep_apt");
			columnStr.append(" ,std,sta,arr_apt,etd,etd_src,eta,eta_src,off_time,off_src,on_time,on_src,out_time,out_src");
			columnStr.append(" ,in_time,in_src,ac_stop,vip,vip_name,no_oil_load,alt_apt1,alt_apt2,delay_code,delay_time");
			columnStr.append(" ,site_checked,segment_time,cfp_flag,cpl_flag,fpl_flag,rel_flag,fly_standard,fly_map_stopflag");
			columnStr.append(" ,cancel_flag,release_flag,release_time,sita_ad_send,sita_aa_send,change_mode,memo,takeoff_fuel");
			columnStr.append(" ,trip_fuel,eta_upd_source,region,plan_flag,create_src,update_src,sta_src,flt_task_src");
			columnStr.append(" ,ac_src,ac_stop_src,alt_apt_src,delay_src,cancel_src,crew_owner,cabin_owner,ac_owner");
			columnStr.append(" ,owner_src,codeshare_flag1,codeshare1_iata_c,codeshare1_flt_id,codeshare_flag2,codeshare2_iata_c");
			columnStr.append(" ,codeshare2_flt_id,codeshare_flag3,codeshare3_iata_c,codeshare3_flt_id,codeshare_src,in_range_time");
			columnStr.append(" ,in_range_src,memo_src,oil_src,sortie,no_oil_accept,cfp_no_oil,global_pk,cancel_reason,ac_type_market");
			columnStr.append(" ,extend_service,ac_type_old,ac_stop_arr,release_region,crew_standard,off_delay_standard,on_delay_standard");
			columnStr.append(" ,first_flag,cancel_type,cancel_time,vehicle_flag,delay_flag_rls,delay_code_rls,memo_rls,socrls_flag");
			columnStr.append(" ,no_oil_load_pre,cip,watch_flag,release_seat,rls_remark,taxy_fuel,rlsseat_flag ");
			
			//查询和表字段区分开
			String col_CoSeqQry = ",trim(co_seq) co_seq ,trim(flt_type) flt_type, trim(flt_task) flt_task,DECODE(ETD,NULL,STD,ETD) ctd";
			String col_CoSeqIn = ",co_seq, flt_type, flt_task,ctd";
			
			StringBuilder qrySql = new StringBuilder("SELECT ");
			qrySql.append(columnStr).append(col_CoSeqQry);
			qrySql.append(" FROM ").append(omis_fltsch_table).append(" WHERE 1=1");
			qrySql.append(dateFilter); //过滤范围
			
			StringBuilder insertSql = new StringBuilder("INSERT INTO ").append(fltTable);
			insertSql.append(" ( ").append(columnStr).append(col_CoSeqIn);
			insertSql.append(",UPDATETIME ) VALUES (");
			
			insertSql.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"); //40个
			insertSql.append(",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"); //40个
			insertSql.append(",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"); //36个
			insertSql.append(",SYSDATE)");
			insertSql.append("");
			
			//批量数据导入
			dmo.executeImportByDS(omis_ds, qrySql.toString(), getFromCols(116), ApmsConst.DS_APMS, insertSql.toString(), 4000);
			
			logger.info("抽取OMIS航班计划接口数据结束...");
		}catch (Exception e) {
			logger.error("抽取OMIS航班计划接口数据异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(omis_ds);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	
	
	/** 生成一个序列数组 */
	private int[] getFromCols(int length){
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i+1;
		
		return fromcols;
	}

}
