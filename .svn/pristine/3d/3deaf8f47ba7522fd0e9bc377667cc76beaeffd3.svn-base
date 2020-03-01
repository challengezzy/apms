package com.apms.bs.datacompute.engine;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;


public class EngineComputeParamService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private static EngineComputeParamService paramService = null;
	
	private EngineComputeParamService() throws Exception {
		refreshCache();
    }

    public static EngineComputeParamService getInstance()  throws Exception {
        if (paramService != null) {
            return paramService;
        }
        paramService = new EngineComputeParamService();
        return paramService;
    }
	
	/** 参数计算值Map, key为组合值engmodel_args_argsvalue */
	private HashMap<String, Double> pvalueMap = new HashMap<String, Double>();
	
	public void refreshCache() throws Exception{
		String sql = "SELECT C.ID,ENGMODELID,ARGS,ARGSVALUE,CALVALUE,EM.MODEL" +
				" FROM B_ENGINE_COMPUTEMAP C,B_ENGINE_MODEL EM WHERE EM.ID=C.ENGMODELID";
		CommDMO dmo = new CommDMO();
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			String engModel = vo.getStringValue("MODEL");
			String args = vo.getStringValue("ARGS");
			String argsValue = vo.getStringValue("ARGSVALUE");
			Double calv = vo.getDoubleValue("CALVALUE");
			
			String key = engModel + "_" + args + "_" +argsValue;
			pvalueMap.put(key, calv);
		}
	}
	
	/**
	 * 从发动机计算参数映射表中获取计算参数值
	 * @param engModel
	 * @param args
	 * @param argsValue
	 * @return
	 */
	public double getParamValue(String engModel,String args,String argsValue){
		double calValue = 0;
		String key = engModel + "_" + args + "_" +argsValue;
		Double value = pvalueMap.get(key);
		if( value == null ){
			logger.warn("发动机计算参数key=["+key+"]未找到对应的配置值");
			calValue = 0;//未取到的值按0处理
		}else{
			calValue = value;
		}
		
		return calValue;
	}

}
