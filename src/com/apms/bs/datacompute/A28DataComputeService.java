package com.apms.bs.datacompute;

import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.vibration.VibrationFigureVo;
import com.apms.bs.vibration.VibrationService;

public class A28DataComputeService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	
	private String insertSql = "INSERT INTO A_DFD_VIBRATION_PEAK(ID,MSG_NO,ACNUM,DATE_UTC"
		+",GROUPID,IRDIMENSION,FREQ1,FREQ8,FREQ3,FREQ2,FREQ4,FREQ5,FREQ6,FREQ7,FREQ9,FREQ10,FREQ11,FREQ12,FREQ13,FREQ14,FREQ15,FREQ16"
		+",FREQ17,FREQ18,FREQ19,FREQ20,FREQ21,FREQ22,FREQ23,FREQ24,FREQ25,FREQ26,FREQ27,FREQ28,FREQ29,FREQ30,FREQ31,FREQ32,UPDATE_DATE) "
		+" values(S_A_DFD_VIBRATION_PEAK.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";

	
	/**
	 * 计算飞机拉动峰值数据
	 * @param curVo
	 * @throws Exception
	 */
	public void computeA28ByHistoryVos(HashVO curVo) throws Exception{
		
		//计算数据使用28号报文数据
		//String id = curVo.getStringValue("ID");
		String msgno = curVo.getStringValue("MSG_NO");
		String acnum = curVo.getStringValue("ACNUM");
		Date date_utc = curVo.getDateValue("DATE_UTC");
		String groupid = curVo.getStringValue("GROUPID");

		//根据gooupid查询该组的5条数据
		VibrationService vibService = new VibrationService();
		HashVO[] vos = vibService.getVibVos(acnum, groupid);
    	
		VibrationFigureVo zFigureVo = vibService.vibrationCompute(vos,acnum,groupid,"Z",false);
		if( zFigureVo.isSpwvdOk() == false ){
			//数据计算失败
			throw new Exception( zFigureVo.getResultDesc() );
		}
		
    	double[] peakZArr = zFigureVo.getSpwvdFigure().getEnergyPeak();
    	
    	VibrationFigureVo yFigureVo = vibService.vibrationCompute(vos,acnum,groupid,"Y",false);
    	double[] peakYArr = yFigureVo.getSpwvdFigure().getEnergyPeak();
    	
    	dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,acnum,date_utc,groupid,"Z"
    			,peakZArr[0],peakZArr[1],peakZArr[2],peakZArr[3],peakZArr[4],peakZArr[5],peakZArr[6],peakZArr[7]
    			,peakZArr[8],peakZArr[9],peakZArr[10],peakZArr[11],peakZArr[12],peakZArr[13],peakZArr[14],peakZArr[15]                                                                                        
    			,peakZArr[16],peakZArr[17],peakZArr[18],peakZArr[19],peakZArr[20],peakZArr[21],peakZArr[22],peakZArr[23]
    			,peakZArr[24],peakZArr[25],peakZArr[26],peakZArr[27],peakZArr[28],peakZArr[29],peakZArr[30],peakZArr[31]);
    	
    	dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql, msgno,acnum,date_utc,groupid,"Y"
    			,peakYArr[0],peakYArr[1],peakYArr[2],peakYArr[3],peakYArr[4],peakYArr[5],peakYArr[6],peakYArr[7]
    			,peakYArr[8],peakYArr[9],peakYArr[10],peakYArr[11],peakYArr[12],peakYArr[13],peakYArr[14],peakYArr[15]                                                                                        
    			,peakYArr[16],peakYArr[17],peakYArr[18],peakYArr[19],peakYArr[20],peakYArr[21],peakYArr[22],peakYArr[23]
    			,peakYArr[24],peakYArr[25],peakYArr[26],peakYArr[27],peakYArr[28],peakYArr[29],peakYArr[30],peakYArr[31]);
		
    	logger.debug("飞机["+acnum+"],GROUPID=["+groupid+"]的抖动数据分析完成！");
		
	}
	
}
