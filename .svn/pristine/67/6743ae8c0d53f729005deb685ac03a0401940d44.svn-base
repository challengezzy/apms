package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA50Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a50.AcarsECVo_A50_A320;
import com.apms.bs.dataprase.vo.a50.AcarsL1Vo_A50_A320;
import com.apms.bs.dataprase.vo.a50.AcarsP1Vo_A50_A320;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * V2500发动机,N1震动报文
 * @author jerry
 * @date May 25, 2016
 */
public class A50DataParseImpl_A320 extends ReportContentParseClass {

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno,String content, String trans_time) throws Exception{
		praseData(acarsVo,msgno,content,trans_time);		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		return res;
	}

	protected void praseData(HashVO acarsVo,String msgno,String gStrA46,String transdate) throws Exception{
		AcarsDfdA50Vo_A320 a50vo = new AcarsDfdA50Vo_A320();
		String tmpStr = gStrA46.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		int l1num = 1;
		int r1num = 1;
		boolean isRep = false;
		//给a50vo 对象赋值开始
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
			if(line.startsWith("EC")){				
				a50vo.setEc(new AcarsECVo_A50_A320(line));
			}else if(line.startsWith("EE")){				
				a50vo.setEe(new AcarsECVo_A50_A320(line));
			}else if(line.startsWith("P1")){
				a50vo.setP1(new AcarsP1Vo_A50_A320(line,isRep));
				
			}else if(line.startsWith("L1")){
				if(l1num ==1 ){
					a50vo.setL1_1(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(l1num ==2 ){
					a50vo.setL1_2(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(l1num ==3 ){
					a50vo.setL1_3(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(l1num ==4 ){
					a50vo.setL1_4(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(l1num ==5 ){
					a50vo.setL1_5(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}
				l1num++;
				
			}else if(line.startsWith("R1")){
				if(r1num == 1){
					a50vo.setR1_1(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(r1num == 2){
					a50vo.setR1_2(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(r1num == 3){
					a50vo.setR1_3(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(r1num == 4){
					a50vo.setR1_4(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}else if(r1num == 5){
					a50vo.setR1_5(new AcarsL1Vo_A50_A320(line, transdate,isRep));
				}
				r1num++;
			}
		}
		
		insertToTable(acarsVo, msgno, a50vo);
	}
	
	
	public void insertToTable(HashVO acarsVo,String msgno, AcarsDfdA50Vo_A320 a50vo) throws Exception{
		
		CommDMO dmo = new CommDMO();
		String insertSql1 = "insert into A_DFD_A50IAEV25_LIST(ID,MSG_NO,ACNUM,RPTDATE," 
			+ "ESN_1,EHRS_1,ECYC_1,ESN_2,EHRS_2,ECYC_2,"
			+ "N1SET,N1VAR,N1DT,TMR,VIBSET,"
			+ "L1_RPM_1,L1_MIL_1,L1_DEG_1,L1_TIME_1,L1_UNIT_1,"
			+ "L1_RPM_2,L1_MIL_2,L1_DEG_2,L1_TIME_2,L1_UNIT_2,"
			+ "L1_RPM_3,L1_MIL_3,L1_DEG_3,L1_TIME_3,L1_UNIT_3,"
			+ "L1_RPM_4,L1_MIL_4,L1_DEG_4,L1_TIME_4,L1_UNIT_4,"
			+ "L1_RPM_5,L1_MIL_5,L1_DEG_5,L1_TIME_5,L1_UNIT_5,"
			+ "R1_RPM_1,R1_MIL_1,R1_DEG_1,R1_TIME_1,R1_UNIT_1,"
			+ "R1_RPM_2,R1_MIL_2,R1_DEG_2,R1_TIME_2,R1_UNIT_2,"
			+ "R1_RPM_3,R1_MIL_3,R1_DEG_3,R1_TIME_3,R1_UNIT_3,"
			+ "R1_RPM_4,R1_MIL_4,R1_DEG_4,R1_TIME_4,R1_UNIT_4,"
			+ "R1_RPM_5,R1_MIL_5,R1_DEG_5,R1_TIME_5,R1_UNIT_5,UPDATETIME )"
			+ " values(S_A_DFD_HEAD.nextval,?,?,? ,?,?,?,?,? ,?,?,?,?,?,? ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" 
			+ " ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ,sysdate)";
		
		
		AcarsECVo_A50_A320 ec = a50vo.getEc(); 
		AcarsECVo_A50_A320 ee = a50vo.getEe();
		AcarsP1Vo_A50_A320 p1 = a50vo.getP1();
		
		AcarsL1Vo_A50_A320 l1 = a50vo.getL1_1();
		AcarsL1Vo_A50_A320 l2 = a50vo.getL1_2();
		AcarsL1Vo_A50_A320 l3 = a50vo.getL1_3();
		AcarsL1Vo_A50_A320 l4 = a50vo.getL1_4();
		AcarsL1Vo_A50_A320 l5 = a50vo.getL1_5();
		
		
		AcarsL1Vo_A50_A320 r1 = a50vo.getR1_1();
		AcarsL1Vo_A50_A320 r2 = a50vo.getR1_2();
		AcarsL1Vo_A50_A320 r3 = a50vo.getR1_3();
		AcarsL1Vo_A50_A320 r4 = a50vo.getR1_4();
		AcarsL1Vo_A50_A320 r5 = a50vo.getR1_5();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),headVo.getDateUtc()
				,ec.getEsn(),ec.getEhrs(),ec.getEcyc(), ee.getEsn(),ee.getEhrs(),ee.getEcyc()
				,p1.getN1set(),p1.getN1var(),p1.getN1dt(),p1.getTmr(),p1.getVibset()
				,l1.getRpm(),l1.getMil(),l1.getDeg(),l1.getTime(),l1.getUnit()
				,l2.getRpm(),l2.getMil(),l2.getDeg(),l2.getTime(),l2.getUnit()
				,l3.getRpm(),l3.getMil(),l3.getDeg(),l3.getTime(),l3.getUnit()
				,l4.getRpm(),l4.getMil(),l4.getDeg(),l4.getTime(),l4.getUnit()
				,l5.getRpm(),l5.getMil(),l5.getDeg(),l5.getTime(),l5.getUnit()
				,r1.getRpm(),r1.getMil(),r1.getDeg(),r1.getTime(),r1.getUnit()
				,r2.getRpm(),r2.getMil(),r2.getDeg(),r2.getTime(),r2.getUnit()
				,r3.getRpm(),r3.getMil(),r3.getDeg(),r3.getTime(),r3.getUnit()
				,r4.getRpm(),r4.getMil(),r4.getDeg(),r4.getTime(),r4.getUnit()
				,r5.getRpm(),r5.getMil(),r5.getDeg(),r5.getTime(),r5.getUnit()
				);
		
	}
}
