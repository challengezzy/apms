package com.apms.matlab;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.apms.bs.util.StringUtil;
import com.apms.matlab.function.ApmsMatlabFuncService;
import com.apms.matlab.vo.BintProgResult;
import com.apms.matlab.vo.FftCountResult;
import com.apms.matlab.vo.MathCountResult;
import com.apms.matlab.vo.MathOneFitResult;
import com.apms.matlab.vo.MathOneXFitResult;
import com.apms.matlab.vo.MathOneXPerdictorAeraResult;
import com.apms.matlab.vo.Math_TTestResult;
import com.apms.matlab.vo.Spwvd_FigureResult;
import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWJavaObjectRef;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.mathworks.toolbox.javabuilder.webfigures.WebFigure;

/**
 *Matlab函数调用服务类
 *@date Dec 2, 2012
 **/
public class MatlabFunctionService {
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static ApmsMatlabFuncService funcService;//只实例化一个就可以
	
	public MatlabFunctionService(){
		try {
			if(funcService == null)
				funcService = new ApmsMatlabFuncService();
			
		} catch (MWException e) {
			System.out.println("ApmsMatlabFuncService构造异常！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 简单数据统计计算
	 * function[ValueSum,ValueAvg,ValueStd,Row_Num,Col_Num]=Math_Count(OneD_ArryValue)
	 * @param OneD_ArryValue 1维数组
	 * @return ValueSum 数组的和 ,ValueAvg 数组的均值 ,ValueStd 数组的方差  
	 * @throws Exception
	 */
	public MathCountResult Math_Count(Double[] array)throws Exception{
		//构建入参
		//logger.debug("调用Math_Count计算：");
		MWNumericArray inputObj = new MWNumericArray(array);
		
		Object[] outputArr = funcService.Math_Count(5,inputObj);
		
		MathCountResult result = new MathCountResult();
		result.setSum( new Double(outputArr[0].toString()) );
		result.setAvg( new Double(outputArr[1].toString()) );
		result.setStd( new Double(outputArr[2].toString()) );
		result.setRowNum( new Integer(outputArr[3].toString()) );
		result.setColNum( new Integer(outputArr[4].toString()) );
		
		//logger.debug("调用Math_Count完成！");
		for(int i=0;i<outputArr.length;i++){
			MWArray.disposeArray(outputArr[i]);
		}
		funcService.dispose();
		
		return result;
	}
	
	/**
	 * 用于评估两组数据是否有在统计学意义下的差异 调用 MATLAB Math_t_test 
		[diff,significance,ci]=Math_t_test(x,y,alpha,tail)
		Diff=1 两组数据不一致 =0 两组数据一致
		Significance 显著性 一般与alpha比较，如果小于alpha那么 这个Diff 结果可信
		CI=置信区间，数值在这个范围是可信的
	 * @param X=样本数组1 一般放0~N 点
	 * @param Y=样本数组2 一般放N+1~2N
	 * @param alpha 差异 置信区间为 1-alpha
	 * @param tail 默认为"both"，取值为 both,left,right
	 * @throws Exception
	 */
	public Math_TTestResult Math_t_test(Double[] arrx,Double[] arry,Double alpha,String tail)throws Exception{
		//		x=[];
		//		y=[2,2,2,5,5,5,8,8,8,9];
		//		alpha=0.05;
		//		tail='both';
		//		[diff,significance,ci]=Math_t_test(x,y,alpha,tail)
		//logger.debug("调用Math_t_test计算:");
		MWNumericArray x = new MWNumericArray(arrx);
		MWNumericArray y = new MWNumericArray(arry);
		
		Object[] outputArr = funcService.Math_t_test(3,x,y,alpha,tail);
//		logger.debug("Math_t_test输出参数：--------------------------");
//		for(int i =0;i<outputArr.length;i++){
//			logger.debug("参数"+i+"为：" + outputArr[i]);
//		}
		
		Math_TTestResult result = new Math_TTestResult();
		int diff = 0;
		double sign = 1;
		if("NaN".equalsIgnoreCase(outputArr[0].toString())){
			logger.debug("样本T检验返回的diff为Nan，表示X,Y两个数组完全一致！");
			diff = 0;
			sign = 1;
		}else{
			diff = new Integer(outputArr[0].toString());
			sign = new Double(outputArr[1].toString());
		}
		
		result.setDiff( diff );
		result.setSignificance(sign);
		result.setCi( outputArr[2].toString());
		
		for(int i=0;i<outputArr.length;i++){
			MWArray.disposeArray(outputArr[i]);
		}
		funcService.dispose();
		
		return result;
	}
	
	/**
	 * 一元线性回归 y=kx+b,返回k,b
	 * function [PolyFit_arry,PolyFit_S]=Math_OneXFit(x,y,m)
		% x 自变量数组
		% y 因变量数组
		% n 多项式次数
		% PolyFit_arry 多项式系数数组
		% PolyFit_S 多项式误差估计结构数组
	 * @throws Exception
	 */
	public MathOneFitResult Math_One_OneFit(Double[] arrx,Double[] arry) throws Exception{
		
		//logger.debug("调用Math_OneXFit计算：");
		
		//只计算一元线性回归，返回K\
		Object[] outputArr = funcService.Math_OneXFit(2,arrx,arry,1);
		MathOneFitResult result = new MathOneFitResult();
		
		MWNumericArray r1 = (MWNumericArray)outputArr[0];
		
		double k = r1.getDouble(1);
		double b = r1.getDouble(2);
		
		//设置计算结果
		result.setK(k);
		result.setB(b);
				
		//logger.debug("调用Math_OneXFit完成。");
		for(int i=0;i<outputArr.length;i++){
			MWArray.disposeArray(outputArr[i]);
		}
		funcService.dispose();
		
		return result;
	}
	
	/**
	 * 一元N次回归，  返回一元N次多项式的系数
	 * function [PolyFit_arry,PolyFit_S]=Math_OneXFit(x,y,m)
		% x 自变量数组
		% y 因变量数组
		% n 多项式次数
		% PolyFit_arry 多项式系数数组
		% PolyFit_S 多项式误差估计结构数组
	 * @throws Exception
	 */
	public MathOneXFitResult Math_OneXFit(Double[] arrx,Double[] arry,int times) throws Exception{
		
//		logger.debug("调用Math_OneXFit计算：");
		
		//只计算一元N次回归
		Object[] outputArr = funcService.Math_OneXFit(2,arrx,arry,times);
		MathOneXFitResult result = new MathOneXFitResult();
		result.setTimes(times);
		
		MWNumericArray r1 = (MWNumericArray)outputArr[0];
		double[] coefficients = new double[times+1];
		
		for(int i=1; i<=times+1;i++){
			coefficients[i-1] = r1.getDouble(i);
		}
		
		result.setCoefficients(coefficients);
		
//		logger.debug("调用Math_OneXFit完成。");
		for(int i=0;i<outputArr.length;i++){
			MWArray.disposeArray(outputArr[i]);
		}
		funcService.dispose();
		
		return result;
	}
	
	/**
	 * 产生数据的预测范围
	 * @throws Exception
	 */
	public MathOneXPerdictorAeraResult Math_OneX_Perdictor_Aera(Double[] arrx,Double[] arry,Double[] polyFitArr)
		throws Exception{
		/**
		 * function [Y_Result,DetaValue,Perd_AreaValue,betaa]=Math_OneX_Perdictor_Aera(X_Arry,Y_Arry,PolyFit_arry,Model)
			%产生数据的预测范围
			%Y_Result Y预测值
			%DetaValue 差值
			%UpValue 可信区间为Y_Result+DetaValue
			%DownValue 可信区间为Y_Result+DetaValue
			% PolyFit_arry 多项式系数数组
			% X_Arry 实测X数值
			% Y_Arry 实测Y数值
			% PolyFit_arry 一元N阶回归系数结果
			% Model 回归产生的公式模型;
			% betaa 回归系数的置信区间
		 */

//		logger.debug("调用Math_OneX_Perdictor_Aera计算：");
				
		Object[] outputArr = funcService.Math_OneX_Perdictor_Aera(4,arrx,arry,polyFitArr,"Math_OneXFit_Ysum");
		
		MWNumericArray yResult = (MWNumericArray)outputArr[0];
		MWNumericArray detaResult = (MWNumericArray)outputArr[1];
		
		double[] y_arr = new double[arrx.length];
		double[] deta_arr = new double[arrx.length];
		
		for(int i=0;i<y_arr.length;i++){
			y_arr[i] = yResult.getDouble(i+1);
			deta_arr[i] = detaResult.getDouble(i+1);
		}
		
		MathOneXPerdictorAeraResult result = new MathOneXPerdictorAeraResult();
		result.setDetaValue(deta_arr);
		result.setY_result(y_arr);
		
		
//		logger.debug("调用Math_OneX_Perdictor_Aera完成。");
		for(int i=0;i<outputArr.length;i++){
			MWArray.disposeArray(outputArr[i]);
		}
		funcService.dispose();
		
		return result;
	}
	
	/**
	 * %对一元N阶方程求解
	   %F  以文本形式输入需求解的一元N阶方程 如 X^2+2X+100=900,F=[1,2,-800]
	   %X  以数组形式复数存储的解 
	 * @throws Exception
	 */
	public Double[] Math_SolveX(double[] coeArr)throws Exception{
		//构建入参
//		logger.debug("调用Math_SolveX计算：");
		Object[] outputArr = funcService.Math_SolveX(1, coeArr);
		MWNumericArray xValueArr = (MWNumericArray)outputArr[0];
		String[] values = StringUtil.splitString2Array(xValueArr.toString(), "\n", false);
		
		ArrayList<Double> xList = new ArrayList<Double>(8);
				
		int index = 0;
		for(int i=0;i<values.length;i++){
			index++;
			if("".equals(values[i].trim()) || values[i].indexOf("*") > 0){//还有空行的情况出现,或者数乘形式
				index--;//跳过
				continue;
			}
			
			if(values[i].indexOf("i") > 0){
				//数据解为复数，如(0.5398 - 0.1826i)，去除
			}else{
				
				//double val = new Double(values[i].trim());,有E表示的情况
				double val = xValueArr.getDouble(index);
				xList.add(val);
			}
			
		}
		
		Double[] solveArr = new Double[xList.size()];
		xList.toArray(solveArr);
//		logger.debug("调用Math_SolveX完成。");
		for(int i=0;i<outputArr.length;i++){
			MWArray.disposeArray(outputArr[i]);
		}
		funcService.dispose();
		
		return solveArr;
	}
	
	/**
	 * function [X_Value,XReal_Value,X_length,XReal_length]=Math_SolveXReal(F,Y_Result)
		%对一元N阶方程求解
		%F  以文本形式输入需求解的一元N阶方程 X^2+2X+100=900 求 X 如 F=[1,2,-800]
		%X  以数组形式复数存储的解 
		%XReal_Value 实数解
		%X_Value 所有解
		%Y_Result Y值
		%X_length 数组所有解长度
		%XReal_length 实数解长度
	 * @throws Exception
	 */
	public Double[] Math_SolveXReal(double[] coeArr,double y_value)throws Exception{
		//构建入参
//		logger.debug("调用Math_SolveXReal计算：");
		Object[] outputArr = funcService.Math_SolveXReal(4,coeArr,y_value);
		MWNumericArray xValueArr = (MWNumericArray)outputArr[1];
		int rootNum = new Integer(outputArr[3].toString());//返回实数解个数
		
		Double[] solveArr = new Double[rootNum];
		
		for(int i=1;i<=rootNum;i++){
			double val = xValueArr.getDouble(i);
			solveArr[i-1] = val;
		}
		
//		logger.debug("调用Math_SolveXReal完成。");
		for(int i=0;i<outputArr.length;i++){
			MWArray.disposeArray(outputArr[i]);
		}
		funcService.dispose();
		
		return solveArr;
	}
	
	/**
	 * 正态分布
	 * % y 正态分布x值 可以是数组 的y值
	 *   
	 *	% p 正态分布的概率 概率值都是单边概率  可以用P1-P2 获得双边概率
		% x 正态分布的x值 可以是数组
		% MeanValue 均值
		% StdValue 方差
	 * @throws Exception
	 */
	public void Math_Norm()throws Exception{
		//构建入参
//		System.out.println("Math_Norm输入参数：--------------------------");
		Float[] xArr = {new Float(500.0)};
		Float[] meanValueArr = {new Float(100.0)};
		Float[] stdValueArr = {new Float(200.0)};
		
		MWNumericArray x = new MWNumericArray(xArr);
		MWNumericArray MeanValue = new MWNumericArray(meanValueArr);
		MWNumericArray StdValue = new MWNumericArray(stdValueArr);
		System.out.println("x="+x+",MeanValue="+MeanValue+",StdValue=" +StdValue);
		
		Object[] outputArr = funcService.Math_Norm(2,x,MeanValue,StdValue);
		
		System.out.println("Math_Norm输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			System.out.println(outputArr[i]);
		}
		
		MWArray.disposeArray(outputArr);
	}
	
	/**
	 * 泊松分布
	 * function [y,p]=Math_Poiss(x,MeanValue)
		% y 泊松分布x值的y值 可以是数组 
		% p 泊松分布的概率
		% x 泊松分布的x值 可以是数组
		% MeanValue 均值
	 */
	public void Math_Poiss()throws Exception{
		//构建入参
		System.out.println("Math_Poiss输入参数：--------------------------");
		Float[] xArr = {new Float(750.0)};
		Float[] meanValueArr = {new Float(500.0)};
		
		MWNumericArray x = new MWNumericArray(new Float(750.0));
		MWNumericArray MeanValue = new MWNumericArray(meanValueArr);
		System.out.println("x="+x+",MeanValue="+MeanValue);
		
		Object[] outputArr = funcService.Math_Poiss(2,x,MeanValue);
		
		System.out.println("Math_Poiss输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			System.out.println(outputArr[i]);
		}
	}
	
	/**
	 * 小波分析
	 * @param sig 信号数据
	 * @param sampFreq 信号采集频率
	 * @param freqBins 分辨率
	 * @param title 图表标题
	 * @param xlabel L轴说明
	 * @param ylabel Y轴说明
	 * @param showPic 是否显示图形
	 * @return
	 * @throws Exception
	 */
	public Spwvd_FigureResult SPWVD_Figure(double[] sig, double sampFreq, double freqBins, String title, String xlabel, String ylabel,boolean showPic) throws Exception {
		/**
		 *% Wigner-Ville分布计算程序 小波分析
			[t,f,WVD,W,FreqEnger]=SPWVD_Figure(Sig,SampFreq,FreqBins,GLen,HLen,TitleStr,xLabelStr,yLabelStr,showPic,az,el)
			%  Sig      : 输入信号
			%  SampFreq : 信号的采集频率
			%  FreqBins : 频率轴划分区间数（默认值：512）
			%  alfa     : 窗函数控制参数
			%  GLen     : 窗函数g的长度（默认值：FreqBins/5）
			%  HLen     : 窗函数h的长度（默认值：FreqBins/4）
			%  WVD      :   SWWigner-Ville计算结果,返回 FreqBins行 X sig.length 列的二维矩阵
			%  t        : 时间轴
			%  w        : webfigure 波形分析图形，可以jsp中显示
			%  FreqEnger： 峰值数据
		 */
		
		// 构建入参
		double gLen = freqBins / 5;
		double hLen = freqBins / 4;

		// az\el 表示3D图表的视角
		
		Object[] outputArr = funcService.SPWVD_Figure(5, sig, sampFreq, freqBins, gLen, hLen, title, xlabel, ylabel,showPic,-37.5,30);

//		for(int i =0;i<outputArr.length;i++){
//			System.out.println("参数"+i+"为：*************");
//			System.out.println(outputArr[i]);
//		}
		
		Spwvd_FigureResult result = new Spwvd_FigureResult();

		//时间轴
		MWNumericArray tArr = (MWNumericArray) outputArr[0];
		double[][] tArr2 = (double[][])tArr.toArray();
		result.setTime(tArr2[0]);
		tArr.dispose();
		
		//频率轴
		MWNumericArray fArr = (MWNumericArray) outputArr[1];
		double[][] fArr2 = (double[][])fArr.toArray();
		result.setFreq(fArr2[0]);
		fArr.dispose();
		
		if(showPic){
			//获取波形图
			MWJavaObjectRef ref = (MWJavaObjectRef) outputArr[3];
			WebFigure figure = (WebFigure) ref.get();
			result.setFigure(figure);
			ref.dispose();
		}

		//获取能量峰值
		MWNumericArray peakOutput = (MWNumericArray) outputArr[4];
		double[][] peakMatric = (double[][])peakOutput.toArray();
		//把N*1 的二维数据，变成一维数组
		double[] peakArr = new double[peakMatric.length];
		for(int i=0;i<peakMatric.length;i++){
			peakArr[i] = peakMatric[i][0];
		}
		result.setEnergyPeak(peakArr);
		peakOutput.dispose();
		
		//figure.dispose();
		MWArray.disposeArray(peakOutput);
		MWArray.disposeArray(outputArr[1]);
		MWArray.disposeArray(outputArr[2]);
		funcService.dispose();

		return result;
	}
	
	
	/**
	 * 傅立叶变换
	 * @param sig 输入信号
	 * @param sampFreq 抽样频率
	 * @param showPic 是否画图 =0 不出图 =1 出图
	 * @param tTitleStr 时域图的抬头 
	 * @param tXlabel 时域图X轴标签
	 * @param tYlabel 时域图Y轴标签
	 * @param fTitleStr 频域图的抬头 
	 * @param fXlabel 频域图X轴标签
	 * @param fYlabel 频域图Y轴标签
	 * @return
	 * @throws Exception
	*/
	public FftCountResult FFT_Count(double[] sig, double sampFreq, boolean showPic, String tTitleStr, String tXlabel, String tYlabel,String fTitleStr, String fXlabel, String fYlabel) throws Exception {
		/**
		 * function [tx,fx,ty,fy,tFigure,fFigure]=FFT_Count(Sig,SampFreq,showPic,tTitleStr,tXlabel,tYlabel,fTitleStr,fXlabel,fYlabel)
		    %tx：采样数据的时间数组 画时域图的X值
			%fx: 采样数据的频谱数组 画频域图的X值
			%ty ：采样数据的值数值 画时域图的Y值
			%fy :采样数据处理后的频域幅值 画频域图的Y值
			%tFigure : 时域图
			%fFigure : 频域图
			% 输入参数
			%Sig :输入信号
			%SampFreq :抽样频率
			%showPic 是否画图 =0 不出图 =1 出图
			%tTitleStr 时域图的抬头 
			%tXlabel 时域图X轴标签
			%tYlabel 时域图Y轴标签
			%fTitleStr 频域图的抬头 
			%fXlabel 频域图X轴标签
			%fYlabel 频域图Y轴标签
		 */
		
		Object[] outputArr = funcService.FFT_Count(6, sig,sampFreq,showPic,tTitleStr,tXlabel,tYlabel,fTitleStr,fXlabel,fYlabel);
		

//		for(int i =0;i<outputArr.length;i++){
//			System.out.println("参数"+i+"为：*************");
//			System.out.println(outputArr[i]);
//		}
		
		FftCountResult result = new FftCountResult();
		
		//时域图X值
		MWNumericArray tArr = (MWNumericArray) outputArr[0];
		double[][] tArr2 = (double[][])tArr.toArray();
		result.setTimeX(tArr2[0]);
		tArr.dispose();
		
		//时域图Y值
		MWNumericArray fArr = (MWNumericArray) outputArr[1];
		double[][] fArr2 = (double[][])fArr.toArray();
		result.setFreqX(fArr2[0]);
		fArr.dispose();
		
		if(showPic){
			//获取时域图
			MWJavaObjectRef tref = (MWJavaObjectRef) outputArr[4];
			WebFigure tfigure = (WebFigure) tref.get();
			result.settFigure(tfigure);
			tref.dispose();
			
			//获取频域图
			MWJavaObjectRef fref = (MWJavaObjectRef) outputArr[5];
			WebFigure ffigure = (WebFigure) fref.get();
			result.setfFigure(ffigure);
			fref.dispose();
		}
		
		
		return result;
	}
	
	/**
	 * 傅立叶变换,不出图
	 * @param sig 输入信号
	 * @param sampFreq 抽样频率
	 * @return
	 * @throws Exception
	 */
	public FftCountResult FFT_Count_NoFigure(double[] sig, double sampFreq) throws Exception{
		return FFT_Count(sig, sampFreq, false, null, null, null, null, null, null);
	}
	
	public BintProgResult bintProg(double[] f, double[][] A,double[] b,double[][] Aeq,double[] beq) throws Exception{
		/**
		 * function[x,fval,exitflag,output]=Math_Bintprog(f,A,b,Aeq,beq)
			% 0-1规划算法
			% 如果约束条件为>= 时 请对 方程左右两边的参数同时乘以-1,<=条件 不用变换
			%f 线性规划求解方程系数
			%A 线性约束矩阵 A·x≤ b.
			%b 线性约束条件 A·x≤ b.
			%Aeq 线性相等条件约束矩阵 Aeq·x = beq.
			%beq 线性相等约束条件 Aeq·x = beq..
			% exitflag 处理结果返回参数.
			%   1 找到求解.
			%   0 超过迭代次数，请设定OPTIONS参数
			%   -2 问题无解
			%   -4  搜索节点超过限制数量，请设定OPTIONS参数.
			%   -5 搜索超过限制时间，请设定OPTIONS参数.
			%   -6 在一个节点进行迭代求解线性规划松弛问题的规划求解超过限制，请设定OPTIONS参数.	
			% output 包含的优化信息结构。
			% 包括 iterations，该结构域的迭代次数迭代次数。
			%      nodes 搜索的节点数目
			%      time  该算法的执行时间
			%      algorithm 使用的优化算法
			%      branchStrategy 选择分支变量选择策略
			%      nodeSearchStrategy 选择下一个节点搜索树看到的期权策略
			%      message 退出消息
			if (nargin<1),
			    error('NO Maxtri input');
			end;
			[x,fval,exitflag,output]=bintprog(f,A,b,Aeq,beq);
			end
		 */
		
		Object[] outputArr;
		if(Aeq == null){
			outputArr = funcService.Math_Bintprog(4, f,A,b);
		}else{
			outputArr = funcService.Math_Bintprog(4, f,A,b,Aeq,beq);
		}
		
		BintProgResult result = new BintProgResult();
		
		//返回结果说明
		MWNumericArray mwt = (MWNumericArray)outputArr[2];
		int exitFlag = mwt.getInt();
		result.setExitFlag(exitFlag);
		
		MWArray outputA = (MWArray)outputArr[3];
		result.setOutput(outputA.toString());
		
		if(exitFlag == 1){//解成功
			//X解矩阵
			MWNumericArray xArr = (MWNumericArray) outputArr[0];
			double[] x = xArr.getDoubleData(); 
			result.setX(x);
			
			//规划值fval
			MWNumericArray mwFval = (MWNumericArray)outputArr[1];
			double fval = mwFval.getDouble();
			result.setFval(fval);
		}
		
//		for(int i =0;i<outputArr.length;i++){
//			MWArray arr = (MWArray)outputArr[i];
//			System.out.println("参数"+i+"为：" + arr.classID());
//		}
		
		MWArray.disposeArray(outputArr[0]);
		MWArray.disposeArray(outputArr[1]);
		MWArray.disposeArray(outputArr[2]);
		MWArray.disposeArray(outputArr[3]);
		funcService.dispose();
		
		return result;
	}
	
}
