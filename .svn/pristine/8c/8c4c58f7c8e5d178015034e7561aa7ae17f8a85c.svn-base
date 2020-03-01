
package com.apms.bs.aircraft.interceptor;

import java.util.ArrayList;
import java.util.Date;
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
import com.apms.bs.util.DateUtil;

/**
 * 服务端拦截器实现类,飞机初始化或修改的时候实现
 */
public class AirCraftInitUpdateBefore implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		CommDMO dmo = new CommDMO();
		int BaseorgID = 0;//执管基地编号
		String ENGMODELID="";//发动机型号
		String ENGSN="";//发动机序号
		String ENGLOC="";//存放位置
		String AIRCRAFTID="";//飞机编号
		String ENGWING_LOC="";//在翼位置
		String INFODATE="";//数据日期
		String TSN_HRS="";//总时间
		String CSN="";//总循环
		String CHKFH_HRS="";//维修后总时间
		String CHKFC="";//维修后总循环
		String CHKSTATE="";//维修标志
		String UPDAY="";//装上时间
		String DOWNDY="";//拆下时间
		String DATESTATE="";//数据状态
		String EFFECT="";//数据有效性
		String OIL_SEAL_DATE="";//油封时间
		String OIL_SEAL_DAY="";//油封期限
		String LLP_LIMIT="";//LLP最低循环
		String LLP_COMPNENT="";//LLP最低循环部件名称
		String USERID="";//更改人
		String UPDATEDY="";//更改时间
		String upstr="";
		
		//下拉框执行错误
		if(dataValue.get( "BASEORGID" ) instanceof ComBoxItemVO){
			String id = ((ComBoxItemVO)dataValue.get( "BASEORGID" )).getId();
			BaseorgID = Integer.parseInt(id);
			//dataValue.put( "MTTYPE", dbom.getMtTypeByChartType( type ) );
		}
