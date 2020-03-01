package com.apms.bs.dataprase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.apms.ApmsConst;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.HashVO;
import smartx.framework.common.vo.NovaLogger;

/**
 * 报文解析服务工厂类,生成具体的解析类
 * @author zzy
 *
 */
public class DataParseClassFactory {
	
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private static DataParseClassFactory factory;
	
	private String parseConfMtcode = "ReportDataParseClassImpl";//报文解析配置元数据编码
	
	private Map<String, String> headParseMap = new HashMap<String, String>();//key=acmode, value=实现类	
	private Map<String, String> contentParseMap = new HashMap<String, String>();//key=fdimuversion+"_"+rptno,value=实现类名
	
	//实现类对象映射表
	private Map<String, ReportHeadParseClass> headClassMap = new HashMap<String, ReportHeadParseClass>();
	private Map<String, ReportContentParseClass> contentClassMap = new HashMap<String, ReportContentParseClass>();
	
	private DataParseClassFactory() throws Exception{
		//初始化，
		initParseConifg();
	}
	
	/**
	 * 单例类
	 * @return
	 * @throws Exception 
	 */
	public static DataParseClassFactory getInstance() throws Exception{
		if(factory == null){
			try{
				factory = new DataParseClassFactory();
			}catch (Exception e) {
				e.printStackTrace();
				throw new Exception("DataParseClassFactory类初始化配置失败！");
			}
		}
		
		return factory;
	}
	
	/**
	 * 根据机型获取报文头解析实现类
	 * @param acmodel
	 * @return
	 * @throws Exception
	 */
	public ReportHeadParseClass getHeadParseClass(String acmodel) throws Exception{
		String headClassName = headParseMap.get(acmodel);
		String logStr;
		if(headClassName == null){
			logStr = "未找到机型["+acmodel+"]对应的报文头解析实现类!";
			logger.warn(logStr);
			//throw new Exception(logStr);
			return null;
		}
		
		ReportHeadParseClass headClassObj = null;
		
		//先从缓存中获取，如果没有实例化，则实例化一个实例
		//headClassObj = headClassMap.get(headClassName);
		
		//多个报文编号同时解析，会出现head数据异常，这个每次新生成一个解析类对象
		try{
			Class<?> c = Class.forName(headClassName);
			headClassObj = (ReportHeadParseClass) c.newInstance();
			
		}catch(Exception e){
			logStr = "报文解析类["+headClassName+"]实例化异常！！";
			logger.error(logStr);
			throw new Exception(logStr);
		}
		
//		if(headClassObj == null){
//			//TODO 只有一个解析对象，没有判断使用状态，不支持多线程
//			try{
//				Class<?> c = Class.forName(headClassName);
//				headClassObj = (ReportHeadParseClass) c.newInstance();
//				
//				headClassMap.put(headClassName, headClassObj);
//			}catch(Exception e){
//				logStr = "报文解析类["+headClassName+"]实例化异常！！";
//				logger.error(logStr);
//				throw new Exception(logStr);
//			}
//		}
		
		return headClassObj;
	}
	
	
	/**
	 * 获取报文内容解析实现类
	 * @param fdimuVersion 机上软件版本
	 * @param rptno 报文编号
	 * @return
	 */
	public ReportContentParseClass getDataParseClass(String fdimuVersion,String rptno) throws Exception{
		ReportContentParseClass parseClassObj = null;
		
		String key = fdimuVersion +"_" + rptno;
		String className = contentParseMap.get(key);
		
		String logStr;
		if(className == null){
			logStr = "未找到软件版本["+fdimuVersion+"]、报文编号["+rptno+"]对应的报文内容解析实现类!";
			logger.warn(logStr);
			// throw new Exception(logStr); 不抛出异常，返回null的对象
			return null;
		}
		
		//先从缓存中获取，如果没有实例化，则实例化一个实例
		//parseClassObj = contentClassMap.get(className); 从缓存中获取，有多线程同步问题
		if(parseClassObj == null){
			//TODO 只有一个解析对象，没有判断使用状态，不支持多线程
			try{
				Class<?> c = Class.forName(className);
				parseClassObj = (ReportContentParseClass) c.newInstance();
				
				//contentClassMap.put(className, parseClassObj);
			}catch(Exception e){
				logStr = "报文内容解析类["+className+"]实例化异常！！";
				logger.error(logStr);
				throw new Exception(logStr);
			}
		}
		
		return parseClassObj;
	}
	
