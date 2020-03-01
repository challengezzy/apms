package com.apms.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.bs.SysConst;
import smartx.framework.common.bs.dbaccess.dsmgr.DataSourceManager;
import smartx.framework.common.utils.Sys;
import smartx.framework.common.vo.NovaLogger;
import smartx.framework.metadata.bs.NovaServerEnvironment;

import com.apms.ApmsConst;
import com.apms.bs.acarsreport.AcarsRptReparsingService;
import com.apms.bs.datacompute.DataComputeService;
import com.apms.bs.dataprase.DataParseService;
import com.apms.bs.util.DateUtil;
import com.apms.vo.ApmsVarConst;

/**
 * 测试报文解析的入口类
 * 
 * @author jerry
 * @date Apr 9, 2017
 */
public class ApmsComputeTest {

	public static String configFileUrl = "com/apms/sample/SmartXConfig.xml";

	private static void initDataSources() throws JDOMException, IOException {

		long ll_1 = System.currentTimeMillis();
		InputStream is = ApmsComputeTest.class.getClassLoader().getResourceAsStream(configFileUrl);
		Document doc = new SAXBuilder().build(is);

		NovaServerEnvironment.getInstance().put(SysConst.KEY_SYSTEMCONFIGFILE, "系统配置文件内容", doc);
		Sys.putInfo("NOVA2_SYS_ROOTPATH", "com/apms/sample/");

		String sysRootPath = "d:";
		String str_key = "WebAppRealPath";
		// 需要在系统全局规划中给出在Sys类中的系统缓冲规划 WebAppRealPath：应用系统物理路径：String
		Sys.putInfo(str_key, sysRootPath);
		NovaServerEnvironment.getInstance().put(str_key, "本应用的绝对地址", sysRootPath); //
		NovaLogger.getLogger(ApmsComputeTest.class).debug("[" + str_key + "] = [" + sysRootPath + "]");

		List initparams = doc.getRootElement().getChildren("init-param");
		if (initparams != null) {
			for (int i = 0; i < initparams.size(); i++) {
				if (initparams.get(i) instanceof org.jdom.Element) {
					org.jdom.Element param = (org.jdom.Element) initparams.get(i);
					if (param != null) {
						str_key = param.getAttributeValue("key");
						String str_value = param.getAttributeValue("value");
						String str_descr = param.getAttributeValue("descr");
						NovaLogger.getLogger(ApmsComputeTest.class).debug("配置参数：" + str_key + " 取值：" + str_value + " 解释：" + str_descr);
						// 需要在系统全局规划中给出在Sys类中的系统缓冲规划 str_key：str_descr：str_value
						Sys.putInfo(str_key, str_value);
						NovaServerEnvironment.getInstance().put(str_key, str_descr, str_value); // 往缓存中送入...

						NovaLogger.getLogger(ApmsComputeTest.class).debug("[" + str_key + "] = [" + str_value + "]");
					}
				}
			}

		}

		Element datasources = doc.getRootElement().getChild("datasources"); // 得到datasources子结点!!
		NovaLogger.getLogger(ApmsComputeTest.class).debug("开始初始化数据库连接池...");
		if (datasources != null) {
			List sources = datasources.getChildren("datasource"); // 得到所有子结点!!
			DataSourceManager.initDS(sources);

			// 服务器端设置变量
			NovaServerEnvironment.getInstance().put("defaultdatasource", DataSourceManager.getDefaultDS()); // 设置默认数据源!!
			String[] keys = DataSourceManager.getDataSources();
			NovaServerEnvironment.getInstance().put("ALLDATASOURCENAMES", keys);
			for (int i = 0; i < keys.length; i++) {
				NovaServerEnvironment.getInstance().put(keys[i], DataSourceManager.getDataSourceUrl(keys[i])); //
			}
		}
		long ll_2 = System.currentTimeMillis();
		NovaLogger.getLogger(ApmsComputeTest.class).debug("初始化数据库连接池结束,耗时[" + (ll_2 - ll_1) + "]");
	}

	public static void initContext() throws Exception {
		// initSomePars("");
		initDataSources();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 初始化数据库配置信息
			// initSomePars("");
			initDataSources();

			computeTest();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放数据库连接，非常重要，数据库连接是有限的！！！！！！！！！
			// 每次事务操作完成后，均应该释放连接
			// dmo.releaseContext(ApmsConst.DS_DEFAULT);
		}
	}

	public static void computeTest() throws Exception{
		DataComputeService service = DataComputeService.getInstance();
		AcarsRptReparsingService reparseService = new AcarsRptReparsingService();
		
		String begin = "2014-11-01";
//		Date beginDate = DateUtil.StringToDate(begin, "yyyy-MM-dd");
		//reparseService.clearComputedReport(ApmsVarConst.RPTNO_A34, "B6918","null",DateUtil.moveDay(new Date(), -100),new Date());
		//new CommDMO().commit(ApmsConst.DS_APMS);
//		
		
		service.computeA13Data("B6670");
		
		
	}
}
