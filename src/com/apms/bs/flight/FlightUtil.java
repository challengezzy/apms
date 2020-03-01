package com.apms.bs.flight;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.flight.vo.FlightNotifyVo;
import com.apms.bs.flight.vo.FlightUserVo;
import com.apms.bs.util.DateUtil;

/**
 * 航班工具类
 * @author jerry
 * @date Mar 15, 2014
 */
public class FlightUtil {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	
	/**
	 * 获取航班中通知消息的相关信息
	 * @param fltpk
	 * @return
	 */
	public FlightNotifyVo getFlightNotifyVo(String fltpk_d,String fltpk_a,String flttype) throws Exception{
		
		String fltpk;
		if(fltpk_a == null || fltpk_a.length() <1){
			fltpk = fltpk_d; //航前
		}else{
			fltpk = fltpk_a; //取到达航班的信息,航后、过站
		}
		
		String sql = "SELECT T.ID,T.FLT_PK,T.FLIGHTNO,T.FLT_DATE,T.FLT_DATE_STR,T.IATA_C,T.ACNUM,T.ACMODEL";
		sql += ",T.AC_STOP,T.AC_STOP_ARR,T.DEP_APT,T.ARR_APT,T.ACMODELID,T.ETA,T.ETD,T.CTA,T.CTD,CDM_OUT";
		sql += ",T.WORKFORCE_STATE,T.DUTY_USER,T.MAINTAIN_USER,T.RELEASE_USER,T.GUARDIAN_USER,T.HANDOVER_USER";
		sql += ",T.FLTTYPE_DEP,T.FLTTYPE_ARR";
		sql += " FROM V_FLIGHTSCH_SHORT T WHERE T.FLT_PK = " + fltpk;
		sql += "";
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		if(vos.length < 1){
			logger.error("****根据fltpk["+fltpk+"]未查询到航班****");
			return null;
		}
		
		HashVO vo = vos[0];
		String fltdate;
		String flightno;
		String t_cta;//实际到达-预达-计达
		String t_ctd;//实际起飞-预起-计起
		String t_cdm =" 空  ";
		String acnum;
		String apcode3 ;
		String acstop;
		
		flightno = vo.getStringValue("FLIGHTNO");
		fltdate = DateUtil.getDateStr(vo.getDateValue("FLT_DATE"));
		acnum = vo.getStringValue("ACNUM");
		t_cta = DateUtil.getDateStr(vo.getDateValue("CTA"), "HH:mm");
		t_ctd = DateUtil.getDateStr(vo.getDateValue("CTD"), "HH:mm");
		
		if(vo.getDateValue("CDM_OUT") != null ){
			t_cdm = DateUtil.getDateStr(vo.getDateValue("CDM_OUT"), "HH:mm");
		}
		
		String acmodel = vo.getStringValue("ACMODEL");
		
		if(FlightConst.FLIGHT_TYPE_PF.equals(flttype)){
			apcode3 = vo.getStringValue("DEP_APT");
			acstop = vo.getStringValue("AC_STOP");
		}else{
			apcode3 = vo.getStringValue("ARR_APT");
			acstop = vo.getStringValue("AC_STOP_ARR");
		}
		
		//如果是过站，再取起飞信息\CDM
		if(FlightConst.FLIGHT_TYPE_TR.equals(flttype) || FlightConst.FLIGHT_TYPE_AFPF.equals(flttype)){
			String sql2 = "SELECT T.ID,T.FLT_PK,T.FLIGHTNO,T.FLT_DATE,T.FLT_DATE_STR,T.IATA_C,T.ACNUM,T.ACMODEL";
			sql2 += ",T.AC_STOP,T.AC_STOP_ARR,T.DEP_APT,T.ARR_APT,T.ACMODELID,T.ETA,T.ETD,T.CTA,T.CTD,CDM_OUT";
			sql2 += ",T.WORKFORCE_STATE,T.DUTY_USER,T.MAINTAIN_USER,T.RELEASE_USER,T.GUARDIAN_USER,T.HANDOVER_USER";
			sql2 += ",T.FLTTYPE_DEP,T.FLTTYPE_ARR";
			sql2 += " FROM V_FLIGHTSCH_SHORT T WHERE T.FLT_PK = " + fltpk_d;
			sql2 += "";
			//离港航班信息
			HashVO[] vosd = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql2);
			if(vos.length < 1){
				logger.error("****根据fltpk["+fltpk_d+"]未查询到航班****");
				return null;
			}
			vo = vosd[0];
			t_ctd = DateUtil.getDateStr(vo.getDateValue("CTD"), "HH:mm");
			
			if(vo.getDateValue("CDM_OUT") != null ){
				t_cdm = DateUtil.getDateStr(vo.getDateValue("CDM_OUT"), "HH:mm");
			}
			
		}
		
		
		FlightNotifyVo fltNotify = new FlightNotifyVo(fltpk,flttype, fltdate, flightno, t_cta, t_ctd
														, acnum, apcode3, acstop,acmodel,t_cdm);
		
