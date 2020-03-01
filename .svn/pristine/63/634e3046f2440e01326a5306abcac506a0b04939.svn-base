package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a48.AcarsDfdA48Vo_PerfIae;
import com.apms.bs.dataprase.vo.a48.AcarsDfdA48Vo_WarnIae;
import com.apms.bs.dataprase.vo.a48.AcarsECVo_A48_Iae;
import com.apms.bs.dataprase.vo.a48.AcarsF1Vo_A48_Iae;
import com.apms.bs.dataprase.vo.a48.AcarsL1Vo_A48_Iae;
import com.apms.bs.dataprase.vo.a48.AcarsL2Vo_A48_Iae;
import com.apms.bs.dataprase.vo.a48.AcarsS1Vo_A48_Iae;
import com.apms.bs.dataprase.vo.a48.AcarsS2Vo_A48_Iae;
import com.apms.bs.dataprase.vo.a48.AcarsS3Vo_A48_Iae;
import com.apms.bs.dataprase.vo.a48.AcarsSVo_A48_Iae;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * V2500发动机,启动活门 A48
 * @author jerry
 * @date May 25, 2016
 */
public class A48DataParseImpl_IaeA320 extends ReportContentParseClass {

	private CommDMO dmo = new CommDMO();
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno,String content, String trans_time) throws Exception{
		praseData(acarsVo,msgno,content,trans_time);		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		return res;
	}
	
	//告警报文的CODE列表
	private String[] warnCodes = {"4111", "4121", "4131", "4141", "4211", "4221", "4231", "4241", 
								  "4112", "4122", "4132", "4142", "4212", "4222", "4232", "4242"};
	
	/** 判断是否是告警CODE */
	private boolean isWarnCode(String code){
		for(String warnc : warnCodes){
			if( warnc.equals(code) ){
				return true;
			}
		}
		return false;
	}

	private void praseData(HashVO acarsVo,String msgno,String content,String transdate) throws Exception{
		//先判断CODE,再解析是性能报还是告警报
		String code = headVo.getCode();
		boolean isWarn = isWarnCode(code);
		
		if(isWarn == true){
			//故障报文解析
			parseWarn(acarsVo, msgno, content, transdate,code);
		}else{ //性能报解析
			parsePerf(acarsVo, msgno, content, transdate);
		}
	}
	
	protected void parseWarn(HashVO acarsVo,String msgno,String content,String transdate,String code) throws Exception{
		AcarsDfdA48Vo_WarnIae a48w = new AcarsDfdA48Vo_WarnIae();
		String tmpStr = content.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		//判断发动机位置 左发还是右发
		String pos = code.substring(3,4);
		a48w.setPos(pos);
		
		boolean isRep = false;
		int s1num = 1;
		int s2num = 1;
		int s3num = 1;
		int s4num = 1;
		int s5num = 1;
		int s6num = 1;
		//给a48vo 对象赋值开始
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
			if(line.startsWith("EC")){				
				a48w.setEc(new AcarsECVo_A48_Iae(line));
			}else if(line.startsWith("EE")){				
				a48w.setEe(new AcarsECVo_A48_Iae(line));
			}else if(line.startsWith("F1")){
				a48w.setF1(new AcarsF1Vo_A48_Iae(line));
				
			}else if(line.startsWith("F2")){
				a48w.setF2(new AcarsF1Vo_A48_Iae(line));
				
			}else if(line.startsWith("S1")){
				if(s1num == 1){
					a48w.setS11(new AcarsS1Vo_A48_Iae(line));
				}else if(s1num == 2){
					a48w.setS12(new AcarsS1Vo_A48_Iae(line));
				}else if(s1num == 3){
					a48w.setS13(new AcarsS1Vo_A48_Iae(line));
				}
				s1num++;
				
			}else if(line.startsWith("S2")){
				if(s2num == 1){
					a48w.setS21(new AcarsS2Vo_A48_Iae(line,isRep));
				}else if(s2num == 2){
					a48w.setS22(new AcarsS2Vo_A48_Iae(line,isRep));
				}else if(s2num == 3){
					a48w.setS23(new AcarsS2Vo_A48_Iae(line,isRep));
				}
				s2num++;
				
			}else if(line.startsWith("S3")){
				if(s3num == 1){
					a48w.setS31(new AcarsS3Vo_A48_Iae(line,isRep));
				}else if(s3num == 2){
					a48w.setS32(new AcarsS3Vo_A48_Iae(line,isRep));
				}else if(s3num == 3){
					a48w.setS33(new AcarsS3Vo_A48_Iae(line,isRep));
				}
				s3num++;
				
			}else if(line.startsWith("S4")){
				if(s4num == 1){
					a48w.setS41(new AcarsS1Vo_A48_Iae(line));
				}else if(s4num == 2){
					a48w.setS42(new AcarsS1Vo_A48_Iae(line));
				}
				s4num++;
				
			}else if(line.startsWith("S5")){
				if(s5num == 1){
					a48w.setS51(new AcarsS2Vo_A48_Iae(line,isRep));
				}else if(s5num == 2){
					a48w.setS52(new AcarsS2Vo_A48_Iae(line,isRep));
				}
				s5num++;
				
			}else if(line.startsWith("S6")){
				if(s6num == 1){
					a48w.setS61(new AcarsS3Vo_A48_Iae(line,isRep));
				}else if(s6num == 2){
					a48w.setS62(new AcarsS3Vo_A48_Iae(line,isRep));
				}
				s6num++;
			}
		}
		
		//将对象数据持久化
		insertWarnToTable(acarsVo, msgno, a48w);
		
		//发告警消息
		AlarmMonitorTargetVo targetVo = new AlarmMonitorTargetVo();
		targetVo.setPkValue(msgno);
		targetVo.setMsgno(msgno);
		targetVo.setDateUtc(headVo.getDateUtc());
		targetVo.setAcnum(headVo.getAcid());
		targetVo.setRptno("A48");
		targetVo.addParam(DfdVarConst.KEY_BODYVO, a48w);
		targetVo.addParam(DfdVarConst.KEY_HEADVO, headVo);
		
		String monitorObjCode = "A48_VSV性能告警";
		AlarmComputeService.getInstance().alarmObjectAdd(monitorObjCode, msgno, targetVo);
		
	}
	
	protected void parsePerf(HashVO acarsVo,String msgno,String content,String transdate) throws Exception{
		AcarsDfdA48Vo_PerfIae a48vo = new AcarsDfdA48Vo_PerfIae();
		String tmpStr = content.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);	
		int l1num = 1;
		int r1num = 1;
		int l2num = 1;
		int r2num = 1;
		int s5num = 1;
		//给a48vo 对象赋值开始
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
			if(line.startsWith("EC")){				
				a48vo.setEc(new AcarsECVo_A48_Iae(line));
			}else if(line.startsWith("EE")){				
				a48vo.setEe(new AcarsECVo_A48_Iae(line));
			}else if(line.startsWith("F1")){
				a48vo.setF1(new AcarsF1Vo_A48_Iae(line));
				
			}else if(line.startsWith("F2")){
				a48vo.setF2(new AcarsF1Vo_A48_Iae(line));
				
			}else if(line.startsWith("L3")){
				a48vo.setL3(new AcarsL1Vo_A48_Iae(line));
				
			}else if(line.startsWith("R3")){
				a48vo.setR3(new AcarsL1Vo_A48_Iae(line));
				
			}else if(line.startsWith("L4")){
				a48vo.setL4(new AcarsL2Vo_A48_Iae(line));
			}else if(line.startsWith("R4")){
				a48vo.setR4(new AcarsL2Vo_A48_Iae(line));
				
			}else if(line.startsWith("L1")){
				if(l1num == 1){
					a48vo.setL1_1(new AcarsL1Vo_A48_Iae(line));
				}else if(l1num == 2){
					a48vo.setL1_2(new AcarsL1Vo_A48_Iae(line));
				}
				l1num++;
			}else if(line.startsWith("R1")){
				if(r1num == 1){
					a48vo.setR1_1(new AcarsL1Vo_A48_Iae(line));
				}else if(r1num == 2){
					a48vo.setR1_2(new AcarsL1Vo_A48_Iae(line));
				}
				r1num++;
			}else if(line.startsWith("L2")){
				if(l2num == 1){
					a48vo.setL2_1(new AcarsL2Vo_A48_Iae(line));
				}else if(l2num == 2){
					a48vo.setL2_2(new AcarsL2Vo_A48_Iae(line));
				}
				l2num++;
			}else if(line.startsWith("R2")){
				if(r2num == 1){
					a48vo.setR2_1(new AcarsL2Vo_A48_Iae(line));
				}else if(r2num == 2){
					a48vo.setR2_2(new AcarsL2Vo_A48_Iae(line));
				}
				r2num++;
			}else if(line.startsWith("S5")){
				//三行S5的处理
				if( s5num == 1){
					a48vo.setS1(new AcarsSVo_A48_Iae(line, transdate));
				}else if( s5num == 2){
					a48vo.setS2(new AcarsSVo_A48_Iae(line, transdate));
				}else if( s5num == 3){
					a48vo.setS3(new AcarsSVo_A48_Iae(line, transdate));
				}
				s5num++;
			}
		}
		
		//将对象数据持久化
		insertPerfToTable(acarsVo, msgno, a48vo);
	}
	
	
	protected void insertWarnToTable(HashVO acarsVo,String msgno, AcarsDfdA48Vo_WarnIae a48w) throws Exception{
		StringBuilder sb = new StringBuilder("insert into A_DFD_A48IAEV25_LIST_WARN(ID,MSG_NO,ACNUM,RPTDATE,CODE");
		sb.append(",WINGLOC,ESN_1,EHRS_1,ECYC_1,ESN_2,EHRS_2,ECYC_2");
		sb.append(",SVAWAF,SVAL,SVATK,SVAXCF,B25WAF,B25L,B25TK,B25XCF");
		sb.append(",UPDATETIME) values(S_A_DFD_HEAD.nextval,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?,?");
		sb.append(",sysdate)");
		
		AcarsECVo_A48_Iae ec = a48w.getEc();
		AcarsECVo_A48_Iae ee = a48w.getEe();
		
		AcarsF1Vo_A48_Iae f1 = a48w.getF1();
		AcarsF1Vo_A48_Iae f2 = a48w.getF2();
		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		//插入A48 告警
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode()
			,a48w.getPos(), ec.getEsn(),ec.getEhrs(),ec.getEcyc(), ee.getEsn(),ee.getEhrs(),ee.getEcyc()
			,f1.getWaf(),f1.getL(),f1.getTk(),f1.getXcf(),f2.getWaf(),f2.getL(),f2.getTk(),f2.getXcf()
		);
		//插入告警明细参数
		if(a48w.getS11() != null ){
			insertWarnData("S11", 1, a48w.getS11(), a48w.getS21(), a48w.getS31());
		}
		if(a48w.getS12() != null ){
			insertWarnData("S12", 2, a48w.getS12(), a48w.getS22(), a48w.getS32());
		}
		if(a48w.getS13() != null ){
			insertWarnData("S13", 3, a48w.getS13(), a48w.getS23(), a48w.getS33());
		}
		if(a48w.getS41() != null ){
			insertWarnData("S41", 4, a48w.getS41(), a48w.getS51(), a48w.getS61());
		}
		if(a48w.getS42() != null ){
			insertWarnData("S41", 5, a48w.getS42(), a48w.getS52(), a48w.getS62());
			
		}
	}
	
	protected void insertWarnData(String pname,int seq,AcarsS1Vo_A48_Iae s1,AcarsS2Vo_A48_Iae s2,AcarsS3Vo_A48_Iae s3) throws Exception{
		StringBuilder sb = new StringBuilder("insert into A_DFD_A48IAEV25_LIST_WARNDATA(ID,MSG_NO,ACNUM,RPTDATE");
		sb.append(",PNAME,SEQ");
		sb.append(",EPR,EGT,N1,N2,FF,P3,T3");
		sb.append(",EPRC,T2,P2,MN,ECW3,ECW4,TRP");
		sb.append(",SVA,BAF,T25,P25,CTL");
		sb.append(",UPDATETIME) values(S_A_DFD_HEAD.nextval,?,?,?");
		sb.append(",?,?");
		sb.append(",?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?");
		sb.append(",sysdate)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), msgno,headVo.getAcid(),headVo.getDateUtc()
				,pname,seq
				,s1.getEpr(),s1.getEgt(),s1.getN1(),s1.getN2(),s1.getFf(),s1.getP3(),s1.getT3()
				,s2.getEprc(),s2.getT2(),s2.getP2(),s2.getMn(),s2.getEcw3(),s2.getEcw4(),s2.getTrp()
				,s3.getSva(),s3.getBaf(),s3.getT25(),s3.getP25(),s3.getCtl()
			);
	}
	
	/** 性能报文数据入库 */
	protected void insertPerfToTable(HashVO acarsVo,String msgno, AcarsDfdA48Vo_PerfIae a48Iae) throws Exception{
		
		StringBuilder sb = new StringBuilder("insert into A_DFD_A48IAEV25_LIST_PREF(ID,MSG_NO,ACNUM,RPTDATE,CODE");
		sb.append(",ESN_1,EHRS_1,ECYC_1,ESN_2,EHRS_2,ECYC_2");
		sb.append(",SVAWAF,SVAL,SVATK,SVAXCF,B25WAF,B25L,B25TK,B25XCF");
		sb.append(",SVADQ_1,SVADAV_1,SVADVAR_1,TIME_1");
		sb.append(",SVADQ_2,SVADAV_2,SVADVAR_2,TIME_2");
		sb.append(",SVADQ_3,SVADAV_3,SVADVAR_3,TIME_3");
		sb.append(",UPDATETIME) values(S_A_DFD_HEAD.nextval,?,?,?,?");
		sb.append(",?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?,?");
		sb.append(",?,?,?,?");
		sb.append(",?,?,?,?");
		sb.append(",?,?,?,?");
		sb.append(",sysdate)");
		
		AcarsECVo_A48_Iae ec = a48Iae.getEc();
		AcarsECVo_A48_Iae ee = a48Iae.getEe();
		
		AcarsF1Vo_A48_Iae f1 = a48Iae.getF1();
		AcarsF1Vo_A48_Iae f2 = a48Iae.getF2();
		
		AcarsSVo_A48_Iae s1 = a48Iae.getS1();
		AcarsSVo_A48_Iae s2 = a48Iae.getS2();
		AcarsSVo_A48_Iae s3 = a48Iae.getS3();
		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		//48 性能报
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode()
				, ec.getEsn(),ec.getEhrs(),ec.getEcyc(), ee.getEsn(),ee.getEhrs(),ee.getEcyc()
				,f1.getWaf(),f1.getL(),f1.getTk(),f1.getXcf(),f2.getWaf(),f2.getL(),f2.getTk(),f2.getXcf()
				,s1.getSvadq(),s1.getSvadav(),s1.getSvadvar(),s1.getTime()
				,s2.getSvadq(),s2.getSvadav(),s2.getSvadvar(),s2.getTime()
				,s3.getSvadq(),s3.getSvadav(),s3.getSvadvar(),s3.getTime()
		);
		
		//性能报明细
		if(a48Iae.getL1_1() != null){
			insertPerfData("L11",1,a48Iae.getL1_1(), a48Iae.getL2_1());
		}
		if(a48Iae.getL1_2() != null){
			insertPerfData("L12",2,a48Iae.getL1_2(), a48Iae.getL2_2());
		}
		if(a48Iae.getR1_1() != null){
			insertPerfData("R11",3,a48Iae.getR1_1(), a48Iae.getR2_1());
		}
		if(a48Iae.getR1_2() != null){
			insertPerfData("R12",4,a48Iae.getR1_2(), a48Iae.getR2_2());
		}
		if(a48Iae.getL3() != null){
			insertPerfData("L31",5,a48Iae.getL3(), a48Iae.getL4());
		}
		if(a48Iae.getR3() != null){
			insertPerfData("R31",6,a48Iae.getR3(), a48Iae.getR4());
		}
	}
	
	protected void insertPerfData(String pname,int seq,AcarsL1Vo_A48_Iae r1,AcarsL2Vo_A48_Iae r2) throws Exception{
		StringBuilder sb = new StringBuilder("insert into A_DFD_A48IAEV25_LIST_PREFDATA(ID,MSG_NO,ACNUM,RPTDATE");
		sb.append(",PNAME,SEQ");
		sb.append(",EPR,EGT,N1,N2,FF,T2,P2");
		sb.append(",T25,P25,P3,T3,P49,SVA,CTL");
		sb.append(",UPDATETIME) values(S_A_DFD_HEAD.nextval,?,?,?");
		sb.append(",?,?");
		sb.append(",?,?,?,?,?,?,?");
		sb.append(",?,?,?,?,?,?,?");
		sb.append(",sysdate)");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString(), msgno,headVo.getAcid(),headVo.getDateUtc()
				,pname,seq
				,r1.getEpr(),r1.getEgt(),r1.getN1(),r1.getN2(),r1.getFf(),r1.getT2(),r1.getP2()
				,r2.getT25(),r2.getP25(),r2.getP3(),r2.getT3(),r2.getP49(),r2.getSva(),r2.getCtl()
			);
	}
	
}
