package com.apms.bs.dataprase.impl;


import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA05Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a04a05.AcarsC3Vo_A05_A320_IAE;
import com.apms.bs.dataprase.vo.a04a05.AcarsEC_EEVo_A04A05_A320_IAE;
import com.apms.bs.dataprase.vo.a04a05.AcarsN1N2Vo_A05_A320_IAE;
import com.apms.bs.dataprase.vo.a04a05.AcarsSTVXVo_A05_A320_IAE;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A05DataParseImpl_A320_IAE extends ReportContentParseClass{
	
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
				a05vo.setEc_i(new  AcarsEC_EEVo_A04A05_A320_IAE(line));
			}else if(line.startsWith("EE")){
				a05vo.setEe_i(new  AcarsEC_EEVo_A04A05_A320_IAE(line));
			}else if(line.startsWith("N1")){
				a05vo.setN1_i(new  AcarsN1N2Vo_A05_A320_IAE(line));
			}else if(line.startsWith("N2")){
				a05vo.setN2_i(new  AcarsN1N2Vo_A05_A320_IAE(line));
			}else if(line.startsWith("S1")){
				a05vo.setS1_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T1")){
				a05vo.setT1_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("S2")){
				a05vo.setS2_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T2")){
				a05vo.setT2_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("S3")){
				a05vo.setS3_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T3")){
				a05vo.setT3_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("S4")){
				a05vo.setS4_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T4")){
				a05vo.setT4_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("S5")){
				a05vo.setS5_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T5")){
				a05vo.setT5_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("S6")){
				a05vo.setS6_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T6")){
				a05vo.setT6_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("S7")){
				a05vo.setS7_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T7")){
				a05vo.setT7_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("S8")){
				a05vo.setS8_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T8")){
				a05vo.setT8_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("S9")){
				a05vo.setS9_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T9")){
				a05vo.setT9_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("S0")){
				a05vo.setS0_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("T0")){
				a05vo.setT0_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("V1")){
				a05vo.setV1_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("X1")){
				a05vo.setX1_i(new  AcarsSTVXVo_A05_A320_IAE(line));
			}else if(line.startsWith("C3")){
				a05vo.setC3_i(new  AcarsC3Vo_A05_A320_IAE(line));
			}
			
		}
		

		
		return a05vo;
	}
	
	public void insertA05(String msgno, AcarsDfdA05Vo_A320 a05vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a05iaev25_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_EC,EHRS_EC,ECYC_EC,AP_EC,"
				+ "ESN_EE,EHRS_EE,ECYC_EE,AP_EE," 
				+ "ECW3_N1,ECW4_N1,EVM_N1,"
				+ "ECW3_N2,ECW4_N2,EVM_N2,"
				+ "EPR_S1,EGT_S1,N1_S1,N2_S1,FF_S1,VB1_S1,VB2_S1,"
				+ "EPR_S2,EGT_S2,N1_S2,N2_S2,FF_S2,VB1_S2,VB2_S2,"
				+ "EPR_S3,EGT_S3,N1_S3,N2_S3,FF_S3,VB1_S3,VB2_S3,"
				+ "EPR_S4,EGT_S4,N1_S4,N2_S4,FF_S4,VB1_S4,VB2_S4,"
				+ "EPR_S5,EGT_S5,N1_S5,N2_S5,FF_S5,VB1_S5,VB2_S5,"
				+ "EPR_S6,EGT_S6,N1_S6,N2_S6,FF_S6,VB1_S6,VB2_S6,"
				+ "EPR_S7,EGT_S7,N1_S7,N2_S7,FF_S7,VB1_S7,VB2_S7,"
				+ "EPR_S8,EGT_S8,N1_S8,N2_S8,FF_S8,VB1_S8,VB2_S8,"
				+ "EPR_S9,EGT_S9,N1_S9,N2_S9,FF_S9,VB1_S9,VB2_S9,"
				+ "EPR_S0,EGT_S0,N1_S0,N2_S0,FF_S0,VB1_S0,VB2_S0,"
				
				+ "EPR_T1,EGT_T1,N1_T1,N2_T1,FF_T1,VB1_T1,VB2_T1,"
				+ "EPR_T2,EGT_T2,N1_T2,N2_T2,FF_T2,VB1_T2,VB2_T2,"
				+ "EPR_T3,EGT_T3,N1_T3,N2_T3,FF_T3,VB1_T3,VB2_T3,"
				+ "EPR_T4,EGT_T4,N1_T4,N2_T4,FF_T4,VB1_T4,VB2_T4,"
				+ "EPR_T5,EGT_T5,N1_T5,N2_T5,FF_T5,VB1_T5,VB2_T5,"
				+ "EPR_T6,EGT_T6,N1_T6,N2_T6,FF_T6,VB1_T6,VB2_T6,"
				+ "EPR_T7,EGT_T7,N1_T7,N2_T7,FF_T7,VB1_T7,VB2_T7,"
				+ "EPR_T8,EGT_T8,N1_T8,N2_T8,FF_T8,VB1_T8,VB2_T8,"
				+ "EPR_T9,EGT_T9,N1_T9,N2_T9,FF_T9,VB1_T9,VB2_T9,"
				+ "EPR_T0,EGT_T0,N1_T0,N2_T0,FF_T0,VB1_T0,VB2_T0,"
				
				+ "EPR_V1,EGT_V1,N1_V1,N2_V1,FF_V1,VB1_V1,VB2_V1,"
				+ "EPR_X1,EGT_X1,N1_X1,N2_X1,FF_X1,VB1_X1,VB2_X1,"
				
				+ "TAT_C3,ALT_C3,MN_C3,BLEEDSTATUS_C3,APU_C3,"
				
				+ "UPDATE_DATE)"  //20
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,"
				+ "?,?,?,?,"
				+ "?,?,?,?," 
				+ "?,?,?,"
				+ "?,?,?,"
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
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				
				+ "?,?,?,?,?,"
				
				+ "sysdate)";
		
//		String insertSql1 = "insert into a_dfd_a05_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,"
//			+ "EPR,EGT,N1,N2,FF,VB1,VB2,"
//			+ "UPDATE_DATE)"  
//			+ " values(S_A_DFD_HEAD.nextval,"
//			+ "?,?,?,?,?,?,?,?,?,?,"
//			+ "?,?,?,?,?,?,?,?,sysdate)";
		
		
		AcarsEC_EEVo_A04A05_A320_IAE ec = a05vo.getEc_i();
		AcarsEC_EEVo_A04A05_A320_IAE ee = a05vo.getEe_i();
		AcarsN1N2Vo_A05_A320_IAE n1 = a05vo.getN1_i();
		AcarsN1N2Vo_A05_A320_IAE n2 = a05vo.getN2_i();
		AcarsSTVXVo_A05_A320_IAE s1 = a05vo.getS1_i();
		AcarsSTVXVo_A05_A320_IAE s2 = a05vo.getS2_i();
		AcarsSTVXVo_A05_A320_IAE s3 = a05vo.getS3_i();
		AcarsSTVXVo_A05_A320_IAE s4 = a05vo.getS4_i();
		AcarsSTVXVo_A05_A320_IAE s5 = a05vo.getS5_i();
		AcarsSTVXVo_A05_A320_IAE s6 = a05vo.getS6_i();
		AcarsSTVXVo_A05_A320_IAE s7 = a05vo.getS7_i();
		AcarsSTVXVo_A05_A320_IAE s8 = a05vo.getS8_i();
		AcarsSTVXVo_A05_A320_IAE s9 = a05vo.getS9_i();
		AcarsSTVXVo_A05_A320_IAE s0 = a05vo.getS0_i();
		AcarsSTVXVo_A05_A320_IAE t1 = a05vo.getT1_i();
		AcarsSTVXVo_A05_A320_IAE t2 = a05vo.getT2_i();
		AcarsSTVXVo_A05_A320_IAE t3 = a05vo.getT3_i();
		AcarsSTVXVo_A05_A320_IAE t4 = a05vo.getT4_i();
		AcarsSTVXVo_A05_A320_IAE t5 = a05vo.getT5_i();
		AcarsSTVXVo_A05_A320_IAE t6 = a05vo.getT6_i();
		AcarsSTVXVo_A05_A320_IAE t7 = a05vo.getT7_i();
		AcarsSTVXVo_A05_A320_IAE t8 = a05vo.getT8_i();
		AcarsSTVXVo_A05_A320_IAE t9 = a05vo.getT9_i();
		AcarsSTVXVo_A05_A320_IAE t0 = a05vo.getT0_i();
		AcarsSTVXVo_A05_A320_IAE x1 = a05vo.getX1_i();
		AcarsSTVXVo_A05_A320_IAE v1 = a05vo.getV1_i();
		AcarsC3Vo_A05_A320_IAE c3 =a05vo.getC3_i();
//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),
				n1.getEcw3(),n1.getEcw4(),n1.getEvm(),
				n2.getEcw3(),n2.getEcw4(),n2.getEvm(),
				
				s1.getEpr(),s1.getEgt(),s1.getN1(),s1.getN2(),s1.getFf(),s1.getVb1(),s1.getVb2(),
				s2.getEpr(),s2.getEgt(),s2.getN1(),s2.getN2(),s2.getFf(),s2.getVb1(),s2.getVb2(),
				s3.getEpr(),s3.getEgt(),s3.getN1(),s3.getN2(),s3.getFf(),s3.getVb1(),s3.getVb2(),
				s4.getEpr(),s4.getEgt(),s4.getN1(),s4.getN2(),s4.getFf(),s4.getVb1(),s4.getVb2(),
				s5.getEpr(),s5.getEgt(),s5.getN1(),s5.getN2(),s5.getFf(),s5.getVb1(),s5.getVb2(),
				s6.getEpr(),s6.getEgt(),s6.getN1(),s6.getN2(),s6.getFf(),s6.getVb1(),s6.getVb2(),
				s7.getEpr(),s7.getEgt(),s7.getN1(),s7.getN2(),s7.getFf(),s7.getVb1(),s7.getVb2(),
				s8.getEpr(),s8.getEgt(),s8.getN1(),s8.getN2(),s8.getFf(),s8.getVb1(),s8.getVb2(),
				s9.getEpr(),s9.getEgt(),s9.getN1(),s9.getN2(),s9.getFf(),s9.getVb1(),s9.getVb2(),
				s0.getEpr(),s0.getEgt(),s0.getN1(),s0.getN2(),s0.getFf(),s0.getVb1(),s0.getVb2(),
				
				t1.getEpr(),t1.getEgt(),t1.getN1(),t1.getN2(),t1.getFf(),t1.getVb1(),t1.getVb2(),
				t2.getEpr(),t2.getEgt(),t2.getN1(),t2.getN2(),t2.getFf(),t2.getVb1(),t2.getVb2(),
				t3.getEpr(),t3.getEgt(),t3.getN1(),t3.getN2(),t3.getFf(),t3.getVb1(),t3.getVb2(),
				t4.getEpr(),t4.getEgt(),t4.getN1(),t4.getN2(),t4.getFf(),t4.getVb1(),t4.getVb2(),
				t5.getEpr(),t5.getEgt(),t5.getN1(),t5.getN2(),t5.getFf(),t5.getVb1(),t5.getVb2(),
				t6.getEpr(),t6.getEgt(),t6.getN1(),t6.getN2(),t6.getFf(),t6.getVb1(),t6.getVb2(),
				t7.getEpr(),t7.getEgt(),t7.getN1(),t7.getN2(),t7.getFf(),t7.getVb1(),t7.getVb2(),
				t8.getEpr(),t8.getEgt(),t8.getN1(),t8.getN2(),t8.getFf(),t8.getVb1(),t8.getVb2(),
				t9.getEpr(),t9.getEgt(),t9.getN1(),t9.getN2(),t9.getFf(),t9.getVb1(),t9.getVb2(),
				t0.getEpr(),t0.getEgt(),t0.getN1(),t0.getN2(),t0.getFf(),t0.getVb1(),t0.getVb2(),
				
				v1.getEpr(),v1.getEgt(),v1.getN1(),v1.getN2(),v1.getFf(),v1.getVb1(),v1.getVb2(),
				x1.getEpr(),x1.getEgt(),x1.getN1(),x1.getN2(),x1.getFf(),x1.getVb1(),x1.getVb2(),
				
				c3.getTat(),c3.getAlt(),c3.getMn(),c3.getBleedstatus(),c3.getApu()
				);
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A05 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
