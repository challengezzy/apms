package com.apms.cache;

import java.util.HashMap;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.StringUtil;
import com.apms.cache.vo.DelayCodeVo;

/**
 * 航班相关缓存信息
 * @author jerry
 * @date Nov 3, 2014
 */
public class FlightCache {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static FlightCache flightCache = null;
	
	private CommDMO dmo = new CommDMO();
	
	/** 延误代码  <代码,延误对象> */
	private HashMap<String, DelayCodeVo> delaycodeMap = new HashMap<String, DelayCodeVo>();
	
	public static FlightCache getInstance() {
        if (flightCache != null) {
            return flightCache;
        }
        flightCache = new FlightCache();
        
        return flightCache;
    }
	
	/** 缓存刷新 */
	public void refreshCache() throws Exception{
		refreshDelayCodeCache();
		
	}

	/** 刷新延误代码缓存信息 */
	public void refreshDelayCodeCache() throws Exception{
		String sql = "select id, serial_no, catalog, delaycode, delaydesc, sortseq, innerflag  from b_flight_delaycode ";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		
		for(HashVO vo : vos){
			DelayCodeVo delayVo = new DelayCodeVo();
			delayVo.setId(vo.getStringValue("id"));
			delayVo.setSerial_no(vo.getStringValue("serial_no"));
			delayVo.setCatalog(vo.getStringValue("catalog"));
			delayVo.setDelaycode(vo.getStringValue("delaycode"));
			delayVo.setDelaydesc(vo.getStringValue("delaydesc"));
			delayVo.setSortseq(vo.getStringValue("sortseq"));
			delayVo.setInnerflag(vo.getBooleanValue("innerflag"));
			
			delaycodeMap.put(delayVo.getDelaycode(), delayVo);
		}
		logger.info("航班延误代码缓存初始化完成！");
	}
	
	public String getDelayReason(String delaycode){
		
		if(delaycode == null){
			return "";
		}
		
		String code = StringUtil.replaceAll(delaycode, "*", "");
		DelayCodeVo delayvo = delaycodeMap.get(code);
		if(delayvo == null){
			return "";
		}else{
			return delayvo.getDelaydesc();
		}
	}
	
}
