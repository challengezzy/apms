package com.apms.bs.datacompute;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.datacompute.engine.EngModelService;
import com.apms.bs.datacompute.vo.A04CfmComputeVo;
import com.apms.bs.datacompute.vo.ChangePointVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.bs.datacompute.vo.a04.A04EgtCfmVo;
import com.apms.bs.util.MathUtil;
import com.apms.vo.ApmsVarConst;

/**
 * CFM发动机报文相关计算
 * @author rick
 * @date Oct 20, 2015
 */
public class A04CfmDataComputeService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private static HashMap<String, ChangePointVo> changePointMap = new HashMap<String, ChangePointVo>();
	
	private EngineCfmEgtMarginService marginService = new EngineCfmEgtMarginService();
	
	private EngModelService modelService = new EngModelService();
	
	private static A04CfmDataComputeService computesService = null;
	public static A04CfmDataComputeService getInstance(){
		if (computesService == null)
			computesService = new A04CfmDataComputeService();

		return computesService;
	}
	/**
	 * A04发动机 起飞报文计算
	 * @param acnum
	 * @return
	 * @throws Exception
	 */
	public int computeA04Data(String acnum) throws Exception {
		StringBuilder qrySb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,T.DATE_UTC RPTDATE,T.CODE,T.RPTNO");
		qrySb.append(",ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,ESN_2,EHRS_2, ERT_2, ECYC_2,  AP_2, N1_1, N1C_1");
		//获取发动机型号及参数,加减1天是为了在拆换日当天也能取到数据
		qrySb.append(",(SELECT N1MOD FROM V_ENG_INFOHIS V WHERE V.ENGSN = L.ESN_1 AND V.STIME <= L.RPTDATE+1 AND V.ETIME >= L.RPTDATE-1 AND ROWNUM =1) N1MOD_1");
		qrySb.append(",(SELECT ENGMODEL FROM V_ENG_INFOHIS V WHERE V.ENGSN = L.ESN_1 AND V.STIME <= L.RPTDATE+1 AND V.ETIME >= L.RPTDATE-1 AND ROWNUM =1) ENGMODEL_1");
		qrySb.append(",(SELECT N1MOD FROM V_ENG_INFOHIS V WHERE V.ENGSN = L.ESN_2 AND V.STIME <= L.RPTDATE+1 AND V.ETIME >= L.RPTDATE-1 AND ROWNUM =1) N1MOD_2");
		qrySb.append(",(SELECT ENGMODEL FROM V_ENG_INFOHIS V WHERE V.ENGSN = L.ESN_2 AND V.STIME <= L.RPTDATE+1 AND V.ETIME >= L.RPTDATE-1 AND ROWNUM =1) ENGMODEL_2");
		qrySb.append("");
		qrySb.append(",N2_1, EGT_1, FF_1,N1_2, N1C_2,N2_2, EGT_2, FF_2,T25_1, P3_1, T3_1, T5_1,VSV_1");
		qrySb.append(",VBV_1,  T25_2,  P3_2,T3_2, T5_2, VSV_2, VBV_2, HPT_1,LPT_1, PD_1, PT2_1, HPT_2");
		qrySb.append(",LPT_2, PD_2, PT2_2, VN_1,VL_1, PHA_1, PHT_1,VC_1, VH_1, EVM_1,VN_2, VL_2, PHA_2");
		qrySb.append(",PHT_2, VC_2, VH_2, OIP_1, OIT_1,ECW1_1,OIP_2,OIT_2, ECW2_2, T.TAT, T.BLEED_STATUS");
		qrySb.append(" FROM A_DFD_HEAD T,A_DFD_A04CFM56_5B_LIST L ");
		qrySb.append(" WHERE L.MSG_NO=T.MSG_NO AND T.RPTNO='A04' AND T.STATUS=0 ");
		if (acnum != null) {
			qrySb.append(" AND T.ACNUM = '" + acnum + "'");
		}
		// 测试条件
//		qrySb.append(" and T.MSG_NO = 260801014 ");
		qrySb.append(" ORDER BY L.MSG_NO ASC");

		String testSql = "select * from (" + qrySb.toString() + ") where rownum< 4000";

		int num = 0;//计算的报文数量
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);
			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");
				logger.debug("开始计算[" + msgno + "]报文数据");
				try {
					String asn = vo.getStringValue("ACNUM");
					String engModel_1 = vo.getStringValue("ENGMODEL_1");
					String engModel_2 = vo.getStringValue("ENGMODEL_2");
//					if(engModel_1 == null){
//						engModel_1 = modelService.getEngModelByAcnum(asn);
//					}
//					if(engModel_2 == null){
//						engModel_2 = modelService.getEngModelByAcnum(asn);
//					}
					//从飞行上依然没有找到型号数据，则跳过不处理
					if(engModel_1 == null || engModel_2 == null){
						DataComputeUtil.updateDfdHeadNottime(msgno, "未找到左发或右发对应的发动机型号，不计算");
						dmo.commit(ApmsConst.DS_APMS);
						continue;
					}
					
					A04CfmComputeVo computedVo = computeA04CfmByHistoryVos(vo);
					//告警判断和执行 begin
					AlarmMonitorTargetVo targetVo = DataComputeUtil.getAlarmTargetVo(vo);
					HashMap<String, Object> targetMap = targetVo.getParamMap();
					
					//TODO 红线值告警
					
					//TODO 飘点告警
					
					// 此条报文计算完成后，进行告警触发
					AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A04_CFM_COMPUTE, msgno, targetVo);
					//告警判断和执行 end
					
					// 更新数据状态为已计算
					DataComputeUtil.updateDfdHeadDealed(msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num++;
				} catch (Exception e) {
					dmo.rollback(ApmsConst.DS_APMS);
					e.printStackTrace();
					logger.error("msgno=" + msgno + ", 报文计算异常:" + e.getMessage());
					DataComputeUtil.updateDfdHeadError(msgno, e.getMessage());
					dmo.commit(ApmsConst.DS_APMS);
				}
			}
		} catch (Exception e) {
			logger.error("A04Cfm报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}
	
	public String getChangeVoKey(String acnum,String esn,int position){
		String key = acnum + "_" + esn + "_" + position;
		return key;
	}
	
	public ChangePointVo getLastChangePointVo(String acnum,String esn,int position) throws Exception{
		String key = getChangeVoKey(acnum, esn, position);
		ChangePointVo changeVo = changePointMap.get(key);
		
		//没查到分两种情况1，刚启动未进缓存 2，新的飞机发动机数据
		if(changeVo == null){
			//这里进行初始化
			String qrysql;
			if(position == 1){
				qrysql ="SELECT MSG_NO,ACNUM,ESN_1 DSN FROM A_DFD_A04CFM56_COMPUTE C WHERE C.MSG_NO = "
					+ "(SELECT MAX(MSG_NO) FROM A_DFD_A04CFM56_COMPUTE C1 WHERE C1.ACNUM=? AND C1.ISCHANGEPOINT1=1 )";
			}else{
				qrysql ="SELECT MSG_NO,ACNUM,ESN_2 DSN FROM A_DFD_A04CFM56_COMPUTE C WHERE C.MSG_NO = "
					+ "(SELECT MAX(MSG_NO) FROM A_DFD_A04CFM56_COMPUTE C1 WHERE C1.ACNUM=? AND C1.ISCHANGEPOINT2=1 )";
			}
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrysql, acnum);
			if(vos.length>0){
				changeVo = new ChangePointVo();
				changeVo.setAcnum(acnum);
				changeVo.setDsn(vos[0].getStringValue("DSN"));
				changeVo.setMsgno(vos[0].getStringValue("MSG_NO"));
				changeVo.setPosition(position);
				
				changePointMap.put(key, changeVo);
			}else{
				//不处理，返回空的changevo
			}
		}
		
		return changeVo;
	}
	
	public A04CfmComputeVo computeA04CfmByHistoryVos(HashVO curVo) throws Exception{
		A04CfmComputeVo computedVo = new A04CfmComputeVo();
		String rptno = ApmsVarConst.RPTNO_A04;	
		DfdFieldCompute fieldComp = new DfdFieldCompute();
		
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		Date date_utc = curVo.getDateValue("RPTDATE");
		String esn_1 = curVo.getStringValue("ESN_1");
		String esn_2 = curVo.getStringValue("ESN_2");
		
		String changeMsgno1 = "1";
		String changeMsgno2 = "1";
		boolean isChangePoint1 = false;
		boolean isChangePoint2 = false;
		
		//changeMsgno应该有两个，左发和右发的拆换点肯定不一致
		ChangePointVo changevo1 = getLastChangePointVo(acnum,esn_1, 1);
		ChangePointVo changevo2 = getLastChangePointVo(acnum,esn_2, 2);
		
		//判断前一条报文数据和本条报文数据发动机序号是否一致，如果一致，changepoint在前面。 如果不一致，本条数据就是changepoint
		if(changevo1 != null && esn_1.equals(changevo1.getDsn()) ){ //ESN一致
			isChangePoint1 = false;
			changeMsgno1 = changevo1.getMsgno();
		}else{
			//缓存未查到，或者esn不一致,是拆换点
			isChangePoint1 = true;
			changeMsgno1 = msgno;
			changevo1 = new ChangePointVo(acnum,msgno,esn_1,1);
			String key = getChangeVoKey(acnum, esn_1, 1);
			changePointMap.put(key , changevo1);
		}
		
		if(changevo2 != null && esn_2.equals(changevo2.getDsn()) ){ //ESN一致
			isChangePoint2 = false;
			changeMsgno2 = changevo2.getMsgno();
		}else{
			//缓存未查到，或者esn不一致,是拆换点
			isChangePoint2 = true;
			changeMsgno2 = msgno;
			changevo2 = new ChangePointVo(acnum,msgno,esn_2,2);
			String key = getChangeVoKey(acnum, esn_2, 2);
			changePointMap.put(key , changevo2);
		}
		
		//计算左右差值的changePoint开始点,取最新的一个点
		computedVo.setIsChangePoint1(isChangePoint1 ?1 :0);
		computedVo.setIsChangePoint2(isChangePoint2 ?1 :0);
		
		computedVo.setEsn_1(esn_1);
		computedVo.setEsn_2(esn_2);
		computedVo.setId(curVo.getStringValue("ID"));
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setDate_utc(date_utc);
		
		String x_fieldname = "RPTDATE_HOURS";
		long s = computedVo.getDate_utc().getTime();		
		Double x_value = (double) s/3600000;
		
		//计算EGT_Margin_1  EGT_Margin_2,DIV_EGT_Margin_1,DIV_EGT_Margin_2;
		A04EgtCfmVo calvo1 = marginService.cfmEgt1MarginCompute(curVo);
		A04EgtCfmVo calvo2 = marginService.cfmEgt2MarginCompute(curVo);
		computedVo.setCalvo1(calvo1);
		computedVo.setCalvo2(calvo2);
		
		double egt_margin_1 = calvo1.getHdegtm();
		double egt_margin_2 = calvo2.getHdegtm();
		computedVo.setEgt_margin_1(egt_margin_1);
		computedVo.setEgt_margin_2(egt_margin_2);
		String f_name = "EGT_MARGIN_1";
		double f_value = computedVo.getEgt_margin_1();
		FieldComputeVo egt_margin_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		egt_margin_1Vo.setTimes(1);
		egt_margin_1Vo.setRank(true);
		fieldComp.fieldRegressionCompute(curVo, egt_margin_1Vo,changeMsgno1,isChangePoint1,false,true,false);//做一元一次回归计算
		egt_margin_1Vo.insertToTable();//插入数据库
		
		f_name = "EGT_MARGIN_2";
		f_value = computedVo.getEgt_margin_2();
		FieldComputeVo egt_margin_2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		egt_margin_2Vo.setTimes(1);
		egt_margin_2Vo.setRank(true);
		fieldComp.fieldRegressionCompute(curVo, egt_margin_2Vo,changeMsgno2,isChangePoint2,false,true,false);
		egt_margin_2Vo.insertToTable();//插入数据库
		
		computedVo.setDiv_egt_margin1(MathUtil.deviation(egt_margin_1, egt_margin_2));
		computedVo.setDiv_egt_margin2(MathUtil.deviation(egt_margin_2, egt_margin_1));
//		f_name = "DIV_EGT_MARGIN";
//		f_value = computedVo.getDiv_egt_margin1();
//		FieldComputeVo div_egt_margin_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_egt_margin_1Vo, chgMsgno,isChgPoint);
//		div_egt_margin_1Vo.insertToTable();//插入数据库
		
		//计算DIV_N2_1   DIV_N2_2
		double n2_1 = curVo.getDoubleValue("N2_1");
		double n2_2 = curVo.getDoubleValue("N2_2");
		computedVo.setDiv_n2_1(MathUtil.deviation(n2_1, n2_2));
		computedVo.setDiv_n2_2(MathUtil.deviation(n2_2, n2_1));
//		f_name = "DIV_N2";
//		f_value = computedVo.getDiv_n2_1();
//		FieldComputeVo div_n2_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_n2_1Vo,chgMsgno,isChgPoint);
//		div_n2_1Vo.insertToTable();//插入数据库
		
		//计算DIV_FF_1  DIV_FF_2
		double ff_1 = curVo.getDoubleValue("FF_1");
		double ff_2 = curVo.getDoubleValue("FF_2");
		computedVo.setDiv_ff_1(MathUtil.deviation(ff_1, ff_2));
		computedVo.setDiv_ff_2(MathUtil.deviation(ff_2, ff_1));
		//计算FF的5点均
		f_name = "FF_1";
		f_value = Double.parseDouble(curVo.getStringValue("FF_1"));
		FieldComputeVo FfVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, FfVo, changeMsgno1, isChangePoint1);
		FfVo.insertToRollNTable();
		
		f_name = "FF_2";
		f_value = Double.parseDouble(curVo.getStringValue("FF_2"));
		FieldComputeVo FfVo1 = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRollNCompute(curVo, FfVo1, changeMsgno2, isChangePoint2);
		FfVo1.insertToRollNTable();
//		f_name = "DIV_FF";
//		f_value = computedVo.getDiv_ff_1();
//		FieldComputeVo div_ff_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_ff_1Vo,chgMsgno,isChgPoint);
//		div_ff_1Vo.insertToTable();//插入数据库
		
		//计算DIV_T3_1  DIV_T3_2
		double t3_1 = curVo.getDoubleValue("T3_1");
		double t3_2 = curVo.getDoubleValue("T3_2");
		computedVo.setDiv_t3_1(MathUtil.deviation(t3_1, t3_2));
		computedVo.setDiv_t3_2(MathUtil.deviation(t3_2, t3_1));
//		f_name = "DIV_T3";
//		f_value = computedVo.getDiv_t3_1();
//		FieldComputeVo div_t3_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_t3_1Vo,chgMsgno,isChgPoint);
//		div_t3_1Vo.insertToTable();//插入数据库
				
		//计算DIV_OIP_1   DIV_OIP_2
		double OIP_1 = curVo.getDoubleValue("OIP_1");
		double OIP_2 = curVo.getDoubleValue("OIP_2");
		computedVo.setDiv_oip_1(MathUtil.deviation(OIP_1, OIP_2));
		computedVo.setDiv_oip_1(MathUtil.deviation(OIP_2, OIP_1));
		//左发滑油压力值做飘点计算
		f_name = "OIP_1";
		f_value = Double.parseDouble(curVo.getStringValue("OIP_1"));
		FieldComputeVo oip1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, oip1Vo,changeMsgno1,isChangePoint1);
		oip1Vo.insertToTable();//插入数据库
		f_name = "OIP_2";
		f_value = Double.parseDouble(curVo.getStringValue("OIP_2"));
		FieldComputeVo oip2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, oip2Vo,changeMsgno2,isChangePoint2);
		oip2Vo.insertToTable();//插入数据库
