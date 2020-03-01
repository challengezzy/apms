package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA09Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a09.AcarsEC_EEVo_A09_A320_IAE;
import com.apms.bs.dataprase.vo.a09.AcarsN1Vo_A09_A320_IAE;
import com.apms.bs.dataprase.vo.a09.AcarsSTVo_A09_A320_IAE;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A09DataParseImpl_A320_IAE extends ReportContentParseClass{
	
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
				a09vo.setEc_i(new  AcarsEC_EEVo_A09_A320_IAE(line));
			}else if(line.startsWith("EE")){
				a09vo.setEe_i(new  AcarsEC_EEVo_A09_A320_IAE(line));
			}else if(line.startsWith("N1")){
				a09vo.setN1_i(new  AcarsN1Vo_A09_A320_IAE(line));
			}else if(line.startsWith("S1")){
				a09vo.setS1_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("T1")){
				a09vo.setT1_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("S2")){
				a09vo.setS2_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("T2")){
				a09vo.setT2_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("S3")){
				a09vo.setS3_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("T3")){
				a09vo.setT3_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("S4")){
				a09vo.setS4_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("T4")){
				a09vo.setT4_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("S5")){
				a09vo.setS5_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("T5")){
				a09vo.setT5_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("S6")){
				a09vo.setS6_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("T6")){
				a09vo.setT6_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("S7")){
				a09vo.setS7_i(new  AcarsSTVo_A09_A320_IAE(line));
			}else if(line.startsWith("T7")){
				a09vo.setT7_i(new  AcarsSTVo_A09_A320_IAE(line));
			}
			
		}
		

		
		return a09vo;
	}
	
	public void insertA09(String msgno, AcarsDfdA09Vo_A320 a09vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a09iave25_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ECYC_1,AP_1,SVA_1,BAF_1,"
				+ "ESN_2,EHRS_2,ECYC_2,AP_2,SVA_2,BAF_2," 
				+ "E,DIV,REF,K,ECW3,ECW4,PARA,"
				+ "EPR_S1,EGT_S1,N1_S1,N2_S1,FF_S1,TN_S1,EPRC_S1,"
		        + "EPR_S2,EGT_S2,N1_S2,N2_S2,FF_S2,TN_S2,EPRC_S2,"
		        + "EPR_S3,EGT_S3,N1_S3,N2_S3,FF_S3,TN_S3,EPRC_S3,"
		        + "EPR_S4,EGT_S4,N1_S4,N2_S4,FF_S4,TN_S4,EPRC_S4,"
		        + "EPR_S5,EGT_S5,N1_S5,N2_S5,FF_S5,TN_S5,EPRC_S5,"
		        + "EPR_S6,EGT_S6,N1_S6,N2_S6,FF_S6,TN_S6,EPRC_S6,"
		        + "EPR_S7,EGT_S7,N1_S7,N2_S7,FF_S7,TN_S7,EPRC_S7,"
		        
		        + "EPR_T1,EGT_T1,N1_T1,N2_T1,FF_T1,TN_T1,EPRC_T1,"
		        + "EPR_T2,EGT_T2,N1_T2,N2_T2,FF_T2,TN_T2,EPRC_T2,"
		        + "EPR_T3,EGT_T3,N1_T3,N2_T3,FF_T3,TN_T3,EPRC_T3,"
		        + "EPR_T4,EGT_T4,N1_T4,N2_T4,FF_T4,TN_T4,EPRC_T4,"
		        + "EPR_T5,EGT_T5,N1_T5,N2_T5,FF_T5,TN_T5,EPRC_T5,"
		        + "EPR_T6,EGT_T6,N1_T6,N2_T6,FF_T6,TN_T6,EPRC_T6,"
		        + "EPR_T7,EGT_T7,N1_T7,N2_T7,FF_T7,TN_T7,EPRC_T7,"
				+ "UPDATE_DATE)"  
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"

				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"

				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"

				+ "sysdate)";
		
		
				
		AcarsEC_EEVo_A09_A320_IAE ec = a09vo.getEc_i();
		AcarsEC_EEVo_A09_A320_IAE ee = a09vo.getEe_i();
		AcarsN1Vo_A09_A320_IAE n1 = a09vo.getN1_i();
		AcarsSTVo_A09_A320_IAE s1=a09vo.getS1_i();
		AcarsSTVo_A09_A320_IAE s2=a09vo.getS2_i();
		AcarsSTVo_A09_A320_IAE s3=a09vo.getS3_i();
		AcarsSTVo_A09_A320_IAE s4=a09vo.getS4_i();
		AcarsSTVo_A09_A320_IAE s5=a09vo.getS5_i();
		AcarsSTVo_A09_A320_IAE s6=a09vo.getS6_i();
		AcarsSTVo_A09_A320_IAE s7=a09vo.getS7_i();
		AcarsSTVo_A09_A320_IAE t1=a09vo.getT1_i();
		AcarsSTVo_A09_A320_IAE t2=a09vo.getT2_i();
		AcarsSTVo_A09_A320_IAE t3=a09vo.getT3_i();
		AcarsSTVo_A09_A320_IAE t4=a09vo.getT4_i();
		AcarsSTVo_A09_A320_IAE t5=a09vo.getT5_i();
		AcarsSTVo_A09_A320_IAE t6=a09vo.getT6_i();
		AcarsSTVo_A09_A320_IAE t7=a09vo.getT7_i();


//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),ec.getSva(),ec.getBaf(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),ee.getSva(),ee.getBaf(),
				n1.getE(),n1.getDiv(),n1.getRef(),n1.getK(),n1.getEcw3(),n1.getEcw4(),n1.getPara(),
				s1.getEpr(),s1.getEgt(),s1.getN1(),s1.getN2(),s1.getFf(),s1.getTn(),s1.getEprc(),
		        s2.getEpr(),s2.getEgt(),s2.getN1(),s2.getN2(),s2.getFf(),s2.getTn(),s2.getEprc(),
		        s3.getEpr(),s3.getEgt(),s3.getN1(),s3.getN2(),s3.getFf(),s3.getTn(),s3.getEprc(),
		        s4.getEpr(),s4.getEgt(),s4.getN1(),s4.getN2(),s4.getFf(),s4.getTn(),s4.getEprc(),
		        s5.getEpr(),s5.getEgt(),s5.getN1(),s5.getN2(),s5.getFf(),s5.getTn(),s5.getEprc(),
		        s6.getEpr(),s6.getEgt(),s6.getN1(),s6.getN2(),s6.getFf(),s6.getTn(),s6.getEprc(),
		        s7.getEpr(),s7.getEgt(),s7.getN1(),s7.getN2(),s7.getFf(),s7.getTn(),s7.getEprc(),

		        t1.getEpr(),t1.getEgt(),t1.getN1(),t1.getN2(),t1.getFf(),t1.getTn(),t1.getEprc(),
		        t2.getEpr(),t2.getEgt(),t2.getN1(),t2.getN2(),t2.getFf(),t2.getTn(),t2.getEprc(),
		        t3.getEpr(),t3.getEgt(),t3.getN1(),t3.getN2(),t3.getFf(),t3.getTn(),t3.getEprc(),
		        t4.getEpr(),t4.getEgt(),t4.getN1(),t4.getN2(),t4.getFf(),t4.getTn(),t4.getEprc(),
		        t5.getEpr(),t5.getEgt(),t5.getN1(),t5.getN2(),t5.getFf(),t5.getTn(),t5.getEprc(),
		        t6.getEpr(),t6.getEgt(),t6.getN1(),t6.getN2(),t6.getFf(),t6.getTn(),t6.getEprc(),
		        t7.getEpr(),t7.getEgt(),t7.getN1(),t7.getN2(),t7.getFf(),t7.getTn(),t7.getEprc()
				);
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A09 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
