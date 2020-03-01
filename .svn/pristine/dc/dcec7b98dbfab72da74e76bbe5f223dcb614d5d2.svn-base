package com.apms.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.print.attribute.standard.Fidelity;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.apms.ApmsConst;
import com.apms.bs.datatask.DTTeleGraphExtractTask;
import com.apms.bs.intf.oldsys.HistoryDataExtractByzhl;
import com.apms.bs.intf.oldsys.HistoryDataExtractService;
import com.apms.bs.intf.omis.OmisDataExtractFlightLogService;
import com.apms.bs.intf.omis.OmisDataExtractService;
import com.apms.bs.intf.omis.TeleGraphExtractService;
import com.apms.bs.sms.ShortMassageHwService;
import com.apms.bs.weather.WeatherInfo;
import com.apms.bs.weather.WeatherService;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.bs.dbaccess.dsmgr.DataSourceManager;
import smartx.framework.common.utils.Sys;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaAppModuleConfigIFC;
import smartx.framework.metadata.bs.NovaServerEnvironment;

public class ApmsTaskTest {
	
	public static String configFileUrl = "com/apms/sample/SmartXConfig.xml";
	
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
	 private static void initSomePars(String sysRootPath) throws JDOMException, IOException {

	        InputStream is = ApmsTest.class.getClassLoader().getResourceAsStream(configFileUrl);
	        Document doc = new SAXBuilder().build(is);

	        long ll_1 = System.currentTimeMillis();
	        String str_key = "WebAppRealPath";
	        //TODO 需要在系统全局规划中给出在Sys类中的系统缓冲规划    WebAppRealPath：应用系统物理路径：String
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
	                            //TODO 需要在系统全局规划中给出在Sys类中的系统缓冲规划    str_key：str_descr：str_value
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
	 public static void initContext() throws Exception{
		 initSomePars("");
		 initDataSources();
	 }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommDMO dmo = new CommDMO();
		try {
			//初始化数据库配置信息
			initSomePars("");
			initDataSources();
			OmisDataExtractService om=new OmisDataExtractService();
			//om.extractFlyLog("CA","5", "HGH", "3");
			
			//m.extractFlyLog("CA","5", "HGH", "14");
			//om.computeAircraftflyplan();
			//飞行日志计算
			//om.extractFlightLog("5", "3", "2013-01-01");
			//报文导入
			//om.Eng_compute("2013-04-24", "3", 1);
			//TeleGraphExtractService tg=new TeleGraphExtractService();
			//tg.extractTeleGraph();
			
			//老数据导入飞行日志
			
			HistoryDataExtractByzhl fromsqldata= new HistoryDataExtractByzhl();
			OmisDataExtractFlightLogService omisflylog=new OmisDataExtractFlightLogService();
			omisflylog.extractFlightLog("CA", "3", "2013-1-1");
			
			//fromsqldata.extracthistoryFlightlog("2000-01-01","2014-01-01");
			
			//fromsqldata.extractAcFlyLog("2006-01-01","2007-01-01");//飞行日志分段表
			//fromsqldata.extractAcFlyLog("2007-01-01","2008-01-01");//飞行日志分段表
			//fromsqldata.extractAcFlyLog("2008-01-01","2009-01-01");//飞行日志分段表
			//fromsqldata.extractAcFlyLog("2009-01-01","2010-01-01");//飞行日志分段表
			//fromsqldata.extractAcFlyLog("2010-01-01","2011-01-01");//飞行日志分段表
			//fromsqldata.extractAcFlyLog("2011-01-01","2012-01-01");//飞行日志分段表
			//fromsqldata.extractAcFlyLog("2012-01-01","2015-01-01");//飞行日志分段表
			
			//fromsqldata.extracthistoryFlightlog("2013-05-01", "2013-09-01");//航班计划数据
			//fromsqldata.computeAircraftflyplan();//计算航班计划里面的数据
			
			//fromsqldata.extractAcFlyLog("2007-04-30","2007-06-02");//飞行日志分段表
			
			//fromsqldata.extractAcFlydaily("2007-04-30","2007-06-02");//飞行日志daily
			
			//fromsqldata.extractEngFlydaily("2007-04-30","2007-06-02");//发动机DAILY
			
			//fromsqldata.computeAcflydata();//计算飞行日志daily的增量
			
			//fromsqldata.computeEngdata();//计算发动日志daily的增量
			
			//fromsqldata.computeAcflylogdata();//计算飞行日志的分段总计
			
			//fromsqldata.computeEngflylogdata();//计算发动机分段表里面的总计数据
			
			//fromsqldata.compenginfo();
			/*
			fromsqldata.extractACFlydata("2000-01-01","2006-01-01");
			fromsqldata.extractACFlydata("2006-01-01","2007-01-01");
			fromsqldata.extractACFlydata("2007-01-01","2008-01-01");
			fromsqldata.extractACFlydata("2008-01-01","2009-01-01");
			fromsqldata.extractACFlydata("2009-01-01","2010-01-01");
			fromsqldata.extractACFlydata("2010-01-01","2011-01-01");
			fromsqldata.extractACFlydata("2011-01-01","2015-01-01");
			//fromsqldata.extractACFlydata("2001-01-01","2007-01-01");
			//fromsqldata.extractENGdaily();
			fromsqldata.addcompute();
			*/
			//fromsqldata.extractACdailydata(null, null);
//			fromsqldata.addflydailycompute();
			//数据库操作
			
			//DbAccessSample db = new DbAccessSample();
			//db.readData();
			//db.readDataUseParam();
			
//			db.insertData();
//			db.queryLogData(ApmsConst.DS_DEFAULT,"select operation,id,time,source from pub_log where source = 'testuser'");
//			db.updateData();
//			db.queryLogData(ApmsConst.DS_DEFAULT,"select operation,id,time,source from pub_log where source = 'testuser'");
//			db.deleteData();
//			db.queryLogData(ApmsConst.DS_DEFAULT,"select operation,id,time,source from pub_log where source = 'testuser'");
			
			
			//db.jdbcTest();
			//weatherTest();
			//parseTest();
			
			//oltGraphExtract();
			//smsSendTest();
			
			//parseTest();
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//释放数据库连接，非常重要，数据库连接是有限的！！！！！！！！！
			//每次事务操作完成后，均应该释放连接
			//dmo.releaseContext(ApmsConst.DS_DEFAULT);
			dmo.releaseContext();
		}
	}
	
	//提取老系统中报文 数据
	public static void oltGraphExtract() throws Exception{
		HistoryDataExtractService service = new HistoryDataExtractService();
		
		service.extractTeleGraph();
	}
	
	public static void weatherTest() throws Exception{
		WeatherService ws = new WeatherService();
		
		String cityCode = "101210101";//杭州
		WeatherInfo wi=  ws.getWeatherByCity(cityCode);
		
		ws.updateWeatherData(cityCode, wi);
	}
	
	public static void parseTest() throws Exception{
//		DataPraseService service = DataPraseService.getInstance();
//		
//		//service.praseTeleGraphA23();
//		service.computeTeleGraphA23();
	}
	
	public static void smsSendTest() throws Exception{
		String srcAddr = "18916752189";
		String destAddr = "18916752189";
		String content = "短信测试群发";
		
		ArrayList<String> destList = new ArrayList<String>();
		destList.add("18916752189");
		destList.add("18658176006");
		destList.add("13957157885");
		
		ShortMassageHwService service = ShortMassageHwService.getInstance();//获取服务实例
		
		service.send(srcAddr, destList, content);
	}

}
