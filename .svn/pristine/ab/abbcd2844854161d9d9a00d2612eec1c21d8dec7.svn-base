package com.apms.bs.dataprase.impl2;

import smartx.framework.common.vo.HashVO;

import com.apms.bs.dataprase.impl.A50DataParseImpl_A320;
import com.apms.bs.dataprase.vo.AcarsDfdA50Vo_A320;
import com.apms.bs.dataprase.vo.a50.AcarsECVo_A50_A320;
import com.apms.bs.dataprase.vo.a50.AcarsL1Vo_A50_A320;
import com.apms.bs.dataprase.vo.a50.AcarsP1Vo_A50_A320;
import com.apms.bs.util.StringUtil;

/**
 * V2500发动机,N1震动报文
 * @author jerry
 * @date May 25, 2016
 */
public class A50DataParseImpl_A320_REP extends A50DataParseImpl_A320 {

	protected void praseData(HashVO acarsVo,String msgno,String gStrA46,String transdate) throws Exception{
		AcarsDfdA50Vo_A320 a50vo = new AcarsDfdA50Vo_A320();
		String tmpStr = gStrA46.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		int l1num = 1;
		int r1num = 1;
		boolean isRep = true;
		//给a50vo 对象赋值开始
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
			if(line.startsWith("EC")){				
				a50vo.setEc(new AcarsECVo_A50_A320(line));
			}else if(line.startsWith("EE")){				
				a50vo.setEe(new AcarsECVo_A50_A320(line));
			}else if(line.startsWith("P1")){
				a50vo.setP1(new AcarsP1Vo_A50_A320(line,isRep));
				
			}else if(line.startsWith("L1")){
				if(l1num ==1 ){
					a50vo.setL1_1(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(l1num ==2 ){
					a50vo.setL1_2(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(l1num ==3 ){
					a50vo.setL1_3(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(l1num ==4 ){
					a50vo.setL1_4(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(l1num ==5 ){
					a50vo.setL1_5(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}
				l1num++;
				
			}else if(line.startsWith("R1")){
				if(r1num == 1){
					a50vo.setR1_1(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(r1num == 2){
					a50vo.setR1_2(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(r1num == 3){
					a50vo.setR1_3(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(r1num == 4){
					a50vo.setR1_4(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(r1num == 5){
					a50vo.setR1_5(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}
				r1num++;
			}
		}
		
		insertToTable(acarsVo, msgno, a50vo);
	}
	
}
