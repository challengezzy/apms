package com.apms.bs.dataprase.impl;

import java.util.ArrayList;
import java.util.List;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;

import com.apms.ApmsConst;
import com.apms.bs.dataprase.ReportContentParseClass;
import com.apms.bs.dataprase.vo.AcarsDfdA46Vo_A321;
import com.apms.bs.dataprase.vo.AcarsParseResult;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsECVo_A46_A321;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsEEVo_A46_A321;
import com.apms.bs.dataprase.vo.a19a21a24.AcarsS1Vo_A46_A321;

import com.apms.bs.util.StringUtil;
import com.apms.vo.ApmsVarConst;

public class A46DataParseImpl_A321 extends ReportContentParseClass {

	@Override
	public AcarsParseResult parseContentData(HashVO acarsVo, String msgno,String content, String trans_time) throws Exception{
		praseA46Data(acarsVo,msgno,content,trans_time);		
		AcarsParseResult res = new AcarsParseResult(ApmsVarConst.TELEGRAPH_PARSE_OK);
		return res;
	}

	protected void praseA46Data(HashVO acarsVo,String msgno,String gStrA46,String transdate) throws Exception{
		AcarsDfdA46Vo_A321 a46vo = new AcarsDfdA46Vo_A321();
		String tmpStr = gStrA46.replaceAll("[\n\r]", " ");
		String[] lines = StringUtil.splitString2Array(tmpStr, "/", true);	
		List<String> S1lines = new ArrayList<String>();
		//给a46vo 对象赋值开始
		for(int i=0; i<lines.length; i++){
			String line = lines[i].trim();
			
			if(line.startsWith("EC")){				
				a46vo.setEc( getECVo(line) );
			}else if(line.startsWith("EE")){				
				a46vo.setEe(new AcarsEEVo_A46_A321(line));
			}else if(line.startsWith("S1")){
				S1lines.add(line);//把所有S1 行放S1lines 集合 单独处理
			}
		}
		
		for(int i=0;i<S1lines.size();i++){
			if(i==0) a46vo.setS1_time1(getS1Vo(S1lines.get(0), transdate));
			if(i==1) a46vo.setS1_time2(getS1Vo(S1lines.get(1), transdate));
			if(S1lines.size()==30){
				if(i==2) a46vo.setS1_time3(getS1Vo(S1lines.get(2), transdate));
				if(i==3) a46vo.setS1_time4(getS1Vo(S1lines.get(3), transdate));
				if(i==4) a46vo.setS1_time5(getS1Vo(S1lines.get(4), transdate));
				if(i==5) a46vo.setS1_time6(getS1Vo(S1lines.get(5), transdate));
				if(i==6) a46vo.setS1_time7(getS1Vo(S1lines.get(6), transdate));
				if(i==7) a46vo.setS1_time8(getS1Vo(S1lines.get(7), transdate));
				if(i==8) a46vo.setS1_time9(getS1Vo(S1lines.get(8), transdate));
				if(i==9) a46vo.setS1_time10(getS1Vo(S1lines.get(9), transdate));
				if(i==10) a46vo.setS1_time11(getS1Vo(S1lines.get(10), transdate));
				if(i==11) a46vo.setS1_time12(getS1Vo(S1lines.get(11), transdate));
				if(i==12) a46vo.setS1_time13(getS1Vo(S1lines.get(12), transdate));
				if(i==13) a46vo.setS1_time14(getS1Vo(S1lines.get(13), transdate));
				if(i==14) a46vo.setS1_time15(getS1Vo(S1lines.get(14), transdate));
				if(i==15) a46vo.setS1_time16(getS1Vo(S1lines.get(15), transdate));
				if(i==16) a46vo.setS1_time17(getS1Vo(S1lines.get(16), transdate));
				if(i==17) a46vo.setS1_time18(getS1Vo(S1lines.get(17), transdate));
				if(i==18) a46vo.setS1_time19(getS1Vo(S1lines.get(18), transdate));
				if(i==19) a46vo.setS1_time20(getS1Vo(S1lines.get(19), transdate));
				if(i==20) a46vo.setS1_time21(getS1Vo(S1lines.get(20), transdate));
				if(i==21) a46vo.setS1_time22(getS1Vo(S1lines.get(21), transdate));
				if(i==22) a46vo.setS1_time23(getS1Vo(S1lines.get(22), transdate));
				if(i==23) a46vo.setS1_time24(getS1Vo(S1lines.get(23), transdate));
				if(i==24) a46vo.setS1_time25(getS1Vo(S1lines.get(24), transdate));
				if(i==25) a46vo.setS1_time26(getS1Vo(S1lines.get(25), transdate));
				if(i==26) a46vo.setS1_time27(getS1Vo(S1lines.get(26), transdate));
				if(i==27) a46vo.setS1_time28(getS1Vo(S1lines.get(27), transdate));
				if(i==28) a46vo.setS1_time29(getS1Vo(S1lines.get(28), transdate));
				if(i==29) a46vo.setS1_time30(getS1Vo(S1lines.get(29), transdate));
			}
		}
		
		int s1count = S1lines.size();
		//给a46vo 对象赋值结束
		//将对象数据持久化 分别插入  a_dfd_a46_list 和 a_dfd_a46_list_data 表
		insertA46(acarsVo, msgno, a46vo,s1count);
	}
	
	
	protected AcarsECVo_A46_A321 getECVo(String line) throws Exception{
		boolean isRep = false;
		return new AcarsECVo_A46_A321(line, isRep);
	}
	
