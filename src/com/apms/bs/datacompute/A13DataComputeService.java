package com.apms.bs.datacompute;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.datacompute.vo.A13ComputeVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.bs.sysconfig.ApmsSysParamConfService;
import com.apms.bs.sysconfig.ApuConfVarVo;
import com.apms.vo.SysParamConfVo;

public class A13DataComputeService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private double oid_change_temp = 25;//判断APU滑油更换的滑油温度差值
	private SysParamConfVo sysVar;
	private ApuConfVarVo apuVar;
	
	public A13DataComputeService() throws Exception{
		sysVar = ApmsSysParamConfService.getInstance().getConfVo();
		
		apuVar = sysVar.getApuVo();
		oid_change_temp = apuVar.getOid_change_temp();
		
	}
	
	
	/**
	 * 根据当前和历史N条记录，计算报文数据
	 * @param curVo 要计算的报文数据
	 * @throws Exception
	 */
	public A13ComputeVo compute13ByHistoryVos(HashVO curVo) throws Exception{
		A13ComputeVo computedVo = new A13ComputeVo();
		
		String id = curVo.getStringValue("ID");
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		String rptno = curVo.getStringValue("RPTNO");
		Date date_utc = curVo.getDateValue("DATE_UTC");
		String asn = curVo.getStringValue("ASN");
		String code = curVo.getStringValue("CODE");
		computedVo.setId(id);
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setDate_utc(date_utc);
		computedVo.setCode(code);
		computedVo.setAsn(asn);
		
		boolean isChangePoint = false;		
		
		//1判断是否是新的计算开始点(APU拆换点)，并找出Change点的msgno
//		StringBuilder sb = new StringBuilder("SELECT L.ID,L.MSG_NO FROM A_DFD_A13_COMPUTE L,B_APU A ");
//		sb.append(" where a.apusn=l.asn AND l.utcdate > a.install_time and L.ISCHANGEPOINT = 1 ");
//		sb.append(" and l.acnum=? AND L.ASN=? ");
//		sb.append(" and l.msg_no < ? "); //根据APU的装上时间判断太依赖于APU的数据，这个数据有可能被用户人工修改，不靠谱
		
		String sql= "SELECT MSG_NO,ACNUM,ASN FROM A_DFD_A13_COMPUTE C WHERE C.MSG_NO =(SELECT MAX(MSG_NO) FROM A_DFD_A13_COMPUTE C1 WHERE C1.ACNUM=?)";
		
		String changeMsgno = "1000";//APU拆换后，第一条报文号
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql,acnum);
		//判断前一条报文数据和本条报文数据ASN是否一致，如果一致，changepoint在前面。 如果不一致，本条数据就是changepoint
		if(vos.length> 0){
			String preAsn = vos[0].getStringValue("ASN");
			if(asn.equalsIgnoreCase(preAsn)){
				isChangePoint = false;
				
				//查找changepoint点的msg_no
				String sql2 = "SELECT MSG_NO,ACNUM,ASN FROM A_DFD_A13_COMPUTE C WHERE C.MSG_NO = (SELECT MAX(MSG_NO) FROM A_DFD_A13_COMPUTE C1 WHERE C1.ACNUM=? AND C1.ISCHANGEPOINT=1)";
				HashVO[] chgVos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql2,acnum);
				if(chgVos.length > 0){
					changeMsgno = chgVos[0].getStringValue("MSG_NO");
				}else{
					logger.error("此报文msgno=["+msgno+"]前应该有ChangePoint，但是未查到，数据可能有问题！！");
				}
			}else{
				isChangePoint = true;
				changeMsgno = msgno;
			}
			
		}else{
			isChangePoint = true;
			changeMsgno = msgno;
		}
		computedVo.setIsChangePoint(isChangePoint?1:0);
		
		//查红线值，及计算基础值
		getRedLineValue(computedVo);
		
		//2.计算本报文基础数据
		computeBasic(curVo, computedVo);
		
		//计算APU参数基准值
		computeApuBaseValue(computedVo, isChangePoint);
		
		//计算部分参数5点均
		compute5AvgValue(computedVo, isChangePoint, changeMsgno);
		
		//3, 回归数据计算
		String x_fname = "AHRS";//对运行时间进行回归
		double x_fvalue = curVo.getDoubleValue(x_fname);
		DfdFieldCompute fieldComp = new DfdFieldCompute();
		
		//4 添加滑油后，启动时滑油温度会明显下降，应指出滑油OT的温度下降的15度
		String changeOilMsgno = changeMsgno; //滑油更新点
		boolean isChangeOil = false;
		
		if(isChangePoint == false){ //如果APU更换了，所有数据从换APU开始计算
			HashVO preVo = getPreA13ComputeVo(computedVo);
			if(preVo != null){
				double preOtMax = preVo.getDoubleValue("OT_MAX");
				if(computedVo.getOt_max() < preOtMax - oid_change_temp ){ //滑油OT的温度下降的25度,表示换滑油点
					changeOilMsgno = preVo.getStringValue("MSG_NO");
					isChangeOil = true;
				}
			}
		}
		//如果本报文不是更新滑油点，查出更换滑油点的MSGNO
		if(!isChangeOil && !isChangePoint){
			String qryChgOil = "SELECT MSG_NO,UTCDATE FROM A_DFD_A13_COMPUTE C WHERE C.MSG_NO > ? AND C.ISCHANGEOIL=1 ORDER BY MSG_NO DESC";
			HashVO[] tvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qryChgOil, changeMsgno);
			if(tvos.length>0){
				changeOilMsgno = tvos[0].getStringValue("MSG_NO");
			}
		}
		computedVo.setIsChangeOil(isChangeOil?1:0);
		
		//STA回归计算
		String f_name = "STA_V1";//APU自启动时间
		double f_value = curVo.getDoubleValue(f_name);
		FieldComputeVo fieldVo_STA = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_STA.setTimes(3);
		fieldVo_STA.setEstimateLimit(true);
		fieldVo_STA.setLimitY(computedVo.getRl_sta());
		fieldVo_STA.setRank(true);
	 	fieldComp.fieldRegressionCompute(curVo, fieldVo_STA, changeMsgno,isChangePoint,true,true,true);
	 	computedVo.setSta_v1_est_time(fieldVo_STA.getDeta_x_on_estlimitvalue());
	 	
		f_name = "IGV_MAX";
		f_value = computedVo.getIgv_max();
		FieldComputeVo fieldVo_igv = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_igv.setTimes(3);
		fieldVo_igv.setRank(false);
		fieldComp.fieldRegressionCompute(curVo, fieldVo_igv, changeMsgno,isChangePoint,true,false,false);
		
		f_name = "EGTA_MAX_COR";
		f_value = computedVo.getEgta_max_cor();
		FieldComputeVo fieldVo_egta = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_egta.setTimes(3);
		fieldVo_egta.setEstimateLimit(true);
		fieldVo_egta.setLimitY(computedVo.getRl_egt_cor());
		fieldVo_egta.setRank(true);
		fieldComp.fieldRegressionCompute(curVo, fieldVo_egta, changeMsgno,isChangePoint,true,true,true);
		computedVo.setEgta_max_cor_est_time(fieldVo_egta.getDeta_x_on_estlimitvalue());
		
		f_name = "P2A_MAX_COR";
		f_value = computedVo.getP2a_max_cor();
		FieldComputeVo fieldVo_p2a = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_p2a.setTimes(3);
		fieldVo_p2a.setRank(false);
		fieldComp.fieldRegressionCompute(curVo, fieldVo_p2a, changeMsgno,isChangePoint,true,false,false);
		
		f_name = "PT_MAX_COR";
		f_value = computedVo.getPt_max_cor();
		FieldComputeVo fieldVo_pt = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_pt.setTimes(3);
		fieldVo_pt.setEstimateLimit(true);
		fieldVo_pt.setLimitY(computedVo.getRl_pt_cor());
		fieldVo_pt.setRank(true);
		fieldComp.fieldRegressionCompute(curVo, fieldVo_pt, changeMsgno,isChangePoint,true,true,true);
		computedVo.setPt_max_cor_est_time(fieldVo_pt.getDeta_x_on_estlimitvalue());
		
		f_name = "WB_MAX_COR";
		f_value = computedVo.getWb_max_cor();
		FieldComputeVo fieldVo_wb = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_wb.setTimes(3);
		fieldVo_wb.setEstimateLimit(true);
		fieldVo_wb.setLimitY(computedVo.getRl_wb_cor());
		fieldVo_wb.setRank(true);
		fieldComp.fieldRegressionCompute(curVo, fieldVo_wb, changeMsgno,isChangePoint,true,true,true);
		
		
		f_name = "APR_MAX";
		f_value = computedVo.getApr_max();
		FieldComputeVo fieldVo_apr = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_apr.setTimes(3);
		fieldVo_apr.setEstimateLimit(true);
		fieldVo_apr.setLimitY(computedVo.getRl_apr_cor());
		fieldVo_apr.setRank(true);
		fieldComp.fieldRegressionCompute(curVo, fieldVo_apr, changeMsgno,isChangePoint,true,true,true);
		computedVo.setApr_max_est_time(fieldVo_apr.getDeta_x_on_estlimitvalue());
		
		f_name = "OT_MAX";
		f_value = computedVo.getOt_max();
		FieldComputeVo fieldVo_ot = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_ot.setTimes(1);
		fieldVo_ot.setEstimateLimit(true);
		fieldVo_ot.setRank(true);
		fieldVo_ot.setLimitY(computedVo.getRl_ota()-computedVo.getLcit_max());//RL_OTA -LCIT_MAX
		fieldComp.fieldRegressionCompute(curVo, fieldVo_ot, changeOilMsgno,isChangePoint,true,true,true);
		computedVo.setOt_max_est_time(fieldVo_ot.getDeta_x_on_estlimitvalue());
		
		
	 	//数据入库
	 	computedVo.insertToTable();
	 	fieldVo_STA.insertToTable();
	 	fieldVo_igv.insertToTable();
	 	fieldVo_egta.insertToTable();
	 	fieldVo_p2a.insertToTable();
	 	fieldVo_pt.insertToTable();
	 	fieldVo_apr.insertToTable();
	 	fieldVo_wb.insertToTable();
	 	fieldVo_ot.insertToTable();
	 	
	 	computedVo.setFieldVo_apr(fieldVo_apr);
	 	computedVo.setFieldVo_egta(fieldVo_egta);
	 	computedVo.setFieldVo_igv(fieldVo_igv);
	 	computedVo.setFieldVo_ot(fieldVo_ot);
	 	computedVo.setFieldVo_p2a(fieldVo_p2a);
	 	computedVo.setFieldVo_pt(fieldVo_pt);
	 	computedVo.setFieldVo_sta(fieldVo_STA);
		return computedVo;
	}
	
	//基本属性字段计算
	private void computeBasic(HashVO curVo,A13ComputeVo computedVo) throws Exception{
		if(curVo.getDoubleValue("TAT") == null || curVo.getDoubleValue("ALT")== null ){
			throw new Exception("报文数据不全，缺少TAT/ALT数据！");
		}
		
		computedVo.setTat(curVo.getDoubleValue("TAT"));//外界温度
		computedVo.setAlt(curVo.getDoubleValue("ALT"));//海拔
		computedVo.setAhrs(curVo.getDoubleValue("AHRS"));//小时
		computedVo.setAcyc(curVo.getDoubleValue("ACYC"));//循环
		
		int maxSeq = getMaxEgtaSeq(curVo.getDoubleValue("egta_n1"),curVo.getDoubleValue("egta_n2"),curVo.getDoubleValue("egta_n3"));
		
		String esn_max    = curVo.getStringValue("esn_n"+maxSeq);
		Double egta_max   = curVo.getDoubleValue("egta_n"+maxSeq);
		Double igv_max    = curVo.getDoubleValue("igv_n"+maxSeq);
		Double p2a_max    = curVo.getDoubleValue("p2a_s"+maxSeq);
		Double lcit_max   = curVo.getDoubleValue("lcit_s"+maxSeq);
		Double wb_max     = curVo.getDoubleValue("wb_s"+maxSeq);
		Double pt_max     = curVo.getDoubleValue("pt_s"+maxSeq);
		Double ota_max    = curVo.getDoubleValue("ota_s"+maxSeq);
		Double gla_max    = curVo.getDoubleValue("gla_s"+maxSeq);
		Double na_max     = curVo.getDoubleValue("na_n"+maxSeq);

		try{
			double scv_max    = curVo.getDoubleValue("scv_n"+maxSeq);
			double hot_max    = curVo.getDoubleValue("hot_n"+maxSeq);
			double lcdt_max   = curVo.getDoubleValue("lcdt_s"+maxSeq);//这个数据有可能为XXXX
			computedVo.setLcdt_max(lcdt_max);
			computedVo.setScv_max(scv_max);
			computedVo.setHot_max(hot_max);
		}catch (Exception e) {
			//
		}
		
		computedVo.setEsn_max(esn_max);
		computedVo.setNa_max(na_max);
		computedVo.setEgta_max(egta_max);
		computedVo.setIgv_max(igv_max);
		computedVo.setP2a_max(p2a_max);
		computedVo.setLcit_max(lcit_max);
		computedVo.setWb_max(wb_max);
		computedVo.setPt_max(pt_max);
		
		computedVo.setOta_max(ota_max);
		computedVo.setGla_max(gla_max);
		
		double sta_v1 = curVo.getDoubleValue("sta_v1");
		double egtp_v1 = curVo.getDoubleValue("egtp_v1");
		double npa_v1 = curVo.getDoubleValue("npa_v1");
		double ota_v1 = curVo.getDoubleValue("ota_v1");
		double lcit_v1 = curVo.getDoubleValue("lcit_v1");
		computedVo.setSta_v1(sta_v1);
		computedVo.setEgtp_v1(egtp_v1);
		computedVo.setNpa_v1(npa_v1);
		computedVo.setOta_v1(ota_v1);
		computedVo.setLcit_v1(lcit_v1);
		
		logger.debug("基础数据计算完成！");
		this.computeCor(curVo,computedVo);
		this.computePdi(curVo,computedVo);
		
		
	}
	
	//计算5点均值
	private void compute5AvgValue(A13ComputeVo computedVo,boolean isChangePoint,String changeMsgno) throws Exception{
		if(isChangePoint){
			computedVo.setEgtp_v1_cor_roll5(computedVo.getEgtp_v1_cor());
			computedVo.setNpa_v1_cor_roll5(computedVo.getNpa_v1_cor());
			computedVo.setHot_ot_max_roll5(computedVo.getHot_ot_max());
		}else{
			StringBuilder sb = new StringBuilder("SELECT * FROM ( ");
			sb.append("select egtp_v1_cor,npa_v1_cor,nvl(HOT_OT_MAX,0) HOT_OT_MAX  from a_dfd_a13_compute");
			sb.append(" WHERE ACNUM=? AND MSG_NO<? AND MSG_NO>? ORDER BY MSG_NO DESC");
			sb.append(" ) WHERE ROWNUM < " + 5);//考虑飘点情况,多取N条数据
			
			HashVO[] vos =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), 
					computedVo.getAcnum(),computedVo.getMsgno(),changeMsgno);
			
			double egptSum = computedVo.getEgtp_v1_cor();
			double npaSum = computedVo.getNpa_v1_cor();
			double hototSum = computedVo.getHot_ot_max();
			
			int num = 1;
			for(int i=0;i<vos.length;i++){
				num ++;
				egptSum += vos[i].getDoubleValue("EGTP_V1_COR");
				npaSum += vos[i].getDoubleValue("NPA_V1_COR");
				hototSum += vos[i].getDoubleValue("HOT_OT_MAX");
			}
			
			computedVo.setEgtp_v1_cor_roll5( egptSum/num );
			computedVo.setNpa_v1_cor_roll5(npaSum/num);
			computedVo.setHot_ot_max_roll5(hototSum/num);
		}
	}
	
	
	//APU基准值计算
	private void computeApuBaseValue(A13ComputeVo computedVo,boolean isChangePoint) throws Exception{
		double base_sta      =0; //基准值-自启动时间    
		double base_igv      =0; //基准值-APU最大EGT时的IGV角度 
		double base_egt_cor  =0; //基准值-EGT           
		double base_p2a_cor  =0; //基准值-P2A           
		double base_apr_cor  =0; //基准值-APR           
		double base_npa_cor  =0; //基准值-NPA           
		double base_pt_cor   =0; //基准值-PT            
		int base_pointnum =0;//已计算基准值的个数,算到10个值为基准值
		
		boolean needUpdate = false;//是否需要更新
		
		if(isChangePoint){
			//从当前点开始计算
			base_sta = computedVo.getSta_v1();
			base_igv = computedVo.getIgv_max();
			base_egt_cor = computedVo.getEgta_max_cor();
			base_p2a_cor = computedVo.getP2a_max_cor();
			base_apr_cor = computedVo.getApr_max();
			base_npa_cor = computedVo.getNpa_v1_cor();
			base_pt_cor = computedVo.getPt_max_cor();
			base_pointnum = 1;
			
			needUpdate = true;
		}else{
			StringBuilder sb = new StringBuilder("select ID,APUSN,BASEORGID,AIRCRAFTID,APUMODELID ");
			sb.append(",BASE_STA,BASE_IGV,BASE_EGT_COR,BASE_P2A_COR,BASE_APR_COR,BASE_NPA_COR,BASE_PT_COR,BASE_POINTNUM");
			sb.append(" from b_apu where apusn=?");
			
			HashVO[] vos =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), computedVo.getAsn());
			
			if(vos.length ==0){
				logger.warn("ANU ["+computedVo.getAsn()+"]在数据库中不存在！！！" );
			}else{
				base_pointnum = vos[0].getIntegerValue("BASE_POINTNUM");
				if(base_pointnum >= 10){//已计算了10条数据，不需要再进行计算
					logger.debug("ANU ["+computedVo.getAsn()+"]已计算了10条数据，不需要再进行计算");
				}else{
					base_sta     = (computedVo.getSta_v1() + vos[0].getDoubleValue("BASE_STA")*base_pointnum) / (base_pointnum+1);
					base_igv     = (computedVo.getIgv_max() + vos[0].getDoubleValue("BASE_IGV")*base_pointnum) / (base_pointnum+1);
					base_egt_cor = (computedVo.getEgta_max_cor() + vos[0].getDoubleValue("BASE_EGT_COR")*base_pointnum) / (base_pointnum+1);
					base_p2a_cor = (computedVo.getP2a_max_cor() + vos[0].getDoubleValue("BASE_P2A_COR")*base_pointnum) / (base_pointnum+1);
					base_apr_cor = (computedVo.getApr_max() + vos[0].getDoubleValue("BASE_APR_COR")*base_pointnum) / (base_pointnum+1);
					base_npa_cor = (computedVo.getNpa_v1_cor() + vos[0].getDoubleValue("BASE_NPA_COR")*base_pointnum) / (base_pointnum+1);
					base_pt_cor  = (computedVo.getPt_max_cor() + vos[0].getDoubleValue("BASE_PT_COR")*base_pointnum) / (base_pointnum+1);
					
					base_pointnum = base_pointnum + 1;
					needUpdate = true;
				}
			}
		}
		
		if(needUpdate){
			StringBuilder sb = new StringBuilder("update b_apu set ");
			sb.append("BASE_STA=?,BASE_IGV=?,BASE_EGT_COR=?,BASE_P2A_COR=?");
			sb.append(",BASE_APR_COR=?,BASE_NPA_COR=?,BASE_PT_COR=?");
			sb.append(",BASE_POINTNUM=?");
			sb.append(" where apusn=?");
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), base_sta,base_igv,base_egt_cor,base_p2a_cor
						,base_apr_cor,base_npa_cor,base_pt_cor,base_pointnum, computedVo.getAsn());
			
			logger.debug("ANU ["+computedVo.getAsn()+"]基准值计算，当前已计算["+base_pointnum+"]个点！");
		}
		
	}

	public static void main(String[] args) throws Exception{
		A13DataComputeService ser = new A13DataComputeService();
		A13ComputeVo computedVo = new A13ComputeVo();
		computedVo.setTat(15.5);
		computedVo.setAlt(121);
		computedVo.setEgta_max(617);
		computedVo.setPt_max(3.88);
		long start = System.currentTimeMillis();
		double thita =ser.getThita(computedVo);
		
		long end = System.currentTimeMillis();
		System.out.println("耗时:" + (end-start) );
		
		System.out.println(thita);
	}
	
	//计算修正值数据
	private void computeCor(HashVO curVo, A13ComputeVo computedVo) throws Exception{
		double alt = computedVo.getAlt();
		double tat = computedVo.getTat();
		double egta = computedVo.getEgta_max();
		double pt = computedVo.getPt_max();
		double p2a = computedVo.getP2a_max();
		double wb = computedVo.getWb_max();
		double lcit = computedVo.getLcit_max();
		double igv =  computedVo.getIgv_max();
		double gla = computedVo.getGla_max();
		
//		Thita(thita_tat) = e ^ (-((AltValue * CoverFt) / 1000) / ((8.51 * (273.15 + TATValue)) / (9.8 * 29)))
//		大气压力修正系数 
//		Pamb = Pstd * Thita 用于估算当前环境的大气压力
		//计算Thita
		double t_exp = (-((alt * ApmsConst.COVERFT_M) / 1000) / ((8.51 * (273.15 + tat)) / (9.8 * 29)));
		double thita = Math.exp(t_exp);
		computedVo.setThita(thita);
		
		//计算pamb
		double pamb = ApmsConst.PSTD * thita;
		//System.out.println("pamb = " + pamb);
		
		//npa修正参数  npa*(T0/T1)^.0.5，修正为15度时数据
		double npa = computedVo.getNpa_v1();
		double t1 = computedVo.getLcit_v1() + ApmsConst.ABSOLUTE_ZERO;
		double t0 = 15 + ApmsConst.ABSOLUTE_ZERO;
		
		double npa_cor = npa*Math.sqrt(t0/t1);
		computedVo.setNpa_v1_cor(npa_cor);
		
		//p2a修正 P2A_COR= P2A*(T0/T1)^.0.5，修正为50度时数据
		t0 = 50 + ApmsConst.ABSOLUTE_ZERO;
		double p2a_cor = p2a* Math.sqrt(t0/t1);
		computedVo.setP2a_max_cor(p2a_cor);
		
		//WB修正 WB_COR=WB/thita_tat+(-0.283+0.006*tat-0.0000089*tat^2-0.0000000593*tat^3)/60, 2013/7/9 huanglei 修正值是千克/分钟,除60
		double wb_cor = wb/thita + (-0.283 + 0.006*tat - 0.0000089* Math.pow(tat, 2) - 0.0000000593*Math.pow(tat, 3))/60;
		computedVo.setWb_max_cor(wb_cor);
		
		//计算滑油修正温度ot_max= （OTAmax-LCITmax）,ot_v1 = OTA_V1-LCIT_V1
		double ot_max = computedVo.getOta_max() - computedVo.getLcit_max();
		double ot_v1 = computedVo.getOta_v1() - computedVo.getLcit_v1();
		computedVo.setOt_max(ot_max);
		computedVo.setOt_v1(ot_v1);
		
		//thita_lcit THITA_LCIT = P2A/PSTD 
		double thita_lcit = p2a/ApmsConst.PSTD;
		computedVo.setThita_lcit(thita_lcit);
		
		//EGTP_COR=(EGTP+273.5)*THITA - 273.5
		double egtp_cor = (computedVo.getEgtp_v1()+273.5) * thita - 273.5;
		computedVo.setEgtp_v1_cor(egtp_cor);
		
		//add by zzy 20130801 根据不同型号APU计算修正
		double pt_cor;
		double egt_cor;
		if("GTCP331-350C".equalsIgnoreCase(computedVo.getApuModel())){
			double egt_c = 81.79743476337904 + -1.182866479925304 * lcit + -0.0120109096270087 *Math.pow(lcit,2) + 9.631922944616654e-006 *Math.pow(lcit,3);
			double pt_c = -0.6840784284539592 + 0.01432611460962489 * lcit + -5.513440941155017e-005*Math.pow(lcit,2)+ -4.552230550735096e-008 *Math.pow(lcit,3);

			double egt_gla_c = 0.4241810734012095 + -0.5366729538486382 * gla + -0.002168040056738909 * Math.pow(gla,2) + 5.37653015500729e-006 * Math.pow(gla,3);

			double pt_igv_c=0.01344800283711652 + 0.01572685608255891 * igv + 0.002019946078763184 *Math.pow(igv,2) + -4.71454121693412e-005 * Math.pow(igv,3);

			double egt_igv_c=-0.2351292270447342 + 2.062734467218623 * igv + -0.0582581758320685 * Math.pow(igv,2) + 0.002848086313412029 * Math.pow(igv,3);


			egt_cor=egta+egt_gla_c+egt_c+egt_igv_c;  //egt数据修正
			pt_cor = pt/thita+pt_c+pt_igv_c; //pt数据修正

		}else{
			//PtCor = PT/Thita + detaPT;
//			PtCor = PT / Thita - 0.08 + (-0.716 + 0.016 * TATValue + (-0.0000301 * TATValue ^ 2) + (0.0000000203 * TATValue ^ 3)) 
//			输出PT压力修正公式,黄色底色的数据可设置，跟APU型号有关
			double detaPt = - 0.08 + (-0.716 + 0.016 * tat + (-0.0000301 * Math.pow(tat, 2)) + (0.0000000203 * Math.pow(tat, 3)) );
			pt_cor = pt/thita + detaPt;
			
//			Pbreq = 3.09 跟飞机型号有关 希望可设置
//			Pdeta = 1 修正压力 跟APU型号有关 希望可设置
//			TATcor = 79.102 - 1.026 * TATValue - 0.011 * TATValue ^ 2 + (-0.000000952 * TATValue ^ 3)(温度修正公式需要设置) 
//			EGTcor= egta +  TATcor + 36 * (Pamb - Pdeta) / Thita - 1 - 89 * (PbCor - Pbreq)
			double tatcor = 79.102 - 1.026 * tat - 0.011 * Math.pow(tat, 2) + (-0.000000952 * Math.pow(tat, 3));		
			double pbreq = 3.09;//跟飞机型号有关 希望可设置
			//double pdeta = 1; //修正压力 跟APU型号有关 希望可设置,不使用
			egt_cor = egta + tatcor + 36*(pamb-p2a)/thita -1 - 89*(pt_cor - pbreq);		
		}
		computedVo.setPt_max_cor(pt_cor);
		computedVo.setEgta_max_cor(egt_cor);
		
		//APR引气出口与进口的增压比 计算PT_MAX_COR/P2A_MAX_COR
		double apr = pt_cor/p2a_cor;
		computedVo.setApr_max(apr);
		
		logger.debug("修正数据计算完成！");
		
	}
	
	//PDI性能参数计算
	private void computePdi(HashVO curVo,A13ComputeVo computedVo) throws Exception{
		//pdi_new=(EGTcor/EGTredline)*a +(STA/STAredline)*b+(PTredline/PTcor)*c
		//pdi_new=(100-(EGTredline-EGTcor)/100)*a +(40-(STAredline-STA))/40)*b+(0.4-(PTcor-PTredline))/0.4)*c 黄磊一次修改
		//pdi_new=(EGT_DNMT-(EGTredline-EGTcor)/EGT_DNMT)*a +(STA_DNMT-(STAredline-STA))/DNMT)*b+(PT_DNMT-(PTcor-PTredline))/PT_DNMT)*c 二次修改
		
		StringBuilder sb = new StringBuilder("SELECT EGT_BASE,EGT_WEIGHT,STA_BASE,STA_WEIGHT,TSR_BASE,TSR_WEIGHT,PT_BASE,PT_WEIGHT,P.TYPE");
		sb.append(",REPAIR_FLAG,TIME_ONREPAIRED,CYCLE_ONREPAIRED"); //维修信息
		sb.append(" from apu_compute_param p,b_apu a ");
		sb.append(" where p.apumodel_id=a.apumodelid and a.apusn=? ");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), computedVo.getAsn());
		for(int i=0;i<vos.length;i++){
			HashVO pvo = vos[i];
			String type = pvo.getStringValue("TYPE");

			if("1".equals(type)){//pdi_new
				//pdi_new=(EGT_DNMT-(EGTredline-EGTcor)/EGT_DNMT)*a +(STA_DNMT-(STAredline-STA))/DNMT)*b+(PT_DNMT-(PTcor-PTredline))/PT_DNMT)*c 二次修改,新公式 20140318 zhangzy 
				double pdi_new = (computedVo.getEgt_dnmt()-(pvo.getDoubleValue("EGT_BASE")-computedVo.getEgta_max_cor()))/computedVo.getEgt_dnmt() * pvo.getDoubleValue("EGT_WEIGHT")
						+  (computedVo.getSta_dnmt()-(pvo.getDoubleValue("STA_BASE")-computedVo.getSta_v1()))/computedVo.getSta_dnmt() * pvo.getDoubleValue("STA_WEIGHT")
						+ (computedVo.getPt_dnmt()-(computedVo.getPt_max_cor()- pvo.getDoubleValue("PT_BASE"))) / computedVo.getPt_dnmt() * pvo.getDoubleValue("PT_WEIGHT");
				
				computedVo.setPdi_new(pdi_new);
			}else if("0".equals(type)){//pdi_old
				double tsr = computedVo.getAhrs()/60;//维修后运行时间, 如果没有维修过，取运行总时间
				//if("1".equals(pvo.getStringValue("REPAIR_FLAG"))) {//维修过
					//modify by zhangzy 自修理后时间从L_APU_RUNLOG中获取，以兼容APU修理几次后，对计算数据删除重新计算的情况
				//} 不再进行判断是否维修过，直接减(兼容APU修理数据不一致的情况)
				double time_onrepaired = curVo.getDoubleValue("TIME_ONREPAIRED")==null ?0 :curVo.getDoubleValue("TIME_ONREPAIRED"); 
				tsr = (computedVo.getAhrs() - time_onrepaired)/60;//换算成小时
				
				//pdi_old=(EGTcor/EGTredline)*a +(STA/STAredline)*b+(PTredline/PTcor)*c +(TSR/TSRredline)*d
//				double pdi_old = computedVo.getEgta_max_cor() / pvo.getDoubleValue("EGT_BASE") * pvo.getDoubleValue("EGT_WEIGHT")
//				+ computedVo.getSta_v1() / pvo.getDoubleValue("STA_BASE") * pvo.getDoubleValue("STA_WEIGHT")
//				+ pvo.getDoubleValue("PT_BASE") / computedVo.getPt_max_cor() * pvo.getDoubleValue("PT_WEIGHT")
//				+ tsr/ pvo.getDoubleValue("TSR_BASE") * pvo.getDoubleValue("TSR_WEIGHT");
				
				//PDI_OLD=(EGT_DNMT-(EGTredline-EGTcor)/EGT_DNMT)*a +(STA_DNMT-(STAredline-STA))/DNMT)*b+(PT_DNMT-(PTcor-PTredline))/PT_DNMT)*c+(TSR/TSRredline)*d
				//二次修改,新公式 20140318 zhangzy
				double pdi_old= tsr/ pvo.getDoubleValue("TSR_BASE") * pvo.getDoubleValue("TSR_WEIGHT")
					+ (computedVo.getEgt_dnmt()-(pvo.getDoubleValue("EGT_BASE")-computedVo.getEgta_max_cor()))/computedVo.getEgt_dnmt() * pvo.getDoubleValue("EGT_WEIGHT")
					+  (computedVo.getSta_dnmt()-(pvo.getDoubleValue("STA_BASE")-computedVo.getSta_v1()))/computedVo.getSta_dnmt() * pvo.getDoubleValue("STA_WEIGHT")
					+ (computedVo.getPt_dnmt()-(computedVo.getPt_max_cor()- pvo.getDoubleValue("PT_BASE"))) / computedVo.getPt_dnmt() * pvo.getDoubleValue("PT_WEIGHT");
			
				
				computedVo.setPdi_old(pdi_old);
			}
		}
		
		if(vos.length ==0){
			logger.warn("APU []对应的APU型号没有设置计算参数数据！");
		}
		
		logger.debug("PDI计算完成！");
	}
	
	
	private double getThita(A13ComputeVo computedVo) throws Exception{
//		Thita = e ^ (-((AltValue * CoverFt) / 1000) / ((8.51 * (273.15 + TATValue)) / (9.8 * 29)))
//		大气压力修正系数 
//		Pamb = Pstd * Thita 用于估算当前环境的大气压力
		
		double thita;
		double alt = computedVo.getAlt();
		double tat = computedVo.getTat();
		double egta = computedVo.getEgta_max();
		
		double exp = (-((alt * ApmsConst.COVERFT_M) / 1000) / ((8.51 * (273.15 + tat)) / (9.8 * 29)));
		
		System.out.println("exp =" +exp);
		thita = Math.exp(exp);
		System.out.println("thita = " + thita);
		
		double pstd = 1.0132;
		double pt = computedVo.getPt_max();
		double pamb = pstd * thita;
		System.out.println("pamb = " + pamb);
		
//		PtCor = PT / Thita - 0.08 + (-0.716 + 0.016 * TATValue + (-0.0000301 * TATValue ^ 2) + (0.0000000203 * TATValue ^ 3)) 
//		输出PT压力修正公式,黄色底色的数据可设置，跟APU型号有关

		double ptcor = pt/thita - 0.08 + (-0.716 + 0.016 * tat + (-0.0000301 * Math.pow(tat, 2)) + (0.0000000203 * Math.pow(tat, 3)) );
		System.out.println("ptcor = " + ptcor);
		
//		Ptreq = 3.09 跟飞机型号有关 希望可设置
//		Pdeta = 1 修正压力 跟APU型号有关 希望可设置
//		TATcor = 79.102 - 1.026 * TATValue - 0.011 * TATValue ^ 2 + (-0.000000952 * TATValue ^ 3)(温度修正公式需要设置) 
//		EGTcor= egta +  TATcor + 36 * (Pamb - Pdeta) / Thita - 1 - 89 * (PbCor - Ptreq)
		double tatcor = 79.102 - 1.026 * tat - 0.011 * Math.pow(tat, 2) + (-0.000000952 * Math.pow(tat, 3));
		
		System.out.println(" tatcor = " + tatcor);
		
		double ptreq = 3.09;
		double pdeta = 1;
		double egtcor = egta + tatcor + 36*(pamb-pdeta)/thita -1 - 89*(ptcor - ptreq);
		
		System.out.println(" egtcor = " + egtcor);
		
		return thita;
	}
	
	//找到EGTA最大的一组数据序号
	private int getMaxEgtaSeq(double egta_n1,double egta_n2,double egta_n3){
		int seq = 1;
		double max = egta_n1;
		
		if(egta_n2 > egta_n1){
			seq = 2;
			max = egta_n2;
		}		
		if(egta_n3 > max){
			seq = 3;
			max = egta_n3;
		}		
		
		return seq;
	}
	
	/**
	 * 获取红线值
	 * @param computedVo
	 * @return 是否查到红线值
	 * @throws Exception
	 */
	public boolean getRedLineValue(A13ComputeVo computedVo) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT M.MODEL,M.SUBMODEL,RL_NA,RL_STA,RL_IGV,RL_EGT,RL_EGT_COR");
		sb.append(",RL_APR,RL_APR_COR,RL_WB,RL_WB_COR,RL_PT,RL_PT_COR,RL_OTA,RL_OTA_COR");
		sb.append(",EGT_DNMT,PT_DNMT,STA_DNMT");
		sb.append(" FROM B_APU_MODEL M,B_APU A ");
		sb.append(" WHERE M.ID=A.APUMODELID AND A.APUSN=?");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), computedVo.getAsn());
		if(vos.length > 0){
			HashVO vo = vos[0];
			computedVo.setRl_na(vo.getDoubleValue("rl_na"));
			computedVo.setRl_sta(vo.getDoubleValue("rl_sta"));
			computedVo.setRl_igv(vo.getDoubleValue("rl_igv"));
			computedVo.setRl_egt(vo.getDoubleValue("rl_egt"));
			computedVo.setRl_egt_cor(vo.getDoubleValue("rl_egt_cor"));			
			computedVo.setRl_apr(vo.getDoubleValue("rl_apr"));
			computedVo.setRl_apr_cor(vo.getDoubleValue("rl_apr_cor"));
			computedVo.setRl_wb(vo.getDoubleValue("rl_wb"));
			computedVo.setRl_wb_cor(vo.getDoubleValue("rl_wb_cor"));
			computedVo.setRl_pt(vo.getDoubleValue("rl_pt"));
			computedVo.setRl_pt_cor(vo.getDoubleValue("rl_pt_cor"));
			computedVo.setRl_ota(vo.getDoubleValue("rl_ota"));
			computedVo.setRl_ota_cor(vo.getDoubleValue("rl_ota_cor"));
			computedVo.setEgt_dnmt(vo.getDoubleValue("egt_dnmt"));
			computedVo.setPt_dnmt(vo.getDoubleValue("pt_dnmt"));
			computedVo.setSta_dnmt(vo.getDoubleValue("sta_dnmt"));
			computedVo.setApuModel(vo.getStringValue("MODEL"));
			
			return true;
		}else{
			return false;
		}
		
		
	}
	
	//获取当前报文的前一报文
	public HashVO getPreA13ComputeVo(A13ComputeVo computedVo) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT L.ID,L.MSG_NO,L.OTA_MAX,L.OT_MAX,L.LCIT_MAX");
		sb.append(" FROM A_DFD_A13_COMPUTE L WHERE ACNUM='"+computedVo.getAcnum()+"' AND L.MSG_NO=");
		sb.append(" (SELECT MAX(MSG_NO) FROM A_DFD_A13_COMPUTE WHERE ACNUM='"+computedVo.getAcnum()+"' AND MSG_NO < "+computedVo.getMsgno()+")");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		
		if(vos.length > 0)
			return vos[0];
		else {
			return null;
		}
		
				
	}
	
}
