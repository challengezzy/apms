package com.apms.bs.sms;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;

import smartx.framework.common.vo.NovaLogger;

import com.chinamobile.openmas.client.Sms;

/**
 * 利用openmas发关短信
 * @author jerry
 * @date Jan 7, 2017
 */
public class SmsOpenMasSender {
	
	private static Logger logger = NovaLogger.getLogger(SmsOpenMasSender.class);;
	/**
	 * openmas 的服务地址
	 */
	private static final String WEBSERVICE_URL = "http://10.210.197.44:9080/openmasservice";
	private static final String APPLICATIONID = "jiwu_API";
	private static final String PASSWORD = "pJ13oxPwm2n8";
	private static final String EXTENDCODE = "6"; // 自定义扩展代码（模块）
	
	private static Sms SMS;
	
	private static SmsOpenMasSender sender;
	
	private SmsOpenMasSender(){
		
	}
	
	public static SmsOpenMasSender getInstance(){
		if(sender == null){
			sender = new SmsOpenMasSender();
		}
		
		return sender;
	}
	

	//初始化短信接口
	static {
		try {
			SMS = new Sms(WEBSERVICE_URL);
		} catch (AxisFault e) {
			logger.error("初始化短信接口失败," + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 获取短信接口
	 * @return 返回短信Mms
	 */
	public static Sms getSms() {
		if (SMS == null) {
			throw new RuntimeException("SMS init failure");
		}
		return SMS;
	}
	
	/**
	 * 短信发送给单人
	 * @param destinationAddresses
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String sendMessage(String destAddr,String message) throws Exception{
		try{
			String[] destinationAddresses = new String[]{destAddr};
			String gateWayId = getSms().SendMessage(destinationAddresses, message, EXTENDCODE, APPLICATIONID, PASSWORD);
			logger.debug("短信已发送, gatewayid= " + gateWayId);
			
			return gateWayId;
		}catch (Exception e) {
			logger.error("短信发送异常!");
			throw e;
		}
	}
	
	/**
	 * 一条短信发送给多人
	 * @param destinationAddresses
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String sendMessage(String[] destAddresses,String message) throws Exception{
		try{
			String gateWayId = getSms().SendMessage(destAddresses, message, EXTENDCODE, APPLICATIONID, PASSWORD);
			logger.debug("短信已发送, gatewayid= " + gateWayId);
			
			return gateWayId;
		}catch (Exception e) {
			logger.error("短信发送异常!");
			throw e;
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
		String[] destinationAddresses = new String[] { "18916752189" };
		String message = "openmas短信测试";
		String extendCode = "6"; // 自定义扩展代码（模块）
		String ApplicationID = "jiwu_API";
		String Password = "pJ13oxPwm2n8";

		// 发送短信
		String gateWayId = getSms().SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
		System.out.println(gateWayId);
	}

}
