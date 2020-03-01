package com.apms.bs.neuralnet;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

public class DiagnoseDataSerivce {

	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	/**
	 * 删除已诊断数据，为重新诊断准备
	 * @param baseOrgName
	 * @param acnum
	 * @param apumodel
	 * @param begintime
	 * @param endTime
	 */
	public int deleteApuDiagnoseData(String baseOrgName, String acnum, String apumodel, Date beginDate, Date endDate) throws Exception {
		int rows = 0;
		String sql = "DELETE FROM L_APU_DIAGNOSERESULT T WHERE 1=1";
		if(acnum != null){
			sql += " AND ACNUM = '"+acnum+"'";
		}
		if(apumodel != null){
			sql += " AND APUMODEL = '"+apumodel+"'";
		}
		
		if(baseOrgName != null){
			sql += " AND EXISTS (SELECT 1 FROM B_AIRCRAFT A,B_ORGANIZATION O " +
					" WHERE O.ID=A.BASEORGID AND A.AIRCRAFTSN=T.ACNUM AND O.NAME='"+baseOrgName+"')";
		}
		
		if(beginDate != null && endDate != null){
			sql += "AND EXISTS (SELECT 1 FROM A_DFD_A13_COMPUTE C WHERE C.MSG_NO=T.MSG_NO" +
				   " AND C.UTCDATE >= to_date('" + DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD') " +
				   	" AND C.UTCDATE<= to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD') )";
			
		}else if(beginDate != null){
			sql += "AND EXISTS (SELECT 1 FROM A_DFD_A13_COMPUTE C WHERE C.MSG_NO=T.MSG_NO" +
			   " AND C.UTCDATE >= to_date('" + DateUtil.getDateStr(beginDate)+"','YYYY-MM-DD') )";
			   
		}else if(endDate != null){
			sql += "AND EXISTS (SELECT 1 FROM A_DFD_A13_COMPUTE C WHERE C.MSG_NO=T.MSG_NO" +
			   	" AND C.UTCDATE<= to_date('"+DateUtil.getDateStr(endDate)+"','YYYY-MM-DD'))";
		}
		
		CommDMO dmo = new CommDMO();
		rows = dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
		dmo.commit(ApmsConst.DS_APMS);
		
		logger.debug("删除APU诊断数据成功，共删除["+rows+"]条。");
		
		return rows;
	}

}
