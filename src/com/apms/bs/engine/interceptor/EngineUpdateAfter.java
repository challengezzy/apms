package com.apms.bs.engine.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.ComBoxItemVO;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

import com.apms.ApmsConst;

/**
 * 服务端拦截器实现类
 */
public class EngineUpdateAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
//		double tsn = new Double(dataValue.get("TSN_HRS") == null ? "0" : dataValue.get("TSN_HRS").toString());
//		double csn = new Double(dataValue.get("CSN") == null ? "0" : dataValue.get("CSN").toString());
//		String infodate = dataValue.get("INFODATE") == null ? "0" : dataValue.get("INFODATE").toString();
//		String status = dataValue.get("DATASTATE") == null ? "0" : ((ComBoxItemVO) dataValue.get("DATASTATE")).getId().toString();
//		String engsn = dataValue.get("ENGSN") == null ? "0" : dataValue.get("ENGSN").toString();
//		String acid = "";
//		String sql = "";
//		ArrayList<String> updateSqlList = new ArrayList<String>();
//		if (dataValue.get("AIRCRAFTID") != null) {
//			acid = dataValue.get("AIRCRAFTID").toString();
//		}else{
//			throw new Exception("飞机号为空");
//		}
		
//		if ( false) {// 如果是数据补录则直接写入发动机日志 Integer.parseInt(status) == 3 || status.equals("数据补录")
//			sql = "select t.id from l_eng_flightlog t,b_engine_info t1 where t.fidate=t1.infodate and t.engsn='" + engsn + "'" +
//				" and t1.acnum=t.acnum and t.fidate=to_date(substr('" + infodate+ "',0,10),'yyyy-MM-dd')";
//			HashVO[] engflylogs = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
//			if (engflylogs.length > 0) {
//				HashVO engflylog1 = engflylogs[0];
//				sql = "update l_eng_flightlog set tsn=" + tsn + ",csn=" + csn + ",modifystatus='3',computedstatus='3'" +
//						" where id=" + engflylog1.getStringValue("id");
//				updateSqlList.add(sql);
//			} else {
//				sql = "insert into l_eng_flightlog(id,fiflbsn,global_pk,acnum,add_cycle,add_time,computedstatus,engsn,engmodelid,tsn,csn,time_oninstall,modifystatus,cycle_oninstall,time_onrepaired,cycle_onrepaired,fidate)"
//						+ "select s_l_eng_flightlog.nextval," +
//						" CONCAT(CONCAT(replace(substr('"+ infodate+ "',0,10),'-',''),t1.aircraftsn),s_l_eng_flightlog.currval),s_l_eng_flightlog.currval,aircraftsn,0,0,3,engsn,engmodelid," +
//						"tsn,csn,time_oninstall,DATASTATE,cycle_oninstall,time_onrepaired,cycle_onrepaired, to_date(substr('"
//						+ infodate + "',0,10),'yyyy-MM-dd') from b_engine_info t,b_aircraft t1 where t.aircraftid=t1.id and engsn='" + engsn + "'";
//				updateSqlList.add(sql);
//			}
//
//			sql = "select t.id from l_eng_flightlog_daily t,b_engine_info t1 where t.fidate=t1.infodate and t.engsn='" + engsn + "' and t1.acnum=t.acnum and t.fidate=to_date(substr('" + infodate
//					+ "',0,10),'yyyy-MM-dd')";
//			HashVO[] engflylogdailys = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
//			if (engflylogdailys.length > 0) {
//				HashVO engflylogdaily1 = engflylogdailys[0];
//				sql = "update l_eng_flightlog_daily set tsn=" + tsn + ",csn=" + csn + ",computedstatus='3' where id=" + engflylogdaily1.getStringValue("id");
//				updateSqlList.add(sql);
//			} else {
//				sql = "insert into l_eng_flightlog_daily(id,upday,fidate,acnum,engsn,engmodelid,tsn,csn,time_oninstall,cycle_oninstall,add_cycle,add_time,time_onrepaired,cycle_onrepaired,computedstatus)"
//						+ "select s_l_eng_flightlog_daily.nextval,upday,to_date(substr('"
//						+ infodate
//						+ "',0,10),'yyyy-MM-dd') fidate,aircraftsn,engsn,engmodelid,tsn,csn,time_oninstall,cycle_oninstall,0 add_time,0 add_cycle,time_onrepaired,cycle_onrepaired,DATASTATE computedstatus  from b_engine_info t,b_aircraft t1 where t.aircraftid=t1.id and engsn='"
//						+ engsn + "'";
//				updateSqlList.add(sql);
//			}
//			dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
//			dmo.commit(ApmsConst.DS_APMS);
//			logger.debug("数据补录时，增加日志数据完成！");
//		}
		
		//TODO 判断飞机型号有无更改


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
