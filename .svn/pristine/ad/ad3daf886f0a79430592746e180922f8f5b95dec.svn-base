package com.apms.bs.intf.ams;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaServerEnvironment;

import com.apms.ApmsConst;
import com.apms.bs.sms.ShortMassageHwService;
import com.apms.bs.util.DateUtil;

/**
 * 从MCCAMS系统中提取工作计划数据
 * @author jerry
 * @date Jan 16, 2016
 */
public class AmsWorkPlaneExtractService {
	
	private static int failNum = 0; //接口数据同步失败次数计数
	private static Date lastUpdateDay = DateUtil.StringToDate("2000-01-01", "yyyy-MM-dd"); //上一次判断的最新操作时间
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO(false);
	private static StringBuilder basicSb ;
	private static StringBuilder insertSb ;
	private String sdfStr = "yyyy-MM-dd HH:mm:ss:SSS"; //时间戳必须精确到毫秒数
	private String sqlDel="delete from ams_workorderplane where ownerid = ?";
	private String dQrySql = "select OWNERID,UPDATETIME from MPWORKORDER_PLAN_LOGREC where ACTION_CLASS='D' AND UPDATETIME >?";
	
	static{
		basicSb = new StringBuilder();
		basicSb.append("SELECT TOP 100 PERCENT ");
		basicSb.append("dbo.MPWORKORDER_PLAN.WORKORDERSN AS 生产编号,");
		basicSb.append("dbo.MPWORKORDER_PLAN.PLANSN AS 维修管理号,");
		basicSb.append("dbo.MPWORKORDER_PLAN.ACNO AS 机号, ");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.ATA AS ATA, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.PARTPN AS 件号, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.PARTSN AS 序号, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.CONTEXTCN AS 内容,");
		basicSb.append("dbo.MPWORKORDER_PLAN.REMARK AS 备注, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.RII AS 必检,");
		basicSb.append("dbo.MPWORKORDER_PLAN.MH AS 工时, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.OPDY AS 实施日期, ");
		basicSb.append("ROUND(CONVERT(FLOAT, dbo.VIEWACENGLOGCONV.TSN, 8) / 60, 2) AS 实施小时,");
		basicSb.append("dbo.VIEWACENGLOGCONV.CSN AS 实施循环,");
		basicSb.append("dbo.VIEWACENGLOGCONV.DATESTATE AS 时间状态,");
		basicSb.append("dbo.MPWORKORDER_PLAN.LISTNUM AS 包含项目,");
		basicSb.append("dbo.MPWORKORDER_PLAN.OperatingState AS 实施地点, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.OPERATE_DEP AS 实施部门,");
		basicSb.append("dbo.MPWORKORDER_PLAN.SUPPORT_DEP AS 配合部门,");
		basicSb.append("dbo.MPWORKORDER_PLAN.PARTSREQ AS 航材状态, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.TOOLSREQ AS 工具状态, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.MHREQ AS 人力状态,");
		basicSb.append("dbo.MPWORKORDER_PLAN.FLTCHK AS 航班配合, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.COMPLETESTATE AS 完成状态,");
		basicSb.append("CONVERT(VARCHAR,DATEDIFF(DAY,dbo.MPWORKORDER_PLAN.OPDY,dbo.VIEWMPMAINT_PLAN_UNION.STAR_DY),8)+'DY '");
		basicSb.append(" + CONVERT(VARCHAR,ROUND(CONVERT(FLOAT,(dbo.VIEWMPMAINT_PLAN_UNION.STAR_FH -dbo.VIEWACENGLOGCONV.TSN),8)/60,2),8) + 'FH '");
		basicSb.append(" + CONVERT(VARCHAR,dbo.VIEWMPMAINT_PLAN_UNION.STAR_CY - dbo.VIEWACENGLOGCONV.CSN,8) + 'CY' AS 允许开始实施,");
		basicSb.append("CONVERT(VARCHAR,DATEDIFF(DAY,dbo.MPWORKORDER_PLAN.OPDY,dbo.VIEWMPMAINT_PLAN_UNION.END_DAY),8)+'DY '");
		basicSb.append(" + CONVERT(VARCHAR,ROUND(CONVERT(FLOAT,(dbo.VIEWMPMAINT_PLAN_UNION.END_FH-dbo.VIEWACENGLOGCONV.TSN),8)/60,2),8)+'FH '");
		basicSb.append(" + CONVERT(VARCHAR,dbo.VIEWMPMAINT_PLAN_UNION.END_CY - dbo.VIEWACENGLOGCONV.CSN,8) + 'CY' AS 允许截止实施, ");
		basicSb.append("dbo.STAFFINFO.StaffName AS 操做员, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.UPDATEDAY AS 操做时间,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.STAR_DY AS 允许开始日期,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.END_DAY AS 允许截止日期,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.TIMEATT AS 时间源,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.PARKSTATE AS 停场状态,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.PARKTIME AS 停场天数, ");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.PARKATT AS 停场分类,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.OUTRANGE AS 日期超限, ");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.EFFECT AS 有效性, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.OWNERID AS 系统编号,");
		basicSb.append("dbo.MPWORKORDER_PLAN.PARENTID AS 相关编号, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.FROMMODEL AS 模块源,");
		basicSb.append("dbo.MPWORKORDER_PLAN.MD_OWNERID AS 模块信息编号, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.MD_PARENTID AS 模块相关编号,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.CONNECTEN AS 英文描述,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.MTOPZONE AS 区域, ");
		basicSb.append("dbo.MPCAMP_AC.ACCESS AS 接近,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.CURRENTCNUM AS 计划序号,");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.TASKID AS 方案号, ");
		basicSb.append("dbo.VIEWMPMAINT_PLAN_UNION.TASKATT AS 工作属性,");
		basicSb.append("dbo.RII_SYMBOL.SYMBOL AS 必检符号, ");
		basicSb.append("dbo.MPCAMP_AC.CTI AS 首重间隔, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.MsgListCount as 关注消息, ");
		basicSb.append("dbo.MPWORKORDER_PLAN.CALLBACK as 工作退回");		
		basicSb.append(" FROM dbo.MPCAMP_AC RIGHT OUTER JOIN dbo.MPWORKORDER_PLAN");
		basicSb.append(" LEFT OUTER JOIN dbo.VIEWMPMAINT_PLAN_UNION ON dbo.MPWORKORDER_PLAN.PLANSN =dbo.VIEWMPMAINT_PLAN_UNION.PLANSN");
		basicSb.append(" AND dbo.MPWORKORDER_PLAN.WORKPOST = dbo.VIEWMPMAINT_PLAN_UNION.ROUTEPOST");
		basicSb.append(" LEFT OUTER Join dbo.RII_SYMBOL ON dbo.MPWORKORDER_PLAN.RII =dbo.RII_SYMBOL.RII");
		basicSb.append(" LEFT OUTER JOIN dbo.VIEWACENGLOGCONV ON dbo.MPWORKORDER_PLAN.OPDY =dbo.VIEWACENGLOGCONV.INFODATE");
		basicSb.append(" AND dbo.MPWORKORDER_PLAN.PARTSN =dbo.VIEWACENGLOGCONV.ENGSN AND dbo.MPWORKORDER_PLAN.WORKPOST =dbo.VIEWACENGLOGCONV.ENGPOST");
		basicSb.append(" AND dbo.MPWORKORDER_PLAN.ACNO =dbo.VIEWACENGLOGCONV.acno ON dbo.MPCAMP_AC.CAMPITEM =dbo.VIEWMPMAINT_PLAN_UNION.CAMPITEM");
		basicSb.append(" LEFT OUTER JOIN dbo.STAFFINFO ON dbo.MPWORKORDER_PLAN.USERID =dbo.STAFFINFO.StaffID");
		basicSb.append(" WHERE 1=1");
		basicSb.append(" AND (dbo.MPWORKORDER_PLAN.WORKPOST = 'HGH') ");
		//basicSb.append(" AND ((dbo.VIEWMPMAINT_PLAN_UNION.EFFECT = 1) OR (dbo.VIEWMPMAINT_PLAN_UNION.EFFECT IS NULL))");
		//basicSb.append(" AND (dbo.MPWORKORDER_PLAN.COMPLETESTATE = 0) ");
		//basicSb.append(" AND (dbo.MPWORKORDER_PLAN.PARENTID = 0)"); //明细项数据
		//basicSb.append(" order by dbo.MPWORKORDER_PLAN.UPDATEDAY asc ");
		//basicSb.append("");
		
