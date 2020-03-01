package com.sep.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.utils.FileUtil;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.publics.form.vo.InitMetaDataConst;

import com.apms.ApmsConst;
import com.apms.bs.common.HashVoUtil;

public class SepDataService {
	protected  Logger logger = NovaLogger.getLogger(this.getClass());
	
	private  CommDMO dmo = new CommDMO();
	
	
	/**
	 * 获取A3提示卡的打印数据
	 * @param cardId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getA3CardData(String cardId) throws Exception{

		Map<String, Object> resMap = new HashMap<String, Object>(64);
		
		String cardSql = "SELECT * FROM V_JOBCARD_BASIC WHERE ID= "+cardId;
		HashVO[] cardvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, cardSql );
		Map<String, Object> jobcardbasic = HashVoUtil.hashVoToMap(cardvos[0]);
		resMap.put("jobcardbasic", jobcardbasic);
		
		//查找工卡的使用飞机型号
		String siutlistSql = "select (select m.modelcode from b_aircraft_model m where id = s.acmodelid) modelcode " +
							"from u_jobcard_suitlist s where s.jobcardid = " +cardId;
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, siutlistSql);
		String siutlist="";
		if(vos.length>0){
			for(int i=0;i<vos.length;i++){
				if(i== vos.length-1){
					siutlist = siutlist + vos[i].getStringValue("modelcode");
				}else{
					siutlist =siutlist + vos[i].getStringValue("modelcode")+",";
				}
			}
		}
		resMap.put("suitlist",siutlist);
		 
		//查找工卡风险点关键点及对应的图片和差错历史
		String keypointSql ="select id,k.detaildesc,k.adaptordesc,k.checktype " +
				" from U_JOBCARD_KEYPOINT k where k.jobcardid = " + cardId +" order by SEQNUM";
		HashVO[] vos1 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, keypointSql);
		String  path = InitMetaDataConst.RootPath;
		resMap.put("path",path);

		String  filePath = InitMetaDataConst.RootPath  + "imageCache/"  ;
		FileUtil.createDirIfNotExists(filePath);
		if(vos1.length>0){
			for(int i=0;i<vos1.length;i++){
				String keypointId = vos1[i].getStringValue("id");
				String tempSql = "select MATERIALFILE,NAME from U_JOBCARD_KEYPOINT_MET where keypointid = " + keypointId + " " +
						"and FILESUFFIXES in('jpg','png','jpeg') and rownum<=3 order by seq ";
				HashVO[] vos2 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, tempSql);
				if(vos2.length>0){
					for(int j=0;j<vos2.length;j++){
						byte[] bytes = vos2[j].getBytesValue("MATERIALFILE");
						String fileName = vos2[j].getStringValue("NAME");
						File file = new File(filePath+fileName);
						if(!file.exists()){
							FileOutputStream fstream = new FileOutputStream(file);
							BufferedOutputStream stream = new BufferedOutputStream(fstream);
							stream.write(bytes);
						}
						vos1[i].setAttributeValue("imagePath"+(j+1), filePath+fileName);

					}
				}
				
				String tempSql2 = "select ERRORDESC from U_JOBCARD_ERRORHIS where KEYPOINTID =" +keypointId+" and ISSHOWA3 =1";
				HashVO[] vos3 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, tempSql2);
				if(vos3.length>0){
					for(int k=0;k<vos3.length;k++){
						String errhisText = vos3[k].getStringValue("ERRORDESC");
						vos1[i].setAttributeValue("errhisText"+(k+1), errhisText);
					}
				}
				
			}
		}
		
		List<Map<String,Object>> keypointList = new ArrayList<Map<String,Object>>();
	    keypointList.addAll(HashVoUtil.hashVosToMapList(vos1));
	    resMap.put("keypointList",keypointList);
	    
	    String toolsListSqlts  = "select name,spec,count,remark,partno from u_jobcard_tool t where type = 1 and JOBCARDID = " + cardId;
		HashVO[] vosts = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, toolsListSqlts);
		List<Map<String,Object>> toolsListts = new ArrayList<Map<String,Object>>();
		toolsListts.addAll(HashVoUtil.hashVosToMapList(vosts));
		resMap.put("toolsListts",toolsListts);
		
		String toolsListSqlyb  = "select name,spec,count,remark,partno from u_jobcard_tool t where type = 0 and JOBCARDID = " + cardId; 
		HashVO[] vosyb = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, toolsListSqlyb);
		List<Map<String,Object>> toolsListyb = new ArrayList<Map<String,Object>>();
		toolsListyb.addAll(HashVoUtil.hashVosToMapList(vosyb));
		resMap.put("toolsListyb",toolsListyb);
		
		String chemiacalSql = "select name,num,sapnum,repnum,remark from u_jobcard_chemiacal  where jobcardid=" +cardId;
		HashVO[] vos2 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, chemiacalSql);
		List<Map<String,Object>> chemiacalList = new ArrayList<Map<String,Object>>();
		chemiacalList.addAll(HashVoUtil.hashVosToMapList(vos2));
		resMap.put("chemiacalList",chemiacalList);
		
		String airmaterialSql = "select partno,name,repnum,sapnum,amount,ipc,amount from u_jobcard_airmaterial where jobcardid=" +cardId;
		HashVO[] vosm = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, airmaterialSql);
		List<Map<String,Object>> amlist = new ArrayList<Map<String,Object>>();
		amlist.addAll(HashVoUtil.hashVosToMapList(vosm));
		resMap.put("airmaterialList",amlist);
		
		return resMap;
	}
	
	/**
	 * 获取非例行工单的打印信息
	 * @param cardId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getNrCardData(String cardId) throws Exception{

		Map<String, Object> resMap = new HashMap<String, Object>(64);
		
		String cardSql = "SELECT ID, CARDNO, ATA, ACNUM, ACTYPE, CHECKTYPE, SKILL, WORKORDERNO, ORIGINATINGDOC"
			+ ", FAULTMARKNO, FAULTMARKZONE, FAULTDESC, REFDOC_REV, GROUNDTIME, PLAN_MH, ACT_MH" 
			+ ", ISEXCEEDLIMIT, REPCLASS, ISSECURITYCHECK,CHECKCONTENT, PREPARED_USER, APPROVED_USER, APPROVED_DATE" 
			+ ", TO_CHAR(APPROVED_DATE,'YYYY/MM/DD') APPROVEDDATE_STR" 
			+ ", STATION, UPDATETIME, UPDATEUSER " 
			+ " FROM U_NONROUTINE_JOBCARD WHERE ID = " + cardId ;
		HashVO[] cardvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, cardSql );
		Map<String, Object> jobcardbasic = HashVoUtil.hashVoToMap(cardvos[0]);
		resMap.put("nrcard", jobcardbasic);
		
		//更换件
		String partSql = "SELECT ID, NRCARDID, ITEM, PARTNAME, QTY, PARTNO_NO, BATCHNO_NO, PARTNO_OFF, BATCHNO_OFF FROM U_NR_JOBCARD_PARTS" +
				" where NRCARDID =" +cardId + " ORDER BY ITEM ";;
		HashVO[] parts = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, partSql);
		List<Map<String,Object>> partList = new ArrayList<Map<String,Object>>();
		partList.addAll(HashVoUtil.hashVosToMapList(parts));
		resMap.put("partList",partList);
		
		//工具
		String toolSql ="SELECT ID, NRCARDID, ITEM, NAME, PARTNO, QTY, REMARK FROM U_NR_JOBCARD_TOOLS " 
				+ " WHERE NRCARDID = " + cardId +" order by ITEM";
		HashVO[] tools = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, toolSql);
		List<Map<String,Object>> toolList = new ArrayList<Map<String,Object>>();
		toolList.addAll(HashVoUtil.hashVosToMapList(tools));
		resMap.put("toolList",toolList);

		//器材
		String airmaterialSql = "SELECT ID, NRCARDID, ITEM, NAME, PARTNO, IPC, QTY, REMARK FROM U_NR_JOBCARD_MATERIALS" +
				" where NRCARDID =" +cardId + " ORDER BY ITEM ";
		HashVO[] ams = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, airmaterialSql);
		List<Map<String,Object>> amlist = new ArrayList<Map<String,Object>>();
		amlist.addAll(HashVoUtil.hashVosToMapList(ams));
		resMap.put("materialList",amlist);
		
		//工作步骤
		String actionSql = "SELECT ID, NRCARDID, ITEM, RECTIFY_ACTION, MECH, INSP FROM U_NR_JOBCARD_ACTION" +
				" where NRCARDID =" +cardId + " ORDER BY ITEM ";;
		HashVO[] actions = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, actionSql);
		List<Map<String,Object>> actionlist = new ArrayList<Map<String,Object>>();
		actionlist.addAll(HashVoUtil.hashVosToMapList(actions));
		resMap.put("actionList",actionlist);
		
		return resMap;
	}
	
}
