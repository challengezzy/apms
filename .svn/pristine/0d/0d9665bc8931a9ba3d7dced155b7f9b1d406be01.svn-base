package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA07Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a07.AcarsECVo_A07_A320_IAE;
import com.apms.bs.dataprase.vo.a07.AcarsEEVo_A07_A320_IAE;
import com.apms.bs.dataprase.vo.a07.AcarsN1Vo_A07_A320_IAE;
import com.apms.bs.dataprase.vo.a07.AcarsSxVo_A07_A320_IAE;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A07DataParseImpl_A320_IAE extends ReportContentParseClass{
	
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
		
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){
				a07vo.setEc_i(new  AcarsECVo_A07_A320_IAE(line));
			}else if(line.startsWith("EE")){
				a07vo.setEe_i(new  AcarsEEVo_A07_A320_IAE(line));
			}else if(line.startsWith("N1")){
				a07vo.setN1_i(new  AcarsN1Vo_A07_A320_IAE(line));
			}else if(line.startsWith("S1")){
				a07vo.setS1_i(new  AcarsSxVo_A07_A320_IAE(line));
			}else if(line.startsWith("S2")){
				a07vo.setS2_i(new  AcarsSxVo_A07_A320_IAE(line));
			}else if(line.startsWith("S3")){
				a07vo.setS3_i(new  AcarsSxVo_A07_A320_IAE(line));
			}else if(line.startsWith("S4")){
				a07vo.setS4_i(new  AcarsSxVo_A07_A320_IAE(line));
			}else if(line.startsWith("S5")){
				a07vo.setS5_i(new  AcarsSxVo_A07_A320_IAE(line));
			}else if(line.startsWith("S6")){
				a07vo.setS6_i(new  AcarsSxVo_A07_A320_IAE(line));
			}else if(line.startsWith("S7")){
				a07vo.setS7_i(new  AcarsSxVo_A07_A320_IAE(line));
			}else if(line.startsWith("S8")){
				a07vo.setS8_i(new  AcarsSxVo_A07_A320_IAE(line));
			}else if(line.startsWith("S9")){
				a07vo.setS9_i(new  AcarsSxVo_A07_A320_IAE(line));
			}else if(line.startsWith("S0")){
				a07vo.setS0_i(new  AcarsSxVo_A07_A320_IAE(line));
			}
			
		}
		

		
		return a07vo;
	}
	
	public void insertA07(String msgno, AcarsDfdA07Vo_A320 a07vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a07iave25_list(ID,MSG_NO,ACNUM,RPTDATE,CODE,"
				+ "ESN_1,EHRS_1,ECYC_1,AP_1,ECW4_1,Y1_EC,Y2_EC,"
				+ "ESN_2,EHRS_2,ECYC_2,AP_2,ECW4_2," 
				+ "E,MAX,LIM,REF,TOL,TTP,EVM,PARA,"
				+ "N1_S1,N2_S1,VB1_S1,VB2_S1,PHA_S1,OIT_S1,OIP_S1,PSB_S1,BVP_S1,"
		        + "N1_S2,N2_S2,VB1_S2,VB2_S2,PHA_S2,OIT_S2,OIP_S2,PSB_S2,BVP_S2,"
		        + "N1_S3,N2_S3,VB1_S3,VB2_S3,PHA_S3,OIT_S3,OIP_S3,PSB_S3,BVP_S3,"
		        + "N1_S4,N2_S4,VB1_S4,VB2_S4,PHA_S4,OIT_S4,OIP_S4,PSB_S4,BVP_S4,"
		        + "N1_S5,N2_S5,VB1_S5,VB2_S5,PHA_S5,OIT_S5,OIP_S5,PSB_S5,BVP_S5,"
		        + "N1_S6,N2_S6,VB1_S6,VB2_S6,PHA_S6,OIT_S6,OIP_S6,PSB_S6,BVP_S6,"
		        + "N1_S7,N2_S7,VB1_S7,VB2_S7,PHA_S7,OIT_S7,OIP_S7,PSB_S7,BVP_S7,"
		        + "N1_S8,N2_S8,VB1_S8,VB2_S8,PHA_S8,OIT_S8,OIP_S8,PSB_S8,BVP_S8,"
		        + "N1_S9,N2_S9,VB1_S9,VB2_S9,PHA_S9,OIT_S9,OIP_S9,PSB_S9,BVP_S9,"
		        + "N1_S0,N2_S0,VB1_S0,VB2_S0,PHA_S0,OIT_S0,OIP_S0,PSB_S0,BVP_S0,"
				+ "UPDATE_DATE)"  
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,"
				+ "?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,"
				+ "sysdate)";
		
		
		AcarsECVo_A07_A320_IAE ec = a07vo.getEc_i();
		AcarsEEVo_A07_A320_IAE ee = a07vo.getEe_i();
		AcarsN1Vo_A07_A320_IAE n1 = a07vo.getN1_i();
		AcarsSxVo_A07_A320_IAE s1=a07vo.getS1_i();
		AcarsSxVo_A07_A320_IAE s2=a07vo.getS2_i();
		AcarsSxVo_A07_A320_IAE s3=a07vo.getS3_i();
		AcarsSxVo_A07_A320_IAE s4=a07vo.getS4_i();
		AcarsSxVo_A07_A320_IAE s5=a07vo.getS5_i();
		AcarsSxVo_A07_A320_IAE s6=a07vo.getS6_i();
		AcarsSxVo_A07_A320_IAE s7=a07vo.getS7_i();
		AcarsSxVo_A07_A320_IAE s8=a07vo.getS8_i();
		AcarsSxVo_A07_A320_IAE s9=a07vo.getS9_i();
		AcarsSxVo_A07_A320_IAE s0=a07vo.getS0_i();
	    if(s0==null)
	    {
	    	throw new Exception("原始数据不完整,解析失败!");
	    }
//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),ec.getEcw4(),ec.getY1(),ec.getY2(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp(),ee.getEcw4(),
				n1.getE(),n1.getMax(),n1.getLim(),n1.getRef(),n1.getTol(),n1.getTtp(),n1.getEvm(),n1.getPara(),
				s1.getN1(),s1.getN2(),s1.getVb1(),s1.getVb2(),s1.getPha(),s1.getOit(),s1.getOip(),s1.getPsb(),s1.getBvp(),
		        s2.getN1(),s2.getN2(),s2.getVb1(),s2.getVb2(),s2.getPha(),s2.getOit(),s2.getOip(),s2.getPsb(),s2.getBvp(),
		        s3.getN1(),s3.getN2(),s3.getVb1(),s3.getVb2(),s3.getPha(),s3.getOit(),s3.getOip(),s3.getPsb(),s3.getBvp(),
		        s4.getN1(),s4.getN2(),s4.getVb1(),s4.getVb2(),s4.getPha(),s4.getOit(),s4.getOip(),s4.getPsb(),s4.getBvp(),
		        s5.getN1(),s5.getN2(),s5.getVb1(),s5.getVb2(),s5.getPha(),s5.getOit(),s5.getOip(),s5.getPsb(),s5.getBvp(),
		        s6.getN1(),s6.getN2(),s6.getVb1(),s6.getVb2(),s6.getPha(),s6.getOit(),s6.getOip(),s6.getPsb(),s6.getBvp(),
		        s7.getN1(),s7.getN2(),s7.getVb1(),s7.getVb2(),s7.getPha(),s7.getOit(),s7.getOip(),s7.getPsb(),s7.getBvp(),
		        s8.getN1(),s8.getN2(),s8.getVb1(),s8.getVb2(),s8.getPha(),s8.getOit(),s8.getOip(),s8.getPsb(),s8.getBvp(),
		        s9.getN1(),s9.getN2(),s9.getVb1(),s9.getVb2(),s9.getPha(),s9.getOit(),s9.getOip(),s9.getPsb(),s9.getBvp(),
		        s0.getN1(),s0.getN2(),s0.getVb1(),s0.getVb2(),s0.getPha(),s0.getOit(),s0.getOip(),s0.getPsb(),s0.getBvp()
				);
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A07 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
