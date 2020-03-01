package com.apms.bs.intf.omis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class JavadatediffZzy {

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

	public static String DateAdd(String timeInterval, String date1, int num) throws Exception{
		String date = null;
		try {
			// Calendar calendar = Calendar.getInstance();
			// HH大写为24小时制小写为12小时制
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// calendar.setTime(sdf.parse(startDate));

			// System.out.println(sdf.format(calendar.getTime()));
			Date d = new Date();
			d = sdf.parse(date1);
			// System.out.println(d.getYear());

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
}
