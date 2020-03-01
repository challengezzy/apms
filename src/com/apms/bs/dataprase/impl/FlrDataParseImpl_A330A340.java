package com.apms.bs.dataprase.impl;

import java.util.Date;
import org.apache.log4j.Logger;
import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.vo.AcarsCfdFlrVo;
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
public class FlrDataParseImpl_A330A340 {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private FlrDataParseImpl_A330A340(){
		
	}
	
	public static FlrDataParseImpl_A330A340 getInstance(){
		FlrDataParseImpl_A330A340 wrn = null;
		if(wrn==null){
			wrn=new FlrDataParseImpl_A330A340();
		}
		return wrn;
	}
	
	public AcarsParseResult parseContentData(String rptno,String msgno,String acnum,String telContent,
			String fltno,Date transtime) throws Exception {

		AcarsCfdFlrVo flrvo = praseData(telContent,msgno,transtime);
		insertCfdFlr(msgno, flrvo);
		
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
	public AcarsCfdFlrVo praseData(String graphStr,String msgno,Date transtime)
			throws Exception {
		AcarsCfdFlrVo flrvo = new AcarsCfdFlrVo();

		String[] lines = StringUtil.splitString2Array(graphStr, "FLR/FR", true);
		if(lines.length<=0){
			 lines = StringUtil.splitString2Array(graphStr, "FLR/DBNCCA01/FR", true);//兼容
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
				flrvo.setHead(new AcarsHeadVoCfd(line,rptYearStr,msgno,"FLR"));
			}else{
				line=line.replaceAll("\r\n", "").trim();
				String rptDateStr=lines[i].substring(0,6);//报文日期报头会用到
				
				String timeStr=line.substring(6, 12);
				
				String hourStr=timeStr.substring(0,2);
				String minStr=timeStr.substring(2,4);
//				String ssStr=timeStr.substring(4,6);
				try{
					if(Integer.parseInt(hourStr)>=24){
						throw new Exception("报文内容时间的小时为"+hourStr+",超过了23");
					}	
				}catch(NumberFormatException e){
					 throw new Exception(hourStr+"无法转化成数字");
				}
				try{
					if(Integer.parseInt(minStr)>=60){
						throw new Exception("报文内容时间的分钟为"+minStr+",超过了59");
					}
				}catch(NumberFormatException e){
					 throw new Exception(minStr+"无法转化成数字");
				}
//				try{
//					if(Integer.parseInt(ssStr)>=60){
//						throw new Exception("报文内容时间的秒数为"+ssStr+",超过了59");
//					}
//				}catch(NumberFormatException e){
//					throw new Exception(ssStr+"无法转化成数字");
//				}
				
				int hour=Integer.parseInt(timeStr.substring(0,2));
				int date=Integer.parseInt(rptDateStr.substring(4,6));
//				if((hour+8)/24>=1){//报文头时间转换成东八区时间=格林时间+8
//					hour=(hour+8)%24;
//					date=date+1;
//				}else{
//					hour=hour+8;
//				}
				int temp=new Date().getYear()+1900;
				String tempStr=String.valueOf(temp);
				String yearStr=tempStr.substring(2,4);
				int nowyear=Integer.parseInt(yearStr);
				if(Integer.parseInt(rptDateStr.substring(0,2))>nowyear){//如果日期大于现在时间的年份，数据出错，取transtime的年份
					if(transtime.getYear()>new Date().getYear()){
						rptDateStr="20"+String.valueOf(new Date().getYear()+1900).substring(2,4)+"-"+rptDateStr.substring(2,4)+"-"+String.valueOf(date);
					}else{
						rptDateStr="20"+String.valueOf(transtime.getYear()+1900).substring(2, 4)+"-"+rptDateStr.substring(2,4)+"-"+String.valueOf(date);
					}
				}else{
					rptDateStr="20"+rptDateStr.substring(0,2)+"-"+rptDateStr.substring(2,4)+"-"+String.valueOf(date);
				}
				String reporttimeStr=rptDateStr+" "+String.valueOf(hour)+":"+minStr+":"+"00";
				Date reporttime=DateUtil.moveSecond(DateUtil.StringToDate(reporttimeStr, "yyyy-MM-dd HH:mm:ss"),8*60*60);
				String reporttimestr=DateUtil.getDateStr(reporttime, "yyyy-MM-dd HH:mm:ss");
				flrvo.setReporttime(reporttimestr);//报文时间
				
				flrvo.setMsg_no(msgno);//消息编号
				
				String atano_major=line.substring(12, 14);
				String atano_minor=line.substring(14, 16);
				String atano_sub=line.substring(16, 18);
				flrvo.setAtano_major(atano_major);
				flrvo.setAtano_minor(atano_minor);
				flrvo.setAtano_sub(atano_sub);
				
				
				String phase=line.substring(18,20);
				flrvo.setPhase(phase);//飞行阶段
				
				String sourceAndContent=line.substring(20);//从故障源到后面再解
				String[] strArr=sourceAndContent.split(",");
				String fault_content="";
				fault_content=strArr[strArr.length-2];//故障内容
				flrvo.setFault_content(fault_content);
				String fault_source="";
				for(int k=0;k<strArr.length-2;k++){
					if(k==0){
						String arr[]=strArr[k].split(" ");
						if(arr.length<=0){
							fault_source=strArr[k];
						}else{
							fault_source+=arr[0];
						}
					}else if(!"".equals(strArr[i])){
						fault_source+="\r\n"+","+strArr[k];
					}
				}
				flrvo.setFault_source(fault_source);
			}
			
		}

		return flrvo;
	}
	/**
	 * 保存到数据库
	 * @param msgno
	 * @param flrvo
	 * @throws Exception
	 */
	public void insertCfdFlr(String msgno, AcarsCfdFlrVo flrvo)
			throws Exception {
		CommDMO dmo = new CommDMO();
		//查询关联的ID
		String flightidStr="select ffs.flt_pk from f_flight_schedule ffs where  ffs.ctd - 2/24 <= to_date('"+flrvo.getHead().getHeadtime()+
		"','YYYY-MM-DD HH24:MI:SS') and "+"to_date('"+flrvo.getHead().getHeadtime() +"','YYYY-MM-DD HH24:MI:SS')<=ffs.cta + 2/24 and ffs.flightno='"
			+flrvo.getHead().getFlightno()+"' and 	ffs.acnum='"+flrvo.getHead().getAcnum()+"' and rownum = 1";
		logger.info("执行语句:"+flightidStr);
		HashVO[] vos= dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, flightidStr);
		String flightid;
		if(vos.length>0){
			flightid=vos[0].getStringValue("flt_pk");
		}else{
			flightid=null;
		}
		
