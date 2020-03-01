

package com.apms.bs.engine.interceptor;

import java.util.ArrayList;

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
 * 服务端拦截器实现类,添加发动机的时候同时添加发动的LLP部件,和发动机的初始飞行日志
 */
public class EngineInsertAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
		String engsn="";//发动机序号
		String query="";
		String engloc="";
		//增加发动的时候同时增加一段飞行日志
		ArrayList<String> updateSqlList = new ArrayList<String>();
		String fidate=null;
		if (dataValue.get("ENGLOC")!=null){
		engloc=dataValue.get("ENGLOC").toString();
		}
		
		if (dataValue.get("ENGSN")!=null){
			engsn=dataValue.get("ENGSN").toString();//发动机序号
		}
		else
		{
			engsn="";//发动机序号
		}
		
		if (dataValue.get("INFODATE")!=null){
			fidate=dataValue.get("INFODATE").toString();//数据日期
		}
		else
		{
			fidate=null;//数据日期
		}
		//假如有数据，删除数据日期之后的数据
		query="delete from l_eng_flightlog where engsn='"+engsn+"' and fidate>=to_date(substr('"+fidate+"',0,10),'yyyy-MM-dd')";
		updateSqlList.add(query);
		
		query="delete from l_eng_flightlog_daily where engsn='"+engsn+"' and fidate>=to_date(substr('"+fidate+"',0,10),'yyyy-MM-dd')";
		updateSqlList.add(query);
		
		if (engloc.equals("1")||engloc.equals("在翼")){//在翼的时候添加
			query="insert into l_eng_flightlog(id,fiflbsn,global_pk,acnum,add_cycle,add_time,computedstatus,engsn,engmodelid,tsn,csn" +
			",time_oninstall,modifystatus,cycle_oninstall,TIME_ONREPAIRED,CYCLE_ONREPAIRED,fidate,fiactdep,ENGWING_LOC,updatetime,userid)" +
			"select s_l_eng_flightlog.nextval,CONCAT(CONCAT(replace(substr('"+fidate+"',0,10),'-',''),t1.aircraftsn),s_l_eng_flightlog.currval)" +
			",s_l_eng_flightlog.currval,t1.aircraftsn,0,0,1,engsn,engmodelid,tsn,csn,time_oninstall,2" +
			",cycle_oninstall,TIME_ONREPAIRED,CYCLE_ONREPAIRED,infodate,infodate,ENGWING_LOC,sysdate,'init' " +
			" from b_engine_info t,b_aircraft t1 where t.aircraftid=t1.id and engsn='"+engsn+"'";
			updateSqlList.add(query);
			
			//写入发动机daily日志
			query="insert into l_eng_flightlog_daily(id,upday,fidate,acnum,engsn,engmodelid,tsn,csn,time_oninstall,cycle_oninstall" +
				",add_cycle,add_time,TIME_ONREPAIRED,CYCLE_ONREPAIRED,computedstatus,ENGWING_LOC,updatetime)" +
				"select s_l_eng_flightlog_daily.nextval,upday,infodate fidate,aircraftsn" +
				",engsn,engmodelid,tsn,csn,time_oninstall,cycle_oninstall" +
				",0 add_time,0 add_cycle,TIME_ONREPAIRED,CYCLE_ONREPAIRED,1 computedstatus,ENGWING_LOC,sysdate" +
				" from b_engine_info t,b_aircraft t1 where t.aircraftid=t1.id and engsn='"+engsn+"'";
			updateSqlList.add(query);
		}
		//增加LLP信息	
		query="select * from b_eng_llpparts t where engsn='"+engsn+"'";
		HashVO[] engVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
		if(engVos.length>0){//如果数据存在
			//HashVO engVos1=engVos[0];
		}
		else{
			query="insert into b_eng_llpparts( id,engid,engsn,STRUCTID,part_area,part_name,engtime_oninstall,engcycle_oninstall,time_oninstall,cycle_oninstall," +
					"update_man,update_time,comments)" +
					"select s_b_eng_llpparts.nextval,t1.id,t1.engsn,t2.id,t2.module,t2.part_desc part_name ,t1.time_oninstall,t1.cycle_oninstall,0 time_oninstall ,0 cycle_oninstall," +
					"'admin' update_man,sysdate update_time,t1.llp_compnent " +
					"from b_engine_info t1,b_eng_llp_struct t2 " +
					"where t1.engmodelid=t2.engmodelid and engsn='"+engsn+"'";
			updateSqlList.add(query);
		}
		
		dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
		if (engloc.equals("1")||engloc.equals("在翼")){//在翼的时候添加
			//生成拆换记录，梯次装上的信息
			String sql = "insert into l_eng_swaplog(id ,acnum ,eng_model ,engine_postion ,engsn ,swap_date ,swap_date_str"
					 + " ,SWAP_REASON ,swap_action ,time_oninstall ,cycle_oninstall ,time_onrepaired ,cycle_onrepaired "
					 + " ,REPAIR_FLAG ,COMMENTS ,UPDATE_MAN ,UPDATE_TIME)"
					 + " select s_l_eng_swaplog.nextval,t.acnum,t.engmodelid,t.engwing_loc,t.engsn,l.fidate,to_char(l.fidate,'yyyy-mm-dd')"
					 + "        ,1,1,l.time_oninstall,l.cycle_oninstall,l.time_onrepaired,l.cycle_onrepaired"
					 + "        ,t.chkstate,'新增发动机时初始的装上拆换记录','system',sysdate"
					 + " from b_engine_info t,l_eng_flightlog l where t.engsn=l.engsn and l.modifystatus=2 and t.engsn='"+engsn+"'";
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);//生成拆换日志
		}
		
		dmo.commit(ApmsConst.DS_APMS);	
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
