package com.apms.bs.dataprase;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.impl.FlrDataParseImpl;
import com.apms.bs.dataprase.impl.FlrDataParseImpl_A330A340;
import com.apms.bs.dataprase.impl.MpfDataParseImpl;
import com.apms.bs.dataprase.impl.MpfDataParseImpl_A330A340;
import com.apms.bs.dataprase.impl.SmdDataParseImpl;
import com.apms.bs.dataprase.impl.WrnDataParseImpl;
import com.apms.bs.dataprase.impl.WrnDataParseImpl_A330A340;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.util.DateUtil;
import com.apms.vo.ApmsVarConst;

/**
 *报文数据解析服务
 *@date Dec 1, 2012
 **/
public class DataParseService {
	
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static DataParseService praseService = null;
	
	private DataParseClassFactory parseFactory;
	
	private String updateDfdSql = "UPDATE A_ACARS_TELEGRAPH_DFD T SET T.ERRINT = ?,ERRMESSAGE=?,FDIMUVERSION=?,PARSETIME=SYSDATE WHERE T.MSG_NO = ?";
	
	private String updateCfdSql = "UPDATE A_ACARS_TELEGRAPH_CFD T SET T.ERRINT = ?,ERRMESSAGE=?,PARSETIME=SYSDATE WHERE T.MSG_NO = ?";
	
	public static DataParseService getInstance(){
		if(praseService == null)
			praseService = new DataParseService();
		
		return praseService;
	}
	
	/**
	 * 解析所有类型的未解析报文
	 * @throws Exception
	 */
	public String parseAcarsReport(String msgno,String reportNo,String acnum) throws Exception{
		return parseAcarsReport(msgno,reportNo,acnum,null,null);
	}
	/**
	 * 解析所有类型的未解析报文
	 * @throws Exception
	 */
	public String parseAcarsReport(String reportNo,String acnum) throws Exception{
		return parseAcarsReport(null,reportNo,acnum,null,null);
	}
	
	/**
	 * 解析指定编号的报文
	 * @param reportNo
	 * @throws Exception
	 */
	public String parseAcarsReport(String reportNo) throws Exception{
		return parseAcarsReport(null,reportNo,null,null,null);
	}
	
	
	
	/**
	 * 报文解析
	 * @param msgno
	 * @param reportNo 报文编号，为null时解析所有类型报文
	 * @param acnum 飞机号，为null时解析所有飞机报文
	 * @throws Exception
	 */
	public String parseAcarsReport(String msgno,String reportNo,String acnum,String beginDate,String endDate) throws Exception{
		HashVO[] vos= getUnprasedTeleGraph(msgno,reportNo, acnum,beginDate,endDate,false); //查询未解析的报文
		return parseAcarsReport(vos);
		
	}
	
	/**
	 * 报文解析
	 * @param msgno
	 * @param reportNo 报文编号，为null时解析所有类型报文
	 * @param acnum 飞机号，为null时解析所有飞机报文
	 * @throws Exception
	 */
	public String parseAcarsReport(String msgno,String reportNo,String acnum,String beginDate,String endDate,boolean isOrderby) throws Exception{
		HashVO[] vos= getUnprasedTeleGraph(msgno,reportNo, acnum,beginDate,endDate,isOrderby); //查询未解析的报文
		return parseAcarsReport(vos);
		
	}
	