//		f_name = "DIV_OIP";
//		f_value = computedVo.getDiv_oip_1();
//		FieldComputeVo div_oip1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_oip1Vo, chgMsgno, isChgPoint);
//		div_oip1Vo.insertToTable();//插入数据库
		
		//计算DIV_OIT_1  DIV_OIT_2
		double oit_1 = curVo.getDoubleValue("OIT_1");
		double oit_2 = curVo.getDoubleValue("OIT_2");
		computedVo.setDiv_oit_1(MathUtil.deviation(oit_1, oit_2));
		computedVo.setDiv_oit_2(MathUtil.deviation(oit_2, oit_1));
		f_name = "OIT_1";
		f_value = Double.parseDouble(curVo.getStringValue("OIT_1"));
		FieldComputeVo oit_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, oit_1Vo,changeMsgno1,isChangePoint1);
		oit_1Vo.insertToTable();//插入数据库
		f_name = "OIT_2";
		f_value = Double.parseDouble(curVo.getStringValue("OIT_2"));
		FieldComputeVo oit_2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, oit_2Vo,changeMsgno2,isChangePoint2);
		oit_2Vo.insertToTable();//插入数据库
		
//		f_name = "DIV_OIT";
//		f_value = computedVo.getDiv_oit_1();
//		FieldComputeVo div_oit_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_oit_1Vo,chgMsgno,isChgPoint);
//		div_oit_1Vo.insertToTable();//插入数据库

		//计算DIV_VN_1  DIV_VN_2
		double vn_1 = curVo.getDoubleValue("VN_1");
		double vn_2 = curVo.getDoubleValue("VN_2");
		computedVo.setDiv_vn_1(MathUtil.deviation(vn_1, vn_2));
		computedVo.setDiv_vn_2(MathUtil.deviation(vn_2, vn_1));
