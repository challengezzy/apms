package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA04Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a04a05.AcarsCEVo_A04_A320;
import com.apms.bs.dataprase.vo.a04a05.AcarsEC_EEVo_A04A05_A320_IAE;
import com.apms.bs.dataprase.vo.a04a05.AcarsN1N2Vo_A04_A320_IAE;
import com.apms.bs.dataprase.vo.a04a05.AcarsS1S2Vo_A04_A320_IAE;
import com.apms.bs.dataprase.vo.a04a05.AcarsT1T2Vo_A04_A320_IAE;
import com.apms.bs.dataprase.vo.a04a05.AcarsV1V2Vo_A04_A320_IAE;
import com.apms.bs.dataprase.vo.a04a05.AcarsV3V4Vo_A04_A320_IAE;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A04DataParseImpl_A320_IAE extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA04Vo_A320 a04vo = praseA04Data(content,trans_time);
		insertA04(msgno, a04vo);
		
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
	public AcarsDfdA04Vo_A320 praseA04Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA04Vo_A320 a04vo = new AcarsDfdA04Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		//logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("CE")){
				a04vo.setCe(new  AcarsCEVo_A04_A320(line));
			}else if(line.startsWith("EC")){
				a04vo.setEc_i(new  AcarsEC_EEVo_A04A05_A320_IAE(line));
			}else if(line.startsWith("EE")){
				a04vo.setEe_i(new  AcarsEC_EEVo_A04A05_A320_IAE(line));
			}else if(line.startsWith("N1")){
				a04vo.setN1_i(new  AcarsN1N2Vo_A04_A320_IAE(line));
			}else if(line.startsWith("N2")){
				a04vo.setN2_i(new  AcarsN1N2Vo_A04_A320_IAE(line));
			}else if(line.startsWith("S1")){
				a04vo.setS1_i(new  AcarsS1S2Vo_A04_A320_IAE(line));
			}else if(line.startsWith("S2")){
				a04vo.setS2_i(new  AcarsS1S2Vo_A04_A320_IAE(line));
			}else if(line.startsWith("T1")){
				a04vo.setT1_i(new  AcarsT1T2Vo_A04_A320_IAE(line));
			}else if(line.startsWith("T2")){
				a04vo.setT2_i(new  AcarsT1T2Vo_A04_A320_IAE(line));
			}else if(line.startsWith("V1")){
				a04vo.setV1_i(new  AcarsV1V2Vo_A04_A320_IAE(line));
			}else if(line.startsWith("V2")){
				a04vo.setV2_i(new  AcarsV1V2Vo_A04_A320_IAE(line));
			}else if(line.startsWith("V3")){
				a04vo.setV3_i(new  AcarsV3V4Vo_A04_A320_IAE(line));
			}else if(line.startsWith("V4")){
				a04vo.setV4_i(new  AcarsV3V4Vo_A04_A320_IAE(line));
			}
			
		}
		

		
		return a04vo;
	}
	
	public void insertA04(String msgno, AcarsDfdA04Vo_A320 a04vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into A_DFD_A04IAEV25_LIST(ID,MSG_NO,ACNUM,RPTDATE,CODE,TAT,ALT,"
				+ "ESN_1,EHRS_1,ECYC_1,AP_1,"
				+ "ESN_2,EHRS_2,ECYC_2,AP_2," //13
				+ "NO1_N1,NO2_N1,NO3_N1,NO4_N1,NO5_N1,NO6_N1,NO7_N1,NO8_N1,EGTM_1,EGTB_1,"
				+ "NO1_N2,NO2_N2,NO3_N2,NO4_N2,NO5_N2,NO6_N2,NO7_N2,NO8_N2,EGTM_2,EGTB_2," //18
				+ "EPR_1,EGT_1,N1_1,N2_1,FF_1,EPRT_1,EPRC_1,"
				+ "EPR_2,EGT_2,N1_2,N2_2,FF_2,EPRT_2,EPRC_2,"  //14
				+ "P3_1,T3_1,GLE_1,P2_1,T2_1,P49_1,"
				+ "P3_2,T3_2,GLE_2,P2_2,T2_2,P49_2," //12
				+ "ECW1_1,ECW2_1,EVM_1,OIP_1,OIT_1,FT_1,O_1,F_1,"
				+ "ECW1_2,ECW2_2,EVM_2,OIP_2,OIT_2,FT_2,O_2,F_2,"  //16
				+ "VB1_1,VB2_1,PHA_1,PSB_1,TLA_1,BVP_1,"
				+ "VB1_2,VB2_2,PHA_2,PSB_2,TLA_2,BVP_2," //12
				+ "BACTIVENUM_N1,BACTIVENUM_N2,"
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
				+ "?,?,?,?,?,?,?,?, ?,?,sysdate)";
		
		AcarsCEVo_A04_A320 ce = a04vo.getCe();
		AcarsEC_EEVo_A04A05_A320_IAE ec = a04vo.getEc_i();
		AcarsEC_EEVo_A04A05_A320_IAE ee = a04vo.getEe_i();
		AcarsN1N2Vo_A04_A320_IAE n1 = a04vo.getN1_i();
		AcarsN1N2Vo_A04_A320_IAE n2 = a04vo.getN2_i();
		AcarsS1S2Vo_A04_A320_IAE s1 = a04vo.getS1_i();
		AcarsS1S2Vo_A04_A320_IAE s2 = a04vo.getS2_i();
		AcarsT1T2Vo_A04_A320_IAE t1 = a04vo.getT1_i();
		AcarsT1T2Vo_A04_A320_IAE t2 = a04vo.getT2_i();
		AcarsV1V2Vo_A04_A320_IAE v1 = a04vo.getV1_i();
		AcarsV1V2Vo_A04_A320_IAE v2 = a04vo.getV2_i();
		AcarsV3V4Vo_A04_A320_IAE v3 = a04vo.getV3_i();
		AcarsV3V4Vo_A04_A320_IAE v4 = a04vo.getV4_i();

//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),ce.getTat(),ce.getAlt(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),
				n1.get_1(),n1.get_2(),n1.get_3(),n1.get_4(),n1.get_5(),n1.get_6(),n1.get_7(),n1.get_8(),n1.getEgtm(),n1.getEgtb(),
				n2.get_1(),n2.get_2(),n2.get_3(),n2.get_4(),n2.get_5(),n2.get_6(),n2.get_7(),n2.get_8(),n2.getEgtm(),n1.getEgtb(),
				s1.getEpr(),s1.getEgt(),s1.getN1(),s1.getN2(),s1.getFf(),s1.getEprt(),s1.getEprc(),
				s2.getEpr(),s2.getEgt(),s2.getN1(),s2.getN2(),s2.getFf(),s2.getEprt(),s2.getEprc(),
				t1.getP3(),t1.getT3(),t1.getGle(),t1.getP2(),t1.getT2(),t1.getP49(),
				t2.getP3(),t2.getT3(),t2.getGle(),t2.getP2(),t2.getT2(),t2.getP49(),
				v1.getEcw1(),v1.getEcw2(),v1.getEvm(),v1.getOip(),v1.getOit(),v1.getFt(),v1.getO(),v1.getF(),
				v2.getEcw1(),v2.getEcw2(),v2.getEvm(),v2.getOip(),v2.getOit(),v2.getFt(),v2.getO(),v2.getF(),
				v3.getVb1(),v3.getVb2(),v3.getPha(),v3.getPsb(),v3.getTla(),v3.getBvp(),
				v4.getVb1(),v4.getVb2(),v4.getPha(),v4.getPsb(),v4.getTla(),v4.getBvp(),
				n1.getBumpActiveNum(),n2.getBumpActiveNum() );
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A04 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
