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
 * 从维修工作记录中，提取APU的维修记录
 * @author jerry
 * @date Dec 17, 2016
 */
public class AmsApuRepairExtractService {
	
	private static int failNum = 0; //接口数据同步失败次数计数
	private static Date lastUpdateDay = DateUtil.StringToDate("2000-01-01", "yyyy-MM-dd"); //上一次判断的最新操作时间
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO(false);
	private static StringBuilder basicSb ;
	private static StringBuilder insertSb ;
	private String sdfStr = "yyyy-MM-dd HH:mm:ss:SSS"; //时间戳必须精确到毫秒数
	private String sqlDel="delete from O_APUCOMP_REPAIRLOG where ITEMID = ?";
	//private String dQrySql = "select OWNERID,UPDATETIME from MPWORKORDER_PLAN_LOGREC where ACTION_CLASS='D' AND UPDATETIME >?";
	
	static{
		basicSb = new StringBuilder();
		basicSb.append("SELECT dbo.VIEWMPMAINT_PLAN_UNION.ATA,           ");
		basicSb.append(" dbo.MPWORKORDER_PLAN.WORKORDERSN,               ");
		basicSb.append(" dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.PARTNAME,  ");
		basicSb.append(" dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.PARTPNDN,  ");
		basicSb.append(" dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.PARTSNDN,  ");
		basicSb.append(" dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.PARTDNLOC, ");
		basicSb.append(" dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.FROMMODEL, ");
		basicSb.append(" dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.UPDATEDY,  ");
		basicSb.append(" dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.SWAPATT,   ");
		basicSb.append(" dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.ITEMID,    ");
		basicSb.append(" dbo.MPWORKORDER_PLAN.ACNO                       ");
		basicSb.append("FROM dbo.VIEWMPMAINT_PLAN_UNION INNER JOIN       ");
		basicSb.append(" dbo.MPWORKORDER_PLAN ON                         ");
		basicSb.append(" dbo.VIEWMPMAINT_PLAN_UNION.PLANSN = dbo.MPWORKORDER_PLAN.PLANSN INNER   ");
		basicSb.append("  JOIN dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2 ON   ");
		basicSb.append(" dbo.MPWORKORDER_PLAN.WORKORDERSN = dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.WORKORDERSN ");
		basicSb.append("WHERE (dbo.VIEWMPMAINT_PLAN_UNION.ATA = '49')    ");
		//basicSb.append("and dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.UPDATEDY > '2016-06-06'   ");
		//basicSb.append("");
		
		insertSb = new StringBuilder();
		insertSb.append("insert into O_APUCOMP_REPAIRLOG(");
		insertSb.append(" ID, ATANO_MAJOR, WORKORDERSN, PARTNAME, PARTPNDN, PARTSNDN, PARTDNLOC, FROMMODEL,UPDATEDY");
		insertSb.append(" ,SWAPATT,ITEMID, ACNUM, REPAIRACTION,DATASOURCE, UPDATETIME)");
		insertSb.append(" values(S_ID.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?, 0,1,SYSDATE)");//11个
		insertSb.append("");
		
	}
	
	public int extractData() throws Exception{
		try{
			
			logger.info("提取APU组件拆换记录数据开始...");
			
			int num = 0;
			//查询当前数据表中最大msg_no
			Date updateday = getRecentUpdateTime();
			
			//TODO 处理删除的数据
//			HashVO[] delVos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMSOLD, dQrySql,updateday );
//			for(int i=0;i<delVos.length; i++){
//				String ownerid = delVos[i].getStringValue("ITEMID");
//				dmo.executeUpdateByDS(ApmsConst.DS_APMS,sqlDel,ownerid );
//			}
//			dmo.commit(ApmsConst.DS_APMS);
			
			int diff = DateUtil.dateDiff("second", updateday, lastUpdateDay);
			if(diff <=0 ){
				logger.info("APU组件拆换记录数据没有更新，跳过本次同步");
				//return;
			}
			
			StringBuilder qrySb = new StringBuilder();
			qrySb.append(basicSb.toString());
			qrySb.append(" and dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.UPDATEDY > '"+DateUtil.getDateStr(updateday,sdfStr)+"' ");
			qrySb.append(" order by dbo.VIEW_MPWORKORDER_PARTSWAP_STYLE2.UPDATEDY asc ");
			qrySb.append("");
			
			//增量更新 1，新增数据  2，变更数据 3，删除数据
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMSOLD, qrySb.toString());
			if(vos.length ==0){
				logger.debug("APU组件拆换记录没有更新的数据。");
			}else{
				String sql = "select 1 from O_APUCOMP_REPAIRLOG t where ITEMID = ?";
				for (int i = 0; i < vos.length; i++) {
					String itemid = vos[i].getStringValue("ITEMID");
					
					HashVO[] vos_tem = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS,sql,itemid);
					if (vos_tem.length<=0) {
						insertWorkPlanData(vos[i]);
					} else {
						updateWorkPlaninfo(vos[i],itemid);
					}
					num++;
					
					if(num%80 == 0){
						dmo.commit(ApmsConst.DS_APMS);
					}
				}
			}
			dmo.commit(ApmsConst.DS_APMS);
			
