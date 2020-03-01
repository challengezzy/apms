package com.apms.bs.datacompute.engine;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

public class EngModelService {
	
	private CommDMO dmo;
	/**
	 * 根据飞机号查到飞机型号
	 * @param acnum
	 * @return
	 * @throws Exception
	 */
	public String getEngModelByAcnum(String acnum) throws Exception{
		String engModel = null;
		String sql = "SELECT AC.AIRCRAFTSN ACNUM,M.ID,M.MODEL,M.SERIES,M.MODELLEVEL "
				+ " FROM B_AIRCRAFT AC, B_ENGINE_MODEL M "
				+ " WHERE M.ID = AC.ENGINE_MODE AND AC.AIRCRAFTSN = ?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, acnum);
		if(vos.length > 0){
			engModel = vos[0].getStringValue("MODEL");
		}
		
		return engModel;
	}

}
