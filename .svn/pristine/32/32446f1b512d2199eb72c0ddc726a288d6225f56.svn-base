package com.apms.bs.dataprase.impl2;

import smartx.framework.common.vo.HashVO;

import com.apms.bs.dataprase.impl.A49DataParseImpl_A320;
import com.apms.bs.dataprase.vo.AcarsDfdA49Vo_A320;
import com.apms.bs.dataprase.vo.a49.AcarsECVo_A49_A320;
import com.apms.bs.dataprase.vo.a49.AcarsL1Vo_A49_A320;
import com.apms.bs.dataprase.vo.a49.AcarsL2Vo_A49_A320;
import com.apms.bs.dataprase.vo.a49.AcarsL3Vo_A49_A320;
import com.apms.bs.util.StringUtil;

/**
 * V2500发动机,N1震动报文
 * @author jerry
 * @date May 25, 2016
 */
public class A49DataParseImpl_A320_REP extends A49DataParseImpl_A320 {

	protected void praseData(HashVO acarsVo,String msgno,String gStrA46,String transdate) throws Exception{
		AcarsDfdA49Vo_A320 a49vo = new AcarsDfdA49Vo_A320();
		String tmpStr = gStrA46.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		boolean isRep = true;
		//给a49vo 对象赋值开始
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
			if(line.startsWith("EC")){				
				a49vo.setEc(new AcarsECVo_A49_A320(line));
			}else if(line.startsWith("EE")){				
				a49vo.setEe(new AcarsECVo_A49_A320(line));
			}else if(line.startsWith("L1")){
				a49vo.setL1(new AcarsL1Vo_A49_A320(line, transdate));
				
			}else if(line.startsWith("L2")){
				a49vo.setL2(new AcarsL2Vo_A49_A320(line));
			}else if(line.startsWith("R1")){
				
				a49vo.setR1(new AcarsL1Vo_A49_A320(line, transdate));
			}else if(line.startsWith("R2")){
				
				a49vo.setR2(new AcarsL2Vo_A49_A320(line));
				
			}else if(line.startsWith("L3")){
				
				a49vo.setL3(new AcarsL3Vo_A49_A320(line, transdate,isRep));
			}else if(line.startsWith("R3")){
				
				a49vo.setR3(new AcarsL3Vo_A49_A320(line, transdate,isRep));
			}
		}
		
		//将对象数据持久化 分别插入  A_DFD_A49IAEV25_LIST
		insertToTable(acarsVo, msgno, a49vo);
	}
	
}
