package com.apms.bs.apu.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.apu.ApuRunLogCompute;
import com.apms.bs.apu.ApuSwapService;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.vo.ComBoxItemVO;
import smartx.framework.metadata.vo.Pub_Templet_1VO;
import smartx.framework.metadata.vo.RefItemVO;
import smartx.publics.form.bs.service.FormInterceptor;

/**
 * 服务端拦截器实现类
 */
public class ApuInsertAfter implements FormInterceptor {

	protected Logger logger = NovaLogger.getLogger(this.getClass());

	/**
	 * 单条数据新增、更新时，使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, Map<String, Object> dataValue) throws Exception {
		
		//新增APU时，同时新增APU的LLP部件信息
		String llpInsertSql = "INSERT INTO B_APU_LLPPARTS(ID,APUID,APUSN,STRUCTID,PART_AREA,PART_NAME,UPDATE_TIME)"
			+ " VALUES(S_B_APU_LLPPARTS.NEXTVAL,?,?,?,?,?,sysdate)";
		
		String queryStructSql = "SELECT ID,APU_AREA,PART_DESC FROM B_APU_LLP_STRUCT T WHERE APUMODEL_ID = ?";
		
		RefItemVO modelRef = (RefItemVO)dataValue.get("APUMODELID");
		String apuModelId = modelRef.getId();
		String apuId = dataValue.get("ID").toString();
		String apusn = dataValue.get("APUSN").toString();
		
		CommDMO dmo = new CommDMO();
		HashVO[] structs = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, queryStructSql, apuModelId);
		for(int i=0;i<structs.length;i++){
			HashVO svo = structs[i];
			String apuArea = svo.getStringValue("APU_AREA");
			String partDesc = svo.getStringValue("PART_DESC");
			String structid = svo.getStringValue("ID");
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, llpInsertSql, apuId,apusn,structid,apuArea,partDesc);
		}
		
		ComBoxItemVO positionItem = (ComBoxItemVO)dataValue.get("POSITION");
		if("1".equalsIgnoreCase(positionItem.getId()) ){
			//在翼的APU 进行初始化第一条APU日志信息
			ApuRunLogCompute runlog = new ApuRunLogCompute();
			runlog.initApuRunLog(apusn,null);
			
			//初始化一条拆换记录中装上信息
			
			new ApuSwapService().initApuInstallSwapLog(apusn);
			
		}
		
		logger.debug("APU新增前置拦截器执行完成！");
	}
	
	/**
	 * 列表数据删除，或列表数据新增、删除时使用
	 */
	public void doSomething(Pub_Templet_1VO templetVO, List<Map<String, Object>> dataValueList) throws Exception {
	}

	/**
	 * 不常用 ，待查证
	 */
	public void doSomething(Map<String, Object> map) throws Exception {
		
	}

	/**
	 * 待查证
	 */
	public void doSomething(List<Map<String, Object>> dataValueList) throws Exception {

	}

}
