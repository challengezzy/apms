package com.apms.bs.dfd;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;

public class DfdDataUtil {
	
	public static DfdCodeMapVo getCodeMapVo(String rptno,String code,String acnum) throws Exception{
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder("SELECT m.id,m.rptno,m.acmodel,m.code,m.code_str,m.isalarm ");
		sb.append(",ac.aircraftsn");
		sb.append(" FROM A_DFD_CODE_MAP M,B_AIRCRAFT AC,B_AIRCRAFT_MODEL ACM");
		sb.append(" where ACM.ID=AC.ACMODELID AND M.ACMODEL=ACM.MODELCODE ");
		sb.append(" and ac.aircraftsn=? AND rptno=? and code=?");
		sb.append(" and m.isalarm =1");//告警
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), acnum,rptno,code);
		if(vos.length > 0){
			DfdCodeMapVo mapVo = new DfdCodeMapVo();
			mapVo.setId(vos[0].getStringValue("id"));
			//mapVo.setAcmodel(vos[0].getStringValue("acmodel"));
			mapVo.setCode(vos[0].getStringValue("code"));
			mapVo.setCode_str(vos[0].getStringValue("code_str"));
			mapVo.setIsalarm(vos[0].getStringValue("isalarm"));
			mapVo.setRptno(vos[0].getStringValue("rptno"));
			mapVo.setAcnum(vos[0].getStringValue("aircraftsn"));
			
			return mapVo;
		}else{
			return null;
		}
	}

}
