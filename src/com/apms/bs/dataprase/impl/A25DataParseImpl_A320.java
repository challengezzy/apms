package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.*;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsCFVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsCGVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsKO_KIVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsSWZVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsT1_T2Vo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsTL_TRVo_A25_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsV1Vo_A25_A320;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

public class A25DataParseImpl_A320 extends ReportContentParseClass{
	
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		AcarsDfdA25Vo_A320 a25vo = praseA25Data(content,trans_time);
		insertA25(msgno, a25vo);
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	protected AcarsDfdA25Vo_A320 praseA25Data(String gStrA25,String transdate) throws Exception{
		AcarsDfdA25Vo_A320 a25vo = new AcarsDfdA25Vo_A320();
		
		
		String tmpStr = gStrA25.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);

		boolean isRep = false;
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			
			if(line.startsWith("CF")){			
				a25vo.setCf(new AcarsCFVo_A25_A320(line,isRep));
			}else if(line.startsWith("CG")){			
				a25vo.setCg(new AcarsCGVo_A25_A320(line,isRep));
			}else if(line.startsWith("TL")){				
				a25vo.setTl(new AcarsTL_TRVo_A25_A320(line,isRep));
			}else if(line.startsWith("TR")){				
				a25vo.setTr(new AcarsTL_TRVo_A25_A320(line,isRep));
			}else if(line.startsWith("S1")){				
				a25vo.setS1(new AcarsSWZVo_A25_A320(line,transdate,isRep));
			}else if(line.startsWith("S2")){				
				a25vo.setS2(new AcarsSWZVo_A25_A320(line,transdate,isRep));
			}else if(line.startsWith("KO")||line.startsWith("KS")){				
				a25vo.setKo(new AcarsKO_KIVo_A25_A320(line,isRep));
			}else if(line.startsWith("T1")){				
				a25vo.setT1(new AcarsT1_T2Vo_A25_A320(line,isRep));
			}else if(line.startsWith("T2")){				
				a25vo.setT2(new AcarsT1_T2Vo_A25_A320(line,isRep));
			}else if(line.startsWith("V1")){				
				a25vo.setV1(new AcarsV1Vo_A25_A320(line,transdate));
			}else if(line.startsWith("W1")||line.startsWith("X1")){				
				a25vo.setW1(new AcarsSWZVo_A25_A320(line,transdate,isRep));
			}else if(line.startsWith("KI")||line.startsWith("KX")){				
				a25vo.setKi(new AcarsKO_KIVo_A25_A320(line,isRep));
			}else if(line.startsWith("Z1")){				
				a25vo.setZ1(new AcarsSWZVo_A25_A320(line,transdate,isRep));
			}else if(line.startsWith("Z2")){				
				a25vo.setZ2(new AcarsSWZVo_A25_A320(line,transdate,isRep));
			}else if(line.startsWith("Z3")){	
				a25vo.setZ3(new AcarsSWZVo_A25_A320(line,transdate,isRep));

			}
			
		}
		
		//System.out.println("hello n1 "+a14vo.getN1().getNa());

		return a25vo;
		
	}
	
	public void insertA25(String msgno, AcarsDfdA25Vo_A320 a25vo) throws Exception{
		//String acmodel=ac_model;
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a25_list(ID,MSG_NO,ACNUM,DATE_UTC,"
			+ "ESN_1,EHRS_1,ECYC_1,EHR_GA_1,"
			+ "ESN_2,EHRS_2,ECYC_2,EHR_GA_2,"
			+ "EHR_GA_T1,EHR_AIR_T1,"
			+ "EHR_GA_T2,EHR_AIR_T2,"
			+ "OIQ1_S1,OIQ2_S1,SD_S1,ENGAI_S1,TIME_S1,"
			+ "OIQ1_S2,OIQ2_S2,SD_S2,ENGAI_S2,TIME_S2,"
			+ "OIK1_K0,OIK2_K0,"
			+ "OIP_1,OIT_1,OIQ_1,N2_1,P3_1,OIK_1,"
			+ "OIP_2,OIT_2,OIQ_2,N2_2,P3_2,OIK_2,"
			+ "TIME_V1,SD_V1,ENGAI_V1,OILSTB_V1,"
			+ "OIQ1_W1,OIQ2_W1,TIME_W1,SD_W1,ENGAI_W1,"
			+ "OIK1_K1,OIK2_K1,"
			+ "OIQ1_Z1,OIQ2_Z1,TIME_Z1,SD_Z1,ENGAI_Z1,"
			+ "OIQ1_Z2,OIQ2_Z2,TIME_Z2,SD_Z2,ENGAI_Z2,"
			+ "OIQ1_Z3,OIQ2_Z3,TIME_Z3,SD_Z3,ENGAI_Z3,"
			+ "UPDATE_DATE)" 
			+ " values(S_A_DFD_HEAD.nextval," 
			+ "?,?,?,?,?,?,?,?,?,?,"
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,?,?,?,?,?," 
			+ "?,?,?,?,?,sysdate)";

		

		AcarsCFVo_A25_A320 cf = a25vo.getCf(); 
		AcarsCGVo_A25_A320 cg = a25vo.getCg();
		AcarsTL_TRVo_A25_A320 tl = a25vo.getTl();
		AcarsTL_TRVo_A25_A320 tr = a25vo.getTr();
		AcarsSWZVo_A25_A320 s1  = a25vo.getS1();
		AcarsSWZVo_A25_A320 s2  = a25vo.getS2();
		AcarsKO_KIVo_A25_A320 ko  = a25vo.getKo();
		AcarsT1_T2Vo_A25_A320 t1  = a25vo.getT1();
		AcarsT1_T2Vo_A25_A320 t2  = a25vo.getT2();
		AcarsV1Vo_A25_A320 v1  = a25vo.getV1();
		AcarsSWZVo_A25_A320 w1  = a25vo.getW1();
		AcarsKO_KIVo_A25_A320 ki  = a25vo.getKi();
		AcarsSWZVo_A25_A320 z1  = a25vo.getZ1();
		AcarsSWZVo_A25_A320 z2  = a25vo.getZ2();
		AcarsSWZVo_A25_A320 z3  = a25vo.getZ3();
		if(ko==null || ki==null)
		{
			throw new Exception("原始报文缺少ko或ki行数据！");
		}
		headParseClass.insertHeadData(msgno, headVo);
		if (tl==null || tr==null){
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),
					cf.getEsn(),cf.getEhrs(),cf.getEcyc(),cf.getEhr_g_a(),
					cg.getEsn(),cg.getEhrs(),cg.getEcyc(),cg.getEhr_g_a(),
					null,null,
					null,null,
					s1.getOiq1(),s1.getOiq2(),s1.getSd(),s1.getEngai(),s1.getMonddHHMMSS(),
					s2.getOiq1(),s2.getOiq2(),s2.getSd(),s2.getEngai(),s2.getMonddHHMMSS(),
					null,null,
					t1.getOip(),t1.getOit(),t1.getOiq(),t1.getN2(),t1.getP3(),t1.getOik(),
					t2.getOip(),t2.getOit(),t2.getOiq(),t2.getN2(),t2.getP3(),t2.getOik(),
					v1.getMonddHHMMSS(),v1.getSd(),v1.getEngai(),v1.getOilstb(),
					w1.getOiq1(),w1.getOiq2(),w1.getMonddHHMMSS(),w1.getSd(),w1.getEngai(),
					null,null,
					z1.getOiq1(),z1.getOiq2(),z1.getMonddHHMMSS(),z1.getSd(),z1.getEngai(),
					z2.getOiq1(),z2.getOiq2(),z2.getMonddHHMMSS(),z2.getSd(),z2.getEngai(),
					z3.getOiq1(),z3.getOiq2(),z3.getMonddHHMMSS(),z3.getSd(),z3.getEngai());
			
		}else {
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),
					cf.getEsn(),cf.getEhrs(),cf.getEcyc(),cf.getEhr_g_a(),
					cg.getEsn(),cg.getEhrs(),cg.getEcyc(),cg.getEhr_g_a(),
					tl.getEhr_g_a(),tl.getEhr_air(),
					tr.getEhr_g_a(),tr.getEhr_air(),
					s1.getOiq1(),s1.getOiq2(),s1.getSd(),s1.getEngai(),s1.getMonddHHMMSS(),
					s2.getOiq1(),s2.getOiq2(),s2.getSd(),s2.getEngai(),s2.getMonddHHMMSS(),
					ko.getOik1(),ko.getOik2(),
					t1.getOip(),t1.getOit(),t1.getOiq(),t1.getN2(),t1.getP3(),t1.getOik(),
					t2.getOip(),t2.getOit(),t2.getOiq(),t2.getN2(),t2.getP3(),t2.getOik(),
					v1.getMonddHHMMSS(),v1.getSd(),v1.getEngai(),v1.getOilstb(),
					w1.getOiq1(),w1.getOiq2(),w1.getMonddHHMMSS(),w1.getSd(),w1.getEngai(),
					ki.getOik1(),ki.getOik2(),
					z1.getOiq1(),z1.getOiq2(),z1.getMonddHHMMSS(),z1.getSd(),z1.getEngai(),
					z2.getOiq1(),z2.getOiq2(),z2.getMonddHHMMSS(),z2.getSd(),z2.getEngai(),
					z3.getOiq1(),z3.getOiq2(),z3.getMonddHHMMSS(),z3.getSd(),z3.getEngai());
		}
		

		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A25_A320报文[msg_no]=["+msgno+"]入库成功！");
		
	}
		
		

}
