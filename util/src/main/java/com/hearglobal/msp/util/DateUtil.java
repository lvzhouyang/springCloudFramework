package com.hearglobal.msp.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public final static String DefaultShortFormat = "yyyy-MM-dd";
    public final static String DefaultLongFormat = "yyyy-MM-dd HH:mm:ss";
    public final static String DefaultMinuteFormat = "yyyy-MM-dd HH:mm";

    /**
     * the string format must yyyy-MM-dd
     *
     * @param str
     * @return
     */
    public static Date string2DateDay(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultShortFormat);
        str = StringUtil.trimToNull(str);
        try {
            return formatter.parse(str);
        } catch (ParseException e) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
    }

    /**
     * 格式化字符串成Date.格式不正确的时候抛异常出来
     *
     * @param str must be yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static Date string2DateDay4Exception(String str) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultShortFormat);
        str = StringUtil.trimToNull(str);
        return formatter.parse(str);
    }

    /**
     * the string format must yyyy-MM-dd HH:mm:ss
     *
     * @param str
     * @return
     * 2011-4-21
     */
    public static Date string2DateSecond24(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultLongFormat);
        str = StringUtil.trimToNull(str);
        String matchStr = "[0-2]\\d\\d\\d-\\d\\d-\\d\\d [0-2]\\d:[0-6]\\d";
        if (StringUtil.regexMatch(str, matchStr)) {
            str = str + ":00";
        }
        try {
            return formatter.parse(str);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * the string format must yyyy-MM-dd HH:mm
     *
     * @param str
     * @return
     */
    public static Date stirng2DateMinute(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultMinuteFormat);
        str = StringUtil.trimToNull(str);
        try {
            return formatter.parse(str);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * must yyyy MM dd HH mm ss
     *
     * @param str
     * @param formatString
     * @return
     */
    public static Date stirng2Date(String str, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        str = StringUtil.trimToNull(str);
        try {
            return formatter.parse(str);
        } catch (ParseException e) {
            return new Date();
        } catch (IllegalArgumentException e) {
            logger.warn("format string Illegal: {},{}",formatString,e);
            return null;
        }
    }

    /**
     * 字符串转日期.如果异常则返回null
     *
     * @param str          日期字符串
     * @param formatString 格式化的格式代码 yyyy MM dd HH mm ss
     * @return
     */
    public static Date string2Date4Null(String str, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        str = StringUtil.trimToNull(str);
        try {
            return formatter.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String Date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultShortFormat);
        return formatter.format(date);
    }

    /**
     * yyyyMMdd
     *
     * @param date
     * @return
     */
    public static Integer Date2IntDay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(formatter.format(date));
    }

    /**
     * 秒转日期
     *
     * @param seconds
     * @return
     */
    public static String secondsToString(Integer seconds) {
        return Date2String(fromUnixTime(seconds));
    }

    /**
     * @param date
     * @return yyyy-MM-dd HH:mm的字符串
     */
    public static String Date2StringMin(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultMinuteFormat);
        return formatter.format(date);
    }

    /**
     * @param date
     * @return yyyy-MM-dd HH:mm:ss的字符串
     */
    public static String Date2StringSec(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DefaultLongFormat);
        return formatter.format(date);
    }

    /**
     * @param seconds
     * @return 距当前时间的秒数
     */
    public static String Date2StringSec(Integer seconds) {
        return Date2StringSec(fromUnixTime(seconds));
    }

    /**
     * must yyyy MM dd HH mm ss
     *
     * @param date
     * @param formatString
     * @return
     */
    public static String Date2String(Date date, String formatString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatString);
            return formatter.format(date);
        } catch (IllegalArgumentException e) {
            logger.warn("format string Illegal: {},{}",formatString,e);
            return "";
        }
    }

    /**
     * 返回当前时间的秒数
     *
     * @return
     */
    public static int unixTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 把秒转换为Date
     *
     * @param seconds
     * @return
     */
    public static Date fromUnixTime(Integer seconds) {
        return new Date(seconds * 1000L);
    }

    /**
     * @return
     */
    public static Date today() {
        return toDay(new Date());
    }

    public static Date toDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 指定日期当天的最后一秒
     *
     * @param date
     * @return
     */
    public static Date toNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 返回开始到结束时间所有的日期
     *
     * @param startDateStr （yyyy-MM-dd） 开始时间
     * @param endDateStr   （yyyy-MM-dd） 结束时间
     * @return
     */
    public static List<String> dateBetween(String startDateStr, String endDateStr) {
        List<String> dateList = new ArrayList<String>();
        java.util.Date endDate = string2DateDay(endDateStr);
        java.util.Date startDate = string2DateDay(startDateStr);
        long day = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000); // 相差天数
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i <= day; i++) {
            cal.setTime(startDate);
            cal.add(Calendar.DAY_OF_MONTH, i);
            dateList.add(Date2String(cal.getTime()));
        }
        return dateList;
    }

    /**
     * 将时间转换成昨天
     *
     * @param date
     * @return
     */
    public static Date toYesterday(Date date) {
        return add(date, Calendar.DAY_OF_YEAR, -1);
    }

    /**
     * 将时间转换成明天
     *
     * @param date
     * @return
     */
    public static Date toTommorow(Date date) {
        return add(date, Calendar.DAY_OF_YEAR, 1);
    }

    private static Date add(Date date, int field, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(field, value);
        return cal.getTime();
    }

    /**
     * 得到某年某月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(Integer year, Integer month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(Calendar.YEAR);
    }

    /**
     * 得到月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到某天的星期.1~~7
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int rel = 0;
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                rel = 1;
                break;
            case Calendar.TUESDAY:
                rel = 2;
                break;
            case Calendar.WEDNESDAY:
                rel = 3;
                break;
            case Calendar.THURSDAY:
                rel = 4;
                break;
            case Calendar.FRIDAY:
                rel = 5;
                break;
            case Calendar.SATURDAY:
                rel = 6;
                break;
            default:
                rel = 7;
                break;
        }
        return rel;
    }

    /**
     * 日期（天）转unixtime
     *
     * @param day
     * @return
     */
    public static int day2Unixtime(String day) {
        return (int) (DateUtil.string2DateDay(day).getTime() / 1000L);
    }

    /**
     * date转unixtime
     *
     * @param date
     * @return
     */
    public static int date2Unixtime(Date date) {
        return (int) (date.getTime() / 1000L);
    }

    /**
     * 得到时间
     *
     * @param date
     * @return
     */
    public static Date toTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, 1970);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    public static Boolean isExceedSomeDays(Date date, Integer dayCount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, dayCount);
        if (calendar.getTime().before(new Date())) {
            return true;
        }
        return false;

    }


    public static boolean isSameDate(Date firtDate, Date secondDate) {
        return toDay(firtDate).equals(toDay(secondDate));
    }

    public static Timestamp stringToTimestamp(String time) {
        return new Timestamp(stirng2Date(time, DefaultLongFormat).getTime());
    }

    public static String intSecond2Str(Integer second) {
        Long time = second * 1000l;
        Date date = new Date(time);
        return Date2StringSec(date);
    }

    /**
     * yyyyMMdd转yyyy-MM-dd
     */
    public static String transDateStr(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = formatter.parse(dateStr);
            return Date2String(date, DefaultShortFormat);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 得到几天前的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d,int day) {
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
        return now.getTime();
    }
}
