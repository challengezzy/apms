package com.apms.bs.intf.oldsys;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;

public class HistoryDataExtractService {
	
		private Logger logger = NovaLogger.getLogger(this.getClass());
		
		private CommDMO dmo = new CommDMO();
		
		public void extractTeleGraph() throws Exception{
			try{
				int recordNum = 0;//记录数
				logger.info("抽取接口报文数据开始...");
				
				//查询A23报文数据，28个字段
				String qrySql = "SELECT T.MSG_NO, T.FOLDER_NO, T.ADDRESS_SOURCE, T.ADDRESS_DESTINATION, "
			      +" T.TEL_TERMINAL, T.TEL_HEADER, T.TEL_CONTENT, T.RECEIVE_SEND_FLAG, "
			      +" T.FLT_PK, T.AC_ID, T.IATA_C, T.FLT_ID, T.NMDPS_CODE, T.RGS_CODE, "
			      +"  T.TRANS_TIME, T.MSG_SEQ_NO, T.DEP_APT, T.ARR_APT, T.SENDTELE_DATE, "
			      +" T.ACTION_TEL, T.ACTION_TIME, T.STATUS, T.LATITUDE, T.LONGITUDE, T.ALTITUDE, "
			      +"  T.FLYING_TIME, T.RECORD_TIME, 0 AS ERRINT"
			      +" FROM ACARS_TELEGRAPH T";
			      //+"  INNER JOIN ACARS_DFD_HEAD H ON T.MSG_NO = H.MSG_NO";
			      //+" WHERE (H.RPTNO = 'A23') AND (H.ACNUM IN ('B6032', 'B6023'))";
				
				String insertSql = "INSERT INTO A_ACARS_TELEGRAPH(ID,MSG_NO,FOLDER_NO,ADDRESS_SOURCE,ADDRESS_DESTINATION,TEL_TERMINAL,TEL_HEADER, "
						+"TEL_CONTENT,RECEIVE_SEND_FLAG,FLT_PK,AC_ID,IATA_C,FLT_ID,NMDPS_CODE,RGS_CODE,TRANS_TIME,MSG_SEQ_NO,DEP_APT,ARR_APT,SENDTELE_DATE,"
						+"ACTION,ACTION_TIME,STATUS,LATITUDE,LONGITUDE,ALTITUDE,FLYING_TIME,RECORD_TIME,errint)"
						+" VALUES(S_A_ACARS_TELEGRAPH.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //27个?
				
				//批量数据导入
				dmo.executeImportByDS(ApmsConst.DS_APMSOLD, qrySql, getFromCols(28), ApmsConst.DS_APMS, insertSql, 1000);
				
				logger.info("抽取接口报文数据结束...");
			}catch (Exception e) {
				logger.error("报文数据抽取异常！", e);
				throw e;
			}finally{
				dmo.releaseContext(ApmsConst.DS_APMSOLD);
				dmo.releaseContext(ApmsConst.DS_APMS);
			}
		}
		
		public int[] getFromCols(int length){
			int[] fromcols = new int[length];
			for (int i = 0; i < fromcols.length; i++)
				fromcols[i] = i+1;
			
			return fromcols;
		}
}
