package com.apms.bs.datacompute;

import smartx.framework.common.vo.HashVO;

import com.apms.bs.datacompute.engine.EngineComputeParamService;
import com.apms.bs.datacompute.vo.a04.A04EgtIaeVo;

public class EngineIaeEgtMarginService {
	
	private String defaultVal = "1";//匹配配置表里的默认值
	
	/** 左发计算 */
	public A04EgtIaeVo egt1MarginCompute(HashVO curVo) throws Exception{
		String engModel = curVo.getStringValue("ENGMODEL_1");
		double TAT = curVo.getDoubleValue("TAT");
		double EGT = curVo.getDoubleValue("EGT_1");
		double EPRI = curVo.getDoubleValue("EPR_1");
		double T3 = curVo.getDoubleValue("T3_1");
		
		boolean isT25=false;//是否有t25参数
		double T25 = 0;
		if( curVo.getDoubleValue("T25_1") != null){
			isT25 = true;
			T25 = curVo.getDoubleValue("T25_1");
		}
		
		A04EgtIaeVo calVo = new A04EgtIaeVo();
		
		double MN = curVo.getDoubleValue("MN");
		String bleedStatus = curVo.getStringValue("BLEED_STATUS");
		double WAI = 0;
		double CAI = 0;
		
		String waiSwitch = bleedStatus.substring(2, 3);
		if("1".equals( waiSwitch)){ 
			WAI = 1;
		}
		String caiSwitch = bleedStatus.substring(3, 4);
		if("1".equals( caiSwitch)){ 
			CAI = 1;
		}
		calVo.setMn(MN);
		calVo.setCai(CAI);
		calVo.setWai(WAI);
		
		Double ecsValue1 = new Double(bleedStatus.substring(0, 2));//从BLEED_STATUS中取，pack flow值不为0表示ecs开启,按比例算
		double ECS = ecsValue1/100 ; //直接转换成比例数据
		calVo.setEcs(ECS);
		
		//THETA = power((TAT+273.15)/288.15,x)
		//温度修正系数
		double x = EngineComputeParamService.getInstance().getParamValue(engModel, "X", defaultVal);
		if(x ==0 ){
			x = 0.950; 
		}
		double theta = Math.pow((TAT+273.15)/288.15, x);
		
		calVo.setTheta(theta);
		
		iaeEgtMarginCompute(calVo, EGT, bleedStatus, engModel,MN,WAI,CAI,ECS,EPRI,T3,theta,isT25,T25);
		//logger.debug("iae 型号发动机egt margin计算ok");
		
		double n1 = curVo.getDoubleValue("N1_1");
		double n2 = curVo.getDoubleValue("N2_1");
		double ff = curVo.getDoubleValue("FF_1");
		calVo.setDelta_n1(getDeltaN1(engModel,theta, n1));
		calVo.setDelta_n2(getDeltaN2(engModel,theta, n2));
		calVo.setDelta_ff(getDeltaFF(engModel,theta, ff));
		
		return calVo;
	}
	
	/** 右发计算 */
	public A04EgtIaeVo egt2MarginCompute(HashVO curVo) throws Exception{
		String engModel = curVo.getStringValue("ENGMODEL_2");
		double TAT = curVo.getDoubleValue("TAT");
		double EGT = curVo.getDoubleValue("EGT_2");
		double EPRI = curVo.getDoubleValue("EPR_2");
		double T3 = curVo.getDoubleValue("T3_2");
		
		boolean isT25 = false;//是否有t25参数
		double T25 = 0;
		if( curVo.getDoubleValue("T25_2") != null){
			isT25 = true;
			T25 = curVo.getDoubleValue("T25_2");
		}
		
		A04EgtIaeVo calVo = new A04EgtIaeVo();
		
		double MN = curVo.getDoubleValue("MN");
		String bleedStatus = curVo.getStringValue("BLEED_STATUS");
		double WAI = 0;
		double CAI = 0;
		String waiSwitch = bleedStatus.substring(2, 3);
		if("1".equals( waiSwitch)){ 
			WAI = 1;
		}
		String caiSwitch = bleedStatus.substring(3, 4);
		if("1".equals( caiSwitch)){ 
			CAI = 1;
		}
		calVo.setCai(CAI);
		calVo.setWai(WAI);
		
		Double ecsValue2 = new Double(bleedStatus.substring(11, 13));//右发取值位置
		double ECS = ecsValue2/100 ;
		calVo.setEcs(ECS);
		
		//THETA = (TAT+273.15)/288.15
		double theta = (TAT+273.15)/288.15;
		
		iaeEgtMarginCompute(calVo, EGT, bleedStatus, engModel,MN,WAI,CAI,ECS,EPRI,T3,theta,isT25,T25);
		
		double n1 = curVo.getDoubleValue("N1_2");
		double n2 = curVo.getDoubleValue("N2_2");
		double ff = curVo.getDoubleValue("FF_2");
		calVo.setDelta_n1(getDeltaN1(engModel,theta, n1));
		calVo.setDelta_n2(getDeltaN2(engModel,theta, n2));
		calVo.setDelta_ff(getDeltaFF(engModel,theta, ff));
		
		return calVo;
	}
	
