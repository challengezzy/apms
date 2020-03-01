package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA02Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a01a02.AcarsECVo_A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsEEVo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsN1N2Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsS1S2Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsT1T2Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsV1V2Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsV3V6Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsV7V8Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsX1X2Vo_A02_A320;
import com.apms.bs.dataprase.vo.a01a02.AcarsX3Vo_A02_A320;
import com.apms.bs.dataprase.vo.a01a02.AcarsX4X5Vo_A02_A320;
import com.apms.bs.dataprase.vo.a01a02.AcarsX6X7Vo_A02_A320;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * 
 * @author zzy
 * 
 */
public class A02DataParseImpl_A320_CFM extends ReportContentParseClass {

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno,
			String content, String trans_time) throws Exception {

		AcarsDfdA02Vo_A320 a02vo = praseA02Data(content, trans_time);
		insertA02(msgno, a02vo);

		AcarsParseResult res = new AcarsParseResult(
				ApmsVarConst.TELEGRAPH_PARSE_OK);

		return res;

	}

	/**
	 * 报文解析
	 * 
	 * @param graphStr
	 *            报文内容
	 * @param transdate
	 *            报文传送日期 YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	public AcarsDfdA02Vo_A320 praseA02Data(String graphStr, String transdate)
			throws Exception {
		AcarsDfdA02Vo_A320 a02vo = new AcarsDfdA02Vo_A320();

		String tmpStr = graphStr.replaceAll("[\n\r]", " ");

		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);

		logger.debug("报文每行数据如下：");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			//logger.debug("第" + i + "行:" + line);
			if (line.startsWith("EC")) {
				a02vo.setEc_c_a02(new AcarsECVo_A02_A320_CFM(line));
			} else if (line.startsWith("EE")) {
				a02vo.setEe_c(new AcarsEEVo_A01A02_A320_CFM(line));
			} else if (line.startsWith("N1")) {
				a02vo.setN1_c(new AcarsN1N2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("N2")) {
				a02vo.setN2_c(new AcarsN1N2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("S1")) {
				a02vo.setS1_c(new AcarsS1S2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("S2")) {
				a02vo.setS2_c(new AcarsS1S2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("T1")) {
				a02vo.setT1_c(new AcarsT1T2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("T2")) {
				a02vo.setT2_c(new AcarsT1T2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V1")) {
				a02vo.setV1_c(new AcarsV1V2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V2")) {
				a02vo.setV2_c(new AcarsV1V2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V3")) {
				a02vo.setV3_c(new AcarsV3V6Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V4")) {
				a02vo.setV4_c(new AcarsV3V6Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V5")) {
				a02vo.setV5_c(new AcarsV3V6Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V6")) {
				a02vo.setV6_c(new AcarsV3V6Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V7")) {
				a02vo.setV7_c(new AcarsV7V8Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V8")) {
				a02vo.setV8_c(new AcarsV7V8Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("X1")) {
				a02vo.setX1(new AcarsX1X2Vo_A02_A320(line));
			} else if (line.startsWith("X2")) {
				a02vo.setX2(new AcarsX1X2Vo_A02_A320(line));
			} else if (line.startsWith("X3")) {
				a02vo.setX3(new AcarsX3Vo_A02_A320(line));
			} else if (line.startsWith("X4")) {
				a02vo.setX4(new AcarsX4X5Vo_A02_A320(line));
			} else if (line.startsWith("X5")) {
				a02vo.setX5(new AcarsX4X5Vo_A02_A320(line));
			} else if (line.startsWith("X6")) {
				a02vo.setX6(new AcarsX6X7Vo_A02_A320(line));
			} else if (line.startsWith("X7")) {
				a02vo.setX7(new AcarsX6X7Vo_A02_A320(line));
			}

		}

		return a02vo;
	}

	public void insertA02(String msgno, AcarsDfdA02Vo_A320 a02vo)
			throws Exception {
		CommDMO dmo = new CommDMO();

		String insertSql = "insert into a_dfd_a02cfm56_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,QA_EC,QE_EC,"
				+ "ESN_2,EHRS_2,ERT_2,ECYC_2,AP_2," // EC/EE
				+ "N1_1,N1C_1,N2_1,EGT_1,FF_1,PS13_1,"
				+ "N1_2,N1C_2,N2_2,EGT_2,FF_2,PS13_2," // N1/N2
				+ "P25_1,T25_1,P3_1,T3_1,T5_1,VSV_1,VBV_1,"
				+ "P25_2,T25_2,P3_2,T3_2,T5_2,VSV_2,VBV_2,"// S1/S2
				+ "HPT_1,LPT_1,GLE_1,PD_1,TN_1,PT2_1,OIQH_1,"
				+ "HPT_2,LPT_2,GLE_2,PD_2,TN_2,PT2_2,OIQH_2," // T1/T2
				+ "VN_1,VL_1,PHA_1,PHT_1,VC_1,VH_1,EVM_1,"
				+ "VN_2,VL_2,PHA_2,PHT_2,VC_2,VH_2,EVM_2," // V1/V2
				+ "VN_1_SD,VL_1_SD,PHA_1_SD,PHT_1_SD,N1_1_SD,"
				+ "VN_2_SD,VL_2_SD,PHA_2_SD,PHT_2_SD,N1_2_SD,"// V3/V4
				+ "VN_1_SC,VL_1_SC,PHA_1_SC,PHT_1_SC,N1_1_SC,"
				+ "VN_2_SC,VL_2_SC,PHA_2_SC,PHT_2_SC,N1_2_SC," // V5/V6
				+ "OIP_1,OIT_1,ECW_1,SSEL_1,"
				+ "OIP_2,OIT_2,ECW_2,SSEL_2," // V7/V8
				+ "WFQ_1,ELEV_1,AOA_1,SLP_1,CFPG_1,CIVV_1,"
				+ "WFQ_2,ELEV_2,AOA_2,SLP_2,CFPG_2,CIVV_2," // X1/X2
				+ "RUDD,RUDT,AILL,AILR,STAB,ROLL,YAW," // X3
				+ "RSP2_1,RSP3_1,RSP4_1,RSP5_1,FLAP_1,SLAT_1,"
				+ "RSP2_2,RSP3_2,RSP4_2,RSP5_2,FLAP_2,SLAT_2," // X4/X5
				+ "THDG_1,LONP_1,LATP_1,WS_1,WD_1,FT_1,FD_1,"
				+ "THDG_2,LONP_2,LATP_2,WS_2,WD_2,FT_2,FD_2," // X6/X7
				+ "UPDATE_DATE)" // 1
				+ " values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,"
				+ "?,?,?,?,?,?,?," + "?,?,?,?,?,"// EC/EE
				+ "?,?,?,?,?,?," + "?,?,?,?,?,?,"// N1/N2
				+ "?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,"// S1/S2
				+ "?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,"// T1/T2
				+ "?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,"// V1/V2
				+ "?,?,?,?,?," + "?,?,?,?,?,"// V3/V4
				+ "?,?,?,?,?," + "?,?,?,?,?,"// V5/V6
				+ "?,?,?,?," + "?,?,?,?,"// V7/V8
				+ "?,?,?,?,?,?," + "?,?,?,?,?,?,"// X1/X2
				+ "?,?,?,?,?,?,"// X3
				+ "?,?,?,?,?,?," + "?,?,?,?,?,?,?,"// X4/X5
				+ "?,?,?,?,?,?,?," + "?,?,?,?,?,?,?,"// X6/X7
				+ "sysdate)";

		AcarsECVo_A02_A320_CFM ec = a02vo.getEc_c_a02();
		AcarsEEVo_A01A02_A320_CFM ee = a02vo.getEe_c();
		AcarsN1N2Vo_A01A02_A320_CFM n1 = a02vo.getN1_c();
		AcarsN1N2Vo_A01A02_A320_CFM n2 = a02vo.getN2_c();
		AcarsS1S2Vo_A01A02_A320_CFM s1 = a02vo.getS1_c();
		AcarsS1S2Vo_A01A02_A320_CFM s2 = a02vo.getS2_c();
		AcarsT1T2Vo_A01A02_A320_CFM t1 = a02vo.getT1_c();
		AcarsT1T2Vo_A01A02_A320_CFM t2 = a02vo.getT2_c();
		AcarsV1V2Vo_A01A02_A320_CFM v1 = a02vo.getV1_c();
		AcarsV1V2Vo_A01A02_A320_CFM v2 = a02vo.getV2_c();
		AcarsV3V6Vo_A01A02_A320_CFM v3 = a02vo.getV3_c();
		AcarsV3V6Vo_A01A02_A320_CFM v4 = a02vo.getV4_c();
		AcarsV3V6Vo_A01A02_A320_CFM v5 = a02vo.getV5_c();
		AcarsV3V6Vo_A01A02_A320_CFM v6 = a02vo.getV6_c();
		AcarsV7V8Vo_A01A02_A320_CFM v7 = a02vo.getV7_c();
		AcarsV7V8Vo_A01A02_A320_CFM v8 = a02vo.getV8_c();
		AcarsX1X2Vo_A02_A320 x1 = a02vo.getX1();
		AcarsX1X2Vo_A02_A320 x2 = a02vo.getX2();
		AcarsX3Vo_A02_A320 x3 = a02vo.getX3();
		AcarsX4X5Vo_A02_A320 x4 = a02vo.getX4();
		AcarsX4X5Vo_A02_A320 x5 = a02vo.getX5();
		AcarsX6X7Vo_A02_A320 x6 = a02vo.getX6();
		AcarsX6X7Vo_A02_A320 x7 = a02vo.getX7();

		// 报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);

		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,
				headVo.getAcid(), headVo.getDateUtc(), headVo.getCode(),
				ec.getEsn(), ec.getEhrs(), ec.getErt(), ec.getEcyc(),
				ec.getAp(), ec.getQa(), ec.getQe(), ee.getEsn(), ee.getEhrs(),
				ee.getErt(), ee.getEcyc(), ee.getAp(), n1.getN1(), n1.getN1c(),
				n1.getN2(), n1.getEgt(), n1.getFf(), n1.getPs13(), n2.getN1(),
				n2.getN1c(), n2.getN2(), n2.getEgt(), n2.getFf(), n2.getPs13(),
				s1.getP25(), s1.getT25(), s1.getP3(), s1.getT3(), s1.getT5(),
				s1.getVsv(), s1.getVbv(), s2.getP25(), s2.getT25(), s2.getP3(),
				s2.getT3(), s2.getT5(), s2.getVsv(), s2.getVbv(), t1.getHpt(),
				t1.getLpt(), t1.getGle(), t1.getPd(), t1.getTn(), t1.getPt2(),
				t1.getOiqh(), t2.getHpt(), t2.getLpt(), t2.getGle(),
				t2.getPd(), t2.getTn(), t2.getPt2(), t2.getOiqh(), v1.getVn(),
				v1.getVl(), v1.getPha(), v1.getPht(), v1.getVc(), v1.getVh(),
				v1.getEvm(), v2.getVn(), v2.getVl(), v2.getPha(), v2.getPht(),
				v2.getVc(), v2.getVh(), v2.getEvm(), v3.getVn(), v3.getVl(),
				v3.getPha(), v3.getPht(), v3.getN1(), v4.getVn(), v4.getVl(),
				v4.getPha(), v4.getPht(), v4.getN1(), v5.getVn(), v5.getVl(),
				v5.getPha(), v5.getPht(), v5.getN1(), v6.getVn(), v6.getVl(),
				v6.getPha(), v6.getPht(), v6.getN1(), v7.getOip(), v7.getOit(),
				v7.getEcw1(), v7.getSsel(), v8.getOip(), v8.getOit(),
				v7.getEcw1(), v8.getSsel(), x1.getWfq(), x1.getElev(),
				x1.getAoa(), x1.getSlp(), x1.getCfpg(), x1.getCivv(),
				x2.getWfq(), x2.getElev(), x2.getAoa(), x2.getSlp(),
				x2.getCfpg(), x2.getCivv(), x3.getRudd(), x3.getRudt(),
				x3.getAill(), x3.getAilr(), x3.getStab(), x3.getRoll(),
				x3.getYaw(), x4.getRsp2(), x4.getRsp3(), x4.getRsp4(),
				x4.getRsp5(), x4.getFlap(), x4.getSlat(), x5.getRsp2(),
				x5.getRsp3(), x5.getRsp4(), x5.getRsp5(), x5.getFlap(),
				x5.getSlat(), x6.getThdg(), x6.getLonp(), x6.getLatp(),
				x6.getWs(), x6.getWd(), x6.getFt(), x6.getFd(), x7.getThdg(),
				x7.getLonp(), x7.getLatp(), x7.getWs(), x7.getWd(), x7.getFt(),
				x7.getFd());

		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A02 报文[msg_no]=[" + msgno + "]入库成功！");

	}

}
