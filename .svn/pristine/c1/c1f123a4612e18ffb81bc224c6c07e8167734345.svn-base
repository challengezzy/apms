package com.apms.bs.apu;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.vo.BintProgResult;
import com.apms.matlab.vo.MathOneXFitResult;

public class ApuPdiService {
	
	protected Logger logger = NovaLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	private MatlabFunctionService functionService = new MatlabFunctionService();
	
	public Map<String, Object> getApuChangeForecastData(Map<String, Object> predictObj ) throws Exception{
		String apumodel = (String)predictObj.get("APUMODEL");
		String baseOrgId = (String)predictObj.get("BASEORGID");
		int periodSpan = (Integer)predictObj.get("PERIODSPAN");
		int repairSpan = (Integer)predictObj.get("REPAIRSPAN");
		int backupNum = (Integer)predictObj.get("BACKUPNUM");
		double pdiLimit = new Double(predictObj.get("PDILIMIT").toString());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resDataList = new ArrayList<Map<String,Object>>();
		try{
			HashMap<String, HashMap<String,Object>> resdataMap;
			resdataMap = computeSwapPredictEq(baseOrgId,periodSpan, repairSpan, backupNum, pdiLimit, apumodel, resultMap);
			//把数据结果转换为数组形式
			for(String asn : resdataMap.keySet()){
				resDataList.add(resdataMap.get(asn));
			}
		}catch (Exception e) {
			logger.warn(" 计算APU拆换策略失败 ！" + e.toString());
		}
		
		//加入pdi值一直小时门限值的数据
		HashMap<String, HashMap<String,Object>> resdataMap2;
		resdataMap2 = getLowPdiDataList(baseOrgId,periodSpan, pdiLimit, apumodel, resultMap);
		//把数据结果转换为数组形式
		for(String asn : resdataMap2.keySet()){
			resDataList.add(resdataMap2.get(asn));
		}
		
		
		resultMap.put("DATALIST", resDataList );
		logger.info("本次预测处理["+resDataList.size()+"]条APU数据");
		
		return resultMap;
	}
	
