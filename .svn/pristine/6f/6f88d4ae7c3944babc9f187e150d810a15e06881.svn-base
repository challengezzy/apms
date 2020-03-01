package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.*;
import com.apms.bs.dataprase.vo.a33.AcarsEC_EEVo_A33_A320;
import com.apms.bs.dataprase.vo.a33.AcarsMNxVo_A33_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

public class A33DataParseImpl_A320 extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA33Vo_A320 a33vo = praseA33Data(content,trans_time);
		String acmodel = acarsVo.getStringValue("acmodel");
		
		insertA33(msgno, a33vo, acmodel);
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		AlarmMonitorTargetVo targetVo = getAlarmTargetVo();
		targetVo.addParam(DfdVarConst.KEY_BODYVO, a33vo);
		targetVo.setDevicesn(headVo.getEsn());
		//告警触发
		AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A33_PARSE, msgno, targetVo);
		
		return res;
		
	}
	
	protected AcarsDfdA33Vo_A320 praseA33Data(String gStrA33,String transdate) throws Exception{
		AcarsDfdA33Vo_A320 a33vo = new AcarsDfdA33Vo_A320();
		
		String tmpStr = gStrA33.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){				
				a33vo.setEc(new AcarsEC_EEVo_A33_A320(line));
			}else if(line.startsWith("EE")){				
				a33vo.setEe(new AcarsEC_EEVo_A33_A320(line));
			}else if(line.startsWith("M1")){				
				a33vo.setM1( getMNxVo(line) );
			}else if(line.startsWith("N1")){				
				a33vo.setN1( getMNxVo(line) );
			}else if(line.startsWith("M2")){				
				a33vo.setM2( getMNxVo(line) );
			}else if(line.startsWith("N2")){				
				a33vo.setN2( getMNxVo(line) );
			}else if(line.startsWith("M3")){				
				a33vo.setM3( getMNxVo(line) );
			}else if(line.startsWith("N3")){				
				a33vo.setN3( getMNxVo(line) );
			}else if(line.startsWith("M4")){				
				a33vo.setM4( getMNxVo(line) );
			}else if(line.startsWith("N4")){				
				a33vo.setN4( getMNxVo(line) );
			}else if(line.startsWith("M5")){				
				a33vo.setM5( getMNxVo(line) );
			}else if(line.startsWith("N5")){				
				a33vo.setN5( getMNxVo(line) );
			}else if(line.startsWith("M6")){				
				a33vo.setM6( getMNxVo(line) );
			}else if(line.startsWith("N6")){				
				a33vo.setN6( getMNxVo(line) );
			}else if(line.startsWith("M7")){				
				a33vo.setM7( getMNxVo(line) );
			}else if(line.startsWith("N7")){				
				a33vo.setN7( getMNxVo(line) );
			}else if(line.startsWith("M8")){				
				a33vo.setM8( getMNxVo(line) );
			}else if(line.startsWith("N8")){				
				a33vo.setN8( getMNxVo(line) );
			}else if(line.startsWith("M9")){				
				a33vo.setM9( getMNxVo(line) );
			}else if(line.startsWith("N9")){				
				a33vo.setN9( getMNxVo(line) );
			}
	
		}
		
		return a33vo;
		
	}
	
	/**
	 * 解析M,N行数据为对象
	 * @param line
	 * @return
	 * @throws Exception
	 */
	protected AcarsMNxVo_A33_A320 getMNxVo(String line) throws Exception{
		boolean isRep = false;
		return new AcarsMNxVo_A33_A320(line,isRep);
	}
	
	public void insertA33(String msgno, AcarsDfdA33Vo_A320 a33vo, String ac_model) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a33_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,"
				+ "ESN_1,EHRS_1,ECYC_1,"
				+ "ESN_2,EHRS_2,ECYC_2,"
				+ "UPDATE_DATE)" 
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,sysdate)";
		String insertSql1= "insert into a_dfd_a33_value_rec(ID,MSG_NO,"
			    + "ROW_STR,ROW_NUM,EPR_1,N1_1,N2_1,FF_1,EGT_1,EEC_CHA_1,EEC_CHA_EGT_1,EPR_2,N1_2,N2_2,FF_2,EGT_2,EEC_CHA_2,EEC_CHA_EGT_2,"
			    + "UPDATE_DATE)"
			    + " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		

		AcarsEC_EEVo_A33_A320 ec = a33vo.getEc();
		AcarsEC_EEVo_A33_A320 ee = a33vo.getEe();
		AcarsMNxVo_A33_A320 m1 = a33vo.getM1();
		AcarsMNxVo_A33_A320 n1 = a33vo.getN1();
		AcarsMNxVo_A33_A320 m2 = a33vo.getM2();
		AcarsMNxVo_A33_A320 n2 = a33vo.getN2();
		AcarsMNxVo_A33_A320 m3 = a33vo.getM3();
		AcarsMNxVo_A33_A320 n3 = a33vo.getN3();
		AcarsMNxVo_A33_A320 m4 = a33vo.getM4();
		AcarsMNxVo_A33_A320 n4 = a33vo.getN4();
		AcarsMNxVo_A33_A320 m5 = a33vo.getM5();
		AcarsMNxVo_A33_A320 n5 = a33vo.getN5();
		AcarsMNxVo_A33_A320 m6 = a33vo.getM6();
		AcarsMNxVo_A33_A320 n6 = a33vo.getN6();
		AcarsMNxVo_A33_A320 m7 = a33vo.getM7();
		AcarsMNxVo_A33_A320 n7 = a33vo.getN7();
		AcarsMNxVo_A33_A320 m8 = a33vo.getM8();
		AcarsMNxVo_A33_A320 n8 = a33vo.getN8();
		AcarsMNxVo_A33_A320 m9 = a33vo.getM9();
		AcarsMNxVo_A33_A320 n9 = a33vo.getN9();
		
		headParseClass.insertHeadData(msgno, headVo);
		
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc());
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,
				"M1",1,m1.getEpr(),m1.getN1(),m1.getN2(),m1.getFf(),m1.getEgt(),m1.getEec_cha(),m1.getEec_cha_egt(),n1.getEpr(),n1.getN1(),n1.getN2(),n1.getFf(),n1.getEgt(),n1.getEec_cha(),n1.getEec_cha_egt());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,
				"M2",2,m2.getEpr(),m2.getN1(),m2.getN2(),m2.getFf(),m2.getEgt(),m2.getEec_cha(),m2.getEec_cha_egt(),n2.getEpr(),n2.getN1(),n2.getN2(),n2.getFf(),n2.getEgt(),n2.getEec_cha(),n2.getEec_cha_egt());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,
				"M3",3,m3.getEpr(),m3.getN1(),m3.getN2(),m3.getFf(),m3.getEgt(),m3.getEec_cha(),m3.getEec_cha_egt(),n3.getEpr(),n3.getN1(),n3.getN2(),n3.getFf(),n3.getEgt(),n3.getEec_cha(),n3.getEec_cha_egt());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,
				"M4",4,m4.getEpr(),m4.getN1(),m4.getN2(),m4.getFf(),m4.getEgt(),m4.getEec_cha(),m4.getEec_cha_egt(),n4.getEpr(),n4.getN1(),n4.getN2(),n4.getFf(),n4.getEgt(),n4.getEec_cha(),n4.getEec_cha_egt());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,
				"M5",5,m5.getEpr(),m5.getN1(),m5.getN2(),m5.getFf(),m5.getEgt(),m5.getEec_cha(),m5.getEec_cha_egt(),n5.getEpr(),n5.getN1(),n5.getN2(),n5.getFf(),n5.getEgt(),n5.getEec_cha(),n5.getEec_cha_egt());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,
				"M6",6,m6.getEpr(),m6.getN1(),m6.getN2(),m6.getFf(),m6.getEgt(),m6.getEec_cha(),m6.getEec_cha_egt(),n6.getEpr(),n6.getN1(),n6.getN2(),n6.getFf(),n6.getEgt(),n6.getEec_cha(),n6.getEec_cha_egt());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,
				"M7",7,m7.getEpr(),m7.getN1(),m7.getN2(),m7.getFf(),m7.getEgt(),m7.getEec_cha(),m7.getEec_cha_egt(),n7.getEpr(),n7.getN1(),n7.getN2(),n7.getFf(),n7.getEgt(),n7.getEec_cha(),n7.getEec_cha_egt());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,
				"M8",8,m8.getEpr(),m8.getN1(),m8.getN2(),m8.getFf(),m8.getEgt(),m8.getEec_cha(),m8.getEec_cha_egt(),n8.getEpr(),n8.getN1(),n8.getN2(),n8.getFf(),n8.getEgt(),n8.getEec_cha(),n8.getEec_cha_egt());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,
				"M9",9,m9.getEpr(),m9.getN1(),m9.getN2(),m9.getFf(),m9.getEgt(),m9.getEec_cha(),m9.getEec_cha_egt(),n9.getEpr(),n9.getN1(),n9.getN2(),n9.getFf(),n9.getEgt(),n9.getEec_cha(),n9.getEec_cha_egt());
				
		dmo.commit(ApmsConst.DS_APMS);
		
	}
		

}
