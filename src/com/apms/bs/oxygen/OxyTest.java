package com.apms.bs.oxygen;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.bs.dbaccess.dsmgr.DataSourceManager;
import smartx.framework.common.utils.Sys;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaAppModuleConfigIFC;
import smartx.framework.metadata.bs.NovaServerEnvironment;

import com.apms.ApmsConst;

public class OxyTest {
	
	public static String configFileUrl = "com/apms/sample/SmartXConfig.xml";
	
	private static void initDataSources() throws JDOMException, IOException{
		long ll_1 = System.currentTimeMillis();
        NovaLogger.getLogger(OxyTest.class).debug("开始初始化数据库连接池...");
        InputStream is = OxyTest.class.getClassLoader().getResourceAsStream(configFileUrl);
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
        NovaLogger.getLogger(OxyTest.class).debug("初始化数据库连接池结束,耗时[" + (ll_2 - ll_1) + "]");
	}
	 private static void initSomePars(String sysRootPath) throws JDOMException, IOException {

	        InputStream is = OxyTest.class.getClassLoader().getResourceAsStream(configFileUrl);
	        Document doc = new SAXBuilder().build(is);

	        long ll_1 = System.currentTimeMillis();
	        String str_key = "WebAppRealPath";
	        //TODO 需要在系统全局规划中给出在Sys类中的系统缓冲规划    WebAppRealPath：应用系统物理路径：String
	        Sys.putInfo(str_key, sysRootPath);
	        NovaServerEnvironment.getInstance().put(str_key, "本应用的绝对地址", sysRootPath); //
	        NovaLogger.getLogger(OxyTest.class).debug("[" + str_key + "] = [" + sysRootPath + "]");
	          

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
	                            NovaLogger.getLogger(OxyTest.class).debug("配置参数："+str_key+" 取值："+str_value+" 解释："+str_descr);                            
	                            //TODO 需要在系统全局规划中给出在Sys类中的系统缓冲规划    str_key：str_descr：str_value
	                            Sys.putInfo(str_key, str_value);
	                            NovaServerEnvironment.getInstance().put(str_key, str_descr, str_value); // 往缓存中送入...
	                            
	                            NovaLogger.getLogger(OxyTest.class).debug("[" + str_key + "] = [" + str_value + "]");
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
	                    	NovaLogger.getLogger(OxyTest.class).debug("获得模块名称："+tmp);
	                    } catch (Exception ex) {
	                    	NovaLogger.getLogger(OxyTest.class).debug("获得模块名称："+str_value);                        
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
	            NovaLogger.getLogger(OxyTest.class).debug("初始化基本参数结束,耗时[" + (ll_2 - ll_1) + "]");
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
			
			OxygenChangeService ser = new OxygenChangeService();
			String baseOrgId = "3";//杭州维修基地
			String apcode3 = "HGH";
			int maxChgNum = 2;
			int preDayNum = 7;
			int presChangePoint = 1600;
			
			//ser.changePredict(baseOrgId, apcode3, maxChgNum, preDayNum, presChangePoint);
			
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

}
