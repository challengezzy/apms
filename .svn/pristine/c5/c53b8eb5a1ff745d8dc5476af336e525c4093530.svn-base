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

public class A16DataParseImpl extends ReportContentParseClass{

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA15Vo_A320 a16vo = praseA16Data(content,trans_time);
		
		insertA16(msgno, a16vo);
		
		AlarmMonitorTargetVo targetVo = getAlarmTargetVo();
		targetVo.addParam(DfdVarConst.KEY_BODYVO, a16vo);
		//告警触发
		AlarmComputeService.getInstance().alarmObjectAdd(MonitorObjConst.A16_PARSE, msgno, targetVo);
		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	public AcarsDfdA15Vo_A320 praseA16Data(String gStr,String transdate) throws Exception{
		AcarsDfdA15Vo_A320 a16vo = new AcarsDfdA15Vo_A320();
		
		String tmpStr = gStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){				
				a16vo.setEc(new AcarsEC_EEVo_A15_A320(line));
			}else if(line.startsWith("EE")){				
				a16vo.setEe(new AcarsEC_EEVo_A15_A320(line));
			}else if(line.startsWith("E1")){				
				a16vo.setE1(new AcarsE1Vo_A15_A320(line));
			}else if(line.startsWith("S1")){				
				a16vo.setS1(new AcarsST12Vo_A15_A320(line));
			}else if(line.startsWith("S2")){				
				a16vo.setS2(new AcarsST12Vo_A15_A320(line));
			}else if(line.startsWith("S3")){				
				a16vo.setS3(new AcarsST34Vo_A15_A320(line));
			}else if(line.startsWith("S4")){				
				a16vo.setS4(new AcarsST34Vo_A15_A320(line));
			}else if(line.startsWith("T1")){				
				a16vo.setT1(new AcarsST12Vo_A15_A320(line));
			}else if(line.startsWith("T2")){				
				a16vo.setT2(new AcarsST12Vo_A15_A320(line));
			}else if(line.startsWith("T3")){				
				a16vo.setT3(new AcarsST34Vo_A15_A320(line));
			}else if(line.startsWith("T4")){				
				a16vo.setT4(new AcarsST34Vo_A15_A320(line));
			}
		}
		
		return a16vo;
		
	}
	
	/**
	 * A23报文解析内容写入a_dfd_a23_list
	 * @param msgno
	 * @param a23vo
	 */
	public void insertA16(String msgno, AcarsDfdA15Vo_A320 a16vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a15a16_list(ID,MSG_NO,ACNUM,DATE_UTC," 
				+ "ESN_1,EHRS_1,AP_1,FLAP_1,SLAT_1," 
				+ "ESN_2,EHRS_2,AP_2,FLAP_2,SLAT_2," 
				+ "MAX_E1,LIM_E1," 
				+ "RALT_S1,RALR_S1,PTCH_S1,PTCR_S1,ROLL_S1,ROLR_S1,YAW_S1," 
				+ "RALT_S2,RALR_S2,PTCH_S2,PTCR_S2,ROLL_S2,ROLR_S2,YAW_S2,"
				+ "VRTA_S3,LONA_S3,LATA_S3,"
				+ "VRTA_S4,LONA_S4,LATA_S4,"
				+ "UPDATE_DATE,COUNT,GW)" 
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?," 
				+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?)";
		
		AcarsEC_EEVo_A15_A320 ec = a16vo.getEc();
		AcarsEC_EEVo_A15_A320 ee = a16vo.getEe();
		AcarsE1Vo_A15_A320 e1 = a16vo.getE1();
		AcarsST12Vo_A15_A320 s1 = a16vo.getS1();
		AcarsST12Vo_A15_A320 s2 = a16vo.getS2();
		AcarsST34Vo_A15_A320 s3 = a16vo.getS3();
		AcarsST34Vo_A15_A320 s4 = a16vo.getS4();
        if(s4==null)
        {
        	throw new Exception("原始报文中无S4行数据");
        }
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc()
				,ec.getEsn(),ec.getEhrs(),ec.getAp(),ec.getFlap(),ec.getSlat()
				,ee.getEsn(),ee.getEhrs(),ee.getAp(),ee.getFlap(),ee.getSlat()
				,e1.getMax(),e1.getLim()
				,s1.getRalt(),s1.getRalr(),s1.getPtch(),s1.getPtcr(),s1.getRoll(),s1.getRolr(),s1.getYam()
				,s2.getRalt(),s2.getRalr(),s2.getPtch(),s2.getPtcr(),s2.getRoll(),s2.getRolr(),s2.getYam()
				,s3.getVrta(),s3.getLona(),s3.getLata()
				,s4.getVrta(),s4.getLona(),s4.getLata()
				,e1.getCounts(),headVo.getGw());
		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A16报文[msg_no]=["+msgno+"]入库成功！");

	}
}