//		f_name = "DIV_VN";
//		f_value = computedVo.getDiv_vn_1();
//		FieldComputeVo div_vn_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_vn_1Vo , chgMsgno,isChgPoint);
//		div_vn_1Vo.insertToTable();//插入数据库
		
		//计算DIV_VL_1  DIV_VL_2
		double vl_1 = curVo.getDoubleValue("VL_1");
		double vl_2 = curVo.getDoubleValue("VL_2");
		computedVo.setDiv_vl_1(MathUtil.deviation(vl_1, vl_2));
		computedVo.setDiv_vl_2(MathUtil.deviation(vl_2, vl_1));
//		f_name = "DIV_VL";
//		f_value = computedVo.getDiv_vl_1();
//		FieldComputeVo div_vl_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_vl_1Vo , chgMsgno,isChgPoint);
//		div_vl_1Vo.insertToTable();//插入数据库
		
		//计算DIV_VC_1  DIV_VC_2
		double vc_1 = curVo.getDoubleValue("VC_1");
		double vc_2 = curVo.getDoubleValue("VC_2");
		computedVo.setDiv_vc_1(MathUtil.deviation(vc_1, vc_2));
		computedVo.setDiv_vc_2(MathUtil.deviation(vc_2, vc_1));
//		f_name = "DIV_VC";
//		f_value = computedVo.getDiv_vc_1();
//		FieldComputeVo div_vc_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_vc_1Vo , chgMsgno,isChgPoint);
//		div_vc_1Vo.insertToTable();//插入数据库
		
		//计算DIV_VH_1  DIV_VH_2
		double vh_1 = curVo.getDoubleValue("VH_1");
		double vh_2 = curVo.getDoubleValue("VH_2");
		computedVo.setDiv_vh_1(MathUtil.deviation(vh_1, vh_2));
		computedVo.setDiv_vh_2(MathUtil.deviation(vh_2, vh_1));
