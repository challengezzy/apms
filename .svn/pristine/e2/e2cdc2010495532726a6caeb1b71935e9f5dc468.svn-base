package com.apms.bs.datacompute;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import com.apms.ApmsConst;
import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.datacompute.vo.A13ComputeVo;
import com.apms.bs.datacompute.vo.A21ComputeVo;
import com.apms.bs.datacompute.vo.A23ComputeVo;
import com.apms.bs.datacompute.vo.A24ComputeVo;
import com.apms.bs.datacompute.vo.A25ComputeVo;
import com.apms.bs.datacompute.vo.A27ComputeVo;
import com.apms.bs.datacompute.vo.A34ParsedVo;
import com.apms.bs.datacompute.vo.A38ComputeVo;
import com.apms.bs.datacompute.vo.A40ComputeVo;
import com.apms.vo.ApmsVarConst;

/**
 * 报文数据计算服务类
 * 
 * @author jerry
 * @date Dec 26, 2012
 */
public class DataComputeService {

	private Logger logger = NovaLogger.getLogger(this.getClass());// 日志

	private static DataComputeService computesService = null;

	public static DataComputeService getInstance() {
		if (computesService == null)
			computesService = new DataComputeService();

		return computesService;
	}

	public int computeA13Data() throws Exception {
		return computeA13Data(null);
	}

