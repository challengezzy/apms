package com.apms.bs.flight;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.flight.vo.AirportPositionVo;

/**
 * 航班计划管理服务类
 * @author jerry
 * @date Apr 15, 2014
 */
public class AirportMapService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	public AirportMapService(){
		
	}
	
	/**
	 * 查询航线级别的部门及期所在机场
	 * @return
	 * @throws Exception
	 */
	public ArrayList<AirportPositionVo> getAirportPositionList(String aptId) throws Exception{
		
		StringBuilder qrySql = new StringBuilder("select id,airportid,code,terminalno,isbridge,linkcode,positiondesc");
		qrySql.append(" ,(select count(1) from b_airport_device d where d.type=0 and d.upstate=0 and d.positionid=p.id) workladdernum ");//可用工作梯数量
		qrySql.append("  ,longitude,latitude,x,y,rotation, comments,updatetime,updateuser ");
		qrySql.append(" from b_airport_position p ");
		qrySql.append(" where airportid = " + aptId );
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrySql.toString());
		ArrayList<AirportPositionVo> posiList = new ArrayList<AirportPositionVo>(vos.length);
		
		for(HashVO vo : vos){
			AirportPositionVo posiVo = new AirportPositionVo();
			posiVo.setId(vo.getStringValue("id"));
			posiVo.setAirportid(vo.getStringValue("airportid"));
			posiVo.setCode(vo.getStringValue("code"));
			posiVo.setTerminalno(vo.getStringValue("terminalno"));
			posiVo.setIsbridge(vo.getStringValue("isbridge"));
			posiVo.setLinkcode(vo.getStringValue("linkcode"));
			posiVo.setPositiondesc(vo.getStringValue("positiondesc"));
			posiVo.setLongitude(vo.getStringValue("longitude"));
			posiVo.setLatitude(vo.getStringValue("latitude"));
			posiVo.setX(vo.getStringValue("x"));
			posiVo.setY(vo.getStringValue("y"));
			posiVo.setRotation(vo.getStringValue("rotation"));
			posiVo.setWorkladdernum(vo.getStringValue("workladdernum"));
			
			posiList.add(posiVo);
		}
		logger.debug("查询机场所有机位信息结束.");
		
		return posiList;
	}
	
	/**
	 * 更新机位的坐标信息
	 * @param aptid
	 * @param stopcode
	 * @param x
	 * @param y
	 * @param rotation
	 * @throws Exception
	 */
	public void updateAcstopGeoInfo(String aptid,String stopcode,String x,String y,String rotation) throws Exception{
		String sql = "update b_airport_position t set t.x="+x+",t.y="+y+",t.rotation="+rotation+" ";
		sql += " where t.airportid="+aptid+" and t.code='"+stopcode+"'";
		
		String insertSql = "INSERT INTO B_AIRPORT_POSITION(ID,AIRPORTID,CODE,LINKCODE,X,Y,ROTATION,ISBRIDGE,TERMINALNO,UPDATETIME,UPDATEUSER)";
		insertSql += " VALUES(S_B_AIRPORT_POSITION.NEXTVAL,?,?,NULL,?,?,?,0,NULL,SYSDATE,NULL)";
		
		String querySql = "SELECT 1 FROM B_AIRPORT_POSITION T  WHERE T.AIRPORTID="+aptid+" AND T.CODE='"+stopcode+"'";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, querySql);
		if(vos.length > 0){
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
			dmo.commit(ApmsConst.DS_APMS);
			logger.debug("更新aptid="+aptid+",code="+stopcode+"的地理坐标信息成功！");
		}else{
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, aptid,stopcode,x,y,rotation);
			dmo.commit(ApmsConst.DS_APMS);
			logger.debug("新增aptid="+aptid+",code="+stopcode+"的机位信息成功！");
		}
		
	}

}
