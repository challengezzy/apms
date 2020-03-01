package com.apms.bs.flight.notify;

import java.util.List;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.flight.FlightUtil;
import com.apms.bs.flight.vo.FlightUserVo;
import com.apms.bs.sms.ShortMassageHwService;
import com.apms.bs.sms.ShortMessageUtil;

public class FlightChangeNotifyThread extends Thread {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private String apcode3;
	
	private FlightUtil util = new FlightUtil();
	private ShortMessageUtil msgUtil = new ShortMessageUtil();
	ShortMassageHwService service = ShortMassageHwService.getInstance();//获取服务实例
	
	public FlightChangeNotifyThread(String apcode3){
		this.apcode3 = apcode3;
	}

	@Override
	public void run() {
		try{
			
			//处理航班取消
			dealCancelChange();
			
			//处理预达、机位变更
			dealDataChange();
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("分发航班工作分派短信出现异常！！！");
			try{ dmo.rollback(ApmsConst.DS_APMS);}catch (Exception e2) {	}
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		
	}
	
	public void dealDataChange() throws Exception{
		//查询取消的航班变动信息,1天内未处理
		String qrysql = "SELECT F.FLIGHTNO,F.ACNUM,F.ETA ET,F.AC_STOP_ARR ACSTOP,C.CHANGETYPE,C.ID CHGID";
		qrysql += ",F.DUTY_USER,F.MAINTAIN_USER,F.RELEASE_USER,F.GUARDIAN_USER,F.HANDOVER_USER";
		qrysql += ",auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm";
		qrysql += ",auto_lift,airpress_user,poweron_user,sign2_user,area_user";
		qrysql += ",F.PICKUPCREW_USER,F.CLEAN_USER,F.SEWAGE_USER,CDM_OUT,TO_CHAR(CDM_OUT,'HH24:MI') T_CDMOUT";
		qrysql += " FROM F_FLIGHT_CHANGELOG C,F_FLIGHT_SCHEDULE F";
		qrysql += " WHERE F.FLT_PK=C.FLIGHTID AND F.FLT_DATE=TRUNC(SYSDATE) AND F.FLTTYPE_ARR= ANY('AF','TR','AF+PF') ";
		qrysql += " AND F.ARR_APT='"+ apcode3 +"'";
		qrysql += " AND C.CHANGETYPE=ANY(25,45) AND C.CHANGETIME>SYSDATE-1 AND c.notified=0";
		qrysql += " AND F.AC_STOP_ARR IS NOT NULL AND F.ETA IS NOT NULL";
		qrysql += " UNION ALL ";
		qrysql += "SELECT F.FLIGHTNO,F.ACNUM,F.ETD ET,F.AC_STOP ACSTOP,C.CHANGETYPE,C.ID CHGID";
		qrysql += ",F.DUTY_USER,F.MAINTAIN_USER,F.RELEASE_USER,F.GUARDIAN_USER,F.HANDOVER_USER";
		qrysql += ",auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm";
		qrysql += ",auto_lift,airpress_user,poweron_user,sign2_user,area_user";
		qrysql += ",F.PICKUPCREW_USER,F.CLEAN_USER,F.SEWAGE_USER,CDM_OUT,TO_CHAR(CDM_OUT,'HH24:MI') T_CDMOUT";
		qrysql += " FROM F_FLIGHT_CHANGELOG C,F_FLIGHT_SCHEDULE F";
		qrysql += " WHERE F.FLT_PK=C.FLIGHTID AND F.FLT_DATE=TRUNC(SYSDATE) AND F.FLTTYPE_ARR= ANY('PF') ";
		qrysql += " AND F.DEP_APT='"+ apcode3 +"'";
		qrysql += " AND C.CHANGETYPE=ANY(20,40,52) AND C.CHANGETIME>SYSDATE-1 AND c.notified=0";
		qrysql += " AND F.AC_STOP IS NOT NULL AND F.ETD IS NOT NULL";
		
		
		List<FlightUserVo> userList;
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrysql);
		//机位、预达/预起变更
		//某某某，需要接送的 B某某飞机 CA某某航班 时间/机位/属性/ 变更为（PF,TR,AF） 的 落地时间（提前/延误）变更为，落地机位变更为，请关注。
		String chgFormat = "%s,您需要接送的%s,%s航班,%s变更,最新机位%s,%s%s,请关注";
		for(HashVO vo : vos){//每个取消航班
			String et = vo.getStringValue("ET");//预计时间
			String acstop = vo.getStringValue("ACSTOP");
			
			//必须是有预计时间和机位时，才发提醒信息
			if(et == null || et.length()<1 || acstop == null || acstop.length()<1 ){
				continue ;
			}
			
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
			String acnum = vo.getStringValue("ACNUM");
			String fltno = vo.getStringValue("FLIGHTNO");
			
			String chgtype = vo.getStringValue("CHANGETYPE");
			String chgAttr = "";
			String etAttr = "";
			
			if("25".equals(chgtype)){
				chgAttr = "预计到达时间";
				etAttr = "预达";
				
			}else if("45".equals(chgtype)){
				chgAttr = "进港机位";
				etAttr = "预达";
				
			}else if("20".equals(chgtype)){
				chgAttr = "预计起飞时间";
				etAttr = "预起";
				
			}else if("40".equals(chgtype)){
				chgAttr = "离港机位";
				etAttr = "预起";
			}else if("52".equals(chgtype)){
				chgAttr = "CDM时间";
				etAttr = "CDM:";
				et = vo.getStringValue("T_CDMOUT");
			}
			
			//根据变更信息类型不同发送给不同人员
			if("52".equals(chgtype)){
				userList = util.getFlightUserVos(releaseUser, maintainUser, dutyUser, guardianUser, handoverUser
						,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
						,auto_lift,null,null,null,null
						,null,null,null);
			}else{
				userList = util.getFlightUserVos(releaseUser, maintainUser, dutyUser, guardianUser, handoverUser
						,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
						,auto_lift,airpress_user,poweron_user,sign2_user,area_user
						,pickupcrewUser,cleanUser,sewageUser);
			}
			
			for(FlightUserVo uservo : userList){
				//%s,您需要接送的%s飞机,%s航班,%s变更,最新机位%s,%s%s,请关注
				String msg = String.format(chgFormat, uservo.getUsername(),acnum,fltno,chgAttr,acstop,etAttr,et);
				
				String dest = util.getPhoneByUsername(uservo.getUsername());
				if(dest != null && dest.length() == 11 ){//只发长度为11位的手机
					msgUtil.saveMsgToMid(dest, msg,1);//只要记录，不用重复发送
					service.send(dest, msg);//发短信
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
			
			//标记变动信息已处理
			markChangeDealed(vo.getStringValue("CHGID"));
		}
		
		
	}
	
	/**
	 * 处理取消的航班
	 * @throws Exception
	 */
	public void dealCancelChange() throws Exception{
		//查询取消的航班变动信息,1天内未处理
		String qrysql = "SELECT F.FLIGHTNO,F.ACNUM,F.CANCEL_REASON,F.MEMO,C.ID CHGID";
		qrysql += ",F.DUTY_USER,F.MAINTAIN_USER,F.RELEASE_USER,F.GUARDIAN_USER,F.HANDOVER_USER";
		qrysql += ",F.VEHICLE_USER,F.PICKUPCREW_USER,F.CLEAN_USER,F.SEWAGE_USER";
		qrysql += " FROM F_FLIGHT_CHANGELOG C,F_FLIGHT_SCHEDULE F";
		qrysql += " WHERE F.FLT_PK=C.FLIGHTID AND F.FLT_DATE=TRUNC(SYSDATE) ";
		qrysql += " AND ( F.ARR_APT='"+apcode3+"' OR F.DEP_APT='"+apcode3+"' )";
		qrysql += " AND C.CHANGETYPE=200 AND C.CHANGETIME>SYSDATE-1 AND C.notified=0";
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrysql);
		
		List<FlightUserVo> userList;
		//某某某，您需要接送的B某某飞机 CA某某航班 已取消，请注意相应的工作变更。
		String cancelFormat = "%s,您需要接送的%s飞机,%s航班计划已取消，请注意相应的工作变更！";
		for(HashVO vo : vos){//每个取消航班
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
			
			String acnum = vo.getStringValue("ACNUM");
			String fltno = vo.getStringValue("FLIGHTNO"); 
			
			userList = util.getFlightUserVos(releaseUser, maintainUser, dutyUser, guardianUser, handoverUser
					,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
					,auto_lift,airpress_user,poweron_user,sign2_user,area_user
					,pickupcrewUser,cleanUser,sewageUser);
			
			for(FlightUserVo uservo : userList){ //每个用户

				String msg = String.format(cancelFormat, uservo.getUsername(),acnum,fltno);
				
				String dest = util.getPhoneByUsername(uservo.getUsername());
				if(dest != null && dest.length() == 11 ){//只发长度为11位的手机
					msgUtil.saveMsgToMid(dest, msg,1);//只要记录，不用重复发送
					service.send(dest, msg);//发短信
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
			
			//标记变动信息已处理
			markChangeDealed(vo.getStringValue("CHGID"));
		}
		
	}
	
	private void markChangeDealed(String chgid) throws Exception{
		String sql = "update f_flight_changelog c set notified=1 where id= ?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql, chgid);
		dmo.commit(ApmsConst.DS_APMS);
	}
	

}
