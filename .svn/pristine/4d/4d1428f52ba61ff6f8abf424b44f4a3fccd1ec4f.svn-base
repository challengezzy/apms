package com.apms.bs.remind;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 提醒单处理服务类
 * @author jerry
 * @date May 7, 2014
 */
public class RemindManageService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	public int generateRemindIns() throws Exception{
		int numAdd = 0;
		String insert = "INSERT INTO W_DEFECTREMIND_INS(ID,FLIGHTID,REMINDID, FLIGHTDATE,STATUS,UPDATETIME,UPDATEUSER)";
		insert += "VALUES(S_W_DEFECTREMIND_INS.NEXTVAL, ?, ?, ?, 0,SYSDATE,'system')";
		
		StringBuilder qrySql = new StringBuilder();
		qrySql.append("SELECT T.FLT_PK FLIGHTID,R.ID REMINDID,T.FLT_DATE,T.FLIGHTNO,T.ACNUM,T.CTA,R.STARTTIME,R.ENDTIME");
		qrySql.append(",R.LOOPINTERVAL,R.LOOPTYPE"); //循环形式
		qrySql.append(",R.WEEKDAY_0,R.WEEKDAY_1,R.WEEKDAY_2,R.WEEKDAY_3,R.WEEKDAY_4,R.WEEKDAY_5,R.WEEKDAY_6"); //周模式
		qrySql.append(" FROM F_FLIGHT_SCHEDULE T,W_DEFECTREMIND R ");
		qrySql.append(" WHERE (R.ACNUM=T.ACNUM OR R.FLT_NO=T.FLIGHTNO) ");//飞机或航班号关联
		qrySql.append(" AND T.CTA>SYSDATE AND T.CTA<SYSDATE+2 ");//查询24小时内
		qrySql.append(" AND R.STATUS=0 AND R.STARTTIME<=T.CTA AND R.ENDTIME>=T.CTA"); //提醒单为有效状态
		qrySql.append(" AND NOT EXISTS (SELECT 1 FROM W_DEFECTREMIND_INS RS WHERE RS.FLIGHTID=T.FLT_PK AND RS.REMINDID=R.ID)");//不存在提醒实例
		
		qrySql.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows( ApmsConst.DS_APMS, qrySql.toString());
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			boolean isCreate = false;
			Date fltdate = vo.getDateValue("FLT_DATE");
			int loopInterval = new Integer(vo.getStringValue("LOOPINTERVAL"));
			String loopType = vo.getStringValue("LOOPTYPE");
			
			String flightid = vo.getStringValue("FLIGHTID");
			String remindid = vo.getStringValue("REMINDID");
			
			if("0".equals(loopType)){ //按天生成计划
				Date start = vo.getDateValue("STARTTIME");
				int dayDiff = DateUtil.dayDiff(fltdate, start);
				int mod = dayDiff%(loopInterval+1); //根据余数判断是否是提醒日
				if(mod == 0){
					isCreate = true;
				}
			}else if("1".equals(loopType)){ //按周模式生成计划
				//判断当天所属的星期是否要生成计划数据
				int dayOfWeek = DateUtil.getWeekDay(fltdate);
				if(dayOfWeek == 0 && "1".equals(vo.getStringValue("weekday_0"))){
					isCreate = true;
				}else if(dayOfWeek == 1 && "1".equals(vo.getStringValue("weekday_1"))){
					isCreate = true;
				}else if(dayOfWeek == 2 && "1".equals(vo.getStringValue("weekday_2"))){
					isCreate = true;
				}else if(dayOfWeek == 3 && "1".equals(vo.getStringValue("weekday_3"))){
					isCreate = true;
				}else if(dayOfWeek == 4 && "1".equals(vo.getStringValue("weekday_4"))){
					isCreate = true;
				}else if(dayOfWeek == 5 && "1".equals(vo.getStringValue("weekday_5"))){
					isCreate = true;
				}else if(dayOfWeek == 6 && "1".equals(vo.getStringValue("weekday_6"))){
					isCreate = true;
				}
			}else if("2".equals(loopType)){
				isCreate = true;
			}
			
