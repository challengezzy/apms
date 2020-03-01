package com.apms.bs.dataprase.impl;

import java.util.Date;
import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.vo.AcarsCfdSmdVo;
import com.apms.bs.dataprase.vo.AcarsHeadVoCfd;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * CFD的SMD报文
 * @author Administrator
 *
 */
public class SmdDataParseImpl {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private SmdDataParseImpl(){
		
	}
	
	public static SmdDataParseImpl getInstance(){
		SmdDataParseImpl wrn = null;
		if(wrn==null){
			wrn=new SmdDataParseImpl();
		}
		return wrn;
	}
	
	public AcarsParseResult parseContentData(String rptno,String msgno,String acnum,String telContent,
			String fltno,Date transtime) throws Exception {

		AcarsCfdSmdVo cfdvo = praseData(telContent,msgno,transtime);
		insertCfdSmd(msgno, cfdvo);
		
		AcarsParseResult res = new AcarsParseResult(
				ApmsVarConst.TELEGRAPH_PARSE_OK);

		return res;

	}

	/**
	 * 报文解析
	 * 
	 * @param graphStr
	 *            报文内容
	 * @param transdate
	 *            报文传送日期 YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public AcarsCfdSmdVo praseData(String graphStr,String msgno,Date transtime)
			throws Exception {
		if(graphStr.indexOf("QTB")!=-1){
			throw new Exception("该报文未传完！出现QTB");
		}
		AcarsCfdSmdVo smdvo = new AcarsCfdSmdVo();

		String[] lines = StringUtil.splitString2Array(graphStr, "SMD", true);
		
//		int rptYearStrIndex=lines[1].indexOf("/DM");//报文年数报头会用到
		String rptYearStr=String.valueOf(transtime.getYear()+1900)+"-"+String.valueOf(transtime.getMonth()+1);
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			if(line.startsWith("CFD")){
				smdvo.setHead(new AcarsHeadVoCfd(line,rptYearStr,msgno,"SMD"));
			}else{
				smdvo.setMsg_no(msgno);//报文编号
				String[] linesons = StringUtil.splitString2Array(line, "\r\n", true);
				for(int j=0;j<linesons.length;j++){
					String lineson=linesons[j].trim();
					if(lineson.startsWith("DATE")){
						String[] linearr = StringUtil.splitString2Array(lineson, ":", true);
						if(linearr.length>=2){
							String ropordTimeStr=String.valueOf(transtime.getYear()+1900)+"-"+ DateUtil.abbrMonthToNumber(linearr[1].trim().substring(0,3))+"-"+linearr[1].trim().substring(3);
							smdvo.setReporttime(ropordTimeStr);
						}
					}else if(lineson.startsWith("APU S/N")){
						String[] linearr = StringUtil.splitString2Array(lineson, ":", true);
						if(linearr.length>=2){
							String apusnStr=linearr[1].trim();
							smdvo.setApusn(apusnStr);
						}
					}else if(lineson.startsWith("HOURS")){
						String[] linearr = StringUtil.splitString2Array(lineson, ":", true);
						if(linearr.length>=2){
							String hoursStr=linearr[1].trim();
							smdvo.setHours(hoursStr);
						}
					}else if(lineson.startsWith("START ATTEMPTS")){
						String[] linearr = StringUtil.splitString2Array(lineson, ":", true);
						if(linearr.length>=2){
							String attemptsStr=linearr[1].trim();
							smdvo.setAttempts(attemptsStr);
						}
					}else if(lineson.startsWith("START CYCLES")){
						String[] linearr = StringUtil.splitString2Array(lineson, ":", true);
						if(linearr.length>=2){
							String cyclesStr=linearr[1].trim();
							smdvo.setCycles(cyclesStr);
						}
					}else if(lineson.startsWith("OIL LEVEL")){
						String[] linearr = StringUtil.splitString2Array(lineson, ":", true);
						if(linearr.length>=2){
							String levelStr=linearr[1].trim();
							smdvo.setOillevel(levelStr);
						}
					}
				}
			}
		}
		logger.debug("报文每行数据如下：");
		
		return smdvo;
	}
	/**
	 * 保存到数据库
	 * @param msgno
	 * @param smd
	 * @throws Exception
	 */
	public void insertCfdSmd(String msgno, AcarsCfdSmdVo smd)
			throws Exception {
		CommDMO dmo = new CommDMO();
		//查询关联的ID
		String flightidStr="select ffs.flt_pk from f_flight_schedule ffs where  ffs.ctd - 2/24 <= to_date('"+smd.getHead().getHeadtime()+
		"','YYYY-MM-DD HH24:MI:SS') and "+"to_date('"+smd.getHead().getHeadtime() +"','YYYY-MM-DD HH24:MI:SS')<=ffs.cta + 2/24 and ffs.flightno='"
		+smd.getHead().getFlightno()+"' and 	ffs.acnum='"+smd.getHead().getAcnum()+"' and rownum = 1";
		HashVO[] vos= dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, flightidStr);
		String flightid;
		if(vos.length>0){
			flightid=vos[0].getStringValue("flt_pk");
		}else{
			flightid=null;
		}
		String insertHeadSql="insert into A_CFD_HEAD(ID,MSG_NO,FLIGHTID,FLIGHTNO,ACNUM,APTCODE3,VAL_BJS,HEADTIME,RPTSEQ,RPTNO,ISRELATED,UPDATETIME)"
			+" values(s_a_cfd_head.nextval,"+smd.getHead().getMsg_no()+","+flightid+",'"+smd.getHead().getFlightno()+"','"+smd.getHead().getAcnum()
			+"','"+smd.getHead().getAptcode3()+"','"+smd.getHead().getVal_bjs()+"',to_date('"+smd.getHead().getHeadtime()+"', 'YYYY-MM-DD HH24:mi:ss')"
			+","+smd.getHead().getRptseq()+",'"+smd.getHead().getRptno()+"',decode("+flightid+",null,0,1),sysdate)";
		String insertContentSql="insert into A_CFD_SMD(id,msg_no,APUSN,HOURS,ATTEMPTS,CYCLES,OILLEVEL,reporttime,updatetime) values(s_A_CFD_SMD.nextval,"
			+smd.getMsg_no()+",'"+smd.getApusn()+"','"+smd.getHours()+"','"+smd.getAttempts()+"','"+smd.getCycles()+"','"+smd.getOillevel()+"',to_date('"+smd.getReporttime()+"', 'YYYY-MM-DD'),sysdate)";
		logger.info("执行语句:"+insertHeadSql+"\n"+"执行语句:"+insertContentSql);
		String[] sqlArr=new String[2];
		sqlArr[0]=insertHeadSql;
		sqlArr[1]=insertContentSql;
		dmo.executeBatchByDS(ApmsConst.DS_APMS, sqlArr);
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("CFD的SMD报文[msg_no]=[" + msgno + "]入库成功！");
			
				
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("CFD报文[msg_no]=["+msgno+"]入库成功！");
	}

}
