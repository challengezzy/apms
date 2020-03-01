package com.apms.bs.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 日期控件
 * 
 * @author zzy
 * 
 */
public class DateUtil extends Date {
	private static final long serialVersionUID = -4827936629536807453L;

	public DateUtil() {
		super();
	}

	/**
	 * 
	 * @param date
	 *            String
	 */
	public DateUtil(String date) {
		super();
		// 20050202
		// 01234567
		int year, month, day;
		if (date.indexOf("-") == -1) {
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(4, 6));
			day = Integer.parseInt(date.substring(6, 8));
		}
		// 2004-01-01
		// 0123456789
		else {
			year = Integer.parseInt(date.substring(0, 4));
			month = Integer.parseInt(date.substring(5, 7));
			day = Integer.parseInt(date.substring(8, 10));
		}

		this.setYear(year - 1900);
		this.setMonth(month - 1);
		this.setDate(day);
	}

	/**
	 * 
	 * @param t
	 *            long
	 */
	public DateUtil(long t) {
		super();
		this.setTime(t);
	}

	/**
	 * 
	 * @param dt
	 *            Date
	 */
	public DateUtil(Date dt) {
		super();
		this.setTime(dt.getTime());
	}

	/**
	 * 
	 * @param fmt
	 *            String
	 * @return String
	 */
	public String format(String fmt) {
		return new SimpleDateFormat(fmt).format(this);
	}

	public static String abbrMonthToNumber(String monthAbbr) {
		String monthNum = null;
		if ("JAN".equalsIgnoreCase(monthAbbr)) {
			monthNum = "01";
		} else if ("FEB".equalsIgnoreCase(monthAbbr)) {
			monthNum = "02";
		} else if ("MAR".equalsIgnoreCase(monthAbbr)) {
			monthNum = "03";
		} else if ("APR".equalsIgnoreCase(monthAbbr)) {
			monthNum = "04";
		} else if ("MAY".equalsIgnoreCase(monthAbbr)) {
			monthNum = "05";
		} else if ("JUN".equalsIgnoreCase(monthAbbr)) {
			monthNum = "06";
		} else if ("JUL".equalsIgnoreCase(monthAbbr)) {
			monthNum = "07";
		} else if ("AUG".equalsIgnoreCase(monthAbbr)) {
			monthNum = "08";
		} else if ("SEP".equalsIgnoreCase(monthAbbr)) {
			monthNum = "09";
		} else if ("OCT".equalsIgnoreCase(monthAbbr)) {
			monthNum = "10";
		} else if ("NOV".equalsIgnoreCase(monthAbbr)) {
			monthNum = "11";
		} else if ("DEC".equalsIgnoreCase(monthAbbr)) {
			monthNum = "12";
		}

		return monthNum;
	}

	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取日期
	 * @param time
	 * @return
	 */
	public static Date toDay(Date time){
		if(time == null){
			return null;
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = StringToDate(sdf.format(time), "yyyy-MM-dd");
			return d1;
		}
	}

	/**
	 * @param dt
	 *            Date
	 * @param fmt
	 *            String
	 * @return String
	 */
	public static String format(Date dt, String fmt) {
		return new SimpleDateFormat(fmt).format(dt);
	}

	/**
	 * 两个日期的天数差异，date1-date2
	 * */
	public static int dayDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return -5000000;
		}

		Date d1 = StringToDate(getDateStr(date1), "yyyy-MM-dd");
		Date d2 = StringToDate(getDateStr(date2), "yyyy-MM-dd");

		long ms1 = d1.getTime();
		long ms2 = d2.getTime();

		int diff = (int) ((ms1 - ms2) / (24 * 60 * 60 * 1000));

		return diff;
	}

	/**
	 * 两个日期的小时数差异，date1-date2
	 * */
	public static int hoursDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return -5000000;
		}

		long ms1 = date1.getTime();
		long ms2 = date2.getTime();

		int diff = (int) ((ms1 - ms2) / (60 * 60 * 1000));

		return diff;
	}

	/**
	 * 移动天
	 * 
	 * @param days
	 *            int
	 */
	public static Date moveDay(Date dt, int days) {
		long mills = (long) days * 24 * 3600 * 1000;
		Date newDt = new Date(dt.getTime() + mills);

		return newDt;
	}

	public static Date moveSecond(Date dt, int seconds) {
		long mills = (long) seconds * 1000;

		Date newDt = new Date(dt.getTime() + mills);
		return newDt;
	}
	
	public static Date moveMinute(Date dt,int minutes) {
		return moveSecond(dt, minutes*60 );
	}
	
	public static Date moveHour(Date dt,int hours) {
		return moveSecond(dt, hours*60*60 );
	}

	/**
	 * 按指定日期单位计算两个日期间的间隔
	 * 
	 * @param timeInterval
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int dateDiff(String timeInterval, Date date1, Date date2) {
		timeInterval = timeInterval.toLowerCase();
		if (timeInterval.equals("year") || timeInterval.equals("y")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			int time = calendar.get(Calendar.YEAR);
			calendar.setTime(date2);
			return time - calendar.get(Calendar.YEAR);
		}

		if (timeInterval.equals("quarter") || timeInterval.equals("q")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			int time = calendar.get(Calendar.YEAR) * 4;
			calendar.setTime(date2);
			time -= calendar.get(Calendar.YEAR) * 4;
			calendar.setTime(date1);
			time += calendar.get(Calendar.MONTH) / 4;
			calendar.setTime(date2);
			return time - calendar.get(Calendar.MONTH) / 4;
		}

		if (timeInterval.equals("month") || timeInterval.equals("m")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			int time = calendar.get(Calendar.YEAR) * 12;
			calendar.setTime(date2);
			time -= calendar.get(Calendar.YEAR) * 12;
			calendar.setTime(date1);
			time += calendar.get(Calendar.MONTH);
			calendar.setTime(date2);
			return time - calendar.get(Calendar.MONTH);
		}

		if (timeInterval.equals("week") || timeInterval.equals("w")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			int time = calendar.get(Calendar.YEAR) * 52;
			calendar.setTime(date2);
			time -= calendar.get(Calendar.YEAR) * 52;
			calendar.setTime(date1);
			time += calendar.get(Calendar.WEEK_OF_YEAR);
			calendar.setTime(date2);
			return time - calendar.get(Calendar.WEEK_OF_YEAR);
		}

		if (timeInterval.equals("day") || timeInterval.equals("d")) {
			int time = (int) (date1.getTime() / 1000 / 60 / 60 / 24);
			return (int) (time - date2.getTime() / 1000 / 60 / 60 / 24);
		}

		if (timeInterval.equals("hour") || timeInterval.equals("h")) {
			long time = date1.getTime() / 1000 / 60 / 60;
			return (int) (time - date2.getTime() / 1000 / 60 / 60);
		}

		if (timeInterval.equals("minute") || timeInterval.equals("mi") || timeInterval.equals("n")) {
			long time = date1.getTime() / 1000 / 60;
			return (int) (time - date2.getTime() / 1000 / 60);
		}

		if (timeInterval.equals("second") || timeInterval.equals("s")) {
			long time = date1.getTime() / 1000;
			return (int) (time - date2.getTime() / 1000);
		}

		return (int) (date1.getTime() - date2.getTime());
	}

	/**
	 * 章海龙代码迁移
	 * @param timeInterval
	 * @param date1
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public static String dateAdd(String timeInterval, String date1, int num) throws Exception{
		String date = null;
		try {
			// Calendar calendar = Calendar.getInstance();
			// HH大写为24小时制小写为12小时制
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// System.out.println(sdf.format(calendar.getTime()));
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(date1));

			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			int hours = c.get(Calendar.HOUR_OF_DAY);
			int mi = c.get(Calendar.MINUTE);
			int second = c.get(Calendar.SECOND);
			if (timeInterval.toLowerCase().equals("y")) {
				year += num;
			}
			if (timeInterval.toLowerCase().equals("m")) {
				month += num;
			}
			if (timeInterval.toLowerCase().equals("d")) {
				day += num;
			}
			if (timeInterval.toLowerCase().equals("h")) {
				hours += num;
			}
			if (timeInterval.toLowerCase().equals("n")) {
				mi += num;
			}
			if (timeInterval.toLowerCase().equals("s")) {
				second += num;
			}
			/*
			 * System.out.println(year); System.out.println(month);
			 * System.out.println(day); System.out.println(hours);
			 * System.out.println(mi); System.out.println(second);
			 */
			// Calendar c1 = new GregorianCalendar();

			Calendar c1 = new GregorianCalendar();

			c1.set(year, month, day, hours, mi, second);
			// c1.set(year,3-1,28);
			// c1.add(Calendar.DATE,4);
			// c1.add(Calendar.MONTH,11);

			date = sdf.format(c1.getTime());
			// System.out.println(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	public static boolean isRYear(Date dt) {
		return (isRYear(1900 + dt.getYear()));
	}

	/**
	 * 
	 * @param y
	 *            int
	 * @return boolean
	 */
	public static boolean isRYear(int y) {
		return (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0));
	}

	/**
	 * 移动天
	 * 
	 * @param days
	 *            int
	 */
	public void moveDay(int days) {
		long mills = (long) days * 24 * 3600 * 1000;
		this.setTime(getTime() + mills);
	}

	/**
	 * 移动小时
	 * 
	 * @param hours
	 *            int
	 */
	public void moveHour(int hours) {
		long mills = (long) hours * 3600 * 1000;
		this.setTime(getTime() + mills);
	}

	/**
	 * 移动分钟
	 * 
	 * @param minutes
	 *            int
	 */
	public void moveMinute(int minutes) {
		long mills = (long) minutes * 60 * 1000;
		this.setTime(getTime() + mills);
	}

	/**
	 * 移动月
	 * 
	 * @param monthes
	 *            int
	 */
	public void moveMonth(int monthes) {
		setMonth(this.getMonth() + monthes);
	}

	/**
	 * 移动秒
	 * 
	 * @param seconds
	 *            int
	 */
	public void moveSecond(int seconds) {
		long mills = (long) seconds * 1000;
		this.setTime(this.getTime() + mills);
	}

	/**
	 * 移动周
	 * 
	 * @param weeks
	 *            int
	 */
	public void moveWeek(int weeks) {
		long mills = (long) weeks * 7 * 24 * 3600 * 1000;
		this.setTime(this.getTime() + mills);
	}

	/**
	 * 移动年
	 * 
	 * @param year
	 *            int
	 */
	public void moveYear(int year) {
		setYear(this.getYear() + year);
	}

	/**
	 * 获取日期字符串
	 * 
	 * @return String
	 */
	public String getDateStr() {
		return new SimpleDateFormat("yyyy-MM-dd").format(this);
	}

	/**
	 * 获取一个日期值的日期字符串,yyyy-MM-dd
	 * 
	 * @param dt
	 *            Date
	 * @return String
	 */
	public static String getDateStr(Date dt) {
		return new SimpleDateFormat("yyyy-MM-dd").format(dt);
	}

	/** 获取日期指定格式的字符串 */
	public static String getDateStr(Date dt, String format) {
		return new SimpleDateFormat(format).format(dt);
	}

	/**
	 * 获取月份
	 * 
	 * @param dt
	 * @return
	 */
	public static int getMonth(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);

		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 获取年份
	 * 
	 * @param dt
	 * @return
	 */
	public static int getYear(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);

		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取星期几
	 * 
	 * @param dt
	 * @return
	 */
	public static int getWeekDay(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		return dayOfWeek;
	}

	/**
	 * 获取带时间的日期字符串
	 * 
	 * @return String
	 */
	public String getLongDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this);
	}

	/**
	 * 获取一个日期值的带时间日期字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dt
	 *            Date
	 * @return String
	 */
	public static String getLongDate(Date dt) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dt);
	}

	/**
	 * 获取时间字符串
	 * 
	 * @return String
	 */
	public String getTimeStr() {
		return new SimpleDateFormat("HH:mm:ss").format(this);
	}

	/**
	 * 获取一个日期的时间字符串
	 * 
	 * @param dt
	 *            Date
	 * @return String
	 */
	public static String getTimeStr(Date dt) {
		return new SimpleDateFormat("HH:mm:ss").format(dt);
	}

	/**
	 * 时间刷新
	 * 
	 * @return DateUtil
	 */
	public DateUtil move() {
		this.setTime(new Date().getTime());
		return this;
	}

	/**
	 * 时间移动一个字符串表达式，只支持单次运算，可考虑添加多次运算的方式
	 * 
	 * @param move
	 *            String
	 * @return DateUtil
	 */
	public DateUtil move(String move) {
		if (move == null || move.length() == 0) {
			return this;
		}

		/**
		 * 计算前面的数值
		 */
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < move.length(); i++) {
			char c = move.charAt(i);
			if (c == '-' || c >= '0' && c <= '9') {
				buf.append(c);
			} else if (c == '+') { // 过滤+
			} else {
				// 非数值字符，退出循环
				break;
			}
		}

		/**
		 * 获取后面的实际移动单位
		 */
		int i = Integer.parseInt(buf.toString());
		int index = move.indexOf(buf.toString());
		if (index >= 0) {
			move = move.substring(index + buf.length());
		}
		move.toLowerCase();
		if (move.equals("year")) {
			this.moveYear(i);
		} else if (move.equals("month")) {
			this.moveMonth(i);
		} else if (move.equals("day")) {
			this.moveDay(i);
		} else if (move.equals("hour")) {
			this.moveHour(i);
		} else if (move.equals("minute")) {
			this.moveMinute(i);
		} else if (move.equals("second")) {
			this.moveSecond(i);
		} else if (move.equals("week")) {
			this.moveWeek(i);
		}

		return this;
	}

	/**
	 * 重载toString 方法
	 * 
	 * @return String
	 */
	public String toString() {
		return format("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 
	 * @param dt
	 *            Date
	 * @return String
	 */
	public static String toString(Date dt) {
		return format(dt, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * @param date Date
	 * @return Timestamp added by jiayc
	 */
	public static java.sql.Timestamp dateToTimeStamp(java.util.Date date) {
		if (date == null) {
			return null;
		} else {
			return new java.sql.Timestamp(date.getTime());
		}
	}
	
	/**
	 * 拼装成sql中需要的to_date,格式为'yyyy-MM-dd HH24:MI:SS'
	 * @param dt
	 * @return
	 */
	public static String toSqlDateTime(Date dt){
		return "to_date('"+DateUtil.getLongDate(dt)+"','yyyy-MM-dd HH24:MI:SS')";
	}
	
	/**
	 * 拼装成sql中需要的to_date,格式为'yyyy-MM-dd'
	 * @param dt
	 * @return
	 */
	public static String toSqlDate(Date dt){
		return "to_date('"+DateUtil.getDateStr(dt)+"','yyyy-MM-dd')";
	}

	public static void main(String args[]) {
		System.out.println(DateUtil.format(new Date(), "yyyy-MM"));
		System.out.println(new DateUtil());
		System.out.println(DateUtil.toString(new Date()));
		System.out.println(new DateUtil(DateUtil.parse("" + new Date())).move("10day"));
	}
}