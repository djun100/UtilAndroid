package com.cy.DataStructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author json转换时间
 *
 */
public class UtilDate {

	public static String getTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(date);
	}

	public static String dateToStrng(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return format.format(date);
	}
	public static Date getDate(long milliseconds){
		return new Date(milliseconds);
	}
	/**
	 * date 格式化输出
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToStrng(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String longToDate(long timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(timestamp);
	}

	public static String getDateMMdd(long starttime) {
		Date date = new Date(starttime);
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		return format.format(date);
	}

	public static String getDateMMddFuture(int day) {
		Date date = new Date();
		date.setDate(date.getDay() + day);
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		return format.format(date);
	}

	/**
	 * @param date
	 * @param addDay
	 * @return 未来addDay天指定时间点的date
	 */
	public static Date getDateFuture(Date date, int addDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, addDay);
		calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
		calendar.set(Calendar.MINUTE, date.getMinutes());
		calendar.set(Calendar.SECOND, date.getSeconds());
		return calendar.getTime();
	}
	/**use:<br>
	 *  HashMap<Integer, Integer> timeHashMap=new HashMap<Integer, Integer>();<br>
		timeHashMap.put(Calendar.DAY_OF_MONTH, 1);<br>
		date=getDateFuture(date, timeHashMap);<br>
	 * @param date
	 * @param timeToAdd eg: use Calendar.Day
	 * @return
	 */
	public static Date getDateFuture(Date date, HashMap<Integer, Integer> timeToAdd) {
		if (date==null || timeToAdd==null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		for (Entry<Integer, Integer> time: timeToAdd.entrySet()) {
			calendar.add(time.getKey(), time.getValue());
		}
		return calendar.getTime();
	}
	public static Date addDays(Date now, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);// 让日期加1
		return calendar.getTime();
	}

	public static boolean timePassed(String time) {
		boolean passed = true;
		Date fromDate = new Date();
		SimpleDateFormat simple1 = new SimpleDateFormat("kk:mm");

		// 当前时间
		String timeStr = android.text.format.DateFormat.format("kk:mm", System.currentTimeMillis())
				.toString();

		try {
			fromDate = simple1.parse(timeStr);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		long curTime = fromDate.getTime();

		try {
			fromDate = simple1.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (fromDate.getTime() >= curTime) {
			return passed;
		} else {
			return !passed;
		}
	}

	/**
	 * 获取星期值
	 */
	public static String getWeekOfDate() {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		Date curDate = new Date(System.currentTimeMillis());
		cal.setTime(curDate);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取星期值
	 */
	public static String getWeekOfDate(Date date, int day) {
		String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取星期值
	 */
	public static String getWeekOfDateFuture(int day) {
		String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };
		Calendar cal = Calendar.getInstance();
		Date curDate = new Date(System.currentTimeMillis());
		cal.setTime(curDate);
		cal.add(Calendar.DAY_OF_MONTH, day);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static String getTimeString(int msec) {
		if (msec < 0) {
			return String.format("--:--:--");
		}
		int total = msec / 1000;
		int hour = total / 3600;
		total = total % 3600;
		int minute = total / 60;
		int second = total % 60;
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

	protected static String sTempPath = "/data/local/tmp";

	public static String getTempPath() {
		return sTempPath;
	}

	public static void main(String[] args) {
		System.err.println(addDays(new Date(), 21));
		System.out.println(getDatePassed("2004-01-02 11:30:24","2004-03-26 13:31:40",null));
	}

	/**
	 * @param time
	 * @param format
	 *            eg: HH:mm yyyyMMdd
	 * @return
	 */
	public static Date getDateFromString(String time, String format) {
		if (format == null) {
			format = "HH:mm";
		}
		java.text.DateFormat format1 = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = format1.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**计算时间差
	 * @param advance
	 * @param after
	 * @param format
	 * @return
	 */
	public static String getDatePassed(String advance, String after, String format) {
		if (format==null) {
			format="yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat dfs = new SimpleDateFormat(format);
		Date begin;
		Date end;
		try {
			begin = dfs.parse(advance);
			end = dfs.parse(after);
		} catch (ParseException e) {
			return "时间差计算参数有误";
		}
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long day1 = between / (24 * 3600);
		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;
		long second1 = between % 60 % 60;
		System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
		if (day1 != 0) {
			return day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
		} else if (day1 == 0 && hour1 != 0) {
			return hour1 + "小时" + minute1 + "分" + second1 + "秒";
		} else if (hour1 == 0 && minute1 != 0) {
			return minute1 + "分" + second1 + "秒";
		} else if (minute1 == 0 && second1 != 0) {
			return second1 + "秒";
		} else {
			return "0秒";
		}
	}
	/**计算时间差
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getDatePassed(Date begin, Date end) {
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long day1 = between / (24 * 3600);
		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;
		long second1 = between % 60 % 60;
		System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
		if (day1 != 0) {
			return day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
		} else if (day1 == 0 && hour1 != 0) {
			return hour1 + "小时" + minute1 + "分" + second1 + "秒";
		} else if (hour1 == 0 && minute1 != 0) {
			return minute1 + "分" + second1 + "秒";
		} else if (minute1 == 0 && second1 != 0) {
			return second1 + "秒";
		} else {
			return "0秒";
		}
	}
}
