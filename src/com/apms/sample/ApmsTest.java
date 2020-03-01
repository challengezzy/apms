package com.apms.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.bs.SysConst;
import smartx.framework.common.bs.dbaccess.dsmgr.DataSourceManager;
import smartx.framework.common.utils.Sys;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaAppModuleConfigIFC;
import smartx.framework.metadata.bs.NovaServerEnvironment;

import com.apms.ApmsConst;
import com.apms.bs.EnginFormService;
import com.apms.bs.acarsreport.AcarsRptReparsingService;
import com.apms.bs.apu.ApuPdiService;
import com.apms.bs.datacompute.A01CfmDataComputeService;
import com.apms.bs.datacompute.A01IaevDataComputeService;
import com.apms.bs.datacompute.A04CfmDataComputeService;
import com.apms.bs.datacompute.A04IaevDataComputeService;
import com.apms.bs.datacompute.A49IaevDataComputeService;
import com.apms.bs.datacompute.DataComputeService;
import com.apms.bs.dataprase.DataParseClassFactory;
import com.apms.bs.dataprase.DataParseService;
import com.apms.bs.dataprase.DataPreParseService;
import com.apms.bs.email.EmailService;
import com.apms.bs.engine.EngineFlightLogService;
import com.apms.bs.engine.IaevEngineChartDetail;
import com.apms.bs.flight.FlightScheduleSynService;
import com.apms.bs.intf.ams.AmsPartSwapExtractService;
import com.apms.bs.intf.ams.AmsWorkPlaneExtractService;
import com.apms.bs.intf.foc.FocAcstopSynService;
import com.apms.bs.intf.oldsys.HistoryDataExtractService;
import com.apms.bs.intf.omis.DDInfoExtractService;
import com.apms.bs.intf.omis.OmisDataExtractFlightLogServiceZzy;
import com.apms.bs.intf.omis.OmisFlightScheduleService;
import com.apms.bs.intf.omis.TeleGraphExtractService;
import com.apms.bs.remind.RemindManageService;
import com.apms.bs.sms.ShortMassageHwService;
import com.apms.bs.util.DateUtil;
import com.apms.bs.vibration.VibrationService;
import com.apms.bs.weather.WeatherInfo;
import com.apms.bs.weather.WeatherService;
import com.apms.cache.ApmsServerCache;
import com.apms.cache.FlightCache;
import com.apms.vo.ApmsVarConst;

public class ApmsTest {
	
	public static String configFileUrl = "com/apms/sample/SmartXConfig.xml";
	
