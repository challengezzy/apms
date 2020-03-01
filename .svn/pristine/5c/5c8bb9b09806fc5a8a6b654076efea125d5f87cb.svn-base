package com.apms.bs.datacompute;

import java.util.Date;

import smartx.framework.common.vo.HashVO;

import com.apms.bs.datacompute.engine.EngineComputeParamService;
import com.apms.bs.datacompute.vo.a04.A04EgtCfmVo;

public class EngineCfmEgtMarginService {
	
	private String defaultVal = "1";//匹配配置表里的默认值
	
	//获取相关参数 直接返回cfm发动机 的 egt_margin_1, egt_margin_2
	public A04EgtCfmVo cfmEgt1MarginCompute(HashVO curVo) throws Exception{
		double TAT = curVo.getDoubleValue("TAT");
		double N1IND_1 = curVo.getDoubleValue("N1_1");
		double EGT_1 = curVo.getDoubleValue("EGT_1");
		String engModel1 = curVo.getStringValue("ENGMODEL_1");
		int N1MOD_1 = curVo.getIntegerValue("N1MOD_1")==null ? 0 :curVo.getIntegerValue("N1MOD_1");
		String bleedStatus = curVo.getStringValue("BLEED_STATUS");
		String esn_1 = curVo.getStringValue("ESN_1");
		Date rptTime = curVo.getDateValue("RPTDATE");
		
		A04EgtCfmVo calVo = new A04EgtCfmVo();
		
		cfmEgtMarginCompute(calVo, true,TAT, N1IND_1, EGT_1, bleedStatus, engModel1, N1MOD_1, esn_1, rptTime);
		
		double t3 = curVo.getDoubleValue("T3_1");
		double n2 = curVo.getDoubleValue("N2_1");
		double ff = curVo.getDoubleValue("FF_1");
		calVo.setDelta_t3(getDeltaT3(engModel1,calVo.getTheta(), t3));
		calVo.setDelta_n2(getDeltaN2(engModel1,n2,TAT));
		calVo.setDelta_ff(getDeltaFF(engModel1,calVo.getTheta(), ff));
		
		return calVo;
	}
	
	public A04EgtCfmVo cfmEgt2MarginCompute(HashVO curVo) throws Exception{
		double TAT = curVo.getDoubleValue("TAT");
		double N1IND_2 = curVo.getDoubleValue("N1_2");
		double EGT_2 = curVo.getDoubleValue("EGT_2");
		String engModel2 = curVo.getStringValue("ENGMODEL_2");
		int N1MOD_2 = curVo.getIntegerValue("N1MOD_2")==null ? 0 :curVo.getIntegerValue("N1MOD_2");
		String bleedStatus = curVo.getStringValue("BLEED_STATUS");
		String esn_2 = curVo.getStringValue("ESN_2");
		Date rptTime = curVo.getDateValue("RPTDATE");
		
		A04EgtCfmVo calVo = new A04EgtCfmVo();
		
		cfmEgtMarginCompute(calVo, false,TAT, N1IND_2, EGT_2, bleedStatus, engModel2, N1MOD_2, esn_2, rptTime);
		double t3 = curVo.getDoubleValue("T3_2");
		double n2 = curVo.getDoubleValue("N2_2");
		double ff = curVo.getDoubleValue("FF_2");
		calVo.setDelta_t3(getDeltaT3(engModel2,calVo.getTheta(), t3));
		calVo.setDelta_n2(getDeltaN2(engModel2,n2,TAT));
		calVo.setDelta_ff(getDeltaFF(engModel2,calVo.getTheta(), ff));
		
		return calVo;
	}
	
