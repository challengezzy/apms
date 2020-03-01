package com.apms.bs.dataprase.impl;


import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA09Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a09.AcarsEC_EEVo_A09_A320_CFM;
import com.apms.bs.dataprase.vo.a09.AcarsN1Vo_A09_A320_CFM;
import com.apms.bs.dataprase.vo.a09.AcarsSTVo_A09_A320_CFM;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A09DataParseImpl_A320_CFM extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA09Vo_A320 a09vo = praseA09Data(content,trans_time);
		insertA09(msgno, a09vo);
		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	/**
	 * 报文解析
	 * @param graphStr 报文内容
	 * @param transdate 报文传送日期 YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	public AcarsDfdA09Vo_A320 praseA09Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA09Vo_A320 a09vo = new AcarsDfdA09Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){
				a09vo.setEc_c(new  AcarsEC_EEVo_A09_A320_CFM(line));
			}else if(line.startsWith("EE")){
				a09vo.setEe_c(new  AcarsEC_EEVo_A09_A320_CFM(line));
			}else if(line.startsWith("N1")){
				a09vo.setN1_c(new  AcarsN1Vo_A09_A320_CFM(line));
			}else if(line.startsWith("S1")){
				a09vo.setS1_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("T1")){
				a09vo.setT1_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("S2")){
				a09vo.setS2_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("T2")){
				a09vo.setT2_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("S3")){
				a09vo.setS3_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("T3")){
				a09vo.setT3_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("S4")){
				a09vo.setS4_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("T4")){
				a09vo.setT4_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("S5")){
				a09vo.setS5_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("T5")){
				a09vo.setT5_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("S6")){
				a09vo.setS6_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("T6")){
				a09vo.setT6_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("S7")){
				a09vo.setS7_c(new  AcarsSTVo_A09_A320_CFM(line));
			}else if(line.startsWith("T7")){
				a09vo.setT7_c(new  AcarsSTVo_A09_A320_CFM(line));
			}
			
		}
		

		
		return a09vo;
	}
	
	public void insertA09(String msgno, AcarsDfdA09Vo_A320 a09vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a01_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,"
				+ "ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,VSV_1,VBV_1,"
				+ "ESN_2,EHRS_2,ERT_2,ECYC_2,AP_2,VSV_2,VBV_2," 
				+ "E_N1,DIV_N1,REF_N1,K_N1,ECW1_N1,ECW2_N1,PSEL_N1,"
				+ "UPDATE_DATE)"  
				+ " values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,sysdate)";
		
		String insertSqlST = "insert into a_dfd_a01_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,"
			+ "N1,N1C,N2,EGT,FF,TN,"
			+ "UPDATE_DATE)"  
			+ " values(S_A_DFD_HEAD.nextval,"
			+ "?,?,?,?,?,?,?,?,?,?,"
			+ "sysdate)";
		
		
		
		AcarsEC_EEVo_A09_A320_CFM ec = a09vo.getEc_c();
		AcarsEC_EEVo_A09_A320_CFM ee = a09vo.getEe_c();
		AcarsN1Vo_A09_A320_CFM n1 = a09vo.getN1_c();


//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),ec.getVbv(),ec.getVbv(),
				ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),ee.getVsv(),ee.getVbv(),
				n1.getE(),n1.getDiv(),n1.getRef(),n1.getK(),n1.getEcw1(),n1.getEcw2(),n1.getPsel()
				);
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A09 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
