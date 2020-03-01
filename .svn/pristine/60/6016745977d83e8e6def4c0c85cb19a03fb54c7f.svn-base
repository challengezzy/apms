package com.apms.bs.engine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.common.HashVoUtil;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.oxygen.vo.A_DFD_RankPoint;
import com.apms.bs.sysconfig.ApmsSysParamConfService;
import com.apms.bs.sysconfig.EngineConfVarVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.MathUtil;
import com.apms.vo.SysParamConfVo;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

public class IaevEngineChartDetail {
	
	protected  Logger logger = NovaLogger.getLogger(this.getClass());
	
	private  CommDMO dmo = new CommDMO(); 
	
	private SysParamConfVo sysVar;
	private EngineConfVarVo engineVar;
	private double iae_a01_egt_base;
	private double iae_a01_ff_base;
	private double iae_a01_n2_base;
	
	private double iae_a04_egt_base;
	private double iae_a04_ff_base;
	private double iae_a04_n2_base;
	private double iae_a01_n1_base;
	private double iae_a04_n1_base;
	
	private EngineCfdService cfdService = new EngineCfdService();

	
	public IaevEngineChartDetail() throws Exception{
		sysVar = ApmsSysParamConfService.getInstance().getConfVo();
		
		engineVar = sysVar.getEngineVo();
		iae_a01_egt_base = engineVar.getIae_a01_egt_base();
		iae_a01_ff_base = engineVar.getIae_a01_ff_base();
		iae_a01_n1_base = engineVar.getIae_a01_n1_base();

		iae_a01_n2_base = engineVar.getIae_a01_n2_base();
		
		
		iae_a04_egt_base = engineVar.getIae_a04_egt_base();
		iae_a04_ff_base = engineVar.getIae_a04_ff_base();
		iae_a04_n1_base = engineVar.getIae_a04_n1_base();
		iae_a04_n2_base = engineVar.getIae_a04_n2_base();
		
	
	}
	//拿到iaev 巡航页面上所需数据
	public  Map<String, Object> getEngIaeA01ChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>();

		List<Map<String,Object>> allA01List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA01List = getEngineA01ListVos(begin_date, end_date, acNum);
//		for(int i=1;i<vosA01List.length;i++){
//			HashVO vo = vosA01List[i];
//			
//		}
		allA01List.addAll(HashVoUtil.hashVosToMapList(vosA01List));
		resMap.put("allA01List", allA01List);
		
