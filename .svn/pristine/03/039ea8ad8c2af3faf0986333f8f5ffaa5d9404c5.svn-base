package com.apms.bs.intf.omis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

/**
 *OMIS接口数据采集--飞行日志采集 
 *@date  2014-2-21
 **/
@Deprecated
public class OmisDataExtractFlightLogService {
	private Logger logger = NovaLogger.getLogger(this.getClass());	
	private CommDMO omis = new CommDMO();
	private CommDMO apms = new CommDMO();
	
	/*飞行日志更新 zhanghailong 2012-12-28 */
	public int extractFlightLog(String CO_SEQ,String BASEORGID,String omisStartPoint) throws Exception{
		int recordCount = 0;
		String queryAcid ="";
		String Sacid="";
		String Flyupdate="";
		String flogstr="";
		String beginTime1="";
		ArrayList<String> updateSqlList = new ArrayList<String>();
		logger.info("采集OMIS库中飞行日志数据开始！");
		//取得飞机号和更新的起始日期 
		queryAcid="select a.*,b.* from b_apms_outlink a,b_aircraft b  where a.model=b.aircraftsn AND a.linksys='OMISFLY' AND b.stats='1'  order by b.aircraftsn";
		HashVO[] voacid=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
		
		for(int i=0;i<voacid.length;i++){//根据飞机号来取数据
		  // try{
			updateSqlList.clear();
			HashVO voacid1=voacid[i];
			Sacid=voacid1.getStringValue("AIRCRAFTSN");
			
			Sacid.replaceAll("-","");
			
			//判断删除本地不存在的数据
			//删除本地从开始时间之后的在omis数据库上不存在的数据 
			if (voacid1.getStringValue("update_time")==null||voacid1.getStringValue("update_time")==""){
				//如果时间没有则取当天的日期
				beginTime1=getCurrentNowSecond().substring(0,10)+" 00:00:00";
			}
			else{
				//如果有日期 则取得日期值
				beginTime1=voacid1.getStringValue("update_time").substring(0,10)+" 00:00:00";
			}
			//取得大于等于beginTime1日期之后存在的数据，然后删除本地上大于等于beginTime1日期之后不存在的数据，同时把大于等于beginTime1日期之后的数据更改为待计算状态。
			
			//删除预计的数据
			
			flogstr="delete from l_ac_flightlog  where  acnum='"+Sacid+"' and fidate>=to_date('"+beginTime1.substring(0,10)+"','yyyy-MM-dd') and MODIFYSTATUS<>1 and modifystatus<>2";
			updateSqlList.add(flogstr);
			//修改重新计算日期
			flogstr="update l_ac_flightlog set computedstatus=0  where  acnum='"+Sacid+"' and fidate>=to_date('"+beginTime1.substring(0,10)+"','yyyy-MM-dd') and MODIFYSTATUS=1";
			updateSqlList.add(flogstr);
			
			flogstr="delete from l_ac_flightlog_daily  where  acnum='"+Sacid+"' and vdfcdate>=to_date('"+beginTime1.substring(0,10)+"','yyyy-MM-dd') ";
			updateSqlList.add(flogstr);
			//修改重新计算日期
			//flogstr="update l_ac_flightlog set computedstatus=0  where  acnum='"+Sacid+"' and fidate>=to_date('"+beginTime1.substring(0,10)+"','yyyy-MM-dd')";
			//updateSqlList.add(flogstr);
			
			//flogstr="update l_ac_flightlog_daily set computedstatus=0  where  acnum='"+Sacid+"' and vdfcdate>=to_date('"+beginTime1.substring(0,10)+"','yyyy-MM-dd')";
			//updateSqlList.add(flogstr);
			
			//提交更新数据
			apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			apms.commit(ApmsConst.DS_APMS);	
			
			//运行飞行日志有的数据
			logger.info("采集OMIS库中"+Flyupdate+"实际飞行日志数据开始！");
			
			Flyupdate=extract_FlightLog(voacid1,beginTime1,BASEORGID);
		//	Flyupdate=Flylog_calculate(voacid1,omisStartPoint,BASEORGID);
			
			logger.info("采集OMIS库中"+Flyupdate+"实际飞行日志数据结束！");
			//计算飞行日志分段日志中的总计数据
			Flight_compute_log(voacid1,Flyupdate,BASEORGID,0);
			//计算飞行日志过程
			Flight_compute(voacid1,Flyupdate,BASEORGID,0);
			
		
			//计算飞行日志完成之后的预计飞行时间
			logger.info("采集OMIS库中"+Flyupdate+"计划飞行日志数据开始！");
			
			Flyupdate=extractPredictFlightLog(voacid1,Flyupdate,BASEORGID);
			
			logger.info("采集OMIS库中"+Flyupdate+"计划飞行日志数据结束！");
			Flightlog_predict_compute(voacid1,Flyupdate,BASEORGID,0);
			
			
			logger.info("采集OMIS库中"+Sacid+"飞行日志数据完成！");
		}
		logger.info("采集OMIS库中飞行日志数据完成！");

		return  recordCount;
	}

	/**
	 * 单独某架飞机从某个日期开始重新计算
	 * @param acnum
	 * @param omisStartPoint
	 * @param BASEORGID
	 * @return
	 */
	public String extractSingleACFlightLogStart(String acnum,String omisStartPoint,String BASEORGID){
		String flogstr="";
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try{	
			//删除开始日期之后的数据
			
			flogstr="delete from l_ac_flightlog  where  acnum='"+acnum+"' and fidate>=to_date('"+omisStartPoint.substring(0,10)+"','yyyy-MM-dd')";
			updateSqlList.add(flogstr);
			
			flogstr="delete from l_ac_flightlog_daily  where  acnum='"+acnum+"' and vdfcdate>=to_date('"+omisStartPoint.substring(0,10)+"','yyyy-MM-dd')";
			updateSqlList.add(flogstr);
			//提交更新数据
			apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			apms.commit(ApmsConst.DS_APMS);	
			//单架飞机抽取数据
			logger.info("采集OMIS库中单架飞机"+acnum+"正常飞行日志数据开始！");
			extractSingleACFlightLog(acnum,omisStartPoint,BASEORGID);
			//计算单架飞行日志分段日志中的总计数据
			logger.info("采集OMIS库中单架飞机"+acnum+"计算机飞行日志数据开始！");
			Flight_Simplecompute_log(acnum,omisStartPoint,BASEORGID,0);
			//计算飞单架行日志过程
			logger.info("采集OMIS库中单架飞机"+acnum+"计算飞行daily日志数据开始！");
			Flight_Simplecompute(acnum,omisStartPoint,BASEORGID,0);
			
			logger.info("采集OMIS库中单架飞机"+acnum+"计划飞行日志数据开始！");
			
			extract_SimpleFlightLog_predict(acnum,omisStartPoint,BASEORGID);
			
			logger.info("采集OMIS库中单架飞机"+acnum+"计划飞行日志数据结束！");
			Flightlog_Simplepredict_compute(acnum,omisStartPoint,BASEORGID,0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//释放数据库连接，非常重要，数据库连接是有限的！！！！！！！！！
			//每次事务操作完成后，均应该释放连接
			omis.releaseContext(ApmsConst.DS_OMIS);
			apms.releaseContext(ApmsConst.DS_DEFAULT);
			
		}
				return  "";
	}
	/*单架飞机数据*/
	public String extractSingleACFlightLog(String acnum,String omisStartPoint,String BASEORGID){
		String flogstr = "";
		String queryAcid = "";
		String Sacid = "";
		String omisID;
		String omisACNo;
		String omisFlightDate = null;
		String omisFlightNO;
		String omisDeploc;
		String omisArrLoc;
		String omisStd;
		String omisSta;
		String omisOut;
		String omisOff;
		String omisOn;
		String omisIn;
		String Flbid = "1";
		String update = "";
		String global_pk = "";
		int omisFc;
		int AirMin;
		int BlockMin;// '申明读取OMIS数据函数
		
		long basicAirMin=0;//初始
		long basicBlockMin=0;//初始
		
		long basicomisFc=0;//初始
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		long curaddtime=0;
		long curaddcycle=0;
		String operate_time="";//最后更新时间
		HashVO voflylog2=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try{

			Sacid=acnum;//voacid.getStringValue("AIRCRAFTSN");
			
			Sacid.replaceAll("-","");
			//取得飞行日志的基础值
			queryAcid="select * from (select * from l_ac_flightlog_Daily t where COMPUTEDSTATUS='1' and t.acnum='"+Sacid+"'  order by vdfcdate desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				
						update=omisStartPoint;//voacid.getStringValue("update_time");
						
				 //取得数据
				 basicAirMin=voacid_log1.getLognValue("VDFCAIR");
				 basicBlockMin=voacid_log1.getLognValue("VDFCBLOCK");
				 basicomisFc=voacid_log1.getLognValue("FIACTLG_SUM");
			}
			else{
				//update=voacid.getStringValue("update_time");
				update=omisStartPoint;
				//不存在，赋值0
				 basicAirMin=0;
				 basicBlockMin=0;
				 basicomisFc=0;
			}
			if (update==null){
				update=omisStartPoint;
			}
			//飞行日志基础数据end
			
			
		flogstr="SELECT CONCAT(F.ID_NO,F.global_pk) AS SYS_ID,F.global_pk AS global_pk,D.AC_ID AS ACNUM,D.FLT_DATE AS FLIGHTDATE,concat(F.iata_c,F.flt_id) as Flightno," +
          "F.dep_apt AS DEPLOC,F.arr_apt AS ARRLOC,F.std AS F_STD,F.sta AS F_STA,F.OUT_TIME AS OUTGATE,F.OFF_TIME ,F.ON_TIME," +          
		  "f.IN_TIME , f.OFF_TIME_LOG, f.ON_TIME_LOG, f.fc, f.con_fc , f.operate_time" +		
          " FROM airchina.ac_fly_log F,airchina.AC_FH_FC D " +
          " Where f.ID_NO = d.ID_NO AND D.ac_id='"+Sacid+"' and  d.flt_date>=to_date(substr('"+update+"',0,10),'yyyy-MM-dd')  order by D.FLT_DATE, F.std" ;
         
				HashVO[] voflylog=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_OMIS, flogstr);
				for(int j=0;j<voflylog.length;j++){
					HashVO voflylog1=voflylog[j];
				
					//不是第一条数据的时候，取前一条数据
					if (j>0){
						voflylog2=voflylog[j-1];	
					}
					else{//第一条数据的时候取得相同的数据
						voflylog2=voflylog[j];	
					}
					
					omisID = voflylog1.getStringValue("sys_id");
					omisACNo = voflylog1.getStringValue("acnum");
					omisFlightDate =voflylog1.getStringValue("flightdate");
					omisFlightNO =voflylog1.getStringValue("flightno");
					omisDeploc =voflylog1.getStringValue("deploc");
					omisArrLoc = voflylog1.getStringValue("arrloc");
					omisStd = voflylog1.getStringValue("f_std");
					omisSta = voflylog1.getStringValue("f_sta");
					operate_time=voflylog1.getStringValue("operate_time");
					global_pk=voflylog1.getStringValue("global_pk");
					/*//判断是否是同一天的数据
					if (j>0){
						dif=Math.abs(DateUtil.dateDiff("d", voflylog1.getDateValue("flightdate"), voflylog2.getDateValue("flightdate")));
					}
					else{
						dif=Math.abs(DateUtil.dateDiff("d", voflylog1.getDateValue("flightdate"), Lastdate));
					}
						difa=Math.abs(DateUtil.dateDiff("d", voflylog1.getDateValue("flightdate"), voflylog3.getDateValue("flightdate")));
					logger.info("difa="+difa);
					*/
					//logger.info("omisID="+omisID);
					if (operate_time!=null){//当operate_time 不为null的时候,取on_time_log,off_time_log
						if ( voflylog1.getStringValue("off_time")==null|| voflylog1.getStringValue("off_time").compareTo("1900-01-01")==0){ //  '没有离港时间时
							omisOff = omisStd;
							}else{
							omisOff = voflylog1.getStringValue("off_time");
							}
				
							if ( voflylog1.getStringValue("on_time")==null|| voflylog1.getStringValue("on_time").compareTo("1900-01-01")==0){ // '没有到达时间时
								omisOn = omisSta;
							}else{
								omisOn = voflylog1.getStringValue("on_time");
							}
							if ( voflylog1.getStringValue("on_time_log")==null|| voflylog1.getStringValue("on_time_log").compareTo("1900-01-01")==0||voflylog1.getStringValue("off_time_log")==null|| voflylog1.getStringValue("off_time_log").compareTo("1900-01-01")==0){ // '实际时间
							}else{
								omisOff = voflylog1.getStringValue("off_time_log");
								omisOn = voflylog1.getStringValue("on_time_log");
							}
				
							if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){//  '没有离港时间时
							omisOut =DateUtil.dateAdd("n", omisOff ,-5);// '*******************时间处理
							}else{
							omisOut = voflylog1.getStringValue("outgate");
							}
				
							if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){// '没有离港时间时
							omisIn =DateUtil.dateAdd("n", omisOn, 5);// '*******************时间处理
							}else{
							omisIn = voflylog1.getStringValue("in_time");
							
							}
					}
					else{
						if ( voflylog1.getStringValue("off_time")==null|| voflylog1.getStringValue("off_time").compareTo("1900-01-01")==0){ //  '没有离港时间时
						omisOff = omisStd;
						}else{
						omisOff = voflylog1.getStringValue("off_time");
						}
			
						if ( voflylog1.getStringValue("on_time")==null|| voflylog1.getStringValue("on_time").compareTo("1900-01-01")==0){ // '没有到达时间时
							omisOn = omisSta;
						}else{
							omisOn = voflylog1.getStringValue("on_time");
						}
			
			
						if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){//  '没有离港时间时
						omisOut =DateUtil.dateAdd("n", omisOff ,-5);// '*******************时间处理
						}else{
						omisOut = voflylog1.getStringValue("outgate");
						}
			
