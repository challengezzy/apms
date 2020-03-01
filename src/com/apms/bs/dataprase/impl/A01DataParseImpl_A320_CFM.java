package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA01Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a01a02.AcarsECVo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsEEVo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsN1N2Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsS1S2Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsT1T2Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsV1V2Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsV3V6Vo_A01A02_A320_CFM;
import com.apms.bs.dataprase.vo.a01a02.AcarsV7V8Vo_A01A02_A320_CFM;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * 
 * @author zzy
 * 
 */
public class A01DataParseImpl_A320_CFM extends ReportContentParseClass {

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno,
			String content, String trans_time) throws Exception {

		AcarsDfdA01Vo_A320 a01vo = praseA01Data(content, trans_time);
		insertA01(msgno, a01vo);

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
	public AcarsDfdA01Vo_A320 praseA01Data(String graphStr, String transdate)
			throws Exception {
		AcarsDfdA01Vo_A320 a01vo = new AcarsDfdA01Vo_A320();

		String tmpStr = graphStr.replaceAll("[\n\r]", " ");

		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);

		//logger.debug("报文每行数据如下：");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			//logger.debug("第" + i + "行:" + line);

			if (line.startsWith("EC")) {
				a01vo.setEc_c(new AcarsECVo_A01A02_A320_CFM(line));
				System.out.println(a01vo.getEc_c());
			} else if (line.startsWith("EE")) {
				a01vo.setEe_c(new AcarsEEVo_A01A02_A320_CFM(line));
			} else if (line.startsWith("N1")) {
				a01vo.setN1_c(new AcarsN1N2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("N2")) {
				a01vo.setN2_c(new AcarsN1N2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("S1")) {
				a01vo.setS1_c(new AcarsS1S2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("S2")) {
				a01vo.setS2_c(new AcarsS1S2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("T1")) {
				a01vo.setT1_c(new AcarsT1T2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("T2")) {
				a01vo.setT2_c(new AcarsT1T2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V1")) {
				a01vo.setV1_c(new AcarsV1V2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V2")) {
				a01vo.setV2_c(new AcarsV1V2Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V3")) {
				a01vo.setV3_c(new AcarsV3V6Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V4")) {
				a01vo.setV4_c(new AcarsV3V6Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V5")) {
				a01vo.setV5_c(new AcarsV3V6Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V6")) {
				a01vo.setV6_c(new AcarsV3V6Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V7")) {
				a01vo.setV7_c(new AcarsV7V8Vo_A01A02_A320_CFM(line));
			} else if (line.startsWith("V8")) {
				a01vo.setV8_c(new AcarsV7V8Vo_A01A02_A320_CFM(line));
			}

		}

		return a01vo;
	}

	public void insertA01(String msgno, AcarsDfdA01Vo_A320 a01vo)
			throws Exception {
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a01cfm56_5b_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,QE_EC,"
				+ "ESN_2,EHRS_2,ERT_2,ECYC_2,AP_2," // 16
				+ "N1_1,N1C_1,N2_1,EGT_1,FF_1,PS13_1,"
				+ "N1_2,N1C_2,N2_2,EGT_2,FF_2,PS13_2," // 12
				+ "P25_1,T25_1,P3_1,T3_1,T5_1,VSV_1,VBV_1,"
				+ "P25_2,T25_2,P3_2,T3_2,T5_2,VSV_2,VBV_2,"
				+ "HPT_1,LPT_1,GLE_1,PD_1,TN_1,PT2_1,OIQH_1,"
				+ "HPT_2,LPT_2,GLE_2,PD_2,TN_2,PT2_2,OIQH_2,"
				+ "VN_1,VL_1,PHA_1,PHT_1,VC_1,VH_1,EVM_1,"
				+ "VN_2,VL_2,PHA_2,PHT_2,VC_2,VH_2,EVM_2," // 42
				+ "VN_1_SD,VL_1_SD,PHA_1_SD,PHT_1_SD,N1_1_SD,"
				+ "VN_2_SD,VL_2_SD,PHA_2_SD,PHT_2_SD,N1_2_SD,"
				+ "VN_1_SC,VL_1_SC,PHA_1_SC,PHT_1_SC,N1_1_SC,"
				+ "VN_2_SC,VL_2_SC,PHA_2_SC,PHT_2_SC,N1_2_SC," // 20
				+ "OIP_1,OIT_1,ECW1,SSEL_1,"
				+ "OIP_2,OIT_2,ECW2,SSEL_2,"
				+ "UPDATE_DATE)" // 9
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
				+ "?,?,?,?,?,?,?,sysdate)";

		AcarsECVo_A01A02_A320_CFM ec = a01vo.getEc_c();
		AcarsEEVo_A01A02_A320_CFM ee = a01vo.getEe_c();
		AcarsN1N2Vo_A01A02_A320_CFM n1 = a01vo.getN1_c();
		AcarsN1N2Vo_A01A02_A320_CFM n2 = a01vo.getN2_c();
		AcarsS1S2Vo_A01A02_A320_CFM s1 = a01vo.getS1_c();
		AcarsS1S2Vo_A01A02_A320_CFM s2 = a01vo.getS2_c();
		AcarsT1T2Vo_A01A02_A320_CFM t1 = a01vo.getT1_c();
		AcarsT1T2Vo_A01A02_A320_CFM t2 = a01vo.getT2_c();
		AcarsV1V2Vo_A01A02_A320_CFM v1 = a01vo.getV1_c();
		AcarsV1V2Vo_A01A02_A320_CFM v2 = a01vo.getV2_c();
		AcarsV3V6Vo_A01A02_A320_CFM v3 = a01vo.getV3_c();
		AcarsV3V6Vo_A01A02_A320_CFM v4 = a01vo.getV4_c();
		AcarsV3V6Vo_A01A02_A320_CFM v5 = a01vo.getV5_c();
		AcarsV3V6Vo_A01A02_A320_CFM v6 = a01vo.getV6_c();
		AcarsV7V8Vo_A01A02_A320_CFM v7 = a01vo.getV7_c();
		AcarsV7V8Vo_A01A02_A320_CFM v8 = a01vo.getV8_c();

		// 报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);

		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,
				headVo.getAcid(), headVo.getDateUtc(), headVo.getCode(),
				ec.getEsn(), ec.getEhrs(), ec.getErt(), ec.getEcyc(),
				ec.getAp(), ec.getQe(), ee.getEsn(), ee.getEhrs(), ee.getErt(),
				ee.getEcyc(), ee.getAp(), n1.getN1(), n1.getN1c(), n1.getN2(),
				n1.getEgt(), n1.getFf(), n1.getPs13(), n2.getN1(), n2.getN1c(),
				n2.getN2(), n2.getEgt(), n2.getFf(), n2.getPs13(), s1.getP25(),
				s1.getT25(), s1.getP3(), s1.getT3(), s1.getT5(), s1.getVsv(),
				s1.getVbv(), s2.getP25(), s2.getT25(), s2.getP3(), s2.getT3(),
				s2.getT5(), s2.getVsv(), s2.getVbv(), t1.getHpt(), t1.getLpt(),
				t1.getGle(), t1.getPd(), t1.getTn(), t1.getPt2(), t1.getOiqh(),
				t2.getHpt(), t2.getLpt(), t2.getGle(), t2.getPd(), t2.getTn(),
				t2.getPt2(), t2.getOiqh(), v1.getVn(), v1.getVl(), v1.getPha(),
				v1.getPht(), v1.getVc(), v1.getVh(), v1.getEvm(), v2.getVn(),
				v2.getVl(), v2.getPha(), v2.getPht(), v2.getVc(), v2.getVh(),
				v2.getEvm(), v3.getVn(), v3.getVl(), v3.getPha(), v3.getPht(),
				v3.getN1(), v4.getVn(), v4.getVl(), v4.getPha(), v4.getPht(),
				v4.getN1(), v5.getVn(), v5.getVl(), v5.getPha(), v5.getPht(),
				v5.getN1(), v6.getVn(), v6.getVl(), v6.getPha(), v6.getPht(),
				v6.getN1(), v7.getOip(), v7.getOit(), v7.getEcw1(),
				v7.getSsel(), v8.getOip(), v8.getOit(), v8.getEcw1(),
				v8.getSsel());

		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A01 报文[msg_no]=[" + msgno + "]入库成功！");

	}

}
