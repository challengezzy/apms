package com.apms.bs.datatask;

import org.dom4j.Element;
import smartx.framework.common.bs.CommDMO;
import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;
import com.apms.ApmsConst;
import com.apms.bs.intf.omis.DDInfoExtractService;

/**
 * 向Acars新表抽取数据
 * 
 * @date Nov 28, 2011
 **/
public class DTAcarsDataFromTempToNowTableDataTask implements DataTaskExecuteIFC {

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
		mainThread.logTaskRun("开始执行[向Acars新表抽取数据]任务....");
		try {
			String columnStr=" id, msg_no, tel_content, rptno, prefix, ac_id, trans_time, record_time, errint, errmessage, parsetime, flt_id,"
				+" nmdps_code, rgs_code, folder_no, address_source, address_destination, tel_terminal, tel_header, receive_send_flag, flt_pk," 
				+" iata_c, msg_seq_no, dep_apt, arr_apt, sendtele_date, action, action_time, status, latitude, longitude, altitude, flying_time,";
			String columnAddedStr="(select am.modelcode from  b_aircraft air,b_aircraft_model am where air.aircraftsn=a.ac_id and air.acmodelid=am.id) modelcode,"
				+" (select am.modelseries from  b_aircraft air,b_aircraft_model am where air.aircraftsn=a.ac_id and air.acmodelid=am.id) modelseries,"
				+" null FDIMUVERSION,0 PREPROCESSINT";
			String qrySql="select"+columnStr+columnAddedStr+" from a_acars_telegraph_temp a";
			String insertSql="insert into a_acars_telegraph("+columnStr+"modelcode,modelseries,FDIMUVERSION,PREPROCESSINT"+") values("+"?,?,?,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,?,?,?,"+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			dmo.executeImportByDS(ApmsConst.DS_APMS, qrySql, getFromCols(37), ApmsConst.DS_APMS, insertSql, 4000);
		} catch (Exception e) {
			dmo.rollback(ApmsConst.DS_APMS);
			mainThread.logTaskRun("[向Acars新表抽取数据]任务执行异常。" + e.getMessage());
		} finally {

			// 释放数据库连接
			dmo.releaseContext(ApmsConst.DS_APMS);
			dmo.releaseContext(ApmsConst.DS_OMIS);
		}
		mainThread.logTaskRun("[向Acars新表抽取数据]任务执行完成！");

	}
	public int[] getFromCols(int length){
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i+1;
		
		return fromcols;
	}

}