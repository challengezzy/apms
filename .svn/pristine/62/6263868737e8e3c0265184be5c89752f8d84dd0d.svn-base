package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.vo.ApmsVarConst;

/**
 * V2500发动机,VSV 47报文
 * @author jerry
 * @date May 25, 2016
 */
public class A47DataParseImpl_A320 extends ReportContentParseClass {

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno,String content, String trans_time) throws Exception{
		praseData(acarsVo,msgno,content,trans_time);		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		return res;
	}

	private void praseData(HashVO acarsVo,String msgno,String content,String transdate) throws Exception{
		
		//解析出note内容
		int noteIdx = content.indexOf("NOTE:");
		int oxyrecIdx = content.indexOf("OXYGEN PRESSURE RECORD");
		
		if(noteIdx <0 || oxyrecIdx <0){
			throw new Exception("报文格式错误 ，内容不包含[NOTE:]或 [OXYGEN PRESSURE RECORD]");
		}
		
		String tempstr = content.substring(noteIdx+5, oxyrecIdx);
		
		int tipIdx = tempstr.indexOf("PLEASE CHECK THE");
		//去掉提醒的信息
		if(tipIdx > -1){
			tempstr = tempstr.substring(0,tipIdx);
		}
		
		String note = tempstr.replaceAll("[\n\r]", " ");
		//将对象数据持久化 分别插入  A_DFD_A49IAEV25_LIST
		insertToTable(acarsVo, msgno, note);
	}
	
	
	public void insertToTable(HashVO acarsVo,String msgno, String note) throws Exception{
		
		CommDMO dmo = new CommDMO();
		String insertSql1 = "insert into A_DFD_A47_LIST(ID,MSG_NO,ACNUM,RPTDATE,CODE,NOTE ,UPDATETIME)"
			+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,? ,sysdate)";
		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),note);
		
	}
}
