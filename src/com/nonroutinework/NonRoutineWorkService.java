package com.nonroutinework;

import java.util.List;

import com.apms.ApmsConst;

import flex.messaging.io.ArrayList;

import smartx.framework.common.bs.CommDMO;

public class NonRoutineWorkService {
	private CommDMO dmo = new CommDMO();
	
	public String saveWbs_Rbs(List<Wbs_RbsTable> list) throws Exception{
		
		List<Wbs_RbsTable> li=list;
		List<String> sqlList=new ArrayList();
		for(int i=0;i<li.size();i++){
			Wbs_RbsTable wbt=(Wbs_RbsTable)li.get(i);
			String sql="insert into wbs_rbs(id, workid, processname, tech_p, tech_i, tech_d, personal_p, personal_i, personal_d, tool_p, tool_i, tool_d, " +
					"equipment_p, equipment_i, equipment_d, file_p, file_i, file_d, mana_p, mana_i, mana_d, env_p, env_i, env_d, riskprocess, updatetime, updateuser)"
				+" values(s_wbs_rbs.nextval,null,'"+wbt.getProcessname()+"',"+wbt.getTech_p()
				+","+wbt.getTech_i()+","+wbt.getTech_d()+","+wbt.getPersonal_p()+","+wbt.getPersonal_i()+","+wbt.getPersonal_d()
				+","+wbt.getTool_p()+","+wbt.getTool_i()+","+wbt.getTool_d()+","+wbt.getEquipment_p()+","+wbt.getEquipment_i()
				+","+wbt.getEquipment_d()+","+wbt.getFile_p()+","+wbt.getFile_i()+","+wbt.getFile_d()+","+wbt.getMana_p()
				+","+wbt.getMana_i()+","+wbt.getMana_d()+","+wbt.getEnv_p()+","+wbt.getEnv_i()+","+wbt.getEnv_d()+",null,sysdate,null)";
			sqlList.add(sql);
		}
		dmo.executeBatchByDS(ApmsConst.DS_APMS, sqlList);
		dmo.commit(ApmsConst.DS_APMS);
		return "success";
		
	}

}
