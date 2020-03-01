package com.apms.bs.datacompute;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.datacompute.vo.A25ComputeVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.bs.sysconfig.ApmsSysParamConfService;
import com.apms.bs.sysconfig.OilConfVarVo;
import com.apms.bs.util.DateUtil;
import com.apms.vo.ApmsVarConst;
import com.apms.vo.SysParamConfVo;

public class A25DataComputeService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	private SysParamConfVo sysVar;
	private OilConfVarVo oilVar;
	
	private double oil_add_limit = 1.5;//前后报文滑油量差值判断点，如相差大于此值，认为加滑油了
	private double ehrsDetaDefault = 0.75; //前后报文运行小时差值为0，又计算不出时间差时，默认定义时间差为0.75小时
	
	public A25DataComputeService() throws Exception{
		sysVar = ApmsSysParamConfService.getInstance().getConfVo();
		
		oilVar = sysVar.getOilVo();
		oil_add_limit = oilVar.getDeta_oiladd_limit();
		
		ehrsDetaDefault = oilVar.getEhrs_deta_default();
		
	}
	
	/**
	 * 根据当前和历史N条记录，计算报文数据
	 * @param curVo 要计算的报文数据
	 * @throws Exception
	 */
	public A25ComputeVo compute25ByHistoryVos(HashVO curVo) throws Exception{
		A25ComputeVo computedVo = new A25ComputeVo();
		
		int cnt = curVo.getIntegerValue("CNT");
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		Date date_utc = curVo.getDateValue("DATE_UTC");
//		double ehr_ga_cf = curVo.getDoubleValue("EHR_GA_1");
//		double ehr_ga_cg = curVo.getDoubleValue("EHR_GA_2");
		double oiq1 = curVo.getDoubleValue("OIQ1_Z3");
		double oiq2 = curVo.getDoubleValue("OIQ2_Z3");
		double cf_ehrs = curVo.getDoubleValue("EHRS_1");
		double cg_ehrs = curVo.getDoubleValue("EHRS_2");
		String rptno = ApmsVarConst.RPTNO_A25;
		
		computedVo.setId(curVo.getStringValue("ID"));
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setDate_utc(date_utc);
		computedVo.setEsn_1(curVo.getStringValue("ESN_1"));
		computedVo.setEsn_2(curVo.getStringValue("ESN_2"));
		computedVo.setOiq1_z3(oiq1);
		computedVo.setOiq2_z3(oiq2);
		computedVo.setCf_ehrs(cf_ehrs);
		computedVo.setCg_ehrs(cg_ehrs);
		computedVo.setCnt(cnt);
		
		
//		boolean isChangePoint2 = false;//是否是加油点
		String changeMsgno2 = "1";//TODO
		
		//获取该报文的前一报文信息
		HashVO preVo = getPreA25Vo(computedVo);
		HashVO detectA27 = getA25A27(computedVo, preVo);
		
		
		
		//1, 判断是否是加油点，并计算与前一报文滑油量差值、滑油消耗率。 左、右发的加油点分别判断
		boolean isChangePoint1 = computedOilAdd(computedVo, preVo, curVo, detectA27, 1);//左发是否是加油点
		
		//2,如果当前报文不是加油点，找出加油点报文msg_no
		String changeMsgno1 = "100";
		if(!isChangePoint1) {
			changeMsgno1 = getOilAddMsgno(computedVo,1);
		}else {
			changeMsgno1 = msgno;
		}
		
		logger.debug("加油点信息判断完成！");
				
		DfdFieldCompute fieldComp = new DfdFieldCompute();
		//3,对滑油消耗率做方差计算、样本T检验
		String f_name = "DETA_OIQ1_FWDRATE";
		double f_value = computedVo.getDeta_oiq1_fwdrate();
		FieldComputeVo fwdRate1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, fwdRate1Vo, changeMsgno1, isChangePoint1, true);
		
		
		//4,滑油量对运行小时(EHRS)做K值回归，对K值做2DETA 对K值独立样本T检验 （从加油点开始算）
		//4.1 左发计算
		String x_fname = "EHRS_1";//对运行时间进行回归
		double x_fvalue = curVo.getDoubleValue(x_fname);
		f_name = "OIQ1_Z3";
		f_value = curVo.getDoubleValue(f_name);
		FieldComputeVo fieldVo_oil1 = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_oil1.setTimes(1);
		fieldVo_oil1.setEstimateLimit(true);
		fieldVo_oil1.setLimitY(15.0);//TODO 发动机添加滑油红线,和发动机型号绑定
	 	fieldComp.fieldRegressionKCompute(curVo, fieldVo_oil1, changeMsgno1,isChangePoint1,true);
	 	
	 	
	 	//TODO 对右发做如上4步处理
		boolean isChangePoint2 = computedOilAdd(computedVo, preVo, curVo, detectA27, 2);
		if(!isChangePoint2) {
			changeMsgno2 = getOilAddMsgno(computedVo,2);
		}else {
			changeMsgno2 = msgno;
		}
		
		//对滑油消耗率做方差计算、样本T检验
		f_name = "DETA_OIQ2_FWDRATE";
		f_value = computedVo.getDeta_oiq2_fwdrate();
		FieldComputeVo fwdRate2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
		fieldComp.fieldRegressionCompute(curVo, fwdRate2Vo, changeMsgno2, isChangePoint2, true);
		
		//4,滑油量对运行小时(EHRS)做K值回归，对K值做2DETA 对K值独立样本T检验 （从加油点开始算）
		x_fname = "EHRS_2";//对运行时间进行回归
		x_fvalue = curVo.getDoubleValue(x_fname);
		f_name = "OIQ2_Z3";
		f_value = curVo.getDoubleValue(f_name);
		FieldComputeVo fieldVo_oil2 = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_oil2.setTimes(1);
		fieldVo_oil2.setEstimateLimit(true);
		fieldVo_oil2.setLimitY(15.0);//TODO 发动机添加滑油红线,和发动机型号绑定
	 	fieldComp.fieldRegressionKCompute(curVo, fieldVo_oil2, changeMsgno2,isChangePoint2,true);
		
	 	
	 	//数据入库
	 	computedVo.insertToTable();
	 	fwdRate1Vo.insertToTable();
	 	fieldVo_oil1.insertToTable();
		fwdRate2Vo.insertToTable();
	 	fieldVo_oil2.insertToTable();
	 	
		return computedVo;
	}
	
	//TODO 判断是否是加油点
	public boolean computedOilAdd(A25ComputeVo computedVo,HashVO preVo,HashVO curVo,HashVO detectA27, int esnSeq) throws Exception{
		//计算出加油点、耗油率、耗油告警等信息		
		double rate_oiq1; //左发滑油消耗率
		double rate_oiq2; //右发滑油消耗率

		
		if (preVo == null){
			
			return false;
		} else if (esnSeq == 1){                        //判断左发
			double oil1Pre = preVo.getDoubleValue("OIQ1_Z3");  //
			double oil1now = computedVo.getOiq1_z3();
			double deta_oiq1 = oil1now - oil1Pre;    //DETA_OIQ1=现在报文滑油量-先前报文滑油量
			computedVo.setDeta_oiq1_fwd(deta_oiq1);
			
			int cntnow = curVo.getIntegerValue("CNT");
			int cntpre = preVo.getIntegerValue("CNT");
			int deta_cnt = cntnow - cntpre;    //计算 前后报文CNT的差
			if (deta_cnt == 1){               //如果CNT差为1，EHRS增量： 提取当前报文的EHR_GA_1
				double ehr_ga_1 = curVo.getDoubleValue("EHR_GA_1");
				rate_oiq1 = -deta_oiq1/(ehr_ga_1);   //计算 滑油消耗率
			} else {                         //如果CNT差不为1，EHRS增量：计算为前后报文的EHRS差
				double ehrs1Pre = preVo.getDoubleValue("CF_EHRS");
				double ehrs1now = computedVo.getCf_ehrs();
				double ehr_ga1 = ehrs1now - ehrs1Pre;
				if (ehr_ga1 == 0){            //当ERHS增量为0时，默认0.75（可变）
					ehr_ga1 = ehrsDetaDefault;
				}
				rate_oiq1 = -deta_oiq1/ehr_ga1;
			}
			
			if (detectA27 !=null && detectA27.getIntegerValue("OILADD_ENG1")==1){//判断 如果左发有A27报文
				computedVo.setDeta_oiq1_fwd_s(1);   //判断 如果有A27报文， 添加滑油标记
				computedVo.setIs_oiq1_addrpt(1);     //有A27号报文添加
				computedVo.setDeta_oiq1_fwdrate(0.0); //如果有滑油添加标记 设置消耗率为0
				return true;
				
			} else {
				if (deta_oiq1>= oil_add_limit){           //判断 如果滑油变化大于0.7（可设置），添加滑油标记，并且告警
					computedVo.setDeta_oiq1_fwd_s(1);
					computedVo.setDeta_oiq1_fwdalarm(1);   
					computedVo.setDeta_oiq1_fwdrate(0.0); //如果有滑油添加标记 设置消耗率为0
					return true;
				}else {
					computedVo.setDeta_oiq1_fwdrate(rate_oiq1); //如果没有滑油添加标记 记录当前消耗率 
					return false;				
				}
			}
			
			
		} else {                                  //判断右发
			double oil2Pre = preVo.getDoubleValue("OIQ2_Z3");  //
			double oil2now = computedVo.getOiq2_z3();
			double deta_oiq2 = oil2now - oil2Pre;    //DETA_OIQ2=现在报文滑油量-先前报文滑油量
			computedVo.setDeta_oiq2_fwd(deta_oiq2);
			
			int cntnow = curVo.getIntegerValue("CNT");
			int cntpre = preVo.getIntegerValue("CNT");
			int deta_cnt = cntnow - cntpre;    //计算 前后报文CNT的差
			if (deta_cnt == 1){               //如果CNT差为1，EHRS增量： 提取当前报文的EHR_GA_2
				double ehr_ga_2 = curVo.getDoubleValue("EHR_GA_2");
				rate_oiq2 = -deta_oiq2/(ehr_ga_2);
			} else {                         //如果CNT差不为1，EHRS增量：计算为前后报文的EHRS差
				double ehrs2Pre = preVo.getDoubleValue("CF_EHRS");
				double ehrs2now = computedVo.getCf_ehrs();
				double ehr_ga2 = ehrs2now - ehrs2Pre;
				if (ehr_ga2 == 0){            //当ERHS增量为0时，默认0.75（可变）
					ehr_ga2 = ehrsDetaDefault;
				}
				rate_oiq2 = -deta_oiq2/ehr_ga2;
				computedVo.setDeta_oiq2_fwdrate(rate_oiq2);
			}
			
			if (detectA27 != null && detectA27.getIntegerValue("OILADD_ENG2") == 1){   //判断 如果 右发 有A27报文
				computedVo.setDeta_oiq2_fwd_s(1);   //判断 如果有A27报文， 添加滑油标记
				computedVo.setIs_oiq2_addrpt(1);
				computedVo.setDeta_oiq2_fwdrate(0.0); //如果有滑油添加标记 设置消耗率为0
				return true;	
			} else {
				if (deta_oiq2>= oil_add_limit){           //判断 如果滑油变化大于0.7（可设置），添加滑油标记，并且告警
					computedVo.setDeta_oiq2_fwd_s(1);
					computedVo.setDeta_oiq2_fwdalarm(1);    
					computedVo.setDeta_oiq2_fwdrate(0.0); //如果有滑油添加标记 设置消耗率为0
					return true;
				}else  {
					computedVo.setDeta_oiq2_fwdrate(rate_oiq2); //如果没有滑油添加标记 记录当前消耗率 
					return false;            
				}
				
			}
			
		}
		
		
	}
	
	//TODO 查找加油点的报文 msg_no
	public String getOilAddMsgno(A25ComputeVo computedVo,int esnSeq) throws Exception{
		String sql = "select msg_no from a_dfd_a25_compute where acnum= ? and msg_no < ?";
		String esn = "";
		
		if(esnSeq == 2){//查右发
			sql += " and esn_2=? and deta_oiq1_fwd_s = 2";
			esn = computedVo.getEsn_1();
		}else{//查左发
			sql += " and esn_1=? and deta_oiq1_fwd_s = 1";
			esn = computedVo.getEsn_2();
		}
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql,computedVo.getAcnum(),computedVo.getMsgno(),esn);
		if(vos.length > 0)
			return vos[0].getStringValue("msg_no");
		else{
			return "1";//未查到数据，以1 为起点
		}
	}
	
	
	//获取当前报文的前一报文
	public HashVO getPreA25Vo(A25ComputeVo computedVo) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT L.MSG_NO,L.ACNUM,L.DATE_UTC,L.ESN_1,L.OIQ1_Z3,L.CF_EHRS");
		sb.append(",L.DETA_OIQ1_FWD,L.DETA_OIQ1_FWD_S,L.DETA_OIQ1_FWDALARM,L.IS_OIQ1_ADDRPT,L.DETA_OIQ1_FWDRATE");
		sb.append(",L.ESN_2,L.OIQ2_Z3,L.CG_EHRS,L.DETA_OIQ2_FWD,L.DETA_OIQ2_FWD_S,L.DETA_OIQ2_FWDALARM,L.IS_OIQ2_ADDRPT,L.DETA_OIQ2_FWDRATE,H.CNT");
		sb.append(" FROM A_DFD_A25_COMPUTE L, A_DFD_HEAD H ");
		sb.append(" WHERE L.ACNUM='"+computedVo.getAcnum()+"' AND H.RPTNO='A25' and H.MSG_NO=L.MSG_NO and L.MSG_NO=");
		sb.append(" (SELECT MAX(MSG_NO) FROM A_DFD_A25_COMPUTE WHERE ACNUM='"+computedVo.getAcnum()+"' AND MSG_NO < "+computedVo.getMsgno()+")");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		
		if(vos.length > 0)
			return vos[0];
		else {
			return null;
		}
		
				
	}
	
	public HashVO getA25A27(A25ComputeVo computedVo, HashVO preVo) throws Exception{
		if (preVo ==null){
			return null;
		} else {
			StringBuilder sb = new StringBuilder("SELECT A.OILADD_ENG1, A.OILADD_ENG2 FROM A_DFD_HEAD H, A_DFD_A27_LIST A ");
			sb.append(" WHERE H.MSG_NO=A.MSG_NO AND H.RPTNO='A27' AND h.ACNUM='" + computedVo.getAcnum() + "' " +"AND ");
			sb.append(" (A.OILADD_ENG1=1 OR A.OILADD_ENG2=1) AND ");
			sb.append(" h.DATE_UTC< to_date('"+ DateUtil.getLongDate(computedVo.getDate_utc()) +"','YYYY-MM-DD hh24:mi:ss')" + " AND h.DATE_UTC >= to_date('" + DateUtil.getLongDate(preVo.getDateValue("DATE_UTC")) + "','YYYY-MM-DD hh24:mi:ss')");
			
			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
			
			if(vos.length > 0){
				return vos[0];
			}else{
				return null;
			}
		}	
		
	}	
	
}
