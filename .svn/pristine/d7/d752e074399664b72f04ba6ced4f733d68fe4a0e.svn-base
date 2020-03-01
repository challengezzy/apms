package com.apms.bs.dataprase;

import org.apache.log4j.Logger;

import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.dataprase.vo.AcarsHeadVo;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dfd.DfdVarConst;

import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 抽象的报文数据解析类，所有的具体的报文解析类的父类
 * 报文解析中可以进行告警
 * @author zzy
 *
 */
public abstract class ReportContentParseClass {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	/**
	 * 报文头对象
	 */
	protected AcarsHeadVo headVo;
	
	protected String msgno;//消息编号
	protected String acmodel;//机型
	protected String rptno; //报文编号
	
	protected ReportHeadParseClass headParseClass;
	
	/**
	 * 报文内容解析并入库,放回解析结果
	 * @param acarsVo
	 * @param msgno
	 * @param content
	 * @param date_utc 报文头日期
	 * @throws Exception
	 */
	public abstract AcarsParseResult parseContentData(HashVO acarsVo,String msgno,String content,String date_utc) throws Exception;

	/**
	 * 生成AlarmTargetVo基本对象信息
	 * @return
	 */
	public AlarmMonitorTargetVo getAlarmTargetVo(){
		
		AlarmMonitorTargetVo targetVo = new AlarmMonitorTargetVo();
		targetVo.setMsgno(msgno);
		targetVo.setDateUtc(headVo.getDateUtc());
		targetVo.setAcnum(headVo.getAcid());
		targetVo.setAcmodel(acmodel);
		targetVo.setRptno(rptno);
		targetVo.addParam(DfdVarConst.KEY_HEADVO, headVo);
		
		return targetVo;
	}
	
	/**
	 * 设置报文基本信息
	 * @param msgno
	 * @param rptno
	 * @param acmodel
	 */
	public void setBasicInfo(String msgno,String rptno,String acmodel){
		this.msgno = msgno;
		this.rptno = rptno;
		this.acmodel = acmodel;
	}
	
	public AcarsHeadVo getHeadVo() {
		return headVo;
	}

	public void setHeadVo(AcarsHeadVo headVo) {
		this.headVo = headVo;
	}

	public ReportHeadParseClass getHeadParseClass() {
		return headParseClass;
	}

	public void setHeadParseClass(ReportHeadParseClass headParseClass) {
		this.headParseClass = headParseClass;
	}

	public String getAcmodel() {
		return acmodel;
	}

	public void setAcmodel(String acmodel) {
		this.acmodel = acmodel;
	}
	
}