			if(isCreate){
				numAdd++;
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insert,flightid,remindid, fltdate);
			}
			//单次循环结束
		}
		if(numAdd > 0){
			dmo.commit(ApmsConst.DS_APMS);
			logger.info("本次生成提醒信息["+numAdd+"]条!");
		}else{
			logger.debug("本次未生成新的提醒信息!");
		}
		
		return numAdd;
	}
	
	/**
	 * 在提醒项更新后创建提醒单实例
	 * @return
	 * @throws Exception
	 */
	public int generateRemindInsById(String remindId) throws Exception{
		int numAdd = 0;
		String insert = "INSERT INTO W_DEFECTREMIND_INS(ID,FLIGHTID,REMINDID, FLIGHTDATE,STATUS,UPDATETIME,UPDATEUSER)";
		insert += "VALUES(S_W_DEFECTREMIND_INS.NEXTVAL, ?, ?, ?, 0,SYSDATE,'system')";
		
		StringBuilder qrySql = new StringBuilder();
		qrySql.append("SELECT T.FLT_PK FLIGHTID,R.ID REMINDID,T.FLT_DATE,T.FLIGHTNO,T.ACNUM,T.CTA,R.STARTTIME,R.ENDTIME");
		qrySql.append(",R.LOOPINTERVAL,R.LOOPTYPE"); //循环形式
		qrySql.append(",R.WEEKDAY_0,R.WEEKDAY_1,R.WEEKDAY_2,R.WEEKDAY_3,R.WEEKDAY_4,R.WEEKDAY_5,R.WEEKDAY_6"); //周模式
		qrySql.append(" FROM F_FLIGHT_SCHEDULE T,W_DEFECTREMIND R ");
		qrySql.append(" WHERE (R.ACNUM=T.ACNUM OR R.FLT_NO=T.FLIGHTNO) ");//飞机或航班号关联
		qrySql.append(" AND T.CTA>SYSDATE AND T.CTA<SYSDATE+1 ");//查询24小时内
		qrySql.append(" AND R.STATUS=0 AND R.STARTTIME<=T.CTA AND R.ENDTIME>=T.CTA"); //提醒单为有效状态
		qrySql.append(" AND R.ID = " + remindId );
		qrySql.append(" AND NOT EXISTS (SELECT 1 FROM W_DEFECTREMIND_INS RS WHERE RS.FLIGHTID=T.FLT_PK AND RS.REMINDID=R.ID)");//不存在提醒实例
		
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows( ApmsConst.DS_APMS, qrySql.toString());
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			boolean isCreate = true; //不用判断，直接创建
			Date fltdate = vo.getDateValue("FLT_DATE");
			String flightid = vo.getStringValue("FLIGHTID");
			String remindid = vo.getStringValue("REMINDID");
			
			if(isCreate){
				numAdd++;
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insert,flightid,remindid, fltdate);
			}
			//单次循环结束
		}
		if(numAdd > 0){
			dmo.commit(ApmsConst.DS_APMS);
			logger.info("本次生成提醒信息["+numAdd+"]条!");
		}else{
			logger.debug("原提醒信息已存在，不需要重复生成!");
		}
		
		return numAdd;
	}
	
	/**
	 * 更新提醒信息实例状态为已关闭
	 * @param user
	 * @param tipObj
	 * @throws Exception
	 */
	public void flightRemindConfirm(String user, Map<String, Object> tipObj) throws Exception{
		String flightid =  (String)tipObj.get("FLIGHTID");
		//String remindid = (String)tipObj.get("REMINDID");
		String id = (String)tipObj.get("ID");
		String remindid = (String)tipObj.get("REMINDID");
		String dealer = (String)tipObj.get("DEALER");
		String comments = (String)tipObj.get("COMMENTS");
		String dealtimeStr = (String)tipObj.get("DEALTIME");
		
//		Date dealtime = DateUtil.StringToDate(dealtimeStr, "yyyy-MM-dd HH:mm:ss");
		
		String sql = "update w_defectremind_ins set status=1,dealtime=to_date('"+dealtimeStr+"','yyyy-mm-dd hh24:mi:ss'),dealer='"+dealer+"',comments='"+comments+"',updatetime=sysdate,updateuser="+user+" where id="+id;
		String sql_report = "insert into f_flightreport select s_f_flightreport.nextval,"+flightid+",(select f.FLIGHTNO from f_flight_schedule f where f.flt_pk="
			+flightid+"),'运行提示处理',dd.creator,'"+dealer+"',to_date('"+dealtimeStr+"','yyyy-mm-dd hh24:mi:ss'),dd.REMINDDESC,'"+comments+"',sysdate,null "
			+"from W_DEFECTREMIND dd where dd.id="+remindid;
		String sqlArr[]=new String[2];
		sqlArr[0]=sql;
		sqlArr[1]=sql_report;
		dmo.executeBatchByDS(ApmsConst.DS_APMS, sqlArr);
		dmo.commit(ApmsConst.DS_APMS);
		
		logger.debug("关闭提醒信息remindInsId=["+id+"]成功!");
		
	}

}
