package com.apms.bs.apu;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.MathUtil;
import com.apms.vo.ApmsVarConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * APU拆换服务类
 * @author jerry
 * @date May 22, 2013
 */
public class ApuSwapService {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO(); 
	
	public void swapApu(Map<String, Object> swapInfo) throws Exception{
		
		String updateMan = swapInfo.get("UPDATE_MAN").toString();
		String time_str = swapInfo.get("TIME").toString();
		Date swaptime = DateUtil.StringToDate(time_str, "yyyy-MM-dd HH:mm:ss");
		String reason = swapInfo.get("REASON").toString();
		String faultDesc = swapInfo.get("FAULT_DESC").toString();
		String newApusn = swapInfo.get("NEWAPUSN").toString();
		String oldApusn = swapInfo.get("OLDAPUSN").toString();
		String comments = swapInfo.get("COMMENTS").toString();
		//拆换之前做一下判断，如果拆换时间的时间早于被拆下APU的最后一条报文信息，则说明该拆换时间不对，不允许进行拆换
		String tempSql = "SELECT MAX(DATATIME) MAXDATATIME FROM L_APU_RUNLOG L WHERE L.ASN='"+oldApusn+"' AND LOGTYPE=0";
		HashVO[] tempvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, tempSql);
		if(tempvos.length > 0){
			Date lastRptDate = tempvos[0].getDateValue("MAXDATATIME");
			if( lastRptDate != null &&  swaptime.before(lastRptDate) ){
				throw new Exception("拆换时间["+DateUtil.getLongDate(swaptime)+"]早于该APU的最后一新报文时间["+DateUtil.getLongDate(lastRptDate)+"],不允许拆换！");
			}
		}
		
		//更新新、旧APU状态
		String oldFunctionStatus = ApmsVarConst.FUNCSTATUS_FAULT;//故障
		if(ApmsVarConst.SWAP_REASON_FAULT.equals(reason) ){//故障拆换
			oldFunctionStatus = ApmsVarConst.FUNCSTATUS_FAULT;
		}else if(ApmsVarConst.SWAP_REASON_ECHELON.equals(reason) ){//梯次拆换
			oldFunctionStatus = ApmsVarConst.FUNCSTATUS_USABLE;//可用
		}
		
		//记录新、旧APU快照信息
		//查询APU信息
		StringBuilder querySb = new StringBuilder();
		querySb.append("SELECT ID,APUSN,BASEORGID,AIRCRAFTID,APUMODELID,FUNCTIONSTATUS,POSITION,DATATIME,TOTALTIME,");
		querySb.append("(select ac.aircraftsn from b_aircraft ac where ac.id = a.aircraftid) ACNUM,");
		querySb.append("TOTALCYCLE,TIME_ONINSTALL,CYCLE_ONINSTALL,TIME_ONREPAIRED,CYCLE_ONREPAIRED,REPAIR_FLAG");   
		querySb.append(" FROM B_APU A WHERE A.APUSN= ANY('"+newApusn+"','"+oldApusn+"') ");
		
		StringBuilder apuLogSb = new StringBuilder();
		String removeLogId = dmo.getSequenceNextValByDS(ApmsConst.DS_APMS, "S_L_APU_SWAPLOG");
		String addLogId = dmo.getSequenceNextValByDS(ApmsConst.DS_APMS, "S_L_APU_SWAPLOG");
		
