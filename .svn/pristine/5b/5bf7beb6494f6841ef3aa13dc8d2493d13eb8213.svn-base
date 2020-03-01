package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA06Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a06.AcarsEC_EEVo_A06_A320_IAE;
import com.apms.bs.dataprase.vo.a06.AcarsN1Vo_A06_A320_IAE;
import com.apms.bs.dataprase.vo.a06.AcarsSTVo_A06_A320_IAE;
import com.apms.bs.dataprase.vo.a06.AcarsVXVo_A06_A320_IAE;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A06DataParseImpl_A320_IAE extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA06Vo_A320 a06vo = praseA06Data(content,trans_time);
		insertA06(msgno, a06vo);
		
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
	public AcarsDfdA06Vo_A320 praseA06Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA06Vo_A320 a06vo = new AcarsDfdA06Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		//logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){
				a06vo.setEc_i(new  AcarsEC_EEVo_A06_A320_IAE(line));
			}else if(line.startsWith("EE")){
				a06vo.setEe_i(new  AcarsEC_EEVo_A06_A320_IAE(line));
			}else if(line.startsWith("N1")){
				a06vo.setN1_i(new  AcarsN1Vo_A06_A320_IAE(line));
			}else if(line.startsWith("S1")){
				a06vo.setS1_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("T1")){
				a06vo.setT1_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("S2")){
				a06vo.setS2_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("T2")){
				a06vo.setT2_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("S3")){
				a06vo.setS3_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("T3")){
				a06vo.setT3_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("S4")){
				a06vo.setS4_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("T4")){
				a06vo.setT4_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("S5")){
				a06vo.setS5_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("T5")){
				a06vo.setT5_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("S6")){
				a06vo.setS6_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("T6")){
				a06vo.setT6_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("S7")){
				a06vo.setS7_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("T7")){
				a06vo.setT7_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("S8")){
				a06vo.setS8_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("T8")){
				a06vo.setT8_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("S9")){
				a06vo.setS9_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("T9")){
				a06vo.setT9_i(new  AcarsSTVo_A06_A320_IAE(line));
			}else if(line.startsWith("V1")){
				a06vo.setV1_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("X1")){
				a06vo.setX1_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("V2")){
				a06vo.setV2_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("X2")){
				a06vo.setX2_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("V3")){
				a06vo.setV3_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("X3")){
				a06vo.setX3_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("V4")){
				a06vo.setV4_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("X4")){
				a06vo.setX4_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("V5")){
				a06vo.setV5_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("X5")){
				a06vo.setX5_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("V6")){
				a06vo.setV6_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("X6")){
				a06vo.setX6_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("V7")){
				a06vo.setV7_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("X7")){
				a06vo.setX7_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("V8")){
				a06vo.setV8_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("X8")){
				a06vo.setX8_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("V9")){
				a06vo.setV9_i(new  AcarsVXVo_A06_A320_IAE(line));
			}else if(line.startsWith("X9")){
				a06vo.setX9_i(new  AcarsVXVo_A06_A320_IAE(line));
			}
			
		}
		

		
		return a06vo;
	}
	
	public void insertA06(String msgno, AcarsDfdA06Vo_A320 a06vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a06iaev25_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ECYC_1,AP_1,HPT_1,SVA_1,BAF_1,LP_1,"
				+ "ESN_2,EHRS_2,ECYC_2,AP_2,HPT_2,SVA_2,BAF_2,LP_2," 
				+ "E,MAX,LIM,REF,TOL,TTP,Y1,Y2,PARA,"
				+ "EPR_S1,EGT_S1,N1_S1,N2_S1,FF_S1,P3_S1,T3_S1,"
				+ "EPR_S2,EGT_S2,N1_S2,N2_S2,FF_S2,P3_S2,T3_S2,"
				+ "EPR_S3,EGT_S3,N1_S3,N2_S3,FF_S3,P3_S3,T3_S3,"
				+ "EPR_S4,EGT_S4,N1_S4,N2_S4,FF_S4,P3_S4,T3_S4,"
				+ "EPR_S5,EGT_S5,N1_S5,N2_S5,FF_S5,P3_S5,T3_S5,"
				+ "EPR_S6,EGT_S6,N1_S6,N2_S6,FF_S6,P3_S6,T3_S6,"
				+ "EPR_S7,EGT_S7,N1_S7,N2_S7,FF_S7,P3_S7,T3_S7,"
				+ "EPR_S8,EGT_S8,N1_S8,N2_S8,FF_S8,P3_S8,T3_S8,"
				+ "EPR_S9,EGT_S9,N1_S9,N2_S9,FF_S9,P3_S9,T3_S9,"
				
				+ "EPR_T1,EGT_T1,N1_T1,N2_T1,FF_T1,P3_T1,T3_T1,"
				+ "EPR_T2,EGT_T2,N1_T2,N2_T2,FF_T2,P3_T2,T3_T2,"
				+ "EPR_T3,EGT_T3,N1_T3,N2_T3,FF_T3,P3_T3,T3_T3,"
				+ "EPR_T4,EGT_T4,N1_T4,N2_T4,FF_T4,P3_T4,T3_T4,"
				+ "EPR_T5,EGT_T5,N1_T5,N2_T5,FF_T5,P3_T5,T3_T5,"
				+ "EPR_T6,EGT_T6,N1_T6,N2_T6,FF_T6,P3_T6,T3_T6,"
				+ "EPR_T7,EGT_T7,N1_T7,N2_T7,FF_T7,P3_T7,T3_T7,"
				+ "EPR_T8,EGT_T8,N1_T8,N2_T8,FF_T8,P3_T8,T3_T8,"
				+ "EPR_T9,EGT_T9,N1_T9,N2_T9,FF_T9,P3_T9,T3_T9,"
				
				+ "EPRC_V1,T2_V1,P2_V1,MN_V1,ECW3_V1,ECW4_V1,TRP_V1,"
				+ "EPRC_V2,T2_V2,P2_V2,MN_V2,ECW3_V2,ECW4_V2,TRP_V2,"
				+ "EPRC_V3,T2_V3,P2_V3,MN_V3,ECW3_V3,ECW4_V3,TRP_V3,"
				+ "EPRC_V4,T2_V4,P2_V4,MN_V4,ECW3_V4,ECW4_V4,TRP_V4,"
				+ "EPRC_V5,T2_V5,P2_V5,MN_V5,ECW3_V5,ECW4_V5,TRP_V5,"
				+ "EPRC_V6,T2_V6,P2_V6,MN_V6,ECW3_V6,ECW4_V6,TRP_V6,"
				+ "EPRC_V7,T2_V7,P2_V7,MN_V7,ECW3_V7,ECW4_V7,TRP_V7,"
				+ "EPRC_V8,T2_V8,P2_V8,MN_V8,ECW3_V8,ECW4_V8,TRP_V8,"
				+ "EPRC_V9,T2_V9,P2_V9,MN_V9,ECW3_V9,ECW4_V9,TRP_V9,"

				+ "EPRC_X1,T2_X1,P2_X1,MN_X1,ECW3_X1,ECW4_X1,TRP_X1,"
				+ "EPRC_X2,T2_X2,P2_X2,MN_X2,ECW3_X2,ECW4_X2,TRP_X2,"
				+ "EPRC_X3,T2_X3,P2_X3,MN_X3,ECW3_X3,ECW4_X3,TRP_X3,"
				+ "EPRC_X4,T2_X4,P2_X4,MN_X4,ECW3_X4,ECW4_X4,TRP_X4,"
				+ "EPRC_X5,T2_X5,P2_X5,MN_X5,ECW3_X5,ECW4_X5,TRP_X5,"
				+ "EPRC_X6,T2_X6,P2_X6,MN_X6,ECW3_X6,ECW4_X6,TRP_X6,"
				+ "EPRC_X7,T2_X7,P2_X7,MN_X7,ECW3_X7,ECW4_X7,TRP_X7,"
				+ "EPRC_X8,T2_X8,P2_X8,MN_X8,ECW3_X8,ECW4_X8,TRP_X8,"
				+ "EPRC_X9,T2_X9,P2_X9,MN_X9,ECW3_X9,ECW4_X9,TRP_X9,"
				+ "UPDATE_DATE)"  
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "null,null,null,null,null,null,null,"
			//	+ "?,?,?,?,?,?,?,"
				
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "null,null,null,null,null,null,null,"

		//		+ "?,?,?,?,?,?,?,"
				
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "null,null,null,null,null,null,null,"

	//			+ "?,?,?,?,?,?,?,"
				
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "null,null,null,null,null,null,null,"

		//		+ "?,?,?,?,?,?,?,"
				+ "sysdate)";
		
		
		AcarsEC_EEVo_A06_A320_IAE ec = a06vo.getEc_i();
		AcarsEC_EEVo_A06_A320_IAE ee = a06vo.getEe_i();
		AcarsN1Vo_A06_A320_IAE n1 = a06vo.getN1_i();
		AcarsSTVo_A06_A320_IAE s1 =a06vo.getS1_i();
		AcarsSTVo_A06_A320_IAE s2 =a06vo.getS2_i();
		AcarsSTVo_A06_A320_IAE s3 =a06vo.getS3_i();
		AcarsSTVo_A06_A320_IAE s4 =a06vo.getS4_i();
		AcarsSTVo_A06_A320_IAE s5 =a06vo.getS5_i();
		AcarsSTVo_A06_A320_IAE s6 =a06vo.getS6_i();
		AcarsSTVo_A06_A320_IAE s7 =a06vo.getS7_i();
		AcarsSTVo_A06_A320_IAE s8 =a06vo.getS8_i();
	  //AcarsSTVo_A06_A320_IAE s9 =a06vo.getS9_i();
		
		AcarsSTVo_A06_A320_IAE t1 =a06vo.getT1_i();
		AcarsSTVo_A06_A320_IAE t2 =a06vo.getT2_i();
		AcarsSTVo_A06_A320_IAE t3 =a06vo.getT3_i();
		AcarsSTVo_A06_A320_IAE t4 =a06vo.getT4_i();
		AcarsSTVo_A06_A320_IAE t5 =a06vo.getT5_i();
		AcarsSTVo_A06_A320_IAE t6 =a06vo.getT6_i();
		AcarsSTVo_A06_A320_IAE t7 =a06vo.getT7_i();
		AcarsSTVo_A06_A320_IAE t8 =a06vo.getT8_i();
	  //AcarsSTVo_A06_A320_IAE t9 =a06vo.getT9_i();
		
		AcarsVXVo_A06_A320_IAE v1 =a06vo.getV1_i();
		AcarsVXVo_A06_A320_IAE v2 =a06vo.getV2_i();
		AcarsVXVo_A06_A320_IAE v3 =a06vo.getV3_i();
		AcarsVXVo_A06_A320_IAE v4 =a06vo.getV4_i();
		AcarsVXVo_A06_A320_IAE v5 =a06vo.getV5_i();
		AcarsVXVo_A06_A320_IAE v6 =a06vo.getV6_i();
		AcarsVXVo_A06_A320_IAE v7 =a06vo.getV7_i();
		AcarsVXVo_A06_A320_IAE v8 =a06vo.getV8_i();
	  //AcarsVXVo_A06_A320_IAE v9 =a06vo.getV9_i();

		AcarsVXVo_A06_A320_IAE x1 =a06vo.getX1_i();
		AcarsVXVo_A06_A320_IAE x2 =a06vo.getX2_i();
		AcarsVXVo_A06_A320_IAE x3 =a06vo.getX3_i();
		AcarsVXVo_A06_A320_IAE x4 =a06vo.getX4_i();
		AcarsVXVo_A06_A320_IAE x5 =a06vo.getX5_i();
		AcarsVXVo_A06_A320_IAE x6 =a06vo.getX6_i();
		AcarsVXVo_A06_A320_IAE x7 =a06vo.getX7_i();
		AcarsVXVo_A06_A320_IAE x8 =a06vo.getX8_i();
	  //AcarsVXVo_A06_A320_IAE x9 =a06vo.getX9_i();
		if(x8==null||v8==null||s8==null||t8==null)
		{
			throw new NullPointerException("原始报文数据不完整，解析失败");
		}
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),ec.getHpt(),ec.getSva(),ec.getBaf(),ec.getLp(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),ee.getHpt(),ee.getSva(),ee.getBaf(),ee.getLp(),
				n1.getE(),n1.getMax(),n1.getLim(),n1.getRef(),n1.getTol(),n1.getTtp(),n1.getY1(),n1.getY2(),n1.getPara(),
				
				s1.getEpr(),s1.getEgt(),s1.getN1(),s1.getN2(),s1.getFf(),s1.getP3(),s1.getT3(),
				s2.getEpr(),s2.getEgt(),s2.getN1(),s2.getN2(),s2.getFf(),s2.getP3(),s2.getT3(),
				s3.getEpr(),s3.getEgt(),s3.getN1(),s3.getN2(),s3.getFf(),s3.getP3(),s3.getT3(),
				s4.getEpr(),s4.getEgt(),s4.getN1(),s4.getN2(),s4.getFf(),s4.getP3(),s4.getT3(),
				s5.getEpr(),s5.getEgt(),s5.getN1(),s5.getN2(),s5.getFf(),s5.getP3(),s5.getT3(),
				s6.getEpr(),s6.getEgt(),s6.getN1(),s6.getN2(),s6.getFf(),s6.getP3(),s6.getT3(),
				s7.getEpr(),s7.getEgt(),s7.getN1(),s7.getN2(),s7.getFf(),s7.getP3(),s7.getT3(),
				s8.getEpr(),s8.getEgt(),s8.getN1(),s8.getN2(),s8.getFf(),s8.getP3(),s8.getT3(),
			//	s9.getEpr(),s9.getEgt(),s9.getN1(),s9.getN2(),s9.getFf(),s9.getP3(),s9.getT3(),
				
				t1.getEpr(),t1.getEgt(),t1.getN1(),t1.getN2(),t1.getFf(),t1.getP3(),t1.getT3(),
				t2.getEpr(),t2.getEgt(),t2.getN1(),t2.getN2(),t2.getFf(),t2.getP3(),t2.getT3(),
				t3.getEpr(),t3.getEgt(),t3.getN1(),t3.getN2(),t3.getFf(),t3.getP3(),t3.getT3(),
				t4.getEpr(),t4.getEgt(),t4.getN1(),t4.getN2(),t4.getFf(),t4.getP3(),t4.getT3(),
				t5.getEpr(),t5.getEgt(),t5.getN1(),t5.getN2(),t5.getFf(),t5.getP3(),t5.getT3(),
				t6.getEpr(),t6.getEgt(),t6.getN1(),t6.getN2(),t6.getFf(),t6.getP3(),t6.getT3(),
				t7.getEpr(),t7.getEgt(),t7.getN1(),t7.getN2(),t7.getFf(),t7.getP3(),t7.getT3(),
				t8.getEpr(),t8.getEgt(),t8.getN1(),t8.getN2(),t8.getFf(),t8.getP3(),t8.getT3(),
			//	t9.getEpr(),t9.getEgt(),t9.getN1(),t9.getN2(),t9.getFf(),t9.getP3(),t9.getT3(),
				
				v1.getEprc(),v1.getT2(),v1.getP2(),v1.getMn(),v1.getEcw3(),v1.getEcw4(),v1.getTrp(),
				v2.getEprc(),v2.getT2(),v2.getP2(),v2.getMn(),v2.getEcw3(),v2.getEcw4(),v2.getTrp(),
				v3.getEprc(),v3.getT2(),v3.getP2(),v3.getMn(),v3.getEcw3(),v3.getEcw4(),v3.getTrp(),
				v4.getEprc(),v4.getT2(),v4.getP2(),v4.getMn(),v4.getEcw3(),v4.getEcw4(),v4.getTrp(),
				v5.getEprc(),v5.getT2(),v5.getP2(),v5.getMn(),v5.getEcw3(),v5.getEcw4(),v5.getTrp(),
				v6.getEprc(),v6.getT2(),v6.getP2(),v6.getMn(),v6.getEcw3(),v6.getEcw4(),v6.getTrp(),
				v7.getEprc(),v7.getT2(),v7.getP2(),v7.getMn(),v7.getEcw3(),v7.getEcw4(),v7.getTrp(),
				v8.getEprc(),v8.getT2(),v8.getP2(),v8.getMn(),v8.getEcw3(),v8.getEcw4(),v8.getTrp(),
			//	v9.getEprc(),v9.getP2(),v9.getT2(),v9.getMn(),v9.getEcw3(),v9.getEcw4(),v9.getTrp(),

				x1.getEprc(),x1.getT2(),x1.getP2(),x1.getMn(),x1.getEcw3(),x1.getEcw4(),x1.getTrp(),
				x2.getEprc(),x2.getT2(),x2.getP2(),x2.getMn(),x2.getEcw3(),x2.getEcw4(),x2.getTrp(),
				x3.getEprc(),x3.getT2(),x3.getP2(),x3.getMn(),x3.getEcw3(),x3.getEcw4(),x3.getTrp(),
				x4.getEprc(),x4.getT2(),x4.getP2(),x4.getMn(),x4.getEcw3(),x4.getEcw4(),x4.getTrp(),
				x5.getEprc(),x5.getT2(),x5.getP2(),x5.getMn(),x5.getEcw3(),x5.getEcw4(),x5.getTrp(),
				x6.getEprc(),x6.getT2(),x6.getP2(),x6.getMn(),x6.getEcw3(),x6.getEcw4(),x6.getTrp(),
				x7.getEprc(),x7.getT2(),x7.getP2(),x7.getMn(),x7.getEcw3(),x7.getEcw4(),x7.getTrp(),
				x8.getEprc(),x8.getT2(),x8.getP2(),x8.getMn(),x8.getEcw3(),x8.getEcw4(),x8.getTrp()
			//	x9.getEprc(),x9.getP2(),x9.getT2(),x9.getMn(),x9.getEcw3(),x9.getEcw4(),x9.getTrp()
				);		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A06 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
