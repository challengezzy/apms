package com.apms.bs.dataprase.impl2;

import com.apms.bs.dataprase.impl.A24DataParseImpl_A320;
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


public class A24DataParseImpl_A320_REP extends A24DataParseImpl_A320{
	
	
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
				a24vo.setD1(new AcarsD1Vo_A21_A320(line,"REP"));
				
			} else if (line.startsWith("F1")) {
				a24vo.setF1(new AcarsF1Vo_A21_A320(line,"REP"));
				
			} else if (line.startsWith("G1")) {
				a24vo.setG1(new AcarsG1Vo_A21_A320(line,"REP"));
				
			} else if (line.startsWith("H1")) {
				a24vo.setH1(new AcarsH1Vo_A21_A320(line));
			}			
		}
		return a24vo;	
	}
}
