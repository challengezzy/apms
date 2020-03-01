package com.apms.bs.dataprase.impl;

import com.apms.ApmsConst;
import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA15Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a15a16.AcarsE1Vo_A15_A320;
import com.apms.bs.dataprase.vo.a15a16.AcarsEC_EEVo_A15_A320;
import com.apms.bs.dataprase.vo.a15a16.AcarsST12Vo_A15_A320;
import com.apms.bs.dataprase.vo.a15a16.AcarsST34Vo_A15_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

public class A15DataParseImpl extends ReportContentParseClass{

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA15Vo_A320 a15vo = praseA15Data(content,trans_time);
		a15vo.setHead(headVo);
		
		//数据入库
		insertA15(msgno, a15vo);
		
		AlarmMonitorTargetVo targetVo = getAlarmTargetVo();
		targetVo.addParam(DfdVarConst.KEY_BODYVO, a15vo);
		//告警触发
		AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A15_PARSE, msgno, targetVo);
		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);		
		return res;
		
	}
	
	public AcarsDfdA15Vo_A320 praseA15Data(String gStr,String transdate) throws Exception{
		AcarsDfdA15Vo_A320 a15vo = new AcarsDfdA15Vo_A320();
		
		String tmpStr = gStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){				
				a15vo.setEc(new AcarsEC_EEVo_A15_A320(line));
			}else if(line.startsWith("EE")){				
				a15vo.setEe(new AcarsEC_EEVo_A15_A320(line));
			}else if(line.startsWith("E1")){				
				a15vo.setE1(new AcarsE1Vo_A15_A320(line));
			}else if(line.startsWith("S1")){				
				a15vo.setS1(new AcarsST12Vo_A15_A320(line));
			}else if(line.startsWith("S2")){				
				a15vo.setS2(new AcarsST12Vo_A15_A320(line));
			}else if(line.startsWith("S3")){				
				a15vo.setS3(new AcarsST34Vo_A15_A320(line));
			}else if(line.startsWith("S4")){				
				a15vo.setS4(new AcarsST34Vo_A15_A320(line));
			}else if(line.startsWith("T1")){				
				a15vo.setT1(new AcarsST12Vo_A15_A320(line));
			}else if(line.startsWith("T2")){				
				a15vo.setT2(new AcarsST12Vo_A15_A320(line));
			}else if(line.startsWith("T3")){				
				a15vo.setT3(new AcarsST34Vo_A15_A320(line));
			}else if(line.startsWith("T4")){				
				a15vo.setT4(new AcarsST34Vo_A15_A320(line));
			}
		}
		
		return a15vo;
		
	}
	
	/**
	 * A23报文解析内容写入a_dfd_a23_list
	 * @param msgno
	 * @param a23vo
	 */
	public void insertA15(String msgno, AcarsDfdA15Vo_A320 a15vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a15a16_list(ID,MSG_NO,ACNUM,DATE_UTC," 
				+ "ESN_1,EHRS_1,AP_1,FLAP_1,SLAT_1," 
				+ "ESN_2,EHRS_2,AP_2,FLAP_2,SLAT_2," 
				+ "MAX_E1,LIM_E1," 
				+ "RALT_S1,RALR_S1,PTCH_S1,PTCR_S1,ROLL_S1,ROLR_S1,YAW_S1," 
				+ "RALT_S2,RALR_S2,PTCH_S2,PTCR_S2,ROLL_S2,ROLR_S2,YAW_S2,"
				+ "VRTA_S3,LONA_S3,LATA_S3,"
				+ "VRTA_S4,LONA_S4,LATA_S4,"
				+ "RALT_T1,RALR_T1,PTCH_T1,PTCR_T1,ROLL_T1,ROLR_T1,YAW_T1," 
				+ "RALT_T2,RALR_T2,PTCH_T2,PTCR_T2,ROLL_T2,ROLR_T2,YAW_T2,"
				+ "VRTA_T3,LONA_T3,LATA_T3,"
				+ "VRTA_T4,LONA_T4,LATA_T4,"
				+ "UPDATE_DATE,COUNT,GW)" 
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?," 
				+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ";
		
		AcarsEC_EEVo_A15_A320 ec = a15vo.getEc();
		AcarsEC_EEVo_A15_A320 ee = a15vo.getEe();
		AcarsE1Vo_A15_A320 e1 = a15vo.getE1();
		AcarsST12Vo_A15_A320 s1 = a15vo.getS1();
		AcarsST12Vo_A15_A320 s2 = a15vo.getS2();
		AcarsST34Vo_A15_A320 s3 = a15vo.getS3();
		AcarsST34Vo_A15_A320 s4 = a15vo.getS4();
		AcarsST12Vo_A15_A320 t1 = a15vo.getT1();
		AcarsST12Vo_A15_A320 t2 = a15vo.getT2();
		AcarsST34Vo_A15_A320 t3 = a15vo.getT3();
		AcarsST34Vo_A15_A320 t4 = a15vo.getT4();
		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		if (t1 == null) {
			logger.warn("原始报文中T1-T4行无数据");
			//没有T行数据的情况
			insertSql += ",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,sysdate,?,?)";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc()
					,ec.getEsn(),ec.getEhrs(),ec.getAp(),ec.getFlap(),ec.getSlat()
					,ee.getEsn(),ee.getEhrs(),ee.getAp(),ee.getFlap(),ee.getSlat()
					,e1.getMax(),e1.getLim()
					,s1.getRalt(),s1.getRalr(),s1.getPtch(),s1.getPtcr(),s1.getRoll(),s1.getRolr(),s1.getYam()
					,s2.getRalt(),s2.getRalr(),s2.getPtch(),s2.getPtcr(),s2.getRoll(),s2.getRolr(),s2.getYam()
					,s3.getVrta(),s3.getLona(),s3.getLata()
					,s4.getVrta(),s4.getLona(),s4.getLata()
					,e1.getCounts(),headVo.getGw());
			
		} else {
			insertSql += ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?)";
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc()
					,ec.getEsn(),ec.getEhrs(),ec.getAp(),ec.getFlap(),ec.getSlat()
					,ee.getEsn(),ee.getEhrs(),ee.getAp(),ee.getFlap(),ee.getSlat()
					,e1.getMax(),e1.getLim()
					,s1.getRalt(),s1.getRalr(),s1.getPtch(),s1.getPtcr(),s1.getRoll(),s1.getRolr(),s1.getYam()
					,s2.getRalt(),s2.getRalr(),s2.getPtch(),s2.getPtcr(),s2.getRoll(),s2.getRolr(),s2.getYam()
					,s3.getVrta(),s3.getLona(),s3.getLata()
					,s4.getVrta(),s4.getLona(),s4.getLata()
					,t1.getRalt(),t1.getRalr(),t1.getPtch(),t1.getPtcr(),t1.getRoll(),t1.getRolr(),t1.getYam()
					,t2.getRalt(),t2.getRalr(),t2.getPtch(),t2.getPtcr(),t2.getRoll(),t2.getRolr(),t2.getYam()
					,t3.getVrta(),t3.getLona(),t3.getLata()
					,t4.getVrta(),t4.getLona(),t4.getLata()
					,e1.getCounts(),headVo.getGw());
		}
		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A15报文[msg_no]=["+msgno+"]入库成功！");

	}

}