		//查询A01所有的左右换发点
	    List<Map<String,Object>> allA01LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"left");
	    allA01LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA01LeftChangedList", allA01LeftChangedList);
	   
	    List<Map<String,Object>> allA01RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"right");
	    allA01RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA01RightChangedList", allA01RightChangedList);
		
		String allDiv ="EGT_MARGIN_1,EGT_MARGIN_2";
		String[] alldiv = allDiv.split(",");
		
	
		List<Map<String,Object>> temp;
		for(int i=0;i<alldiv.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA04List = getEngineA04ComputedVos(begin_date, end_date, acNum,alldiv[i]);
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA04List));
			String key = "allA04Compu_"+alldiv[i].toLowerCase();
		    resMap.put(key,temp) ;
		}
		
		//斜率点集合
		List<A_DFD_RankPoint> n1_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n1_2RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> n2_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n2_2RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> egt_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> egt_2RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> egtMargin_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> egtMargin_2RankList = new ArrayList<A_DFD_RankPoint>();

		List<A_DFD_RankPoint> ff_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> ff_2RankList = new ArrayList<A_DFD_RankPoint>();
		//计算数据数组
		List<Double> xarr_time = new ArrayList<Double>(vosA01List.length);

		List<Double> n1yarr1 = new ArrayList<Double>(vosA01List.length);
		List<Double> n1yarr2 = new ArrayList<Double>(vosA01List.length);

		List<Double> n2yarr2 = new ArrayList<Double>(vosA01List.length);
		List<Double> n2yarr1 = new ArrayList<Double>(vosA01List.length);
		
		List<Double> egtyarr2 = new ArrayList<Double>(vosA01List.length);
		List<Double> egtyarr1 = new ArrayList<Double>(vosA01List.length);
		
		List<Double> egtMarginyarr1 = new ArrayList<Double>(vosA01List.length);
		List<Double> egtMarginyarr2 = new ArrayList<Double>(vosA01List.length);
		
		List<Double> ffyarr1 = new ArrayList<Double>(vosA01List.length);
		List<Double> ffyarr2 = new ArrayList<Double>(vosA01List.length);
		
		for (int i = 0; i < vosA01List.length; i++) {
			HashVO vo = vosA01List[i];
			n1yarr1.add(vo.getDoubleValue("DELTA_N1_1"));
			n1yarr2.add(vo.getDoubleValue("DELTA_N1_2"));
			
			xarr_time.add(new Double(vo.getDateValue("UTCDATE").getTime()+""));//
			n2yarr1.add(vo.getDoubleValue("DELTA_N2_1"));
			n2yarr2.add(vo.getDoubleValue("DELTA_N2_2"));
			
			egtyarr1.add(vo.getDoubleValue("DELTA_EGT_1"));
			egtyarr2.add(vo.getDoubleValue("DELTA_EGT_2"));
			
			egtMarginyarr1.add(vo.getDoubleValue("EGT_MARGIN_1"));
			egtMarginyarr2.add(vo.getDoubleValue("EGT_MARGIN_2"));
			
			ffyarr1.add(vo.getDoubleValue("DELTA_FF_1"));
			ffyarr2.add(vo.getDoubleValue("DELTA_FF_2"));
		}
		
		if(isRank && xarr_time.size() > 2){
			//回归计算
			List<A_DFD_RankPoint> tmpRank_n1_1 = MathUtil.computeRankList(xarr_time,n1yarr1);
			n1_1RankList.addAll(tmpRank_n1_1);
		
			List<A_DFD_RankPoint> tmpRank_n1_2 = MathUtil.computeRankList(xarr_time,n1yarr2);
			n1_2RankList.addAll(tmpRank_n1_2);
		
			List<A_DFD_RankPoint> tmpRank_n2_1 = MathUtil.computeRankList(xarr_time,n2yarr1);
			n2_1RankList.addAll(tmpRank_n2_1);
		
			List<A_DFD_RankPoint> tmpRank_n2_2 = MathUtil.computeRankList(xarr_time,n2yarr2);
			n2_2RankList.addAll(tmpRank_n2_2);
		
			List<A_DFD_RankPoint> tmpRank_egt_1 = MathUtil.computeRankList(xarr_time,egtyarr1);
			egt_1RankList.addAll(tmpRank_egt_1);
		
			List<A_DFD_RankPoint> tmpRank_egt_2 = MathUtil.computeRankList(xarr_time,egtyarr2);
			egt_2RankList.addAll(tmpRank_egt_2);
		
			List<A_DFD_RankPoint> tmpRank_egmMargin_1 = MathUtil.computeRankList(xarr_time, egtMarginyarr1);
			egtMargin_1RankList.addAll(tmpRank_egmMargin_1);
		
			List<A_DFD_RankPoint> tmpRank_egmMargin_2 = MathUtil.computeRankList(xarr_time, egtMarginyarr2);
			egtMargin_2RankList.addAll(tmpRank_egmMargin_2);
		
			List<A_DFD_RankPoint> tmpRank_ff_1 = MathUtil.computeRankList(xarr_time, ffyarr1);
			ff_1RankList.addAll(tmpRank_ff_1);
		
			List<A_DFD_RankPoint> tmpRank_ff_2 = MathUtil.computeRankList(xarr_time, ffyarr2);
			ff_2RankList.addAll(tmpRank_ff_2);
		}
		
		resMap.put("n1_1RankList",n1_1RankList );
		resMap.put("n1_2RankList",n1_2RankList );
		resMap.put("n2_1RankList",n2_2RankList);
		resMap.put("n2_2RankList",n2_2RankList);
		resMap.put("egt_1RankList", egt_1RankList);
		resMap.put("egt_2RankList", egt_2RankList);
		resMap.put("ff_1RankList", ff_1RankList);
		resMap.put("ff_2RankList", ff_2RankList);
		resMap.put("egtMargin_1RankList", egtMargin_1RankList);
		resMap.put("egtMargin_2RankList", egtMargin_2RankList);
		
		List<Map<String,Object>> warnList = cfdService.getEngWarnList(begin_date, end_date, acNum);
		List<Map<String,Object>> faultList = cfdService.getEngFaultList(begin_date, end_date, acNum);
		List<Map<String,Object>> workList = cfdService.getEngMWork(begin_date, end_date, acNum);
		
		if(allA01List.size() > 1){
			warnList.add(0, allA01List.get(0));
			warnList.add(allA01List.get(allA01List.size()-1));
			
			faultList.add(0, allA01List.get(0));
			faultList.add(allA01List.get(allA01List.size()-1));
			
			workList.add(0, allA01List.get(0));
			workList.add(allA01List.get(allA01List.size()-1));
		}
		resMap.put("warnList",  warnList);
		resMap.put("faultList", faultList );		
		resMap.put("workList", workList );
		
		return resMap;
	}
	
	//拿到iaev 起飞页面上所需数据
	public  Map<String, Object> getEngIaeA04ChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>();

		//查询A04所有的左右换发点
	    List<Map<String,Object>> allA04LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA04ListChangedVos(begin_date, end_date, acNum,"left");
	    allA04LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA04LeftChangedList", allA04LeftChangedList);
	   
	    List<Map<String,Object>> allA04RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA04ListChangedVos(begin_date, end_date, acNum,"right");
	    allA04RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA04RightChangedList", allA04RightChangedList);
	    
		List<Map<String,Object>> allA04List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA04List = getEngineA04ListVos(begin_date, end_date, acNum);
		for(int i=1;i<vosA04List.length;i++){
			HashVO vo = vosA04List[i];
			Double delta_egt_1 = vo.getDoubleValue("DELTA_EGT_1");
			Double delta_ff_1 = vo.getDoubleValue("DELTA_FF_1");
			Double delta_n1_1 = vo.getDoubleValue("DELTA_N1_1");
			Double delta_n2_1 = vo.getDoubleValue("DELTA_N2_1");
			Double delta_t3_1 = vo.getDoubleValue("DELTA_T3_1");
			
			Double delta_egt_2 = vo.getDoubleValue("DELTA_EGT_2");
			Double delta_ff_2 = vo.getDoubleValue("DELTA_FF_2");
			Double delta_n1_2 = vo.getDoubleValue("DELTA_N1_2");
			Double delta_n2_2 = vo.getDoubleValue("DELTA_N2_2");
			Double delta_t3_2 = vo.getDoubleValue("DELTA_T3_2");
			
			vo.setAttributeValue("DIV_DELTA_EGT_1", delta_egt_1 - delta_egt_2 );
			vo.setAttributeValue("DIV_DELTA_FF_1", delta_ff_1- delta_ff_2 );
			vo.setAttributeValue("DIV_DELTA_N1_1", delta_n1_1 - delta_n1_2 );
			vo.setAttributeValue("DIV_DELTA_N2_1", delta_n2_1 - delta_n2_2 );
			vo.setAttributeValue("DIV_DELTA_EGT_2", delta_egt_2 - delta_egt_1 );
			vo.setAttributeValue("DIV_DELTA_FF_2", delta_ff_2 - delta_ff_1 );
			vo.setAttributeValue("DIV_DELTA_N1_2", delta_n1_2 - delta_n1_1 );
			vo.setAttributeValue("DIV_DELTA_N2_2", delta_n2_2 - delta_n2_1 );
			
			vo.setAttributeValue("DIV_DELTA_T3_1", delta_t3_1 - delta_t3_2 );
			vo.setAttributeValue("DIV_DELTA_T3_2", delta_t3_2 - delta_t3_1 );
		}
		allA04List.addAll(HashVoUtil.hashVosToMapList(vosA04List));
		resMap.put("allA04List", allA04List);
		
		//查询fieldcompute计算值
		String allDiv ="EGT_MARGIN_1,EGT_MARGIN_2";
		String[] alldiv = allDiv.split(",");
		List<Map<String,Object>> temp;
		for(int i=0;i<alldiv.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA04List = getEngineA04ComputedVos(begin_date, end_date, acNum,alldiv[i]);
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA04List));
			String key = "allA04Compu_"+alldiv[i].toLowerCase();
		    resMap.put(key,temp) ;
		}
		
		//斜率点集合
		List<A_DFD_RankPoint> n1_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n1_2RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> n2_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n2_2RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> egt_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> egt_2RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> egtMargin_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> egtMargin_2RankList = new ArrayList<A_DFD_RankPoint>();

		List<A_DFD_RankPoint> ff_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> ff_2RankList = new ArrayList<A_DFD_RankPoint>();
		//计算数据数组
		List<Double> xarr_time = new ArrayList<Double>(vosA04List.length);

		List<Double> n1yarr1 = new ArrayList<Double>(vosA04List.length);
		List<Double> n1yarr2 = new ArrayList<Double>(vosA04List.length);

		List<Double> n2yarr2 = new ArrayList<Double>(vosA04List.length);
		List<Double> n2yarr1 = new ArrayList<Double>(vosA04List.length);
		
		List<Double> egtyarr2 = new ArrayList<Double>(vosA04List.length);
		List<Double> egtyarr1 = new ArrayList<Double>(vosA04List.length);
		
		List<Double> egtMarginyarr1 = new ArrayList<Double>(vosA04List.length);
		List<Double> egtMarginyarr2 = new ArrayList<Double>(vosA04List.length);
		
		List<Double> ffyarr1 = new ArrayList<Double>(vosA04List.length);
		List<Double> ffyarr2 = new ArrayList<Double>(vosA04List.length);
		
		
		if(isRank && vosA04List.length > 3){
			for (int i = 0; i < vosA04List.length; i++) {
				HashVO vo = vosA04List[i];
				xarr_time.add(new Double(vo.getDateValue("UTCDATE").getTime()+""));//
				n1yarr1.add(vo.getDoubleValue("DELTA_N1_1"));
				n1yarr2.add(vo.getDoubleValue("DELTA_N1_2"));
				
				n2yarr1.add(vo.getDoubleValue("DELTA_N2_1"));
				n2yarr2.add(vo.getDoubleValue("DELTA_N2_2"));
				
				egtyarr1.add(vo.getDoubleValue("DELTA_EGT_1"));
				egtyarr2.add(vo.getDoubleValue("DELTA_EGT_2"));
				
				ffyarr1.add(vo.getDoubleValue("DELTA_FF_1"));
				ffyarr2.add(vo.getDoubleValue("DELTA_FF_2"));
				
				egtMarginyarr1.add(vo.getDoubleValue("EGT_MARGIN_1"));
				egtMarginyarr2.add(vo.getDoubleValue("EGT_MARGIN_2"));
			}
			
			//回归计算
			List<A_DFD_RankPoint> tmpRank_n2vsv_1 = MathUtil.computeRankList(xarr_time,n1yarr1);
			n1_1RankList.addAll(tmpRank_n2vsv_1);
		
			List<A_DFD_RankPoint> tmpRank_n2vsv_2 = MathUtil.computeRankList(xarr_time,n1yarr2);
			n1_2RankList.addAll(tmpRank_n2vsv_2);
		
			List<A_DFD_RankPoint> tmpRank_n2_1 = MathUtil.computeRankList(xarr_time,n2yarr1);
			n2_1RankList.addAll(tmpRank_n2_1);
		
			List<A_DFD_RankPoint> tmpRank_n2_2 = MathUtil.computeRankList(xarr_time,n2yarr2);
			n2_2RankList.addAll(tmpRank_n2_2);
		
			List<A_DFD_RankPoint> tmpRank_egt_1 = MathUtil.computeRankList(xarr_time,egtyarr1);
			egt_1RankList.addAll(tmpRank_egt_1);
		
			List<A_DFD_RankPoint> tmpRank_egt_2 = MathUtil.computeRankList(xarr_time,egtyarr2);
			egt_2RankList.addAll(tmpRank_egt_2);
		
			List<A_DFD_RankPoint> tmpRank_egmMargin_1 = MathUtil.computeRankList(xarr_time, egtMarginyarr1);
			egtMargin_1RankList.addAll(tmpRank_egmMargin_1);
		
			List<A_DFD_RankPoint> tmpRank_egmMargin_2 = MathUtil.computeRankList(xarr_time, egtMarginyarr2);
			egtMargin_2RankList.addAll(tmpRank_egmMargin_2);
		
			List<A_DFD_RankPoint> tmpRank_ff_1 = MathUtil.computeRankList(xarr_time, ffyarr1);
			ff_1RankList.addAll(tmpRank_ff_1);
		
			List<A_DFD_RankPoint> tmpRank_ff_2 = MathUtil.computeRankList(xarr_time, ffyarr2);
			ff_2RankList.addAll(tmpRank_ff_2);
		}
		
		resMap.put("n1_1RankList",n1_1RankList );
		resMap.put("n1_2RankList",n1_2RankList );
		resMap.put("n2_1RankList",n2_2RankList);
		resMap.put("n2_2RankList",n2_2RankList);
		resMap.put("egt_1RankList", egt_1RankList);
		resMap.put("egt_2RankList", egt_2RankList);
		resMap.put("ff_1RankList", ff_1RankList);
		resMap.put("ff_2RankList", ff_2RankList);
		resMap.put("egtMargin_1RankList", egtMargin_1RankList);
		resMap.put("egtMargin_2RankList", egtMargin_2RankList);
		
		
		List<Map<String,Object>> warnList = cfdService.getEngWarnList(begin_date, end_date, acNum);
		List<Map<String,Object>> faultList = cfdService.getEngFaultList(begin_date, end_date, acNum);
		List<Map<String,Object>> workList = cfdService.getEngMWork(begin_date, end_date, acNum);
		
		if(allA04List.size() > 1){
			warnList.add(0, allA04List.get(0));
			warnList.add(allA04List.get(allA04List.size()-1));
			
			faultList.add(0, allA04List.get(0));
			faultList.add(allA04List.get(allA04List.size()-1));
			
			workList.add(0, allA04List.get(0));
			workList.add(allA04List.get(allA04List.size()-1));
		}
		resMap.put("warnList",  warnList);
		resMap.put("faultList", faultList );
		
		resMap.put("workList", workList );
		
		return resMap;
	}
	
	//拿到iaev 滑油页面上所需数据
	public  Map<String, Object> getIaevEngIneSildeOilChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>();	
		
		//查询A01所有的左右换发点
	    List<Map<String,Object>> allA01LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"left");
	    allA01LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA01LeftChangedList", allA01LeftChangedList);
	   
	    List<Map<String,Object>> allA01RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"right");
	    allA01RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA01RightChangedList", allA01RightChangedList);
	    
	    //查询A04所有的左右换发点
	    List<Map<String,Object>> allA04LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList2 = getEngineA04ListChangedVos(begin_date, end_date, acNum,"left");
	    allA04LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList2));
	    resMap.put("allA04LeftChangedList", allA04LeftChangedList);
	   
	    List<Map<String,Object>> allA04RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList2 = getEngineA04ListChangedVos(begin_date, end_date, acNum,"right");
	    allA04RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList2));
	    resMap.put("allA04RightChangedList", allA04RightChangedList);
	    
		List<Map<String,Object>> temp;

		String allA01Div ="OIP_1,OIP_2,OIT_1,OIT_2,OIQH_1,OIQH_2,DIV_OIP,DIV_OIT,DIV_OIQH";
		String[] alla01div = allA01Div.split(",");
		for(int i=0;i<alla01div.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA01List = getEngineA01ComputedVos(begin_date, end_date, acNum,alla01div[i]);
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA01List));
			String key = "allA01Compu_"+alla01div[i].toLowerCase();
		    resMap.put(key,temp) ;
		}
		
		String allA04Div ="OIP_1,OIP_2,OIT_1,OIT_2,DIV_OIP,DIV_OIT";
		String[] alla04div = allA04Div.split(",");
		for(int i=0;i<alla04div.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA04List = getEngineA04ComputedVos(begin_date, end_date, acNum,alla04div[i]);
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA04List));
			String key = "allA04Compu_"+alla04div[i].toLowerCase();
		    resMap.put(key,temp) ;
		}
		
		List<Map<String,Object>> allA27List1 = new ArrayList<Map<String,Object>>();
	    List<Map<String,Object>> allA27List2 = new ArrayList<Map<String,Object>>();
	    
		List<Map<String,Object>> allList = new ArrayList<Map<String,Object>>();//所有点
	    List<Map<String,Object>> changList1 = new ArrayList<Map<String,Object>>();//左发
		List<Map<String,Object>> changList2 = new ArrayList<Map<String,Object>>();//右发
	    HashVO[] vos1A27 = getA27ComputedVos1(begin_date, end_date, acNum);
		HashVO[] vos2A27 = getA27ComputedVos2(begin_date, end_date, acNum);
		allA27List1.addAll(HashVoUtil.hashVosToMapList(vos1A27));
		allA27List2.addAll(HashVoUtil.hashVosToMapList(vos2A27));
		
		HashVO[] vos = getComputedVos(begin_date, end_date, acNum);

		for (int i = 0; i < vos.length; i++) {
			HashVO vo = vos[i];
			Map<String,Object> ac = HashVoUtil.hashVoToMap(vo);
			int isAdd1 = new Integer(ac.get("DETA_OIQ1_FWD_S").toString());
			int isAdd2 = new Integer(ac.get("DETA_OIQ2_FWD_S").toString());
			//应该有左发、右发两个信息
			if(DfdVarConst.CHANGEPOINT_YES == isAdd1 || DfdVarConst.CHANGEPOINT_YES == isAdd2){
				//左发新滑油添加点
				if(DfdVarConst.CHANGEPOINT_YES == isAdd1){
					changList1.add(ac);
				}
				if(DfdVarConst.CHANGEPOINT_YES == isAdd2){
					changList2.add(ac);
				}
				
				ac.put("OIQ1_RATE_ROLL5",null);
				ac.put("OIQ2_RATE_ROLL5",null);
				allList.add(ac);
			}else{
				allList.add(ac);
			}
		}
		resMap.put("allList", allList);
		resMap.put("allA27List1", allA27List1);
		resMap.put("allA27List2", allA27List2);
		resMap.put("changList1", changList1);
		resMap.put("changList2", changList2);
		return resMap;
	}
	
	
	//拿到iaev 振动值页面上所需数据
	public  Map<String, Object> getIaevEngineVibrationChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>();

		List<Map<String,Object>> allA01List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA01List = getEngineA01ListVos(begin_date, end_date, acNum);
		allA01List.addAll(HashVoUtil.hashVosToMapList(vosA01List));
		resMap.put("allA01List", allA01List);
		
		//查询A01所有的左右换发点
	    List<Map<String,Object>> allA01LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"left");
	    allA01LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA01LeftChangedList", allA01LeftChangedList);
	   
	    List<Map<String,Object>> allA01RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"right");
	    allA01RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA01RightChangedList", allA01RightChangedList);
	    
		List<Map<String,Object>> allA04List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA04List = getEngineA04ListVos(begin_date, end_date, acNum);
		allA04List.addAll(HashVoUtil.hashVosToMapList(vosA04List));
		resMap.put("allA04List", allA04List);
		
		//查询A04所有的左右换发点
	    List<Map<String,Object>> allA04LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList2 = getEngineA04ListChangedVos(begin_date, end_date, acNum,"left");
	    allA04LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList2));
	    resMap.put("allA04LeftChangedList", allA04LeftChangedList);
	   
	    List<Map<String,Object>> allA04RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList2 = getEngineA04ListChangedVos(begin_date, end_date, acNum,"right");
	    allA04RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList2));
	    resMap.put("allA04RightChangedList", allA04RightChangedList);
		
		//斜率点集合
		List<A_DFD_RankPoint> n1_1A01RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n1_2A01RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> n2_1A01RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n2_2A01RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> n1_1A04RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n1_2A04RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> n2_1A04RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n2_2A04RankList = new ArrayList<A_DFD_RankPoint>();
		
		//计算数据数组
		List<Double> xarr_time_A01 = new ArrayList<Double>(vosA01List.length);
		List<Double> n1yarr1_A01 = new ArrayList<Double>(vosA01List.length);
		List<Double> n1yarr2_A01 = new ArrayList<Double>(vosA01List.length);
		List<Double> n2yarr2_A01 = new ArrayList<Double>(vosA01List.length);
		List<Double> n2yarr1_A01 = new ArrayList<Double>(vosA01List.length);
		
		List<Double> xarr_time_A04 = new ArrayList<Double>(vosA04List.length);
		List<Double> n1yarr1_A04 = new ArrayList<Double>(vosA04List.length);
		List<Double> n1yarr2_A04 = new ArrayList<Double>(vosA04List.length);
		List<Double> n2yarr2_A04 = new ArrayList<Double>(vosA04List.length);
		List<Double> n2yarr1_A04 = new ArrayList<Double>(vosA04List.length);
		
		for (int i = 0; i < vosA01List.length; i++) {
			HashVO vo = vosA01List[i];
			xarr_time_A01.add(new Double(vo.getDateValue("UTCDATE").getTime()+""));//
			n1yarr1_A01.add(vo.getDoubleValue("VB1_1"));
			n1yarr2_A01.add(vo.getDoubleValue("VB1_2"));
			
			n2yarr1_A01.add(vo.getDoubleValue("VB2_1"));
			n2yarr2_A01.add(vo.getDoubleValue("VB2_2"));
		}
		
		for (int i = 0; i < vosA04List.length; i++) {
			HashVO vo = vosA04List[i];
			xarr_time_A04.add(new Double(vo.getDateValue("UTCDATE").getTime()+""));//
			n1yarr1_A04.add(vo.getDoubleValue("VB1_1"));
			n1yarr2_A04.add(vo.getDoubleValue("VB1_2"));
			
			n2yarr1_A04.add(vo.getDoubleValue("VB2_1"));
			n2yarr2_A04.add(vo.getDoubleValue("VB2_2"));
		}
		if(isRank && xarr_time_A01.size() > 2){
			//回归计算
			List<A_DFD_RankPoint> tmpRank_n2vsv_1 = MathUtil.computeRankList(xarr_time_A01,n1yarr1_A01);
			n1_1A01RankList.addAll(tmpRank_n2vsv_1);
		
			List<A_DFD_RankPoint> tmpRank_n2vsv_2 = MathUtil.computeRankList(xarr_time_A01,n1yarr2_A01);
			n1_2A01RankList.addAll(tmpRank_n2vsv_2);
		
			List<A_DFD_RankPoint> tmpRank_n2_1 = MathUtil.computeRankList(xarr_time_A01,n2yarr1_A01);
			n2_1A01RankList.addAll(tmpRank_n2_1);
		
			List<A_DFD_RankPoint> tmpRank_n2_2 = MathUtil.computeRankList(xarr_time_A01,n2yarr2_A01);
			n2_2A01RankList.addAll(tmpRank_n2_2);
		}
		
		if(isRank && xarr_time_A04.size() > 2){
			//回归计算
			List<A_DFD_RankPoint> tmpRank_n2vsv_1 = MathUtil.computeRankList(xarr_time_A04,n1yarr1_A04);
			n1_1A04RankList.addAll(tmpRank_n2vsv_1);
		
			List<A_DFD_RankPoint> tmpRank_n2vsv_2 = MathUtil.computeRankList(xarr_time_A04,n1yarr2_A04);
			n1_2A04RankList.addAll(tmpRank_n2vsv_2);
		
			List<A_DFD_RankPoint> tmpRank_n2_1 = MathUtil.computeRankList(xarr_time_A04,n2yarr1_A04);
			n2_1A04RankList.addAll(tmpRank_n2_1);
		
			List<A_DFD_RankPoint> tmpRank_n2_2 = MathUtil.computeRankList(xarr_time_A04,n2yarr2_A04);
			n2_2A04RankList.addAll(tmpRank_n2_2);
		}
		
		resMap.put("n1_1A01RankList",n1_1A01RankList);
		resMap.put("n1_2A01RankList",n1_2A01RankList);
		resMap.put("n2_1A01RankList",n2_2A01RankList);
		resMap.put("n2_2A01RankList",n2_2A01RankList);
		
		resMap.put("n1_1A04RankList",n1_1A04RankList);
		resMap.put("n1_2A04RankList",n1_2A04RankList);
		resMap.put("n2_1A04RankList",n2_2A04RankList);
		resMap.put("n2_2A04RankList",n2_2A04RankList);
	    
		return resMap;
	}
	
	//拿到iaev 单元体性能页面上所需数据
	public Map<String, Object> getEngIneA01UnitChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>();

		List<Map<String,Object>> allA01List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA01List = getEngineA01ListVos(begin_date, end_date, acNum);
		allA01List.addAll(HashVoUtil.hashVosToMapList(vosA01List));
		resMap.put("allA01List", allA01List);
		
		//查询A01所有的左右换发点
	    List<Map<String,Object>> allA01LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"left");
	    allA01LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA01LeftChangedList", allA01LeftChangedList);
	   
	    List<Map<String,Object>> allA01RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"right");
	    allA01RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA01RightChangedList", allA01RightChangedList);
	    
		String allDiv ="EGT_MARGIN_1,EGT_MARGIN_2";
		String[] alldiv = allDiv.split(",");
		
	
		List<Map<String,Object>> temp;
		for(int i=0;i<alldiv.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA01List = getEngineA01ComputedVos(begin_date, end_date, acNum,alldiv[i]);
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA01List));
			String key = "allA04Compu_"+alldiv[i].toLowerCase();
		    resMap.put(key,temp) ;
		}
		
		
		//斜率点集合
		List<A_DFD_RankPoint> n1_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n1_2RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> n2_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> n2_2RankList = new ArrayList<A_DFD_RankPoint>();
		
		
		List<A_DFD_RankPoint> egtMargin_1RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> egtMargin_2RankList = new ArrayList<A_DFD_RankPoint>();

		
		//计算数据数组
		List<Double> xarr_time = new ArrayList<Double>(vosA01List.length);

		List<Double> n1yarr1 = new ArrayList<Double>(vosA01List.length);
		List<Double> n1yarr2 = new ArrayList<Double>(vosA01List.length);

		List<Double> n2yarr2 = new ArrayList<Double>(vosA01List.length);
		List<Double> n2yarr1 = new ArrayList<Double>(vosA01List.length);
		
		List<Double> egtMarginyarr1 = new ArrayList<Double>(vosA01List.length);
		List<Double> egtMarginyarr2 = new ArrayList<Double>(vosA01List.length);
	
		for (int i = 0; i < vosA01List.length; i++) {
			HashVO vo = vosA01List[i];
			xarr_time.add(new Double(vo.getDateValue("UTCDATE").getTime()+""));//

			n1yarr1.add(vo.getDoubleValue("N1_1"));
			n1yarr2.add(vo.getDoubleValue("N1_2"));
			n2yarr1.add(vo.getDoubleValue("N2_1"));
			n2yarr2.add(vo.getDoubleValue("N2_2"));
			egtMarginyarr1.add(vo.getDoubleValue("EGT_MARGIN_1"));
			egtMarginyarr2.add(vo.getDoubleValue("EGT_MARGIN_2"));
		}
		
		if(isRank && xarr_time.size() > 2){
			//回归计算
			List<A_DFD_RankPoint> tmpRank_n1_1 = MathUtil.computeRankList(xarr_time,n1yarr1);
			n1_1RankList.addAll(tmpRank_n1_1);
		
			List<A_DFD_RankPoint> tmpRank_n1_2 = MathUtil.computeRankList(xarr_time,n1yarr2);
			n1_2RankList.addAll(tmpRank_n1_2);
		
			List<A_DFD_RankPoint> tmpRank_n2_1 = MathUtil.computeRankList(xarr_time,n2yarr1);
			n2_1RankList.addAll(tmpRank_n2_1);
		
			List<A_DFD_RankPoint> tmpRank_n2_2 = MathUtil.computeRankList(xarr_time,n2yarr2);
			n2_2RankList.addAll(tmpRank_n2_2);
		
			List<A_DFD_RankPoint> tmpRank_egmMargin_1 = MathUtil.computeRankList(xarr_time, egtMarginyarr1);
			egtMargin_1RankList.addAll(tmpRank_egmMargin_1);
		
			List<A_DFD_RankPoint> tmpRank_egmMargin_2 = MathUtil.computeRankList(xarr_time, egtMarginyarr2);
			egtMargin_2RankList.addAll(tmpRank_egmMargin_2);
		}
		
		resMap.put("n1_1RankList",n1_1RankList );
		resMap.put("n1_2RankList",n1_2RankList );
		resMap.put("n2_1RankList",n2_2RankList);
		resMap.put("n2_2RankList",n2_2RankList);
		resMap.put("egtMargin_1RankList", egtMargin_1RankList);
		resMap.put("egtMargin_2RankList", egtMargin_2RankList);
		
		return resMap;
	}
	
	
	//拿到iaev A01作动组件及传感器页面上所需数据
	public  Map<String, Object> getEngIneA01AutuChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>();

		List<Map<String,Object>> allA01List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA01List = getEngineA01ListVos(begin_date, end_date, acNum);
		allA01List.addAll(HashVoUtil.hashVosToMapList(vosA01List));
		resMap.put("allA01List", allA01List);
		
		//查询A01所有的左右换发点
	    List<Map<String,Object>> allA01LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"left");
	    allA01LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA01LeftChangedList", allA01LeftChangedList);
	   
	    List<Map<String,Object>> allA01RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA01ListChangedVos(begin_date, end_date, acNum,"right");
	    allA01RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA01RightChangedList", allA01RightChangedList);
	    
	    for(Map<String,Object> a01item : allA01List){
	    	a01item.put("T2TAT_RL_UPPER", 4);//加入T2-TAT 的红线值
	    	a01item.put("T2TAT_RL_LOWER", -4);
	    }
	    
