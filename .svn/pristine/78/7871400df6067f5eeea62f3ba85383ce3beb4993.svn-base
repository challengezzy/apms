package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA02Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a01a02.AcarsECVo_A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsEEVo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsN1N2Vo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsS1S2Vo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsT1T2Vo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsV1V2Vo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsV3V4Vo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsX1X2Vo_A02_A320;
import com.apms.bs.dataprase.vo.a01a02.AcarsX3Vo_A02_A320;
import com.apms.bs.dataprase.vo.a01a02.AcarsX4X5Vo_A02_A320;
import com.apms.bs.dataprase.vo.a01a02.AcarsX6X7Vo_A02_A320;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A02DataParseImpl_A320_IAE extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA02Vo_A320 a02vo = praseA02Data(content,trans_time);
		insertA02(msgno, a02vo);
		
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
	public AcarsDfdA02Vo_A320 praseA02Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA02Vo_A320 a02vo = new AcarsDfdA02Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
//			try{
			if(line.startsWith("EC")){
				a02vo.setEc_i_a02(new  AcarsECVo_A02_A320_IAE(line));
			}else if(line.startsWith("EE")){
				a02vo.setEe_i(new  AcarsEEVo_A01A02_A320_IAE(line));
			}else if(line.startsWith("N1")){
				a02vo.setN1_i(new  AcarsN1N2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("N2")){
				a02vo.setN2_i(new  AcarsN1N2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("S1")){
				a02vo.setS1_i(new  AcarsS1S2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("S2")){
				a02vo.setS2_i(new  AcarsS1S2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("T1")){
				a02vo.setT1_i(new  AcarsT1T2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("T2")){
				a02vo.setT2_i(new  AcarsT1T2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("V1")){
				a02vo.setV1_i(new  AcarsV1V2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("V2")){
				a02vo.setV2_i(new  AcarsV1V2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("V3")){
				a02vo.setV3_i(new  AcarsV3V4Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("V4")){
				a02vo.setV4_i(new  AcarsV3V4Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("X1")){
				a02vo.setX1(new  AcarsX1X2Vo_A02_A320(line));
			}else if(line.startsWith("X2")){
				a02vo.setX2(new  AcarsX1X2Vo_A02_A320(line));
			}else if(line.startsWith("X3")){
				a02vo.setX3(new  AcarsX3Vo_A02_A320(line));
			}else if(line.startsWith("X4")){
				a02vo.setX4(new  AcarsX4X5Vo_A02_A320(line));
			}else if(line.startsWith("X5")){
				a02vo.setX5(new  AcarsX4X5Vo_A02_A320(line));
			}else if(line.startsWith("X6")){
				a02vo.setX6(new  AcarsX6X7Vo_A02_A320(line));
			}else if(line.startsWith("X7")){
				a02vo.setX7(new  AcarsX6X7Vo_A02_A320(line));
			}
//			}catch(NullPointerException e)
//			{
//				throw new Exception(e.getClass().toString()+":在解析报文"+graphStr+"第"+i+"行:" + line+"的过程中出现空指针异常!");
//			}
			
		}
		

		
		return a02vo;
	}
	
	public void insertA02(String msgno, AcarsDfdA02Vo_A320 a02vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into A_DFD_A02IAEV25_LIST(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ECYC_1,AP_1,QA_EC,QE_EC,"
				+ "ESN_2,EHRS_2,ECYC_2,AP_2," //EC/6/EE/4
				+ "EPR_1,EPRC_1,EGT_1,N1_1,N2_1,FF_1,P125_1,"
				+ "EPR_2,EPRC_2,EGT_2,N1_2,N2_2,FF_2,P125_2," //N1/7/N2/7
				+ "P25_1,T25_1,P3_1,T3_1,P49_1,SVA_1,"
				+ "P25_2,T25_2,P3_2,T3_2,P49_2,SVA_2," //S1/6/S2/6
				+ "BAF_1,ACC_1,LP_1,GLE_1,PD_1,TN_1,P2_1,T2_1,"
				+ "BAF_2,ACC_2,LP_2,GLE_2,PD_2,TN_2,P2_2,T2_2," //T1/7/T2/7
				+ "ECW1_1,ECW2_1,EVM_1,OIP_1,OIT_1,OIQH_1,"
				+ "ECW1_2,ECW2_2,EVM_2,OIP_2,OIT_2,OIQH_2,"  //V1/6/V2/6
				+ "VB1_1,VB2_1,PHA_1,"
				+ "VB1_2,VB2_2,PHA_2," //V3/3/V4/3
				+ "WFQ_1,ELEV_1,AOA_1,SLP_1,CFPG_1,CIVV_1," 
				+ "WFQ_2,ELEV_2,AOA_2,SLP_2,CFPG_2,CIVV_2," //X1/6//X2/6
				+ "RUDD,RUDT,AILL,AILR,STAB,ROLL,YAW," //X3/7
				+ "RSP2_1,RSP3_1,RSP4_1,RSP5_1,FLAP_1,SLAT_1,"
				+ "RSP2_2,RSP3_2,RSP4_2,RSP5_2,FLAP_2,SLAT_2," //X4/6/X5/6
				+ "THDG_1,LONP_1,LATP_1,WS_1,WD_1,FT_1,FD_1,"
				+ "THDG_2,LONP_2,LATP_2,WS_2,WD_2,FT_2,FD_2," //X6/6/X7/6
				+ "UPDATE_DATE)"  //1
				+ " values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?," //EC/6/EE/4
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"//N1/7/N2/7
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"//S1/6/S2/6
				+ "?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,"//T1/8/T2/8
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"//V1/6/V2/6
				+ "?,?,?,"
				+ "?,?,?,"//V3/3/V4/3
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"//X1/6//X2/6
				+ "?,?,?,?,?,?,?,"//X3/7
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"//X4/6/X5/6
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"//X6/6/X7/6
				+ "sysdate)";
		
		
		AcarsECVo_A02_A320_IAE ec = a02vo.getEc_i_a02();
		AcarsEEVo_A01A02_A320_IAE ee = a02vo.getEe_i();
		AcarsN1N2Vo_A01A02_A320_IAE n1 = a02vo.getN1_i();
		AcarsN1N2Vo_A01A02_A320_IAE n2 = a02vo.getN2_i();
		AcarsS1S2Vo_A01A02_A320_IAE s1 = a02vo.getS1_i();
		AcarsS1S2Vo_A01A02_A320_IAE s2 = a02vo.getS2_i();
		AcarsT1T2Vo_A01A02_A320_IAE t1 = a02vo.getT1_i();
		AcarsT1T2Vo_A01A02_A320_IAE t2 = a02vo.getT2_i();
		AcarsV1V2Vo_A01A02_A320_IAE v1 = a02vo.getV1_i();
		AcarsV1V2Vo_A01A02_A320_IAE v2 = a02vo.getV2_i();
		AcarsV3V4Vo_A01A02_A320_IAE v3 = a02vo.getV3_i();
		AcarsV3V4Vo_A01A02_A320_IAE v4 = a02vo.getV4_i();
		AcarsX1X2Vo_A02_A320 x1 = a02vo.getX1();
		AcarsX1X2Vo_A02_A320 x2 = a02vo.getX2();
		AcarsX3Vo_A02_A320 x3 = a02vo.getX3();
		AcarsX4X5Vo_A02_A320 x4 = a02vo.getX4();
		AcarsX4X5Vo_A02_A320 x5 = a02vo.getX5();
		AcarsX6X7Vo_A02_A320 x6 = a02vo.getX6();
		AcarsX6X7Vo_A02_A320 x7 = a02vo.getX7();

//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),ec.getQa(),ec.getQe(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),//EC/6/EE/4
				n1.getEpr(),n1.getEprc(),n1.getEgt(),n1.getN1(),n1.getN2(),n1.getFf(),n1.getP125(),
				n2.getEpr(),n2.getEprc(),n2.getEgt(),n2.getN1(),n2.getN2(),n2.getFf(),n2.getP125(),//N1/7/N2/7
				s1.getP25(),s1.getT25(),s1.getP3(),s1.getT3(),s1.getP49(),s1.getSva(),
				s2.getP25(),s2.getT25(),s2.getP3(),s2.getT3(),s2.getP49(),s2.getSva(),//S1/6/S2/6
				t1.getBaf(),t1.getAcc(),t1.getLp(),t1.getGle(),t1.getPd(),t1.getTn(),t1.getP2(),t1.getT2(),
				t2.getBaf(),t2.getAcc(),t2.getLp(),t2.getGle(),t2.getPd(),t2.getTn(),t2.getP2(),t2.getT2(), //T1/8/T2/8
				v1.getEcw1(),v1.getEcw2(),v1.getEvm(),v1.getOip(),v1.getOit(),v1.getOiqh(),
				v2.getEcw1(),v2.getEcw2(),v2.getEvm(),v2.getOip(),v2.getOit(),v2.getOiqh(),//V1/6/V2/6
				v3.getVb1(),v3.getVb2(),v3.getPha(),
				v4.getVb1(),v4.getVb2(),v4.getPha(),//V3/3/V4/3
				x1.getWfq(),x1.getElev(),x1.getAoa(),x1.getSlp(),x1.getCfpg(),x1.getCivv(),
				x2.getWfq(),x2.getElev(),x2.getAoa(),x2.getSlp(),x2.getCfpg(),x2.getCivv(),//X1/6//X2/6
				x3.getRudd(),x3.getRudt(),x3.getAill(),x3.getAilr(),x3.getStab(),x3.getRoll(),x3.getYaw(),//X3/7
				x4.getRsp2(),x4.getRsp3(),x4.getRsp4(),x4.getRsp5(),x4.getFlap(),x4.getSlat(),
				x5.getRsp2(),x5.getRsp3(),x5.getRsp4(),x5.getRsp5(),x5.getFlap(),x5.getSlat(),//X4/6/X5/6
				x6.getThdg(),x6.getLonp(),x6.getLatp(),x6.getWs(),x6.getWd(),x6.getFt(),x6.getFd(),
				x7.getThdg(),x7.getLonp(),x7.getLatp(),x7.getWs(),x7.getWd(),x7.getFt(),x7.getFd());//X6/6/X7/6
		
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A02 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
