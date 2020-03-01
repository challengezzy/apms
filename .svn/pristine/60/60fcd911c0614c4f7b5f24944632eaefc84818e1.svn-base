package com.apms.bs.flight.notify;

import java.util.List;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.flight.FlightConst;
import com.apms.bs.flight.FlightUtil;
import com.apms.bs.flight.vo.FlightNotifyVo;
import com.apms.bs.flight.vo.FlightUserVo;
import com.apms.bs.sms.ShortMessageUtil;

public class FlightUserNotifyThread extends Thread {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private List<FlightUserVo> oldList;
	
	private List<FlightUserVo> newList;
	
	private FlightNotifyVo fltvo;
	
	private FlightUtil util = new FlightUtil();
	private ShortMessageUtil msgUtil = new ShortMessageUtil();
	
	public FlightUserNotifyThread(List<FlightUserVo> oldList,List<FlightUserVo> newList,FlightNotifyVo flightvo){
		this.oldList = oldList;
		this.newList = newList;
		this.fltvo = flightvo;
	}

	@Override
	public void run() {
		List<FlightUserVo> addList = util.getAddedUsers(newList, oldList);
		List<FlightUserVo> removedList = util.getRemovedUsers(newList, oldList);
		
		String insertSql = "INSERT INTO F_FLIGHT_WORKREC(ID,FLIGHTID,USERNAME,JOBNAME,ISVALID,UPDATETIME) VALUES (S_ID.NEXTVAL,?,?,?,1,SYSDATE)";
		String updateSql = "UPDATE F_FLIGHT_WORKREC SET ISVALID=0,UPDATETIME=SYSDATE WHERE FLIGHTID=? AND USERNAME=? AND JOBNAME=?";
		
		try{
			//工作被安排：
			//某某某，您需要完成 B某某飞机 CA某某航班（PF,TR,AF） 的 放行、维修、勤务工作。
			//该航班将于 某日 XXX时间 落地，停靠 机场代码HGH 第501机位，当前接机有X项提醒请尽早阅读。
			//String addFormat = "%s,您需要完成%s航班的%s工作,机号%s,机型%s。该航班%s时间%s,停靠%s机位,请提前准备。";
			for(FlightUserVo uservo : addList){
				String flttime = fltvo.getFltdate()+"  "+fltvo.getT_cta();
				String format;
				String msg = "航班提醒";
				
				if( FlightConst.FLIGHT_TYPE_PF.equals( fltvo.getFlttype()) ){ //航前
					//某某某，你被安排BXXXX飞机 ， PF航班CAXXXX的 XXX工作,机位 XX,起飞时间 XXXX,CDM时间 XXXX
					format = "%s,你被安排%s飞机 ,机型%s, %s航班%s的 %s工作,机位 %s,起飞时间 %s,CDM时间 %s";
					flttime = fltvo.getFltdate()+"  "+fltvo.getT_ctd();
					
					msg = String.format(format, uservo.getUsername(),fltvo.getAcnum(),fltvo.getAcmodel()
							,fltvo.getFlttype(),fltvo.getFlightno(),uservo.getPost(),fltvo.getAcstop()
							,flttime,fltvo.getT_cdm());
					
					
				}else if( FlightConst.FLIGHT_TYPE_AF.equals( fltvo.getFlttype()) ){//航后
					//某某某,你被安排BXXXX飞机 , AF航班 CAXXXX的 XXX工作。航班预计到达时间 XXXX 机位 XX
					format = "%s,你被安排%s飞机 ,机型%s, %s航班%s的 %s工作. 到达时间%s,机位%s.";
					msg = String.format(format, uservo.getUsername(),fltvo.getAcnum(),fltvo.getAcmodel()
							,fltvo.getFlttype(),fltvo.getFlightno(),uservo.getPost()
							,flttime,fltvo.getAcstop());
					
				}else { //TR,AF+PF
					//某某某,你被安排接送BXXX飞机 , TR航班CAXXX的 XXX工作。预计到达时间 XXXX,机位 XX,起飞时间 XXXX,CDM时间 XXXX 
					format = "%s,你被安排%s飞机,机型%s, %s航班%s的 %s工作,到达时间%s,机位 %s,起飞时间 %s,CDM时间 %s";
					msg = String.format(format, uservo.getUsername(),fltvo.getAcnum(),fltvo.getAcmodel()
							,fltvo.getFlttype(),fltvo.getFlightno(),uservo.getPost(),flttime, fltvo.getAcstop()
							,fltvo.getT_ctd(),fltvo.getT_cdm());
				}
				
				String dest = util.getPhoneByUsername(uservo.getUsername());
				if(dest != null && dest.length() == 11 ){//只发长度为11位的手机
					msgUtil.saveMsgToMid(dest, msg, 0);
				}
				
				//记录用户工作分配
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, fltvo.getFlightid(),uservo.getUsername(),uservo.getPost());
				
			}
			dmo.commit(ApmsConst.DS_APMS);
			//工作被取消
			//某某某，您需要完成的 B某某飞机 CA某某航班（PF,TR,AF）的 放行、维修、勤务工作已取消，请关注。
			String rmFormat = "%s,你需要完成的%s航班%s工作,工作已取消,机号%s,请关注。";
			for(FlightUserVo uservo : removedList){
				String msg = String.format(rmFormat, uservo.getUsername(),fltvo.getFlightno(),uservo.getPost(),fltvo.getAcnum());
				String dest = util.getPhoneByUsername(uservo.getUsername());
				if(dest != null && dest.length() == 11 ){//只发长度为11位的手机
					msgUtil.saveMsgToMid(dest, msg, 0);
				}
				
				//更新航班工作记录为无效
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, fltvo.getFlightid(),uservo.getUsername(),uservo.getPost());
			}
			dmo.commit(ApmsConst.DS_APMS);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("分发航班工作分派短信出现异常！！！");
			try{ dmo.rollback(ApmsConst.DS_APMS);}catch (Exception e2) {	}
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		
	}
	

}
