package com.dlshouwen.core.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 日期工具类
 * @author 大连首闻科技有限公司
 * @version 2013-7-13 15:18:09
 */
@SuppressWarnings({ "rawtypes", "static-access", "unused" })
public class DateUtils {

	/**
	 * 获取当前日期
	 * <p>用于获取当前日期，格式为yyyy-MM-dd
	 * @return 当前日期
	 */
	public static String getNowDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
        
	/**
	 * 获取当前日期
	 * <p>用于获取当前日期，格式为yyyyMMdd
         * @param date 日期
	 * @return 具体格式的日期
	 */
	public static String getNowDate(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * 获取当前时间
	 * <p>用于获取当前时间，格式为：yyyy-MM-dd HH:mm:ss
	 * @return 当前时间
	 */
	public static String getNowTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 获取当前时间，包含毫秒
	 * <p>用于获取当前时间，包含毫秒，格式为：yyyy-MM-dd HH:mm:ss SSS
	 * @return 当前时间
	 */
	public static String getNowTimeHaveMS() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date());
	}

	/**
	 * 按指定格式获取当前时间
	 * <p>用于获取指定格式的当前时间，格式通过format参数传递
	 * @param format 日期格式
	 * @return 格式化后的当前时间
	 */
	public static String getNowDateFormat(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 按指定格式获取指定时间
	 * <p>用于按指定格式获取指定时间
	 * @param date 需要格式化的时间字符串
	 * @param format 日期格式
	 * @return 格式化后的时间
	 * @throws Exception 抛出格式化异常
	 */
	public static String getDateFormat(String date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(sdf.parse(date));
	}

	/**
	 * 将日期字符串转换为Calendar类型日期
	 * <p>用于将日期字符串转换为Calendar类型日期
	 * @param baseDate yyyyMMdd格式日期
	 * @return Calendar类型日期对象
	 */
	public static Calendar parseDateTime(String baseDate) {
		Calendar cal = new GregorianCalendar();
		int yy = Integer.parseInt(baseDate.substring(0, 4));
		int mm = Integer.parseInt(baseDate.substring(4, 6));
		int dd = Integer.parseInt(baseDate.substring(6, 8));
		int hh = 0;
		int mi = 0;
		int ss = 0;
		int sss = 0;
		if (baseDate.length() > 12 && baseDate.length() < 21) {
			hh = Integer.parseInt(baseDate.substring(11, 13));
			mi = Integer.parseInt(baseDate.substring(14, 16));
			ss = Integer.parseInt(baseDate.substring(17, 19));
		}
		if (baseDate.length() > 21) {
			hh = Integer.parseInt(baseDate.substring(11, 13));
			mi = Integer.parseInt(baseDate.substring(14, 16));
			ss = Integer.parseInt(baseDate.substring(17, 19));
			sss = Integer.parseInt(baseDate.substring(20, 23));
		}
		cal.set(yy, mm, dd, hh, mi, ss);
		cal.set(Calendar.MILLISECOND, sss);
		return cal;
	}

	/**
	 * 格式化时间
	 * <p>用于格式化时间
	 * @param date 需要被格式化的时间
	 * @param format 时间格式
	 * @return 格式化后的时间
	 */
	public static String formatDateTime(String date, String format) {
		return new SimpleDateFormat(format).format(parseDateTime(date).getTime());
	}

	/**
	 * 根据字符串时间格式获取日
	 * <p>用于获取字符串时间中的日，用Calendar方式
	 * @param date 时间
	 * @return 日
	 */
	public static int getDay(String date) {
		return parseDateTime(date).get(Calendar.DATE);
	}
        
	/**
	 * 根据字符串时间格式获取日
	 * <p>用于获取字符串时间中的日，用Calendar方式
	 * @param date 时间
	 * @return 日
	 */
	public static int getDay(Date date) {
                String myDate = getNowDate(date);
		return parseDateTime(myDate).get(Calendar.DATE);
	}

	/**
	 * 根据字符串时间格式获取月
	 * <p>用于获取字符串时间中的月，用Calendar方式
	 * @param date 时间
	 * @return 月
	 */
	public static int getMonth(String date) {
		return parseDateTime(date).get(Calendar.MONTH) + 1;
	}
        
	/**
	 * 根据字符串时间格式获取月
	 * <p>用于获取字符串时间中的月，用Calendar方式
	 * @param date 时间
	 * @return 月
	 */
	public static int getMonth(Date date) {
                String myDate = getNowDate(date);
		return parseDateTime(myDate).get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 根据字符串时间格式获取年
	 * <p>用于获取字符串时间中的年，用Calendar方式
	 * @param date 时间
	 * @return 年
	 */
	public static int getYear(String date) {
		return parseDateTime(date).get(Calendar.YEAR);
	}
        
	/**
	 * 根据字符串时间格式获取年
	 * <p>用于获取字符串时间中的年，用Calendar方式
	 * @param date 时间
	 * @return 年
	 */
	public static int getYear(Date date) {
                String myDate = getNowDate(date);
		return parseDateTime(myDate).get(Calendar.YEAR);
	}

	/**
	 * 根据字符串时间格式获取星期
	 * <p>用于获取字符串时间中的星期，用Calendar方式，返回数字，1表示星期日，2表示星期一，以此类推
	 * @param date 时间
	 * @return 星期
	 */
	public static int getWeekDay(String date) {
		return parseDateTime(date).get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据字符串时间格式获取星期
	 * <p>用于获取字符串时间中的星期，用Calendar方式，返回中文，星期日-星期六
	 * @param date 时间
	 * @return 星期
	 */
	public static String getWeekDayName(String date) {
		String weekName[] = { "日", "一", "二", "三", "四", "五", "六" };
		return "星期" + weekName[getWeekDay(date) - 1];
	}

	/**
	 * 日期加法运算
	 * <p>用于进行加法运算
	 * @param iCount 增加数量，可为负值
	 * @param iType 增加的类型：0-年、1-月、2-日、3-时、4-分、5-秒
	 * @return 运算后的日期，若包含时分秒则格式为yyyy-MM-dd HH:mm:ss，若不包含时分秒，则格式为yyyy-MM-dd
	 */
	public static String dateAdd(String date, int iCount, int iType) {
		Calendar Cal = parseDateTime(date);
		int pType = 0;
		if (iType == 0)
			pType = 1;
		else if (iType == 1)
			pType = 2;
		else if (iType == 2)
			pType = 5;
		else if (iType == 3)
			pType = 10;
		else if (iType == 4)
			pType = 12;
		else if (iType == 5)
			pType = 13;
		Cal.add(pType, iCount);
		SimpleDateFormat sdf = null;
		if (iType <= 2)
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		else
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(Cal.getTime());
	}

	/**
	 * 日期减法运算
	 * <p>用于进行日期减法运算，使用开始时间减去结束时间
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param iType 返回值类型：0-毫秒、1-秒、2-分钟、3-小时、4-天
	 * @return 日期减法运算后的结果
	 */
	public static int dateDiff(String beginDate, String endDate, int iType) {
		Calendar calBegin = parseDateTime(beginDate);
		Calendar calEnd = parseDateTime(endDate);
		long lBegin = calBegin.getTimeInMillis();
		long lEnd = calEnd.getTimeInMillis();
		int ms = (int) (lBegin - lEnd);
		int ss = (int) ((lBegin - lEnd) / 1000L);
		int min = ss / 60;
		int hour = min / 60;
		int day = hour / 24;
		if (iType == 0)
			return ms;
		else if (iType == 1)
			return ss;
		else if (iType == 2)
			return min;
		else if (iType == 3)
			return hour;
		else if (iType == 4)
			return day;
		else
			return -1;
	}

	/**
	 * 判断某年是否为闰年
	 * <p>用于判断某年是否为闰年
	 * @param year 年份
	 * @return 是否为闰年
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && (year % 100 != 0))
			return true;
		else if (year % 400 == 0)
			return true;
		else
			return false;
	}

	/**
	 * 获取当前时间为该年度的第几周
	 * <p>用于获取当前时间为该年度的第几周
	 * @return 当前时间为该年度的第几周
	 */
	public static int getWeekNumOfYear() {
		return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取指定日期为该年度的第几周
	 * <p>用于获取指定日期为该年度的第几周，日期格式为yyyy-MM-dd
	 * @param date 日期，格式为yyyy-MM-dd
	 * @return 该年度的第几周
	 * @throws Exception 抛出格式化异常
	 */
	public static int getWeekNumOfYearDay(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 计算某年某周的开始日期
	 * <p>用于计算某年某周的开始日期
	 * @param year 年份
	 * @param week 第几周
	 * @return 开始日期，格式为yyyy-MM-dd
	 * @throws Exception 抛出格式化异常
	 */
	public static String getYearWeekFirstDay(int year, int week) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String tempYear = Integer.toString(year);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return getDateFormat(tempDate, "yyyy-MM-dd");

	}

	/**
	 * 计算某年某周的结束日期
	 * <p>用于计算某年某周的结束日期
	 * @param year 年份
	 * @param week 第几周
	 * @return 结束日期，格式为yyyy-MM-dd
	 * @throws Exception 抛出格式化异常
	 */
	public static String getYearWeekEndDay(int year, int week) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.WEEK_OF_YEAR, week + 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String tempYear = Integer.toString(year);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return getDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * 计算某年某月的开始日期
	 * <p>用于计算某年某月的开始日期
	 * @param year 年份
	 * @param month 第几月
	 * @return 开始日期，格式为yyyy-MM-dd
	 * @throws Exception 抛出格式化异常
	 */
	public static String getYearMonthFirstDay(int year, int month) throws ParseException {
		String tempYear = Integer.toString(year);
		String tempMonth = Integer.toString(month);
		String tempDay = "1";
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return getDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * 计算某年某月的结束日期
	 * <p>用于计算某年某月的结束日期
	 * @param year 年份
	 * @param month 第几月
	 * @return 结束日期，格式为yyyy-MM-dd
	 * @throws Exception 抛出格式化异常
	 */
	public static String getYearMonthEndDay(int year, int month) throws ParseException {
		String tempYear = Integer.toString(year);
		String tempMonth = Integer.toString(month);
		String tempDay = "31";
		if (tempMonth.equals("1") || tempMonth.equals("3")
				|| tempMonth.equals("5") || tempMonth.equals("7")
				|| tempMonth.equals("8") || tempMonth.equals("10")
				|| tempMonth.equals("12"))
			tempDay = "31";
		if (tempMonth.equals("4") || tempMonth.equals("6")
				|| tempMonth.equals("9") || tempMonth.equals("11"))
			tempDay = "30";
		if (tempMonth.equals("2"))
			tempDay = isLeapYear(year) ? "29" : "28";
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return getDateFormat(tempDate, "yyyy-MM-dd");
	}

	/**
	 * 计算某年某月的结束日期，只返回日
	 * <p>用于计算某年某月的结束日期，只返回日
	 * @param year 年份
	 * @param month 第几月
	 * @return 结束日期日
	 * @throws Exception 抛出格式化异常
	 */
	public static String getYearMonthEndDayReturnDay(int year, int month) throws ParseException {
		String tempMonth = Integer.toString(month);
		String tempDay = "31";
		if (tempMonth.equals("1") || tempMonth.equals("3")
				|| tempMonth.equals("5") || tempMonth.equals("7")
				|| tempMonth.equals("8") || tempMonth.equals("10")
				|| tempMonth.equals("12"))
			tempDay = "31";
		if (tempMonth.equals("4") || tempMonth.equals("6")
				|| tempMonth.equals("9") || tempMonth.equals("11"))
			tempDay = "30";
		if (tempMonth.equals("2"))
			tempDay = isLeapYear(year) ? "29" : "28";
		return tempDay;
	}
        
        /**
         * 获取某一日期距今的年月
         * @param dateStart 开始日期
         * @return x年y个月
         */
        public static String getYearMonthDayToNow(Date dateStart) {
            int rmonth = 0, ryear = 0;
            int year = DateUtils.getYear(dateStart);
            int month = DateUtils.getMonth(dateStart);
            int yearNow = DateUtils.getYear(new Date());
            int monthNow = DateUtils.getMonth(new Date());
            if(monthNow > month) {
                rmonth = monthNow - month;
                ryear = yearNow - year;
            }else if (monthNow < month && yearNow > year) {
                rmonth = 12 + monthNow - month;
                ryear = yearNow - year - 1;
            }
            
            String rdate = "";
            rdate += ryear>0 ? ryear+"年" : "";
            rdate += rmonth>0 ? rmonth+"个月" : "";
            return rdate;
        }
        
	/**
	 * 获取某个日期前n个月的相对应的一天
	 * <p>用于获取某个日期前n个月的相对应的一天，格式为yyyy-MM-dd
	 * @param date 日期
	 * @param n 前几个月
	 * @return 前n个月的相对应的一天
	 */
	public static String getNMonthBeforeOneDay(String date, int n) {
		Calendar c = switchStringToCalendar(date);
		c.add(c.MONTH, -n);
		return "" + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-" + c.get(c.DATE);
	}

	/**
	 * 获取某个日期后n个月的相对应的一天
	 * <p>用于获取某个日期后n个月的相对应的一天，格式为yyyy-MM-dd
	 * @param date 日期
	 * @param n 后几个月
	 * @return 后n个月的相对应的一天
	 */
	public static String getNMonthAfterOneDay(String date, int n) {
		Calendar c = switchStringToCalendar(date);
		c.add(c.MONTH, n);
		return "" + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-" + c.get(c.DATE);
	}

	/**
	 * 获取某个日期前n天日期
	 * <p>用于获取某个日期前n天日期，格式为yyyy-MM-dd
	 * @param date 日期
	 * @param n 前几天
	 * @return 前n天日期
	 */
	public static String getNDayBeforeOneDate(String date, int n) {
		Calendar c = switchStringToCalendar(date);
		c.add(c.DAY_OF_MONTH, -n);
		return "" + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-" + c.get(c.DATE);
	}

	/**
	 * 获取某个日期后n天日期
	 * <p>用于获取某个日期后n天日期，格式为yyyy-MM-dd
	 * @param date 日期
	 * @param n 后几天
	 * @return 后n天日期
	 */
	public static String getNDayAfterOneDate(String date, int n) {
		Calendar c = switchStringToCalendar(date);
		c.add(c.DAY_OF_MONTH, n);
		return "" + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-" + c.get(c.DATE);
	}

	/**
	 * 日期字符串转化成Calendar类型日期
	 * <p>用于将日期字符串转化成Calendar类型日期
	 * @param date 待转换日期字符串
	 * @return 转换为Calendar类型的日期对象
	 */
	public static Calendar switchStringToCalendar(String date) {
		Calendar c = Calendar.getInstance();
		c.setTime(switchStringToDate(date));
		return c;
	}

	/**
	 * 日期对象转化成Calendar类型日期
	 * <p>用于将日期对象转化成Calendar类型日期
	 * @param date 待转换日期对象
	 * @return 转换为Calendar类型的日期对象
	 */
	public Calendar switchStringToCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * 日期字符串转化成日期对象
	 * <p>用于将日期字符串转化成日期对象，支持格式为yyyy-MM-dd
	 * @param sDate 待转换的日期字符串，格式为yyyy-MM-dd
	 * @return 转换后的日期对象
	 */
	public static Date switchStringToDate(String sDate) {
		Date date = null;
                if(sDate.length()==10) {
                    try {
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            date = df.parse(sDate);
                    } catch (Exception e) {
                            System.out.println("日期转换失败:" + e.getMessage());
                    }
                }else if(sDate.length()<10) {
                    String s[] = sDate.split("-");
                    if(s.length == 3) {
                        String year = s[0];
                        String month = s[1];
                        String day = s[2];
                        year = year.length()==4?year:"20"+year;
                        month = month.length()==2?month:"0"+month;
                        day = day.length()==2?day:"0"+day;
                        return switchStringToDate(year+"-"+month+"-"+day);
                    }
                }
		return date;
	}

	/**
	 * 比较两日期大小 
	 * <p>用于比较两个日期的大小，格式为yyyy-MM-dd，若date1大于或等于date2，则返回true
	 * @param date1 需要比较的日期
	 * @param date2 需要比较的日期
	 * @return 比较结果
	 * @throws Exception 抛出格式化异常
	 */
	public static boolean compDate(String date1, String date2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date2).after(sdf.parse(date1)) ? false : true;
	}

	/**
	 * 获取月份间的日期列表
	 * <p>用于获取月份间的日期列表，存在于List<String>类型中，月份格式yyyyMM，返回日期格式为yyyy-MM-dd
	 * @param begin 开始月份，格式为yyyyMM
	 * @param end 结束月份，格式为yyyyMM
	 * @return 月份间的日期列表，List<String>格式，日期格式为yyyy-MM-dd
	 * @throws Exception 抛出格式化异常
	 */
	public static List<String> getMonthList(String begin, String end) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		return getDayList(format.parse(begin), format.parse(end));
	}

	/**
	 * 获取日期间的日期列表
	 * <p>用于获取日期间的日期列表，存在于List<String>类型中，返回日期格式为yyyy-MM-dd
	 * @param begin 开始日期，Date类型对象
	 * @param end 结束日期，Date类型对象
	 * @return 日期间的日期列表，List<String>格式，日期格式为yyyy-MM-dd
	 * @throws Exception 抛出格式化异常
	 */
	public static List<String> getDayList(Date begin, Date end) throws ParseException {
		List<String> ls = new ArrayList<String>();
		String str;
		Calendar cal0 = new GregorianCalendar();
		Calendar cal1 = new GregorianCalendar();
		cal0.setTime(begin);
		cal1.setTime(end);
		Date date;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; cal1.after(cal0); i++) {
			str = String.valueOf(cal0.get(Calendar.YEAR));
			str = str + "-" + String.valueOf(cal0.get(Calendar.MONTH) + 1);
			str = str + "-" + String.valueOf(cal0.get(Calendar.DATE));
			date = new Date();
			date = format.parse(str);
			ls.add(format.format(date));
			cal0.add(Calendar.DATE, 1);
		}
		if (cal1.get(Calendar.MONTH) != cal0.get(Calendar.MONTH)) {
			str = String.valueOf(cal0.get(Calendar.YEAR));
			str = str + String.valueOf(cal0.get(Calendar.MONTH) + 1);
			str = str + String.valueOf(cal0.get(Calendar.DATE));
			date = new Date();
			date = format.parse(str);
			ls.add(format.format(date));
		}
		return ls;
	}

	/**
	 * 获取日期字符串间的日期列表
	 * <p>用于获取日期字符串间的日期列表，存在于List<String>类型中，返回日期格式为yyyy-MM-dd
	 * @param begin 开始日期，日期字符串对象，格式为yyyyMMdd
	 * @param end 结束日期，日期字符串对象，格式为yyyyMMdd
	 * @return 日期间的日期列表，List<String>格式，日期格式为yyyy-MM-dd
	 * @throws Exception 抛出格式化异常
	 */
	public static List getDayList(String begin, String end) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMDD");
		return getDayList(format.parse(begin), format.parse(end));
	}
        
        /***
         * 获取某一时间的年份和月份 格式yyyy年/MM月
         * @param date
         * @return 
         */
        public static String getYearMonth(Date date) {
            String myDate = "";
            if(date != null) {
                String year = getYear(date) + "";
                String month = getMonth(date) + "";
                if(month.charAt(0) == '0') {
                    month = month.substring(1, 1);
                }
                myDate = year + "/" + month;
            }else {
                myDate = "至今";
            }
            return myDate;
        }
        
        /**
         * 将java.util.Date类型时间转换成java.sql.Date类型
         * @param date
         * @return 
         */
        public static java.sql.Date switchUDateToSDate(java.util.Date date) {
            if(null != date) {
                return new java.sql.Date(date.getTime());
            }else {
                return null;
            }
        }
        
        /**
         * 将java.sql.Date类型时间转换成java.util.Date类型
         * @param date
         * @return 
         */
        public static java.util.Date switchSDateToUDate(java.sql.Date date) {
            if(null != date) {
                return new java.util.Date(date.getTime());
            }else {
                return null;
            }
        }
}