	/**
	 * 解析S1行数据
	 * @param lineS1
	 * @param transdate
	 * @return
	 * @throws Exception
	 */
	protected AcarsS1Vo_A46_A321 getS1Vo(String lineS1,String transdate) throws Exception{
		boolean isRep = false;
		return new AcarsS1Vo_A46_A321(lineS1, transdate, isRep);
	}
	
	protected void insertA46(HashVO acarsVo,String msgno, AcarsDfdA46Vo_A321 a46vo,int s1count) throws Exception{
		
		String acnum = acarsVo.getStringValue("ACNUM");
		CommDMO dmo = new CommDMO();
		String insertSql1 = "insert into a_dfd_a46_list(ID,MSG,ACNUM,RPTDATE,ESN_1,EHRS_1," 
			+ "ECYC_1,PER_1,ESN_2,EHRS_2,ECYC_2,RECDATETIME)" 
			+ " values(S_A_DFD_A46_LIST.nextval,?,?,?,?,?,?,?,?,?,?,sysdate)";
		
		String insertSql2= "insert into a_dfd_a46_list_data(ID,MSG_NO,"
		    + "ENGQT1,ENGQT2,GATHERTIME,DATA_SEQUENCN)"
		    + " values(S_A_DFD_A46_LIST_Data.nextval,?,?,?,?,?)";
		
		AcarsECVo_A46_A321 ec = a46vo.getEc(); 
		AcarsEEVo_A46_A321 ee = a46vo.getEe();
		AcarsS1Vo_A46_A321 S1 = a46vo.getS1_time1();
		AcarsS1Vo_A46_A321 S2 = a46vo.getS1_time2();
		//报文头数据入库
		headParseClass.insertHeadData(msgno, headVo);
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql1, msgno,acnum,headVo.getDateUtc(),ec.getEsn(),ec.getEhrs(),
				ec.getEcyc(),ec.getPer(),ee.getEsn(),ee.getEhrs(),ee.getEcyc());
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S1.getEngqt1(),S1.getEngqt2(),S1.getDate_utc(),1);	
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S2.getEngqt1(),S2.getEngqt2(),S2.getDate_utc(),2);	
		if(s1count==30){
			AcarsS1Vo_A46_A321 S3 = a46vo.getS1_time3();
			AcarsS1Vo_A46_A321 S4 = a46vo.getS1_time4();
			AcarsS1Vo_A46_A321 S5 = a46vo.getS1_time5();
			AcarsS1Vo_A46_A321 S6 = a46vo.getS1_time6();
			AcarsS1Vo_A46_A321 S7 = a46vo.getS1_time7();
			AcarsS1Vo_A46_A321 S8 = a46vo.getS1_time8();
			AcarsS1Vo_A46_A321 S9 = a46vo.getS1_time9();
			AcarsS1Vo_A46_A321 S10 = a46vo.getS1_time10();
			
			AcarsS1Vo_A46_A321 S11 = a46vo.getS1_time11();
			AcarsS1Vo_A46_A321 S12 = a46vo.getS1_time12();
			AcarsS1Vo_A46_A321 S13 = a46vo.getS1_time13();
			AcarsS1Vo_A46_A321 S14 = a46vo.getS1_time14();
			AcarsS1Vo_A46_A321 S15 = a46vo.getS1_time15();
			AcarsS1Vo_A46_A321 S16 = a46vo.getS1_time16();
			AcarsS1Vo_A46_A321 S17 = a46vo.getS1_time17();
			AcarsS1Vo_A46_A321 S18 = a46vo.getS1_time18();
			AcarsS1Vo_A46_A321 S19 = a46vo.getS1_time19();
			AcarsS1Vo_A46_A321 S20 = a46vo.getS1_time20();
			
			AcarsS1Vo_A46_A321 S21 = a46vo.getS1_time21();
			AcarsS1Vo_A46_A321 S22 = a46vo.getS1_time22();
			AcarsS1Vo_A46_A321 S23 = a46vo.getS1_time23();
			AcarsS1Vo_A46_A321 S24 = a46vo.getS1_time24();
			AcarsS1Vo_A46_A321 S25 = a46vo.getS1_time25();
			AcarsS1Vo_A46_A321 S26 = a46vo.getS1_time26();
			AcarsS1Vo_A46_A321 S27 = a46vo.getS1_time27();
			AcarsS1Vo_A46_A321 S28 = a46vo.getS1_time28();
			AcarsS1Vo_A46_A321 S29 = a46vo.getS1_time29();
			AcarsS1Vo_A46_A321 S30 = a46vo.getS1_time30();
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S3.getEngqt1(),S3.getEngqt2(),S3.getDate_utc(),3);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S4.getEngqt1(),S4.getEngqt2(),S4.getDate_utc(),4);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S5.getEngqt1(),S5.getEngqt2(),S5.getDate_utc(),5);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S6.getEngqt1(),S6.getEngqt2(),S6.getDate_utc(),6);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S7.getEngqt1(),S7.getEngqt2(),S7.getDate_utc(),7);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S8.getEngqt1(),S8.getEngqt2(),S8.getDate_utc(),8);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S9.getEngqt1(),S9.getEngqt2(),S9.getDate_utc(),9);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S10.getEngqt1(),S10.getEngqt2(),S10.getDate_utc(),10);
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S11.getEngqt1(),S11.getEngqt2(),S11.getDate_utc(),11);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S12.getEngqt1(),S12.getEngqt2(),S12.getDate_utc(),12);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S13.getEngqt1(),S13.getEngqt2(),S13.getDate_utc(),13);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S14.getEngqt1(),S14.getEngqt2(),S14.getDate_utc(),14);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S15.getEngqt1(),S15.getEngqt2(),S15.getDate_utc(),15);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S16.getEngqt1(),S16.getEngqt2(),S16.getDate_utc(),16);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S17.getEngqt1(),S17.getEngqt2(),S17.getDate_utc(),17);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S18.getEngqt1(),S18.getEngqt2(),S18.getDate_utc(),18);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S19.getEngqt1(),S19.getEngqt2(),S19.getDate_utc(),19);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S20.getEngqt1(),S20.getEngqt2(),S20.getDate_utc(),20);
			
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S21.getEngqt1(),S21.getEngqt2(),S21.getDate_utc(),21);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S22.getEngqt1(),S22.getEngqt2(),S22.getDate_utc(),22);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S23.getEngqt1(),S23.getEngqt2(),S23.getDate_utc(),23);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S24.getEngqt1(),S24.getEngqt2(),S24.getDate_utc(),24);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S25.getEngqt1(),S25.getEngqt2(),S25.getDate_utc(),25);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S26.getEngqt1(),S26.getEngqt2(),S26.getDate_utc(),26);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S27.getEngqt1(),S27.getEngqt2(),S27.getDate_utc(),27);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S28.getEngqt1(),S28.getEngqt2(),S28.getDate_utc(),28);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S29.getEngqt1(),S29.getEngqt2(),S29.getDate_utc(),29);	
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql2, msgno,S30.getEngqt1(),S30.getEngqt2(),S30.getDate_utc(),30);
		}
	
	}
}
