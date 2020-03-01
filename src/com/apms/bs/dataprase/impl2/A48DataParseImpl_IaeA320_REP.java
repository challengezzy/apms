package com.apms.bs.dataprase.impl2;

import smartx.framework.common.vo.HashVO;

import com.apms.bs.alarm.AlarmComputeService;
import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.dataprase.impl.A48DataParseImpl_IaeA320;
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

/**
 * V2500发动机,启动活门 A48
 * @author jerry
 * @date May 25, 2016
 */
public class A48DataParseImpl_IaeA320_REP extends A48DataParseImpl_IaeA320 {

	protected void parseWarn(HashVO acarsVo,String msgno,String content,String transdate,String code) throws Exception{
		AcarsDfdA48Vo_WarnIae a48w = new AcarsDfdA48Vo_WarnIae();
		String tmpStr = content.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		//判断发动机位置 左发还是右发
		String pos = code.substring(3,4);
		a48w.setPos(pos);
		boolean isRep = true;
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
	
}
