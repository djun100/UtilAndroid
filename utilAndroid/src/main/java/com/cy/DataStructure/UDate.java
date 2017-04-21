package com.cy.DataStructure;

import android.text.TextUtils;

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
public class UDate {

	public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_MM_DD_YYYY_HH_MM_SS_diagonal = "MM/dd/yyyy HH:mm:ss";
	public static final String FORMAT_HH_MM_SS = "HH:mm:ss";
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMAT_YYYY_MM = "yyyy-MM";
	public static final String FORMAT_MM_DD = "MM-dd";
	public static final String FORMAT_YYYY = "yyyy";
	public static final String FORMAT_MM = "MM";
	public static final String FORMAT_DD = "dd";
	public static final String FORMAT_HH_MM = "HH:mm";
	public static final String FORMAT_YYYY_MM_DD_HH_MM_CHINA = "yyyy年MM月dd日 HH:mm";

	public static String getDateStrNow(String format){
		format= TextUtils.isEmpty(format)?FORMAT_YYYYMMDDHHMMSS:format;
		return getDateStr(System.currentTimeMillis(),format);
	}

	public static String getTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_HH_MM_SS);
		return format.format(date);
	}

	public static String dateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_MM_DD_YYYY_HH_MM_SS_diagonal);
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
	public static String getDateStr(Date date, String format) {
		format= TextUtils.isEmpty(format)?FORMAT_YYYY_MM_DD_HH_MM_SS:format;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String getDateStr(long timestamp, String format) {
		format= TextUtils.isEmpty(format)?FORMAT_YYYY_MM_DD_HH_MM_SS:format;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(timestamp);
	}

	public static String getDateMMdd(long starttime) {
		Date date = new Date(starttime);
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_MM_DD);
		return format.format(date);
	}

	public static String getDateMMddFuture(int day) {
		Date date = new Date();
		date.setDate(date.getDay() + day);
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_MM_DD);
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


	/**增减天数
	 * @param now
	 * @param day
	 * @return
	 */
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
	public static String getWeekOfDate(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
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

	public static String getTimeStr(int msec) {
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
	 * @param dateStr
	 * @param format
	 *            eg: HH:mm yyyyMMdd
	 * @return
	 */
	public static Date parseDate(String dateStr, String format) {
		if (format == null) {
			format = "HH:mm";
		}
		java.text.DateFormat format1 = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = format1.parse(dateStr);
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
		format= TextUtils.isEmpty(format)?FORMAT_YYYY_MM_DD_HH_MM_SS:format;
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
	public static String getTimeDiff(Date begin, Date end) {
		return getTimeDiff(begin.getTime(),end.getTime());
	}
	/**计算时间差
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String getTimeDiff(long begin, long end) {
		long between = (end - begin) / 1000;// 除以1000是为了转换成秒
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

	//判断选择的日期是否是 本周
	public static boolean isThisWeek(long time) {
		Calendar calendar = Calendar.getInstance();
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setTime(new Date(time));
		int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		if (paramWeek == currentWeek) {
			return true;
		}
		return false;
	}

	//判断选择的日期是否是 今天
	public static boolean isToday(long time) {
		return isThisTime(time, FORMAT_YYYY_MM_DD);
	}

	//判断选择的日期是否是 昨天
	public static boolean isYesterday(Date date) {
		date= addDays(date,1);
		return isThisTime(date.getTime(), FORMAT_YYYY_MM_DD);
	}

	//判断选择的日期是否是 本月
	public static boolean isThisMonth(long time) {
		return isThisTime(time, FORMAT_YYYY_MM);
	}

	private static boolean isThisTime(long time, String pattern) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String param = sdf.format(date);//参数时间
		String now = sdf.format(new Date());//当前时间
		if (param.equals(now)) {
			return true;
		}
		return false;
	}

	/**
	 * @param time
	 * @描述 —— 指定时间距离当前时间的中文信息
	 */
	public static String getReadablePassed(long time) {
		Calendar cal = Calendar.getInstance();
		long timel = cal.getTimeInMillis() - time;
		if (timel / 1000 < 60) {
			return "1分钟以内";
		} else if (timel / 1000 / 60 < 60) {
			return timel / 1000 / 60 + "分钟前";
		} else if (timel / 1000 / 60 / 60 < 24) {
			return timel / 1000 / 60 / 60 + "小时前";
		} else {
			return timel / 1000 / 60 / 60 / 24 + "天前";
		}
	}

	/**
	 * 将Timestamp转化成今天、昨天和具体日期的时间
	 * @param timestamp
	 * @return
	 */
	public static String getDetailTime(long timestamp) {
		Calendar nowCal = Calendar.getInstance();
		Calendar targetCal = Calendar.getInstance();
		targetCal.setTimeInMillis(timestamp);
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_HH_MM);
		SimpleDateFormat format2=new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_CHINA);
		if(nowCal.get(Calendar.DATE) - targetCal.get(Calendar.DATE) == 0) {
			//今天
			return format.format(timestamp);
		} else if(nowCal.get(Calendar.DATE) - targetCal.get(Calendar.DATE) == 1) {
			//昨天
			return "昨天" + format.format(timestamp);
		} else {
			//昨天以前
			return format2.format(timestamp);
		}
	}
}
