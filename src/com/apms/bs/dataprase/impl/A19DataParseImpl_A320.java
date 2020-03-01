package com.apms.bs.dataprase.impl;

import java.util.ArrayList;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.*;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsECVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsEEVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsExNxVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsSxTxVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsVxVo_A19_A320;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsXxVo_A19_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;


public class A19DataParseImpl_A320 extends ReportContentParseClass{
	
	ArrayList<AcarsExNxVo_A19_A320> enVoList;
	ArrayList<AcarsSxTxVo_A19_A320> stVoList;
	ArrayList<AcarsVxVo_A19_A320> vVoList;
	ArrayList<AcarsXxVo_A19_A320> xVoList;
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		AcarsDfdA19Vo_A320 a19vo = praseA19Data(content,trans_time);
		String acmodel = acarsVo.getStringValue("acmodel");
		
		insertA19(msgno, a19vo, acmodel);
		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		AlarmMonitorTargetVo targetVo = getAlarmTargetVo();
		targetVo.addParam(DfdVarConst.KEY_BODYVO, a19vo);
		targetVo.setDevicesn(headVo.getEsn());
		//告警触发
		AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A19_PARSE, msgno, targetVo);
		
		
		
		return res;
		
	}
	
	private AcarsDfdA19Vo_A320 praseA19Data(String gStrA19,String transdate) throws Exception{
		AcarsDfdA19Vo_A320 a19vo = new AcarsDfdA19Vo_A320();
		
		enVoList = new ArrayList<AcarsExNxVo_A19_A320>();
		stVoList = new ArrayList<AcarsSxTxVo_A19_A320>();
		vVoList = new ArrayList<AcarsVxVo_A19_A320>();
		xVoList = new ArrayList<AcarsXxVo_A19_A320>();
		
		String tmpStr = gStrA19.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		AcarsExNxVo_A19_A320 enVo;
		AcarsSxTxVo_A19_A320 stVo;
		AcarsVxVo_A19_A320 vVo;
		AcarsXxVo_A19_A320 xVo;
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			
			if(line.startsWith("EC")){				
				a19vo.setEc(new AcarsECVo_A19_A320(line));
			}else if(line.startsWith("EE")){				
				a19vo.setEe(new AcarsEEVo_A19_A320(line));
			}else if(line.startsWith("E1")){
				enVo = new AcarsExNxVo_A19_A320(line,"E1");
				a19vo.setE1(enVo);
				enVoList.add(enVo);			
			}else if(line.startsWith("E2")){				
				enVo = new AcarsExNxVo_A19_A320(line,"E2");
				a19vo.setE2(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("E3")){				
				enVo = new AcarsExNxVo_A19_A320(line,"E3");
				a19vo.setE3(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("E4")){				
				enVo = new AcarsExNxVo_A19_A320(line,"E4");
				a19vo.setE4(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("E5")){				
				enVo = new AcarsExNxVo_A19_A320(line,"E5");
				a19vo.setE5(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("E6")){				
				enVo = new AcarsExNxVo_A19_A320(line,"E6");
				a19vo.setE6(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("E7")){				
				enVo = new AcarsExNxVo_A19_A320(line,"E7");
				a19vo.setE7(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("E8")){				
				enVo = new AcarsExNxVo_A19_A320(line,"E8");
				a19vo.setE8(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("E9")){				
				enVo = new AcarsExNxVo_A19_A320(line,"E9");
				a19vo.setE9(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("E0")){				
				enVo = new AcarsExNxVo_A19_A320(line,"E0");
				a19vo.setE0(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("1E")){				
				enVo = new AcarsExNxVo_A19_A320(line,"1E");
				a19vo.set_1e(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("2E")){				
				enVo = new AcarsExNxVo_A19_A320(line,"2E");
				a19vo.set_2e(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N1")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N1");
				a19vo.setN1(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N2")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N2");
				a19vo.setE2(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N3")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N3");
				a19vo.setN3(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N4")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N4");
				a19vo.setN4(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N5")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N5");
				a19vo.setN5(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N6")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N6");
				a19vo.setN6(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N7")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N7");
				a19vo.setN7(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N8")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N8");
				a19vo.setN8(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N9")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N9");
				a19vo.setN9(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("N0")){				
				enVo = new AcarsExNxVo_A19_A320(line,"N0");
				a19vo.setN0(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("1N")){				
				enVo = new AcarsExNxVo_A19_A320(line,"1N");
				a19vo.set_1n(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("2N")){				
				enVo = new AcarsExNxVo_A19_A320(line,"2N");
				a19vo.set_2n(enVo);
				enVoList.add(enVo);
			}else if(line.startsWith("S1")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S1");
				a19vo.setS1(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("S2")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S2");
				a19vo.setS2(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("S3")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S3");
				a19vo.setS3(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("S4")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S4");
				a19vo.setS4(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("S5")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S5");
				a19vo.setS5(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("S6")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S6");
				a19vo.setS6(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("S7")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S7");
				a19vo.setS7(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("S8")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S8");
				a19vo.setS8(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("S9")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S9");
				a19vo.setS9(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("S0")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"S0");
				a19vo.setS0(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("1S")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"1S");
				a19vo.set_1s(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("2S")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"2S");
				a19vo.set_2s(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T1")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T1");
				a19vo.setT1(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T2")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T2");
				a19vo.setT2(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T3")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T3");
				a19vo.setT3(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T4")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T4");
				a19vo.setT4(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T5")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T5");
				a19vo.setT5(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T6")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T6");
				a19vo.setT6(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T7")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T7");
				a19vo.setT7(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T8")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T8");
				a19vo.setT8(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T9")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T9");
				a19vo.setT9(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("T0")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"T0");
				a19vo.setT0(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("1T")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"2T");
				a19vo.set_1t(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("2T")){				
				stVo = new AcarsSxTxVo_A19_A320(line,"2T");
				a19vo.set_2t(stVo);
				stVoList.add(stVo);
			}else if(line.startsWith("V1")){				
				vVo = new AcarsVxVo_A19_A320(line,"V1");
				a19vo.setV1(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("V2")){				
				vVo = new AcarsVxVo_A19_A320(line,"V2");
				a19vo.setV2(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("V3")){				
				vVo = new AcarsVxVo_A19_A320(line,"V3");
				a19vo.setV3(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("V4")){				
				vVo = new AcarsVxVo_A19_A320(line,"V4");
				a19vo.setV4(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("V5")){				
				vVo = new AcarsVxVo_A19_A320(line,"V5");
				a19vo.setV5(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("V6")){				
				vVo = new AcarsVxVo_A19_A320(line,"V6");
				a19vo.setV6(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("V7")){				
				vVo = new AcarsVxVo_A19_A320(line,"V7");
				a19vo.setV7(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("V8")){				
				vVo = new AcarsVxVo_A19_A320(line,"V8");
				a19vo.setV8(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("V9")){				
				vVo = new AcarsVxVo_A19_A320(line,"V9");
				a19vo.setV9(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("V0")){				
				vVo = new AcarsVxVo_A19_A320(line,"V0");
				a19vo.setV0(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("1V")){				
				vVo = new AcarsVxVo_A19_A320(line,"1V");
				a19vo.set_1v(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("2V")){				
				vVo = new AcarsVxVo_A19_A320(line,"2V");
				a19vo.set_2v(vVo);
				vVoList.add(vVo);
			}else if(line.startsWith("X1")){				
				xVo = new AcarsXxVo_A19_A320(line,"X1");
				a19vo.setX1(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("X2")){				
				xVo = new AcarsXxVo_A19_A320(line,"X2");
				a19vo.setX2(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("X3")){				
				xVo = new AcarsXxVo_A19_A320(line,"X3");
				a19vo.setX3(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("X4")){				
				xVo = new AcarsXxVo_A19_A320(line,"X4");
				a19vo.setX4(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("X5")){				
				xVo = new AcarsXxVo_A19_A320(line,"X5");
				a19vo.setX5(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("X6")){				
				xVo = new AcarsXxVo_A19_A320(line,"X6");
				a19vo.setX6(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("X7")){				
				xVo = new AcarsXxVo_A19_A320(line,"X7");
				a19vo.setX7(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("X8")){				
				xVo = new AcarsXxVo_A19_A320(line,"X8");
				a19vo.setX8(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("X9")){				
				xVo = new AcarsXxVo_A19_A320(line,"X9");
				a19vo.setX9(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("X0")){				
				xVo = new AcarsXxVo_A19_A320(line,"X0");
				a19vo.setX0(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("1X")){				
				xVo = new AcarsXxVo_A19_A320(line,"1X");
				a19vo.set_1x(xVo);
				xVoList.add(xVo);
			}else if(line.startsWith("2X")){				
				xVo = new AcarsXxVo_A19_A320(line,"2X");
				a19vo.set_2x(xVo);
				xVoList.add(xVo);
			}
			
		}
		
		//System.out.println("hello n1 "+a14vo.getN1().getNa());
	    //System.out.println("hello s1 "+a14vo.getS1().getP2a());
	    //System.out.println("hello s1 "+a14vo.getS1().getWb());
	    //System.out.println("hello s1 "+a14vo.getS1().getPt());
	    //System.out.println("hello v1 "+a14vo.getV1().getEgtp());
	    //System.out.println("hello v1 "+a14vo.getV1().getNpa());
		return a19vo;
		
	}
	
	public void insertA19(String msgno, AcarsDfdA19Vo_A320 a19vo, String acmodel) throws Exception{
		//String acmodel=ac_model;
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a19_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
			+ "ESN_1,EHRS_1,ECYC_1,AP_1,Y1_1,NL_1,"
			+ "ESN_2,EHRS_2,ECYC_2,AP_2,"
			+ "UPDATE_DATE)" 
			+ " values(S_A_DFD_HEAD.nextval," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,sysdate)";

		AcarsECVo_A19_A320 ec = a19vo.getEc(); 
		AcarsEEVo_A19_A320 ee = a19vo.getEe();
		
	
		headParseClass.insertHeadData(msgno, headVo);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),ec.getY1(),ec.getNl(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp());
		

		
		for(int i=0;i<enVoList.size();i++){
			AcarsExNxVo_A19_A320 en_12 = enVoList.get(i);
			en_12.insertToTable(msgno,headVo,i);
		}
		
		for(int i=0;i<stVoList.size();i++){
			AcarsSxTxVo_A19_A320 st_12 = stVoList.get(i);
			st_12.insertToTable(msgno,headVo,i);
		}
		
		for(int i=0;i<vVoList.size();i++){
			AcarsVxVo_A19_A320 v_12= vVoList.get(i);
			v_12.insertToTable(msgno,headVo,i);
		}
		
		for(int i=0;i<xVoList.size();i++){
			AcarsXxVo_A19_A320 x_12= xVoList.get(i);
			x_12.insertToTable(msgno,headVo,i,acmodel,rptno);
		}

		

		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A19_A320报文[msg_no]=["+msgno+"]入库成功！");
		
	}
		
		

}
