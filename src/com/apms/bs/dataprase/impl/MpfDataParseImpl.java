package com.apms.bs.dataprase.impl;

import java.util.Date;
import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.vo.AcarsCfdMpfVo;
import com.apms.bs.dataprase.vo.AcarsHeadVoCfd;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * WRN报文解析实现类
 * 
 * @author zzy
 * 
 */
public class MpfDataParseImpl {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private MpfDataParseImpl(){
		
	}
	
	public static MpfDataParseImpl getInstance(){
		MpfDataParseImpl wrn = null;
		if(wrn==null){
			wrn=new MpfDataParseImpl();
		}
		return wrn;
	}
	
	public AcarsParseResult parseContentData(String rptno,String msgno,String acnum,String telContent,
			String fltno,Date transtime) throws Exception {

		AcarsCfdMpfVo cfdvo = praseData(telContent,msgno,transtime);
		insertCfdMpf(msgno, cfdvo);
		
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
	public AcarsCfdMpfVo praseData(String graphStr,String msgno,Date transtime)
			throws Exception {
		if(graphStr.indexOf("QTB")!=-1){
			throw new Exception("该报文未传完！出现QTB");
		}
		AcarsCfdMpfVo mpfvo = new AcarsCfdMpfVo();

		String[] lines = StringUtil.splitString2Array(graphStr, "MPF/", true);
		
		int rptYearStrIndex=lines[1].indexOf("/DM");//报文年数报头会用到
		String rptYearStr=lines[1].substring(rptYearStrIndex+3,rptYearStrIndex+7);
		int temp=new Date().getYear()+1900;
		String tempStr=String.valueOf(temp);
		String yearStr=tempStr.substring(2,4);
		int nowyear=Integer.parseInt(yearStr);
		if(Integer.parseInt(rptYearStr.substring(0,2))>nowyear){//如果日期大于现在时间的年份，数据出错，取transtime的年份
			if(transtime.getYear()>new Date().getYear()){
				rptYearStr="20"+String.valueOf(nowyear)+"-"+rptYearStr.substring(2,4);
			}else{
				rptYearStr="20"+String.valueOf(transtime.getYear()+1900).substring(2,4)+"-"+rptYearStr.substring(2,4);
			}
			
		}else{
			rptYearStr="20"+rptYearStr.substring(0,2)+"-"+rptYearStr.substring(2,4);
		}
		logger.debug("报文每行数据如下：");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			if(line.startsWith("CFD")){
				mpfvo.setHead(new AcarsHeadVoCfd(line,rptYearStr,msgno,"MPF"));
			}else{
				mpfvo.setMsg_no(msgno);//报文编号
				if(line.indexOf("/DS")==-1){
					throw new Exception("无到港四字码标志/DS，内容格式不正确！");
				}else{
					String[] linesons = StringUtil.splitString2Array(line, "/DS", true);//为了更准确的解析报文内容，从/DS分开分别解析
					String firstStr=linesons[0];
					
					String[] firstArr=StringUtil.splitString2Array(firstStr, "/", true);
					for(int j=0;j<firstArr.length;j++){
						if(firstArr[j].startsWith("DM")){
							String hourStr=firstArr[j].substring(8,10);
							String minStr=firstArr[j].substring(10,12);
							String ssStr=firstArr[j].substring(12,14);
							if(Integer.parseInt(hourStr)>=24){
								throw new Exception("报文内容时间的小时为"+hourStr+",超过了23");
							}
							if(Integer.parseInt(minStr)>=60){
								throw new Exception("报文内容时间的分钟为"+minStr+",超过了59");
							}
							if(Integer.parseInt(ssStr)>=60){
								throw new Exception("报文内容时间的秒数为"+ssStr+",超过了59");
							}
							int hour=Integer.parseInt(hourStr);
							int date=Integer.parseInt(firstArr[j].substring(6,8));
//							if((hour+8)/24>=1){//报文头时间转换成东八区时间=格林时间+8
//								hour=(hour+8)%24;
//								date=date+1;
//							}else{
//								hour=hour+8;
//							}
							String reporttimeStr;
							if(Integer.parseInt(firstArr[j].substring(2,4))>nowyear){//如果日期大于现在时间的年份
								if(transtime.getYear()>new Date().getYear()){
									reporttimeStr="20"+String.valueOf(nowyear)+"-"+firstArr[j].substring(4,6)+"-"+date
									+" "+hour+":"+minStr+":"+ssStr;
								}else{
									reporttimeStr="20"+String.valueOf(transtime.getYear()+1900).substring(2,4)+"-"+firstArr[j].substring(4,6)+"-"+date
									+" "+hour+":"+minStr+":"+ssStr;
								}
							}else{
								reporttimeStr="20"+firstArr[j].substring(2,4)+"-"+firstArr[j].substring(4,6)+"-"+date
								+" "+hour+":"+minStr+":"+ssStr;
							}
							Date reporttime=DateUtil.moveSecond(DateUtil.StringToDate(reporttimeStr, "yyyy-MM-dd HH:mm:ss"),8*60*60);
							String reporttimestr=DateUtil.getDateStr(reporttime, "yyyy-MM-dd HH:mm:ss");
							mpfvo.setReporttime(reporttimestr);//报文时间
						}
						if(firstArr[j].startsWith("DA")){
							String aptcode4_dep=firstArr[j].substring(2);//离港四字码
							mpfvo.setAptcode4_dep(aptcode4_dep);
						}
					}
					String secondStr=linesons[1];
					mpfvo.setAptcode4_arr(secondStr.substring(0,4));
					if(secondStr.indexOf("NO WARNING")!=-1){//如果无故障和告警信息，之所以是no warning是因为后面一个词有的是or有的是and
						mpfvo.setIsfault("0");
						mpfvo.setIswarning("0");
						mpfvo.setReportcontent("NO WARNING AND FAULT MESSAGE");
					}else{
						if(secondStr.indexOf("/WN")==-1){//是否有告警信息
							mpfvo.setIswarning("0");
						}else{
							mpfvo.setIswarning("1");
						}
						if(secondStr.indexOf("/FR")==-1){//是否有故障信息
							mpfvo.setIsfault("0");
						}else{
							mpfvo.setIsfault("1");
						}
						String faultAndWrnStr=secondStr.substring(4);//故障信息和警告信息的解析
						if(faultAndWrnStr.indexOf("/FR")!=-1){
							String[] faultAndWrnArr=StringUtil.splitString2Array(faultAndWrnStr, "/FR", true);
							String frs="";//故障信息
							String wns="";//警告信息
							for(int k=0;k<faultAndWrnArr.length;k++){
								if(faultAndWrnArr[k].startsWith("/WN")){
									String[] wrnsArr=faultAndWrnArr[k].substring(3).split(",");
									for(int m=0;m<wrnsArr.length;m++){
										wns=wns+"告警："+wrnsArr[m].substring(20)+"\n";
									}
								}else if(!faultAndWrnArr[k].equals("")){
									frs=frs+"故障："+faultAndWrnArr[k].substring(20,faultAndWrnArr[k].indexOf("/ID"))+"\n";
								}
							}
							mpfvo.setReportcontent(frs+wns);
						}else{
							String[] wrnsArr=faultAndWrnStr.substring(3).split(",");
							String wrns="";
							for(int m=0;m<wrnsArr.length;m++){
								wrns=wrns+"告警："+wrnsArr[m].substring(20)+"\n";
							}
							mpfvo.setReportcontent(wrns);
						}
						
					}
				}
			}
		}
		return mpfvo;
	}
	/**
	 * 保存到数据库
	 * @param msgno
	 * @param mpf
	 * @throws Exception
	 */
	public void insertCfdMpf(String msgno, AcarsCfdMpfVo mpf)
			throws Exception {
		CommDMO dmo = new CommDMO();
		//查询关联的ID
		String flightidStr="select ffs.flt_pk from f_flight_schedule ffs where  ffs.ctd - 2/24 <= to_date('"+mpf.getHead().getHeadtime()+
		"','YYYY-MM-DD HH24:MI:SS') and "+"to_date('"+mpf.getHead().getHeadtime() +"','YYYY-MM-DD HH24:MI:SS')<=ffs.cta + 2/24 and ffs.flightno='"
		+mpf.getHead().getFlightno()+"' and 	ffs.acnum='"+mpf.getHead().getAcnum()+"' and rownum = 1";
		HashVO[] vos= dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, flightidStr);
		String flightid;
		if(vos.length>0){
			flightid=vos[0].getStringValue("flt_pk");
		}else{
			flightid=null;
		}
		String insertHeadSql="insert into A_CFD_HEAD(ID,MSG_NO,FLIGHTID,FLIGHTNO,ACNUM,APTCODE3,VAL_BJS,HEADTIME,RPTSEQ,RPTNO,ISRELATED,UPDATETIME)"
			+" values(s_a_cfd_head.nextval,"+mpf.getHead().getMsg_no()+","+flightid+",'"+mpf.getHead().getFlightno()+"','"+mpf.getHead().getAcnum()
			+"','"+mpf.getHead().getAptcode3()+"','"+mpf.getHead().getVal_bjs()+"',to_date('"+mpf.getHead().getHeadtime()+"', 'YYYY-MM-DD HH24:mi:ss')"
			+","+mpf.getHead().getRptseq()+",'"+mpf.getHead().getRptno()+"',decode("+flightid+",null,0,1),sysdate)";
		String insertContentSql="insert into a_cfd_mpf(id,msg_no,aptcode4_dep,aptcode4_arr,iswarning,isfault,reportcontent,reporttime,updatetime) values(s_a_cfd_mpf.nextval,"
			+mpf.getMsg_no()+",'"+mpf.getAptcode4_dep()+"','"+mpf.getAptcode4_arr()+"',"+mpf.getIswarning()+","+mpf.getIsfault()+",'"+mpf.getReportcontent()+"',to_date('"+mpf.getReporttime()+"', 'YYYY-MM-DD HH24:mi:ss'),sysdate)";
		logger.info("执行语句:"+insertHeadSql+"\n"+"执行语句:"+insertContentSql);
		String[] sqlArr=new String[2];
		sqlArr[0]=insertHeadSql;
		sqlArr[1]=insertContentSql;
		dmo.executeBatchByDS(ApmsConst.DS_APMS, sqlArr);
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("CFD的WRN报文[msg_no]=[" + msgno + "]入库成功！");

	}

}
