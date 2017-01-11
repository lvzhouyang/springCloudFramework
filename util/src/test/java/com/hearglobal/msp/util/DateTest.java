package com.hearglobal.msp.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/10.
 * <p>
 * The tests for DateUtils
 */
public class DateTest {

    Date date = new Date();
    String dateStr = "";
    Integer secondInt = 0;

    @Before
    public void init() {
        dateStr = ObjectUtil.toString(date);
        secondInt = 1234567890;
    }

    @Test
    //Date 转换成string 类型
    public void Date2String() {
        System.out.println(DateUtil.Date2String(date));
    }

    @Test
    //string 转 date 00:00:00
    public void string2DateDay() {
        System.out.println(DateUtil.string2DateDay(dateStr));
    }

    @Test
    //string 转 date sec24
    public void string2DateSecond24() {
        System.out.println(DateUtil.string2DateSecond24(dateStr));
    }

    @Test
    //秒转时间
    public void secondsToString() {
        System.out.println(DateUtil.secondsToString(secondInt));
    }

    @Test
    //date 转string 格式为 yyyy-MM-dd HH:mm:ss
    public void Date2StringSec() {
        System.out.println(DateUtil.Date2StringSec(secondInt));
    }

    @Test
    //当前时间的秒数
    public void fromUnixTime() {
        System.out.println(DateUtil.fromUnixTime(secondInt));
    }

    @Test
    //UnixTime 转 date
    public void unixTime() {
        System.out.println(DateUtil.unixTime());
    }


    @Test
    //date 格式化为 天00：00：00
    public void toDay() {
        System.out.println(DateUtil.toDay(date));
    }

    @Test
    // 根据传入的日期（天）字符串得到对应的Unixtime
    public void day2Unixtime() {
        System.out.println(DateUtil.day2Unixtime(dateStr));
    }

    @Test
    //date 格式化为yyyyMMdd
    public void Date2IntDay() {
        System.out.println(DateUtil.Date2IntDay(date));
    }

    @Test
    //date 格式化为 天23：59：59
    public void toNight() {
        System.out.println(DateUtil.toNight(date));
    }

    @Test
    //得到date 的 年 月 周几
    public void getYearMonthWeek() {
        System.out.println(DateUtil.getYear(date));
        System.out.println(DateUtil.getMonth(date));
        System.out.println(DateUtil.getDayOfWeek(date));
    }

    @Test
    //判断两个是不是时间相同
    public void isSameDate() {
        System.out.println(DateUtil.isSameDate(date, new Date()));
    }

    @Test
    //string 转换为时间戳
    public void stringToTimestamp() {
        System.out.println(DateUtil.stringToTimestamp(dateStr));
    }

    @Test
    //列出起止时间字符串之间的时间字符串集合格式为年月日
    public void dateBetween() {
        String date1str = "2017-1-7";
        System.out.println(DateUtil.dateBetween(date1str, dateStr));
    }

    @Test
    //string yyyyMMdd to yyyy-MM-dd
    public void transDateStr() {
        dateStr = "20170110";
        System.out.println(DateUtil.transDateStr(dateStr));
    }
}
