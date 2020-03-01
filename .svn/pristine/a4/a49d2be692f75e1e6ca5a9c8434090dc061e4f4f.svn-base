package com.apms.bs.intf.oldsys;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.intf.omis.Javadatediff;
import com.apms.bs.util.DateUtil;

public class HistoryDataExtractByzhl {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	
	
	public void extractAcFlyLog(String startdate,String enddate,String acnum) throws Exception{
		try{
			logger.info("抽取飞行日志分段数据开始...");
			//删除数据表里面的数据
			//String sql="delete from l_ac_flightlog";
			//dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			//dmo.commit(ApmsConst.DS_APMS);
			
			String delSql = "delete from l_ac_flightlog t where t.fidate >= to_date('"+startdate+"','yyyy-mm-dd')";
			
			//
			String qrySql = "SELECT FIFLBSN, substring(FIFLBSN, len(fiflbsn)-7,8) AS global_pk, FIFLBID, FIAPNUM AS acnum, 3 AS baseid, FIDATE, FIFLTNO, " +
					"FIDEPLOC, FIARVLOC, FIACTDEP, FIACTTAK, FIACTLAD, FIACTARV, FIACTLG, FISTA, FIAIR, 0 AS flair_sum, FIBLOCK, 0 AS fiblock_sum," +
					" 0 AS fiactlg_sum, 0 AS modifystatus, 0 AS computedstatus, UPDATETIME, USERID, 0 AS version" +
					" FROM FRFTINFO where fidate>='"+startdate+"' and fidate<'"+enddate+"'";
			if(acnum != null ){
				qrySql += " and FIAPNUM='" + acnum +"'";
				delSql += " and acnum='" + acnum +"'";
			}
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delSql);
			dmo.commit(ApmsConst.DS_APMS);
		      //+"  INNER JOIN ACARS_DFD_HEAD H ON T.MSG_NO = H.MSG_NO";
		      //+" WHERE (H.RPTNO = 'A23') AND (H.ACNUM IN ('B6032', 'B6023'))";
			
			String insertSql = "INSERT INTO l_ac_flightlog(id,fiflbsn,global_pk,fiflbid,acnum,baseorgid,fidate,fifltno," +
					"fideploc,fiarvloc,fiactdep,fiacttak,fiactlad,fiactarv,fiactlg,fista,fiair,flair_sum,fiblock,fiblock_sum," +
					"fiactlg_sum,modifystatus,computedstatus,updatetime,userid,version)"
					+" VALUES(S_l_ac_flightlog.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //25个?
			
			//批量数据导入
			dmo.executeImportByDS(ApmsConst.DS_APMSOLD, qrySql, getFromCols(25), ApmsConst.DS_APMS, insertSql, 1000);
			
			logger.info("抽取飞行日志分段数据结束...");
		}catch (Exception e) {
			logger.error("飞行日志分段数据抽取异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMSOLD);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	

	public void extractEngFlydaily(String startdate,String enddate) throws Exception{
		try{
			logger.info("抽取发动机日志数据开始...");
			
			String qrySql = "SELECT UPDAY, INFODATE, ACNO, ENGSN, 0 AS engmodelid, TSN, CSN, 0 AS time_oninstall, 0 AS cycle_oninstall, " +
					"0 AS add_cycle, 0 AS add_time, CHKFH, CHKFC, 0 AS computedstatus " +
					"FROM FRENGDALY";
				//where infodate>='"+startdate+"' and infodate<'"+enddate+"'";
			
			String insertSql = "INSERT INTO l_eng_flightlog_daily(id,upday,fidate,acnum,engsn,engmodelid,tsn,csn,time_oninstall,cycle_oninstall," +
					"add_cycle,add_time,time_onrepaired,cycle_onrepaired,computedstatus)"
					+" VALUES(S_l_eng_flightlog_daily.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //14个?
			
			//批量数据导入
			dmo.executeImportByDS(ApmsConst.DS_APMSOLD, qrySql, getFromCols(14), ApmsConst.DS_APMS, insertSql, 1000);
			logger.info("抽取发动机日志数据结束...");
		}catch (Exception e) {
			logger.error("发动机历史日志数据抽取异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMSOLD);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void extractEngFlyLog() throws Exception{
		try{
			logger.info("抽取发动机分段日志数据开始...");
			
			String insertSql="insert into l_eng_flightlog(id,fiflbsn,global_pk,acnum,add_cycle,add_time,computedstatus,engsn" +
				",engmodelid,tsn,csn,time_oninstall,modifystatus,cycle_oninstall,time_onrepaired,cycle_onrepaired,fidate,fiactdep)" +
				" VALUES(S_l_eng_flightlog.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //17个?
			
			String qrySql="select t.fiflbsn,t.global_pk,t.acnum,t.fiactlg,t.fiair,0 computedstatus,t1.engsn,t1.engmodelid,0 tsn " +
					",0 csn,t1.time_oninstall,t.modifystatus,t1.cycle_oninstall,t1.time_onrepaired,t1.cycle_onrepaired,t.fidate,t.fiactdep" +
					" from l_ac_flightlog t,l_eng_flightlog_daily t1 where t.acnum=t1.acnum and t.fidate=t1.fidate";
			dmo.executeImportByDS(ApmsConst.DS_APMS, qrySql, getFromCols(17), ApmsConst.DS_APMS, insertSql, 1000);
			
			logger.info("抽取发动机分段日志数据结束...");
		}catch (Exception e) {
			logger.error("发动机历史日志数据抽取异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMSOLD);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public void extractAcFlydaily(String startdate,String enddate,String acnum) throws Exception{
		try{
			logger.info("抽取飞行日志数据开始...");
			
			String delSql = "delete from l_ac_flightlog_daily t where t.vdfcdate >= to_date('"+startdate+"','yyyy-mm-dd')";
			
			String qrySql = "SELECT  3 AS baseorgid, VDFCAPNUM, VDFCDATE, 0 AS fiair, VDFCAIR, 0 AS fiblock,VDFCBLOCK, 0 AS fiactlg, VDFCLD AS fiactlg_sum, 0 computedstatus, getdate() AS updatetime " +
					" FROM FRFLVDCOUNT where vdfcdate>='"+startdate+"' and vdfcdate<'"+enddate+"'";
		      //+"  INNER JOIN ACARS_DFD_HEAD H ON T.MSG_NO = H.MSG_NO";
		      //+" WHERE (H.RPTNO = 'A23') AND (H.ACNUM IN ('B6032', 'B6023'))";
			if(acnum != null ){
				qrySql += " and vdfcapnum='" + acnum +"'";
				delSql += " and acnum='" + acnum +"'";
			}
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delSql);
			dmo.commit(ApmsConst.DS_APMS);
			
			String insertSql = "INSERT INTO l_ac_flightlog_daily(id,baseorgid,acnum,vdfcdate,fiair,vdfcair,fiblock,vdfcblock,fiactlg,fiactlg_sum,computedstatus,updatetime)"
					+" VALUES(S_l_ac_flightlog_daily.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?)"; //11个?
			
			//批量数据导入
			dmo.executeImportByDS(ApmsConst.DS_APMSOLD, qrySql, getFromCols(11), ApmsConst.DS_APMS, insertSql, 1000);
			
			logger.info("抽取飞行日志数据结束...");
		}catch (Exception e) {
			logger.error("飞行日志数据抽取异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMSOLD);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public String computeEngdata() throws Exception{
		try{
			HashVO flyDataVo2=null;
			long addtime=0;
			long addcycle=0;
			ArrayList<String> updateSqlList = new ArrayList<String>();
			//修改发动机相关的规定的信息
			String sql="update l_eng_flightlog_daily t " +
					"set t.engmodelid=(select t1.engmodelid from b_engine_info t1 where t1.engsn=t.engsn)" +
					",t.time_oninstall=(select t1.time_oninstall from b_engine_info t1 where t1.engsn=t.engsn)" +
					",t.cycle_oninstall=(select t1.cycle_oninstall from b_engine_info t1 where t1.engsn=t.engsn)" +
					",t.time_onrepaired=(select t1.time_onrepaired from b_engine_info t1 where t1.engsn=t.engsn)" +
					",t.cycle_onrepaired=(select t1.cycle_onrepaired from b_engine_info t1 where t1.engsn=t.engsn)" +
					" where computedstatus=0";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			dmo.commit(ApmsConst.DS_APMS);
			
			sql="select engsn from l_eng_flightlog_daily where computedstatus=0 group by engsn";
			HashVO[] voenginfo=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
			for (int i=0;i<voenginfo.length;i++){
				HashVO voenginfo1=voenginfo[i];
				sql="select * from l_eng_flightlog_daily where computedstatus=0 and engsn='"+voenginfo1.getStringValue("engsn")+"' order by engsn,fidate";
				HashVO[] voengflydata=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				updateSqlList.clear();
				for (int j=0;j<voengflydata.length;j++){
					HashVO flyDataVo = voengflydata[j];
					if (j<voengflydata.length-1){
						flyDataVo2=voengflydata[j+1];
					}else{
						flyDataVo2=voengflydata[j];
					}
					
					if (j==0){//第一条
						sql="select * from (select * from l_eng_Flightlog_Daily " +
							" where fidate<to_date(substr('"+flyDataVo.getStringValue("fidate")+"',0,10),'yyyy-mm-dd')" +
							" and  engsn='"+voenginfo1.getStringValue("engsn")+"' order by fidate desc) where rownum<2";
						HashVO[] voengflydatalast=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
						if (voengflydatalast.length>0){
							HashVO voengflydatalast1=voengflydatalast[0];
							addtime=flyDataVo.getLongValue("tsn")-voengflydatalast1.getLongValue("tsn");
							addcycle=flyDataVo.getLongValue("csn")-voengflydatalast1.getLongValue("csn");
						}
						else{
							sql="select sum(add_time) fiairsum,sum(add_cycle) fiactlgsum" +
								" from l_eng_flightlog t where fidate=to_date(substr('"+flyDataVo.getStringValue("fidate")+"',0,10),'yyyy-MM-dd')" +
								" and engsn='"+flyDataVo.getStringValue("engsn")+"'";
							HashVO[] voaclog=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
							if (voaclog.length > 0) {
								HashVO voaclog1 = voaclog[0];
								if (voaclog1.getLongValue("fiairsum") != null) {
									addtime = voaclog1.getLongValue("fiairsum");
									addcycle = voaclog1.getLongValue("fiactlgsum");
								} else {
									addtime = 0;
									addcycle = 0;
								}
							} else {
								addtime = 0;
								addcycle = 0;
							}
						}

						sql="update l_eng_flightlog_daily set add_time="+addtime+",add_cycle="+addcycle +"" +
							",computedstatus=1 where id="+flyDataVo.getIntegerValue("id");
						updateSqlList.add(sql);
						
						addtime=flyDataVo2.getLongValue("tsn")-flyDataVo.getLongValue("tsn");
						addcycle=flyDataVo2.getLongValue("csn")-flyDataVo.getLongValue("csn");
						
						sql="update l_eng_flightlog_daily set add_time="+addtime+",add_cycle="+addcycle +"" +
							",computedstatus=1 where id="+flyDataVo2.getIntegerValue("id");
						updateSqlList.add(sql);
						
						sql="update b_engine_info set tsn="+flyDataVo.getLongValue("tsn")+",csn="+flyDataVo.getLongValue("csn") +"" +
							",infodate=to_date(substr('"+flyDataVo.getStringValue("fidate")+"',0,10),'yyyy-MM-dd')" +
							" where engsn='"+flyDataVo.getStringValue("engsn")+"'";
						updateSqlList.add(sql);
					}
					else{
						addtime=flyDataVo2.getLongValue("tsn")-flyDataVo.getLongValue("tsn");
						addcycle=flyDataVo2.getLongValue("csn")-flyDataVo.getLongValue("csn");
						if(j==voengflydata.length-1){//最后一条数据
							sql="update l_eng_flightlog_daily set add_time="+addtime+",add_cycle="+addcycle +",computedstatus=1 where id="+flyDataVo.getIntegerValue("id");
							updateSqlList.add(sql);
							//修改发动机表为最新的数据表
							sql="update b_engine_info set tsn="+flyDataVo.getLongValue("tsn")+"" +
								",csn="+flyDataVo.getLongValue("csn") +"" +
								",infodate=to_date(substr('"+flyDataVo.getStringValue("fidate")+"',0,10),'yyyy-MM-dd') where engsn='"+flyDataVo.getStringValue("engsn")+"'";
							updateSqlList.add(sql);
						}
						else{
							sql="update l_eng_flightlog_daily set add_time="+addtime+",add_cycle="+addcycle +",computedstatus=1 where id="+flyDataVo2.getIntegerValue("id");
							updateSqlList.add(sql);
							//修改发动机表为最新的数据表
							sql="update b_engine_info set tsn="+flyDataVo2.getLongValue("tsn")+",csn="+flyDataVo2.getLongValue("csn") +"" +
								",infodate=to_date(substr('"+flyDataVo.getStringValue("fidate")+"',0,10),'yyyy-MM-dd')" +
								" where engsn='"+flyDataVo.getStringValue("engsn")+"'";
							updateSqlList.add(sql);
						}
					}
					
					if(j%1000 == 0){
						dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
						dmo.commit(ApmsConst.DS_APMS);
						updateSqlList.clear();
					}
				}
				dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				dmo.commit(ApmsConst.DS_APMS);
			}
			return "";
		}
		catch(Exception e){
			throw e;
		}
		
	}
	
	/**
	 * 计算飞行日志每日统计,计算每日的增量数据
	 * @return
	 * @throws Exception
	 */
	public String computeAcflydata() throws Exception{
		try{
			long addtime=0;
			long addcycle=0;
			long addblock=0;
			ArrayList<String> updateSqlList = new ArrayList<String>();
			
			String sql="select acnum from l_ac_flightlog_daily where computedstatus=0 group by acnum";
			HashVO[] vos=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
			for (int i=0;i<vos.length;i++){
				HashVO acnumVo = vos[i];
				sql="select * from l_ac_flightlog_daily " +
					" where computedstatus=0 and acnum='"+acnumVo.getStringValue("acnum")+"' order by vdfcdate";
				HashVO[] voengflydata=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				
				updateSqlList.clear();
				for (int j=0;j<voengflydata.length;j++){
					HashVO voFlyData1 = voengflydata[j];
					HashVO voFlyData2 = null;
					if (j<voengflydata.length-1){
						voFlyData2=voengflydata[j+1];
					}
					else{
						voFlyData2=voengflydata[j];
					}
					if(j==0){//第一条
						sql="select * from (select * from l_ac_Flightlog_Daily " +
							" where vdfcdate<to_date(substr('"+voFlyData1.getStringValue("vdfcdate")+"',0,10),'yyyy-mm-dd')" +
							" and acnum='"+acnumVo.getStringValue("acnum")+"' order by vdfcdate desc) where rownum<2";
						HashVO[] voengflydatalast=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
						if (voengflydatalast.length>0){
							HashVO voengflydatalast1=voengflydatalast[0];
							addtime=voFlyData1.getLongValue("vdfcair")-voengflydatalast1.getLongValue("vdfcair");
							addcycle=voFlyData1.getLongValue("fiactlg_sum")-voengflydatalast1.getLongValue("fiactlg_sum");
							addblock=voFlyData1.getLongValue("vdfcblock")-voengflydatalast1.getLongValue("vdfcblock");
						}else{
							sql="select sum(fiair) fiairsum,sum(fiblock) fiblocksum,sum(fiactlg) fiactlgsum" +
								" from l_ac_flightlog t where fidate=to_date(substr('"+voFlyData1.getStringValue("vdfcdate")+"',0,10),'yyyy-MM-dd')" +
								" and acnum='"+voFlyData1.getStringValue("acnum")+"'";
							HashVO[] voaclog=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
							if (voaclog.length>0){
								HashVO voaclog1=voaclog[0];
								if (voaclog1.getLongValue("fiairsum")!=null){
									addtime=voaclog1.getLongValue("fiairsum");
									addcycle=voaclog1.getLongValue("fiactlgsum");
									addblock=voaclog1.getLongValue("fiblocksum");
								}
								else{
									addtime=0;
									addcycle=0;
									addblock=0;
								}
							}else{
								addtime=0;
								addcycle=0;
								addblock=0;
							}
						}
						sql="update l_ac_flightlog_daily set fiair="+addtime+",fiactlg="+addcycle +",fiblock="+addblock+"" +
							",computedstatus=1 where id="+voFlyData1.getIntegerValue("id");
						updateSqlList.add(sql);
						//第二条数据
						addtime=voFlyData2.getLongValue("vdfcair")-voFlyData1.getLongValue("vdfcair");
						addcycle=voFlyData2.getLongValue("fiactlg_sum")-voFlyData1.getLongValue("fiactlg_sum");
						addblock=voFlyData2.getLongValue("vdfcblock")-voFlyData1.getLongValue("vdfcblock");
						
						sql="update l_ac_flightlog_daily set fiair="+addtime+",fiactlg="+addcycle +"" +
							",fiblock="+addblock+",computedstatus=1 where id="+voFlyData2.getIntegerValue("id");
						updateSqlList.add(sql);
					}else{
						addtime=voFlyData2.getLongValue("vdfcair")-voFlyData1.getLongValue("vdfcair");
						addcycle=voFlyData2.getLongValue("fiactlg_sum")-voFlyData1.getLongValue("fiactlg_sum");
						addblock=voFlyData2.getLongValue("vdfcblock")-voFlyData1.getLongValue("vdfcblock");
						if(j==voengflydata.length-1){//最后一条数据
							sql="update l_ac_flightlog_daily set fiair="+addtime+",fiactlg="+addcycle +"" +
								",fiblock="+addblock+",computedstatus=1 where id="+voFlyData1.getIntegerValue("id");
						}
						else{
							sql="update l_ac_flightlog_daily set fiair="+addtime+",fiactlg="+addcycle +"" +
								",fiblock="+addblock+",computedstatus=1 where id="+voFlyData2.getIntegerValue("id");
						}
						updateSqlList.add(sql);
					}
				}
				dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				dmo.commit(ApmsConst.DS_APMS);
			}
			return "";
		}
		catch(Exception e){
			throw e;
		}
		
	}
	
	/**
	 * 计算飞行日志分段数据的总计
	 * @return
	 * @throws Exception
	 */
	public String computeAcflylogdata() throws Exception{
		try{
			long basicair=0;
			long basicblock=0;
			long basiccycle=0;
			String basicdate="";
			ArrayList<String> updateSqlList = new ArrayList<String>();
			
			String sql="select t1.acnum,min(t1.fidate) mindate from l_ac_flightlog_Daily t ,l_ac_flightlog t1 " +
					" where t.acnum=t1.acnum and t.vdfcdate=t1.fidate and t1.computedstatus=0 group by t1.acnum " +
					" order by t1.acnum";
			HashVO[] vos1=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
			for (int i=0;i<vos1.length;i++){
				HashVO volog1 = vos1[i];
				sql="select * from (select * from l_ac_flightlog_Daily t " +
					" where vdfcdate<=to_date(substr('"+volog1.getStringValue("mindate")+"',0,10),'yyyy-MM-dd') " +
					" and t.acnum='"+volog1.getStringValue("acnum")+"'  order by vdfcdate desc) where rownum=1";
				
				HashVO[] voacbasic=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				if (voacbasic.length>0) {
					HashVO voacbasic1=voacbasic[0];
				
					if (volog1.getStringValue("mindate").substring(0,10).equals(voacbasic1.getStringValue("vdfcdate").substring(0,10))){
					//最小日期跟取到的日期相等
						sql="select sum(fiair) fiairsum,sum(fiblock) fiblocksum,sum(fiactlg) fiactlgsum " +
							"from l_ac_flightlog t where fidate=to_date(substr('"+volog1.getStringValue("mindate")+"',0,10),'yyyy-MM-dd')" +
							" and acnum='"+volog1.getStringValue("acnum")+"'";
						HashVO[] voengflydata=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
						if (voengflydata.length>0){
							HashVO voengflydata1=voengflydata[0];
							if (voengflydata1.getStringValue("fiairsum")!=null){
								basicair=voacbasic1.getLognValue("vdfcair")-voengflydata1.getLognValue("fiairsum");
								basicblock=voacbasic1.getLognValue("vdfcblock")-voengflydata1.getLognValue("fiblocksum");
								basiccycle=voacbasic1.getLognValue("fiactlg_sum")-voengflydata1.getLognValue("fiactlgsum");
								basicdate=voacbasic1.getStringValue("vdfcdate");
							}else{
								basicair=voacbasic1.getLognValue("vdfcair");
								basicblock=voacbasic1.getLognValue("vdfcblock");
								basiccycle=voacbasic1.getLognValue("fiactlg_sum");
								basicdate=voacbasic1.getStringValue("vdfcdate");
							}
						}
					}else{
						basicair=voacbasic1.getLognValue("vdfcair");
						basicblock=voacbasic1.getLognValue("vdfcblock");
						basiccycle=voacbasic1.getLognValue("fiactlg_sum");
						basicdate=voacbasic1.getStringValue("vdfcdate");
					}
				}
				else
				{
					basicair=0;
					basicblock=0;
					basiccycle=0;
					basicdate="2000-01-01";	
				}
				
				updateSqlList.clear();
				sql="select * from l_ac_flightlog where acnum='"+volog1.getStringValue("acnum")+"'" +
					" and fidate>=to_date(substr('"+basicdate+"',0,10),'yyyy-MM-dd') and computedstatus=0 " +
					" order by fiactdep ";
				HashVO[] vos =dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				for (int j=0;j<vos.length;j++){
					HashVO detailVo = vos[j];
					basicair += detailVo.getLongValue("fiair");
					basicblock += detailVo.getLongValue("fiblock");
					basiccycle += detailVo.getLongValue("fiactlg");
					String fidateCon = "to_date('" +DateUtil.getDateStr( detailVo.getDateValue("fidate"))+"','yyyy-MM-dd')";
					
					sql="update l_ac_flightlog set flair_sum="+basicair+",fiactlg_sum="+basiccycle +"" +
						",fiblock_sum="+basicblock+",computedstatus=1 where id="+detailVo.getLongValue("id");
					//更新每日汇总数据
					String sql2 = "update l_ac_flightlog_daily set vdfcair="+basicair+",vdfcblock="+basicblock+"" +
							",fiactlg_sum="+basiccycle+",updatetime=sysdate " +
							" where acnum='"+detailVo.getStringValue("ACNUM")+"' and vdfcdate="+fidateCon+"";
					updateSqlList.add(sql);
					updateSqlList.add(sql2);
					
					if(j%1000 == 0){
						dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
						dmo.commit(ApmsConst.DS_APMS);
						updateSqlList.clear();
					}
				}
				
				dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				dmo.commit(ApmsConst.DS_APMS);
			}
			//修改更新点
			sql="update b_apms_outlink t set update_time=(select max(fidate) from l_ac_flightlog where acnum=t.model and computedstatus=1)";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			dmo.commit(ApmsConst.DS_APMS);
			sql="update b_apms_outlink t set update_time=sysdate where update_time is null";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			dmo.commit(ApmsConst.DS_APMS);
			return "";
		}catch(Exception e){
			logger.error("计算飞行日志分段数据失败",e);
			throw e;
		}
		
	}
	/**
	 * 计算发动机分段表里面的总计数据
	 * @return
	 * @throws Exception
	 */
	public String computeEngflylogdata() throws Exception{
		try{
			long basiccycle=0;
			long basicair = 0;
			String basicdate="";
			ArrayList<String> updateSqlList = new ArrayList<String>();
			//修改发动机相关的规定的信息
			String sql="update l_eng_flightlog t " +
					" set t.engmodelid=(select t1.engmodelid from b_engine_info t1 where t1.engsn=t.engsn)" +
					",t.time_oninstall=(select t1.time_oninstall from b_engine_info t1 where t1.engsn=t.engsn)" +
					",t.cycle_oninstall=(select t1.cycle_oninstall from b_engine_info t1 where t1.engsn=t.engsn)" +
					",t.time_onrepaired=(select t1.time_onrepaired from b_engine_info t1 where t1.engsn=t.engsn)" +
					",t.cycle_onrepaired=(select t1.cycle_onrepaired from b_engine_info t1 where t1.engsn=t.engsn) " +
					" where computedstatus=0";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			dmo.commit(ApmsConst.DS_APMS);
			
			sql="select t1.engsn,min(t1.fidate) mindate " +
				" from l_eng_flightlog_Daily t ,l_eng_flightlog t1 " +
				" where t.engsn=t1.engsn and t.fidate=t1.fidate and t1.computedstatus=0 " +
				" group by t1.engsn order by t1.engsn";
			////////////////////////还没有结束
			HashVO[] voenginfo=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
			for (int i=0;i<voenginfo.length;i++){
				HashVO voenginfo1=voenginfo[i];
				sql="select * from (select * from l_eng_flightlog_Daily t" +
					" where fidate<=to_date(substr('"+voenginfo1.getStringValue("mindate")+"',0,10),'yyyy-MM-dd')" +
					" and t.engsn='"+voenginfo1.getStringValue("engsn")+"'  order by fidate desc) where rownum<2";
				HashVO[] voacbasic=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				if (voacbasic.length>0) {
					HashVO voacbasic1=voacbasic[0];
					if (voenginfo1.getStringValue("mindate").substring(0,10).equals(voacbasic1.getStringValue("fidate").substring(0,10))){
						//最小日期跟取到的日期相等
						sql="select sum(add_time) fiairsum,sum(add_cycle) fiactlgsum from l_eng_flightlog t where fidate=to_date(substr('"+voenginfo1.getStringValue("mindate")+"',0,10),'yyyy-MM-dd') and engsn='"+voenginfo1.getStringValue("engsn")+"'";
						HashVO[] voengflydata=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
						if (voengflydata.length>0){
						HashVO voengflydata1=voengflydata[0];
							if (voengflydata1.getStringValue("fiairsum")!=null){
							basicair=voacbasic1.getLognValue("tsn")-voengflydata1.getLognValue("fiairsum");
							
							basiccycle=voacbasic1.getLognValue("csn")-voengflydata1.getLognValue("fiactlgsum");
							basicdate=voacbasic1.getStringValue("fidate");
							}else{
								basicair=voacbasic1.getLognValue("tsn");
								
								basiccycle=voacbasic1.getLognValue("csn");
								basicdate=voacbasic1.getStringValue("fidate");
							}
						}
					}else{
						basicair=voacbasic1.getLognValue("tsn");
						basiccycle=voacbasic1.getLognValue("csn");
						basicdate=voacbasic1.getStringValue("fidate");
					}
				}else{
					basicair=0;
					basiccycle=0;
					basicdate="2000-01-01";	
				}
				
				updateSqlList.clear();
				sql="select * from l_eng_flightlog where engsn='"+voenginfo1.getStringValue("engsn")+"'" +
					" and fidate>=to_date(substr('"+basicdate+"',0,10),'yyyy-MM-dd') " +
					" and computedstatus=0 order by fiactdep ";
				HashVO[] voacflylogdata=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				for (int j=0;j<voacflylogdata.length;j++){
					HashVO voacflylogdata1=voacflylogdata[j];
					basicair += voacflylogdata1.getLongValue("add_time");					
					basiccycle += voacflylogdata1.getLongValue("add_cycle");
					
					sql="update l_eng_flightlog set computedstatus=1, tsn="+basicair+",csn="+basiccycle +"" +
						" where id="+voacflylogdata1.getLongValue("id");
					updateSqlList.add(sql);
					
					if(j%1000 == 0){
						dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
						dmo.commit(ApmsConst.DS_APMS);
						updateSqlList.clear();
					}
				}
				dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				dmo.commit(ApmsConst.DS_APMS);
			}
			return "";
		}
		catch(Exception e){
			logger.error("发动机航段日志计算失败",e);
			throw e;
		}
		
	}
	
	//计算航班计划里面的数据
	public String computeAircraftflyplan() throws Exception{
		try{
			long addtime=0;
			long addcycle=0;
			long addblock=0;
			long basicair=0;
			long basicblock=0;
			long basiccycle=0;
			
			ArrayList<String> updateSqlList = new ArrayList<String>();
			
			String sql="select min(flt_date) mindate,ac_id,min(t2.vdfcair) minair,min(t2.vdfcblock) minblock,min(t2.fiactlg_sum) mincycle from i_flt_sch_list t ,b_aircraft t1,l_ac_flightlog_daily t2 where t.ac_id=t1.aircraftsn and t.flt_date=t2.vdfcdate and t.ac_id=t2.acnum and t.ac_id is not null group by t.ac_id order by t.ac_id";
			HashVO[] voenginfo=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
			for (int i=0;i<voenginfo.length;i++){
				HashVO voenginfo1=voenginfo[i];
				basicair=voenginfo1.getLognValue("minair");
				basicblock=voenginfo1.getLognValue("minblock");
				basiccycle=voenginfo1.getLognValue("mincycle");
				
				sql="select  sum(fltair) fiairsum,sum(fltgrd) fiblocksum,sum(flt_ld) fiactlgsum from i_flt_sch_list t where flt_date=to_date(substr('"+voenginfo1.getStringValue("mindate")+"',0,10),'yyyy-MM-dd') and ac_id='"+voenginfo1.getStringValue("ac_id")+"'";
				HashVO[] voengflydata=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				if(voengflydata.length>0){
					HashVO voengflydata3=voengflydata[0];
					if (voengflydata3.getStringValue("fiairsum")!=null){
						basicair-=voengflydata3.getLognValue("fiairsum");
						basicblock-=voengflydata3.getLognValue("fiblocksum");
						basiccycle-=voengflydata3.getLognValue("fiactlgsum");
					}
				}
				updateSqlList.clear();
				sql="select * from i_flt_sch_list where ac_id='"+voenginfo1.getStringValue("ac_id")+"' and flt_date>=to_date(substr('"+voenginfo1.getStringValue("mindate")+"',0,10),'yyyy-MM-dd') order by ac_id,flt_date,std ";
				HashVO[] voacflylogdata=dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				int fltair=0;
				for (int j=0;j<voacflylogdata.length;j++){
					
					HashVO voacflylogdata1=voacflylogdata[j];
					fltair=Math.abs(flt_FLTAIR(voacflylogdata1,j));
					addtime=fltair;
					addblock=fltair+10;
					basicair+=fltair;//voacflylogdata1.getLongValue("fltair");
					basicblock+=fltair+10;//voacflylogdata1.getLongValue("flt_ld");
					if(voacflylogdata1.getStringValue("dep_apt").equals(voacflylogdata1.getStringValue("arr_apt"))){
					basiccycle+=30;
					addcycle=30;
					}
					else{
					basiccycle+=1;//voacflylogdata1.getLongValue("fltgrd");
					addcycle=1;
					}
					sql="update i_flt_sch_list set fltair="+addtime+",flt_ld="+addblock+",fltgrd="+addcycle+",airsum="+basicair+",grdsum="+basiccycle +",ldsum="+basicblock+" where fltrecid="+voacflylogdata1.getLongValue("fltrecid");
					updateSqlList.add(sql);
				}
				dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				dmo.commit(ApmsConst.DS_APMS);
			}
			//修改更新点
			//sql="update b_apms_outlink t set update_time=(select max(vdfcdate) from l_ac_flightlog_daily where acnum=t.model and computedstatus=1)";
			//dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			///dmo.commit(ApmsConst.DS_APMS);
			return "";
		}
		catch(Exception e){
			throw e;
		}
		
	}
	
	/**
	 * 从老AMS系统中抽取航班计划日志数据
	 * @param startdate
	 * @param enddate
	 * @throws Exception
	 */
	@Deprecated
	private void extracthistoryFlightlog(String startdate,String enddate) throws Exception{
		try{
			
			logger.info("抽取航班日志数据开始...");
			//删除数据表里面的数据
			String sql="delete from i_flt_sch_list where flt_date>=to_date('"+startdate+"','yyyy-MM-dd') and flt_date<=to_date('"+enddate+"','yyyy-MM-dd')";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			dmo.commit(ApmsConst.DS_APMS);
			
			//
			String qrySql = "select  FLT_PK, GLOBAL_PK, FLT_DATE, IATA_C, CO_SEQ, FLT_ID, FLT_SEQ, FLT_TASK, con_flt_id, AC_ID, AC_TYPE, FLT_TYPE, DEP_APT, std, sta, ARR_APT, etd, ETD_SRC, eta, " +
					"ETA_SRC,  off_time, OFF_SRC,on_time, ON_SRC, out_time, OUT_SRC,  in_time, IN_SRC,  open_time,  close_time, AC_STOP, AC_STOP_MOVE, VIP, VIP_NAME, ALT_APT1, ALT_APT2, DELAY_CODE," +
					"  delay_time, CANCEL_FLAG, MEMO, PLAN_FLAG, CREATE_SRC, UPDATE_SRC, STA_SRC, FLT_TASK_SRC, AC_SRC, AC_STOP_SRC, DELAY_SRC, ALT_APT_SRC, CANCEL_SRC, AC_TYPE_OLD, AC_TYPE_MARKET," +
					" AC_STOP_ARR, AC_STOP_ARR_MOVE, OFF_DELAY_STANDARD, ON_DELAY_STANDARD, FIRST_FLAG, AC_OWNER, CANCEL_TYPE, CANCEL_REASON,  cancel_time, DELAY_FLAG_RLS, DELAY_CODE_RLS, FLT_ATT, " +
					"0 AS FLT_CHANGE_FLAG, 0 AS FLTAIR, 0 AS FLTGRD, 0 AS FLT_LD, 0 AS AIRSUM, 0 AS GRDSUM, 0 AS LDSUM, updatetime" +
					" FROM FT_SCH_LIST where flt_date>='"+startdate+"' and flt_date<='"+enddate+"'";
			
			
		    String insertSql = "INSERT INTO i_flt_sch_list(FLTRECID,FLT_PK,GLOBAL_PK,FLT_DATE,IATA_C,CO_SEQ,FLT_ID,FLT_SEQ,FLT_TASK,CON_FLT_ID,AC_ID,AC_TYPE,FLT_TYPE,DEP_APT,STD,STA,ARR_APT,ETD,ETD_SRC,ETA," +
					"ETA_SRC,OFF_TIME,OFF_SRC,ON_TIME,ON_SRC,OUT_TIME,OUT_SRC,IN_TIME,IN_SRC,OPEN_TIME,CLOSE_TIME,AC_STOP,AC_STOP_MOVE,VIP,VIP_NAME,ALT_APT1,ALT_APT2,DELAY_CODE," +
					"DELAY_TIME,CANCEL_FLAG,MEMO,PLAN_FLAG,CREATE_SRC,UPDATE_SRC,STA_SRC,FLT_TASK_SRC,AC_SRC,AC_STOP_SRC,DELAY_SRC,ALT_APT_SRC,CANCEL_SRC,AC_TYPE_OLD,AC_TYPE_MARKET," +
					"AC_STOP_ARR,AC_STOP_ARR_MOVE,OFF_DELAY_STANDARD,ON_DELAY_STANDARD,FIRST_FLAG,AC_OWNER,CANCEL_TYPE,CANCEL_REASON,CANCEL_TIME,DELAY_FLAG_RLS,DELAY_CODE_RLS,FLT_ATT," +
					"FLT_CHANGE_FLAG,FLTAIR,FLTGRD,FLT_LD,AIRSUM,GRDSUM,LDSUM,UPDATETIME)"
					+" VALUES(S_i_flt_sch_list.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //72个?
			
			//批量数据导入
			dmo.executeImportByDS(ApmsConst.DS_APMSOLD, qrySql, getFromCols(72), ApmsConst.DS_APMS, insertSql, 1000);
			
			logger.info("抽取航班日志结束...");
			}catch (Exception e) {
				logger.error("航班数据抽取异常！", e);
				throw e;
			}finally{
				dmo.releaseContext(ApmsConst.DS_APMSOLD);
				dmo.releaseContext(ApmsConst.DS_APMS);
			}
	}
	
	@Deprecated
	private void compenginfo() throws Exception{
		try{
			logger.info("发动机信息数据开始...");

			String qrySql = "select ENGSN, 0 as aircraftid, ACNO AS acnum, 3 AS baseorgid, 0 AS engmodelid," +
					" ENGLOC AS engwing_loc, 1 AS engloc, DATESTATE AS status, INFODATE, TSN, CSN, UPDAY," +
					" DOWNDY, 0 AS time_oninstall, 0 AS cycle_oninstall, CHKFH AS time_onrepaired, " +
					" CHKFC AS cycle_onrepaired, DATESTATE AS datastate, CHKSTATE,  0 AS llp_limit," +
					" 0 AS llp_compnent, 0 AS cyc_plan, 0 AS fh_plan, USERID AS update_man, UPDATEDY AS update_time" +
					" FROM FRENGDATA ORDER BY ACNO";
		      //+"  INNER JOIN ACARS_DFD_HEAD H ON T.MSG_NO = H.MSG_NO";
		      //+" WHERE (H.RPTNO = 'A23') AND (H.ACNUM IN ('B6032', 'B6023'))";
			
			String insertSql = "INSERT INTO b_engine_info(id,engsn,aircraftid,acnum,baseorgid,engmodelid,engwing_loc,engloc,status,infodate,tsn,csn,upday,downdy,time_oninstall,cycle_oninstall,time_onrepaired,cycle_onrepaired,datastate,chkstate,llp_limit,llp_compnent,cyc_plan,fh_plan,update_man,update_time)"
					+" VALUES(S_b_engine_info.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //27个?
			
			dmo.executeImportByDS(ApmsConst.DS_APMSOLD, qrySql, getFromCols(25), ApmsConst.DS_APMS202, insertSql, 1000);
			
			
			logger.info("抽取发动机信息结束...");
		}catch (Exception e) {
			logger.error("飞行日志数据抽取异常！", e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMSOLD);
			dmo.releaseContext(ApmsConst.DS_APMS);
			dmo.releaseContext(ApmsConst.DS_APMS202);
		}
	}
	public int flt_FLTAIR(HashVO vo,int i){
		int FLTAIR=0;
		Date date1=null;
		Date date2=null;
		if ( vo.getStringValue("off_time")==null|| vo.getStringValue("off_time").compareTo("1900-01-01")==0){ //  '没有离港时间时
			
				if (vo.getDateValue("etd")!=null){
					date1=vo.getTimeStampValue("etd");
				}
				else{
					date1=vo.getTimeStampValue("std");
				}
			}else{
				date1 = vo.getTimeStampValue("off_time");
			}

			if ( vo.getStringValue("on_time")==null|| vo.getStringValue("on_time").compareTo("1900-01-01")==0){ // '没有到达时间时
				if (vo.getDateValue("eta")!=null){
					date2=vo.getTimeStampValue("eta");
				}
				else{
					date2=vo.getTimeStampValue("sta");
				}
			}else{
				date2 = vo.getTimeStampValue("on_time");
			}

		Javadatediff datediff=new Javadatediff();
		
		//if (vo.getDateValue("etd")==null){//没有离港时间
		//	if (vo.getDateValue("on_time")==null){//没有落地时间
		FLTAIR=datediff.dateDiff("minute", date1,date2);
		//	}
		//	else{
				
		//	}
		//}
		//else{//有离港时间
			
		//}
		return FLTAIR;
		
	}
	
	public int[] getFromCols(int length){
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i+1;
		
		return fromcols;
	}
}
