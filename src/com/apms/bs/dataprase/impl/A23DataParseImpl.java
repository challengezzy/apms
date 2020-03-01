package com.apms.bs.dataprase.impl;

import java.text.ParseException;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA23Vo;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a23.AcarsSTempVo;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A23DataParseImpl extends ReportContentParseClass{
	
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA23Vo a23vo = praseA23Data(content,trans_time);
		insertA23(msgno, a23vo);
		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	/**
	 * 报文解析
	 * @param graphStr 报文内容
	 * @param transdate 报文头日期 YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	public AcarsDfdA23Vo praseA23Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA23Vo a23vo = new AcarsDfdA23Vo();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		//logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.indexOf("S1") > -1){
				AcarsSTempVo svo = new AcarsSTempVo(line,transdate);
				a23vo.setS1(svo);
				
				a23vo.setS1(new AcarsSTempVo(line,transdate));
			}else if(line.indexOf("S2") > -1){
				a23vo.setS2(new AcarsSTempVo(line,transdate));
			}else if(line.indexOf("S3") > -1){
				a23vo.setS3(new AcarsSTempVo(line,transdate));
			}else if(line.indexOf("S4") > -1){
				a23vo.setS4(new AcarsSTempVo(line,transdate));
			}else if(line.indexOf("S5") > -1){
				a23vo.setS5(new AcarsSTempVo(line,transdate));
			}else if(line.indexOf("S6") > -1){
				a23vo.setS6(new AcarsSTempVo(line,transdate));
			}else if(line.indexOf("S7") > -1){
				a23vo.setS7(new AcarsSTempVo(line,transdate));
			}
			
		}
		//修正s46日期数据
		a23vo.correctS46Date();
		
		return a23vo;
	}
	
	public void insertA23(String msgno, AcarsDfdA23Vo a23vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a23_list(ID,MSG_NO,ACNUM,DATE_UTC,CKPTTS1,SATS1,PRESS1,DATETIMES1,"
				+ "CKPTTS2,SATS2,PRESS2,DATETIMES2,CKPTTS3,SATS3,PRESS3,DATETIMES3,SATS4,CKPTTS4,PRESS4,DATETIMES4,"
				+ "SATS5,CKPTTS5,PRESS5,DATETIMES5,SATS6,CKPTTS6,PRESS6,DATETIMES6,SATS7,CKPTTS7,PRESS7,DATETIMES7,RECDATETIME)"
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		
		AcarsSTempVo s1 = a23vo.getS1();
		AcarsSTempVo s2 = a23vo.getS2();
		AcarsSTempVo s3 = a23vo.getS3();
		AcarsSTempVo s4 = a23vo.getS4();
		AcarsSTempVo s5 = a23vo.getS5();
		AcarsSTempVo s6 = a23vo.getS6();
		
		//zhangzy 报文中有s7行为空的情况，此时把s6行数据当s7数据用
		AcarsSTempVo s7 = a23vo.getS7();
		if(s7 == null)
			s7 = s6;
		if(s6==null)
		throw new Exception("原始报文不完整，缺少整行字段");
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),s1.getCkpt_t(),s1.getSat(),s1.getPress(),s1.getDateUtc()
				,s2.getCkpt_t(),s2.getSat(),s2.getPress(),s2.getDateUtc(),s3.getCkpt_t(),s3.getSat(),s3.getPress(),s3.getDateUtc()
				,s4.getCkpt_t(),s4.getSat(),s4.getPress(),s4.getDateUtc(),s5.getCkpt_t(),s5.getSat(),s5.getPress(),s5.getDateUtc()
				,s6.getCkpt_t(),s6.getSat(),s6.getPress(),s6.getDateUtc(),s7.getCkpt_t(),s7.getSat(),s7.getPress(),s7.getDateUtc() );
		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A23报文[msg_no]=["+msgno+"]入库成功！");

	}
	
	public static void main2(String[] args) throws Exception{
//		Date date = DateUtil.StringToDate("2013JAN07", "yyyyMMMdd");		
		String graphStr ="DFD"
			+"FI CA1702/AN B-6048"
			+"DT BJS PEK 040259 D11A"
			+"-  A23/A31923,1,3/CCB-6048,DEC03,235247,ZSHC,ZBAA,1701/"
			+"C111,00145,1000,34,0000,1,0000,33,1/"
			+"CE0055,-0379,---,---,6119,272,I71CA2/"
			+"S10250,0055,1758,DEC03,235142/"
			+"S20248,0055,1758,DEC03,235212/"
			+"S30246,0055,1759,DEC03,235242/"
			+"S40277,0033,1790,DEC04,025740/"
			+"S50276,0033,1790,DEC04,025810/"
			+"S60275,0040,1792,DEC04,025840/"
			+"S70250,0008,1731,DEC04,020025/";
		
		
		A23DataParseImpl s = new A23DataParseImpl();
		AcarsDfdA23Vo a23vo = s.praseA23Data(graphStr,"2013-01-12");
		//praseTeleGraphA23 ptgA23= a23vo.praseTeleGraphA23();
		
		System.out.println(a23vo.getS3().getPress());
		System.out.println(a23vo.getS3().getSat());
	}

}