		return fltNotify;
	}
	
	/**
	 * 获取航班中的工作人员信息
	 * @param fltpk
	 * @return
	 */
	public List<FlightUserVo> getFlightUsers(String fltpk) throws Exception{
		
		String sql = "SELECT T.FLT_PK,T.DUTY_USER,T.MAINTAIN_USER,T.RELEASE_USER,T.GUARDIAN_USER,T.HANDOVER_USER";
		sql += ",auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm";
		sql += ",auto_lift,airpress_user,poweron_user,sign2_user,area_user";
		sql += ",PICKUPCREW_USER,CLEAN_USER,SEWAGE_USER";
		sql += ",T.FLTTYPE_DEP,T.FLTTYPE_ARR";
		sql += " FROM V_FLIGHTSCH_SHORT T WHERE T.FLT_PK = " + fltpk;
		sql += "";
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		if(vos.length < 1){
			logger.error("****根据fltpk["+fltpk+"]未查询到航班****");
			return new ArrayList<FlightUserVo>();
		}
		
		HashVO vo = vos[0];
		String releaseUser = vo.getStringValue("RELEASE_USER");
		String maintainUser = vo.getStringValue("MAINTAIN_USER");
		String dutyUser = vo.getStringValue("DUTY_USER");
		String guardianUser = vo.getStringValue("GUARDIAN_USER");
		String handoverUser = vo.getStringValue("HANDOVER_USER");
		
		String pickupcrewUser = vo.getStringValue("PICKUPCREW_USER");
		String cleanUser = vo.getStringValue("CLEAN_USER");
		String sewageUser = vo.getStringValue("SEWAGE_USER");
		
		String auto_power   = vo.getStringValue("auto_power");
		String auto_airsrc  = vo.getStringValue("auto_airsrc");
		String auto_aircond = vo.getStringValue("auto_aircond");
		String auto_trailer = vo.getStringValue("auto_trailer");
		String auto_snowrm  = vo.getStringValue("auto_snowrm");
		String auto_lift    = vo.getStringValue("auto_lift");
		String airpress_user= vo.getStringValue("airpress_user");
		String poweron_user = vo.getStringValue("poweron_user");
		String sign2_user   = vo.getStringValue("sign2_user");
		String area_user    = vo.getStringValue("area_user");

		
		List<FlightUserVo> userList = getFlightUserVos(releaseUser, maintainUser, dutyUser, guardianUser, handoverUser
									,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
									,auto_lift,airpress_user,poweron_user,sign2_user,area_user
									,pickupcrewUser,cleanUser,sewageUser);
		
		return userList;
	}
	
	public List<FlightUserVo> getFlightUserVos(String releaseUser,String maintainUser,String dutyUser,String guardianUser
			,String handoverUser 
			,String auto_power,String auto_airsrc,String auto_aircond,String auto_trailer,String auto_snowrm
			,String auto_lift,String airpress_user,String poweron_user,String sign2_user,String area_user
			,String pickupcrewUser,String cleanUser,String sewageUser){
		ArrayList<FlightUserVo> userList = new ArrayList<FlightUserVo>();
		//放行人员提取
		FlightUserVo user;
		List<String> ul = getUsers(releaseUser);
		for(String name : ul){
			user = new FlightUserVo("放行", name);
			userList.add(user);
		}
		
		ul = getUsers(maintainUser);
		for(String name : ul){
			user = new FlightUserVo("维修", name);
			userList.add(user);
		}
		
		ul = getUsers(dutyUser);
		for(String name : ul){
			user = new FlightUserVo("勤务", name);
			user.setUsername(name);
			userList.add(user);
		}
		
		ul = getUsers(guardianUser);
		for(String name : ul){
			user = new FlightUserVo("接机", name);
			userList.add(user);
		}
		
		ul = getUsers(handoverUser);
		for(String name : ul){
			user = new FlightUserVo("出港", name);
			userList.add(user);
		}
		
		//杭州20170227新加以下13种
		ul = getUsers(pickupcrewUser);
		for(String name : ul){
			user = new FlightUserVo("接机组", name);
			user.setUsername(name);
			userList.add(user);
		}
		
		ul = getUsers(cleanUser);
		for(String name : ul){
			user = new FlightUserVo("清洁", name);
			userList.add(user);
		}
		
		ul = getUsers(sewageUser);
		for(String name : ul){
			user = new FlightUserVo("污水车", name);
			userList.add(user);
		}
		
		
		ul = getUsers(auto_power);
		for(String name : ul){
			user = new FlightUserVo("电源车", name);
			userList.add(user);
		}
		ul = getUsers(auto_airsrc);
		for(String name : ul){
			user = new FlightUserVo("气源车", name);
			userList.add(user);
		}
		ul = getUsers(auto_aircond);
		for(String name : ul){
			user = new FlightUserVo("空调车", name);
			userList.add(user);
		}
		ul = getUsers(auto_trailer);
		for(String name : ul){
			user = new FlightUserVo("拖车", name);
			userList.add(user);
		}
		ul = getUsers(auto_snowrm);
		for(String name : ul){
			user = new FlightUserVo("除雪车", name);
			userList.add(user);
		}
		ul = getUsers(auto_lift);
		for(String name : ul){
			user = new FlightUserVo("客梯车", name);
			userList.add(user);
		}
		ul = getUsers(airpress_user);
		for(String name : ul){
			user = new FlightUserVo("测量气压", name);
			userList.add(user);
		}
		ul = getUsers(poweron_user);
		for(String name : ul){
			user = new FlightUserVo("通电", name);
			userList.add(user);
		}
		ul = getUsers(sign2_user);
		for(String name : ul){
			user = new FlightUserVo("双签", name);
			userList.add(user);
		}
		ul = getUsers(area_user);
		for(String name : ul){
			user = new FlightUserVo("区域负责", name);
			userList.add(user);
		}
		
		return userList;
	}
	
	/**
	 * 根据人名提取用户，多用户用","分隔
	 * @param userStr
	 * @return
	 */
	public List<String> getUsers(String userStr){
		List<String> users = new ArrayList<String>(5);
		if(userStr ==null || userStr.trim().length() <1){
			return users;
		}
			
		if(userStr.trim().indexOf(",") > 0){//有多个人员的情况
			String[] temps = userStr.split(",");
			for(String u : temps){
				users.add(u);
			}
		}else if(userStr.trim().indexOf("，") > 0){
			String[] temps = userStr.split("，");
			for(String u : temps){
				users.add(u);
			}
		}else{//单个人员
			users.add(userStr);
		}
		
		return users;
	}
	
	/**
	 * 获取新增的用户列表
	 * @param newList
	 * @param oldList
	 * @return
	 */
	public List<FlightUserVo> getAddedUsers(List<FlightUserVo> newList,List<FlightUserVo> oldList){
		ArrayList<FlightUserVo> userList = new ArrayList<FlightUserVo>();
		boolean isNew = true;
		
		for(FlightUserVo nu : newList){
			isNew = true;
			for(FlightUserVo ou : oldList){
				if(ou.getUsername().equals(nu.getUsername())){
					isNew = false;
					break;
				}
			}
			//在oldlist中未找到
			if( isNew ){
				userList.add(nu);
			}
		}
		
		return userList;
	}
	
	/**
	 * 获取取消用户列表
	 * @param newList
	 * @param oldList
	 * @return
	 */
	public List<FlightUserVo> getRemovedUsers(List<FlightUserVo> newList,List<FlightUserVo> oldList){
		ArrayList<FlightUserVo> userList = new ArrayList<FlightUserVo>();
		boolean isDel = true;
		
		for(FlightUserVo ou : oldList){
			isDel = true;
			for(FlightUserVo nu : newList){
				if(ou.getUsername().equals(nu.getUsername())){
					isDel = false;
					break;
				}
			}
			//在newlist中未找到
			if( isDel ){
				userList.add(ou);
			}
		}
		return userList;
	}
	
	/**
	 * 根据用户名查询手机号，可考虑用缓存
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public String getPhoneByUsername(String username) throws Exception{
		String sql = "select mobile from pub_user u where name = ?";
		String phone = null;
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql,username);
		if(vos.length <1){
			logger.debug("查询不到用户["+username+"]");
			return phone;
		}else{
			phone = vos[0].getStringValue("mobile");
			
			return phone;
		}
		
	}

	
}