	private static void initSomePars(String sysRootPath) throws JDOMException, IOException {

        InputStream is = ApmsTest.class.getClassLoader().getResourceAsStream(configFileUrl);
        Document doc = new SAXBuilder().build(is);

        long ll_1 = System.currentTimeMillis();
        
        NovaServerEnvironment.getInstance().put(SysConst.KEY_SYSTEMCONFIGFILE,"系统配置文件内容", doc);
		Sys.putInfo("NOVA2_SYS_ROOTPATH", "com/apms/sample/");
        
        String str_key = "WebAppRealPath";
        // 需要在系统全局规划中给出在Sys类中的系统缓冲规划    WebAppRealPath：应用系统物理路径：String
        Sys.putInfo(str_key, sysRootPath);
        NovaServerEnvironment.getInstance().put(str_key, "本应用的绝对地址", sysRootPath); //
        NovaLogger.getLogger(ApmsTest.class).debug("[" + str_key + "] = [" + sysRootPath + "]");
          

        try {
            java.util.List initparams = doc.getRootElement().getChildren("init-param");
            if (initparams != null) {
                for (int i = 0; i < initparams.size(); i++) {
                    if (initparams.get(i) instanceof org.jdom.Element) {
                        org.jdom.Element param = (org.jdom.Element) initparams.get(i);
                        if (param != null) {
                            str_key = param.getAttributeValue("key");
                            String str_value = param.getAttributeValue("value");
                            String str_descr = param.getAttributeValue("descr");                            
                            NovaLogger.getLogger(ApmsTest.class).debug("配置参数："+str_key+" 取值："+str_value+" 解释："+str_descr);                            
                            //需要在系统全局规划中给出在Sys类中的系统缓冲规划    str_key：str_descr：str_value
                            Sys.putInfo(str_key, str_value);
                            NovaServerEnvironment.getInstance().put(str_key, str_descr, str_value); // 往缓存中送入...
                            
                            NovaLogger.getLogger(ApmsTest.class).debug("[" + str_key + "] = [" + str_value + "]");
                        }
                    }
                }
                //专门处理一下AppModule
                String str_value=(String)Sys.getInfo("APPMODULE");
                if(str_value!=null) {//old "AppModule"
                    try {
                    	String tmp = ((NovaAppModuleConfigIFC)Class.forName(str_value.trim()).newInstance()).getAppModuleName();
                    	NovaServerEnvironment.getInstance().setAppModuleName(new String[]{tmp});
                    	Sys.putInfo("APPMODULE",tmp);
                    	NovaLogger.getLogger(ApmsTest.class).debug("获得模块名称："+tmp);
                    } catch (Exception ex) {
                    	NovaLogger.getLogger(ApmsTest.class).debug("获得模块名称："+str_value);                        
                    }
                }
                
            }
            //2.secondprojectclient
            org.jdom.Element secondProjectClient = doc.getRootElement().getChild("secondprojectclient");
            if (secondProjectClient != null) {
                Vector initClients = new Vector();
                java.util.List sources = secondProjectClient.getChildren(); // 得到所有子结点!!
                for (int i = 0; i < sources.size(); i++) { // 遍历所有子结点!!
                    if (sources.get(i) instanceof org.jdom.Element) { //
                        org.jdom.Element node = (org.jdom.Element) sources.get(i); //
                        initClients.add(node.getText()); // 得到属性
                    }
                }
                NovaServerEnvironment.getInstance().put("CLIENTINIT", initClients); // 往缓存中送入...
            }
            //3.secondprojectafterlogin
            org.jdom.Element secondProjectAfterLogin = doc.getRootElement().getChild("secondprojectafterlogin");
            if(secondProjectAfterLogin!=null){
            	Vector initClients = new Vector();
                java.util.List sources = secondProjectAfterLogin.getChildren(); // 得到所有子结点!!
                for (int i = 0; i < sources.size(); i++) { // 遍历所有子结点!!
                    if (sources.get(i) instanceof org.jdom.Element) { //
                        org.jdom.Element node = (org.jdom.Element) sources.get(i); //
                        initClients.add(node.getText()); // 得到属性
                    }
                }
                NovaServerEnvironment.getInstance().put("CLIENTAFTERLOGIN", initClients); // 往缓存中送入...
            }
            
            //4.secondprojectpanelclient
            org.jdom.Element secondProjectClientPanelInit = doc.getRootElement().getChild("secondprojectpanelclient");
            if (secondProjectClientPanelInit != null) {
                Vector initPanelClients = new Vector();
                java.util.List sources = secondProjectClientPanelInit.getChildren(); // 得到所有子结点!!
                for (int i = 0; i < sources.size(); i++) { // 遍历所有子结点!!
                    if (sources.get(i) instanceof org.jdom.Element) { //
                        org.jdom.Element node = (org.jdom.Element) sources.get(i); //
                        initPanelClients.add(node.getText()); // 得到属性
                    }
                }
                NovaServerEnvironment.getInstance().put("CLIENTPANELINIT", initPanelClients); // 往缓存中送入...
            }
            
            //5.secondprojectbeforelogout
            org.jdom.Element secondProjectClientBeforeLogout = doc.getRootElement().getChild("secondprojectbeforelogout");
            if (secondProjectClientBeforeLogout != null) {
                Vector initPanelClients = new Vector();
                java.util.List sources = secondProjectClientBeforeLogout.getChildren(); // 得到所有子结点!!
                for (int i = 0; i < sources.size(); i++) { // 遍历所有子结点!!
                    if (sources.get(i) instanceof org.jdom.Element) { //
                        org.jdom.Element node = (org.jdom.Element) sources.get(i); //
                        initPanelClients.add(node.getText()); // 得到属性
                    }
                }
                NovaServerEnvironment.getInstance().put("CLIENTBEFORELOGOUT", initPanelClients); // 往缓存中送入...
            }

            org.jdom.Element defaultUser = doc.getRootElement().getChild("default-user");

            long ll_2 = System.currentTimeMillis();
            NovaLogger.getLogger(ApmsTest.class).debug("初始化基本参数结束,耗时[" + (ll_2 - ll_1) + "]");
        } catch (Throwable tr) {
            tr.printStackTrace();
        }

    }
	
