package com.apms.bs.dataprase.impl2;

import com.apms.bs.dataprase.impl.A26DataParseImpl_A320;
import com.apms.bs.dataprase.vo.AcarsDfdA26Vo_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsECVo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsEEVo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsN1_N2Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsR1Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsS1_S2Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsT1_T2Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsV1_V2Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsV3_V4Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsV5Vo_A26_A320;
import com.apms.bs.util.StringUtil;

public class A26DataParseImpl_A320_REP extends A26DataParseImpl_A320 {

	protected AcarsDfdA26Vo_A320 praseA26Data(String gStrA26, String transdate) throws Exception {
		AcarsDfdA26Vo_A320 a26vo = new AcarsDfdA26Vo_A320();

		String[] lines = StringUtil.splitString2Array(gStrA26, "/", true);

		boolean isRep = true;
		//logger.debug("报文每行数据如下：");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();

			if (line.startsWith("EC")) {
				a26vo.setEc(new AcarsECVo_A26_A320(line));
			} else if (line.startsWith("EE")) {
				a26vo.setEe(new AcarsEEVo_A26_A320(line));
			} else if (line.startsWith("N1")) {
				a26vo.setN1(new AcarsN1_N2Vo_A26_A320(line, isRep));
			} else if (line.startsWith("N2")) {
				a26vo.setN2(new AcarsN1_N2Vo_A26_A320(line, isRep));
			} else if (line.startsWith("S1")) {
				a26vo.setS1(new AcarsS1_S2Vo_A26_A320(line, isRep));
			} else if (line.startsWith("S2")) {
				a26vo.setS2(new AcarsS1_S2Vo_A26_A320(line, isRep));
			} else if (line.startsWith("T1")) {
				a26vo.setT1(new AcarsT1_T2Vo_A26_A320(line, isRep));
			} else if (line.startsWith("T2")) {
				a26vo.setT2(new AcarsT1_T2Vo_A26_A320(line, isRep));
			} else if (line.startsWith("V1")) {
				a26vo.setV1(new AcarsV1_V2Vo_A26_A320(line, isRep));
			} else if (line.startsWith("V2")) {
				a26vo.setV2(new AcarsV1_V2Vo_A26_A320(line, isRep));
			} else if (line.startsWith("V3")) {
				a26vo.setV3(new AcarsV3_V4Vo_A26_A320(line, isRep));
			} else if (line.startsWith("V4")) {
				a26vo.setV4(new AcarsV3_V4Vo_A26_A320(line, isRep));
			} else if (line.startsWith("V5")) {
				a26vo.setV5(new AcarsV5Vo_A26_A320(line, isRep));
			} else if (line.startsWith("R1")) {
				a26vo.setR1(new AcarsR1Vo_A26_A320(line));
			}

		}

		// System.out.println("hello n1 "+a14vo.getN1().getNa());
		// System.out.println("hello s1 "+a14vo.getS1().getP2a());
		// System.out.println("hello s1 "+a14vo.getS1().getWb());
		// System.out.println("hello s1 "+a14vo.getS1().getPt());
		// System.out.println("hello v1 "+a14vo.getV1().getEgtp());
		// System.out.println("hello v1 "+a14vo.getV1().getNpa());
		return a26vo;

	}
}
