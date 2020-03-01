package com.apms.bs.dataprase.impl2;

import com.apms.bs.dataprase.impl.A21DataParseImpl_A320;
import com.apms.bs.dataprase.vo.AcarsDfdA21Vo_A320;
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

public class A21DataParseImpl_A320_REP extends A21DataParseImpl_A320 {

	protected AcarsDfdA21Vo_A320 praseA21Data(String gStrA21, String transdate) throws Exception {
		AcarsDfdA21Vo_A320 a21vo = new AcarsDfdA21Vo_A320();
		String tmpStr = gStrA21;
		String[] lines;
		
		tmpStr = gStrA21.replaceAll("[\n\r]", " ");
		lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
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
				a21vo.setM1(new AcarsM1Vo_A21_A320(line,"REP"));
				
			} else if (line.startsWith("M2")) {
				a21vo.setM2(new AcarsM2Vo_A21_A320(line,"REP"));
				
			} else if (line.startsWith("Z1")) {
				a21vo.setZ1(new AcarsZRVo_A21_A320(line));
			} else if (line.startsWith("R1")) {
				a21vo.setR1(new AcarsZRVo_A21_A320(line));
			} else if (line.startsWith("D1")) {
				a21vo.setD1(new AcarsD1Vo_A21_A320(line,"REP"));
				
			} else if (line.startsWith("F1")) {
				a21vo.setF1(new AcarsF1Vo_A21_A320(line,"REP"));
				
			} else if (line.startsWith("G1")) {
				a21vo.setG1(new AcarsG1Vo_A21_A320(line,"REP"));
				
			} else if (line.startsWith("H1")) {
				a21vo.setH1(new AcarsH1Vo_A21_A320(line));
			}

		}

		// System.out.println("hello v1 "+a14vo.getV1().getNpa());
		return a21vo;

	}

}
