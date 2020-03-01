package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA10Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a10.AcarsECVo_A10_A320_CFM;
import com.apms.bs.dataprase.vo.a10.AcarsEEVo_A10_A320_CFM;
import com.apms.bs.dataprase.vo.a10.AcarsN1Vo_A10_A320_CFM;
import com.apms.bs.dataprase.vo.a10.AcarsSxVo_A10_A320_CFM;
import com.apms.bs.dataprase.vo.a10.AcarsTxVo_A10_A320_CFM;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A10DataParseImpl_A320_CFM extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA10Vo_A320 a10vo = praseA10Data(content,trans_time);
		insertA10(msgno, a10vo);
		
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
	public AcarsDfdA10Vo_A320 praseA10Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA10Vo_A320 a10vo = new AcarsDfdA10Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){
				a10vo.setEc_c(new  AcarsECVo_A10_A320_CFM(line));
			}else if(line.startsWith("EE")){
				a10vo.setEe_c(new  AcarsEEVo_A10_A320_CFM(line));
			}else if(line.startsWith("N1")){
				a10vo.setN1_c(new  AcarsN1Vo_A10_A320_CFM(line));
			}else if(line.startsWith("S1")){
				a10vo.setS1_c(new  AcarsSxVo_A10_A320_CFM(line));
			}else if(line.startsWith("T1")){
				a10vo.setT1_c(new  AcarsTxVo_A10_A320_CFM(line));
			}else if(line.startsWith("S2")){
				a10vo.setS2_c(new  AcarsSxVo_A10_A320_CFM(line));
			}else if(line.startsWith("T2")){
				a10vo.setT2_c(new  AcarsTxVo_A10_A320_CFM(line));
			}else if(line.startsWith("S3")){
				a10vo.setS3_c(new  AcarsSxVo_A10_A320_CFM(line));
			}else if(line.startsWith("T3")){
				a10vo.setT3_c(new  AcarsTxVo_A10_A320_CFM(line));
			}else if(line.startsWith("S4")){
				a10vo.setS4_c(new  AcarsSxVo_A10_A320_CFM(line));
			}else if(line.startsWith("T4")){
				a10vo.setT4_c(new  AcarsTxVo_A10_A320_CFM(line));
			}else if(line.startsWith("S5")){
				a10vo.setS5_c(new  AcarsSxVo_A10_A320_CFM(line));
			}else if(line.startsWith("T5")){
				a10vo.setT5_c(new  AcarsTxVo_A10_A320_CFM(line));
			}else if(line.startsWith("S6")){
				a10vo.setS6_c(new  AcarsSxVo_A10_A320_CFM(line));
			}else if(line.startsWith("T6")){
				a10vo.setT6_c(new  AcarsTxVo_A10_A320_CFM(line));
			}else if(line.startsWith("S7")){
				a10vo.setS7_c(new  AcarsSxVo_A10_A320_CFM(line));
			}else if(line.startsWith("T7")){
				a10vo.setT7_c(new  AcarsTxVo_A10_A320_CFM(line));
			}
		
		}
		

		
		return a10vo;
	}
	
	public void insertA10(String msgno, AcarsDfdA10Vo_A320 a10vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a10cfm56_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,Y1_EC,Y2_EC,"
				+ "ESN_2,EHRS_2,ERT_2,ECYC_2,AP_2," 
				+ "E,MAX,LIM,TOL,TTP,TTF,FF,PD,SM,"
				+ "N1_S1,N2_S1,EGT_S1,FF_S1,FMV_S1,T25_S1,PD_S1,"
		        + "N1_S2,N2_S2,EGT_S2,FF_S2,FMV_S2,T25_S2,PD_S2,"
		        + "N1_S3,N2_S3,EGT_S3,FF_S3,FMV_S3,T25_S3,PD_S3,"
		        + "N1_S4,N2_S4,EGT_S4,FF_S4,FMV_S4,T25_S4,PD_S4,"
		        + "N1_S5,N2_S5,EGT_S5,FF_S5,FMV_S5,T25_S5,PD_S5,"
		        + "N1_S6,N2_S6,EGT_S6,FF_S6,FMV_S6,T25_S6,PD_S6,"
		        + "N1_S7,N2_S7,EGT_S7,FF_S7,FMV_S7,T25_S7,PD_S7,"

		        + "P3_T1,T3_T1,VSV_T1,VBV_T1,T5_T1,OIT_T1,ECW5_T1,"
	            + "P3_T2,T3_T2,VSV_T2,VBV_T2,T5_T2,OIT_T2,ECW5_T2,"
	            + "P3_T3,T3_T3,VSV_T3,VBV_T3,T5_T3,OIT_T3,ECW5_T3,"
	            + "P3_T4,T3_T4,VSV_T4,VBV_T4,T5_T4,OIT_T4,ECW5_T4,"
	            + "P3_T5,T3_T5,VSV_T5,VBV_T5,T5_T5,OIT_T5,ECW5_T5,"
	            + "P3_T6,T3_T6,VSV_T6,VBV_T6,T5_T6,OIT_T6,ECW5_T6,"
	            + "P3_T7,T3_T7,VSV_T7,VBV_T7,T5_T7,OIT_T7,ECW5_T7,"

				+ "UPDATE_DATE)"  
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				
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
		
		
		
		
		
		AcarsECVo_A10_A320_CFM ec = a10vo.getEc_c();
		AcarsEEVo_A10_A320_CFM ee = a10vo.getEe_c();
		AcarsN1Vo_A10_A320_CFM n1 = a10vo.getN1_c();
		AcarsSxVo_A10_A320_CFM s1 = a10vo.getS1_c();
		AcarsSxVo_A10_A320_CFM s2 = a10vo.getS2_c();
		AcarsSxVo_A10_A320_CFM s3 = a10vo.getS3_c();
		AcarsSxVo_A10_A320_CFM s4 = a10vo.getS4_c();
		AcarsSxVo_A10_A320_CFM s5 = a10vo.getS5_c();
		AcarsSxVo_A10_A320_CFM s6 = a10vo.getS6_c();
		AcarsSxVo_A10_A320_CFM s7 = a10vo.getS7_c();
		AcarsTxVo_A10_A320_CFM t1 = a10vo.getT1_c();
		AcarsTxVo_A10_A320_CFM t2 = a10vo.getT2_c();
		AcarsTxVo_A10_A320_CFM t3 = a10vo.getT3_c();
		AcarsTxVo_A10_A320_CFM t4 = a10vo.getT4_c();
		AcarsTxVo_A10_A320_CFM t5 = a10vo.getT5_c();
		AcarsTxVo_A10_A320_CFM t6 = a10vo.getT6_c();
		AcarsTxVo_A10_A320_CFM t7 = a10vo.getT7_c();

//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),ec.getY1(),ec.getY2(),
				ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),
				n1.getE(),n1.getMax(),n1.getLim(),n1.getTol(),n1.getTtp(),n1.getTtf(),n1.getFf(),n1.getPd(),n1.getSm(),
				
				s1.getN1(),s1.getN2(),s1.getEgt(),s1.getFf(),s1.getFmv(),s1.getT25(),s1.getPd(),
		        s2.getN1(),s2.getN2(),s2.getEgt(),s2.getFf(),s2.getFmv(),s2.getT25(),s2.getPd(),
		        s3.getN1(),s3.getN2(),s3.getEgt(),s3.getFf(),s3.getFmv(),s3.getT25(),s3.getPd(),
		        s4.getN1(),s4.getN2(),s4.getEgt(),s4.getFf(),s4.getFmv(),s4.getT25(),s4.getPd(),
		        s5.getN1(),s5.getN2(),s5.getEgt(),s5.getFf(),s5.getFmv(),s5.getT25(),s5.getPd(),
		        s6.getN1(),s6.getN2(),s6.getEgt(),s6.getFf(),s6.getFmv(),s6.getT25(),s6.getPd(),
		        s7.getN1(),s7.getN2(),s7.getEgt(),s7.getFf(),s7.getFmv(),s7.getT25(),s7.getPd(),

		        t1.getP3(),t1.getT3(),t1.getVsv(),t1.getVbv(),t1.getT5(),t1.getOit(),t1.getEcw5(),
	            t2.getP3(),t2.getT3(),t2.getVsv(),t2.getVbv(),t2.getT5(),t2.getOit(),t2.getEcw5(),
	            t3.getP3(),t3.getT3(),t3.getVsv(),t3.getVbv(),t3.getT5(),t3.getOit(),t3.getEcw5(),
	            t4.getP3(),t4.getT3(),t4.getVsv(),t4.getVbv(),t4.getT5(),t4.getOit(),t4.getEcw5(),
	            t5.getP3(),t5.getT3(),t5.getVsv(),t5.getVbv(),t5.getT5(),t5.getOit(),t5.getEcw5(),
	            t6.getP3(),t6.getT3(),t6.getVsv(),t6.getVbv(),t6.getT5(),t6.getOit(),t6.getEcw5(),
	            t7==null?null:t7.getP3(), t7==null?null:t7.getT3(), t7==null?null:t7.getVsv(), t7==null?null:t7.getVbv()
	            ,t7==null?null:t7.getT5(), t7==null?null:t7.getOit(), t7==null?null:t7.getEcw5()
				);
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A10报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
