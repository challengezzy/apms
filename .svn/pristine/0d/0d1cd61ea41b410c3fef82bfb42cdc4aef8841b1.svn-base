package com.apms.bs.oxygen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.vo.BintProgResult;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

public class OxygenChangeService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	
	MatlabFunctionService functionService = new MatlabFunctionService();
	
	/**
	 * 氧气瓶拆换预测
	 * @param baseOrgId 飞机属性基地
	 * @param apcode3 落地机场三字代码
	 * @param maxChgNum 每天最大拆换数量
	 * @param preDayNum 预测天数
	 * @param presChangePoint 最小压力拆换点
	 * @throws Exception
	 */
	public Map<String, Object> changePredict(Map<String, Object> predictObj) throws Exception{
		String baseOrgId = (String)predictObj.get("BASEORGID");
		String apcode3 = (String)predictObj.get("ARRAPCODE3");
		int maxChgNum = (Integer)predictObj.get("MAXCHGNUM");
		int preDayNum = (Integer)predictObj.get("PREDAYNUM");
		int presChangePoint = (Integer)predictObj.get("OXYMINPRESS");
		int minPresFilter = (Integer)predictObj.get("MINPRESFILTER");
		int estimateTemp = (Integer)predictObj.get("ESTIMATETEMP");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ArrayList<String> acList = new ArrayList<String>();
		HashMap<String, ArrayList<Double>> dataMap = new HashMap<String, ArrayList<Double>>(); //氧气压力矩阵数据
		
		HashMap<String, HashMap<String,Object>> resdataMap = new HashMap<String, HashMap<String, Object>>(); //结果视图数据
		
		Double maxPres = 99999.0;
		String noAf = "无航后";
		Date today = new Date();
		
		//获取原始数据
		HashVO[] presVos = getOxyPredictedPressVos(baseOrgId, apcode3, preDayNum,minPresFilter,estimateTemp);
		
		//构造飞机、氧气压力矩阵
		for(int i=0;i<presVos.length;i++){
			HashVO vo = presVos[i];
			ArrayList<Double> presList;//一架飞机N天的氧气预测压力
			ArrayList<Integer> dayDetaList;//当前数据和预测时天数差
			HashMap<String,Object> rowmap ;//界面展示数据
			
			String acnum = vo.getStringValue("ACNUM");
			String flt_no = vo.getStringValue("FLT_NO");
			String dep_apt = vo.getStringValue("DEP_APT");
			String arr_apt = vo.getStringValue("ARR_APT");
			Date fltDate = vo.getDateValue("FLT_DATE"); //使用班表到达时间的日期任务航班日期
			Double pres = vo.getDoubleValue("OXYPRESS");
			String orgname = vo.getStringValue("ORGNAME");
			
			String tipMsg = "航班号：" +flt_no+ ",到达时间:" + DateUtil.getLongDate(vo.getDateValue("STA"))
					+ "\n起飞机场："+dep_apt+ "，落地机场" +arr_apt+ ""+ ""+ "";
			
			int detaDay = vo.getIntegerValue("DETADAY");
			int dayIdx = 0;
			
			if( !dataMap.containsKey(acnum) ){
				//发现新的飞机号
				acList.add(acnum);
				
				dayIdx = 0;
				presList = new ArrayList<Double>();
//				dayDetaList = new ArrayList<Integer>();
				rowmap = new HashMap<String, Object>(); //一行新数据
				rowmap.put("ACNUM", acnum);	
				rowmap.put("ORGNAME",orgname );
				
				rowmap.put("LASTUTCDATE", vo.getStringValue("LASTUTCDATE")); 
				rowmap.put("LASTPRESS46", vo.getStringValue("LASTPRESS46"));
				
				//对于前面不是0开头的情况，生成数据进行填充
				while(dayIdx < detaDay && dayIdx<preDayNum){
					presList.add(maxPres);
//					dayDetaList.add(dayIdx);
					String fdate = DateUtil.getDateStr( DateUtil.moveDay(today, dayIdx) );
					rowmap.put(fdate, noAf);
					rowmap.put("TIP"+fdate, "当天无指定机场的航后航班" );
					dayIdx ++;
				}
				presList.add(pres);
//				dayDetaList.add(detaDay);
				rowmap.put(DateUtil.getDateStr(fltDate), pres);
				rowmap.put("TIP"+DateUtil.getDateStr(fltDate), tipMsg );
				
				dataMap.put(acnum, presList);
//				dayDetaMap.put(acnum, dayDetaList);
				resdataMap.put(acnum, rowmap);
			}else if(dataMap.containsKey(acnum)){
				presList = dataMap.get(acnum);
//				dayDetaList = dayDetaMap.get(acnum);
				rowmap = resdataMap.get(acnum);
				
				dayIdx = presList.size();
				while (dayIdx < detaDay && dayIdx<preDayNum){
					presList.add(maxPres);
//					dayDetaList.add(dayIdx);
					
					String fdate = DateUtil.getDateStr( DateUtil.moveDay(today, dayIdx) );
					rowmap.put(fdate, noAf);
					rowmap.put("TIP"+fdate, "当天无指定机场的航后航班" );
					
					dayIdx ++;
				}
				presList.add(detaDay, pres);//考虑到
//				dayDetaList.add(detaDay);
				rowmap.put(DateUtil.getDateStr(fltDate), pres);
				rowmap.put("TIP"+DateUtil.getDateStr(fltDate), tipMsg );
			}
		}
		
		int acCount = acList.size();
		//遍历MAP,对于pressList不满predictDay的数据进行补全
		for(int i=0;i<acCount;i++){
			String acnum = acList.get(i);
			ArrayList<Double> presList = dataMap.get(acnum);
			
			HashMap<String,Object> rowmap = resdataMap.get(acnum);
			while(presList.size() < preDayNum){
				String fdate = DateUtil.getDateStr( DateUtil.moveDay(today, presList.size()) );
				presList.add(maxPres);
				
				
				rowmap.put(fdate, noAf);
			}
		}
		
		//A*x = b 参数矩阵应该是2M x m*n 2m行,m*n列
		double[][] matrixA = new double[acCount*2 + preDayNum ][ acCount*preDayNum ];
		double[] matrixB = new double[ acCount*2 + preDayNum ];
		double[] matrixF = new double[ acCount*preDayNum ];
		//生成数据计算矩阵数据,应该有m*n 个变量,m架飞机*n天数据
		//条件组1,预测时间范围内只换一次 X11+ .. + X1j + X1n <= 1;  ……… ,   Xm1+ .. + Xmj + Xmn <= 1;,共M个
		for(int m=0;m<acCount;m++){
			matrixB[m] = 1;
			for(int n=0;n<preDayNum;n++){
				matrixA[m][ m*preDayNum+n] = 1;
			}
		}
		
		HashMap<String, ArrayList<Integer>> colSeqMap = new HashMap<String, ArrayList<Integer>>(); //列索引位置,对同一天的换瓶情况进行排序,压力值越小，优先级越高
		
		//条件组2,每天只能换maxchg个， X11+…+ Xi1 + Xm1 <=maxChg; ………., X1n +…+Xin + Xmn <= maxChg;， 共N个
		//竖列对优先级进行排序
		for(int n=0;n<preDayNum;n++){
			matrixB[acCount+n] = maxChgNum;
			for(int m=0;m<acCount;m++){
				matrixA[acCount+n][m*preDayNum+n] = 1; //生成A矩阵中的部分数据
				
				String acnum = acList.get(m);
				ArrayList<Integer> seqList = colSeqMap.get(acnum);
				if(seqList == null){
					seqList = new ArrayList<Integer>();
					colSeqMap.put(acnum, seqList);
				}
				
				//生成当前压力值的排序值,遍历该列的所有压力值
				int seq = acCount*200;
				double curPredictPres = dataMap.get(acnum).get(n);
				if(curPredictPres > presChangePoint){
					seq = -100; //不进入排序
				}else{
					for(int j=0;j<acCount;j++){
						double predictPres = dataMap.get(acList.get(j)).get(n);
						if(predictPres < curPredictPres){
							seq -= 200; //如果发现压力值更小的，则它优先级-1
						}
					}
					
					//把压力与1600的差值大小 加权考虑进来,差值越大，加权越多
					seq += (presChangePoint-curPredictPres) ;
				}
				
				seqList.add(seq);
			}
			
		}
		
		//目标求解数组，求A11*X11 +  …. Aij*Xij  + …. + Amn*Xmn 使其最大(共M架飞机，N天的预测数据).
		//条件组3,氧气瓶压力达到最小压力数
		for(int m=0;m<acCount;m++){
			String acnum = acList.get(m);
			matrixB[ acCount + preDayNum + m] = presChangePoint;
			
			int distanse = 0 ;//距离1600这个更换点，间隔的点数.对于每架飞机分别计算
			for(int n=0;n<preDayNum;n++){
				matrixA[acCount + preDayNum + m][ m*preDayNum+n] = dataMap.get(acnum).get(n);
				
				double predictPres = dataMap.get(acnum).get(n); //预测的压力数据，无对应航后的数据值为9999.9
				double weightValue = -predictPres; //第一版，直接使用压力值任务效用值
				int rowSeq = preDayNum - n;//从前向后倒序排列
				int colSeq = colSeqMap.get(acnum).get(n);
				
				weightValue = -(rowSeq * colSeq);
				
//				if(predictPres > presChangePoint){//如果值大于 presChangePoint,则此处X肯定为1
//					weightValue = -1.0;
//				}else{
//					
//					weightValue = -( (Math.abs(predictPres-presChangePoint) - 10*distanse )); //把值与1600的差值放大2倍，这样差值大的优先级高
//					distanse ++ ;
//				}
				
				matrixF[m*preDayNum+n] = weightValue; //由于是示最大值，系数用负数代替
			}
		}		
		
		//TODO 对于整行压力值均大于换瓶压力点的情况，再构造等式条件，xi0+ ... + xij+ ...+ xin =0; 应该会提高运算效率
		
		BintProgResult result = functionService.bintProg(matrixF, matrixA, matrixB, null, null);
		resultMap.put("EXITFLAG", result.getExitFlag());
		resultMap.put("OUTPUTMSG", result.getOutput());
		
		List<Map<String,Object>> resDataList = new ArrayList<Map<String,Object>>();
		if(result.getExitFlag() == 1){
			logger.info("预测氧气瓶更换规划求解成功！");
			double[] xroot = result.getX();
			
			//将预测结果进行赋值
			for(int i=0;i<acCount; i++){
				String acnum = acList.get(i);
				for(int j=0;j<preDayNum;j++){
					HashMap<String,Object> rowmap = resdataMap.get(acnum);
					int isChg = (int)xroot[i*preDayNum + j];
					rowmap.put("CHG"+DateUtil.getDateStr(DateUtil.moveDay(today, j)), isChg );
				}
			}
		}else{
			logger.info("预测氧气瓶更换规划求解失败， EXITFLAG="+ result.getExitFlag()+",失败信息:" + result.getOutput());
		}
		
		//把数据结果转换为数组形式
		for(int i=0;i<acCount;i++){
			String acnum = acList.get(i);
			
			resDataList.add(resdataMap.get(acnum));
		}
		
		resultMap.put("DATALIST", resDataList );
		
		return resultMap;
	}
	
	/**
	 * 
	 * @param baseOrgId
	 * @param apcode3
	 * @param preDayNum
	 * @return
	 * @throws Exception
	 */
	public HashVO[] getOxyPredictedPressVos(String baseOrgId,String apcode3,int preDayNum,int minPresFilter,Integer estimateTemp) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T.ACNUM,FS.FLT_DATE,FS.IATA_C||FS.FLT_ID FLT_NO, ");
		sb.append(" T.BASEORGID,(SELECT NAME FROM B_ORGANIZATION WHERE ID=T.BASEORGID) ORGNAME,");
		sb.append(" FS.FLTTYPE_ARR,T.PRES_ST_C15_S46 LASTPRESS46,T.DATETIME_MID_S46 LASTUTCDATE,FS.DEP_APT,FS.ARR_APT,FS.STA,");
		sb.append("  TRUNC(FS.FLT_DATE) - TRUNC(SYSDATE) DETADAY,");
		sb.append(" ROUND( (T.K* GETDATETOMSNUMBER(FS.STA) + B)*(("+estimateTemp+"+273.5)/288.5 ),0) OXYPRESS,T.DATE_UTC");
		sb.append(" FROM V_A_DFD_A23_OXYSTATE T,F_FLIGHT_SCHEDULE FS");
		sb.append(" WHERE T.ACNUM=FS.ACNUM AND FS.FLTTYPE_ARR = 'AF' ");
		sb.append("  AND FS.FLT_DATE >= TRUNC(SYSDATE) AND FS.FLT_DATE < TRUNC(SYSDATE) + " + preDayNum);//预测天数
		
		if(baseOrgId != null && !"".equals(baseOrgId)){
			sb.append(" AND T.BASEORGID = " + baseOrgId);
		}
		
		if(apcode3 != null && !"".equals(apcode3)){
			sb.append(" AND FS.ARR_APT = '" + apcode3 + "'");
		}
		sb.append(" AND T.PRES_ST_C15_S46 < " + minPresFilter); //氧气瓶压力值过虑
		
		//sb.append(" AND ACNUM = ANY('B6216','B6235','B6677')"); //测试条件 
		
		sb.append(" ORDER BY ACNUM,DETADAY   ");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		
		return vos;
	}

}
