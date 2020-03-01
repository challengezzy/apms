package com.apms.bs.datacompute;

import java.util.Date;
import smartx.framework.common.vo.HashVO;
import com.apms.bs.datacompute.vo.A38ComputeVo;
import com.apms.bs.datacompute.vo.FieldComputeVo;
import com.apms.vo.ApmsVarConst;

public class A38DataComputeService {
	
	/**
	 * 根据当前和历史N条记录，计算报文数据
	 * @param curVo 要计算的报文数据
	 * @throws Exception
	 */
	public A38ComputeVo computeA38ByHistoryVos(HashVO curVo) throws Exception{
		A38ComputeVo computedVo = new A38ComputeVo();
		
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		String asn = curVo.getStringValue("ASN");
		Date date_utc = curVo.getDateValue("DATE_UTC");

		double ct5atp_n1 = curVo.getDoubleValue("CT5ATP_N1");
		double cptatp_n1 = curVo.getDoubleValue("CPTATP_N1");
		double cwfatp_n1 = curVo.getDoubleValue("CWFATP_N1");
		double igvatp_n1 = curVo.getDoubleValue("IGVATP_N1");
		double bdtmax_n1 = curVo.getDoubleValue("BDTMAX_N1");
		
		double ct5atp_n2 = curVo.getDoubleValue("CT5ATP_N2");
		double cptatp_n2 = curVo.getDoubleValue("CPTATP_N2");
		double cwfatp_n2 = curVo.getDoubleValue("CWFATP_N2");
		double igvatp_n2 = curVo.getDoubleValue("IGVATP_N2");
		double bdtmax_n2 = curVo.getDoubleValue("BDTMAX_N2");
		
				
		String rptno = ApmsVarConst.RPTNO_A38;
		
		computedVo.setId(curVo.getStringValue("ID"));
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setAsn(asn);
		computedVo.setDate_utc(date_utc);
		if(ct5atp_n1>=ct5atp_n2){
			computedVo.setCt5atp(ct5atp_n1);
			computedVo.setCptatp(cptatp_n1);
			computedVo.setCwfatp(cwfatp_n1);
			computedVo.setIgvatp(igvatp_n1);
			computedVo.setBdtmax(bdtmax_n1);
		}else{
			computedVo.setCt5atp(ct5atp_n2);
			computedVo.setCptatp(cptatp_n2);
			computedVo.setCwfatp(cwfatp_n2);
			computedVo.setIgvatp(igvatp_n2);
			computedVo.setBdtmax(bdtmax_n2);
		}
	 	
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
		
		String f_name = "CT5ATP";
		double f_value = computedVo.getCt5atp();
		FieldComputeVo varCt5atpVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varCt5atpVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varCt5atpVo.insertToTable();
		
		f_name = "CPTATP";
		f_value = computedVo.getCptatp();
		FieldComputeVo varCptatpVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varCptatpVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varCptatpVo.insertToTable();
		
		f_name = "CWFATP";
		f_value = computedVo.getCwfatp();
		FieldComputeVo varCwfatpVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varCwfatpVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varCwfatpVo.insertToTable();
		
		f_name = "IGVATP";
		f_value = computedVo.getIgvatp();
		FieldComputeVo varIgvatpVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varIgvatpVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varIgvatpVo.insertToTable();
		
		f_name = "BDTMAX";
		f_value = computedVo.getBdtmax();
		FieldComputeVo varBdtmaxVo = new FieldComputeVo(rptno, msgno, acnum, f_name, f_value,x_fieldname,x_value);
		fieldComp.fieldRegressionCompute(curVo, varBdtmaxVo, changeMsgno, isChangePoint, isTTest,true,isRankTTest);
		varBdtmaxVo.insertToTable();

		
		
		computedVo.setFieldVo_ct5atp(varCt5atpVo);
		computedVo.setFieldVo_cptatp(varCptatpVo);
		computedVo.setFieldVo_cwfatp(varCptatpVo);
		computedVo.setFieldVo_igvatp(varIgvatpVo);
		computedVo.setFieldVo_bdtmax(varBdtmaxVo);
		
		computedVo.insertToTable();
		return computedVo;

	}
	
}