//		String allDiv ="DIV_BAF,DIV_SVA,DIV_P125P2,DIV_P25P2,DIV_P3P25,DIV_P49P3,DIV_T2"; 
//		String[] alldiv = allDiv.split(",");
//		List<Map<String,Object>> temp;
//		for(int i=0;i<alldiv.length;i++){
//			temp =new ArrayList<Map<String,Object>>();
//			HashVO[] CvosA01List = getEngineA01ComputedVos(begin_date, end_date, acNum,alldiv[i]);
//			temp.addAll(HashVoUtil.hashVosToMapList(CvosA01List));
//			String key = "allA01Compu_"+alldiv[i].toLowerCase();
//		    resMap.put(key,temp) ;
//		}
		return resMap;
	}
	
	
	//拿到iaev A04作动组件及传感器页面上所需数据
	public  Map<String, Object> getEngIneA04AutuChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>();

		List<Map<String,Object>> allA04List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA04List = getEngineA04ListVos(begin_date, end_date, acNum);
		allA04List.addAll(HashVoUtil.hashVosToMapList(vosA04List));
		resMap.put("allA04List", allA04List);
		
		//查询A04所有的左右换发点
	    List<Map<String,Object>> allA04LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList2 = getEngineA04ListChangedVos(begin_date, end_date, acNum,"left");
	    allA04LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList2));
	    resMap.put("allA04LeftChangedList", allA04LeftChangedList);
	   
	    List<Map<String,Object>> allA04RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList2 = getEngineA04ListChangedVos(begin_date, end_date, acNum,"right");
	    allA04RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList2));
	    resMap.put("allA04RightChangedList", allA04RightChangedList);
	    		
	    for(Map<String,Object> a04item : allA04List){
	    	a04item.put("T2TAT_RL_UPPER", 4);//加入T2-TAT 的红线值
	    	a04item.put("T2TAT_RL_LOWER", -4);
	    }
		
		return resMap;
	}
	
	
	//拿到A01 基本字段信息
	private  HashVO[] getEngineA01ListVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE, T.MSG_NO");
		sb.append(",TAT"); 
		sb.append(",EGT_1, EGT_2, N1_1, N1_2, N2_1, N2_2, FF_1, FF_2, ACC_1, ACC_2");
		sb.append(",trunc(P125_1/P2_1,4) P125P2_1, trunc(P125_2/P2_2,4) P125P2_2, trunc(P25_1/P2_1,4) P25P2_1");
		sb.append(",trunc(P25_2/P2_2,4) P25P2_2, trunc(P3_1/P25_1,4) P3P25_1, trunc(P3_2/P25_2,4) P3P25_2");
		sb.append(",trunc(P3_1/P49_1,4) P49P3_1, trunc(P3_2/P49_2,4) P49P3_2, T2_1, T2_2, T3_1, T3_2, T25_1, T25_2");
		sb.append(", P2_1, P2_2, T2_1-TAT T2TAT_1, T2_2-TAT T2TAT_2");
		sb.append(",TN_1,TN_2,(TN_1-TN_2)/2 DIV_TN_1,(TN_2-TN_1)/2 DIV_TN_2 ");
		sb.append(",SVA_1, SVA_2, BAF_1, BAF_2");
		sb.append(", ENG_BTO_1, ENG_BO_1, EGT_CO_1, EGT_CAL_1, EGT_MARGIN_1, T3_CO_1, T3_CAL_1, EGT_ACT_1");
		sb.append(", ENG_BTO_2, ENG_BO_2, EGT_CO_2, EGT_CAL_2, EGT_MARGIN_2, T3_CO_2, T3_CAL_2, EGT_ACT_2");
		sb.append(", DELTA_EGT_1, DELTA_FF_1, DELTA_N1_1, DELTA_N2_1, DELTA_T3_1,DELTA_T25_1");
		sb.append(", DELTA_EGT_2, DELTA_FF_2, DELTA_N1_2, DELTA_N2_2, DELTA_T3_2,DELTA_T25_2");
		sb.append(", T25_CO_1, T25_CO_2,T25_CAL_1,T25_CAL_2");
		sb.append(", DIV_EGT_MARGIN1, DIV_EGT_MARGIN2, DIV_EGT_1,DIV_EGT_2, DIV_N1_1, DIV_N1_2, DIV_N2_1, DIV_N2_2, DIV_P2_1, DIV_P2_2");
		sb.append(",DIV_FF_1, DIV_FF_2, DIV_SVA_1, DIV_SVA_2, DIV_BAF_1, DIV_BAF_2,OIP_1, OIP_2 ");
		sb.append(",OIT_1, OIT_2,OIQH_1,OIQH_2, DIV_OIP_1, DIV_OIP_2, DIV_OIT_1, DIV_OIT_2, DIV_OIQH_1");
		sb.append(",DIV_OIQH_2, DIV_P125P2_1, DIV_P125P2_2, DIV_P25P2_1, DIV_P25P2_2, DIV_P3P25_1");
		sb.append(",DIV_P3P25_2, DIV_P49P3_1, DIV_P49P3_2,DIV_T2_1,DIV_T2_2");
		sb.append(",T1.EGT_MARGIN_1,T1.EGT_MARGIN_2");
		sb.append(",PHA_1, PHA_2,VB1_1,VB1_2,VB2_1,VB2_2");
		sb.append(",T.ESN_1,T.ESN_2");
		sb.append(" FROM A_DFD_HEAD H,A_DFD_A01IAEV25_LIST T,A_DFD_A01IAEV25_COMPUTE T1"); 
		sb.append("  WHERE H.MSG_NO = T.MSG_NO AND T.MSG_NO = T1.MSG_NO AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY T.MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
	
	//获取A01 iae的换发点
	private  HashVO[] getEngineA01ListChangedVos(String begin_date,String end_date,String acNum,String leftOrRight) throws Exception{
		String leftOrRightStr = "";
		if(leftOrRight=="left"){
			leftOrRightStr = " ISCHANGEPOINT1 =1";
		}else if(leftOrRight=="right"){
			leftOrRightStr = " ISCHANGEPOINT2 =1";
		}
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE, T.MSG_NO");
		sb.append(",TAT"); 
		sb.append(",EGT_1, EGT_2, N1_1, N1_2, N2_1, N2_2, FF_1, FF_2, ACC_1, ACC_2");
		sb.append(",P125_1/P2_1 P125P2_1, P125_2/P2_2 P125P2_2, P25_1/P2_1 P25P2_1");
		sb.append(",P25_2/P2_2 P25P2_2, P3_1/P25_1 P3P25_1, P3_2/P25_2 P3P25_2");
		sb.append(",P3_1/P49_1 P49P3_1, P3_2/P49_2 P49P3_2, T2_1, T2_2, T3_1, T3_2");
		sb.append(",PHA_1, PHA_2, P2_1, P2_2, T2_1-TAT T2TAT_1, T2_2-TAT T2TAT_2");
		sb.append(",SVA_1, SVA_2, BAF_1, BAF_2");
		sb.append(",EGT_MARGIN_1, EGT_MARGIN_2, DIV_EGT_MARGIN1, DIV_EGT_MARGIN2");
		sb.append(", DIV_EGT_1,DIV_EGT_2, DIV_N1_1, DIV_N1_2, DIV_N2_1, DIV_N2_2, DIV_P2_1, DIV_P2_2");
		sb.append(",DIV_FF_1, DIV_FF_2, DIV_SVA_1, DIV_SVA_2, DIV_BAF_1, DIV_BAF_2,OIP_1, OIP_2 ");
		sb.append(",OIT_1, OIT_2,OIQH_1,OIQH_2, DIV_OIP_1, DIV_OIP_2, DIV_OIT_1, DIV_OIT_2, DIV_OIQH_1");
		sb.append(",DIV_OIQH_2, DIV_P125P2_1, DIV_P125P2_2, DIV_P25P2_1, DIV_P25P2_2, DIV_P3P25_1");
		sb.append(",DIV_P3P25_2, DIV_P49P3_1, DIV_P49P3_2");
		sb.append(" FROM A_DFD_HEAD H, A_DFD_A01IAEV25_LIST T,A_DFD_A01IAEV25_COMPUTE T2"); 
		sb.append(" WHERE H.MSG_NO = T.MSG_NO AND T.MSG_NO = T2.MSG_NO");
	    sb.append(" AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND "+leftOrRightStr);

	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY T2.MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
	
	//拿到A01 计算字段、飘点信息
	private HashVO[] getEngineA01ComputedVos (String begin_date,String end_date,String acNum,String param) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,T.RPTDATE UTCDATE");
		sb.append(",EGT_MARGIN_1, EGT_MARGIN_2, DIV_EGT_MARGIN1, DIV_EGT_MARGIN2, DIV_EGT_1");
		sb.append(",DIV_EGT_2, DIV_N1_1, DIV_N1_2, DIV_N2_1, DIV_N2_2, DIV_P2_1, DIV_P2_2, DIV_T2_1, DIV_T2_2");
		sb.append(",DIV_FF_1, DIV_FF_2, DIV_SVA_1, DIV_SVA_2, DIV_BAF_1, DIV_BAF_2,OIP_1, OIP_2 ");
		sb.append(",OIT_1, OIT_2,OIQH_1,OIQH_2, DIV_OIP_1, DIV_OIP_2, DIV_OIT_1, DIV_OIT_2, DIV_OIQH_1");
		sb.append(",DIV_OIQH_2, DIV_P125P2_1, DIV_P125P2_2, DIV_P25P2_1, DIV_P25P2_2, DIV_P3P25_1");
		sb.append(",DIV_P3P25_2, DIV_P49P3_1, DIV_P49P3_2");
		sb.append(",V_POINTTYPE,F_VALUE_ROLL5 ROLL5");
		sb.append(",T.ESN_1,T.ESN_2");
	    sb.append(" FROM A_DFD_A01IAEV25_LIST T,A_DFD_A01IAEV25_COMPUTE T1, A_DFD_FIELD_COMPUTE T2");
	    sb.append(" WHERE T.MSG_NO=T1.MSG_NO AND T1.MSG_NO=T2.MSG_NO");
	    sb.append(" AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T1.ACNUM='"+acNum+"'" + "AND T2.F_NAME='"+param+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	//拿到A04 基本字段信息

	private  HashVO[] getEngineA04ListVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE, T.MSG_NO");
		sb.append(",H.TAT"); 
		sb.append(",EGT_1, EGT_2, N1_1, N1_2, N2_1, N2_2, FF_1, FF_2, PSB_1, PSB_2");
		sb.append(",trunc(P3_1/P49_1,4) P49P3_1, trunc(P3_2/P49_2,4) P49P3_2, T2_1, T2_2, T3_1, T3_2");
		sb.append(", ENG_BTO_1, ENG_BO_1, EGT_CO_1, EGT_CAL_1, EGT_MARGIN_1, T3_CO_1, T3_CAL_1, EGT_ACT_1");
		sb.append(", ENG_BTO_2, ENG_BO_2, EGT_CO_2, EGT_CAL_2, EGT_MARGIN_2, T3_CO_2, T3_CAL_2, EGT_ACT_2");
		sb.append(", DELTA_EGT_1, DELTA_FF_1, DELTA_N1_1, DELTA_N2_1, DELTA_T3_1");
		sb.append(", DELTA_EGT_2, DELTA_FF_2, DELTA_N1_2, DELTA_N2_2, DELTA_T3_2");
		sb.append(",DIV_T2_1,DIV_T2_2,DIV_P49P3_1,DIV_P49P3_2,DIV_EGT_MARGIN1,DIV_EGT_MARGIN2");
		sb.append(",PHA_1, PHA_2, P2_1, P2_2, T2_1-H.TAT T2TAT_1, T2_2-H.TAT T2TAT_2");
		sb.append(",T.ESN_1,T.ESN_2,TLA_1,TLA_2,VB1_1,VB1_2,VB2_1,VB2_2");
		sb.append(",T1.EGT_MARGIN_1, T1.EGT_MARGIN_2");
		sb.append(" FROM A_DFD_HEAD H,A_DFD_A04IAEV25_LIST T,A_DFD_A04IAEV25_COMPUTE T1 "); 
	    sb.append(" WHERE  H.MSG_NO = T.MSG_NO AND T.MSG_NO = T1.MSG_NO AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
	
	//获取A04 iae的换发点
	private  HashVO[] getEngineA04ListChangedVos(String begin_date,String end_date,String acNum,String leftOrRight) throws Exception{
		String leftOrRightStr = "";
		if(leftOrRight=="left"){
			leftOrRightStr = " ISCHANGEPOINT1 =1";
		}else if(leftOrRight=="right"){
			leftOrRightStr = " ISCHANGEPOINT2 =1";
		}
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE, T.MSG_NO");
		sb.append(",H.TAT"); 
		sb.append(",EGT_1, EGT_2, N1_1, N1_2, N2_1, N2_2, FF_1, FF_2");
		sb.append(",P3_1/P49_1 P49P3_1, P3_2/P49_2 P49P3_2, T2_1, T2_2, T3_1, T3_2");
		sb.append(",PHA_1, PHA_2,TLA_1, TLA_2, P2_1, P2_2, T2_1-H.TAT T2TAT_1, T2_2-H.TAT T2TAT_2");
		sb.append(", ENG_BTO_1, ENG_BO_1, EGT_CO_1, EGT_CAL_1, EGT_MARGIN_1, T3_CO_1, T3_CAL_1, EGT_ACT_1");
		sb.append(", ENG_BTO_2, ENG_BO_2, EGT_CO_2, EGT_CAL_2, EGT_MARGIN_2, T3_CO_2, T3_CAL_2, EGT_ACT_2");
		sb.append(", DELTA_EGT_1, DELTA_FF_1, DELTA_N1_1, DELTA_N2_1, DELTA_T3_1");
		sb.append(", DELTA_EGT_2, DELTA_FF_2, DELTA_N1_2, DELTA_N2_2, DELTA_T3_2");
		sb.append(", DIV_EGT_MARGIN1, DIV_EGT_MARGIN2, DIV_EGT_1");
		sb.append(",DIV_EGT_2, DIV_N1_1, DIV_N1_2, DIV_N2_1, DIV_N2_2");
		sb.append(",DIV_FF_1, DIV_FF_2, OIP_1, OIP_2 ");
		sb.append(",OIT_1, OIT_2, DIV_OIP_1, DIV_OIP_2, DIV_OIT_1, DIV_OIT_2");
		sb.append(", DIV_P49P3_1, DIV_P49P3_2");
		sb.append(" FROM A_DFD_HEAD H, A_DFD_A04IAEV25_LIST T,A_DFD_A04IAEV25_COMPUTE T2"); 
		sb.append(" WHERE H.MSG_NO = T.MSG_NO AND T.MSG_NO = T2.MSG_NO ");
	    sb.append(" AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND "+leftOrRightStr);
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY T.MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
	
	//拿到A04 计算字段、飘点信息
	private HashVO[] getEngineA04ComputedVos (String begin_date,String end_date,String acNum,String param) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,T.RPTDATE UTCDATE");
		sb.append(",EGT_MARGIN_1, EGT_MARGIN_2, DIV_EGT_MARGIN1, DIV_EGT_MARGIN2, DIV_EGT_1");
		sb.append(",DIV_EGT_2, DIV_N1_1, DIV_N1_2, DIV_N2_1, DIV_N2_2");
		sb.append(",DIV_FF_1, DIV_FF_2, OIP_1, OIP_2, DIV_T2_1, DIV_T2_2");
		sb.append(",OIT_1, OIT_2, DIV_OIP_1, DIV_OIP_2, DIV_OIT_1, DIV_OIT_2");
		sb.append(", DIV_P49P3_1, DIV_P49P3_2");
		sb.append(",V_POINTTYPE,F_VALUE_ROLL5 ROLL5");
		sb.append(",T.ESN_1,T.ESN_2");
	    sb.append(" FROM A_DFD_A04IAEV25_LIST T,A_DFD_A04IAEV25_COMPUTE T1, A_DFD_FIELD_COMPUTE T2");
	    sb.append(" WHERE T.MSG_NO=T1.MSG_NO AND T1.MSG_NO=T2.MSG_NO");
	    sb.append(" AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T1.ACNUM='"+acNum+"'" + "AND T2.F_NAME='"+param+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	//滑油27号报文相关
	private HashVO[] getComputedVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,DATE_UTC UTCDATE");
		sb.append(",T.ESN_1,T.OIQ1_Z3,T.CF_EHRS,T.DETA_OIQ1_FWD,T.DETA_OIQ1_FWD_S,T.DETA_OIQ1_FWDALARM,T.IS_OIQ1_ADDRPT,T.DETA_OIQ1_FWDRATE");
		sb.append(",T.ESN_2,T.OIQ2_Z3,T.CG_EHRS,T.DETA_OIQ2_FWD,T.DETA_OIQ2_FWD_S,T.DETA_OIQ2_FWDALARM,T.IS_OIQ2_ADDRPT,T.DETA_OIQ2_FWDRATE");
		
		//复杂计算值查询
		sb.append(",T1.F_VALUE_ROLL5 OIQ1_RATE_ROLL5");//左发滑油消耗率5点均
		sb.append(",T2.F_VALUE_ROLL5 OIQ2_RATE_ROLL5");//发滑油消耗率5点均
		sb.append(",T1.V_POINTTYPE OIQ1_RATE_POINTTYPE");//左发 飘点
		sb.append(",T2.V_POINTTYPE OIQ2_RATE_POINTTYPE");//右发 飘点
		
	    sb.append(" FROM A_DFD_A25_COMPUTE T");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T2");
	    sb.append(" WHERE DATE_UTC>=? AND DATE_UTC<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='DETA_OIQ1_FWDRATE'");
	    sb.append(" AND T2.MSG_NO =T.MSG_NO AND T2.F_NAME='DETA_OIQ2_FWDRATE'");

	    
	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	//滑油消耗率空中查询
	private HashVO[] getA27ComputedVos1(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,DATE_UTC UTCDATE");
		sb.append(",T.ESN_1,T.OILADD_ENG1,T.CAL_GA_ENG1,T.CAL_AIR_ENG1");
		
		//复杂计算值查询
		sb.append(",T1.F_VALUE_ROLL5 GA_ENG1_ROLL5");//左发滑油消耗率5点均  地面加空中
		sb.append(",T2.F_VALUE_ROLL5 AIR_ENG1_ROLL5");//右发滑油消耗率5点均  空中
		sb.append(",T1.V_POINTTYPE GA_ENG1_POINTTYPE");//左发飘点
		sb.append(",T2.V_POINTTYPE AIR_ENG1_POINTTYPE");//右发飘点
		
	    sb.append(" FROM A_DFD_A27_COMPUTE T");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T2");
	    sb.append(" WHERE DATE_UTC>=? AND DATE_UTC<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='CAL_GA_ENG1'");
	    sb.append(" AND T2.MSG_NO =T.MSG_NO AND T2.F_NAME='CAL_AIR_ENG1'");
	    
	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	//滑油消耗率前后查询
	private HashVO[] getA27ComputedVos2(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,DATE_UTC UTCDATE");
		sb.append(",T.ESN_2,T.OILADD_ENG2,T.CAL_GA_ENG2,T.CAL_AIR_ENG2");
		
		//复杂计算值查询
		sb.append(",T1.F_VALUE_ROLL5 GA_ENG2_ROLL5");//发滑油消耗率5点均 地面加空中
		sb.append(",T2.F_VALUE_ROLL5 AIR_ENG2_ROLL5");//右发滑油消耗率5点均  空中
		sb.append(",T1.V_POINTTYPE GA_ENG2_POINTTYPE");//左发飘点
		sb.append(",T2.V_POINTTYPE AIR_ENG2_POINTTYPE");//右发飘点
		
	    sb.append(" FROM A_DFD_A27_COMPUTE T");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" ,A_DFD_FIELD_COMPUTE T2");
	    sb.append(" WHERE DATE_UTC>=? AND DATE_UTC<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='CAL_GA_ENG2'");
	    sb.append(" AND T2.MSG_NO =T.MSG_NO AND T2.F_NAME='CAL_AIR_ENG2'");
	    
	    sb.append("");
	    sb.append("");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
}
