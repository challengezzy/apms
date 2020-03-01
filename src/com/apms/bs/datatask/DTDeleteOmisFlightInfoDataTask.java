package com.apms.bs.datatask;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;
import com.apms.ApmsConst;
import com.apms.bs.intf.omis.Omis_Flight_DeleteExtractService;

/**
 * 删除取消的OMIS航班计划
 * 
 * @date Nov 28, 2011
 **/
public class DTDeleteOmisFlightInfoDataTask implements DataTaskExecuteIFC {

	/**
	 * @param task
	 *            定义该报文的参数XML
	 * @param mainThread
	 *            执行任务的主线程
	 * @throws Exception
	 */
	public void dataTaskExec(Element task, DataTaskExecThread mainThread)
			throws Exception {
		
		CommDMO dmo = new CommDMO();
		mainThread.logTaskRun("开始执行[OMIS删除航班数据同步]任务....");
		//任务之前先进行同步airchina.flight_delete表数据到omis_flight_delete中
		Omis_Flight_DeleteExtractService ofd=new Omis_Flight_DeleteExtractService();
		ofd.extractFlightDeleteInfo();
		
//		String sql="select flt_pk from omis_flight_delete f where f.action_date>=sysdate-1" +
//				" and f.flt_date<=sysdate+7";
		String sql="select flt_pk from omis_flight_delete ofd where ofd.action_date >= sysdate - 1" +
		" and ofd.flt_date <= sysdate + 7 and  exists(select 1 from f_flight_schedule f where f.flt_pk=ofd.flt_pk)";
		try {
			HashVO[] vos= dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			List<String> list = new ArrayList<String>();
			String flt_pkArrPK[]=new String[vos.length];
			if(vos.length>0){
				for (int i = 0; i < vos.length; i++) {
					// 机务工作状态
					HashVO vo = vos[i];
					Long flt_pk=vo.getLongValue("FLT_PK");
					String delSql="delete from F_WORKER_STATE f where f.FLIGHTID="+flt_pk;
					list.add(delSql);
					
					String delSql2="delete from F_AC_STATE f where f.FLIGHTID="+flt_pk;
					list.add(delSql2);
					
					String delSql3="delete from F_VEHICLE_DEMAND f where f.FLIGHTID="+flt_pk;
					list.add(delSql3);
					
					String delSql4="delete from F_FLIGHT_CHANGELOG f where f.FLIGHTID="+flt_pk;
					list.add(delSql4);
					
					String delSql5="delete from W_JOB_MAINTENANCE f where f.FLIGHTID="+flt_pk;
					list.add(delSql5);
					
					//在删除航班计划前记录一条删除的记录，该表在f_flight_schedule的基础上增加了deltime删除时间这个字段.
					String insertDelRecordSql="insert into f_flight_schedule_del_record(id, flt_pk, flt_date, iata_c, co_seq" 
							+",flightno, flt_id, flt_seq, flt_task, acnum, acmodel,dep_apt, arr_apt, ac_stop" 
							+", ac_stop_arr, ac_stop_move, ac_stop_arr_move, std, sta, etd, eta, ctd, cta, off_time" 
							+", on_time, out_time, in_time, memo, flightdesc, ac_state, workforce_state, flttype_dep" 
							+",flttype_arr, isconfirmed, islockedin, confirmedtime, hashvalue, pre_flightid, next_flightid, next_flightno" 
							+",unusaulflt_rptid, isdelay, delay_code, delay_time, cancel_flag, cancel_type, cancel_time, cancel_reason" 
							+",cancel_src, ac_owner, ac_type_old, ac_type_market, vip, vip_name, plan_flag, alt_apt1, alt_apt2, release_user" 
							+", maintain_user, duty_user, handover_user, guardian_user, comments, updatetime, updateuser, datasource, deltime)" 
							
							+" select s_f_flight_schedule_del_record.nextval,flt_pk, flt_date, iata_c, co_seq"
							+",flightno, flt_id, flt_seq, flt_task, acnum, acmodel,dep_apt, arr_apt, ac_stop" 
							+", ac_stop_arr, ac_stop_move, ac_stop_arr_move, std, sta, etd, eta, ctd, cta, off_time"
							+", on_time, out_time, in_time, memo, flightdesc, ac_state, workforce_state, flttype_dep" 
							+",flttype_arr, isconfirmed, islockedin, confirmedtime, hashvalue, pre_flightid, next_flightid, next_flightno" 
							+",unusaulflt_rptid, isdelay, delay_code, delay_time, cancel_flag, cancel_type, cancel_time, cancel_reason" 
							+",cancel_src, ac_owner, ac_type_old, ac_type_market, vip, vip_name, plan_flag, alt_apt1, alt_apt2, release_user" 
							+", maintain_user, duty_user, handover_user, guardian_user, comments, updatetime, updateuser, datasource,sysdate"
							
							+" from f_flight_schedule  where flt_pk="+flt_pk;
				
					list.add(insertDelRecordSql);
				}
				
//				for (int i = 0; i < vos.length; i++) {
//					// 航班状态
//					HashVO vo = vos[i];
//					Long flt_pk=vo.getLongValue("FLT_PK");
//					
//				}
//				
//				
//				for (int i = 0; i < vos.length; i++) {
//					//航班车辆需求
//					HashVO vo = vos[i];
//					Long flt_pk=vo.getLongValue("FLT_PK");
//				}
//				
//				
//				for (int i = 0; i < vos.length; i++) {
//					// 航班变动记录
//					HashVO vo = vos[i];
//					Long flt_pk=vo.getLongValue("FLT_PK");
//					
//				}
//				
//				for (int i = 0; i < vos.length; i++) {
//					// 维修工作;
//					HashVO vo = vos[i];
//					Long flt_pk=vo.getLongValue("FLT_PK");
//					
//				}
				dmo.executeBatchByDS(ApmsConst.DS_APMS, list);
				for (int i = 0; i < vos.length; i++) {
					// 对查询到的数据进行处理
					HashVO vo = vos[i];
					Long flt_pk=vo.getLongValue("FLT_PK");
					String delSql="delete from f_flight_schedule f where f.flt_pk="+flt_pk;
					flt_pkArrPK[i]=delSql;
				}
				dmo.executeBatchByDS(ApmsConst.DS_APMS, flt_pkArrPK);
				dmo.commit(ApmsConst.DS_APMS);
				mainThread.logTaskRun("本次任务删除了"+vos.length+"条取消的航班信息.");
			}else{
				mainThread.logTaskRun("没有要删除的取消航班信息");
			}
			
		} catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			mainThread.logTaskRun("OMIS删除航班数据同步执行异常。" + e.getMessage());
		} finally {
			// 释放数据库连接
			dmo.releaseContext(ApmsConst.DS_APMS);
			dmo.releaseContext(ApmsConst.DS_OMIS);
		}
		mainThread.logTaskRun("[OMIS删除航班数据同步]任务执行完成！");

	}

}