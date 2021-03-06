package com.apms;

/**
 *APMS 项目常量类
 *@date Dec 1, 2012
 **/
public class ApmsConst {
	
	public static String DS_DEFAULT = "datasource_default";//默认数据源名称
	
	public static String DS_APMS = "datasource_apms";//APMS数据源
	public static String DS_OMIS = "datasource_omis";//APMS数据源
	/**
	 * APMS报文接口
	 */
	public static String DS_IN = "datasource_in";//
	public static String DS_SMDB = "datasource_smdb";//短信发送接口数据库
	
	public static String DS_APMS202 = "datasource_apms202";//202.1上的APMS数据源ORCL
	public static String DS_APMSOLD = "datasource_apmsold";//APMS老数据库,SQLSERVER
	public static String DS_FOC = "datasource_foc";
	
	public static String FDIMUFILE_DIR = "fdimufile";
	public static String APUFDIMUFILE_DIR = "apufdimufile";
	public static String AIRCRAFTWEIGHTFILE_DIR = "aircraftweightfile";
	public static String ENGINEFDIMUFILE_DIR="enginefdimufile";
	
	//计算中常量
	public static double ABSOLUTE_ZERO = 273.5; //绝对0度，温度转换
	
	public static double COVERFT_M = 0.3048;//英尺转换米 
	
	/**
	 * 标态大气压力
	 */
	public static double PSTD = 1.0132;//标态压力
	
	public static int BOOLEAN_FLASE = 0;
	public static int BOOLEAN_TRUE = 1;

}