	/**
	 * 初始化解析配置,配置数据由数据库表中进行读取
	 * @throws Exception
	 */
	private void initParseConifg() throws Exception{
		CommDMO dmo = new CommDMO();
		String sqlHead = "SELECT ACMODEL,PARSECLASS FROM B_RPTPARSE_CFG_HEAD T WHERE T.ISVALID=1";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS,sqlHead);
		
		//报文头解析
		for (int i = 0; i < vos.length; i++) {
			HashVO vo = vos[i];
			String acmode = vo.getStringValue("ACMODEL");
			String headParseClass = vo.getStringValue("PARSECLASS");
			// 加入到Map中
			headParseMap.put(acmode, headParseClass);
		}
		
		logger.info("报文头解析配置初始化完成，共[" + vos.length + "]条");
		
		//报文内容解析配置
		String sqlContent = "SELECT FDIMUVERSION,RPTNO,PARSECLASS FROM B_RPTPARSE_CFG_CONTENT T WHERE T.ISVALID=1";
		HashVO[] vosContent = dmo.getHashVoArrayByDS(ApmsConst.DS_APMS, sqlContent);

		for (int i = 0; i < vosContent.length; i++) {
			HashVO vo = vosContent[i];
			String version = vo.getStringValue("FDIMUVERSION");
			String rptno = vo.getStringValue("RPTNO");
			String contentparseclass = vo.getStringValue("PARSECLASS");
			
			//加入到内容解析类MAP
			contentParseMap.put(version+"_"+rptno, contentparseclass);
		}
		logger.info("报文内容解析配置初始化完成，共["+vosContent.length+"]条");
			
	}
	
	//初始化解析配置
	private void initParseConifg_old() throws Exception{
		CommDMO dmo = new CommDMO();
		String sql = "select ID,NAME,CODE,CONTENT from pub_metadata_templet mt where mt.code = ?";
		HashVO[] vos = dmo.getHashVoArrayByDS(ApmsConst.DS_DEFAULT,sql,parseConfMtcode);
		if(vos.length > 0){
			String confContent = vos[0].getStringValue("CONTENT");
			Document doc = DocumentHelper.parseText(confContent);
			Element root = doc.getRootElement();
			
			//初始化报文头解析类
			Element headparsemapE = root.element("headparsemap");
			@SuppressWarnings("unchecked")
			List<Element> headEList = headparsemapE.elements("headitem");
			for(int i=0;i<headEList.size();i++){
				Element headitemE = headEList.get(i);
				String acmode = headitemE.attributeValue("acmode");
				String headParseClass = headitemE.attributeValue("headparseclass");
				
				//加入到Map中
				headParseMap.put(acmode, headParseClass);
				
				//System.out.println("INSERT INTO b_rptparse_cfg_head(id,acmodel,parseclass) values(s_id.nextval,'"+acmode+"','"+headParseClass+"') ;");
			}
			
			//初始化报文内容解析类
			Element contentparsemapE = root.element("contentparsemap");
			@SuppressWarnings("unchecked")
			List<Element> fdimuEList = contentparsemapE.elements("fdimu");
			for(int i=0;i<fdimuEList.size();i++){
				Element fdimuE = fdimuEList.get(i);
				String version = fdimuE.attributeValue("version");
			
				@SuppressWarnings("unchecked")
				List<Element> contentEList = fdimuE.elements("contentitem");
				for(int j=0;j<contentEList.size();j++){
					Element contentE = contentEList.get(j);
					String rptno = contentE.attributeValue("rptno");
					String contentparseclass = contentE.attributeValue("contentparseclass");
					
					//加入到内容解析类MAP
					contentParseMap.put(version+"_"+rptno, contentparseclass);
					
					//System.out.println("insert into b_rptparse_cfg_content(id,fdimuversion,rptno,parseclass)" +
					//		" values(s_id.nextval,'"+version+"','"+rptno+"','"+contentparseclass+"') ;");
				}
			}
			
		}else{
			throw new Exception("未找到报文解析配置对应的元数据["+parseConfMtcode+"]");
		}
			
	}
	

}