	/**
	 * 计算EGT_MARGIN修正值
	 * @param leftEng 判断是左发还是右发
	 * @throws Exception
	 */
	public void cfmEgtMarginCompute(A04EgtCfmVo calVo,boolean leftEng,double TAT,double N1IND
			,double EGT,String bleedStatus,String engModel,int N1MOD,String esn,Date rptTime) throws Exception{
		
		//A.2.1,THETA = (TAT+273.15)/288.15
		double THETA = (TAT+273.15)/288.15;
		
		
		//A.2.2,N1KIND = N1IND*5000.0/100.0/(THETA**0.47), Where 100% fan speed=5000.rpm, N1IND即N1转速
		double N1KIND = N1IND*5000.0/100.0/Math.pow(THETA, 0.47);
		
		//A.2.2.1 计算VAREXP,Where VAREXP is determined from N1KIND and table 1
		// VAREXP=0.3579453344550019+8.790998217605209*Math.pow(10,-5)*x -1.363074986403867*Math.pow(10,-8)*x^2;
		double VAREXP = 0.3579453344550019 + 8.790998217605209*Math.pow(10,-5)* N1KIND -1.363074986403867*Math.pow(10,-8) * N1KIND*N1KIND;
		calVo.setVarexp(VAREXP);
		
		//A.2.3 N1KIND2 = N1IND*5000/100/(THETA**VAREXP) Where VAREXP is determined from N1KIND and table 1
		double N1KIND2 = N1IND*5000/100/Math.pow(THETA,VAREXP);
		calVo.setTheta(Math.pow(THETA,VAREXP));
		
		//A.2.4 N1KACTCON = N1KIND2-DN1KMOD, Where ND1KMOD is determined from N1KIND2,N1MOD and table 2
		double DN1KMOD = getN1KMOD(esn, N1KIND2,N1MOD,rptTime);//根据不同的发动机配平指数N1MOD，取不同的值

		double N1KACTCON = N1KIND2-DN1KMOD;
		
		//A.2.5 N1ACT = N1KACTCON*(THETA**VAREXP) Where N1ACT is the actual fan speed in rpm
		double N1ACT = N1KACTCON* Math.pow(THETA,VAREXP);
		//A.2.6 N1KACT = N1ACT/(THETA**0.47), Where NAKACT is the fan speed corrected with 0.47 theta exponent
		double N1KACT = N1ACT / Math.pow(THETA,0.47);
		
		double VAREXP2 = 0.3579453344550019 + 8.790998217605209*Math.pow(10,-5)* N1KACT -1.363074986403867*Math.pow(10,-8) * N1KACT*N1KACT;
		//A.2.7 N1KACT2 = N1ACT/(THETA**VAREXP2) ,Where VAREXP2 is determined from N1KACT and table 1
		double N1KACT2 = N1ACT/ Math.pow(THETA,VAREXP2);
		
		//A.3.1 EGTK = (EGT+273.5)/(THETA**EGTEXP) Where EXTEXP is determined from N1ACT and table 3
		double EGTEXP = 0.89;
		double EGTK = (EGT+273.5)/ Math.pow(THETA,EGTEXP);
		calVo.setEgtk(EGTK);
		
		//A.3.2 EGTKM = EGTKLIM - EGTK; Where EGTKLIM is determined from N1KACT2 and table4
		double EGTKLIM = (398.096+0.099*N1KACT2+ 0.0000121*N1KACT2*N1KACT2);
		double EGTKM = EGTKLIM - EGTK;
		
		//A.3.3 EGTKADJ = DEGTCOWL+DEGTWING+DEGTBLD+DEGTN1MOD+DEGTRAT
		/* Where DEGTCOWL is determined by switch setting and table 5
		 * Where DEGTWING is determined by switch setting and table 6
		 * Where DEGTBLD is determined by switch setting and table 7
		 * Where DEGTN1MOD is determined by N1MOD and table 8
		 * Where DEGTRAT is determined by switch setting and table 9
		 */
		double EGTKADJ = getEgtkadj(leftEng,bleedStatus, engModel,N1MOD,esn,rptTime);
		
		//A.3.4 EGTKM2 = EGTKM+EGTKADJ
		double EGTKM2 = EGTKM+EGTKADJ;
		calVo.setEgtkm2(EGTKM2);
		
		//A4 Calculate Hot Day EGT Margin, HDEGTM = EGTKM2*HDTHET
		//HDTHET 根据不同的发动机型号取不同的值
		double HDTHET = EngineComputeParamService.getInstance().getParamValue(engModel, "HDTHET", defaultVal);;
		double HDEGTM = EGTKM2*HDTHET;
		calVo.setHdegtm(HDEGTM);
		
		//A5 HDEGTM 根据海拔高度转换
		
		//A6 计算See level OATL
		double EGTCP = EngineComputeParamService.getInstance().getParamValue(engModel, "EGTCP", defaultVal);
		
		double SLOATL = Math.pow( 1223.15/(1223.15-HDEGTM),1.1236 ) *EGTCP - 273.15;
		
		calVo.setSloatl(SLOATL);
		
		//计算delta_egt, delta_t3
		double baseEgtm = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_EGTM", defaultVal);
		double deltaEgtm = baseEgtm - HDEGTM ;
		calVo.setDelta_egtm(deltaEgtm);
		
	}
	
	
	public double getEgtkadj(boolean leftEng,String bleedStatus,String engModel,int N1MOD,String esn,Date rptTime) throws Exception{
		//A.3.3 EGTKADJ = DEGTCOWL+DEGTWING+DEGTBLD+DEGTN1MOD+DEGTRAT
		//根据不同的发动机型号取值
		/* Where DEGTCOWL is determined by switch setting and table 5
		 * Where DEGTWING is determined by switch setting and table 6
		 * Where DEGTBLD is determined by switch setting and table 7
		 * Where DEGTN1MOD is determined by N1MOD and table 8
		 * Where DEGTRAT is determined by switch setting and table 9
		 */
		double DEGTCOWL=0;
		double DEGTWING=0;
		double DEGTBLD=0;
		double DEGTN1MOD=0;
		double DEGTRAT=0;
		
		String cowlSwitch;
		String wingSwitch;
		String bldSwitch;
		//判断是左发还是右发
		//要区分不同的发动机型号取不同的值，下面取值是按照5B4型号进行
		if(leftEng == true){//左发
			cowlSwitch = bleedStatus.substring(3, 4);
			wingSwitch = bleedStatus.substring(2, 3);
			bldSwitch = bleedStatus.substring(0, 2);
		}else{//右发动机
			cowlSwitch = bleedStatus.substring(9, 10);
			wingSwitch = bleedStatus.substring(10, 11);
			bldSwitch = bleedStatus.substring(11, 13);
		}
		
		if("1".equals(cowlSwitch)){ 
			DEGTCOWL = EngineComputeParamService.getInstance().getParamValue(engModel, "DEGTCOWL", defaultVal);
		}
		
		if("1".equals( wingSwitch)){ 
			DEGTWING = EngineComputeParamService.getInstance().getParamValue(engModel, "DEGTWING", defaultVal);
		}

		double p_bld = EngineComputeParamService.getInstance().getParamValue(engModel, "DEGTBLD", defaultVal);
		double bld = new Double(bldSwitch);
		DEGTBLD = bld*p_bld/100;
		
		//根据型号和N1MOD 进行取值 table 8
		DEGTN1MOD = EngineComputeParamService.getInstance().getParamValue(engModel, "DEGTN1MOD", N1MOD+"");
		
		DEGTRAT = EngineComputeParamService.getInstance().getParamValue(engModel, "DEGTRAT", defaultVal);
		 
		double EGTKADJ = DEGTCOWL+DEGTWING+DEGTBLD+DEGTN1MOD+DEGTRAT;
		
		return EGTKADJ;
	}
	
