package com.apms.bs.intf.omis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import com.apms.ApmsConst;


/**
 * OMIS接口中DD单数据同步
 * 
 * @author jerry
 * @date Mar 10, 2014
 */
public class DDInfoExtractService {

	private Logger logger = NovaLogger.getLogger(this.getClass());

	private CommDMO dmo = new CommDMO();
	

	private String omis_dd_tablename = " AIRCHINA.DD_INFO ";//"omis_dd_info";AIRCHINA.DD_INFO
	private String issue_fploil_action=" AIRCHINA.ISSUE_FPLOIL_ACTION ";//AIRCHINA.ISSUE_FPLOIL_ACTION
	private String omis_datssource = ApmsConst.DS_OMIS;//ApmsConst.DS_APMS;omis_datssource
	
	public void extractDDInfo() throws Exception {
		try {

			// 接口同步过程
			// 1 查询出最新从OMIS系统同步数据的时间戳OPERATE_TIME
			String operate_time = getMaxOperateTime();
			
			if(operate_time == null){//第一次初始化化情况
				initDDInfoData();
			}
			
			// 2 从OMIS中查询所有大于OPERATE_TIME的DD单
			HashVO[] vos = queryForDD(operate_time);
			
			// 3 根据DD_NO进行比较，在APMS系统中存在两种情况 单据存在和单据不存在
			// 4 单据不存在，则直接新增到W_DD_INFO表中，数据来源标记为OMIS
			// 5 单据存在， 则更新信息
			// 6 更新的记录项，均要更新UPDATETIME为当前时间
			int num = 0;
			if(vos.length ==0){
				logger.debug("DD单中没有更新的数据。");
			}else{
				String sql=getInsertSql();
				for (int i = 0; i < vos.length; i++) {
					String dd_no = vos[i].getStringValue("dd_no");
					String querySql = "select 1 from w_dd_info a where a.dd_no=?";
					HashVO[] vos_tem = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS,querySql, dd_no);
					if (vos_tem.length<=0) {
						insertIntoDd_info(vos[i],sql);
						logger.info("单号为"+vos[i].getStringValue("DD_NO")+"]抽取成功！");
					} else {
						updateDd_info(vos[i],sql);
					}
					
					num++;
					if(num%500 ==0){
						dmo.commit(ApmsConst.DS_APMS);
					}
				}
			}
			dmo.commit(ApmsConst.DS_APMS);
			//DD单同步修改，有修改但operate_time没改变的情况
			extractDDInfoRevise();
			logger.info("抽取DD单数据结束...");
		} catch (Exception e) {
			logger.error("DD单数据抽取异常！", e);
			throw e;
		} finally {
			dmo.releaseContext(omis_datssource);
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	/**
	 * dd单同步修正
	 * @throws Exception
	 */
	private void extractDDInfoRevise()throws Exception{
		String logSql=getInsertLogSql();
		/*
		//1.action为DDINFOAUPD的数据
		StringBuilder querySql=new StringBuilder("SELECT DD_NO FROM "+omis_dd_tablename);
		querySql.append(" A  WHERE A.DD_NO=ANY( SELECT OLD_PK FROM "+issue_fploil_action);
		querySql.append(" B WHERE B.ISSUE_DATE > SYSDATE - 1  AND B.ACTION = 'DDINFOAUPD' )");
		querySql.append(" AND A.OPERATE_TIME<(SELECT MAX(ISSUE_DATE) FROM");
		querySql.append(" "+issue_fploil_action);
		querySql.append(" C  WHERE C.OLD_PK=A.DD_NO)");	
		HashVO[] vos=dmo.getHashVoArrayByDS(omis_datssource, querySql.toString());
		if(vos.length>0){
			List<String> listDel=new ArrayList<String>();
			List<String> listInt=new ArrayList<String>();
			for(int i=0;i<vos.length;i++){
				String sqlDel="DELETE FROM W_DD_INFO WHERE DD_NO='"+vos[i].getStringValue("DD_NO")+"'";//vos[i].getStringValue("DD_NO")
				listDel.add(sqlDel); 
				
				String sqlInt="SELECT NEW_PK FROM "+issue_fploil_action
				+" WHERE ISSUE_DATE=(SELECT MAX(ISSUE_DATE) FROM "+issue_fploil_action+" WHERE OLD_PK='"+vos[i].getStringValue("DD_NO")+"')";
				HashVO[] vo=dmo.getHashVoArrayByDS(omis_datssource, sqlInt);
				if(vo.length>0){
					listInt.add(vo[0].getStringValue("new_pk"));
				}
			}
			dmo.executeBatchByDS(ApmsConst.DS_APMS, listDel);//
			dmo.commit(ApmsConst.DS_APMS);
			HashVO[] vosInt =queryForDdByDd_no(listInt);
			String sql=getInsertSql();
			for(int i=0;i<vosInt.length;i++){
				insertIntoDd_info(vosInt[i],sql);
				insertIntoIssue_fploil_action(vosInt[i],"UPDATE",logSql);
			}
		}*/
		
		
		/*
		//2.issue_fploil_action中最近三天只新增的
		StringBuilder addedSql=new StringBuilder("SELECT NEW_PK DD_NO FROM "); 
		addedSql.append(issue_fploil_action);
		addedSql.append(" A WHERE A.ACTION='DDINFOAINT' AND ISSUE_DATE>SYSDATE-2");
		addedSql.append(" AND A.NEW_PK!=ALL(SELECT B.NEW_PK FROM ");
		addedSql.append(issue_fploil_action);
		addedSql.append(" B WHERE B.ACTION='DDINFOADLT' AND B.ISSUE_DATE>SYSDATE-2)");
		HashVO[] addedVo=dmo.getHashVoArrayByDS(omis_datssource, addedSql.toString());
		if(addedVo.length>0){
			List<String> listDel=new ArrayList<String>();
			List<String> listInt=new ArrayList<String>();
			for(int i=0;i<addedVo.length;i++){
				String sqlDel="DELETE FROM W_DD_INFO WHERE DD_NO='"+addedVo[i].getStringValue("DD_NO")+"'";
				listDel.add(sqlDel); 
				
				listInt.add(addedVo[i].getStringValue("DD_NO"));
			}
			dmo.executeBatchByDS(ApmsConst.DS_APMS, listDel);
			dmo.commit(ApmsConst.DS_APMS);
			HashVO[] vosInt =queryForDdByDd_no(listInt);
			String sql=getInsertSql();
			for(int i=0;i<vosInt.length;i++){
				insertIntoDd_info(vosInt[i],sql);
				insertIntoIssue_fploil_action(vosInt[i],"INSERT",logSql);
			}
		}
		*/
		//zhangzy 暂时只处理删除的DD单数据
		//3.issue_fploil_action中最近三天只删除的
		StringBuilder delSql=new StringBuilder("SELECT NEW_PK DD_NO,NULL OPERATE_TIME FROM "); 
		delSql.append(issue_fploil_action);
		delSql.append(" A WHERE A.ACTION='DDINFOADLT' AND ISSUE_DATE>SYSDATE-3");
		HashVO[] delVo=dmo.getHashVoArrayByDS(omis_datssource, delSql.toString());
		if(delVo.length>0){
			List<String> listDel=new ArrayList<String>();
			for(int i=0;i<delVo.length;i++){
				String sqlDel="DELETE FROM W_DD_INFO WHERE DD_NO='"+delVo[i].getStringValue("DD_NO")+"'";
				listDel.add(sqlDel); 
				insertIntoIssue_fploil_action(delVo[i],"DELETE",logSql);
			}
			dmo.executeBatchByDS(ApmsConst.DS_APMS, listDel);
			dmo.commit(ApmsConst.DS_APMS);
		}
		/*
		//4.issue_fploil_action中最近三天（删除和新增都有，但是删除时间>新增的）
		//首先找到删除和新增都有的
		StringBuilder delAndAddSql=new StringBuilder("SELECT DISTINCT NEW_PK DD_NO,NULL OPERATE_TIME FROM  "); 
		delAndAddSql.append(issue_fploil_action);
		delAndAddSql.append(" A where a.action='DDINFOADLT' AND ISSUE_DATE>SYSDATE-3");
		delAndAddSql.append(" AND EXISTS(SELECT 1 FROM ");
		delAndAddSql.append(issue_fploil_action);
		delAndAddSql.append(" B WHERE B.ACTION='DDINFOAINT' AND B.NEW_PK=A.NEW_PK AND B.ISSUE_DATE>SYSDATE-3) ");
		HashVO[] delAndAddVo=dmo.getHashVoArrayByDS(omis_datssource, delAndAddSql.toString());
		if(delAndAddVo.length>0){
			List<String> listDlt=new ArrayList<String>();
			for(int i=0;i<delAndAddVo.length;i++){
				//之后比较删除和新增的时间谁的更大，时间大的是最新更新的
				StringBuilder sql=new StringBuilder("SELECT 1 FROM ");
				sql.append(issue_fploil_action);
				sql.append(" A WHERE A.NEW_PK='");
				sql.append(delAndAddVo[i].getStringValue("DD_NO"));
				sql.append("' AND");
				sql.append(" (SELECT MAX(ISSUE_DATE) FROM");
				sql.append(issue_fploil_action);
				sql.append(" B WHERE B.ISSUE_DATE > SYSDATE - 3 AND B.ACTION = 'DDINFOAINT' AND B.NEW_PK = A.NEW_PK)");
				sql.append(" >");
				sql.append(" ( SELECT MAX(ISSUE_DATE) FROM");
				sql.append(issue_fploil_action);
				sql.append(" C WHERE C.ISSUE_DATE > SYSDATE - 3 AND C.ACTION = 'DDINFOADLT'AND C.NEW_PK = A.NEW_PK)");
				HashVO[] vos1=dmo.getHashVoArrayByDS(omis_datssource, sql.toString());
				if(vos1.length>0){
					String sqlDel="DELETE FROM W_DD_INFO WHERE DD_NO='"+delAndAddVo[i].getStringValue("DD_NO")+"'";
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, sqlDel);
					dmo.commit(ApmsConst.DS_APMS);
					HashVO[] vo=queryForDdByDd_no(delAndAddVo[i].getStringValue("DD_NO"));
					String sql2=getInsertSql();
					for(int j=0;j<vo.length;i++){
						insertIntoDd_info(vo[i],sql2);
						insertIntoIssue_fploil_action(vo[i],"INSERT",logSql);
					}
				}else{
					String sqlDel="DELETE FROM W_DD_INFO WHERE DD_NO='"+delAndAddVo[i].getStringValue("DD_NO")+"'";
					listDlt.add(sqlDel); 
					insertIntoIssue_fploil_action(delAndAddVo[i],"DELETE",logSql);
				}
			}
			dmo.executeBatchByDS(ApmsConst.DS_APMS, listDlt);
		}
		*/
		dmo.commit(omis_datssource);
		dmo.commit(ApmsConst.DS_APMS);
	}
	
	private void initDDInfoData() throws Exception{
		StringBuilder querySql=new StringBuilder("select DD_NO,FIRST_NO,FIRST_DATE,ARRANGE_DATE,AC_ID,AC_TYPE,WORK_NO,ENG_SN,INPUT_DATE, ");
		querySql.append(" TRANS_FROM,ISSUE_DATE,TOTAL_FH,TOTAL_CYCL,DEFECT_RPT,REPEAT,MATERIAL,ITEM_CODE,TARGET_DATE,TARGET_FH,");
		querySql.append(" TARGET_CYCL,REQ_GRANDT,REQ_MH,TECHNICIAN,INSPECTOR,DD_CLS,RECT,STATUS,TECHNICIAN_FINISH,INSPECTOR_FINISH, ");
		querySql.append(" FINISH_SIGN,EF_MAN,EF_DATE,KEEP_OTHERS,ITEM_NO,ATA_NO,POSITION,ALARM_FLAG,ALARM_FH,ALARM_FC,ALARM_DAY, ");
		querySql.append(" KEEP_DATE,KEEP_FH,KEEP_FC,APT_IATA,  ");
		querySql.append(" RELATIVE_DEP,");
		querySql.append(" CASE WHEN INSTR(RELATIVE_DEP,'签派')>0 THEN 1 ELSE 0 END 签派,");
		querySql.append(" CASE WHEN INSTR(RELATIVE_DEP,'飞行队')>0 THEN 1 ELSE 0 END 飞行队,");
		querySql.append(" CASE WHEN INSTR(RELATIVE_DEP,'客舱')>0 THEN 1 ELSE 0 END 客舱,");
		querySql.append(" CASE WHEN INSTR(RELATIVE_DEP,'机组')>0 THEN 1 ELSE 0 END 机组,");
		querySql.append(" MEL_CODE,SEVICE_SIGN,ACTION_SIGN,");
		querySql.append(" USE_LIMITED,");
		querySql.append(" CASE WHEN INSTR(USE_LIMITED,'减载')>0 THEN 1 ELSE 0 END 减载,");
		querySql.append(" CASE WHEN INSTR(USE_LIMITED,'减客')>0 THEN 1 ELSE 0 END 减客,");
		querySql.append(" CASE WHEN INSTR(USE_LIMITED,'高原航线')>0 THEN 1 ELSE 0 END 高原航线,");
		querySql.append(" CASE WHEN INSTR(USE_LIMITED,'类飞行')>0 THEN 1 ELSE 0 END 类飞行,");
		querySql.append(" CASE WHEN INSTR(USE_LIMITED,'RVSM')>0 THEN 1 ELSE 0 END RVSM,");
		querySql.append(" AMM_CODE,SRM_CODE,E_R,SYS_FLAG,PROPOSER,PROPOSED_DATE,APPROVER,APPROVED_DATE,MATERIAL_APPLY,MATERIAL_STATUS,");
		querySql.append(" TOOLS_STATUS,CHECK_PK,AOC_FLAG,PROPERTY_FLAG,PROPERTY_CLOSE,PROPERTY_DATE,PROPERTYER,PROPERTY_RESULT, ");
		querySql.append(" trim(M_FLAG) m_flag,M_DESC,DELAY_NUM,DD_NO_NEXT,NUM_INSTALL,NUM_BAD,NUM_RELEASE,OPERATE_USER,OPERATE_TIME ");
		querySql.append(" FROM " + omis_dd_tablename );
		
		dmo.executeImportByDS(omis_datssource, querySql.toString(), getFromCols(85), ApmsConst.DS_APMS, getInsertSql(), 3000);
		logger.debug("DD单中没有数据,全量初始化");
	}
	
	public int[] getFromCols(int length){
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i+1;
		
		return fromcols;
	}

	/**
	 * 插入DD单表m_dd_info
	 * 
	 * @param vo
	 * @throws Exception 
	 */
	public void insertIntoDd_info(HashVO vo,String sql) throws Exception{
		String rel_qianpai = "0";
		String rel_feixing = "0";
		String rel_kecang = "0";
		String rel_jizu = "0";
		String relative_dep=vo.getStringValue("relative_dep");
		if(relative_dep!=null && !"".equals(relative_dep) && !",".equals(relative_dep)){
			if(relative_dep.indexOf("签派")!=-1){
				rel_qianpai = "1";
			}
			
			if(relative_dep.indexOf("飞行队")!=-1){
				rel_feixing = "1";
			}
			
			if(relative_dep.indexOf("客舱")!=-1){
				rel_kecang = "1";
			}
			
			if(relative_dep.indexOf("机组")!=-1){
				rel_jizu = "1";	
			}
		}
		String rel_jianzai = "0";
		String rel_jianke = "0";
		String rel_hangxian = "0";
		String rel_2feixing = "0";
		String rel_RVSM = "0";
		String use_limited=vo.getStringValue("use_limited");
		if(use_limited!=null && !"".equals(use_limited) && !",".equals(use_limited)){
			if(use_limited.indexOf("减载")!=-1){
				rel_jianzai ="1";
			}
			if(use_limited.indexOf("减客")!=-1){
				rel_jianke="1";
			}
			if(use_limited.indexOf("高原航线")!=-1){
				rel_hangxian="1";
			}
			if(use_limited.indexOf("Ⅱ类飞行")!=-1){
				rel_2feixing="1";
			}
		    if(use_limited.indexOf("RVSM")!=-1){
		    	rel_RVSM="1";
		    }
		}
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,sql,vo.getStringValue("DD_NO"),vo.getStringValue("FIRST_NO"),vo.getDateValue("FIRST_DATE"),
			      vo.getDateValue("ARRANGE_DATE"),vo.getStringValue("AC_ID"),vo.getStringValue("AC_TYPE"),vo.getStringValue("WORK_NO"),
			      vo.getStringValue("ENG_SN"),vo.getDateValue("INPUT_DATE"),vo.getStringValue("TRANS_FROM"),vo.getDateValue("ISSUE_DATE"),
			      vo.getIntegerValue("TOTAL_FH"),vo.getIntegerValue("TOTAL_CYCL"),vo.getStringValue("DEFECT_RPT"),vo.getIntegerValue("REPEAT"),
			      vo.getIntegerValue("MATERIAL"),vo.getStringValue("ITEM_CODE"),vo.getDateValue("TARGET_DATE"),vo.getIntegerValue("TARGET_FH"),
			      vo.getIntegerValue("TARGET_CYCL"),vo.getIntegerValue("REQ_GRANDT"),vo.getIntegerValue("REQ_MH"),vo.getStringValue("TECHNICIAN"),
			      vo.getStringValue("INSPECTOR"),vo.getStringValue("DD_CLS"),vo.getStringValue("RECT"),vo.getStringValue("STATUS"),
			      vo.getStringValue("TECHNICIAN_FINISH"),vo.getStringValue("INSPECTOR_FINISH"),vo.getIntegerValue("FINISH_SIGN"),vo.getStringValue("EF_MAN"),
			      vo.getDateValue("EF_DATE"),vo.getStringValue("KEEP_OTHERS"),vo.getStringValue("ITEM_NO"),vo.getStringValue("ATA_NO"),
			      vo.getStringValue("POSITION"),vo.getIntegerValue("ALARM_FLAG"),vo.getIntegerValue("ALARM_FH"),vo.getIntegerValue("ALARM_FC"),
			      vo.getIntegerValue("ALARM_DAY"),vo.getIntegerValue("KEEP_DATE"),vo.getIntegerValue("KEEP_FH"),vo.getIntegerValue("KEEP_FC"),
			      vo.getStringValue("APT_IATA"),vo.getStringValue("RELATIVE_DEP"),
			      rel_qianpai,rel_feixing,rel_kecang,rel_jizu,
			      vo.getStringValue("MEL_CODE"),vo.getIntegerValue("SEVICE_SIGN"),vo.getIntegerValue("ACTION_SIGN"),vo.getStringValue("USE_LIMITED"),
			      rel_jianzai,rel_jianke,rel_hangxian,rel_2feixing,rel_RVSM,
			      vo.getStringValue("AMM_CODE"),vo.getStringValue("SRM_CODE"),vo.getStringValue("E_R"),vo.getStringValue("SYS_FLAG"),
			      vo.getStringValue("PROPOSER"),vo.getDateValue("PROPOSED_DATE"),vo.getStringValue("APPROVER"),vo.getDateValue("APPROVED_DATE"),
			      vo.getStringValue("MATERIAL_APPLY"),vo.getStringValue("MATERIAL_STATUS"),vo.getStringValue("TOOLS_STATUS"),vo.getStringValue("CHECK_PK"),
			      vo.getStringValue("AOC_FLAG"),vo.getStringValue("PROPERTY_FLAG"),vo.getStringValue("PROPERTY_CLOSE"),vo.getDateValue("PROPERTY_DATE"),
			      vo.getStringValue("PROPERTYER"),vo.getStringValue("PROPERTY_RESULT"),vo.getStringValue("M_FLAG"),vo.getStringValue("M_DESC"),
			      vo.getIntegerValue("DELAY_NUM"),vo.getStringValue("DD_NO_NEXT"),vo.getIntegerValue("NUM_INSTALL"),vo.getIntegerValue("NUM_BAD"),
			      vo.getIntegerValue("NUM_RELEASE"),vo.getStringValue("OPERATE_USER"),vo.getDateValue("OPERATE_TIME"));
	
		
	}
	/**
	 * 插入对于dd单增删改的记录表issue_fploil_action
	 * @param vo
	 * @throws Exception 
	 */
	public void insertIntoIssue_fploil_action(HashVO vo,String action,String sql) throws Exception{
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,sql,vo.getStringValue("DD_NO"),action,vo.getDateValue("OPERATE_TIME"));
	
		
	}
	public String getInsertSql(){
		String sql="insert into w_dd_info(ID,DD_NO,FIRST_NO,FIRST_DATE,ARRANGE_DATE,AC_ID,AC_TYPE,WORK_NO,ENG_SN,INPUT_DATE"        
			+",TRANS_FROM,ISSUE_DATE,TOTAL_FH,TOTAL_CYCL,DEFECT_RPT,REPEAT,MATERIAL,ITEM_CODE,TARGET_DATE,TARGET_FH,TARGET_CYCL,REQ_GRANDT,REQ_MH"  
			+",TECHNICIAN,INSPECTOR,DD_CLS,RECT,STATUS,TECHNICIAN_FINISH,INSPECTOR_FINISH,FINISH_SIGN,EF_MAN,EF_DATE,KEEP_OTHERS,ITEM_NO,ATA_NO" //36 
			+",POSITION,ALARM_FLAG,ALARM_FH,ALARM_FC,ALARM_DAY,KEEP_DATE,KEEP_FH ,KEEP_FC ,APT_IATA,RELATIVE_DEP,RELDEP_QIANPAI,RELDEP_FEIXINGDUI" //12
			+",RELDEP_KECANG,RELDEP_JIZU,MEL_CODE,SEVICE_SIGN,ACTION_SIGN,USE_LIMITED,LIMIT_JIANZAI,LIMIT_JIANKE,LIMIT_GAOYUAN,LIMIT_ERLEIFLY,LIMIT_RVSM,AMM_CODE"//12
			+",SRM_CODE,E_R,SYS_FLAG,PROPOSER,PROPOSED_DATE,APPROVER,APPROVED_DATE,MATERIAL_APPLY,MATERIAL_STATUS,TOOLS_STATUS,CHECK_PK,AOC_FLAG,PROPERTY_FLAG" //12    
			+",PROPERTY_CLOSE,PROPERTY_DATE,PROPERTYER,PROPERTY_RESULT,M_FLAG,M_DESC,DELAY_NUM,DD_NO_NEXT,NUM_INSTALL,NUM_BAD,NUM_RELEASE,OPERATE_USER,OPERATE_TIME"//13      
			+",DATASOURCE,UPDATETIME)" //2//87
			+"values(S_W_DD_INFO.nextval,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
		    +"?,?,?,?,"
		    +"?,?,?,?,?,"
		    +"?,?,?,"
			+"?,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,?,"
			+"?,?,?,?,?,?,?,?,"
			+"0,sysdate)";
		return sql;
	}
	public String getInsertLogSql(){
		String sql="insert into issue_fploil_action"
			+"(id, dd_no, action, operate_time, updatetime)"
			+" values(S_ISSUE_FPLOIL_ACTION.NEXTVAL, ?, ?, ?, sysdate)";
		return sql;
	}
	/**
	 * 更新表m_dd_info
	 * 
	 * @param vo
	 * @throws Exception 
	 */
	public void updateDd_info(HashVO vo,String sql) throws Exception {
		String sqlDel="delete from w_dd_info where DD_NO= ?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS,sqlDel, vo.getStringValue("dd_no"));
		insertIntoDd_info(vo,sql);
		logger.info("单号为"+vo.getStringValue("DD_NO")+"]更新成功！");
	}

	/**
	 * 从OMIS中查询所有大于OPERATE_TIME的DD单
	 * 
	 * @return
	 * @throws Exception
	 */
	public HashVO[] queryForDD(String timeStr) throws Exception {
		HashVO[] vos;
		StringBuilder querySql=new StringBuilder("select  dd_no, first_no, first_date, arrange_date, ac_id, ac_type, work_no, eng_sn, input_date, ");
		querySql.append("trans_from, issue_date, total_fh, total_cycl, defect_rpt, repeat, material, item_code, target_date, ");
		querySql.append("target_fh, target_cycl, req_grandt, req_mh, technician, inspector, dd_cls, rect, status, ef_date, ");
		querySql.append("technician_finish, inspector_finish, ef_man, finish_sign, keep_others, item_no, ata_no, position, ");
		querySql.append("alarm_fh, alarm_fc, alarm_day, keep_date, keep_fh, keep_fc, alarm_flag, apt_iata, relative_dep, mel_code,");
		querySql.append(" sevice_sign, action_sign, use_limited, amm_code, srm_code, e_r, operate_user, operate_time, sys_flag, ");
		querySql.append("proposer, proposed_date, approver, approved_date, material_apply, material_status, tools_status, check_pk, ");
		querySql.append("aoc_flag, property_flag, property_close, property_date, propertyer, property_result, trim(M_FLAG) m_flag, m_desc,");//M_FLAG可能包含空格
		querySql.append(" delay_num, dd_no_next, num_install, num_bad, num_release  from ");
		querySql.append( omis_dd_tablename );//or ef_date>to_date('"+ timeStr + "','yyyy-MM-dd HH24:MI:SS')
		querySql.append(" where operate_time>to_date('"+ timeStr + "','yyyy-MM-dd HH24:MI:SS')");
		querySql.append(" order by operate_time asc  ");
		vos = dmo.getHashVoArrayByDSUnlimitRows(omis_datssource, querySql.toString());
		return vos;
	}
	/**
	 * 从OMIS中查询所有在该List中包含dd_no的DD单
	 * 
	 * @return
	 * @throws Exception
	 */
	public HashVO[] queryForDdByDd_no(List<String> dd_noList) throws Exception {
		HashVO[] vos=null;
		if(!dd_noList.isEmpty()){
			StringBuilder querySql=new StringBuilder("select  dd_no, first_no, first_date, arrange_date, ac_id, ac_type, work_no, eng_sn, input_date, ");
			querySql.append("trans_from, issue_date, total_fh, total_cycl, defect_rpt, repeat, material, item_code, target_date, ");
			querySql.append("target_fh, target_cycl, req_grandt, req_mh, technician, inspector, dd_cls, rect, status, ef_date, ");
			querySql.append("technician_finish, inspector_finish, ef_man, finish_sign, keep_others, item_no, ata_no, position, ");
			querySql.append("alarm_fh, alarm_fc, alarm_day, keep_date, keep_fh, keep_fc, alarm_flag, apt_iata, relative_dep, mel_code,");
			querySql.append(" sevice_sign, action_sign, use_limited, amm_code, srm_code, e_r, operate_user, operate_time, sys_flag, ");
			querySql.append("proposer, proposed_date, approver, approved_date, material_apply, material_status, tools_status, check_pk, ");
			querySql.append("aoc_flag, property_flag, property_close, property_date, propertyer, property_result, trim(M_FLAG) m_flag, m_desc,");//M_FLAG可能包含空格
			querySql.append(" delay_num, dd_no_next, num_install, num_bad, num_release  from ");
			querySql.append( omis_dd_tablename );//or ef_date>to_date('"+ timeStr + "','yyyy-MM-dd HH24:MI:SS')
			querySql.append(" where 1=1 and dd_no=any(");
			for(int i=0;i<dd_noList.size();i++){
				if(i==0){
					querySql.append("'");
					querySql.append(dd_noList.get(i));
					querySql.append("'");
				}else{
					querySql.append(",'");
					querySql.append(dd_noList.get(i));
					querySql.append("'");
				}
			}
			querySql.append(" ) order by operate_time asc  ");
			//querySql=querySql.append(" where dd_no= any('026346','CD0004897','CD0004898','2007327')");
			vos = dmo.getHashVoArrayByDSUnlimitRows(omis_datssource, querySql.toString());
		}
		
		return vos;
	}
	/**
	 * 从OMIS中查询所有在该List中包含dd_no的DD单
	 * 
	 * @return
	 * @throws Exception
	 */
	public HashVO[] queryForDdByDd_no(String dd_no) throws Exception {
		HashVO[] vos=null;
		StringBuilder querySql=new StringBuilder("select  dd_no, first_no, first_date, arrange_date, ac_id, ac_type, work_no, eng_sn, input_date, ");
		querySql.append("trans_from, issue_date, total_fh, total_cycl, defect_rpt, repeat, material, item_code, target_date, ");
		querySql.append("target_fh, target_cycl, req_grandt, req_mh, technician, inspector, dd_cls, rect, status, ef_date, ");
		querySql.append("technician_finish, inspector_finish, ef_man, finish_sign, keep_others, item_no, ata_no, position, ");
		querySql.append("alarm_fh, alarm_fc, alarm_day, keep_date, keep_fh, keep_fc, alarm_flag, apt_iata, relative_dep, mel_code,");
		querySql.append(" sevice_sign, action_sign, use_limited, amm_code, srm_code, e_r, operate_user, operate_time, sys_flag, ");
		querySql.append("proposer, proposed_date, approver, approved_date, material_apply, material_status, tools_status, check_pk, ");
		querySql.append("aoc_flag, property_flag, property_close, property_date, propertyer, property_result, trim(M_FLAG) m_flag, m_desc,");//M_FLAG可能包含空格
		querySql.append(" delay_num, dd_no_next, num_install, num_bad, num_release  from ");
		querySql.append( omis_dd_tablename );//or ef_date>to_date('"+ timeStr + "','yyyy-MM-dd HH24:MI:SS')
		querySql.append(" where dd_no='");
		querySql.append(dd_no);
		querySql.append("' ");
		//querySql=querySql.append(" where dd_no= any('026346','CD0004897','CD0004898','2007327')");
		vos = dmo.getHashVoArrayByDSUnlimitRows(omis_datssource, querySql.toString());
		
		return vos;
	}

	/**
	 * 查询出从OMIS系统中导入的最新DD单据的更新时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getMaxOperateTime() throws Exception {
		String operate_time;
		String querySql = "SELECT MAX(OPERATE_TIME) operate_time FROM W_DD_INFO D WHERE D.DATASOURCE = 0 AND D.OPERATE_TIME > SYSDATE - 9999";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);

		if(vos.length==0){
			operate_time =null;
			}
		else{
				operate_time = vos[0].getStringValue("operate_time");
			}
		
		logger.debug("本地库中的最大操作时间为: " + operate_time);

		return operate_time;
	}

}
