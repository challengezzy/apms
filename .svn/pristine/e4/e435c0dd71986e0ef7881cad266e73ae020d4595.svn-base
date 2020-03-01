package com.apms.bs.apu.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

import com.apms.ApmsConst;

/**
 * APU部件信息更新后，更新APU中最低小时循环
 */
public class ApuLlpUpdateAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		String apuid = dataValue.get("APUID").toString();
		CommDMO dmo = new CommDMO();
		StringBuilder sb = new StringBuilder();
		sb.append("select a.totalcycle,p.cycle_oninstall,p.limit_cyc,p.apucycle_oninstall,");    
		sb.append("(p.limit_cyc-((a.totalcycle-p.apucycle_oninstall)+p.cycle_oninstall) ) leftCyc,");
		sb.append(" p.id,p.part_name ");
		sb.append(" from b_apu_llpparts p,b_apu a where a.id=p.apuid and p.apuid= ? order by leftcyc   ");
		
		String updateSql = "update b_apu a set a.llp_minloop=?,a.llp_minloop_part=? where a.id=?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), apuid);
		if(vos.length > 0){
			HashVO vo = vos[0];
			//String partid = vo.getStringValue("ID");
			String leftCyc = vo.getStringValue("LEFTCYC");
			String partname = vo.getStringValue("PART_NAME");
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, leftCyc,partname,apuid);
		}
		
		
		logger.debug("更新APU最小LLP小时循环部件完成！");
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {

	}

	/**
	 * 不常用 ，待查证
	 */
	public void doSomething(Map<String, Object> map) throws Exception {
		
	}

	/**
	 * 待查证
	 */
	public void doSomething(List<Map<String, Object>> dataValueList) throws Exception {

	}

}
