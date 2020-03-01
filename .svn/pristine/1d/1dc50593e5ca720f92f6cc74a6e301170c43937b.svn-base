package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA05Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a04a05.AcarsEC_EEVo_A04A05_A320_CFM;
import com.apms.bs.dataprase.vo.a04a05.AcarsN1N2Vo_A05_A320_CFM;
import com.apms.bs.dataprase.vo.a04a05.AcarsSTVXVo_A05_A320_CFM;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A05DataParseImpl_A320_CFM extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA05Vo_A320 a05vo = praseA05Data(content,trans_time);
		insertA05(msgno, a05vo);
		
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
	public AcarsDfdA05Vo_A320 praseA05Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA05Vo_A320 a05vo = new AcarsDfdA05Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		//logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){
				a05vo.setEc_c(new  AcarsEC_EEVo_A04A05_A320_CFM(line));
			}else if(line.startsWith("EE")){
				a05vo.setEe_c(new  AcarsEC_EEVo_A04A05_A320_CFM(line));
			}else if(line.startsWith("N1")){
				a05vo.setN1_c(new  AcarsN1N2Vo_A05_A320_CFM(line));
			}else if(line.startsWith("N2")){
				a05vo.setN2_c(new  AcarsN1N2Vo_A05_A320_CFM(line));
			}else if(line.startsWith("S1")){
				a05vo.setS1_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T1")){
				a05vo.setT1_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("S2")){
				a05vo.setS2_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T2")){
				a05vo.setT2_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("S3")){
				a05vo.setS3_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T3")){
				a05vo.setT3_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("S4")){
				a05vo.setS4_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T4")){
				a05vo.setT4_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("S5")){
				a05vo.setS5_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T5")){
				a05vo.setT5_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("S6")){
				a05vo.setS6_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T6")){
				a05vo.setT6_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("S7")){
				a05vo.setS7_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T7")){
				a05vo.setT7_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("S8")){
				a05vo.setS8_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T8")){
				a05vo.setT8_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("S9")){
				a05vo.setS9_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T9")){
				a05vo.setT9_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("S0")){
				a05vo.setS0_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("T0")){
				a05vo.setT0_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("V1")){
				a05vo.setV1_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}else if(line.startsWith("X2")){
				a05vo.setX1_c(new  AcarsSTVXVo_A05_A320_CFM(line));
			}
			
		}
		

		
		return a05vo;
	}
	
	public void insertA05(String msgno, AcarsDfdA05Vo_A320 a05vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a05_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,"
				+ "ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,"
				+ "ESN_2,EHRS_2,ERT_1,ECYC_2,AP_2," 
				+ "ECW2_N1,ECW5_N1,EVM_N1,"
				+ "ECW2_N2,ECW5_N2,EVM_N2," 
				+ "UPDATE_DATE)"  //22
				+ " values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "sysdate)";
		
//		String insertSql1 = "insert into a_dfd_a05_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,"
//			+ "N1,N1C,N2,EGT,FF,VN,VC,VH,VL)"  
//			+ " values(S_A_DFD_HEAD.nextval,"
//			+ "?,?,?,?,?,?,?,?,?,?,"
//			+ "?,?,?,?,?,?,?,?,?,?,"
//			+ "sysdate)";
		
		
		AcarsEC_EEVo_A04A05_A320_CFM ec = a05vo.getEc_c();
		AcarsEC_EEVo_A04A05_A320_CFM ee = a05vo.getEe_c();
		AcarsN1N2Vo_A05_A320_CFM n1 = a05vo.getN1_c();
		AcarsN1N2Vo_A05_A320_CFM n2 = a05vo.getN2_c();

//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),
				ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),
				n1.getEcw2(),n1.getEcw5(),n1.getEvm(),
				n2.getEcw2(),n2.getEcw5(),n2.getEvm());
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A01 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
