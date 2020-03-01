package com.apms.bs.intf.omis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;

/**
 * OMIS接口数据采集
 * zhangzy20160119 不再使用
 * @date Dec 15, 2012
 **/
public class OmisDataExtractService {

	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO omis = new CommDMO();
	private CommDMO apms = new CommDMO();
	String FLT_Date_F = "";
	String FLT_Acid_F = "";
	String FLT_Date_C = "";
	String FLT_Acid_C = "";
	int FLT_Cancel_C;
	String FLT_Date_B = "";
	String FLT_Acid_B = "";

	public OmisDataExtractService() {

	}

	/** 获取航班计划 */
	public int extractFlightSchedule(String iata_c, String CO_SEQ, String BASEORGID, String d) throws Exception {
		int recordCount = 0;
		String queryomisfly = "";
		String queryapms = "";
		String insertstr = "";
		String upstr = "";
		String fltcompare = "";

		String fltAtt = "";// 航段类型判断
		String FLT_CHANGE_FLAG = "1";// 修改标志0为修改前，1为修改后，2为人工修改

		int FLTAIR = 0;// 空中时间
		int FLTGRD = 1;// 起落次数
		int FLT_LD = 0;// 轮挡

		long basicAirMin = 0;
		long basicBlockMin = 0;
		long basicOmisFc = 0;

		ArrayList<String> updateSqlList = new ArrayList<String>();
		String[] CO_SEQArr;
		String[] iata_cArr;
		String CO_SEQa = "";
		String iata_ca = "";
		try {
			if (iata_c.indexOf(",") > 0) {// 有多个的iata_ca的情况
				iata_cArr = iata_c.split(",");
				for (int i = 0; i < iata_cArr.length; i++) {
					if (iata_ca == "") {
						iata_ca = "'" + iata_cArr[i] + "'";
					} else {
						iata_ca += ",'" + iata_cArr[i] + "'";
					}
				}
			} else {
				iata_ca = "'" + iata_c + "'";

			}
			if (CO_SEQ.indexOf(",") > 0) {// 有多个的CO_SEQ的情况
				CO_SEQArr = CO_SEQ.split(",");
				for (int i = 0; i < CO_SEQArr.length; i++) {
					if (CO_SEQa == "") {
						CO_SEQa = "'" + CO_SEQArr[i] + "'";
					} else {
						CO_SEQa += ",'" + CO_SEQArr[i] + "'";
					}
				}
			} else {
				CO_SEQa = "'" + CO_SEQ + "'";

			}
			// 先把数据标志置零
			if (d == "3") {// 取三天的数据，前一天，当天，后一天
				logger.info("采集OMIS库中前后24小时航班日志数据开始！");
				upstr = "update I_FLT_SCH_LIST set FLT_CHANGE_FLAG='0' where FLT_CHANGE_FLAG='1' and iata_c in(" + iata_ca + ")" + " and (CO_SEQ in(" + CO_SEQa + ") or arr_apt='" + BASEORGID
						+ "' or dep_apt='" + BASEORGID + "' ) " + "and FLT_DATE >= TO_DATE('" + getNowDate() + "','YYYY-MM-DD')-2 and flt_date<=TO_DATE('" + getNowDate() + "','YYYY-MM-DD')+1";
				apms.executeUpdateByDS(ApmsConst.DS_APMS, upstr);
				apms.commit(ApmsConst.DS_APMS);
				// 置零结束-----------------------------------------------
				// 取omis数据开始----------------

				queryomisfly = "select * from airchina.flight_information t " + " WHERE iata_c in(" + iata_ca + ") " + " and (CO_SEQ in(" + CO_SEQa + ") or arr_apt='" + BASEORGID + "' or dep_apt='"
						+ BASEORGID + "' ) " + "and  FLT_DATE>= TO_DATE('" + getNowDate() + "','YYYY-MM-DD')-2  and flt_date<=TO_DATE('" + getNowDate() + "','YYYY-MM-DD')+1 "
						+ "ORDER BY AC_ID,FLT_DATE,STD";

			} else {// 取7天的数据
				logger.info("采集OMIS库中后" + d + "天航班日志数据开始！");
				upstr = "update I_FLT_SCH_LIST set FLT_CHANGE_FLAG='0' where  FLT_CHANGE_FLAG='1' and iata_c in(" + iata_ca + ") and (CO_SEQ in(" + CO_SEQa + ") or arr_apt='" + BASEORGID
						+ "' or dep_apt='" + BASEORGID + "' ) and FLT_DATE >=TO_DATE('" + getNowDate() + "','YYYY-MM-DD')-2 and FLT_DATE<= TO_DATE('" + getNowDate() + "','YYYY-MM-DD')+" + d;
				apms.executeUpdateByDS(ApmsConst.DS_APMS, upstr);
				apms.commit(ApmsConst.DS_APMS);
				// 置零结束-----------------------------------------------
				// 取omis数据开始----------------

				queryomisfly = "select * from airchina.flight_information t WHERE iata_c in(" + iata_ca + ") and (CO_SEQ in(" + CO_SEQa + ") or arr_apt='" + BASEORGID + "' or dep_apt='" + BASEORGID
						+ "' ) and  FLT_DATE>= TO_DATE('" + getNowDate() + "','YYYY-MM-DD')-2 AND FLT_DATE<= TO_DATE('" + getNowDate() + "','YYYY-MM-DD')+40 ORDER BY AC_ID,FLT_DATE,STD";
			}

			updateSqlList.clear();
			HashVO[] vos = omis.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_OMIS, queryomisfly);// 取得数据
			for (int i = 0; i < vos.length; i++) {
				// 对查询到的数据进行处理
				HashVO vo = vos[i];

				// 计算航段类型,PF--航前,TR--短停,AF--航后,AF_PF--航后航前
				fltAtt = dealFltAtt(vos, i);

				queryapms = "select * from I_FLT_SCH_LIST where FLT_PK=" + vo.getLongValue("FLT_PK");
				HashVO[] voapms = apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, queryapms);
				if (voapms.length == 0) {// 在apms库上查找是否 存在数据，如果不存在则添加
					logger.info("APMS上没有新数据，开始写入新的数据！");

					insertstr = "insert into I_FLT_SCH_LIST(FLTRECID,FLT_PK,GLOBAL_PK,FLT_DATE,IATA_C," + "CO_SEQ,FLT_ID,FLT_SEQ,FLT_TASK,CON_FLT_ID,AC_ID,AC_TYPE,FLT_TYPE,DEP_APT,STD,STA,"
							+ "ARR_APT,ETD,ETD_SRC,ETA,ETA_SRC,OFF_TIME,OFF_SRC,ON_TIME,ON_SRC,OUT_TIME,OUT_SRC,IN_TIME,IN_SRC,"
							+ "OPEN_TIME,CLOSE_TIME,AC_STOP,AC_STOP_MOVE,VIP,VIP_NAME,ALT_APT1,ALT_APT2,DELAY_CODE,DELAY_TIME,CANCEL_FLAG,MEMO,"
							+ "PLAN_FLAG,CREATE_SRC,UPDATE_SRC,STA_SRC,FLT_TASK_SRC,AC_SRC,AC_STOP_SRC,DELAY_SRC,ALT_APT_SRC,CANCEL_SRC,AC_TYPE_OLD,"
							+ "AC_TYPE_MARKET,AC_STOP_ARR,AC_STOP_ARR_MOVE,OFF_DELAY_STANDARD,ON_DELAY_STANDARD,FIRST_FLAG,AC_OWNER,CANCEL_TYPE,CANCEL_REASON,"
							+ "CANCEL_TIME,DELAY_FLAG_RLS,DELAY_CODE_RLS,MEMO_RLS,PERFORMANCE_REC,FLT_ATT,FLT_CHANGE_FLAG,FLTAIR,FLTGRD,FLT_LD,AIRSUM,GRDSUM,LDSUM,UPDATETIME)";

					// 拼装Insert语句
					insertstr = insertstr + getInsertColumnValueStr(vo) + "'" + fltAtt + "'," + FLT_CHANGE_FLAG + "," + FLTAIR + "," + FLTGRD + "," + FLT_LD + "," + basicAirMin + "," + basicOmisFc
							+ "," + basicBlockMin + ",sysdate)";

					updateSqlList.add(insertstr);

					logger.info("新的航班数据写入完成！");

				} else {// 如果存在就判断是否有改变，如果有改变就修改，如果没有改变就不修改
					logger.info("数据比较到第" + i + "条！");
					// 判断变更的数据
					fltcompare = getUpdateColumnStr(vo, voapms, i);

					upstr = "update I_FLT_SCH_LIST set FLT_CHANGE_FLAG='1',FLT_ATT='" + fltAtt + "'," + fltcompare + ",FLTAIR=" + FLTAIR + ",AIRSUM=" + basicAirMin + ",FLT_LD=" + FLT_LD + ",LDSUM="
							+ basicBlockMin + ",FLTGRD=" + FLTGRD + ",GRDSUM=" + basicOmisFc + " where FLT_PK=" + vo.getLongValue("FLT_PK");

					updateSqlList.add(upstr);
				}

			}

