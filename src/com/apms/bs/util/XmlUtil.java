package com.apms.bs.util;

import java.io.ByteArrayOutputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * XML与JAVA对象转换
 * @author jerry
 * @date Mar 25, 2013
 */
public class XmlUtil {
	
	private static Serializer serializer;
	
	/**
	 * 根据XML转换成对应的JAVA对象
	 * read the contents of the XML
    * document from the provided source and convert it into an object
    * of the specified type. If the XML source cannot be deserialized
    * or there is a problem building the object graph an exception
    * is thrown. The instance deserialized is returned
	 * @param <T>
	 * @param type this is the class type to be deserialized from XML
     * @param source this provides the source of the XML document
     * @param strict this determines whether to read in strict mode 
	 * @throws Exception
	 */
	public static <T> T getObjectByXml(Class<? extends T> type, String sourceXml, boolean strict) throws Exception{
		//把XML文件内容转换成对象
		if(serializer == null)
			serializer = new Persister();
		
//		InputStream in = XmlUtil.class.getResourceAsStream("/com/pams/bs/util/VoTest.xml");
//		java.io.InputStreamReader reader=new java.io.InputStreamReader(in,"utf-8");
//		char[] buf=new char[1024];
//		StringWriter writer = new StringWriter();
//		int pos;
//		while((pos=reader.read(buf))!=-1){
//			writer.write(buf,0,pos);
//		}
//		reader.close();
//		String str=writer.toString();
//		System.out.println(str);
		
		//TestVo vo = serializer.read(type, sourceXml,strict);
		
		return serializer.read(type, sourceXml,strict);
	}
	
	/**
	 * 把XML对象转换成字符串
	 * @param source 
	 * @return
	 * @throws Exception
	 */
	public static String getXmlByObject(Object source) throws Exception{
		if(serializer == null)
			serializer = new Persister();
		
		//再把对象转换成XML字符串
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		serializer.write( source, outputStream);
		
		String returnStr = outputStream.toString("utf-8");
		outputStream.close();
		//System.out.println(returnStr);
		
		return returnStr;
	}

}

