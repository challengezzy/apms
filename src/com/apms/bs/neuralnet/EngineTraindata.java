package com.apms.bs.neuralnet;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

public class EngineTraindata {
	protected  Logger logger = NovaLogger.getLogger(this.getClass());
	
	private  CommDMO dmo = new CommDMO(); 
	
	private static final int isValid = 1;  //有效
	private static final int sourceType = 0;  //报文分析
	
	public boolean insertEngineTraindata(String msgNo,String dateType,String explain,String user,
													String position,String rptNo,String engineType) throws Exception{
		Boolean exist = false;

		String s = "select id  from l_engine_traindata where msg_no =" + msgNo +" and position="+position ;
		
		HashVO[] vos1 = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, s.toString());
		if(vos1.length>0){
			exist = true;
			return exist;
		}
	
		String esn;
		HashVO vo;
		StringBuilder sb = new StringBuilder("SELECT  T.RPTDATE,T.MSG_NO,T.ACNUM,T.ESN_1,T.ESN_2 ");
		sb.append(" FROM a_dfd_a01cfm56_5b_list T  WHERE  T.MSG_NO = "+msgNo);
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
	    vo = vos[0];
		if(position =="1"){
//			StringBuilder sb = new StringBuilder("SELECT  T.UTCDATE,T.MSG_NO,T.ACNUM,E.ENGMODELID,T.ESN_1,T.ESN_2 ");
//			sb.append(" FROM a_dfd_a01cfm56_5b_list T,b_engine_info e WHERE b.esn_1 = e.engsn and T.MSG_NO = "+msgNo);
//			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
//		    vo = vos[0];
			esn = vo.getStringValue("ESN_1");
		}else{
//			StringBuilder sb = new StringBuilder("SELECT  T.UTCDATE,T.MSG_NO,T.ACNUM,E.ENGMODELID,T.ESN_1,T.ESN_2 ");
//			sb.append(" FROM a_dfd_a01cfm56_5b_list T,b_engine_info e WHERE b.esn_2 = e.engsn and T.MSG_NO = "+msgNo);
//			HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
//		    vo = vos[0];
			esn = vo.getStringValue("ESN_2");
		}
		
//		id, msg_no, acnum, engsn, position, engmodelid, rptdate, sourcetype, datetype, 
//		isvalid, comments, updatetime, updateuser, rpt_no, engine_type
		StringBuilder sb2 = new StringBuilder("insert into l_engine_traindata values (s_l_apu_traindata.nextval,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?)");
		Date date = vo.getDateValue("RPTDATE");
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb2.toString(),msgNo,
				vo.getStringValue("ACNUM"),esn,position,"",date,
				sourceType,dateType,isValid,explain,user,rptNo,engineType);
		dmo.commit(ApmsConst.DS_APMS);
		return exist;		
	}
	
	
	public void updateEngineTraindata(String msgNo,String dateType,String explain,String user,String position) throws Exception{
		StringBuilder sb = new StringBuilder("update  l_engine_traindata t set t.DATATYPE =" +dateType+ " , t.COMMENTS = '"
				+explain +"', t.UPDATEUSER = '" +user+ "' , t.UPDATETIME = sysdate where t.msg_no ="+msgNo + "and position ="+position );
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString());
		dmo.commit(ApmsConst.DS_APMS);
	}
	
	

}
