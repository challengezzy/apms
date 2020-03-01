package com.apms.bs.engine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.common.HashVoUtil;
import com.apms.bs.dfd.DfdVarConst;
import com.apms.bs.oxygen.vo.A_DFD_RankPoint;
import com.apms.bs.sysconfig.ApmsSysParamConfService;
import com.apms.bs.sysconfig.EngineConfVarVo;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.MathUtil;
import com.apms.vo.SysParamConfVo;
public class CfmEngineChartDetail {
	protected  Logger logger = NovaLogger.getLogger(this.getClass());
	
	private  CommDMO dmo = new CommDMO(); 
	
	private SysParamConfVo sysVar;
	private EngineConfVarVo engineVar;
	private double cfm_a01_egt_base;
	private double cfm_a01_ff_base;
	private double cfm_a01_n2_base;

	private double cfm_a04_egt_base;
	private double cfm_a04_ff_base;
	private double cfm_a04_n2_base;

	private EngineCfdService cfdService = new EngineCfdService();

	public CfmEngineChartDetail() throws Exception{
		sysVar = ApmsSysParamConfService.getInstance().getConfVo();
		
		engineVar = sysVar.getEngineVo();
		cfm_a01_egt_base = engineVar.getCfm_a01_egt_base();
		cfm_a01_ff_base = engineVar.getCfm_a01_ff_base();
		cfm_a01_n2_base = engineVar.getCfm_a01_n2_base();
		
		cfm_a04_egt_base = engineVar.getCfm_a04_egt_base();
		cfm_a04_ff_base = engineVar.getCfm_a04_ff_base();
		cfm_a04_n2_base = engineVar.getCfm_a04_n2_base();

	}
	//拿到cfm 巡航页面上所需数据
	public  Map<String, Object> getEngineA01ChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询发动机报文(A01)数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		//查询A01所有的左右换发点
	    List<Map<String,Object>> allA01LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA01ChangedListVos(begin_date, end_date, acNum,"left");
	    allA01LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA01LeftChangedList", allA01LeftChangedList);
	   
	    List<Map<String,Object>> allA01RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA01ChangedListVos(begin_date, end_date, acNum,"right");
	    allA01RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA01RightChangedList", allA01RightChangedList);
	    
	    //查询 A01数据对象
		List<Map<String,Object>> allA01List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA01List = getA01ListVos(begin_date, end_date, acNum);
		allA01List.addAll(HashVoUtil.hashVosToMapList(vosA01List));
		resMap.put("allA01List", allA01List);
		
	    //egt margin取04报文中的
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
	    