	private static void initDataSources() throws JDOMException, IOException{
		long ll_1 = System.currentTimeMillis();
        NovaLogger.getLogger(ApmsTest.class).debug("开始初始化数据库连接池...");
        InputStream is = ApmsTest.class.getClassLoader().getResourceAsStream(configFileUrl);
        Document doc = new SAXBuilder().build(is);
        Element datasources = doc.getRootElement().getChild("datasources"); // 得到datasources子结点!!
        if (datasources != null) {
            List sources = datasources.getChildren("datasource"); // 得到所有子结点!!
            DataSourceManager.initDS(sources);
            
            //服务器端设置变量
            NovaServerEnvironment.getInstance().put("defaultdatasource", DataSourceManager.getDefaultDS()); // 设置默认数据源!!
            String[] keys=DataSourceManager.getDataSources();
            NovaServerEnvironment.getInstance().put("ALLDATASOURCENAMES",keys );
            for(int i=0;i<keys.length;i++){
                NovaServerEnvironment.getInstance().put(keys[i],DataSourceManager.getDataSourceUrl(keys[i]) ); //
            }
        }
        long ll_2 = System.currentTimeMillis();
        NovaLogger.getLogger(ApmsTest.class).debug("初始化数据库连接池结束,耗时[" + (ll_2 - ll_1) + "]");
	}
	
