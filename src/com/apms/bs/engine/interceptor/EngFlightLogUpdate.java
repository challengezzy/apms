package com.apms.bs.engine.interceptor;

import java.text.SimpleDateFormat;
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
import com.apms.bs.intf.omis.Javadatediff;

/**
 * 服务端拦截器实现类,飞机初始化或修改的时候实现
 */
public class EngFlightLogUpdate implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {

		String engsn = "";// 发动机序号
		String swapdate = null;
		String modifydate = null;

		String fidate = dataValue.get("FIDATE").toString();// 航班日期
		double tsn_hour = new Double(dataValue.get("TSN_HOUR") == null ? "0" : dataValue.get("TSN_HOUR").toString());// 空中时间
		double csn = new Double(dataValue.get("CSN") == null ? "0" : dataValue.get("CSN").toString());// 循环数

		double tsn_d = tsn_hour*60;
		long tsn =new Double(tsn_d).longValue();
		dataValue.put("TSN", tsn);
		
		if (dataValue.get("ENGSN") != null) {
			engsn = dataValue.get("ENGSN").toString();// 发动机序号
		} else {
			engsn = "";// 发动机序号
		}

		String id = dataValue.get("ID").toString();// 记录号
		double addtsn = 0;
		double addcsn = 0;

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String query = "";
		query = "select * from l_eng_flightlog t where t.id=" + id;
		ArrayList<String> updateSqlList = new ArrayList<String>();
		HashVO[] vos1 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
		if (vos1.length > 0) {// 如果数据存在
			HashVO logvo = vos1[0];
			
			//计算增量小时循环，根据问题进行计算
			addtsn = tsn - logvo.getLongValue("TSN");
			addcsn = csn - logvo.getLongValue("CSN");
			
			dataValue.put("ADD_TIME", new Long(dataValue.get("ADD_TIME").toString())+addtsn );
			dataValue.put("ADD_CYCLE", new Long(dataValue.get("ADD_CYCLE").toString())+addcsn );

			String fidateCond = "to_date(substr('" + fidate+ "',0,10),'yyyy-MM-dd')";
			
			// 取得修改日期后面的最近的拆换日期			
			query = "select swap_date from (select swap_date from l_eng_swaplog t where engsn='" + engsn + "'" +
					" and swap_date>="+fidateCond+" and swap_action=0 order by swap_date ) where rownum<2";// 取得最小的拆换日期
			HashVO[] engswap = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			if (engswap.length > 0) {// 如果有拆换日期，数据修改到拆换日期为止
				HashVO engswap1 = engswap[0];
				swapdate = engswap1.getStringValue("swap_date");
			} else {
				// 没有拆换日期,该日志之后没有拆换
				swapdate = null;
			}
			
			query = "select fiactdep from (select fiactdep from l_eng_flightlog t where engsn='" + engsn + "'" +
					" and fidate>"+fidateCond+" and modifystatus=1 order by fiactdep ) where rownum<2";
			HashVO[] tmpVos2 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			if (tmpVos2.length > 0) {// 大于这个日期有修正的的数据
				HashVO engmodifyvo1 = tmpVos2[0];
				modifydate = engmodifyvo1.getStringValue("fiactdep");
			} else {
				// 最近没有修正数据
				modifydate = null;
			}
			
			//判断拆换日期和最近的修正日期数据
			// 判断结束日期
			if (modifydate == null && swapdate == null) {// 没有拆换日期和修正日期
				query = "update l_eng_flightlog set tsn=tsn+(" + addtsn + "),csn=csn+(" + addcsn + ")" +
						",computedstatus=1" +
						" where fidate>="+fidateCond+" and engsn='"+ engsn + "' ";
				updateSqlList.add(query);

				query = "update l_eng_flightlog_daily set tsn=tsn+(" + addtsn + "),csn=csn+(" + addcsn + ")" +
						",add_time=add_time+(" + addtsn + "),add_cycle=add_cycle+(" + addcsn+ "),COMPUTEDSTATUS=1" +
						" where FIDATE="+fidateCond+" and engsn='" + engsn + "'";
				updateSqlList.add(query);

				query = "update l_eng_flightlog_daily set tsn=tsn+(" + addtsn + "),csn=csn+(" + addcsn + "),COMPUTEDSTATUS=1" +
						" where FIDATE>"+fidateCond+" and engsn='" + engsn + "'";
				updateSqlList.add(query);
				
			} else{
				String lastDate = modifydate;
				if(modifydate == null){
					lastDate = swapdate;
				}else if(swapdate == null){
					lastDate = modifydate;
				}else{
					int d = Javadatediff.dateDiff("d", sdf1.parse(modifydate), sdf1.parse(swapdate));
					if (d >= 0) {// 如果修正日期大于拆换日期，则选拆换日期
						lastDate = swapdate;
					}else{
						lastDate = modifydate;
					}
				}
				
				String lastCond = "to_date(substr('" +lastDate+ "',0,10),'yyyy-MM-dd')";
				//使用日期限制 
				query = "update l_eng_flightlog set tsn=tsn+(" + addtsn + "),csn=csn+(" + addcsn + ")" +
						 ",computedstatus=1" +
						" where FIDATE>="+fidateCond+" and fidate<="+lastCond+" and engsn='" + engsn + "'";
				updateSqlList.add(query);

				query = "update l_eng_flightlog_daily set tsn=tsn+(" + addtsn + "),csn=csn+(" + addcsn + ")" +
						",add_time=add_time+(" + addtsn + "),add_cycle=add_cycle+(" + addcsn+ "),COMPUTEDSTATUS=1" +
						" where FIDATE="+fidateCond+" and fidate<="+lastCond+" and engsn='"+ engsn + "'";
				updateSqlList.add(query);

				query = "update l_eng_flightlog_daily set tsn=tsn+(" + addtsn + "),csn=csn+(" + addcsn + "),COMPUTEDSTATUS=1" 
						+" where FIDATE>"+fidateCond+" and fidate<="+lastCond+" and engsn='" + engsn + "'";
				updateSqlList.add(query);
			} 
			
			query = "update b_engine_info t" +
					" set tsn=(select max(tsn) from l_eng_flightlog_daily t1 where t1.engsn=t.engsn)" +
					",infodate=(select max(fidate) from l_eng_flightlog_daily t2 where t2.engsn=t.engsn)" +
					",csn=(select max(csn) from l_eng_flightlog_daily t2 where t2.engsn=t.engsn)" +
					" where engsn='"+ engsn + "'";
			
			updateSqlList.add(query);
			dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			dmo.commit(ApmsConst.DS_APMS);
		}
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