/*
		if (dataValue.get("BASEORGID")!=null){
			String BaseorgID1=dataValue.get("BASEORGID").toString();//执管基地编号
			if (BaseorgID1.equals("杭州维修基地")){
				BaseorgID=3;
				}
			else if(BaseorgID1.equals("上海维修基地")){
				BaseorgID=4;
			}
			else{
				BaseorgID=0;
			}
		}
		else
		{
			BaseorgID=0;//执管基地编号
		}
		*/
		upstr=",BaseorgID="+BaseorgID;
		if (dataValue.get("ENGMODELID")!=null){
			ENGMODELID=dataValue.get("ENGMODELID").toString();//发动机型号
		}
		else
		{
			ENGMODELID="0";//发动机型号
		}
		//upstr=upstr+",ENGMODELID='"+ENGMODELID+"'";
		
		//if (dataValue.get("ENGMODELID")!=null){
		//	ENGMODELID=dataValue.get("ENGMODELID").toString();//发动机型号
		//}
		//else
		//{
		//	ENGMODELID="0";//发动机型号
		//}
		
		if (dataValue.get("ENGSN")!=null){
			ENGSN=dataValue.get("ENGSN").toString();//发动机序号
		}
		else
		{
			ENGSN="0";//发动机序号号
		}
		if(dataValue.get( "ENGLOC" ) instanceof ComBoxItemVO){
			ENGLOC = ((ComBoxItemVO)dataValue.get( "ENGLOC" )).getId();
			//BaseorgID = Integer.parseInt(id);
			//dataValue.put( "MTTYPE", dbom.getMtTypeByChartType( type ) );
		}
	/*	
		if (dataValue.get("ENGLOC")!=null){
			ENGLOC=dataValue.get("ENGLOC").toString();//存放位置
			if (ENGLOC.equals("在翼")){
				ENGLOC="1";
			}
			else if(ENGLOC.equals("库房")){
				ENGLOC="0";
			}
			else{
				ENGLOC="2";
			}
		}
		else
		{
			ENGLOC="0";//存放位置
		}
		//upstr=upstr+",ENGLOC="+ENGLOC;
		*/
		if (dataValue.get("AIRCRAFTID")!=null){
			AIRCRAFTID=dataValue.get("AIRCRAFTID").toString();//飞机编号
		}
		else
		{
			AIRCRAFTID="0";//飞机编号
		}
		//upstr=upstr+",AIRCRAFTID='"+AIRCRAFTID+"'";
	
		
		if (dataValue.get("ENGWING_LOC")!=null){
			ENGWING_LOC=dataValue.get("ENGWING_LOC").toString();//在翼位置
		}
		else
		{
			ENGWING_LOC="0";//在翼位置
		}
		//upstr=upstr+",ENGWING_LOC="+ENGWING_LOC;
		
		if (dataValue.get("INFODATE")!=null){
			INFODATE=dataValue.get("INFODATE").toString();//数据日期
		}
		else
		{
			INFODATE="";//数据日期
		}
		
		if (dataValue.get("TSN")!=null){
			TSN_HRS=dataValue.get("TSN").toString();//总时间
		}
		else
		{
			TSN_HRS="0";//总时间
		}
		
		if (dataValue.get("CSN")!=null){
			CSN=dataValue.get("CSN").toString();//总循环
		}
		else
		{
			CSN="0";//总循环
		}
		if (dataValue.get("CHKFH")!=null){
			CHKFH_HRS=dataValue.get("CHKFH").toString();//维修后总时间
		}
		else
		{
			CHKFH_HRS="0";//维修后总时间
		}
		if (dataValue.get("CHKFC")!=null){
			CHKFC=dataValue.get("CHKFC").toString();//维修后总循环
		}
		else
		{
			CHKFC="0";//维修后总循环
		}
		if(dataValue.get( "CHKSTATE" ) instanceof ComBoxItemVO){
			CHKSTATE = ((ComBoxItemVO)dataValue.get( "CHKSTATE" )).getId();
			//BaseorgID = Integer.parseInt(id);
			//dataValue.put( "MTTYPE", dbom.getMtTypeByChartType( type ) );
		}
		/*
		if (dataValue.get("CHKSTATE")!=null){
			CHKSTATE=dataValue.get("CHKSTATE").toString();//维修标志
			if(CHKSTATE.equals("未送修")){
				CHKSTATE="0";
			}
			else{
				CHKSTATE="1";
			}
		}
		else
		{
			CHKSTATE="0";//维修标志
		}
		
		//upstr=upstr+",CHKSTATE="+CHKSTATE;
		*/
		if (dataValue.get("UPDAY")!=null){
			UPDAY=dataValue.get("UPDAY").toString();//装上时间
			//upstr=upstr+",UPDAY=to_date(substr('"+UPDAY+"',0,10),'yyyy-mm-dd')";
		}
		else
		{
			UPDAY=null;//装上时间
			//upstr=upstr+",UPDAY=null";
		}
		
		
		if (dataValue.get("DOWNDY")!=null){
			DOWNDY=dataValue.get("DOWNDY").toString();//拆下时间
		//	upstr=upstr+",DOWNDY=to_date(substr('"+DOWNDY+"',0,10),'yyyy-mm-dd')";
		}
		else
		{
			DOWNDY=null;//拆下时间
		//	upstr=upstr+",DOWNDY=null";
		}
		if(dataValue.get( "DATESTATE" ) instanceof ComBoxItemVO){
			DATESTATE = ((ComBoxItemVO)dataValue.get( "DATESTATE" )).getId();
			//BaseorgID = Integer.parseInt(id);
			//dataValue.put( "MTTYPE", dbom.getMtTypeByChartType( type ) );
		}
		/*
		if (dataValue.get("DATESTATE")!=null){
			DATESTATE=dataValue.get("DATESTATE").toString();//数据状态
			if (DATESTATE.equals("初始化")){
				DATESTATE="2";	
			}
			else if(DATESTATE.equals("正常")){
				DATESTATE="0";
			}
			else{
				DATESTATE="1";
			}
		}
		else
		{
			DATESTATE="0";//数据状态
		}
		*/
		if (dataValue.get("EFFECT")!=null){
			EFFECT=dataValue.get("EFFECT").toString();//数据有效性
			
		}
		else
		{
			EFFECT="0";//数据有效性
		}
		if (dataValue.get("OIL_SEAL_DATE")!=null){
			OIL_SEAL_DATE=dataValue.get("OIL_SEAL_DATE").toString();//油封时间
			//upstr=upstr+",OIL_SEAL_DATE=to_date(substr('"+OIL_SEAL_DATE+"',0,19),'yyyy-mm-dd HH:mi:ss')";
		}
		else
		{
			OIL_SEAL_DATE=null;//油封时间
			//upstr=upstr+",OIL_SEAL_DATE=null";
		}
		
		if (dataValue.get("OIL_SEAL_DAY")!=null){
			OIL_SEAL_DAY=dataValue.get("OIL_SEAL_DAY").toString();//油封期限
		}
		else
		{
			OIL_SEAL_DAY="0";//油封期限
		}
		//upstr=upstr+",OIL_SEAL_DAY="+OIL_SEAL_DAY;	
	
		if (dataValue.get("LLP_LIMIT")!=null){
			LLP_LIMIT=dataValue.get("LLP_LIMIT").toString();//LLP最低循环
		}
		else
		{
			LLP_LIMIT="";//LLP最低循环
		}
		if (dataValue.get("LLP_COMPNENT")!=null){
			LLP_COMPNENT=dataValue.get("LLP_COMPNENT").toString();//LLP最低循环部件名称
		}
		else
		{
			LLP_COMPNENT="";//LLP最低循环部件名称
		}
		if (dataValue.get("USERID")!=null){
			USERID=dataValue.get("USERID").toString();//更改人
		}
		else
		{
			USERID="0";//更改人
		}
		upstr=upstr+",USERID='"+USERID+"'";
		if (dataValue.get("UPDATEDY")!=null){
			UPDATEDY=dataValue.get("UPDATEDY").toString();//更改时间
			upstr=upstr+",UPDATEDY=to_date(substr('"+UPDATEDY+"',0,19),'yyyy-MM-dd hh24:mi:ss')";
		}
		else
		{
			UPDATEDY=null;//更改时间
			upstr=upstr+",UPDATEDY=null";
		}
	
		
		//upstr=",BaseorgID="+BaseorgID+",ENGMODELID="+ENGMODELID+",ENGLOC="+ENGLOC+",AIRCRAFTID="+AIRCRAFTID+",ENGWING_LOC="+ENGWING_LOC+"";
		//upstr=upstr+",CHKSTATE="+CHKSTATE+",UPDAY="+UPDAY+",DOWNDY="+DOWNDY+",EFFECT="+EFFECT+",OIL_SEAL_DATE="+OIL_SEAL_DATE+",OIL_SEAL_DAY="+OIL_SEAL_DAY+"";	
		//upstr=upstr+",USERID=0,UPDATEDY=sysdate";
		logger.info("upstr="+upstr);
		String MaxDate="";//最大修改日期
		String query="";
		long basicTsn=0;
		long basiccs=0;
		long Addtsn=0;
		long AddCsn=0;
		long AddCHKTSN=0;
		long AddCHKCsn=0;
		long fair=0;
		String acsn="";
		Javadatediff dateadd=new Javadatediff();
		query="select * from b_engine_info t,b_aircraft t1 where t.aircraftid=t1.id and ENGSN='"+ENGSN+"'";
		ArrayList<String> updateSqlList = new ArrayList<String>();
		HashVO[] engVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
		if(engVos.length>0){//如果发动机已经存在
			HashVO engVos1=engVos[0];
			acsn=engVos1.getStringValue("aircraftsn");
			//if (DATESTATE=="2"||DATESTATE=="初始化"){//如果发动存在又录入的是初始化，则不做修改
				
			//}
			//else{//否则就修改
			
				if (engVos1.getIntegerValue("ENGLOC")==1){//在翼的时候才能修改
					//判断数据是否存在
					query="select * from  l_frengdaly where engsn='"+ENGSN+"' and infodate =to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd') order by infodate";
					HashVO[] engdalyVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
					if(engdalyVos.length>0){//发动机数据日志存在
						
						//basicTsn=engdalyVos1.getLongValue("TSN");
						//basiccs=engdalyVos1.getLongValue("CSN");
						//fair=Long.parseLong(TSN_HRS)-basicTsn;
						//取得大于当前修改日期的修正日期
						query="select * from  l_frengdaly where engsn='"+ENGSN+"' and infodate>to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd') and datestate=1 order by infodate";
						HashVO[] engMax = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
						if (engMax.length>0) {//如果存在
							HashVO engMax1=engMax[0];
							MaxDate=engMax1.getStringValue("infodate");
						}
						else{
							MaxDate=dateadd.DateAdd("d", INFODATE, 365);
						}
						
						HashVO engdalyVos2=engdalyVos[0];//当天的数据
						//取得输入数据与当天数据库的数据的差值
						Addtsn=Long.parseLong(TSN_HRS)-engdalyVos2.getLongValue("TSN");
						AddCsn=Long.parseLong(CSN)-engdalyVos2.getLongValue("CSN");
						
						AddCHKTSN=Long.parseLong(CHKFH_HRS)-engdalyVos2.getLongValue("CHKFH");
						AddCHKCsn=Long.parseLong(CHKFC)-engdalyVos2.getLongValue("CHKFC");
						
						query="update l_frengdaly set datestate="+DATESTATE+upstr+", tsn="+Long.parseLong(TSN_HRS)+",csn="+Long.parseLong(CSN)+",FHAIR="+fair+", CHKFH="+Long.parseLong(CHKFH_HRS)+",CHKFC="+Long.parseLong(CHKFC)+" where engsn='"+ENGSN+"' and infodate=to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd')";
						updateSqlList.add(query);
						
						query="update l_frengdaly set tsn=TSN+("+Addtsn+")"+upstr+",csn=CSN+("+AddCsn+"), CHKFH=CHKFH+("+AddCHKTSN+"),CHKFC=CHKFC+("+AddCHKCsn+") where engsn='"+ENGSN+"' and infodate between to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd')+1 and to_date(substr('"+MaxDate+"',1,10),'yyyy-MM-dd')";
						updateSqlList.add(query);
						
						query="update l_frengdaly_log set  flair_sum=flair_sum+("+Addtsn+") where fidate>=to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd') and fiapnum='"+acsn+"'";
						updateSqlList.add(query);
						
						//第一次写入发动机拆换信息表。
						query="select * from L_ENG_SWAPINFO t where engine_no='"+ENGMODELID+"' and install_eng_num='"+ENGMODELID+"' and install_num='"+ENGSN+"'";
						HashVO[] enginstall = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
							if (enginstall.length==0){//没有数据
								query="insert into L_ENG_SWAPINFO(id,repair_time,engine_no,plane_no,engine_postion,install_eng_num,install_num,install_time,install_cycle,install_update_time,INSTALL_UPDATE_CYCLE,comments,update_man,update_time)" +
										"values(S_L_ENG_SWAPINFO.nextval,to_date(substr('"+INFODATE+"',0,10),'yyyy-mm-dd'),'"+ENGMODELID+"','"+AIRCRAFTID+"',"+ENGWING_LOC+",'"+ENGMODELID+"','"+ENGSN+"',"+TSN_HRS+","+CSN+","+CHKFH_HRS+","+CHKFC+",'',0,sysdate)";
								updateSqlList.add(query);
								
							}
							//写入 llp表
							query="select * from l_eng_llpparts where engsn='"+ENGSN+"'";	
							HashVO[] engvollp = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
							if (engvollp.length==0){
								query="insert into l_eng_llpparts (id,engid,engsn,part_area)select s_l_eng_llpparts.nextval, t.id,'"+ENGSN+"',t1.module from b_engine_model t,b_eng_llp_struct t1 where t.id=t1.model_id and t.model='"+ENGMODELID+"'";
								dmo.executeUpdateByDS(ApmsConst.DS_APMS, query);
								dmo.commit(ApmsConst.DS_APMS);
							}
						///写入LLP表end
						
						dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
						dmo.commit(ApmsConst.DS_APMS);
						
					}
					else{//数据不存在
						query="insert into l_frengdaly(id,baseorgid,engname,engsn,engloc,acno,infodate,tsn,csn,upday,fhair,chkfh,chkfc,datestate,status,userid,updatedy,reccorrect,chkstate)" +
								"values(s_l_frengdaly.nextval,"+BaseorgID+",'"+ENGMODELID+"','"+ENGSN+"','"+ENGLOC+"','"+AIRCRAFTID+"',to_date(substr('"+INFODATE+"',0,10),'yyyy-MM-dd'),"+TSN_HRS+","+CSN+",to_date(substr('"+UPDAY+"',0,10),'yyyy-MM-dd'),0,"+CHKFH_HRS+","+CHKFC+","+DATESTATE+",1,'"+USERID+"',sysdate,1,1)";
						dmo.executeUpdateByDS(ApmsConst.DS_APMS, query);
						dmo.commit(ApmsConst.DS_APMS);
						
						//第一次写入发动机拆换信息表。
						query="select * from L_ENG_SWAPINFO t where engine_no='"+ENGMODELID+"' and install_eng_num='"+ENGMODELID+"' and install_num='"+ENGSN+"'";
						HashVO[] enginstall = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
							if (enginstall.length==0){//没有数据
								query="insert into L_ENG_SWAPINFO(id,repair_time,engine_no,plane_no,engine_postion,install_eng_num,install_num,install_time,install_cycle,install_update_time,INSTALL_UPDATE_CYCLE,comments,update_man,update_time)" +
										"values(S_L_ENG_SWAPINFO.nextval,to_date(substr('"+INFODATE+"',0,10),'yyyy-mm-dd'),'"+ENGMODELID+"','"+AIRCRAFTID+"',"+ENGWING_LOC+",'"+ENGMODELID+"','"+ENGSN+"',"+TSN_HRS+","+CSN+","+CHKFH_HRS+","+CHKFC+",'',0,sysdate)";
								//updateSqlList.add(query);
								dmo.executeUpdateByDS(ApmsConst.DS_APMS, query);
								dmo.commit(ApmsConst.DS_APMS);
							}
						
						
							//写入 llp表
							query="select * from l_eng_llpparts where engsn='"+ENGSN+"'";	
							HashVO[] engvollp = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
							if (engvollp.length==0){
								query="insert into l_eng_llpparts (id,engid,engsn,part_area)select s_l_eng_llpparts.nextval, t.id,'"+ENGSN+"',t1.module from b_engine_model t,b_eng_llp_struct t1 where t.id=t1.model_id and t.model='"+ENGMODELID+"'";
								dmo.executeUpdateByDS(ApmsConst.DS_APMS, query);
								dmo.commit(ApmsConst.DS_APMS);
							}
						///写入LLP表end
					}
					
				
				
					 	//boolean result=TSN_HRS.matches("[0-9]+");
					 	//boolean result1=CSN.matches("[0-9]+");
				        //if (result == true&&result1==true){ 
				        //	Addtsn=basicTsn-Long.parseLong(TSN_HRS);
				        //	AddCsn=basiccs-Long.parseLong(CSN);
				        	
				           // System.out.println("该字符串是纯数字");
				        
				        //}else{
				        //System.out.println("该字符串不是纯数字");
				       // }
				}//在翼
				else{//不在翼
					
				}//不在翼
			}//不是初始化
			
		//}
		else{//发动机不存在
			if(ENGLOC.equals("1")){//在翼才修改开始
				
				query="select * from  l_frengdaly where engsn='"+ENGSN+"' and infodate between to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd')-1 and  to_date(substr('"+INFODATE+"',0,10),'yyyy-MM-dd') order by infodate";
				HashVO[] engdalyVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
				if(engdalyVos.length>0){//发动机数据日志存在
					HashVO engdalyVos1=engdalyVos[0];//前一天的数据
					basicTsn=engdalyVos1.getLongValue("TSN");
					basiccs=engdalyVos1.getLongValue("CSN");
					fair=Long.parseLong(TSN_HRS)-basicTsn;
					acsn=engdalyVos1.getStringValueForDay("acno");
					//取得大于当前修改日期的修正日期
					query="select * from  l_frengdaly where engsn='"+ENGSN+"' and infodate>to_date(substr('"+INFODATE+"',0,10),'yyyy-MM-dd') and datestate=1 order by infodate";
					HashVO[] engMax = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
					if (engMax.length>0) {//如果存在---------------------------
						HashVO engMax1=engMax[0];
						MaxDate=engMax1.getStringValue("infodate");
						
					}
					else{
						MaxDate=Javadatediff.DateAdd("d", INFODATE, 365);
					}
					
					HashVO engdalyVos2=engdalyVos[1];//当天的数据
					//取得输入数据与当天数据库的数据的差值
					Addtsn=Long.parseLong(TSN_HRS)-engdalyVos2.getLongValue("TSN");
					AddCsn=Long.parseLong(CSN)-engdalyVos2.getLongValue("CSN");
					
					AddCHKTSN=Long.parseLong(CHKFH_HRS)-engdalyVos2.getLongValue("CHKFH");
					AddCHKCsn=Long.parseLong(CHKFC)-engdalyVos2.getLongValue("CHKFC");
					
					query="update l_frengdaly set datestate=1 "+upstr+", tsn="+Long.parseLong(TSN_HRS)+",csn="+Long.parseLong(CSN)+",FHAIR="+fair+", CHKFH="+Long.parseLong(CHKFH_HRS)+",CHKFC="+Long.parseLong(CHKFC)+" where engsn='"+ENGSN+"' and infodate=to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd');";
					updateSqlList.add(query);
					if(MaxDate==null){
						query="update l_frengdaly set tsn=TSN+("+Addtsn+")"+upstr+",csn=CSN+("+AddCsn+"), CHKFH=CHKFH+("+AddCHKTSN+"),CHKFC=CHKFC+("+AddCHKCsn+") where engsn='"+ENGSN+"' and infodate>=to_date(substr('"+INFODATE+"',0,10),'yyyy-MM-dd')+1;";
					}
					else{
					query="update l_frengdaly set tsn=TSN+("+Addtsn+")"+upstr+",csn=CSN+("+AddCsn+"), CHKFH=CHKFH+("+AddCHKTSN+"),CHKFC=CHKFC+("+AddCHKCsn+") where engsn='"+ENGSN+"' and infodate between to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd')+1 and to_date(substr('"+MaxDate+"',1,10),'yyyy-MM-dd');";
					}
					updateSqlList.add(query);
					query="update l_frengdaly_log set  flair_sum="+Long.parseLong(TSN_HRS)+" where fidate=to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd') and fiapnum='"+acsn+"';";
					updateSqlList.add(query);
					
					query="update l_frengdaly_log set  flair_sum=flair_sum+("+Addtsn+") where fidate>to_date(substr('"+INFODATE+"',1,10),'yyyy-MM-dd') and fiapnum='"+acsn+"';";
					updateSqlList.add(query);

					//第一次写入发动机拆换信息表。
					query="select * from L_ENG_SWAPINFO t where engine_no='"+ENGMODELID+"' and install_eng_num='"+ENGMODELID+"' and install_num='"+ENGSN+"'";
					HashVO[] enginstall = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
						if (enginstall.length==0){//没有数据
							query="insert into L_ENG_SWAPINFO(id,repair_time,engine_no,plane_no,engine_postion,install_eng_num,install_num,install_time,install_cycle,install_update_time,INSTALL_UPDATE_CYCLE,comments,update_man,update_time)" +
									"values(S_L_ENG_SWAPINFO.nextval,to_date(substr('"+INFODATE+"',0,10),'yyyy-mm-dd'),'"+ENGMODELID+"','"+AIRCRAFTID+"',"+ENGWING_LOC+",'"+ENGMODELID+"','"+ENGSN+"',"+TSN_HRS+","+CSN+","+CHKFH_HRS+","+CHKFC+",'',0,sysdate)";
							updateSqlList.add(query);
							
						}
						//写入 llp表
						query="select * from l_eng_llpparts where engsn='"+ENGSN+"'";	
						HashVO[] engvollp = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
						if (engvollp.length==0){
							query="insert into l_eng_llpparts (id,engid,engsn,part_area)select s_l_eng_llpparts.nextval, t.id,t.model,'',t1.module from b_engine_model t,b_eng_llp_struct t1 where t.id=t1.model_id and t.model='"+ENGMODELID+"'";
							dmo.executeUpdateByDS(ApmsConst.DS_APMS, query);
							dmo.commit(ApmsConst.DS_APMS);
						}
					///写入LLP表end
					dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
					dmo.commit(ApmsConst.DS_APMS);
				}
				else{//发动机数据不存在
					acsn=AIRCRAFTID;
					query="insert into l_frengdaly(id,baseorgid,engname,engsn,engloc,acno,infodate,tsn,csn,upday,downday,fhair,chkfh,chkfc,datestate,status,userid,updatedy,reccorrect,chkstate)"+
						"values()";
					
				}
			//query="insert into b_engine_info(id,aircraftid,baseorgid,engmodelid,engsn,engwing_loc,engloc,status,infodate,tsn,csn,upday,downdy,chkfh,chkfc,datestate,userid,updatedy,chkstate,oil_seal_date,oil_seal_day,eng_gaixing,effect)" +
			//		"values(s_b_engine_info.nextval,"+AIRCRAFTID+","+BaseorgID+","+ENGMODELID+","+ENGSN+","+ENGWING_LOC+","+ENGLOC+",1,"+INFODATE+","+TSN_HRS+","+CSN+","+UPDAY+","+DOWNDY+","+CHKFH_HRS+","+CHKFC+","+DATESTATE+",0,sysdate,"+CHKSTATE+","+OIL_SEAL_DATE+","+OIL_SEAL_DAY+",0,"+EFFECT+")";
			//updateSqlList.add(query);
				
			//第一次写入发动机拆换信息表。
			//query="select * from L_ENG_SWAPINFO t where engine_no='"+ENGMODELID+"' and install_eng_num='"+ENGMODELID+"' and install_num='"+ENGSN+"'";
			//HashVO[] enginstall = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
			//	if (enginstall.length==0){//没有数据
			//		query="insert into L_ENG_SWAPINFO(id,repair_time,engine_no,plane_no,engine_postion,install_eng_num,install_num,install_time,install_cycle,install_update_time,INSTALL_UPDATE_CYCLE,comments,update_man,update_time)" +
			//				"values(s_l_eng_repairinfo.nextval,to_date('"+INFODATE+"','yyyy-mm-dd'),'"+ENGMODELID+"','"+AIRCRAFTID+"',"+ENGWING_LOC+",'"+ENGMODELID+"','"+ENGSN+"',"+TSN_HRS+","+CSN+","+CHKFH_HRS+","+CHKFC+",'',0,sysdate)";
			//		updateSqlList.add(query);
			//		
			//	}
				//写入 llp表
				
			///写入LLP表end
			 
			 
			}//在翼end
			else{//不在翼start
				
			}//不在翼end
		}//发动机不存在
		
		//更新当前最新信息
			
		
		
		query="select * from (select * from l_frengdaly t where datestate=0 and status=1 and engsn='"+ENGSN+"' order by infodate desc) where rownum<2";
		HashVO[] engmaxvalue = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, query);
		if(engmaxvalue.length>0){
		HashVO	engmaxvalue1=engmaxvalue[0];
		dataValue.put("TSN", engmaxvalue1.getLognValue("tsn"));
		dataValue.put("CSN", engmaxvalue1.getLognValue("csn"));
		dataValue.put("CHKFH", engmaxvalue1.getLognValue("chkfh"));
		dataValue.put("CHKFC", engmaxvalue1.getLognValue("chkfc"));
		dataValue.put("INFODATE", engmaxvalue1.getStringValue("INFODATE"));
		
		}//更新最新信息end
		//double weightBase = new Double(dataValue.get("WEIGHT_BASE").toString());
		//double momentBase = new Double(dataValue.get("MOMENT_BASE").toString());
		
		//double modifyPosition = new Double(dataValue.get("MODIFY_POSTION").toString());
		//double kValue = new Double(dataValue.get("K_VALUE").toString());
		
		//String acId = dataValue.get("ID").toString();
		
		//String weightDateStr = dataValue.get("WEIGHT_DATE").toString();
		//Date weightDate = DateUtil.StringToDate(weightDateStr, "yyyy-MM-dd HH:mm:ss");
		
		//基准重心计算gravity= (力臂RC-MODIFY_POSTION)/K_VALUE(K值),力臂RC= MOMENT_BASE/WEIGHT_BASE
		
		//double gravity = (momentBase/weightBase-modifyPosition)/kValue;
		
		//查询当前称重日期之后，所有已完成的工作计划，对重心重新计算
		//String temp = "SELECT WP.ID,W.WEIGHT_ADD,W.MOMENT_ADD,WP.WORK_DATE FROM F_WEIGHT_WORKPLAN WP,B_WEIGHT_WORK W "
		//	+ " WHERE W.ID=WP.WEIGHT_WORK_ID AND WORK_STATE=1 AND ACNUM="+acId
		//	+ " AND WORK_DATE > to_date('"+weightDateStr+"','YYYY-MM-DD HH24:mi:ss') ORDER BY WORK_DATE";
		
	//	ArrayList<String> updateSqlList = new ArrayList<String>();
	//	HashVO[] wpVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, temp);
		//从基准重心开始计算
	//	double weightCur = weightBase;
	//	double momentCur = momentBase;
	//	for(int i=0;i<wpVos.length;i++){
	//		HashVO wpvo = wpVos[i];
	//		String workplanId = wpvo.getStringValue("ID");
	//		double weightAdd = new Double(wpvo.getStringValue("WEIGHT_ADD"));
	//		double momentAdd = new Double(wpvo.getStringValue("MOMENT_ADD"));
			//计算计划完成前、后重心
	//		double weightBefore = weightCur;
	//		double momentBefore = momentCur;
	//		double gravityBefore = (momentBefore/weightBefore-modifyPosition)/kValue;
			
	//		double weightAfter = weightCur + weightAdd;
	//		double momentAfter = momentCur + momentAdd;
	//		double gravityAfter = (momentAfter/weightAfter-modifyPosition)/kValue;
			
	//		String tmpSql = "update f_weight_workplan set weight_before="+weightBefore+",moment_before="+momentBefore+",weight_after="+weightAfter
	//			+",moment_after="+momentAfter+",gravity_before="+gravityBefore+",gravity_after="+gravityAfter+",gravity_base="+gravity
	//			+" where id=" + workplanId;
			
	//		updateSqlList.add(tmpSql);
			
			//当前重心已发生改变
	//		weightCur = weightAfter;
	//		momentCur = momentAfter;
	//	}
		//double gravityCur = (momentCur/weightCur - modifyPosition) / kValue;
		
		//更新当前重心信息
	//	dataValue.put("WEIGHT_CURRENT", weightCur);
	//	dataValue.put("MOMENT_CURRENT", momentCur);
	//	dataValue.put("CURRENT_GRAVITY", gravityCur);
		
	//	dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
	//	logger.debug("飞机当前重心更新完成！");
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
