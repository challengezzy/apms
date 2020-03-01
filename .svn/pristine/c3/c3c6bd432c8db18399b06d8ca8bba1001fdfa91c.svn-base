package com.apms.bs.maindata;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.cache.vo.ACModelVo;
import com.apms.cache.vo.AirCraftVo;
import com.apms.cache.vo.OrganizationVo;
import com.apms.cache.vo.UserVo;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 查询主数据及关联关系
 * @author jerry
 * @date Mar 19, 2014
 */
public class MainDataQueryService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	/**
	 * 查询航线级别的部门及期所在机场
	 * @return
	 * @throws Exception
	 */
	public ArrayList<OrganizationVo> getLineOrgAirport() throws Exception{
		
		
		//查询所有保障的航线组织及其所在机场
		StringBuilder qrySql = new StringBuilder("SELECT O.ID,O.NAME ORGNAME,O.CODE ORGCODE,O.ORGLEVEL,O.PARENTORGID ");
		qrySql.append(" ,O.AIRLINE_ID");
		qrySql.append(" ,(SELECT IATA_CODE FROM B_AIRLINE AL WHERE AL.ID=O.AIRLINE_ID) IATA_C");
		qrySql.append(" ,O.AIRPORT_ID,A.CODE_3");
		qrySql.append(" FROM B_ORGANIZATION O,B_AIRPORT A");
		qrySql.append(" WHERE A.ID=O.AIRPORT_ID AND O.ORGLEVEL=2 ");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrySql.toString());
		ArrayList<OrganizationVo> lineOrgList = new ArrayList<OrganizationVo>(vos.length);
		
		for(HashVO vo : vos){
			OrganizationVo orgVo = new OrganizationVo();
			orgVo.setId(vo.getStringValue("ID"));
			orgVo.setName(vo.getStringValue("ORGNAME"));
			orgVo.setCode(vo.getStringValue("ORGCODE"));
			orgVo.setBaseOrgId(vo.getStringValue("PARENTORGID"));
			orgVo.setOrgLevel(vo.getStringValue("ORGLEVEL"));
			orgVo.setAirlineId(vo.getStringValue("AIRLINE_ID"));
			orgVo.setAirlineIataCode(vo.getStringValue("IATA_C"));
			orgVo.setAirportId(vo.getStringValue("AIRPORT_ID"));
			orgVo.setAirportCode3(vo.getStringValue("CODE_3"));
			
			lineOrgList.add(orgVo);
		}
		
		logger.debug("查询所有保障的航线组织及其所在机场结束.");
		
		return lineOrgList;
	}
	
	public ArrayList<UserVo> getValidUserList() throws Exception{
		String sql = "select id,loginname,name,no,leadertype,isfieldstaff,mobile,email"
				+ " ,orgid_dep,orgid_base,orgid_line,orgid_section,orgid_group,airportid"
				+ " from b_user where accountstatus =1";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		ArrayList<UserVo> userList = new ArrayList<UserVo>(vos.length);
		
		for(HashVO vo : vos){
			UserVo user = new UserVo();
			user.setId(vo.getStringValue("ID"));
			user.setLoginname(vo.getStringValue("loginname"));
			user.setName(vo.getStringValue("name"));
			user.setNo(vo.getStringValue("no"));
			user.setLeadertype(vo.getIntegerValue("leadertype"));
			user.setIsfieldstaff(vo.getBooleanValue("isfieldstaff"));
			user.setMobile(vo.getStringValue("mobile"));
			user.setEmail(vo.getStringValue("email"));
			user.setOrgid_base(vo.getStringValue("orgid_base"));
			user.setOrgid_dep(vo.getStringValue("orgid_dep"));
			user.setOrgid_group(vo.getStringValue("orgid_group"));
			user.setOrgid_line(vo.getStringValue("orgid_line"));
			user.setOrgid_section(vo.getStringValue("orgid_section"));
			user.setAirportid(vo.getStringValue("airportid"));
			
			userList.add(user);
		}
			
		return userList;
	}
	
	/**
	 * 查询机型列表
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ACModelVo> getAcModelList() throws Exception{
		String sql = "SELECT ID,MODELCODE,MODELSERIES,AIRFRAMETYPE FROM B_AIRCRAFT_MODEL";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		ArrayList<ACModelVo> modelList = new ArrayList<ACModelVo>(vos.length);
		
		for(HashVO vo : vos){
			ACModelVo model = new ACModelVo();
			model.setId(vo.getStringValue("ID"));
			model.setModelcode(vo.getStringValue("MODELCODE"));
			model.setModelseries(vo.getStringValue("MODELSERIES"));
			model.setAirframetype(vo.getIntegerValue("AIRFRAMETYPE"));
			
			modelList.add(model);
		}
		
		return modelList;
	}
	
	/**
	 * 查询飞机列表
	 * @return
	 * @throws Exception
	 */
	public ArrayList<AirCraftVo> getAircriftList() throws Exception{
		String sql = "SELECT A.ID ID,A.AIRCRAFTSN ACNUM," 
				+" B.ID ACMODELID, B.MODELCODE ACMODEL ,C.ID BASEORGID, C.NAME BASEORG,B.AIRFRAMETYPE FRAMETYPE,D.ID AIRLINEID,D.NAME AIRLINE"
				+" FROM B_AIRCRAFT A,B_AIRCRAFT_MODEL B,B_ORGANIZATION C,B_AIRLINE D " 
				+" WHERE A.ACMODELID=B.ID AND A.BASEORGID=C.ID AND A.AIRLINEID=D.ID";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		ArrayList<AirCraftVo> acvoList = new ArrayList<AirCraftVo>(vos.length);
		
		for(HashVO vo : vos){
			AirCraftVo acvo = new AirCraftVo();
			acvo.setId(vo.getStringValue("ID"));
			acvo.setAcnum(vo.getStringValue("ACNUM"));
			acvo.setAcmodelId(vo.getStringValue("ACMODELID"));
			acvo.setAcmodel(vo.getStringValue("ACMODEL"));
			
			acvo.setBaseOrgId(vo.getStringValue("BASEORGID"));
			acvo.setBaseOrg(vo.getStringValue("BASEORG"));
			acvo.setFrameType(vo.getStringValue("FRAMETYPE"));
			
			acvo.setAirlineId(vo.getStringValue("AIRLINEID"));
			acvo.setAirline(vo.getStringValue("AIRLINE"));
			
			acvoList.add(acvo);
		}
		
		return acvoList;
	}    
}
