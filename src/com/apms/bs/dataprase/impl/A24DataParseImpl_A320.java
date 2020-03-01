package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.*;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsD1Vo_A21_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsECVo_A21_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsEEVo_A21_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsExNxVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsF1Vo_A21_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsG1Vo_A21_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsH1Vo_A21_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsSxTxVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsVxVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsXxVo_A19_A320;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;


public class A24DataParseImpl_A320 extends ReportContentParseClass{
	
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		AcarsDfdA24Vo_A320 a24vo = praseA24Data(content,trans_time);		
		insertA24(msgno, a24vo);
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		return res;
		
	}
	
	protected AcarsDfdA24Vo_A320 praseA24Data(String gStrA24,String transdate) throws Exception{
		AcarsDfdA24Vo_A320 a24vo = new AcarsDfdA24Vo_A320();
		String tmpStr = gStrA24.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);	
		
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
			if(line.startsWith("EC")){				
				a24vo.setEc(new AcarsECVo_A21_A320(line));
			}else if(line.startsWith("EE")){				
				a24vo.setEe(new AcarsEEVo_A21_A320(line));
			}else if(line.startsWith("E1")){
				a24vo.setE1(new AcarsExNxVo_A19_A320(line,""));			
			}else if(line.startsWith("N1")){
				a24vo.setN1(new AcarsExNxVo_A19_A320(line,""));				
			}else if(line.startsWith("S1")){				
				a24vo.setS1(new AcarsSxTxVo_A19_A320(line,""));	
			}else if(line.startsWith("T1")){				
				a24vo.setT1(new AcarsSxTxVo_A19_A320(line,""));	
			}else if(line.startsWith("V1")){				
				a24vo.setV1(new AcarsVxVo_A19_A320(line,""));	
			}else if(line.startsWith("X1")){				
				a24vo.setX1(new AcarsXxVo_A19_A320(line,""));	
			}else if (line.startsWith("D1")) {
				a24vo.setD1(new AcarsD1Vo_A21_A320(line));
			} else if (line.startsWith("F1")) {
				a24vo.setF1(new AcarsF1Vo_A21_A320(line));
			} else if (line.startsWith("G1")) {
				a24vo.setG1(new AcarsG1Vo_A21_A320(line));
			} else if (line.startsWith("H1")) {
				a24vo.setH1(new AcarsH1Vo_A21_A320(line));
			}			
		}
		return a24vo;	
	}
	public void insertA24(String msgno, AcarsDfdA24Vo_A320 a24vo) throws Exception{
		AcarsECVo_A21_A320 ec = a24vo.getEc(); 
		AcarsEEVo_A21_A320 ee = a24vo.getEe();
		AcarsExNxVo_A19_A320 e1 = a24vo.getE1();
		AcarsExNxVo_A19_A320 n1 = a24vo.getN1();
		AcarsSxTxVo_A19_A320 s1 = a24vo.getS1();
		AcarsSxTxVo_A19_A320 t1 = a24vo.getT1();
		AcarsVxVo_A19_A320 v1 = a24vo.getV1();
		AcarsXxVo_A19_A320 x1 = a24vo.getX1();
		AcarsD1Vo_A21_A320 d1 = a24vo.getD1();
		AcarsF1Vo_A21_A320 f1 = a24vo.getF1();
		AcarsG1Vo_A21_A320 g1 = a24vo.getG1();
		AcarsH1Vo_A21_A320 h1 = a24vo.getH1();
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a24_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
			+ "ESN_1,EHRS_1,ECYC_1,AP_1,Y1_1,NL_1,LIM_1,"
			+ "ESN_2,EHRS_2,ECYC_2,AP_2,Y1_2,LIM_2," //18
			+ "N1_E1,N2_E1,PF_E1,COT_E1,RI_E1,RO_E1,PBV_E1,FCV_E1,"
			+ "N1_N1,N2_N1,PF_N1,COT_N1,RI_N1,RO_N1,PBV_N1,FCV_N1,"
			+ "P3_S1,T3_S1,TW_S1,TP_S1,TPO_S1,PD_S1,ALT_S1,PS_S1,"
			+ "P3_T1,T3_T1,TW_T1,TP_T1,TPO_T1,PD_T1,ALT_T1,PS_T1,"
			+ "TAT_V1,SAT_V1,ZCB_V1,ZLD_V1,SC1_V1,SC2_V1,SC3_V1,RV_V1,"
			+ "PCSW_X1,VSCB_X1,PDC_X1,VF_X1,VW_X1,VA_X1,OVP_X1,CPC_X1," //48
			+ "PDMT_L_D1,PDMT_R_D1,"
			+ "CKT_F1,FWDT_F1,AFTT_F1,HOTAIRPB_F1,"
			+ "CKDUCT_G1,FWDUCT_G1,AFTDUCT_G1,MIXF_G1,MIXCAB_G1,"
			+ "TAPRV_H1,TAV_H1,MAINCTL_H1,SECDCTL_H1,"
			+ "UPDATE_DATE)" 
			+ " values(S_A_DFD_HEAD.nextval," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,";
		if (d1 == null) {
			insertSql = insertSql
					+ "null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,sysdate)";
		    headParseClass.insertHeadData(msgno, headVo);
		    dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),ec.getY1(),ec.getNl(),ec.getLim(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),ee.getY1(),ee.getLim(),
				e1.getN1(),e1.getN2(),e1.getPf(),e1.getCot(),e1.getRi(),e1.getRo(),e1.getPbv(),e1.getFcv(),
				n1.getN1(),n1.getN2(),n1.getPf(),n1.getCot(),n1.getRi(),n1.getRo(),n1.getPbv(),n1.getFcv(),
				s1.getP3(),s1.getT3(),s1.getTw(),s1.getTp(),s1.getTpo(),s1.getPd(),s1.getAlt(),s1.getPs(),
				t1.getP3(),t1.getT3(),t1.getTw(),t1.getTp(),t1.getTpo(),t1.getPd(),t1.getAlt(),t1.getPs(),
				v1.getTat(),v1.getSat(),v1.getZcb(),v1.getZld(),v1.getSc1(),v1.getSc2(),v1.getSc3(),v1.getRv(),
				x1.getPcsw(),x1.getVscb(),x1.getPdc(),x1.getVf(),x1.getVw(),x1.getVa(),x1.getOvp(),x1.getCpc());
		
		}
		else{
			insertSql = insertSql + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			headParseClass.insertHeadData(msgno, headVo);
			 dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
						ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),ec.getY1(),ec.getNl(),ec.getLim(),
						ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),ee.getY1(),ee.getLim(),
						e1.getN1(),e1.getN2(),e1.getPf(),e1.getCot(),e1.getRi(),e1.getRo(),e1.getPbv(),e1.getFcv(),
						n1.getN1(),n1.getN2(),n1.getPf(),n1.getCot(),n1.getRi(),n1.getRo(),n1.getPbv(),n1.getFcv(),
						s1.getP3(),s1.getT3(),s1.getTw(),s1.getTp(),s1.getTpo(),s1.getPd(),s1.getAlt(),s1.getPs(),
						t1.getP3(),t1.getT3(),t1.getTw(),t1.getTp(),t1.getTpo(),t1.getPd(),t1.getAlt(),t1.getPs(),
						v1.getTat(),v1.getSat(),v1.getZcb(),v1.getZld(),v1.getSc1(),v1.getSc2(),v1.getSc3(),v1.getRv(),
						x1.getPcsw(),x1.getVscb(),x1.getPdc(),x1.getVf(),x1.getVw(),x1.getVa(),x1.getOvp(),x1.getCpc(),
						d1.getPdmt_l(), d1.getPdmt_r(),
						f1.getCkt(),f1.getFwdt(), f1.getAftt(), f1.getHotairpb(),
						g1.getCkduct(), g1.getFwduct(), g1.getAftduct(),g1.getMixf(), g1.getMixcab(),
						h1.getTaprv(), h1.getTav(),h1.getMAINCTL_H1(), h1.getSECDCTL_H1());
		}
		x1.insertToTable(msgno, headVo, 1, acmodel,rptno);
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A24_A320报文[msg_no]=["+msgno+"]入库成功！");
		
	}
		
		

}
