package com.apms.bs.dataprase.impl;

import java.util.Date;
import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.vo.AcarsCfdWrnVo;
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
public class WrnDataParseImpl {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private WrnDataParseImpl(){
		
	}
	
	public static WrnDataParseImpl getInstance(){
		WrnDataParseImpl wrn = null;
		if(wrn==null){
			wrn=new WrnDataParseImpl();
		}
		return wrn;
	}
	
	public AcarsParseResult parseContentData(String rptno,String msgno,String acnum,String telContent,
			String fltno,Date transtime) throws Exception {

		AcarsCfdWrnVo wrnvo = praseData(telContent,msgno,transtime);
		insertCfdWrn(msgno, wrnvo);
		
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
	public AcarsCfdWrnVo praseData(String graphStr,String msgno,Date transtime)
			throws Exception {
		AcarsCfdWrnVo wrnvo = new AcarsCfdWrnVo();

		String[] lines = StringUtil.splitString2Array(graphStr, "WRN/WN", true);
		if(lines.length<=0){
			 lines = StringUtil.splitString2Array(graphStr, "WRN/DBNCCA01/WN", true);//兼容
		}
		String rptYearStr=lines[1].substring(0,6);//报文年数报头会用到
		
		if(Integer.parseInt(rptYearStr.substring(0,2))>Integer.parseInt(String.valueOf((new Date().getYear()+1900)).substring(0, 2))){//如果日期大于现在时间的年份
			if(transtime.getYear()>new Date().getYear()){
				rptYearStr="20"+String.valueOf(new Date().getYear()+1900).substring(2, 4)+"-"+rptYearStr.substring(2,4);
			}else{
				rptYearStr="20"+String.valueOf(transtime.getYear()+1900).substring(2, 4)+"-"+rptYearStr.substring(2,4);
			}
		}else{
			rptYearStr="20"+rptYearStr.substring(0,2)+"-"+rptYearStr.substring(2,4);
		}
		logger.debug("报文每行数据如下：");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			logger.debug("第" + i + "行:" + line);
			if(line.startsWith("CFD")){
				wrnvo.setHead(new AcarsHeadVoCfd(line,rptYearStr,msgno,"WRN"));
			}else{
				line=line.replaceAll("\r\n", "").trim();
				String rptDateStr=lines[i].substring(0,6);//报文日期报头会用到
				String timeStr=line.substring(6, 12);
				
				String hourStr=timeStr.substring(0,2);
				String minStr=timeStr.substring(2,4);
				String ssStr=timeStr.substring(4,6);
				if(Integer.parseInt(hourStr)>=24){
					throw new Exception("报文内容时间的小时为"+hourStr+",超过了23");
				}
				if(Integer.parseInt(minStr)>=60){
					throw new Exception("报文内容时间的分钟为"+minStr+",超过了59");
				}
				if(Integer.parseInt(ssStr)>=60){
					throw new Exception("报文内容时间的秒数为"+ssStr+",超过了59");
				}
				int date=Integer.parseInt(rptDateStr.substring(4,6));
				int hour=Integer.parseInt(hourStr);
//				if((hour+8)/24>=1){//报文头时间转换成东八区时间=格林时间+8
//					hour=(hour+8)%24;
//					date=date+1;
//				}else{
//					hour=hour+8;
//				}
				int tempY=new Date().getYear()+1900;
				String tempStr=String.valueOf(tempY);
				String yearStr=tempStr.substring(2,4);
				int nowyear=Integer.parseInt(yearStr);
				if(Integer.parseInt(rptDateStr.substring(0,2))>nowyear){//如果日期大于现在时间的年份
					if(transtime.getYear()>new Date().getYear()){
						rptDateStr="20"+String.valueOf(new Date().getYear()+1900).substring(2,4)+"-"+rptDateStr.substring(2,4)+"-"+String.valueOf(date);
					}else{
						rptDateStr="20"+String.valueOf(transtime.getYear()+1900).substring(2, 4)+"-"+rptDateStr.substring(2,4)+"-"+String.valueOf(date);
					}
				}else{
					rptDateStr="20"+rptDateStr.substring(0,2)+"-"+rptDateStr.substring(2,4)+"-"+String.valueOf(date);
				}
				String reporttimeStr=rptDateStr+" "+String.valueOf(hour)+":"+minStr+":"+ssStr;
				Date reporttime=DateUtil.moveSecond(DateUtil.StringToDate(reporttimeStr, "yyyy-MM-dd HH:mm:ss"),8*60*60);
				String reporttimestr=DateUtil.getDateStr(reporttime, "yyyy-MM-dd HH:mm:ss");
				wrnvo.setReporttime(reporttimestr);
				
				wrnvo.setMsg_no(msgno);
				
				String atano_major=line.substring(12, 14);
				String atano_minor=line.substring(14, 16);
				String atano_sub=line.substring(16, 18);

				wrnvo.setAtano_major(atano_major);
				wrnvo.setAtano_minor(atano_minor);
				wrnvo.setAtano_sub(atano_sub);
				
				String phase=line.substring(18,20);
				try{
					Integer.parseInt(phase);
				}catch(NumberFormatException e){
					throw new NumberFormatException("'"+phase+"'无法转换成数字格式");
				}
				wrnvo.setPhase(phase);//飞行阶段
				
				String warn_content=line.substring(20,line.length());
				wrnvo.setWarn_content(warn_content);
			}
			
		}

		return wrnvo;
	}
	/**
	 * 保存到数据库
	 * @param msgno
	 * @param wrnvo
	 * @throws Exception
	 */
	public void insertCfdWrn(String msgno, AcarsCfdWrnVo wrnvo)
			throws Exception {
		CommDMO dmo = new CommDMO();
		//查询关联的ID
		String flightidStr="select ffs.flt_pk from f_flight_schedule ffs where  ffs.ctd - 2/24 <= to_date('"+wrnvo.getHead().getHeadtime()+
		"','YYYY-MM-DD HH24:MI:SS') and "+"to_date('"+wrnvo.getHead().getHeadtime() +"','YYYY-MM-DD HH24:MI:SS')<=ffs.cta + 2/24 and ffs.flightno='"
		+wrnvo.getHead().getFlightno()+"' and 	ffs.acnum='"+wrnvo.getHead().getAcnum()+"' and rownum = 1";
		HashVO[] vos= dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, flightidStr);
		String flightid;
		if(vos.length>0){
			flightid=vos[0].getStringValue("flt_pk");
		}else{
			flightid=null;
		}
		//插入头信息
		String insertHeadSql="insert into A_CFD_HEAD(ID,MSG_NO,FLIGHTID,FLIGHTNO,ACNUM,APTCODE3,VAL_BJS,HEADTIME,RPTSEQ,RPTNO,ISRELATED,UPDATETIME)"
			+" values(s_a_cfd_head.nextval,"+wrnvo.getHead().getMsg_no()+","+flightid+",'"+wrnvo.getHead().getFlightno()+"','"+wrnvo.getHead().getAcnum()
			+"','"+wrnvo.getHead().getAptcode3()+"','"+wrnvo.getHead().getVal_bjs()+"',to_date('"+wrnvo.getHead().getHeadtime()+"', 'YYYY-MM-DD HH24:mi:ss')"
			+","+wrnvo.getHead().getRptseq()+",'"+wrnvo.getHead().getRptno()+"',decode("+flightid+",null,0,1),sysdate)";
		//插入内容信息
		String insertContentSql="insert into A_CFD_WARNING(id,msg_no,atano_major,atano_minor,atano_sub,phase,warn_content,reporttime,updatetime) values(s_a_cfd_warning.nextval,"
			+wrnvo.getMsg_no()+",'"+wrnvo.getAtano_major()+"','"+wrnvo.getAtano_minor()+"','"+wrnvo.getAtano_sub()+"',"+wrnvo.getPhase()+",'"+wrnvo.getWarn_content()+"',to_date('"+wrnvo.getReporttime()+"', 'YYYY-MM-DD HH24:mi:ss'),sysdate)";
		//
		logger.info("执行语句:"+insertHeadSql+"\n"+"执行语句:"+insertContentSql);
		String[] sqlArr=new String[2];
		sqlArr[0]=insertHeadSql;
		sqlArr[1]=insertContentSql;
		dmo.executeBatchByDS(ApmsConst.DS_APMS, sqlArr);
		logger.info("CFD的WRN报文[msg_no]=[" + msgno + "]入库成功！");

	}

}
