package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.datacompute.vo.A34ParsedVo;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.*;
import com.apms.bs.dataprase.vo.a34.AcarsEC_EEVo_A34_A320;
import com.apms.bs.dataprase.vo.a34.AcarsS135Vo_A34_A320;
import com.apms.bs.dataprase.vo.a34.AcarsS246Vo_A34_A320;
import com.apms.bs.dataprase.vo.a34.AcarsS7Vo_A34_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

public class A34DataParseImpl_A320 extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		// TODO Auto-generated method stub
		AcarsDfdA34Vo_A320 a34vo = praseA34Data(content,trans_time);
		//String acmodel = acarsVo.getStringValue("acmodel");
		insertA34(msgno, a34vo);
		A34ParsedVo a34parsedVo=new A34ParsedVo();
		AlarmMonitorTargetVo targetVo = getAlarmTargetVo();
		a34parsedVo.setAcnum(headVo.getAcid());
		a34parsedVo.setDate_utc(headVo.getDateUtc());
		a34parsedVo.setWav1_tmr_s7(a34vo.getS7().getWav1_tmr());
		a34parsedVo.setWav2_tmr_s7(a34vo.getS7().getWav2_tmr());
		a34parsedVo.setEsn_1(a34vo.getEc().getEsn());
		a34parsedVo.setEsn_1(a34vo.getEe().getEsn());
		targetVo.addParam("COMPUTEDVO", a34parsedVo);
		//告警触发
		AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A34_COMPUTE, msgno, targetVo);
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	private AcarsDfdA34Vo_A320 praseA34Data(String gStrA34,String trans_time) throws Exception{
		AcarsDfdA34Vo_A320 a34vo = new AcarsDfdA34Vo_A320();
		
//		AcarsS135Vo_A34_A320 s135= new AcarsS135Vo_A34_A320();
//		s135.setTransdate(trans_time);
		
		
		String tmpStr = gStrA34.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			
			if(line.startsWith("EC")){				
				a34vo.setEc(new AcarsEC_EEVo_A34_A320(line));
			}else if(line.startsWith("EE")){				
				a34vo.setEe(new AcarsEC_EEVo_A34_A320(line));
			}else if(line.startsWith("S1")){				
				a34vo.setS1(new AcarsS135Vo_A34_A320(line,trans_time));
			}else if(line.startsWith("S2")){				
				a34vo.setS2(new AcarsS246Vo_A34_A320(line));
			}else if(line.startsWith("S3")){				
				a34vo.setS3(new AcarsS135Vo_A34_A320(line,trans_time));
			}else if(line.startsWith("S4")){				
				a34vo.setS4(new AcarsS246Vo_A34_A320(line));
			}else if(line.startsWith("S5")){				
				a34vo.setS5(new AcarsS135Vo_A34_A320(line,trans_time));
			}else if(line.startsWith("S6")){				
				a34vo.setS6(new AcarsS246Vo_A34_A320(line));
			}else if(line.startsWith("S7")){				
				a34vo.setS7(new AcarsS7Vo_A34_A320(line,trans_time));
			}
			
		}
		
	    //System.out.println("hello v1 "+a14vo.getV1().getNpa());
		return a34vo;
		
	}
	
	public void insertA34(String msgno, AcarsDfdA34Vo_A320 a34vo) throws Exception{
		//String acmodel=ac_model;
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a34_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,"
			    + "ESN_1,EHRS_1,ECYC_1,"
			    + "ESN_2,EHRS_2,ECYC_2," 
			    + "PD_S1,TPO_S1,WAV_S1,"
			    + "PD_S2,TPO_S2,WAV_S2,DATE_S1,"
			    + "PD_S3,TPO_S3,WAV_S3,"
			    + "PD_S4,TPO_S4,WAV_S4,DATE_S3,"
			    + "PD_S5,TPO_S5,WAV_S5,"
			    + "PD_S6,TPO_S6,WAV_S6,DATE_S5,"
			    + "WAV1_TMR_S7,WAV1_TMR_COR_S7,WAV2_TMR_S7,WAV2_TMR_COR_S7,DATE_S7,"
				+ "UPDATE_DATE)" 
				+ " values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,?,?,?,?,?,?," 
				+ "?,?,?,?,?,?,?,?,?,?," 
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,sysdate)";
		
		

		AcarsEC_EEVo_A34_A320 ec = a34vo.getEc();
		AcarsEC_EEVo_A34_A320 ee = a34vo.getEe();
		AcarsS135Vo_A34_A320 s1 = a34vo.getS1();
		AcarsS246Vo_A34_A320 s2 = a34vo.getS2();
		AcarsS135Vo_A34_A320 s3 = a34vo.getS3();
		AcarsS246Vo_A34_A320 s4 = a34vo.getS4();
		AcarsS135Vo_A34_A320 s5 = a34vo.getS5();
		AcarsS246Vo_A34_A320 s6 = a34vo.getS6();
		AcarsS7Vo_A34_A320 s7 = a34vo.getS7();
		
		double wav1 = Double.valueOf(s7.getWav1_tmr());
		double pd1 = Double.valueOf(s5.getPd());
		
		//WAV_TMR_COR 修正公式更改WAV_TMR_COR=WAV_TMR +(-0.069*(PD)+2.07),原 " (30/PD)*(WAV_TMR-0.0425)+0.0425" 弃用,zhangzy 2013/7/18
		
		double wav1_tmr_cor_s7 = wav1 +(-0.069*(pd1)+2.07) ; //(30/pd1)*(wav1-0.0425)+0.0425;
		double wav2 = Double.valueOf(s7.getWav2_tmr());
		double pd2 = Double.valueOf(s6.getPd());
		double wav2_tmr_cor_s7 = wav2 +(-0.069*(pd2)+2.07);
		
		headParseClass.insertHeadData(msgno, headVo);
		
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),
				s1.getPd(),s1.getTpo(),s1.getWav(),
				s2.getPd(),s2.getTpo(),s2.getWav(),s1.getDateTime(),
				s3.getPd(),s3.getTpo(),s3.getWav(),
				s4.getPd(),s4.getTpo(),s4.getWav(),s3.getDateTime(),
				s5.getPd(),s5.getTpo(),s5.getWav(),
				s6.getPd(),s6.getTpo(),s6.getWav(),s5.getDateTime(),
				s7.getWav1_tmr(),wav1_tmr_cor_s7,s7.getWav2_tmr(),wav2_tmr_cor_s7,s7.getDateTime());
		
		
		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A34_A320报文[msg_no]=["+msgno+"]入库成功！");
		
	}
		
		

}
