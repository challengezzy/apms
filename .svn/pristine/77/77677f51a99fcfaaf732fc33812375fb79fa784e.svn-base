
package com.apms.bs.aircraft.interceptor;


import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.common.vo.SimpleHashVO;
import com.apms.ApmsConst;

/**
 * 服务端拦截器实现类,飞机初始化或修改的时候实现
 */
public class AircraftMonitorService {

	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	public SimpleHashVO[] queryForAcnum(String code_3) throws Exception{
		String sql="SELECT to_char(P.BEGINTIME, 'hh24:mi') time,(SELECT COUNT(1) FROM V_FLIGHTUNION_STAT F WHERE F.APT = '"+code_3+"'"
			+" AND F.TIMEFILTER > SYSDATE - 8 / 24 AND F.TIMEFILTER < SYSDATE + 16 / 24 AND F.CTA < P.ENDTIME  AND F.CTD >= P.BEGINTIME) totalacnum,"
			+" (SELECT COUNT(1) FROM V_FLIGHTUNION_STAT F, B_AIRCRAFT_MODEL AM WHERE F.APT = '"+code_3+"'"
			+" AND AM.ID = F.ACMODELID AND AM.AIRFRAMETYPE=0 AND F.TIMEFILTER > SYSDATE - 8 / 24 AND F.TIMEFILTER < SYSDATE + 16 / 24 AND F.CTA < P.ENDTIME"
			+" AND F.CTD >= P.BEGINTIME) zhaiacnum,"
	        +" (SELECT COUNT(1) FROM V_FLIGHTUNION_STAT F, B_AIRCRAFT_MODEL AM WHERE F.APT = '"+code_3+"'"  
	        +" AND AM.ID = F.ACMODELID AND AM.AIRFRAMETYPE=1 AND F.TIMEFILTER > SYSDATE - 8 / 24  AND F.TIMEFILTER < SYSDATE + 16 / 24 AND F.CTA < P.ENDTIME" 
	        +"  AND F.CTD >= P.BEGINTIME) kuanacnum," 
	        +" (SELECT COUNT(1) FROM V_FLIGHTUNION_STAT F WHERE F.APT = '"+code_3+"'" 
	        +" AND NOT EXISTS(SELECT 1 FROM B_AIRCRAFT_MODEL AM,B_AIRCRAFT AC WHERE AC.AIRCRAFTSN=F.ACNUM AND AM.ID=AC.ACMODELID)" 
	        +"  AND F.TIMEFILTER > SYSDATE - 8 / 24 AND F.TIMEFILTER < SYSDATE + 16 / 24 AND F.CTA < P.ENDTIME AND F.CTD >= P.BEGINTIME) qita"
	        +" FROM R_PERIOD P WHERE P.BEGINTIME >= SYSDATE - 8 / 24 AND P.ENDTIME < SYSDATE + 16 / 24 ORDER BY P.BEGINTIME";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql);
		SimpleHashVO[] result = new SimpleHashVO[vos.length];
		for (int i = 0; i < vos.length; i++) {
			SimpleHashVO vo = new SimpleHashVO(vos[i]);
			result[i] = vo;
		}
		return result;	
	}

}
