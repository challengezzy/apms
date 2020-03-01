package com.apms.bs.dataprase;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;

/**
 *报文数据预解析处理
 *@date Oct 16, 2014
 **/
public class DataPreParseService {
	
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	public void dfdDataExtract() throws Exception{
		try{
			logger.info("同步DFD报文数据开始...");
			String maxMsgNo = getMaxDfdMsgno();
			
			StringBuilder querySb = new StringBuilder("SELECT * FROM (");
			querySb.append("select CASE WHEN substr(TEL_CONTENT,instr(TEL_CONTENT,'-  ')+3,3)='.1/' ");
			//处理CFD报文中出现.1/的情况
			querySb.append(" THEN substr(TEL_CONTENT,instr(TEL_CONTENT,'.1/')+3,3) ");
			querySb.append("WHEN substr(TEL_CONTENT,instr(TEL_CONTENT,'/REP')+1,3)='REP' ");//新报文，编码在REP后20170408
			querySb.append("  THEN 'A'||substr(TEL_CONTENT,instr(TEL_CONTENT,'/REP')+5,2)");
			querySb.append("ELSE substr(TEL_CONTENT,instr(TEL_CONTENT,'-  ')+3,3) END RPTNO ");
			
			querySb.append(" ,ID,MSG_NO,TEL_CONTENT,AC_ID");
			querySb.append(" ,(SELECT M.MODELSERIES FROM B_AIRCRAFT AC, B_AIRCRAFT_MODEL M WHERE T.AC_ID = AC.AIRCRAFTSN AND M.ID = AC.ACMODELID) MODELSERIES");
			querySb.append(" ,(SELECT M.MODELCODE FROM B_AIRCRAFT AC, B_AIRCRAFT_MODEL M WHERE T.AC_ID = AC.AIRCRAFTSN AND M.ID = AC.ACMODELID) MODELCODE ");
			querySb.append(" ,PREFIX,TRANS_TIME,RECORD_TIME,ERRINT");
			querySb.append(" FROM A_ACARS_TELEGRAPH T WHERE T.PREFIX='DFD' " );
			querySb.append(" AND MSG_NO > " +maxMsgNo + " ORDER BY MSG_NO " );
			querySb.append(" ) WHERE ROWNUM < 20000 "); //每次最多一万条
			
			String insertSql = "INSERT INTO A_ACARS_TELEGRAPH_DFD(RPTNO,ID,MSG_NO"
					+" ,TEL_CONTENT,AC_ID,MODELSERIES,MODELCODE,PREFIX,TRANS_TIME,RECORD_TIME,ERRINT )"
					+" VALUES(?,?,?,?,?,?,?,?,?,?,?)"; //11个?
			
			//批量数据导入
			dmo.executeImportByDS(ApmsConst.DS_APMS, querySb.toString(), getFromCols(11), ApmsConst.DS_APMS, insertSql, 1000);
			
			logger.info("同步DFD报文数据结束。");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("DFD报文数据抽取异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void cfdDataExtract() throws Exception{
		try{
			logger.info("同步CFD报文数据开始...");
			String maxMsgNo = getMaxCfdMsgno();
			
			StringBuilder querySb = new StringBuilder("SELECT * FROM (");
			querySb.append("select CASE WHEN substr(TEL_CONTENT,instr(TEL_CONTENT,'-  ')+3,3)='.1/' ");
			//处理CFD报文中出现.1/的情况
			querySb.append(" THEN substr(TEL_CONTENT,instr(TEL_CONTENT,'.1/')+3,3) ELSE substr(TEL_CONTENT,instr(TEL_CONTENT,'-  ')+3,3) END RPTNO");
			querySb.append(" ,ID,MSG_NO,TEL_CONTENT,AC_ID,FLT_ID");
			querySb.append(" ,(SELECT M.MODELSERIES FROM B_AIRCRAFT AC, B_AIRCRAFT_MODEL M WHERE T.AC_ID = AC.AIRCRAFTSN AND M.ID = AC.ACMODELID) MODELSERIES");
			querySb.append(" ,(SELECT M.MODELCODE FROM B_AIRCRAFT AC, B_AIRCRAFT_MODEL M WHERE T.AC_ID = AC.AIRCRAFTSN AND M.ID = AC.ACMODELID) MODELCODE ");
			querySb.append(" ,PREFIX,TRANS_TIME,RECORD_TIME,ERRINT");
			querySb.append(" FROM A_ACARS_TELEGRAPH T WHERE T.PREFIX='CFD' " );
			querySb.append(" AND MSG_NO > " +maxMsgNo + " ORDER BY MSG_NO " );
			querySb.append(" ) WHERE ROWNUM < 20000 "); //每次最多一万条
			
			String insertSql = "INSERT INTO A_ACARS_TELEGRAPH_CFD(RPTNO,ID,MSG_NO"
					+" ,TEL_CONTENT,AC_ID,FLT_ID,MODELSERIES,MODELCODE,PREFIX,TRANS_TIME,RECORD_TIME,ERRINT )"
					+" VALUES(?,?,?,?,?,?,?,?,?,?,?,?)"; //12个?
			
			//批量数据导入
			dmo.executeImportByDS(ApmsConst.DS_APMS, querySb.toString(), getFromCols(12), ApmsConst.DS_APMS, insertSql, 1000);
			
			logger.info("同步CFD报文数据结束。");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("CFD报文数据抽取异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 查询DFD数据表中最大msg_no
	 * @return
	 * @throws Exception
	 */
	public String getMaxDfdMsgno() throws Exception{
		String querySql = "SELECT MAX(MSG_NO) MAXMSGNO FROM A_ACARS_TELEGRAPH_DFD";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);
		
		String msgno =  vos[0].getStringValue("maxmsgno");
		if(msgno == null || msgno.length() < 1){
			msgno = "0";
		}
		
		logger.debug("当前报文最大DFD消息编号: " + msgno);
		
		return msgno;		
	}
	
	/**
	 * 查询CFD数据表中最大msg_no
	 * @return
	 * @throws Exception
	 */
	public String getMaxCfdMsgno() throws Exception{
		String querySql = "SELECT MAX(MSG_NO) MAXMSGNO FROM A_ACARS_TELEGRAPH_CFD";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);
		
		String msgno =  vos[0].getStringValue("maxmsgno");
		if(msgno == null || msgno.length() < 1){
			msgno = "0";
		}
		
		logger.debug("当前报文最大CFD消息编号: " + msgno);
		
		return msgno;		
	}
	
	public int[] getFromCols(int length){
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i+1;
		
		return fromcols;
	}
	
}
