package com.apms.bs.datacompute;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.datacompute.vo.A23ComputeVo;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.sysconfig.ApmsSysParamConfService;
import com.apms.bs.sysconfig.OxygenVarVo;
import com.apms.bs.sysconfig.RegressionVarVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.MathUtil;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.vo.MathCountResult;
import com.apms.matlab.vo.MathOneFitResult;
import com.apms.matlab.vo.Math_TTestResult;
import com.apms.vo.SysParamConfVo;

public class A23DataComputeService {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private SysParamConfVo sysVar;
	private OxygenVarVo oxyVar;//氧气相关设置变量
	private RegressionVarVo regVar;//回归计算相关变量设置
	
	private MatlabFunctionService functionService = new MatlabFunctionService();
	
	private CommDMO dmo = new CommDMO();
	
	public A23DataComputeService() throws Exception{
		sysVar = ApmsSysParamConfService.getInstance().getConfVo();
		oxyVar = sysVar.getOxygenVar();
		regVar = sysVar.getRegressionVar();
	}
	
	/**
	 * 根据当前和历史N条记录，计算报文数据
	 * @param a23vo 要计算的报文数据
	 * @throws Exception
	 */
	public A23ComputeVo computeA23ByHistoryVos(HashVO a23vo) throws Exception{
		A23ComputeVo computedVo = new A23ComputeVo();
		
		String id = a23vo.getStringValue("ID");
		String msgno = a23vo.getStringValue("MSG_NO");
		String acnum = a23vo.getStringValue("ACNUM");
		Date date_utc = a23vo.getDateValue("DATE_UTC");
		computedVo.setId(id);
		computedVo.setMsgno(msgno);
		computedVo.setAcnum(acnum);
		computedVo.setDate_utc(date_utc);
		
		//1.计算本报文时间、掉压等数据计算
		computeBasic(a23vo, computedVo);
		
		//2.首先查询要计算的数据中，有没有换瓶点，如果有换瓶点，从换瓶点开始计算数据
		int pointsN = regVar.getNumberOfPoints();//N点数
		String queryDataByACnum = "SELECT * FROM ( "
			+ "select * from A_DFD_A23_COMPUTE where msg_no < ? and acnum=? ORDER BY MSG_NO DESC " //TODO 最好再加个日期过滤，如3个月内，以提高查询速度
			+ " ) WHERE ROWNUM < " + pointsN*3 ;//考虑飘点情况,取3N条数据
		
		double deta_pres_fwd = 0; //当前S13标态压力与前一报文标态S46压力差;
		int deta_pres_fwd_s = 0;//当前S13标态压力与前一报文标态S46压力差状态,-1,0,1  表示泄压门限、正常泄压、换瓶
		
		//查询该报文数据解析所需的回归数据
		HashVO[] dataHis = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, queryDataByACnum, msgno,acnum);
		//3.判断是否是换瓶的情况
		if(dataHis.length > 0){
			HashVO preVo = dataHis[0];
			double h_pres_st_c15_s46 = preVo.getDoubleValue("PRES_ST_C15_S46");
			deta_pres_fwd = computedVo.getPres_st_c15_s13() - h_pres_st_c15_s46;
			deta_pres_fwd_s = computeDetaPresState(deta_pres_fwd);
		}
		computedVo.setDeta_pres_fwd(deta_pres_fwd);
		computedVo.setDeta_pres_fwd_s(deta_pres_fwd_s);
		
		//掉压率N点回归计算
		computeDetaPresRateSt(a23vo, computedVo,dataHis,deta_pres_fwd_s);
		
		//报文24H以上S46掉压
		computeDetaPresRateSt24H(a23vo, computedVo,dataHis,deta_pres_fwd_s);
		
		//5点滚动平均计算
		computePresStC15_Roll5(a23vo, computedVo, dataHis);
		
		//N点斜率回归计算
		computeS46Rake(a23vo, computedVo, dataHis, deta_pres_fwd_s);
		
		computedVo.insertToTable();
		
