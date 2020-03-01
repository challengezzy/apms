package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.dataprase.vo.AcarsDfdA26Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsECVo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsEEVo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsN1_N2Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsS1_S2Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsT1_T2Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsV1_V2Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsV3_V4Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsV5Vo_A26_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsR1Vo_A26_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;


public class A26DataParseImpl_A320 extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA26Vo_A320 a26vo = praseA26Data(content,trans_time);
//		String acmodel = acarsVo.getStringValue("acmodel");		
		insertA26(msgno, a26vo);
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	protected AcarsDfdA26Vo_A320 praseA26Data(String gStrA26,String transdate) throws Exception{
		AcarsDfdA26Vo_A320 a26vo = new AcarsDfdA26Vo_A320();
		
		String[] lines = StringUtil.splitString2Array(gStrA26, "/", true);
		
		boolean isRep = false;
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
			if(line.startsWith("EC")){				
				a26vo.setEc(new AcarsECVo_A26_A320(line));
			}else if(line.startsWith("EE")){				
				a26vo.setEe(new AcarsEEVo_A26_A320(line));
			}else if(line.startsWith("N1")){				
				a26vo.setN1(new AcarsN1_N2Vo_A26_A320(line,isRep));
			}else if(line.startsWith("N2")){				
				a26vo.setN2(new AcarsN1_N2Vo_A26_A320(line,isRep));
			}else if(line.startsWith("S1")){				
				a26vo.setS1(new AcarsS1_S2Vo_A26_A320(line,isRep));
			}else if(line.startsWith("S2")){				
				a26vo.setS2(new AcarsS1_S2Vo_A26_A320(line,isRep));
			}else if(line.startsWith("T1")){				
				a26vo.setT1(new AcarsT1_T2Vo_A26_A320(line));
			}else if(line.startsWith("T2")){				
				a26vo.setT2(new AcarsT1_T2Vo_A26_A320(line));
			}else if(line.startsWith("V1")){				
				a26vo.setV1(new AcarsV1_V2Vo_A26_A320(line,isRep));
			}else if(line.startsWith("V2")){				
				a26vo.setV2(new AcarsV1_V2Vo_A26_A320(line,isRep));
			}else if(line.startsWith("V3")){				
				a26vo.setV3(new AcarsV3_V4Vo_A26_A320(line));
			}else if(line.startsWith("V4")){				
				a26vo.setV4(new AcarsV3_V4Vo_A26_A320(line));
			}else if(line.startsWith("V5")){				
				a26vo.setV5(new AcarsV5Vo_A26_A320(line,isRep));
			}else if(line.startsWith("R1")){				
				a26vo.setR1(new AcarsR1Vo_A26_A320(line));
			}
			
		}
		
		//System.out.println("hello n1 "+a14vo.getN1().getNa());
	    //System.out.println("hello s1 "+a14vo.getS1().getP2a());
	    //System.out.println("hello s1 "+a14vo.getS1().getWb());
	    //System.out.println("hello s1 "+a14vo.getS1().getPt());
	    //System.out.println("hello v1 "+a14vo.getV1().getEgtp());
	    //System.out.println("hello v1 "+a14vo.getV1().getNpa());
		return a26vo;
		
	}
	
	public void insertA26(String msgno, AcarsDfdA26Vo_A320 a26vo) throws Exception{
//		String acmodel=ac_model;
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a26_list(ID,MSG_NO,ACNUM,DATE_UTC,RPTNO,CODE,REASON," 
			    + "ESN_1,EHRS_1,ECYC_1,AP_1,"
			    + "ESN_2,EHRS_2,ECYC_2,AP_2,"
			    + "EPR_N1,EPRC_N1,EGT_N1,N1_N1,N2_N1,FF_N1,P125_N1,"
			    + "EPR_N2,EPRC_N2,EGT_N2,N1_N2,N2_N2,FF_N2,P125_N2,"
			    + "P25_S1,T25_S1,P3_S1,T3_S1,P49_S1,SVA_S1,"
			    + "P25_S2,T25_S2,P3_S2,T3_S2,P49_S2,SVA_S2,"
			    + "BAF_T1,ACC_T1,LP_T1,GLE_T1,PD_T1,TN_T1,P2_T1,T2_T1,"
			    + "BAF_T2,ACC_T2,LP_T2,GLE_T2,PD_T2,TN_T2,P2_T2,T2_T2,"
			    + "ECW1_V1,ECW2_V1,EVM_V1,OIP_V1,OIT_V1,OIQ_V1,"
			    + "ECW1_V2,ECW2_V2,EVM_V2,OIP_V2,OIT_V2,OIQ_V2,"
			    + "VB1_V3,VB2_V3,PHA_V3,OIK_480_V3,OIK_0_V3,"
			    + "VIB1_V4,VIB2_V4,PHA_V4,OIK_480_V4,OIK_0_V4,"
			    + "OIPL_V5,OITL_V5,OIQL_V5,OIQCKL_V5,"
				+ "UPDATE_DATE)" 
				+ " values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,sysdate)";

		

		AcarsECVo_A26_A320 ec = a26vo.getEc(); 
		AcarsEEVo_A26_A320 ee = a26vo.getEe();
		AcarsN1_N2Vo_A26_A320 n1 = a26vo.getN1();
		AcarsN1_N2Vo_A26_A320 n2 = a26vo.getN2();
		AcarsS1_S2Vo_A26_A320 s1 = a26vo.getS1();
		AcarsS1_S2Vo_A26_A320 s2 = a26vo.getS2();
		AcarsT1_T2Vo_A26_A320 t1 = a26vo.getT1();
		AcarsT1_T2Vo_A26_A320 t2 = a26vo.getT2();
		AcarsV1_V2Vo_A26_A320 v1 = a26vo.getV1();
		AcarsV1_V2Vo_A26_A320 v2 = a26vo.getV2();
		AcarsV3_V4Vo_A26_A320 v3 = a26vo.getV3();
		AcarsV3_V4Vo_A26_A320 v4 = a26vo.getV4();
		AcarsV5Vo_A26_A320 v5 = a26vo.getV5();
		AcarsR1Vo_A26_A320 r1 = a26vo.getR1();
		
	
		
		headParseClass.insertHeadData(msgno, headVo);
		
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),"A26",headVo.getCode(),r1.getReason(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),
				n1.getEpr(),n1.getEprc(),n1.getEgt(),n1.getN1(),n1.getN2(),n1.getFf(),n1.getP125(),
				n2.getEpr(),n2.getEprc(),n2.getEgt(),n2.getN1(),n2.getN2(),n2.getFf(),n2.getP125(),
				s1.getP25(),s1.getT25(),s1.getP3(),s1.getT3(),s1.getP49(),s1.getSva(),
				s2.getP25(),s2.getT25(),s2.getP3(),s2.getT3(),s2.getP49(),s2.getSva(),
				t1.getBaf(),t1.getAcc(),t1.getLp(),t1.getGle(),t1.getPd(),t1.getTn(),t1.getP2(),t1.getT2(),
				t2.getBaf(),t2.getAcc(),t2.getLp(),t2.getGle(),t2.getPd(),t2.getTn(),t2.getP2(),t2.getT2(),
				v1.getEcw1(),v1.getEcw2(),v1.getEvm(),v1.getOip(),v1.getOit(),v1.getOiq(),
				v2.getEcw1(),v2.getEcw2(),v2.getEvm(),v2.getOip(),v2.getOit(),v2.getOiq(),
				v3.getVb1(),v3.getVb2(),v3.getPha(),v3.getOik_480(),v3.getOik_0(),
				v4.getVb1(),v4.getVb2(),v4.getPha(),v4.getOik_480(),v4.getOik_0(),
				v5.getOipl(),v5.getOitl(),v5.getOiql(),v5.getOiqckl());
		
		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A26_A320报文[msg_no]=["+msgno+"]入库成功！");
		
		AlarmMonitorTargetVo targetVo = new AlarmMonitorTargetVo();
		targetVo.setPkValue(msgno);
		targetVo.setMsgno(msgno);
		targetVo.setDateUtc(headVo.getDateUtc());
		targetVo.setAcnum(headVo.getAcid());
		targetVo.setRptno("A26");
		targetVo.addParam(DfdVarConst.KEY_BODYVO, a26vo);
		targetVo.addParam(DfdVarConst.KEY_HEADVO, headVo);
		
		String monitorObjCode = MonitorObjConst.A26_PARSE;
		
		AlarmComputeService.getInstance().alarmObjectAdd(monitorObjCode, msgno, targetVo);
		logger.info("A26 CODE 告警成功！");
	}
	
		
}
