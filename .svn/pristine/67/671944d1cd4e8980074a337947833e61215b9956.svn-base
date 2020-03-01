package com.apms.bs.intf.foc;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaServerEnvironment;

import com.apms.ApmsConst;
import com.apms.bs.sms.ShortMassageHwService;

/**
 * 杭州机位信息在foc中维护，从foc中同步机位信息,已写死只处理杭州机场的航班数据
 * @author jerry
 * @date Jan 19, 2016
 */
public class FocAcstopSynService {
	private static int failNum = 0; //接口数据同步失败次数计数
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO(false);
	private static StringBuilder arrDiffSql;
	private static StringBuilder depDiffSql;
	private static StringBuilder inChgLogSql;
	private static StringBuilder cdmDiffSql;
	
	private String focFltTable = "foc.v_flight_dynamic";
	
	static{
		//到达航班变更
		arrDiffSql = new StringBuilder();
		arrDiffSql.append("SELECT S.FLT_PK,S.FLT_DATE,S.FLIGHTNO,A.ACSTOP,S.AC_STOP_ARR,S.ACNUM");
		arrDiffSql.append(" FROM F_FLIGHT_SCHEDULE S,FOC_FLIGHTACSTOP A ");
		arrDiffSql.append("  WHERE S.FLT_DATE >= TRUNC(SYSDATE-1) AND S.FLT_DATE<=TRUNC(SYSDATE+1)");
		arrDiffSql.append(" AND S.ARR_APT='HGH' AND A.FLIGHT_DATE=S.FLT_DATE");
		arrDiffSql.append(" AND A.FLIGHT_NO=S.FLIGHTNO AND A.DEP_APT=S.DEP_APT AND A.ARR_APT=S.ARR_APT");
		arrDiffSql.append(" AND A.ACSTOP IS NOT NULL AND (S.AC_STOP_ARR IS NULL OR A.ACSTOP != S.AC_STOP_ARR)");
		
		//离港航班的变更判断
		depDiffSql = new StringBuilder();
		depDiffSql.append("SELECT S.FLT_PK,S.FLT_DATE,S.FLIGHTNO,A.ACSTOP,S.AC_STOP,S.ACNUM");
		depDiffSql.append(" FROM F_FLIGHT_SCHEDULE S,FOC_FLIGHTACSTOP A ");
		depDiffSql.append(" WHERE S.FLT_DATE >= TRUNC(SYSDATE-1) AND S.FLT_DATE<=TRUNC(SYSDATE+1)");
		depDiffSql.append("  AND S.DEP_APT='HGH' AND A.FLIGHT_DATE=S.FLT_DATE");
		depDiffSql.append(" AND A.FLIGHT_NO=S.FLIGHTNO AND A.DEP_APT=S.DEP_APT AND A.ARR_APT=S.ARR_APT");
		depDiffSql.append(" AND A.ACSTOP IS NOT NULL AND (S.AC_STOP IS NULL OR A.ACSTOP != S.AC_STOP)");
		
		//CDM时间的变更判断
		cdmDiffSql = new StringBuilder();
		cdmDiffSql.append("SELECT S.FLT_PK,S.FLT_DATE,S.FLIGHTNO,A.CDM_OUT CDM_NEW,S.CDM_OUT CMD_OLD,S.ACNUM");
		cdmDiffSql.append(" FROM F_FLIGHT_SCHEDULE S,FOC_FLIGHTACSTOP A ");
		cdmDiffSql.append(" WHERE S.FLT_DATE >= TRUNC(SYSDATE-1) AND S.FLT_DATE<=TRUNC(SYSDATE+1)");
		cdmDiffSql.append("  AND S.DEP_APT='HGH' AND A.FLIGHT_DATE=S.FLT_DATE");
		cdmDiffSql.append(" AND A.FLIGHT_NO=S.FLIGHTNO AND A.DEP_APT=S.DEP_APT AND A.ARR_APT=S.ARR_APT");
		cdmDiffSql.append(" AND A.CDM_OUT IS NOT NULL AND (S.CDM_OUT IS NULL OR A.CDM_OUT != S.CDM_OUT)");
		
		//变动日志
		inChgLogSql = new StringBuilder();
		inChgLogSql.append("INSERT INTO F_FLIGHT_CHANGELOG(ID");
		inChgLogSql.append(" ,FLIGHTID ,ACNUM ,FLIGHTNO ,FLIGHTDATE ,CHANGETYPE ");
		inChgLogSql.append(" ,CHANGECONTENT ,NEEDBOARDCAST ,ISBOARDCAST ,CHANGETIME");		 
		inChgLogSql.append(" ,UPDATETIME ,UPDATEUSER ) ");
		inChgLogSql.append(" VALUES(S_F_FLIGHT_CHANGELOG.NEXTVAL,?,?,?,?,?,?,?,0,SYSDATE,SYSDATE,'SYSTEM')");
		inChgLogSql.append("");
	}
	
	
	public int synAcStopFromFoc() throws Exception{
		int num = 0;
		try{
			//0 删除临时数据
			String delSql = "DELETE FROM FOC_FLIGHTACSTOP WHERE 1=1";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, delSql);
			dmo.commit(ApmsConst.DS_APMS);
			
			String qrySql = "SELECT FLIGHT_DATE,FLIGHT_NO,DEPARTURE_AIRPORT,ARRIVAL_AIRPORT,TRIM(BAY) ACSTOP,CDM_OUT"
						+" FROM "+focFltTable+" WHERE FLIGHT_DATE>=TRUNC(SYSDATE-1)";
			String insertSql = "INSERT INTO FOC_FLIGHTACSTOP(FLIGHT_DATE,FLIGHT_NO,DEP_APT,ARR_APT,ACSTOP,CDM_OUT,UPDATETIME)"
				+" VALUES(?,?,?,?,?,?,SYSDATE)";
			
			//1，全量同步数据(300条左右)
			dmo.executeImportByDS(ApmsConst.DS_FOC, qrySql, getFromCols(6)
								 ,ApmsConst.DS_APMS, insertSql.toString() , 800);
			dmo.commit(ApmsConst.DS_APMS);
			
			boolean needNotify = true;
			int chgType =0;
			String chgContent = "";
			
			//2,比较acstop是否有变更,到港机位
			String uptArrStop = "UPDATE F_FLIGHT_SCHEDULE T SET T.AC_STOP_ARR=? WHERE T.FLT_PK=?";
			HashVO[] vosArr = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, arrDiffSql.toString() );
			for(int i=0;i<vosArr.length; i++){
				String fltpk = vosArr[i].getStringValue("FLT_PK");
				String acstop = vosArr[i].getStringValue("ACSTOP");
				String oldstop = vosArr[i].getStringValue("AC_STOP_ARR");
				chgType = 45;
				if(oldstop == null){
					chgContent = "进港机位:[空]->["+acstop+"]";
				}else{
					chgContent = "进港机位:" + "["+oldstop+"]->["+acstop+"]";
				}
				//3，更新机位信息
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptArrStop, acstop,fltpk);
				//更新变动的航班记录，并记录到变动记录表中
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, inChgLogSql.toString(),fltpk
						,vosArr[i].getStringValue("ACNUM"),vosArr[i].getStringValue("FLIGHTNO")
						,vosArr[i].getDateValue("FLT_DATE"), chgType, chgContent ,needNotify);
				
				num++;
			}
			dmo.commit(ApmsConst.DS_APMS);
			
			//CDM_OUT变更
			String uptCdm = "UPDATE F_FLIGHT_SCHEDULE T SET T.CDM_OUT=? WHERE T.FLT_PK=?";
			HashVO[] vosCdm = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, cdmDiffSql.toString() );
			for(int i=0;i<vosCdm.length; i++){
				String fltpk = vosCdm[i].getStringValue("FLT_PK");
				String cdm_new = vosCdm[i].getStringValue("CDM_NEW");
				String cmd_old = vosCdm[i].getStringValue("CDM_OLD");
				Date cdm = vosCdm[i].getDateValue("CDM_NEW");
				chgType = 52;
				if(cmd_old == null){
					chgContent = "CDM时间:[空]->["+cdm_new+"]";
				}else{
					chgContent = "CDM时间:" + "["+cmd_old+"]->["+cdm_new+"]";
				}
				
				//3，更新机位信息
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptCdm, cdm,fltpk);
				
				//更新变动的航班记录，并记录到变动记录表中
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, inChgLogSql.toString(),fltpk
						,vosCdm[i].getStringValue("ACNUM"),vosCdm[i].getStringValue("FLIGHTNO")
						,vosCdm[i].getDateValue("FLT_DATE"), chgType, chgContent ,needNotify);
				
				num++;
			}
			dmo.commit(ApmsConst.DS_APMS);
			
			//离港机位变更
			String uptDepStop = "UPDATE F_FLIGHT_SCHEDULE T SET T.AC_STOP=? WHERE T.FLT_PK=?";
			HashVO[] vosDep = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, depDiffSql.toString() );
			for(int i=0;i<vosDep.length; i++){
				String fltpk = vosDep[i].getStringValue("FLT_PK");
				String acstop = vosDep[i].getStringValue("ACSTOP");
				String oldstop = vosDep[i].getStringValue("AC_STOP");
				chgType = 40;
				if(oldstop == null){
					chgContent = "离港机位:[空]->["+acstop+"]";
				}else{
					chgContent = "离港机位:" + "["+oldstop+"]->["+acstop+"]";
				}
				
				//3，更新机位信息
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptDepStop, acstop,fltpk);
				
				//更新变动的航班记录，并记录到变动记录表中
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, inChgLogSql.toString(),fltpk
						,vosDep[i].getStringValue("ACNUM"),vosDep[i].getStringValue("FLIGHTNO")
						,vosDep[i].getDateValue("FLT_DATE"), chgType, chgContent ,needNotify);
				
				num++;
			}
			dmo.commit(ApmsConst.DS_APMS);
			
			failNum = 0;
			logger.debug("foc机位信息同步成功!,更新信息["+num+"]条");
			return num;
		}catch (Exception e) {
			logger.error("FOC航班机位数据同步异常！", e);
			
			failNum++;
			String pname = (String)NovaServerEnvironment.getInstance().get("PROJECT_NAME");
			String msg = pname +"机位数据同步连续失败"+failNum+"次,请查看任务日志";
			
			if( failNum >= 3){
				//数据任务失败，及时发短信告知维护人员
				ShortMassageHwService.getInstance().send( "18916752189", msg);//zzy
				ShortMassageHwService.getInstance().send( "18658176006", msg);//huanglei
			}
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_FOC);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public int[] getFromCols(int length){
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i+1;
		
		return fromcols;
	}

}