			// 删除没有找到的数据
			logger.info("删除没有找到的数据！");
			upstr = "delete from I_FLT_SCH_LIST where FLT_CHANGE_FLAG='0' " + " and FLT_DATE BETWEEN TO_DATE('" + getNowDate() + "','YYYY-MM-DD')-1 AND TO_DATE('" + getNowDate() + "','YYYY-MM-DD')+1";
			updateSqlList.add(upstr);

			apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
			apms.commit(ApmsConst.DS_APMS);
			if (d == "3") {
				logger.info("采集OMIS库中24小时航班日志数据完成！");
			} else {
				logger.info("采集OMIS库中" + d + "天航班日志数据完成！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			omis.releaseContext(ApmsConst.DS_OMIS);
			apms.releaseContext(ApmsConst.DS_DEFAULT);
			apms.releaseContext(ApmsConst.DS_APMS);

		}
		return recordCount;
	}

	/** 判断航班维护类型 */
	private String dealFltAtt(HashVO[] vos, int i) {
		String fltAtt = "";
		HashVO vo = vos[i];

		if (FLT_Date_F.equals("")) {// 第一个航班
			if (vo.getStringValue("FLT_DATE") == null) {
				FLT_Date_C = "";
			} else {
				FLT_Date_C = vo.getStringValue("FLT_DATE");
			}
			if (vo.getStringValue("AC_ID") == null) {
				FLT_Acid_C = "";
			} else {
				FLT_Acid_C = vo.getStringValue("AC_ID");
			}

			FLT_Cancel_C = vo.getIntegerValue("CANCEL_FLAG");
			if ((i + 1) >= vos.length) {// 记录集未
				FLT_Date_B = "";
				FLT_Acid_B = "";
			} else {
				HashVO vo1 = vos[i + 1];
				FLT_Date_B = vo1.getStringValue("FLT_DATE");
				if (vo1.getStringValue("ac_id") == null) {
					FLT_Acid_B = "";
				} else {
					FLT_Acid_B = vo1.getStringValue("ac_id");
				}
			}
			switch (FLT_Cancel_C) {

				case 0: {// 没有取消航班
					if (FLT_Date_B.equals(FLT_Date_C)) {// 前后数据时同一天的数据
						if (FLT_Acid_B.equals(FLT_Acid_C)) {// 前后飞机是同一架飞机
							fltAtt = "PF";
						} else {
							fltAtt = "PF_AF";
						}
					} else {
						fltAtt = "PF_AF";
					}
					break;
				}
				case 1: {// 取消航班
					fltAtt = "CNL";
					break;
				}

				default: {
					if (FLT_Date_B.equals(FLT_Date_C)) {// 前后数据时同一天的数据
						if (FLT_Acid_B.equals(FLT_Acid_C)) {// 前后数据飞机是同一架飞机
							fltAtt = "PF";
						} else {
							fltAtt = "PF_AF";
						}
					} else {
						fltAtt = "PF_AF";
					}
				}
			}
			FLT_Date_F = FLT_Date_C;
			FLT_Acid_F = FLT_Acid_C;
		} else {// 不是第一条数据
			if (vo.getStringValue("FLT_DATE") == null) {
				FLT_Date_C = "";
			} else {
				FLT_Date_C = vo.getStringValue("FLT_DATE");
			}
			if (vo.getStringValue("AC_ID") == null) {
				FLT_Acid_C = "";
			} else {
				FLT_Acid_C = vo.getStringValue("AC_ID");
			}
			FLT_Cancel_C = vo.getIntegerValue("CANCEL_FLAG");
			if ((i + 1) >= vos.length) {// 记录集未
				FLT_Date_B = "";
				FLT_Acid_B = "";
			} else {
				HashVO vo1 = vos[i + 1];
				FLT_Date_B = vo1.getStringValue("FLT_DATE");
				FLT_Acid_B = vo1.getStringValue("ac_id");
			}
			switch (FLT_Cancel_C) {
			case 0: {// 未取消的航班
				if (FLT_Date_F.equals(FLT_Date_C)) {// 上一个数据跟这个数据是同一天的情况
					if (FLT_Date_C.equals(FLT_Date_B)) {// 同一天的航班
						if (FLT_Acid_F.equals(FLT_Acid_C)) {// 同一架飞机
							if (FLT_Acid_C.equals(FLT_Acid_B)) {// 同一架飞机,是短停
								fltAtt = "TR";
							} else { // 'FLT_Acid_C <> FLT_Acid_B不同一架飞机 是航后
								fltAtt = "AF";
							}

						} else { // 'FLT_Acid_F <> FLT_Acid_C航班日期不相同
							if (FLT_Acid_C.equals(FLT_Acid_B)) {// 飞机相同则是航前
								fltAtt = "PF";
							} else {// 'FLT_Acid_C <> FLT_Acid_B飞机不相同，航后航前
								fltAtt = "PF_AF";
							}

						}

					} else { // 'FLT_Date_C <> FLT_Date_B航班日期不同
						if (FLT_Acid_F.equals(FLT_Acid_C)) {// 与上一个数据的飞机相同
							if (FLT_Acid_C.equals(FLT_Acid_B)) {// 与下一个数据的飞机相同
								fltAtt = "AF";
							} else {// 'FLT_Acid_C <> FLT_Acid_B
								fltAtt = "AF";
							}
						} else {// 'FLT_Acid_F <> FLT_Acid_C与上个数据的飞机不相同
							if (FLT_Acid_C.equals(FLT_Acid_B)) {// 与下个数据的飞机相同
								fltAtt = "PF_AF";
							} else {
								fltAtt = "PF_AF";
							}
						}

					}
				} else {// 'FLT_Date_F <> FLT_Date_C与上个数据的日期不同
					if (FLT_Date_C.equals(FLT_Date_B)) {// 与下个数据的日期相同
						if (FLT_Acid_F.equals(FLT_Acid_C)) {// 与上个数据的飞机相同
							if (FLT_Acid_C.equals(FLT_Acid_B)) {// 与下个数据的飞机相同
								fltAtt = "PF";
							} else {// 'FLT_Acid_C <> FLT_Acid_B//与下个数据的飞机不同
								fltAtt = "PF_AF";

							}
						} else {// ' FLT_Acid_F <> FLT_Acid_C与上个数据的飞机不同
							if (FLT_Acid_C.equals(FLT_Acid_B)) {// 与下个数据的飞机相同
								fltAtt = "PF";
							} else {// 'FLT_Acid_C <> FLT_Acid_B//与下个数据的飞机不相同
								fltAtt = "PF_AF";
							}

						}
					} else {// 'FLT_Date_C <> FLT_Date_B//与下个数据的日期不同
						if (FLT_Acid_F.equals(FLT_Acid_C)) {// 与上个数据的飞机相同
							if (FLT_Acid_C.equals(FLT_Acid_B)) {// 与下个数据的飞机相同
								fltAtt = "PF_AF";
							} else {// 'FLT_Acid_C <> FLT_Acid_B与下个数据非飞机不同
								fltAtt = "PF_AF";
							}

						} else {// 'FLT_Acid_F <> FLT_Acid_C与上个数据飞机不同
							if (FLT_Acid_C.equals(FLT_Acid_B)) {// 与下个数据飞机相同
								fltAtt = "PF_AF";
							} else {
								fltAtt = "PF_AF";
							}

						}
					}
				}
				break;
			}
			case 1: {// 取消航班
				fltAtt = "CNL";
				break;
			}
			default: {

			}

			}
			FLT_Date_F = FLT_Date_C;
			FLT_Acid_F = FLT_Acid_C;
		}

		return fltAtt;
	}