	/**
	 * 报文内容解析
	 * @param vos 未解析的报文列表
	 * @throws Exception
	 */
	private String parseAcarsReport(HashVO[] vos) throws Exception{		
		parseFactory = DataParseClassFactory.getInstance();
		CommDMO dmo = new CommDMO();		
		
		int num_ok = 0;
		int num_err = 0;
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			
			String acnum = vo.getStringValue("acnum");
			String rptno = vo.getStringValue("rptno");
			String msgno = vo.getStringValue("msg_no");
			String telContent = vo.getStringValue("tel_content");
			Date trans_time = vo.getDateValue("transdate");
			Date trans_time_full = null;
			String transtimefull = vo.getStringValue("trans_time");
			String dmu = null;
			
			try {
				trans_time_full = DateUtil.StringToDate(transtimefull, "yyyy-MM-dd HH:mm:ss");
			} catch (Exception e) {
				logger.debug("date_utc转换为日期异常！");
			}

			String acmodel = vo.getStringValue("modelcode");//机型
			
			//logger.debug(telContent);
			try{
				//如果有报文未传完标记，直接返回
				if (telContent.contains("QTB")){
					//logger.debug("报文未传完");
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateDfdSql,ApmsVarConst.TELEGRAPH_PARSE_QTB,"报文中包含[QTB]，内容未传完",dmu,msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num_err ++;
					continue;
				} 
				
				//1，根据机型获取head的解析类实现
				ReportHeadParseClass headClass = parseFactory.getHeadParseClass(acmodel);
				if(headClass == null){
					String reason = "未找到机型["+acmodel+"]对应的报文["+rptno+"]头解析实现类!";
					//logger.info(reason);
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateDfdSql, ApmsVarConst.TELEGRAPH_PARSE_HEADIML_NOTEXISTS, reason,dmu, msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num_err ++;
					continue;
				}
				
				//2，解析报文Head的数据
				AcarsHeadVo headVo = headClass.parseHeadData(vo, msgno, telContent, trans_time,trans_time_full);
				//兼容报文中飞机号为空的情况2014-12-23 zhangzy
				if(headVo.getAcid() == null || headVo.getAcid().length()==0){
					headVo.setAcid(acnum);
				}
				headClass.setBasicInfo(msgno, rptno, acmodel);
				//3,从Head中查询出机上软件版本号dmu
				dmu =headVo.getDmu();
				if(dmu == null || dmu.length()==0 ){ //dmu为空则无法进行下一步的解析
					String reason = "软件版本号为空,机型["+acmodel+"]对应的报文["+rptno+"]头解析异常!";
					logger.debug(reason);
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateDfdSql, ApmsVarConst.TELEGRAPH_PARSE_FDIMU_NULL, reason, dmu,msgno);
					dmo.commit(ApmsConst.DS_APMS);
					num_err ++;
					continue;
				}
				
				//4 根据机上软件版本号dmu，获取报文body部分的解析实现类
				ReportContentParseClass parseClass = parseFactory.getDataParseClass(headVo.getDmu(),rptno);
				
				if(parseClass == null){ //针对新软件版本号的情况，不记录解析异常，以便区分
					String reason = "未找到软件版本["+headVo.getDmu()+"]、报文编号["+rptno+"]对应的报文内容解析实现类!";
					logger.debug(reason);
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateDfdSql, ApmsVarConst.TELEGRAPH_PARSE_UNSUPPORT_FDIMU, reason,dmu, msgno);
				}else{
					parseClass.setHeadVo(headVo);
					parseClass.setHeadParseClass(headClass);
					parseClass.setBasicInfo(msgno, rptno, acmodel);
					//5，调用解析实现类，进行报文数据的解析(包含入库)
					AcarsParseResult result = parseClass.parseContentData(vo, msgno, telContent, headVo.getDate_utc());
					if (result.getStatus() == 3) {
						logger.info("DFD报文msg_no=[" + msgno + "]解析未解析！");
						dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateDfdSql, result.getStatus(), result.getReason(),dmu, msgno);
					} else {

						logger.debug("DFD报文msg_no=[" + msgno + "]解析成功！");
						dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateDfdSql, result.getStatus(), result.getReason(),dmu, msgno);
					}
				}
				
				 num_ok++;
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("DFD报文msg_no=["+msgno+"]解析失败！" + e.toString());
				dmo.rollback(ApmsConst.DS_APMS);
				try{
					dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateDfdSql, ApmsVarConst.TELEGRAPH_PARSE_ERROR,e.toString(),dmu,msgno);
				}catch(Exception l){
					l.printStackTrace();
					logger.error("DFD报文msg_no=["+msgno+"]解析失败！" + l.toString());
					dmo.rollback(ApmsConst.DS_APMS);
				}finally{
					dmo.commit(ApmsConst.DS_APMS);
				}
				
				dmo.commit(ApmsConst.DS_APMS);
				num_err ++;
				
			}
		    finally{
				dmo.commit(ApmsConst.DS_APMS);
			}
		}
		String parseMessage = "本次共解析DFD报文[" + (num_ok+num_err) + "]条，成功["+num_ok+"]条，失败["+num_err+"]条";
		logger.info(parseMessage);
		
		return parseMessage;
	}
	
	public String parseApuAcarsReport(String acNum,String beginDate,String endDate) throws Exception{
		String parseLog = "";
//		ArrayList<String> rptnoList = new ArrayList<String>();
//		rptnoList.add(ApmsVarConst.RPTNO_A13);
//		rptnoList.add(ApmsVarConst.RPTNO_A14);
		
		//解析A13\A14
		HashVO[] vos = getUnprasedApu("A", acNum,beginDate,endDate);
		String log1 = parseAcarsReport(vos);
		parseLog += "\n" + log1;
		
//		//解析A13\A14
//		rptnoList = new ArrayList<String>();
//		rptnoList.add(ApmsVarConst.RPTNO_R13);
//		rptnoList.add(ApmsVarConst.RPTNO_R14);
		
		vos = getUnprasedApu("R", acNum ,beginDate,endDate);
		log1 = parseAcarsReport(vos);
		parseLog += "\n" + log1;
		
		return parseLog;
	}
	public String parseCFDAcarsReport(String rptNo,String acNum,String beginDate,String endDate)throws Exception{
		return parseCFDAcarsReport(null,rptNo,acNum,beginDate,endDate);
	}
	/**
	 * 解析cfd报文
	 * @param acNum
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String parseCFDAcarsReport(String msg_no,String rptNo,String acNum,String beginDate,String endDate) throws Exception{
		CommDMO dmo = new CommDMO();
		//查询未解析的CFD报文
		HashVO[] vos = getUnprasedCFD(msg_no,rptNo, acNum,beginDate,endDate);
		if(vos.length<=0){
			logger.info("没有查询到有要解析的报文！");
		}
		int num_ok = 0;
		int num_err = 0;
		for(int i=0;i<vos.length;i++){// t.ac_id acnum,t.msg_no,t.rptno,t.ac_id,t.flt_id,t.trans_time,t.tel_content
			String rptno = vos[i].getStringValue("rptno");
			String msgno = vos[i].getStringValue("msg_no");
			String acnum = vos[i].getStringValue("ac_id");
			String telContent = vos[i].getStringValue("tel_content");
			String fltno = vos[i].getStringValue("flt_id");
			Date transtime = vos[i].getDateValue("trans_time");
			String acmodel = vos[i].getStringValue("modelseries");
			AcarsParseResult result=null;
			try{
				if("WRN".equals(rptno)){
					if("A330".equals(acmodel)||"A340".equals(acmodel)){
						WrnDataParseImpl_A330A340 wrn=WrnDataParseImpl_A330A340.getInstance();
						result=wrn.parseContentData(rptno,msgno,acnum,telContent,fltno,transtime);
					}else{
						WrnDataParseImpl wrn=WrnDataParseImpl.getInstance();
						result=wrn.parseContentData(rptno,msgno,acnum,telContent,fltno,transtime);
					}
					num_ok++;
				}else if("FLR".equals(rptno)){
					if("A330".equals(acmodel)||"A340".equals(acmodel)){
						FlrDataParseImpl_A330A340 flr=FlrDataParseImpl_A330A340.getInstance();
						result=flr.parseContentData(rptno,msgno,acnum,telContent,fltno,transtime);
					}else{
						FlrDataParseImpl flr=FlrDataParseImpl.getInstance();
						result=flr.parseContentData(rptno,msgno,acnum,telContent,fltno,transtime);
					}
					num_ok++;			
				}else if("MPF".equals(rptno)){
					if("A330".equals(acmodel)||"A340".equals(acmodel)){
						MpfDataParseImpl_A330A340 mpf=MpfDataParseImpl_A330A340.getInstance();
						result=mpf.parseContentData(rptno,msgno,acnum,telContent,fltno,transtime);
						num_ok++;
					}else{
						MpfDataParseImpl mpf=MpfDataParseImpl.getInstance();
						result=mpf.parseContentData(rptno,msgno,acnum,telContent,fltno,transtime);
						num_ok++;
					}
				}else if("SMD".equals(rptno)){
					SmdDataParseImpl smd=SmdDataParseImpl.getInstance();
					result=smd.parseContentData(rptno,msgno,acnum,telContent,fltno,transtime);
					num_ok++;
				}
				
				if(result != null){
					if (result.getStatus() == 3) {
						logger.info("CFD报文msg_no=[" + msgno + "]待解析，设备号不一致！");
						dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateCfdSql, result.getStatus(), result.getReason(), msgno);
					} else {

						logger.info("CFD报文msg_no=[" + msgno + "]解析成功！");
						dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateCfdSql, result.getStatus(), result.getReason(), msgno);
					}
					dmo.commit(ApmsConst.DS_APMS);
				}
			}catch(Exception e){
				e.printStackTrace();
				logger.error("CFD报文msg_no=["+msgno+"]解析失败！" + e.toString());
				dmo.rollback(ApmsConst.DS_APMS);
				
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateCfdSql, ApmsVarConst.TELEGRAPH_PARSE_ERROR,e.toString(),msgno);
				dmo.commit(ApmsConst.DS_APMS);
				num_err ++;
			}finally{
				dmo.releaseContext(ApmsConst.DS_APMS);
			}
			
		}
		String parseMessage = "本次共解析CFD报文[" + (num_ok+num_err) + "]条，成功["+num_ok+"]条，失败["+num_err+"]条";
		logger.info(parseMessage);
		return parseMessage;
	}
	
	
	private StringBuilder getBasicQuerySql(){
		//生成未解析报文基本信息
		StringBuilder sb = new StringBuilder("select t.ac_id acnum,t.msg_no,t.rptno,t.modelcode,t.modelseries");
		sb.append(",t.ac_id,t.tel_content,to_char(t.trans_time,'YYYY-MM-DD') transdate, t.trans_time");
		sb.append(" from a_acars_telegraph_dfd t,b_aircraft ac");
		sb.append(" where t.errint=0");
		sb.append(" and ac.isdfdparse=1 and ac.aircraftsn=t.ac_id "); //只查询要求进行解析的飞机
		
		return sb;
	}
	
	/**
	 * 查询排好序的未解析的报文，注意查询的报文数据量
	 * @param rptnoPrefix A或R， 
	 * @param acNum
	 * @return 对象列表
	 * @throws Exception
	 */
	private HashVO[] getUnprasedApu(String rptnoPrefix,String acNum,String beginDate,String endDate) throws Exception{
		
		StringBuilder selBasic = new StringBuilder("select t.ac_id acnum,t.msg_no,t.rptno,t.modelcode,t.modelseries");
		selBasic.append(",t.ac_id,t.tel_content,to_char(t.trans_time,'YYYY-MM-DD') transdate,t.trans_time");
		selBasic.append(" from a_acars_telegraph_dfd T,b_aircraft ac ");
		selBasic.append(" where 1=1");
		selBasic.append(" and ac.isdfdparse=1 and ac.aircraftsn=t.ac_id "); //只查询要求进行解析的飞机
		
//		selBasic.append(" and T.msg_no= 276802621 "); //发布时注意注释本行数据
		
		//加入时间段限制
		if(beginDate!=null && beginDate.length() > 0){
			selBasic.append(" and trans_time >=  to_date('"+DateUtil.getLongDate(strToDate(beginDate))+"','YYYY-MM-DD hh24:mi:ss')");
		}
		
		if(endDate!=null && endDate.length() > 0){
			selBasic.append(" and trans_time <=  to_date('"+DateUtil.getLongDate(strToDate(endDate))+"','YYYY-MM-DD hh24:mi:ss')");
		}
		
		if( acNum != null ){//加入飞机号限制
			selBasic.append(" and T.AC_ID = '"+acNum+"'");
		}
		
		StringBuilder qrySb = new StringBuilder();
		String qry13_0 = selBasic.toString() + " AND ERRINT=0 AND RPTNO='"+rptnoPrefix+"13'";
		//String qry13_3 = selBasic.toString() + " AND ERRINT=3 AND RPTNO='"+rptnoPrefix+"13'"; 
		String qry14_0 = selBasic.toString() + " AND ERRINT=0 AND RPTNO='"+rptnoPrefix+"14'";
		//String qry14_3 = selBasic.toString() + " AND ERRINT=3 AND RPTNO='"+rptnoPrefix+"14'";//14号未解析报文
		
		qrySb.append("select * from ( ");
		qrySb.append(qry13_0.toString());
		//qrySb.append(" union all " + qry13_3.toString());//测试可注释用
		qrySb.append(" union all " + qry14_0.toString());
		//qrySb.append(" union all " + qry14_3.toString());
		
		qrySb.append(" ) order by msg_no");
		
		String sql = "select * from ("+qrySb.toString()+") where rownum<= 3000"; //单次5000 
		
		CommDMO dmo = new CommDMO();
		HashVO[] vos= dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
		
		return vos;
	}
	/**
	 * 查询未解析的cfd报文
	 * @param msg_no
	 * @param rptno
	 * @param acNum
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	private HashVO[] getUnprasedCFD(String msg_no,String rptno,String acNum,String beginDate,String endDate) throws Exception{
		
		StringBuilder selBasic = new StringBuilder("select t.ac_id acnum,t.msg_no,t.rptno,t.ac_id,t.flt_id,t.trans_time,t.tel_content");
		selBasic.append(" ,t.modelcode,t.modelseries");
		selBasic.append(" from a_acars_telegraph_CFD T,b_aircraft ac ");
		selBasic.append(" where t.errint=0  and ac.iscfdparse=1 and ac.aircraftsn=t.ac_id");
		selBasic.append(" and trans_time>=to_date('20141001','YYYYMMDD')");//只处理2014年10月1号之后的报文数据
		//加入时间段限制
		if(beginDate!=null && beginDate.length() > 0){
			selBasic.append(" and trans_time >=  to_date('"+DateUtil.getLongDate(strToDate(beginDate))+"','YYYY-MM-DD hh24:mi:ss')");
		}
		if(endDate!=null && endDate.length() > 0){
			selBasic.append(" and trans_time <=  to_date('"+DateUtil.getLongDate(strToDate(endDate))+"','YYYY-MM-DD hh24:mi:ss')");
		}
		if( msg_no != null ){//加入消息号限制
			selBasic.append(" and T.msg_no = '"+msg_no+"'"); 
		}
		
		if( rptno != null && rptno != "SMD"){//加入报文号限制
			selBasic.append(" and T.rptno = '"+rptno+"'"); 
		}else if(rptno=="SMD"){
			selBasic.append(" and T.rptno = '"+rptno+"' and T.tel_content like '%APU DATA / OIL%'" );
		}
		
		if( acNum != null ){//加入飞机号限制
			selBasic.append(" and T.AC_ID = '"+acNum+"'"); 
		}
		selBasic.append(" and rownum<= 30000 order by msg_no");
		CommDMO dmo = new CommDMO();
		HashVO[] vos= dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, selBasic.toString());
		return vos;
	}
	
	/**
	 * 查询未解析的报文不排序
	 * @param rptNO
	 * @param acNum
	 * @return
	 * @throws Exception
	 */
	private HashVO[] getUnprasedTeleGraph(String msgno,String rptNO,String acNum,String beginDate,String endDate,boolean isOrderBy) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		sb.append(getBasicQuerySql());
		if(msgno !=null){
			sb.append(" and t.msg_no = '"+msgno+"'");
		}		
		//sb.append(" and T.errmessage  like 'java.lang.Exception: 数据行%'");
		//sb.append("and trans_time> to_date('2013-04-02','YYYY-MM-DD')");
		
		if(rptNO != null){
			sb.append(" and t.rptno = '"+rptNO+"'");
		}else{
			//排除掉需要排序的13、14号报文
			sb.append(" and t.rptno not in ('A13','R13','A14','R14')");
		}
		
		if( acNum != null ){//此条件测试用
			sb.append(" and t.AC_ID = '"+acNum+"'"); 
		}
		
		//加入时间段限制
		if(beginDate!=null && beginDate.length() > 0){
			sb.append(" and trans_time >=  to_date('"+DateUtil.getLongDate(strToDate(beginDate))+"','YYYY-MM-DD hh24:mi:ss')");
		}
		
		if(endDate!=null && endDate.length() > 0){
			sb.append(" and trans_time <=  to_date('"+DateUtil.getLongDate(strToDate(endDate))+"','YYYY-MM-DD hh24:mi:ss')");
		}
		
		String sql = "";
		if(isOrderBy){
			sb.append(" order by msg_no ");
			sql = "select * from ("+ sb.toString()+") where rownum<= 3000"; //单次5000
		}else{
			sb.append(" and rownum <= 5000"); //限定单次查询的数据量
			sql = sb.toString();
		}
		
		
			
		CommDMO dmo = new CommDMO();
		HashVO[] vos= dmo.getHashVoArrayByDSUnlimitRows(ApmsConst.DS_APMS, sql);
		
		return vos;
	}
	
	private Date strToDate(String dateStr) throws Exception{
		Date date;
		String dfStr1 = "yyyy-MM-dd HH:mm:ss";
		String dfStr2 = "yyyy-MM-dd";
		
		if(dateStr.length() > 10){
			date = DateUtil.StringToDate(dateStr, dfStr1);
		}else{
			date = DateUtil.StringToDate(dateStr, dfStr2);
		}
	    
		return date;
	}
	
}
