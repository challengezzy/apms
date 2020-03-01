package com.apms.bs.dataprase.impl;

import com.apms.ApmsConst;
import com.apms.bs.alarm.MonitorObjConst;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsDfdA14Vo_A320;
import com.apms.bs.dataprase.vo.a13a14.AcarsE1Vo_A14_A320;
import com.apms.bs.dataprase.vo.a13a14.AcarsNxVo_A14_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.StringUtil;

public class A14DataParseImpl_A320 extends ApuDataParseImpl{
	
	private AcarsDfdA14Vo_A320 a14vo;
	
	
	public void praseDataToVo(String gStrA14,String transdate) throws Exception{
		a14vo = new AcarsDfdA14Vo_A320();
		
		String tmpStr = gStrA14.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			
			if(line.startsWith("E1")){	
				AcarsE1Vo_A14_A320 e1 = new AcarsE1Vo_A14_A320(line);
				a14vo.setE1(e1);
				
				asn = e1.getAsn();
				curAhrs = new Integer(e1.getAhrs());
				curAcyc = new Integer(e1.getAcyc());
				
				checkApuMatch(transdate); //检验apu和飞机号是否匹配
				if(!isApuACRight){
					break;//如果数据不匹配，不必再继续解
				}
				
			}else if(line.startsWith("N0")){				
				a14vo.setN0(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("N1")){				
				a14vo.setN1(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("N2")){				
				a14vo.setN2(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("N3")){				
				a14vo.setN3(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("N4")){				
				a14vo.setN4(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("N5")){				
				a14vo.setN5(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("N6")){				
				a14vo.setN6(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("N7")){				
				a14vo.setN7(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("N8")){				
				a14vo.setN8(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("N9")){				
				a14vo.setN9(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("1N")){				
				a14vo.set_1n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("2N")){				
				a14vo.set_2n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("3N")){				
				a14vo.set_3n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("4N")){				
				a14vo.set_4n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("5N")){				
				a14vo.set_5n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("6N")){				
				a14vo.set_6n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("7N")){				
				a14vo.set_7n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("8N")){				
				a14vo.set_8n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("9N")){				
				a14vo.set_9n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("0N")){				
				a14vo.set_0n(new AcarsNxVo_A14_A320(line));
			}else if(line.startsWith("S1")){				
				a14vo.setS1(new AcarsNxVo_A14_A320(line));
			}
			
		}
		
	}
	
	public void resetHourCycle() throws Exception{
		a14vo.getE1().setAhrs(curAhrs);
		a14vo.getE1().setAcyc(curAcyc);
	}
	
	public void insertParseData() throws Exception{
		String insertSql = "insert into a_dfd_a14_list(ID,MSG_NO,ACMODEL,ACNUM,RPTDATE,CODE,ASN,AHRS,ACYC,PFAD,ACW3,ACW4,"
				+ "RECDATETIME)" 
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		String insertSql1= "insert into a_dfd_a14_list_data(ID,MSG_NO,ACNUM,"
			    + "ROW_NUM,ROW_TITLE,NA,EGTA,OTA,IGV,WB,LCIT,PT,GLA,"
			    + "RECDATETIME)"
			    + " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";

		AcarsE1Vo_A14_A320 e1 = a14vo.getE1();
		AcarsNxVo_A14_A320 n0 = a14vo.getN0();
		AcarsNxVo_A14_A320 n1 = a14vo.getN1();
		AcarsNxVo_A14_A320 n2 = a14vo.getN2();
		AcarsNxVo_A14_A320 n3 = a14vo.getN3();
		AcarsNxVo_A14_A320 n4 = a14vo.getN4();
		AcarsNxVo_A14_A320 n5 = a14vo.getN5();
		AcarsNxVo_A14_A320 n6 = a14vo.getN6();
		AcarsNxVo_A14_A320 n7 = a14vo.getN7();
		AcarsNxVo_A14_A320 n8 = a14vo.getN8();
		AcarsNxVo_A14_A320 n9 = a14vo.getN9();
		AcarsNxVo_A14_A320 _1n = a14vo.get_1n();
		AcarsNxVo_A14_A320 _2n = a14vo.get_2n();
		AcarsNxVo_A14_A320 _3n = a14vo.get_3n();
		AcarsNxVo_A14_A320 _4n = a14vo.get_4n();
		AcarsNxVo_A14_A320 _5n = a14vo.get_5n();
		AcarsNxVo_A14_A320 _6n = a14vo.get_6n();
		AcarsNxVo_A14_A320 _7n = a14vo.get_7n();
		AcarsNxVo_A14_A320 _8n = a14vo.get_8n();
		AcarsNxVo_A14_A320 _9n = a14vo.get_9n();
		AcarsNxVo_A14_A320 _0n = a14vo.get_0n();
		AcarsNxVo_A14_A320 s1 = a14vo.getS1();
		
		AcarsAcwVo acw3 = e1.getAcw3Vo();
		AcarsAcwVo acw4 = e1.getAcw4Vo();
		
		
		headParseClass.insertHeadData(msgno, headVo);
		
		acw3.insertToTable(msgno,  headVo.getAcid(),acmodel, rptno, "E1");
		acw4.insertToTable(msgno,  headVo.getAcid(),acmodel, rptno, "E1");
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,acmodel,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				e1.getAsn(),curAhrs,curAcyc,e1.getPfad(),e1.getAcw3(),e1.getAcw4());
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				1,"N0",n0.getNa(),n0.getEgta(),n0.getOta(),n0.getIgv(),n0.getWb(),n0.getLcit(),n0.getPt(),n0.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				2,"N1",n1.getNa(),n1.getEgta(),n1.getOta(),n1.getIgv(),n1.getWb(),n1.getLcit(),n1.getPt(),n1.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				3,"N2",n2.getNa(),n2.getEgta(),n2.getOta(),n2.getIgv(),n2.getWb(),n2.getLcit(),n2.getPt(),n2.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				4,"N3",n3.getNa(),n3.getEgta(),n3.getOta(),n3.getIgv(),n3.getWb(),n3.getLcit(),n3.getPt(),n3.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				5,"N4",n4.getNa(),n4.getEgta(),n4.getOta(),n4.getIgv(),n4.getWb(),n4.getLcit(),n4.getPt(),n4.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				6,"N5",n5.getNa(),n5.getEgta(),n5.getOta(),n5.getIgv(),n5.getWb(),n5.getLcit(),n5.getPt(),n5.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				7,"N6",n6.getNa(),n6.getEgta(),n6.getOta(),n6.getIgv(),n6.getWb(),n6.getLcit(),n6.getPt(),n6.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				8,"N7",n7.getNa(),n7.getEgta(),n7.getOta(),n7.getIgv(),n7.getWb(),n7.getLcit(),n7.getPt(),n7.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				9,"N8",n8.getNa(),n8.getEgta(),n8.getOta(),n8.getIgv(),n8.getWb(),n8.getLcit(),n8.getPt(),n8.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				10,"N9",n9.getNa(),n9.getEgta(),n9.getOta(),n9.getIgv(),n9.getWb(),n9.getLcit(),n9.getPt(),n9.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				11,"1N",_1n.getNa(),_1n.getEgta(),_1n.getOta(),_1n.getIgv(),_1n.getWb(),_1n.getLcit(),_1n.getPt(),_1n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				12,"2N",_2n.getNa(),_2n.getEgta(),_2n.getOta(),_2n.getIgv(),_2n.getWb(),_2n.getLcit(),_2n.getPt(),_2n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				13,"3N",_3n.getNa(),_3n.getEgta(),_3n.getOta(),_3n.getIgv(),_3n.getWb(),_3n.getLcit(),_3n.getPt(),_3n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				14,"4N",_4n.getNa(),_4n.getEgta(),_4n.getOta(),_4n.getIgv(),_4n.getWb(),_4n.getLcit(),_4n.getPt(),_4n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				15,"5N",_5n.getNa(),_5n.getEgta(),_5n.getOta(),_5n.getIgv(),_5n.getWb(),_5n.getLcit(),_5n.getPt(),_5n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				16,"6N",_6n.getNa(),_6n.getEgta(),_6n.getOta(),_6n.getIgv(),_6n.getWb(),_6n.getLcit(),_6n.getPt(),_6n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				17,"7N",_7n.getNa(),_7n.getEgta(),_7n.getOta(),_7n.getIgv(),_7n.getWb(),_7n.getLcit(),_7n.getPt(),_7n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				18,"8N",_8n.getNa(),_8n.getEgta(),_8n.getOta(),_8n.getIgv(),_8n.getWb(),_8n.getLcit(),_8n.getPt(),_8n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				19,"9N",_9n.getNa(),_9n.getEgta(),_9n.getOta(),_9n.getIgv(),_9n.getWb(),_9n.getLcit(),_9n.getPt(),_9n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				20,"0N",_0n.getNa(),_0n.getEgta(),_0n.getOta(),_0n.getIgv(),_0n.getWb(),_0n.getLcit(),_0n.getPt(),_0n.getGla());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,headVo.getAcid(),
				21,"S1",s1.getNa(),s1.getEgta(),s1.getOta(),s1.getIgv(),s1.getWb(),s1.getLcit(),s1.getPt(),s1.getGla());
		
		logger.info("A14_A320报文[msg_no]=["+msgno+"]入库成功！");
		
	}

	@Override
	public void setAlarmDataBody() throws Exception {
		monitorObjCode = MonitorObjConst.A14_PARSE;
		
		targetVo.addParam(DfdVarConst.KEY_BODYVO, a14vo);
	}
}