	/**
	 * 查询PDI值一值未过门限值的APU数据
	 * @param periodSpan
	 * @param repairSpan
	 * @param backupNum
	 * @param pdiLimit
	 * @param apumodel
	 * @param resultMap
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, HashMap<String,Object>> getLowPdiDataList(String baseOrgId,int periodSpan,
				double pdiLimit,String apumodel,Map<String, Object> resultMap) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("select batchno,curdate,asn,bdate,edate,periodidx,ahrs,pdi,a.datatime lastdatatime,");
		sb.append("(select ac.aircraftsn from b_aircraft ac where ac.id=a.aircraftid) acnum,");
		sb.append("(select name from b_organization o where o.id = a.baseorgid) baseorgname,");
		sb.append(" a.baseorgid ");
		sb.append(" from l_apu_pdipredict t,b_apu a where t.asn=a.apusn");
		//取最新的预测值
		sb.append(" and batchno = (select max(batchno) from l_apu_pdipredict t1 where curdate <= sysdate)");
		sb.append(" and not exists (select 1 from l_apu_pdipredict t2 where t2.asn=t.asn and t2.batchno=t.batchno");
		sb.append("   and t2.pdi >= "+pdiLimit+"  and t2.periodidx <="+ periodSpan +")");//存在PDI超限
		sb.append(" and a.apumodelid=(select id from b_apu_model m where m.submodel='"+ apumodel +"')");
		sb.append(" and periodidx <= "+ periodSpan);
		if(baseOrgId != null && baseOrgId.length()>0 ){
			sb.append(" and a.baseorgid= "+ baseOrgId);
		}
		sb.append(" order by asn,periodidx ");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		HashMap<String, HashMap<String,Object>> resdataMap = new HashMap<String, HashMap<String, Object>>(); //结果视图数据

		for(int i=0;i<vos.length;i++){
			HashMap<String,Object> rowmap ;//界面展示数据
			
			HashVO vo = vos[i];
			String asn = vo.getStringValue("ASN");
			int pidx = vo.getIntegerValue("PERIODIDX");
			//显示3位小数
			double pdi = Double.parseDouble( String.format("%.3f", vo.getDoubleValue("PDI")) );
			double ahrs = vo.getDoubleValue("AHRS")/60; //转换为小时
			
			String bdate = DateUtil.getDateStr(vo.getDateValue("BDATE"), "yyMMdd");
			String edate = DateUtil.getDateStr(vo.getDateValue("EDATE"), "yyMMdd"); 
			
			String headTxt = "周期" +pidx+ "-" + bdate+ "\n ~" + edate ;
			String tipMsg = "机号：" +vo.getStringValue("ACNUM")+ ",ASN:" + vo.getStringValue("ASN")
				+ "n总小时：" + ahrs + ", PDI:" + pdi
				+ "\n时间范围："+bdate+ "~" + edate + "";
			
			//表示是一个新的APU数据
			if( !resdataMap.containsKey(asn) ){
				rowmap = new HashMap<String, Object>(); //一行新数据
				rowmap.put("ASN", asn);
				rowmap.put("ACNUM", vo.getStringValue("ACNUM"));
				rowmap.put("BASEORGNAME", vo.getStringValue("BASEORGNAME"));
				rowmap.put("CURDATE", vo.getStringValueForDay("CURDATE"));
				rowmap.put("LASTDATATIME", vo.getStringValueForDay("LASTDATATIME"));
				rowmap.put("LOWDATA", "1"); //低于门限值数据
				
				resdataMap.put(asn, rowmap);
			}
			rowmap = resdataMap.get(asn);
			
			rowmap.put("PDATA"+pidx, pdi);
			rowmap.put("TIP"+pidx, tipMsg );
			rowmap.put("HEAD"+pidx, headTxt );
		}
		
		return resdataMap;
	}
	
	/**
	 * APU拆预测策略计算
	 * @param periodSpan 计算周期跨度
	 * @param repairSpan 修理周期跨度 
	 * @param backupNum 备发数量
	 * @param pdiLimit PDI门限值
	 * @param apumodel APU型号
	 * @throws Exception
	 */
	public HashMap<String, HashMap<String,Object>> computeSwapPredict(String baseOrgId,int periodSpan,int repairSpan,int backupNum
			,double pdiLimit,String apumodel,Map<String, Object> resultMap) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("select batchno,curdate,asn,bdate,edate,periodidx,ahrs,pdi,a.datatime lastdatatime,");
		sb.append("(select ac.aircraftsn from b_aircraft ac where ac.id=a.aircraftid) acnum,");
		sb.append("(select name from b_organization o where o.id = a.baseorgid) baseorgname,");
		sb.append(" a.baseorgid ");
		sb.append(" from l_apu_pdipredict t,b_apu a where t.asn=a.apusn");
		//取最新的预测值
		sb.append(" and batchno = (select max(batchno) from l_apu_pdipredict t1 where curdate <= sysdate)");
		sb.append(" and exists (select 1 from l_apu_pdipredict t2 where t2.asn=t.asn and t2.batchno=t.batchno");
		sb.append("   and t2.pdi >= "+pdiLimit+"  and t2.periodidx <="+ periodSpan +")");//存在PDI超限
		sb.append(" and a.apumodelid=(select id from b_apu_model m where m.submodel='"+ apumodel +"')");
		sb.append(" and periodidx <= "+ periodSpan);
		if(baseOrgId != null && baseOrgId.length()>0 ){
			sb.append(" and a.baseorgid= "+ baseOrgId);
		}
		sb.append(" order by asn,periodidx ");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		List<List<HashVO>> dataList = new ArrayList<List<HashVO>>();
		if(vos.length < periodSpan){
			logger.warn("根据条件未找到需要进行拆换的APU数据!");
			throw new Exception("根据条件未找到需要进行拆换的APU数据!");
		}
		
		ArrayList<String> asnList = new ArrayList<String>();
		HashMap<String, HashMap<String,Object>> resdataMap = new HashMap<String, HashMap<String, Object>>(); //结果视图数据

