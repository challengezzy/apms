package com.apms.bs.dataprase.impl;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA06Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a06.AcarsEC_EEVo_A06_A320_CFM;
import com.apms.bs.dataprase.vo.a06.AcarsN1Vo_A06_A320_CFM;
import com.apms.bs.dataprase.vo.a06.AcarsSTVo_A06_A320_CFM;
import com.apms.bs.dataprase.vo.a06.AcarsVXVo_A06_A320_CFM;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A06DataParseImpl_A320_CFM extends ReportContentParseClass{
	
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
				a06vo.setEc_c(new  AcarsEC_EEVo_A06_A320_CFM(line));
			}else if(line.startsWith("EE")){
				a06vo.setEe_c(new  AcarsEC_EEVo_A06_A320_CFM(line));
			}else if(line.startsWith("N1")){
				a06vo.setN1_c(new  AcarsN1Vo_A06_A320_CFM(line));
			}else if(line.startsWith("S1")){
				a06vo.setS1_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("T1")){
				a06vo.setT1_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("S2")){
				a06vo.setS2_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("T2")){
				a06vo.setT2_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("S3")){
				a06vo.setS3_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("T3")){
				a06vo.setT3_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("S4")){
				a06vo.setS4_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("T4")){
				a06vo.setT4_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("S5")){
				a06vo.setS5_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("T5")){
				a06vo.setT5_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("S6")){
				a06vo.setS6_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("T6")){
				a06vo.setT6_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("S7")){
				a06vo.setS7_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("T7")){
				a06vo.setT7_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("S8")){
				a06vo.setS8_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("T8")){
				a06vo.setT8_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("S9")){
				a06vo.setS9_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("T9")){
				a06vo.setT9_c(new  AcarsSTVo_A06_A320_CFM(line));
			}else if(line.startsWith("V1")){
				a06vo.setV1_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("X1")){
				a06vo.setX1_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("V2")){
				a06vo.setV2_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("X2")){
				a06vo.setX2_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("V3")){
				a06vo.setV3_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("X3")){
				a06vo.setX3_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("V4")){
				a06vo.setV4_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("X4")){
				a06vo.setX4_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("V5")){
				a06vo.setV5_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("X5")){
				a06vo.setX5_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("V6")){
				a06vo.setV6_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("X6")){
				a06vo.setX6_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("V7")){
				a06vo.setV7_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("X7")){
				a06vo.setX7_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("V8")){
				a06vo.setV8_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("X8")){
				a06vo.setX8_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("V9")){
				a06vo.setV9_c(new  AcarsVXVo_A06_A320_CFM(line));
			}else if(line.startsWith("X9")){
				a06vo.setX9_c(new  AcarsVXVo_A06_A320_CFM(line));
			}
			
		}
		

		
		return a06vo;
	}
	
	public void insertA06(String msgno, AcarsDfdA06Vo_A320 a06vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a06cfm56_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+"ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,TR_1,"
				+"ESN_2,EHRS_2,ERT_2,ECYC_2,AP_2,TR_2," 
				+"E,MAX,LIM,REF,TOL,TTP,Y1,Y2,PSEL,"
				+"N1_S1,N1C_S1,N2_S1,EGT_S1,FF_S1,P3_S1,T3_S1,"
				+"N1_S2,N1C_S2,N2_S2,EGT_S2,FF_S2,P3_S2,T3_S2,"
				+"N1_S3,N1C_S3,N2_S3,EGT_S3,FF_S3,P3_S3,T3_S3,"
				+"N1_S4,N1C_S4,N2_S4,EGT_S4,FF_S4,P3_S4,T3_S4,"
				+"N1_S5,N1C_S5,N2_S5,EGT_S5,FF_S5,P3_S5,T3_S5,"
				+"N1_S6,N1C_S6,N2_S6,EGT_S6,FF_S6,P3_S6,T3_S6,"
				+"N1_S7,N1C_S7,N2_S7,EGT_S7,FF_S7,P3_S7,T3_S7,"
				+"N1_S8,N1C_S8,N2_S8,EGT_S8,FF_S8,P3_S8,T3_S8,"
				+"N1_S9,N1C_S9,N2_S9,EGT_S9,FF_S9,P3_S9,T3_S9,"
				
				+"N1_T1,N1C_T1,N2_T1,EGT_T1,FF_T1,P3_T1,T3_T1,"
				+"N1_T2,N1C_T2,N2_T2,EGT_T2,FF_T2,P3_T2,T3_T2,"
				+"N1_T3,N1C_T3,N2_T3,EGT_T3,FF_T3,P3_T3,T3_T3,"
				+"N1_T4,N1C_T4,N2_T4,EGT_T4,FF_T4,P3_T4,T3_T4,"
				+"N1_T5,N1C_T5,N2_T5,EGT_T5,FF_T5,P3_T5,T3_T5,"
				+"N1_T6,N1C_T6,N2_T6,EGT_T6,FF_T6,P3_T6,T3_T6,"
				+"N1_T7,N1C_T7,N2_T7,EGT_T7,FF_T7,P3_T7,T3_T7,"
				+"N1_T8,N1C_T8,N2_T8,EGT_T8,FF_T8,P3_T8,T3_T8,"
				+"N1_T9,N1C_T9,N2_T9,EGT_T9,FF_T9,P3_T9,T3_T9,"
				
				+"T5_V1,P25_V1,T25_V1,PT2_V1,ECW1_V1,VSV_V1,VBV_V1,"
				+"T5_V2,P25_V2,T25_V2,PT2_V2,ECW1_V2,VSV_V2,VBV_V2,"
				+"T5_V3,P25_V3,T25_V3,PT2_V3,ECW1_V3,VSV_V3,VBV_V3,"
				+"T5_V4,P25_V4,T25_V4,PT2_V4,ECW1_V4,VSV_V4,VBV_V4,"
				+"T5_V5,P25_V5,T25_V5,PT2_V5,ECW1_V5,VSV_V5,VBV_V5,"
				+"T5_V6,P25_V6,T25_V6,PT2_V6,ECW1_V6,VSV_V6,VBV_V6,"	
				+"T5_V7,P25_V7,T25_V7,PT2_V7,ECW1_V7,VSV_V7,VBV_V7,"
				+"T5_V8,P25_V8,T25_V8,PT2_V8,ECW1_V8,VSV_V8,VBV_V8,"
				+"T5_V9,P25_V9,T25_V9,PT2_V9,ECW1_V9,VSV_V9,VBV_V9,"
				
				+"T5_X1,P25_X1,T25_X1,PT2_X1,ECW1_X1,VSV_X1,VBV_X1,"
				+"T5_X2,P25_X2,T25_X2,PT2_X2,ECW1_X2,VSV_X2,VBV_X2,"
				+"T5_X3,P25_X3,T25_X3,PT2_X3,ECW1_X3,VSV_X3,VBV_X3,"
				+"T5_X4,P25_X4,T25_X4,PT2_X4,ECW1_X4,VSV_X4,VBV_X4,"
				+"T5_X5,P25_X5,T25_X5,PT2_X5,ECW1_X5,VSV_X5,VBV_X5,"
				+"T5_X6,P25_X6,T25_X6,PT2_X6,ECW1_X6,VSV_X6,VBV_X6,"
				+"T5_X7,P25_X7,T25_X7,PT2_X7,ECW1_X7,VSV_X7,VBV_X7,"
				+"T5_X8,P25_X8,T25_X8,PT2_X8,ECW1_X8,VSV_X8,VBV_X8,"
				+"T5_X9,P25_X9,T25_X9,PT2_X9,ECW1_X9,VSV_X9,VBV_X9,"

				+"UPDATE_DATE)"  //1
				+" values(S_A_DFD_HEAD.nextval,?,?,?,?,"
				+"?,?,?,?,?,?,"
				+"?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,?,?,"
				
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"?,?,?,?,?,?,?,"
				+"sysdate)";
		
		
		
		
		AcarsEC_EEVo_A06_A320_CFM ec = a06vo.getEc_c();
		AcarsEC_EEVo_A06_A320_CFM ee = a06vo.getEe_c();	
		AcarsN1Vo_A06_A320_CFM n1 = a06vo.getN1_c();
		
		AcarsSTVo_A06_A320_CFM s1=a06vo.getS1_c();
		AcarsSTVo_A06_A320_CFM s2=a06vo.getS2_c();
		AcarsSTVo_A06_A320_CFM s3=a06vo.getS3_c();
		AcarsSTVo_A06_A320_CFM s4=a06vo.getS4_c();
		AcarsSTVo_A06_A320_CFM s5=a06vo.getS5_c();
		AcarsSTVo_A06_A320_CFM s6=a06vo.getS6_c();
		AcarsSTVo_A06_A320_CFM s7=a06vo.getS7_c();
		AcarsSTVo_A06_A320_CFM s8=a06vo.getS8_c();
		AcarsSTVo_A06_A320_CFM s9=a06vo.getS9_c();
		
		AcarsSTVo_A06_A320_CFM t1=a06vo.getT1_c();
		AcarsSTVo_A06_A320_CFM t2=a06vo.getT2_c();
		AcarsSTVo_A06_A320_CFM t3=a06vo.getT3_c();
		AcarsSTVo_A06_A320_CFM t4=a06vo.getT4_c();
		AcarsSTVo_A06_A320_CFM t5=a06vo.getT5_c();
		AcarsSTVo_A06_A320_CFM t6=a06vo.getT6_c();
		AcarsSTVo_A06_A320_CFM t7=a06vo.getT7_c();
		AcarsSTVo_A06_A320_CFM t8=a06vo.getT8_c();
		AcarsSTVo_A06_A320_CFM t9=a06vo.getT9_c();
		
		AcarsVXVo_A06_A320_CFM v1=a06vo.getV1_c();
		AcarsVXVo_A06_A320_CFM v2=a06vo.getV2_c();
		AcarsVXVo_A06_A320_CFM v3=a06vo.getV3_c();
		AcarsVXVo_A06_A320_CFM v4=a06vo.getV4_c();
		AcarsVXVo_A06_A320_CFM v5=a06vo.getV5_c();
		AcarsVXVo_A06_A320_CFM v6=a06vo.getV6_c();
		AcarsVXVo_A06_A320_CFM v7=a06vo.getV7_c();
		AcarsVXVo_A06_A320_CFM v8=a06vo.getV8_c();
		AcarsVXVo_A06_A320_CFM v9=a06vo.getV9_c();
		
		AcarsVXVo_A06_A320_CFM x1=a06vo.getX1_c();
		AcarsVXVo_A06_A320_CFM x2=a06vo.getX2_c();
		AcarsVXVo_A06_A320_CFM x3=a06vo.getX3_c();
		AcarsVXVo_A06_A320_CFM x4=a06vo.getX4_c();
		AcarsVXVo_A06_A320_CFM x5=a06vo.getX5_c();
		AcarsVXVo_A06_A320_CFM x6=a06vo.getX6_c();
		AcarsVXVo_A06_A320_CFM x7=a06vo.getX7_c();
		AcarsVXVo_A06_A320_CFM x8=a06vo.getX8_c();
		AcarsVXVo_A06_A320_CFM x9=a06vo.getX9_c();
		if(x9==null||v9==null||s9==null||t9==null)
		{
			throw new NullPointerException("原始报文数据不完整，解析失败");
		}
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
	
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),ec.getTr(),
				ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),ee.getTr(),
				n1.getE(),n1.getMax(),n1.getLim(),n1.getRef(),n1.getTol(),n1.getTtp(),n1.getY1(),n1.getY2(),n1.getPsel(),
				s1.getN1(),s1.getN1c(),s1.getN2(),s1.getEgt(),s1.getFf(),s1.getP3(),s1.getT3(),
				s2.getN1(),s2.getN1c(),s2.getN2(),s2.getEgt(),s2.getFf(),s2.getP3(),s2.getT3(),
				s3.getN1(),s3.getN1c(),s3.getN2(),s3.getEgt(),s3.getFf(),s3.getP3(),s3.getT3(),
				s4.getN1(),s4.getN1c(),s4.getN2(),s4.getEgt(),s4.getFf(),s4.getP3(),s4.getT3(),
				s5.getN1(),s5.getN1c(),s5.getN2(),s5.getEgt(),s5.getFf(),s5.getP3(),s5.getT3(),
				s6.getN1(),s6.getN1c(),s6.getN2(),s6.getEgt(),s6.getFf(),s6.getP3(),s6.getT3(),
				s7.getN1(),s7.getN1c(),s7.getN2(),s7.getEgt(),s7.getFf(),s7.getP3(),s7.getT3(),
				s8.getN1(),s8.getN1c(),s8.getN2(),s8.getEgt(),s8.getFf(),s8.getP3(),s8.getT3(),
				s9.getN1(),s9.getN1c(),s9.getN2(),s9.getEgt(),s9.getFf(),s9.getP3(),s9.getT3(),

				t1.getN1(),t1.getN1c(),t1.getN2(),t1.getEgt(),t1.getFf(),t1.getP3(),t1.getT3(),
				t2.getN1(),t2.getN1c(),t2.getN2(),t2.getEgt(),t2.getFf(),t2.getP3(),t2.getT3(),
				t3.getN1(),t3.getN1c(),t3.getN2(),t3.getEgt(),t3.getFf(),t3.getP3(),t3.getT3(),
				t4.getN1(),t4.getN1c(),t4.getN2(),t4.getEgt(),t4.getFf(),t4.getP3(),t4.getT3(),
				t5.getN1(),t5.getN1c(),t5.getN2(),t5.getEgt(),t5.getFf(),t5.getP3(),t5.getT3(),
				t6.getN1(),t6.getN1c(),t6.getN2(),t6.getEgt(),t6.getFf(),t6.getP3(),t6.getT3(),
				t7.getN1(),t7.getN1c(),t7.getN2(),t7.getEgt(),t7.getFf(),t7.getP3(),t7.getT3(),
				t8.getN1(),t8.getN1c(),t8.getN2(),t8.getEgt(),t8.getFf(),t8.getP3(),t8.getT3(),
				t9.getN1(),t9.getN1c(),t9.getN2(),t9.getEgt(),t9.getFf(),t9.getP3(),t9.getT3(),

				v1.getT5(),v1.getP25(),v1.getT25(),v1.getP12(),v1.getEcw1(),v1.getVsv(),v1.getVbv(),
				v2.getT5(),v2.getP25(),v2.getT25(),v2.getP12(),v2.getEcw1(),v2.getVsv(),v2.getVbv(),
				v3.getT5(),v3.getP25(),v3.getT25(),v3.getP12(),v3.getEcw1(),v3.getVsv(),v3.getVbv(),
				v4.getT5(),v4.getP25(),v4.getT25(),v4.getP12(),v4.getEcw1(),v4.getVsv(),v4.getVbv(),
				v5.getT5(),v5.getP25(),v5.getT25(),v5.getP12(),v5.getEcw1(),v5.getVsv(),v5.getVbv(),
				v6.getT5(),v6.getP25(),v6.getT25(),v6.getP12(),v6.getEcw1(),v6.getVsv(),v6.getVbv(),
				v7.getT5(),v7.getP25(),v7.getT25(),v7.getP12(),v7.getEcw1(),v7.getVsv(),v7.getVbv(),
				v8.getT5(),v8.getP25(),v8.getT25(),v8.getP12(),v8.getEcw1(),v8.getVsv(),v8.getVbv(),
				v9.getT5(),v9.getP25(),v9.getT25(),v9.getP12(),v9.getEcw1(),v9.getVsv(),v9.getVbv(),
				
				x1.getT5(),x1.getP25(),x1.getT25(),x1.getP12(),x1.getEcw1(),x1.getVsv(),x1.getVbv(),
				x2.getT5(),x2.getP25(),x2.getT25(),x2.getP12(),x2.getEcw1(),x2.getVsv(),x2.getVbv(),
				x3.getT5(),x3.getP25(),x3.getT25(),x3.getP12(),x3.getEcw1(),x3.getVsv(),x3.getVbv(),
				x4.getT5(),x4.getP25(),x4.getT25(),x4.getP12(),x4.getEcw1(),x4.getVsv(),x4.getVbv(),
				x5.getT5(),x5.getP25(),x5.getT25(),x5.getP12(),x5.getEcw1(),x5.getVsv(),x5.getVbv(),
				x6.getT5(),x6.getP25(),x6.getT25(),x6.getP12(),x6.getEcw1(),x6.getVsv(),x6.getVbv(),
				x7.getT5(),x7.getP25(),x7.getT25(),x7.getP12(),x7.getEcw1(),x7.getVsv(),x7.getVbv(),
				x8.getT5(),x8.getP25(),x8.getT25(),x8.getP12(),x8.getEcw1(),x8.getVsv(),x8.getVbv(),
				x9.getT5(),x9.getP25(),x9.getT25(),x9.getP12(),x9.getEcw1(),x9.getVsv(),x9.getVbv()

				);
		
		
			
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A06 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