	private double getN1KMOD(String esn,double N1KIND2,int N1MOD,Date rptTime){
		
		double DN1KMOD=0;
		//先获取发动机机的N1MOD的值
		//取计算公式中RPM的值
		double x = N1KIND2; //N1KIND2 取是要计算的RPM
		
		if( N1MOD == 1){
			//N1MOD=1    
			//N1KIND2<4100   DN1KMOD=-790.8680615236851 + 0.4034199981031732 * x + -5.100283837108721e-005 * x*x + 0 * x*x*x
			//4100<=N1KIND2<=4400  DN1KMOD= 6
			//N1KIND2>4400 DN1KMOD=283.5548324967487 + -0.09597601685507477 * x + 0 * x*x + 1.698168418560248e-009 * x*x*x
			if(N1KIND2< 4100 ){
				DN1KMOD=-790.8680615236851 + 0.4034199981031732 * x + -5.100283837108721e-005 * x*x + 0 * x*x*x;
			}else if ( 4100 <=N1KIND2 && N1KIND2 <= 4400 ){
				DN1KMOD= 6;
			}else{
				DN1KMOD=283.5548324967487 + -0.09597601685507477 * x + 0 * x*x + 1.698168418560248e-009 * x*x*x;
			}
		}else if( N1MOD == 2){
			//N1MOD=2    
			//N1KIND2<4100   DN1KMOD=-1581.73612304737 + 0.8068399962063464 * x + -0.0001020056767421744 * x*x + 0 * x*x*x
			//4100<=N1KIND2<=4400  DN1KMOD= 12
			//N1KIND2>4400 DN1KMOD=567.1096649934973 + -0.1919520337101495 * x + 0 * x*x + 3.396336837120496e-009 * x*x*x
			if(N1KIND2< 4100 ){
				DN1KMOD=-1581.73612304737 + 0.8068399962063464 * x + -0.0001020056767421744 * x*x + 0 * x*x*x;
			}else if ( 4100 <=N1KIND2 && N1KIND2 <= 4400 ){
				DN1KMOD= 12;
			}else{
				DN1KMOD=567.1096649934973 + -0.1919520337101495 * x + 0 * x*x + 3.396336837120496e-009 * x*x*x;
			}
		}else if( N1MOD == 3){
			//NIMOD=3
			//N1KIND2<4100   DN1KMOD=-2372.604184571057 + 1.21025999430952 * x + -0.0001530085151132616 * x*x + 0 * x*x*x
			//4100<=N1KIND2<=4400  DN1KMOD= 18
			//N1KIND2>4400 DN1KMOD=850.6644974902379 + -0.2879280505652217 * x + 0 * x*x + 5.094505255680702e-009 * x*x*x
			if(N1KIND2< 4100 ){
				DN1KMOD=-2372.604184571057 + 1.21025999430952 * x + -0.0001530085151132616 * x*x + 0 * x*x*x;
			}else if ( 4100 <=N1KIND2 && N1KIND2 <= 4400 ){
				DN1KMOD= 18;
			}else{
				DN1KMOD=850.6644974902379 + -0.2879280505652217 * x + 0 * x*x + 5.094505255680702e-009 * x*x*x;
			}
		}else if( N1MOD == 4){
			//NIMOD=4
			//N1KIND2<4100   DN1KMOD=-3495.613335231193 + 1.78733078208513 * x + -0.000226565126347293 * x*x + 0 * x*x*x
			//4100<=N1KIND2<=4400  DN1KMOD= 25
			//N1KIND2>4400 DN1KMOD=242.0204230120884 + 0 * x + -4.101253912277151e-005 * x*x + 6.744302381798769e-009 * x*x*x
			if(N1KIND2< 4100 ){
				DN1KMOD=-3495.613335231193 + 1.78733078208513 * x + -0.000226565126347293 * x*x + 0 * x*x*x;
			}else if ( 4100 <=N1KIND2 && N1KIND2 <= 4400 ){
				DN1KMOD= 25;
			}else{
				DN1KMOD=242.0204230120884 + 0 * x + -4.101253912277151e-005 * x*x + 6.744302381798769e-009 * x*x*x;
			}
		}else if( N1MOD == 5){
			//NIMOD=5
			//N1KIND2<4100   DN1KMOD=-4121.280344307381 + 2.112857264237559 * x + -0.0002684155395259942 * x*x + 0 * x*x*x
			//4100<=N1KIND2<=4400  DN1KMOD= 31
			//N1KIND2>4400 DN1KMOD=715.9760683264042 + 0 * x + -0.0001086177661765698 * x*x + 1.663672639436867e-008 * x*x*x
			if(N1KIND2< 4100 ){
				DN1KMOD=-4121.280344307381 + 2.112857264237559 * x + -0.0002684155395259942 * x*x + 0 * x*x*x;
			}else if ( 4100 <=N1KIND2 && N1KIND2 <= 4400 ){
				DN1KMOD= 31;
			}else{
				DN1KMOD=715.9760683264042 + 0 * x + -0.0001086177661765698 * x*x + 1.663672639436867e-008 * x*x*x;
			}
		}else if( N1MOD == 6){
			//NIMOD=6
			//N1KIND2<4100   DN1KMOD=-5334.093665198732 + 2.722209461197158 * x + -0.00034434301472041 * x*x + 0 * x*x*x
			//4100<=N1KIND2<=4400  DN1KMOD=40
			//N1KIND2>4400 DN1KMOD=910.2498705144849 + 0 * x + -0.0001382208447608685 * x*x + 2.118943947136876e-008 * x*x*x
			if(N1KIND2< 4100 ){
				DN1KMOD=-5334.093665198732 + 2.722209461197158 * x + -0.00034434301472041 * x*x + 0 * x*x*x;
			}else if ( 4100 <=N1KIND2 && N1KIND2 <= 4400 ){
				DN1KMOD=40;
			}else{
				DN1KMOD=910.2498705144849 + 0 * x + -0.0001382208447608685 * x*x + 2.118943947136876e-008 * x*x*x;
			}
		}else if( N1MOD == 7){
			//NIMOD=7
			//N1KIND2<4100   DN1KMOD=-7008.289670807172 + 3.578163668167417 * x + -0.0004528348298323154 * x*x + 0 * x*x*x
			//4100<=N1KIND2<=4400  DN1KMOD=52
			//N1KIND2>4400 DN1KMOD=1236.797858282329 + 0 * x + -0.0001877143241017716 * x*x + 2.87394020521783e-008 * x*x*x
			if(N1KIND2< 4100 ){
				DN1KMOD=-7008.289670807172 + 3.578163668167417 * x + -0.0004528348298323154 * x*x + 0 * x*x*x;
			}else if ( 4100 <=N1KIND2 && N1KIND2 <= 4400 ){
				DN1KMOD=52 ;
			}else{
				DN1KMOD=1236.797858282329 + 0 * x + -0.0001877143241017716 * x*x + 2.87394020521783e-008 * x*x*x ;
			}
		}else{
			DN1KMOD=0;
		}

		return DN1KMOD;
	}
	
	private double getDeltaT3(String engModel,double theta,double t3) throws Exception{
		double base = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_T3", "1");
		
		return t3/theta - base;
	}
	private double getDeltaN2(String engModel,double n2,double TAT) throws Exception{
		double base = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_N2", "1");
		double theta = Math.pow( (TAT+273.15)/288.15, 0.47); 
		return n2/theta - base;
	}
	private double getDeltaFF(String engModel,double theta,double ff) throws Exception{
		double base = EngineComputeParamService.getInstance().getParamValue(engModel, "BASE_FF", "1");
		
		return ((ff/theta - base)/base )*100;
	}

}
