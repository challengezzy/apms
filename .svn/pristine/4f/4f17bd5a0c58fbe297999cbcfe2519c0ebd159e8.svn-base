package com.apms.bs.apu;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.apu.vo.ApuAcnumTimeScopeVo;
import com.apms.bs.util.DateUtil;
import com.apms.vo.ApmsVarConst;

/**
 * APU拆换记录缓存信息
 * @author jerry
 * @date Apr 23, 2017
 */
public class ApuSwapLogCacheService {
	
	private static ApuSwapLogCacheService apuSwapLogCacheService;
	
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	/**
	 * APU与飞机号的不同时时间段的对应关系表
	 */
	public Map<String, List<ApuAcnumTimeScopeVo>> apuAcnumMapList = new HashMap<String, List<ApuAcnumTimeScopeVo>>();
	
	private CommDMO dmo = new CommDMO();
	
	private Date dateMin = DateUtil.StringToDate("2000-01-01", "yyyy-MM-dd");
	private Date dateMax = DateUtil.StringToDate("2099-01-01", "yyyy-MM-dd");
	
	private ApuSwapLogCacheService(){
	}
	
	public static ApuSwapLogCacheService getInstance(){
		if(apuSwapLogCacheService == null){
			apuSwapLogCacheService = new ApuSwapLogCacheService();
		}
		
		return apuSwapLogCacheService;
	}
	
	/**
	 * 根据apusn和时间，查询在那个时间段时的机号
	 * @param apusn
	 * @param time
	 * @return
	 */
	public ApuAcnumTimeScopeVo getScopeVoApuTime(String apusn,Date time){
		ApuAcnumTimeScopeVo scopeVo = null;
		
		try{
			List<ApuAcnumTimeScopeVo> timeScopeList = getAcnumTimeScopList(apusn);
			
			if(timeScopeList == null || timeScopeList.size() < 1){
				return null;
			}
			
			int i= timeScopeList.size()-1;
			//倒序查找,一般情况下都是最新的报文，包含在最后一条记录中
			for(;i>=0;i--){
				ApuAcnumTimeScopeVo tempVo = timeScopeList.get(i);
				//判断是否是装-拆对应的时间范围内
				if( tempVo.isInScope(time) ){
					scopeVo = tempVo;
					break;
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询APU与ACNUM在历史时间段的对应关系失败!",e);
		}
		
		return scopeVo;
	}
	
	/**
	 * 从缓存中删除，通常有新的拆换记录的情况下，需要做此操作
	 * @param apusn
	 */
	public void deleteApuTimeScopeListCache(String apusn){
		apuAcnumMapList.remove(apusn);
	}
	
	/**
	 * 根据apusn查询该apu的所有时间段的拆换记录
	 * @param apusn
	 * @return
	 */
	public List<ApuAcnumTimeScopeVo> getAcnumTimeScopList(String apusn) throws Exception{
		List<ApuAcnumTimeScopeVo> timeScopeList = apuAcnumMapList.get(apusn);
		
		//如果没有获取到拆换的对应记录，则说明没有该APU的拆换记录 或者没有初始化
		if(timeScopeList == null){
			addAcnumTimeScopList(apusn);
		}
		
		return apuAcnumMapList.get(apusn);
	}
	
	private void addAcnumTimeScopList(String apusn) throws Exception{
		
		StringBuilder sb = new StringBuilder("SELECT L.SWAP_DATE,A.APUSN,L.SWAP_ACTION");
		sb.append(",(SELECT T.AIRCRAFTSN FROM B_AIRCRAFT T WHERE T.ID=L.AIRCRAFTID) ACNUM,L.ID");
		sb.append(",L.TIME_ONINSTALL,L.CYCLE_ONINSTALL,L.TIME_ONREPAIRED,L.CYCLE_ONREPAIRED");
		sb.append(",L.APUID,L.APUMODELID,L.REPAIR_FLAG");
		sb.append("  FROM L_APU_SWAPLOG L,B_APU A ");
		sb.append(" WHERE A.ID=L.APUID AND A.APUSN = ?  ORDER BY L.SWAP_DATE ASC ");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), apusn);
		List<ApuAcnumTimeScopeVo> timeScopeList = new ArrayList<ApuAcnumTimeScopeVo>(vos.length);
		
		//数据一定是装-拆-装-拆循环出现的
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			String acnum = vo.getStringValue("ACNUM");
			String swapAction = vo.getStringValue("SWAP_ACTION");
			Date swapDate = vo.getDateValue("SWAP_DATE");
			
			ApuAcnumTimeScopeVo scopeVo = new ApuAcnumTimeScopeVo();
			scopeVo.setAcnum(acnum);
			scopeVo.setApusn(apusn);
			scopeVo.setTimeOninstall(vo.getDoubleValue("TIME_ONINSTALL"));
			scopeVo.setCycleOninstall(vo.getDoubleValue("CYCLE_ONINSTALL"));
			scopeVo.setTimeOnrepaired(vo.getDoubleValue("TIME_ONREPAIRED"));
			scopeVo.setCycleOnrepaired(vo.getDoubleValue("CYCLE_ONREPAIRED"));
			
			scopeVo.setApuId(vo.getStringValue("APUID"));
			scopeVo.setApuModelId(vo.getStringValue("APUMODELID"));
			scopeVo.setRepairFlag(vo.getStringValue("REPAIR_FLAG"));
			
			//如果第一条记录为拆下，则向前回溯一条scopeVo记录
			if(i==0 && ApmsVarConst.SWAP_ACTION_REMOVE.equals(swapAction)){
				scopeVo.setRemovetime(swapDate);
				scopeVo.setInstallTime(dateMin);
				
				timeScopeList.add(scopeVo);
				continue;
			}
			//最后一条记录为装上,如拆下日期为最大值
			if(i==vos.length-1 && ApmsVarConst.SWAP_ACTION_INSTALL.equals(swapAction)){
				scopeVo.setRemovetime(dateMax);
				scopeVo.setInstallTime(swapDate);
				
				timeScopeList.add(scopeVo);
				continue;
			}
			//不处理拆下
			if(ApmsVarConst.SWAP_ACTION_REMOVE.equals(swapAction)){
				continue;
			}
			
			//代码走到这里一定是装上的记录了
			HashVO vor = vos[i+1];
			Date rmDate = vor.getDateValue("SWAP_DATE");
			scopeVo.setInstallTime(swapDate);
			scopeVo.setRemovetime(rmDate);
			
			timeScopeList.add(scopeVo);
		}
		//放入缓存
		apuAcnumMapList.put(apusn, timeScopeList);
	}

}