		apuLogSb.append("INSERT INTO L_APU_SWAPLOG(ID,AIRCRAFTID,APUMODELID,APUID,SWAP_DATE,SWAP_DATE_STR,");
		apuLogSb.append("SWAP_REASON,SWAP_ACTION,FAULT_DESC,");
		apuLogSb.append("TIME_ONINSTALL,CYCLE_ONINSTALL,TIME_ONREPAIRED,CYCLE_ONREPAIRED,TIME_ONREMOVE,CYCLE_ONREMOVE,REPAIR_FLAG,");
		apuLogSb.append("UPDATE_MAN,UPDATE_TIME,COMMENTS)");
		apuLogSb.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?) ");
		
		String acnum = "";
		String acid = "";//所在飞机ID
		double newTotalTime = 0;
		double newTotalCycle = 0;
		String newapuid = "0";
		double oldTotalTime = 0;
		double oldTotalCycle = 0;
		String oldapuid = "0";
		HashVO[] apus = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySb.toString());
		//拆下apu
		for(int i=0;i<apus.length;i++){
			HashVO vo = apus[i];
			String apusn = vo.getStringValue("APUSN");
			if(apusn.equals(oldApusn)){
				acnum = vo.getStringValue("ACNUM");
				acid = vo.getStringValue("AIRCRAFTID");
				oldTotalTime = vo.getDoubleValue("TOTALTIME");
				oldTotalCycle = vo.getDoubleValue("TOTALCYCLE");
				oldapuid = vo.getStringValue("ID");
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, apuLogSb.toString(),removeLogId
						,acid,vo.getStringValue("APUMODELID"),vo.getStringValue("ID"),swaptime,time_str
						,reason,ApmsVarConst.SWAP_ACTION_REMOVE,faultDesc
						,vo.getStringValue("TIME_ONINSTALL"),vo.getStringValue("CYCLE_ONINSTALL")
						,vo.getStringValue("TIME_ONREPAIRED"),vo.getStringValue("CYCLE_ONREPAIRED")
						,oldTotalTime,oldTotalCycle//TIME_ONREMOVE,CYCLE_ONREMOVE
						,vo.getStringValue("REPAIR_FLAG"),updateMan,comments);
				
				//新增一条APU大修日志
//				String repairLogSql = "INSERT INTO O_APU_OVERHAUL_LOG(ID,ASN,REPAIRDATE,APUHOUR" +
//						",REPAIRDESC,UPDATEUSER,UPDATETIME,REPAIRCOST,CURRENCY,COMMENTS)"+
//						" VALUES(S_O_APU_OVERHAUL_LOG.NEXTVAL,?,?,? ,?,?,SYSDATE,-1,2,'拆换记录生成')";
//				dmo.executeUpdateByDS(ApmsConst.DS_APMS, repairLogSql, oldApusn,swaptime
//						, oldTotalTime/60 ,reason,updateMan);
				
			}
		}
		//装上APU
		for(int i=0;i<apus.length;i++){
			HashVO vo = apus[i];
			String apusn = vo.getStringValue("APUSN");
			if(apusn.equals(newApusn)){
				newTotalTime = vo.getDoubleValue("TOTALTIME");
				newTotalCycle = vo.getDoubleValue("TOTALCYCLE");
				newapuid = vo.getStringValue("ID");
				
				//记录装上APU日志
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, apuLogSb.toString(),addLogId
						,acid,vo.getStringValue("APUMODELID"),vo.getStringValue("ID"),swaptime,time_str
						,reason,ApmsVarConst.SWAP_ACTION_INSTALL,faultDesc
						,newTotalTime,newTotalCycle//TIME_ONINSTALL,CYCLE_ONINSTALL
						,vo.getStringValue("TIME_ONREPAIRED"),vo.getStringValue("CYCLE_ONREPAIRED")
						,null,null//TIME_ONREMOVE,CYCLE_ONREMOVE
						,vo.getStringValue("REPAIR_FLAG"),updateMan,comments);
				
			}
		}
		
		//插入一条拆下APU的最后日志信息
		ApuRunLogCompute runlog = new ApuRunLogCompute();
		runlog.initApuRunLog(oldApusn,swaptime);
		
		String updateOld = "UPDATE B_APU SET AIRCRAFTID=null,FUNCTIONSTATUS=?,POSITION=?,REMOVE_TIME=? WHERE APUSN=?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateOld, oldFunctionStatus,ApmsVarConst.DEVPOSITION_STORE,swaptime,oldApusn);
		
		//设置新APU的 装上时总时间、装上时总循环,APU的数据时间改为拆换时间
		String updateNew = "UPDATE B_APU SET AIRCRAFTID=?,POSITION=?,TIME_ONINSTALL=?,CYCLE_ONINSTALL=?,INSTALL_TIME=?,DATATIME=? WHERE APUSN=?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateNew, acid,ApmsVarConst.DEVPOSITION_SWING,newTotalTime,newTotalCycle,swaptime,swaptime,newApusn);
		
		//插入一条装上APU的 初始化日志数据
		runlog.initApuRunLog(newApusn,swaptime);
		
		//记录LLP部件拆换
		swapApuLlp(updateMan, time_str, swaptime, newTotalTime, newTotalCycle, newapuid, oldTotalTime, oldTotalCycle, oldapuid);
		
		//更新数据提交
		dmo.commit(ApmsConst.DS_APMS);
		fileDealAfterSwap(swapInfo,removeLogId);
		
		//删除APU的拆换记录的缓存
		ApuSwapLogCacheService.getInstance().deleteApuTimeScopeListCache(newApusn);
		ApuSwapLogCacheService.getInstance().deleteApuTimeScopeListCache(oldApusn);
		
		//更新装上APU的报文解析状态
		String rptsql="UPDATE A_ACARS_TELEGRAPH_DFD SET ERRINT=0 WHERE RPTNO = ANY('A13','R13','A14','R14') AND ERRINT=3 AND AC_ID=?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, rptsql, acnum);
		dmo.commit(ApmsConst.DS_APMS);
	}
	
	public void swapApuLlp(String updateMan,String swapTimeStr,Date swapTime,
			double newTotalTime,double newTotalCycle,String newapuid,
			double oldTotalTime,double oldTotalCycle,String oldapuid) throws Exception{
		StringBuilder llpQeury = new StringBuilder();
		llpQeury.append("SELECT ID,APUID,APUSN,PART_AREA,PART_NAME,PART_NO,PART_SN,STATE,POSITION,");
		llpQeury.append("LIMIT_CYC,LIMIT_TIME,APUTIME_ONINSTALL,APUCYCLE_ONINSTALL,TIME_ONINSTALL,CYCLE_ONINSTALL,LIMIT_CYC,LIMIT_TIME");
		llpQeury.append(" FROM B_APU_LLPPARTS");
		llpQeury.append(" WHERE APUID=?");
		
		//记录新、旧APU下LLP快照
		StringBuilder llpLogSb = new StringBuilder();
		llpLogSb.append("INSERT INTO L_APU_LLP_SWAPLOG(");
		llpLogSb.append("ID,APUID,SWAP_DATE,SWAP_DATE_STR,SWAP_REASON,SWAP_ACTION,FAULT_DESC,");
		llpLogSb.append("PART_AREA,PART_NAME,PART_NO,PART_SN,PART_STATE,");
		llpLogSb.append("APUTIME_ONINSTALL,APUCYCLE_ONINSTALL,APUTIME_ONREMOVE,APUCYCLE_ONREMOVE,");
		llpLogSb.append("TIME_ONINSTALL,CYCLE_ONINSTALL,TIME_ONREMOVE,CYCLE_ONREMOVE,");
		llpLogSb.append("UPDATE_MAN,UPDATE_TIME,COMMENTS,LIMIT_CYC,LIMIT_TIME)");
		llpLogSb.append(" VALUES(S_L_APU_LLP_SWAPLOG.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,null,?,?)");
		llpLogSb.append("");
		
		//记录拆下APU的LLP快照
		HashVO[] curLlps = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, llpQeury.toString(),oldapuid);
		for(int i=0;i<curLlps.length;i++){
			HashVO vo = curLlps[i];
			double useTime = oldTotalTime- MathUtil.nullToZero(vo.getDoubleValue("APUTIME_ONINSTALL"));
			double useCycle = oldTotalCycle- MathUtil.nullToZero(vo.getDoubleValue("APUCYCLE_ONINSTALL"));
			double partTime =  useTime+ MathUtil.nullToZero(vo.getDoubleValue("TIME_ONINSTALL"));
			double partCycle = useCycle + MathUtil.nullToZero(vo.getDoubleValue("CYCLE_ONINSTALL"));
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, llpLogSb.toString(), oldapuid,swapTime,swapTimeStr
					,ApmsVarConst.SWAP_REASON_FOLLOW,ApmsVarConst.SWAP_ACTION_REMOVE,null
					,vo.getStringValue("PART_AREA"),vo.getStringValue("PART_NAME"),vo.getStringValue("PART_NO")
					,vo.getStringValue("PART_SN"),vo.getStringValue("STATE")
					,vo.getStringValue("APUTIME_ONINSTALL"),vo.getStringValue("APUCYCLE_ONINSTALL") //APUTIME_ONINSTALL,APUCYCLE_ONINSTALL
					,oldTotalTime,oldTotalCycle //APUTIME_ONREMOVE,APUCYCLE_ONREMOVE
					,vo.getDoubleValue("TIME_ONINSTALL"),vo.getDoubleValue("CYCLE_ONINSTALL"),partTime,partCycle,updateMan
					,vo.getDoubleValue("LIMIT_CYC"),vo.getDoubleValue("LIMIT_TIME")
					);
		}
		
		//记录新装APU的LLP快照
		HashVO[] newLlps = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, llpQeury.toString(),newapuid);
		for(int i=0;i<curLlps.length;i++){
			HashVO vo = newLlps[i];
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, llpLogSb.toString(), newapuid,swapTime,swapTimeStr
					,ApmsVarConst.SWAP_REASON_FOLLOW,ApmsVarConst.SWAP_ACTION_INSTALL,null
					,vo.getStringValue("PART_AREA"),vo.getStringValue("PART_NAME"),vo.getStringValue("PART_NO")
					,vo.getStringValue("PART_SN"),vo.getStringValue("APUTIME_ONINSTALL")
					,vo.getStringValue("APUTIME_ONINSTALL"),vo.getStringValue("APUCYCLE_ONINSTALL") //APUTIME_ONINSTALL,APUCYCLE_ONINSTALL
					,null,null //APUTIME_ONREMOVE,APUCYCLE_ONREMOVE
					,vo.getStringValue("TIME_ONINSTALL"),vo.getStringValue("CYCLE_ONINSTALL"),null,null,updateMan
					,vo.getDoubleValue("LIMIT_CYC"),vo.getDoubleValue("LIMIT_TIME")
					);
		}
		
		dmo.commit(ApmsConst.DS_APMS);
	}

	/**
	 * 转移APU的附件到拆换日志中
	 * @param swapInfo
	 * @param removeLogId
	 * @throws Exception
	 */
	public void fileDealAfterSwap(Map<String, Object> swapInfo,String removeLogId) throws Exception {
		
		String insertSql =" update b_fileinfo a "
		+" set a.origin_table='L_APU_SWAPLOG' ,a.origin_id=?,a.comments='APU拆换后该文件的关联转移至该APU的拆换日志' "
		+" where a.origin_id=(select c.id from b_apu c where c.apusn=?) ";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,insertSql,removeLogId,swapInfo.get("OLDAPUSN"));
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("拆换后附件处理成功！");
	}
	
	public void initApuInstallSwapLog(String apusn) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("insert into l_apu_swaplog(id,aircraftid,apumodelid,apuid,swap_date,swap_date_str,swap_reason,swap_action,fault_desc");
		sb.append(" ,time_oninstall,cycle_oninstall,time_onrepaired,cycle_onrepaired,repair_flag,update_man,update_time) ");
		sb.append(" select s_l_apu_swaplog.nextval,a.aircraftid,a.apumodelid,a.id,a.install_time ");
		sb.append(" ,to_char(a.install_time,'yyyy-mm-dd hh24:mi:ss'),0,1,'新装初始化' ");
		sb.append(" ,a.time_oninstall,a.cycle_oninstall,a.time_onrepaired,a.cycle_onrepaired,a.repair_flag,'sys',sysdate");
		sb.append(" from b_apu a ");
		sb.append(" where 1=1 and a.position=1 and a.apusn='" + apusn +  "' ");
		sb.append(" and a.aircraftid is not null and a.install_time is not null ");
		sb.append(" and not exists (select 1 from l_apu_swaplog t where t.apuid = a.id and t.swap_action = 1) ");
		sb.append("");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,sb.toString());
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("初始化APU["+ apusn +"]的新装日志！");
	}
	
}