	 public static void initContext() throws Exception{
		 initSomePars("");
		 initDataSources();
	 }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommDMO dmo = null;
		try {
			//初始化数据库配置信息
			initSomePars("");
			initDataSources();
			dmo = new CommDMO();
			
			//DDInfoExtractService dd =new DDInfoExtractService();
//			dd.extractDDInfoRevise();
			//数据库操作
//			DbAccessSample db = new DbAccessSample();
//			db.readData();
//			db.readDataUseParam();
//			
//			db.insertData();
//			db.queryLogData(ApmsConst.DS_DEFAULT,"select operation,id,time,source from pub_log where source = 'testuser'");
//			db.updateData();
//			db.queryLogData(ApmsConst.DS_DEFAULT,"select operation,id,time,source from pub_log where source = 'testuser'");
//			db.deleteData();
//			db.queryLogData(ApmsConst.DS_DEFAULT,"select operation,id,time,source from pub_log where source = 'testuser'");
			
			DataParseClassFactory parseFactory = DataParseClassFactory.getInstance();
			
			//testEnginLogExtract();
			
			//workplanExtract();
			
			//flightLogExtract();
			
			//synFlightSch();
			
			//focAcstop();
			
			//parseTest();
			
			//partSwapExtract();
			
			//computePdiPredict();
			
//			computeEngineTest();
			
			//engFlylogCompute();
			
//			String datastr = DateUtil.getDateStr(new Date(), "MM-dd HH:mm");
//			System.out.println(datastr);
//			int[] pars = {10,8,9,7,4};
//			fun("pre " , pars);
			
//			DDInfoExtractService ddInfo = new DDInfoExtractService();
//			ddInfo.extractDDInfo();
			
//			
			//mailTest();
			dmo.commit(ApmsConst.DS_APMS);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//释放数据库连接，非常重要，数据库连接是有限的！！！！！！！！！
			//每次事务操作完成后，均应该释放连接
			//dmo.releaseContext(ApmsConst.DS_DEFAULT);
			dmo.releaseContext();
		}
	}
	
	public static void computePdiPredict() throws Exception{
		ApuPdiService service = new ApuPdiService();
		int zeroLimit = 100;
		int oldPdiLimit = 500;
		service.computeAllPdiPredict(15,12,zeroLimit,oldPdiLimit);
		
//		int periodSpan = 12;
//		int repairSpan = 2;
//		int backupNum = 2;
//		double pdiLimit = 1.2;
//		String baseOrgId = "3";
//		String apumodel = "GTCP131-9A";
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		service.computeSwapPredict(baseOrgId,periodSpan, repairSpan, backupNum, pdiLimit,apumodel,resultMap);
	}
	
	
	public static void partSwapExtract() throws Exception{
		AmsPartSwapExtractService service = new AmsPartSwapExtractService();
		service.extractData();
	}
	
	public static void focAcstop() throws Exception{
		FocAcstopSynService foc = new FocAcstopSynService();
		foc.synAcStopFromFoc();
	}
	
	public static void flightLogExtract() throws Exception{
//		OmisDataExtractFlightLogServiceZzy serivce = new OmisDataExtractFlightLogServiceZzy();
//		String baseorgid = "3";
//		String dayoff = "4";
//		
//		serivce.extractFlightLog( baseorgid, dayoff);
		
	}
	
	public static void engFlylogCompute() throws Exception{
		EngineFlightLogService engFlylogService = new EngineFlightLogService();
		int num = engFlylogService.extractFlightLog(null,null);
	}
	
	public static void workplanExtract() throws Exception{
		AmsWorkPlaneExtractService service = new AmsWorkPlaneExtractService();
		service.extractWorkPlan();
		
	}
	
	public static void testEnginLogExtract() throws Exception{
		String engsn = "V15783";//B1637飞机
		Date startdate = DateUtil.StringToDate("2004-12-21","yyyy-MM-dd");
		Date enddate = DateUtil.StringToDate("2016-6-13","yyyy-MM-dd");
		EngineFlightLogService engFlylogService = new EngineFlightLogService();
		//engFlylogService.extractSingleEngFlightLog(engsn,startdate,enddate);
		
		engFlylogService.extractFlightLog(null, null);
		
//		Date date = DateUtil.StringToDate("2015-12-14", "yyyy-MM-dd");
//		CommDMO dmo = new CommDMO();
//		String sql = "update l_eng_flightlog_daily set csn=csn+?  where id=1956269 and fidate=?";
//		
//		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sql, 3,date);
//		dmo.commit(ApmsConst.DS_APMS);
	}
	
	public static void dataPreParse() throws Exception{
		DataPreParseService service = new DataPreParseService();
		service.dfdDataExtract();
		service.cfdDataExtract();
	}
	
	public static void tipCreate() throws Exception{
		RemindManageService remind = new RemindManageService();
		remind.generateRemindIns();
	}
	
	public static void fun(String prefix, int ...params){
		for(int p : params){
			System.out.println(prefix+ " " +p);
		}
	}
	
	public static void synFlightSch() throws Exception{
		ApmsServerCache server = ApmsServerCache.getInstance();
		server.refreshCache();
		FlightCache flightC = FlightCache.getInstance();
		flightC.refreshCache();
		
		FlightScheduleSynService synService = new FlightScheduleSynService();
		synService.synFlightSchedule("2");
	}
	
	public static void extractOmisFlithSchTest() throws Exception{
		OmisFlightScheduleService service = new OmisFlightScheduleService();
		service.extractCurrentFlight("7");
		//service.extractHistoryFlight();
	}
	
	public static void vibrationTest() throws Exception{
		VibrationService vib = new VibrationService();
		vib.vibrationAnalyse("B6031","2013-04-26:00007");
	}
	
	public static void mailTest() throws Exception{
		EmailService email = new EmailService();
		email.sendMail("jerry.zhang@demand-driven.cn", "邮件测试", "测试内容\n换行了！");
	}
	
	//提取老系统中报文 数据
	public static void oltGraphExtract() throws Exception{
		HistoryDataExtractService service = new HistoryDataExtractService();
		
		service.extractTeleGraph();
	}
	
	//接口报文数据抽取
	public static void extractTeleGraph() throws Exception{
		TeleGraphExtractService service = new TeleGraphExtractService();
		service.extractTeleGraph();
	}
	
	public static void weatherTest() throws Exception{
		WeatherService ws = new WeatherService();
		
		String cityCode = "101210101";//杭州
		WeatherInfo wi=  ws.getWeatherByCity(cityCode);
		
		ws.updateWeatherData(cityCode, wi);
	}
	
	public static void parseTest() throws Exception{
		DataParseService parseService = DataParseService.getInstance();
		AcarsRptReparsingService reparseService = new AcarsRptReparsingService();
//		String begin = "2016-05-01 23:38:36";
//		String end = "2016-07-06 23:38:36";
//		Date beginDate=DateUtil.StringToDate(begin, "yyyy-MM-dd HH:mm:ss");
//		Date endDate=DateUtil.StringToDate(end, "yyyy-MM-dd HH:mm:ss");
		//消除已解析数据
//		reparseService.clearParsedReport(ApmsVarConst.RPTNO_A14,null,beginDate,endDate);
//		reparseService.clearComputedReport(ApmsVarConst.RPTNO_A33,"B1877", null, null);
		
//		String begin = "2010-10-25 23:38:36";
//		String end = "2011-4-5 23:38:36";
		
		//parseService.parseApuAcarsReport("B6676",null,null);
//		parseService.parseApuAcarsReport(null,null,null);
//		
		String begin = "2016-06-01 23:38:36";
		String end = "2017-01-01 23:38:36";
		String msgno = "382301823";//"365714842";
		String rptno = "A36";
		String acnum = "B6032";
		boolean isOrderby = true; //36号报文解析要排序
		parseService.parseAcarsReport(null, rptno, null,begin,end,isOrderby);//"286494780"
//		parseService.parseCFDAcarsReport(null, rptno, null,null,null);
//		parseService.
//		ApuRunLogCompute runlog = new ApuRunLogCompute();
//		runlog.forecastRunLogDaily("2056");
		
	}
	
	public static void computeTest() throws Exception{
		DataComputeService service = DataComputeService.getInstance();
		AcarsRptReparsingService reparseService = new AcarsRptReparsingService();
		
		String begin = "2014-11-01";
//		Date beginDate = DateUtil.StringToDate(begin, "yyyy-MM-dd");
		reparseService.clearComputedReport(ApmsVarConst.RPTNO_A34, "B6918","null",DateUtil.moveDay(new Date(), -100),new Date());
		new CommDMO().commit(ApmsConst.DS_APMS);
//		
		
		service.computeA34Data("B6846");
		
		
	}
	
	
	public static void computeEngineTest() throws Exception{
//		A01CfmDataComputeService a01CfmService = new A01CfmDataComputeService();
//		int num = a01CfmService.computeA01Data("B2210");
		
//		A04CfmDataComputeService a04cfmService = new A04CfmDataComputeService();
//		a04cfmService.computeA04Data("B1639");
		
//		A01IaevDataComputeService a01iaeService = new A01IaevDataComputeService();
//		a01iaeService.computeA01Data("B6733");
		
//		AcarsRptReparsingService reparseService = new AcarsRptReparsingService();
//		reparseService.clearComputedReport(ApmsVarConst.RPTNO_A49, "B1879",null,null,null);
//		new CommDMO().commit(ApmsConst.DS_APMS);
		
//		A49IaevDataComputeService a49iaeService = new A49IaevDataComputeService();
//		a49iaeService.computeA49Data("B1879");
		
		
		A04IaevDataComputeService a04iaeService = new A04IaevDataComputeService();
		a04iaeService.computeA04Data("B6733");
	}
	
	
	public static void smsSendTest() throws Exception{
		String srcAddr = "18916752189";
		String destAddr = "18916752189";
		String content = "短信测试群发";
		
		ArrayList<String> destList = new ArrayList<String>();
		destList.add("18916752189");
		destList.add("18658176006");
		
		ShortMassageHwService service = ShortMassageHwService.getInstance();//获取服务实例
		
		service.send(srcAddr, destList, content);
	}

}
