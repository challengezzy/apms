package com.apms.mobile;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.SimpleHashVO;

import com.apms.ApmsConst;
import com.apms.bs.common.HashVoUtil;
import com.apms.bs.flight.FlightWorkService;
import com.apms.bs.system.LoginService;
import com.apms.bs.system.LoginUserVo;

/**
 * 移动APP后台调用服务类
 * 
 * @author Kevin
 * 
 */
public class MobileService {

	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();

	private FlightWorkService workService = new FlightWorkService();
	
	/**
	 * 用户登录
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	public LoginUserVo userLogin(String username, String password) throws Exception {
		LoginService loginService = new LoginService();

		LoginUserVo loginVo = loginService.doLogin(username, password);

		logger.debug("移动端用户[" + loginVo.getLoginname() + "]登录，判定状态[" + loginVo.getLoginStatus() + "]，所属机场[" + loginVo.getAirportName() + "]");

		return loginVo;
	}
	
	//获取最新的app版本
	public Map<String, Object> getLastVersion() throws Exception{
		String sql = "SELECT * FROM ( SELECT T.ID,T.VNAME,T.VNO,T.VNUM,T.VDESC" +
				",ROW_NUMBER() OVER(ORDER BY VNUM DESC) RN,T.UPDATETIME FROM B_APP_VERSION T ) WHERE RN=1";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		Map<String, Object> vmap = HashVoUtil.hashVoToMapLowerCase(vos[0]);
		
		return vmap;
	}

	/**
	 * 查询工作梯
	 */
	public WorkladderVo[] getWorkladder(String code_3, String workladdername, String status, String airposition) throws Exception {
		try {
			String sql = "select name,airposition,type,positiondesc,platform_height" 
						+ ",upstate,heightdesc,platform_area,isguardbar,comments";
			sql += " from v_device_workladder_app";
			sql += " where code_3='" + code_3 + "' and upstate='" + status + "'";
			if (workladdername != null && !"".equals(workladdername.toString())) {
				sql += " and name like '%" + workladdername + "%'";
			}
			if (airposition != null && !"".equals(airposition)) {
				sql += " and airposition like '%机场" + airposition + "%'";
			}
			HashVO[] workladders = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			WorkladderVo[] workladderVos = new WorkladderVo[workladders.length];
			for (int i = 0; i < workladders.length; i++) {
				WorkladderVo workladderVo = new WorkladderVo();
				workladderVo.setName(workladders[i].getStringValue("name"));
				workladderVo.setAirportname(workladders[i].getStringValue("airportname"));
				workladderVo.setAirposition(workladders[i].getStringValue("airposition"));
				workladderVo.setType(workladders[i].getStringValue("type"));
				workladderVo.setPositiondesc(workladders[i].getStringValue("positiondesc"));
				workladderVo.setUpstate(workladders[i].getStringValue("upstate"));
				workladderVo.setPlatform_height(workladders[i].getStringValue("platform_height"));
				workladderVo.setHeightdesc(workladders[i].getStringValue("heightdesc"));
				workladderVo.setPlatform_area(workladders[i].getStringValue("platform_area"));
				workladderVo.setIsguardbar(workladders[i].getStringValue("isguardbar"));
				workladderVo.setComments(workladders[i].getStringValue("comments"));
				workladderVos[i] = workladderVo;
			}
			return workladderVos;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	/**
	 * 飞机查询
	 */
	public HashMap<String, Object>[] getAircraftList(String baseorgId,String acnum, String acmodel) throws Exception {
		try {
			String sql = "select id, apusn, apu_datatime_str, apu_csn, apu_tsn, apu_tsr, apu_csr, apumodel, APU_LLP_MIN, ";//apu
			sql += " esn1, eng_datatime_str1,engmodel1, eng_csn1, eng_tsn1, eng_tsr1, eng_csr1, LLP_LIMIT1, LEFT_DAY1, "; //发动机1
			sql += " esn2, eng_datatime_str2,engmodel2, eng_csn2, eng_tsn2, eng_tsr2, eng_csr2, LLP_LIMIT2, LEFT_DAY2, "; //发动机2
			sql += " baseorg, airline, aircraftsn, baseorgid, airlineid, acmodelid,";//飞机基本信息
			sql += " engmodelid, acmodel, engine_mode, status, fsn, msn, ";
			sql += " vdfcdate_str, fiactlg_sum, vdfcair_h, vdfcblock_h ";//飞行日志信息
			sql += " from v_aircraft_app where 1=1";
			if (baseorgId != null && !"".equals(baseorgId)) {
				sql += " and baseorgid = " + baseorgId;
			}
			
			if (acnum != null && !"".equals(acnum)) {
				sql += " and aircraftsn like '%" + acnum.toUpperCase() + "%'";
			}
			if (acmodel != null && !"".equals(acmodel)) {
				sql += " and acmodel like '%" + acmodel.toUpperCase() + "%'";
			}
			logger.debug("查询飞机sql语句：" + sql);
			HashVO[] aircrafts = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			
			@SuppressWarnings("unchecked")
			HashMap<String, Object>[] aircraftvos = new HashMap[aircrafts.length];
			for (int i = 0; i < aircrafts.length; i++) { // 转换为map对象
				aircraftvos[i] = (HashMap<String, Object>) HashVoUtil.hashVoToMapLowerCase(aircrafts[i]);
			}
			return aircraftvos;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	/**
	 * 获得机场三字码列表
	 * @return
	 * @throws Exception
	 */
	public String[] getAirports() throws Exception {
		try {
			String sql = "select CODE_3 from V_AIRPORT_REFQUERY";

			HashVO[] airportsVo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			String[] airports = new String[airportsVo.length];
			for (int i = 0; i < airportsVo.length; i++) {
				airports[i] = airportsVo[i].getStringValue("code_3");
			}
			return airports;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	public HashMap<String, Object>[] getFlightDynamic(String code_3, String flightno, String acnum, String hours
			, int af, int pf, int tr, int afpf) throws Exception{
		return getFlightDynamic(code_3, flightno, acnum, hours, af, pf, tr, afpf,"in");
	}
			
	/**
	 * 航班查询界面的查询航班（未来几个小时将要到达的航班和现在在港的航班）
	 */
	public HashMap<String, Object>[] getFlightDynamic(String code_3, String flightno, String acnum, String hours
			, int af, int pf, int tr, int afpf,String sortType) throws Exception {

		String sql = "select flt_date_str,substr(flt_date_str,6) fltdt_str,acnum,acmodel,apt_union,maintaintype,stda,acportstate";
		sql += ",eta,etd,t_eta, t_etd";
		sql += ",to_char(off_time,'hh24:mi') t_off_time,to_char(on_time,'hh24:mi') t_on_time";
		sql += ",(select decode(p.isbridge,0,'远机位',1,'靠桥位') From b_airport_position p,b_airport a where p.airportid=a.id and a.code_3=t.apt and code=t.ac_stop) acstop_bridge";
		sql += " ,s_tip,j_tip,g_tip,c_tip,uc_tip ,s_tip||j_tip||g_tip||c_tip||v_tip||l_tip flag_tip ";
		sql += ",t_cdmout,cdm_out";
		sql += ", dd_flag, m_flag ,dd_flag||m_flag flag_ddm ";
		sql += " ,duty_user,maintain_user,release_user,guardian_user,handover_user ";
		sql += " ,vehicle_user,pickupcrew_user,clean_user,sewage_user ";
		sql += " ,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm,auto_lift";
		sql += " ,airpress_user,poweron_user,sign2_user,area_user";
		sql += " ,ac_stop,flightno_union,flt_pk_a,flt_pk_d,flightno_a,flightno_d,workforce_state_name";
		//sql += ",null jobnames";
		sql += " ,ishavefault,ishavewarning,ac_state_a,ac_state_d,remain_min_arr,remain_min_dep,workforce_state,release_user ";
		sql += " from v_flightsch_join t ";
		sql += " where t.apt='" + code_3 + "'";
		if (flightno != null && flightno.trim().length() > 0) {
			sql += " and flightno_union like '%" + flightno.toUpperCase() + "%'";
		}
		if (acnum != null && acnum.trim().length() >0 ) {
			sql += " and acnum like '%" + acnum.toUpperCase() + "%'";
		}
		sql += " and ((maintaintype in('-1'";
		if (af == 1) {
			sql += ",'AF'";
		}
		if (pf == 1) {
			sql += ",'PF'";
		} 
		if (tr == 1) {
			sql += ",'TR'";
		}
		if (afpf == 1) {
			sql += ",'AF+PF'";
		}
		sql += " ) and t.timefilter >= sysdate-30/(24*60) ";
		sql += " and t.timefilter <= sysdate+" + hours + "/24)";
		sql += " or (t.ACPORTSTATE=any('落地','靠桥','开舱','关舱','推出') and t.FLT_DATE=trunc(sysdate)))";
		//sql += " order by t.timefilter";
		//排序规则
		if("in".equals(sortType)){
			sql += " order by t.cta asc nulls last";
		}else if("out".equals(sortType)){
			sql += " order by t.cdm_out asc nulls last,t.ctd asc nulls last";
		}else if("acstop".equals(sortType)){
			sql += " order by t.ac_stop asc nulls last";
		}
		
		logger.debug("航班动态列表数据查询sql语句：" + sql);
		
		return getMapVos(sql);
			
	}

	/**
	 * 查询我的航班
	 * @param code_3
	 * @param workStateType 状态 1,未完成工作 2，已完成工作 3，所有工作
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object>[] getMyFlight(String code_3,int workStateType,String uname) throws Exception {
		
		//TODO 调试用条件
		//uname = "王猛";
		
		try {
			String sql = "select flt_date_str,substr(flt_date_str,6) fltdt_str,acnum,acmodel,apt_union,maintaintype,stda,acportstate";
			sql += ",eta,etd,t_eta, t_etd";
			sql += ",to_char(off_time,'hh24:mi') t_off_time,to_char(on_time,'hh24:mi') t_on_time";
			sql += ",(select decode(p.isbridge,0,'远机位',1,'靠桥位') From b_airport_position p,b_airport a where p.airportid=a.id and a.code_3=t.apt and code=t.ac_stop) acstop_bridge";
			sql += " ,s_tip,j_tip,g_tip,c_tip,uc_tip ,s_tip||j_tip||g_tip||c_tip||v_tip||l_tip flag_tip ";
			sql += ",t_cdmout,cdm_out";
			sql += ", dd_flag, m_flag ,dd_flag||m_flag flag_ddm ";
			sql += " ,duty_user,maintain_user,release_user,guardian_user,handover_user ";
			sql += " ,vehicle_user,pickupcrew_user,clean_user,sewage_user ";
			sql += " ,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm,auto_lift";
			sql += " ,airpress_user,poweron_user,sign2_user,area_user";
			sql += " ,ac_stop,flightno_union,flt_pk_a,flt_pk_d,flightno_a,flightno_d,workforce_state_name";
			sql += ",(select wm_concat(jobname) from f_flight_workrec w where (w.FLIGHTID = T.FLT_PK_D OR w.FLIGHTID = T.FLT_PK_A) AND ISVALID=1 and w.username='"+uname+"' ) jobnames";
			sql += " ,ishavefault,ishavewarning,ac_state_a,ac_state_d,remain_min_arr,remain_min_dep,workforce_state,release_user ";
			sql += " from v_flightsch_join t";
			sql += " where t.apt='" + code_3 + "' and flt_date=trunc(sysdate)"; //当天航班
			
			if(workStateType == 1){
				sql += " and ( t.WORKFORCE_STATE<40  or (workforce_state=40 and datatype=1 and (OFF_time is null or OFF_time > sysdate-0.5/24)) )";//工作未完成
			}else if(workStateType == 2){
				sql += " and t.WORKFORCE_STATE = 40 ";//工作已完成
			}else if(workStateType ==3){
				//所有工作，不限定条件
			}
			
			//测试用条件
			//sql += " and acnum = any('B8800','B6677','B6848','B5946') ";
			sql += "AND EXISTS (SELECT 1 FROM F_FLIGHT_WORKREC R WHERE (R.FLIGHTID = T.FLT_PK_D OR R.FLIGHTID = T.FLT_PK_A)" +
					" AND R.ISVALID=1 AND R.USERNAME='"+uname+"' ) ";
//			sql += "and (t.RELEASE_USER like '%"+uname+"%' or t.MAINTAIN_USER like '%"+uname+"%' " 
//				+ " or t.GUARDIAN_USER like '%"+uname+"%' or t.DUTY_USER like '%"+uname+"%' " 
//				+ " or t.HANDOVER_USER like '%"+uname+"%')";
			sql += " order by flt_date,workforce_state";

			logger.debug("查询我的反馈航班sql：" + sql);
			HashVO[] flvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			@SuppressWarnings("unchecked")
			HashMap<String, Object>[] flightvos = new HashMap[flvos.length];
			for (int i = 0; i < flvos.length; i++) { // 转换为map对象
				flightvos[i] = (HashMap<String, Object>) HashVoUtil.hashVoToMapLowerCase(flvos[i]);
			}
			return flightvos;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	/**
	 * 查询航班的详细信息
	 * @param flt_date_str
	 * @param ap_union
	 * @param flightno_union
	 */
	public SimpleHashVO getDetailFligh(String flt_pk_aStr, String flt_pk_dStr) throws Exception {
		try {
			String querySql = " select datatype, flt_date, flt_date_str, iata_c, iata_c_name, co_seq, co_seq_name,";
			querySql += " acnum,acmodel,acmodelid,apt, maintaintype, workforce_state, workforce_state_name,";
			querySql += " ac_stop,isconfirmed, isconfirmed_name,islockedin,islockedin_name,";
			querySql += " memo,flightdesc, flt_pk_a, flt_pk_d, flightno_union,flightno_a,flightno_d, apt_union,";
			querySql += " stda, sta, std, cta, ctd,eta,etd,t_eta,remain_min_arr,remain_min_dep,";
			querySql += " duty_user,maintain_user,release_user,guardian_user,handover_user,";
			querySql += " vehicle_user,pickupcrew_user,clean_user,sewage_user, ";
			querySql += " auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm,auto_lift,";
			querySql += " airpress_user,poweron_user,sign2_user,area_user,";
			querySql += " ac_state_a, ac_state_name_a, ac_state_d, ac_state_name_d, acportstate,";
			querySql += " dd_flag, m_flag ,dd_flag||m_flag flag_dd,";
			querySql += " s_tip,j_tip,g_tip,c_tip,uc_tip ,s_tip||j_tip||g_tip||c_tip flag_tip,";
			querySql += " chgalternum,chglognum,ishavefault,ishavewarning,timefilter ";
			querySql += " from v_flightsch_join  ";
			querySql += " where 1=1";
			if (flt_pk_aStr == null) {
				querySql += " and flt_pk_a is null";
			} else {
				querySql += " and flt_pk_a=" + flt_pk_aStr;
			}
			if (flt_pk_dStr == null) {
				querySql += " and flt_pk_d is null";
			} else {
				querySql += " and flt_pk_d=" + flt_pk_dStr;
			}
			logger.debug("查询航班明细sql:" + querySql);
			HashVO[] flvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);
			SimpleHashVO vo = new SimpleHashVO(flvos[0]);
			return vo;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 保存航班状态反馈信息
	 */
	public void saveFlightStatus(String flightid, String flightno, int acstatus, String statusTime
			, String reporter, String comments, String updateUser) throws Exception {
		// 拼装f_ac_state的sql语句
		String f_ac_state_sql = "insert into f_ac_state(id, flightid, flightno, state, reporter, comments, updatetime,updateuser)" 
				+ " values(s_f_ac_state.nextval," + flightid + ",'" + flightno+ "'," + acstatus;
		if (reporter != null && "".equals(reporter)) {
			f_ac_state_sql += ",'" + reporter + "',";
		} else {
			f_ac_state_sql += ",null,";
		}
		if (comments != null && !"".equals(comments)) {
			f_ac_state_sql += "'" + comments + "',";
		} else {
			f_ac_state_sql += "null,";
		}
		f_ac_state_sql += "to_date('" + statusTime + "','YYYY-MM-DD HH24:MI:SS'),'" + updateUser + "')";
		try {
			// 更新航班计划的飞机状态信息
			// 更新前需要查询该航班的飞机状态，航班状态的值只有小于要改的值才能更新
			String ac_state_sql = "select ac_state from f_flight_schedule  where flt_pk=" + flightid;
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, ac_state_sql);
			if (vos.length <= 0) {
				logger.error("未查找到flt_pk=["+flightid+"]航班！");
			} else {
				int ac_statenum = vos[0].getIntegerValue("ac_state");
				if (ac_statenum < acstatus) {
					String f_flight_schedule_sql = "update f_flight_schedule set ac_state=" + acstatus + " where flt_pk=" + flightid;
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, f_flight_schedule_sql);
					logger.debug("该航班飞机状态更新成功！");
				} else {
					logger.warn("该航班飞机状态更新过时！");
				}
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, f_ac_state_sql);
				dmo.commit(ApmsConst.DS_APMS);
			}
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	/**
	 * 保存工作状态反馈信息
	 */
	public void saveWorkStatus(String flightid, String flightno, int workstate, String statusTime
			, String reporter, String comments, String updateUser) throws Exception {
		// 拼装f_ac_state的sql语句
		String f_ac_state_sql = "insert into f_worker_state(id, flightid, flightno, state, reporter, comments, updatetime,updateuser)"
					+ " values(s_f_ac_state.nextval," + flightid + ",'" + flightno+ "'," + workstate;
		if (reporter != null && "".equals(reporter)) {
			f_ac_state_sql += ",'" + reporter + "',";
		} else {
			f_ac_state_sql += ",null,";
		}
		if (comments != null && !"".equals(comments)) {
			f_ac_state_sql += "'" + comments + "',";
		} else {
			f_ac_state_sql +="null,";
		}
		f_ac_state_sql += "to_date('" + statusTime + "','YYYY-MM-DD HH24:MI:SS'),'" + updateUser + "')";
		try {
			// 更新航班计划的飞机状态信息
			// 更新前需要查询该航班的工作状态，航班状态的值只有小于要改的值才能更新
			String ac_state_sql = "select workforce_state from f_flight_schedule  where flt_pk=" + flightid;
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, ac_state_sql);
			if (vos.length <= 0) {
				logger.error("航班flt_pk=["+flightid+"]未查到！！！！");
			} else {
				int workforce_state = vos[0].getIntegerValue("workforce_state");
				if (workforce_state < workstate) {
					String uptSql = "update f_flight_schedule set workforce_state=" + workstate + " where flt_pk=" + flightid;
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, uptSql);
				}
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, f_ac_state_sql);
				dmo.commit(ApmsConst.DS_APMS);
			}
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	/**
	 * 机位提醒信息查询
	 */
	public HashMap<String, Object>[] getPositiontip(String code_3, String airposition) throws Exception {
		String sql = "select at.code,at.type_name,at.tipinfo,at.terminalno,at.isbridge_name,at.type,positiondesc" 
			+ " from v_acstop_tip at,b_airport ap"
			+ " where at.airportid=ap.id and ap.code_3='" + code_3 + "'" 
			+ " and code like '%" + airposition + "%' order by type desc";
		
		logger.debug("机位提醒信息查询SQL :" + sql);
		
		return getMapVos(sql);
	}

	/**
	 * DD单信息查询
	 * 
	 * @param code_3
	 * @param airposition
	 * @return
	 * @throws Exception
	 */
	public DDInfoVo[] getDDInfo(String baseorgid,String acnum,String dayLimit) throws Exception {
		try {
			String sql = "select dd_no,ac_id,eng_sn,finish_sign,m_flag,m_desc,target_date" ;
			sql += ",defect_rpt,rect" ;
			sql += " from v_dd_info_app where baseorgid=" + baseorgid ;
			
			if (acnum != null && !"".equals(acnum)) {
				sql += "  and ac_id like'%" + acnum.toUpperCase() + "%'" ;
			}
			if(dayLimit !=null && dayLimit.length()>0){
				sql += " and first_date > sysdate-"+dayLimit+"";
			}
			
			
			sql += " order by first_date desc,m_flag desc";
			
			logger.debug("查询飞机DD单sql:" + sql);
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			DDInfoVo[] ddvos = new DDInfoVo[vos.length];
			for (int i = 0; i < vos.length; i++) {
				ddvos[i] = new DDInfoVo();
				ddvos[i].setDd_no(vos[i].getStringValue("dd_no"));
				ddvos[i].setEng_sn(vos[i].getStringValue("eng_sn"));
				ddvos[i].setFinish_sign(vos[i].getStringValue("finish_sign"));
				ddvos[i].setM_flag(vos[i].getStringValue("m_flag"));
				ddvos[i].setM_desc(vos[i].getStringValue("m_desc"));
				if (vos[i].getStringValue("target_date") != null) {
					ddvos[i].setTarget_date(vos[i].getStringValue("target_date").substring(0, 10));
				}
				ddvos[i].setDefect_rpt(vos[i].getStringValue("defect_rpt"));
				ddvos[i].setRect(vos[i].getStringValue("rect"));
				ddvos[i].setAc_id(vos[i].getStringValue("ac_id"));
			}
			return ddvos;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	/**
	 * 提醒单信息查询
	 */
	public DefectRemindVo[] queryForDefectremind(String flt_pk_a, String flt_pk_d) throws Exception {
		if (flt_pk_a == null) {
			flt_pk_a = "";
		}
		if (flt_pk_d == null) {
			flt_pk_d = "";
		}
		String sql = "select df.flt_no,df.acnum,decode(df.risklevel,0,'了解',1,'监控','需处理') risklevel" +
				",decode(df.status,0,'提示','关闭') status,df.reminddesc from v_remindins_unconfirm ru,W_DEFECTREMIND df"
				+ " where ru.remindid=df.id and (ru.flightid='" + flt_pk_a + "' or ru.flightid='" + flt_pk_d + "')";
		logger.debug("查询航班对应提醒单SQL:" + sql);
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			DefectRemindVo[] ddvos = new DefectRemindVo[vos.length];
			for (int i = 0; i < vos.length; i++) {
				ddvos[i] = new DefectRemindVo();
				ddvos[i].setFlt_no(vos[i].getStringValue("flt_no"));
				ddvos[i].setAcnum(vos[i].getStringValue("acnum"));
				ddvos[i].setRisklevel(vos[i].getStringValue("risklevel"));
				ddvos[i].setStatus(vos[i].getStringValue("status"));
				ddvos[i].setReminddesc(vos[i].getStringValue("reminddesc"));
			}
			return ddvos;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	/**
	 * 查询cfd故障信息
	 */
	public SimpleHashVO[] selectFaultInfo(String fltPk_a, String flrPk_b) throws Exception {
		String sql = "select acf.fault_content,acf.fault_source" + " from a_cfd_head ach,a_cfd_fault acf" 
				+ " where (ach.flightid=" + fltPk_a + " or ach.flightid=" + flrPk_b +")"
				+ " and ach.msg_no=acf.msg_no order by acf.reporttime";
		logger.debug("查询航班对应CFD故障SQL:" + sql);
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	/**
	 * 查询cfd警告信息
	 */
	public SimpleHashVO[] selectWarningInfo(String fltPk_a, String flrPk_b) throws Exception {
		String sql = "select acw.warn_content" + " from a_cfd_head ach,a_cfd_warning acw" 
				+ " where (ach.flightid=" + fltPk_a + " or ach.flightid=" + flrPk_b + ")"
				+ " and ach.msg_no=acw.msg_no order  by acw.reporttime";
		logger.debug("查询航班对应CFD告警SQL:" + sql);
		try {
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			SimpleHashVO[] result = new SimpleHashVO[vos.length];
			for (int i = 0; i < vos.length; i++) {
				SimpleHashVO vo = new SimpleHashVO(vos[i]);
				result[i] = vo;
			}
			return result;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}

	/**
	 * 查询故障信息和警告信息
	 */
	public FaultAndWarning getFaultAndWarning(String baseorgId,String acnum, String hour) throws Exception {
		FaultAndWarning faultAndWarning = new FaultAndWarning();
		// 故障查询语句
		String faultSql = "select ach.flightno,acf.atano_major||acf.atano_minor||acf.atano_sub atano";
		faultSql += ",acf.fault_content,acf.fault_source,ach.acnum,lpad(phase,2,'0') phase";
		faultSql += ",reporttime,to_char(reporttime,'yyyy-mm-dd hh24:mi:ss') rpttime_str";
		faultSql += " from a_cfd_head ach,a_cfd_fault acf,b_aircraft ac";
		faultSql += " where ach.msg_no=acf.msg_no and ac.aircraftsn=ach.acnum and ac.baseorgid="+baseorgId;
		if(acnum != null && acnum.length() >0 && !"飞机号".equals(acnum) ){
			faultSql += " and ach.acnum='" + acnum.toUpperCase() + "'";
		}
		faultSql += " and acf.reporttime<=sysdate and acf.reporttime>=sysdate-" + hour + "/24 order  by acf.reporttime";
		

		// 警告查询语句
		String warningSql = "select ach.flightno,acw.warn_content,acw.reporttime,ach.acnum,lpad(phase,2,'0') phase ";
		warningSql += ",acw.atano_major||acw.atano_minor||acw.atano_sub atano";
		warningSql += ",to_char(reporttime,'yyyy-mm-dd hh24:mi:ss') rpttime_str";
		warningSql += " from a_cfd_head ach,a_cfd_warning acw,b_aircraft ac ";
		warningSql += " where ach.msg_no=acw.msg_no and ac.aircraftsn=ach.acnum and ac.baseorgid="+baseorgId;
		if(acnum != null && acnum.length() >0  && !"飞机号".equals(acnum) ){
			warningSql += " and ach.acnum='" + acnum.toUpperCase() + "'";
		}
		warningSql += " and acw.reporttime<=sysdate and acw.reporttime>=sysdate-"+ hour + "/24  order  by acw.reporttime";
		
		
		HashMap<String, Object>[] faults = getMapVos(faultSql);
		HashMap<String, Object>[] warnings = getMapVos(warningSql);
		
		faultAndWarning.setFault(faults);
		faultAndWarning.setWarning(warnings);
		
		return faultAndWarning;
	}
	
	//查询用户所属基地的飞机
	public String[] getAcnum(String username) throws Exception {
		String sql = "select air.aircraftsn acnum from b_user u,b_organization org,b_aircraft air";
			sql += " where u.loginname='" + username + "' and org.id=u.orgid_base and  air.baseorgid=org.id";
		try {
			HashVO[] acnumVo = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			String[] acnums = new String[acnumVo.length];
			for (int i = 0; i < acnumVo.length; i++) {
				acnums[i] = acnumVo[i].getStringValue("acnum");
			}
			return acnums;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	/**
	 * 查询AMS工作计划
	 */
	public HashMap<String, Object>[] getAmsWorkPlan(String code_3,String dateStr,String acnum
			,String ordersn,String plansn,String completeState) throws Exception {
		String sql = "select t.workordersn,t.plansn,t.acno acnum,t.contextcn,t.mh,opdy,to_char(opdy,'YYYY-MM-DD') opdy_str,";
		sql += "(select s.t_cta from v_flightsch_short s where s.flt_date=opdy and s.acnum=t.acno and s.flttype_arr='AF' and rownum<=1 ) t_cta,";
	    sql += "(select s.ac_stop_arr from v_flightsch_short s where s.flt_date=opdy and s.acnum=t.acno and s.flttype_arr='AF' and rownum<=1 ) acstop,";
		sql += " t.rii,(SELECT VALUECN FROM B_DICTIONARY T WHERE T.CLASSNAME='WORKORDERPLANE' AND T.ATTRIBUTENAME='RII_SYMBOL' AND VALUE=T.RII) rii_desc";
		sql += " from ams_workorderplane t";
		sql += " where PARENTID=0 AND EFFECT=1 and opdy=to_date('"+dateStr+"','yyyymmdd') "; //当天航班
		
		if (acnum != null && !"".equals(acnum)) {
			sql += " and acno like '%" + acnum.toUpperCase() + "%'";
		}
		if (ordersn != null && !"".equals(ordersn)) {
			sql += " and workordersn like '%" + ordersn + "%'";
		}
		if (plansn != null && !"".equals(plansn)) {
			sql += " and plansn like '%" + plansn + "%'";
		}
		
		if("99".equals(completeState)){
			//所有状态均查询，不过滤
		}else{
			sql += " AND COMPLETESTATE=" + completeState;
		}
		
		sql += " order by acno";
		logger.debug("查询AMS维修计划sql：" + sql);
		
		return getMapVos(sql);
	}
	
	//工作分配
	public void flightTaskDispatch(String releaseUser,String maintainUser,String dutyUser
			,String guardianUser,String handoverUser
			,String auto_power,String auto_airsrc,String auto_aircond,String auto_trailer,String auto_snowrm
			,String auto_lift,String airpress_user,String poweron_user,String sign2_user,String area_user
			,String pickupcrewUser,String cleanUser,String sewageUser
			,String fltpk_a,String fltpk_d,String optUser) throws Exception{
		
		workService.flightTaskDispatch(releaseUser, maintainUser, dutyUser, guardianUser, handoverUser
				,auto_power,auto_airsrc,auto_aircond,auto_trailer,auto_snowrm
				,auto_lift,airpress_user,poweron_user,sign2_user,area_user
				,pickupcrewUser,cleanUser,sewageUser, fltpk_a, fltpk_d, optUser);
	}
	
	/**
	 * 根据SQL查询数据，转换成map对象
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object>[] getMapVos(String sql) throws Exception{
		try {
			HashVO[] flvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
			@SuppressWarnings("unchecked")
			HashMap<String, Object>[] planVos = new HashMap[flvos.length];
			for (int i = 0; i < flvos.length; i++) { // 转换为map对象
				planVos[i] = (HashMap<String, Object>) HashVoUtil.hashVoToMapLowerCase(flvos[i]);
			}
			return planVos;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
	}
	
	
}