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
import com.apms.bs.datacompute.vo.A40ComputeVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.bs.util.MathUtil;
import com.apms.vo.ApmsVarConst;

public class A40DataComputeService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());// 日志
	
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

		int num = 0;
		CommDMO dmo = new CommDMO();
		try {
			HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, testSql);

			for (int i = 0; i < vos.length; i++) {
				HashVO vo = vos[i];
				String msgno = vo.getStringValue("MSG_NO");
				logger.debug("开始计算[" + msgno + "]报文数据");
				try {
					A40ComputeVo computedVo = computeA40ByHistoryVos(vo);
					AlarmMonitorTargetVo targetVo = DataComputeUtil.getAlarmTargetVo(vo);
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
			logger.error("A40报文计算时异常:" + e.getMessage());
			throw e;
		} finally {
			dmo.releaseContext(ApmsConst.DS_APMS);
		}

		logger.debug("本次报文计算完成！共计算[" + num + "]条");

		return num;
	}
	
	/**
	 * 根据当前和历史N条记录，计算报文数据
	 * @param curVo 要计算的报文数据
	 * @throws Exception
	 */
	public A40ComputeVo computeA40ByHistoryVos(HashVO curVo) throws Exception{
		A40ComputeVo computedVo = new A40ComputeVo();
		
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		Date date_utc = curVo.getDateValue("DATE_UTC");

		double ff1_avg = curVo.getDoubleValue("FF1_AVG");
		double ff2_avg = curVo.getDoubleValue("FF2_AVG");
		double pd1_avg = curVo.getDoubleValue("PD1_AVG");
		double pd2_avg = curVo.getDoubleValue("PD2_AVG");
		double n21_avg = curVo.getDoubleValue("N21_AVG");
		double n22_avg = curVo.getDoubleValue("N22_AVG");
		
				
		String rptno = ApmsVarConst.RPTNO_A40;
		
		computedVo.setId(curVo.getStringValue("ID"));
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setDate_utc(date_utc);
		computedVo.setFf1_avg(ff1_avg);
		computedVo.setFf2_avg(ff2_avg);
	 	computedVo.setN21_avg(n21_avg);
	 	computedVo.setN22_avg(n22_avg);
	 	computedVo.setPd1_avg(pd1_avg);
	 	computedVo.setPd2_avg(pd2_avg);
	 	
	 	double sub_ff1_ff2=MathUtil.subtract(ff1_avg, ff2_avg);
	 	double sub_pd1_pd2=MathUtil.subtract(pd1_avg, pd2_avg);
	 	double sub_n21_n22=MathUtil.subtract(n21_avg, n22_avg);
		
	 	computedVo.setSub_ff1_ff2(sub_ff1_ff2);
	 	computedVo.setSub_pd1_pd2(sub_pd1_pd2);
	 	computedVo.setSub_n21_n22(sub_n21_n22);
	 	
		String changeMsgno = "1";
		boolean isChangePoint = false;
		boolean isTTest = false;
		boolean isRankTTest = false;
		//获取该报文的前一报文信息		
		DfdFieldCompute fieldComp = new DfdFieldCompute();
		//设置20*2 = 40个回归点
		fieldComp.setPointsN(20);
		//对油量做方差计算、样本T检验
		
		String x_fieldname = "RPTDATE_HOURS";
		long s = computedVo.getDate_utc().getTime();
		Double x_value = (double) s/3600000;
		
		
		
		String f_name = "FF1_AVG";
		double f_value = ff1_avg;
		FieldComputeVo varFf1_avgVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varFf1_avgVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varFf1_avgVo.insertToTable();
		
		f_name = "SUB_FF1_FF2";
		f_value = sub_ff1_ff2;
		FieldComputeVo varSUB_FF1_FF2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varSUB_FF1_FF2Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varSUB_FF1_FF2Vo.insertToTable();
		
		f_name = "SUB_PD1_PD2";
		f_value = sub_pd1_pd2;
		FieldComputeVo varSUB_PD1_PD2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varSUB_PD1_PD2Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varSUB_PD1_PD2Vo.insertToTable();
		
		f_name = "SUB_N21_N22";
		f_value = sub_n21_n22;
		FieldComputeVo varSUB_N21_N22Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varSUB_N21_N22Vo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varSUB_N21_N22Vo.insertToTable();

		f_name = "FF2_AVG";
		f_value = ff2_avg;
		FieldComputeVo varFf2_avgVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varFf2_avgVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varFf2_avgVo.insertToTable();
		
		f_name = "N21_AVG";
		f_value = n21_avg;
		FieldComputeVo varN21_avgVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varN21_avgVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varN21_avgVo.insertToTable();
		
		f_name = "N22_AVG";
		f_value = n22_avg;
		FieldComputeVo varN22_avgVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varN22_avgVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varN22_avgVo.insertToTable();
		
		f_name = "PD1_AVG";
		f_value = pd1_avg;
		FieldComputeVo varPd1_avgVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPd1_avgVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPd1_avgVo.insertToTable();
		
		f_name = "PD2_AVG";
		f_value = pd2_avg;
		FieldComputeVo varPd2_avgVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPd2_avgVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPd2_avgVo.insertToTable();
		
		//以上是对均值和均值的差值进行计算，然后进行均值和均值差值的标准方差进行计算
		f_name = "FF1_AVG_STD";
		f_value=varFf1_avgVo.getV_std();
		FieldComputeVo varFF1_AVG_STDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varFF1_AVG_STDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varFF1_AVG_STDVo.insertToTable();
		
		f_name = "FF2_AVG_STD";
		f_value=varFf2_avgVo.getV_std();
		FieldComputeVo varFF2_AVG_STDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varFF2_AVG_STDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varFF2_AVG_STDVo.insertToTable();
		
		f_name = "PD1_AVG_STD";
		f_value=varPd1_avgVo.getV_std();
		FieldComputeVo varPD1_AVG_STDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPD1_AVG_STDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPD1_AVG_STDVo.insertToTable();
		
		f_name = "PD2_AVG_STD";
		f_value=varPd2_avgVo.getV_std();
		FieldComputeVo varPD2_AVG_STDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varPD2_AVG_STDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varPD2_AVG_STDVo.insertToTable();
		
		f_name = "N21_AVG_STD";
		f_value=varN21_avgVo.getV_std();
		FieldComputeVo varN21_AVG_STDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varN21_AVG_STDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varN21_AVG_STDVo.insertToTable();
		
		f_name = "N22_AVG_STD";
		f_value=varN22_avgVo.getV_std();
		FieldComputeVo varN22_AVG_STDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varN22_AVG_STDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varN22_AVG_STDVo.insertToTable();
		
		f_name = "SUB_FF1_FF2_STD";
		f_value=varSUB_FF1_FF2Vo.getV_std();
		FieldComputeVo varSUB_FF1_FF2_STDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varSUB_FF1_FF2_STDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varSUB_FF1_FF2_STDVo.insertToTable();
		
		f_name = "SUB_PD1_PD2_STD";
		f_value=varSUB_PD1_PD2Vo.getV_std();
		FieldComputeVo varSUB_PD1_PD2_STDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varSUB_PD1_PD2_STDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varSUB_PD1_PD2_STDVo.insertToTable();
		
		f_name = "SUB_N21_N22_STD";
		f_value=varSUB_N21_N22Vo.getV_std();
		FieldComputeVo varSUB_N21_N22_STDVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varSUB_N21_N22_STDVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varSUB_N21_N22_STDVo.insertToTable();
		
		computedVo.setFieldVo_ff1_avg(varFf1_avgVo);
		computedVo.setFieldVo_ff2_avg(varFf2_avgVo);
		computedVo.setFieldVo_n21_avg(varN21_avgVo);
		computedVo.setFieldVo_n22_avg(varN22_avgVo);
		computedVo.setFieldVo_pd1_avg(varPd1_avgVo);
		computedVo.setFieldVo_pd2_avg(varPd2_avgVo);
		computedVo.setFieldVo_ff1_ff2_avg(varSUB_FF1_FF2Vo);
		computedVo.setFieldVo_pd1_pd2_avg(varSUB_PD1_PD2Vo);
		computedVo.setFieldVo_n21_n22_avg(varSUB_N21_N22Vo);
		
		computedVo.setFieldVo_ff1_avg_std(varFF1_AVG_STDVo);
		computedVo.setFieldVo_ff2_avg_std(varFF2_AVG_STDVo);
		computedVo.setFieldVo_n21_avg_std(varN21_AVG_STDVo);
		computedVo.setFieldVo_n22_avg_std(varN22_AVG_STDVo);
		computedVo.setFieldVo_pd1_avg_std(varPD1_AVG_STDVo);
		computedVo.setFieldVo_pd2_avg_std(varPD2_AVG_STDVo);
		computedVo.setFieldVo_sub_ff1_ff2_std(varSUB_FF1_FF2_STDVo);
		computedVo.setFieldVo_sub_pd1_pd2_std(varSUB_PD1_PD2_STDVo);
		computedVo.setFieldVo_sub_n21_n22_std(varSUB_N21_N22_STDVo);
		
		computedVo.setFf1_avg_std(varFf1_avgVo.getV_std());
		computedVo.setFf2_avg_std(varFf2_avgVo.getV_std());
		computedVo.setPd1_avg_std(varPd1_avgVo.getV_std());
		computedVo.setPd2_avg_std(varPd2_avgVo.getV_std());
		computedVo.setN21_avg_std(varN21_avgVo.getV_std());
		computedVo.setN22_avg_std(varN22_avgVo.getV_std());
		computedVo.setSub_ff1_ff2_std(varSUB_FF1_FF2Vo.getV_std());
		computedVo.setSub_n21_n22_std(varSUB_N21_N22Vo.getV_std());
		computedVo.setSub_pd1_pd2_std(varSUB_PD1_PD2Vo.getV_std());
		
		computedVo.insertToTable();
		return computedVo;

	}
	
}
