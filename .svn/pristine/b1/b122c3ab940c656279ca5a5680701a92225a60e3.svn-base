package com.apms.bs.dataprase.impl;

import com.apms.ApmsConst;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsDfdR13Vo_A330_A340;
import com.apms.bs.dataprase.vo.a13a14.AcarsE1Vo_R13_A330_A340;
import com.apms.bs.dataprase.vo.a13a14.AcarsNxVo_R13_A330_A340;
import com.apms.bs.dataprase.vo.a13a14.AcarsV1Vo_R13_A330_A340;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

public class R13DataParseImpl_A330 extends ApuDataParseImpl{
	
	AcarsDfdR13Vo_A330_A340 r13vo;

	public void praseDataToVo(String gStr,String transdate) throws Exception{

		r13vo = new AcarsDfdR13Vo_A330_A340();
		String modelSeires = "A330";
		
//		String tmpStr = gStr.replaceAll("[\n\r]", " ");
		String tmpStr = gStr;
		String[] lines = StringUtil.splitString2Array(tmpStr, "\n", true);
		
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
            if(line.startsWith("E1")){	
            	AcarsE1Vo_R13_A330_A340 e1 = new AcarsE1Vo_R13_A330_A340(line.substring(3) ,modelSeires);
				r13vo.setE1(e1);
				
				asn = e1.getAsn();
				curAhrs = new Integer(e1.getAhrs());
				curAcyc = new Integer(e1.getAcyc());
				
				checkApuMatch(transdate);
				if(!isApuACRight){
					break;//如果数据不匹配，不必再继续解
				}
            	
			}else if(line.startsWith("N1")){				
				r13vo.setN1(new AcarsNxVo_R13_A330_A340(line.substring(3) ,modelSeires));
			}else if(line.startsWith("N2")){				
				r13vo.setN2(new AcarsNxVo_R13_A330_A340(line.substring(3) ,modelSeires));
			}else if(line.startsWith("N3")){				
				r13vo.setN3(new AcarsNxVo_R13_A330_A340(line.substring(3) ,modelSeires));
			}else if(line.startsWith("V1")){				
				r13vo.setV1(new AcarsV1Vo_R13_A330_A340(line.substring(3) ,modelSeires));
			}
            
		
		}
		
	}
	
	public void resetHourCycle() throws Exception{
		r13vo.getE1().setAhrs(curAhrs);
		r13vo.getE1().setAcyc(curAcyc);
	}
	
	/**
	 * A23报文解析内容写入a_dfd_a23_list
	 * @param msgno
	 * @param a23vo
	 */
	synchronized public void insertParseData() throws Exception {
		String insertSql_330 = "insert into a_dfd_a13_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,TAT,ALT,"
				+ "ASN,AHRS,ACYC,AHRS_ADD,ACYC_ADD,ECID,ACW1,"
			    + "ESN_N1,EGTA_N1,GLA_S1,WB_S1,PT_S1,P2A_S1,LCDT_S1,LCIT_S1,IGV_N1,SCV_N1,OTA_S1,HOT_N1,"
			    + "ESN_N2,EGTA_N2,GLA_S2,WB_S2,PT_S2,P2A_S2,LCDT_S2,LCIT_S2,IGV_N2,SCV_N2,OTA_S2,HOT_N2,"
			    + "ESN_N3,EGTA_N3,GLA_S3,WB_S3,PT_S3,P2A_S3,LCDT_S3,LCIT_S3,IGV_N3,SCV_N3,OTA_S3,HOT_N3,"
			    + "STA_V1,EGTP_V1,NPA_V1,OTA_V1,LCIT_V1,CORRECT_FLAG,"
				+ "RECDATETIME)" 
				+ " values(S_A_DFD_HEAD.nextval,"
				+ "?,?,?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,sysdate)";
		

		AcarsE1Vo_R13_A330_A340 e1 = r13vo.getE1();
		AcarsNxVo_R13_A330_A340 n1 = r13vo.getN1();
		AcarsNxVo_R13_A330_A340 n2 = r13vo.getN2();
		AcarsNxVo_R13_A330_A340 n3 = r13vo.getN3();
		AcarsV1Vo_R13_A330_A340 v1 = r13vo.getV1();
		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql_330, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),headVo.getTat(),headVo.getAlt()
				,e1.getAsn(),curAhrs,curAcyc,ahrs_inc,acyc_inc,e1.getEcid(),e1.getAcw1()
				,n1.getEsn(),n1.getEgta(),n1.getGla(),n1.getWb(),n1.getPt(),n1.getP2a(),n1.getLcot(),n1.getLcit(),n1.getIgv(),n1.getScv(),n1.getLot(),n1.getHot()
				,n2.getEsn(),n2.getEgta(),n2.getGla(),n2.getWb(),n2.getPt(),n2.getP2a(),n2.getLcot(),n2.getLcit(),n2.getIgv(),n2.getScv(),n2.getLot(),n2.getHot()
				,n3.getEsn(),n3.getEgta(),n3.getGla(),n3.getWb(),n3.getPt(),n3.getP2a(),n3.getLcot(),n3.getLcit(),n3.getIgv(),n3.getScv(),n3.getLot(),n3.getHot()
				,v1.getSta(),v1.getEgtp(),v1.getNpa(),v1.getLot(),v1.getLcit(),corFlag);
		
		//acw记录
		AcarsAcwVo acw1= new AcarsAcwVo(ApmsVarConst.CW_ACW1,e1.getAcw1());
		acw1.insertToTable(msgno, headVo.getAcid(),acmodel,rptno, "E1");
		
		logger.debug("R13报文[msg_no]=["+msgno+"]入库成功！");

	}
	
	@Override
	public void setAlarmDataBody() throws Exception {
		monitorObjCode = MonitorObjConst.R13_PARSE;
		
		targetVo.addParam(DfdVarConst.KEY_BODYVO, r13vo);		
	}

}
