package com.apms.bs.dataprase.impl2;

import com.apms.bs.dataprase.impl.A25DataParseImpl_A320;
import com.apms.bs.dataprase.vo.AcarsDfdA25Vo_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsCFVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsCGVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsKO_KIVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsSWZVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsT1_T2Vo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsTL_TRVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsV1Vo_A25_A320;
import com.apms.bs.util.StringUtil;

public class A25DataParseImpl_A320_REP extends A25DataParseImpl_A320 {

	protected AcarsDfdA25Vo_A320 praseA25Data(String gStrA25, String transdate) throws Exception {
		
		AcarsDfdA25Vo_A320 a25vo = new AcarsDfdA25Vo_A320();
		String tmpStr = gStrA25.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);

		boolean isRep = true;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			// logger.debug("第"+i+"行:" + line);
			if (line.startsWith("CF")) {
				a25vo.setCf(new AcarsCFVo_A25_A320(line, isRep));
			} else if (line.startsWith("CG")) {
				a25vo.setCg(new AcarsCGVo_A25_A320(line, isRep));
			} else if (line.startsWith("TL")) {
				a25vo.setTl(new AcarsTL_TRVo_A25_A320(line, isRep));
			} else if (line.startsWith("TR")) {
				a25vo.setTr(new AcarsTL_TRVo_A25_A320(line, isRep));
			} else if (line.startsWith("S1")) {
				a25vo.setS1(new AcarsSWZVo_A25_A320(line, transdate, isRep));
			} else if (line.startsWith("S2")) {
				a25vo.setS2(new AcarsSWZVo_A25_A320(line, transdate, isRep));
			} else if (line.startsWith("KO") || line.startsWith("KS")) {
				a25vo.setKo(new AcarsKO_KIVo_A25_A320(line, isRep));
			} else if (line.startsWith("T1")) {
				a25vo.setT1(new AcarsT1_T2Vo_A25_A320(line, isRep));
			} else if (line.startsWith("T2")) {
				a25vo.setT2(new AcarsT1_T2Vo_A25_A320(line, isRep));
			} else if (line.startsWith("V1")) {
				a25vo.setV1(new AcarsV1Vo_A25_A320(line, transdate));
			} else if (line.startsWith("W1") || line.startsWith("X1")) {
				a25vo.setW1(new AcarsSWZVo_A25_A320(line, transdate, isRep));
			} else if (line.startsWith("KI") || line.startsWith("KX")) {
				a25vo.setKi(new AcarsKO_KIVo_A25_A320(line, isRep));
			} else if (line.startsWith("Z1")) {
				a25vo.setZ1(new AcarsSWZVo_A25_A320(line, transdate, isRep));
			} else if (line.startsWith("Z2")) {
				a25vo.setZ2(new AcarsSWZVo_A25_A320(line, transdate, isRep));
			} else if (line.startsWith("Z3")) {
				a25vo.setZ3(new AcarsSWZVo_A25_A320(line, transdate, isRep));

			}

		}

		return a25vo;

	}
}
