package com.apms.bs.dataprase.impl2;

import com.apms.bs.dataprase.impl.A36DataParseImpl;
import com.apms.bs.dataprase.vo.AcarsDfdA36Vo_A320;
import com.apms.bs.dataprase.vo.a36.AcarsE1Vo_A36;
import com.apms.bs.dataprase.vo.a36.AcarsS0Vo_A36;
import com.apms.bs.dataprase.vo.a36.AcarsS1Vo_A36;
import com.apms.bs.util.StringUtil;

/**
 * A36报文解析实现类
 * @author zzy
 *
 */
public class A36DataParseImpl_REP extends A36DataParseImpl{
	
	@Override
	protected AcarsDfdA36Vo_A320 parseTextA36Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA36Vo_A320 a36vo = new AcarsDfdA36Vo_A320();
		String[] lines  = StringUtil.splitString2Array(graphStr, "\r\n", false);
		logger.debug("报文每行数据如下：");
		
		boolean isRep = true;
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			if(line.startsWith("E1")){
				a36vo.setE1(new AcarsE1Vo_A36(line));
				asn=a36vo.getE1().getAsn();
				a36vo.setE1Have(true);
			}else if(line.startsWith("S0")){
				a36vo.setS0(new AcarsS0Vo_A36(line,transdate,isRep));
			}else if(line.startsWith("S1")){
				a36vo.setS1(new AcarsS1Vo_A36(line,isRep));
			}
		}
		
		return a36vo;
	}
	
}
