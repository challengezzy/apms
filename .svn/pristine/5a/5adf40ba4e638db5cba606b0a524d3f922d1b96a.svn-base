package com.apms.bs.neuralnet;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

public class ApuDiagnoseresultData {
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO(); 
	private static final int correctFlag = 1;  //修正标识 已修正
	private static final int sourceType = 1;  //样本来源 诊断修正
	private static final int isValid = 1;  //有效

	public String apuDiagnoseresultAddTraindata(String msg_no, String datatype_old, String datetype_new,
			String acnum, String dateStr, String asn, String apumodelid, String comments, String user) throws Exception {
		
		
		Date date = DateUtil.StringToDate(dateStr,"yyyy-MM-dd hh:mm:ss");
		comments = comments ==null?"":comments;
		String flag = "0";
		if(datatype_old != datetype_new){
			String s = "select id  from l_apu_traindata where msg_no =" + msg_no;
			HashVO[] vos1 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, s.toString());
			if(vos1.length>0){
				StringBuilder sb = new StringBuilder("update  l_apu_traindata t set t.SOURCETYPE =" + sourceType + ",t.datatype =" + datetype_new + " where t.msg_no ="+msg_no);
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString());
				dmo.commit(ApmsConst.DS_APMS);
				flag ="1";

			}else{
				StringBuilder sb = new StringBuilder("insert into l_apu_traindata values (s_l_apu_traindata.nextval,?,?,?,?,?,?,?,?,?,sysdate,?)");

				dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(),msg_no,
						asn,acnum,date,
						apumodelid,isValid,sourceType,datetype_new,comments,user);
				dmo.commit(ApmsConst.DS_APMS);
				flag ="2";
			}
			
			
			StringBuilder sb = new StringBuilder("update  L_APU_DIAGNOSERESULT t set t.CORRECTFLAG =" + correctFlag + "where t.msg_no ="+msg_no);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString());
			dmo.commit(ApmsConst.DS_APMS);

		}
		return flag;
		
	}
}