		List<HashVO> alist = null;
		for(int i=0;i<vos.length;i++){
			HashMap<String,Object> rowmap ;//界面展示数据
			
			HashVO vo = vos[i];
			String asn = vo.getStringValue("ASN");
			int pidx = vo.getIntegerValue("PERIODIDX");
			//显示3位小数
			double pdi = Double.parseDouble( String.format("%.3f", vo.getDoubleValue("PDI")) );
			double ahrs = vo.getDoubleValue("AHRS")/60; //转换为小时
			
			String bdate = DateUtil.getDateStr(vo.getDateValue("BDATE"), "yyMMdd");
			String edate = DateUtil.getDateStr(vo.getDateValue("EDATE"), "yyMMdd"); 
			
			String headTxt = "周期" +pidx+ "-" + bdate+ "\n ~" + edate ;
			String tipMsg = "机号：" +vo.getStringValue("ACNUM")+ ",ASN:" + vo.getStringValue("ASN")
				+ "n总小时：" + ahrs + ", PDI:" + pdi
				+ "\n时间范围："+bdate+ "~" + edate + "";
			
			
			
			//表示是一个新的APU数据
			if( !resdataMap.containsKey(asn) ){
				alist = new ArrayList<HashVO>();				
				dataList.add(alist);
				
				asnList.add(asn);
				
				rowmap = new HashMap<String, Object>(); //一行新数据
				rowmap.put("ASN", asn);
				rowmap.put("ACNUM", vo.getStringValue("ACNUM"));
				rowmap.put("BASEORGNAME", vo.getStringValue("BASEORGNAME"));
				rowmap.put("CURDATE", vo.getStringValueForDay("CURDATE")); 
				rowmap.put("LASTDATATIME", vo.getStringValueForDay("LASTDATATIME"));
				
				
				resdataMap.put(asn, rowmap);
			}
			rowmap = resdataMap.get(asn);
			
			rowmap.put("PDATA"+pidx, pdi);
			rowmap.put("TIP"+pidx, tipMsg );
			rowmap.put("HEAD"+pidx, headTxt );
			
			alist.add(vos[i]);
			
		}
		
		int apucount = dataList.size();//APU数量
		//应该有 [apuCount*2 + (periodSpan-repairSpan+1) ]行[apucount*periodSpan]列
		int lineNum = apucount*2 + periodSpan-repairSpan+1 ;
		int columnNum = apucount*periodSpan;
		
		//使用dataList数据,构造apucount*periodSapn的矩阵 求解矩阵
		double[][] matrixPdi = new double[apucount][periodSpan];
		double[] matrixF = new double[columnNum];
		System.out.println("规划参数值 预测期间范围=["+periodSpan+"],修理周期=["+repairSpan+"],备件数量=["+backupNum+"],PDI门限=["+pdiLimit+"],APU型号=["+apumodel+"]");
		System.out.println("*******求解数据********");
		for(int i=0;i<apucount;i++){
			for(int j=0;j<periodSpan;j++){
				List<HashVO> apulist = dataList.get(i);
				HashVO avo = apulist.get(j);
				double pdi = avo.getDoubleValue("pdi");
				matrixPdi[i][j] = pdi;
				
				matrixF[i*periodSpan+j] = pdi;
				
				System.out.print(pdi);
				System.out.print("  ,  ");
			}
			System.out.println();
		}
		System.out.println("***************");
		
		double[][] matrixA = new double[lineNum][columnNum];
		double[] matrixB = new double[lineNum];
		
		//条件组1,每个APU预测时间范围内只换一次 X11+ .. + X1j + X1n <= 1;  ……… ,   Xm1+ .. + Xmj + Xmn <= 1;,共M个
		for(int m=0;m<apucount;m++){
			matrixB[m] = 1;
			for(int n=0;n<periodSpan;n++){
				matrixA[m][ m*periodSpan+n] = 1;
			}
		}
		
