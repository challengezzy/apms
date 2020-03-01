package com.apms.bs.datacompute;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.datacompute.vo.A27ComputeVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.vo.ApmsVarConst;

public class A27DataComputeService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	/**
	 * 根据当前和历史N条记录，计算报文数据
	 * @param curVo 要计算的报文数据
	 * @throws Exception
	 */
	public A27ComputeVo compute27ByHistoryVos(HashVO curVo) throws Exception{
		A27ComputeVo computedVo = new A27ComputeVo();
		
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		Date date_utc = curVo.getDateValue("DATE_UTC");
		int oiladd1 = 0;
		int oiladd2 = 0;
		double cl_calga;
		double cl_calair;
		double cr_calga;
		double cr_calair;
		double cl_oiqav;
		double cr_oiqav;
		String esn_1;
		String esn_2;
		oiladd1 = curVo.getIntegerValue("OILADD_ENG1");
		oiladd2 = curVo.getIntegerValue("OILADD_ENG2");
		if (oiladd1==1){                               //判断 是否左发滑油添加
			esn_1 = curVo.getStringValue("ESN_1");
			cl_calga = curVo.getDoubleValue("CAL_GA_ENG1");
			cl_calair = curVo.getDoubleValue("CAL_AIR_ENG1");
			cl_oiqav = curVo.getDoubleValue("OIQAV_ENG1");

			computedVo.setOiladd_1(1);
			computedVo.setEsn_1(esn_1);
			computedVo.setCl_calga(cl_calga);
			computedVo.setCl_calair(cl_calair);
			computedVo.setCl_oiqav(cl_oiqav);
			

		}
		if (oiladd2==1){                               //判断 是否右发滑油添加
			esn_2 = curVo.getStringValue("ESN_2");
			cr_calga = curVo.getDoubleValue("CAL_GA_ENG2");
			cr_calair = curVo.getDoubleValue("CAL_AIR_ENG2");
			cr_oiqav = curVo.getDoubleValue("OIQAV_ENG2");
			
			computedVo.setOiladd_2(1);
			computedVo.setEsn_2(esn_2);
			computedVo.setCr_calga(cr_calga);
			computedVo.setCr_calair(cr_calair);
			computedVo.setCr_oiqav(cr_oiqav);

		}
		
		String rptno = ApmsVarConst.RPTNO_A27;
		
		computedVo.setId(curVo.getStringValue("ID"));
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setDate_utc(date_utc);
		
		if (oiladd1==1 || oiladd2==1){
		 	computedVo.insertToTable();

		}
		
		String changeMsgno = "1";
		
		//获取该报文的前一报文信息		
	
		DfdFieldCompute fieldComp = new DfdFieldCompute();
		//对油量做方差计算、样本T检验
		if (oiladd1==1){
			String f_name = "CAL_GA_ENG1";
			double f_value = computedVo.getCl_calga();
			FieldComputeVo RateGA1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
			fieldComp.fieldRegressionCompute(curVo, RateGA1Vo, changeMsgno, false, true);
			
			f_name = "CAL_AIR_ENG1";
			f_value = computedVo.getCl_calair();
			FieldComputeVo RateAIR1Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
			fieldComp.fieldRegressionCompute(curVo, RateAIR1Vo, changeMsgno, false, true);
			
			RateGA1Vo.insertToTable();
			RateAIR1Vo.insertToTable();
		 	
		}
		
		if (oiladd2==1){
			String f_name = "CAL_GA_ENG2";
			double f_value = computedVo.getCr_calga();
			FieldComputeVo RateGA2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
			fieldComp.fieldRegressionCompute(curVo, RateGA2Vo, changeMsgno, false, true);
			
			f_name = "CAL_AIR_ENG2";
			f_value = computedVo.getCr_calair();
			FieldComputeVo RateAIR2Vo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value);
			fieldComp.fieldRegressionCompute(curVo, RateAIR2Vo, changeMsgno, false, true);
			
			//数据入库
		 	RateGA2Vo.insertToTable();
			RateAIR2Vo.insertToTable();
		 	
		}
		
		logger.debug("msgno=["+msgno+"]27号报文计算完成");
		return computedVo;

		
	}
}
