package com.apms.bs.dataprase.impl;

import java.util.Date;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA36Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a36.AcarsE1Vo_A36;
import com.apms.bs.dataprase.vo.a36.AcarsS0Vo_A36;
import com.apms.bs.dataprase.vo.a36.AcarsS1Vo_A36;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A36报文解析实现类
 * @author zzy
 *
 */
public class A36DataParseImpl extends ReportContentParseClass{
	
	protected String asn;
	protected CommDMO dmo = new CommDMO();
	boolean isApuACRight=true;

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA36Vo_A320 a36vo = praseA36Data(content,trans_time);
		AcarsParseResult res;
		
		insertA36(msgno, a36vo);
		
		res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	/**
	 * 解析报文中文件内容
	 * @param graphStr
	 * @param transdate
	 * @return
	 * @throws Exception
	 */
	protected AcarsDfdA36Vo_A320 parseTextA36Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA36Vo_A320 a36vo = new AcarsDfdA36Vo_A320();
		String[] lines  = StringUtil.splitString2Array(graphStr, "\r\n", false);
		logger.debug("报文每行数据如下：");
		
		boolean isSep = false;
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			if(line.startsWith("E1")){
				a36vo.setE1(new AcarsE1Vo_A36(line));
				asn=a36vo.getE1().getAsn();
				a36vo.setE1Have(true);
			}else if(line.startsWith("S0")){
				a36vo.setS0(new AcarsS0Vo_A36(line,transdate,isSep));
			}else if(line.startsWith("S1")){
				a36vo.setS1(new AcarsS1Vo_A36(line,isSep));
			}
		}
		
		return a36vo;
	}
	
	/**
	 * 报文解析
	 * @param graphStr 报文内容
	 * @param transdate 报文传送日期 YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	public AcarsDfdA36Vo_A320 praseA36Data(String graphStr,String transdate) throws Exception{
		
		AcarsDfdA36Vo_A320 a36vo = parseTextA36Data(graphStr, transdate);
		if(a36vo.isE1Have() != true){
			//没有E1行数据的情况， 先查找最近的有没有A13或A36号报文，从A13报文上获取APU总小时数。
			//如果有A36报文，根据前一个A36报文的小时数加上当前报文的运行时间，得到APU总小时数
			//如果APU有拆装过，按拆装时间的小时循环
			String swapSql = "SELECT * FROM ( " 
					+ " SELECT S.SWAP_DATE,S.SWAP_ACTION,AC.AIRCRAFTSN ACNUM,A.APUSN "
					+ " ,S.TIME_ONINSTALL,S.CYCLE_ONINSTALL,RANK() OVER(ORDER BY S.SWAP_DATE DESC) RANKNUM " 
					+ " FROM L_APU_SWAPLOG S,B_AIRCRAFT AC,B_APU A "
					+ " WHERE AC.ID =S.AIRCRAFTID AND A.ID=S.APUID AND S.SWAP_ACTION =1 " 
					+ " AND AC.AIRCRAFTSN = ? AND SWAP_DATE < ? " 
					+ ") WHERE RANKNUM = 1 ";
			
			//查询最近的拆装时间
			HashVO[] swapvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, swapSql, headVo.getAcid(), headVo.getDateUtc());
			
			//Date stoptime = a36vo.getS0().getStoptime();
			String a13Sql = "SELECT * FROM (" +
				" SELECT T.MSG_NO,T.ACNUM,T.ASN,T.AHRS,T.ACYC,T.DATE_UTC, RANK() OVER(ORDER BY T.DATE_UTC DESC ) RANKNUM"+
				" FROM A_DFD_A13_LIST T WHERE T.ACNUM=? AND T.DATE_UTC < ?"+
				" ) WHERE RANKNUM = 1";
			
			String a36Sql = "SELECT * FROM (" + 
				" SELECT T.MSG_NO,T.ACNUM,T.ASN,T.AHRS,T.ACYC,T.RPTDATE, RANK() OVER(ORDER BY T.RPTDATE DESC ) RANKNUM" +
				" FROM A_DFD_A36_LIST T WHERE T.ACNUM=? AND T.RPTDATE < ?" +
				" ) WHERE RANKNUM = 1";
			
			HashVO[] a13vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, a13Sql, headVo.getAcid(), headVo.getDateUtc());
			HashVO[] a36vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, a36Sql, headVo.getAcid(), headVo.getDateUtc());
			
			int timemode = 0;//36,13,1分别代表取A13,A36和安装时间
			//获取最新的APU小时数,判断A13,A36和安装时间哪个更近
			if( swapvos.length > 0 ){
				Date swaptime = swapvos[0].getDateValue("SWAP_DATE");
				
				if(a36vos.length > 0 && a13vos.length > 0){
					Date a36time = a36vos[0].getDateValue("RPTDATE");
					Date a13time = a13vos[0].getDateValue("DATE_UTC");
					
					//取3者中最新的时间
					if(a36time.after(a13time)){
						if(a36time.after(swaptime)){
							timemode = 36;
						}else{
							timemode = 1;
						}
					}else{
						if(a13time.after(swaptime)){
							timemode = 13;
						}else{
							timemode = 1;
						}
					}
					
				}else if(a36vos.length > 0 && a13vos.length == 0){
					//从燃油报中取时间
					Date a36time = a36vos[0].getDateValue("RPTDATE");
					if(a36time.after(swaptime)){
						timemode = 36;
					}else{
						timemode = 1;
					}
					
				}else if(a36vos.length == 0 && a13vos.length > 0){
					//从发动机启动报文取时间
					Date a13time = a13vos[0].getDateValue("DATE_UTC");
					if(a13time.after(swaptime)){
						timemode = 13;
					}else{
						timemode = 1;
					}
				}else{
					//只有安装时间
					timemode = 1;
				}
			}else{//无安装时间
				if(a36vos.length > 0 && a13vos.length > 0){
					Date a36time = a36vos[0].getDateValue("RPTDATE");
					Date a13time = a13vos[0].getDateValue("DATE_UTC");
					
					if(a36time.after(a13time)){
						timemode = 36;
					}else{
						timemode = 13;
					}
					
				}else if(a36vos.length > 0 && a13vos.length == 0){
					//从燃油报中取时间
					timemode = 36;
					
				}else if(a36vos.length == 0 && a13vos.length > 0){
					//从发动机启动报文取时间
					timemode = 13;
				}else{
					//什么数据也没取到
					timemode = 0;
				}
			}
			
			String ahrs = "0";
			String acyc = "0";
			String asn = "xxxx";//末知
			if(timemode == 13){
				
				ahrs = a13vos[0].getStringValue("AHRS");
				acyc = a13vos[0].getStringValue("ACYC");
				asn = a13vos[0].getStringValue("ASN");
			}else if(timemode == 36){
				
				ahrs = a36vos[0].getStringValue("AHRS");
				acyc = a36vos[0].getStringValue("ACYC");
				asn = a36vos[0].getStringValue("ASN");
			}else if(timemode == 1){
				ahrs = swapvos[0].getStringValue("TIME_ONINSTALL");
				acyc = swapvos[0].getStringValue("CYCLE_ONINSTALL");
				asn = swapvos[0].getStringValue("APUSN");
			}
			
			AcarsE1Vo_A36 e1 = new AcarsE1Vo_A36();
			e1.setAcyc(acyc);
			e1.setAhrs(ahrs);
			e1.setAsn(asn);
			
			a36vo.setE1(e1);		
			
		}
		
		return a36vo;
	}
	
	public void insertA36(String msgno, AcarsDfdA36Vo_A320 a36vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql = "insert into a_dfd_a36_list(id, msg_no, acnum, rptdate, "
				+ "asn, ahrs, acyc, pfad, OPERATE_MINS,STOPTIME,VID,FUSED, UPDATETIME)"  
				+ " values(S_A_DFD_A38_LIST.nextval,?,?,?,"
				+ "?,?,?,?,?,?,?,?, sysdate)";
		
		AcarsE1Vo_A36 e1 = a36vo.getE1();
		AcarsS0Vo_A36 s0 = a36vo.getS0();
		AcarsS1Vo_A36 s1 = a36vo.getS1();

		double used = s1.getFused();
		//根据APU型号，决定取USED 还是FUSED2
		//如果APU是 131-9A的类型，那么这个数据就会采集，显示两位小数数值，如果是APS3200的APU 那么 左边的数据是不会显示的，只显示右边的两位小数数值
		String modelSql = "SELECT SUBMODEL fROM B_APU_MODEL T,B_APU A WHERE T.ID = A.APUMODELID AND A.APUSN='"+e1.getAsn()+"'";
		HashVO[] modelvos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, modelSql);
		if(modelvos.length > 0){
			String submodel = modelvos[0].getStringValue("SUBMODEL");
			if(submodel.contains("GTCP") ){
				used = s1.getFused();
			}else{
				used = s1.getFused2();
			}
		}
		
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,headVo.getAcid(),headVo.getDateUtc(),
				e1.getAsn(), e1.getAhrs(), e1.getAcyc(),e1.getPfad(),
				s0.getOperate_mins(),s0.getStoptime(),s0.getVid(), used );
			
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A36报文[msg_no]=["+msgno+"]入库成功！");

	}
	
	public String getAsn() {
		return asn;
	}

	public void setAsn(String asn) {
		this.asn = asn;
	}


}
