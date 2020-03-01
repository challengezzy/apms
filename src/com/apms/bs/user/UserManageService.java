package com.apms.bs.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;
import com.apms.cache.ApmsServerCache;
import com.apms.cache.vo.OndutySchemaVo;
import com.apms.cache.vo.UserVo;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.SimpleHashVO;

public class UserManageService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	
	/** 轮班模式缓存 */
	private static HashMap<String, OndutySchemaVo> dutySchemaMap = new HashMap<String, OndutySchemaVo>();
	
	/**
	 * 查询人员排班
	 * @return
	 * @throws Exception
	 */
	public SimpleHashVO[] getCrewSchedulingUser(String airportname ,String deptname,String username,String startDate,String endDate) throws Exception {
		
		Date beginDate=DateUtil.StringToDate(startDate, "yyyy-MM-dd");
		Date lastDate=DateUtil.StringToDate(endDate, "yyyy-MM-dd");
		
		StringBuilder sql=new StringBuilder("SELECT a.AIRPORTNAME,a.BASEORGNAME,a.DEPTNAME,a.USERNAME,");
		for(Date date=beginDate;date.before(lastDate);date=DateUtil.moveDay(date, 1)){
			sql.append("MAX(DECODE(a.WORKDATE_STR,'");
			sql.append(DateUtil.getDateStr(date));//
			sql.append("',a.WORKSCHEMANAME)) \"");
			sql.append(DateUtil.getDateStr(date));//
			sql.append("\",");
		}
		sql.append("MAX(DECODE(a.WORKDATE_STR,'");
		sql.append(DateUtil.getDateStr(lastDate));//'2014-03-28'
		sql.append("',a.WORKSCHEMANAME)) \"");
		sql.append(DateUtil.getDateStr(lastDate));//'"2014-03-28",'
		sql.append("\" FROM V_W_USER_WORKSCHEDULE a WHERE 1=1 ");
		if(!"".equals(airportname)){
			sql.append("AND a.AIRPORTNAME like '%");
			sql.append(airportname);
			sql.append("%' ");
		}
		
		if(!"".equals(deptname)){
			sql.append("AND a.DEPTNAME like '%");
			sql.append(deptname);
			sql.append("%' ");
		}
		
		if(!"".equals(username)){
			sql.append("AND a.USERNAME like '%");
			sql.append(username);
			sql.append("%' ");
		}
		sql.append(" GROUP BY (a.AIRPORTNAME,a.BASEORGNAME,a.DEPTNAME,a.USERNAME)");
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql.toString());
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		} catch (Exception e) {
			logger.error("查询人员排班错误！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		
	}
	/**
	 * 保存人员排班
	 * */
	public String saveCrewSchedulingUser(Date startDate,int dateNum,List<UserDataVo> users,Date updateDate,String updateUser) throws Exception {
		Iterator<UserDataVo> itrQuery = users.iterator();
		List<String> insertSqlList=new ArrayList<String>();//插入语句集合
		List<String> updateSqlList=new ArrayList<String>();//更新语句集合（如果该人员在该日期已经排过班则更新）
		Date todayDate=new Date();
		UserVo userVoData = null;
		while(itrQuery.hasNext()){//首先要先查询一下是否有这个人员
			UserDataVo userData = itrQuery.next();
			userVoData=selectUserName(userData.getName_());
			if(userVoData==null){
				return "人员\""+userData.getName_()+"\"不存在！";
			}
		}
		Iterator<UserDataVo> itrInsertOrUpdate = users.iterator();
		while (itrInsertOrUpdate.hasNext()) {
			UserDataVo userData = itrInsertOrUpdate.next();
			Date workdate;//工作日期
			String workschemaname;//轮班模式
			for(int i=0;i<dateNum;i++){
				OndutySchemaVo ondutySchemaVo;
				String startTime;
				String endTime;
				String workschemaid;
				workschemaname=userData.pubFun4getPeriodDataValue(i);
				workdate=DateUtil.moveDay(startDate, i);
				if(todayDate.after(workdate)){
					return "排班日期"+DateUtil.getDateStr(workdate)+"过期！";
				}
				OndutySchemaVo ondutySchemaVoTemp=getDutySchemaByName(workschemaname);
				if(ondutySchemaVoTemp==null){
					return "轮班模式'"+workschemaname+"'不存在！";
				}else{
					ondutySchemaVo=ondutySchemaVoTemp;
					
					endTime=ondutySchemaVo.getEndtime();
					startTime=ondutySchemaVo.getStarttime();
					workschemaid= ondutySchemaVo.getId();
				}
				logger.debug("以下是人员排班的insert或update语句：");
				if(!selectWorkFromUserNameAndDate(userData.getName_(),workdate)){
					startTime=DateUtil.getDateStr(workdate)+" "+startTime;
					if(ondutySchemaVo.getSpanday()==0){//判断倒班间隔数
						endTime=DateUtil.getDateStr(workdate)+" "+endTime;
					}else{
						endTime=DateUtil.getDateStr(DateUtil.moveDay(workdate,ondutySchemaVo.getSpanday()))+" "+endTime;
					}
					StringBuilder insertSql=new StringBuilder("INSERT INTO W_USER_WORKSCHEDULE(ID,USERID,USERNAME,DEPTNAME,AIRPORTNAME,");
					insertSql.append("WORKDATE_STR,WORKDATE,WORKSCHEMAID,WORKSCHEMANAME,STARTTIME,ENDTIME,COMMENTS,UPDATETIME,UPDATEUSER) ");
					insertSql.append("VALUES( S_W_USER_WORKSCHEDULE.NEXTVAL,");
					insertSql.append(selectUserName(userData.getName_()).getId());
					insertSql.append(",'");
					insertSql.append(userData.getName_());
					insertSql.append("','");
					insertSql.append(userData.getOrg());
					insertSql.append("','");
					insertSql.append(userData.getAirport());
					insertSql.append("','");
					insertSql.append(DateUtil.getDateStr(workdate));
					insertSql.append("',");
					insertSql.append("TO_DATE('");
					insertSql.append(DateUtil.getDateStr(workdate));
					insertSql.append("','YYYY-MM-DD')");
					insertSql.append(",");
					insertSql.append(workschemaid);
					insertSql.append(",'");
					insertSql.append(workschemaname);
					insertSql.append("',");
					insertSql.append("TO_DATE('");
					insertSql.append(startTime);
					insertSql.append("','YYYY-MM-DD HH24:MI:SS')");
					insertSql.append(",");
					insertSql.append("to_date('");
					insertSql.append(endTime);
					insertSql.append("','YYYY-MM-DD HH24:MI:SS')");
					insertSql.append(",NULL,");
					insertSql.append("TO_DATE('");
					insertSql.append(DateUtil.getDateStr(updateDate));
					insertSql.append("','YYYY-MM-DD')");
					insertSql.append(",'");
					insertSql.append(updateUser);
					insertSql.append("') ");
					logger.debug(insertSql);
					insertSqlList.add(insertSql.toString());
				}else{//WORKSCHEMAID,WORKSCHEMANAME,STARTTIME,ENDTIME,UPDATETIME,UPDATEUSER
					startTime=DateUtil.getDateStr(workdate)+" "+startTime;
					if(ondutySchemaVo.getSpanday()==0){//判断倒班间隔数
						endTime=DateUtil.getDateStr(workdate)+" "+endTime;
					}else{
						endTime=DateUtil.getDateStr(DateUtil.moveDay(workdate,ondutySchemaVo.getSpanday()))+" "+endTime;
					}
					StringBuilder updateSql=new StringBuilder("UPDATE W_USER_WORKSCHEDULE SET WORKSCHEMAID   =  ");
					updateSql.append(workschemaid);
					updateSql.append(",WORKSCHEMANAME='"+workschemaname + "'");
					
					updateSql.append(",STARTTIME = TO_DATE('"+startTime+"'");
					updateSql.append(",'YYYY-MM-DD HH24:MI:SS')");
					updateSql.append(",ENDTIME = TO_DATE('"+endTime+"'");
					updateSql.append(",'YYYY-MM-DD HH24:MI:SS'),");
					updateSql.append("UPDATETIME= TO_DATE('"+DateUtil.getDateStr(updateDate)+"'");
					updateSql.append(",'YYYY-MM-DD'),UPDATEUSER = '");
					updateSql.append(updateUser);
					updateSql.append("' WHERE USERNAME = '");
					updateSql.append(userData.getName_());
					updateSql.append("' AND WORKDATE = to_date('"+DateUtil.getDateStr(workdate)+"'");
					updateSql.append(",'YYYY-MM-DD')");
					logger.debug(updateSql);
					updateSqlList.add(updateSql.toString());
				}	
			}
		}
		try {
			if(insertSqlList.size()>0){
				dmo.executeBatchByDS(ApmsConst.DS_APMS, insertSqlList);
			}
			if(updateSqlList.size()>0){
				dmo.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			}
			dmo.commit(ApmsConst.DS_APMS);
			return "保存成功！";
		} catch (Exception e) {
			logger.error("保存人员排班到数据库出错！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 *查询是否有这个排班的人
	 */
	public UserVo selectUserName(String userName) throws Exception{
		UserVo uservo = ApmsServerCache.getInstance().getUserVoByName(userName);
		if(uservo==null){
			String sql="SELECT id FROM B_USER WHERE NAME='"+userName+"' ";
			try {
				HashVO[] vos =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
				if(vos.length<=0){
					return null;
				}else{
					uservo=new UserVo();
					uservo.setId(vos[0].getStringValue("id"));
				}
				return uservo;
			} catch (Exception e) {
				logger.error("查询"+userName+"是否存在时出错！", e);
				throw e;
			} finally {
				dmo.releaseContext(ApmsConst.DS_APMS);
			}
		}else{
			return uservo;
		}
		
	}
	/**
	 *通过用户姓名和日期查询是否已经排过班
	 */
	public boolean selectWorkFromUserNameAndDate(String userName,Date workDate) throws Exception{
		String sql="SELECT 1 FROM W_USER_WORKSCHEDULE WHERE USERNAME='"+userName+"' AND WORKDATE=TO_DATE('"+DateUtil.getDateStr(workDate)+"','YYYY-MM-DD')";
		try {
			HashVO[] vos =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			if(vos.length<=0){
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("查询"+userName+"在"+DateUtil.toString(workDate)+"是否排过班时出错！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 *查询轮班模式。
	 *@param workschemaname 轮班模式名称
	 */
	public OndutySchemaVo getDutySchemaByName(String schemaName) throws Exception{
		if("".equals(schemaName)){
			schemaName="休息";
		}
		OndutySchemaVo schemaVo = dutySchemaMap.get(schemaName);
		//缓存中没有查到，从数据库里取
		if(schemaVo == null){
			String sql = "SELECT ID,STARTTIME,ENDTIME,looptype,spanday FROM B_ONDUTY_SCHEMA WHERE NAME=? ";
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql ,schemaName);
			if(vos.length>0){
				HashVO vo = vos[0];
				schemaVo = new OndutySchemaVo();
				schemaVo.setId(vo.getStringValue("id"));
				schemaVo.setStarttime( vo.getStringValue("starttime"));
				schemaVo.setEndtime( vo.getStringValue("endtime"));
				schemaVo.setSpanday( Integer.parseInt(vo.getStringValue("spanday")) );
				dutySchemaMap.put(schemaName, schemaVo);
			}else{
				return null;
			}
		}
		
		return schemaVo;
	}
}
