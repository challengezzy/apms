package com.apms.matlab;

import java.util.ArrayList;

import com.apms.bs.datacompute.vo.A13ComputeVo;
import com.apms.bs.util.StringUtil;
import com.apms.matlab.function.ApmsMatlabFuncService;
import com.mathworks.toolbox.javabuilder.MWCharArray;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

/**
 *matlab测试类
 *@date Dec 2, 2012
 **/
public class MatlabTest {
	
	private static ApmsMatlabFuncService funcService;
	
	public static void main(String[] args) throws Exception{
		funcService = new ApmsMatlabFuncService();
		//AbuotUS();
		
//		//正态分布
//		Math_Norm();
//		
//		//一元回归
		Math_OneXFit();
//		
//		//泊松分布
//		Math_Poiss();
//		
//		//简单统计计算
		//Math_Count();
//		
		//小波分析
		//SPWVD();
//		
//		//傅利叶
//		FFT();
		
//		Math_t_test();
		
//		Math_SolveX();
		
//		Math_SolveXReal();
		
//		performanceTest();
		
		//回归范围
		//Math_OneX_Perdictor_Aera();
		
	}
	
	public static void performanceTest() throws Exception{
		long begin = System.currentTimeMillis();
		int totalTimes = 200000;
		int mb = 1024*1024;
		long usedMemory =Runtime.getRuntime().totalMemory()/mb;
		for(int i=0;i<=totalTimes; i++){
			//正态分布
			Math_Norm();
//			A13ComputeVo vo = new A13ComputeVo();
//			vo.setAcnum("22");
			//Thread.sleep(50);
			
			if(i%10000 == 0){
				long now = System.currentTimeMillis();
				System.out.println("第"+i+"次运行，耗时:" + (now-begin)/1000 + " s,当前使用内存"
						+ " total: "+ Runtime.getRuntime().totalMemory()/mb
						+ " free: "+ Runtime.getRuntime().freeMemory()/mb
						+ " max: "+ Runtime.getRuntime().maxMemory()/mb
						);
				now += 1;
				
			}
			if(i%20000 ==0){
				ApmsMatlabFuncService.disposeAllInstances();
				System.out.println("funcService.dispose()");
				Thread.sleep(1000);
			}
		}
		
		for(int i=0;i<100;i++){
			long now = System.currentTimeMillis();
			System.out.println("第"+i+"次运行，耗时:" + (now-begin)/1000 + " s,当前使用内存"
					+ " total: "+ Runtime.getRuntime().totalMemory()/mb
					+ " free: "+ Runtime.getRuntime().freeMemory()/mb
					+ " max: "+ Runtime.getRuntime().maxMemory()/mb
					);
			Thread.sleep(2000);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("测试完成，执行"+totalTimes+"次，耗时:" + (end-begin)/1000 + " s");
	}
	
	/**
	 * 简单测试
	 * @param args
	 * @throws Exception
	 */
	public static void AbuotUS() throws Exception{
		
		//调用 AbuotUS
		System.out.println("AbuotUS输出：--------------------------");
		funcService.AbuotUS();
	}
	
	/**
	 * 简单数据统计计算
	 * ValueSum 数组的和     
	 * ValueAvg 数组的均值   
	 * ValueStd 数组的方差   
	 * OneD_ArryValue 1维数组
	 * @throws Exception
	 */
	public static void Math_Count()throws Exception{
		//调用Math_Count
		//构建入参
		Float[] inputArr = new Float[5];
		System.out.println("Math_Count输入参数：--------------------------");
		for(int i=0;i<inputArr.length;i++){
			inputArr[i] = (float)i +5;
			System.out.println(inputArr[i]);
		}
		//这里直接传数据不行，非得转换成Object？？？？
		//Object inputObj = inputArr;
		MWNumericArray inputObj = new MWNumericArray(inputArr);
		
		Object[] outputArr = funcService.Math_Count(5,inputObj);
		System.out.println("Math_Count输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			System.out.println(outputArr[i]);
		}
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
	public static void Math_Norm()throws Exception{
		//构建入参
//		System.out.println("Math_Norm输入参数：--------------------------");
		Float[] xArr = {new Float(500.0)};
		Float[] meanValueArr = {new Float(100.0)};
		Float[] stdValueArr = {new Float(200.0)};
		
		MWNumericArray x = new MWNumericArray(xArr);
		MWNumericArray MeanValue = new MWNumericArray(meanValueArr);
		MWNumericArray StdValue = new MWNumericArray(stdValueArr);
//		System.out.println("x="+x+",MeanValue="+MeanValue+",StdValue=" +StdValue);
		
		ApmsMatlabFuncService service = new ApmsMatlabFuncService();
		Object[] outputArr = service.Math_Norm(2,x,MeanValue,StdValue);
		service.dispose();
//		Object[] outputArr = funcService.Math_Norm(2,x,MeanValue,StdValue);
		
//		System.out.println("Math_Norm输出参数：--------------------------");
//		for(int i =0;i<outputArr.length;i++){
//			System.out.println("参数"+i+"为：*************");
//			System.out.println(outputArr[i]);
//		}
	}
	
	/**
	 * function [PolyFit_arry,PolyFit_S]=Math_OneXFit(x,y,m)
		% x 自变量数组
		% y 因变量数组
		% n 多项式次数
		% PolyFit_arry 多项式系数数组
		% PolyFit_S 多项式误差估计结构数组
	 * @throws Exception
	 */
	public static void Math_OneXFit()throws Exception{
		
		int[] xArr = {1,2,3};
		int[] yArr = {4,5,6};
		float[] fxArr = new float[10];
		float[] fyArr = new float[10];
		
		System.out.println("Math_OneXFit输入参数：--------------------------");
		for(int i=0;i<xArr.length;i++){
//			fxArr[i] = (float)11-i;
//			fyArr[i] = fxArr[i] * fxArr[i];
			fxArr[i] = xArr[i];
			fyArr[i] = yArr[i];
			System.out.println("x=" + xArr[i] + ", y=" + yArr[i]);
		}
		//Object inputObj = inputArr;
		MWNumericArray xObj = new MWNumericArray(fxArr);
		MWNumericArray yObj = new MWNumericArray(fyArr);
		
		Object[] outputArr = funcService.Math_OneXFit(2,xObj,yObj,2);
		
		MWNumericArray r1 = (MWNumericArray)outputArr[0];
		System.out.println(r1.getDouble(1));
		System.out.println(r1.getDouble(2));
		System.out.println(r1.getDouble(3));
		
		System.out.println("Math_OneXFit输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			System.out.println(outputArr[i]);
		}
		
		MWNumericArray r1Array = (MWNumericArray)outputArr[0];
		int t = r1Array.numberOfElements();
		Double k = r1Array.getDouble(1);
		double b = r1Array.getDouble(2);
		
		System.out.println("k="+k+" ,b="+b);
		
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
	public static void Math_SolveXReal()throws Exception{
		//构建入参
		//System.out.println("Math_SolveXReal函数");
		double[] coeArr = {1.6807554372937735E-8, -0.055209396243570005, 69450.53941717843, -2.2063076654835014E10};
		double y_value = 90.0;
		Object[] outputArr = funcService.Math_SolveXReal(4,coeArr,y_value);
		
		System.out.println(outputArr[0]);
		System.out.println(outputArr[1]);
		System.out.println(outputArr[2]);
		System.out.println(outputArr[3]);
		
		MWNumericArray xValueArr = (MWNumericArray)outputArr[1];
		int rootNum = new Integer(outputArr[3].toString());//返回实数解个数
		
		Double[] solveArr = new Double[rootNum];
		
		//System.out.println("实数解为：");
		for(int i=1;i<=rootNum;i++){
			double val = xValueArr.getDouble(i);
			solveArr[i-1] = val;
			//System.out.println(solveArr[i-1]);
		}
		
		//System.out.println("调用Math_SolveXReal完成。");
	}
	
	/**
	 * %对一元N阶方程求解
	   %F  以文本形式输入需求解的一元N阶方程 如 X^2+2X+100=900,F=[1,2,-800]
	   %X  以数组形式复数存储的解 
	 * @throws Exception
	 */
	public static void Math_SolveX()throws Exception{
		//构建入参
		System.out.println("Math_SolveX函数");
		double[] coeArr = {1.6807554372937735E-8, -0.055209396243570005, 60450.53941717843, -2.2063076654835014E10};
		
		Object[] outputArr = funcService.Math_SolveX(1, coeArr);
		
		System.out.println("Math_SolveX输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			MWNumericArray xValue = (MWNumericArray)outputArr[i];
			System.out.println(xValue);
			
			String[] s = StringUtil.splitString2Array(xValue.toString(), "\n", false);
			System.out.println(s[0].trim());
			System.out.println(s[1].trim());
			System.out.println(s[2].trim());
			
			System.out.println("获取的值为:");
			System.out.println(xValue.getDouble(1));
			System.out.println(xValue.getDouble(2));
			System.out.println(xValue.getDouble(3));
		}
		
		Object[] outputArr2 = funcService.Math_SolveX(1, coeArr);
		MWNumericArray xValueArr = (MWNumericArray)outputArr2[0];
		String[] values = StringUtil.splitString2Array(xValueArr.toString(), "\n", false);
		
		ArrayList<Double> xList = new ArrayList<Double>(8);
		
		for(int i=0;i<values.length;i++){
			if(values[i].indexOf("i") > 0){
				//数据解为复数，如(0.5398 - 0.1826i)，去除
			}else{
				double val = new Double(values[i].trim());
				xList.add(val);
			}
			
		}
		
		Double[] solveArr = new Double[xList.size()];
		xList.toArray(solveArr);
		System.out.println("调用Math_SolveX完成。");
	}
	
	/**
	 * 产生数据的预测范围
	 * @throws Exception
	 */
	public static void Math_OneX_Perdictor_Aera()throws Exception{
		//构建入参
		System.out.println("Math_OneX_Perdictor_Aera输入参数：--------------------------");
		float[] xArr = {1,2,3,4,5,6,7,8,9,10};
		float[] yArr = {1,4,5,8,11,12,14,17,18,19};
		
		Float[] polyFit_arr = {new Float( 2.0545),new Float(-0.4000)};
				
		Object[] outputArr = funcService.Math_OneX_Perdictor_Aera(4,xArr,yArr,polyFit_arr,"Math_OneXFit_Ysum");
		
		System.out.println("Math_OneX_Perdictor_Aera输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			System.out.println(outputArr[i]);
		}
		
		MWNumericArray r3 = (MWNumericArray)outputArr[2];
		
		
	}
	
	/**
	 * 0\1规划,未测试
	 * @throws Exception
	 */
	public static void Math_Bintprog()throws Exception{
		//构建入参
		System.out.println("Math_Poiss输入参数：--------------------------");
		Float[] xArr = {new Float(750.0)};
		Float[] meanValueArr = {new Float(500.0)};
		
		MWNumericArray x = new MWNumericArray(new Float(750.0));
		MWNumericArray MeanValue = new MWNumericArray(meanValueArr);
		System.out.println("x="+x+",MeanValue="+MeanValue);
		
		Object[] outputArr = funcService.Math_Bintprog(2,x,MeanValue);
		
		System.out.println("Math_Poiss输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			System.out.println(outputArr[i]);
		}
	}
	
	/**
	 * 泊松分布
	 *  [y,p]=Math_Poiss(x,MeanValue)
	 * @throws Exception
	 */
	public static void Math_Poiss()throws Exception{
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
	 * SPWVD
	 *  [t,f,WVD]=SPWVD(s1,fs,FreqBins,GLen,HLen);
	 * @throws Exception
	 */
	public static void SPWVD()throws Exception{
		//构建入参
		System.out.println("SPWVD输入参数：--------------------------");
		double[] s1Arr = { 1.9504,
			    0.5995,
			    0.3680,
			    1.0000,
			   -0.5320,
			   -0.1659,
			    2.4602,
			    2.0000,
			    0.6125,
			    1.2483,
			    0.2334,
			   -1.0000,
			    1.1333,
			    2.0137,
			    0.1027,
			   -0.0000,
			   -0.1027,
			   -2.0137,
			    1.9504};
		
		Float[] meanValueArr = {new Float(500.0)};
		
		Float FreqBins= (float)80;
		Float fs= (float)32;
		Float GLen=FreqBins/5;
		Float HLen=FreqBins/4;
		
		MWNumericArray FreqBinsP = new MWNumericArray(FreqBins);
		MWNumericArray GLenP = new MWNumericArray(GLen);
		MWNumericArray HLenP = new MWNumericArray(HLen);
		MWNumericArray fsP = new MWNumericArray(fs);
		MWNumericArray s1ArrP = new MWNumericArray(s1Arr);
		
		Object[] outputArr = funcService.SPWVD(3, s1ArrP,fsP,FreqBinsP,GLenP,HLenP);
		
		System.out.println("SPWVD输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			System.out.println(outputArr[i]);
		}
	}
	
	/**
	 * FFT
	 *  [t,f,Freq_YValue]=FFT(Sig,SampFreq)
	 * @throws Exception
	 */
	public static void FFT()throws Exception{
		//构建入参
		System.out.println("FFT输入参数：--------------------------");
		double[] s1Arr = { 1.9504,
			    0.5995,
			    0.3680,
			    1.0000,
			   -0.5320,
			   -0.1659,
			    2.4602,
			    2.0000,
			    0.6125,
			    1.2483,
			    0.2334,
			   -1.0000,
			    1.1333,
			    2.0137,
			    0.1027,
			   -0.0000,
			   -0.1027,
			   -2.0137,
			    1.9504};
		
		Float FreqBins= (float)80;
		Float fs= (float)32;
		Float GLen=FreqBins/5;
		Float HLen=FreqBins/4;
		
		MWNumericArray fsP = new MWNumericArray(fs);
		MWNumericArray s1ArrP = new MWNumericArray(s1Arr);
		
		Object[] outputArr = funcService.FFT(3, s1ArrP,fsP);
		
		System.out.println("FFT输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			System.out.println(outputArr[i]);
		}
	}
	
	
	public static void Math_t_test()throws Exception{
//		x=[];
//		y=[2,2,2,5,5,5,8,8,8,9];
//		alpha=0.05;
//		tail='both';
//		[diff,significance,ci]=Math_t_test(x,y,alpha,tail)
//		Float[] xArr = {new Float(1),new Float(2),new Float(3),new Float(4),new Float(5),new Float(6),new Float(7)};
//		Float[] yArr = {new Float(2),new Float(3),new Float(3),new Float(2),new Float(7),new Float(7),new Float(7)};
		
		float[] xArr = {1,1,1,1,1};
		float[] yArr = {1,1,1,1,1};
		
		MWNumericArray x = new MWNumericArray(xArr);
		MWNumericArray y = new MWNumericArray(yArr);
		//MWNumericArray alpha = new MWNumericArray(0.05);
		//MWCharArray tail = new MWCharArray("both");
		
		
		Object[] outputArr = funcService.Math_t_test(3,x,y,0.95,"both");
		
		System.out.println("Math_t_test输出参数：--------------------------");
		for(int i =0;i<outputArr.length;i++){
			System.out.println("参数"+i+"为：*************");
			System.out.println(outputArr[i]);
			
			System.out.println(outputArr[i].toString());
		}
	}

}
