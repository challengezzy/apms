package com.apms.bs.datacompute;

import java.util.Date;

import smartx.framework.common.vo.HashVO;

import com.apms.bs.datacompute.vo.A34ParsedVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.vo.ApmsVarConst;

public class A34DataComputeService {
	
	/**
	 * 根据当前和历史N条记录，计算报文数据
	 * @param curVo 要计算的报文数据
	 * @throws Exception
	 */
	public A34ParsedVo compute34ByHistoryVos(HashVO curVo) throws Exception{
		A34ParsedVo computedVo = new A34ParsedVo();
		
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		Date date_utc = curVo.getDateValue("DATE_UTC");

		String wav1_tmr_cor_s7;
		String wav2_tmr_cor_s7;
		double ehrs_1;
		double ehrs_2;
		String esn_1;
		String esn_2;
		
		esn_1 = curVo.getStringValue("ESN_1");
		esn_2 = curVo.getStringValue("ESN_2");
		wav1_tmr_cor_s7 = curVo.getStringValue("WAV1_TMR_COR_S7");
		wav2_tmr_cor_s7 = curVo.getStringValue("WAV2_TMR_COR_S7");
		ehrs_1 = curVo.getDoubleValue("EHRS_1");
		ehrs_2 = curVo.getDoubleValue("EHRS_2");
		
		computedVo.setEsn_1(esn_1);
		computedVo.setEsn_2(esn_2);
		computedVo.setWav1_tmr_s7(wav1_tmr_cor_s7);
		computedVo.setWav2_tmr_s7(wav2_tmr_cor_s7);
		
		String rptno = ApmsVarConst.RPTNO_A34;
		
		computedVo.setId(curVo.getStringValue("ID"));
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setDate_utc(date_utc);
		
		String changeMsgno = "1";// TODO 人工设置的起始点
			
		DfdFieldCompute fieldComp = new DfdFieldCompute();
		
		//对WAV_TMR_COR_S7做5点均，方差，K检验，T检验 根据运行小时
		String x_fname = "EHRS_1";//对运行时间进行回归
		double x_fvalue = curVo.getDoubleValue(x_fname);
		String f_name = "WAV1_TMR_COR_S7";
		double f_value = curVo.getDoubleValue("WAV1_TMR_COR_S7");
		FieldComputeVo fieldVo_cor1 = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_cor1.setTimes(1);
		fieldVo_cor1.setEstimateLimit(true);
	 	fieldComp.fieldRegressionCompute(curVo, fieldVo_cor1, changeMsgno,false,true,false,true);
	 	
	 	x_fname = "EHRS_2";//对运行时间进行回归
		x_fvalue = curVo.getDoubleValue(x_fname);
		f_name = "WAV2_TMR_COR_S7";
		f_value = curVo.getDoubleValue("WAV2_TMR_COR_S7");
		FieldComputeVo fieldVo_cor2 = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fname,x_fvalue);
		fieldVo_cor2.setTimes(1);
		fieldVo_cor2.setEstimateLimit(true);
	 	fieldComp.fieldRegressionCompute(curVo, fieldVo_cor2, changeMsgno,false,true,false,true);
				
		
	 	//数据入库
		fieldVo_cor1.insertToTable();
		fieldVo_cor2.insertToTable();
		
		return computedVo;
	}
	
	
}
