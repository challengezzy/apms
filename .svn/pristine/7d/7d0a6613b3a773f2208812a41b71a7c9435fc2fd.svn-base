package com.apms.bs.dataprase.impl2;

import com.apms.bs.dataprase.impl.A46DataParseImpl_A321;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsECVo_A46_A321;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsS1Vo_A46_A321;

public class A46DataParseImpl_A321_REP extends A46DataParseImpl_A321 {

	private boolean isRep = true;
	
	protected AcarsS1Vo_A46_A321 getS1Vo(String lineS1,String transdate) throws Exception{
		
		return new AcarsS1Vo_A46_A321(lineS1, transdate, isRep);
	}
	
	protected AcarsECVo_A46_A321 getECVo(String line) throws Exception{
		return new AcarsECVo_A46_A321(line, isRep);
	}
}
