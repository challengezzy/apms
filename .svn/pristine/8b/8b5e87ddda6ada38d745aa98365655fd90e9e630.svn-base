package com.apms.bs.dataprase.impl;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA07Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a07.AcarsEC_EEVo_A07_A320_CFM;
import com.apms.bs.dataprase.vo.a07.AcarsN1Vo_A07_A320_CFM;
import com.apms.bs.dataprase.vo.a07.AcarsSxVo_A07_A320_CFM;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A07DataParseImpl_A320_CFM extends ReportContentParseClass{
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA07Vo_A320 a07vo = praseA07Data(content,trans_time);
		insertA07(msgno, a07vo);
		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	/**
	 * 报文解析
	 * @param graphStr 报文内容
	 * @param transdate 报文传送日期 YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	public AcarsDfdA07Vo_A320 praseA07Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA07Vo_A320 a07vo = new AcarsDfdA07Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		//logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			if("".equals(line))
				return a07vo;
			if(','==line.charAt(line.length()-1)){
				line=line.substring(0, line.length()-1);
				}
			//logger.debug("第"+i+"行:" + line);
			if(line.startsWith("EC")){
				a07vo.setEc_c(new  AcarsEC_EEVo_A07_A320_CFM(line));
			}else if(line.startsWith("EE")){
				a07vo.setEe_c(new  AcarsEC_EEVo_A07_A320_CFM(line));
			}else if(line.startsWith("N1")){
				a07vo.setN1_c(new  AcarsN1Vo_A07_A320_CFM(line));
			}else if(line.startsWith("S1")){
				a07vo.setS1_c(new  AcarsSxVo_A07_A320_CFM(line));
			}else if(line.startsWith("S2")){
				a07vo.setS2_c(new  AcarsSxVo_A07_A320_CFM(line));
			}else if(line.startsWith("S3")){
				a07vo.setS3_c(new  AcarsSxVo_A07_A320_CFM(line));
			}else if(line.startsWith("S4")){
				a07vo.setS4_c(new  AcarsSxVo_A07_A320_CFM(line));
			}else if(line.startsWith("S5")){
				a07vo.setS5_c(new  AcarsSxVo_A07_A320_CFM(line));
			}else if(line.startsWith("S6")){
				a07vo.setS6_c(new  AcarsSxVo_A07_A320_CFM(line));
			}else if(line.startsWith("S7")){
				a07vo.setS7_c(new  AcarsSxVo_A07_A320_CFM(line));
			}else if(line.startsWith("S8")){
				a07vo.setS8_c(new  AcarsSxVo_A07_A320_CFM(line));
			}else if(line.startsWith("S9")){
				a07vo.setS9_c(new  AcarsSxVo_A07_A320_CFM(line));
			}else if(line.startsWith("S0")){
				a07vo.setS0_c(new  AcarsSxVo_A07_A320_CFM(line));
			}
			
		}
		

		
		return a07vo;
	}
	
	public void insertA07(String msgno, AcarsDfdA07Vo_A320 a07vo) throws Exception{
		CommDMO dmo = new CommDMO();
		AcarsEC_EEVo_A07_A320_CFM ec = a07vo.getEc_c();
		AcarsEC_EEVo_A07_A320_CFM ee = a07vo.getEe_c();
		AcarsN1Vo_A07_A320_CFM n1 = a07vo.getN1_c();
		AcarsSxVo_A07_A320_CFM s1=a07vo.getS1_c();
		AcarsSxVo_A07_A320_CFM s2=a07vo.getS2_c();
		AcarsSxVo_A07_A320_CFM s3=a07vo.getS3_c();
		AcarsSxVo_A07_A320_CFM s4=a07vo.getS4_c();
		AcarsSxVo_A07_A320_CFM s5=a07vo.getS5_c();
		AcarsSxVo_A07_A320_CFM s6=a07vo.getS6_c();
		AcarsSxVo_A07_A320_CFM s7=a07vo.getS7_c();
		AcarsSxVo_A07_A320_CFM s8=a07vo.getS8_c();
		AcarsSxVo_A07_A320_CFM s9=a07vo.getS9_c();
		AcarsSxVo_A07_A320_CFM s0=a07vo.getS0_c();
        
		String insertSql = "insert into a_dfd_a07CFM56_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ERT_1,ECYC_1,AP_1,ECW1_1,"
				+ "ESN_2,EHRS_2,ERT_2,ECYC_2,AP_2,ECW1_2," 
				+ "E,MAX,LIM,REF,TOL,TTP,EVM,"
				+ "N1_S1,N2_S1,VN_S1,VC_S1,VH_S1,VL_S1,PHA_S1,PHT_S1,OIT_S1,OIP_S1,"
		        + "N1_S2,N2_S2,VN_S2,VC_S2,VH_S2,VL_S2,PHA_S2,PHT_S2,OIT_S2,OIP_S2,"
		        + "N1_S3,N2_S3,VN_S3,VC_S3,VH_S3,VL_S3,PHA_S3,PHT_S3,OIT_S3,OIP_S3,"
		        + "N1_S4,N2_S4,VN_S4,VC_S4,VH_S4,VL_S4,PHA_S4,PHT_S4,OIT_S4,OIP_S4,"
		        + "N1_S5,N2_S5,VN_S5,VC_S5,VH_S5,VL_S5,PHA_S5,PHT_S5,OIT_S5,OIP_S5,"
		        + "N1_S6,N2_S6,VN_S6,VC_S6,VH_S6,VL_S6,PHA_S6,PHT_S6,OIT_S6,OIP_S6,"
		        + "N1_S7,N2_S7,VN_S7,VC_S7,VH_S7,VL_S7,PHA_S7,PHT_S7,OIT_S7,OIP_S7,"
		        + "N1_S8,N2_S8,VN_S8,VC_S8,VH_S8,VL_S8,PHA_S8,PHT_S8,OIT_S8,OIP_S8,"
		        + "N1_S9,N2_S9,VN_S9,VC_S9,VH_S9,VL_S9,PHA_S9,PHT_S9,OIT_S9,OIP_S9,"
		        + "N1_S0,N2_S0,VN_S0,VC_S0,VH_S0,VL_S0,PHA_S0,PHT_S0,OIT_S0,OIP_S0,"
				+ "UPDATE_DATE)"  
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,";
		    if(s6==null)
			insertSql=insertSql+ "null,null,null,null,null,null,null,null,null,null,";
		        else
		    	insertSql=insertSql+"?,?,?,?,?,?,?,?,?,?,";				
		    if(s7==null)
				insertSql=insertSql+ "null,null,null,null,null,null,null,null,null,null,";
			    else
			    	insertSql=insertSql+"?,?,?,?,?,?,?,?,?,?,";	
		    if(s8==null)
				insertSql=insertSql+ "null,null,null,null,null,null,null,null,null,null,";
			    else
			    	insertSql=insertSql+"?,?,?,?,?,?,?,?,?,?,";
		    if(s9==null)
				insertSql=insertSql+ "null,null,null,null,null,null,null,null,null,null,";
			    else
			    	insertSql=insertSql+"?,?,?,?,?,?,?,?,?,?,";
		    if(s0==null)
				insertSql=insertSql+ "null,null,null,null,null,null,null,null,null,null,";
			    else
			    	insertSql=insertSql+"?,?,?,?,?,?,?,?,?,?,";
		    insertSql=insertSql+ "sysdate)";
		
		
		

//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		
			if(s6==null)
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
						ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),ec.getEcw1(),
						ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),ee.getEcw1(),
						n1.getE(),n1.getMax(),n1.getLim(),n1.getRef(),n1.getTol(),n1.getTtp(),n1.getEvm(),
						s1.getN1(),s1.getN2(),s1.getVn(),s1.getVc(),s1.getVh(),s1.getVl(),s1.getPha(),s1.getPht(),s1.getOit(),s1.getOip(),
				        s2.getN1(),s2.getN2(),s2.getVn(),s2.getVc(),s2.getVh(),s2.getVl(),s2.getPha(),s2.getPht(),s2.getOit(),s2.getOip(),
			            s3.getN1(),s3.getN2(),s3.getVn(),s3.getVc(),s3.getVh(),s3.getVl(),s3.getPha(),s3.getPht(),s3.getOit(),s3.getOip(),
			            s4.getN1(),s4.getN2(),s4.getVn(),s4.getVc(),s4.getVh(),s4.getVl(),s4.getPha(),s4.getPht(),s4.getOit(),s4.getOip(),
			            s5.getN1(),s5.getN2(),s5.getVn(),s5.getVc(),s5.getVh(),s5.getVl(),s5.getPha(),s5.getPht(),s5.getOit(),s5.getOip()
			            );
			else if(s7==null)
			{
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
						ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),ec.getEcw1(),
						ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),ee.getEcw1(),
						n1.getE(),n1.getMax(),n1.getLim(),n1.getRef(),n1.getTol(),n1.getTtp(),n1.getEvm(),
						s1.getN1(),s1.getN2(),s1.getVn(),s1.getVc(),s1.getVh(),s1.getVl(),s1.getPha(),s1.getPht(),s1.getOit(),s1.getOip(),
				        s2.getN1(),s2.getN2(),s2.getVn(),s2.getVc(),s2.getVh(),s2.getVl(),s2.getPha(),s2.getPht(),s2.getOit(),s2.getOip(),
			            s3.getN1(),s3.getN2(),s3.getVn(),s3.getVc(),s3.getVh(),s3.getVl(),s3.getPha(),s3.getPht(),s3.getOit(),s3.getOip(),
			            s4.getN1(),s4.getN2(),s4.getVn(),s4.getVc(),s4.getVh(),s4.getVl(),s4.getPha(),s4.getPht(),s4.getOit(),s4.getOip(),
			            s5.getN1(),s5.getN2(),s5.getVn(),s5.getVc(),s5.getVh(),s5.getVl(),s5.getPha(),s5.getPht(),s5.getOit(),s5.getOip(),
			            s6.getN1(),s6.getN2(),s6.getVn(),s6.getVc(),s6.getVh(),s6.getVl(),s6.getPha(),s6.getPht(),s6.getOit(),s6.getOip());
			}
			else if(s8==null)
			{
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
						ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),ec.getEcw1(),
						ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),ee.getEcw1(),
						n1.getE(),n1.getMax(),n1.getLim(),n1.getRef(),n1.getTol(),n1.getTtp(),n1.getEvm(),
						s1.getN1(),s1.getN2(),s1.getVn(),s1.getVc(),s1.getVh(),s1.getVl(),s1.getPha(),s1.getPht(),s1.getOit(),s1.getOip(),
				        s2.getN1(),s2.getN2(),s2.getVn(),s2.getVc(),s2.getVh(),s2.getVl(),s2.getPha(),s2.getPht(),s2.getOit(),s2.getOip(),
			            s3.getN1(),s3.getN2(),s3.getVn(),s3.getVc(),s3.getVh(),s3.getVl(),s3.getPha(),s3.getPht(),s3.getOit(),s3.getOip(),
			            s4.getN1(),s4.getN2(),s4.getVn(),s4.getVc(),s4.getVh(),s4.getVl(),s4.getPha(),s4.getPht(),s4.getOit(),s4.getOip(),
			            s5.getN1(),s5.getN2(),s5.getVn(),s5.getVc(),s5.getVh(),s5.getVl(),s5.getPha(),s5.getPht(),s5.getOit(),s5.getOip(),
			            s6.getN1(),s6.getN2(),s6.getVn(),s6.getVc(),s6.getVh(),s6.getVl(),s6.getPha(),s6.getPht(),s6.getOit(),s6.getOip(),
			            s7.getN1(),s7.getN2(),s7.getVn(),s7.getVc(),s7.getVh(),s7.getVl(),s7.getPha(),s7.getPht(),s7.getOit(),s7.getOip());
			}
			else if(s9==null)
			{
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
						ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),ec.getEcw1(),
						ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),ee.getEcw1(),
						n1.getE(),n1.getMax(),n1.getLim(),n1.getRef(),n1.getTol(),n1.getTtp(),n1.getEvm(),
						s1.getN1(),s1.getN2(),s1.getVn(),s1.getVc(),s1.getVh(),s1.getVl(),s1.getPha(),s1.getPht(),s1.getOit(),s1.getOip(),
				        s2.getN1(),s2.getN2(),s2.getVn(),s2.getVc(),s2.getVh(),s2.getVl(),s2.getPha(),s2.getPht(),s2.getOit(),s2.getOip(),
			            s3.getN1(),s3.getN2(),s3.getVn(),s3.getVc(),s3.getVh(),s3.getVl(),s3.getPha(),s3.getPht(),s3.getOit(),s3.getOip(),
			            s4.getN1(),s4.getN2(),s4.getVn(),s4.getVc(),s4.getVh(),s4.getVl(),s4.getPha(),s4.getPht(),s4.getOit(),s4.getOip(),
			            s5.getN1(),s5.getN2(),s5.getVn(),s5.getVc(),s5.getVh(),s5.getVl(),s5.getPha(),s5.getPht(),s5.getOit(),s5.getOip(),
			            s6.getN1(),s6.getN2(),s6.getVn(),s6.getVc(),s6.getVh(),s6.getVl(),s6.getPha(),s6.getPht(),s6.getOit(),s6.getOip(),
			            s7.getN1(),s7.getN2(),s7.getVn(),s7.getVc(),s7.getVh(),s7.getVl(),s7.getPha(),s7.getPht(),s7.getOit(),s7.getOip(),
	                    s8.getN1(),s8.getN2(),s8.getVn(),s8.getVc(),s8.getVh(),s8.getVl(),s8.getPha(),s8.getPht(),s8.getOit(),s8.getOip());

			}
			else if(s0==null)
			{
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
						ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),ec.getEcw1(),
						ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),ee.getEcw1(),
						n1.getE(),n1.getMax(),n1.getLim(),n1.getRef(),n1.getTol(),n1.getTtp(),n1.getEvm(),
						s1.getN1(),s1.getN2(),s1.getVn(),s1.getVc(),s1.getVh(),s1.getVl(),s1.getPha(),s1.getPht(),s1.getOit(),s1.getOip(),
				        s2.getN1(),s2.getN2(),s2.getVn(),s2.getVc(),s2.getVh(),s2.getVl(),s2.getPha(),s2.getPht(),s2.getOit(),s2.getOip(),
			            s3.getN1(),s3.getN2(),s3.getVn(),s3.getVc(),s3.getVh(),s3.getVl(),s3.getPha(),s3.getPht(),s3.getOit(),s3.getOip(),
			            s4.getN1(),s4.getN2(),s4.getVn(),s4.getVc(),s4.getVh(),s4.getVl(),s4.getPha(),s4.getPht(),s4.getOit(),s4.getOip(),
			            s5.getN1(),s5.getN2(),s5.getVn(),s5.getVc(),s5.getVh(),s5.getVl(),s5.getPha(),s5.getPht(),s5.getOit(),s5.getOip(),
			            s6.getN1(),s6.getN2(),s6.getVn(),s6.getVc(),s6.getVh(),s6.getVl(),s6.getPha(),s6.getPht(),s6.getOit(),s6.getOip(),
			            s7.getN1(),s7.getN2(),s7.getVn(),s7.getVc(),s7.getVh(),s7.getVl(),s7.getPha(),s7.getPht(),s7.getOit(),s7.getOip(),
	                    s8.getN1(),s8.getN2(),s8.getVn(),s8.getVc(),s8.getVh(),s8.getVl(),s8.getPha(),s8.getPht(),s8.getOit(),s8.getOip(),
	                    s9.getN1(),s9.getN2(),s9.getVn(),s9.getVc(),s9.getVh(),s9.getVl(),s9.getPha(),s9.getPht(),s9.getOit(),s9.getOip());

			}
			else{
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
						ec.getEsn(),ec.getEhrs(),ec.getErt(),ec.getEcyc(),ec.getAp(),ec.getEcw1(),
						ee.getEsn(),ee.getEhrs(),ee.getErt(),ee.getEcyc(),ee.getAp(),ee.getEcw1(),
						n1.getE(),n1.getMax(),n1.getLim(),n1.getRef(),n1.getTol(),n1.getTtp(),n1.getEvm(),
						s1.getN1(),s1.getN2(),s1.getVn(),s1.getVc(),s1.getVh(),s1.getVl(),s1.getPha(),s1.getPht(),s1.getOit(),s1.getOip(),
				        s2.getN1(),s2.getN2(),s2.getVn(),s2.getVc(),s2.getVh(),s2.getVl(),s2.getPha(),s2.getPht(),s2.getOit(),s2.getOip(),
			            s3.getN1(),s3.getN2(),s3.getVn(),s3.getVc(),s3.getVh(),s3.getVl(),s3.getPha(),s3.getPht(),s3.getOit(),s3.getOip(),
			            s4.getN1(),s4.getN2(),s4.getVn(),s4.getVc(),s4.getVh(),s4.getVl(),s4.getPha(),s4.getPht(),s4.getOit(),s4.getOip(),
			            s5.getN1(),s5.getN2(),s5.getVn(),s5.getVc(),s5.getVh(),s5.getVl(),s5.getPha(),s5.getPht(),s5.getOit(),s5.getOip(),
			            s6.getN1(),s6.getN2(),s6.getVn(),s6.getVc(),s6.getVh(),s6.getVl(),s6.getPha(),s6.getPht(),s6.getOit(),s6.getOip(),
			            s7.getN1(),s7.getN2(),s7.getVn(),s7.getVc(),s7.getVh(),s7.getVl(),s7.getPha(),s7.getPht(),s7.getOit(),s7.getOip(),
			            s8.getN1(),s8.getN2(),s8.getVn(),s8.getVc(),s8.getVh(),s8.getVl(),s8.getPha(),s8.getPht(),s8.getOit(),s8.getOip(),
			            s9.getN1(),s9.getN2(),s9.getVn(),s9.getVc(),s9.getVh(),s9.getVl(),s9.getPha(),s9.getPht(),s9.getOit(),s9.getOip(),
			            s0.getN1(),s0.getN2(),s0.getVn(),s0.getVc(),s0.getVh(),s0.getVl(),s0.getPha(),s0.getPht(),s0.getOit(),s0.getOip());
			}
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A07 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
