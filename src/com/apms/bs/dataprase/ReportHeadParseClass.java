package com.apms.bs.dataprase;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.vo.ApmsVarConst;

public abstract class ReportHeadParseClass {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	protected String msgno;//消息编号
	protected String acmodel;//机型
	protected String rptno; //报文编号
	
	/**
	 * 报文表头数据解析
	 * @param acarsVo
	 * @param msgno
	 * @param content
	 * @param trans_time
	 * @return
	 * @throws Exception
	 */
	public abstract AcarsHeadVo parseHeadData(HashVO acarsVo,String msgno,String content,Date trans_time,Date trans_time_full) throws Exception;
	
	
	public void setBasicInfo(String msgno,String rptno,String acmodel){
		this.msgno = msgno;
		this.rptno = rptno;
		this.acmodel = acmodel;
	}
	
	/**
	 * 插入报文表头数据到A_DFD_HEAD表
	 * @param msgno
	 * @param head
	 * @throws Exception
	 */
	public void insertHeadData(String msgno, AcarsHeadVo head) throws Exception{
		
		CommDMO dmo = new CommDMO();
		String insertSql = "INSERT INTO A_DFD_HEAD(ID,MSG_NO,RPTNO,ACNUM,ACMODEL,DATE_UTC,FLY_FROM,FLY_TO,FLT,PH,CNT,CODE,"
			+"BLEED_STATUS,APU,TAT,ALT,CAS,MN,GW,CG,DMU,STATUS,PRV,TIEBACK,MOD,AP1,AP2,SYS,RECORDTIME)"
			+" VALUES(S_A_DFD_HEAD.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,msgno,rptno
				,head.getAcid(), acmodel ,head.getDateUtc(),head.getFrom(),head.getTo(),head.getFlt()
				,head.getPh(),head.getCnt(),head.getCode(),head.getBleed_status(),head.getApu()
				,head.getTat(),head.getAlt(),head.getCas(),head.getMn(),head.getGw(),head.getCg(),head.getDmu()
				,ApmsVarConst.DFD_COMPUTESTATUS_INIT,head.getPrv(),head.getTiebck(),head.getMod(),head.getAp1(),head.getAp2(),head.getSys());
		
	}
	


	public Logger getLogger() {
		return logger;
	}


	public void setLogger(Logger logger) {
		this.logger = logger;
	}


	public String getMsgno() {
		return msgno;
	}


	public void setMsgno(String msgno) {
		this.msgno = msgno;
	}


	public String getAcmodel() {
		return acmodel;
	}


	public void setAcmodel(String acmodel) {
		this.acmodel = acmodel;
	}


	public String getRptno() {
		return rptno;
	}


	public void setRptno(String rptno) {
		this.rptno = rptno;
	}

}
