package com.apms.bs;

import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

public class AcarsReportFormService {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	
	public int dfdParseStateReset(String user, Map<String, String> qryObj) throws Exception{
		String errint =  qryObj.get("ERRINT");
		String rptno = qryObj.get("RPTNO");
		String acnum = qryObj.get("ACNUM");
		String modelcode = qryObj.get("MODELCODE");
		String begintime = qryObj.get("BEGINTIME");
		String endtime = qryObj.get("ENDTIME");
		
		StringBuilder uptSb = new StringBuilder();
		uptSb.append("UPDATE A_ACARS_TELEGRAPH_DFD T SET T.ERRINT = 0 WHERE ERRINT = " + errint );
		
		if(rptno != null && rptno.length() > 0){
			uptSb.append(" AND RPTNO='" + rptno + "'");
		}
		
		if(acnum != null && acnum.length() > 0){
			uptSb.append(" AND AC_ID='" + acnum + "'");
		}
		
		if(modelcode != null && modelcode.length() > 0){
			uptSb.append(" AND MODELCODE='" + modelcode + "'");
		}
		
		if(begintime != null && begintime.length() > 16){
			uptSb.append(" AND TRANS_TIME >= TO_DATE('"+begintime+"','YYYY-MM-DD HH24:MI:SS')");
		}
		
		if(endtime != null && endtime.length() > 16){
			uptSb.append(" AND TRANS_TIME <= TO_DATE('"+endtime+"','YYYY-MM-DD HH24:MI:SS')");
		}
		
		int rows = 0;
		try{
			rows = dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptSb.toString());
			dmo.commit(ApmsConst.DS_APMS);
			logger.debug("共有["+rows+"]条数据的解析状态修改为未解析!");
			
			return rows;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("执行报文解析状态更新失败! sql: " + uptSb.toString(), e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
}