	/**
	 * 计算EGT_MARGIN修正值
	 * @param curVo
	 * @throws Exception
	 */
	private void iaeEgtMarginCompute(A04EgtIaeVo calVo,double EGT,String bleedStatus,String engModel
			,double MN,double WAI,double CAI,double ECS,double EPRI,double T3,double theta
			,boolean isT25,double T25) throws Exception{
		
		double egtRedLine = EngineComputeParamService.getInstance().getParamValue(engModel, "REDLINE", defaultVal);
		if(egtRedLine == 0){
			egtRedLine = 645; // 万一没有匹配，默认值设为645
		}
		//step 1
		double egtActual = getEgtActual(EGT, engModel);
		calVo.setEgt_act(egtActual);
		
		
		//step 2
		double egtCo = getEgtCo(engModel, egtActual, theta);
		calVo.setEgt_co(egtCo);
		
		double t3Co = getEgtCo(engModel, T3, theta);
		calVo.setT3_co(t3Co);
		
		// step 3
		double engBo = getEgtBo(engModel, MN, EPRI, CAI, WAI, ECS);
		calVo.setEng_bo(engBo);
		
		// step 4
		double engBto = getEgtBto(engModel);
		calVo.setEng_bto(engBto);
		
		// step 5, to不记录到数据库
		double egtTo = engBto + (egtCo - engBo);
		double t3To = engBto + (t3Co - engBo);
		
		// step 6
		double egtCal = getEgtCal(engModel, egtTo);
		calVo.setEgt_cal(egtCal);
		
		double t3Cal = getEgtCal(engModel, t3To);
		calVo.setT3_cal(t3Cal);
		
		// step 7
		double egtMargin = egtRedLine - egtCal;
		calVo.setEgt_margin(egtMargin);
		
		//计算delta_egt, delta_t3
		double baseEgt = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_EGT", defaultVal);
		double deltaEgt = egtCal - baseEgt;
		calVo.setDelta_egt(deltaEgt);
		
		double baseT3 = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_T3", defaultVal);
		double deltaT3 = t3Cal - baseT3;
		calVo.setDelta_t3(deltaT3);
		
		if(isT25){
			//step 2
			double t25Co = getEgtCo(engModel, T25, theta);
			calVo.setT25_co(t25Co);
			
			// step 5,6, to不记录到数据库
			double t25To = engBto + (t25Co - engBo);
			double t25Cal = getEgtCal(engModel, t25To);
			calVo.setT25_cal(t25Cal);
			
			double baseT25 = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_T25", defaultVal);
			double deltaT25 = t25Cal - baseT25;
			calVo.setDelta_t25(deltaT25);
		}

	}
	
	/** step 6 */
	private double getEgtCal(String engModel,double egtTo) throws Exception{
		double x = EngineComputeParamService.getInstance().getParamValue(engModel, "X", defaultVal);
		if(x ==0 ){
			x = 0.950; 
		}
		
		double tatTo = EngineComputeParamService.getInstance().getParamValue(engModel, "TAT", defaultVal);
		double egtCal = egtTo *Math.pow( (tatTo+273.15)/288.15 ,x ) - 273.15;
		
		return egtCal;
	}
	
	/** step 4 */
	private double getEgtBto(String engModel) throws Exception{
		//4,使用配置中的参数值进行计算
		//不同的发动机型号取不同的C值
		double C1,C2,C3,C4,C5,C6,C7,C8;
		C1 = EngineComputeParamService.getInstance().getParamValue(engModel, "C1", defaultVal);
		C2 = EngineComputeParamService.getInstance().getParamValue(engModel, "C2", defaultVal);
		C3 = EngineComputeParamService.getInstance().getParamValue(engModel, "C3", defaultVal);
		C4 = EngineComputeParamService.getInstance().getParamValue(engModel, "C4", defaultVal);
		C5 = EngineComputeParamService.getInstance().getParamValue(engModel, "C5", defaultVal);
		C6 = EngineComputeParamService.getInstance().getParamValue(engModel, "C6", defaultVal);
		C7 = EngineComputeParamService.getInstance().getParamValue(engModel, "C7", defaultVal);
		C8 = EngineComputeParamService.getInstance().getParamValue(engModel, "C8", defaultVal);
		
		double epriTo = EngineComputeParamService.getInstance().getParamValue(engModel, "EPRI", defaultVal);
		double mnTo = EngineComputeParamService.getInstance().getParamValue(engModel, "MN", defaultVal);
		
		double egtBto = C1*mnTo*mnTo + C2*mnTo + C3*Math.pow(epriTo, 4) + C4*Math.pow(epriTo, 3) 
						+ C5*Math.pow(epriTo, 2) + C6*epriTo + C7*mnTo*epriTo +C8;
		
		return egtBto;
	}
	