			lastUpdateDay = updateday;
			logger.info("抽取接口APU组件拆换记录数据结束...");
			failNum = 0;
			
			return num;
		}catch (Exception e) {
			logger.error("变更的APU组件拆换记录抽取异常！", e);
			
			failNum++;
			String pname = (String)NovaServerEnvironment.getInstance().get("PROJECT_NAME");
			String msg = pname +"执行APU组件拆换记录数据同步连续失败"+failNum+"次,请查看任务日志";
			
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
	public void updateWorkPlaninfo(HashVO vo,String itemid) throws Exception {
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,sqlDel,itemid );
		insertWorkPlanData(vo);
		logger.debug("itemid="+itemid+"]更新成功！");
	}
	
	private void insertWorkPlanData(HashVO vo) throws Exception{
		
//		insertSb.append("insert into O_APUCOMP_REPAIRLOG(");
//		insertSb.append(" ID, ATANO_MAJOR, WORKORDERSN, PARTNAME, PARTPNDN, PARTSNDN, PARTDNLOC, FROMMODEL,UPDATEDY");
//		insertSb.append(" ,SWAPATT,ITEMID, ACNUM, REPAIRACTION,DATASOURCE UPDATETIME)");
//		insertSb.append(" values(S_ID.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?, 0,1,SYSDATE)");//11个
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,insertSb.toString()
				,vo.getStringValue("ATA"),vo.getStringValue("WORKORDERSN") ,vo.getStringValue("PARTNAME")
				,vo.getStringValue("PARTPNDN"),vo.getStringValue("PARTSNDN"),vo.getStringValue("PARTDNLOC")
				,vo.getStringValue("FROMMODEL"),vo.getDateValue("UPDATEDY"),vo.getStringValue("SWAPATT")
				,vo.getStringValue("ITEMID"),vo.getStringValue("ACNO")
				);
		
		logger.debug("ITEMID="+vo.getIntegerValue("ITEMID")+"]新增成功！");
	}
	
	public void extractWorkPlanInit() throws Exception{
		try{
			logger.info("提取APU组件拆换记录数据开始...");
			
			//查询当前数据表中最大msg_no
			Date updateday = getRecentUpdateTime();
			
			int diff = DateUtil.dateDiff("second", updateday, lastUpdateDay);
			if(diff <=0 ){
				logger.info("APU组件拆换记录数据没有更新，跳过本次同步");
				//return;
			}
			
			StringBuilder qrySb = new StringBuilder();
			qrySb.append(basicSb.toString());
			qrySb.append(" AND DBO.MPWORKORDER_PLAN.UPDATEDAY > '"+DateUtil.getDateStr(updateday,sdfStr)+"'");
			qrySb.append(" order by dbo.MPWORKORDER_PLAN.UPDATEDAY asc ");
			qrySb.append("");
			
			//批量数据导入
			dmo.executeImportByDS(ApmsConst.DS_APMSOLD, qrySb.toString(), getFromCols(11)
								 ,ApmsConst.DS_APMS, insertSb.toString() , 500);
			
			lastUpdateDay = updateday;
			logger.info("抽取接口APU组件拆换记录数据结束...");
			failNum = 0;
		}catch (Exception e) {
			logger.error("APU组件拆换记录数据抽取异常！", e);
			
			failNum++;
			String pname = (String)NovaServerEnvironment.getInstance().get("PROJECT_NAME");
			String msg = pname +"执行APU组件拆换记录数据同步连续失败"+failNum+"次,请查看任务日志";
			
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
		String querySql = "SELECT MAX(UPDATEDY) UPDATEDAY FROM O_APUCOMP_REPAIRLOG ";
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
		
		logger.debug("当前记录最近的数据时间: " + updateStr);
		
		return updateday;		
	}
	
}

