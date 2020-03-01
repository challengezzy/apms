package com.apms.bs.flight;

import java.util.List;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.flight.notify.FlightUserNotifyThread;
import com.apms.bs.flight.vo.FlightNotifyVo;
import com.apms.bs.flight.vo.FlightUserVo;

/**
 * 航班工作服务类
 * @author jerry
 * @date Apr 3, 2016
 */
public class FlightWorkService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private FlightUtil util = new FlightUtil();
	
	/**
	 * 工作分配
	 * @param releaseUser
	 * @param maintainUser
	 * @param dutyUser
	 * @param guardianUser
	 * @param handoverUser
	 * @param fltpk_a
	 * @param fltpk_d
	 * @throws Exception
	 */
	public void flightTaskDispatch(String releaseUser,String maintainUser,String dutyUser
			, String guardianUser,String handoverUser
			,String auto_power,String auto_airsrc,String auto_aircond,String auto_trailer,String auto_snowrm,String auto_lift
			,String airpress_user,String poweron_user,String sign2_user,String area_user
			,String pickupcrewUser,String cleanUser,String sewageUser
			,String fltpk_a,String fltpk_d,String optUser) throws Exception{
		try{
			//为保证效率，先只查询原航班人员，不做短信通知处理
			notifyFlightUser(releaseUser, maintainUser, dutyUser, guardianUser, handoverUser
					,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
					,auto_lift,airpress_user,poweron_user,sign2_user,area_user
					,pickupcrewUser,cleanUser,sewageUser, fltpk_a, fltpk_d);
			
			String uptSql = "UPDATE F_FLIGHT_SCHEDULE T SET T.RELEASE_USER=?,T.MAINTAIN_USER=?,T.DUTY_USER=?";
			uptSql += ", GUARDIAN_USER=? ,HANDOVER_USER=?" ;
			uptSql += ",AUTO_POWER=? ,AUTO_AIRSRC=? ,AUTO_AIRCOND=? ,AUTO_TRAILER=? ,AUTO_SNOWRM=?";
			uptSql += ",AUTO_LIFT=? ,AIRPRESS_USER=? ,POWERON_USER=? ,SIGN2_USER=? ,AREA_USER=?";
			uptSql += ",PICKUPCREW_USER=? ,CLEAN_USER=? ,SEWAGE_USER=?";
			uptSql += ",T.WORKFORCE_STATE = DECODE(T.WORKFORCE_STATE,0,10,WORKFORCE_STATE) "; //工作状态
			uptSql += ",T.UPDATETIME=SYSDATE,T.UPDATEUSER=?";
			uptSql +=  " WHERE T.FLT_PK=? ";
			
			//同时更新两个航班的人员信息
			if( fltpk_a != null && !"".equals(fltpk_a) ){
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptSql,releaseUser,maintainUser,dutyUser
							, guardianUser, handoverUser
							,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
							,auto_lift,airpress_user,poweron_user,sign2_user,area_user
							,pickupcrewUser,cleanUser,sewageUser, optUser, fltpk_a);
			}
			if( fltpk_d != null && !"".equals(fltpk_d) ){
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptSql,releaseUser,maintainUser,dutyUser
						, guardianUser, handoverUser
						,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
						,auto_lift,airpress_user,poweron_user,sign2_user,area_user
						,pickupcrewUser,cleanUser,sewageUser, optUser, fltpk_d);
			}
			dmo.commit(ApmsConst.DS_APMS);
			
			logger.debug("更新航班fltpk=["+fltpk_a +","+fltpk_d+"]工作人员信息成功！");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询最新航班同步信息失败!", e);
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 通知相关人员航班信息更改
	 * @param releaseUser
	 * @param maintainUser
	 * @param dutyUser
	 * @param guardianUser
	 * @param handoverUser
	 * @param fltpk_a
	 * @param fltpk_d
	 */
	public void notifyFlightUser(String releaseUser,String maintainUser,String dutyUser,String guardianUser,String handoverUser
			,String auto_power,String auto_airsrc,String auto_aircond,String auto_trailer,String auto_snowrm
			,String auto_lift,String airpress_user,String poweron_user,String sign2_user,String area_user
			,String pickupcrewUser,String cleanUser,String sewageUser,String fltpk_a,String fltpk_d ){
		
		String flttype ;//航班类型AF,PF,TR
		String fltpk;
		if(fltpk_a == null || fltpk_a.length() <1){
			flttype = "PF";
			fltpk = fltpk_d;
		}else{
			fltpk = fltpk_a; //取到达航班的信息
			if( fltpk_d == null || fltpk_d.length() <1){
				flttype = "AF";
			}else{
				flttype = "TR";
			}
		}
		
		try{
			//1,查出未更改之前的人员航班信息
			List<FlightUserVo> oldList = util.getFlightUsers(fltpk);
			List<FlightUserVo> newList = util.getFlightUserVos(releaseUser, maintainUser, dutyUser, guardianUser, handoverUser
									,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
									,auto_lift,airpress_user,poweron_user,sign2_user,area_user
									,pickupcrewUser,cleanUser,sewageUser);
			FlightNotifyVo flightvo = util.getFlightNotifyVo(fltpk_d,fltpk_a, flttype);
			//启动短信通知线程
			FlightUserNotifyThread thread = new FlightUserNotifyThread(oldList,newList,flightvo);
			thread.start();
			
		}catch (Exception e) {
			logger.error("短信通知人员信息变更发生异常!!",e);
			e.printStackTrace();
		}
	}

}
