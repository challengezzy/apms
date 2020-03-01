package com.apms.bs.dataprase.impl;

import java.util.ArrayList;
import java.util.List;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA39Vo_A320;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a39.AcarsEC_EEVo_A39_A320_CFM;
import com.apms.bs.dataprase.vo.a39.AcarsS1Vo_A39_A320_LIST;
import com.apms.bs.dataprase.vo.a39.AcarsS2Vo_A39_A320_CFM;
import com.apms.bs.util.DateUtil;
import com.apms.bs.util.StringUtil;
import com.apms.matlab.MatlabFunctionService;
import com.apms.matlab.vo.MathCountResult;
import com.apms.vo.ApmsVarConst;

/**
 * A40报文解析实现类
 * @author xujingkai
 */
public class A40DataParseImpl extends ReportContentParseClass{
	
	int count=0;
	private MatlabFunctionService functionService = new MatlabFunctionService();
	
	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo,String msgno, String content, String trans_time) throws Exception {
		
		AcarsDfdA39Vo_A320 a39vo = praseA39Data(content,trans_time);
		insertA11(msgno, a39vo);
		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		
		return res;
		
	}
	
	/**
	 * 报文解析
	 * @param graphStr 报文内容
	 * @param transdate 报文传送日期 YYYY-MM-DD
	 * @return
	 * @throws Exception
	 */
	public AcarsDfdA39Vo_A320 praseA39Data(String graphStr,String transdate) throws Exception{
		AcarsDfdA39Vo_A320 a39vo = new AcarsDfdA39Vo_A320();
		
		String tmpStr = graphStr.replaceAll("[\n\r]", " ");
		
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);
		
		logger.debug("报文每行数据如下：");
		ArrayList<AcarsS2Vo_A39_A320_CFM> s2List=new ArrayList<AcarsS2Vo_A39_A320_CFM>();
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			//logger.debug("第"+i+"行:" + line);
			
			if(line.startsWith("EC")){
				a39vo.setEc_c(new  AcarsEC_EEVo_A39_A320_CFM(line));
			}else if(line.startsWith("EE")){
				a39vo.setEe_c(new  AcarsEC_EEVo_A39_A320_CFM(line));
			}else if(line.startsWith("S1")){
				a39vo.setS1(new AcarsS1Vo_A39_A320_LIST(headVo.getDateUtc(),line));
			}else if(line.startsWith("S2")){
				s2List.add(new AcarsS2Vo_A39_A320_CFM(line));
				count++;
			}
		}
		a39vo.setS2(s2List);
		
		Double[] arr_ff1 = new Double[s2List.size()];
		Double[] arr_ff2 = new Double[s2List.size()];
		Double[] arr_n21 = new Double[s2List.size()];
		Double[] arr_n22 = new Double[s2List.size()];
		Double[] arr_pd1 = new Double[s2List.size()];
		Double[] arr_pd2 = new Double[s2List.size()];
		
		for(int i=0;i<s2List.size();i++){
			AcarsS2Vo_A39_A320_CFM s2 = s2List.get(i);
			arr_ff1[i] = s2.getFf1();
			arr_ff2[i] = s2.getFf2();
			arr_n21[i] = s2.getN21();
			arr_n22[i] = s2.getN22();
			arr_pd1[i] = s2.getPd1();
			arr_pd2[i] = s2.getPd2();
		}
		
		//计算报文中点的均值和方差
		MathCountResult countRes = functionService.Math_Count(arr_ff1);
		a39vo.setFf1_avg(countRes.getAvg());
		a39vo.setFf1_std(countRes.getStd());
		
		countRes = functionService.Math_Count(arr_ff2);
		a39vo.setFf2_avg(countRes.getAvg());
		a39vo.setFf2_std(countRes.getStd());
		
		countRes = functionService.Math_Count(arr_n21);
		a39vo.setN21_avg(countRes.getAvg());
		a39vo.setN21_std(countRes.getStd());
		
		countRes = functionService.Math_Count(arr_n22);
		a39vo.setN22_avg(countRes.getAvg());
		a39vo.setN22_std(countRes.getStd());
		
		countRes = functionService.Math_Count(arr_pd1);
		a39vo.setPd1_avg(countRes.getAvg());
		a39vo.setPd1_std(countRes.getStd());
		
		countRes = functionService.Math_Count(arr_pd2);
		a39vo.setPd2_avg(countRes.getAvg());
		a39vo.setPd2_std(countRes.getStd());
		
		return a39vo;
	}
	
	public void insertA11(String msgno, AcarsDfdA39Vo_A320 a39vo) throws Exception{
		CommDMO dmo = new CommDMO();
		String insertSql0 = "insert into a_dfd_a40_list(ID,MSG_NO,ACNUM,RPTDATE,CODE," 
				+ "esn_ec, ehrs_ec, ecyc_ec,"
				+ "esn_ee, ehrs_ee, ecyc_ee," 
				+ "starttime_s1, endtime_s1,s2_rowcount, interval_time,"
				+ " FF1_AVG,FF1_STD,FF2_AVG,FF2_STD,N21_AVG,N21_STD,N22_AVG,N22_STD,PD1_AVG,PD1_STD,PD2_AVG,PD2_STD,"
				+ "UPDATE_DATE)"  
				+ " values(S_A_DFD_A39_LIST.NEXTVAL,"
				+ "?,?,?,?,"
				+ "?,?,?,?,?,?,"
				+ "?,?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,?,?,?,"
				+ "sysdate)";
		AcarsEC_EEVo_A39_A320_CFM ec = a39vo.getEc_c();
		AcarsEC_EEVo_A39_A320_CFM ee = a39vo.getEe_c();
		AcarsS1Vo_A39_A320_LIST s1= a39vo.getS1();
		ArrayList<AcarsS2Vo_A39_A320_CFM> s2= a39vo.getS2();

		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql0, msgno,headVo.getAcid(),headVo.getDateUtc(),headVo.getCode(),
				ec.getEsn(),ec.getEhrs(),ec.getEcyc(),
				ee.getEsn(),ee.getEhrs(),ee.getEcyc(),
				s1.getStarttime(),s1.getEndtime(),count,s1.getInterval(),
				a39vo.getFf1_avg(),a39vo.getFf1_std(),a39vo.getFf2_avg(),a39vo.getFf2_std(),
				a39vo.getN21_avg(),a39vo.getN21_std(),a39vo.getN22_avg(),a39vo.getN22_std(),
				a39vo.getPd1_avg(),a39vo.getPd1_std(),a39vo.getPd2_avg(),a39vo.getPd2_std()
				);
		
		List<String> list=new ArrayList<String>();
		for(int i=0;i<s2.size();i++){
			int seq=i+1;
			String datestr="";
			if(s1.getStarttime()!=null){
				datestr=DateUtil.getDateStr(DateUtil.moveSecond(s1.getStarttime(),(int)s1.getInterval()*i), "yyyyMMdd HH:mm:ss");
			}
			String sql1="INSERT INTO A_DFD_A40_LIST_S2(ID, MSG_NO, ROW_SEQ, STARTTIME,"
				+ " FF1, FF2, N21, N22, PD1, PD2, UPDATETIME)"
				+ " VALUES(S_A_DFD_A39_LIST_S2.nextval,"
				+ msgno+","+seq+","+"to_date('"+datestr+"','YYYYMMDD HH24:MI:SS')"
				+ ","+s2.get(i).getFf1()+","+s2.get(i).getFf2()
				+ ","+s2.get(i).getN21()+","+s2.get(i).getN22()
				+ ","+s2.get(i).getPd1()+","+s2.get(i).getPd2()
				+ ",sysdate)";
			list.add(sql1);
		}
		dmo.executeBatchByDS(ApmsConst.DS_APMS, list);
		dmo.commit(ApmsConst.DS_APMS);
		logger.info("A40 报文[msg_no]=["+msgno+"]入库成功！");

	}
	


}
