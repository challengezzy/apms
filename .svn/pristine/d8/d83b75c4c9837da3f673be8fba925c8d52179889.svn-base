package com.apms.bs.alarm.rule.impl;

import java.util.HashMap;
import java.util.Map;

import com.apms.bs.alarm.rule.vo.AlarmMonitorTargetVo;
import com.apms.bs.alarm.rule.vo.AlarmRuleVo;
import com.apms.bs.dataprase.vo.AcarsAcwVo;
import com.apms.bs.dataprase.vo.AcarsDfdA19Vo_A320;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

/**
 * A14报文,ACWX控制字告警
 * @author jerry
 * @date Apr 1, 2013
 */
public class AlarmImplA19_Acw  extends AlarmImplAcw{
	AcarsDfdA19Vo_A320 a19vo;
	AlarmRuleVo ruleVo;
	AlarmMonitorTargetVo targetVo;
	String acnum;
	String acmodel;
	@Override
	public void alartTrigger(AlarmRuleVo _ruleVo,AlarmMonitorTargetVo _targetVo) throws Exception{
		ruleVo = _ruleVo;
		targetVo = _targetVo;
		a19vo = (AcarsDfdA19Vo_A320)targetVo.getParam(DfdVarConst.KEY_BODYVO);
		acnum = targetVo.getAcnum();
		acmodel = targetVo.getAcmodel();
		
		//{TIME} 飞机{ACNUM}，报文{RPTNO},{ROWTITLE} {ACWX}控制字编码{ACWCODE}。
		//异常详细内容:	{EXPDETAIL}		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("TIME", DateUtil.getLongDate(targetVo.getDateUtc())); 
		paramMap.put("ACNUM", acnum);
		paramMap.put("RPTNO",ApmsVarConst.RPTNO_A19);
		paramMap.put("ROWTITLE", "");
		
		//控制字告警
		
		if (a19vo.getX0()!=null){
			if (a19vo.getX0().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX0().getPcsw());
				getPCSW(pcsw);
			}	
		}
		if (a19vo.getX1()!=null){
			if (a19vo.getX1().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX1().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.getX2()!=null){
			if (a19vo.getX2().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX2().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.getX3()!=null){
			if (a19vo.getX3().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX3().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.getX4()!=null){
			if (a19vo.getX4().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX4().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.getX5()!=null){
			if (a19vo.getX5().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX5().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.getX6()!=null){
			if (a19vo.getX6().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX6().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.getX7()!=null){
			if (a19vo.getX7().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX7().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.getX8()!=null){
			if (a19vo.getX8().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX8().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.getX9()!=null){
			if (a19vo.getX9().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.getX9().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.get_1x()!=null){
			if (a19vo.get_1x().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.get_1x().getPcsw());
				getPCSW(pcsw);
			}
		}
		if (a19vo.get_2x()!=null){
			if (a19vo.get_2x().getPcsw()!=null){
				AcarsAcwVo pcsw = new AcarsAcwVo(ApmsVarConst.CW_PCSW, a19vo.get_2x().getPcsw());
				getPCSW(pcsw);
			}
		}

		
	}
	private void getPCSW(AcarsAcwVo pcsw) throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("TIME", DateUtil.getLongDate(targetVo.getDateUtc())); 
		paramMap.put("ACNUM", acnum);
		paramMap.put("RPTNO",ApmsVarConst.RPTNO_A19);
		paramMap.put("ROWTITLE", "");
		
		String warningStr = getAcwWarning(pcsw, acmodel, ApmsVarConst.RPTNO_A19, "PCSW", null);
		//warningStr如果为null,表示不需要进行告警
		if(warningStr != null){
			paramMap.put("ACWX",pcsw.getAcwName());
			paramMap.put("ACWCODE",pcsw.getAcwVal());
			paramMap.put("EXPDETAIL",warningStr);
			//对ACW告警内容进行变量替换		
			String newStr = StringUtil.buildExpression(paramMap, ruleVo.getMsgContent());		
			logger.info("告警消息为：【"+newStr+"】");
			msgService.insertDispathAlarmMessage(ruleVo, targetVo, newStr);
		}
		
	}
		

}