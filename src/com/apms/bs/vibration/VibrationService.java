package com.apms.bs.vibration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.common.HashVoUtil;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.util.DateUtil;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.vo.FftCountResult;
import com.apms.matlab.vo.Spwvd_FigureResult;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 抖动分析服务类
 * @author jerry
 * @date Apr 26, 2013
 */
public class VibrationService {
	
	Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	MatlabFunctionService functionService = new MatlabFunctionService();
	
	public void vibrationAnalyse(String acnum,String groupid) throws Exception{
		HashVO[] vos = getVibVos(acnum, groupid);
		vibrationCompute(vos, acnum, groupid, "Z", true);
		vibrationCompute(vos, acnum, groupid, "Y", true);
		
		
	}
	
	/**
	 * 抖动分析
	 * @param vos
	 * @param acnum
	 * @param groupid
	 * @param dataCol
	 * @param isNeedFigure
	 * @return
	 * @throws Exception
	 */
	public VibrationFigureVo vibrationCompute(HashVO[] vos,String acnum,String groupid,String dataCol,boolean isNeedFigure) throws Exception{
		return vibrationCompute(vos, acnum, groupid, dataCol, isNeedFigure, false);
	}
	
	/**
	 * 抖动分析
	 * @param vos 参与计算的数据
	 * @param acnum 飞机号
	 * @param groupid 组号
	 * @param dataCol 数据列 Z或Y
	 * @param isNeedFigure 是否返回图形数据
	 * @param isFft 是否对数据作傅利叶变换
	 * @return
	 * @throws Exception
	 */
	public VibrationFigureVo vibrationCompute(HashVO[] vos,String acnum,String groupid,String dataCol,boolean isNeedFigure,boolean isFft) throws Exception{
		VibrationFigureVo figureVo = new VibrationFigureVo();
		
		if(vos.length < 800){
			
			logger.debug("本次报文数据采集不完整，不足800条数据!");
			figureVo.setResultDesc("本次报文数据采集不完整，不足800条数据,无法进行图形分析!");
			figureVo.setSpwvdOk(false);
			
			return figureVo;
		}else{
			double[] arr = new double[vos.length];
			
			for(int i=0;i<vos.length;i++){
				HashVO vo = vos[i];
				arr[i] = vo.getDoubleValue(dataCol);
			}
			
			double sampFreq = 32; //信号的采集频率
			double freqBins = 512; //数据分辨率
			
			String vibName = "俯仰";
			if(dataCol.equalsIgnoreCase("Y")){
				vibName = "偏航";
			}
			
			String title = "" + vibName + " 平滑伪WignerVille分布";
			String spwvdXLable = "t/s";
			String spwvdYLable = "f/Hz";
			Spwvd_FigureResult result = functionService.SPWVD_Figure(arr,sampFreq,freqBins,title,spwvdXLable,spwvdYLable,isNeedFigure);
			figureVo.setSpwvdFigure(result);
			
			//傅里叶变换图
			if(isFft){
				String tTitleStr = "" + vibName + " 运动轨迹";
				String fTitleStr = "" + vibName + " FFT变换";
				String tXlabel = "时间(s)";
				String tYlabel = "加速度(g,9.8m/s^2)";
				String fXlabel = "频率(Hz)";
				String fYlabel = "幅值(G)";
				
				FftCountResult fftRes = functionService.FFT_Count(arr, sampFreq, true, tTitleStr, tXlabel, tYlabel, fTitleStr, fXlabel, fYlabel);
				figureVo.setFftResult(fftRes);
			}
			
			figureVo.setSpwvdOk(true);
			
			return figureVo;
		}
	}
	