	/** step 3 */
	private double getEgtBo(String engModel,double MN,double EPRI,double CAI,double WAI,double ECS) throws Exception{
		//3
		//不同的发动机型号取不同的C值
		double C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11;
		C1 = EngineComputeParamService.getInstance().getParamValue(engModel, "C1", defaultVal);
		C2 = EngineComputeParamService.getInstance().getParamValue(engModel, "C2", defaultVal);
		C3 = EngineComputeParamService.getInstance().getParamValue(engModel, "C3", defaultVal);
		C4 = EngineComputeParamService.getInstance().getParamValue(engModel, "C4", defaultVal);
		C5 = EngineComputeParamService.getInstance().getParamValue(engModel, "C5", defaultVal);
		C6 = EngineComputeParamService.getInstance().getParamValue(engModel, "C6", defaultVal);
		C7 = EngineComputeParamService.getInstance().getParamValue(engModel, "C7", defaultVal);
		C8 = EngineComputeParamService.getInstance().getParamValue(engModel, "C8", defaultVal);
		C9 = EngineComputeParamService.getInstance().getParamValue(engModel, "C9", defaultVal);
		C10 = EngineComputeParamService.getInstance().getParamValue(engModel, "C10", defaultVal);
		C11 = EngineComputeParamService.getInstance().getParamValue(engModel, "C11", defaultVal);
		
		double egtBo = C1*MN*MN + C2*MN + C3*Math.pow(EPRI, 4) + C4*Math.pow(EPRI, 3) + C5*Math.pow(EPRI, 2)
				+ C6*EPRI + C7*MN*EPRI +C8 + C9*CAI + C10*WAI +C11*ECS;
		
		return egtBo;
	}
	
	/**
	 * 计算中的step 2
	 */
	private double getEgtCo(String engModel,double egtActual,double theta) throws Exception{
		//2 
		double egtCo = (egtActual + 273.15) /theta;
		return egtCo;
	}
	
	private double getEgtActual(double egtObserved, String engModel) throws Exception{
		
		double egtActual = egtObserved;
		//有五种情况进行计算
		double mode = EngineComputeParamService.getInstance().getParamValue(engModel, "MODELTYPE", "1");
		if(mode == 22){
			//for V2522-A5和V2522-A5 SelectOne Engines
			/*if egtObserved <610 then egtActual = egtObserved
			 *if 610 <= egtObserved <= 635 then egtActual = egtObserved+(-0.4*egtObserved) +244;
			 *if egtObserved >= 635 egtActual = egtObserved-10
			 */
			if (egtObserved <610){
				egtActual = egtObserved;
			}else if (610 <= egtObserved && egtObserved <= 635){
				egtActual = egtObserved-0.4*egtObserved +244;
			}else if (egtObserved >= 635){
				egtActual = egtObserved-10;
			}
			
		}else if(mode == 24 ){
			//for V2527M-A5和V2527M-A5 SelectOne Engines, v2530-a5
			//egtActual = egtObserved
			
			egtActual = egtObserved;
			
		}else if(mode == 27 ){
			//for V2527-A5和V2527-A5 SelectOne,V2527E-A5和V2527E-A5,V2527M-A5和V2527M-A5 SelectOne Engines
			/*if egtObserved <610 then egtActual = egtObserved
			 *if 610 <= egtObserved <= 635 then egtActual = egtObserved+ 0.4*egtObserved -244;
			 *if egtObserved >= 635 egtActual = egtObserved+10
			 */
			if (egtObserved <610){
				egtActual = egtObserved;
			}else if (610 <= egtObserved && egtObserved <= 635){
				egtActual = egtObserved+ 0.4*egtObserved -244;
			}else if (egtObserved >= 635){
				egtActual = egtObserved+10;
			}
			
		}else if(mode == 30 ){
			//模式1，取实际值
			//for V2530-A5  Engines
			egtActual = egtObserved;
		}else if(mode == 33 ){
			//for V2533-A5
			/*if egtObserved <610 then egtActual = egtObserved
			 *if 610 <= egtObserved <= 650 then egtActual = egtObserved+ 0.5*egtObserved -305;
			 *if egtObserved >= 650 then egtActual = egtObserved+20
			 */
			
			if (egtObserved <610){
				egtActual = egtObserved;
			}else if (610 <= egtObserved && egtObserved <= 650){
				egtActual = egtObserved+ 0.5*egtObserved -305;
			}else if (egtObserved >= 650){
				egtActual = egtObserved+20;
			}
		}else if(mode == 2500 ){
			//FOR V2500-A1
			if (egtObserved <580){
				egtActual = egtObserved;
			}else if (580 <= egtObserved && egtObserved <= 610){
				egtActual = egtObserved+ 0.5*egtObserved - 290.0;
			}else if (egtObserved >= 610){
				egtActual = egtObserved+ 15;
			}
			
		}
		
		return egtActual;
	}

	private double getDeltaN1(String engModel,double theta,double n1) throws Exception{
		double base = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_N1", "1");
		
		return n1/theta - base;
	}
	private double getDeltaN2(String engModel,double theta,double n2) throws Exception{
		double base = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_N2", "1");
		
		return n2/theta - base;
	}
	private double getDeltaFF(String engModel,double theta,double ff) throws Exception{
		double base = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_FF", "1");
		
		return ((ff/theta - base)/base )*100;
	}

}
