
package com.apms.bs.aircraft.interceptor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.apms.bs.intf.omis.Javadatediff;

/**
 * 服务端拦截器实现类,飞机初始化或修改的时候实现
 */
public class FlightLogUpdate implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		CommDMO dmo = new CommDMO();
		String ID="";
		int BaseorgID = 0;//执管基地编号
		double FIAIR=0;//空中时间
		double FIBLOCK=0;//轮挡时间
		
		String FLAIR_SUM="";//总时间
		String FIBLOCK_SUM="";//总循环
		double FIACTLG=0;//起落次数
		String FIACTLG_SUM="";//起落次数
		String FIDATE="";//航班日期
		String USERID="";//更改人
		String UPDATEDY="";//更改时间
		String upstr="";
		String FIACTDEP="";//推出时间
		int MODIFYSTATUS=0;//数据状态
		//更新 前给实际字段赋值
	
		double airsum = new Double(dataValue.get("FLAIR_SUM_HOUR")==null?"0":dataValue.get("FLAIR_SUM_HOUR").toString());
		double airblocksum = new Double(dataValue.get("FIBLOCK_SUM_HOUR")==null?"0":dataValue.get("FIBLOCK_SUM_HOUR").toString());
		airsum=airsum*60;
		airblocksum=airblocksum*60;
		double a=(round(airsum,0,BigDecimal.ROUND_HALF_EVEN));
		double b=(round(airblocksum,0,BigDecimal.ROUND_HALF_EVEN));
		//BigDecimal a=new BigDecimal(Double.toString(airsum));
		//BigDecimal b=new BigDecimal(Double.toString(airblocksum));
		long outairsum=new Double(a).longValue();
		long outairblocksum=new Double(b).longValue();
		dataValue.put("FLAIR_SUM", outairsum);
		dataValue.put("FIBLOCK_SUM", outairblocksum);
		//
		if (dataValue.get("BASEORGID") instanceof ComBoxItemVO) {
			String bid = ((ComBoxItemVO) dataValue.get("BASEORGID")).getId();
			BaseorgID = Integer.parseInt(bid);
		}
		if (dataValue.get("MODIFYSTATUS") instanceof ComBoxItemVO) {
			String modifs = ((ComBoxItemVO) dataValue.get("MODIFYSTATUS")).getId();
			MODIFYSTATUS = Integer.parseInt(modifs);
		}

		if (dataValue.get("FIDATE") != null) {
			FIDATE = dataValue.get("FIDATE").toString();// 航班日期
		} else {
			FIDATE = "";// 航班日期
		}

		if (dataValue.get("FIACTDEP") != null) {
			FIACTDEP = dataValue.get("FIACTDEP").toString();// 航班日期
		} else {
			FIACTDEP = "";// 航班日期
		}

		if (dataValue.get("FIAIR") != null) {
			FIAIR = new Double(dataValue.get("FIAIR").toString());// 空中时间
		} else {
			FIAIR = 0;// 空中时间
		}

		if (dataValue.get("FIBLOCK") != null) {
			FIBLOCK = new Double(dataValue.get("FIBLOCK").toString());// 轮挡时间
		} else {
			FIBLOCK = 0;// 轮挡时间
		}

		if (dataValue.get("FLAIR_SUM") != null) {
			FLAIR_SUM = dataValue.get("FLAIR_SUM").toString();// 总时间
		} else {
			FLAIR_SUM = "0";// 总时间
		}

		if (dataValue.get("FIBLOCK_SUM") != null) {
			FIBLOCK_SUM = dataValue.get("FIBLOCK_SUM").toString();// 总轮挡时间
		} else {
			FIBLOCK_SUM = "0";// 总轮挡时间
		}
		if (dataValue.get("FIACTLG") != null) {
			FIACTLG = new Double(dataValue.get("FIACTLG").toString());// 循环数
		} else {
			FIACTLG = 0;// 循环数
		}

		if (dataValue.get("FIACTLG_SUM") != null) {
			FIACTLG_SUM = dataValue.get("FIACTLG_SUM").toString();// 总循环数
		} else {
			FIACTLG_SUM = "0";// 总循环数
		}

		if (dataValue.get("ID") != null) {
			ID = dataValue.get("ID").toString();// 记录号
		} else {
			ID = "0";// 记录号
		}

		if (dataValue.get("USERID") != null) {
			USERID = dataValue.get("USERID").toString();// 更改人
		} else {
			USERID = "0";// 更改人
		}
		upstr = upstr + ",USERID='" + USERID + "'";
		if (dataValue.get("UPDATEDY") != null) {
			UPDATEDY = dataValue.get("UPDATEDY").toString();// 更改时间
			upstr = upstr + ",UPDATEDY=to_date(substr('" + UPDATEDY + "',0,19),'yyyy-MM-dd hh24:mi:ss')";
		} else {
			UPDATEDY = null;// 更改时间
			upstr = upstr + ",UPDATEDY=null";
		}
		
		logger.info("upstr="+upstr);
		
		String query="";
		long basicFLAIR_SUM=0;
		long basicFIBLOCK_SUM=0;
		long basicFIACTLG_SUM=0;
		long basicfiair=0;
		long AddFLAIR_SUM=0;
		long AddFIBLOCK_SUM=0;
		long AddFIACTLG_SUM=0;
		double addtime=0;
		double addcycle=0;
		double addblock=0;
		String fltdate="";
		int dif;
		String flylogMinTime="";//最大修改日期
		
		String acsn="";
		Javadatediff dateadd=new Javadatediff();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		query="select * from l_ac_flightlog t where id="+ID;
		ArrayList<String> updateSqlList = new ArrayList<String>();
		HashVO[] logVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
		if(logVos.length>0){//如果数据存在
			HashVO logVo=logVos[0];
			acsn=logVo.getStringValue("acnum");
			basicFLAIR_SUM=logVo.getLognValue("FLAIR_SUM");
			fltdate=logVo.getStringValue("fidate");
			basicFIBLOCK_SUM=logVo.getLognValue("FIBLOCK_SUM");
			basicFIACTLG_SUM=logVo.getLognValue("FIACTLG_SUM");
			
			AddFLAIR_SUM=Long.parseLong(FLAIR_SUM)-basicFLAIR_SUM;
			AddFIBLOCK_SUM=Long.parseLong(FIBLOCK_SUM)-basicFIBLOCK_SUM;
			AddFIACTLG_SUM=Long.parseLong(FIACTLG_SUM)-basicFIACTLG_SUM;
			
			basicfiair=logVo.getLognValue("fiair");
//			addtime=FIAIR-logVo.getLognValue("fiair");
//			addcycle=FIACTLG-logVo.getLognValue("fiactlg");
//			addblock=FIBLOCK-logVo.getLognValue("fiblock");
			//TODO modify by zhangzy 增量数据取总量值
			addtime = AddFLAIR_SUM;
			addcycle = AddFIACTLG_SUM;
			addblock = AddFIBLOCK_SUM;
			
			//只有正常数据才改为修正
			if( MODIFYSTATUS ==0 && (addtime!=0 || addcycle!=0 || addblock!=0) ){
				ComBoxItemVO modifycomb = ((ComBoxItemVO)dataValue.get( "MODIFYSTATUS" ));
				modifycomb.setId("1");
				modifycomb.setName("修正");
				dataValue.put("MODIFYSTATUS",modifycomb);//判断数据有修正后，更新修正标志为"修正"
			}
			
			//add by zhangzy 更新本次的小时循环数据
			dataValue.put("FIAIR", FIAIR+addtime);
			dataValue.put("FIBLOCK", FIBLOCK+addblock);
			dataValue.put("FIACTLG", FIACTLG+addcycle);
			
			//zhangzy20151022 是取当前修改数据之后，最近的一个修正数据的时间
			query="select MIN(FIACTDEP) MINDATE from l_ac_flightlog t WHERE ACNUM='"+acsn+"' AND MODIFYSTATUS=1"
				+ " and FIACTDEP>to_date(substr('"+FIACTDEP+"',0,19),'yyyy-MM-dd hh24:mi:ss')";
			
			HashVO[] minDateVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			if (minDateVos.length > 0) {// 存在最大的修正日期
				HashVO minDateVo = minDateVos[0];
				if (minDateVo.getStringValue("MINDATE") != null) {
					flylogMinTime = minDateVo.getStringValue("MINDATE");
				} else {
					flylogMinTime = FIDATE;
				}
			}
			else{
				flylogMinTime = FIDATE;	
			}
			if (FIACTDEP == null) {
				FIACTDEP = fltdate + " 00:00:01";
			}
			
			String uptSubSql = " FLAIR_SUM=FLAIR_SUM+("+addtime+"),FIBLOCK_SUM=FIBLOCK_SUM+("+addblock+"),FIACTLG_SUM=FIACTLG_SUM+("+addcycle+") ";
			
			dif=Javadatediff.dateDiff("d", sdf1.parse(FIDATE), sdf1.parse(flylogMinTime));
			if (dif>=0){
				//修改飞行日志的数据
				if (MODIFYSTATUS==2 || MODIFYSTATUS==3 ){
					query="update l_ac_flightlog set "+uptSubSql
						+" where acnum='"+acsn+"' and fidate>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') ";
				}
				else{
					query="update l_ac_flightlog set  "+uptSubSql+" where acnum='"+acsn
					+"' and fidate>=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and FIACTDEP>to_date(substr('"+FIACTDEP+"',0,19),'yyyy-MM-dd hh24:mi:ss')";
				}
			}
			else{
				if (MODIFYSTATUS==2 || MODIFYSTATUS==3 ){
					query="update l_ac_flightlog set  "+uptSubSql+" where acnum='"+acsn
						+"' and fidate>=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<to_date(substr('"+flylogMinTime+"',0,10),'yyyy-MM-dd')  ";	
				}
				else{
					query="update l_ac_flightlog set  "+uptSubSql+" where acnum='"+acsn
						+"' and fidate>=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<to_date(substr('"+flylogMinTime+"',0,10),'yyyy-MM-dd')  " +
						" and FIACTDEP>to_date(substr('"+FIACTDEP+"',0,19),'yyyy-MM-dd hh24:mi:ss')";
				}
			}
			updateSqlList.add(query);
			
			query="select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg" +
					",max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum" +
					" from l_ac_flightlog t where acnum='"+acsn+"' and fidate=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd')" +
					" group by acnum,fidate,baseorgid order by acnum,fidate";
			HashVO[] flightdailyCvo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			if (flightdailyCvo.length>0){//统计存在，写入飞行日志
				HashVO flightdailyCvo1=flightdailyCvo[0];
				query="select * from l_ac_flightlog_daily where acnum='"+acsn+"' and vdfcdate=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd')";
				HashVO[] flightdailyDvo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
				if (flightdailyDvo.length>0){//日志表里存在
					HashVO flightdailyDvo1=flightdailyDvo[0];
					//修改当天
					query="update l_ac_flightlog_daily set VDFCAIR=vdfcair+("+addtime+"),VDFCBLOCK=vdfcblock+("+addblock+")" +
							",FIACTLG_SUM=FIACTLG_SUM+("+addcycle+"),fiair=fiair+("+addtime+")" +
							",fiblock=fiblock+("+addblock+"),fiactlg=fiactlg+("+addcycle+") " +
							" where acnum='"+acsn+"' and vdfcdate=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd')";
					updateSqlList.add(query);
					//增量数据，往后加
					if(dif>=0){
						query="update L_AC_FLIGHTLOG_DAILY set VDFCAIR=VDFCAIR+("+addtime+"),VDFCBLOCK=VDFCBLOCK+("+addblock+")" +
							",FIACTLG_SUM=FIACTLG_SUM+("+addcycle+")" +
							" where acnum='"+acsn+"' and vdfcdate>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd')";
					}
					else{
						query="update L_AC_FLIGHTLOG_DAILY set VDFCAIR=VDFCAIR+("+addtime+"),VDFCBLOCK=VDFCBLOCK+("+addblock+")" +
						",FIACTLG_SUM=FIACTLG_SUM+("+addcycle+") where acnum='"+acsn+"'" +
						" and vdfcdate>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') " +
						" and vdfcdate<to_date(substr('"+flylogMinTime+"',0,10),'yyyy-MM-dd')";
					}
					updateSqlList.add(query);
				}
				else{
					//判断写入 
					query="insert into l_ac_flightlog_daily " +
						"(id,baseorgid,acnum,vdfcdate,fiair,vdfcair,fiblock,vdfcblock,fiactlg,fiactlg_sum,computedstatus,updatetime)" +
						"values(S_l_ac_flightlog_daily.nextval,"+BaseorgID+",'"+acsn+"',to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd')" +
						","+flightdailyCvo1.getLongValue("fiair")+","+flightdailyCvo1.getLongValue("flair_sum")+","
						+flightdailyCvo1.getLongValue("fiblock")+","+flightdailyCvo1.getLongValue("fiblock_sum")+","
						+flightdailyCvo1.getLongValue("fiactlg")+","+flightdailyCvo1.getLongValue("fiactlg_sum")+",1,sysdate)";
					updateSqlList.add(query);
					//以此为基数，加上当天的数据，往后加
					if(dif>=0){
						query="update L_AC_FLIGHTLOG_DAILY set VDFCAIR=VDFCAIR+("+addtime+"),VDFCBLOCK=VDFCBLOCK+("+addblock+")" +
								",FIACTLG_SUM=FIACTLG_SUM+("+addcycle+")" +
								" where acnum='"+acsn+"' and vdfcdate>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd')";
					}
					else{
						query="update L_AC_FLIGHTLOG_DAILY set VDFCAIR=VDFCAIR+("+addtime+"),VDFCBLOCK=VDFCBLOCK+("+addblock+")" +
								",FIACTLG_SUM=FIACTLG_SUM+("+addcycle+") " +
								"where acnum='"+acsn+"' and vdfcdate>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd')" +
								" and vdfcdate<to_date(substr('"+flylogMinTime+"',0,10),'yyyy-MM-dd')";
					}
					updateSqlList.add(query);
					
				}
			}
			
			dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			dmo.commit(ApmsConst.DS_APMS);		
			updateSqlList.clear();
			
			/**
			//修改发动的日志数据
			String engmodelid="";
			double addtsn=0;
			double addcsn=0;
			String swapdate=null;
			String engmodifydate=null;
			long tsn=0;
			long csn=0;
			String engsn="";
			long englogid=0;
			double FIAIR1 = new Double(FIAIR);//new Double(dataValue.get("TSN").toString());//空中时间
			double FIACTLG1 = new Double(FIACTLG_SUM);//new Double(dataValue.get("CSN").toString());//循环数
			FIAIR1=FIAIR1*60;
			query="select t.engsn,t1.engmodelid from l_eng_flightlog t,b_engine_info t1 where t.engsn=t1.engsn and t.acnum='"+acsn+"' group by t.engsn,t1.engmodelid";
			
			HashVO[] engflyVos= dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			//if(engVos.length>0){//如果数据存在
			for (int i=0;i<engflyVos.length;i++){
				updateSqlList.clear();
				HashVO engflyVos1=engflyVos[i];
				engsn=engflyVos1.getStringValue("engsn");
				engmodelid=engflyVos1.getStringValue("engmodelid");

				query="select * from (select * from l_eng_swaplog t where engsn='"+engsn+"' and swap_date>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and swap_action=0  order by swap_date ) where rownum<2";//取得最小的拆换日期
				HashVO[] engswap = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
				if(engswap.length>0){//如果有拆换日期，数据修改到拆换日期为止
					HashVO engswap1=engswap[0];
					swapdate=engswap1.getStringValue("swap_date");
				}
				else{//没有拆换日期
					swapdate=null;
				}
				query="select * from l_eng_flightlog where engsn='"+engsn+"' and fidate=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and acnum='"+acsn+"' and add_time="+basicfiair;
				HashVO[] englogVos= dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
				if (englogVos.length>0){
					HashVO englogVos1 =englogVos[0];
						tsn=englogVos1.getLongValue("ADD_TIME");
						csn=englogVos1.getLongValue("ADD_CYCLE");
						
						addtsn=FIAIR-tsn;
						addcsn=Math.abs(csn-FIACTLG);
						englogid=englogVos1.getLognValue("id");
						query="select * from (select * from l_eng_flightlog t where engsn='"+engsn+"' and fidate>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd')  and modifystatus=1 order by fidate ) where rownum<2";
						HashVO[] engmodifyvo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
						if(engmodifyvo.length>0){//大于这个日期有修正的的数据
							HashVO engmodifyvo1=engmodifyvo[0];
							engmodifydate=engmodifyvo1.getStringValue("fidate");
						}
						else{//没有修正数据
							engmodifydate=null;
						}
						//判断结束日期
						if (engmodifydate==null&&swapdate==null){//没有拆换日期和修正日期
							//修改当条数据
							query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where id= "+englogid;
							updateSqlList.add(query);
							//修改之后的数据 
							query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd')  and engsn='"+engsn+"' and id<> "+englogid;
							updateSqlList.add(query);
							//修改发动机日志表当天的数据
							query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
							updateSqlList.add(query);
							//修改发动机日志表之后的数据
							query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
							updateSqlList.add(query);
							//dmo.executeUpdateByDS(ApmsConst.DS_APMS, query);
							//dmo.commit(ApmsConst.DS_APMS);
						}
						else if(engmodifydate!=null&&swapdate==null){//有修正日期，没有拆换日志
							query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where  id="+englogid;
							updateSqlList.add(query);
							
							
							query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+engmodifydate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"' and id<>"+englogid;
							updateSqlList.add(query);
							
							query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+engmodifydate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
							updateSqlList.add(query);
							
							query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+engmodifydate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
							updateSqlList.add(query);
						}
						else if(engmodifydate==null&&swapdate!=null){//没有修正日期，有拆换日志
							query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where  id="+englogid;
							updateSqlList.add(query);
							
							
							query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+swapdate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"' and id<>"+englogid;
							updateSqlList.add(query);
							
							query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+swapdate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
							updateSqlList.add(query);
							
							query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+swapdate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
							updateSqlList.add(query);
						}
						else{//有拆换日志和修正日期
							int d=dateadd.dateDiff("d", sdf1.parse(engmodifydate), sdf1.parse(swapdate));
							if (d>=0){//如果修正日期大于拆换日期，则选拆换日期
								query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where id= "+englogid;
								updateSqlList.add(query);
								
								query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+swapdate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"' and id<>"+englogid;
								updateSqlList.add(query);
								
								query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+swapdate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
								
								updateSqlList.add(query);
								
								query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+swapdate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
								
								updateSqlList.add(query);
							}
							else{
								query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where id= "+englogid;
								updateSqlList.add(query);
								
								query="update l_eng_flightlog set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+engmodifydate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"' and id<>"+englogid;
								updateSqlList.add(query);
								
								query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),add_time=add_time+("+addtsn+"),add_cycle=add_cycle+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE=to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+engmodifydate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
							
								updateSqlList.add(query);
								
								
								query="update l_eng_flightlog_daily set tsn=tsn+("+addtsn+"),csn=csn+("+addcsn+"),COMPUTEDSTATUS=1 where FIDATE>to_date(substr('"+FIDATE+"',0,10),'yyyy-MM-dd') and fidate<=to_date(substr('"+engmodifydate+"',0,10),'yyyy-MM-dd') and engsn='"+engsn+"'";
							
								updateSqlList.add(query);
							}
						}
						query="update b_engine_info t set tsn=(select max(tsn) from l_eng_flightlog_daily t1 where t1.engsn=t.engsn),infodate=(select max(fidate) from l_eng_flightlog_daily t2 where t2.engsn=t.engsn),csn=(select max(csn) from l_eng_flightlog_daily t2 where t2.engsn=t.engsn) where engsn='"+engsn+"'";
						updateSqlList.add(query);
						
				dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				dmo.commit(ApmsConst.DS_APMS);
				}//end if
					
			}//end for
			**/

		}//end if
	}//eng function
	 public static double round(double value, int scale, int roundingMode) {   
	        BigDecimal bd = new BigDecimal(value);   
	        bd = bd.setScale(scale, roundingMode);   
	        double d = bd.doubleValue();   
	        bd = null;   
	        return d;   
	    }   


	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

}
