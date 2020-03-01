package com.apms.bs.datatask;

import org.dom4j.Element;

import com.apms.bs.weather.WeatherInfo;
import com.apms.bs.weather.WeatherService;

import smartx.publics.datatask.DataTaskExecThread;
import smartx.publics.datatask.DataTaskExecuteIFC;

/**
 * 更新天气预报任务
 *@date Nov 28, 2011
 **/
public class DTWeatherForecastTask implements DataTaskExecuteIFC{

	/**
	 * 参数应该传入城市名称、城市编码
	 */
	public void dataTaskExec(Element task,DataTaskExecThread mainThread) throws Exception {
		
		String cityName = task.elementText("CITYNAME");
		String cityCode = task.elementText("CITYCODE");
		
		mainThread.logTaskRun("开始执行[DTWeatherForecastTask]任务，" + 
					"cityName="+cityName+", cityCode=" +cityCode);
		
		WeatherService service = WeatherService.getInstance();//获取服务实例
		
		WeatherInfo wi =  service.getWeatherByCity(cityCode);
		
		service.updateWeatherData(cityCode, wi);
				
		mainThread.logTaskRun("["+cityName+"]天气获取成功！");
		
	}

}