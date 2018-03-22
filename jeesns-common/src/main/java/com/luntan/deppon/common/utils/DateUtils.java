//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.luntan.deppon.common.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public DateUtils() {
    }

    public static String getDate2Str(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        if(date == null) {
            return "";
        } else {
            simpleDateFormat.applyPattern(format);
            return simpleDateFormat.format(date);
        }
    }

    public static String getDate2yymmddHHmmStr(Date date) {
        if(date == null) {
            date = new Date();
        }

        return getDate2Str("yyyyMMddHHmm", date);
    }

    private static Date getStrToDate(String format, String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(format);
        ParsePosition parseposition = new ParsePosition(0);
        return simpleDateFormat.parse(str, parseposition);
    }

    public static String getDate2SStr(Date date) {
        return getDate2Str("yyyy-MM-dd HH:mm:ss", date);
    }

    public static String getDate2ymdStr(Date date) {
        return date == null?"":getDate2Str("yyyy-MM-dd", date);
    }

    public static String getDate2yymmddStr(Date date) {
        return date == null?"":getDate2Str("yyyyMMdd", date);
    }

    public static String getDate2yymmddHHmmssStr(Date date) {
        return date == null?"":getDate2Str("yyyyMMddHHmmss", date);
    }

    public static Date getStr2SDate(String str) {
        return getStrToDate("yyyy-MM-dd HH:mm:ss", str);
    }

    public static String getDateTime2Right(String str) {
        str = str + " 23:59:59";
        return !isDateTime(str)?null:str;
    }

    private static boolean isDateTime(String dateTime) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        Date dt = df.parse(dateTime, pos);
        return dt != null;
    }

    public static Date addYears(Date date, int amount) {
        return add(date, 1, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, 2, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, 3, amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, 5, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, 11, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, 12, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return add(date, 14, amount);
    }

    private static Date add(Date date, int calendarField, int amount) {
        if(date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }

    public static void main(String[] args) {
        System.out.println(getDate2Str("yyyy-MM-dd", new Date()));
    }

    public static String convert(Date date, String dateFormat) {
        if(date == null) {
            return null;
        } else {
            if(null == dateFormat) {
                dateFormat = "yyyy-MM-dd HH:mm:ss";
            }

            return (new SimpleDateFormat(dateFormat)).format(date);
        }
    }
}
