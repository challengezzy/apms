package com.apms.bs.dataprase.impl;

import java.util.ArrayList;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA28A32Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a28a32.AcarsEC_EEVo_A28A32_A320;
import com.apms.bs.dataprase.vo.a28a32.AcarsNxVo_A28A32_A320;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A28A32DataParseImpl_A320 extends ReportContentParseClass{
	
	ArrayList<AcarsNxVo_A28A32_A320> nxVoList;	
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA28A32Vo_A320 a28a32vo = praseA28A32Data(content,trans_time);
		insertA28A32(msgno, a28a32vo);
		
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
	public AcarsDfdA28A32Vo_A320 praseA28A32Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA28A32Vo_A320 a28a32vo = new AcarsDfdA28A32Vo_A320();
		nxVoList = new ArrayList<AcarsNxVo_A28A32_A320>();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		AcarsNxVo_A28A32_A320 nxVo;
		
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){
				a28a32vo.setEc(new  AcarsEC_EEVo_A28A32_A320(line));
			}else if(line.startsWith("EE")){
				a28a32vo.setEe(new  AcarsEC_EEVo_A28A32_A320(line));
			}else if(line.startsWith("N1")){
				nxVo = new AcarsNxVo_A28A32_A320(line,"N1");
				a28a32vo.setNx(nxVo);
				nxVoList.add(nxVo);		
			}else if(line.startsWith("N2")){
				nxVo = new AcarsNxVo_A28A32_A320(line,"N2");
				a28a32vo.setNx(nxVo);
				nxVoList.add(nxVo);
			}else if(line.startsWith("N3")){
				nxVo = new AcarsNxVo_A28A32_A320(line,"N3");
				a28a32vo.setNx(nxVo);
				nxVoList.add(nxVo);
			}else if(line.startsWith("N4")){
				nxVo = new AcarsNxVo_A28A32_A320(line,"N4");
				a28a32vo.setNx(nxVo);
				nxVoList.add(nxVo);
			}else if(line.startsWith("N5")){
				nxVo = new AcarsNxVo_A28A32_A320(line,"N5");
				a28a32vo.setNx(nxVo);
				nxVoList.add(nxVo);
			}else if(line.startsWith("N6")){
				nxVo = new AcarsNxVo_A28A32_A320(line,"N6");
				a28a32vo.setNx(nxVo);
				nxVoList.add(nxVo);
			}
			
		}
		

		
		return a28a32vo;
	}
	
	public void insertA28A32(String msgno, AcarsDfdA28A32Vo_A320 a28a32vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a2832_list(ID,MSG_NO,ACNUM,DATE_UTC,CODE,GROUPID,"
				+ "ESN_1,EHRS_1,ECYC_1,AP_1,"
				+ "ESN_2,EHRS_2,ECYC_2,AP_2,"
				+ "UPDATE_DATE)"
				+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		
		String insertSql1 = "insert into A_DFD_VIBRATIONRECORD(ID,MSG_NO,ROW_NUM,ROW_TITLE,IR_Z,IR_Y)"
			+ " values(S_A_DFD_HEAD.nextval,?,?,?,?,?)";
		
		AcarsEC_EEVo_A28A32_A320 ec = a28a32vo.getEc();
		AcarsEC_EEVo_A28A32_A320 ee = a28a32vo.getEe();
//		AcarsNxVo_A28A32_A320 nx = a28a32vo.getNx();

		String groupid = DateUtil.getDateStr(headVo.getDateUtc())+":"+headVo.getCnt();//组标识符，由报文日期+CNT组成
		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),groupid,
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),ec.getAp(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),ee.getAp());
		
		for (int g=0;g<nxVoList.size();g++){
			AcarsNxVo_A28A32_A320 nx = nxVoList.get(g);			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,g+1,nx.getNvalue(),nx.getIr_z(),nx.getIr_y());
		}		
				
		
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A28~A32 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