		insertSb = new StringBuilder();
		insertSb.append("insert into AMS_WORKORDERPLANE(");
		insertSb.append("workordersn,plansn,acno,ATANO_MAJOR,partpn,partsn,contextcn,remark,rii,mh,opdy,tsn,csn,datestate,listnum");
		insertSb.append(",operatingstate,operate_dep,support_dep,partsreq,toolsreq,mhreq,fltchk,completestate,allow_startdo,allow_stopdo");
		insertSb.append(",staffname,updateday,start_day,end_day,timeatt,parkstate,parktime,parkatt,outrange");
		insertSb.append(",effect,ownerid,parentid,frommodel,md_ownerid,md_parentid,connecten,mtopzone,ACCESS_COVERPLATE");
		insertSb.append(",currentcnum,taskid,taskatt,symbol,cti,msglistcount,callback,updatetime )");
		insertSb.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");
		insertSb.append(",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)");
		insertSb.append("");
		
	}
	
	public int extractWorkPlan() throws Exception{
		try{
			
			logger.info("提取变更的工作计划数据开始...");
			
			int num = 0;
			//查询当前数据表中最大msg_no
			Date updateday = getRecentUpdateTime();
			
			//处理删除的数据
			HashVO[] delVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMSOLD, dQrySql,updateday );
			for(int i=0;i<delVos.length; i++){
				String ownerid = delVos[i].getStringValue("OWNERID");
				dmo.executeUpdateByDS(ApmsConst.DS_APMS,sqlDel,ownerid );
			}
			dmo.commit(ApmsConst.DS_APMS);
			
			int diff = DateUtil.dateDiff("second", updateday, lastUpdateDay);
			if(diff <=0 ){
				logger.info("工作计划数据没有更新，跳过本次同步");
				//return;
			}
			
			StringBuilder qrySb = new StringBuilder();
			qrySb.append(basicSb.toString());
			qrySb.append(" AND DBO.MPWORKORDER_PLAN.UPDATEDAY > '"+DateUtil.getDateStr(updateday,sdfStr)+"'");
			qrySb.append(" order by dbo.MPWORKORDER_PLAN.UPDATEDAY asc ");
			qrySb.append("");
			
			//增量更新 1，新增数据  2，变更数据 3，删除数据
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMSOLD, qrySb.toString());
			if(vos.length ==0){
				logger.debug("AMS系统维修工作没有更新的数据。");
			}else{
				String sql = "select 1 from ams_workorderplane t where t.ownerid = ?";
				for (int i = 0; i < vos.length; i++) {
					String ownerid = vos[i].getStringValue("系统编号");
					
					HashVO[] vos_tem = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS,sql,ownerid);
					if (vos_tem.length<=0) {
						insertWorkPlanData(vos[i]);
					} else {
						updateWorkPlaninfo(vos[i],ownerid);
					}
					num++;
					
					if(num%80 == 0){
						dmo.commit(ApmsConst.DS_APMS);
					}
				}
			}
			dmo.commit(ApmsConst.DS_APMS);
			
			lastUpdateDay = updateday;
			logger.info("抽取接口变更的工作计划数据结束...");
			failNum = 0;
			
			return num;
		}catch (Exception e) {
			logger.error("变更的工作计划数据抽取异常！", e);
			
			failNum++;
			String pname = (String)NovaServerEnvironment.getInstance().get("PROJECT_NAME");
			String msg = pname +"执行工作计划数据同步连续失败"+failNum+"次,请查看任务日志";
			
			if( failNum >= 3){
				//数据任务失败，及时发短信告知维护人员
				ShortMassageHwService.getInstance().send( "18916752189", msg);//zzy
				ShortMassageHwService.getInstance().send( "18658176006", msg);//huanglei
			}
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMSOLD);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 更新信息，先删除再新增
	 * @param vo
	 * @throws Exception
	 */
	public void updateWorkPlaninfo(HashVO vo,String ownerid) throws Exception {
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,sqlDel,ownerid );
		insertWorkPlanData(vo);
		logger.debug("ownerid="+ownerid+"]更新成功！");
	}
	
	private void insertWorkPlanData(HashVO vo) throws Exception{
//		insert into AMS_WORKORDERPLANE(
//		workordersn,plansn,acno,partpn,partsn,contextcn,remark,rii,mh,opdy,tsn,csn,datestate,listnum
//		,operatingstate,operate_dep,support_dep,partsreq,toolsreq,mhreq,fltchk,completestate,allow_startdo,allow_stopdo
//		,staffname,updateday,start_day,end_day,timeatt,parkstate,parktime,parkatt,outrange
//		,effect,ownerid,parentid,frommodel,md_ownerid,md_parentid,connecten,mtopzone,ACCESS_COVERPLATE
//		,currentcnum,taskid,taskatt,symbol,cti,msglistcount,callback,updatetime )
		
		Integer effect = vo.getIntegerValue("有效性")==null?1 :vo.getIntegerValue("有效性") ;
		Integer completestate = vo.getIntegerValue("完成状态");
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,insertSb.toString()
				,vo.getStringValue("生产编号"),vo.getStringValue("维修管理号"),vo.getStringValue("机号"),vo.getStringValue("ATA")
				,vo.getStringValue("件号")
				,vo.getStringValue("序号"),vo.getStringValue("内容"),vo.getStringValue("备注"),vo.getIntegerValue("必检")
				,vo.getDoubleValue("工时"),vo.getDateValue("实施日期"),vo.getDoubleValue("实施小时"),vo.getDoubleValue("实施循环")
				,vo.getStringValue("时间状态"),vo.getDoubleValue("包含项目"),vo.getStringValue("实施地点"),vo.getStringValue("实施部门")
				,vo.getStringValue("配合部门"),vo.getIntegerValue("航材状态"),vo.getIntegerValue("工具状态"),vo.getIntegerValue("人力状态")
				,vo.getIntegerValue("航班配合"), completestate ,vo.getStringValue("允许开始实施"),vo.getStringValue("允许截止实施")
				,vo.getStringValue("操做员"),vo.getTimeStampValue("操做时间"),vo.getDateValue("允许开始日期"),vo.getDateValue("允许截止日期")
				,vo.getStringValue("时间源"),vo.getIntegerValue("停场状态"),vo.getDoubleValue("停场天数"),vo.getStringValue("停场分类")
				,vo.getIntegerValue("日期超限"),effect,vo.getIntegerValue("系统编号"),vo.getIntegerValue("相关编号")
				,vo.getStringValue("模块源"),vo.getIntegerValue("模块信息编号"),vo.getIntegerValue("模块相关编号"),vo.getStringValue("英文描述")
				,vo.getStringValue("区域"),vo.getStringValue("接近"),vo.getIntegerValue("计划序号"),vo.getStringValue("方案号")
				,vo.getStringValue("工作属性"),vo.getStringValue("必检符号"),vo.getStringValue("首重间隔"),vo.getIntegerValue("关注消息")
				,vo.getIntegerValue("工作退回") );
		
		logger.debug("ownerid="+vo.getIntegerValue("系统编号")+"]新增成功！");
	}
	
	public void extractWorkPlanInit() throws Exception{
		try{
			logger.info("提取工作计划数据开始...");
			
			//查询当前数据表中最大msg_no
			Date updateday = getRecentUpdateTime();
			
			int diff = DateUtil.dateDiff("second", updateday, lastUpdateDay);
			if(diff <=0 ){
				logger.info("工作计划数据没有更新，跳过本次同步");
				//return;
			}
			
			StringBuilder qrySb = new StringBuilder();
			qrySb.append(basicSb.toString());
			qrySb.append(" AND DBO.MPWORKORDER_PLAN.UPDATEDAY > '"+DateUtil.getDateStr(updateday,sdfStr)+"'");
			qrySb.append(" order by dbo.MPWORKORDER_PLAN.UPDATEDAY asc ");
			qrySb.append("");
			
			//批量数据导入
			dmo.executeImportByDS(ApmsConst.DS_APMSOLD, qrySb.toString(), getFromCols(49)
								 ,ApmsConst.DS_APMS, insertSb.toString() , 500);
			
			lastUpdateDay = updateday;
			logger.info("抽取接口工作计划数据结束...");
			failNum = 0;
		}catch (Exception e) {
			logger.error("工作计划数据抽取异常！", e);
			
			failNum++;
			String pname = (String)NovaServerEnvironment.getInstance().get("PROJECT_NAME");
			String msg = pname +"执行工作计划数据同步连续失败"+failNum+"次,请查看任务日志";
			
			if( failNum >= 3){
				//数据任务失败，及时发短信告知维护人员
				ShortMassageHwService.getInstance().send( "18916752189", msg);//zzy
				ShortMassageHwService.getInstance().send( "18658176006", msg);//huanglei
			}
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMSOLD);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	public int[] getFromCols(int length){
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i+1;
		
		return fromcols;
	}
	
	/**
	 * 获取当前系统中，已同步的最新的操作时间
	 * @return
	 * @throws Exception
	 */
	public Date getRecentUpdateTime() throws Exception{
		String querySql = "SELECT MAX(UPDATEDAY) UPDATEDAY FROM AMS_WORKORDERPLANE ";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);
		
		Date updateday = null;
		Timestamp t1 = null;
		String updateStr = null;
		if(vos.length > 0){
			t1 = vos[0].getTimeStampValue("UPDATEDAY");
		}
		if(t1 != null){
            updateday = new Date(t1.getTime());
		}else{
			updateStr = "2005-01-01 00:00:00:000";
			updateday = DateUtil.StringToDate(updateStr,sdfStr);
		}
		
		logger.debug("当前工作计划最近的数据时间: " + updateStr);
		
		return updateday;		
	}
	
}