//		f_name = "DIV_VH";
//		f_value = computedVo.getDiv_vh_1();
//		FieldComputeVo div_vh_1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
//		fieldComp.fieldRegressionCompute(curVo, div_vh_1Vo , chgMsgno,isChgPoint);
//		div_vh_1Vo.insertToTable();//插入数据库
		
		
		//左发delta回归计算
		f_name = "DELTA_EGTM_1";
		f_value = calvo1.getDelta_egtm();
		FieldComputeVo del_egtm_1vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, del_egtm_1vo,changeMsgno1,isChangePoint1);
		del_egtm_1vo.insertToTable();//插入数据库
		
		f_name = "DELTA_T3_1";
		f_value = calvo1.getDelta_t3();
		FieldComputeVo del_t3_1vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, del_t3_1vo,changeMsgno1,isChangePoint1);
		del_t3_1vo.insertToTable();//插入数据库
		
		f_name = "DELTA_N2_1";
		f_value = calvo1.getDelta_n2();
		FieldComputeVo del_n2_1vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, del_n2_1vo,changeMsgno1,isChangePoint1);
		del_n2_1vo.insertToTable();//插入数据库
		
		f_name = "DELTA_FF_1";
		f_value = calvo1.getDelta_ff();
		FieldComputeVo del_ff_1vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, del_ff_1vo,changeMsgno1,isChangePoint1);
		del_ff_1vo.insertToTable();//插入数据库
		
		//右发 delta回归计算
		f_name = "DELTA_EGTM_2";
		f_value = calvo2.getDelta_egtm();
		FieldComputeVo del_egtm_2vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, del_egtm_2vo,changeMsgno2,isChangePoint2);
		del_egtm_2vo.insertToTable();//插入数据库
		
		f_name = "DELTA_T3_2";
		f_value = calvo2.getDelta_t3();
		FieldComputeVo del_t3_2vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, del_t3_2vo,changeMsgno2,isChangePoint2);
		del_t3_2vo.insertToTable();//插入数据库
		
		f_name = "DELTA_N2_2";
		f_value = calvo2.getDelta_n2();
		FieldComputeVo del_n2_2vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, del_n2_2vo,changeMsgno2,isChangePoint2);
		del_n2_2vo.insertToTable();//插入数据库
		
		f_name = "DELTA_FF_2";
		f_value = calvo2.getDelta_ff();
		FieldComputeVo del_ff_2vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, del_ff_2vo,changeMsgno2,isChangePoint2);
		del_ff_2vo.insertToTable();//插入数据库
		
		computedVo.insertToTable();
		
		return computedVo;
	}

}
