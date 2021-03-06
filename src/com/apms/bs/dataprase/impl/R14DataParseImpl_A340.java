package com.apms.bs.dataprase.impl;

import java.util.ArrayList;

import com.apms.ApmsConst;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsDfdR14Vo_A330_A340;
import com.apms.bs.dataprase.vo.a13a14.AcarsE1Vo_R14_A330_A340;
import com.apms.bs.dataprase.vo.a13a14.AcarsNxVo_R14_A330_A340;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;

public class R14DataParseImpl_A340 extends ApuDataParseImpl{
	
	ArrayList<AcarsNxVo_R14_A330_A340> nxVoList;
	
	AcarsDfdR14Vo_A330_A340 r14vo;
	
	public void praseDataToVo(String gStr, String transdate) throws Exception {
		
		String modelSeires = "A340";
		r14vo = new AcarsDfdR14Vo_A330_A340();

		nxVoList = new ArrayList<AcarsNxVo_R14_A330_A340>();
		AcarsNxVo_R14_A330_A340 nxVo;
		
		String tmpStr = gStr;
		String[] lines = StringUtil.splitString2Array(tmpStr, "\n", true);
		
		AcarsE1Vo_R14_A330_A340 e1 = new AcarsE1Vo_R14_A330_A340(lines[7],modelSeires);
		
		r14vo.setE1(e1);
		
		asn = e1.getAsn();
		curAhrs = new Integer(e1.getAhrs());
		curAcyc = new Integer(e1.getAcyc());
		
		nxVo = new AcarsNxVo_R14_A330_A340(lines[8],modelSeires,"N1");
		r14vo.setN1(nxVo);
		nxVoList.add(nxVo);
		nxVo = new AcarsNxVo_R14_A330_A340(lines[9],modelSeires,"N2");
		r14vo.setN2(nxVo);
		nxVoList.add(nxVo);	
		nxVo = new AcarsNxVo_R14_A330_A340(lines[10],modelSeires,"N3");
		r14vo.setN3(nxVo);
		nxVoList.add(nxVo);	
		nxVo = new AcarsNxVo_R14_A330_A340(lines[11],modelSeires,"N4");
		r14vo.setN4(nxVo);
		nxVoList.add(nxVo);	
		nxVo = new AcarsNxVo_R14_A330_A340(lines[12],modelSeires,"N5");
		r14vo.setN5(nxVo);
		nxVoList.add(nxVo);	
		nxVo = new AcarsNxVo_R14_A330_A340(lines[13],modelSeires,"N6");
		r14vo.setN6(nxVo);
		nxVoList.add(nxVo);	
		nxVo = new AcarsNxVo_R14_A330_A340(lines[14],modelSeires,"N7");
		r14vo.setN7(nxVo);
		nxVoList.add(nxVo);	
		nxVo = new AcarsNxVo_R14_A330_A340(lines[15],modelSeires,"N8");
		r14vo.setN8(nxVo);
		nxVoList.add(nxVo);	
		nxVo = new AcarsNxVo_R14_A330_A340(lines[16],modelSeires,"N9");
		r14vo.setN9(nxVo);
		nxVoList.add(nxVo);	
		nxVo = new AcarsNxVo_R14_A330_A340(lines[17],modelSeires,"N0");
		r14vo.setN0(nxVo);
		nxVoList.add(nxVo);
		
	}
	
	public void resetHourCycle() throws Exception{
		r14vo.getE1().setAhrs(curAhrs);
		r14vo.getE1().setAcyc(curAcyc);
	}
	
	public void insertParseData() throws Exception{
		String insertSql = "insert into a_dfd_a14_list(ID,MSG_NO,ACMODEL,ACNUM,RPTDATE,CODE,ASN,AHRS,ACYC,PFAD,ACW2,ACW3,"
				+ "RECDATETIME)" 
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		
		String insertSql1= "insert into a_dfd_a14_list_data(ID,MSG_NO,ACNUM,"
		    + "ROW_NUM,ROW_TITLE,NA,EGTA,WF,OTA,IGV,WB,LCIT,PT,P2A,LCOT,SCV,HOT,GLA,"
		    + "RECDATETIME)"
		    + " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,"
		    + "?,?,?,?,?,?,?,sysdate)";
		
		AcarsE1Vo_R14_A330_A340 e1 = r14vo.getE1();
		
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,acmodel,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				e1.getAsn(),curAhrs,curAcyc,e1.getEcid(),e1.getAcw2(),e1.getAcw3());
		
		for (int g=0;g<nxVoList.size();g++){
			AcarsNxVo_R14_A330_A340 nx = nxVoList.get(g);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno, headVo.getAcid(),g+1, nx.getNx() ,nx.getNa(),nx.getEgta(),nx.getWf(),nx.getLot()
					,nx.getIgv(),nx.getWb(),nx.getLcit(),nx.getPt(),nx.getP2a(),nx.getLcot(),nx.getScv(),nx.getHot(),nx.getGla());	
		}
				
		//a_dfd_acw_decode
		AcarsAcwVo acw2 = e1.getAcw2Vo();
		AcarsAcwVo acw3 = e1.getAcw3Vo();
		acw2.insertToTable(msgno, headVo.getAcid(),acmodel, rptno, "E1");
		acw3.insertToTable(msgno, headVo.getAcid(),acmodel, rptno, "E1");
		
		logger.info("R14_A330_A340报文[msg_no]=["+msgno+"]入库成功！");
		
	}
	
	@Override
	public void setAlarmDataBody() throws Exception {
		monitorObjCode = MonitorObjConst.R14_PARSE;
		targetVo.addParam(DfdVarConst.KEY_BODYVO, r14vo);
		
	}

}
