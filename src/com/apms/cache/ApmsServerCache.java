package com.apms.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.apms.bs.maindata.MainDataQueryService;
import com.apms.cache.vo.AirCraftVo;
import com.apms.cache.vo.AirPortVo;
import com.apms.cache.vo.OrganizationVo;
import com.apms.cache.vo.UserVo;

/**
 * 服务端数据缓存类
 * 缓存机场、飞机等信息
 * @author jerry
 * @date Mar 15, 2014
 */
public class ApmsServerCache {
	
	private static ApmsServerCache serverCache = null;
	
	/**  维修站点列表,机场三字码  */
	private ArrayList<String> maintainAptList = new ArrayList<String>();
	/** 维修站点映射 */
	private HashMap<String, AirPortVo> maintainApMap = new HashMap<String, AirPortVo>();
	/** 维修站点三字码 */
	private HashMap<String, String> maintainApCodeMap = new HashMap<String, String>();
	
	/** 维护飞机列表 ,飞机号*/
	private ArrayList<String> maintainACList = new ArrayList<String>();
	
	/** 飞机信息 <飞机号,飞机> */
	private HashMap<String, AirCraftVo> acnumMap = new HashMap<String, AirCraftVo>();
	/** 用户名、用户 */
	private HashMap<String, UserVo> userNameMap = new HashMap<String, UserVo>();
	/** 用户登录名、用户 */
	private HashMap<String, UserVo> userLoginNameMap = new HashMap<String, UserVo>();
	
	private MainDataQueryService dataQueryService = new MainDataQueryService();
	
	private ApmsServerCache() {
    }

    public static ApmsServerCache getInstance() {
        if (serverCache != null) {
            return serverCache;
        }
        serverCache = new ApmsServerCache();
        return serverCache;
    }
	
	/** 缓存刷新 */
	public void refreshCache() throws Exception{
		refreshMaintainAptList();
		
		refreshUserCache();
		refreshAirCraftCache();
	}
	
	public void refreshMaintainAptList() throws Exception{
		//刷新维修机场列表
		ArrayList<OrganizationVo> lineOrgList = dataQueryService.getLineOrgAirport();
		
		for(OrganizationVo org : lineOrgList){
			//TODO 初始化机场
			maintainApMap.put(org.getAirportCode3(), new AirPortVo());
			maintainApCodeMap.put(org.getAirportCode3(), org.getAirportCode3());
		}
		
		maintainAptList.clear(); //先清空
		Iterator<String> iter = maintainApMap.keySet().iterator();
		while(iter.hasNext()){
			String apcode = iter.next();
			maintainAptList.add(apcode);
		}
	}
	
	/** 刷新用户相关缓存信息 */
	public void refreshUserCache() throws Exception{
		ArrayList<UserVo> userList = dataQueryService.getValidUserList();
		
		
		for(UserVo user : userList){
			userNameMap.put(user.getName(), user);
			userLoginNameMap.put(user.getLoginname(), user);
		}
	}
	/** 刷新飞机相关缓存信息 */
	public void refreshAirCraftCache() throws Exception{
		ArrayList<AirCraftVo> airCriftList = dataQueryService.getAircriftList();
		
		
		for(AirCraftVo airCrift : airCriftList){
			acnumMap.put(airCrift.getAcnum(), airCrift);
		}
	}
	/** 获取维修机场列表 */
	public ArrayList<String> getMaintainAptList(){
		return maintainAptList;
	}
	
	/** 获取维修飞机列表 */
	public ArrayList<String> getMaintainACList(){
		return maintainACList;
	}
	
	public AirPortVo getAirPortVoByCode3(String code3){
		return maintainApMap.get(code3);
	}
	
	/**
	 * 判断机场是否属于维护的机场列表
	 * @param code3
	 * @return
	 */
	public boolean isMaintainAptByCode3(String code3){
		if(maintainApCodeMap.get(code3) != null){
			return true;
		}
		return false;
	}
	
	public UserVo getUserVoByName(String name){
		
		
		return userNameMap.get(name);
	}
	public AirCraftVo getAirCraftVoByAcnum(String acnum){
		return acnumMap.get(acnum);
	}

}