		allDiv ="DELTA_EGTM_1,DELTA_EGTM_2,DELTA_N2_1,DELTA_N2_2,DELTA_FF_1,DELTA_FF_2,DELTA_T3_1,DELTA_T3_2";
		alldiv = allDiv.split(",");
		for(int i=0;i<alldiv.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA04List = getEngineA01ComputedVos(begin_date, end_date, acNum,alldiv[i]);
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA04List));
			String key = "allA01Compu_"+alldiv[i].toLowerCase();
		    resMap.put(key,temp) ;
		}
		
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
		
		if(isRank && vosA01List.length > 2){
			//斜率点集合
			List<A_DFD_RankPoint> n2Vsv_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> n2Vsv_2RankList = new ArrayList<A_DFD_RankPoint>();
			
			List<A_DFD_RankPoint> n2_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> n2_2RankList = new ArrayList<A_DFD_RankPoint>();
			
			List<A_DFD_RankPoint> egt_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> egt_2RankList = new ArrayList<A_DFD_RankPoint>();
			
			List<A_DFD_RankPoint> egtMargin_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> egtMargin_2RankList = new ArrayList<A_DFD_RankPoint>();

			List<A_DFD_RankPoint> ff_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> ff_2RankList = new ArrayList<A_DFD_RankPoint>();
			//计算数据数组
			List<Double> xarr1 = new ArrayList<Double>(vosA01List.length);
			List<Double> yarr1 = new ArrayList<Double>(vosA01List.length);
			List<Double> xarr2 = new ArrayList<Double>(vosA01List.length);
			List<Double> yarr2 = new ArrayList<Double>(vosA01List.length);

			List<Double> xarr_time = new ArrayList<Double>(vosA01List.length);
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
				xarr1.add(vo.getDoubleValue("VSV_1"));//
				yarr1.add(vo.getDoubleValue("N2_1"));
				xarr2.add(vo.getDoubleValue("VSV_2"));//
				yarr2.add(vo.getDoubleValue("N2_2"));
				
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
			
			//回归计算
			List<A_DFD_RankPoint> tmpRank_n2vsv_1 = MathUtil.computeRankList(xarr1,yarr1);
			n2Vsv_1RankList.addAll(tmpRank_n2vsv_1);
		
			List<A_DFD_RankPoint> tmpRank_n2vsv_2 = MathUtil.computeRankList(xarr2,yarr2);
			n2Vsv_2RankList.addAll(tmpRank_n2vsv_2);
		
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
			
			resMap.put("n2Vsv_1RankList",n2Vsv_1RankList );
			resMap.put("n2Vsv_2RankList",n2Vsv_2RankList );
			resMap.put("n2_1RankList",n2_2RankList);
			resMap.put("n2_2RankList",n2_2RankList);
			resMap.put("egt_1RankList", egt_1RankList);
			resMap.put("egt_2RankList", egt_2RankList);
			resMap.put("ff_1RankList", ff_1RankList);
			resMap.put("ff_2RankList", ff_2RankList);
			resMap.put("egtMargin_1RankList", egtMargin_1RankList);
			resMap.put("egtMargin_2RankList", egtMargin_2RankList);
		}	    
		
		return resMap;
	}
	
	
	//拿到cfm 起飞页面上所需数据
	public  Map<String, Object> getEngineA04ChartData(String begin_date,String end_date,String acNum,boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		logger.debug("开始查询发动机报文(A04)数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		//查询A04所有的左右换发点
	    List<Map<String,Object>> allA04LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList =getEngineA04ChangedListVos(begin_date, end_date, acNum,"left");
	    allA04LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA04LeftChangedList", allA04LeftChangedList);
	   
	    List<Map<String,Object>> allA04RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA01ChangedListVos(begin_date, end_date, acNum,"right");
	    allA04RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA04RightChangedList", allA04RightChangedList);
	    
		//查询 A04数据对象
		List<Map<String,Object>> allA04List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA04List = getA04ListVos(begin_date, end_date, acNum);
		allA04List.addAll(HashVoUtil.hashVosToMapList(vosA04List));
		resMap.put("allA04List", allA04List);
		
		//egt margin取04报文中的
		String allDiv ="EGT_MARGIN_1,EGT_MARGIN_2,DELTA_EGTM_1,DELTA_EGTM_2,DELTA_N2_1,DELTA_N2_2,DELTA_FF_1,DELTA_FF_2,DELTA_T3_1,DELTA_T3_2";
		String[] alldiv = allDiv.split(",");
		List<Map<String,Object>> temp;
		for(int i=0;i<alldiv.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA04List = getEngineA04ComputedVos(begin_date, end_date, acNum,alldiv[i]);
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA04List));
			String key = "allA04Compu_"+alldiv[i].toLowerCase();
		    resMap.put(key,temp) ;
		}
		
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
		
		if(isRank && vosA04List.length > 2){
			//斜率点集合
			List<A_DFD_RankPoint> n2Vsv_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> n2Vsv_2RankList = new ArrayList<A_DFD_RankPoint>();
			
			List<A_DFD_RankPoint> n2_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> n2_2RankList = new ArrayList<A_DFD_RankPoint>();
			
			List<A_DFD_RankPoint> egt_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> egt_2RankList = new ArrayList<A_DFD_RankPoint>();
			
			List<A_DFD_RankPoint> egtMargin_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> egtMargin_2RankList = new ArrayList<A_DFD_RankPoint>();

			List<A_DFD_RankPoint> ff_1RankList = new ArrayList<A_DFD_RankPoint>();
			List<A_DFD_RankPoint> ff_2RankList = new ArrayList<A_DFD_RankPoint>();
			//计算数据数组
			List<Double> xarr1 = new ArrayList<Double>(vosA04List.length);
			List<Double> yarr1 = new ArrayList<Double>(vosA04List.length);
			List<Double> xarr2 = new ArrayList<Double>(vosA04List.length);
			List<Double> yarr2 = new ArrayList<Double>(vosA04List.length);

			List<Double> xarr_time = new ArrayList<Double>(vosA04List.length);
			List<Double> n2yarr2 = new ArrayList<Double>(vosA04List.length);
			List<Double> n2yarr1 = new ArrayList<Double>(vosA04List.length);
			
			List<Double> egtyarr2 = new ArrayList<Double>(vosA04List.length);
			List<Double> egtyarr1 = new ArrayList<Double>(vosA04List.length);
			
			List<Double> egtMarginyarr1 = new ArrayList<Double>(vosA04List.length);
			List<Double> egtMarginyarr2 = new ArrayList<Double>(vosA04List.length);
			
			List<Double> ffyarr1 = new ArrayList<Double>(vosA04List.length);
			List<Double> ffyarr2 = new ArrayList<Double>(vosA04List.length);
			
			for (int i = 0; i < vosA04List.length; i++) {
				HashVO vo = vosA04List[i];
				xarr1.add(vo.getDoubleValue("VSV_1"));//
				yarr1.add(vo.getDoubleValue("N2_1"));
				xarr2.add(vo.getDoubleValue("VSV_2"));//
				yarr2.add(vo.getDoubleValue("N2_2"));
				
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
			
			//回归计算
			List<A_DFD_RankPoint> tmpRank_n2vsv_1 = MathUtil.computeRankList(xarr1,yarr1);
			n2Vsv_1RankList.addAll(tmpRank_n2vsv_1);
		
			List<A_DFD_RankPoint> tmpRank_n2vsv_2 = MathUtil.computeRankList(xarr2,yarr2);
			n2Vsv_2RankList.addAll(tmpRank_n2vsv_2);
		
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
			
			resMap.put("n2Vsv_1RankList",n2Vsv_1RankList );
			resMap.put("n2Vsv_2RankList",n2Vsv_2RankList );
			resMap.put("n2_1RankList",n2_2RankList);
			resMap.put("n2_2RankList",n2_2RankList);
			resMap.put("egt_1RankList", egt_1RankList);
			resMap.put("egt_2RankList", egt_2RankList);
			resMap.put("ff_1RankList", ff_1RankList);
			resMap.put("ff_2RankList", ff_2RankList);
			resMap.put("egt_margin1RankList", egtMargin_1RankList);
			resMap.put("egt_margin2RankList", egtMargin_2RankList);
		}
		
		
		return resMap;

	}
	
	
	//拿到cfm 滑油页面上所需数据
	public  Map<String, Object> getCfmEngineSildeOilChartData(String begin_date,String end_date,String acNum) throws Exception
	{
		logger.debug("开始查询发动机滑油报文数据，查询条件ACNUM="+acNum+",BeginDate="+begin_date+", EndDate= "+end_date);
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		 //查询A01所有的左右换发点
	    List<Map<String,Object>> allA01LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA01ChangedListVos(begin_date, end_date, acNum,"left");
	    allA01LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA01LeftChangedList", allA01LeftChangedList);
	   
	    List<Map<String,Object>> allA01RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA01ChangedListVos(begin_date, end_date, acNum,"right");
	    allA01RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA01RightChangedList", allA01RightChangedList);
	    
	  //查询A04所有的左右换发点
	    List<Map<String,Object>> allA04LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList2 =getEngineA04ChangedListVos(begin_date, end_date, acNum,"left");
	    allA04LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList2));
	    resMap.put("allA04LeftChangedList", allA04LeftChangedList);
	   
	    List<Map<String,Object>> allA04RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList2 = getEngineA01ChangedListVos(begin_date, end_date, acNum,"right");
	    allA04RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList2));
	    resMap.put("allA04RightChangedList", allA04RightChangedList);
	    
		String allDiv ="OIP_1,OIP_2,OIT_1,OIT_2";
		String[] alldiv = allDiv.split(",");
		List<Map<String,Object>> temp;
		for(int i=0;i<alldiv.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA04List = getEngineA04ComputedVos(begin_date, end_date, acNum,alldiv[i]);
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA04List));
			String key = "allA04Compu_"+alldiv[i].toLowerCase();
		    resMap.put(key,temp) ;
		}
	    
		allDiv ="OIP_1,OIP_2,OIT_1,OIT_2";
		alldiv = allDiv.split(",");
		for(int i=0;i<alldiv.length;i++){
			temp =new ArrayList<Map<String,Object>>();
			HashVO[] CvosA04List = getEngineA01ComputedVos(begin_date, end_date, acNum,alldiv[i]);
			temp.addAll(HashVoUtil.hashVosToMapList(CvosA04List));
			String key = "allA01Compu_"+alldiv[i].toLowerCase();
		    resMap.put(key,temp) ;
		}
		
		 //查询 A01数据对象
		List<Map<String,Object>> allA01List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA01List = getA01ListVos(begin_date, end_date, acNum);
		allA01List.addAll(HashVoUtil.hashVosToMapList(vosA01List));
		resMap.put("allA01List", allA01List);
		
		//查询 A04数据对象
		List<Map<String,Object>> allA04List = new ArrayList<Map<String,Object>>();
		HashVO[] vosA04List = getA04ListVos(begin_date, end_date, acNum);
		allA04List.addAll(HashVoUtil.hashVosToMapList(vosA04List));
		resMap.put("allA04List", allA04List);
		
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
	
	
	//拿到cfm 振动值页面上所需数据
	public  Map<String, Object> getCfmVibrationChartData(String begin_date,String end_date,String acNum, boolean isRank) throws Exception
	{
		Map<String, Object> resMap = new HashMap<String, Object>(64);
		List<Map<String,Object>> allA01All_vibration = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA01_vibration = getA01CfmVibrationVos(begin_date, end_date, acNum);
	    allA01All_vibration.addAll(HashVoUtil.hashVosToMapList(vosA01_vibration));
	    resMap.put("allA01All_vibration", allA01All_vibration);
	    
		List<Map<String,Object>> allA04All_vibration = new ArrayList<Map<String,Object>>();
	    HashVO[] vosA04_vibration = getA04CfmVibrationVos(begin_date, end_date, acNum);
	    allA04All_vibration.addAll(HashVoUtil.hashVosToMapList(vosA04_vibration));
	    resMap.put("allA04All_vibration", allA04All_vibration);
	    
	    //查询A01所有的左右换发点
	    List<Map<String,Object>> allA01LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList = getEngineA01ChangedListVos(begin_date, end_date, acNum,"left");
	    allA01LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList));
	    resMap.put("allA01LeftChangedList", allA01LeftChangedList);
	   
	    List<Map<String,Object>> allA01RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList = getEngineA01ChangedListVos(begin_date, end_date, acNum,"right");
	    allA01RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList));
	    resMap.put("allA01RightChangedList", allA01RightChangedList);
	    
	    //查询A04所有的左右换发点
	    List<Map<String,Object>> allA04LeftChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] leftChangedList2 =getEngineA04ChangedListVos(begin_date, end_date, acNum,"left");
	    allA04LeftChangedList.addAll(HashVoUtil.hashVosToMapList(leftChangedList2));
	    resMap.put("allA04LeftChangedList", allA04LeftChangedList);
	   
	    List<Map<String,Object>> allA04RightChangedList = new ArrayList<Map<String,Object>>();
	    HashVO[] rightChangedList2 = getEngineA01ChangedListVos(begin_date, end_date, acNum,"right");
	    allA04RightChangedList.addAll(HashVoUtil.hashVosToMapList(rightChangedList2));
	    resMap.put("allA04RightChangedList", allA04RightChangedList);
	    
	    //斜率点集合
		List<A_DFD_RankPoint> vn_1A01RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vn_2A01RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vl_1A01RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vl_2A01RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vc_1A01RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vc_2A01RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vh_1A01RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vh_2A01RankList = new ArrayList<A_DFD_RankPoint>();
		
		List<A_DFD_RankPoint> vn_1A04RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vn_2A04RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vl_1A04RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vl_2A04RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vc_1A04RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vc_2A04RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vh_1A04RankList = new ArrayList<A_DFD_RankPoint>();
		List<A_DFD_RankPoint> vh_2A04RankList = new ArrayList<A_DFD_RankPoint>();
		
		
		//计算数据数组
		List<Double> xarr_a01 = new ArrayList<Double>(vosA01_vibration.length);
	    List<Double> yarr_vn_1_a01 = new ArrayList<Double>(vosA01_vibration.length);
	    List<Double> yarr_vn_2_a01 = new ArrayList<Double>(vosA01_vibration.length);
	    
	    List<Double> yarr_vl_1_a01 = new ArrayList<Double>(vosA01_vibration.length);
	    List<Double> yarr_vl_2_a01 = new ArrayList<Double>(vosA01_vibration.length);
	    
	    List<Double> yarr_vc_1_a01 = new ArrayList<Double>(vosA01_vibration.length);
	    List<Double> yarr_vc_2_a01 = new ArrayList<Double>(vosA01_vibration.length);
	    
	    List<Double> yarr_vh_1_a01 = new ArrayList<Double>(vosA01_vibration.length);
	    List<Double> yarr_vh_2_a01 = new ArrayList<Double>(vosA01_vibration.length);
	    
	    
	    List<Double> xarr_a04 = new ArrayList<Double>(vosA01_vibration.length);
	    List<Double> yarr_vn_1_a04 = new ArrayList<Double>(vosA04_vibration.length);
	    List<Double> yarr_vn_2_a04 = new ArrayList<Double>(vosA04_vibration.length);
	    
	    List<Double> yarr_vl_1_a04 = new ArrayList<Double>(vosA04_vibration.length);
	    List<Double> yarr_vl_2_a04 = new ArrayList<Double>(vosA04_vibration.length);
	    
	    List<Double> yarr_vc_1_a04 = new ArrayList<Double>(vosA04_vibration.length);
	    List<Double> yarr_vc_2_a04 = new ArrayList<Double>(vosA04_vibration.length);
	    
	    List<Double> yarr_vh_1_a04 = new ArrayList<Double>(vosA04_vibration.length);
	    List<Double> yarr_vh_2_a04 = new ArrayList<Double>(vosA04_vibration.length);
	    
		for (int i = 0; i < vosA01_vibration.length; i++) {
			HashVO vo = vosA01_vibration[i];
			xarr_a01.add(new Double(vo.getDateValue("UTCDATE").getTime() + ""));//
			yarr_vn_1_a01.add(vo.getDoubleValue("VN_1"));
			yarr_vn_2_a01.add(vo.getDoubleValue("VN_2"));

			yarr_vc_1_a01.add(vo.getDoubleValue("VC_1"));
			yarr_vc_2_a01.add(vo.getDoubleValue("VC_2"));

			yarr_vl_1_a01.add(vo.getDoubleValue("VL_1"));
			yarr_vl_2_a01.add(vo.getDoubleValue("VL_2"));

			yarr_vh_1_a01.add(vo.getDoubleValue("VH_1"));
			yarr_vh_2_a01.add(vo.getDoubleValue("VH_2"));

		}
	    
		for (int i = 0; i < vosA04_vibration.length; i++) {
			HashVO vo = vosA04_vibration[i];
			xarr_a04.add(new Double(vo.getDateValue("UTCDATE").getTime() + ""));
			yarr_vn_1_a04.add(vo.getDoubleValue("VN_1"));
			yarr_vn_2_a04.add(vo.getDoubleValue("VN_2"));

			yarr_vc_1_a04.add(vo.getDoubleValue("VC_1"));
			yarr_vc_2_a04.add(vo.getDoubleValue("VC_2"));

			yarr_vl_1_a04.add(vo.getDoubleValue("VL_1"));
			yarr_vl_2_a04.add(vo.getDoubleValue("VL_2"));

			yarr_vh_1_a04.add(vo.getDoubleValue("VH_1"));
			yarr_vh_2_a04.add(vo.getDoubleValue("VH_2"));
		}

		if (isRank && xarr_a01.size() > 2) {
			// 回归计算
			List<A_DFD_RankPoint> tmpRank_vn1 = MathUtil.computeRankList(xarr_a01, yarr_vn_1_a01);
			vn_1A01RankList.addAll(tmpRank_vn1);
			List<A_DFD_RankPoint> tmpRank_vn2 = MathUtil.computeRankList(xarr_a01, yarr_vn_2_a01);
			vn_2A01RankList.addAll(tmpRank_vn2);
			
			List<A_DFD_RankPoint> tmpRank_vc1 = MathUtil.computeRankList(xarr_a01, yarr_vc_1_a01);
			vc_1A01RankList.addAll(tmpRank_vc1);
			List<A_DFD_RankPoint> tmpRank_vc2 = MathUtil.computeRankList(xarr_a01, yarr_vc_2_a01);
			vc_2A01RankList.addAll(tmpRank_vc2);
			
			List<A_DFD_RankPoint> tmpRank_vl1 = MathUtil.computeRankList(xarr_a01, yarr_vl_1_a01);
			vl_1A01RankList.addAll(tmpRank_vl1);
			List<A_DFD_RankPoint> tmpRank_vl2 = MathUtil.computeRankList(xarr_a01, yarr_vl_2_a01);
			vl_2A01RankList.addAll(tmpRank_vl2);
			
			List<A_DFD_RankPoint> tmpRank_vh1 = MathUtil.computeRankList(xarr_a01, yarr_vh_1_a01);
			vh_1A01RankList.addAll(tmpRank_vh1);
			List<A_DFD_RankPoint> tmpRank_vh2 = MathUtil.computeRankList(xarr_a01, yarr_vh_2_a01);
			vh_2A01RankList.addAll(tmpRank_vh2);
		}
		
		if (isRank && xarr_a04.size() > 2) {
			// 回归计算
			List<A_DFD_RankPoint> tmpRank_vn1 = MathUtil.computeRankList(xarr_a04, yarr_vn_1_a04);
			vn_1A04RankList.addAll(tmpRank_vn1);
			List<A_DFD_RankPoint> tmpRank_vn2 = MathUtil.computeRankList(xarr_a04, yarr_vn_2_a04);
			vn_2A04RankList.addAll(tmpRank_vn2);
			
			List<A_DFD_RankPoint> tmpRank_vc1 = MathUtil.computeRankList(xarr_a04, yarr_vc_1_a04);
			vc_1A04RankList.addAll(tmpRank_vc1);
			List<A_DFD_RankPoint> tmpRank_vc2 = MathUtil.computeRankList(xarr_a04, yarr_vc_2_a04);
			vc_2A04RankList.addAll(tmpRank_vc2);
			
			List<A_DFD_RankPoint> tmpRank_vl1 = MathUtil.computeRankList(xarr_a04, yarr_vl_1_a04);
			vl_1A04RankList.addAll(tmpRank_vl1);
			List<A_DFD_RankPoint> tmpRank_vl2 = MathUtil.computeRankList(xarr_a04, yarr_vl_2_a04);
			vl_2A04RankList.addAll(tmpRank_vl2);
			
			List<A_DFD_RankPoint> tmpRank_vh1 = MathUtil.computeRankList(xarr_a04, yarr_vh_1_a04);
			vh_1A04RankList.addAll(tmpRank_vh1);
			List<A_DFD_RankPoint> tmpRank_vh2 = MathUtil.computeRankList(xarr_a04, yarr_vh_2_a04);
			vh_2A04RankList.addAll(tmpRank_vh2);
		}
		resMap.put("vn_1A01RankList",vn_1A01RankList );
		resMap.put("vn_2A01RankList",vn_2A01RankList );

		resMap.put("vc_1A01RankList",vc_1A01RankList );
		resMap.put("vc_2A01RankList",vc_2A01RankList );
		
		resMap.put("vl_1A01RankList",vl_1A01RankList );
		resMap.put("vl_2A01RankList",vl_2A01RankList );
		
		resMap.put("vh_1A01RankList",vh_1A01RankList );
		resMap.put("vh_2A01RankList",vh_2A01RankList );
		
		
		resMap.put("vn_1A04RankList",vn_1A04RankList );
		resMap.put("vn_2A04RankList",vn_2A04RankList );

		resMap.put("vc_1A04RankList",vc_1A04RankList );
		resMap.put("vc_2A04RankList",vc_2A04RankList );
		
		resMap.put("vl_1A04RankList",vl_1A04RankList );
		resMap.put("vl_2A04RankList",vl_2A04RankList );
		
		resMap.put("vh_1A04RankList",vh_1A04RankList );
		resMap.put("vh_2A04RankList",vh_2A04RankList );
		
	   
	    return resMap;
	}
	
	
	private  HashVO[] getA01ListVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE,T.MSG_NO");
		sb.append("");
		sb.append(",T.EGT_1,T.EGT_2,T.N2_1,T.N2_2,T.FF_1,T.FF_2,T.T3_1,T.T3_2");
		sb.append(",DELTA_EGTM_1,DELTA_EGTM_2,DELTA_N2_1,DELTA_N2_2,DELTA_FF_1,DELTA_FF_2,DELTA_T3_1,DELTA_T3_2");
		sb.append(",T1.EGT_MARGIN_1,T1.EGT_MARGIN_2");
		sb.append(",trunc(nvl(T.p3_1,0)/T.PT2_1,4) P3_PT2_1,trunc(nvl(T.p3_2,0)/T.PT2_2,4) P3_PT2_2");
		sb.append(",trunc(T.p3_1/T.PT2_1 - T.p3_2/T.PT2_2 ,4) DIV_P3PT2_1,trunc(T.p3_2/T.PT2_2 - T.p3_1/T.PT2_1, 4) DIV_P3PT2_2");
		sb.append(",trunc(decode(T.p25_1,null,0,0,0,T.p3_1/T.p25_1),4) P3_P25_1");
		sb.append(",trunc(decode(T.p25_2,null,0,0,0,T.p3_2/T.p25_2),4) P3_P25_2");
		sb.append(",DIV_P3P25_1,DIV_P3P25_2");
		sb.append(",DIV_OIP_1,DIV_OIP_2,DIV_OIT_1,DIV_OIT_2");
		sb.append(",T.VSV_1,T.VSV_2,T.VBV_1,T.VBV_2");
		sb.append(",T.HPT_1,T.HPT_2,T.LPT_1,T.LPT_2");
		sb.append(",T.ESN_1,T.ESN_2");
		sb.append(",(DELTA_EGTM_1-DELTA_EGTM_2)/2 DIV_EGTM_1,(DELTA_EGTM_2-DELTA_EGTM_1)/2 DIV_EGTM_2");
		sb.append(",(DELTA_FF_1-DELTA_FF_2)/2 DIV_FF_1,(DELTA_FF_2-DELTA_FF_1)/2 DIV_FF_2");
		sb.append(",(DELTA_N2_1-DELTA_N2_2)/2 DIV_N2_1,(DELTA_N2_2-DELTA_N2_1)/2 DIV_N2_2");
		sb.append(",(DELTA_T3_1-DELTA_T3_2)/2 DIV_T3_1,(DELTA_T3_2-DELTA_T3_1)/2 DIV_T3_2");
		sb.append(",(select count(1) from l_engine_traindata where msg_no = t.msg_no and position ='1') LEXIST");
		sb.append(",(select count(1) from l_engine_traindata where msg_no = t.msg_no and position ='2') REXIST");
		sb.append(",(select POSITION from l_engine_traindata where msg_no = t.msg_no and position ='1') LPOSITION");
		sb.append(",(select POSITION from l_engine_traindata where msg_no = t.msg_no and position ='2') RPOSITION");

		sb.append(",(select DATATYPE from l_engine_traindata where msg_no = t.msg_no and position ='1') LDATATYPE");
		sb.append(",(select DATATYPE from l_engine_traindata where msg_no = t.msg_no and position ='2') RDATATYPE");
		sb.append(",(select COMMENTS from l_engine_traindata where msg_no = t.msg_no and position ='1') LCOMMENTS");
		sb.append(",(select COMMENTS from l_engine_traindata where msg_no = t.msg_no and position ='2') RCOMMENTS");

		sb.append(" FROM A_DFD_A01CFM56_5B_LIST T,A_DFD_A01CFM56_COMPUTE T1 ");
		
	    sb.append(" WHERE T.MSG_NO = T1.MSG_NO AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	private HashVO[] getA04ListVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE,T.MSG_NO");
		sb.append(",T.EGT_1,T.EGT_2,T.N2_1,T.N2_2,T.FF_1,T.FF_2,T.T3_1,T.T3_2");
		sb.append(",DELTA_EGTM_1,DELTA_EGTM_2,DELTA_N2_1,DELTA_N2_2,DELTA_FF_1,DELTA_FF_2,DELTA_T3_1,DELTA_T3_2");
		sb.append(",T1.EGT_MARGIN_1,T1.EGT_MARGIN_2");
		sb.append(",T.VSV_1,T.VSV_2,T.VBV_1,T.VBV_2");
		sb.append(",T.HPT_1,T.HPT_2,T.LPT_1,T.LPT_2");
		sb.append(",DIV_OIP_1,DIV_OIP_2,DIV_OIT_1,DIV_OIT_2");
		sb.append(",T.ESN_1,T.ESN_2");
		sb.append(",(DELTA_EGTM_1-DELTA_EGTM_2)/2 DIV_EGTM_1,(DELTA_EGTM_2-DELTA_EGTM_1)/2 DIV_EGTM_2");
		sb.append(",(DELTA_FF_1-DELTA_FF_2)/2 DIV_FF_1,(DELTA_FF_2-DELTA_FF_1)/2 DIV_FF_2");
		sb.append(",(DELTA_N2_1-DELTA_N2_2)/2 DIV_N2_1,(DELTA_N2_2-DELTA_N2_1)/2 DIV_N2_2");
		sb.append(",(DELTA_T3_1-DELTA_T3_2)/2 DIV_T3_1,(DELTA_T3_2-DELTA_T3_1)/2 DIV_T3_2");
		
		sb.append(" FROM A_DFD_A04CFM56_5B_LIST T,A_DFD_A04CFM56_COMPUTE T1");
	    sb.append(" WHERE T.MSG_NO = T1.MSG_NO AND T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
	
	
	//获取A01 cfm的换发点
	private  HashVO[] getEngineA01ChangedListVos(String begin_date,String end_date,String acNum,String leftOrRight) throws Exception{
		String leftOrRightStr = "";
		if(leftOrRight=="left"){
			leftOrRightStr = " ISCHANGEPOINT1 =1";
		}else if(leftOrRight=="right"){
			leftOrRightStr = " ISCHANGEPOINT2 =1";
		}
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE,T.MSG_NO");
		sb.append(",T.EGT_1,T.EGT_2"); 
		sb.append(",T.N2_1,T.N2_2");
		sb.append(",T.FF_1,T.FF_2");
		sb.append(",T1.EGT_MARGIN_1,T1.EGT_MARGIN_2");
		sb.append(",nvl(T.p25_1,0)/T.PT2_1 P25_PT2_1,nvl(T.p25_2,0)/T.PT2_2 P25_PT2_2");
		sb.append(",T.T3_1,T.T3_2");
		sb.append(",decode(T.p25_1,null,0,0,0,T.p3_1/T.p25_1) P3_P25_1,decode(T.p25_2,null,0,0,0,T.p3_2/T.p25_2) P3_P25_2");
		sb.append(",T.VSV_1,T.VSV_2");
		
		sb.append(",T.HPT_1,T.HPT_2,T.LPT_1,T.LPT_2");
		sb.append(",T1.DIV_EGT_1,T1.DIV_EGT_2,T1.DIV_N2_1,T1.DIV_N2_2,T1.DIV_FF_1,T1.DIV_FF_2");
		sb.append(",T1.EGT_MARGIN_1,T1.EGT_MARGIN_2");
		sb.append(",T1.DIV_EGT_MARGIN1,T1.DIV_EGT_MARGIN2");
		sb.append(",T1.DIV_T3_1,T1.DIV_T3_2,T1.DIV_P25PT2_1,T1.DIV_P25PT2_2");
		sb.append(",T1.DIV_P3P25_1,T1.DIV_P3P25_2");
		sb.append(",T.OIP_1, T.OIP_2,T.OIT_1, T.OIT_2,T1.DIV_OIP_1, T1.DIV_OIP_2, T1.DIV_OIT_1, T1.DIV_OIT_2");
		sb.append(",T.VN_1,T.VN_2,T.VC_1,T.VC_2"); 
		sb.append(",T.VL_1,T.VL_2,T.VH_1,T.VH_2");
		sb.append(",T.PHA_1,T.PHA_2,T.PHT_1,T.PHT_2");
		sb.append(" FROM A_DFD_A01CFM56_5B_LIST T left join A_DFD_A01CFM56_COMPUTE T1 on T.MSG_NO = T1.MSG_NO");
	    sb.append(" WHERE T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND"+leftOrRightStr);
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY T.MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
	
	
	private HashVO[] getEngineA01ComputedVos (String begin_date,String end_date,String acNum,String param) throws Exception{
		StringBuilder sb = new StringBuilder("");
		sb.append("SELECT T.MSG_NO,T1.V_POINTTYPE,F_VALUE VALUE,F_VALUE_ROLL5 ROLL5,T1.F_NAME NAME");
		sb.append(",T.ESN_1,T.ESN_2,T.ACNUM,T.RPTDATE,T.RPTDATE UTCDATE");
	    sb.append(" FROM A_DFD_A01CFM56_COMPUTE T,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" WHERE T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"' " );
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='"+param+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	//获取A04 cfm的换发点
	private  HashVO[] getEngineA04ChangedListVos(String begin_date,String end_date,String acNum, String leftOrRight) throws Exception{
		String leftOrRightStr = "";
		if(leftOrRight=="left"){
			leftOrRightStr = " ISCHANGEPOINT1 =1";
		}else if(leftOrRight=="right"){
			leftOrRightStr = " ISCHANGEPOINT2 =1";
		}
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE,T.MSG_NO");
		sb.append(",T.EGT_1,T.EGT_2"); 
		sb.append(",T.N2_1,T.N2_2");
		sb.append(",T.FF_1,T.FF_2");
		sb.append(",T1.EGT_MARGIN_1,T1.EGT_MARGIN_2");
		sb.append(",T.T3_1,T.T3_2");
		sb.append(",T.VSV_1,T.VSV_2");
		sb.append(",T.HPT_1,T.HPT_2,T.LPT_1,T.LPT_2");
		
		sb.append(",T1.DIV_EGT_1,T1.DIV_EGT_2,T1.DIV_N2_1,T1.DIV_N2_2,T1.DIV_FF_1,T1.DIV_FF_2");
		sb.append(",T1.DIV_T3_1,T1.DIV_T3_2");
		sb.append(",T1.EGT_MARGIN_1,T1.EGT_MARGIN_2");
		sb.append(",T1.DIV_EGT_MARGIN1,T1.DIV_EGT_MARGIN2");
		sb.append(",T.OIP_1, T.OIP_2,T.OIT_1, T.OIT_2,T1.DIV_OIP_1, T1.DIV_OIP_2, T1.DIV_OIT_1, T1.DIV_OIT_2");
		sb.append(",T.VN_1,T.VN_2,T.VC_1,T.VC_2"); 
		sb.append(",T.VL_1,T.VL_2,T.VH_1,T.VH_2");
		sb.append(",T.PHA_1,T.PHA_2,T.PHT_1,T.PHT_2");
		sb.append(" FROM A_DFD_A04CFM56_5B_LIST T");
		sb.append(" left join A_DFD_A04CFM56_COMPUTE T1 on T.MSG_NO = T1.MSG_NO");
	    sb.append(" WHERE T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" AND"+leftOrRightStr);
	    sb.append(" ORDER BY T.MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
	
	private HashVO[] getEngineA04ComputedVos (String begin_date,String end_date,String acNum,String param) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T.MSG_NO,T1.V_POINTTYPE,F_VALUE VALUE,F_VALUE_ROLL5 ROLL5,T1.F_NAME NAME");
		sb.append(",T.ESN_1,T.ESN_2,T.ACNUM,T.RPTDATE,T.RPTDATE UTCDATE");
		sb.append(",T.DIV_EGT_MARGIN1,T.DIV_EGT_MARGIN2");
	    sb.append(" FROM A_DFD_A04CFM56_COMPUTE T,A_DFD_FIELD_COMPUTE T1");
	    sb.append(" WHERE RPTDATE>=? AND RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"' ");
	    sb.append(" AND T1.MSG_NO =T.MSG_NO AND T1.F_NAME='"+param+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}
	
	private HashVO[] getComputedVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.ID,T.MSG_NO,T.ACNUM,DATE_UTC UTCDATE");
		sb.append(",T.ESN_1,T.OIQ1_Z3,T.CF_EHRS,T.DETA_OIQ1_FWD,T.DETA_OIQ1_FWD_S,T.DETA_OIQ1_FWDALARM,T.IS_OIQ1_ADDRPT,T.DETA_OIQ1_FWDRATE");
		sb.append(",T.ESN_2,T.OIQ2_Z3,T.CG_EHRS,T.DETA_OIQ2_FWD,T.DETA_OIQ2_FWD_S,T.DETA_OIQ2_FWDALARM,T.IS_OIQ2_ADDRPT,T.DETA_OIQ2_FWDRATE");
		
		//复杂计算值查询
		sb.append(",T1.F_VALUE_ROLL5 OIQ1_RATE_ROLL5");//左发滑油消耗率5点均
		sb.append(",T2.F_VALUE_ROLL5 OIQ2_RATE_ROLL5");//发滑油消耗率5点均
		sb.append(",T1.V_POINTTYPE OIQ1_RATE_POINTTYPE");//左发 飘点
		sb.append(",T2.V_POINTTYPE OIQ2_RATE_POINTTYPE");//右发 飘点
		sb.append(",T.ESN_1,T.ESN_2");
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
	
	
	private  HashVO[] getA01CfmVibrationVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE,T.MSG_NO");
		sb.append(",T.VN_1,T.VN_2,T.VC_1,T.VC_2,T.N1_1,T.N1_2"); 
		sb.append(",T.VL_1,T.VL_2,T.VH_1,T.VH_2,T.N2_1,T.N2_2");
		sb.append(",T.PHA_1,T.PHA_2,T.PHT_1,T.PHT_2");
		sb.append(",T.ESN_1,T.ESN_2");
		sb.append(" FROM A_DFD_A01CFM56_5B_LIST T ");
	    sb.append(" WHERE T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
	
	
	private  HashVO[] getA04CfmVibrationVos(String begin_date,String end_date,String acNum) throws Exception{
		StringBuilder sb = new StringBuilder("SELECT T.RPTDATE UTCDATE,T.MSG_NO");
		sb.append(",T.VN_1,T.VN_2,T.VC_1,T.VC_2,T.N1_1,T.N1_2"); 
		sb.append(",T.VL_1,T.VL_2,T.VH_1,T.VH_2,T.N2_1,T.N2_2");
		sb.append(",T.PHA_1,T.PHA_2,T.PHT_1,T.PHT_2");
		sb.append(",T.ESN_1,T.ESN_2");
		sb.append(" FROM A_DFD_A04CFM56_5B_LIST T ");
	    sb.append(" WHERE T.RPTDATE>=? AND T.RPTDATE<=?");
	    sb.append(" AND T.ACNUM='"+acNum+"'");
	    sb.append(" ORDER BY MSG_NO");
	    
	    String dfStr = "yyyy-MM-dd HH:mm:ss";
	    Date beginDt = DateUtil.StringToDate(begin_date, dfStr);
	    Date endDt = DateUtil.StringToDate(end_date, dfStr);
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString(), beginDt,endDt);
		
		return vos;
	}	
}

