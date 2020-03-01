package com.apms.bs.aircraft.interceptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.intf.omis.Javadatediff;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.ComBoxItemVO;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;

/**
 * 飞机飞行日志新增后置拦截器，增加飞行日志的天航班数据
 */
public class FlightLogInsertAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		CommDMO dmo = new CommDMO();
		int baseorgid = 0;// 维修基地编号

		if (dataValue.get("BASEORGID") instanceof ComBoxItemVO) {
			String bid = ((ComBoxItemVO) dataValue.get("BASEORGID")).getId();
			baseorgid = Integer.parseInt(bid);

		}
		String updateStr = "";
		String acid = dataValue.get("ACNUM") == null ? "0" : dataValue.get("ACNUM").toString();// 飞机号;

		String fidate = dataValue.get("FIDATE") == null ? "0" : dataValue.get("FIDATE").toString();// 航班日期
		String fltno = dataValue.get("FIFLTNO") == null ? "0" : dataValue.get("FIFLTNO").toString();// 航班号
		String fideploc = dataValue.get("FIDEPLOC") == null ? "0" : dataValue.get("FIDEPLOC").toString();// 离港三字代码
		String fiarvloc = dataValue.get("FIARVLOC") == null ? "0" : dataValue.get("FIARVLOC").toString();// 到港三字代码
		String fiactdep = dataValue.get("FIACTDEP") == null ? "0" : dataValue.get("FIACTDEP").toString();// 推出时间
		String fiacttak = dataValue.get("FIACTTAK") == null ? "0" : dataValue.get("FIACTTAK").toString();// 起飞时间
		String fiactlad = dataValue.get("FIACTLAD") == null ? "0" : dataValue.get("FIACTLAD").toString();// 着落时间
		String fiactarv = dataValue.get("FIACTARV") == null ? "0" : dataValue.get("FIACTARV").toString();// 靠桥时间
		int fiactlg = Integer.parseInt(dataValue.get("FIACTLG") == null ? "0" : dataValue.get("FIACTLG").toString());// 着落次数
		long fiair = new Long(dataValue.get("FIAIR") == null ? "0" : dataValue.get("FIAIR").toString());// 空中时间
		String fiblock = dataValue.get("FIBLOCK") == null ? "0" : dataValue.get("FIBLOCK").toString();// 轮挡时间
		double fiairsum = new Double(dataValue.get("FLAIR_SUM_HOUR") == null ? "0" : dataValue.get("FLAIR_SUM_HOUR").toString()) * 60;// 空中累积小时
		double fiblocksum = new Double(dataValue.get("FIBLOCK_SUM_HOUR") == null ? "0" : dataValue.get("FIBLOCK_SUM_HOUR").toString()) * 60;// 轮挡累积小时

		String fiactlgsum = dataValue.get("FIACTLG_SUM") == null ? "0" : dataValue.get("FIACTLG_SUM").toString();// 着落次数累积
		String fista = dataValue.get("FISTA") == null ? "0" : dataValue.get("FISTA").toString();// 校验标志

		String modifystatus = dataValue.get("MODIFYSTATUS") == null ? "0" : ((ComBoxItemVO) dataValue.get("MODIFYSTATUS")).getId().toString();// 修正标志

		String computedstatus = dataValue.get("COMPUTEDSTATUS") == null ? "0" : ((ComBoxItemVO) dataValue.get("COMPUTEDSTATUS")).getId().toString();// 数据状态

		String userid = dataValue.get("USERID") == null ? "0" : dataValue.get("USERID").toString();// 更新人
		String query = "select * from l_ac_flightlog_daily where acnum='" + acid + "' and vdfcdate=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd')";
		ArrayList<String> updateSqlList = new ArrayList<String>();
		HashVO[] flylogVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
		if (flylogVos.length > 0) {// 有存在日志则不写入，否则就写入日志
			// 取得大于添加日期的最近的修正数据日期
			query = "select * from (select * from l_ac_flightlog t where modifystatus='1' and acnum='" + acid + "' and fidate>=to_date(substr('" + fidate
					+ "',0,10),'yyyy-MM-dd') and fiactdep>to_date(substr('" + fiactdep + "',0,19),'yyyy-MM-dd hh24:mi:ss')  order by fidate,fiactdep ) where rownum<2";
			HashVO[] flyvo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			if (flyvo.length > 0) {// 有修正数据
				HashVO vomodfydate = flyvo[0];
				updateStr = "update l_ac_flightlog set flair_sum=flair_sum+" + fiair + ",fiblock_sum=fiblock_sum+" + fiblock + ",fiactlg_sum=fiactlg_sum+" + fiactlg + " where acnum='" + acid
						+ "' and fidate>=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd') and fiactdep>to_date(substr('" + fidate + "',0,19),'yyyy-MM-dd hh24:mi:ss') and fidate<=to_date(substr('"
						+ vomodfydate.getStringValue("fidate") + "',0,10),'yyyy-MM-dd')  and fiactdep<to_date(substr('" + vomodfydate.getStringValue("fiactdep") + "',0,19),'yyyy-MM-dd hh24:mi:ss') ";
				updateSqlList.add(updateStr);

				updateStr = "update l_ac_flightlog_daily set vdfcair=vdfcair+" + fiair + ",vdfcblock=vdfcblock+" + fiblock + ",fiactlg_sum=fiactlg_sum+" + fiactlg + " where acnum='" + acid
						+ "' and vdfcdate>=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd') and fiactdep>to_date(substr('" + fidate
						+ "',0,19),'yyyy-MM-dd hh24:mi:ss') and vdfcdate<to_date(substr('" + vomodfydate.getStringValue("fidate") + "',0,10),'yyyy-MM-dd')";
				updateSqlList.add(updateStr);
			} else {// 没有修正数据
				updateStr = "update l_ac_flightlog set flair_sum=flair_sum+" + fiair + ",fiblock_sum=fiblock_sum+" + fiblock + ",fiactlg_sum=fiactlg_sum+" + fiactlg + " where acnum='" + acid
						+ "' and fidate>=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd') and fiactdep>to_date(substr('" + fiactdep + "',0,19),'yyyy-MM-dd hh24:mi:ss')";
				updateSqlList.add(updateStr);
				// 修改飞行数据日数据
				updateStr = "update l_ac_flightlog_daily set vdfcair=vdfcair+" + fiair + ",vdfcblock=vdfcblock+" + fiblock + ",fiactlg_sum=fiactlg_sum+" + fiactlg + " where acnum='" + acid
						+ "' and vdfcdate>=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd') ";
				updateSqlList.add(updateStr);

			}

		} else {// 如果 不存在则只写入daily日志
			query = "insert into l_ac_flightlog_daily(id,baseorgid,acnum,vdfcdate,fiair,vdfcair,fiblock,vdfcblock,fiactlg,fiactlg_sum,computedstatus,updatetime)"
					+ "values(s_l_ac_flightlog_daily.nextval," + baseorgid + ",'" + acid + "',to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd')," + fiair + "," + fiairsum + "," + fiblock + ","
					+ fiblocksum + "," + fiactlg + "," + fiactlgsum + ",'" + computedstatus + "',sysdate)";
			updateSqlList.add(query);
			// 同时修改数据
			query = "select * from (select * from l_ac_flightlog t where modifystatus='1' and acnum='" + acid + "' and fidate>to_date(substr('" + fidate
					+ "',0,10),'yyyy-MM-dd') and fiactdep>to_date(substr('" + fiactdep + "',0,19),'yyyy-MM-dd hh24:mi:ss')  order by fidate,fiactdep ) where rownum<2";
			HashVO[] flyvo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			if (flyvo.length > 0) {// 有修正数据
				HashVO vomodfydate = flyvo[0];
				updateStr = "update l_ac_flightlog set flair_sum=flair_sum+" + fiair + ",fiblock_sum=fiblock_sum+" + fiblock + ",fiactlg_sum=fiactlg_sum+" + fiactlg + " where acnum='" + acid
						+ "' and fidate>=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd') and fiactdep>to_date(substr('" + fiactdep
						+ "',0,19),'yyyy-MM-dd hh24:mi:ss') and fidate<=to_date(substr('" + vomodfydate.getStringValue("fidate") + "',0,10),'yyyy-MM-dd') and fiactdep<to_date(substr('"
						+ vomodfydate.getStringValue("fiactdep") + "',0,19),'yyyy-MM-dd hh24:mi:ss')";
				updateSqlList.add(updateStr);

				updateStr = "update l_ac_flightlog_daily set vdfcair=vdfcair+" + fiair + ",vdfcblock=vdfcblock+" + fiblock + ",fiactlg_sum=fiactlg_sum+" + fiactlg + " where acnum='" + acid
						+ "' and vdfcdate>=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd') and vdfcdate<=to_date(substr('" + vomodfydate.getStringValue("fidate") + "',0,10),'yyyy-MM-dd')";
				updateSqlList.add(updateStr);
			} else {// 没有修正数据
				updateStr = "update l_ac_flightlog set flair_sum=flair_sum+" + fiair + ",fiblock_sum=fiblock_sum+" + fiblock + ",fiactlg_sum=fiactlg_sum+" + fiactlg + " where acnum='" + acid
						+ "' and fidate>=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd') and fiactdep>to_date(substr('" + fiactdep + "',0,19),'yyyy-MM-dd hh24:mi:ss')";
				updateSqlList.add(updateStr);
				// 修改飞行数据日数据
				updateStr = "update l_ac_flightlog_daily set vdfcair=vdfcair+" + fiairsum + ",vdfcblock=vdfcblock+" + fiblocksum + ",fiactlg_sum=fiactlg_sum+" + fiactlgsum + " where acnum='" + acid
						+ "' and vdfcdate>=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd') ";
				updateSqlList.add(updateStr);
			}
		}
		// 提交飞行日志
		dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
		dmo.commit(ApmsConst.DS_APMS);
		updateSqlList.clear();
		logger.debug("飞行日志新增后置拦截器执行完成！");
		//
		logger.debug("在飞行日志新增后增加发动机日志拦截器执行开始！");
		// 写入发动机日志
		String engmodelid = "";
		String swapdate = null;
		String engmodifydate = null;
		String engsn = "";
		long englogid = 0;
		Javadatediff dateadd = new Javadatediff();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		// query="select * from l_eng_flightlog t,b_engine_info t1 where t.engsn=t1.engsn and t1.acnum='"+acid+"' and t.fidate=to_date(substr('"+fidate+"',0,10),'yyyy-MM-dd')";
		query = "select t1.engsn,t1.engmodelid from b_engine_info t1 where  t1.acnum='" + acid + "' group by t1.engsn,t1.engmodelid";

		HashVO[] englogVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
		// if(englogVos.length>0){//

		for (int i = 0; i < englogVos.length; i++) {
			// updateSqlList.clear();
			// 有则判断有没有修正日期和拆换动作
			HashVO englogVos1 = englogVos[i];
			engsn = englogVos1.getStringValue("engsn");
			engmodelid = englogVos1.getStringValue("engmodelid");

			query = "select * from (select * from l_eng_swaplog t where engsn='" + engsn + "' and swap_date>to_date(substr('" + fidate
					+ "',0,10),'yyyy-MM-dd') and swap_action=0  order by swap_date ) where rownum<2";// 取得最小的拆换日期
			HashVO[] engswap = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);

			if (engswap.length > 0) {// 如果有拆换日期，数据修改到拆换日期为止
				HashVO engswap1 = engswap[0];
				swapdate = engswap1.getStringValue("swap_date");
			} else {// 没有拆换日期
				swapdate = null;
			}

			query = "select * from (select * from l_eng_flightlog t where engsn='" + engsn + "' and fidate>to_date(substr('" + fidate
					+ "',0,10),'yyyy-MM-dd')  and modifystatus=1 order by fidate ) where rownum<2";
			HashVO[] engmodifyvo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			if (engmodifyvo.length > 0) {// 大于这个日期有修正的的数据
				HashVO engmodifyvo1 = engmodifyvo[0];
				engmodifydate = engmodifyvo1.getStringValue("fidate");
			} else {// 没有修正数据
				engmodifydate = null;
			}
			// //判断结束日期
			query = "select * from l_eng_flightlog where engsn='" + engsn + "' and fidate=to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd')";
			HashVO[] engvo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			if (engvo.length > 0) {// 判断当天有没有数据，如果有则不写入

			} else {// 没有数据，则大步地写入
				query = "insert into l_eng_flightlog(id,fiflbsn,global_pk,acnum,add_cycle,add_time,computedstatus,engsn,engmodelid,tsn,csn,time_oninstall, modifystatus,cycle_oninstall,time_onrepaired,cycle_onrepaired, fidate)"
						+ "select s_l_eng_flightlog.nextval,CONCAT(CONCAT(replace(substr('"
						+ fidate
						+ "',0,10),'-',''),t1.acnum),s_l_eng_flightlog.currval),s_l_eng_flightlog.currval,acnum,"
						+ fiactlg
						+ ","
						+ fiair
						+ ",'"
						+ computedstatus
						+ "',engsn,engmodelid,"
						+ fiairsum
						+ ","
						+ fiactlgsum
						+ ",time_oninstall,'"
						+ modifystatus
						+ "',cycle_oninstall,time_onrepaired,cycle_onrepaired,to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd') from b_engine_info t1 where  t1.engsn='" + engsn + "' ";
				// dmo.executeUpdateByDS(ApmsConst.DS_APMS,
				// query,fiactlg,fiair,computedstatus,fiairsum,fiactlgsum,modifystatus,fidate,acid);
				updateSqlList.add(query);
				query = "insert into l_eng_flightlog_daily(id,fidate,acnum,engsn,engmodelid,upday,tsn,csn,time_oninstall,cycle_oninstall,add_cycle,add_time,time_onrepaired,cycle_onrepaired,computedstatus)"
						+ "select s_l_eng_flightlog_daily.nextval,to_date(substr('"
						+ fidate
						+ "',0,10),'yyyy-MM-dd'),acnum,engsn,engmodelid,upday,"
						+ fiairsum
						+ ","
						+ fiactlgsum
						+ ",time_oninstall,cycle_oninstall,"
						+ fiactlg
						+ ","
						+ fiair
						+ ",time_onrepaired,cycle_onrepaired,'"
						+ computedstatus
						+ "' from b_engine_info t1 where  t1.engsn='"
						+ engsn
						+ "' ";
				updateSqlList.add(query);
			}
			// 修改发动机数据
			if (engmodifydate == null && swapdate == null) {// 没有拆换日期和修正日期
				// 修改大于日期之后的数据
				query = "update l_eng_flightlog set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('" + fidate + "',0,10),'yyyy-MM-dd')  and engsn='"
						+ engsn + "'";
				updateSqlList.add(query);
				// 修改发动机日志表之后的数据
				query = "update l_eng_flightlog_daily set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('" + fidate
						+ "',0,10),'yyyy-MM-dd') and engsn='" + engsn + "'";
				updateSqlList.add(query);

			} else if (engmodifydate != null && swapdate == null) {// 有修正日期，没有拆换日志

				query = "update l_eng_flightlog set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('" + fidate
						+ "',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('" + engmodifydate + "',0,10),'yyyy-MM-dd') and engsn='" + engsn + "'";
				updateSqlList.add(query);

				query = "update l_eng_flightlog_daily set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('" + fidate
						+ "',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('" + engmodifydate + "',0,10),'yyyy-MM-dd') and engsn='" + engsn + "'";
				updateSqlList.add(query);
			} else if (engmodifydate == null && swapdate != null) {// 没有修正日期，有拆换日志

				query = "update l_eng_flightlog set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('" + fidate
						+ "',0,10),'yyyy-MM-dd') and fidate<to_date(substr('" + swapdate + "',0,10),'yyyy-MM-dd') and engsn='" + engsn + "'";
				updateSqlList.add(query);

				query = "update l_eng_flightlog_daily set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('" + fidate
						+ "',0,10),'yyyy-MM-dd') and fidate<to_date(substr('" + swapdate + "',0,10),'yyyy-MM-dd') and engsn='" + engsn + "'";
				updateSqlList.add(query);
			} else {// 有拆换日志和修正日期
				int d = dateadd.dateDiff("d", sdf1.parse(engmodifydate), sdf1.parse(swapdate));
				if (d >= 0) {// 如果修正日期大于拆换日期，则选拆换日期

					query = "update l_eng_flightlog set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('" + fidate
							+ "',0,10),'yyyy-MM-dd') and fidate<to_date(substr('" + swapdate + "',0,10),'yyyy-MM-dd') and engsn='" + engsn + "'";
					updateSqlList.add(query);

					query = "update l_eng_flightlog_daily set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('" + fidate
							+ "',0,10),'yyyy-MM-dd') and fidate<to_date(substr('" + swapdate + "',0,10),'yyyy-MM-dd') and engsn='" + engsn + "'";

					updateSqlList.add(query);
				} else {

					query = "update l_eng_flightlog set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>=to_date(substr('" + fidate
							+ "',0,10),'yyyy-MM-dd') and fidate<to_date(substr('" + engmodifydate + "',0,10),'yyyy-MM-dd') and engsn='" + engsn + "'";
					updateSqlList.add(query);

					query = "update l_eng_flightlog_daily set tsn=tsn+(" + fiair + "),csn=csn+(" + fiactlg + "),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('" + fidate
							+ "',0,10),'yyyy-MM-dd') and fidate<to_date(substr('" + engmodifydate + "',0,10),'yyyy-MM-dd') and engsn='" + engsn + "'";

					updateSqlList.add(query);
				}
			}
			query = "update b_engine_info t set tsn=(select max(tsn) from l_eng_flightlog_daily t1 where t1.engsn=t.engsn),infodate=(select max(fidate) from l_eng_flightlog_daily t2 where t2.engsn=t.engsn),csn=(select max(csn) from l_eng_flightlog_daily t2 where t2.engsn=t.engsn) where engsn='"
					+ engsn + "'";
			updateSqlList.add(query);
		}
		// for end

		dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
		dmo.commit(ApmsConst.DS_APMS);
		logger.debug("在飞行日志新增后增加发动机日志拦截器执行结束！");
		logger.debug("全部过程结束！");

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
