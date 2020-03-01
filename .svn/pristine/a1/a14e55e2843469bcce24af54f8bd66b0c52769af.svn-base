package com.apms.bs.intf.omis;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaServerEnvironment;

import com.apms.ApmsConst;
import com.apms.bs.sms.ShortMassageHwService;

/**
 * 吉祥航空报文数据同步
 * @author zzy
 *
 */
public class JXTeleGraphExtractService {
	
	private static int failNum = 0; //接口数据同步失败次数计数
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	public void extractTeleGraph() throws Exception{
		try{
			logger.info("抽取接口报文数据开始...");
			
			//查询当前数据表中最大msg_no
			String maxMsgNo = getMaxMsgno();
			String acnos = getACNOs();
			
			
			StringBuilder querySb = new StringBuilder("select CASE WHEN substr(TEL_CONTENT,instr(TEL_CONTENT,'-  ')+3,3)='.1/' ");
			//处理CFD报文中出现.1/的情况
			querySb.append(" THEN substr(TEL_CONTENT,instr(TEL_CONTENT,'.1/')+3,3) ");
			querySb.append("WHEN substr(TEL_CONTENT,instr(TEL_CONTENT,'/REP')+1,3)='REP' ");//新报文，编码在REP后20170408
			querySb.append("  THEN 'A'||substr(TEL_CONTENT,instr(TEL_CONTENT,'/REP')+5,2)");
			querySb.append("ELSE substr(TEL_CONTENT,instr(TEL_CONTENT,'-  ')+3,3) END RPTNO,");
			querySb.append("PREFIX,MSG_NO,TEL_CONTENT,ACNUM,");
			querySb.append("MODELCODE,MODELSERIES,TRANS_TIME");
			querySb.append( "  ");
			querySb.append( " FROM intf_telegraph_dfd WHERE MSG_NO >" +maxMsgNo);
			querySb.append(" and ACNUM in " + acnos );
			querySb.append( " ORDER BY MSG_NO");
				//+" from a_acars_telegraph where msg_no = 100416795";
			
			String insertSql = "INSERT INTO A_ACARS_TELEGRAPH_DFD(ID,RPTNO,PREFIX,MSG_NO, "
					+"TEL_CONTENT,AC_ID,MODELCODE,MODELSERIES,TRANS_TIME,RECORD_TIME,errint)"
					+" VALUES(S_A_ACARS_TELEGRAPH.NEXTVAL,?,?,?,?,?,?,?,?,sysdate,0)"; //8个?
			
			//批量数据导入
			dmo.executeImportByDS(ApmsConst.DS_IN, querySb.toString(), getFromCols(8), ApmsConst.DS_APMS, insertSql, 1000);
			
			logger.info("抽取接口中间库报文数据结束...");
			failNum = 0;
		}catch (Exception e) {
			logger.error("报文数据抽取异常！", e);
			
			failNum++;
			String pname = (String)NovaServerEnvironment.getInstance().get("PROJECT_NAME");
			String msg = pname +"执行中间库Acars报文数据同步,连续失败"+failNum+"次,请查看任务日志";
			
			if( failNum >= 3){
				//数据任务失败，及时发短信告知维护人员
				ShortMassageHwService.getInstance().send( "18916752189", msg);//zzy
				ShortMassageHwService.getInstance().send( "18658176006", msg);//huanglei
			}
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_IN);
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
		String querySql = "SELECT MAX(MSG_NO) MAXMSGNO FROM A_ACARS_TELEGRAPH_DFD";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);
		
		String msgno =  vos[0].getStringValue("maxmsgno");
		if(msgno == null || msgno.length() < 1)
			msgno = "0";
		
		logger.debug("当前报文最大消息编号: " + msgno);
		
		return msgno;		
	}
	
	/**
	 * 获取当前所有启用的飞机编号
	 * @return
	 * @throws Exception
	 */
	public String getACNOs() throws Exception{
		String querySql = "SELECT AIRCRAFTSN FROM B_AIRCRAFT WHERE STATS=1 AND ISACARS=1 ";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);
		
		StringBuilder sb = new StringBuilder("('-1'");
		for(int i=0;i<vos.length;i++){
			sb.append(",'" +vos[i].getStringValue("AIRCRAFTSN")+ "'");
		}
		sb.append(")");
		
		return sb.toString();
	}

}
