package com.apms.bs.neuralnet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import com.apms.ApmsConst;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

public class ApuTraindata {
	
	protected  Logger logger = NovaLogger.getLogger(this.getClass());
	
	private  CommDMO dmo = new CommDMO(); 
	
	private static final int isValid = 1;  //有效
	private static final int sourceType = 0;  //报文分析
	
	public boolean insertApuTraindata(String msgNo,String dateType,String explain,String user) throws Exception{
		Boolean exist = false;

		String s = "select id  from l_apu_traindata where msg_no =" + msgNo;
		HashVO[] vos1 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, s.toString());
		if(vos1.length>0){
			exist = true;
			return exist;
		}

		StringBuilder sb = new StringBuilder("SELECT  T.UTCDATE,T.MSG_NO,T.ACNUM,T.ASN,P.Apumodelid ");
		sb.append(" FROM A_DFD_A13_COMPUTE T,B_APU P WHERE T.ASN = P.APUSN AND T.MSG_NO = "+msgNo);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		HashVO vo = vos[0];
		
		StringBuilder sb2 = new StringBuilder("insert into l_apu_traindata values (s_l_apu_traindata.nextval,?,?,?,?,?,?,?,?,?,sysdate,?)");
		Date date = DateUtil.StringToDate(vo.getStringValue("UTCDATE"),"yyyy-MM-dd hh:mm:ss");
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb2.toString(),vo.getDoubleValue("MSG_NO"),
				vo.getDoubleValue("ASN"),vo.getStringValue("ACNUM"), date,
				vo.getDoubleValue("Apumodelid"),isValid,sourceType,dateType,explain,user);
		dmo.commit(ApmsConst.DS_APMS);
		return exist;		
	}
	
	
	public void updateApuTraindata(String msgNo,String dateType,String explain,String user) throws Exception{
		StringBuilder sb = new StringBuilder("update  l_apu_traindata t set t.DATATYPE =" +dateType+ " , t.COMMENTS = '"
				+explain +"', t.UPDATEUSER = '" +user+ "' , t.UPDATETIME = sysdate where t.msg_no ="+msgNo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString());
		dmo.commit(ApmsConst.DS_APMS);
	}
	
	
}
