package com.apms.bs.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.apms.ApmsConst;
import com.apms.bs.util.DateUtil;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

public class WeatherService {
	
	Logger logger = NovaLogger.getLogger(this.getClass());
	
	private String weatherBaseUrl = //"http://m.weather.com.cn/data/";
		//"http://www.weather.com.cn/data/zs/";
		"http://weather.51wnl.com/weatherinfo/GetMoreWeather";
	
	private static WeatherService weatherService = null;
	
	public WeatherService(){
		
	}
	
	public static WeatherService getInstance(){
		if(weatherService == null)
			weatherService = new WeatherService();
		
		return weatherService;
	}
	
	/**
	 * 根据城市编码获取天气预报
	 * @param cityCode
	 * @return WeatherInfo 6天天气情况
	 * @throws Exception
	 */
	public WeatherInfo getWeatherByCity(String cityCode) throws Exception{
		logger.info("开始查询["+cityCode+"]的天气预报！");
		//String cityCode = "101220801";
		
		String date = DateUtil.getDateStr(new Date());//日期格式 yyyy-MM-dd
		String weaterInfo = this.getWeatherJsonInfo3(cityCode);
		logger.debug("获取的天气原始信息为： " + weaterInfo);
		
		JSONObject jsonobj = JSONObject.fromObject((JSONObject.fromObject(weaterInfo).getString("weatherinfo")));
		WeatherInfo weather = (WeatherInfo) JSONObject.toBean(jsonobj,WeatherInfo.class);
		weather.setDate(DateUtil.StringToDate(date, "yyyy-MM-dd"));
		
		logger.debug("天气查询成功！ ");
		
		return weather;
	}
	
	public void updateWeatherData(String cityCode,WeatherInfo wi) throws Exception{
		
		logger.debug("获取的【"+cityCode+"】天气信息更新到数据库！");
		//1, 删除数据库中该日期之后的天气信息
		CommDMO dmo = new CommDMO();
		String delSql = "DELETE B_WEATHER T WHERE CITYCODE=? AND T.FORECASTDATE >= ?";
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, delSql, cityCode,DateUtil.getDateStr(wi.getDate()));
		
		//2, 插入该城市之后6天的天气信息
		
		String addSql = "INSERT INTO B_WEATHER(ID,FORECASTDATE,CITYNAME,CITYCODE,LOW_TEMPERATURE,HIGH_TEMPERATURE,TYPE,WIND_POWER,UPDATE_DATE) "
				+ " VALUES(S_B_WEATHER.NEXTVAL,?,?,?,?,?,?,?,SYSDATE)";
		
		try{
			//当天
			DateUtil wdate = new DateUtil(wi.getDate());
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, addSql, wdate.getDateStr(),wi.getCity(),cityCode,getLowTemperature(wi.getTemp1()),
						getHighTemperature(wi.getTemp1()), wi.getWeather1(),wi.getFl1());
			