		//条件组2,在指定的周期内必须拆换一次,即P11*X11+P12*X12+..+P1n*X1n>=P_LIMIT,转换为小于 -P11*X11-P12*X12-..-P1n*X1n<=P_LIMIT
		for(int m=0; m<apucount; m++){
			matrixB[apucount+m] = -pdiLimit;
			for(int n=0; n<periodSpan; n++){
				
				matrixA[apucount+m][ m*periodSpan+n ] = -matrixPdi[m][n];
			}
		}
		//条件组3,在APU的修理周期内,拆换数据不能大于备用APU数量,共有(periodSpan-repairSpan+1)个 X11+X12+X12..+X21+X22+X23...+Xn1+Xn2+Xn3<= backupNum;
		for(int m=0; m< (periodSpan-repairSpan+1) ; m++){
			matrixB[apucount*2+m] = backupNum;
			for(int n=0; n<apucount; n++){
				//设置repairSpan列
				for(int z=0;z<repairSpan;z++){
					matrixA[apucount*2+m][ m + n*periodSpan +z ] = 1;
				}
			}
		}
		
		BintProgResult result = functionService.bintProg(matrixF, matrixA, matrixB, null, null);
		resultMap.put("EXITFLAG", result.getExitFlag());
		resultMap.put("OUTPUTMSG", result.getOutput());
		
		if(result.getExitFlag() == 1){
			logger.info("PDI预测APU的拆换规划求解成功！");
			double[] xroot = result.getX();
			
			System.out.println("******拆换策略*********");
			//将预测结果进行赋值
			for(int i=0;i< apucount; i++){
				String asn = asnList.get(i);
				for(int j=0; j<periodSpan ; j++){
					HashMap<String,Object> rowmap = resdataMap.get(asn);
					int isChg = (int)xroot[i*periodSpan + j];
					rowmap.put("CHG"+(j+1), isChg );
					System.out.print(isChg +"  ,  ");
				}
				System.out.println();
			}
			System.out.println("***************");
		}else{
			logger.warn("PDI预测APU的拆换策略计算求解失败， EXITFLAG="+ result.getExitFlag()+",失败信息:" + result.getOutput());
		}
		
		logger.info("根据PDI预测APU的拆换策略计算成功!");
		
