package com.apms.bs.dataprase.impl;

import com.apms.ApmsConst;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsDfdA13Vo_A320;
import com.apms.bs.dataprase.vo.a13a14.AcarsE1Vo_A13_A320;
import com.apms.bs.dataprase.vo.a13a14.AcarsNxVo_A13_A320;
import com.apms.bs.dataprase.vo.a13a14.AcarsSxVo_A13_A320;
import com.apms.bs.dataprase.vo.a13a14.AcarsV1Vo_A13_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

public class A13DataParseImpl_A320 extends ApuDataParseImpl{
	
	private AcarsDfdA13Vo_A320 a13vo;

	public void praseDataToVo(String gStr,String transdate) throws Exception{
		a13vo = new AcarsDfdA13Vo_A320();
		String tmpStr = gStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		//logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			if (line.startsWith("E1")) {
				AcarsE1Vo_A13_A320 e1 = new AcarsE1Vo_A13_A320(line);
				a13vo.setE1(e1);
				
				asn = e1.getAsn();
				curAhrs = e1.getAhrs();
				curAcyc = e1.getAcyc();
				
				//zhangzy 20170521

			} else if (line.startsWith("N1")) {
				a13vo.setN1(new AcarsNxVo_A13_A320(line));
			} else if (line.startsWith("N2")) {
				a13vo.setN2(new AcarsNxVo_A13_A320(line));
			} else if (line.startsWith("N3")) {
				a13vo.setN3(new AcarsNxVo_A13_A320(line));
			} else if (line.startsWith("S1")) {
				a13vo.setS1(new AcarsSxVo_A13_A320(line));
			} else if (line.startsWith("S2")) {
				a13vo.setS2(new AcarsSxVo_A13_A320(line));
			} else if (line.startsWith("S3")) {
				a13vo.setS3(new AcarsSxVo_A13_A320(line));
			} else if (line.startsWith("V1")) {
				a13vo.setV1(new AcarsV1Vo_A13_A320(line));
			}

		}
		
	}
	
	public void resetHourCycle() throws Exception{
		a13vo.getE1().setAhrs(curAhrs);
		a13vo.getE1().setAcyc(curAcyc);
	}
	
	/**
	 * A23报文解析内容写入a_dfd_a23_list
	 * @param msgno
	 * @param a23vo
	 */
	synchronized public void insertParseData() throws Exception{
		
		AcarsE1Vo_A13_A320 e1 = a13vo.getE1();
		AcarsNxVo_A13_A320 n1 = a13vo.getN1();
		AcarsNxVo_A13_A320 n2 = a13vo.getN2();
		AcarsNxVo_A13_A320 n3 = a13vo.getN3();
		AcarsSxVo_A13_A320 s1 = a13vo.getS1();
		AcarsSxVo_A13_A320 s2 = a13vo.getS2();
		AcarsSxVo_A13_A320 s3 = a13vo.getS3();
		AcarsV1Vo_A13_A320 v1 = a13vo.getV1();
		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		// a_dfd_a13_list
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),headVo.getTat(),headVo.getAlt(),e1.getAsn(),curAhrs,curAcyc,ahrs_inc,acyc_inc,e1.getPfad()
				,n1.getEsn(),n1.getAcw1(),n1.getAcw2(),n1.getNa(),n1.getEgta(),n1.getIgv()
				,n2.getEsn(),n2.getAcw1(),n2.getAcw2(),n2.getNa(),n2.getEgta(),n2.getIgv()
				,n3.getEsn(),n3.getAcw1(),n3.getAcw2(),n3.getNa(),n3.getEgta(),n3.getIgv()
				,s1.getP2a(),s1.getLcit(),s1.getWb(),s1.getPt(),s1.getLcdt(),s1.getOta(),s1.getGla()
				,s2.getP2a(),s2.getLcit(),s2.getWb(),s2.getPt(),s2.getLcdt(),s2.getOta(),s2.getGla()
				,s3.getP2a(),s3.getLcit(),s3.getWb(),s3.getPt(),s3.getLcdt(),s3.getOta(),s3.getGla()
				,v1.getSta(),v1.getEgtp(),v1.getNpa(),v1.getOta(),v1.getLcit(),corFlag);
		
		
		//插入ACW控制字解析数据
		AcarsAcwVo acw1_n1= new AcarsAcwVo(ApmsVarConst.CW_ACW1,n1.getAcw1());
		AcarsAcwVo acw1_n2= new AcarsAcwVo(ApmsVarConst.CW_ACW1,n2.getAcw1());
		AcarsAcwVo acw1_n3= new AcarsAcwVo(ApmsVarConst.CW_ACW1,n3.getAcw1());
		AcarsAcwVo acw2_n1= new AcarsAcwVo(ApmsVarConst.CW_ACW2,n1.getAcw2());
		AcarsAcwVo acw2_n2= new AcarsAcwVo(ApmsVarConst.CW_ACW2,n2.getAcw2());
		AcarsAcwVo acw2_n3= new AcarsAcwVo(ApmsVarConst.CW_ACW2,n3.getAcw2());
		
		acw1_n1.insertToTable(msgno, headVo.getAcid(), acmodel, rptno, "N1");
		acw1_n2.insertToTable(msgno, headVo.getAcid(), acmodel, rptno, "N2");
		acw1_n3.insertToTable(msgno, headVo.getAcid(), acmodel, rptno, "N3");
		
		acw2_n1.insertToTable(msgno, headVo.getAcid(), acmodel, rptno, "N1");
		acw2_n2.insertToTable(msgno, headVo.getAcid(), acmodel, rptno, "N2");
		acw2_n3.insertToTable(msgno, headVo.getAcid(), acmodel, rptno, "N3");
		
		logger.info("A13报文[msg_no]=["+msgno+"]入库成功！");
		
	}
	
	

	@Override
	public void setAlarmDataBody() throws Exception {
		monitorObjCode = MonitorObjConst.A13_PARSE;
		
		targetVo.addParam(DfdVarConst.KEY_BODYVO, a13vo);		
	}

}
