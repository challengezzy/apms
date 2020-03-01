package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA04Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a04a05.AcarsCEVo_A04_A320;
import com.apms.bs.dataprase.vo.a04a05.AcarsEC_EEVo_A04A05_A320_CFM;
import com.apms.bs.dataprase.vo.a04a05.AcarsN1N2Vo_A04_A320_CFM;
import com.apms.bs.dataprase.vo.a04a05.AcarsS1S2Vo_A04_A320_CFM;
import com.apms.bs.dataprase.vo.a04a05.AcarsT1T2Vo_A04_A320_CFM;
import com.apms.bs.dataprase.vo.a04a05.AcarsV1V2Vo_A04_A320_CFM;
import com.apms.bs.dataprase.vo.a04a05.AcarsV3V4Vo_A04_A320_CFM;
import com.apms.bs.dataprase.vo.a04a05.AcarsX1X2Vo_A04_A320_CFM;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A04DataParseImpl_A320_CFM extends ReportContentParseClass{
	
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
				a04vo.setEc_c(new  AcarsEC_EEVo_A04A05_A320_CFM(line));
			}else if(line.startsWith("EE")){
				a04vo.setEe_c(new  AcarsEC_EEVo_A04A05_A320_CFM(line));
			}else if(line.startsWith("N1")){
				a04vo.setN1_c(new  AcarsN1N2Vo_A04_A320_CFM(line));
			}else if(line.startsWith("N2")){
				a04vo.setN2_c(new  AcarsN1N2Vo_A04_A320_CFM(line));
			}else if(line.startsWith("S1")){
				a04vo.setS1_c(new  AcarsS1S2Vo_A04_A320_CFM(line));
			}else if(line.startsWith("S2")){
				a04vo.setS2_c(new  AcarsS1S2Vo_A04_A320_CFM(line));
			}else if(line.startsWith("T1")){
				a04vo.setT1_c(new  AcarsT1T2Vo_A04_A320_CFM(line));
			}else if(line.startsWith("T2")){
				a04vo.setT2_c(new  AcarsT1T2Vo_A04_A320_CFM(line));
			}else if(line.startsWith("V1")){
				a04vo.setV1_c(new  AcarsV1V2Vo_A04_A320_CFM(line));
			}else if(line.startsWith("V2")){
				a04vo.setV2_c(new  AcarsV1V2Vo_A04_A320_CFM(line));
			}else if(line.startsWith("V3")){
				a04vo.setV3_c(new  AcarsV3V4Vo_A04_A320_CFM(line));
			}else if(line.startsWith("V4")){
				a04vo.setV4_c(new  AcarsV3V4Vo_A04_A320_CFM(line));
			}else if(line.startsWith("X1")){
				a04vo.setX1_c(new  AcarsX1X2Vo_A04_A320_CFM(line));
			}else if(line.startsWith("X2")){
				a04vo.setX2_c(new  AcarsX1X2Vo_A04_A320_CFM(line));
			}
			
		}
		

		
		return a04vo;
	}
	
	public void insertA04(String msgno, AcarsDfdA04Vo_A320 a04vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_Dfd_A04cfm56_5b_List(ID,MSG_NO,ACNUM,RPTDATE,CODE,TAT,ALT,"
				+ "ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,"
				+ "ESN_2,EHRS_2,ERT_2,ECYC_2,AP_2," //15
				+ "NO1_N1,NO2_N1,NO3_N1,NO4_N1,NO5_N1,NO6_N1,NO7_N1,NO8_N1,egtm_n1,"
				+ "NO1_N2,NO2_N2,NO3_N2,NO4_N2,NO5_N2,NO6_N2,NO7_N2,NO8_N2,egtm_n2," //18
				+ "n1_1,n1c_1,n2_1,egt_1,ff_1,p3_1,n1mx_1,"
				+ "n1_2,n1c_2,n2_2,egt_2,ff_2,p3_2,n1mx_2," 
				+ "t3_1,t25_1,t12_1,pd_1,t5_1,vsv_1,vbv_1,"
				+ "t3_2,t25_2,t12_2,pd_2,t5_2,vsv_2,vbv_2," //28
				+ "vn_1,vl_1,pha_1,pht_1,vc_1,vh_1,evm_1,"
				+ "vn_2,vl_2,pha_2,pht_2,vc_2,vh_2,evm_2,"  //14
				+ "oip_1,oit_1,o_1,f_1,ecw1_1,ecw2_1,psel_1,"
				+ "oip_2,oit_2,o_2,f_2,ecw1_2,ecw2_2,psel_2," //14
				+ "pt2_1,ft_1,hpt_1,lpt_1,ralt_1,"
				+ "pt2_2,ft_2,hpt_2,lpt_2,ralt_2," //10
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
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,sysdate)";
		
		AcarsCEVo_A04_A320 ce = a04vo.getCe();
		AcarsEC_EEVo_A04A05_A320_CFM ec = a04vo.getEc_c();
		AcarsEC_EEVo_A04A05_A320_CFM ee = a04vo.getEe_c();
		AcarsN1N2Vo_A04_A320_CFM n1 = a04vo.getN1_c();
		AcarsN1N2Vo_A04_A320_CFM n2 = a04vo.getN2_c();
		AcarsS1S2Vo_A04_A320_CFM s1 = a04vo.getS1_c();
		AcarsS1S2Vo_A04_A320_CFM s2 = a04vo.getS2_c();
		AcarsT1T2Vo_A04_A320_CFM t1 = a04vo.getT1_c();
		AcarsT1T2Vo_A04_A320_CFM t2 = a04vo.getT2_c();
		AcarsV1V2Vo_A04_A320_CFM v1 = a04vo.getV1_c();
		AcarsV1V2Vo_A04_A320_CFM v2 = a04vo.getV2_c();
		AcarsV3V4Vo_A04_A320_CFM v3 = a04vo.getV3_c();
		AcarsV3V4Vo_A04_A320_CFM v4 = a04vo.getV4_c();
		AcarsX1X2Vo_A04_A320_CFM x1 = a04vo.getX1_c();
		AcarsX1X2Vo_A04_A320_CFM x2 = a04vo.getX2_c();

//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),ce.getTat(),ce.getAlt(),
				ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),
				ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),
				n1.get_1(),n1.get_2(),n1.get_3(),n1.get_4(),n1.get_5(),n1.get_6(),n1.get_7(),n1.get_8(),n1.getEgtm(),
				n2.get_1(),n2.get_2(),n2.get_3(),n2.get_4(),n2.get_5(),n2.get_6(),n2.get_7(),n2.get_8(),n2.getEgtm(),
				s1.getN1(),s1.getN1c(),s1.getN2(),s1.getEgt(),s1.getFf(),s1.getP3(),s1.getN1mx(),
				s2.getN1(),s2.getN1c(),s2.getN2(),s2.getEgt(),s2.getFf(),s2.getP3(),s2.getN1mx(),
				t1.getT3(),t1.getT25(),t1.getT12(),t1.getP0(),t1.getT5(),t1.getVsv(),t1.getVbv(),
				t2.getT3(),t2.getT25(),t2.getT12(),t2.getP0(),t2.getT5(),t2.getVsv(),t2.getVbv(),
				v1.getVn(),v1.getVl(),v1.getPha(),v1.getPht(),v1.getVc(),v1.getVh(),v1.getEvm(),
				v2.getVn(),v2.getVl(),v2.getPha(),v2.getPht(),v2.getVc(),v2.getVh(),v2.getEvm(),
				v3.getOip(),v3.getOit(),v3.getO(),v3.getF(),v3.getEcw1(),v3.getEcw2(),v3.getPsel(),
				v4.getOip(),v4.getOit(),v4.getO(),v4.getF(),v4.getEcw1(),v4.getEcw2(),v4.getPsel(),
				x1.getPt2(),x1.getFt(),x1.getHpt(),x1.getLpt(),x1.getRalt(),
				x2.getPt2(),x2.getFt(),x2.getHpt(),x2.getLpt(),x2.getRalt());
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A04 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
