package com.apms.bs.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.apms.bs.oxygen.vo.A_DFD_RankPoint;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.vo.MathOneFitResult;
import com.apms.matlab.vo.MathOneXPerdictorAeraResult;

public class MathUtil {
	
	
	/**
	 * 判断两个值的差值是否明显，超过给定的百分比
	 * @param val1
	 * @param val2
	 * @param percent
	 * @return
	 */
	public static boolean isValueBigDiff(double val1,double val2,int percent){
		double small,big;
		
		if(val1 > val2){
			small = val2;
			big = val1;
		}else if(val2 > val1){
			small = val1;
			big = val2;
		}else{
			//两个值相等，直接返回false;
			
			return false;
		}
		
		double denominator = Math.abs(small);//分母设置为最小值的那个
		if(small == 0){
			denominator = Math.abs(big);
		}
		
		double diffPer = 100*(big-small)/denominator;//差值百分比
		
		if(diffPer > percent)
			return true;
		else
			return false;
		
	}
	
	/**
	 * 取3个值 的中值
	 * @param val1
	 * @param val2
	 * @param val3
	 * @return
	 */
	public static double getMiddleValue(double val1,double val2,double val3){
		double max,mid,min;//大、中、小三值
		
		//先用前两个值，排出大小关系
		if(val1 > val2){
			max = val1;
			min = val2;
		}else{
			max = val2;
			min = val1;
		}
		
		//判断第3个数值，得到中间值
		if(val3 > max){
			mid = max;
		}else if(val3 < min){
			mid = min;
		}else{
			mid = val3;
		}
			
		return mid;
	}
	
	/**
	 * 获取3个时间值中值
	 * @param val1
	 * @param val2
	 * @param val3
	 * @return
	 */
	public static Date getMiddleDate(Date val1,Date val2,Date val3){
		Date max,mid,min;//大、中、小三值
		
		//先用前两个值，排出大小关系
		if(val1.getTime() > val2.getTime()){
			max = val1;
			min = val2;
		}else{
			max = val2;
			min = val1;
		}
		
		//判断第3个数值，得到中间值
		if(val3.getTime() > max.getTime()){
			mid = max;
		}else if(val3.getTime() < min.getTime()){
			mid = min;
		}else{
			mid = val3;
		}
			
		return mid;
	}
	
	/**
	 * null值作为0处理
	 * @param value
	 * @return
	 */
	public static double nullToZero(Double value){
		if(value == null){
			return 0;
		}else{
			return value.doubleValue();
		}
		
	}
	
	/**
	 * 把字符串对象转换为doulbe值，如果对象值为空，转换为0 
	 * @param val
	 * @return
	 */
	public static double toDoubleValue(Object val){
		if(val == null)
			return 0;
		else{
			double res = new Double(val.toString());
			return res;
		}
	}
	
	/**
	 * 把Double数转换成小数点后N位
	 * @param value 原数
	 * @param position 小数点后个数
	 * @return
	 * @throws Exception
	 */
	public static double round(Double value,int position) throws Exception{
		BigDecimal bg = new BigDecimal(value);
		
		double result = bg.setScale(position, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return result;

	}

	/**
	 * 计算两个数值之间的差值
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static double subtract(double num1, double num2){
		double diff = num1 - num2 ;
		return diff;
	}
	
	/**
	 * 离差也叫差量，是单项数值与平均值之间的差
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static double deviation(double num1, double num2){
		double deviation = num1 - (num1 + num2)/2;
		return deviation;
	}
	
	/**
	 * N点回归斜率计算
	 * @param xList
	 * @param yList
	 * @return
	 */
	public static List<A_DFD_RankPoint> computeRankList(List<Double> xList,List<Double> yList) throws Exception{
		
		List<A_DFD_RankPoint> rank = new ArrayList<A_DFD_RankPoint>();
		int pointCount = xList.size();//点个数
		
		Double[] xarr = new Double[pointCount];
		Double[] yarr = new Double[pointCount];
		xList.toArray(xarr); 
		yList.toArray(yarr);
		
		MatlabFunctionService funService = new MatlabFunctionService();
		//一元回归
		MathOneFitResult oneFit = funService.Math_One_OneFit(xarr,yarr);
		
		//根据回归公式y=kx+b, 计算出第一个点 和第N个点的回归值
//		double y_1 =  oneFit.getK()*xarr[0] + oneFit.getB();
//		double y_n =  oneFit.getK()*xarr[pointCount-1] + oneFit.getB();
		
		//计算预测范围 add by jerry 2013/3/24
		Double[] polyFitArr = new Double[2];
		polyFitArr[0] = oneFit.getK();
		polyFitArr[1] = oneFit.getB();
		//范围预测
		MathOneXPerdictorAeraResult areaResult = funService.Math_OneX_Perdictor_Aera(xarr, yarr, polyFitArr);
		
		for(int i=0;i<pointCount;i++){
			A_DFD_RankPoint point = new A_DFD_RankPoint();
			double y_i =  oneFit.getK()*xarr[i] + oneFit.getB();
			
			point.setK(oneFit.getK());
			point.setX(xarr[i]);
			point.setY(y_i);
			point.setUpValue( (areaResult.getY_result()[i]+areaResult.getDetaValue()[i]*2));
			point.setDownValue( (areaResult.getY_result()[i]-areaResult.getDetaValue()[i]*2));
			
			rank.add(point);
		}
		
//		A_DFD_RankPoint point1 = new A_DFD_RankPoint();
//		point1.setK(oneFit.getK());
//		point1.setX(xarr[0]);
//		point1.setY(y_1);
//		point1.setUpValue( (areaResult.getY_result()[0]+areaResult.getDetaValue()[0]));
//		point1.setDownValue( (areaResult.getY_result()[0]-areaResult.getDetaValue()[0]));
//		
//		A_DFD_RankPoint pointN = new A_DFD_RankPoint();
//		pointN.setK(oneFit.getK());
//		pointN.setX(xarr[xarr.length-1]);
//		pointN.setY(y_n);
//		pointN.setUpValue( (areaResult.getY_result()[pointCount-1]+areaResult.getDetaValue()[pointCount-1]));
//		pointN.setDownValue( (areaResult.getY_result()[pointCount-1]-areaResult.getDetaValue()[pointCount-1]));
//		
//		rank.add(point1);
//		rank.add(pointN);
		return rank;
	}
	
}