	/**
	 * 启动发动机APU报文计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA13Data(String acnum) throws Exception {
		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,L.DATE_UTC,L.CODE,L.TAT,L.ALT,L.ASN,L.AHRS,L.ACYC");
		qrySb.append(",L.AHRS_ADD,L.ACYC_ADD,L.ECID,L.PFAD,L.ACW1,L.ESN_N1,L.ACW1_N1,L.ACW2_N1,L.NA_N1,L.EGTA_N1,L.IGV_N1,L.SCV_N1,L.HOT_N1");
		qrySb.append(",L.ESN_N2,L.ACW1_N2,L.ACW2_N2,L.NA_N2,L.EGTA_N2,L.IGV_N2,L.SCV_N2,L.HOT_N2,L.ESN_N3,L.ACW1_N3,L.ACW2_N3");
		qrySb.append(",L.NA_N3,L.EGTA_N3,L.IGV_N3,L.SCV_N3,L.HOT_N3,L.P2A_S1,L.LCIT_S1,L.WB_S1,L.PT_S1,L.LCDT_S1,L.OTA_S1,L.GLA_S1");
		qrySb.append(",L.P2A_S2,L.LCIT_S2,L.WB_S2,L.PT_S2,L.LCDT_S2,L.OTA_S2,L.GLA_S2,L.P2A_S3,L.LCIT_S3,L.WB_S3,L.PT_S3");
		qrySb.append(",L.LCDT_S3,L.OTA_S3,L.GLA_S3,L.STA_V1,L.EGTP_V1,L.NPA_V1,L.OTA_V1,L.LCIT_V1,L.RECDATETIME,L.CORRECT_FLAG");
		qrySb.append(",T.RPTNO,T.ACMODEL");
		qrySb.append(",LOG.TIME_ONINSTALL,LOG.TIME_ONREPAIRED,LOG.CYCLE_ONINSTALL,LOG.CYCLE_ONREPAIRED");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A13_LIST L,L_APU_RUNLOG LOG");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND LOG.MSG_NO=L.MSG_NO AND T.STATUS=0 ");
		qrySb.append(" AND L.CODE = '4000'"); // 只有状态为4000的数据才参与计算

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}

		// qrySb.append(" and t.acnum = 'B6033' ");//B6031
		// qrySb.append(" and l.asn = '2056' ");
		// qrySb.append(" and t.msg_no= 111938319 ");

		qrySb.append(" ORDER BY L.MSG_NO");

		String testSql = "select * from (" + qrySb.toString() + ") where rownum< 10000";
		A13DataComputeService computeService = new A13DataComputeService();
		CommDMO dmo = new CommDMO();
		int num = 0;

		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");
				try {
					logger.debug("开始计算[" + msgno + "]报文数据");

					AlarmMonitorTargetVo targetVo = new AlarmMonitorTargetVo();

					A13ComputeVo computedVo = computeService.compute13ByHistoryVos(vo);
					targetVo.setMsgno(msgno);
					targetVo.setDateUtc(computedVo.getDate_utc());
					targetVo.addParam("COMPUTEDVO", computedVo);
					targetVo.setRptno("A13");
					targetVo.setDevicesn(computedVo.getAsn());
					targetVo.setAcnum(computedVo.getAcnum());

					// 此条报文计算完成后，进行告警触发
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A13_COMPUTE, msgno, targetVo);

					// 更新数据状态为已计算
					updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num++;
				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}

			}

		} catch (Exception e) {
			logger.error("A13报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		logger.debug("本次A13报文计算完成！共计算[" + num + "]条");

		return num;
	}

	public int computeA21Data() throws Exception {
		return computeA21Data(null);
	}

	/**
	 * ECS巡航报文计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA21Data(String acnum) throws Exception {
		
		A21DataComputeService computeService = new A21DataComputeService();
		int num = computeService.computeA21Data(acnum);

		return num;
	}

	public int computeA23Data() throws Exception {
		return computeA23Data(null);
	}

	/**
	 * A23氧气报文数据计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA23Data(String acnum) throws Exception {
		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,L.CKPTTS1,L.SATS1,L.PRESS1,L.DATETIMES1");
		qrySb.append(",L.CKPTTS2,L.SATS2,L.PRESS2,L.DATETIMES2,L.CKPTTS3,L.SATS3,L.PRESS3,L.DATETIMES3,L.SATS4,L.CKPTTS4,L.PRESS4,L.DATETIMES4");
		qrySb.append(",L.SATS5,L.CKPTTS5,L.PRESS5,L.DATETIMES5,L.SATS6,L.CKPTTS6,L.PRESS6,L.DATETIMES6,L.SATS7,L.CKPTTS7,L.PRESS7,L.DATETIMES7");
		qrySb.append(",L.RECDATETIME,L.DATE_UTC");
		qrySb.append(",T.RPTNO,T.ACMODEL");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A23_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND T.RPTNO='A23' AND T.STATUS=0 ");

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}
		// qrySb.append(" and t.acnum = 'B6032' ");//B6677,测试用 B6023
		// qrySb.append(" and t.msg_no= 132026127 ");

		qrySb.append(" ORDER BY L.MSG_NO");

		String testSql = "select * from (" + qrySb.toString() + ") where rownum< 10000";

		A23DataComputeService computeUtil = new A23DataComputeService();

		int num = 0;
		CommDMO dmo = new CommDMO();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");
				logger.debug("开始计算[" + msgno + "]报文数据");
				try {
					A23ComputeVo computedVo = computeUtil.computeA23ByHistoryVos(vo);

					AlarmMonitorTargetVo targetVo = getAlarmTargetVo(vo);
					targetVo.setMsgno(msgno);
					targetVo.addParam("COMPUTEDVO", computedVo);
					// 此条报文计算完成后，进行告警触发
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A23_COMPUTE, msgno, targetVo);

					// 更新数据状态为已计算
					updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);

				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
			num++;
		} catch (Exception e) {
			logger.error("A23报文计算时异常:" + e.getMessage());

			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}

	public int computeA24Data() throws Exception {
		return computeA24Data(null);
	}

	/**
	 * ECS地面报文计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA24Data(String acnum) throws Exception {

		A24DataComputeService computeService = new A24DataComputeService();
		int num = computeService.computeA24Data(acnum);
		
		return num;
	}

	public int computeA25Data() throws Exception {
		return computeA25Data(null);
	}

	/**
	 * 发动机滑油状态报文计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA25Data(String acnum) throws Exception {
		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,L.DATE_UTC,L.ESN_1,L.EHRS_1,L.ECYC_1,L.EHR_GA_1,L.ESN_2,L.EHRS_2,L.ECYC_2,L.EHR_GA_2");
		qrySb.append(",L.EHR_GA_T1,L.EHR_AIR_T1,L.EHR_GA_T2,L.EHR_AIR_T2,L.OIQ1_S1,L.OIQ2_S1,L.SD_S1,L.ENGAI_S1,L.TIME_S1,L.OIQ1_S2,L.OIQ2_S2,L.SD_S2,L.ENGAI_S2,L.TIME_S2");
		qrySb.append(",L.OIK1_K0,L.OIK2_K0,L.OIP_1,L.OIT_1,L.OIQ_1,L.N2_1,L.P3_1,L.OIK_1,L.OIP_2,L.OIT_2,L.OIQ_2,L.N2_2,L.P3_2");
		qrySb.append(",L.OIK_2,L.TIME_V1,L.SD_V1,L.ENGAI_V1,L.OILSTB_V1,L.OIQ1_W1,L.OIQ2_W1,L.TIME_W1,L.SD_W1,L.ENGAI_W1,L.OIK1_K1,L.OIK2_K1");
		qrySb.append(",L.OIQ1_Z1,L.OIQ2_Z1,L.TIME_Z1,L.SD_Z1,L.ENGAI_Z1,L.OIQ1_Z2,L.OIQ2_Z2,L.TIME_Z2,L.SD_Z2,L.ENGAI_Z2");
		qrySb.append(",L.OIQ1_Z3,L.OIQ2_Z3,L.TIME_Z3,L.SD_Z3,L.ENGAI_Z3 ");
		qrySb.append(",T.CNT,T.RPTNO,T.ACMODEL");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A25_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND T.RPTNO='A25' AND T.STATUS=0 ");

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}
		// qrySb.append(" and t.acnum = 'B6677' ");//B6031
		// qrySb.append(" and t.msg_no= 111938319 ");
		// qrySb.append(" and L.date_utc> to_date('2013-03-01','YYYY-MM-DD')");
		qrySb.append(" ORDER BY L.MSG_NO");

		String testSql = "select * from (" + qrySb.toString() + ") where rownum<8000";

		A25DataComputeService computeService = new A25DataComputeService();
		CommDMO dmo = new CommDMO();
		int num = 0;
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");

				try {
					logger.debug("开始计算[" + msgno + "]报文数据");
					A25ComputeVo computedVo = computeService.compute25ByHistoryVos(vo);
					AlarmMonitorTargetVo targetVo = getAlarmTargetVo(vo);
					targetVo.setMsgno(msgno);
					targetVo.setDateUtc(computedVo.getDate_utc());
					targetVo.addParam("COMPUTEDVO", computedVo);

					// 此条报文计算完成后，进行告警触发
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A25_COMPUTE, msgno, targetVo);

					// 更新数据状态为已计算
					updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num++;
				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
		} catch (Exception e) {
			logger.error("A25报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}

	public int computeA27Data() throws Exception {
		return computeA27Data(null);
	}

	/**
	 * 发动机滑油添加报文计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA27Data(String acnum) throws Exception {
		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,L.DATE_UTC,L.CODE,L.ESN_1,L.EHRS_1,L.ECYC_1,L.ESN_2,L.EHRS_2,L.ECYC_2");
		qrySb.append(",L.TMR27_PP,L.CKTMR_PP,L.ENDTMR_PP,L.DETQ_PP,L.ENDTQ_PP,L.OIQEXT_PP,L.OIQ_ENG1_Z4,L.OIQ_ENG2_Z4,L.ENG_SHUTTIME_Z4");
		qrySb.append(",L.OIQ_ENG1_Z5,L.OIQ_ENG2_Z5,L.ENG_SHUTTIME_Z5,L.OILADD_ENG1,L.ADDTIME_ENG1,L.PFAD_ENG1,L.PRE20S_ENG1,L.OIQST_ENG1,L.OIQOLD_ENG1");
		qrySb.append(",L.OILADD_ENG2,L.ADDTIME_ENG2,L.PFAD_ENG2,L.PRE20S_ENG2,L.OIQST_ENG2,L.OIQOLD_ENG2");
		qrySb.append(",L.QDT0_ENG1,L.QDT0_TIME_ENG1,L.QDT1_ENG1,L.QDT1_TIME_ENG1,L.QDT2_ENG1,L.QDT2_TIME_ENG1,L.QDT3_ENG1,L.QDT3_TIME_ENG1");
		qrySb.append(",L.QDT4_ENG1,L.QDT4_TIME_ENG1,L.QDT0_ENG2,L.QDT0_TIME_ENG2,L.QDT1_ENG2,L.QDT1_TIME_ENG2,L.QDT2_ENG2,L.QDT2_TIME_ENG2");
		qrySb.append(",L.QDT3_ENG2,L.QDT3_TIME_ENG2,L.QDT4_ENG2,L.QDT4_TIME_ENG2,L.CAL_GA_ENG1,L.CAL_AIR_ENG1,L.OIQAV_ENG1,L.OIQDT_ENG1,L.OIQCNT_ENG1");
		qrySb.append(",L.CAL_GA_ENG2,L.CAL_AIR_ENG2,L.OIQAV_ENG2,L.OIQDT_ENG2,L.OIQCNT_ENG2,L.QT_ENG1,L.FH_ENG1,L.OILCAL_ENG1,L.QT_ENG2,L.FH_ENG2,L.OILCAL_ENG2,L.EMPLOYID");
		qrySb.append(",T.RPTNO,T.ACMODEL");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A27_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND T.RPTNO='A27' AND T.STATUS=0 ");

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}
		// 测试条件
//		 qrySb.append(" and t.acnum = 'B6677' ");
		// qrySb.append(" and t.msg_no= 111938319 ");
		// qrySb.append(" and L.date_utc> to_date('2012-12-01','YYYY-MM-DD')");

//		qrySb.append(" ORDER BY L.MSG_NO");
		qrySb.append(" ORDER BY L.DATE_UTC DESC");


		String testSql = "select * from (" + qrySb.toString() + ") where rownum<5000";

		A27DataComputeService computeService = new A27DataComputeService();
		CommDMO dmo = new CommDMO();
		int num = 0;
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");

				logger.debug("开始计算[" + msgno + "]报文数据");
				try {
					A27ComputeVo computedVo = computeService.compute27ByHistoryVos(vo);
					AlarmMonitorTargetVo targetVo = getAlarmTargetVo(vo);
					targetVo.setMsgno(msgno);
					targetVo.setDateUtc(computedVo.getDate_utc());
					targetVo.addParam("COMPUTEDVO", computedVo);

					// 此条报文计算完成后，进行告警触发
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A27_COMPUTE, msgno, targetVo);

					// 更新数据状态为已计算
					updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num++;

				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
		} catch (Exception e) {
			logger.error("A27报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}

	public int computeA28Data() throws Exception {
		return computeA28Data(null);
	}

	/**
	 * A28飞机拉动报文数据计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA28Data(String acnum) throws Exception {
		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,L.DATE_UTC,L.CODE,L.GROUPID");
		qrySb.append(",L.ESN_1,L.EHRS_1,L.ECYC_1,L.AP_1,L.ESN_2,L.EHRS_2,L.ECYC_2,L.AP_2,L.UPDATE_DATE");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A2832_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND T.RPTNO='A32' AND T.STATUS=0 ");

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}
		// 测试条件
		// qrySb.append(" and t.acnum = 'B6048' ");
		// qrySb.append(" and T.MSG_NO = 196031379 ");

		qrySb.append(" ORDER BY L.MSG_NO");

		String testSql = "select * from (" + qrySb.toString() + ") where rownum< 10000";

		A28DataComputeService computeUtil = new A28DataComputeService();

		int num = 0;
		CommDMO dmo = new CommDMO();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");
				logger.debug("开始计算[" + msgno + "]报文数据");
				try {
					computeUtil.computeA28ByHistoryVos(vo);

					// 更新数据状态为已计算
					updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num++;
				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
		} catch (Exception e) {
			logger.error("A28报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}

	public int computeA34Data() throws Exception {
		return computeA34Data(null);
	}

	/**
	 * 大翼防冰报文计算
	 * 
	 * @return
	 * @throws Exception
	 */
	public int computeA34Data(String acnum) throws Exception {

		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,L.DATE_UTC,L.CODE,L.ESN_1,L.EHRS_1,L.ECYC_1");
		qrySb.append(",L.ESN_2,L.EHRS_2,L.ECYC_2,L.PD_S1,L.TPO_S1,L.WAV_S1,L.PD_S2,L.TPO_S2,L.WAV_S2,L.DATE_S1,L.PD_S3,L.TPO_S3,L.WAV_S3");
		qrySb.append(",L.PD_S4,L.TPO_S4,L.WAV_S4,L.DATE_S3,L.PD_S5,L.TPO_S5,L.WAV_S5,L.PD_S6,L.TPO_S6,L.WAV_S6,L.DATE_S5");
		qrySb.append(",L.WAV1_TMR_S7,L.WAV1_TMR_COR_S7,L.WAV2_TMR_S7,L.WAV2_TMR_COR_S7,L.DATE_S7");
		qrySb.append(",T.RPTNO,T.ACMODEL");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A34_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND T.RPTNO='A34' AND T.STATUS=0 ");

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}
		// qrySb.append(" and t.acnum = 'B6677' ");//B6031
		// qrySb.append(" and t.msg_no= 111938319 ");

