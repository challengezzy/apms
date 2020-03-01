package com.apms.bs.dataprase.impl;


import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA01Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a01a02.AcarsECVo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsEEVo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsN1N2Vo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsS1S2Vo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsT1T2Vo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsV1V2Vo_A01A02_A320_IAE;
import com.apms.bs.dataprase.vo.a01a02.AcarsV3V4Vo_A01A02_A320_IAE;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A01DataParseImpl_A320_IAE extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA01Vo_A320 a01vo = praseA01Data(content,trans_time);
		insertA01(msgno, a01vo);
		
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
	public AcarsDfdA01Vo_A320 praseA01Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA01Vo_A320 a01vo = new AcarsDfdA01Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		//logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){
				a01vo.setEc_i(new  AcarsECVo_A01A02_A320_IAE(line));
			}else if(line.startsWith("EE")){
				a01vo.setEe_i(new  AcarsEEVo_A01A02_A320_IAE(line));
			}else if(line.startsWith("N1")){
				a01vo.setN1_i(new  AcarsN1N2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("N2")){
				a01vo.setN2_i(new  AcarsN1N2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("S1")){
				a01vo.setS1_i(new  AcarsS1S2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("S2")){
				a01vo.setS2_i(new  AcarsS1S2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("T1")){
				a01vo.setT1_i(new  AcarsT1T2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("T2")){
				a01vo.setT2_i(new  AcarsT1T2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("V1")){
				a01vo.setV1_i(new  AcarsV1V2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("V2")){
				a01vo.setV2_i(new  AcarsV1V2Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("V3")){
				a01vo.setV3_i(new  AcarsV3V4Vo_A01A02_A320_IAE(line));
			}else if(line.startsWith("V4")){
				a01vo.setV4_i(new  AcarsV3V4Vo_A01A02_A320_IAE(line));
			}
			
		}
		

		
		return a01vo;
	}
	
	public void insertA01(String msgno, AcarsDfdA01Vo_A320 a01vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a01iaev25_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ECYC_1,AP_1,QE_EC,"
				+ "ESN_2,EHRS_2,ECYC_2,AP_2," //14
				+ "EPR_1,EPRC_1,EGT_1,N1_1,N2_1,FF_1,P125_1,"
				+ "EPR_2,EPRC_2,EGT_2,N1_2,N2_2,FF_2,P125_2,"  //14
				+ "P25_1,T25_1,P3_1,T3_1,P49_1,SVA_1,"
				+ "P25_2,T25_2,P3_2,T3_2,P49_2,SVA_2," 
				+ "BAF_1,ACC_1,LP_1,GLE_1,PD_1,TN_1,P2_1,T2_1,"
				+ "BAF_2,ACC_2,LP_2,GLE_2,PD_2,TN_2,P2_2,T2_2," 
				+ "ECW1_1,ECW2_1,EVM_1,OIP_1,OIT_1,OIQH_1,"
				+ "ECW1_2,ECW2_2,EVM_2,OIP_2,OIT_2,OIQH_2,"  //40
				+ "VB1_1,VB2_1,PHA_1,"
				+ "VB1_2,VB2_2,PHA_2," 
				+ "UPDATE_DATE)"  //7
				+ " values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,sysdate)";
		
		
		AcarsECVo_A01A02_A320_IAE ec = a01vo.getEc_i();
		AcarsEEVo_A01A02_A320_IAE ee = a01vo.getEe_i();
		AcarsN1N2Vo_A01A02_A320_IAE n1 = a01vo.getN1_i();
		AcarsN1N2Vo_A01A02_A320_IAE n2 = a01vo.getN2_i();
		AcarsS1S2Vo_A01A02_A320_IAE s1 = a01vo.getS1_i();
		AcarsS1S2Vo_A01A02_A320_IAE s2 = a01vo.getS2_i();
		AcarsT1T2Vo_A01A02_A320_IAE t1 = a01vo.getT1_i();
		AcarsT1T2Vo_A01A02_A320_IAE t2 = a01vo.getT2_i();
		AcarsV1V2Vo_A01A02_A320_IAE v1 = a01vo.getV1_i();
		AcarsV1V2Vo_A01A02_A320_IAE v2 = a01vo.getV2_i();
		AcarsV3V4Vo_A01A02_A320_IAE v3 = a01vo.getV3_i();
		AcarsV3V4Vo_A01A02_A320_IAE v4 = a01vo.getV4_i();

//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),ec.getQe(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),
				n1.getEpr(),n1.getEprc(),n1.getEgt(),n1.getN1(),n1.getN2(),n1.getFf(),n1.getP125(),
				n2.getEpr(),n2.getEprc(),n2.getEgt(),n2.getN1(),n2.getN2(),n2.getFf(),n2.getP125(),
				s1.getP25(),s1.getT25(),s1.getP3(),s1.getT3(),s1.getP49(),s1.getSva(),
				s2.getP25(),s2.getT25(),s2.getP3(),s2.getT3(),s2.getP49(),s2.getSva(),
				t1.getBaf(),t1.getAcc(),t1.getLp(),t1.getGle(),t1.getPd(),t1.getTn(),t1.getP2(),t1.getT2(),
				t2.getBaf(),t2.getAcc(),t2.getLp(),t2.getGle(),t2.getPd(),t2.getTn(),t2.getP2(),t2.getT2(),
				v1.getEcw1(),v1.getEcw2(),v1.getEvm(),v1.getOip(),v1.getOit(),v1.getOiqh(),
				v2.getEcw1(),v2.getEcw2(),v2.getEvm(),v2.getOip(),v2.getOit(),v2.getOiqh(),
				v3.getVb1(),v3.getVb2(),v3.getPha(),
				v4.getVb1(),v4.getVb2(),v4.getPha());
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A01 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