						if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){// '没有离港时间时
						omisIn =DateUtil.dateAdd("n", omisOn, 5);// '*******************时间处理
						}else{
						omisIn = voflylog1.getStringValue("in_time");
						
						}
					}
					logger.info("计算UTC时间开始");
					//logger.info("omisOut="+omisOut);
					//logger.info("omisOn="+omisOn);
					//logger.info("omisOff="+omisOff);
					//logger.info("omisIn="+omisIn);
					//'convert utc time
					omisOut =DateUtil.dateAdd("h", omisOut,-8);
					omisOn =  DateUtil.dateAdd("h",omisOn, -8);
					omisOff = DateUtil.dateAdd("h",omisOff, -8);
					omisIn =  DateUtil.dateAdd("h", omisIn, -8);
					
					//'------------
					AirMin =Math.abs(DateUtil.dateDiff("minute", sdf.parse(omisOff), sdf.parse(omisOn)));
					BlockMin =Math.abs(DateUtil.dateDiff("minute",sdf.parse(omisOut),sdf.parse(omisIn)));
					logger.info("计算UTC时间结束");
					if (voflylog1.getStringValue("fc")!=null){
					omisFc =voflylog1.getIntegerValue("fc");
					}else{
					omisFc = 1;
					
					}
					//同一架飞机同一天
					if (omisFlightDate.equals(voflylog2.getStringValue("flightdate").trim())){
						//当天累计
						 basicAirMinC+=AirMin;
						 basicBlockMinC+=BlockMin;
						 basicomisFcC+=omisFc;
					}
					else{//同一架飞机不同天
						//当天累计
						 basicAirMinC=AirMin;
						 basicBlockMinC=BlockMin;
						 basicomisFcC=omisFc;
					}

					
					//总数累计
					 basicAirMin+=AirMin;
					 basicBlockMin+=BlockMin;
					 basicomisFc+=omisFc;
					 //本次循环的增加的总数
					 curaddtime+=AirMin;
					 curaddcycle+=omisFc;
					 //判断飞机是否存在
					
					flogstr="select * from l_ac_flightlog where fiflbsn='"+omisID+"'";
					HashVO[] voflylogapms=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, flogstr);
					if (voflylogapms.length>0){//有数据
						//取得老数据
						//HashVO voflylogapms1=voflylogapms[0];
						//减去老数据
						 //basicAirMin-=voflylogapms1.getIntegerValue("FIAIR");
						 //basicBlockMin-=voflylogapms1.getIntegerValue("FIBLOCK");
						 //basicomisFc-=voflylogapms1.getIntegerValue("FIACTLG");
						
						if(omisDeploc!=""&&omisArrLoc!=""){//当数据为正常是才被修改MODIFYSTATUS=0
						flogstr="update l_ac_flightlog set COMPUTEDSTATUS=0,FIDATE=to_date(substr('"+omisFlightDate+"',1,10),'yyyy-mm-dd'),FIFLTNO='"+omisFlightNO+"',FIDEPLOC='"+omisDeploc+"',FIARVLOC='"+omisArrLoc+"',FIACTDEP=to_date('"+omisOut+"','yyyy-mm-dd hh24:mi:ss')," +
								"FIACTTAK=to_date('"+omisOff+"','yyyy-mm-dd hh24:mi:ss'), FIACTLAD=to_date('"+omisOn+"','yyyy-mm-dd hh24:mi:ss'),FIACTARV=to_date('"+omisIn+"','yyyy-mm-dd hh24:mi:ss'),FIACTLG="+omisFc+", FISTA=2,FIAIR="+AirMin+",FIBLOCK="+BlockMin+",FLAIR_SUM="+basicAirMin+",FIBLOCK_SUM="+basicBlockMin+",FIACTLG_SUM="+basicomisFc+",updatetime=sysdate where fiflbsn='"+omisID+"' and MODIFYSTATUS=0";
						updateSqlList.add(flogstr);
						//当他是修正数据的时候，只修改计算状态
						flogstr="update l_ac_flightlog set COMPUTEDSTATUS=0,updatetime=sysdate where fiflbsn='"+omisID+"' and MODIFYSTATUS<>0";
						updateSqlList.add(flogstr);
						}else{
							flogstr="delete from l_ac_flightlog WHERE fiflbsn='"+omisID+"'";
							updateSqlList.add(flogstr);
						}
						
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						//apms.commit(ApmsConst.DS_APMS);
						
						//如果有存在，则把整天的数据都设置为重新计算的状态
						
						flogstr="update l_ac_flightlog set COMPUTEDSTATUS=0,updatetime=sysdate where FIDATE>=to_date(substr('"+omisFlightDate+"',1,10),'yyyy-mm-dd') and acnum='"+Sacid+"' and COMPUTEDSTATUS<>2";
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						//apms.commit(ApmsConst.DS_APMS);
						updateSqlList.add(flogstr);
						flogstr="update l_ac_flightlog_daily set COMPUTEDSTATUS=0,updatetime=sysdate where vdfcDATE>=to_date(substr('"+omisFlightDate+"',0,10),'yyyy-mm-dd') and acnum='"+Sacid+"' and COMPUTEDSTATUS<>2";
						updateSqlList.add(flogstr);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						//apms.commit(ApmsConst.DS_APMS);
						
						//删除当日有正常数据的预计数据
						flogstr="delete from l_ac_flightlog where FIDATE=to_date(substr('"+omisFlightDate+"',0,10),'yyyy-mm-dd') and acnum='"+Sacid+"' and COMPUTEDSTATUS=2";
						updateSqlList.add(flogstr);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						//apms.commit(ApmsConst.DS_APMS);
						////////////////////////////////////////////////////////////////////////////
					}
					else{
						//没有
						if(!omisDeploc.equals("")&&!omisArrLoc.equals("")){
						flogstr="INSERT into l_ac_flightlog(ID,MODIFYSTATUS,COMPUTEDSTATUS,FIFLBSN,FIFLBID,global_pk,acnum,BASEORGID,FIDATE,FIFLTNO,FIDEPLOC,FIARVLOC,FIACTDEP,FIACTTAK,FIACTLAD,FIACTARV,FIACTLG,FISTA,FIAIR,FIBLOCK,FLAIR_SUM,FIBLOCK_SUM,FIACTLG_SUM,UPDATETIME,USERID)" +
								" VALUES(S_l_ac_flightlog.nextval,0,0,'"+omisID+"','"+Flbid+"','"+global_pk+"','"+omisACNo+"',"+BASEORGID+",to_date(substr('"+omisFlightDate.trim()+"',1,10),'yyyy-mm-dd'),'"+omisFlightNO+"','"+omisDeploc+"','"+omisArrLoc+"',to_date('"+omisOut+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisOff+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisOn+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisIn+"','yyyy-mm-dd hh24:mi:ss'),'"+omisFc+"',2,"+AirMin+","+BlockMin+","+basicAirMin+","+basicBlockMin+","+basicomisFc+",sysdate,0) ";
						}
						else{
						flogstr="delete from l_ac_flightlog WHERE fiflbsn='"+omisID+"'";
						}
						updateSqlList.add(flogstr);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						//apms.commit(ApmsConst.DS_APMS);
					}
					
					
					//写入飞行日志结束
					////////////////////////////////////////////////////////////////////////////
				
				}

				///////////////////////////////////////////////////////////////////////////////
				if(operate_time==null||operate_time==""){
					operate_time=getCurrentNowSecond();
				}
				
				flogstr="update b_apms_outlink set update_time=to_date(substr('"+operate_time+"',0,19),'yyyy-mm-dd hh24:mi:ss') where linksys='OMISFLY' AND MODEL='"+Sacid+"'";
				updateSqlList.add(flogstr);
				apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				apms.commit(ApmsConst.DS_APMS);
				
				//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);

				//apms.commit(ApmsConst.DS_APMS);
				
		//   }catch(Exception e){
		//	   logger.info("采集OMIS库中"+Sacid+"飞行日志数据失败！");
		//	   continue ;
		//   }
		}
		catch(Exception e){
		e.printStackTrace();	
		}
		
		return operate_time;
	}
	
	//单架飞机分段日志，总计统计计算
	public String Flight_Simplecompute_log(String acnum,String startdate,String BASEORGID,int status ){
		long sumAirMin=0;//总计
		long sumBlockMin=0;//总计
		
		long sumomisFc=0;//总计
		
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		String Sacid ="";
		String queryAcid="";
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try{
			//Sacid=voacid.getStringValue("AIRCRAFTSN");
			Sacid=acnum;
			
			logger.info("计算"+Sacid+"的飞行日志分段日志总计开始");
			Sacid.replaceAll("-","");
			queryAcid="select * from (select * from l_ac_flightlog t where COMPUTEDSTATUS='1' and t.acnum='"+Sacid+"'  order by fidate desc,fiactdep desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			//取得分段日志最近一条的正常数据
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				 sumAirMin=voacid_log1.getLognValue("flair_sum");//总计
				 sumBlockMin=voacid_log1.getLognValue("fiblock_sum");//总计									
				 sumomisFc=voacid_log1.getLognValue("fiactlg_sum");//总计
			}
			queryAcid="select * from l_ac_flightlog t where COMPUTEDSTATUS='0' and t.acnum='"+Sacid+"'  order by fidate,fiactdep";
			HashVO[] vofly_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			for(int i=0;i<vofly_log.length;i++){
				logger.info("计算"+Sacid+"的第"+i+"条飞行日志分段日志");
				 HashVO vofly_log1=vofly_log[i];
				 basicAirMinC=vofly_log1.getLognValue("fiair");
				 basicBlockMinC=vofly_log1.getLognValue("fiblock");
				 basicomisFcC=vofly_log1.getLognValue("fiactlg");
				 sumAirMin+=basicAirMinC;//总计
				 sumBlockMin+=basicBlockMinC;//总计									
				 sumomisFc+=basicomisFcC;//总计
				 queryAcid="update l_ac_flightlog set flair_sum="+sumAirMin+",fiblock_sum="+sumBlockMin+",fiactlg_sum="+sumomisFc+" where id="+vofly_log1.getLognValue("id");
				 updateSqlList.add(queryAcid);
			}
			apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			apms.commit(ApmsConst.DS_APMS);	
			logger.info("计算"+Sacid+"的飞行日志分段日志总计结束");
		}
		catch(Exception e){
			e.printStackTrace();		
			
		}
		return "0";
	}