		String insertHeadSql="insert into A_CFD_HEAD(ID,MSG_NO,FLIGHTID,FLIGHTNO,ACNUM,APTCODE3,VAL_BJS,HEADTIME,RPTSEQ,RPTNO,ISRELATED,UPDATETIME)"
			+" values(s_a_cfd_head.nextval,"+flrvo.getHead().getMsg_no()+","+flightid+",'"+flrvo.getHead().getFlightno()+"','"+flrvo.getHead().getAcnum()
			+"','"+flrvo.getHead().getAptcode3()+"','"+flrvo.getHead().getVal_bjs()+"',to_date('"+flrvo.getHead().getHeadtime()+"', 'YYYY-MM-DD HH24:mi:ss')"
			+","+flrvo.getHead().getRptseq()+",'"+flrvo.getHead().getRptno()+"',decode("+flightid+",null,0,1),sysdate)";
		String insertContentSql="insert into A_CFD_FAULT(id,msg_no,atano_major,atano_minor,atano_sub,phase,fault_source,fault_content," 
			+"reporttime,updatetime) values(s_a_cfd_fault.nextval,"+flrvo.getMsg_no()+",'"+flrvo.getAtano_major()+"','"+flrvo.getAtano_minor()
			+"','"+flrvo.getAtano_sub()+"',"+flrvo.getPhase()+",'"+flrvo.getFault_source()+"','"
			+flrvo.getFault_content()+"',to_date('"+flrvo.getReporttime()+"', 'YYYY-MM-DD HH24:mi:ss'),sysdate)";
		logger.info("执行语句:"+insertHeadSql+"\n"+"执行语句:"+insertContentSql);
		String[] sqlArr=new String[2];
		sqlArr[0]=insertHeadSql;
		sqlArr[1]=insertContentSql;
		dmo.executeBatchByDS(ApmsConst.DS_APMS, sqlArr);
		logger.info("CFD的WRN报文[msg_no]=[" + msgno + "]入库成功！");

	}

}
