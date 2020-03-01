package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA11Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a11.AcarsEC_EEVo_A11_A320_CFM;
import com.apms.bs.dataprase.vo.a11.AcarsN1N2Vo_A11_A320_CFM;
import com.apms.bs.dataprase.vo.a11.AcarsS1S2Vo_A11_A320_CFM;
import com.apms.bs.dataprase.vo.a11.AcarsT1T2Vo_A11_A320_CFM;
import com.apms.bs.dataprase.vo.a11.AcarsV1V2Vo_A11_A320_CFM;
import com.apms.bs.dataprase.vo.a11.AcarsV3V4Vo_A11_A320_CFM;
import com.apms.bs.dataprase.vo.a11.AcarsX1X2Vo_A11_A320_CFM;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A11DataParseImpl_A320_CFM extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA11Vo_A320 a11vo = praseA11Data(content,trans_time);
		insertA11(msgno, a11vo);
		
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
	public AcarsDfdA11Vo_A320 praseA11Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA11Vo_A320 a11vo = new AcarsDfdA11Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){
				a11vo.setEc_c(new  AcarsEC_EEVo_A11_A320_CFM(line));
			}else if(line.startsWith("EE")){
				a11vo.setEe_c(new  AcarsEC_EEVo_A11_A320_CFM(line));
			}else if(line.startsWith("N1")){
				a11vo.setN1_c(new  AcarsN1N2Vo_A11_A320_CFM(line));
			}else if(line.startsWith("N2")){
				a11vo.setN2_c(new  AcarsN1N2Vo_A11_A320_CFM(line));
			}else if(line.startsWith("S1")){
				a11vo.setS1_c(new  AcarsS1S2Vo_A11_A320_CFM(line));
			}else if(line.startsWith("S2")){
				a11vo.setS2_c(new  AcarsS1S2Vo_A11_A320_CFM(line));
			}else if(line.startsWith("T1")){
				a11vo.setT1_c(new  AcarsT1T2Vo_A11_A320_CFM(line));
			}else if(line.startsWith("T2")){
				a11vo.setT2_c(new  AcarsT1T2Vo_A11_A320_CFM(line));
			}else if(line.startsWith("V1")){
				a11vo.setV1_c(new  AcarsV1V2Vo_A11_A320_CFM(line));
			}else if(line.startsWith("V2")){
				a11vo.setV2_c(new  AcarsV1V2Vo_A11_A320_CFM(line));
			}else if(line.startsWith("V3")){
				a11vo.setV3_c(new  AcarsV3V4Vo_A11_A320_CFM(line));
			}else if(line.startsWith("V4")){
				a11vo.setV4_c(new  AcarsV3V4Vo_A11_A320_CFM(line));
			}else if(line.startsWith("X1")){
				a11vo.setX1_c(new  AcarsX1X2Vo_A11_A320_CFM(line));
			}else if(line.startsWith("X2")){
				a11vo.setX2_c(new  AcarsX1X2Vo_A11_A320_CFM(line));
			}
		
		}
		

		
		return a11vo;
	}
	
	public void insertA11(String msgno, AcarsDfdA11Vo_A320 a11vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a11iave25_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE," //5
				+ "ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,"
				+ "ESN_2,EHRS_2,ERT_2,ECYC_2,AP_2," //10
				+ "N1_N1,N1C_N1,N2_N1,EGT_N1,FF_N1,PS13_N1,"
				+ "N1_N2,N1C_N2,N2_N2,EGT_N2,FF_N2,PS13_N2," //12
				+ "P25_S1,T25_S1,P3_S1,T3_S1,T5_S1,VSV_S1,VBV_S1,"
				+ "P25_S2,T25_S2,P3_S2,T3_S2,T5_S2,VSV_S2,VBV_S2," //14
				+ "HPT_T1,LPT_T1,GLE_T1,PD_T1,TN_T1,PT2_T1,"
				+ "HPT_T2,LPT_T2,GLE_T2,PD_T2,TN_T2,PT2_T2," //12
				+ "VN_V1,VL_V1,PHA_V1,PHT_V1,VC_V1,VH_V1,EVM_V1,"
				+ "VN_V2,VL_V2,PHA_V2,PHT_V2,VC_V2,VH_V2,EVM_V2," //14
				+ "OIP_V3,OIT_V3,ECW1_V3,SSEL_V3,"
				+ "OIP_V4,OIT_V4,ECW1_V4,SSEL_V4," //8
				+ "EGTK_X1,N1K_X1,N2K_X1,FFK_X1,"
				+ "EGTK_X2,N1K_X2,N2K_X2,FFK_X2," //8
				+ "UPDATE_DATE)"  //1
				+ " values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,sysdate)";
		
		
		AcarsEC_EEVo_A11_A320_CFM ec = a11vo.getEc_c();
		AcarsEC_EEVo_A11_A320_CFM ee = a11vo.getEe_c();
		AcarsN1N2Vo_A11_A320_CFM n1 = a11vo.getN1_c();
		AcarsN1N2Vo_A11_A320_CFM n2 = a11vo.getN2_c();
		AcarsS1S2Vo_A11_A320_CFM s1 = a11vo.getS1_c();
		AcarsS1S2Vo_A11_A320_CFM s2 = a11vo.getS2_c();
		AcarsT1T2Vo_A11_A320_CFM t1 = a11vo.getT1_c();
		AcarsT1T2Vo_A11_A320_CFM t2 = a11vo.getT2_c();
		AcarsV1V2Vo_A11_A320_CFM v1 = a11vo.getV1_c();
		AcarsV1V2Vo_A11_A320_CFM v2 = a11vo.getV2_c();
		AcarsV3V4Vo_A11_A320_CFM v3 = a11vo.getV3_c();
		AcarsV3V4Vo_A11_A320_CFM v4 = a11vo.getV4_c();
		AcarsX1X2Vo_A11_A320_CFM x1 = a11vo.getX1_c();
		AcarsX1X2Vo_A11_A320_CFM x2 = a11vo.getX2_c();



//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),
				ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),
				n1.getN1(),n1.getN1(),n1.getN2(),n1.getEgt(),n1.getFf(),n1.getPs13(),
				n2.getN1(),n2.getN1(),n2.getN2(),n2.getEgt(),n2.getFf(),n2.getPs13(),
				s1.getP25(),s1.getT25(),s1.getP3(),s1.getT3(),s1.getT5(),s1.getVsv(),s1.getVbv(),
				s2.getP25(),s2.getT25(),s2.getP3(),s2.getT3(),s2.getT5(),s2.getVsv(),s2.getVbv(),
				t1.getHpt(),t1.getLpt(),t1.getGle(),t1.getPd(),t1.getTn(),t1.getPt2(),
				t2.getHpt(),t2.getLpt(),t2.getGle(),t2.getPd(),t2.getTn(),t2.getPt2(),
				v1.getVn(),v1.getVl(),v1.getPha(),v1.getPht(),v1.getVc(),v1.getVh(),v1.getEvm(),
				v2.getVn(),v2.getVl(),v2.getPha(),v2.getPht(),v2.getVc(),v2.getVh(),v2.getEvm(),
				v3.getOip(),v3.getOit(),v3.getEcw1(),v3.getSsel(),
				v4.getOip(),v4.getOit(),v4.getEcw1(),v4.getSsel(),
				x1.getEgtk(),x1.getN1k(),x1.getN2k(),x1.getFfk(),
				x2.getEgtk(),x2.getN1k(),x2.getN2k(),x2.getFfk()
				);
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A11 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