//	//单架飞机飞行日志计算；在导入飞行日志daily之后进行计算。
	public String Flight_Simplecompute(String acnum,String startdate,String BASEORGID,int status ){
		String queryAcid="";
		String Sacid="";
		long basicAirMin=0;//初始
		long basicBlockMin=0;//初始
		
		long basicomisFc=0;//初始
		
		long sumAirMin=0;//总计
		long sumBlockMin=0;//总计
		
		long sumomisFc=0;//总计
		
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		Date LastDate=null;//最后的日期
		String LastDateS="";
		int d=0;
		
		int lastid=0;
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try{
			//Sacid=voacid.getStringValue("AIRCRAFTSN");
			Sacid=acnum;
			Sacid.replaceAll("-","");
			queryAcid="select * from (select * from l_ac_flightlog_Daily t where COMPUTEDSTATUS='1' and t.acnum='"+Sacid+"'  order by vdfcdate desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			//取得飞行日志计算开始日期
			logger.info("取得飞行日志计算开始日期");
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				
				 basicAirMin=voacid_log1.getLongValue("vdfcair");
				 basicBlockMin=voacid_log1.getLongValue("vdfcblock");
				 basicomisFc=voacid_log1.getLongValue("fiactlg_sum");
				 LastDate=voacid_log1.getDateValue("vdfcdate");
				 LastDateS=voacid_log1.getStringValue("vdfcdate");
				 lastid=voacid_log1.getIntegerValue("id");
				 sumAirMin=basicAirMin;//总计
				 sumBlockMin=basicBlockMin;//总计									
				 sumomisFc=basicomisFc;//总计
				 queryAcid="select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=0 and acnum='"+Sacid+"' group by acnum,fidate,baseorgid order by acnum,fidate";
					HashVO[] flylogvo=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
					for(int i=0;i<flylogvo.length;i++){///计算日期是否有缺失的日期
						HashVO flylogvo1= flylogvo[i];
						HashVO flylogvo2=null;
						 basicAirMinC=flylogvo1.getLognValue("fiair");
						 basicBlockMinC=flylogvo1.getLognValue("fiblock");
						 basicomisFcC=flylogvo1.getLognValue("fiactlg");
						 sumAirMin+=basicAirMinC;//总计
						 sumBlockMin+=basicBlockMinC;//总计									
						 sumomisFc+=basicomisFcC;//总计
						 if(i==0){//与前一天的数据比较
							d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), LastDate));
							if(d>1){
								for(int kk1=0;kk1<d;kk1++){//循环写入飞行空日期的飞行日志
									if (kk1<d){
										 basicAirMinC=0;
										 basicBlockMinC=0;
										 basicomisFcC=0;
									}
									else{
										 basicAirMinC=flylogvo1.getLognValue("fiair");
										 basicBlockMinC=flylogvo1.getLognValue("fiblock");
										 basicomisFcC=flylogvo1.getLognValue("fiactlg");
									}
										queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+LastDateS+"',1,10),'yyyy-mm-dd')+"+kk1+" and acnum= '"+Sacid+"'";
										HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
										
										if(voflylogsum1.length>0){
											
											queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+LastDateS+"',1,10),'yyyy-mm-dd')+"+kk1+" and acnum= '"+Sacid+"'";
											logger.info("中间有空间S1，又有记录！");
										}
										else{
											logger.info("中间有空间S1，没有记录！");
											queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME)select S_l_ac_flightlog_Daily.nextval,baseorgid,vdfcdate+"+kk1+",acnum,"+sumAirMin+","+basicAirMinC+","+sumBlockMin+","+basicBlockMinC+","+basicomisFcC+","+sumomisFc+",COMPUTEDSTATUS,sysdate from l_ac_flightlog_Daily where id="+lastid;
										}
										updateSqlList.add(queryAcid);
										//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
										
										//apms.commit(ApmsConst.DS_APMS);
									
								}
							}
						}//第一条数据与前面的数据比较end
						
						if(i<flylogvo.length-1){//取得后一条的数据
								flylogvo2=flylogvo[i+1];
						}
						else{
								flylogvo2=flylogvo[i];
						}
						//判断当前记录跟后一记录的日期差值
						d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
						if(d>1){//有空缺的日期存在
							for(int kk=0;kk<d;kk++){//循环写入飞行空日期的飞行日志
								if(kk>0){//当日期后。
									 basicAirMinC=0;
									 basicBlockMinC=0;
									 basicomisFc=0;
								}
								else{
									 basicAirMinC=flylogvo1.getLognValue("fiair");
									 basicBlockMinC=flylogvo1.getLognValue("fiblock");
									 basicomisFcC=flylogvo1.getLognValue("fiactlg");
								}
									queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
									HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
									
									if(voflylogsum1.length>0){
										
										queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
										logger.info("中间有空间S2，又有记录！");
									}
									else{
										logger.info("中间有空间S2，没有记录！");
										queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd')+"+kk+",'"+Sacid+"',"+sumAirMin+","+(basicAirMinC)+","+sumBlockMin+","+(basicBlockMinC)+","+basicomisFcC+","+sumomisFc+",1,sysdate)";
									}
									updateSqlList.add(queryAcid);
									//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
									
									//apms.commit(ApmsConst.DS_APMS);
								
							}
						}
						else{
							
							queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
							HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
							
							if(voflylogsum1.length>0){
								
								//queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+basicAirMinC+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
								queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
								logger.info("有记录存在S3，则修改！");
							}
							else{
								logger.info("没有记录S3，则添加！");
								//queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(basicAirMinC)+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(basicBlockMinC)+","+basicomisFcC+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",1,sysdate)";
								queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+sumAirMin+","+basicAirMinC+","+sumBlockMin+","+basicBlockMinC+","+basicomisFcC+","+sumomisFc+",1,sysdate)";
							}
							
							updateSqlList.add(queryAcid);
							//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
							
							//apms.commit(ApmsConst.DS_APMS);
							
						}
						queryAcid="update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate=to_date(substr('"+flylogvo1.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') and acnum='"+flylogvo1.getStringValue("acnum")+"'";
						updateSqlList.add(queryAcid);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
						//apms.commit(ApmsConst.DS_APMS);
					}
			
			}
			else{//数据不存在
				queryAcid="select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=0 and acnum='"+Sacid+"' group by acnum,fidate,baseorgid order by acnum,fidate";
				HashVO[] flylogvo=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
				for(int i=0;i<flylogvo.length;i++){///计算日期是否有缺失的日期
					HashVO flylogvo1= flylogvo[i];
					HashVO flylogvo2=null;
					if(i<flylogvo.length-1){//取得后一条的数据
						flylogvo2=flylogvo[i+1];
					}
					else{
						flylogvo2=flylogvo[i];
					}
					//判断当前记录跟后一记录的日期差值
					d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
					if(d>1){//有空缺的日期存在
						for(int kk=0;kk<d;kk++){//循环写入飞行空日期的飞行日志
							if(kk>0){//当日期后。
								 //basicAirMin-=basicAirMinC;
								 //basicBlockMin-=basicBlockMinC;
								 //basicomisFc-=basicomisFcC;
								 basicAirMinC=0;
								 basicBlockMinC=0;
								 basicomisFc=0;
							}
							else{
								 basicAirMinC=flylogvo1.getLognValue("fiair");
								 basicBlockMinC=flylogvo1.getLognValue("fiblock");
								 basicomisFcC=flylogvo1.getLognValue("fiactlg");
							}
								queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
								HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
								
								if(voflylogsum1.length>0){
									
									queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+basicAirMinC+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
									logger.info("中间有空间S4，又有记录！");
								}
								else{
									logger.info("中间有空间S4，没有记录！");
									queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd')+"+kk+",'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(basicAirMinC)+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(basicBlockMinC)+","+basicomisFcC+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",1,sysdate)";
								}
								updateSqlList.add(queryAcid);
								//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
								
								//apms.commit(ApmsConst.DS_APMS);
							
						}
					}
					else{
						queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
						HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
						
						if(voflylogsum1.length>0){
							
							queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+flylogvo1.getLognValue("fiair")+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+flylogvo1.getLognValue("fiblock")+",FIACTLG="+flylogvo1.getLognValue("fiactlg")+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
							logger.info("有记录存在S5，则修改！");
						}
						else{
							logger.info("没有记录S5，则添加！");
							queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(flylogvo1.getLognValue("fiair"))+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(flylogvo1.getLognValue("fiblock"))+","+flylogvo1.getLognValue("FIACTLG")+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",1,sysdate)";
						}
						updateSqlList.add(queryAcid);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
						
						//apms.commit(ApmsConst.DS_APMS);
						
					}
					queryAcid="update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate=to_date(substr('"+flylogvo1.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') and acnum='"+flylogvo1.getStringValue("acnum")+"'";
					updateSqlList.add(queryAcid);
					//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
					//apms.commit(ApmsConst.DS_APMS);
				}
				
			}
			apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			apms.commit(ApmsConst.DS_APMS);	
			logger.info("计算单架飞机"+Sacid+"的飞行日志结束");
		}
		catch(Exception e){
			e.printStackTrace();		
			
		}
		return "0";
	}
	
	/**
	 * 根据航班计算更新未来预计的飞行日志数据
	 * @param voacid
	 * @param omisStartPoint
	 * @param BASEORGID
	 * @return
	 */
	public String extractPredictFlightLog(HashVO voacid,String omisStartPoint,String BASEORGID){
		String flogstr="";
		String queryAcid ="";
		String Sacid="";
		String omisID ;
		String omisACNo; 
		String omisFlightDate = null;
		String    omisFlightNO;
		String omisDeploc;
		String omisArrLoc;
		String    omisStd;
		String omisSta;
		String omisOut;
		String omisOff;
		String    omisOn;
		String omisIn;
		String Flbid ="1";
		String update="";
		String global_pk="";
		int omisFc;
		int AirMin;
		int BlockMin;// '申明读取OMIS数据函数
		
		long basicAirMin=0;//初始
		long basicBlockMin=0;//初始
		
		long basicomisFc=0;//初始
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		long curaddtime=0;//当前更新增加的时间
		long curaddcycle=0;//当前更新增加的循环
		HashVO voflylog2=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try{

			Sacid=voacid.getStringValue("AIRCRAFTSN");
			
			Sacid.replaceAll("-","");
			//取得飞行日志的基础值
			//queryAcid="select * from (select * from l_ac_flightlog_Daily t where STATUS='1' and t.acnum='"+Sacid+"'   order by vdfcdate desc) where rownum<2";
			queryAcid="select * from (select *  from l_ac_flightlog t where COMPUTEDSTATUS=1 and acnum='"+Sacid+"' order by fidate desc, fiactarv desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				
				//取得数据
				 basicAirMin=voacid_log1.getLognValue("flair_sum");
				 basicBlockMin=voacid_log1.getLognValue("fiblock_sum");
				 basicomisFc=voacid_log1.getLognValue("FIACTLG_SUM");
				 update=voacid_log1.getStringValue("fidate");
			}
			else{
				
				update=omisStartPoint;
				//不存在，赋值0
				 basicAirMin=0;
				 basicBlockMin=0;
				 basicomisFc=0;
			}
			//飞行日志基础数据end
			flogstr="select F.global_pk AS SYS_ID,F.global_pk AS global_pk,f.AC_ID AS ACNUM,f.FLT_DATE AS FLIGHTDATE,concat(F.iata_c,F.flt_id) as Flightno,F.dep_apt AS DEPLOC,F.arr_apt AS ARRLOC,F.std AS F_STD,F.sta AS F_STA,F.OUT_TIME AS OUTGATE,F.OFF_TIME ,F.ON_TIME," +
				"f.IN_TIME ,case  when dep_apt=arr_apt then 50 else 1 end   fc from airchina.flight_information f where flt_date>to_date(substr('"+update.trim()+"',0,10),'yyyy-mm-dd') and ac_id='"+Sacid+"' order by flt_date,f.std";
				HashVO[] voflylog=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_OMIS, flogstr);
				for(int j=0;j<voflylog.length;j++){
					//if(datestate.equals("2")){//如果是初始化数据，则不执行下面的
					//	datestate="1";
						//continue;
					//}
					HashVO voflylog1=voflylog[j];
					//不是第一条数据的时候，取前一条数据
					if (j>0){
						voflylog2=voflylog[j-1];	
					}
					else{//第一条数据的时候取得相同的数据
						voflylog2=voflylog[j];	
					}
					
					omisID = voflylog1.getStringValue("sys_id");
					omisACNo = voflylog1.getStringValue("acnum");
					omisFlightDate =voflylog1.getStringValue("flightdate").trim();
					omisFlightNO =voflylog1.getStringValue("flightno");
					omisDeploc =voflylog1.getStringValue("deploc");
					omisArrLoc = voflylog1.getStringValue("arrloc");
					omisStd = voflylog1.getStringValue("f_std");
					omisSta = voflylog1.getStringValue("f_sta");
					//operate_time=voflylog1.getStringValue("operate_time");
					global_pk=voflylog1.getStringValue("global_pk");
					
					//int dif=Math.abs(DateUtil.dateDiff("d", voflylog1.getDateValue("flightdate"), voflylog2.getDateValue("flightdate")));
					//logger.info("omisFlightDate="+omisFlightDate);
					
					//logger.info("omisID="+omisID);
					if ( voflylog1.getStringValue("off_time")==null|| voflylog1.getStringValue("off_time").compareTo("1900-01-01")==0){ //  '没有离港时间时
					omisOff = omisStd;
					}else{
					omisOff = voflylog1.getStringValue("off_time");
					}
		
					if ( voflylog1.getStringValue("on_time")==null|| voflylog1.getStringValue("on_time").compareTo("1900-01-01")==0){ // '没有到达时间时
						omisOn = omisSta;
					}else{
						omisOn = voflylog1.getStringValue("on_time");
					}
		
		
					if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){//  '没有离港时间时
					omisOut =DateUtil.dateAdd("n", omisOff ,-5);// '*******************时间处理
					}else{
					omisOut = voflylog1.getStringValue("outgate");
					}
		
					if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){// '没有离港时间时
					omisIn =DateUtil.dateAdd("n", omisOn, 5);// '*******************时间处理
					}else{
					omisIn = voflylog1.getStringValue("in_time");
					
					}
					logger.info("计算UTC时间开始");
					//logger.info("omisOut="+omisOut);
					//logger.info("omisOn="+omisOn);
					//logger.info("omisOff="+omisOff);
					//logger.info("omisIn="+omisIn);
					//'convert utc time
					omisOut =DateUtil.dateAdd("h", omisOut,-8);
					omisOn =  DateUtil.dateAdd("h",omisOn, -8);
					omisOff = DateUtil.dateAdd("h",omisOff, -8);
					omisIn =  DateUtil.dateAdd("h", omisIn, -8);
					
					//'------------
					AirMin =Math.abs(DateUtil.dateDiff("minute", sdf.parse(omisOff), sdf.parse(omisOn)));
					BlockMin =Math.abs(DateUtil.dateDiff("minute",sdf.parse( omisOut),sdf.parse( omisIn)));
					logger.info("计算UTC时间结束");
					if (voflylog1.getStringValue("fc")!=null){
					omisFc =voflylog1.getIntegerValue("fc");
					}else{
					omisFc = 1;
					
					}
					//同一架飞机同一天
					if (omisFlightDate.equals(voflylog2.getStringValue("flightdate").trim())){
						//当天累计
						 basicAirMinC+=AirMin;
						 basicBlockMinC+=BlockMin;
						 basicomisFcC+=omisFc;
					}
					else{//同一架飞机不同天
						//当天累计
						 basicAirMinC=AirMin;
						 basicBlockMinC=BlockMin;
						 basicomisFcC=omisFc;
					}

					
					//总数累计
					 basicAirMin+=AirMin;
					 basicBlockMin+=BlockMin;
					 basicomisFc+=omisFc;
					 curaddtime+=AirMin;
					 curaddcycle+=omisFc;
					
					//修改存在的数据
					//写入飞行日志开始 
					flogstr="select * from l_ac_flightlog where global_pk='"+global_pk+"'";
					HashVO[] voflylogapms=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, flogstr);
					if (voflylogapms.length>0){//有数据
						//取得老数据
						//HashVO voflylogapms1=voflylogapms[0];
						//减去老数据
						// basicAirMin-=voflylogapms1.getIntegerValue("FIAIR");
						// basicBlockMin-=voflylogapms1.getIntegerValue("FIBLOCK");
						// basicomisFc-=voflylogapms1.getIntegerValue("FIACTLG");
						
						if(omisDeploc!=""&&omisArrLoc!=""){
						flogstr="update l_ac_flightlog set FIFLBSN='"+omisID+"',COMPUTEDSTATUS=2, FIDATE=to_date(substr('"+omisFlightDate+"',1,10),'yyyy-mm-dd'),FIFLTNO='"+omisFlightNO+"',FIDEPLOC='"+omisDeploc+"',FIARVLOC='"+omisArrLoc+"',FIACTDEP=to_date('"+omisOut+"','yyyy-mm-dd hh24:mi:ss')," +
								"FIACTTAK=to_date('"+omisOff+"','yyyy-mm-dd hh24:mi:ss'), FIACTLAD=to_date('"+omisOn+"','yyyy-mm-dd hh24:mi:ss'),FIACTARV=to_date('"+omisIn+"','yyyy-mm-dd hh24:mi:ss'),FIACTLG="+omisFc+", FISTA=2,FIAIR="+AirMin+",FIBLOCK="+BlockMin+",FLAIR_SUM="+basicAirMin+",FIBLOCK_SUM="+basicBlockMin+",FIACTLG_SUM="+basicomisFc+",updatetime=sysdate where global_pk='"+global_pk+"'";
						}else{
							flogstr="delete from l_ac_flightlog WHERE global_pk='"+global_pk+"'";
						}
						updateSqlList.add(flogstr);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						//apms.commit(ApmsConst.DS_APMS);
						
					}
					else{
						//没有
						if(!omisDeploc.equals("")&&!omisArrLoc.equals("")){
						flogstr="INSERT into l_ac_flightlog(ID,MODIFYSTATUS,COMPUTEDSTATUS,FIFLBSN,FIFLBID,global_pk,acnum,BASEORGID,FIDATE,FIFLTNO,FIDEPLOC,FIARVLOC,FIACTDEP,FIACTTAK,FIACTLAD,FIACTARV,FIACTLG,FISTA,FIAIR,FIBLOCK,FLAIR_SUM,FIBLOCK_SUM,FIACTLG_SUM,UPDATETIME,USERID)" +
								" VALUES(S_l_ac_flightlog.nextval,0,2,'"+omisID+"','"+Flbid+"','"+global_pk+"','"+omisACNo+"',"+BASEORGID+",to_date(substr('"+omisFlightDate.trim()+"',1,10),'yyyy-mm-dd'),'"+omisFlightNO+"','"+omisDeploc+"','"+omisArrLoc+"',to_date('"+omisOut+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisOff+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisOn+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisIn+"','yyyy-mm-dd hh24:mi:ss'),'"+omisFc+"',2,"+AirMin+","+BlockMin+","+basicAirMin+","+basicBlockMin+","+basicomisFc+",sysdate,0) ";
						}
						else{
						flogstr="delete from l_ac_flightlog WHERE global_pk='"+global_pk+"'";
						}
						updateSqlList.add(flogstr);
					}
					
					//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
					
					//apms.commit(ApmsConst.DS_APMS);
					//写入飞行日志结束
					////////////////////////////////////////////////////////////////////////////
					//写入发动机日志开始预计数据
					/*
					flogstr="select * from b_engine_info t,b_aircraft t2 where t.aircraftid=t2.id and t2.aircraftsn='"+Sacid+"' order by engwing_loc";
					HashVO[] voengacnum=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, flogstr);
					for(int eng=0;eng<voengacnum.length;eng++){//发动机梳理
						HashVO voengacnum1=voengacnum[eng];
						String engsn=voengacnum1.getStringValue("engsn");
						String engmodelid=voengacnum1.getStringValue("engmodelid");
						queryAcid="select * from (select * from l_eng_flightlog t where COMPUTEDSTATUS='1' and engsn='"+engsn+"' and acnum='"+Sacid+"'  order by fidate desc) where rownum<2";
						//queryAcid="select * from (select * from l_ac_flightlog t where COMPUTEDSTATUS='1' and t.acnum='"+Sacid+"'  order by fiactarv desc) where rownum<2";
						HashVO[] voeng_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
						if (voeng_log.length>0){
							HashVO voeng_log1=voeng_log[0];
							EngbasicAirMin=voeng_log1.getLongValue("TSN");
							EngbasicCycle=voeng_log1.getLongValue("CSN");
						}
						else{
							EngbasicAirMin=0;
							EngbasicCycle=0;
						}
						EngbasicAirMin+=curaddtime;
						EngbasicCycle+=curaddcycle;
						
						flogstr="select * from L_ENG_FLIGHTLOG where engsn='"+engsn+"' and  global_pk='"+global_pk+"'";
						HashVO[] voengapms=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, flogstr);
						if (voengapms.length>0){//有数据
							if(omisDeploc!=""&&omisArrLoc!=""){//当数据正常是才被修改MODIFYSTATUS=0,
								flogstr="update L_ENG_FLIGHTLOG set COMPUTEDSTATUS=2, FIDATE=to_date(substr('"+omisFlightDate+"',0,10),'yyyy-mm-dd'),FIFLBSN='"+omisID+"'," +
										"TSN="+EngbasicAirMin+",CSN="+EngbasicCycle+",ADD_TIME="+AirMin+",ADD_CYCLE="+omisFc+" where  ENGSN='"+engsn+"' AND global_pk='"+global_pk+"' and MODIFYSTATUS=0";
								}else{
									flogstr="delete from L_ENG_FLIGHTLOG WHERE  ENGSN='"+engsn+"' AND global_pk='"+global_pk+"'";
								}
							updateSqlList.add(flogstr);
							//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
							
							//apms.commit(ApmsConst.DS_APMS);
							
						}
						else{//没有数据
							if(!omisDeploc.equals("")&&!omisArrLoc.equals("")){
								flogstr="INSERT into L_ENG_FLIGHTLOG(ID,MODIFYSTATUS,COMPUTEDSTATUS,FIFLBSN,global_pk,engsn,engmodelid,FIDATE,TSN,CSN,TIME_ONINSTALL,CYCLE_ONINSTALL,ADD_TIME,ADD_CYCLE,acnum)" +
										" VALUES(S_L_ENG_FLIGHTLOG.nextval,0,2,'"+omisID+"','"+global_pk+"','"+engsn+"','"+engmodelid+"',to_date(substr('"+omisFlightDate.trim()+"',1,10),'yyyy-mm-dd'),"+EngbasicAirMin+","+EngbasicCycle+","+voengacnum1.getLognValue("TIME_ONINSTALL")+","+voengacnum1.getLongValue("CYCLE_ONINSTALL")+","+AirMin+","+omisFc+",'"+Sacid+"') ";
								}
							updateSqlList.add(flogstr);
							//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
							
							//apms.commit(ApmsConst.DS_APMS);		
							}
					}
					*/
					//写入发动机日志结束
					
					///////////////////////////////////////////////////////////////////////////////
					
					
					
				}
				//条件数据更新
				apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				apms.commit(ApmsConst.DS_APMS);
		//   }catch(Exception e){
		//	   logger.info("采集OMIS库中"+Sacid+"飞行日志数据失败！");
		//	   continue ;
		//   }
				if (omisFlightDate==null){
					omisFlightDate=omisStartPoint;
				}
		}
		catch (JDOMException e) {
		e.printStackTrace();
		}
		catch(Exception e){
		e.printStackTrace();	
		}
		//返回最后的日期
		return omisFlightDate;
		
	}
	//飞行日志预计部分计算；在导入预计飞行日志之后进行计算。
	public String Flightlog_predict_compute(HashVO voacid,String startdate,String BASEORGID,int status ){
		String queryAcid="";
		String Sacid="";
		long basicAirMin=0;//初始
		long basicBlockMin=0;//初始
		
		long basicomisFc=0;//初始
		
		long sumAirMin=0;//总计
		long sumBlockMin=0;//总计
		
		long sumomisFc=0;//总计
		
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		Date LastDate=null;//最后的日期
		String LastDateS="";
		int d=0;
		int lastid=0;
		try{
			Sacid=voacid.getStringValue("AIRCRAFTSN");
			
			Sacid.replaceAll("-","");
			queryAcid="select * from (select * from l_ac_flightlog_Daily t where COMPUTEDSTATUS='1' and t.acnum='"+Sacid+"'  order by vdfcdate desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			//取得飞行日志计算开始日期
			logger.info("取得飞行日志计算开始日期");
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				
				 basicAirMin=voacid_log1.getLongValue("vdfcair");
				 basicBlockMin=voacid_log1.getLongValue("vdfcblock");
				 basicomisFc=voacid_log1.getLongValue("fiactlg_sum");
				 LastDate=voacid_log1.getDateValue("vdfcdate");
				 LastDateS=voacid_log1.getStringValue("vdfcdate");
				 lastid=voacid_log1.getIntegerValue("id");
				 sumAirMin=basicAirMin;//总计
				 sumBlockMin=basicBlockMin;//总计									
				 sumomisFc=basicomisFc;//总计
				 queryAcid="select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=2 and acnum='"+Sacid+"' group by acnum,fidate,baseorgid order by acnum,fidate";
					HashVO[] flylogvo=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
					for(int i=0;i<flylogvo.length;i++){///计算日期是否有缺失的日期
						HashVO flylogvo1= flylogvo[i];
						HashVO flylogvo2=null;
						 basicAirMinC=flylogvo1.getLognValue("fiair");
						 basicBlockMinC=flylogvo1.getLognValue("fiblock");
						 basicomisFcC=flylogvo1.getLognValue("fiactlg");
						 sumAirMin+=basicAirMinC;//总计
						 sumBlockMin+=basicBlockMinC;//总计									
						 sumomisFc+=basicomisFcC;//总计
						 if(i==0){//与前一天的数据比较
							d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), LastDate));
							if(d>1){
								for(int kk1=1;kk1<=d;kk1++){//循环写入飞行空日期的飞行日志
									if (kk1<d){
										 basicAirMinC=0;
										 basicBlockMinC=0;
										 basicomisFcC=0;
									}
									else{
										 basicAirMinC=flylogvo1.getLognValue("fiair");
										 basicBlockMinC=flylogvo1.getLognValue("fiblock");
										 basicomisFcC=flylogvo1.getLognValue("fiactlg");
									}
										queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+LastDateS+"',1,10),'yyyy-mm-dd')+"+kk1+" and acnum= '"+Sacid+"'";
										HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
										
										if(voflylogsum1.length>0){
											
											queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+basicAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+basicBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+basicomisFc+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+LastDateS+"',1,10),'yyyy-mm-dd')+"+kk1+" and acnum= '"+Sacid+"'";
											logger.info("预计中间有空间1，又有记录！");
										}
										else{
											logger.info("预计中间有空间1，没有记录！");
											queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME)select S_l_ac_flightlog_Daily.nextval,baseorgid,vdfcdate+"+kk1+",acnum,"+basicAirMin+","+basicAirMinC+","+basicBlockMin+","+basicBlockMinC+","+basicomisFcC+","+basicomisFc+",2,sysdate from l_ac_flightlog_Daily where id="+lastid;
										}
										apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
										
										apms.commit(ApmsConst.DS_APMS);
									
								}
							}
						}//第一条数据与前面的数据比较end
						
						if(i<flylogvo.length-1){//取得后一条的数据
								flylogvo2=flylogvo[i+1];
						}
						else{
								flylogvo2=flylogvo[i];
						}
						//判断当前记录跟后一记录的日期差值
						d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
						if(d>1){//有空缺的日期存在
							for(int kk=0;kk<d;kk++){//循环写入飞行空日期的飞行日志
								if(kk>0){//当日期后。
									 basicAirMinC=0;
									 basicBlockMinC=0;
									 basicomisFcC=0;
								}
								else{
									 basicAirMinC=flylogvo1.getLognValue("fiair");
									 basicBlockMinC=flylogvo1.getLognValue("fiblock");
									 basicomisFcC=flylogvo1.getLognValue("fiactlg");
								}
									queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
									HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
									
									if(voflylogsum1.length>0){
										
										queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
										logger.info("预计中间有空间2，又有记录！");
									}
									else{
										logger.info("预计中间有空间2，没有记录！");
										queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd')+"+kk+",'"+Sacid+"',"+sumAirMin+","+(basicAirMinC)+","+sumBlockMin+","+(basicBlockMinC)+","+basicomisFcC+","+sumomisFc+",2,sysdate)";
									}
									apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
									
									apms.commit(ApmsConst.DS_APMS);
								
							}
						}
						else{
							
							queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
							HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
							
							if(voflylogsum1.length>0){
								
								//queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+basicAirMinC+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
								queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
								logger.info("预计有记录存在3，则修改！");
							}
							else{
								logger.info("预计没有记录3，则添加！");
								//queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(basicAirMinC)+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(basicBlockMinC)+","+basicomisFcC+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",1,sysdate)";
								queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+sumAirMin+","+basicAirMinC+","+sumBlockMin+","+basicBlockMinC+","+basicomisFcC+","+sumomisFc+",2,sysdate)";
							}
							apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
							
							apms.commit(ApmsConst.DS_APMS);
							
						}
						//queryAcid="update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate=to_date(substr('"+flylogvo1.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') and acnum='"+flylogvo1.getStringValue("acnum")+"'";
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
						//apms.commit(ApmsConst.DS_APMS);
					}
			
			}
			else{//数据不存在
				queryAcid="select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=2 and acnum='"+Sacid+"' group by acnum,fidate,baseorgid order by acnum,fidate";
				HashVO[] flylogvo=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
				for(int i=0;i<flylogvo.length;i++){///计算日期是否有缺失的日期
					HashVO flylogvo1= flylogvo[i];
					HashVO flylogvo2=null;
					if(i<flylogvo.length-1){//取得后一条的数据
						flylogvo2=flylogvo[i+1];
					}
					else{
						flylogvo2=flylogvo[i];
					}
					//判断当前记录跟后一记录的日期差值
					d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
					if(d>1){//有空缺的日期存在
						for(int kk=0;kk<d;kk++){//循环写入飞行空日期的飞行日志
							if(kk>0){//当日期后。
								 //basicAirMin-=basicAirMinC;
								 //basicBlockMin-=basicBlockMinC;
								 //basicomisFc-=basicomisFcC;
								 basicAirMinC=0;
								 basicBlockMinC=0;
								 basicomisFcC=0;
							}
							else{
								 basicAirMinC=flylogvo1.getLognValue("fiair");
								 basicBlockMinC=flylogvo1.getLognValue("fiblock");
								 basicomisFcC=flylogvo1.getLognValue("fiactlg");
							}
								queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
								HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
								
								if(voflylogsum1.length>0){
									
									queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+basicAirMinC+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
									logger.info("预计中间有空间4，又有记录！");
								}
								else{
									logger.info("预计中间有空间4，没有记录！");
									queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd')+"+kk+",'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(basicAirMinC)+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(basicBlockMinC)+","+basicomisFcC+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",2,sysdate)";
								}
								apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
								
								apms.commit(ApmsConst.DS_APMS);
							
						}
					}
					else{
						queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
						HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
						
						if(voflylogsum1.length>0){
							
							queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+flylogvo1.getLognValue("fiair")+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+flylogvo1.getLognValue("fiblock")+",FIACTLG="+flylogvo1.getLognValue("fiactlg")+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
							logger.info("预计有记录存在5，则修改！");
						}
						else{
							logger.info("预计没有记录5，则添加！");
							queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(flylogvo1.getLognValue("fiair"))+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(flylogvo1.getLognValue("fiblock"))+","+flylogvo1.getLognValue("FIACTLG")+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",2,sysdate)";
						}
						apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
						
						apms.commit(ApmsConst.DS_APMS);
						
					}
					//queryAcid="update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate=to_date(substr('"+flylogvo1.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') and acnum='"+flylogvo1.getStringValue("acnum")+"'";
					//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
					//apms.commit(ApmsConst.DS_APMS);
				}
				
			}
			logger.info("计算"+Sacid+"的预计飞行日志结束");
		}
		catch(Exception e){
			e.printStackTrace();		
			
		}
		return "0";
	}
	
	public String getCurrentNowSecond() {
		Date today = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = f.format(today);
		return time;
	}

	public String getNowDate() {
		String temp_str = "";
		Date dt = new Date();

		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		// SimpleDateFormat sdf = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;

	}
	 
	/*更新飞行数据有日期的数据*/
	public String extract_FlightLog(HashVO voacid,String omisStartPoint,String BASEORGID){
		String flogstr="";
		String queryAcid ="";
		String Sacid="";
		String omisID ;
		String omisACNo; 
		String omisFlightDate = null;
		String    omisFlightNO;
		String omisDeploc;
		String omisArrLoc;
		String    omisStd;
		String omisSta;
		String omisOut;
		String omisOff;
		String    omisOn;
		String omisIn;
		String Flbid ="1";
		String update="";
		String global_pk="";
		int omisFc;
		int AirMin;
		int BlockMin;// '申明读取OMIS数据函数
		
		long basicAirMin=0;//初始
		long basicBlockMin=0;//初始
		
		long basicomisFc=0;//初始
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		long curaddtime=0;
		long curaddcycle=0;
		String operate_time="";//最后更新时间
		HashVO voflylog2=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try{

			Sacid=voacid.getStringValue("AIRCRAFTSN");
			
			Sacid.replaceAll("-","");
			//取得飞行日志的基础值
			queryAcid="select * from (select * from l_ac_flightlog_Daily t where COMPUTEDSTATUS='1' and t.acnum='"+Sacid+"'  order by vdfcdate desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				
						update=voacid.getStringValue("update_time");
						
				 //取得数据
				 basicAirMin=voacid_log1.getLognValue("VDFCAIR");
				 basicBlockMin=voacid_log1.getLognValue("VDFCBLOCK");
				 basicomisFc=voacid_log1.getLognValue("FIACTLG_SUM");
			}
			else{
				update=voacid.getStringValue("update_time");
				//不存在，赋值0
				 basicAirMin=0;
				 basicBlockMin=0;
				 basicomisFc=0;
			}
			if (update==null){
				update=omisStartPoint;
			}
			//飞行日志基础数据end
			
		flogstr="SELECT CONCAT(F.ID_NO,F.global_pk) AS SYS_ID,F.global_pk AS global_pk,D.AC_ID AS ACNUM,D.FLT_DATE AS FLIGHTDATE,concat(F.iata_c,F.flt_id) as Flightno," +
          "F.dep_apt AS DEPLOC,F.arr_apt AS ARRLOC,F.std AS F_STD,F.sta AS F_STA,F.OUT_TIME AS OUTGATE,F.OFF_TIME ,F.ON_TIME," +          
		  "f.IN_TIME , f.OFF_TIME_LOG, f.ON_TIME_LOG, f.fc, f.con_fc , f.operate_time" +		
          " FROM airchina.ac_fly_log F,airchina.AC_FH_FC D " +
          " Where f.ID_NO = d.ID_NO AND D.ac_id='"+Sacid+"' and  d.flt_date>=to_date(substr('"+update+"',0,10),'yyyy-MM-dd')-5 and (f.operate_time is null  or f.operate_time>to_date('"+update+"','yyyy-MM-dd hh24:mi:ss'))  order by D.FLT_DATE, F.std" ;
          //" Where f.ID_NO = d.ID_NO AND D.ac_id='"+Sacid+"' and d.flt_date>= to_date(substr('" +update.trim()+"',1,10),'yyyy-mm-dd')"+
          //"and D.FLT_DATE>=to_date(substr('" + omisStartPoint.trim() + "',1,10),'yyyy-mm-dd')  and (f.operate_time is null  or f.operate_time>to_date('"+update+"','yyyy-MM-dd hh24:mi:ss'))  order by D.FLT_DATE, F.std" ;
		//'ORCALE SQL 语句";
		//logger.info(flogstr);
				HashVO[] voflylog=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_OMIS, flogstr);
				for(int j=0;j<voflylog.length;j++){
					HashVO voflylog1=voflylog[j];
				
					//不是第一条数据的时候，取前一条数据
					if (j>0){
						voflylog2=voflylog[j-1];	
					}
					else{//第一条数据的时候取得相同的数据
						voflylog2=voflylog[j];	
					}
					omisID = voflylog1.getStringValue("sys_id");
					omisACNo = voflylog1.getStringValue("acnum");
					omisFlightDate =voflylog1.getStringValue("flightdate");
					omisFlightNO =voflylog1.getStringValue("flightno");
					omisDeploc =voflylog1.getStringValue("deploc");
					omisArrLoc = voflylog1.getStringValue("arrloc");
					omisStd = voflylog1.getStringValue("f_std");
					omisSta = voflylog1.getStringValue("f_sta");
					operate_time=voflylog1.getStringValue("operate_time");
					global_pk=voflylog1.getStringValue("global_pk");
					/*//判断是否是同一天的数据
					if (j>0){
						dif=Math.abs(DateUtil.dateDiff("d", voflylog1.getDateValue("flightdate"), voflylog2.getDateValue("flightdate")));
					}
					else{
						dif=Math.abs(DateUtil.dateDiff("d", voflylog1.getDateValue("flightdate"), Lastdate));
					}
						difa=Math.abs(DateUtil.dateDiff("d", voflylog1.getDateValue("flightdate"), voflylog3.getDateValue("flightdate")));
					logger.info("difa="+difa);
					*/
					//logger.info("omisID="+omisID);
					if (operate_time!=null){//当operate_time 不为null的时候,取on_time_log,off_time_log
						if ( voflylog1.getStringValue("off_time")==null|| voflylog1.getStringValue("off_time").compareTo("1900-01-01")==0){ //  '没有离港时间时
							omisOff = omisStd;
							}else{
							omisOff = voflylog1.getStringValue("off_time");
							}
				
							if ( voflylog1.getStringValue("on_time")==null|| voflylog1.getStringValue("on_time").compareTo("1900-01-01")==0){ // '没有到达时间时
								omisOn = omisSta;
							}else{
								omisOn = voflylog1.getStringValue("on_time");
							}
							if ( voflylog1.getStringValue("on_time_log")==null|| voflylog1.getStringValue("on_time_log").compareTo("1900-01-01")==0||voflylog1.getStringValue("off_time_log")==null|| voflylog1.getStringValue("off_time_log").compareTo("1900-01-01")==0){ // '实际时间
							}else{
								omisOff = voflylog1.getStringValue("off_time_log");
								omisOn = voflylog1.getStringValue("on_time_log");
							}
				
							if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){//  '没有离港时间时
							omisOut =DateUtil.dateAdd("n", omisOff ,-5);// '*******************时间处理
							}else{
							omisOut = voflylog1.getStringValue("outgate");
							}
				
							if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){// '没有离港时间时
							omisIn =DateUtil.dateAdd("n", omisOn, 5);// '*******************时间处理
							}else{
							omisIn = voflylog1.getStringValue("in_time");
							
							}
					}
					else{
						if ( voflylog1.getStringValue("off_time")==null|| voflylog1.getStringValue("off_time").compareTo("1900-01-01")==0){ //  '没有离港时间时
						omisOff = omisStd;
						}else{
						omisOff = voflylog1.getStringValue("off_time");
						}
			
						if ( voflylog1.getStringValue("on_time")==null|| voflylog1.getStringValue("on_time").compareTo("1900-01-01")==0){ // '没有到达时间时
							omisOn = omisSta;
						}else{
							omisOn = voflylog1.getStringValue("on_time");
						}
			
			
						if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){//  '没有离港时间时
						omisOut =DateUtil.dateAdd("n", omisOff ,-5);// '*******************时间处理
						}else{
						omisOut = voflylog1.getStringValue("outgate");
						}
			
						if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){// '没有离港时间时
						omisIn =DateUtil.dateAdd("n", omisOn, 5);// '*******************时间处理
						}else{
						omisIn = voflylog1.getStringValue("in_time");
						
						}
					}
					logger.info("计算UTC时间开始");
					omisOut =DateUtil.dateAdd("h", omisOut,-8);
					omisOn =  DateUtil.dateAdd("h",omisOn, -8);
					omisOff = DateUtil.dateAdd("h",omisOff, -8);
					omisIn =  DateUtil.dateAdd("h", omisIn, -8);
					
					//'------------
					AirMin =Math.abs(DateUtil.dateDiff("minute", sdf.parse(omisOff), sdf.parse(omisOn)));
					BlockMin =Math.abs(DateUtil.dateDiff("minute",sdf.parse(omisOut),sdf.parse(omisIn)));
					logger.info("计算UTC时间结束");
					if (voflylog1.getStringValue("fc")!=null){
					omisFc =voflylog1.getIntegerValue("fc");
					}else{
					omisFc = 1;
					
					}
					//同一架飞机同一天
					if (omisFlightDate.equals(voflylog2.getStringValue("flightdate").trim())){
						//当天累计
						 basicAirMinC+=AirMin;
						 basicBlockMinC+=BlockMin;
						 basicomisFcC+=omisFc;
					}
					else{//同一架飞机不同天
						//当天累计
						 basicAirMinC=AirMin;
						 basicBlockMinC=BlockMin;
						 basicomisFcC=omisFc;
					}

					
					//总数累计
					 basicAirMin+=AirMin;
					 basicBlockMin+=BlockMin;
					 basicomisFc+=omisFc;
					 //本次循环的增加的总数
					 curaddtime+=AirMin;
					 curaddcycle+=omisFc;
					 //判断飞机是否存在
					 //写入飞行日志数据开始
					 /*
					 if (difa>1){//存在中间空日期的情况与前一条记录有空闲
						//要重新判断一下，当是第一条时只考虑与以前的数据判断，之后都跟后面的数据比较是否有空日期
						 //存在多天空的情况
						 for(int kk=0;kk<difa;kk++){
						 flogstr="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+voflylog1.getStringValue("flightdate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+"+1 and AIRCRAFTSN= '"+Sacid+"'";
							HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, flogstr);
							if(voflylogsum1.length>0){
								
								flogstr="update l_ac_flightlog_Daily set VDFCAIR="+(basicAirMin)+",FIAIR=0,VDFCBLOCK="+(basicBlockMin)+",FIBLOCK=0,FIACTLG=0,FIACTLG_SUM="+(basicomisFc)+",STATUS=1,UPDATETIME=sysdate,USERID='0' where  VDFCDATE=to_date(substr('"+voflylog1.getStringValue("flightdate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+"+1 and AIRCRAFTSN= '"+Sacid+"'";
								logger.info("中间有空间，又有记录！");
							}
							else{
								logger.info("中间有空间，没有记录！");
								flogstr="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,AIRCRAFTSN,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,DATESTATE,STATUS,UPDATETIME,USERID) values (S_l_ac_flightlog_Daily.nextval,"+BASEORGID+",to_date(substr('"+voflylog1.getStringValue("flightdate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+"+1,'"+Sacid+"',"+(basicAirMin)+",0,"+(basicBlockMin)+",0,0,"+(basicomisFc)+",'0','1',sysdate,'0')";
							}
							apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
							
							apms.commit(ApmsConst.DS_APMS);
						 }
					 }
					 
					flogstr="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+voflylog1.getStringValue("flightdate").trim()+"',1,10),'yyyy-mm-dd') and AIRCRAFTSN= '"+Sacid+"'";
					HashVO[] voflylogsum=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, flogstr);
					if(voflylogsum.length>0){
						
						flogstr="update l_ac_flightlog_Daily set VDFCAIR="+basicAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+basicBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+basicomisFc+",STATUS=1,UPDATETIME=sysdate,USERID='0' where  VDFCDATE=to_date(substr('"+voflylog1.getStringValue("flightdate").trim()+"',1,10),'yyyy-mm-dd') and AIRCRAFTSN= '"+Sacid+"'";
						
					}
					else{
						
						flogstr="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,AIRCRAFTSN,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,DATESTATE,STATUS,UPDATETIME,USERID) values (S_l_ac_flightlog_Daily.nextval,"+BASEORGID+",to_date(substr('"+voflylog1.getStringValue("flightdate").trim()+"',1,10),'yyyy-mm-dd'),'"+Sacid+"',"+basicAirMin+","+basicAirMinC+","+basicBlockMin+","+basicBlockMinC+","+basicomisFcC+","+basicomisFc+",'0','1',sysdate,'0')";
					}
					
					apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
					
					apms.commit(ApmsConst.DS_APMS);
					//飞行日志结束
					
					//修改存在的数据
					 
					 */
					//写入飞行日志开始 
					
					flogstr="select * from l_ac_flightlog where fiflbsn='"+omisID+"'";
					HashVO[] voflylogapms=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, flogstr);
					if (voflylogapms.length>0){//有数据
						//取得老数据
						//HashVO voflylogapms1=voflylogapms[0];
						//减去老数据
						 //basicAirMin-=voflylogapms1.getIntegerValue("FIAIR");
						 //basicBlockMin-=voflylogapms1.getIntegerValue("FIBLOCK");
						 //basicomisFc-=voflylogapms1.getIntegerValue("FIACTLG");
						
						if(omisDeploc!=""&&omisArrLoc!=""){//当数据为正常是才被修改MODIFYSTATUS=0
						flogstr="update l_ac_flightlog set COMPUTEDSTATUS=0,FIDATE=to_date(substr('"+omisFlightDate+"',1,10),'yyyy-mm-dd'),FIFLTNO='"+omisFlightNO+"',FIDEPLOC='"+omisDeploc+"',FIARVLOC='"+omisArrLoc+"',FIACTDEP=to_date('"+omisOut+"','yyyy-mm-dd hh24:mi:ss')," +
								"FIACTTAK=to_date('"+omisOff+"','yyyy-mm-dd hh24:mi:ss'), FIACTLAD=to_date('"+omisOn+"','yyyy-mm-dd hh24:mi:ss'),FIACTARV=to_date('"+omisIn+"','yyyy-mm-dd hh24:mi:ss'),FIACTLG="+omisFc+", FISTA=2,FIAIR="+AirMin+",FIBLOCK="+BlockMin+",FLAIR_SUM="+basicAirMin+",FIBLOCK_SUM="+basicBlockMin+",FIACTLG_SUM="+basicomisFc+",updatetime=sysdate where fiflbsn='"+omisID+"' and MODIFYSTATUS=0";
						}else{
							flogstr="delete from l_ac_flightlog WHERE fiflbsn='"+omisID+"'";
						}
						updateSqlList.add(flogstr);
						
						
						//如果有存在，则把整天的数据都设置为重新计算的状态
						
						flogstr="update l_ac_flightlog set COMPUTEDSTATUS=0,updatetime=sysdate where FIDATE>=to_date(substr('"+omisFlightDate+"',1,10),'yyyy-mm-dd') and acnum='"+Sacid+"' and COMPUTEDSTATUS<>2";
						
						updateSqlList.add(flogstr);
						flogstr="update l_ac_flightlog_daily set COMPUTEDSTATUS=0,updatetime=sysdate where vdfcDATE>=to_date(substr('"+omisFlightDate+"',0,10),'yyyy-mm-dd') and acnum='"+Sacid+"' and COMPUTEDSTATUS<>2";
						updateSqlList.add(flogstr);
						
						apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						apms.commit(ApmsConst.DS_APMS);
						
						//删除当日有正常数据的预计数据
						//flogstr="delete from l_ac_flightlog where FIDATE=to_date(substr('"+omisFlightDate+"',0,10),'yyyy-mm-dd') and acnum='"+Sacid+"' and COMPUTEDSTATUS=2";
						//updateSqlList.add(flogstr);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						//apms.commit(ApmsConst.DS_APMS);
						////////////////////////////////////////////////////////////////////////////
					}
					else{
						//没有
						if(!omisDeploc.equals("")&&!omisArrLoc.equals("")){
						flogstr="INSERT into l_ac_flightlog(ID,MODIFYSTATUS,COMPUTEDSTATUS,FIFLBSN,FIFLBID,global_pk,acnum,BASEORGID,FIDATE,FIFLTNO,FIDEPLOC,FIARVLOC,FIACTDEP,FIACTTAK,FIACTLAD,FIACTARV,FIACTLG,FISTA,FIAIR,FIBLOCK,FLAIR_SUM,FIBLOCK_SUM,FIACTLG_SUM,UPDATETIME,USERID)" +
								" VALUES(S_l_ac_flightlog.nextval,0,0,'"+omisID+"','"+Flbid+"','"+global_pk+"','"+omisACNo+"',"+BASEORGID+",to_date(substr('"+omisFlightDate.trim()+"',1,10),'yyyy-mm-dd'),'"+omisFlightNO+"','"+omisDeploc+"','"+omisArrLoc+"',to_date('"+omisOut+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisOff+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisOn+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisIn+"','yyyy-mm-dd hh24:mi:ss'),'"+omisFc+"',2,"+AirMin+","+BlockMin+","+basicAirMin+","+basicBlockMin+","+basicomisFc+",sysdate,0) ";
						}
						else{
						flogstr="delete from l_ac_flightlog WHERE fiflbsn='"+omisID+"'";
						}
						updateSqlList.add(flogstr);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						//apms.commit(ApmsConst.DS_APMS);
					}
					
					
					//写入飞行日志结束
				}

				///////////////////////////////////////////////////////////////////////////////
				if(operate_time==null||operate_time==""){
					operate_time=getCurrentNowSecond();
				}
				
				flogstr="update b_apms_outlink set update_time=to_date(substr('"+operate_time+"',0,19),'yyyy-mm-dd hh24:mi:ss') where linksys='OMISFLY' AND MODEL='"+Sacid+"'";
				updateSqlList.add(flogstr);
				apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				apms.commit(ApmsConst.DS_APMS);
				updateSqlList.clear();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return operate_time;
	}
	
	//分段日志，总计统计计算
	public String Flight_compute_log(HashVO voacid,String startdate,String BASEORGID,int status ){
		
		long sumAirMin=0;//总计
		long sumBlockMin=0;//总计
		
		long sumomisFc=0;//总计
		
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		String Sacid ="";
		String queryAcid="";
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try{
			Sacid=voacid.getStringValue("AIRCRAFTSN");
			logger.info("计算"+Sacid+"的飞行日志分段日志总计开始");
			Sacid.replaceAll("-","");
			queryAcid="select * from (select * from l_ac_flightlog t where COMPUTEDSTATUS='1' and t.acnum='"+Sacid+"'  order by fidate desc,fiactdep desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			//取得分段日志最近一条的正常数据
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				 sumAirMin=voacid_log1.getLognValue("flair_sum");//总计
				 sumBlockMin=voacid_log1.getLognValue("fiblock_sum");//总计									
				 sumomisFc=voacid_log1.getLognValue("fiactlg_sum");//总计
			}
			queryAcid="select * from l_ac_flightlog t where COMPUTEDSTATUS='0' and t.acnum='"+Sacid+"'  order by fidate,fiactdep";
			HashVO[] vofly_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			for(int i=0;i<vofly_log.length;i++){
				logger.info("计算"+Sacid+"的第"+i+"条飞行日志分段日志");
				 HashVO vofly_log1=vofly_log[i];
				 basicAirMinC=vofly_log1.getLognValue("fiair");
				 basicBlockMinC=vofly_log1.getLognValue("fiblock");
				 basicomisFcC=vofly_log1.getLognValue("fiactlg");
				 sumAirMin+=basicAirMinC;//总计
				 sumBlockMin+=basicBlockMinC;//总计									
				 sumomisFc+=basicomisFcC;//总计
				 queryAcid="update l_ac_flightlog set flair_sum="+sumAirMin+",fiblock_sum="+sumBlockMin+",fiactlg_sum="+sumomisFc+" where id="+vofly_log1.getLognValue("id");
				 updateSqlList.add(queryAcid);
			}
			apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			apms.commit(ApmsConst.DS_APMS);
			updateSqlList.clear();
			logger.info("计算"+Sacid+"的飞行日志分段日志总计结束");
		}
		catch(Exception e){
			e.printStackTrace();		
			
		}
		return "0";
	}
	