			//第2天
			wdate.moveDay(1);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, addSql, wdate.getDateStr(),wi.getCity(),cityCode,getLowTemperature(wi.getTemp2()),
						getHighTemperature(wi.getTemp2()), wi.getWeather2(),wi.getFl2());
			//第3天
			wdate.moveDay(1);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, addSql, wdate.getDateStr(),wi.getCity(),cityCode,getLowTemperature(wi.getTemp3()),
						getHighTemperature(wi.getTemp3()), wi.getWeather3(),wi.getFl3());
			//第4天
			wdate.moveDay(1);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, addSql, wdate.getDateStr(),wi.getCity(),cityCode,getLowTemperature(wi.getTemp4()),
						getHighTemperature(wi.getTemp4()), wi.getWeather4(),wi.getFl4());
	
	
			//第5天
			wdate.moveDay(1);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, addSql, wdate.getDateStr(),wi.getCity(),cityCode,getLowTemperature(wi.getTemp5()),
						getHighTemperature(wi.getTemp5()), wi.getWeather5(),wi.getFl5());
			
			//第6天
			wdate.moveDay(1);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, addSql, wdate.getDateStr(),wi.getCity(),cityCode,getLowTemperature(wi.getTemp6()),
						getHighTemperature(wi.getTemp6()), wi.getWeather6(),wi.getFl6());
			
			dmo.commit(ApmsConst.DS_APMS);
			
			logger.debug("【"+cityCode+"】6天天气更新成功！");
		}catch (Exception e) {
			logger.error("更新天气信息到数据库异常！",e);
			throw e;
		}finally{
			dmo.releaseContext(ApmsConst.DS_APMS);
		}
		
	}
	
	private int getLowTemperature(String tempratureStr){
		String temp1 = tempratureStr.substring(tempratureStr.indexOf("~")+1, tempratureStr.indexOf("℃",tempratureStr.indexOf("~")));
		String temp2 = tempratureStr.substring(0,tempratureStr.indexOf("℃"));
		int temperatue1 = new Integer(temp1);
		int temperatue2 = new Integer(temp2);
		
		if(temperatue1 < temperatue2)
			return temperatue1;
		else
			return temperatue2;
		
	}
	
	private int getHighTemperature(String tempratureStr){
		//温度，最高最低难道是随意出现的,都解析出来，取最大值
		String temp1 = tempratureStr.substring(tempratureStr.indexOf("~")+1, tempratureStr.indexOf("℃",tempratureStr.indexOf("~")));
		String temp2 = tempratureStr.substring(0,tempratureStr.indexOf("℃"));
		int temperatue1 = new Integer(temp1);
		int temperatue2 = new Integer(temp2);
		
		if(temperatue2 < temperatue1)
			return temperatue1;
		else
			return temperatue2;
	}
	
	//抓取网页内容
	private static boolean downloadPage(String path) throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();
		GetMethod getmethod;

		getmethod = new GetMethod(path);
		// 获得响应状态码
		int statusCode = httpClient.executeMethod(getmethod);
		if (statusCode == HttpStatus.SC_OK) {
			System.out.println("response=" + getmethod.getResponseBodyAsString());
			// 写入本地文件
			//FileWriter fwrite = new FileWriter("hello.txt");
			String pageString = getmethod.getResponseBodyAsString();
			getmethod.releaseConnection();
//			fwrite.write(pageString, 0, pageString.length());
//			fwrite.flush();
//			// 关闭文件
//			fwrite.close();
			// 释放资源
			return true;
		}
		return false;
	}
	

	/**
	 * 从国家气象局提供的天气预报接口获取Json天气数据 "http://m.weather.com.cn/data/{citycode}.html"
	 * 
	 * @param cityCode
	 *            城市编码
	 * @return
	 * @throws Exce
	 */
	public String getWeatherJsonInfo3(String cityCode) throws Exception {
		String info = "";
		//String path = weatherBaseUrl + cityCode + ".html";
		String path = weatherBaseUrl + "?cityCode=" + cityCode + "&weatherType=0";
		System.out.println(path);
		URL url = null;
		HttpURLConnection conn = null;
		String inputline = "";
		InputStreamReader inputStream = null;
		try {
			url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10 * 1000);
			conn.setRequestMethod("GET");

			inputStream = new InputStreamReader(conn.getInputStream(), "UTF-8");
			//inputStream = new InputStreamReader(url.openStream(), "UTF-8");
			BufferedReader buffer = new BufferedReader(inputStream);

			while ((inputline = buffer.readLine()) != null) {
				info += inputline;
			}

		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				//关闭连接资源
				if( conn != null){
					conn.disconnect();
				}
				
				if (inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return info;
	}
	
	/**
	 * 测试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		WeatherService service = new WeatherService();
		
		String cityCode = "101220801"; // "101010100";
		String weaterInfo = service.getWeatherJsonInfo3(cityCode);

		System.out.println(weaterInfo);

		JSONObject jsonobj = JSONObject.fromObject((JSONObject.fromObject(weaterInfo).getString("weatherinfo")));
		WeatherInfo weather = (WeatherInfo) JSONObject.toBean(jsonobj,WeatherInfo.class);

		System.out.println(weather.getCity());
		System.out.println(weather.getTemp1()+ " hight:" + service.getHighTemperature(weather.getTemp1())
					+ " low:" + service.getLowTemperature(weather.getTemp1()));
		System.out.println(weather.getTemp2());
		System.out.println(weather.getDate_y());
		System.out.println(weather.getDate());
		System.out.println(weather.getTemp5());

	}

}
