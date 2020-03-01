package com.apms.bs.acarsreport;
import java.util.Date;

//import java.sql.Date;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;
import com.apms.vo.ApmsVarConst;

/**
 * 报文重新解析、及重新计算类
 * @author jerry
 * @date Apr 26, 2013
 */
public class AcarsRptReparsingService {
	
	//提供方法 ,进行告警计算、告警消息生成
	Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	
	/**
	 * 清除报文解析数据
	 * @param rptno 报文编号
	 * @param acnum 飞机号
	 * @throws Exception
	 */
	public void clearParsedReport(String rptno,String acnum,Date beginDate, Date endDate) throws Exception{
		try{
			String engineType = null;	
			//1 重新解析的报文，需要重新计算，因此需要清除已计算数据
			clearComputedReport(rptno,acnum,engineType,beginDate, endDate);
			
			//2 删除报文内容已解析数据
			delRptContentData(rptno, acnum, beginDate, endDate);
			
			//3删除dfd_head数据
			delHead(rptno, acnum, beginDate, endDate);
			
			//4更新接口表解析状态
			updateTelegraphStatus(rptno, acnum, beginDate, endDate);
			
			dmo.commit(ApmsConst.DS_APMS);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 清除报文计算数据
	 * @param rptno
	 * @param acnum
	 * @throws Exception
	 */
	public void clearComputedReport(String rptno,String acnum,String engine_type,Date beginDate,Date endDate) throws Exception{
		//1 删除已计算列表数据
		
		delFieldComputed(rptno,acnum, beginDate, endDate);
		//执行报文数据计算
		if(ApmsVarConst.RPTNO_A13.equalsIgnoreCase(rptno) ){
			
			delA13Computed(rptno, acnum, beginDate, endDate);
			//R13和A13分开处理
			updateHeadUncompputed(ApmsVarConst.RPTNO_A13, acnum, beginDate, endDate);
			updateHeadUncompputed(ApmsVarConst.RPTNO_A14, acnum, beginDate, endDate);
			
		}else if( ApmsVarConst.RPTNO_R13.equalsIgnoreCase(rptno) ){
			
			delA13Computed(rptno, acnum, beginDate, endDate);
			updateHeadUncompputed(ApmsVarConst.RPTNO_R13, acnum, beginDate, endDate);
			updateHeadUncompputed(ApmsVarConst.RPTNO_R14, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A23.equalsIgnoreCase(rptno)){
			delA23Computed(rptno, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A25.equalsIgnoreCase(rptno)){
			delA25Computed(rptno, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A27.equalsIgnoreCase(rptno)){
			delA27Computed(rptno, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A28.equalsIgnoreCase(rptno)){
			delA28Computed(rptno, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A21.equalsIgnoreCase(rptno)){
			delA21Computed(rptno, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A24.equalsIgnoreCase(rptno)){
			delA24Computed(rptno, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A34.equalsIgnoreCase(rptno)){
			delA34Computed(rptno, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A38.equalsIgnoreCase(rptno)){
			delA38Computed(rptno, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A40.equalsIgnoreCase(rptno)){
			delA40Computed(rptno, acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A01.equalsIgnoreCase(rptno)){
			if(ApmsVarConst.RPTNO_A01CFM.equalsIgnoreCase(engine_type)){
				delA01CfmComputed(rptno, acnum, beginDate, endDate);
			}else if(ApmsVarConst.RPTNO_A01IAE.equalsIgnoreCase(engine_type)){
				delA01IaevComputed(rptno, acnum, beginDate, endDate);
			}
		}else if(ApmsVarConst.RPTNO_A04.equalsIgnoreCase(rptno)){
			if(ApmsVarConst.RPTNO_A04CFM.equalsIgnoreCase(engine_type)){
				delA04CfmComputed(rptno, acnum, beginDate, endDate);
			}else if(ApmsVarConst.RPTNO_A04IAE.equalsIgnoreCase(engine_type)){
				delA04IaevComputed(rptno, acnum, beginDate, endDate);
			}
		}else if(ApmsVarConst.RPTNO_A49.equalsIgnoreCase(rptno)){
			
			delA49IaevComputed(rptno, acnum, beginDate, endDate);
		}
			
		//1.2 删除控制字解析内容
		delAcwDecode(rptno,acnum, beginDate, endDate);
		
		//2 更新Head状态为未解析
		updateHeadUncompputed(rptno, acnum, beginDate, endDate);
		
		//3 删除生成的消息、及消息派发信息
		delAlarmMessage(rptno, acnum, beginDate, endDate); 
		
		dmo.commit(ApmsConst.DS_APMS);
	}
	
	public void delAlarmMessage(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE ALARM_MESSAGE WHERE RPTNO='"+rptno+"' ";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		
		//删除告警派发消息
		String sql2 = "DELETE ALARM_MSGSUBSCRIBER MS WHERE NOT EXISTS(SELECT 1 FROM ALARM_MESSAGE M WHERE M.ID=MS.MESSAGEID) ";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		
		logger.debug("删除已产生的告警消息成功,rptno["+rptno+"],acnum["+acnum+"]");
	}
	
	//删除已解析的报文内容数据
	public void delRptContentData(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		if(ApmsVarConst.RPTNO_A01.equalsIgnoreCase(rptno)){
			
			delA01Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A02.equalsIgnoreCase(rptno)){
			
			delA02Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A04.equalsIgnoreCase(rptno)){
			
			delA04Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A05.equalsIgnoreCase(rptno)){
			
			delA05Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A06.equalsIgnoreCase(rptno)){
			
			delA06Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A07.equalsIgnoreCase(rptno)){
			
			delA07Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A09.equalsIgnoreCase(rptno)){
			
			delA09Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A10.equalsIgnoreCase(rptno)){
			
			delA10Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A11.equalsIgnoreCase(rptno)){
			
			delA11Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A19.equalsIgnoreCase(rptno)){
			
			delA19Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A21.equalsIgnoreCase(rptno)){
			
			delA21Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A23.equalsIgnoreCase(rptno)){
			
			delA23Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A24.equalsIgnoreCase(rptno)){
			
			delA24Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A25.equalsIgnoreCase(rptno)){
			
			delA25Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A26.equalsIgnoreCase(rptno)){
			
			delA26Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A27.equalsIgnoreCase(rptno)){
			
			delA27Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A28.equalsIgnoreCase(rptno) || ApmsVarConst.RPTNO_A29.equalsIgnoreCase(rptno)
				|| ApmsVarConst.RPTNO_A30.equalsIgnoreCase(rptno) || ApmsVarConst.RPTNO_A31.equalsIgnoreCase(rptno)
				|| ApmsVarConst.RPTNO_A32.equalsIgnoreCase(rptno)
			){
			
			delA28Parsed(rptno,acnum, beginDate, endDate);
			
			delHead("A28", acnum, beginDate, endDate);
			delHead("A29", acnum, beginDate, endDate);
			delHead("A30", acnum, beginDate, endDate);
			delHead("A31", acnum, beginDate, endDate);
			delHead("A32", acnum, beginDate, endDate);
			
			updateTelegraphStatus("A28",acnum, beginDate, endDate);
			updateTelegraphStatus("A29",acnum, beginDate, endDate);
			updateTelegraphStatus("A30",acnum, beginDate, endDate);
			updateTelegraphStatus("A31",acnum, beginDate, endDate);
			updateTelegraphStatus("A32",acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A33.equalsIgnoreCase(rptno)){
			
			delA33Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A34.equalsIgnoreCase(rptno)){
			
			delA34Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A13.equalsIgnoreCase(rptno) || ApmsVarConst.RPTNO_A14.equalsIgnoreCase(rptno) ){
			delApuParsed(rptno, acnum, beginDate, endDate,"A");
			
		}else if( ApmsVarConst.RPTNO_R13.equalsIgnoreCase(rptno) || ApmsVarConst.RPTNO_R14.equalsIgnoreCase(rptno) ) {
			//删除APU解析报文
			delApuParsed(rptno, acnum, beginDate, endDate,"R");
			
		}else if(ApmsVarConst.RPTNO_A15.equalsIgnoreCase(rptno)|| ApmsVarConst.RPTNO_A16.equalsIgnoreCase(rptno)){
			delA1516Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A36.equalsIgnoreCase(rptno)){
			delA36Parsed(rptno,acnum, beginDate, endDate);
			
		}else if(ApmsVarConst.RPTNO_A38.equalsIgnoreCase(rptno)){
			delA38Parsed(rptno,acnum, beginDate, endDate);
		}else if(ApmsVarConst.RPTNO_A40.equalsIgnoreCase(rptno)){
			delA40Parsed(rptno,acnum, beginDate, endDate);
		}else if(ApmsVarConst.RPTNO_SMD.equalsIgnoreCase(rptno)){
			delSmdParsed(rptno,acnum, beginDate, endDate);
		}else if(ApmsVarConst.RPTNO_A46.equalsIgnoreCase(rptno)){
			delA46Parsed(rptno,acnum, beginDate, endDate);
		}
		else if(ApmsVarConst.RPTNO_A47.equalsIgnoreCase(rptno)){
			delA47Parsed(rptno,acnum, beginDate, endDate);
		}
		else if(ApmsVarConst.RPTNO_A49.equalsIgnoreCase(rptno)){
			delA49Parsed(rptno,acnum, beginDate, endDate);
		}
		else if(ApmsVarConst.RPTNO_A50.equalsIgnoreCase(rptno)){
			delA50Parsed(rptno,acnum, beginDate, endDate);
		}
		
	}
	
	//
	private void updateHeadUncompputed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "UPDATE A_DFD_HEAD H SET H.STATUS=0 WHERE H.RPTNO='"+rptno+"'";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		logger.debug("更新Head数据状态为[未计算],rptno["+rptno+"],acnum["+acnum+"],beginDate["+beginDate+"],endDate["+endDate+"]");
	}
	
	//删除Head表中数据
	private void delHead(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_HEAD H WHERE H.RPTNO='"+rptno+"'";
		if(acnum != null){
			sql += " AND H.ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND H.DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND H.DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		logger.debug("删除Head相关解析数据成功,rptno["+rptno+"],acnum["+acnum+"],beginDate["+beginDate+"],endDate["+endDate+"]");
	}
	
	//更新接口表数据状态为未解析
	private void updateTelegraphStatus(String rptno,String acnum, Date beginDate, Date endDate) throws Exception{
		String sql = "UPDATE A_ACARS_TELEGRAPH_DFD T SET T.ERRINT = 0 WHERE ERRINT !=0 AND RPTNO='"+rptno+"'";
		if(acnum != null){
			sql += " AND AC_ID = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND TRANS_TIME > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND TRANS_TIME < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("更新接口表数据解析状态成功,rptno["+rptno+"],acnum["+acnum+"],beginDate["+beginDate+"],endDate["+endDate+"]");
	}
	
	public void delApuParsed(String rptno,String acnum,Date beginDate, Date endDate,String prefix) throws Exception{
		//APU解析报文数据删除
		String delList13 = "DELETE A_DFD_A13_LIST L WHERE 1=1";
		String delList14 = "DELETE A_DFD_A14_LIST L WHERE 1=1";
		String delList14_data = "DELETE A_DFD_A14_LIST_DATA L WHERE 1=1";
		
	
		String delRunlog = "DELETE L_APU_RUNLOG WHERE 1=1";
		String delRunlogDay = "DELETE L_APU_RUNLOG_DAILY D WHERE 1=1";
		
		String acFilter = " AND ACNUM='"+acnum+"'";
		if(acnum != null){
			delList13 += acFilter;
			delList14 += acFilter;
			delList14_data += acFilter;
			delRunlog += acFilter;
			delRunlogDay += acFilter;
		}
		
		if(rptno != null){
			delList13 += " AND EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.MSG_NO=L.MSG_NO AND H.RPTNO='"+prefix+"13')";
			delList14 += " AND EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.MSG_NO=L.MSG_NO AND H.RPTNO='"+prefix+"14')";
			delList14_data += " AND EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.MSG_NO=L.MSG_NO AND H.RPTNO='"+prefix+"14')";;
			delRunlog += " AND RPTNO = ANY('"+prefix+"13','"+prefix+"14') ";
			delRunlogDay += " AND EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.ACNUM=D.ACNUM AND H.RPTNO='"+prefix+"13' ) ";
		}
		

		if(beginDate != null){
			String bDateFilter1 = " AND DATE_UTC> to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			String bDateFilter2 = " AND DATATIME> to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			String bDateFilter3 = " AND EXISTS (SELECT 1 FROM A_DFD_A14_LIST L WHERE L.MSG_NO=L.MSG_NO AND L.RPTDATE> to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD'))";
			String bDateFilter4 = " AND RPTDATE> to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			
			delList13 += bDateFilter1;
			delList14 += bDateFilter4;
			delList14_data += bDateFilter3;
			delRunlog += bDateFilter2;
			delRunlogDay += bDateFilter2;
		}
		
		if(endDate != null){
			String eDateFilter1 = " AND DATE_UTC< to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			String eDateFilter2 = " AND DATATIME< to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			String eDateFilter3 = " AND EXISTS (SELECT 1 FROM A_DFD_A14_LIST H WHERE L.MSG_NO=H.MSG_NO AND H.RPTDATE< to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD'))";
			String eDateFilter4 = " AND RPTDATE< to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			
			delList13 += eDateFilter1;
			delList14 += eDateFilter4;
			delList14_data += eDateFilter3;
			delRunlog += eDateFilter2;
			delRunlogDay += eDateFilter2;
		}
		
		ArrayList<String> sqlList = new ArrayList<String>();
		sqlList.add(delRunlog );
		sqlList.add(delRunlogDay );
		sqlList.add(delList13 );
		sqlList.add(delList14_data );
		sqlList.add(delList14 );
		
		dmo.executeBatchByDS(ApmsConst.DS_APMS, sqlList);
		
		if(prefix == "A"){
			//删除控制字解析内容
			delAcwDecode(ApmsVarConst.RPTNO_A13,acnum, beginDate, endDate);
			delAcwDecode(ApmsVarConst.RPTNO_A14,acnum, beginDate, endDate);
			
			
			//删除HEAD内容
			delHead(ApmsVarConst.RPTNO_A13,acnum,beginDate,endDate);
			delHead(ApmsVarConst.RPTNO_A14,acnum,beginDate,endDate);
			
			
			//更新接口状态
			updateTelegraphStatus(ApmsVarConst.RPTNO_A13,acnum, beginDate, endDate);
			updateTelegraphStatus(ApmsVarConst.RPTNO_A14,acnum, beginDate, endDate);
			
		}else if ("R".equals(prefix) ){
			delAcwDecode(ApmsVarConst.RPTNO_R13,acnum, beginDate, endDate);
			delAcwDecode(ApmsVarConst.RPTNO_R14,acnum, beginDate, endDate);
			
			delHead(ApmsVarConst.RPTNO_R13,acnum,beginDate,endDate);
			delHead(ApmsVarConst.RPTNO_R14,acnum,beginDate,endDate);
			
			updateTelegraphStatus(ApmsVarConst.RPTNO_R13,acnum, beginDate, endDate);
			updateTelegraphStatus(ApmsVarConst.RPTNO_R14,acnum, beginDate, endDate);
		}
		
		//刷新APU数据为最新的报文日志数据 zhangzy 20141107
		String updateSql = "update b_apu a set (a.totaltime,a.totalcycle) = ( select l.totaltime,l.totalcycle from l_apu_runlog l "
                + " where l.asn=a.apusn and l.datatime=(select max(datatime) from l_apu_runlog l2 where l2.asn=a.apusn ) )" ;
		if(acnum != null){
			updateSql += " WHERE 1=1 and exists (select 1 from b_aircraft ac where ac.aircraftsn='"+acnum+"' and ac.id=a.aircraftid) ";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql);
		
		logger.debug("删除APU相关解析数据成功,飞机号["+acnum+"]");
	}
	
	public void delAcwDecode(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_ACW_DECODE A WHERE A.RPTNO='"+rptno+"' ";

		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.MSG_NO=A.MSG_NO AND H.DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD'))";
		}
		if(endDate != null){
			sql += " AND EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.MSG_NO=A.MSG_NO AND H.DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD'))";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除ACWDECODE解析数据成功,rptno["+rptno+"],acnum["+acnum+"],beginDate["+beginDate+"],endDate["+endDate+"]");
	}
	
	public void delA1516Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		//氧气解析数据删除
		String sql = "DELETE A_DFD_A15A16_LIST WHERE RPTNO='"+rptno+"' ";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除重着落报文解析数据成功,acnum["+acnum+"],beginDate["+beginDate+"],endDate["+endDate+"]"); 
	}
	//删除A01报文内容
	public void delA01Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql1 = "DELETE A_DFD_A01CFM56_5B_LIST WHERE 1=1";
		String sql2 = "DELETE A_DFD_A01IAEV25_LIST WHERE 1=1";
		if(acnum != null){
			sql1 += " AND ACNUM = '"+acnum+"'";
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql1 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql1 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		logger.debug("删除A01号报文解析数据成功,acnum["+acnum+"]");
	}
	
	//删除A02报文内容
	public void delA02Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql1 = "DELETE A_DFD_A02CFM56_LIST WHERE 1=1";
		String sql2 = "DELETE A_DFD_A02IAEV25_LIST WHERE 1=1";
		if(acnum != null){
			sql1 += " AND ACNUM = '"+acnum+"'";
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql1 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql1 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		logger.debug("删除A02号报文解析数据成功,acnum["+acnum+"]");
	}
	
	//删除A04报文内容
	public void delA04Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql1 = "DELETE A_DFD_A04CFM56_5B_LIST WHERE 1=1";
		String sql2 = "DELETE A_DFD_A04IAEV25_LIST WHERE 1=1";
		if(acnum != null){
			sql1 += " AND ACNUM = '"+acnum+"'";
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql1 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql1 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		logger.debug("删除A04号报文解析数据成功,acnum["+acnum+"]");
	}
	
	//删除A05报文内容
	public void delA05Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		//String sql1 = "DELETE A_DFD_A05CFM56_LIST WHERE 1=1";
		String sql2 = "DELETE A_DFD_A05IAEV25_LIST WHERE 1=1";
		if(acnum != null){
			//sql1 += " AND ACNUM = '"+acnum+"'";
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			//sql1 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			//sql1 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		//dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		logger.debug("删除A05号报文解析数据成功,acnum["+acnum+"]");
	}
	
	//删除A06报文内容
	public void delA06Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql1 = "DELETE A_DFD_A06CFM56_LIST WHERE 1=1";
		String sql2 = "DELETE A_DFD_A06IAEV25_LIST WHERE 1=1";
		if(acnum != null){
			sql1 += " AND ACNUM = '"+acnum+"'";
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql1 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql1 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		logger.debug("删除A06号报文解析数据成功,acnum["+acnum+"]");
	}
	
	//删除A07报文内容
	public void delA07Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql1 = "DELETE A_DFD_A07CFM56_LIST WHERE 1=1";
		String sql2 = "DELETE A_DFD_A07IAVE25_LIST WHERE 1=1";
		if(acnum != null){
			sql1 += " AND ACNUM = '"+acnum+"'";
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql1 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql1 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		logger.debug("删除A07号报文解析数据成功,acnum["+acnum+"]");
	}
	
	//删除A09报文内容
	public void delA09Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		//String sql1 = "DELETE A_DFD_A09CFM56_LIST WHERE 1=1";
		String sql2 = "DELETE A_DFD_A09IAVE25_LIST WHERE 1=1";
		if(acnum != null){
			//sql1 += " AND ACNUM = '"+acnum+"'";
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			//sql1 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			//sql1 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		//dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		logger.debug("删除A09号报文解析数据成功,acnum["+acnum+"]");
	}
	
	//删除A10报文内容
	public void delA10Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql1 = "DELETE A_DFD_A10CFM56_LIST WHERE 1=1";
		String sql2 = "DELETE A_DFD_A10IAVE25_LIST WHERE 1=1";
		if(acnum != null){
			sql1 += " AND ACNUM = '"+acnum+"'";
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql1 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql1 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		logger.debug("删除A10号报文解析数据成功,acnum["+acnum+"]");
	}
	
	//删除A11报文内容
	public void delA11Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		//String sql1 = "DELETE A_DFD_A02CFM56_LIST WHERE 1=1";
		String sql2 = "DELETE A_DFD_A11IAVE25_LIST WHERE 1=1";
		if(acnum != null){
			//sql1 += " AND ACNUM = '"+acnum+"'";
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			//sql1 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			//sql1 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		//dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql1);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);

		logger.debug("删除A11号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA19Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		//A19解析报文数据删除
		String delList_a19 = "DELETE A_DFD_A19_LIST WHERE 1=1";
		String delList_en = "DELETE A_DFD_A19_LIST_EN_ROW D WHERE EXISTS (SELECT 1 FROM A_DFD_A19_LIST L WHERE L.MSG_NO=D.MSG_NO ";
		String delList_st = "DELETE A_DFD_A19_LIST_ST_ROW D WHERE EXISTS (SELECT 1 FROM A_DFD_A19_LIST L WHERE L.MSG_NO=D.MSG_NO ";
		String delList_v = "DELETE A_DFD_A19_LIST_V_ROW D WHERE EXISTS (SELECT 1 FROM A_DFD_A19_LIST L WHERE L.MSG_NO=D.MSG_NO ";
		String delList_x = "DELETE A_DFD_A19_LIST_X_ROW D WHERE EXISTS (SELECT 1 FROM A_DFD_A19_LIST L WHERE L.MSG_NO=D.MSG_NO ";
				
		if(acnum != null){
			delList_a19 += " AND ACNUM = '"+acnum+"'";
			delList_en += " AND ACNUM = '"+acnum+"'";
			delList_st += " AND ACNUM = '"+acnum+"'";
			delList_v += " AND ACNUM = '"+acnum+"'";
			delList_x += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			delList_a19 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			delList_en += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			delList_st += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			delList_v += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
			delList_x += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			delList_a19 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			delList_en += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			delList_st += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			delList_v += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
			delList_x += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		//子句结束
		delList_en += " )";
		delList_st += " )";
		delList_v += " )";
		delList_x += " )";
		
		ArrayList<String> sqlList = new ArrayList<String>();
		sqlList.add(delList_en );
		sqlList.add(delList_st );
		sqlList.add(delList_v );
		sqlList.add(delList_x );
		sqlList.add(delList_a19 );

		dmo.executeBatchByDS(ApmsConst.DS_APMS, sqlList);
						
		logger.debug("删除A19号文解析数据成功,acnum["+acnum+"]");
	}
	public void delA21Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{

		String sql = "DELETE A_DFD_A21_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A21报文解析数据成功,acnum["+acnum+"]");
	}
	public void delA23Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		//氧气解析数据删除
		String sql = "DELETE A_DFD_A23_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除氧气报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA24Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A24_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A24号报文解析数据成功,acnum["+acnum+"]");
	}
	public void delA25Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A25_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A25号报文解析数据成功,acnum["+acnum+"]");
	}
	public void delA26Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A26_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A26号报文解析数据成功,acnum["+acnum+"]");
	}
	public void delA27Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A27_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A27号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA28Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A2832_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		String delVibSql = "DELETE A_DFD_VIBRATIONRECORD V WHERE NOT EXISTS (SELECT 1 FROM A_DFD_A2832_LIST L WHERE L.MSG_NO=V.MSG_NO)"; 
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, delVibSql);
		
		logger.debug("删除A28号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA36Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A36_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A36号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA38Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A38_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A38号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA40Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A40_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		String delS2Sql = "DELETE A_DFD_A40_LIST_S2 V WHERE NOT EXISTS (SELECT 1 FROM A_DFD_A40_LIST L WHERE L.MSG_NO=V.MSG_NO)"; 
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, delS2Sql);
		
		logger.debug("删除A40号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA46Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE a_dfd_a46_list WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		String delS2Sql = "DELETE a_dfd_a46_list_data V WHERE NOT EXISTS (SELECT 1 FROM a_dfd_a46_list L WHERE L.MSG=V.MSG_NO)"; 
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, delS2Sql);
		
		logger.debug("删除A46号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA47Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A47_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A47号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA49Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A49IAEV25_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A49号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA50Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A50IAEV25_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A50号报文解析数据成功,acnum["+acnum+"]");
	}

	public void delSmdParsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_CFD_SMD WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除SMD号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA33Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A33_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		String delRecSql = "DELETE A_DFD_A33_VALUE_REC V WHERE NOT EXISTS (SELECT 1 FROM A_DFD_A33_LIST L WHERE L.MSG_NO=V.MSG_NO)"; 
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, delRecSql);
		
		logger.debug("删除A33号报文解析数据成功,acnum["+acnum+"]");
	}
	
	public void delA34Parsed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		
		String sql = "DELETE A_DFD_A34_LIST WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A34号报文解析数据成功,acnum["+acnum+"]");
	}

	public void delFieldComputed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_FIELD_COMPUTE C WHERE C.RPTNO='"+rptno+"' ";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND  EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.MSG_NO=C.MSG_NO AND H.DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD'))";
		}
		if(endDate != null){
			sql += " AND  EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.MSG_NO=C.MSG_NO AND H.DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD'))";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		String sql2 = "DELETE A_DFD_FIELD_NROLL C WHERE C.RPTNO='"+rptno+"' ";
		if(acnum != null){
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql2 += " AND  EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.MSG_NO=C.MSG_NO AND H.DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD'))";
		}
		if(endDate != null){
			sql2 += " AND  EXISTS (SELECT 1 FROM A_DFD_HEAD H WHERE H.MSG_NO=C.MSG_NO AND H.DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD'))";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);
		logger.debug("删除FIELD_COMPUTE计算数据成功,rptno["+rptno+"],acnum["+acnum+"]");
	}
	
	public void delA13Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		//A13计算后数据删除
		String sql = "DELETE A_DFD_A13_COMPUTE WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND UTCDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND UTCDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除APU计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA23Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A23_COMPUTE WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除氧气计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA25Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A25_COMPUTE WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除滑油A25计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA27Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A27_COMPUTE WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除滑油A27计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA28Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_VIBRATION_PEAK WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND DATE_UTC > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND DATE_UTC < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A28飞机抖动计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA21Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A21_COMPUTE WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		//不要忘记清除A_DFD_A21_COMPUTE_STAT的数据
		String sql2 = "DELETE A_DFD_A21_COMPUTE_STAT WHERE 1=1";
		if(acnum != null){
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);
		logger.debug("删除滑油A21计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA24Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A24_COMPUTE WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		//不要忘记清除A_DFD_A21_COMPUTE_STAT的数据
		String sql2 = "DELETE A_DFD_A24_COMPUTE_STAT WHERE 1=1";
		if(acnum != null){
			sql2 += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql2 += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql2 += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql2);
		logger.debug("删除A24计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA34Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE FROM A_DFD_FIELD_COMPUTE F WHERE F.RPTNO='A34'";
		if(acnum != null){
			sql += " AND F.ACNUM = '"+acnum+"'";
		}
		sql +="AND F.MSG_NO=ANY(SELECT MSG_NO FROM A_DFD_A34_LIST L WHERE 1=1";
		if(beginDate != null){
			sql += " AND L.DATE_UTC > TO_DATE('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND L.DATE_UTC < TO_DATE('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		
		sql +=")";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A34计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA38Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A38_COMPUTE WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A38计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA40Computed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A40_COMPUTE WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A40计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA01CfmComputed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A01CFM56_COMPUTE  WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A01Cfm计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA01IaevComputed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE a_dfd_a01iaev25_compute WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A01Iaev计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA04CfmComputed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A04CFM56_COMPUTE  WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A04Cfm计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA04IaevComputed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE a_dfd_a04iaev25_compute WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A04Iaev计算数据成功,acnum["+acnum+"]");
	}
	
	public void delA49IaevComputed(String rptno,String acnum,Date beginDate,Date endDate) throws Exception{
		String sql = "DELETE A_DFD_A49IAEV25_COMPUTE WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(beginDate != null){
			sql += " AND RPTDATE > to_date('"+DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD')";
		}
		if(endDate != null){
			sql += " AND RPTDATE < to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD')";
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		
		logger.debug("删除A49Iaev计算数据成功,acnum["+acnum+"]");
	}
	
}