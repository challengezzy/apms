package com.apms.bs.intf.omis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class JavaDateAdd {
	public static void main(String[] av){
		String startDate="2012-11-13 13:12:12";
		try{
		 //     Calendar calendar = Calendar.getInstance();   
			//HH大写为24小时制小写为12小时制
	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	       //calendar.setTime(sdf.parse(startDate));
	       
		//System.out.println(sdf.format(calendar.getTime()));
	       Date d=new Date();
	       d=sdf.parse(startDate);
	       //System.out.println(d.getYear());
	       
	       Calendar c = Calendar.getInstance();
	       c.setTime(sdf.parse(startDate));
	       
	       int year = c.get(Calendar.YEAR);
	       int month=c.get(Calendar.MONTH);
	       int day=c.get(Calendar.DAY_OF_MONTH);
	       int hours=c.get(Calendar.HOUR_OF_DAY);
	       int mi=c.get(Calendar.MINUTE);
	       int second=c.get(Calendar.SECOND);
	       System.out.println(year);
	       System.out.println(month);
	       System.out.println(day);
	       System.out.println(hours);
	       System.out.println(mi);
	       System.out.println(second);
	       
			//Calendar c1 = new GregorianCalendar();
			Calendar c1 = new GregorianCalendar();
			c1.set(year+1, month, day+10, hours+3, mi+50, second);
			//c1.set(year,3-1,28);
			//c1.add(Calendar.DATE,4);
			//c1.add(Calendar.MONTH,11);

			String date = "";
			date = sdf.format(c1.getTime()); 
			System.out.println(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		/*char[] ch=interval.toLowerCase().toCharArray();
		 switch(ch[0]){
		  case "y": return new Date(date.setFullYear(date.getFullYear()+number));
		  case "m": return new Date(date.setMonth(date.getMonth()+number));
		  case "d": return new Date(date.setDate(date.getDate()+number));
		  case "w": return new Date(date.setDate(date.getDate()+7*number));
		  case "h": return new Date(date.setHours(date.getHours()+number));
		  case "n": return new Date(date.setMinutes(date.getMinutes()+number));
		  case "s": return new Date(date.setSeconds(date.getSeconds()+number));
		  case "l": return new Date(date.setMilliseconds(date.getMilliseconds()+number));
		 } */
		
		}
}
