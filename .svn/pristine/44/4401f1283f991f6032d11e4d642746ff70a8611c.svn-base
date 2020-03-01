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
import com.apms.bs.dataprase.vo.a19a21a24.AcarsM1Vo_A21_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsM2Vo_A21_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsSxTxVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsVxVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsW1Vo_A21_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsXxVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsZRVo_A21_A320;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

public class A21DataParseImpl_A320 extends ReportContentParseClass {

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		AcarsDfdA21Vo_A320 a21vo = praseA21Data(content, trans_time);
		insertA21(msgno, a21vo);
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);

		return res;

	}

	protected AcarsDfdA21Vo_A320 praseA21Data(String gStrA21, String transdate)
			throws Exception {
		AcarsDfdA21Vo_A320 a21vo = new AcarsDfdA21Vo_A320();
		String tmpStr = gStrA21;
		String[] lines;
//		if (tmpStr.indexOf("BLEED RPT <21>") == -1) {
			tmpStr = gStrA21.replaceAll("[\n\r]", " ");
			lines = StringUtil.splitString2Array(tmpStr, "/", true);
//		} else {
//			tmpStr = gStrA21.replaceAll("[\n\r]", "/");
//			lines = StringUtil.splitString2Array(tmpStr, "/", true);
//		}
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();

			if (line.startsWith("EC")) {
				a21vo.setEc(new AcarsECVo_A21_A320(line));
			} else if (line.startsWith("EE")) {
				a21vo.setEe(new AcarsEEVo_A21_A320(line));
			} else if (line.startsWith("E1")) {
				a21vo.setE1(new AcarsExNxVo_A19_A320(line, " "));
			} else if (line.startsWith("N1")) {
				if (line.length() > 12) {
					a21vo.setN1(new AcarsExNxVo_A19_A320(line, " "));
				} else {
					a21vo.setM2(new AcarsM2Vo_A21_A320(line));
				}
			} else if (line.startsWith("S1")) {
				a21vo.setS1(new AcarsSxTxVo_A19_A320(line, " "));
			} else if (line.startsWith("T1")) {
				a21vo.setT1(new AcarsSxTxVo_A19_A320(line, " "));
			} else if (line.startsWith("V1")) {
				a21vo.setV1(new AcarsVxVo_A19_A320(line, " "));
			} else if (line.startsWith("X1")) {
				a21vo.setX1(new AcarsXxVo_A19_A320(line, " "));
			} else if (line.startsWith("W1")) {
				a21vo.setW1(new AcarsW1Vo_A21_A320(line));
			} else if (line.startsWith("M1")) {
				a21vo.setM1(new AcarsM1Vo_A21_A320(line));
			} else if (line.startsWith("M2")) {
				a21vo.setM2(new AcarsM2Vo_A21_A320(line));
			} else if (line.startsWith("Z1")) {
				a21vo.setZ1(new AcarsZRVo_A21_A320(line));
			} else if (line.startsWith("R1")) {
				a21vo.setR1(new AcarsZRVo_A21_A320(line));
			} else if (line.startsWith("D1")) {
				a21vo.setD1(new AcarsD1Vo_A21_A320(line));
			} else if (line.startsWith("F1")) {
				a21vo.setF1(new AcarsF1Vo_A21_A320(line));
			} else if (line.startsWith("G1")) {
				a21vo.setG1(new AcarsG1Vo_A21_A320(line));
			} else if (line.startsWith("H1")) {
				a21vo.setH1(new AcarsH1Vo_A21_A320(line));
			}

		}

		// System.out.println("hello v1 "+a14vo.getV1().getNpa());
		return a21vo;

	}

	public void insertA21(String msgno, AcarsDfdA21Vo_A320 a21vo)
			throws Exception {
		// String acmodel=ac_model;

		CommDMO dmo = new CommDMO();
		AcarsECVo_A21_A320 ec = a21vo.getEc();
		AcarsEEVo_A21_A320 ee = a21vo.getEe();
		AcarsExNxVo_A19_A320 e1 = a21vo.getE1();
		AcarsExNxVo_A19_A320 n1 = a21vo.getN1();
		AcarsSxTxVo_A19_A320 s1 = a21vo.getS1();
		AcarsSxTxVo_A19_A320 t1 = a21vo.getT1();
		AcarsVxVo_A19_A320 v1 = a21vo.getV1();
		AcarsXxVo_A19_A320 x1 = a21vo.getX1();
		AcarsW1Vo_A21_A320 w1 = a21vo.getW1();
		AcarsM1Vo_A21_A320 m1 = a21vo.getM1();
		AcarsM2Vo_A21_A320 m2 = a21vo.getM2();
		AcarsZRVo_A21_A320 z1 = a21vo.getZ1();
		AcarsZRVo_A21_A320 r1 = a21vo.getR1();
		AcarsD1Vo_A21_A320 d1 = a21vo.getD1();
		AcarsF1Vo_A21_A320 f1 = a21vo.getF1();
		AcarsG1Vo_A21_A320 g1 = a21vo.getG1();
		AcarsH1Vo_A21_A320 h1 = a21vo.getH1();

		String insertSql = "insert into a_dfd_a21_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ECYC_1,AP_1,Y1_1,NL_1,LIM_1,"
				+ "ESN_2,EHRS_2,ECYC_2,AP_2,Y1_2,LIM_2," // 18
				+ "N1_E1,N2_E1,PF_E1,COT_E1,RI_E1,RO_E1,PBV_E1,FCV_E1,"
				+ "N1_N1,N2_N1,PF_N1,COT_N1,RI_N1,RO_N1,PBV_N1,FCV_N1,"
				+ "P3_S1,T3_S1,TW_S1,TP_S1,TPO_S1,PD_S1,ALT_S1,PS_S1,"
				+ "P3_T1,T3_T1,TW_T1,TP_T1,TPO_T1,PD_T1,ALT_T1,PS_T1,"
				+ "TAT_V1,SAT_V1,ZCB_V1,ZLD_V1,SC1_V1,SC2_V1,SC3_V1,RV_V1,"
				+ "PCSW_X1,VSCB_X1,PDC_X1,VF_X1,VW_X1,VA_X1,OVP_X1,CPC_X1," // 48
				+ "PB_WAI_W1,PB_PRV1_W1,PB_PRV2_W1,SW_XFR_W1,"
				+ "PIN_HPV_M1,PIN_PRV_M1,OPV1_M1,OPV2_M1,"
				+ "PIN_HPV_M2,PIN_PRV_M2," // 10
				+ "FAV1_Z1,FAV2_Z1,HPV1_Z1,HPV2_Z1,PRV1_Z1,PRV2_Z1,"
				+ "FAV1_R1,FAV2_R1,HPV1_R1,HPV2_R1,PRV1_R1,PRV2_R1,"
				+ "PDMT_L_D1,PDMT_R_D1,"
				+ "CKT_F1,FWDT_F1,AFTT_F1,HOTAIRPB_F1,"
				+ "CKDUCT_G1,FWDUCT_G1,AFTDUCT_G1,MIXF_G1,MIXCAB_G1,"
				+ "TAPRV_H1,TAV_H1,MAINCTL_H1,SECDCTL_H1,"
				+ "UPDATE_DATE)" // 13

				+ "values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,";
		if (d1 == null) {
			insertSql = insertSql
					+ "0,0,0,0,0,null,0,0,0,0,0,null,null,null,null,sysdate)";
			headParseClass.insertHeadData(msgno, headVo);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,
					headVo.getAcid(), headVo.getDateUtc(), headVo.getCode(),
					ec.getEsn(), ec.getEhrs(), ec.getEcyc(), ec.getAp(),
					ec.getY1(), ec.getNl(), ec.getLim(), ee.getEsn(),
					ee.getEhrs(), ee.getEcyc(), ee.getAp(), ee.getY1(),
					ee.getLim(), e1.getN1(), e1.getN2(), e1.getPf(),
					e1.getCot(), e1.getRi(), e1.getRo(), e1.getPbv(),
					e1.getFcv(), n1.getN1(), n1.getN2(), n1.getPf(),
					n1.getCot(), n1.getRi(), n1.getRo(), n1.getPbv(),
					n1.getFcv(), s1.getP3(), s1.getT3(), s1.getTw(),
					s1.getTp(), s1.getTpo(), s1.getPd(), s1.getAlt(),
					s1.getPs(), t1.getP3(), t1.getT3(), t1.getTw(), t1.getTp(),
					t1.getTpo(), t1.getPd(), t1.getAlt(), t1.getPs(),
					v1.getTat(), v1.getSat(), v1.getZcb(), v1.getZld(),
					v1.getSc1(), v1.getSc2(), v1.getSc3(), v1.getRv(),
					x1.getPcsw(), x1.getVscb(), x1.getPdc(), x1.getVf(),
					x1.getVw(), x1.getVa(), x1.getOvp(), x1.getCpc(),
					w1.getPb_wai(), w1.getPb_prv1(), w1.getPb_prv2(),
					w1.getSw_xfr(), m1.getPin_hpv(), m1.getPin_prv(),
					m1.getOpv1(), m1.getOpv2(), m2.getPin_hpv(),
					m2.getPin_prv(), z1.getFav1(), z1.getFav2(), z1.getHpv1(),
					z1.getHpv2(), z1.getPrv1(), z1.getPrv2(), r1.getFav1(),
					r1.getFav2(), r1.getHpv1(), r1.getHpv2(), r1.getPrv1(),
					r1.getPrv2());
		} else {
			insertSql = insertSql + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
			headParseClass.insertHeadData(msgno, headVo);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,
					headVo.getAcid(), headVo.getDateUtc(), headVo.getCode(),
					ec.getEsn(), ec.getEhrs(), ec.getEcyc(), ec.getAp(),
					ec.getY1(), ec.getNl(), ec.getLim(), ee.getEsn(),
					ee.getEhrs(), ee.getEcyc(), ee.getAp(), ee.getY1(),
					ee.getLim(), e1.getN1(), e1.getN2(), e1.getPf(),
					e1.getCot(), e1.getRi(), e1.getRo(), e1.getPbv(),
					e1.getFcv(), n1.getN1(), n1.getN2(), n1.getPf(),
					n1.getCot(), n1.getRi(), n1.getRo(), n1.getPbv(),
					n1.getFcv(), s1.getP3(), s1.getT3(), s1.getTw(),
					s1.getTp(), s1.getTpo(), s1.getPd(), s1.getAlt(),
					s1.getPs(), t1.getP3(), t1.getT3(), t1.getTw(), t1.getTp(),
					t1.getTpo(), t1.getPd(), t1.getAlt(), t1.getPs(),
					v1.getTat(), v1.getSat(), v1.getZcb(), v1.getZld(),
					v1.getSc1(), v1.getSc2(), v1.getSc3(), v1.getRv(),
					x1.getPcsw(), x1.getVscb(), x1.getPdc(), x1.getVf(),
					x1.getVw(), x1.getVa(), x1.getOvp(), x1.getCpc(),
					w1.getPb_wai(), w1.getPb_prv1(), w1.getPb_prv2(),
					w1.getSw_xfr(), m1.getPin_hpv(), m1.getPin_prv(),
					m1.getOpv1(), m1.getOpv2(), m2.getPin_hpv(),
					m2.getPin_prv(), z1.getFav1(), z1.getFav2(), z1.getHpv1(),
					z1.getHpv2(), z1.getPrv1(), z1.getPrv2(), r1.getFav1(),
					r1.getFav2(), r1.getHpv1(), r1.getHpv2(), r1.getPrv1(),
					r1.getPrv2(), d1.getPdmt_l(), d1.getPdmt_r(), f1.getCkt(),
					f1.getFwdt(), f1.getAftt(), f1.getHotairpb(),
					g1.getCkduct(), g1.getFwduct(), g1.getAftduct(),
					g1.getMixf(), g1.getMixcab(), h1.getTaprv(), h1.getTav(),
					h1.getMAINCTL_H1(), h1.getSECDCTL_H1());
		}

		x1.insertToTable(msgno, headVo, 1, acmodel, rptno);

		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A21_A320报文[msg_no]=[" + msgno + "]入库成功！");

	}

}
