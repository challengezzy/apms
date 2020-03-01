package com.apms.flex.util
{
	import mx.formatters.DateFormatter;

	public class DateUtil
	{
		/**
		 * 把日期转换成字符串,formatterType表示字符串格式
		 * 1, "YYYY-MM-DD JJ:NN:SS"  2,"YYYY-MM-DD"  3,"YYYY-MM-DD JJ:NN:SS:QQQ" 4,"MM/DD/YYYY"
		 * */
		public static function dateToString(date:Date, formatterType:int=1):String {
			var dateStr:String;
			var df:DateFormatter = new DateFormatter();
			
			if(formatterType == 1){
				df.formatString = "YYYY-MM-DD JJ:NN:SS";
			}else if(formatterType == 2){
				df.formatString = "YYYY-MM-DD"
			}else if(formatterType == 3){
				df.formatString = "YYYY-MM-DD JJ:NN:SS:QQQ"
			}else if(formatterType == 4){
				df.formatString = "MM/DD/YYYY"
			}else if(formatterType == 5){
				df.formatString = "JJ:NN:SS"
			}
			
			dateStr = df.format(date);
			return dateStr;
		}
		
		/** 把日期转换成字符串  "YYYY-MM-DD JJ:NN:SS:QQQ"*/
		public static function dateToFormatStr(date:Date, formatString:String="YYYY-MM-DD JJ:NN:SS"):String {
			var dateStr:String;
			var df:DateFormatter = new DateFormatter();
			df.formatString = formatString;
			dateStr = df.format(date);
			return dateStr;
		}
		
		/**
		 *判断两个日期相差多少天
		 */
		public static function compareTwoDates(startDate:Date,endDate:Date):int{
			var diff:Number=endDate.getTime()-startDate.getTime();
			var dateNum:int=diff/(24*60*60*1000);
			return dateNum;
		}
		/**
		 * 字符串转换为日期
		 * s = "2007-10-15 20:15"
		 * */
		public static function stringToDate(s:String):Date{
			//s = "2007-10-15 20:15" 
			var temp:Array = s.split(" ");   
			var datepart:String = temp[0];   
			var datearray:Array = datepart.split("-");
			var timepart:String = temp[1];   
			var timearray:Array = timepart.split(":");
			var newDate:Date = new Date(datearray[0],datearray[1]-1,datearray[2],timearray[0],timearray[1]); 
			
			return newDate;   
		}
		
		/**
		 * 字符串转换为日期,只转换到日，忽略小时、分、秒
		 * s = "2007-10-15 20:15"
		 * */
		public static function stringToDateDay(s:String):Date{
			//s = "2007-10-15 20:15" 
			//或s = "2007/10/15 20:15"
			var temp:Array = s.split(" ");   
			var datepart:String = temp[0];
			datepart=StringUtil.StringReplaceAll(datepart,'/','-');
			var datearray:Array= datepart.split("-");
			var timepart:String = temp[1];   
			//var timearray:Array = timepart.split(":");
			var newDate:Date = new Date(datearray[0],datearray[1]-1,datearray[2]); 
			
			return newDate;   
		}
		
		/**
		 * 两个日期的天数差异，date1-date2
		 * */
		public static function dayDiff(date1:Date,date2:Date):Number{
			var mil1:Number = date1.getTime();
			var mil2:Number = date2.getTime();
			
			var diff:Number = (mil1 - mil2) / (24*60*60*1000);
			
			return diff;			
		}
		
		/**
		 * 两个日期的分钟差异，date1-date2
		 * */
		public static function minuteDiff(date1:Date,date2:Date):Number{
			var mil1:Number = date1.getTime();
			var mil2:Number = date2.getTime();
			
			var diff:Number = (mil1 - mil2) / (60*1000);
			
			return diff;			
		}
		
		/**
		 * 移动天
		 * */
		public static function moveDay(date:Date,monthes:int):Date {
			var newDate:Date = new Date();
			
			var curMil:Number = date.getTime();
			//根据毫秒数来移动
			var move:Number = monthes*24*60*60*1000;//
			var newMil:Number = curMil + move;
			
			newDate.setTime(newMil);
			
			return newDate;
		}
		
		/**
		 * 移动小时
		 * */
		public static function moveHour(date:Date,hours:int):Date {
			var newDate:Date = new Date();
			
			var curMil:Number = date.getTime();
			//根据毫秒数来移动
			var move:Number = hours*60*60*1000;//
			var newMil:Number = curMil + move;
			
			newDate.setTime(newMil);
			
			return newDate;
		}
		
		/**
		 * 移动月份
		 * */
		public static function moveMonth(date:Date,monthes:int):Date {
			var newDate:Date = new Date();
			
			var curMil:Number = date.getTime();
			//根据毫秒数来移动
			var move:Number = monthes*30*24*60*60*1000;//
			var newMil:Number = curMil + move;
			
			newDate.setTime(newMil);
				
//			newDate.setHours(date.hours,date.minutes,date.seconds,date.milliseconds);
//			
//			var nowMonth:int = date.month + monthes;
//			if(nowMonth < 0){
//				nowMonth += 12;
//				newDate.setFullYear(date.fullYear-1,nowMonth,date.day);
//			}else if(nowMonth > 11){
//				nowMonth -= 12;
//				newDate.setFullYear(date.fullYear+1,nowMonth,date.day);
//			}else{
//				newDate.setFullYear(date.fullYear,nowMonth,date.day);
//			}
			
			return newDate;
		}
		
		/**
		 * 把毫秒转换成日期
		 * */
		public static function parseMsToDate(ms:Number):Date{
			var newDate:Date = new Date();
			
			newDate.setTime(ms);
			return newDate;
		}
		
		/**
		 * 判断给定的字符串，是否是小时：分钟的形式"HH:MM"
		 * */
		public static function isHourMinute(timeStr:String):Boolean{
			var hh:Number ;
			var mm:Number ;
			var hStr:String ;
			var mStr:String ;
			
			var isValidOk:Boolean = false;
			
			var idx:int = timeStr.indexOf(":");
			if(idx > 0){
				hStr = timeStr.substring(0,idx);
				mStr = timeStr.substring(idx+1,timeStr.length);
				hh = new Number( hStr );
				mm = new Number( mStr );
				
				if ( !isNaN(hh) && !isNaN(mm) && hStr.length>0 && mStr.length>0 ){
					//小时、分钟数值正确
					if( hh >= 0 && hh<24 && mm>=0 && mm <60){
						isValidOk = true;
					}
				}
				
			}
			
			return isValidOk;
		}
		
		public static function getDateFormat(str:String):String{
			
			if(str == null || str == ""){
				return null;
			}
			
			var tempStrArr:Array = str.split(" ");
			
			if(tempStrArr.length == 1){
				
				if(str.indexOf("-")!=-1){
					return "YYYY-MM-DD";
				}
				if(str.indexOf("/")!=-1){
					return "YYYY/MM/DD";
				}
				if(str.indexOf(".")!=-1){
					return "YYYY.MM.DD";
				}
				
			}else if(tempStrArr.length > 1){
				
				if(str.indexOf("-")!=-1){
					return "YYYY-MM-DD JJ:NN:SS";
				}
				if(str.indexOf("/")!=-1){
					return "YYYY/MM/DD JJ:NN:SS";
				}
				if(str.indexOf(".")!=-1){
					return "YYYY.MM.DD JJ:NN:SS";
				}
			}
			
			return null;
		}
		
	}
}