package com.apms.bs.dataprase.impl;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA38Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a38.AcarsE1Vo_A38;
import com.apms.bs.dataprase.vo.a38.AcarsN1N2Vo_A38;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A23报文解析实现类
 * @author zzy
 *
 */
public class A38DataParseImpl extends ReportContentParseClass{
	protected String ct5atp_t1;
	protected String ct5atp_t2;
	protected String asn;
	protected CommDMO dmo = new CommDMO();
	boolean isApuACRight=true;

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA38Vo_A320 a38vo = praseA38Data(content,trans_time);
		AcarsParseResult res;
		boolean isApuACRight=checkApuMatch();
		if(isApuACRight){
			insertA38(msgno, a38vo);
			res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		}else{
			res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_WAIT);
		}
		
		return res;
		
	}
	
	/**
	 * 报文解析
	 * @param graphStr 报文内容
	 * @param transdate 报文传送日期 YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	protected AcarsDfdA38Vo_A320 praseA38Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA38Vo_A320 a38vo = new AcarsDfdA38Vo_A320();
		String[] lines  = StringUtil.splitString2Array(graphStr, "\r\n", false);
		logger.debug("报文每行数据如下：");
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			if(line.startsWith("E1")){
				a38vo.setE1(new AcarsE1Vo_A38(line));
				asn=a38vo.getE1().getAsn();
			}else if(line.startsWith("N1")){
				a38vo.setN1(new AcarsN1N2Vo_A38(line,false));
			}else if(line.startsWith("N2")){
				a38vo.setN2(new AcarsN1N2Vo_A38(line,false));
			}else if(line.startsWith("T1")){
				String ct5atp_t1Str=line.substring(2).trim();
				if(ct5atp_t1Str.contains("---")||ct5atp_t1Str.contains("XXX")){
					ct5atp_t1=null;
				}else{
					ct5atp_t1=ct5atp_t1Str;
					if(ct5atp_t1.endsWith("/")){
						ct5atp_t1=ct5atp_t1.substring(0, ct5atp_t1.length()-1);
					}
				}
			}else if(line.startsWith("T2")){
				String ct5atp_t2Str=line.substring(2).trim();
				if(ct5atp_t2Str.contains("---")||ct5atp_t2Str.contains("XXX")){
					ct5atp_t2=null;
				}else{
					ct5atp_t2=ct5atp_t2Str;
					if(ct5atp_t2.endsWith("/")){
						ct5atp_t2=ct5atp_t2.substring(0, ct5atp_t2.length()-1);
					}
				};
			}
		}
		
		return a38vo;
	}
	
	public boolean checkApuMatch() throws Exception{
		//判断ASN和飞行号是否对应
		String check_apu ="select AIRCRAFTSN,TOTALTIME,TOTALCYCLE,APUSN from b_apu b, b_aircraft t " +
				"where b.aircraftid =t.id and aircraftsn=? and apusn=?";
		HashVO[] vo_apus =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, check_apu, headVo.getAcid(),asn);
		if(vo_apus.length > 0){
			isApuACRight = true;
			
		}else{
			isApuACRight = false;
		}
		
		return isApuACRight;
	}
	
	public void insertA38(String msgno, AcarsDfdA38Vo_A320 a38vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a38_list(id, msg_no, acnum, rptdate, code,"
				+ "asn_e1, ahrs_e1, acyc_e1, pfad_e1, ct5atp_n1, cptatp_n1, cwfatp_n1, igvatp_n1,"
				+ "bdtmax_n1, ct5atp_t1, ct5atp_n2, cptatp_n2, cwfatp_n2, igvatp_n2, ct5atp_t2, bdtmax_n2,UPDATE_DATE)"  //1
				+ " values(S_A_DFD_A38_LIST.nextval,"
				+ "?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,"
				+ "sysdate)";
		
		
		AcarsE1Vo_A38 e1 = a38vo.getE1();
		AcarsN1N2Vo_A38 n1 = a38vo.getN1();
		AcarsN1N2Vo_A38 n2 = a38vo.getN2();

		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				e1.getAsn(),e1.getAhrs(),e1.getAcyc(),e1.getPfad(),n1.getCt5atp(),n1.getCptatp(),n1.getCwfatp(),n1.getIgvatp(),
				n1.getBdtmax(),ct5atp_t1,n2.getCt5atp(),n2.getCptatp(),n2.getCwfatp(),n2.getIgvatp(),ct5atp_t2,n2.getBdtmax());
			
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A38 报文[msg_no]=["+msgno+"]入库成功！");

	}
	
	public String getAsn() {
		return asn;
	}

	public void setAsn(String asn) {
		this.asn = asn;
	}


}