	public HashVO[] getVibVos(String acnum,String groupid) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT L.GROUPID,H.MSG_NO,V.ROW_NUM");
		sb.append(" ,V.IR_Z Z,V.IR_Y Y");              
		sb.append(" FROM A_DFD_VIBRATIONRECORD V,A_DFD_A2832_LIST L,A_DFD_HEAD H");
		sb.append(" WHERE L.MSG_NO=H.MSG_NO AND V.MSG_NO=H.MSG_NO");
		sb.append(" AND L.GROUPID ='"+groupid+"'");
		sb.append(" AND H.ACNUM = '"+acnum+"'");
		sb.append(" ORDER BY V.MSG_NO,V.ROW_NUM");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		
		return vos;
	}
	
	public Map<String, Object> getA28FreqChartData(String begin_date,String end_date,String acNum,String dimension) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询飞机抖动报文(28)计算数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		
		List<Map<String,Object>> allA28List = new ArrayList<Map<String,Object>>();
		//查询 A34数据对象
		HashVO[] voslA28 = getA28Freq(begin_date, end_date, acNum, dimension);
		allA28List.addAll(HashVoUtil.hashVosToMapList(voslA28));

		resMap.put("allA28List", allA28List);
		
		logger.debug("结束查询滑油报文(28)计算数据");
		
		return resMap;
		
	}
	
	private HashVO[] getA28Freq(String begin_date,String end_date,String acNum,String dimension) throws Exception{
		String qrySql = "SELECT ID,MSG_NO,ACNUM,DATE_UTC"
				+",GROUPID,IRDIMENSION,FREQ1,FREQ8,FREQ3,FREQ2,FREQ4,FREQ5,FREQ6,FREQ7,FREQ9,FREQ10,FREQ11,FREQ12,FREQ13,FREQ14,FREQ15,FREQ16"
				+",FREQ17,FREQ18,FREQ19,FREQ20,FREQ21,FREQ22,FREQ23,FREQ24,FREQ25,FREQ26,FREQ27,FREQ28,FREQ29,FREQ30,FREQ31,FREQ32,UPDATE_DATE "
				+" FROM A_DFD_VIBRATION_PEAK T WHERE ACNUM=? AND T.DATE_UTC>=? AND T.DATE_UTC<=? AND IRDIMENSION=? ";
		 
		String dfStr = "yyyy-MM-dd HH:mm:ss";
		Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
		Date endDt = DateUtil.StringToDate(end_date, dfStr);
			
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, qrySql ,acNum,beginDt,endDt,dimension);
			
		return vos;
	}
	
	private HashVO[] getA28Freq_old(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T1.ID,T1.MSG_NO,T1.ACNUM,T1.DATE_UTC");
		
		sb.append(",T1.FREQ1 Y1,T1.FREQ2 Y2,T1.FREQ3 Y3,T1.FREQ4 Y4,T1.FREQ5 Y5,T1.FREQ6 Y6,T1.FREQ7 Y7,T1.FREQ8 Y8");
		sb.append(",T2.FREQ1 Z1,T2.FREQ2 Z2,T2.FREQ3 Z3,T2.FREQ4 Z4,T2.FREQ5 Z5,T2.FREQ6 Z6,T2.FREQ7 Z7,T2.FREQ8 Z8");
		sb.append(",T1.FREQ9 Y9,T1.FREQ10 Y10,T1.FREQ11 Y11,T1.FREQ12 Y12,T1.FREQ13 Y13,T1.FREQ14 Y14,T1.FREQ15 Y15,T1.FREQ16 Y16");
		sb.append(",T2.FREQ9 Z9,T2.FREQ10 Z10,T2.FREQ11 Z11,T2.FREQ12 Z12,T2.FREQ13 Z13,T2.FREQ14 Z14,T2.FREQ15 Z15,T2.FREQ16 Z16");
		sb.append(",T1.FREQ17 Y17,T1.FREQ18 Y18,T1.FREQ19 Y19,T1.FREQ20 Y20,T1.FREQ21 Y21,T1.FREQ22 Y22,T1.FREQ23 Y23,T1.FREQ24 Y24");
		sb.append(",T2.FREQ17 Z17,T2.FREQ18 Z18,T2.FREQ19 Z19,T2.FREQ20 Z20,T2.FREQ21 Z21,T2.FREQ22 Z22,T2.FREQ23 Z23,T2.FREQ24 Z24");
		sb.append(",T1.FREQ25 Y25,T1.FREQ26 Y26,T1.FREQ27 Y27,T1.FREQ28 Y28,T1.FREQ29 Y29,T1.FREQ30 Y30,T1.FREQ31 Y31,T1.FREQ32 Y32");
		sb.append(",T2.FREQ25 Z25,T2.FREQ26 Z26,T2.FREQ27 Z27,T2.FREQ28 Z28,T2.FREQ29 Z29,T2.FREQ30 Z30,T2.FREQ31 Z31,T2.FREQ32 Z32");
		
	    sb.append(" FROM A_DFD_VIBRATION_PEAK T1");
	    sb.append(" ,A_DFD_VIBRATION_PEAK T2");

	    sb.append(" WHERE T1.DATE_UTC>=? AND T1.DATE_UTC<=?");
	    sb.append(" AND T1.ACNUM='"+acNum+"' AND T1.MSG_NO =T2.MSG_NO");
	    sb.append(" AND T1.IRDIMENSION='Y'");
	    sb.append(" AND T2.IRDIMENSION='Z'");



	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY T1.MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}

}