		return computedVo;
	}
	
	private void computeS46Rake(HashVO a23vo,A23ComputeVo computedVo,HashVO[] dataHis,int deta_pres_fwd_s) throws Exception{
		double pres_st_s46_k            =0; //"S46基于时间N点斜率 PolyFit_arry  轴为时间，Y轴为S46压力值        
		double pres_st_s46_b            =computedVo.getPres_st_c15_s46(); //S46基于时间N点截距 PolyFit_S, y=kx+b 中的b                                      
		double pres_st_s46_k_avg      =0    ; //S46的K基于时间N点均  PRES_ST_S46_K平均                                          
		double pres_st_s46_k_std      =0    ; //S46的K基于时间N点均方  PRES_ST_S46_K STD标准方差                                
		int pres_st_s46_k_out         =0 ; //S46的K基于时间N点STD超限,超限1，不超限-0
		int pres_st_s46_k_pointtype   =0;
		int pres_st_s46_k_tsmp        =0 ; //S46的K基于时间2N点独立样本T超限        
		double pres_st_s46_k_tsmp_x_avg = pres_st_s46_k ; //0~N点的数据均值 PRES_ST_S46_K
		double pres_st_s46_k_tsmp_y_avg = pres_st_s46_k  ; //N+1~2N点的数据均值 PRES_ST_S46_K                                                                     
		double pres_st_s46_k_tsmp_sign = 0;
		int pres_st_s46_k_tsmp_alter  =0 ; //超限告警                                                                             
		//int pres_est_time              ; //预估到达门限时间MINUTES
		Date pres_estlimit_date ;//预计到达压力下限的时间   
		
		int pointsN = regVar.getNumberOfPoints();
		
		ArrayList<Double> presS46List = new ArrayList<Double>(pointsN*4);
		ArrayList<Double> presS46_KList = new ArrayList<Double>(pointsN*4);
		ArrayList<Double> timeS46List = new ArrayList<Double>(pointsN*4);
		
		
		//加上本节点数据
		presS46List.add(computedVo.getPres_st_c15_s46());
		timeS46List.add(new Double(computedVo.getDatetime_mid_s46().getTime()+""));
		
		//取的点，不超过2n-1 个
		for(int i=0;i<dataHis.length; i++){
			HashVO hisVo = dataHis[i];
			
			if(i==0){
				int pre1_pointtype = hisVo.getIntegerValue("PRES_ST_S46_K_POINTTYPE");//前面第一个点
				String pre1MsgNo = hisVo.getStringValue("MSG_NO");
				
				computedVo.setDeta_presrate_st_pointtype_1(pre1_pointtype);
				computedVo.setMsgno_1(pre1MsgNo);
			}
			if(i==1){
				int pre2_pointtype = hisVo.getIntegerValue("PRES_ST_S46_K_POINTTYPE");//前面第二个点
				String pre2MsgNo = hisVo.getStringValue("MSG_NO");
				computedVo.setDeta_presrate_st_pointtype_2(pre2_pointtype);
				computedVo.setMsgno_2(pre2MsgNo);
			}
			
			Double pres = hisVo.getDoubleValue("PRES_ST_C15_S46");
			Double k = hisVo.getDoubleValue("PRES_ST_S46_K");
			Date dateS46 = hisVo.getDateValue("DATETIME_MID_S46");
			
			presS46List.add(pres);
			presS46_KList.add(k);
			timeS46List.add(new Double(dateS46.getTime()+""));
			
			int detaPresFwdS = hisVo.getIntegerValue("DETA_PRES_FWD_S");
			
			//有前面的数据中，遇到了换瓶点
			if(detaPresFwdS == DfdVarConst.DETAPRESSTATE_CHANGE){
				logger.debug("在前面2N个数据中遇到换瓶点,换瓶点之前的数据不加入计算！");
				break;
			}
		}
		
		int qryPointsNum = presS46List.size();//实际计算节点数
		if(qryPointsNum > pointsN*2) //样本T检查使用2N个点
			qryPointsNum = pointsN*2;
		
		int n_length,n1_length,n2_length;
		if(qryPointsNum%2 == 1){//奇数个
			n1_length = qryPointsNum/2 + 1;
			n2_length = qryPointsNum/2;
		}else{
			n1_length = qryPointsNum/2 ;
			n2_length = qryPointsNum/2 ;
		}
		
		//n_length 取最小的那一个
		if(qryPointsNum >= pointsN){
			n_length = pointsN;
		}else{
			n_length = qryPointsNum;
		}
		
		//先计算S46基于时间N点斜率 k
		Double[] arrPresS46n = new Double[presS46List.size()];
		Double[] arrTimeS46n = new Double[presS46List.size()];
		for(int m=0; m<presS46List.size(); m++){
			arrPresS46n[m] = presS46List.get(m);
			arrTimeS46n[m] = timeS46List.get(m);
		}
		//求N点一元回归
		MathOneFitResult oneFitRes = functionService.Math_One_OneFit(arrTimeS46n, arrPresS46n);
		pres_st_s46_k = oneFitRes.getK();
		pres_st_s46_b = oneFitRes.getB();
		
		//预估到达门限时间MINUTES  y=kx+b-》 x= (y-b)/k
		Double pres_limt_time = (oxyVar.getOxyPressLower() -pres_st_s46_b )/pres_st_s46_k;
		pres_estlimit_date = new Date(pres_limt_time.longValue());
		System.out.println(pres_estlimit_date);
		
		//把当前计算出的N点斜率加入数据最前面
		presS46_KList.add(0, pres_st_s46_k);
		Double[] arr_2n = new Double[qryPointsNum];//保存所有标准压力数组
		for(int m=0;m<qryPointsNum; m++){
			arr_2n[m] = presS46_KList.get(m);
		}
		
		Double[] arrn1 = new Double[n1_length];//样本T检验 n1
		Double[] arrn2 = new Double[n2_length];//样本T检验 n2
		Double[] arrn = new Double[n_length];//做平均、方差计算
		
		System.arraycopy(arr_2n, 0, arrn, 0, n_length);
		
		//求平均和正态计算
		MathCountResult mathRes = functionService.Math_Count(arrn);
		pres_st_s46_k_avg = mathRes.getAvg();
		pres_st_s46_k_std = mathRes.getStd();
		pres_st_s46_k_out = computeSTDOut(pres_st_s46_k,pres_st_s46_k_avg,pres_st_s46_k_std);
		
		//记为飘点
		if(pres_st_s46_k_out == 1){
			pres_st_s46_k_pointtype = DfdVarConst.POINTTYPE_FLOAT;
		}
		
		//数据逆序排序
		System.arraycopy(arr_2n, 0, arrn2, 0, n2_length);
		System.arraycopy(arr_2n, n2_length, arrn1, 0, n1_length);
		
		if(qryPointsNum > 3){
			MathCountResult vag_n1 = functionService.Math_Count(arrn1);
			
			pres_st_s46_k_tsmp_x_avg = vag_n1.getAvg();
			
			MathCountResult vag_n2 = functionService.Math_Count(arrn2);
			pres_st_s46_k_tsmp_y_avg = vag_n2.getAvg();
			
			Double alpha = regVar.getAlpha();
			String tail = "both";
			Math_TTestResult t_testRes = functionService.Math_t_test(arrn1, arrn2, alpha, tail);
			pres_st_s46_k_tsmp = t_testRes.getDiff();
			pres_st_s46_k_tsmp_sign = t_testRes.getSignificance();
			
			//T检验告警
//			DETA_PRESRATE_ST_TSMP=1 且alpha小于预设值(0.05)时 表示 两组数据有明显差异, 并且两组数据差值超过50%
//			如果 DETA_PRESRATE_ST_TSMP_X_AVG>DETA_PRESRATE_ST_TSMP_Y_AVG 表示情况变好，赋值为-1。 反之赋值为1，情况在变坏。
//			其它情况为0
			if(pres_st_s46_k_tsmp ==1 && pres_st_s46_k_tsmp_sign < regVar.getAlpha() ){
				if( MathUtil.isValueBigDiff(pres_st_s46_k_tsmp_x_avg,pres_st_s46_k_tsmp_y_avg,regVar.getDiffValuePercent())){
					if(pres_st_s46_k_tsmp_x_avg > pres_st_s46_k_tsmp_y_avg){
						pres_st_s46_k_tsmp_alter = -1;
					}else{
						pres_st_s46_k_tsmp_alter = 1;
					}
				}
			}
		}
	
		
		computedVo.setPres_st_s46_k(pres_st_s46_k);
		computedVo.setPres_st_s46_b(pres_st_s46_b);
		computedVo.setPres_st_s46_k_avg(pres_st_s46_k_avg);
		computedVo.setPres_st_s46_k_std(pres_st_s46_k_std);
		computedVo.setPres_st_s46_k_out(pres_st_s46_k_out);
		computedVo.setPres_st_s46_k_pointtype(pres_st_s46_k_pointtype);
		computedVo.setPres_st_s46_k_tsmp(pres_st_s46_k_tsmp);
		computedVo.setPres_st_s46_k_tsmp_x_avg(pres_st_s46_k_tsmp_x_avg);
		computedVo.setPres_st_s46_k_tsmp_y_avg(pres_st_s46_k_tsmp_y_avg);
		computedVo.setPres_st_s46_k_tsmp_sign(pres_st_s46_k_tsmp_sign);
		computedVo.setPres_st_s46_k_tsmp_alter(pres_st_s46_k_tsmp_alter);
		computedVo.setPres_estlimit_date(pres_estlimit_date);
		
		logger.debug("N点斜率K值回归计算完成！");
		
	}
	
	/**
	 * 算标态压力5点均
	 * @param a23vo
	 * @param computedVo
	 * @param dataHis
	 * @throws Exception
	 */
	private void computePresStC15_Roll5(HashVO a23vo,A23ComputeVo computedVo,HashVO[] dataHis) throws Exception{
		double pres_st_c15_s13_roll5 = computedVo.getPres_st_c15_s13();//S13折算压力15度 5点平均
		double pres_st_c15_s46_roll5 = computedVo.getPres_st_c15_s46();//S46折算压力15度 5点平均
		double deta_pres_fwd_roll5 = computedVo.getDeta_pres_fwd();//当前S13标态压力与前一报文标态S46压力差 5点平均
		
		if(DfdVarConst.DETAPRESSTATE_CHANGE == computedVo.getDeta_pres_fwd_s()){
			//如果是换瓶点，取平均值
		}else{
			ArrayList<Double> s13List = new ArrayList<Double>(5);
			ArrayList<Double> s46List = new ArrayList<Double>(5);
			ArrayList<Double> fwdList = new ArrayList<Double>(5);
			
			//加上本节点
			s13List.add(computedVo.getPres_st_c15_s13());
			s46List.add(computedVo.getPres_st_c15_s46());
			fwdList.add(computedVo.getDeta_pres_fwd());
			
			//不超过4个点
			for(int i=0;i<dataHis.length && i<4; i++){
				HashVO hisVo = dataHis[i];
				
				int detaPresFwdS = hisVo.getIntegerValue("DETA_PRES_FWD_S");
				
				s13List.add(hisVo.getDoubleValue("PRES_ST_C15_S13"));
				s46List.add(hisVo.getDoubleValue("PRES_ST_C15_S46"));
				fwdList.add(hisVo.getDoubleValue("DETA_PRES_FWD"));
				
				//有前面的数据中，遇到了换瓶点
				if(detaPresFwdS == DfdVarConst.DETAPRESSTATE_CHANGE){
					logger.debug("在前面2N个数据中遇到换瓶点,换瓶点之前的数据不加入计算！");
					break;
				}
			}
			
			Double[] arr = new Double[s13List.size()];
			MathCountResult s13Res = functionService.Math_Count(s13List.toArray(arr));
			pres_st_c15_s13_roll5 = s13Res.getAvg();
			
			MathCountResult s46Res = functionService.Math_Count(s46List.toArray(arr));
			pres_st_c15_s46_roll5 = s46Res.getAvg();
			
			MathCountResult fwdRes = functionService.Math_Count(fwdList.toArray(arr));
			deta_pres_fwd_roll5 = fwdRes.getAvg();
		}
		
		computedVo.setPres_st_c15_s13_roll5(pres_st_c15_s13_roll5);
		computedVo.setPres_st_c15_s46_roll5(pres_st_c15_s46_roll5);
		computedVo.setDeta_pres_fwd_roll5(deta_pres_fwd_roll5);
		
		logger.info("标态压力、压力差5点回归计算完毕！");
	}
	
	/**
	 * 获取
	 * @param a23vo
	 * @param pressSt_C15_S46
	 * @param datetime_mid_s46
	 * @param msgNo
	 * @return 是否查到24小时前报文数据
	 * @throws Exception
	 */
	private boolean getDetaPresStS46H24(HashVO a23vo,A23ComputeVo computedVo) throws Exception{
		double deta_pres_sts46h24 = 0;
		long	deta_time_mins46h24 =0;              
		double	deta_presrate_sts46h24 = computedVo.getDeta_presrate_st();
		
		boolean isFindData = false;//是否找到了24小时前的报文
		
		//如果24小时内换过氧气瓶，存在DETA_PRES_FWD_S=1，则获取换瓶点的PRES_ST_C15_S46得到差值，如果是换瓶点 此数据为0
		//1,--首先判断24小时之内是否换瓶
		String sql1 = "select t.pres_st_c15_s46,datetime_mid_s46 from a_dfd_a23_compute t where t.msg_no < "+ computedVo.getMsgno()
			+" and t.datetime_mid_s46 > to_date('"+DateUtil.getLongDate(computedVo.getDatetime_mid_s46())+"','YYYY-MM-DD HH24:MI:SS')-1 "
			+" and t.deta_pres_fwd_s=1";//换瓶标志
		
		HashVO[] vos1 =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql1);
		//查到数据，有换瓶的情况
		if(vos1.length > 0){
			double temp = vos1[0].getDoubleValue("pres_st_c15_s46");
			deta_pres_sts46h24 = computedVo.getPres_st_c15_s46() -temp;
			Date timePre = vos1[0].getDateValue("datetime_mid_s46");
			
			deta_time_mins46h24 = (timePre.getTime()-computedVo.getDatetime_mid_s46().getTime() )/(1000*60); //计算时间差
			deta_pres_sts46h24 = computedVo.getPres_st_c15_s46() -temp; //压力差
			
			deta_presrate_sts46h24 = deta_pres_sts46h24/deta_time_mins46h24 *60*24 ;
			
		}else{
			//--取回滚到24小时前的最近的一条报文的S46标态压力与本报文的S46标态压力的差值
			String sql2 = "SELECT * FROM ( "
				+ "select t.pres_st_c15_s46,t.datetime_mid_s46,t.deta_pres_fwd_s from a_dfd_a23_compute t where t.msg_no < "+ computedVo.getMsgno()
				+ " and t.datetime_mid_s46 < to_date('"+DateUtil.getLongDate(computedVo.getDatetime_mid_s46())+"','YYYY-MM-DD HH24:MI:SS')-1 "
			    + " ORDER BY MSG_NO DESC ) WHERE ROWNUM =1 ";
			
			HashVO[] vos2 =dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sql2);
			if(vos2.length > 0){
				double temp = vos2[0].getDoubleValue("pres_st_c15_s46");
				Date timePre = vos2[0].getDateValue("datetime_mid_s46");
				
				deta_time_mins46h24 = (timePre.getTime()-computedVo.getDatetime_mid_s46().getTime() )/(1000*60); //计算时间差
				deta_pres_sts46h24 = computedVo.getPres_st_c15_s46() -temp; //压力差
				
				deta_presrate_sts46h24 = deta_pres_sts46h24/deta_time_mins46h24 *60*24 ;
				
				isFindData = true;
			}
			
		}
		
		computedVo.setDeta_pres_sts46h24(deta_pres_sts46h24);
		computedVo.setDeta_time_mins46h24(deta_time_mins46h24);
		computedVo.setDeta_presrate_sts46h24(deta_presrate_sts46h24);
		
		return isFindData;
	}
	
	private void computeDetaPresRateSt24H(HashVO a23vo,A23ComputeVo computedVo,HashVO[] dataHis,int deta_pres_fwd_s) throws Exception{
          
		double	deta_presrate_sts46h24_roll5 = computedVo.getDeta_presrate_st();         
		double	deta_presrate_st_avgs46h24 = computedVo.getDeta_presrate_st();  
		double	deta_presrate_st_stds46h24 = 0;           
		int	deta_presrate_st_outs46h24 =0;           
		int	deta_presrate_st_s46h24_ptype =0;    
		double	deta_presrate_h24_tsmp_x_avg = computedVo.getDeta_presrate_st();
		double	deta_presrate_h24_tsmp_y_avg = computedVo.getDeta_presrate_st();
		int	deta_presrate_h24_tsmp =0;
		double  deta_presrate_h24_tsmp_sign =0;
		int	deta_presrate_h24_tsmp_alter =0;
		 
		//如果本节点是换瓶点，取默认值，不用计算了
		if(deta_pres_fwd_s == DfdVarConst.DETAPRESSTATE_CHANGE){
			//默认值已有，无需计算
			
		}else if( getDetaPresStS46H24(a23vo, computedVo) ){
			double deta_presrate_sts46h24 = computedVo.getDeta_presrate_st_stds46h24();
			
			int pointsN = regVar.getNumberOfPoints();//N点数
			
			ArrayList<Double> detaPresrateStList = new ArrayList<Double>(pointsN*2);
			int pre1_pointtype=0,pre2_pointtype=0;//该点前面两个点的点类型
			String pre1MsgNo,pre2MsgNo;//该点前面两个点报文编号
			
			//先加入本节点数据
			detaPresrateStList.add(deta_presrate_sts46h24);
			
			//取的点，不超过2n-1 个
			for(int i=0;i<dataHis.length && i<pointsN*2-1; i++){
				HashVO hisVo = dataHis[i];
				int pointType = hisVo.getIntegerValue("DETA_PRESRATE_ST_S46H24_PTYPE");
				
				int detaPresFwdS = hisVo.getIntegerValue("DETA_PRES_FWD_S");
				
				if(i==0){
					pre1_pointtype = hisVo.getIntegerValue("DETA_PRESRATE_ST_S46H24_PTYPE");//前面第一个点
					pre1MsgNo = hisVo.getStringValue("MSG_NO");
					
					computedVo.setDeta_presrate_st_s46h24_ptype_2(pre1_pointtype);
					computedVo.setMsgno_1(pre1MsgNo);
				}
				if(i==1){
					pre2_pointtype = hisVo.getIntegerValue("DETA_PRESRATE_ST_S46H24_PTYPE");//前面第二个点
					pre2MsgNo = hisVo.getStringValue("MSG_NO");
					computedVo.setDeta_presrate_st_s46h24_ptype_1(pre2_pointtype);
					computedVo.setMsgno_2(pre2MsgNo);
				}
				
				//只有实点才加入计算
				if(pointType == DfdVarConst.POINTTYPE_REAL){
					Double h_deta_presrate_st = hisVo.getDoubleValue("DETA_PRESRATE_STS46H24");
					detaPresrateStList.add(h_deta_presrate_st);
				}
				
				//有前面的数据中，遇到了换瓶点
				if(detaPresFwdS == DfdVarConst.DETAPRESSTATE_CHANGE){
					logger.debug("在前面2N个数据中遇到换瓶点,换瓶点之前的数据不加入计算！");
					break;
				}
			}
			
			int qryPointsNum = detaPresrateStList.size();//实际计算节点数
			Double[] arr_deta_presrate_st = new Double[qryPointsNum];//保存所有标准压力数组
			detaPresrateStList.toArray(arr_deta_presrate_st);
			
			int n_length,n1_length,n2_length;
			if(qryPointsNum%2 == 1){//奇数个
				n1_length = qryPointsNum/2 + 1;
				n2_length = qryPointsNum/2;
			}else{
				n1_length = qryPointsNum/2 ;
				n2_length = qryPointsNum/2 ;
			}
			
			//n_length 取最小的那一个
			if(qryPointsNum >= pointsN){
				n_length = pointsN;
			}else{
				n_length = qryPointsNum;
			}
			
			int roll5_length = 5;
			if(qryPointsNum < 5)//不足5点按实际点计算
				roll5_length = qryPointsNum;
			
			Double[] arrRoll5 = new Double[roll5_length];//5占均值
					
			//5点均，在已有N点均的情况下，还要算5点均吗？ YES
			System.arraycopy(arr_deta_presrate_st, 0, arrRoll5, 0, roll5_length);
			//5点均计算
			MathCountResult res = functionService.Math_Count(arrRoll5);
			deta_presrate_sts46h24_roll5 = res.getAvg();
			
			Double[] arrn = new Double[n_length];//做回归计算
			
			System.arraycopy(arr_deta_presrate_st, 0, arrn, 0, n_length);
			
			//求平均和正态计算
			MathCountResult presRateRes = functionService.Math_Count(arrn);
			deta_presrate_st_avgs46h24 = presRateRes.getAvg();
			deta_presrate_st_stds46h24 = presRateRes.getStd();
			deta_presrate_st_outs46h24 = computeSTDOut(deta_presrate_sts46h24,deta_presrate_st_avgs46h24,deta_presrate_st_stds46h24);
			
			if(deta_presrate_st_outs46h24 == 1){
				deta_presrate_st_s46h24_ptype = DfdVarConst.POINTTYPE_FLOAT;
				if(pre1_pointtype== DfdVarConst.POINTTYPE_FLOAT && pre2_pointtype == DfdVarConst.POINTTYPE_FLOAT){
					//如果该点是飘点，还要判断其前两个点是否是飘点，如果连续出现3个飘点，发告警。并把此3个飘点标记为实点
					//卡片只计算结果，后续处理由ComputeVo完成
				}
			}
			
			//数据从当前点开始，倒序排列
			Double[] arrn1 = new Double[n1_length];//样本T检验 n1
			Double[] arrn2 = new Double[n2_length];//样本T检验 n2
			System.arraycopy(arr_deta_presrate_st, 0, arrn2, 0, n2_length);
			System.arraycopy(arr_deta_presrate_st, n2_length, arrn1, 0, n1_length);
			
			if(qryPointsNum > 3){
				MathCountResult vag_n1 = functionService.Math_Count(arrn1);
				deta_presrate_h24_tsmp_x_avg = vag_n1.getAvg();
				
				MathCountResult vag_n2 = functionService.Math_Count(arrn2);
				deta_presrate_h24_tsmp_y_avg = vag_n2.getAvg();
				
				Double alpha = regVar.getAlpha();
				String tail = "both";
				Math_TTestResult t_testRes = functionService.Math_t_test(arrn1, arrn2, alpha, tail);
				deta_presrate_h24_tsmp = t_testRes.getDiff();
				deta_presrate_h24_tsmp_sign = t_testRes.getSignificance();
				
				//T检验告警
				if(deta_presrate_h24_tsmp ==1 && deta_presrate_h24_tsmp_sign < regVar.getAlpha() ){
					if( MathUtil.isValueBigDiff(deta_presrate_h24_tsmp_x_avg,deta_presrate_h24_tsmp_y_avg,regVar.getDiffValuePercent())){
						if(deta_presrate_h24_tsmp_x_avg > deta_presrate_h24_tsmp_y_avg)
							deta_presrate_h24_tsmp_alter = -1;
						else
							deta_presrate_h24_tsmp_alter = 1;
					}
				}
				
			}
		}
		
		
		computedVo.setDeta_presrate_sts46h24_roll5(deta_presrate_sts46h24_roll5);
		computedVo.setDeta_presrate_st_avgs46h24(deta_presrate_st_avgs46h24);
		computedVo.setDeta_presrate_st_stds46h24(deta_presrate_st_stds46h24);
		computedVo.setDeta_presrate_st_outs46h24(deta_presrate_st_outs46h24);
		computedVo.setDeta_presrate_st_s46h24_ptype(deta_presrate_st_s46h24_ptype);
		computedVo.setDeta_presrate_h24_tsmp_x_avg(deta_presrate_h24_tsmp_x_avg);
		computedVo.setDeta_presrate_h24_tsmp_y_avg(deta_presrate_h24_tsmp_y_avg);
		computedVo.setDeta_presrate_h24_tsmp(deta_presrate_h24_tsmp);
		computedVo.setDeta_presrate_h24_tsmp_sign(deta_presrate_h24_tsmp_sign);
		computedVo.setDeta_presrate_h24_tsmp_alter(deta_presrate_h24_tsmp_alter);
		
		logger.debug("S46的24小时掉压率计算完成！");
		
	}
	
	/**
	 * 掉压率回归计算
	 * @param a23vo
	 * @param computedVo
	 * @param deta_pres_fwd_s
	 * @throws Exception
	 */
	private void computeDetaPresRateSt(HashVO a23vo,A23ComputeVo computedVo,HashVO[] dataHis,int deta_pres_fwd_s) throws Exception{
		
		double deta_presrate_st = computedVo.getDeta_presrate_st();//本报文掉压率
		double deta_presrate_st_roll5 = deta_presrate_st;//5点均
		double deta_presrate_st_avg         = deta_presrate_st; //本报文掉压率N点均  回滚N点DETA_PRESRATE_ST数据 求均值AVG
		double deta_presrate_st_std         = 0; //本报文掉压率N点均方 回滚N点DETA_PRESRATE_ST数据 求均值STD
		int deta_presrate_st_out           = 0; //本报文掉压率DETA超限  超过2个 正负STD的范围 值为1 报警并把该点标记为飘点   在其内设为0 不报警
		int deta_presrate_st_pointtype     =0;//0-普通点(实点) 1-飘点   2--不良数据连续出现3次时，把这3个飘点改为实点
		double deta_presrate_st_tsmp_x_avg  = deta_presrate_st; //0~N点的数据均值 
		double deta_presrate_st_tsmp_y_avg  = deta_presrate_st; //N+1~2N点的数据均值 
		int deta_presrate_st_tsmp        = 0; //T检验，差异结果1-有差异，0-无差异
		double deta_presrate_st_tsmp_sign   = 0; //显著性 一般与alpha比较，如果小于alpha那么 这个Diff 结果可信
		int deta_presrate_st_tsmp_alter  = 0; //值为1时，发告警
		
		//如果本节点是换瓶点，取默认值，不用计算了
		if(deta_pres_fwd_s == DfdVarConst.DETAPRESSTATE_CHANGE){
			//默认值已有，无需计算
			
		}else{
			int pointsN = regVar.getNumberOfPoints();//N点数
			
			ArrayList<Double> detaPresrateStList = new ArrayList<Double>(pointsN*2);
			int pre1_pointtype=0,pre2_pointtype=0;//该点前面两个点的点类型
			String pre1MsgNo,pre2MsgNo;//该点前面两个点报文编号
			
			//先加入本节点数据
			detaPresrateStList.add(deta_presrate_st);
			
			//取的点，不超过2n-1 个
			for(int i=0;i<dataHis.length && i<pointsN*2-1; i++){
				HashVO hisVo = dataHis[i];
				int pointType = hisVo.getIntegerValue("DETA_PRESRATE_ST_POINTTYPE");
				
				int detaPresFwdS = hisVo.getIntegerValue("DETA_PRES_FWD_S");
				
				if(i==0){
					pre1_pointtype = hisVo.getIntegerValue("DETA_PRESRATE_ST_POINTTYPE");//前面第一个点
					pre1MsgNo = hisVo.getStringValue("MSG_NO");
					
					computedVo.setDeta_presrate_st_pointtype_1(pre1_pointtype);
					computedVo.setMsgno_1(pre1MsgNo);
				}
				if(i==1){
					pre2_pointtype = hisVo.getIntegerValue("DETA_PRESRATE_ST_POINTTYPE");//前面第二个点
					pre2MsgNo = hisVo.getStringValue("MSG_NO");
					computedVo.setDeta_presrate_st_pointtype_2(pre2_pointtype);
					computedVo.setMsgno_2(pre2MsgNo);
				}
				
				//只有实点才加入计算
				if(pointType == DfdVarConst.POINTTYPE_REAL){
					Double h_deta_presrate_st = hisVo.getDoubleValue("DETA_PRESRATE_ST");
					detaPresrateStList.add(h_deta_presrate_st);
				}
				
				//有前面的数据中，遇到了换瓶点
				if(detaPresFwdS == DfdVarConst.DETAPRESSTATE_CHANGE){
					logger.debug("在前面2N个数据中遇到换瓶点,换瓶点之前的数据不加入计算！");
					break;
				}
			}
			
			int qryPointsNum = detaPresrateStList.size();//实际计算节点数
			Double[] arr_deta_presrate_st = new Double[qryPointsNum];//保存所有标准压力数组
			detaPresrateStList.toArray(arr_deta_presrate_st);
			
			int n_length,n1_length,n2_length;
			if(qryPointsNum%2 == 1){//奇数个
				n1_length = qryPointsNum/2 + 1;
				n2_length = qryPointsNum/2;
			}else{
				n1_length = qryPointsNum/2 ;
				n2_length = qryPointsNum/2 ;
			}
			
			//n_length 取最小的那一个
			if(qryPointsNum >= pointsN){
				n_length = pointsN;
			}else{
				n_length = qryPointsNum;
			}
			
			int roll5_length = 5;
			if(qryPointsNum < 5)//不足5点按实际点计算
				roll5_length = qryPointsNum;
			
			Double[] arrRoll5 = new Double[roll5_length];//5占均值
					
			//5点均，在已有N点均的情况下，还要算5点均吗？ YES
			System.arraycopy(arr_deta_presrate_st, 0, arrRoll5, 0, roll5_length);
			//5点均计算
			MathCountResult res = functionService.Math_Count(arrRoll5);
			deta_presrate_st_roll5 = res.getAvg();
			
			Double[] arrn = new Double[n_length];//做回归计算
			
			System.arraycopy(arr_deta_presrate_st, 0, arrn, 0, n_length);
			
			//求平均和正态计算
			MathCountResult presRateRes = functionService.Math_Count(arrn);
			deta_presrate_st_avg = presRateRes.getAvg();
			deta_presrate_st_std = presRateRes.getStd();
			deta_presrate_st_out = computeSTDOut(deta_presrate_st,deta_presrate_st_avg,deta_presrate_st_std);
			
			if(deta_presrate_st_out == 1){
				deta_presrate_st_pointtype = DfdVarConst.POINTTYPE_FLOAT;
				if(pre1_pointtype== DfdVarConst.POINTTYPE_FLOAT && pre2_pointtype == DfdVarConst.POINTTYPE_FLOAT){
					//如果该点是飘点，还要判断其前两个点是否是飘点，如果连续出现3个飘点，发告警。并把此3个飘点标记为实点
					//卡片只计算结果，后续处理由ComputeVo完成
				}
			}
			
			//数据从当前点开始，倒序排列
			Double[] arrn1 = new Double[n1_length];//样本T检验 n1
			Double[] arrn2 = new Double[n2_length];//样本T检验 n2
			System.arraycopy(arr_deta_presrate_st, 0, arrn2, 0, n2_length);
			System.arraycopy(arr_deta_presrate_st, n2_length, arrn1, 0, n1_length);
			
			if(qryPointsNum > 3){
				MathCountResult vag_n1 = functionService.Math_Count(arrn1);
				deta_presrate_st_tsmp_x_avg = vag_n1.getAvg();
				
				MathCountResult vag_n2 = functionService.Math_Count(arrn2);
				deta_presrate_st_tsmp_y_avg = vag_n2.getAvg();
				
				Double alpha = regVar.getAlpha();
				String tail = "both";
				Math_TTestResult t_testRes = functionService.Math_t_test(arrn1, arrn2, alpha, tail);
				deta_presrate_st_tsmp = t_testRes.getDiff();
				deta_presrate_st_tsmp_sign = t_testRes.getSignificance();
				//T检验告警
				if(deta_presrate_st_tsmp ==1 && deta_presrate_st_tsmp_sign < regVar.getAlpha() ){
					//两组数据平均值相差50%时，进行告警
					if( MathUtil.isValueBigDiff(deta_presrate_st_tsmp_x_avg,deta_presrate_st_tsmp_y_avg,regVar.getDiffValuePercent())){
						if(deta_presrate_st_tsmp_x_avg > deta_presrate_st_tsmp_y_avg)
							deta_presrate_st_tsmp_alter = -1;
						else
							deta_presrate_st_tsmp_alter = 1;
					}
				}
			}
		}
		
		computedVo.setDeta_presrate_st_avg(deta_presrate_st_avg);
		computedVo.setDeta_presrate_st_std(deta_presrate_st_std);
		computedVo.setDeta_presrate_st_out(deta_presrate_st_out);
		computedVo.setDeta_presrate_st_pointtype(deta_presrate_st_pointtype);
		computedVo.setDeta_presrate_st_tsmp_x_avg(deta_presrate_st_tsmp_x_avg);
		computedVo.setDeta_presrate_st_tsmp_y_avg(deta_presrate_st_tsmp_y_avg);
		computedVo.setDeta_presrate_st_tsmp(deta_presrate_st_tsmp);
		computedVo.setDeta_presrate_st_tsmp_sign(deta_presrate_st_tsmp_sign);
		computedVo.setDeta_presrate_st_tsmp_alter(deta_presrate_st_tsmp_alter);
		computedVo.setDeta_presrate_st_roll5(deta_presrate_st_roll5);
		
		logger.debug("掉压率回归计算完毕！");
	}
	
	/**
	 * 本报文基本字段计算
	 * @param a23vo
	 * @param computedVo
	 * @throws Exception
	 */
	private void computeBasic(HashVO a23vo,A23ComputeVo computedVo) throws Exception{
		double ckptt_mid_s13 = MathUtil.getMiddleValue(a23vo.getDoubleValue("CKPTTS1"),a23vo.getDoubleValue("CKPTTS2"),a23vo.getDoubleValue("CKPTTS3"));
		computedVo.setCkptt_mid_s13(ckptt_mid_s13);
		
		double ckptt_mid_s46 = MathUtil.getMiddleValue(a23vo.getDoubleValue("CKPTTS4"),a23vo.getDoubleValue("CKPTTS5"),a23vo.getDoubleValue("CKPTTS6"));
		computedVo.setCkptt_mid_s46(ckptt_mid_s46);
		
		double sat_mid_s13 = MathUtil.getMiddleValue(a23vo.getDoubleValue("SATS1"),a23vo.getDoubleValue("SATS2"),a23vo.getDoubleValue("SATS3"));
		computedVo.setSat_mid_s13(sat_mid_s13);
		
		double sat_mid_s46 = MathUtil.getMiddleValue(a23vo.getDoubleValue("SATS4"),a23vo.getDoubleValue("SATS5"),a23vo.getDoubleValue("SATS6"));
		computedVo.setSat_mid_s46(sat_mid_s46);
		
		double pres_mid_s13 = MathUtil.getMiddleValue(a23vo.getDoubleValue("PRESS1"),a23vo.getDoubleValue("PRESS2"),a23vo.getDoubleValue("PRESS3"));
		computedVo.setPres_mid_s13(pres_mid_s13);
		
		double pres_mid_s46 = MathUtil.getMiddleValue(a23vo.getDoubleValue("PRESS4"),a23vo.getDoubleValue("PRESS5"),a23vo.getDoubleValue("PRESS6"));
		computedVo.setPres_mid_s46(pres_mid_s46);
		
		Date datetime_mid_s13 = MathUtil.getMiddleDate(a23vo.getDateValue("DATETIMES1"),
							a23vo.getDateValue("DATETIMES2"),a23vo.getDateValue("DATETIMES3"));
		computedVo.setDatetime_mid_s13(datetime_mid_s13);
		
		Date datetime_mid_s46 = MathUtil.getMiddleDate(a23vo.getDateValue("DATETIMES4"),
				a23vo.getDateValue("DATETIMES5"),a23vo.getDateValue("DATETIMES6"));
		computedVo.setDatetime_mid_s46(datetime_mid_s46);
		
		//计算标态气压
	    double nr1 = getStandardPressure(a23vo.getDoubleValue("CKPTTS1"),a23vo.getDoubleValue("SATS1"),a23vo.getDoubleValue("PRESS1"));
	    computedVo.setNr1(nr1);
	    
		double nr2 = getStandardPressure(a23vo.getDoubleValue("CKPTTS2"),a23vo.getDoubleValue("SATS2"),a23vo.getDoubleValue("PRESS2"));
		computedVo.setNr2(nr2);
		double nr3 = getStandardPressure(a23vo.getDoubleValue("CKPTTS3"),a23vo.getDoubleValue("SATS3"),a23vo.getDoubleValue("PRESS3"));
		computedVo.setNr3(nr3);
		double nr4 = getStandardPressure(a23vo.getDoubleValue("CKPTTS4"),a23vo.getDoubleValue("SATS4"),a23vo.getDoubleValue("PRESS4"));
		computedVo.setNr4(nr4);
		double nr5 = getStandardPressure(a23vo.getDoubleValue("CKPTTS5"),a23vo.getDoubleValue("SATS5"),a23vo.getDoubleValue("PRESS5"));
		computedVo.setNr5(nr5);
		double nr6 = getStandardPressure(a23vo.getDoubleValue("CKPTTS6"),a23vo.getDoubleValue("SATS6"),a23vo.getDoubleValue("PRESS6"));
		computedVo.setNr6(nr6);
		double nr7 = getStandardPressure(a23vo.getDoubleValue("CKPTTS7"),a23vo.getDoubleValue("SATS7"),a23vo.getDoubleValue("PRESS7"));
		computedVo.setNr7(nr7);
		
		double pressSt_C15_S13 = MathUtil.getMiddleValue(nr1,nr2,nr3);
		computedVo.setPres_st_c15_s13(pressSt_C15_S13);
		
		double pressSt_C15_S46 = MathUtil.getMiddleValue(nr4,nr5,nr6);
		computedVo.setPres_st_c15_s46(pressSt_C15_S46);
		
		double deta_pres_st = pressSt_C15_S13 - pressSt_C15_S46; //本报文掉压 ST_S46-ST_S13
		computedVo.setDeta_pres_st(deta_pres_st);
		
		//本报文S13\S46时间差minutes  DATETIME_MID_S46-DATETIME_MID_S13
		long  deta_time_min =  (datetime_mid_s46.getTime()- datetime_mid_s13.getTime()) /(1000*60); 
		computedVo.setDeta_time_min(deta_time_min);
		
		//本报文掉压率	DETA_PRES_ST/DETA_TIME_MIN*60*24 单位要换算成 PSI/天（24小时）
		double deta_presrate_st = deta_pres_st/deta_time_min *60*24 ;
		computedVo.setDeta_presrate_st(deta_presrate_st);
		
		//压力告警标识
		int pres_s46_warning = computePresS46Warning(pres_mid_s13, pres_mid_s46, pressSt_C15_S13, pressSt_C15_S46);
		computedVo.setPres_s46_warning(pres_s46_warning);
		
		logger.debug("本报文基础数据计算完毕！");
	}
	
	/**
	 * DETA超限值计算
	 * @param presRateSt
	 * @param presRateStAvg
	 * @param presRateStStd
	 * @return
	 */
	private int computeSTDOut(double presRateSt,double presRateStAvg,double presRateStStd){
		double dValue = Math.abs(presRateSt-presRateStAvg);
		
		if(presRateStStd ==0){ //方差为0，肯定没有超限
			return 0;
		}
		
		//本报文掉压率DETA超限  本点-平均值. 超过2个 正负STD的范围 值为1 报警	否则设为0 不报警
		int n_dstd = regVar.getNumberOfStdToAlarm();
		//超过几个STD范围要求可配置
		if( dValue >=  n_dstd*presRateStStd ){
			return 1;
		}else{
			return 0;
		}
		
	}
	
	/**
	 * 计算压力差状态转换
	 * @param deta_pres_fwd
	 * @return
	 */
	private int computeDetaPresState(double deta_pres_fwd){
		int deta_pres_fwd_s = DfdVarConst.DETAPRESSTATE_NOMARL;
		if(deta_pres_fwd < oxyVar.getDecompress_threshold_lower()){
			deta_pres_fwd_s = DfdVarConst.DETAPRESSTATE_LOWER;//泄压门限=-1
		}else if(deta_pres_fwd > oxyVar.getDecompress_threshold_upper()){
			deta_pres_fwd_s = DfdVarConst.DETAPRESSTATE_CHANGE;//x>200 换瓶=1
		}else{
			deta_pres_fwd_s = DfdVarConst.DETAPRESSTATE_NOMARL;//正常泄压=0

		}
		return deta_pres_fwd_s;
	}
	
	/**
	 * 计算告警阈值门限标记
	 * @param ps13
	 * @param ps46
	 * @param ps13st
	 * @param ps46st
	 * @return
	 */
	private int computePresS46Warning(double ps13,double ps46,double ps13st,double ps46st){
		/** 根据告警阈值门限标记如
		x>2500=3
		2300<x<2500=2
		2000<x<2300=1
		正常 2000~1750=0
		低于 1750>x>1600=-1
		低于 1600=-2
		低于 1500=-3
		**/
		
		int lower3 = 1500;
		int lower2 = 1600;
		int lower1 = 1750;
	    int upper1 = 2300;
	    int upper2 = 2500;
		
	    int pres_s46_warning = 0;
	    if(ps13 <lower3 || ps46<lower3 || ps13st<lower3 || ps46st<lower3){
			return -3;
		}else if(ps13 <lower2 || ps46<lower2 || ps13st<lower2 || ps46st<lower2){
			return -2;
		}else if(ps13 <lower1 || ps46<lower1 || ps13st<lower1 || ps46st<lower1){
			return -1;
		}else if(ps13 >upper2 || ps46>upper2 || ps13st>upper2 || ps46st>upper2){
			return 2;
		}else if(ps13 >upper1 || ps46>upper1 || ps13st>upper1 || ps46st>upper1){
			return 1;
		}
		
		return pres_s46_warning;
		
		
	}
	
	/**
	 * 大气温度15度时的压力，可以根据PV/T=PV/T公式转换, P标 = p*t标 / t 
	 * @param ckpt_t 客舱温度
	 * @param sat  静温
	 * @param press  氧气压力
	 * @throws Exception
	 */
	public double getStandardPressure(double ckpt_t,double sat,double press) throws Exception{
		//标态气压    大气温度15度时的压力，可以根据PV/T=PV/T公式转换, P标 = p*t标 / t 
		//PREESS/((CKPT_T+SAT)/2 +273.5)
		double nr = press*(15+ApmsConst.ABSOLUTE_ZERO)  / ( (ckpt_t + sat)/2 + ApmsConst.ABSOLUTE_ZERO);
		
		return nr;
	}

}