		qrySb.append(" ORDER BY L.MSG_NO");

		String testSql = "select * from (" + qrySb.toString() + ") where rownum<5000";

		A34DataComputeService computeService = new A34DataComputeService();
		CommDMO dmo = new CommDMO();
		int num = 0;
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");

				try {
					logger.debug("开始计算[" + msgno + "]报文数据");
					A34ParsedVo computedVo = computeService.compute34ByHistoryVos(vo);
					AlarmMonitorTargetVo targetVo = getAlarmTargetVo(vo);
					targetVo.setMsgno(msgno);
					targetVo.setDateUtc(computedVo.getDate_utc());
					targetVo.addParam("COMPUTEDVO", computedVo);

					// 此条报文计算完成后，进行告警触发
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A34_COMPUTE, msgno, targetVo);

					// 更新数据状态为已计算
					updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num++;
				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
		} catch (Exception e) {
			logger.error("A34报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}

	public int computeA38Data() throws Exception {
		return computeA38Data(null);
	}
	/**
	 * A38报文计算
	 * @param acnum
	 * @return
	 * @throws Exception
	 */
	public int computeA38Data(String acnum) throws Exception {
		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,RPTDATE DATE_UTC,L.CODE,T.RPTNO,");
		qrySb.append(" L.ASN_E1 ASN,");
		qrySb.append(" (SELECT AC.MODELCODE  FROM B_AIRCRAFT AIR,B_AIRCRAFT_MODEL AC WHERE AIR.ACMODELID=AC.ID AND AIR.AIRCRAFTSN=L.ACNUM) ACMODEL,");
		qrySb.append(" L.ASN_E1,L.AHRS_E1,L.ACYC_E1,L.PFAD_E1,L.CT5ATP_N1,L.CPTATP_N1,L.CWFATP_N1,L.IGVATP_N1,L.BDTMAX_N1,L.CT5ATP_T1,L.CT5ATP_N2,");
		qrySb.append(" L.CPTATP_N2,L.CWFATP_N2,L.IGVATP_N2,L.CT5ATP_T2,L.BDTMAX_N2,L.UPDATE_DATE");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A38_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO  AND T.STATUS=0 AND L.CT5ATP_N2!=0 AND L.CT5ATP_N2 IS NOT NULL");

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}
		// 测试条件
		// qrySb.append(" and t.acnum = 'B6048' ");
		// qrySb.append(" and T.MSG_NO = 196031379 ");

		qrySb.append(" ORDER BY L.MSG_NO");

		String testSql = "select * from (" + qrySb.toString() + ") where rownum< 10000";

		A38DataComputeService computeUtil = new A38DataComputeService();

		int num = 0;
		CommDMO dmo = new CommDMO();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");
				logger.debug("开始计算[" + msgno + "]报文数据");
				try {
					A38ComputeVo computedVo =computeUtil.computeA38ByHistoryVos(vo);
					AlarmMonitorTargetVo targetVo = getAlarmTargetVo(vo);
//					targetVo.addParam("COMPUTEDVO", computedVo);
					HashMap<String, Object> targetMap = targetVo.getParamMap();
					
					//红线值告警
					targetMap.put("CT5ATP",computedVo.getCt5atp()+"");
					targetMap.put("CPTATP",computedVo.getCptatp()+"");
					targetMap.put("CWFATP",computedVo.getCwfatp()+"");
					targetMap.put("IGVATP",computedVo.getIgvatp()+"");
					targetMap.put("BDTMAX",computedVo.getBdtmax()+"");
				
					//飘点告警
					int v_pointtype_ct5atp = computedVo.getFieldVo_ct5atp().getV_pointtype();
					int v_out_ct5atp = computedVo.getFieldVo_ct5atp().getV_out();
					targetMap.put("V_POINTTYPE_CT5ATP", v_pointtype_ct5atp);
					targetMap.put("V_OUT_CT5ATP", v_out_ct5atp);
					 
					int v_pointtype_cptatp = computedVo.getFieldVo_cptatp().getV_pointtype();
					int v_out_cptatp = computedVo.getFieldVo_cptatp().getV_out();
					targetMap.put("V_POINTTYPE_PD2_AVG", v_pointtype_cptatp);
					targetMap.put("V_OUT_PD2_AVG", v_out_cptatp);
					
					int v_pointtype_cwfatp = computedVo.getFieldVo_cwfatp().getV_pointtype();
					int v_out_cwfatp = computedVo.getFieldVo_cwfatp().getV_out();
					targetMap.put("V_POINTTYPE_CWFATP", v_pointtype_cwfatp);
					targetMap.put("V_OUT_CWFATP", v_out_cwfatp);
					
					int v_pointtype_igvatp = computedVo.getFieldVo_igvatp().getV_pointtype();
					int v_out_igvatp = computedVo.getFieldVo_igvatp().getV_out();
					targetMap.put("V_POINTTYPE_IGVATP", v_pointtype_igvatp);
					targetMap.put("V_OUT_IGVATP", v_out_igvatp);
					
					int v_pointtype_bdtmax = computedVo.getFieldVo_bdtmax().getV_pointtype();
					int v_out_bdtmax = computedVo.getFieldVo_bdtmax().getV_out();
					targetMap.put("V_POINTTYPE_V_OUT_BDTMAX", v_pointtype_bdtmax);
					targetMap.put("V_OUT_V_OUT_BDTMAX", v_out_bdtmax);
					
					// 此条报文计算完成后，进行告警触发 
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A38_COMPUTE, msgno, targetVo);
					// 更新数据状态为已计算
					updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num++;
				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
		} catch (Exception e) {
			logger.error("A38报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}
	
	public int computeA40Data() throws Exception {
		return computeA40Data(null);
	}
	
	/**
	 * A40报文计算
	 * @param acnum
	 * @return
	 * @throws Exception
	 */
	public int computeA40Data(String acnum) throws Exception {
		StringBuilder qrySb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.ACNUM,RPTDATE DATE_UTC,L.CODE,T.RPTNO");
		qrySb.append(",(select ac.modelcode  from b_aircraft air,b_aircraft_model ac where air.acmodelid=ac.id and air.aircraftsn=L.acnum) ACMODEL");
		qrySb.append(",FF1_AVG,FF1_STD,FF2_AVG,FF2_STD,PD1_AVG,PD1_STD,PD2_AVG,PD2_STD,N21_AVG,N21_STD,N22_AVG,N22_STD,L.UPDATE_DATE");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A40_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND T.RPTNO='A40' AND T.STATUS=0 and L.FF1_AVG!=0 ");

		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}
		// 测试条件
		// qrySb.append(" and t.acnum = 'B6048' ");
		// qrySb.append(" and T.MSG_NO = 196031379 ");

		qrySb.append(" ORDER BY L.MSG_NO");

		String testSql = "select * from (" + qrySb.toString() + ") where rownum< 10000";

		A40DataComputeService computeUtil = new A40DataComputeService();

		int num = 0;
		CommDMO dmo = new CommDMO();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");
				logger.debug("开始计算[" + msgno + "]报文数据");
				try {
					A40ComputeVo computedVo =computeUtil.computeA40ByHistoryVos(vo);
					AlarmMonitorTargetVo targetVo = getAlarmTargetVo(vo);
//					targetVo.addParam("COMPUTEDVO", computedVo);
					HashMap<String, Object> targetMap = targetVo.getParamMap();
					
					//红线值告警
					targetMap.put("PD1_AVG",computedVo.getPd1_avg()+"");
					targetMap.put("PD2_AVG",computedVo.getPd2_avg()+"");
					targetMap.put("PD1_AVG_STD",computedVo.getPd1_avg_std()+"");
					targetMap.put("PD2_AVG_STD",computedVo.getPd2_avg_std()+"");
					targetMap.put("SUB_PD1_PD2", computedVo.getSub_pd1_pd2() + "");
					targetMap.put("SUB_PD1_PD2_STD", computedVo.getSub_pd1_pd2_std() + "");
					//飘点告警
					int v_pointtype_pd1_avg = computedVo.getFieldVo_pd1_avg().getV_pointtype();
					int v_out_pd1_avg = computedVo.getFieldVo_pd1_avg().getV_out();
					targetMap.put("V_POINTTYPE_PD1_AVG", v_pointtype_pd1_avg);
					targetMap.put("V_OUT_PD1_AVG", v_out_pd1_avg);
					
					int v_pointtype_pd2_avg = computedVo.getFieldVo_pd2_avg().getV_pointtype();
					int v_out_pd2_avg = computedVo.getFieldVo_pd2_avg().getV_out();
					targetMap.put("V_POINTTYPE_PD2_AVG", v_pointtype_pd2_avg);
					targetMap.put("V_OUT_PD2_AVG", v_out_pd2_avg);
					
					int v_pointtype_pd1_avg_std = computedVo.getFieldVo_pd1_avg_std().getV_pointtype();
					int v_out_pd1_avg_std = computedVo.getFieldVo_pd1_avg_std().getV_out();
					targetMap.put("V_POINTTYPE_PD1_AVG_STD", v_pointtype_pd1_avg_std);
					targetMap.put("V_OUT_PD1_AVG_STD", v_out_pd1_avg_std);
					
					int v_pointtype_pd2_avg_std = computedVo.getFieldVo_pd2_avg_std().getV_pointtype();
					int v_out_pd2_avg_std = computedVo.getFieldVo_pd2_avg_std().getV_out();
					targetMap.put("V_POINTTYPE_PD2_AVG_STD", v_pointtype_pd2_avg_std);
					targetMap.put("V_OUT_PD2_AVG_STD", v_out_pd2_avg_std);
					
					int v_pointtype_pd1_pd2_avg = computedVo.getFieldVo_pd1_pd2_avg().getV_pointtype();
					int v_out_pd1_pd2_avg = computedVo.getFieldVo_pd1_pd2_avg().getV_out();
					targetMap.put("V_POINTTYPE_SUB_PD1_PD2_AVG", v_pointtype_pd1_pd2_avg);
					targetMap.put("V_OUT_SUB_PD1_PD2_AVG", v_out_pd1_pd2_avg);
					
					int v_pointtype_pd1_pd2_avg_std = computedVo.getFieldVo_sub_pd1_pd2_std().getV_pointtype();
					int v_out_pd1_pd2_avg_std = computedVo.getFieldVo_sub_pd1_pd2_std().getV_out();
					targetMap.put("V_POINTTYPE_SUB_PD1_PD2_AVG_STD", v_pointtype_pd1_pd2_avg_std);
					targetMap.put("V_OUT_SUB_PD1_PD2_AVG_STD", v_out_pd1_pd2_avg_std);
					// 此条报文计算完成后，进行告警触发
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A39_COMPUTE, msgno, targetVo);
					// 更新数据状态为已计算
					updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num++;
				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
		} catch (Exception e) {
			logger.error("A40报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}
	
	/**
	 * 生成告警对象基本信息
	 * 
	 * @param headVo
	 * @return
	 */
	public AlarmMonitorTargetVo getAlarmTargetVo(HashVO headVo) {

		AlarmMonitorTargetVo targetVo = new AlarmMonitorTargetVo();
		targetVo.setMsgno(headVo.getStringValue("MSG_NO"));
		targetVo.setDateUtc(headVo.getDateValue("DATE_UTC"));
		targetVo.setAcnum(headVo.getStringValue("ACNUM"));
		targetVo.setAcmodel(headVo.getStringValue("ACMODEL"));
		targetVo.setRptno(headVo.getStringValue("RPTNO"));

		return targetVo;
	}

	private String updateSql = "update a_dfd_head set status=?,computedesc=? where msg_no=?";

	// 更新数据为已计算
	public void updateDfdHeadDealed(String msgno) throws Exception {
		CommDMO dmo = new CommDMO();
		// 更新状态为
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, ApmsVarConst.DFD_COMPUTESTATUS_DEALED, "OK", msgno);
	}

	public void updateDfdHeadError(String msgno, String errDesc) throws Exception {
		CommDMO dmo = new CommDMO();
		// 更新状态为
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, ApmsVarConst.DFD_COMPUTESTATUS_ERROR, errDesc, msgno);
	}

}
