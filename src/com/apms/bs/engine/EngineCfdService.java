package com.apms.bs.engine;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.common.HashVoUtil;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

public class EngineCfdService {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	private CommDMO dmo = new CommDMO();

	public List<Map<String, Object>> getApuWarnList(String begin_date, String end_date, String acnum) throws Exception {
		String sql = "SELECT T.MSG_NO,T.ATANO_MAJOR||T.ATANO_MINOR||T.ATANO_SUB atano,'W' TYPE" ;
		sql += " ,T.ATANO_MAJOR YVAL ,T.WARN_CONTENT CONTENT,H.ACNUM,T.REPORTTIME UTCDATE";
		sql += " FROM A_CFD_WARNING T,A_CFD_HEAD H WHERE H.MSG_NO=T.MSG_NO AND ATANO_MAJOR ='49' and h.acnum = ? ";
		sql += " AND H.HEADTIME > ? AND H.HEADTIME < ? ";

		String dfStr = "yyyy-MM-dd HH:mm:ss";
		Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
		Date endDt = DateUtil.StringToDate(end_date, dfStr);
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, acnum, beginDt, endDt);
		List<Map<String, Object>> warnList = HashVoUtil.hashVosToMapList(vos);

		return warnList;
	}
	
	public List<Map<String, Object>> getApuFaultList(String begin_date, String end_date, String acnum) throws Exception {
		String sql = "SELECT T.MSG_NO,T.ATANO_MAJOR||T.ATANO_MINOR||T.ATANO_SUB atano,'F' TYPE" + " ,T.ATANO_MAJOR YVAL,T.FAULT_CONTENT CONTENT,H.ACNUM,T.REPORTTIME UTCDATE";
		sql += " FROM A_CFD_FAULT T,A_CFD_HEAD H WHERE H.MSG_NO=T.MSG_NO AND ATANO_MAJOR = '49' and h.acnum = ? ";
		sql += " AND H.HEADTIME > ? AND H.HEADTIME < ? ";

		String dfStr = "yyyy-MM-dd HH:mm:ss";
		Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
		Date endDt = DateUtil.StringToDate(end_date, dfStr);
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, acnum, beginDt, endDt);
		List<Map<String, Object>> warnList = HashVoUtil.hashVosToMapList(vos);

		return warnList;
	}
	
	public List<Map<String, Object>> getApuMWork(String begin_date, String end_date, String acnum) throws Exception {
		String sql = "SELECT W.ACNO ACNUM, W.WORKORDERSN, W.PLANSN,";
		sql += " 2 YVAL,W.OPDY UTCDATE,W.CONTEXTCN";
		sql += " FROM O_APU_REPAIR_LOG R,AMS_WORKORDERPLANE W";
		sql += " WHERE R.WORKORDERSN = W.WORKORDERSN AND W.EFFECT =1 AND W.ACNO=? AND W.OPDY >? AND W.OPDY <? ";

		String dfStr = "yyyy-MM-dd HH:mm:ss";
		Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
		Date endDt = DateUtil.StringToDate(end_date, dfStr);
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, acnum, beginDt, endDt);
		List<Map<String, Object>> warnList = HashVoUtil.hashVosToMapList(vos);

		return warnList;
	}

	public List<Map<String, Object>> getEngWarnList(String begin_date, String end_date, String acnum) throws Exception {
		String sql = "SELECT T.MSG_NO,T.ATANO_MAJOR||T.ATANO_MINOR||T.ATANO_SUB atano,'W' TYPE" + " ,T.ATANO_MAJOR YVAL ,T.WARN_CONTENT CONTENT,H.ACNUM,T.REPORTTIME UTCDATE";
		sql += " FROM A_CFD_WARNING T,A_CFD_HEAD H WHERE H.MSG_NO=T.MSG_NO AND ATANO_MAJOR >= '70' and h.acnum = ? ";
		sql += " AND H.HEADTIME > ? AND H.HEADTIME < ? ";

		String dfStr = "yyyy-MM-dd HH:mm:ss";
		Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
		Date endDt = DateUtil.StringToDate(end_date, dfStr);
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, acnum, beginDt, endDt);
		List<Map<String, Object>> warnList = HashVoUtil.hashVosToMapList(vos);

		return warnList;
	}

	public List<Map<String, Object>> getEngFaultList(String begin_date, String end_date, String acnum) throws Exception {
		String sql = "SELECT T.MSG_NO,T.ATANO_MAJOR||T.ATANO_MINOR||T.ATANO_SUB atano,'F' TYPE" + " ,T.ATANO_MAJOR YVAL,T.FAULT_CONTENT CONTENT,H.ACNUM,T.REPORTTIME UTCDATE";
		sql += " FROM A_CFD_FAULT T,A_CFD_HEAD H WHERE H.MSG_NO=T.MSG_NO AND ATANO_MAJOR >= '70' and h.acnum = ? ";
		sql += " AND H.HEADTIME > ? AND H.HEADTIME < ? ";

		String dfStr = "yyyy-MM-dd HH:mm:ss";
		Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
		Date endDt = DateUtil.StringToDate(end_date, dfStr);
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, acnum, beginDt, endDt);
		List<Map<String, Object>> warnList = HashVoUtil.hashVosToMapList(vos);

		return warnList;
	}

	public List<Map<String, Object>> getEngMWork(String begin_date, String end_date, String acnum) throws Exception {
		String sql = "SELECT W.ACNO ACNUM, R.WORKORDERSN, R.PLANSN, R.COMMENTS,MARGIN_CHG,FF_CHG,EGT_CHG,N1_CHG,N1VIB_CHG,";
		sql += " (SELECT T.VALUECN FROM B_DICTIONARY T WHERE T.CLASSNAME='ENGWORKORDER' AND T.ATTRIBUTENAME='MTTYPE' AND T.VALUE=R.MTTYPE) MTTYPE,";
		sql += " 2 YVAL,W.OPDY UTCDATE,W.CONTEXTCN";
		sql += " FROM L_ENG_WORKORDER_REL R,AMS_WORKORDERPLANE W";
		sql += " WHERE W.WORKORDERSN = R.WORKORDERSN AND W.ACNO=? AND W.OPDY >? AND OPDY <? ";

		String dfStr = "yyyy-MM-dd HH:mm:ss";
		Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
		Date endDt = DateUtil.StringToDate(end_date, dfStr);
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, acnum, beginDt, endDt);
		List<Map<String, Object>> warnList = HashVoUtil.hashVosToMapList(vos);

		return warnList;
	}

}
