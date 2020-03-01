package com.apms.bs.flight;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.SimpleHashVO;

/**
 * 航班计划管理服务类
 * @author jerry
 * @date Apr 15, 2014
 */
public class FlightManageService {
	
protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private String insertFlightSql;
	
	private String insertAcstateSql;
	
	public FlightManageService(){
		StringBuilder inSql = new StringBuilder("INSERT INTO F_FLIGHT_SCHEDULE(ID,FLT_PK ");
		inSql.append("  ,FLT_DATE ,IATA_C ,CO_SEQ ,FLIGHTNO ,FLT_ID ,FLT_SEQ ,FLT_TASK ,ACNUM ,ACMODEL");
		inSql.append(" ,DEP_APT ,ARR_APT ,FLTTYPE_DEP ,FLTTYPE_ARR ,STD ,STA ,CTD ,CTA,FLIGHTDESC");
		inSql.append(" ,HASHVALUE,UPDATEUSER,ISCONFIRMED, AC_STATE,WORKFORCE_STATE ");
		inSql.append(" ,COMMENTS,UPDATETIME,ISLOCKEDIN,DATASOURCE )VALUES(S_F_FLIGHT_SCHEDULE.NEXTVAL, S_FLIGHTSCH_PK.NEXTVAL ");
		inSql.append(" ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");//20个字段
		inSql.append(" , 0, 0,0,'模板生成' ,sysdate , 0,1 )");
		inSql.append(" ");
		
		insertFlightSql = inSql.toString();
		
		StringBuilder inSql2 = new StringBuilder("INSERT INTO F_AC_STATE(ID,FLIGHTID,FLIGHTNO,STATE,REPORTER");
		inSql2.append(",UPDATETIME,UPDATEUSER,COMMENTS) ");
		inSql2.append(" VALUES(S_F_AC_STATE.NEXTVAL,?,?,?,?,?,?,?)");
		inSql2.append("");
		insertAcstateSql = inSql2.toString();
	}
	
	
	