//	//飞行日志计算；在导入飞行日志daily之后进行计算。
	public String Flight_compute(HashVO voacid,String startdate,String BASEORGID,int status ){
		String queryAcid="";
		String Sacid="";
		long basicAirMin=0;//初始
		long basicBlockMin=0;//初始
		
		long basicomisFc=0;//初始
		
		long sumAirMin=0;//总计
		long sumBlockMin=0;//总计
		
		long sumomisFc=0;//总计
		
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		Date LastDate=null;//最后的日期
		String LastDateS="";
		int d=0;
		int lastid=0;
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try{
			Sacid=voacid.getStringValue("AIRCRAFTSN");
			
			Sacid.replaceAll("-","");
			queryAcid="select * from (select * from l_ac_flightlog_Daily t where COMPUTEDSTATUS='1' and t.acnum='"+Sacid+"'  order by vdfcdate desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			//取得飞行日志计算开始日期
			logger.info("取得飞行日志计算开始日期");
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				
				 basicAirMin=voacid_log1.getLongValue("vdfcair");
				 basicBlockMin=voacid_log1.getLongValue("vdfcblock");
				 basicomisFc=voacid_log1.getLongValue("fiactlg_sum");
				 LastDate=voacid_log1.getDateValue("vdfcdate");
				 LastDateS=voacid_log1.getStringValue("vdfcdate");
				 lastid=voacid_log1.getIntegerValue("id");
				 sumAirMin=basicAirMin;//总计
				 sumBlockMin=basicBlockMin;//总计									
				 sumomisFc=basicomisFc;//总计
				 queryAcid="select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=0 and acnum='"+Sacid+"' and fidate>=to_date(substr('"+LastDateS+"',1,10),'yyyy-MM-dd') group by acnum,fidate,baseorgid order by acnum,fidate";
					HashVO[] flylogvo=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
					for(int i=0;i<flylogvo.length;i++){///计算日期是否有缺失的日期
						HashVO flylogvo1= flylogvo[i];
						HashVO flylogvo2=null;
						 basicAirMinC=flylogvo1.getLognValue("fiair");
						 basicBlockMinC=flylogvo1.getLognValue("fiblock");
						 basicomisFcC=flylogvo1.getLognValue("fiactlg");
						 sumAirMin+=basicAirMinC;//总计
						 sumBlockMin+=basicBlockMinC;//总计									
						 sumomisFc+=basicomisFcC;//总计
						 if(i==0){//与前一天的数据比较
							d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), LastDate));
							if(d>1){
								for(int kk1=0;kk1<d;kk1++){//循环写入飞行空日期的飞行日志
									if (kk1<d){
										 basicAirMinC=0;
										 basicBlockMinC=0;
										 basicomisFcC=0;
									}
									else{
										 basicAirMinC=flylogvo1.getLognValue("fiair");
										 basicBlockMinC=flylogvo1.getLognValue("fiblock");
										 basicomisFcC=flylogvo1.getLognValue("fiactlg");
									}
										queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+LastDateS+"',1,10),'yyyy-mm-dd')+"+kk1+" and acnum= '"+Sacid+"'";
										HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
										
										if(voflylogsum1.length>0){
											
											queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+LastDateS+"',1,10),'yyyy-mm-dd')+"+kk1+" and acnum= '"+Sacid+"'";
											logger.info("实际中间有空间1，又有记录！");
										}
										else{
											logger.info("实际数据中间有空间1，没有记录！");
											queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME)select S_l_ac_flightlog_Daily.nextval,baseorgid,vdfcdate+"+kk1+",acnum,"+sumAirMin+","+basicAirMinC+","+sumBlockMin+","+basicBlockMinC+","+basicomisFcC+","+sumomisFc+",COMPUTEDSTATUS,sysdate from l_ac_flightlog_Daily where id="+lastid;
										}
										updateSqlList.add(queryAcid);
										//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
										
										//apms.commit(ApmsConst.DS_APMS);
									
								}
							}
						}//第一条数据与前面的数据比较end
						
						if(i<flylogvo.length-1){//取得后一条的数据
								flylogvo2=flylogvo[i+1];
						}
						else{
								flylogvo2=flylogvo[i];
						}
						//判断当前记录跟后一记录的日期差值
						d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
						if(d>1){//有空缺的日期存在
							for(int kk=0;kk<d;kk++){//循环写入飞行空日期的飞行日志
								if(kk>0){//当日期后。
									 basicAirMinC=0;
									 basicBlockMinC=0;
									 basicomisFc=0;
								}
								else{
									 basicAirMinC=flylogvo1.getLognValue("fiair");
									 basicBlockMinC=flylogvo1.getLognValue("fiblock");
									 basicomisFcC=flylogvo1.getLognValue("fiactlg");
								}
									queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
									HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
									
									if(voflylogsum1.length>0){
										
										queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
										logger.info("实际中间有空间2，又有记录！");
									}
									else{
										logger.info("实际中间有空间2，没有记录！");
										queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd')+"+kk+",'"+Sacid+"',"+sumAirMin+","+(basicAirMinC)+","+sumBlockMin+","+(basicBlockMinC)+","+basicomisFcC+","+sumomisFc+",1,sysdate)";
									}
									updateSqlList.add(queryAcid);
									//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
									
									//apms.commit(ApmsConst.DS_APMS);
								
							}
						}
						else{
							
							queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
							HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
							
							if(voflylogsum1.length>0){
								
								//queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+basicAirMinC+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
								queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
								logger.info("实际有记录存在3，则修改！");
							}
							else{
								logger.info("实际没有记录3，则添加！");
								//queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(basicAirMinC)+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(basicBlockMinC)+","+basicomisFcC+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",1,sysdate)";
								queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+sumAirMin+","+basicAirMinC+","+sumBlockMin+","+basicBlockMinC+","+basicomisFcC+","+sumomisFc+",1,sysdate)";
							}
							
							updateSqlList.add(queryAcid);
							//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
							
							//apms.commit(ApmsConst.DS_APMS);
							
						}
						queryAcid="update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate>=to_date(substr('"+flylogvo1.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') and acnum='"+flylogvo1.getStringValue("acnum")+"' and COMPUTEDSTATUS=0";
						updateSqlList.add(queryAcid);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
						//apms.commit(ApmsConst.DS_APMS);
					}
			
			}
			else{//数据不存在
				queryAcid="select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=0 and acnum='"+Sacid+"' group by acnum,fidate,baseorgid order by acnum,fidate";
				HashVO[] flylogvo=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
				for(int i=0;i<flylogvo.length;i++){///计算日期是否有缺失的日期
					HashVO flylogvo1= flylogvo[i];
					HashVO flylogvo2=null;
					if(i<flylogvo.length-1){//取得后一条的数据
						flylogvo2=flylogvo[i+1];
					}
					else{
						flylogvo2=flylogvo[i];
					}
					//判断当前记录跟后一记录的日期差值
					d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
					if(d>1){//有空缺的日期存在
						for(int kk=0;kk<d;kk++){//循环写入飞行空日期的飞行日志
							if(kk>0){//当日期后。
								 //basicAirMin-=basicAirMinC;
								 //basicBlockMin-=basicBlockMinC;
								 //basicomisFc-=basicomisFcC;
								 basicAirMinC=0;
								 basicBlockMinC=0;
								 basicomisFc=0;
							}
							else{
								 basicAirMinC=flylogvo1.getLognValue("fiair");
								 basicBlockMinC=flylogvo1.getLognValue("fiblock");
								 basicomisFcC=flylogvo1.getLognValue("fiactlg");
							}
								queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
								HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
								
								if(voflylogsum1.length>0){
									
									queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+basicAirMinC+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
									logger.info("中间有空间4，又有记录！");
								}
								else{
									logger.info("中间有空间4，没有记录！");
									queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd')+"+kk+",'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(basicAirMinC)+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(basicBlockMinC)+","+basicomisFcC+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",1,sysdate)";
								}
								updateSqlList.add(queryAcid);
								//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
								
								//apms.commit(ApmsConst.DS_APMS);
							
						}
					}
					else{
						queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
						HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
						
						if(voflylogsum1.length>0){
							
							queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+flylogvo1.getLognValue("fiair")+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+flylogvo1.getLognValue("fiblock")+",FIACTLG="+flylogvo1.getLognValue("fiactlg")+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
							logger.info("有记录存在5，则修改！");
						}
						else{
							logger.info("没有记录5，则添加！");
							queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(flylogvo1.getLognValue("fiair"))+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(flylogvo1.getLognValue("fiblock"))+","+flylogvo1.getLognValue("FIACTLG")+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",1,sysdate)";
						}
						updateSqlList.add(queryAcid);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
						
						//apms.commit(ApmsConst.DS_APMS);
						
					}
					queryAcid="update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate>=to_date(substr('"+flylogvo1.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') and acnum='"+flylogvo1.getStringValue("acnum")+"' and COMPUTEDSTATUS=0";
					updateSqlList.add(queryAcid);
					//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
					//apms.commit(ApmsConst.DS_APMS);
				}
				
			}
			apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			apms.commit(ApmsConst.DS_APMS);	
			updateSqlList.clear();
			logger.info("计算"+Sacid+"的飞行日志结束");
		}
		catch(Exception e){
			e.printStackTrace();		
			
		}
		return "0";
	}
	/*更新飞行日志预计的数据*/
	public String extract_SimpleFlightLog_predict(String acnum,String omisStartPoint,String BASEORGID){
		String flogstr="";
		String queryAcid ="";
		String Sacid="";
		String omisID ;
		String omisACNo; 
		String omisFlightDate = null;
		String    omisFlightNO;
		String omisDeploc;
		String omisArrLoc;
		String    omisStd;
		String omisSta;
		String omisOut;
		String omisOff;
		String    omisOn;
		String omisIn;
		String Flbid ="1";
		String update="";
		String global_pk="";
		int omisFc;
		int AirMin;
		int BlockMin;// '申明读取OMIS数据函数
		
		long basicAirMin=0;//初始
		long basicBlockMin=0;//初始
		
		long basicomisFc=0;//初始
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		long curaddtime=0;//当前更新增加的时间
		long curaddcycle=0;//当前更新增加的循环
		HashVO voflylog2=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		ArrayList<String> updateSqlList = new ArrayList<String>();
		try{

			//Sacid=voacid.getStringValue("AIRCRAFTSN");
			Sacid=acnum;
			
			Sacid.replaceAll("-","");
			//取得飞行日志的基础值
			//queryAcid="select * from (select * from l_ac_flightlog_Daily t where STATUS='1' and t.acnum='"+Sacid+"'   order by vdfcdate desc) where rownum<2";
			queryAcid="select * from (select *  from l_ac_flightlog t where COMPUTEDSTATUS=1 and acnum='"+Sacid+"' order by fidate desc, fiactarv desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				
				//取得数据
				 basicAirMin=voacid_log1.getLognValue("flair_sum");
				 basicBlockMin=voacid_log1.getLognValue("fiblock_sum");
				 basicomisFc=voacid_log1.getLognValue("FIACTLG_SUM");
				 update=voacid_log1.getStringValue("fidate");
			}
			else{
				
				update=omisStartPoint;
				//不存在，赋值0
				 basicAirMin=0;
				 basicBlockMin=0;
				 basicomisFc=0;
			}
			//飞行日志基础数据end
		flogstr="select F.global_pk AS SYS_ID,F.global_pk AS global_pk,f.AC_ID AS ACNUM,f.FLT_DATE AS FLIGHTDATE,concat(F.iata_c,F.flt_id) as Flightno,F.dep_apt AS DEPLOC,F.arr_apt AS ARRLOC,F.std AS F_STD,F.sta AS F_STA,F.OUT_TIME AS OUTGATE,F.OFF_TIME ,F.ON_TIME," +
				"f.IN_TIME ,case  when dep_apt=arr_apt then 50 else 1 end   fc from airchina.flight_information f where flt_date>to_date(substr('"+update.trim()+"',0,10),'yyyy-mm-dd') and ac_id='"+Sacid+"' order by flt_date,f.std";
				HashVO[] voflylog=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_OMIS, flogstr);
				for(int j=0;j<voflylog.length;j++){
					//if(datestate.equals("2")){//如果是初始化数据，则不执行下面的
					//	datestate="1";
						//continue;
					//}
					HashVO voflylog1=voflylog[j];
					//不是第一条数据的时候，取前一条数据
					if (j>0){
						voflylog2=voflylog[j-1];	
					}
					else{//第一条数据的时候取得相同的数据
						voflylog2=voflylog[j];	
					}
					
					omisID = voflylog1.getStringValue("sys_id");
					omisACNo = voflylog1.getStringValue("acnum");
					omisFlightDate =voflylog1.getStringValue("flightdate").trim();
					omisFlightNO =voflylog1.getStringValue("flightno");
					omisDeploc =voflylog1.getStringValue("deploc");
					omisArrLoc = voflylog1.getStringValue("arrloc");
					omisStd = voflylog1.getStringValue("f_std");
					omisSta = voflylog1.getStringValue("f_sta");
					//operate_time=voflylog1.getStringValue("operate_time");
					global_pk=voflylog1.getStringValue("global_pk");
					
					//int dif=Math.abs(DateUtil.dateDiff("d", voflylog1.getDateValue("flightdate"), voflylog2.getDateValue("flightdate")));
					
					//logger.info("omisID="+omisID);
					if ( voflylog1.getStringValue("off_time")==null|| voflylog1.getStringValue("off_time").compareTo("1900-01-01")==0){ //  '没有离港时间时
					omisOff = omisStd;
					}else{
					omisOff = voflylog1.getStringValue("off_time");
					}
		
					if ( voflylog1.getStringValue("on_time")==null|| voflylog1.getStringValue("on_time").compareTo("1900-01-01")==0){ // '没有到达时间时
						omisOn = omisSta;
					}else{
						omisOn = voflylog1.getStringValue("on_time");
					}
		
		
					if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){//  '没有离港时间时
					omisOut =DateUtil.dateAdd("n", omisOff ,-5);// '*******************时间处理
					}else{
					omisOut = voflylog1.getStringValue("outgate");
					}
		
					if (voflylog1.getStringValue("outgate")==null||voflylog1.getStringValue("outgate").compareTo("1900-01-01")==0||voflylog1.getStringValue("in_time")==null||voflylog1.getStringValue("in_time").compareTo("1900-01-01")==0){// '没有离港时间时
					omisIn =DateUtil.dateAdd("n", omisOn, 5);// '*******************时间处理
					}else{
					omisIn = voflylog1.getStringValue("in_time");
					
					}
					logger.info("计算UTC时间开始");
					//'convert utc time
					omisOut = DateUtil.dateAdd("h", omisOut,-8);
					omisOn =  DateUtil.dateAdd("h",omisOn, -8);
					omisOff = DateUtil.dateAdd("h",omisOff, -8);
					omisIn =  DateUtil.dateAdd("h", omisIn, -8);
					
					//'------------
					AirMin =Math.abs(DateUtil.dateDiff("minute", sdf.parse(omisOff), sdf.parse(omisOn)));
					BlockMin =Math.abs(DateUtil.dateDiff("minute",sdf.parse( omisOut),sdf.parse( omisIn)));
					logger.info("计算UTC时间结束");
					if (voflylog1.getStringValue("fc")!=null){
					omisFc =voflylog1.getIntegerValue("fc");
					}else{
					omisFc = 1;
					
					}
					//同一架飞机同一天
					if (omisFlightDate.equals(voflylog2.getStringValue("flightdate").trim())){
						//当天累计
						 basicAirMinC+=AirMin;
						 basicBlockMinC+=BlockMin;
						 basicomisFcC+=omisFc;
					}
					else{//同一架飞机不同天
						//当天累计
						 basicAirMinC=AirMin;
						 basicBlockMinC=BlockMin;
						 basicomisFcC=omisFc;
					}

					
					//总数累计
					 basicAirMin+=AirMin;
					 basicBlockMin+=BlockMin;
					 basicomisFc+=omisFc;
					 curaddtime+=AirMin;
					 curaddcycle+=omisFc;
					
					//修改存在的数据
					//写入飞行日志开始 
					flogstr="select * from l_ac_flightlog where global_pk='"+global_pk+"'";
					HashVO[] voflylogapms=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, flogstr);
					if (voflylogapms.length>0){//有数据
						//取得老数据
						//HashVO voflylogapms1=voflylogapms[0];
						//减去老数据
						// basicAirMin-=voflylogapms1.getIntegerValue("FIAIR");
						// basicBlockMin-=voflylogapms1.getIntegerValue("FIBLOCK");
						// basicomisFc-=voflylogapms1.getIntegerValue("FIACTLG");
						
						if(omisDeploc!=""&&omisArrLoc!=""){
						flogstr="update l_ac_flightlog set FIFLBSN='"+omisID+"',COMPUTEDSTATUS=2, FIDATE=to_date(substr('"+omisFlightDate+"',1,10),'yyyy-mm-dd'),FIFLTNO='"+omisFlightNO+"',FIDEPLOC='"+omisDeploc+"',FIARVLOC='"+omisArrLoc+"',FIACTDEP=to_date('"+omisOut+"','yyyy-mm-dd hh24:mi:ss')," +
								"FIACTTAK=to_date('"+omisOff+"','yyyy-mm-dd hh24:mi:ss'), FIACTLAD=to_date('"+omisOn+"','yyyy-mm-dd hh24:mi:ss'),FIACTARV=to_date('"+omisIn+"','yyyy-mm-dd hh24:mi:ss'),FIACTLG="+omisFc+", FISTA=2,FIAIR="+AirMin+",FIBLOCK="+BlockMin+",FLAIR_SUM="+basicAirMin+",FIBLOCK_SUM="+basicBlockMin+",FIACTLG_SUM="+basicomisFc+",updatetime=sysdate where global_pk='"+global_pk+"'";
						}else{
							flogstr="delete from l_ac_flightlog WHERE global_pk='"+global_pk+"'";
						}
						updateSqlList.add(flogstr);
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
						
						//apms.commit(ApmsConst.DS_APMS);
						
					}
					else{
						//没有
						if(!omisDeploc.equals("")&&!omisArrLoc.equals("")){
						flogstr="INSERT into l_ac_flightlog(ID,MODIFYSTATUS,COMPUTEDSTATUS,FIFLBSN,FIFLBID,global_pk,acnum,BASEORGID,FIDATE,FIFLTNO,FIDEPLOC,FIARVLOC,FIACTDEP,FIACTTAK,FIACTLAD,FIACTARV,FIACTLG,FISTA,FIAIR,FIBLOCK,FLAIR_SUM,FIBLOCK_SUM,FIACTLG_SUM,UPDATETIME,USERID)" +
								" VALUES(S_l_ac_flightlog.nextval,0,2,'"+omisID+"','"+Flbid+"','"+global_pk+"','"+omisACNo+"',"+BASEORGID+",to_date(substr('"+omisFlightDate.trim()+"',1,10),'yyyy-mm-dd'),'"+omisFlightNO+"','"+omisDeploc+"','"+omisArrLoc+"',to_date('"+omisOut+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisOff+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisOn+"','yyyy-mm-dd hh24:mi:ss'),to_date('"+omisIn+"','yyyy-mm-dd hh24:mi:ss'),'"+omisFc+"',2,"+AirMin+","+BlockMin+","+basicAirMin+","+basicBlockMin+","+basicomisFc+",sysdate,0) ";
						}
						else{
						flogstr="delete from l_ac_flightlog WHERE global_pk='"+global_pk+"'";
						}
						updateSqlList.add(flogstr);
					}
					
					//apms.executeUpdateByDS(ApmsConst.DS_APMS, flogstr);
					
					//apms.commit(ApmsConst.DS_APMS);
					//写入飞行日志结束
				}
				//条件数据更新
				apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				apms.commit(ApmsConst.DS_APMS);

				if (omisFlightDate==null){
					omisFlightDate=omisStartPoint;
				}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回最后的日期
		return omisFlightDate;
		
	}
	
	//飞行日志预计部分计算；在导入预计飞行日志之后进行计算。
	public String Flightlog_Simplepredict_compute(String acnum,String startdate,String BASEORGID,int status ){
		String queryAcid="";
		String Sacid="";
		
		long basicAirMin=0;//初始
		long basicBlockMin=0;//初始
		
		long basicomisFc=0;//初始
		
		long sumAirMin=0;//总计
		long sumBlockMin=0;//总计
		
		long sumomisFc=0;//总计
		
		long basicAirMinC=0;//当天总计
		long basicBlockMinC=0;//当天总计
		long basicomisFcC=0;//当天总计
		Date LastDate=null;//最后的日期
		String LastDateS="";
		int d=0;
		int lastid=0;
		try{
			//Sacid=voacid.getStringValue("AIRCRAFTSN");
			Sacid=acnum;
			Sacid.replaceAll("-","");
			queryAcid="select * from (select * from l_ac_flightlog_Daily t where COMPUTEDSTATUS='1' and t.acnum='"+Sacid+"'  order by vdfcdate desc) where rownum<2";
			HashVO[] voacid_log=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
			//取得飞行日志计算开始日期
			logger.info("取得飞行日志计算开始日期");
			if (voacid_log.length>0){
				HashVO voacid_log1=voacid_log[0];
				
				 basicAirMin=voacid_log1.getLongValue("vdfcair");
				 basicBlockMin=voacid_log1.getLongValue("vdfcblock");
				 basicomisFc=voacid_log1.getLongValue("fiactlg_sum");
				 LastDate=voacid_log1.getDateValue("vdfcdate");
				 LastDateS=voacid_log1.getStringValue("vdfcdate");
				 lastid=voacid_log1.getIntegerValue("id");
				 sumAirMin=basicAirMin;//总计
				 sumBlockMin=basicBlockMin;//总计									
				 sumomisFc=basicomisFc;//总计
				 queryAcid="select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=2 and acnum='"+Sacid+"' and fidate>=to_date(substr('"+LastDateS+"',1,10),'yyyy-MM-dd') group by acnum,fidate,baseorgid order by acnum,fidate";
					HashVO[] flylogvo=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
					for(int i=0;i<flylogvo.length;i++){///计算日期是否有缺失的日期
						HashVO flylogvo1= flylogvo[i];
						HashVO flylogvo2=null;
						 basicAirMinC=flylogvo1.getLognValue("fiair");
						 basicBlockMinC=flylogvo1.getLognValue("fiblock");
						 basicomisFcC=flylogvo1.getLognValue("fiactlg");
						 sumAirMin+=basicAirMinC;//总计
						 sumBlockMin+=basicBlockMinC;//总计									
						 sumomisFc+=basicomisFcC;//总计
						 if(i==0){//与前一天的数据比较
							d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), LastDate));
							if(d>1){
								for(int kk1=1;kk1<=d;kk1++){//循环写入飞行空日期的飞行日志
									if (kk1<d){
										 basicAirMinC=0;
										 basicBlockMinC=0;
										 basicomisFcC=0;
									}
									else{
										 basicAirMinC=flylogvo1.getLognValue("fiair");
										 basicBlockMinC=flylogvo1.getLognValue("fiblock");
										 basicomisFcC=flylogvo1.getLognValue("fiactlg");
									}
										queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+LastDateS+"',1,10),'yyyy-mm-dd')+"+kk1+" and acnum= '"+Sacid+"'";
										HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
										
										if(voflylogsum1.length>0){
											
											queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+basicAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+basicBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+basicomisFc+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+LastDateS+"',1,10),'yyyy-mm-dd')+"+kk1+" and acnum= '"+Sacid+"'";
											logger.info("预计中间有空间1，又有记录！");
										}
										else{
											logger.info("预计中间有空间1，没有记录！");
											queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME)select S_l_ac_flightlog_Daily.nextval,baseorgid,vdfcdate+"+kk1+",acnum,"+basicAirMin+","+basicAirMinC+","+basicBlockMin+","+basicBlockMinC+","+basicomisFcC+","+basicomisFc+",2,sysdate from l_ac_flightlog_Daily where id="+lastid;
										}
										apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
										
										apms.commit(ApmsConst.DS_APMS);
									
								}
							}
						}//第一条数据与前面的数据比较end
						
						if(i<flylogvo.length-1){//取得后一条的数据
								flylogvo2=flylogvo[i+1];
						}
						else{
								flylogvo2=flylogvo[i];
						}
						//判断当前记录跟后一记录的日期差值
						d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
						if(d>1){//有空缺的日期存在
							for(int kk=0;kk<d;kk++){//循环写入飞行空日期的飞行日志
								if(kk>0){//当日期后。
									 basicAirMinC=0;
									 basicBlockMinC=0;
									 basicomisFcC=0;
								}
								else{
									 basicAirMinC=flylogvo1.getLognValue("fiair");
									 basicBlockMinC=flylogvo1.getLognValue("fiblock");
									 basicomisFcC=flylogvo1.getLognValue("fiactlg");
								}
									queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
									HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
									
									if(voflylogsum1.length>0){
										
										queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
										logger.info("中间有空间2，又有记录！");
									}
									else{
										logger.info("中间有空间2，没有记录！");
										queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd')+"+kk+",'"+Sacid+"',"+sumAirMin+","+(basicAirMinC)+","+sumBlockMin+","+(basicBlockMinC)+","+basicomisFcC+","+sumomisFc+",2,sysdate)";
									}
									apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
									
									apms.commit(ApmsConst.DS_APMS);
								
							}
						}
						else{
							
							queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
							HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
							
							if(voflylogsum1.length>0){
								
								//queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+basicAirMinC+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=1,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
								queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+sumAirMin+",FIAIR="+basicAirMinC+",VDFCBLOCK="+sumBlockMin+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+sumomisFc+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
								logger.info("有记录存在3，则修改！");
							}
							else{
								logger.info("没有记录3，则添加！");
								//queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(basicAirMinC)+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(basicBlockMinC)+","+basicomisFcC+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",1,sysdate)";
								queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+sumAirMin+","+basicAirMinC+","+sumBlockMin+","+basicBlockMinC+","+basicomisFcC+","+sumomisFc+",2,sysdate)";
							}
							apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
							
							apms.commit(ApmsConst.DS_APMS);
							
						}
						//queryAcid="update l_ac_flightlog set COMPUTEDSTATUS=1 where fidate=to_date(substr('"+flylogvo1.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') and acnum='"+flylogvo1.getStringValue("acnum")+"'";
						//apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
						//apms.commit(ApmsConst.DS_APMS);
					}
			
			}
			else{//数据不存在
				queryAcid="select baseorgid,acnum,fidate,sum(fiair) fiair,sum(fiblock) fiblock,sum(fiactlg) fiactlg,max(flair_sum) flair_sum,max(fiblock_sum) fiblock_sum,max(fiactlg_sum) fiactlg_sum from l_ac_flightlog t where computedstatus=2 and acnum='"+Sacid+"' group by acnum,fidate,baseorgid order by acnum,fidate";
				HashVO[] flylogvo=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
				for(int i=0;i<flylogvo.length;i++){///计算日期是否有缺失的日期
					HashVO flylogvo1= flylogvo[i];
					HashVO flylogvo2=null;
					if(i<flylogvo.length-1){//取得后一条的数据
						flylogvo2=flylogvo[i+1];
					}
					else{
						flylogvo2=flylogvo[i];
					}
					//判断当前记录跟后一记录的日期差值
					d=Math.abs(DateUtil.dateDiff("d", flylogvo1.getDateValue("fidate"), flylogvo2.getDateValue("fidate")));
					if(d>1){//有空缺的日期存在
						for(int kk=0;kk<d;kk++){//循环写入飞行空日期的飞行日志
							if(kk>0){//当日期后。
								 //basicAirMin-=basicAirMinC;
								 //basicBlockMin-=basicBlockMinC;
								 //basicomisFc-=basicomisFcC;
								 basicAirMinC=0;
								 basicBlockMinC=0;
								 basicomisFcC=0;
							}
							else{
								 basicAirMinC=flylogvo1.getLognValue("fiair");
								 basicBlockMinC=flylogvo1.getLognValue("fiblock");
								 basicomisFcC=flylogvo1.getLognValue("fiactlg");
							}
								queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
								HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
								
								if(voflylogsum1.length>0){
									
									queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+basicAirMinC+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+basicBlockMinC+",FIACTLG="+basicomisFcC+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd')+"+kk+" and acnum= '"+Sacid+"'";
									logger.info("中间有空间4，又有记录！");
								}
								else{
									logger.info("中间有空间4，没有记录！");
									queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd')+"+kk+",'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(basicAirMinC)+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(basicBlockMinC)+","+basicomisFcC+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",2,sysdate)";
								}
								apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
								
								apms.commit(ApmsConst.DS_APMS);
							
						}
					}
					else{
						queryAcid="select * from  l_ac_flightlog_Daily where VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
						HashVO[] voflylogsum1=apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryAcid);
						
						if(voflylogsum1.length>0){
							
							queryAcid="update l_ac_flightlog_Daily set VDFCAIR="+(flylogvo1.getLognValue("flair_sum"))+",FIAIR="+flylogvo1.getLognValue("fiair")+",VDFCBLOCK="+(flylogvo1.getLognValue("fiblock_sum"))+",FIBLOCK="+flylogvo1.getLognValue("fiblock")+",FIACTLG="+flylogvo1.getLognValue("fiactlg")+",FIACTLG_SUM="+(flylogvo1.getLognValue("FIACTLG_sum"))+",COMPUTEDSTATUS=2,UPDATETIME=sysdate where  VDFCDATE=to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',1,10),'yyyy-mm-dd') and acnum= '"+Sacid+"'";
							logger.info("有记录存在5，则修改！");
						}
						else{
							logger.info("没有记录5，则添加！");
							queryAcid="insert into l_ac_flightlog_Daily(id,BASEORGID,VDFCDATE,acnum,VDFCAIR,FIAIR,VDFCBLOCK,FIBLOCK,FIACTLG,FIACTLG_SUM,COMPUTEDSTATUS,UPDATETIME) values (S_l_ac_flightlog_Daily.nextval,"+flylogvo1.getIntegerValue("baseorgid")+",to_date(substr('"+flylogvo1.getStringValue("fidate").trim()+"',0,10),'yyyy-mm-dd'),'"+Sacid+"',"+(flylogvo1.getLognValue("flair_sum"))+","+(flylogvo1.getLognValue("fiair"))+","+(flylogvo1.getLognValue("fiblock_sum"))+","+(flylogvo1.getLognValue("fiblock"))+","+flylogvo1.getLognValue("FIACTLG")+","+(flylogvo1.getLognValue("FIACTLG_sum"))+",2,sysdate)";
						}
						apms.executeUpdateByDS(ApmsConst.DS_APMS, queryAcid);
						
						apms.commit(ApmsConst.DS_APMS);
						
					}
				}
				
			}
			logger.info("计算"+Sacid+"的预计飞行日志结束");
		}
		catch(Exception e){
			e.printStackTrace();		
			
		}
		return "0";
	}
	
}