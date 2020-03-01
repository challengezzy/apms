package com.apms.bs.intf.outer;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaServerEnvironment;

import com.apms.ApmsConst;
import com.apms.bs.sms.ShortMassageHwService;

public class IntfTeleGraphSynService {
	
private static int failNum = 0; //接口数据同步失败次数计数
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private String ds241 = "datasource_apms241";
	
	public void extractTeleGraph() throws Exception{
		try{
			logger.info("抽取接口报文数据开始...");
			
			//查询当前数据表中最大msg_no
			String maxMsgNo = getMaxMsgno();
			
			StringBuilder querySb = new StringBuilder();
			querySb.append("select RPTNO,PREFIX,MSG_NO,FOLDER_NO,ADDRESS_SOURCE,ADDRESS_DESTINATION,TEL_TERMINAL,TEL_HEADER,");
			querySb.append(" TEL_CONTENT,RECEIVE_SEND_FLAG,FLT_PK,AC_ID,IATA_C,FLT_ID,NMDPS_CODE,RGS_CODE,TRANS_TIME,MSG_SEQ_NO,DEP_APT,ARR_APT,SENDTELE_DATE,");
			querySb.append(" ACTION,ACTION_TIME,STATUS,LATITUDE,LONGITUDE,ALTITUDE,FLYING_TIME,sysdate,0");
			querySb.append("");
			querySb.append( " from a_acars_telegraph where msg_no > " +maxMsgNo);
			querySb.append( " ORDER BY MSG_NO");
				//+" from a_acars_telegraph where msg_no = 100416795";
			
			String insertSql = "INSERT INTO A_ACARS_TELEGRAPH(ID,RPTNO,PREFIX,MSG_NO,FOLDER_NO,ADDRESS_SOURCE,ADDRESS_DESTINATION,TEL_TERMINAL,TEL_HEADER, "
					+"TEL_CONTENT,RECEIVE_SEND_FLAG,FLT_PK,AC_ID,IATA_C,FLT_ID,NMDPS_CODE,RGS_CODE,TRANS_TIME,MSG_SEQ_NO,DEP_APT,ARR_APT,SENDTELE_DATE,"
					+"ACTION,ACTION_TIME,STATUS,LATITUDE,LONGITUDE,ALTITUDE,FLYING_TIME,RECORD_TIME,errint)"
					+" VALUES(S_A_ACARS_TELEGRAPH.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //30个?
			
			//批量数据导入
			dmo.executeImportByDS(ds241, querySb.toString(), getFromCols(30), ApmsConst.DS_APMS, insertSql, 1000);
			
			logger.info("抽取接口报文数据结束...");
			failNum = 0;
		}catch (Exception e) {
			logger.error("报文数据抽取异常！", e);
			
			failNum++;
			String pname = (String)NovaServerEnvironment.getInstance().get("PROJECT_NAME");
			String msg = pname +"执行Acars报文数据同步,连续失败"+failNum+"次,请查看任务日志";
			
			if( failNum >= 3){
				//数据任务失败，及时发短信告知维护人员
				ShortMassageHwService.getInstance().send( "18916752189", msg);//zzy
				ShortMassageHwService.getInstance().send( "18658176006", msg);//huanglei
			}
			throw e;
		}finally{
			dmo.releaseContext(ds241);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public int[] getFromCols(int length){
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i+1;
		
		return fromcols;
	}
	
	/**
	 * 查询当前数据表中最大msg_no
	 * @return
	 * @throws Exception
	 */
	public String getMaxMsgno() throws Exception{
		String querySql = "SELECT MAX(MSG_NO) MAXMSGNO FROM A_ACARS_TELEGRAPH";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);
		
		String msgno =  vos[0].getStringValue("maxmsgno");
		if(msgno == null || msgno.length() < 1)
			msgno = "0";
		
		logger.debug("当前报文最大消息编号: " + msgno);
		
		return msgno;		
	}

}
