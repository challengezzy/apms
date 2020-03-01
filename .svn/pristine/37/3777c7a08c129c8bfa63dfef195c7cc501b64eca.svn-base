

package com.apms.bs.aircraft.interceptor;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.publics.form.bs.service.FormInterceptor;
import smartx.publics.form.bs.service.SmartXFormService;

/**
 * 飞机飞行日志删除后 拦截器
 */
public class FlightLogDelBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
	
	
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		CommDMO dmo = new CommDMO();
		String acid="";
		String updatestr="";
		String fidate="";
		long fiair=0;
		long fiblock=0;
		long fiactlg=0;
		String fiactdep ="";
		ArrayList<String> updateSqlList = new ArrayList<String>();
		for(Map<String,Object> map : dataValueList){
			//不确定flag的key是什么，取再次
			String flag = (String) map.get(SmartXFormService.KEYNAME_MODIFYFLAG);
			if(flag == null){//新增时也调用 了些拦截器，此时flag为null，不处理
				flag = (String) map.get("flag");
			}
			
			if(flag == null){
				break;
			}
			
			
			String logid = map.get("ID").toString();//得到主要值
			
			//flag 标记数据要作的处理状态，有insert,update,delete 三种
			if(flag.equalsIgnoreCase("delete")){
				updateSqlList.clear();
				String query="select * from l_ac_flightlog where id="+logid;	
				
				HashVO[] flylogVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
				if(flylogVos.length>0){//有存在日志则不写入，否则就写入日志
					HashVO flylogVos1=flylogVos[0];
					acid=flylogVos1.getStringValue("acnum");
					fidate=flylogVos1.getStringValue("fidate");
					fiair=flylogVos1.getLognValue("fiair");
					fiblock=flylogVos1.getLognValue("fiblock");
					fiactlg=flylogVos1.getLognValue("fiactlg");
					fiactdep=flylogVos1.getStringValue("fiactdep");
					query="select * from (select * from l_ac_flightlog t where modifystatus='1' and acnum='"+acid+"' and fidate>=to_date(substr('"+fidate+"',0,10),'yyyy-MM-dd')  and fiactdep>to_date(substr('"+fiactdep+"',0,19),'yyyy-MM-dd hh24:mi:ss')  order by fidate,fiactdep ) where rownum<2";
					HashVO[] flyvo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
					if(flyvo.length>0){//有修正数据
						HashVO vomodfydate=flyvo[0];
						updatestr="update l_ac_flightlog set flair_sum=flair_sum-"+fiair+",fiblock_sum=fiblock_sum-"+fiblock+",fiactlg_sum=fiactlg_sum-"+fiactlg+" where acnum='"+acid+"' and fidate>=to_date(substr('"+fidate+"',0,10),'yyyy-MM-dd')  and fiactdep>to_date(substr('"+fiactdep+"',0,19),'yyyy-MM-dd hh24:mi:ss') and fidate<to_date(substr('"+vomodfydate.getStringValue("fidate")+"',0,10),'yyyy-MM-dd')  and fiactdep<to_date(substr('"+vomodfydate.getStringValue("fiactdep")+"',0,19),'yyyy-MM-dd hh24:mi:ss')";
						updateSqlList.add(updatestr);
						
						updatestr="update l_ac_flightlog_daily set vdfcair=vdfcair-"+fiair+",vdfcblock=vdfcblock-"+fiblock+",fiactlg_sum=fiactlg_sum-"+fiactlg+" where acnum='"+acid+"' and vdfcdate>=to_date(substr('"+fidate+"',0,10),'yyyy-MM-dd') and vdfcdate<=to_date(substr('"+vomodfydate.getStringValue("fidate")+"',0,10),'yyyy-MM-dd')";
						updateSqlList.add(updatestr);
					}
					else{//没有修正数据
						updatestr="update l_ac_flightlog set flair_sum=flair_sum-"+fiair+",fiblock_sum=fiblock_sum-"+fiblock+",fiactlg_sum=fiactlg_sum-"+fiactlg+" where acnum='"+acid+"' and fidate>=to_date(substr('"+fidate+"',0,10),'yyyy-MM-dd') and fiactdep>to_date(substr('"+fiactdep+"',0,19),'yyyy-MM-dd hh24:mi:ss') ";
						updateSqlList.add(updatestr);
						//修改飞行数据日数据
						updatestr="update l_ac_flightlog_daily set vdfcair=vdfcair-"+fiair+",vdfcblock=vdfcblock-"+fiblock+",fiactlg_sum=fiactlg_sum-"+fiactlg+" where acnum='"+acid+"' and vdfcdate>=to_date(substr('"+fidate+"',0,10),'yyyy-MM-dd') ";
						updateSqlList.add(updatestr);
						
					}
				}
				dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				dmo.commit(ApmsConst.DS_APMS);
			}
		}
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
