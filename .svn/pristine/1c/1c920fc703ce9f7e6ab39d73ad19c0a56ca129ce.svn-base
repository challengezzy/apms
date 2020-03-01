package com.apms.bs.datacompute;

import java.util.Date;

import javax.print.attribute.standard.NumberOfDocuments;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.dfd.ConfigVarVo;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.vo.MathCountResult;
import com.apms.matlab.vo.Math_TTestResult;
import com.apms.vo.ApmsVarConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 报文数据解析
 *
 */
public class DataComputeUtil {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private ConfigVarVo conf = new ConfigVarVo();//配置变量
	
	private MatlabFunctionService functionService = new MatlabFunctionService();
	
	private CommDMO dmo = new CommDMO();
	
	private static String updateSql = "update a_dfd_head set status=?,computedesc=? where msg_no=?";

	/**
	 * 生成告警对象基本信息
	 * 
	 * @param headVo
	 * @return
	 */
	public static AlarmMonitorTargetVo getAlarmTargetVo(HashVO headVo) {

		AlarmMonitorTargetVo targetVo = new AlarmMonitorTargetVo();
		targetVo.setMsgno(headVo.getStringValue("MSG_NO"));
		targetVo.setDateUtc(headVo.getDateValue("DATE_UTC"));
		targetVo.setAcnum(headVo.getStringValue("ACNUM"));
		targetVo.setAcmodel(headVo.getStringValue("ACMODEL"));
		targetVo.setRptno(headVo.getStringValue("RPTNO"));

		return targetVo;
	}
	
	// 更新数据为已计算
	public static void updateDfdHeadDealed(String msgno) throws Exception {
		CommDMO dmo = new CommDMO();
		// 更新状态为
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, ApmsVarConst.DFD_COMPUTESTATUS_DEALED, "OK", msgno);
	}

	public static void updateDfdHeadError(String msgno, String errDesc) throws Exception {
		CommDMO dmo = new CommDMO();
		// 更新状态为
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, ApmsVarConst.DFD_COMPUTESTATUS_ERROR, errDesc, msgno);
	}
	
	/**
	 * 更新报文头计算状态为8，配置信息不全
	 * @param msgno
	 * @param errDesc
	 * @throws Exception
	 */
	public static void updateDfdHeadNottime(String msgno, String errDesc) throws Exception {
		CommDMO dmo = new CommDMO();
		// 更新状态为
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, ApmsVarConst.DFD_COMPUTESTATUS_NOTTIME, errDesc, msgno);
	}
	
}
