package com.apms.flex.modules
{
	public class ChartUtil
	{
				
		public static const changePointRadius:Number = 8;
		
		/**
		 * 图形点的显示颜色
		 * */
		public static function getPointFillColor(eventType:Number):uint{
			var color:uint = 0x0000FF;
			
			if(eventType == 0){
				//未标记
				color = 0x0000FF;//蓝色
			}else if(eventType == 1){
				//故障数据
				color = 0xFF0000;//红色
			}else if(eventType == 2){
				//维修工作
				color = 0xFF9900;//橙色
			}else if(eventType == 3){
				//不良数据
				color = 0x555555;//灰色
			}
			
			return color;
		}
		
	}
}