		return resdataMap;
	}
	
	/**
	 * APU的PDI预测数据预计算
	 * @param periodLength
	 * @param periodNum
	 * @param zeroLimit 使用预测值为0的条件
	 * @param oldPdiLimit 使用PDI_OLD任务预测值的条件
	 * @throws Exception
	 */
	public void computeAllPdiPredict(int periodLength,int periodNum,int zeroLimit,int oldPdiLimit) throws Exception{
		
		//查询PDI历史数据
		StringBuilder pdisql = new StringBuilder();
		pdisql.append("SELECT MSG_NO, ACNUM, AHRS, ACYC, PDI_OLD, PDI_NEW,UTCDATE");
		pdisql.append(" FROM A_DFD_A13_COMPUTE C WHERE C.ASN = ? AND C.UTCDATE >= ? ");
		pdisql.append(" ORDER BY UTCDATE ASC");
		
		//查询apu列表
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ID,APUSN,BASEORGID,APUMODELID,DATATIME,TOTALTIME");
		sb.append(" ,INSTALL_TIME,DAY_PREDITION_HOUR,AIRCRAFTID,TOTALCYCLE ");
		sb.append(" FROM B_APU A WHERE 1=1 AND A.POSITION=1");
		//sb.append(" AND A.BASEORGID=3 ");
		
		String insertsql = "INSERT INTO L_APU_PDIPREDICT(ID,BATCHNO,CURDATE,ASN,BDATE,EDATE,PERIODIDX,AHRS,PDI,ALGTYPE,POINTNUM,UPDATETIME)"
			+ " VALUES(S_L_APU_PDIPREDICT.NEXTVAL, ?,?,?,?,?,?,?,?,?,?, SYSDATE)";
		
		Date today = DateUtil.toDay(new Date());
		String batchno = DateUtil.getDateStr(today, "yyyyMMdd");
		
		//清空已生成的数据
		String delSql = "DELETE L_APU_PDIPREDICT WHERE BATCHNO = ?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, delSql, batchno);
		dmo.commit(ApmsConst.DS_APMS);
		
		//测试过滤
		//sb.append(" and apusn = '2276' " );
		HashMap<String, double[]> prePdiMap = new HashMap<String, double[]>();
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		for(int i=0;i<vos.length; i++){
			HashVO apuvo = vos[i];
			//String apuid = apuvo.getStringValue("ID");
			Date installTime = apuvo.getDateValue("INSTALL_TIME");//装上时间
			String apusn = apuvo.getStringValue("APUSN");
			int dayMinute = apuvo.getIntegerValue("DAY_PREDITION_HOUR");//每天运行分钟数
			//Date datatime = apuvo.getDateValue("DATATIME");//最新数据时间
			int curMinute = apuvo.getIntegerValue("TOTALTIME");
			
			//查询出新PDI历史数据，进行回归计算
			HashVO[] pdivos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, pdisql.toString(), apusn, installTime);
			int pointNum = pdivos.length; //点个数
			//PDI回归预测时，点数小于500的，把PDI预测为0.3(不做预测)， 在500~1000点以内用PDI_OLD作预测， 大于1000于用PDI_NEW预测
			int algType = 1;
			if(pointNum < zeroLimit){
				algType = 3; //不预测,预测值直接设置为0.3
			}else if(pointNum < oldPdiLimit){
				algType = 2; //使用PDI_OLD预测
			}
			
			Double[] yArrn = new Double[pointNum];
			Double[] xArrn = new Double[pointNum];
			for(int m=0; m<pointNum; m++){
				HashVO pdivo = pdivos[m];
				xArrn[m] = pdivo.getDoubleValue("AHRS");
				if(algType == 2){
					yArrn[m] = pdivo.getDoubleValue("PDI_OLD");
				}else{
					yArrn[m] = pdivo.getDoubleValue("PDI_NEW");
				}
				
			}
			//一元2次回归
			MathOneXFitResult fitXRes = functionService.Math_OneXFit(xArrn, yArrn, 2);
			double[] coefficient = fitXRes.getCoefficient();
			
			//得到回归方程式 ax^2 + bx + c;
			double a = coefficient[0];
			double b = coefficient[1];
			double c = coefficient[2];
			
			double[] prePdiArr = new double[periodNum];
			//计算未来10个周期内,pdi的值,一个周期是15天
			for(int j=0;j<periodNum; j++){
				int preMin = curMinute + (j+1)*dayMinute* periodLength ; //N个15天后的ahrs
				double prePdi = a*preMin*preMin + b*preMin + c;
				prePdiArr[j] = prePdi;
				
				Date bdate = DateUtil.moveDay(today, j*periodLength);
				Date edate = DateUtil.moveDay(today, (j+1)*periodLength);
				
				if(algType == 3){
					prePdi = 0.3;//点数量太少，新拆装，不预测，设置为常量值
				}
				//数据入库
				//BATCHNO,CURDATE,ASN,BDATE,EDATE,PERIODIDX,AHRS,PDI
				dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertsql, batchno,today,apusn,bdate,edate,(j+1),preMin,prePdi,algType,pointNum);
			}
			
			dmo.commit(ApmsConst.DS_APMS);
			logger.info("apu["+apusn+"]的pdi预测值计算完毕");
			
			prePdiMap.put(apusn, prePdiArr);
		}
		
		//存入到数据库中
		Set<String> asns = prePdiMap.keySet();
		for(String asn : asns){
			double[] pdiArr = prePdiMap.get(asn);
			System.out.println("***********"+asn+"**************");
			
			for(double pdi : pdiArr){
				System.out.print(pdi);
				System.out.println("  ,  ");
			}
			System.out.println();
		}
		
		logger.info("batchno==["+batchno+"]所有的APU性能预测值计算完毕！");		
	}
	
	
	/**
	 * APU拆预测策略计算,使用等式约束
	 * @param periodSpan 计算周期跨度
	 * @param repairSpan 修理周期跨度 
	 * @param backupNum 备发数量
	 * @param pdiLimit PDI门限值
	 * @param apumodel APU型号
	 * @throws Exception
	 */
	public HashMap<String, HashMap<String,Object>> computeSwapPredictEq(String baseOrgId,int periodSpan,int repairSpan,int backupNum
			,double pdiLimit,String apumodel,Map<String, Object> resultMap) throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append("select batchno,curdate,asn,bdate,edate,periodidx,ahrs,pdi,a.datatime lastdatatime,");
		sb.append("(select ac.aircraftsn from b_aircraft ac where ac.id=a.aircraftid) acnum,");
		sb.append("(select name from b_organization o where o.id = a.baseorgid) baseorgname,");
		sb.append(" a.baseorgid ");
		sb.append(" from l_apu_pdipredict t,b_apu a where t.asn=a.apusn");
		//取最新的预测值
		sb.append(" and batchno = (select max(batchno) from l_apu_pdipredict t1 where curdate <= sysdate)");
		sb.append(" and exists (select 1 from l_apu_pdipredict t2 where t2.asn=t.asn and t2.batchno=t.batchno");
		sb.append("   and t2.pdi >= "+pdiLimit+"  and t2.periodidx <="+ periodSpan +")");//存在PDI超限
		sb.append(" and a.apumodelid=(select id from b_apu_model m where m.submodel='"+ apumodel +"')");
		sb.append(" and periodidx <= "+ periodSpan);
		if(baseOrgId != null && baseOrgId.length()>0 ){
			sb.append(" and a.baseorgid= "+ baseOrgId);
		}
		sb.append(" order by asn,periodidx ");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sb.toString());
		List<List<HashVO>> dataList = new ArrayList<List<HashVO>>();
		if(vos.length < periodSpan){
			logger.warn("根据条件未找到需要进行拆换的APU数据!");
			throw new Exception("根据条件未找到需要进行拆换的APU数据!");
		}
		
		ArrayList<String> asnList = new ArrayList<String>();
		HashMap<String, HashMap<String,Object>> resdataMap = new HashMap<String, HashMap<String, Object>>(); //结果视图数据

		List<HashVO> alist = null;
		for(int i=0;i<vos.length;i++){
			HashMap<String,Object> rowmap ;//界面展示数据
			
			HashVO vo = vos[i];
			String asn = vo.getStringValue("ASN");
			int pidx = vo.getIntegerValue("PERIODIDX");
			//显示3位小数
			double pdi = Double.parseDouble( String.format("%.3f", vo.getDoubleValue("PDI")) );
			double ahrs = vo.getDoubleValue("AHRS")/60; //转换为小时
			
			String bdate = DateUtil.getDateStr(vo.getDateValue("BDATE"), "yyMMdd");
			String edate = DateUtil.getDateStr(vo.getDateValue("EDATE"), "yyMMdd"); 
			
			String headTxt = "周期" +pidx+ "-" + bdate+ "\n ~" + edate ;
			String tipMsg = "机号：" +vo.getStringValue("ACNUM")+ ",ASN:" + vo.getStringValue("ASN")
				+ "n总小时：" + ahrs + ", PDI:" + pdi
				+ "\n时间范围："+bdate+ "~" + edate + "";
			
			//表示是一个新的APU数据
			if( !resdataMap.containsKey(asn) ){
				alist = new ArrayList<HashVO>();				
				dataList.add(alist);
				
				asnList.add(asn);
				
				rowmap = new HashMap<String, Object>(); //一行新数据
				rowmap.put("ASN", asn);
				rowmap.put("ACNUM", vo.getStringValue("ACNUM"));
				rowmap.put("BASEORGNAME", vo.getStringValue("BASEORGNAME"));
				rowmap.put("CURDATE", vo.getStringValueForDay("CURDATE")); 
				rowmap.put("LASTDATATIME", vo.getStringValueForDay("LASTDATATIME"));
				
				resdataMap.put(asn, rowmap);
			}
			rowmap = resdataMap.get(asn);
			
			rowmap.put("PDATA"+pidx, pdi);
			rowmap.put("TIP"+pidx, tipMsg );
			rowmap.put("HEAD"+pidx, headTxt );
			
			alist.add(vos[i]);
			
		}
		
		int apucount = dataList.size();//APU数量
		//应该有 [apuCount*2 + (periodSpan-repairSpan+1) ]行[apucount*periodSpan]列
		int lineNum = apucount + periodSpan-repairSpan+1 ;
		int columnNum = apucount*periodSpan;
		
		//使用dataList数据,构造apucount*periodSapn的矩阵 求解矩阵
		double[][] matrixPdi = new double[apucount][periodSpan];
		double[] matrixF = new double[columnNum];
		System.out.println("规划参数值 预测期间范围=["+periodSpan+"],修理周期=["+repairSpan+"],备件数量=["+backupNum+"],PDI门限=["+pdiLimit+"],APU型号=["+apumodel+"]");
		System.out.println("*******求解数据********");
		for(int i=0;i<apucount;i++){
			for(int j=0;j<periodSpan;j++){
				List<HashVO> apulist = dataList.get(i);
				HashVO avo = apulist.get(j);
				double pdi = avo.getDoubleValue("pdi");
				matrixPdi[i][j] = pdi;
				
				matrixF[i*periodSpan+j] = pdi;
				
				System.out.print(pdi);
				System.out.print("  ,  ");
			}
			System.out.println();
		}
		System.out.println("***************");
		
		double[][] matrixA = new double[lineNum][columnNum];
		double[] matrixB = new double[lineNum];
		
		//条件组2,在指定的周期内必须拆换一次,即P11*X11+P12*X12+..+P1n*X1n>=P_LIMIT,转换为小于 -P11*X11-P12*X12-..-P1n*X1n<=P_LIMIT
		for(int m=0; m<apucount; m++){
			matrixB[m] = -pdiLimit;
			for(int n=0; n<periodSpan; n++){
				
				matrixA[m][ m*periodSpan+n ] = -matrixPdi[m][n];
			}
		}
		//条件组3,在APU的修理周期内,拆换数据不能大于备用APU数量,共有(periodSpan-repairSpan+1)个 X11+X12+X12..+X21+X22+X23...+Xn1+Xn2+Xn3<= backupNum;
		for(int m=0; m< (periodSpan-repairSpan+1) ; m++){
			matrixB[apucount+m] = backupNum;
			for(int n=0; n<apucount; n++){
				//设置repairSpan列
				for(int z=0;z<repairSpan;z++){
					matrixA[apucount+m][ m + n*periodSpan +z ] = 1;
				}
			}
		}
		
		double[][] matrixAeq = new double[apucount][apucount*periodSpan];
		double[] matrixBeq = new double[apucount];
		//等式组
		//条件组1,每个APU预测时间范围内只换一次 X11+ .. + X1j + X1n = 1;  ……… ,   Xm1+ .. + Xmj + Xmn = 1;,共M个
		for(int m=0;m<apucount;m++){
			matrixBeq[m] = 1;
			for(int n=0;n<periodSpan;n++){
				matrixAeq[m][ m*periodSpan+n] = 1;
			}
		}
		System.out.println("******不等式矩阵*********");
		//将预测结果进行赋值
		for(int i=0;i< lineNum; i++){
			for(int j=0; j< columnNum ; j++){
				System.out.print(matrixA[i][j] +"  ,  ");
			}
			System.out.println();
		}
		System.out.println("******不等式矩阵右边*********");
		for(int i=0;i< lineNum; i++){
			System.out.print(matrixB[i] +"  ,  ");
		}
		System.out.println("********************");
		System.out.println("******求解系数*********");
		for(int i=0;i< columnNum; i++){
			System.out.print(matrixF[i] +"  ,  ");
		}
		System.out.println("********************");
		
		BintProgResult result = functionService.bintProg(matrixF, matrixA, matrixB, matrixAeq, matrixBeq);
		resultMap.put("EXITFLAG", result.getExitFlag());
		resultMap.put("OUTPUTMSG", result.getOutput());
		
		if(result.getExitFlag() == 1){
			logger.info("PDI预测APU的拆换规划求解成功！");
			double[] xroot = result.getX();
			
			System.out.println("******拆换策略*********");
			//将预测结果进行赋值
			for(int i=0;i< apucount; i++){
				String asn = asnList.get(i);
				for(int j=0; j<periodSpan ; j++){
					HashMap<String,Object> rowmap = resdataMap.get(asn);
					int isChg = (int)xroot[i*periodSpan + j];
					rowmap.put("CHG"+(j+1), isChg );
					System.out.print(isChg +"  ,  ");
				}
				System.out.println();
			}
			System.out.println("***************");
		}else{
			logger.warn("PDI预测APU的拆换策略计算求解失败， EXITFLAG="+ result.getExitFlag()+",失败信息:" + result.getOutput());
		}
		
		logger.info("根据PDI预测APU的拆换策略计算成功!");
		
		return resdataMap;
	}

}