	/**
	 * 后台根据模板生成航班计划
	 * @return
	 * @throws Exception
	 */
	public String flightCreateServer(String flightTempName,int dayNum,String flttypeDep,String flttypeArr) throws Exception{
		String sql = "select id, name, iata_c, co_seq, flightno, flt_seq, flt_task, acnum, acmodel, dep_apt, arr_apt, std, sta "
			+ " , loopinterval, looptype, validstate, weekday_0, weekday_1, weekday_2, weekday_3, weekday_4, weekday_5, weekday_6"
		    + ", updateuser, comments, updatetime from b_flightsch_template where name ='"+ flightTempName +"'";
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		
		if(vos.length < 0){
			return "根据模板["+flightTempName+"]未查到模板数据";
		}
		
		HashVO vo = vos[0];
		String flightno = vo.getStringValue("FLIGHTNO");
		String acnum =  vo.getStringValue("ACNUM");
		String acmodel = vo.getStringValue("ACMODEL");
		String iatac = vo.getStringValue("IATA_C");
		String coseq = vo.getStringValue("CO_SEQ");
		String flttask = vo.getStringValue("FLT_TASK");
		String fltseq = vo.getStringValue("FLT_SEQ");
		String depapt = vo.getStringValue("DEP_APT");
		String arrapt = vo.getStringValue("ARR_APT");
		String std = vo.getStringValue("STD");
		String sta = vo.getStringValue("STA");
		int loopInterval = new Integer(vo.getStringValue("LOOPINTERVAL"));
		String loopType = vo.getStringValue("LOOPTYPE");
		String flightDesc = vo.getStringValue("FLIGHTDESC");
		String weekday0 = vo.getStringValue("WEEKDAY_0");
		String weekday1 = vo.getStringValue("WEEKDAY_1");
		String weekday2 = vo.getStringValue("WEEKDAY_2");
		String weekday3 = vo.getStringValue("WEEKDAY_3");
		String weekday4 = vo.getStringValue("WEEKDAY_4");
		String weekday5 = vo.getStringValue("WEEKDAY_5");
		String weekday6 = vo.getStringValue("WEEKDAY_6");
		
		//日期数据确认转换为天
		Date datebegin = DateUtil.StringToDate(DateUtil.getDateStr(new Date()), "yyyy-MM-dd") ;
		Date dateend = DateUtil.moveDay(datebegin, dayNum);
		
		Date datecur = datebegin;
		long cur = datecur.getTime();
		long end = dateend.getTime();
		
		int num =1;
		
		if("0".equals(loopType)){ //按天生成计划
			while(end >= cur){
				String dateStr = DateUtil.getDateStr(datecur);
				Date dateStd = DateUtil.StringToDate(dateStr+" " +  std, "yyyy-MM-dd HH:mm");
				Date dateSta = DateUtil.StringToDate(dateStr+" " +  sta, "yyyy-MM-dd HH:mm");
				
				//航班不存在则添加，存在则不添加
				if( ! isExistsFlightSch(datecur, iatac+flightno, depapt, arrapt)){
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertFlightSql
							,datecur, iatac,coseq, iatac+flightno, flightno, fltseq, flttask, acnum, acmodel
							,depapt, arrapt,flttypeDep,flttypeArr
							,dateStd, dateSta, dateStd, dateSta,flightDesc
							, dateStd.getTime(), "system");
					num ++;
				}
				
				datecur = DateUtil.moveDay(datecur, loopInterval+1);
				cur = datecur.getTime();
			}
			dmo.commit(ApmsConst.DS_APMS);
		}else if("1".equals(loopType)){ //按周模式生成计划
			while(end >= cur){
				String dateStr = DateUtil.getDateStr(datecur);
				Date dateStd = DateUtil.StringToDate(dateStr+" " +  std, "yyyy-MM-dd HH:mm");
				Date dateSta = DateUtil.StringToDate(dateStr+" " +  sta, "yyyy-MM-dd HH:mm");
				
				//判断当天所属的星期是否要生成计划数据
				boolean isCreate = false;
				int dayOfWeek = DateUtil.getWeekDay(datecur);
				if(dayOfWeek == 0 && "1".equals(weekday0)){
					isCreate = true;
				}else if(dayOfWeek == 1 && "1".equals(weekday1)){
					isCreate = true;
				}else if(dayOfWeek == 2 && "1".equals(weekday2)){
					isCreate = true;
				}else if(dayOfWeek == 3 && "1".equals(weekday3)){
					isCreate = true;
				}else if(dayOfWeek == 4 && "1".equals(weekday4)){
					isCreate = true;
				}else if(dayOfWeek == 5 && "1".equals(weekday5)){
					isCreate = true;
				}else if(dayOfWeek == 6 && "1".equals(weekday6)){
					isCreate = true;
				}
				
				if(isCreate && !isExistsFlightSch(datecur, iatac+flightno, depapt, arrapt) ){
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertFlightSql
							,datecur, iatac,coseq, iatac+flightno, flightno, fltseq, flttask, acnum, acmodel
							,depapt, arrapt,flttypeDep,flttypeArr
							,dateStd, dateSta, dateStd, dateSta,flightDesc
							, dateStd.getTime(), "system");
					num++;
				}
				
				datecur = DateUtil.moveDay(datecur, 1); //加一天
				cur = datecur.getTime();
			}
			dmo.commit(ApmsConst.DS_APMS);
				
		}
		
		return "根据模板["+flightTempName+"]生成航班计划成功，本次生成"+num+"条!";
	}
	
	/**
	 * 根据模板生成航班计划数据
	 * @param user 创建人
	 * @param flightInfo
	 * @return
	 * @throws Exception
	 */
	public String flightCreate(String user, Map<String, Object> flightInfo) throws Exception{
		
		Date datebegin = (Date)flightInfo.get("DATEBEGIN");
		Date dateend = (Date)flightInfo.get("DATEEND");
		String flightno = (String)flightInfo.get("FLIGHTNO");
		String acnum =  (String)flightInfo.get("ACNUM");
		String acmodel = (String)flightInfo.get("ACMODEL");
		String iatac = (String)flightInfo.get("IATA_C");
		String coseq = (String)flightInfo.get("CO_SEQ");
		String flttask = (String)flightInfo.get("FLT_TASK");
		String fltseq = (String)flightInfo.get("FLT_SEQ");
		String depapt = (String)flightInfo.get("DEP_APT");
		String arrapt = (String)flightInfo.get("ARR_APT");
		String std = (String)flightInfo.get("STD");
		String sta = (String)flightInfo.get("STA");
		int loopInterval = new Integer((String)flightInfo.get("LOOPINTERVAL"));
		String loopType = (String)flightInfo.get("LOOPTYPE");
		String flightDesc = (String)flightInfo.get("FLIGHTDESC");
		String flttypeDep = (String)flightInfo.get("FLTTYPE_DEP");
		String flttypeArr = (String)flightInfo.get("FLTTYPE_ARR");
		String weekday0 = (String)flightInfo.get("WEEKDAY_0");
		String weekday1 = (String)flightInfo.get("WEEKDAY_1");
		String weekday2 = (String)flightInfo.get("WEEKDAY_2");
		String weekday3 = (String)flightInfo.get("WEEKDAY_3");
		String weekday4 = (String)flightInfo.get("WEEKDAY_4");
		String weekday5 = (String)flightInfo.get("WEEKDAY_5");
		String weekday6 = (String)flightInfo.get("WEEKDAY_6");
		
		//日期数据确认转换为天
		datebegin = DateUtil.StringToDate(DateUtil.getDateStr(datebegin), "yyyy-MM-dd") ;
		dateend = DateUtil.StringToDate(DateUtil.getDateStr(dateend), "yyyy-MM-dd") ;
		
		Date datecur = datebegin;
		long cur = datecur.getTime();
		long end = dateend.getTime();
		
		int num = 0;
		if("0".equals(loopType)){ //按天生成计划
			while(end >= cur){
				String dateStr = DateUtil.getDateStr(datecur);
				Date dateStd = DateUtil.StringToDate(dateStr+" " +  std, "yyyy-MM-dd HH:mm");
				Date dateSta = DateUtil.StringToDate(dateStr+" " +  sta, "yyyy-MM-dd HH:mm");
				
				if( !isExistsFlightSch(datecur, iatac+flightno, depapt, arrapt) ){
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertFlightSql
							,datecur, iatac,coseq, iatac+flightno, flightno, fltseq, flttask, acnum, acmodel
							,depapt, arrapt,flttypeDep,flttypeArr
							,dateStd, dateSta, dateStd, dateSta,flightDesc
							, dateStd.getTime(), user);
					num++;
				}
				
				datecur = DateUtil.moveDay(datecur, loopInterval+1);
				cur = datecur.getTime();
				
			}
			dmo.commit(ApmsConst.DS_APMS);
		}else if("1".equals(loopType)){ //按周模式生成计划
			while(end >= cur){
				String dateStr = DateUtil.getDateStr(datecur);
				Date dateStd = DateUtil.StringToDate(dateStr+" " +  std, "yyyy-MM-dd HH:mm");
				Date dateSta = DateUtil.StringToDate(dateStr+" " +  sta, "yyyy-MM-dd HH:mm");
				
				//判断当天所属的星期是否要生成计划数据
				boolean isCreate = false;
				int dayOfWeek = DateUtil.getWeekDay(datecur);
				if(dayOfWeek == 0 && "1".equals(weekday0)){
					isCreate = true;
				}else if(dayOfWeek == 1 && "1".equals(weekday1)){
					isCreate = true;
				}else if(dayOfWeek == 2 && "1".equals(weekday2)){
					isCreate = true;
				}else if(dayOfWeek == 3 && "1".equals(weekday3)){
					isCreate = true;
				}else if(dayOfWeek == 4 && "1".equals(weekday4)){
					isCreate = true;
				}else if(dayOfWeek == 5 && "1".equals(weekday5)){
					isCreate = true;
				}else if(dayOfWeek == 6 && "1".equals(weekday6)){
					isCreate = true;
				}
				
				if(isCreate && !isExistsFlightSch(datecur, iatac+flightno, depapt, arrapt) ){
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertFlightSql
							,datecur, iatac,coseq, iatac+flightno, flightno, fltseq, flttask, acnum, acmodel
							,depapt, arrapt,flttypeDep,flttypeArr
							,dateStd, dateSta, dateStd, dateSta,flightDesc
							, dateStd.getTime(), user);
					num++;
				}
				
				datecur = DateUtil.moveDay(datecur, 1); //加一天
				cur = datecur.getTime();
			}
			dmo.commit(ApmsConst.DS_APMS);
				
		}
		
		
		return "根据模板["+flightno+"]生成航班计划成功，本次生成"+num+"条!";
	}
	
	/**
	 * 判断航班是否已经存在
	 * @param fltdate
	 * @param flightno
	 * @param depApt
	 * @param arrApt
	 * @return
	 */
	private boolean isExistsFlightSch(Date fltdate,String flightno,String depApt,String arrApt) throws Exception{
		boolean isExists = false;
		String sql = "select 1 from f_flight_schedule s where s.flt_date=? and s.flightno=? and s.dep_apt=? and s.arr_apt=?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql, fltdate, flightno, depApt, arrApt);
		if(vos.length > 0){
			isExists = true;
		}
		
		return isExists;
	}
	
	/**
	 * 插入飞机状态记录
	 * @param flightid
	 * @param flightno
	 * @param state
	 * @param reporter
	 * @param updatetime
	 * @param updateuser
	 * @param comments
	 * @throws Exception
	 */
	public void insertAcState(String flightid,String flightno,String state,String reporter,
					Date updatetime,String updateuser,String comments) throws Exception{
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertAcstateSql
				,flightid,flightno,state,reporter,updatetime,updateuser,comments );
		dmo.commit(ApmsConst.DS_APMS);
		
		logger.debug("新增航班fligthno=["+flightno+"]状态["+state+"]成功");
	}
	
	public void flightReportAdd(String user, Map<String, Object> reportInfo) throws Exception{
		String flightid =  (String)reportInfo.get("FLIGHTID");
		String flightno = (String)reportInfo.get("FLIGHTNO");
		String type = (String)reportInfo.get("TYPE");
		String reporter = (String)reportInfo.get("REPORTER");
		String recipient = (String)reportInfo.get("RECIPIENT");
		String reporttime = (String)reportInfo.get("REPORTTIME");
		String content = (String)reportInfo.get("REPORTCONTENT");
		String comments = (String)reportInfo.get("COMMENTS");
		
		Date rpttime = DateUtil.StringToDate(reporttime, "yyyy-MM-dd HH:mm:ss");
		
		String sql = "insert into f_flightreport(id,flightid,flightno,type,reporter,recipient,reporttime,reportcontent,comments,updatetime,updateuser )";
		sql += "values(s_f_flightreport.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql, flightid,flightno,type,reporter, recipient ,rpttime,content,comments,user);
		dmo.commit(ApmsConst.DS_APMS);
		
		logger.debug("新增航班fligthno=["+flightno+"]通报记录成功");
		
	}
	
	public SimpleHashVO[] selectFlightForOrglineEveryday(String startDay,String endDay) throws Exception{
		Date beginDate=DateUtil.StringToDate(startDay, "yyyy-MM-dd");
		Date lastDate=DateUtil.StringToDate(endDay, "yyyy-MM-dd");
		StringBuilder sql=new StringBuilder("select fo.name,");
		for(Date date=beginDate;date.before(lastDate);date=DateUtil.moveDay(date, 1)){
			sql.append("MAX(decode(to_char(fo.FLT_DATE,'yyyy-mm-dd'),'");
			sql.append(DateUtil.getDateStr(date));//
			sql.append("',fo.totalnum,null)) \"");
			sql.append(DateUtil.getDateStr(date));//
			sql.append("\",");
		}
		sql.append("MAX(decode(to_char(fo.FLT_DATE,'yyyy-mm-dd'),'");
		sql.append(DateUtil.getDateStr(lastDate));//
		sql.append("',fo.totalnum,null)) \"");
		sql.append(DateUtil.getDateStr(lastDate));//
		sql.append("\" from (select  t.name,t.flt_date, count(1) totalnum from (select u.name username,o.name,u.orgid_line,s.flt_date,s.flightno");
		sql.append(" from b_user u,b_organization o,f_flight_schedule s where u.orgid_line=o.id and s.release_user=u.name ) t");
		sql.append(" where t.flt_date>=to_date('"+startDay+"','yyyy-mm-dd') and t.flt_date<=to_date('"+endDay+"','yyyy-mm-dd') group by t.name,t.flt_date order by flt_date,name ) fo group by fo.name");
		logger.info("查询车间每天航班数："+sql.toString());
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql.toString());
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		} catch (Exception e) {
			logger.error("查询车间每天放行航班数错误！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	} 
	
	public SimpleHashVO[] queryForAcnumByAcmodel(String code_3,String orgline,String startDay,String  endDay) throws Exception{
		String beginDate="to_date('"+startDay+"', 'yyyy-MM-dd')";
		String lastDate="to_date('"+endDay+"', 'yyyy-MM-dd')";
		String sql="select acmodel,(select count(1) from v_flightsch_join_report ft,b_user ut,b_organization orgt, b_aircraft air,b_aircraft_model acm";
		sql +=" where ft.RELEASE_USER = ut.name and ft.RELEASE_USER is not null and ut.orgid_line = orgt.id and air.aircraftsn = ft.ACNUM";
		sql +=" and air.acmodelid = acm.id and ft.FLT_DATE >="+beginDate+" and ft.FLT_DATE <="+lastDate;
		sql +=" and ft.APT = '"+code_3+"'";
		if(!"".equals(orgline)){
			sql +=" and orgt.name = '"+orgline+"'";
		}
		sql +=" and f.acmodel = acm.modelcode group by acm.modelcode) orglinereleasecount,";
		sql +=" (select count(distinct ut.id) from  b_user ut,b_organization orgt,b_aircraft  air,b_aircraft_model acm,B_USER_CAPACITY  buc";
		sql +=" where 1=1";
		if(!"".equals(orgline)){
			sql +="	and orgt.name = '"+orgline+"'";
		}
		sql +=" and ut.orgid_line = orgt.id and ut.id=buc.userid and buc.acmodel_capacity=acm.modelcapacity";
		sql +=" and acm.modelcode=f.acmodel and buc.level_capacity>=40 group by acm.modelcode) orglinereleaserightcount,";
		
		
		sql +=" (select count(distinct ut.id) from  b_user ut,b_organization orgt,";
		sql +=" b_aircraft air,b_aircraft_model acm,B_USER_CAPACITY  buc where 1=1";
		if(!"".equals(orgline)){
			sql +="	and orgt.name = '"+orgline+"'";
		}
		sql +=" and ut.orgid_line = orgt.id and ut.id=buc.userid and buc.acmodel_capacity=acm.modelcapacity and acm.modelcode=f.acmodel";
		sql +=" and buc.level_capacity>=40 group by acm.modelcode)/(select count(1) from v_flightsch_join_report ft,b_user  ut,";
		sql +=" b_organization orgt,b_aircraft air,b_aircraft_model acm";
		sql +=" where ft.RELEASE_USER = ut.name and ft.RELEASE_USER is not null and ut.orgid_line = orgt.id";
		sql +=" and air.aircraftsn = ft.ACNUM and air.acmodelid = acm.id and ft.FLT_DATE >="+beginDate+" and ft.FLT_DATE <="+lastDate;
		sql +=" and ft.APT = '"+code_3+"'";
		if(!"".equals(orgline)){
			sql +=" and orgt.name = '"+orgline+"'"; 
		}
		sql +=" and f.acmodel = acm.modelcode group by acm.modelcode) personaircraftscale";
		sql +=" from (select distinct acm.modelcode acmodel from v_flightsch_join_report f,b_user u,b_organization   org,b_aircraft  air,";
		sql +=" b_aircraft_model acm where f.RELEASE_USER = u.name and f.RELEASE_USER is not null";
		sql +=" and air.aircraftsn = f.ACNUM and air.acmodelid = acm.id and u.orgid_line = org.id and f.FLT_DATE >="+beginDate;
		sql +=" and f.FLT_DATE <="+lastDate+"and f.APT = '"+code_3+"'";
		if(!"".equals(orgline)){
			sql +=" and org.name = '"+orgline+"'";
		}
		sql +=") f";
		logger.info("查询"+code_3+"机场人机比："+sql.toString());
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql.toString());
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		} catch (Exception e) {
			logger.error("查询机场放行航班数错误！", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	

}