	/** 计算空中时间 */
	private int flt_airTime(HashVO vo, int i) {
		/** 空中时间 */
		int FLTAIR = 0;
		Date date1 = null;
		Date date2 = null;
		if (vo.getStringValue("off_time") == null || vo.getStringValue("off_time").compareTo("1900-01-01") == 0) { // '没有离港时间时

			if (vo.getDateValue("etd") != null) {
				date1 = vo.getTimeStampValue("etd");
			} else {
				date1 = vo.getTimeStampValue("std");
			}
		} else {
			date1 = vo.getTimeStampValue("off_time");
		}

		if (vo.getStringValue("on_time") == null || vo.getStringValue("on_time").compareTo("1900-01-01") == 0) { // '没有到达时间时
			if (vo.getDateValue("eta") != null) {
				date2 = vo.getTimeStampValue("eta");
			} else {
				date2 = vo.getTimeStampValue("sta");
			}
		} else {
			date2 = vo.getTimeStampValue("on_time");
		}

		FLTAIR = Javadatediff.dateDiff("minute", date1, date2);

		return FLTAIR;

	}

	private String getInsertColumnValueStr(HashVO vo) {

		String insertstr = "";

		if (vo.getDateValue("FLT_DATE") == null) {
			insertstr = insertstr + "values( s_I_FLT_SCH_LIST.nextval," + vo.getIntegerValue("FLT_PK") + ",'" + vo.getStringValue("GLOBAL_PK") + "',null,";
		} else {
			insertstr = insertstr + "values( s_I_FLT_SCH_LIST.nextval," + vo.getIntegerValue("FLT_PK") + ",'" + vo.getStringValue("GLOBAL_PK") + "',to_date(substr('"
					+ vo.getStringValue("FLT_DATE").trim() + "',1,10),'yyyy-mm-dd'),";
		}

		if (vo.getStringValue("IATA_C") == null || vo.getStringValue("IATA_C") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("IATA_C") + "',";
		}

		if (vo.getStringValue("CO_SEQ") == null || vo.getStringValue("CO_SEQ") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("CO_SEQ") + "',";
		}

		if (vo.getStringValue("FLT_ID") == null || vo.getStringValue("FLT_ID") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("FLT_ID") + "',";
		}

		if (vo.getStringValue("FLT_SEQ") == null || vo.getStringValue("FLT_SEQ") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("FLT_SEQ") + "',";
		}

		if (vo.getStringValue("FLT_TASK") == null || vo.getStringValue("FLT_TASK") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("FLT_TASK") + "',";
		}
		if (vo.getStringValue("CON_FLT_ID") == null || vo.getStringValue("CON_FLT_ID") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("CON_FLT_ID") + "',";
		}
		if (vo.getStringValue("AC_ID") == null || vo.getStringValue("AC_ID") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("AC_ID") + "',";
		}
		if (vo.getStringValue("AC_TYPE") == null || vo.getStringValue("AC_TYPE") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("AC_TYPE") + "',";
		}

		if (vo.getStringValue("FLT_TYPE") == null || vo.getStringValue("FLT_TYPE") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("FLT_TYPE") + "',";
		}
		if (vo.getStringValue("DEP_APT") == null || vo.getStringValue("DEP_APT") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("DEP_APT") + "',";
		}
		if (vo.getStringValue("STD") == null || vo.getStringValue("STD") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("STD").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("STA") == null || vo.getStringValue("STA") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("STA").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("ARR_APT") == null || vo.getStringValue("ARR_APT") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("ARR_APT") + "',";
		}
		if (vo.getStringValue("ETD") == null || vo.getStringValue("ETD") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("ETD").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("ETD_SRC") == null || vo.getStringValue("ETD_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("ETD_SRC") + "',";
		}
		if (vo.getStringValue("ETA") == null || vo.getStringValue("ETA") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("ETA").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("ETA_SRC") == null || vo.getStringValue("ETA_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("ETA_SRC") + "',";
		}
		if (vo.getStringValue("OFF_TIME") == null || vo.getStringValue("OFF_TIME") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("OFF_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("OFF_SRC") == null || vo.getStringValue("OFF_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("OFF_SRC") + "',";
		}
		if (vo.getStringValue("ON_TIME") == null || vo.getStringValue("ON_TIME") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("ON_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("ON_SRC") == null || vo.getStringValue("ON_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("ON_SRC") + "',";
		}
		if (vo.getStringValue("OUT_TIME") == null || vo.getStringValue("OUT_TIME") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + " to_date(substr('" + vo.getStringValue("OUT_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("OUT_SRC") == null || vo.getStringValue("OUT_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("OUT_SRC") + "',";
		}
		if (vo.getStringValue("IN_TIME") == null || vo.getStringValue("IN_TIME") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("IN_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("IN_SRC") == null || vo.getStringValue("IN_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("IN_SRC") + "',";
		}
		if (vo.getStringValue("OPEN_TIME") == null || vo.getStringValue("OPEN_TIME") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("OPEN_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("CLOSE_TIME") == null || vo.getStringValue("CLOSE_TIME") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("CLOSE_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("AC_STOP") == null || vo.getStringValue("AC_STOP") == "") {
			insertstr = insertstr + "null,null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("AC_STOP") + "','" + vo.getStringValue("AC_STOP") + "',";
		}
		if (vo.getStringValue("VIP") == null || vo.getStringValue("VIP") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("VIP") + "',";
		}
		if (vo.getStringValue("VIP_NAME") == null || vo.getStringValue("VIP_NAME") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("VIP_NAME") + "',";
		}
		if (vo.getStringValue("ALT_APT1") == null || vo.getStringValue("ALT_APT1") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("ALT_APT1") + "',";
		}
		if (vo.getStringValue("ALT_APT2") == null || vo.getStringValue("ALT_APT2") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("ALT_APT2") + "',";
		}
		if (vo.getStringValue("DELAY_CODE") == null || vo.getStringValue("DELAY_CODE") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("DELAY_CODE") + "',";
		}
		if (vo.getStringValue("DELAY_TIME") == null || vo.getStringValue("DELAY_TIME") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("DELAY_TIME") + "',";
		}
		if (vo.getStringValue("CANCEL_FLAG") == null || vo.getStringValue("CANCEL_FLAG") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("CANCEL_FLAG") + "',";
		}
		if (vo.getStringValue("MEMO") == null || vo.getStringValue("MEMO") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("MEMO") + "',";
		}
		if (vo.getStringValue("PLAN_FLAG") == null || vo.getStringValue("PLAN_FLAG") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("PLAN_FLAG") + "',";
		}
		if (vo.getStringValue("CREATE_SRC") == null || vo.getStringValue("CREATE_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("CREATE_SRC") + "',";
		}
		if (vo.getStringValue("UPDATE_SRC") == null || vo.getStringValue("UPDATE_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("UPDATE_SRC") + "',";
		}
		if (vo.getStringValue("STA_SRC") == null || vo.getStringValue("STA_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("STA_SRC") + "',";
		}
		if (vo.getStringValue("FLT_TASK_SRC") == null || vo.getStringValue("FLT_TASK_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("FLT_TASK_SRC") + "',";
		}
		if (vo.getStringValue("AC_SRC") == null || vo.getStringValue("AC_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("AC_SRC") + "',";
		}
		if (vo.getStringValue("AC_STOP_SRC") == null || vo.getStringValue("AC_STOP_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("AC_STOP_SRC") + "',";
		}
		if (vo.getStringValue("DELAY_SRC") == null || vo.getStringValue("DELAY_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("DELAY_SRC") + "',";
		}
		if (vo.getStringValue("ALT_APT_SRC") == null || vo.getStringValue("ALT_APT_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("ALT_APT_SRC") + "',";
		}
		if (vo.getStringValue("CANCEL_SRC") == null || vo.getStringValue("CANCEL_SRC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("CANCEL_SRC") + "',";
		}
		if (vo.getStringValue("AC_TYPE_OLD") == null || vo.getStringValue("AC_TYPE_OLD") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("AC_TYPE_OLD") + "',";
		}
		if (vo.getStringValue("AC_TYPE_MARKET") == null || vo.getStringValue("AC_TYPE_MARKET") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("AC_TYPE_MARKET") + "',";
		}
		if (vo.getStringValue("AC_STOP_ARR") == null || vo.getStringValue("AC_STOP_ARR") == "") {
			insertstr = insertstr + "null,null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("AC_STOP_ARR") + "','" + vo.getStringValue("AC_STOP_ARR") + "',";
		}
		if (vo.getStringValue("OFF_DELAY_STANDARD") == null || vo.getStringValue("OFF_DELAY_STANDARD") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("OFF_DELAY_STANDARD") + "',";
		}
		if (vo.getStringValue("ON_DELAY_STANDARD") == null || vo.getStringValue("ON_DELAY_STANDARD") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("ON_DELAY_STANDARD") + "',";
		}
		if (vo.getStringValue("FIRST_FLAG") == null || vo.getStringValue("FIRST_FLAG") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("FIRST_FLAG") + "',";
		}
		if (vo.getStringValue("AC_OWNER") == null || vo.getStringValue("AC_OWNER") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("AC_OWNER") + "',";
		}
		if (vo.getStringValue("CANCEL_TYPE") == null || vo.getStringValue("CANCEL_TYPE") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("CANCEL_TYPE") + "',";
		}
		if (vo.getStringValue("CANCEL_REASON") == null || vo.getStringValue("CANCEL_REASON") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("CANCEL_REASON") + "',";
		}
		if (vo.getStringValue("CANCEL_TIME") == null || vo.getStringValue("CANCEL_TIME") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "to_date(substr('" + vo.getStringValue("CANCEL_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		if (vo.getStringValue("DELAY_FLAG_RLS") == null || vo.getStringValue("DELAY_FLAG_RLS") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("DELAY_FLAG_RLS") + "',";
		}
		if (vo.getStringValue("DELAY_CODE_RLS") == null || vo.getStringValue("DELAY_CODE_RLS") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("DELAY_CODE_RLS") + "',";
		}
		if (vo.getStringValue("MEMO_RLS") == null || vo.getStringValue("MEMO_RLS") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("MEMO_RLS") + "',";
		}
		if (vo.getStringValue("PERFORMANCE_REC") == null || vo.getStringValue("PERFORMANCE_REC") == "") {
			insertstr = insertstr + "null,";
		} else {
			insertstr = insertstr + "'" + vo.getStringValue("PERFORMANCE_REC") + "',";
		}

		return insertstr;
	}

	private String getUpdateColumnStr(HashVO vo, HashVO[] vo1, int i) {
		String upstr = "";
		// 两条记录进行比较
		HashVO voapms = vo1[0];
		if (vo.getStringValue("flt_id") == null && voapms.getStringValue("flt_id") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("flt_id") != null && voapms.getStringValue("flt_id") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "flt_id='" + vo.getStringValue("flt_id") + "',";
		} else if (vo.getStringValue("flt_id") == null && voapms.getStringValue("flt_id") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "flt_id=" + vo.getStringValue("flt_id") + ",";
		} else if (!vo.getStringValue("flt_id").equals(voapms.getStringValue("flt_id"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "flt_id='" + vo.getStringValue("flt_id") + "',";
		}

		// /////////////////////flt_Date///////
		if (vo.getStringValue("flt_date") == null && voapms.getStringValue("flt_date") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("flt_date") != null && voapms.getStringValue("flt_date") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "flt_date=to_date(substr('" + vo.getDateValue("flt_date") + "',1,10),'yyyy-mm-dd'),";
		} else if (vo.getStringValue("flt_date") == null && voapms.getDateValue("flt_date") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "flt_date=" + vo.getTimeStampValue("flt_date") + ",";
		} else if (!vo.getStringValue("flt_date").equals(voapms.getDateValue("flt_date"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "flt_date=to_date(substr('" + vo.getDateValue("flt_date") + "',1,10),'yyyy-mm-dd'),";
		}
		// ///////////////////////////////////////////
		// /////////////////////iata_c///////
		if (vo.getStringValue("iata_c") == null && voapms.getStringValue("iata_c") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("iata_c") != null && voapms.getStringValue("iata_c") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "iata_c='" + vo.getStringValue("iata_c") + "',";
		} else if (vo.getStringValue("iata_c") == null && voapms.getStringValue("iata_c") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "iata_c=" + vo.getStringValue("iata_c") + ",";
		} else if (!vo.getStringValue("iata_c").equals(voapms.getStringValue("iata_c"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "iata_c='" + vo.getStringValue("iata_c") + "',";
		}
		// ///////////////////////////////////////////
		// /////////////////////CO_SEQ///////
		if (vo.getStringValue("CO_SEQ") == null && voapms.getStringValue("CO_SEQ") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("CO_SEQ") != null && voapms.getStringValue("CO_SEQ") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CO_SEQ='" + vo.getStringValue("CO_SEQ") + "',";
		} else if (vo.getStringValue("CO_SEQ") == null && voapms.getStringValue("CO_SEQ") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CO_SEQ=" + vo.getStringValue("CO_SEQ") + ",";
		} else if (!vo.getStringValue("CO_SEQ").equals(voapms.getStringValue("CO_SEQ"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "CO_SEQ='" + vo.getStringValue("CO_SEQ") + "',";
		}
		// /////////////////////FLT_SEQ///////
		if (vo.getStringValue("FLT_SEQ") == null && voapms.getStringValue("FLT_SEQ") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("FLT_SEQ") != null && voapms.getStringValue("FLT_SEQ") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "FLT_SEQ='" + vo.getStringValue("FLT_SEQ") + "',";
		} else if (vo.getStringValue("FLT_SEQ") == null && voapms.getStringValue("FLT_SEQ") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "FLT_SEQ=" + vo.getStringValue("FLT_SEQ") + ",";
		} else if (!vo.getStringValue("FLT_SEQ").equals(voapms.getStringValue("FLT_SEQ"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "FLT_SEQ='" + vo.getStringValue("FLT_SEQ") + "',";
		}
		// /////////////////////FLT_TASK///////
		if (vo.getStringValue("FLT_TASK") == null && voapms.getStringValue("FLT_TASK") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("FLT_TASK") != null && voapms.getStringValue("FLT_TASK") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "FLT_TASK='" + vo.getStringValue("FLT_TASK") + "',";
		} else if (vo.getStringValue("FLT_TASK") == null && voapms.getStringValue("FLT_TASK") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "FLT_TASK=" + vo.getStringValue("FLT_TASK") + ",";
		} else if (!vo.getStringValue("FLT_TASK").equals(voapms.getStringValue("FLT_TASK"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "FLT_TASK='" + vo.getStringValue("FLT_TASK") + "',";
		}
		// /////////////////////CON_FLT_ID///////
		if (vo.getStringValue("CON_FLT_ID") == null && voapms.getStringValue("CON_FLT_ID") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("CON_FLT_ID") != null && voapms.getStringValue("CON_FLT_ID") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CON_FLT_ID='" + vo.getStringValue("CON_FLT_ID") + "',";
		} else if (vo.getStringValue("CON_FLT_ID") == null && voapms.getStringValue("CON_FLT_ID") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CON_FLT_ID=" + vo.getStringValue("CON_FLT_ID") + ",";
		} else if (!vo.getStringValue("CON_FLT_ID").equals(voapms.getStringValue("CON_FLT_ID"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "CON_FLT_ID='" + vo.getStringValue("CON_FLT_ID") + "',";
		}
		// /////////////////////AC_ID///////
		if (vo.getStringValue("AC_ID") == null && voapms.getStringValue("AC_ID") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("AC_ID") != null && voapms.getStringValue("AC_ID") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_ID='" + vo.getStringValue("AC_ID") + "',";
		} else if (vo.getStringValue("AC_ID") == null && voapms.getStringValue("AC_ID") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_ID=" + vo.getStringValue("AC_ID") + ",";
		} else if (!vo.getStringValue("AC_ID").equals(voapms.getStringValue("AC_ID"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "AC_ID='" + vo.getStringValue("AC_ID") + "',";
		}
		// /////////////////////AC_TYPE///////
		if (vo.getStringValue("AC_TYPE") == null && voapms.getStringValue("AC_TYPE") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("AC_TYPE") != null && voapms.getStringValue("AC_TYPE") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_TYPE='" + vo.getStringValue("AC_TYPE") + "',";
		} else if (vo.getStringValue("AC_TYPE") == null && voapms.getStringValue("AC_TYPE") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_TYPE=" + vo.getStringValue("AC_TYPE") + ",";
		} else if (!vo.getStringValue("AC_TYPE").equals(voapms.getStringValue("AC_TYPE"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "AC_TYPE='" + vo.getStringValue("AC_TYPE") + "',";
		}
		// /////////////////////FLT_Type///////
		if (vo.getStringValue("FLT_Type") == null && voapms.getStringValue("FLT_Type") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("FLT_Type") != null && voapms.getStringValue("FLT_Type") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "FLT_Type='" + vo.getStringValue("FLT_Type") + "',";
		} else if (vo.getStringValue("FLT_Type") == null && voapms.getStringValue("FLT_Type") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "FLT_Type=" + vo.getStringValue("FLT_Type") + ",";
		} else if (!vo.getStringValue("FLT_Type").equals(voapms.getStringValue("FLT_Type"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "FLT_Type='" + vo.getStringValue("FLT_Type") + "',";
		}
		// /////////////////////DEP_APT///////
		if (vo.getStringValue("DEP_APT") == null && voapms.getStringValue("DEP_APT") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("DEP_APT") != null && voapms.getStringValue("DEP_APT") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "DEP_APT='" + vo.getStringValue("DEP_APT") + "',";
		} else if (vo.getStringValue("DEP_APT") == null && voapms.getStringValue("DEP_APT") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "DEP_APT=" + vo.getStringValue("DEP_APT") + ",";
		} else if (!vo.getStringValue("DEP_APT").equals(voapms.getStringValue("DEP_APT"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "DEP_APT='" + vo.getStringValue("DEP_APT") + "',";
		}
		// /////////////////////STD///////
		if (vo.getStringValue("STD") == null && voapms.getStringValue("STD") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("STD") != null && voapms.getStringValue("STD") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "STD=to_date(substr('" + vo.getStringValue("STD").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (vo.getStringValue("STD") == null && voapms.getStringValue("STD") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "STD=" + vo.getTimeStampValue("STD") + ",";
		} else if (!vo.getStringValue("STD").equals(voapms.getStringValue("STD"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "STD=to_date(substr('" + vo.getStringValue("STD").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		// /////////////////////STA///////
		if (vo.getStringValue("STA") == null && voapms.getStringValue("STA") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("STA") != null && voapms.getStringValue("STA") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "STA=to_date(substr('" + vo.getStringValue("STA").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (vo.getStringValue("STA") == null && voapms.getStringValue("STA") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "STA=" + vo.getStringValue("STA") + ",";
		} else if (!vo.getStringValue("STA").equals(voapms.getStringValue("STA"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "STA=to_date(substr('" + vo.getStringValue("STA").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		// /////////////////////ARR_APT///////
		if (vo.getStringValue("ARR_APT") == null && voapms.getStringValue("ARR_APT") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("ARR_APT") != null && voapms.getStringValue("ARR_APT") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ARR_APT='" + vo.getStringValue("ARR_APT") + "',";
		} else if (vo.getStringValue("ARR_APT") == null && voapms.getStringValue("ARR_APT") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ARR_APT=" + vo.getStringValue("ARR_APT") + ",";
		} else if (!vo.getStringValue("ARR_APT").equals(voapms.getStringValue("ARR_APT"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "ARR_APT='" + vo.getStringValue("ARR_APT") + "',";
		}
		// /////////////////////ETD///////
		if (vo.getStringValue("ETD") == null && voapms.getStringValue("ETD") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("ETD") != null && voapms.getStringValue("ETD") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ETD=to_date(substr('" + vo.getStringValue("ETD").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (vo.getStringValue("ETD") == null && voapms.getStringValue("ETD") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ETD=" + vo.getStringValue("ETD") + ",";
		} else if (!vo.getStringValue("ETD").equals(voapms.getStringValue("ETD"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "ETD=to_date(substr('" + vo.getStringValue("ETD").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		// /////////////////////ETD_SRC///////
		if (vo.getStringValue("ETD_SRC") == null && voapms.getStringValue("ETD_SRC") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("ETD_SRC") != null && voapms.getStringValue("ETD_SRC") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ETD_SRC='" + vo.getStringValue("ETD_SRC") + "',";
		} else if (vo.getStringValue("ETD_SRC") == null && voapms.getStringValue("ETD_SRC") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ETD_SRC=" + vo.getStringValue("ETD_SRC") + ",";
		} else if (!vo.getStringValue("ETD_SRC").equals(voapms.getStringValue("ETD_SRC"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "ETD_SRC='" + vo.getStringValue("ETD_SRC") + "',";
		}
		// /////////////////////ETA///////
		if (vo.getStringValue("ETA") == null && voapms.getStringValue("ETA") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("ETA") != null && voapms.getStringValue("ETA") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ETA=to_date(substr('" + vo.getStringValue("ETA").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (vo.getStringValue("ETA") == null && voapms.getStringValue("ETA") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ETA=" + vo.getStringValue("ETA") + ",";
		} else if (!vo.getStringValue("ETA").equals(voapms.getStringValue("ETA"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "ETA=to_date(substr('" + vo.getStringValue("ETA").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		// /////////////////////ETA_SRC///////
		if (vo.getStringValue("ETA_SRC") == null && voapms.getStringValue("ETA_SRC") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("ETA_SRC") != null && voapms.getStringValue("ETA_SRC") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ETA_SRC='" + vo.getStringValue("ETA_SRC") + "',";
		} else if (vo.getStringValue("ETA_SRC") == null && voapms.getStringValue("ETA_SRC") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ETA_SRC=" + vo.getStringValue("ETA_SRC") + ",";
		} else if (!vo.getStringValue("ETA_SRC").equals(voapms.getStringValue("ETA_SRC"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "ETA_SRC='" + vo.getStringValue("ETA_SRC") + "',";
		}
		// /////////////////////OFF_TIME///////
		if (vo.getStringValue("OFF_TIME") == null && voapms.getStringValue("OFF_TIME") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("OFF_TIME") != null && voapms.getStringValue("OFF_TIME") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OFF_TIME=to_date(substr('" + vo.getStringValue("OFF_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (vo.getStringValue("OFF_TIME") == null && voapms.getStringValue("OFF_TIME") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OFF_TIME=" + vo.getStringValue("OFF_TIME") + ",";
		} else if (!vo.getStringValue("OFF_TIME").equals(voapms.getStringValue("OFF_TIME"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "OFF_TIME=to_date(substr('" + vo.getStringValue("OFF_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		// /////////////////////OFF_SRC///////
		if (vo.getStringValue("OFF_SRC") == null && voapms.getStringValue("OFF_SRC") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("OFF_SRC") != null && voapms.getStringValue("OFF_SRC") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OFF_SRC='" + vo.getStringValue("OFF_SRC") + "',";
		} else if (vo.getStringValue("OFF_SRC") == null && voapms.getStringValue("OFF_SRC") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OFF_SRC=" + vo.getStringValue("OFF_SRC") + ",";
		} else if (!vo.getStringValue("OFF_SRC").equals(voapms.getStringValue("OFF_SRC"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "OFF_SRC='" + vo.getStringValue("OFF_SRC") + "',";
		}
		// /////////////////////ON_TIME///////
		if (vo.getStringValue("ON_TIME") == null && voapms.getStringValue("ON_TIME") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("ON_TIME") != null && voapms.getStringValue("ON_TIME") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ON_TIME=to_date(substr('" + vo.getStringValue("ON_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (vo.getStringValue("ON_TIME") == null && voapms.getStringValue("ON_TIME") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ON_TIME=" + vo.getStringValue("ON_TIME") + ",";
		} else if (!vo.getStringValue("ON_TIME").equals(voapms.getStringValue("ON_TIME"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "ON_TIME=to_date(substr('" + vo.getStringValue("ON_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		// /////////////////////ON_SRC///////
		if (vo.getStringValue("ON_SRC") == null && voapms.getStringValue("ON_SRC") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("ON_SRC") != null && voapms.getStringValue("ON_SRC") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ON_SRC='" + vo.getStringValue("ON_SRC") + "',";
		} else if (vo.getStringValue("ON_SRC") == null && voapms.getStringValue("ON_SRC") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ON_SRC=" + vo.getStringValue("ON_SRC") + ",";
		} else if (!vo.getStringValue("ON_SRC").equals(voapms.getStringValue("ON_SRC"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "ON_SRC='" + vo.getStringValue("ON_SRC") + "',";
		}
		// /////////////////////OUT_TIME///////
		if (vo.getStringValue("OUT_TIME") == null && voapms.getStringValue("OUT_TIME") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("OUT_TIME") != null && voapms.getStringValue("OUT_TIME") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OUT_TIME=to_date(substr('" + vo.getStringValue("OUT_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (vo.getStringValue("OUT_TIME") == null && voapms.getStringValue("OUT_TIME") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OUT_TIME=" + vo.getStringValue("OUT_TIME") + ",";
		} else if (!vo.getStringValue("OUT_TIME").equals(voapms.getStringValue("OUT_TIME"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "OUT_TIME=to_date(substr('" + vo.getStringValue("OUT_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		// /////////////////////OUT_SRC///////
		if (vo.getStringValue("OUT_SRC") == null && voapms.getStringValue("OUT_SRC") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("OUT_SRC") != null && voapms.getStringValue("OUT_SRC") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OUT_SRC='" + vo.getStringValue("OUT_SRC") + "',";
		} else if (vo.getStringValue("OUT_SRC") == null && voapms.getStringValue("OUT_SRC") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OUT_SRC=" + vo.getStringValue("OUT_SRC") + ",";
		} else if (!vo.getStringValue("OUT_SRC").equals(voapms.getStringValue("OUT_SRC"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "OUT_SRC='" + vo.getStringValue("OUT_SRC") + "',";
		}
		// /////////////////////IN_TIME///////
		if (vo.getStringValue("IN_TIME") == null && voapms.getStringValue("IN_TIME") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("IN_TIME") != null && voapms.getStringValue("IN_TIME") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "IN_TIME=to_date(substr('" + vo.getStringValue("IN_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (vo.getStringValue("IN_TIME") == null && voapms.getStringValue("IN_TIME") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "IN_TIME=" + vo.getStringValue("IN_TIME") + ",";
		} else if (!vo.getStringValue("IN_TIME").equals(voapms.getStringValue("IN_TIME"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "IN_TIME=to_date(substr('" + vo.getStringValue("IN_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		// /////////////////////IN_SRC///////
		if (vo.getStringValue("IN_SRC") == null && voapms.getStringValue("IN_SRC") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("IN_SRC") != null && voapms.getStringValue("IN_SRC") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "IN_SRC='" + vo.getStringValue("IN_SRC") + "',";
		} else if (vo.getStringValue("IN_SRC") == null && voapms.getStringValue("IN_SRC") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "IN_SRC=" + vo.getStringValue("IN_SRC") + ",";
		} else if (!vo.getStringValue("IN_SRC").equals(voapms.getStringValue("IN_SRC"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "IN_SRC='" + vo.getStringValue("IN_SRC") + "',";
		}
		// /////////////////////AC_STOP///////
		if (vo.getStringValue("AC_STOP") == null && voapms.getStringValue("AC_STOP") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("AC_STOP") != null && voapms.getStringValue("AC_STOP") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_STOP='" + vo.getStringValue("AC_STOP") + "',";
		} else if (vo.getStringValue("AC_STOP") == null && voapms.getStringValue("AC_STOP") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_STOP=" + vo.getStringValue("AC_STOP") + ",";
		} else if (!vo.getStringValue("AC_STOP").equals(voapms.getStringValue("AC_STOP"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "AC_STOP='" + vo.getStringValue("AC_STOP") + "',";
		}
		// /////////////////////DELAY_CODE///////
		if (vo.getStringValue("DELAY_CODE") == null && voapms.getStringValue("DELAY_CODE") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("DELAY_CODE") != null && voapms.getStringValue("DELAY_CODE") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "DELAY_CODE='" + vo.getStringValue("DELAY_CODE") + "',";
		} else if (vo.getStringValue("DELAY_CODE") == null && voapms.getStringValue("DELAY_CODE") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "DELAY_CODE=" + vo.getStringValue("DELAY_CODE") + ",";
		} else if (!vo.getStringValue("DELAY_CODE").equals(voapms.getStringValue("DELAY_CODE"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "DELAY_CODE='" + vo.getStringValue("DELAY_CODE") + "',";
		}
		// /////////////////////DELAY_TIME///////
		if (vo.getStringValue("DELAY_TIME") == null && voapms.getStringValue("DELAY_TIME") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("DELAY_TIME") != null && voapms.getStringValue("DELAY_TIME") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "DELAY_TIME=" + vo.getStringValue("DELAY_TIME") + ",";
		} else if (vo.getStringValue("DELAY_TIME") == null && voapms.getStringValue("DELAY_TIME") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "DELAY_TIME=" + vo.getStringValue("DELAY_TIME") + ",";
		} else if (!vo.getStringValue("DELAY_TIME").equals(voapms.getStringValue("DELAY_TIME"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "DELAY_TIME=" + vo.getStringValue("DELAY_TIME") + ",";
		}
		// /////////////////////CANCEL_FLAG///////
		if (vo.getStringValue("CANCEL_FLAG") == null && voapms.getStringValue("CANCEL_FLAG") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("CANCEL_FLAG") != null && voapms.getStringValue("CANCEL_FLAG") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CANCEL_FLAG='" + vo.getStringValue("CANCEL_FLAG") + "',";
		} else if (vo.getStringValue("CANCEL_FLAG") == null && voapms.getStringValue("CANCEL_FLAG") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CANCEL_FLAG=" + vo.getStringValue("CANCEL_FLAG") + ",";
		} else if (!vo.getStringValue("CANCEL_FLAG").equals(voapms.getStringValue("CANCEL_FLAG"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "CANCEL_FLAG='" + vo.getStringValue("CANCEL_FLAG") + "',";
		}
		// /////////////////////MEMO///////
		if (vo.getStringValue("MEMO") == null && voapms.getStringValue("MEMO") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("MEMO") != null && voapms.getStringValue("MEMO") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "MEMO='" + vo.getStringValue("MEMO") + "',";
		} else if (vo.getStringValue("MEMO") == null && voapms.getStringValue("MEMO") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "MEMO=" + vo.getStringValue("MEMO") + ",";
		} else if (!vo.getStringValue("MEMO").equals(voapms.getStringValue("MEMO"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "MEMO='" + vo.getStringValue("MEMO") + "',";
		}
		// /////////////////////AC_OWNER///////
		if (vo.getStringValue("AC_OWNER") == null && voapms.getStringValue("AC_OWNER") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("AC_OWNER") != null && voapms.getStringValue("AC_OWNER") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_OWNER='" + vo.getStringValue("AC_OWNER") + "',";
		} else if (vo.getStringValue("AC_OWNER") == null && voapms.getStringValue("AC_OWNER") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_OWNER=" + vo.getStringValue("AC_OWNER") + ",";
		} else if (!vo.getStringValue("AC_OWNER").equals(voapms.getStringValue("AC_OWNER"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "AC_OWNER='" + vo.getStringValue("AC_OWNER") + "',";
		}
		// /////////////////////AC_TYPE_OLD///////
		if (vo.getStringValue("AC_TYPE_OLD") == null && voapms.getStringValue("AC_TYPE_OLD") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("AC_TYPE_OLD") != null && voapms.getStringValue("AC_TYPE_OLD") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_TYPE_OLD='" + vo.getStringValue("AC_TYPE_OLD") + "',";
		} else if (vo.getStringValue("AC_TYPE_OLD") == null && voapms.getStringValue("AC_TYPE_OLD") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_TYPE_OLD=" + vo.getStringValue("AC_TYPE_OLD") + ",";
		} else if (!vo.getStringValue("AC_TYPE_OLD").equals(voapms.getStringValue("AC_TYPE_OLD"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "AC_TYPE_OLD='" + vo.getStringValue("AC_TYPE_OLD") + "',";
		}
		// /////////////////////AC_TYPE_MARKET///////
		if (vo.getStringValue("AC_TYPE_MARKET") == null && voapms.getStringValue("AC_TYPE_MARKET") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("AC_TYPE_MARKET") != null && voapms.getStringValue("AC_TYPE_MARKET") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_TYPE_MARKET='" + vo.getStringValue("AC_TYPE_MARKET") + "',";
		} else if (vo.getStringValue("AC_TYPE_MARKET") == null && voapms.getStringValue("AC_TYPE_MARKET") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_TYPE_MARKET=" + vo.getStringValue("AC_TYPE_MARKET") + ",";
		} else if (!vo.getStringValue("AC_TYPE_MARKET").equals(voapms.getStringValue("AC_TYPE_MARKET"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "AC_TYPE_MARKET='" + vo.getStringValue("AC_TYPE_MARKET") + "',";
		}
		// /////////////////////AC_STOP_ARR///////
		if (vo.getStringValue("AC_STOP_ARR") == null && voapms.getStringValue("AC_STOP_ARR") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("AC_STOP_ARR") != null && voapms.getStringValue("AC_STOP_ARR") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_STOP_ARR='" + vo.getStringValue("AC_STOP_ARR") + "',";
		} else if (vo.getStringValue("AC_STOP_ARR") == null && voapms.getStringValue("AC_STOP_ARR") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_STOP_ARR=" + vo.getStringValue("AC_STOP_ARR") + ",";
		} else if (!vo.getStringValue("AC_STOP_ARR").equals(voapms.getStringValue("AC_STOP_ARR"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "AC_STOP_ARR='" + vo.getStringValue("AC_STOP_ARR") + "',";
		}
		// /////////////////////OFF_DELAY_STANDARD///////
		if (vo.getStringValue("OFF_DELAY_STANDARD") == null && voapms.getStringValue("OFF_DELAY_STANDARD") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("OFF_DELAY_STANDARD") != null && voapms.getStringValue("OFF_DELAY_STANDARD") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OFF_DELAY_STANDARD='" + vo.getStringValue("OFF_DELAY_STANDARD") + "',";
		} else if (vo.getStringValue("OFF_DELAY_STANDARD") == null && voapms.getStringValue("OFF_DELAY_STANDARD") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "OFF_DELAY_STANDARD=" + vo.getStringValue("OFF_DELAY_STANDARD") + ",";
		} else if (!vo.getStringValue("OFF_DELAY_STANDARD").equals(voapms.getStringValue("OFF_DELAY_STANDARD"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "OFF_DELAY_STANDARD='" + vo.getStringValue("OFF_DELAY_STANDARD") + "',";
		}

		// /////////////////////ON_DELAY_STANDARD///////
		if (vo.getStringValue("ON_DELAY_STANDARD") == null && voapms.getStringValue("ON_DELAY_STANDARD") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("ON_DELAY_STANDARD") != null && voapms.getStringValue("ON_DELAY_STANDARD") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ON_DELAY_STANDARD='" + vo.getStringValue("ON_DELAY_STANDARD") + "',";
		} else if (vo.getStringValue("ON_DELAY_STANDARD") == null && voapms.getStringValue("ON_DELAY_STANDARD") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "ON_DELAY_STANDARD=" + vo.getStringValue("ON_DELAY_STANDARD") + ",";
		} else if (!vo.getStringValue("ON_DELAY_STANDARD").equals(voapms.getStringValue("ON_DELAY_STANDARD"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "ON_DELAY_STANDARD='" + vo.getStringValue("ON_DELAY_STANDARD") + "',";
		}
		// /////////////////////FIRST_FLAG///////
		if (vo.getStringValue("FIRST_FLAG") == null && voapms.getStringValue("FIRST_FLAG") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("FIRST_FLAG") != null && voapms.getStringValue("FIRST_FLAG") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "FIRST_FLAG='" + vo.getStringValue("FIRST_FLAG") + "',";
		} else if (vo.getStringValue("FIRST_FLAG") == null && voapms.getStringValue("FIRST_FLAG") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "FIRST_FLAG=" + vo.getStringValue("FIRST_FLAG") + ",";
		} else if (!vo.getStringValue("FIRST_FLAG").equals(voapms.getStringValue("FIRST_FLAG"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "FIRST_FLAG='" + vo.getStringValue("FIRST_FLAG") + "',";
		}
		// /////////////////////CANCEL_TYPE///////
		if (vo.getStringValue("CANCEL_TYPE") == null && voapms.getStringValue("CANCEL_TYPE") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("CANCEL_TYPE") != null && voapms.getStringValue("CANCEL_TYPE") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CANCEL_TYPE='" + vo.getStringValue("CANCEL_TYPE") + "',";
		} else if (vo.getStringValue("CANCEL_TYPE") == null && voapms.getStringValue("CANCEL_TYPE") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CANCEL_TYPE=" + vo.getStringValue("CANCEL_TYPE") + ",";
		} else if (!vo.getStringValue("CANCEL_TYPE").equals(voapms.getStringValue("CANCEL_TYPE"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "CANCEL_TYPE='" + vo.getStringValue("CANCEL_TYPE") + "',";
		}
		// /////////////////////CANCEL_REASON///////
		if (vo.getStringValue("CANCEL_REASON") == null && voapms.getStringValue("CANCEL_REASON") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("CANCEL_REASON") != null && voapms.getStringValue("CANCEL_REASON") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CANCEL_REASON='" + vo.getStringValue("CANCEL_REASON") + "',";
		} else if (vo.getStringValue("CANCEL_REASON") == null && voapms.getStringValue("CANCEL_REASON") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CANCEL_REASON=" + vo.getStringValue("CANCEL_REASON") + ",";
		} else if (!vo.getStringValue("CANCEL_REASON").equals(voapms.getStringValue("CANCEL_REASON"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "CANCEL_REASON='" + vo.getStringValue("CANCEL_REASON") + "',";
		}

		// /////////////////////CANCEL_TIME///////
		if (vo.getStringValue("CANCEL_TIME") == null && voapms.getStringValue("CANCEL_TIME") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("CANCEL_TIME") != null && voapms.getStringValue("CANCEL_TIME") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CANCEL_TIME=to_date(substr('" + vo.getStringValue("CANCEL_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (vo.getStringValue("CANCEL_TIME") == null && voapms.getStringValue("CANCEL_TIME") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CANCEL_TIME=to_date(substr('" + vo.getStringValue("CANCEL_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		} else if (!vo.getStringValue("CANCEL_TIME").equals(voapms.getStringValue("CANCEL_TIME"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "CANCEL_TIME=to_date(substr('" + vo.getStringValue("CANCEL_TIME").replaceFirst("1900-01-01", getNowDate()) + "',0,19),'yyyy-mm-dd hh24:mi:ss'),";
		}
		// /////////////////////CREATE_SRC///////
		if (vo.getStringValue("CREATE_SRC") == null && voapms.getStringValue("CREATE_SRC") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("CREATE_SRC") != null && voapms.getStringValue("CREATE_SRC") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CREATE_SRC='" + vo.getStringValue("CREATE_SRC") + "',";
		} else if (vo.getStringValue("CREATE_SRC") == null && voapms.getStringValue("CREATE_SRC") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "CREATE_SRC=" + vo.getStringValue("CREATE_SRC") + ",";
		} else if (!vo.getStringValue("CREATE_SRC").equals(voapms.getStringValue("CREATE_SRC"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "CREATE_SRC='" + vo.getStringValue("CREATE_SRC") + "',";
		}
		// /////////////////////AC_STOP_SRC///////
		if (vo.getStringValue("AC_STOP_SRC") == null && voapms.getStringValue("AC_STOP_SRC") == null) {
			// 都为空则没有变
		} else if (vo.getStringValue("AC_STOP_SRC") != null && voapms.getStringValue("AC_STOP_SRC") == null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_STOP_SRC='" + vo.getStringValue("AC_STOP_SRC") + "',";
		} else if (vo.getStringValue("AC_STOP_SRC") == null && voapms.getStringValue("AC_STOP_SRC") != null) {
			// 一个不不为空，一个 为空则有变
			upstr = upstr + "AC_STOP_SRC=" + vo.getStringValue("AC_STOP_SRC") + ",";
		} else if (!vo.getStringValue("AC_STOP_SRC").equals(voapms.getStringValue("AC_STOP_SRC"))) {// 如果航班号不相同
			// 两个不为空，且不相同则有变
			upstr = upstr + "AC_STOP_SRC='" + vo.getStringValue("AC_STOP_SRC") + "',";
		}

		if (upstr.substring(upstr.length() - 1, upstr.length()).equals(",")) {// 去掉最后一个逗号
			upstr = upstr.substring(0, upstr.length() - 1);
		}
		return upstr;

	}

	private String getNowDate() {
		String temp_str = "";
		Date dt = new Date();

		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;

	}

	/** 计算航班计划中的空中时间、轮档时间等 */
	public String computeAircraftflyplan() throws Exception {
		try {
			long addtime = 0;
			long addcycle = 0;
			long addblock = 0;
			long basicair = 0;
			long basicblock = 0;
			long basiccycle = 0;
			String sql = "";
			ArrayList<String> updateSqlList = new ArrayList<String>();
			sql = "select ac_id,min(flt_date) mindate from i_flt_sch_list where ac_id is not null and cancel_flag<>1 and flt_date>=to_date(substr('" + getNowDate()
					+ "',1,10),'yyyy-MM-dd')-3 and  flt_date<=to_date(substr('" + getNowDate() + "',1,10),'yyyy-MM-dd')+1 group by ac_id order by ac_id";
			HashVO[] Voacinfo = apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
			for (int i = 0; i < Voacinfo.length; i++) {
				HashVO Voacinfo1 = Voacinfo[i];

				sql = "select  max(airsum) VDFCAIR ,max(grdsum) FIACTLG_SUM,max(ldsum) VDFCBLOCK from (select * from i_flt_sch_list t where  t.ac_id='" + Voacinfo1.getStringValue("ac_id")
						+ "' and flt_date<=to_date(substr('" + getNowDate() + "',1,10),'yyyy-MM-dd')-3  order by flt_date desc,std desc) where rownum<2";
				HashVO[] Voacbasic = apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				if (Voacbasic.length > 0) {
					HashVO Voacbasic1 = Voacbasic[0];
					if (Voacbasic1.getLongValue("VDFCAIR") == null) {
						basicair = 0;
						basicblock = 0;
						basiccycle = 0;
					} else {
						basicair = Voacbasic1.getLongValue("VDFCAIR");
						basicblock = Voacbasic1.getLongValue("VDFCBLOCK");
						basiccycle = Voacbasic1.getLongValue("FIACTLG_SUM");
					}
				} else {
					basicair = 0;
					basicblock = 0;
					basiccycle = 0;
				}

				updateSqlList.clear();
				sql = "select * from i_flt_sch_list where ac_id='" + Voacinfo1.getStringValue("ac_id") + "' and flt_date>=to_date(substr('" + Voacinfo1.getStringValue("mindate")
						+ "',0,10),'yyyy-MM-dd')-2 and cancel_flag<>1 order by ac_id,flt_date,std ";
				HashVO[] voacflylogdata = apms.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
				int fltair = 0;
				for (int j = 0; j < voacflylogdata.length; j++) {

					HashVO voacflylogdata1 = voacflylogdata[j];
					fltair = Math.abs(flt_airTime(voacflylogdata1, j));
					addtime = fltair;
					addblock = fltair + 10;
					basicair += fltair;// voacflylogdata1.getLongValue("fltair");
					basicblock += fltair + 10;// voacflylogdata1.getLongValue("flt_ld");
					if (voacflylogdata1.getStringValue("dep_apt").equals(voacflylogdata1.getStringValue("arr_apt"))) {
						basiccycle += 30;
						addcycle = 30;
					} else {
						basiccycle += 1;// voacflylogdata1.getLongValue("fltgrd");
						addcycle = 1;
					}
					sql = "update i_flt_sch_list set fltair=" + addtime + ",flt_ld=" + addblock + ",fltgrd=" + addcycle + ",airsum=" + basicair + ",grdsum=" + basiccycle + ",ldsum=" + basicblock
							+ " where fltrecid=" + voacflylogdata1.getLongValue("fltrecid");
					updateSqlList.add(sql);
				}
				apms.executeBatchByDS(ApmsConst.DS_APMS, updateSqlList);
				apms.commit(ApmsConst.DS_APMS);

				// sql="update b_apms_outlink t set update_time=(select max(vdfcdate) from l_ac_flightlog_daily where acnum=t.model and computedstatus=1)";
				// dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql);
				// /dmo.commit(ApmsConst.DS_APMS);

			}
			return "";
		} catch (Exception e) {
			throw e;
		} finally {
			apms.releaseContext(ApmsConst.DS_APMS);
		}
	}

}
