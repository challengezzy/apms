package com.apms.bs.dataprase.impl;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA11Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a11.AcarsEC_EEVo_A11_A320_IAE;
import com.apms.bs.dataprase.vo.a11.AcarsN1N2Vo_A11_A320_IAE;
import com.apms.bs.dataprase.vo.a11.AcarsS1S2Vo_A11_A320_IAE;
import com.apms.bs.dataprase.vo.a11.AcarsT1T2Vo_A11_A320_IAE;
import com.apms.bs.dataprase.vo.a11.AcarsV1V2Vo_A11_A320_IAE;
import com.apms.bs.dataprase.vo.a11.AcarsV3V4Vo_A11_A320_IAE;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A11DataParseImpl_A320_IAE extends ReportContentParseClass{
	
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
				a11vo.setEc_i(new  AcarsEC_EEVo_A11_A320_IAE(line));
			}else if(line.startsWith("EE")){
				a11vo.setEe_i(new  AcarsEC_EEVo_A11_A320_IAE(line));
			}else if(line.startsWith("N1")){
				a11vo.setN1_i(new  AcarsN1N2Vo_A11_A320_IAE(line));
			}else if(line.startsWith("N2")){
				a11vo.setN2_i(new  AcarsN1N2Vo_A11_A320_IAE(line));
			}else if(line.startsWith("S1")){
				a11vo.setS1_i(new  AcarsS1S2Vo_A11_A320_IAE(line));
			}else if(line.startsWith("S2")){
				a11vo.setS2_i(new  AcarsS1S2Vo_A11_A320_IAE(line));
			}else if(line.startsWith("T1")){
				a11vo.setT1_i(new  AcarsT1T2Vo_A11_A320_IAE(line));
			}else if(line.startsWith("T2")){
				a11vo.setT2_i(new  AcarsT1T2Vo_A11_A320_IAE(line));
			}else if(line.startsWith("V1")){
				a11vo.setV1_i(new  AcarsV1V2Vo_A11_A320_IAE(line));
			}else if(line.startsWith("V2")){
				a11vo.setV2_i(new  AcarsV1V2Vo_A11_A320_IAE(line));
			}else if(line.startsWith("V3")){
				a11vo.setV3_i(new  AcarsV3V4Vo_A11_A320_IAE(line));
			}else if(line.startsWith("V4")){
				a11vo.setV4_i(new  AcarsV3V4Vo_A11_A320_IAE(line));
			}
		
		}
		

		
		return a11vo;
	}
	
	public void insertA11(String msgno, AcarsDfdA11Vo_A320 a11vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a11iave25_list(ID,MSG_NO,ACNUM,RPTDATE,CODE," //5
				+ "ESN_1,EHRS_1,ECYC_1,AP_1,"
				+ "ESN_2,EHRS_2,ECYC_2,AP_2," //8
				
				+ "EPR_1,EGT_1,N1_1,N2_1,FF_1,P125_1,"
				+ "EPR_2,EGT_2,N1_2,N2_2,FF_2,P125_2," 
				
				+ "P25_1,T25_1,P3_1,T3_1,P49_1,SVA_1,"
				+ "P25_2,T25_2,P3_2,T3_2,P49_2,SVA_2," //24
				
				+ "BAF_1,ACC_1,LP_1,GLE_1,PD_1,TN_1,P2_1,T2_1,"
				+ "BAF_2,ACC_2,LP_2,GLE_2,PD_2,TN_2,P2_2,T2_2,"  //16
				
				+ "ECW1_1,ECW2_1,EVM_1,VB1_1,VB2_1,PHA_1,"
				+ "ECW1_2,ECW2_2,EVM_2,VB1_2,VB2_2,PHA_2," 
				
				+ "OIP_1,OIT_1,EGTK_1,N1K_1,N2K_1,FFK_1,"
				+ "OIP_2,OIT_2,EGTK_2,N1K_2,N2K_2,FFK_2," //24
				+ "UPDATE_DATE)"  //1
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,"
				+ "?,?,?,?,"
				+ "?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"
				
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"
				
				+ "?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,"
				
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"

				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "sysdate)";
		
		
		AcarsEC_EEVo_A11_A320_IAE ec = a11vo.getEc_i();
		AcarsEC_EEVo_A11_A320_IAE ee = a11vo.getEe_i();
		AcarsN1N2Vo_A11_A320_IAE n1 = a11vo.getN1_i();
		AcarsN1N2Vo_A11_A320_IAE n2 = a11vo.getN1_i();
		AcarsS1S2Vo_A11_A320_IAE s1 = a11vo.getS1_i();
		AcarsS1S2Vo_A11_A320_IAE s2 = a11vo.getS2_i();
		AcarsT1T2Vo_A11_A320_IAE t1 = a11vo.getT1_i();
		AcarsT1T2Vo_A11_A320_IAE t2 = a11vo.getT2_i();
		AcarsV1V2Vo_A11_A320_IAE v1 = a11vo.getV1_i();
		AcarsV1V2Vo_A11_A320_IAE v2 = a11vo.getV2_i();
		AcarsV3V4Vo_A11_A320_IAE v3 = a11vo.getV3_i();
		AcarsV3V4Vo_A11_A320_IAE v4 = a11vo.getV4_i();

//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		logger.debug(ApmsConst.DS_APMS+","+insertSql+","+ msgno+","+headVo.getAcid()+","+headVo.getDateUtc()+","+headVo.getCode()+","+
		        ec.getEsn()+","+ec.getEhrs()+","+ec.getEcyc()+","+ec.getAp()+","+
		        ee.getEsn()+","+ee.getEhrs()+","+ee.getEcyc()+","+ee.getAp()+","+
		        n1.getEpr()+","+n1.getEgt()+","+n1.getN1()+","+n1.getN2()+","+n1.getFf()+","+n1.getP125()+","+
		        n2.getEpr()+","+n2.getEgt()+","+n2.getN2()+","+n2.getN2()+","+n2.getFf()+","+n2.getP125()+","+
		        s1.getP25()+","+s1.getT25()+","+s1.getP3()+","+s1.getT3()+","+s1.getP49()+","+s1.getSva()+","+
		        s2.getP25()+","+s2.getT25()+","+s2.getP3()+","+s2.getT3()+","+s2.getP49()+","+s2.getSva()+","+
		        t1.getBaf()+","+t1.getAcc()+","+t1.getLp()+","+t1.getGle()+","+t1.getPd()+","+t1.getTn()+","+t1.getP2()+","+t1.getT2()+","+
		        t2.getBaf()+","+t2.getAcc()+","+t2.getLp()+","+t2.getGle()+","+t2.getPd()+","+t2.getTn()+","+t2.getP2()+","+t2.getT2()+","+
		        v1.getEcw1()+","+v1.getEcw2()+","+v1.getEvm()+","+v1.getVb1()+","+v1.getVb2()+","+v1.getPha()+","+
		        v2.getEcw1()+","+v2.getEcw2()+","+v2.getEvm()+","+v2.getVb1()+","+v2.getVb2()+","+v2.getPha()+","+
		        v3.getOip()+","+v3.getOit()+","+v3.getEgtk()+","+v3.getN1k()+","+v3.getN2k()+","+v3.getFfk()+","+
		        v4.getOip()+","+v4.getOit()+","+v4.getEgtk()+","+v4.getN1k()+","+v4.getN2k()+","+v4.getFfk());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),
				n1.getEpr(),n1.getEgt(),n1.getN1(),n1.getN2(),n1.getFf(),n1.getP125(),
				n2.getEpr(),n2.getEgt(),n2.getN2(),n2.getN2(),n2.getFf(),n2.getP125(),
				s1.getP25(),s1.getT25(),s1.getP3(),s1.getT3(),s1.getP49(),s1.getSva(),
				s2.getP25(),s2.getT25(),s2.getP3(),s2.getT3(),s2.getP49(),s2.getSva(),
				t1.getBaf(),t1.getAcc(),t1.getLp(),t1.getGle(),t1.getPd(),t1.getTn(),t1.getP2(),t1.getT2(),
				t2.getBaf(),t2.getAcc(),t2.getLp(),t2.getGle(),t2.getPd(),t2.getTn(),t2.getP2(),t2.getT2(),
				v1.getEcw1(),v1.getEcw2(),v1.getEvm(),v1.getVb1(),v1.getVb2(),v1.getPha(),
				v2.getEcw1(),v2.getEcw2(),v2.getEvm(),v2.getVb1(),v2.getVb2(),v2.getPha(),
				v3.getOip(),v3.getOit(),v3.getEgtk(),v3.getN1k(),v3.getN2k(),v3.getFfk(),
				v4.getOip(),v4.getOit(),v4.getEgtk(),v4.getN1k(),v4.getN2k(),v4.getFfk()
				);
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A11 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
