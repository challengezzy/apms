package com.apms.bs.dataprase.impl;

import java.util.Date;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA27Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsAL_ARVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsCL_CRVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsDL_DRVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsELVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsERVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsML_NRVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsPPVo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsZ4_Z5Vo_A27_A320;
import com.apms.bs.dataprase.vo.a25a26a27.AcarsCF_CGVo_A27_A320;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

public class A27DataParseImpl_A320 extends ReportContentParseClass{

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA27Vo_A320 a27vo = praseA27Data(content,trans_time);
		
		insertA27(msgno, a27vo);
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	protected AcarsDfdA27Vo_A320 praseA27Data(String gStr,String transdate) throws Exception{
		AcarsDfdA27Vo_A320 a27vo = new AcarsDfdA27Vo_A320();
		String trans_date = transdate;
		String tmpStr = gStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		boolean isRep = false;
		logger.debug("报文每行数据如下：");
		Date alTime = null;
//		String alday = null;
		Date arTime = null;
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("PP")){				
				a27vo.setPp(new AcarsPPVo_A27_A320(line,isRep));
			}else if(line.startsWith("Z4")){					
				a27vo.setZ4(new AcarsZ4_Z5Vo_A27_A320(line, trans_date,isRep));
			}else if(line.startsWith("Z5")){				
				a27vo.setZ5(new AcarsZ4_Z5Vo_A27_A320(line, trans_date,isRep));
			}else if(line.startsWith("AL")){				
				a27vo.setAl(new AcarsAL_ARVo_A27_A320(line, trans_date,isRep));
				alTime = a27vo.getAl().getTime();
//				alday = a27vo.getAl().getYymmdd();
			}else if(line.startsWith("AR")){				
				a27vo.setAr(new AcarsAL_ARVo_A27_A320(line, trans_date,isRep));
				arTime = a27vo.getAr().getTime();
//				arday = a27vo.getAr().getYymmdd();
			}else if(line.startsWith("ML")){				
				a27vo.setMl(new AcarsML_NRVo_A27_A320(line,isRep));
			}else if(line.startsWith("DL")){		
				a27vo.setDl(new AcarsDL_DRVo_A27_A320(line,alTime,trans_date));
			}else if(line.startsWith("NR")){				
				a27vo.setNr(new AcarsML_NRVo_A27_A320(line,isRep));
			}else if(line.startsWith("DR")){		
				a27vo.setDr(new AcarsDL_DRVo_A27_A320(line,arTime,trans_date));
			}else if(line.startsWith("CL")){				
				a27vo.setCl(new AcarsCL_CRVo_A27_A320(line,isRep));
			}else if(line.startsWith("CR")){				
				a27vo.setCr(new AcarsCL_CRVo_A27_A320(line,isRep));
			}else if(line.startsWith("EL")){				
				a27vo.setEl(new AcarsELVo_A27_A320(line,isRep));
			}else if(line.startsWith("ER")){				
				a27vo.setEr(new AcarsERVo_A27_A320(line));
			}else if(line.startsWith("CF")){				
				a27vo.setCf(new AcarsCF_CGVo_A27_A320(line));
			}else if(line.startsWith("CG")){				
				a27vo.setCg(new AcarsCF_CGVo_A27_A320(line));
			}
		}
		
		return a27vo;
		
		
	}
	
	/**
	 * A23报文解析内容写入a_dfd_a23_list
	 * @param msgno
	 * @param a23vo
	 */
	public void insertA27(String msgno, AcarsDfdA27Vo_A320 a27vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a27_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,"
			    + "TMR27_PP,CKTMR_PP,ENDTMR_PP,DETQ_PP,ENDTQ_PP,OIQEXT_PP," //11
			    + "ESN_1,EHRS_1,ECYC_1," 
			    + "ESN_2,EHRS_2,ECYC_2," 
				+ "OIQ_ENG1_Z4,OIQ_ENG2_Z4,ENG_SHUTTIME_Z4," 
				+ "OIQ_ENG1_Z5,OIQ_ENG2_Z5,ENG_SHUTTIME_Z5,"//3
				+ "OILADD_ENG1,ADDTIME_ENG1,PFAD_ENG1,PRE20S_ENG1,OIQST_ENG1,OIQOLD_ENG1," 
				+ "OILADD_ENG2,ADDTIME_ENG2,PFAD_ENG2,PRE20S_ENG2,OIQST_ENG2,OIQOLD_ENG2," //6
				+ "QDT0_ENG1,QDT0_TIME_ENG1,QDT1_ENG1,QDT1_TIME_ENG1,QDT2_ENG1,QDT2_TIME_ENG1,QDT3_ENG1,QDT3_TIME_ENG1,QDT4_ENG1,QDT4_TIME_ENG1," 
				+ "QDT0_ENG2,QDT0_TIME_ENG2,QDT1_ENG2,QDT1_TIME_ENG2,QDT2_ENG2,QDT2_TIME_ENG2,QDT3_ENG2,QDT3_TIME_ENG2,QDT4_ENG2,QDT4_TIME_ENG2,"//10
				+ "CAL_GA_ENG1,CAL_AIR_ENG1,OIQAV_ENG1,OIQDT_ENG1,OIQCNT_ENG1,"
				+ "CAL_GA_ENG2,CAL_AIR_ENG2,OIQAV_ENG2,OIQDT_ENG2,OIQCNT_ENG2,"//5
				+ "QT_ENG1,FH_ENG1,OILCAL_ENG1,EMPLOYID,"
				+ "QT_ENG2,FH_ENG2,OILCAL_ENG2,"//3
				+ "UPDATE_DATE)" 
				+ " values(S_A_DFD_HEAD.nextval,?," 
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "sysdate)";
		
		AcarsPPVo_A27_A320 pp = a27vo.getPp();
		AcarsCF_CGVo_A27_A320 cf = a27vo.getCf();
		AcarsCF_CGVo_A27_A320 cg = a27vo.getCg();
		AcarsZ4_Z5Vo_A27_A320 z4 = a27vo.getZ4();
		AcarsZ4_Z5Vo_A27_A320 z5 = a27vo.getZ5();
		AcarsAL_ARVo_A27_A320 al = a27vo.getAl();
		AcarsAL_ARVo_A27_A320 ar = a27vo.getAr();
		AcarsML_NRVo_A27_A320 ml = a27vo.getMl();
		AcarsDL_DRVo_A27_A320 dl = a27vo.getDl();
		AcarsML_NRVo_A27_A320 nr = a27vo.getNr();
		AcarsDL_DRVo_A27_A320 dr = a27vo.getDr();
		AcarsCL_CRVo_A27_A320 cl = a27vo.getCl();
		AcarsCL_CRVo_A27_A320 cr = a27vo.getCr();
		AcarsELVo_A27_A320 el = a27vo.getEl();
		AcarsERVo_A27_A320 er = a27vo.getEr();
		if(cf==null||cg==null)
		{
			throw new Exception("原始报文缺少CF或CG行数据！");
		}
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				pp.get_27tmr(),pp.getCktmr(),pp.getEndtmr(),pp.getDetq(),pp.getEndtq(),pp.getOiqext(),
				cf.getEsn(),cf.getEhrs(),cf.getEcyc(),
				cg.getEsn(),cg.getEhrs(),cg.getEcyc(),
				z4.getOiq1(),z4.getOiq2(),z4.getMonddHHMMSS(),
				z5.getOiq1(),z5.getOiq2(),z5.getMonddHHMMSS(),
				al.getOiladd(),al.getTime(),al.getPrad(),al.get_20s(),al.getOiqst(),al.getOiqold(),
				ar.getOiladd(),ar.getTime(),ar.getPrad(),ar.get_20s(),ar.getOiqst(),ar.getOiqold(),
				ml.getQdt0(),dl.getQdt0(),ml.getQdt1(),dl.getQdt1(),ml.getQdt2(),dl.getQdt2(),ml.getQdt3(),dl.getQdt3(),ml.getQdt4(),dl.getQdt4(),
				nr.getQdt0(),dr.getQdt0(),nr.getQdt1(),dr.getQdt1(),nr.getQdt2(),dr.getQdt2(),nr.getQdt3(),dr.getQdt3(),nr.getQdt4(),dr.getQdt4(),
				cl.getCal_g_a(),cl.getCal_air(),cl.getOiqav(),cl.getOiqdt(),cl.getOiqcnt(),
				cr.getCal_g_a(),cr.getCal_air(),cr.getOiqav(),cr.getOiqdt(),cr.getOiqcnt(),
				el.getQt(),el.getFh(),el.getOilcal(),el.getEmployid(),
				er.getQt(),er.getFh(),er.getOilcal()
				);
				

		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A27报文[msg_no]=["+msgno+"]入库成功！");

	}
	
	

}
