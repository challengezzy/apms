package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA49Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a49.AcarsECVo_A49_A320;
import com.apms.bs.dataprase.vo.a49.AcarsL1Vo_A49_A320;
import com.apms.bs.dataprase.vo.a49.AcarsL2Vo_A49_A320;
import com.apms.bs.dataprase.vo.a49.AcarsL3Vo_A49_A320;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * V2500发动机,N1震动报文
 * @author jerry
 * @date May 25, 2016
 */
public class A49DataParseImpl_A320 extends ReportContentParseClass {

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno,String content, String trans_time) throws Exception{
		praseData(acarsVo,msgno,content,trans_time);		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		return res;
	}

	protected void praseData(HashVO acarsVo,String msgno,String gStrA46,String transdate) throws Exception{
		AcarsDfdA49Vo_A320 a49vo = new AcarsDfdA49Vo_A320();
		String tmpStr = gStrA46.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		boolean isRep = false;
		//给a49vo 对象赋值开始
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
			if(line.startsWith("EC")){				
				a49vo.setEc(new AcarsECVo_A49_A320(line));
			}else if(line.startsWith("EE")){				
				a49vo.setEe(new AcarsECVo_A49_A320(line));
			}else if(line.startsWith("L1")){
				a49vo.setL1(new AcarsL1Vo_A49_A320(line, transdate));
				
			}else if(line.startsWith("L2")){
				a49vo.setL2(new AcarsL2Vo_A49_A320(line));
			}else if(line.startsWith("R1")){
				
				a49vo.setR1(new AcarsL1Vo_A49_A320(line, transdate));
			}else if(line.startsWith("R2")){
				
				a49vo.setR2(new AcarsL2Vo_A49_A320(line));
				
			}else if(line.startsWith("L3")){
				
				a49vo.setL3(new AcarsL3Vo_A49_A320(line, transdate,isRep));
			}else if(line.startsWith("R3")){
				
				a49vo.setR3(new AcarsL3Vo_A49_A320(line, transdate,isRep));
			}
		}
		
		//将对象数据持久化 分别插入  A_DFD_A49IAEV25_LIST
		insertToTable(acarsVo, msgno, a49vo);
	}
	
	
	public void insertToTable(HashVO acarsVo,String msgno, AcarsDfdA49Vo_A320 a49vo) throws Exception{
		
		CommDMO dmo = new CommDMO();
		String insertSql1 = "insert into A_DFD_A49IAEV25_LIST(ID,MSG_NO,ACNUM,RPTDATE,CODE," 
			+ "ESN_1,EHRS_1,ECYC_1,ESN_2,EHRS_2,ECYC_2,OPEN_1,CLOSE_1,TIME_1,EPR_1,N1_1,N2_1,FF_1,EGT_1,CTL_1," 
			+ "OPEN_2,CLOSE_2,TIME_2,EPR_2,N1_2,N2_2,FF_2,EGT_2,CTL_2" 
			+ ",EPR_L3,N1_L3,N2_L3,FF_L3,EGT_L3,TIME_L3 ,EPR_R3,N1_R3,N2_R3,FF_R3,EGT_R3,TIME_R3 ,UPDATETIME)"
			+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		
		
		AcarsECVo_A49_A320 ec = a49vo.getEc(); 
		AcarsECVo_A49_A320 ee = a49vo.getEe();
		
		AcarsL1Vo_A49_A320 l1 = a49vo.getL1();
		AcarsL1Vo_A49_A320 r1 = a49vo.getR1();
		
		AcarsL2Vo_A49_A320 l2 = a49vo.getL2();
		AcarsL2Vo_A49_A320 r2 = a49vo.getR2();
		
		AcarsL3Vo_A49_A320 l3 = a49vo.getL3();
		AcarsL3Vo_A49_A320 r3 = a49vo.getR3();
		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode()
				,ec.getEsn(),ec.getEhrs(),ec.getEcyc(), ee.getEsn(),ee.getEhrs(),ee.getEcyc()
				,l1.getOpen(),l1.getClose(),l1.getTime(),l2.getEpr(),l2.getN1(),l2.getN2(),l2.getFf(),l2.getEgt(),l2.getCtl()
				,r1.getOpen(),r1.getClose(),r1.getTime(),r2.getEpr(),r2.getN1(),r2.getN2(),r2.getFf(),r2.getEgt(),r2.getCtl()
				,l3.getEpr(),l3.getN1(),l3.getN2(),l3.getFf(),l3.getEgt(),l3.getTime()
				,r3.getEpr(),r3.getN1(),r3.getN2(),r3.getFf(),r3.getEgt(),r3.getTime()
				);
		
	}
}
