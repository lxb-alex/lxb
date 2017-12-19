package com.lxb.common.utils;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/12/19 0019 15:40:40
 */
public class DateUtil {

    /**
     * 将 TimeStamp 格式时间转换为 Date 时间格式<br/>
     * @param longDate 一串纯数字格式的时间 如：1452873600000
     * @return
     */
    public static Date timeStampToDate(long longDate){
        Timestamp timestamp = new Timestamp(longDate);
        Date date = timestamp;
        return date;
    }

    /**
     * @param date 格式为Thu Dec 17 00:00:00 CST 2015的时间（TimeStamp）
     * @return 正常时间格式（date）
     */
    public static Date timeStampToDate(Date date){
        long longDate = date.getTime();
        return timeStampToDate(longDate);
    }

    /**
     * @param timeStr {nanos=0, time=1452873600000, minutes=0, seconds=0, hours=0, month=0, year=116, timezoneOffset=-480, day=6, date=16}
     * @return YYYY-MM-dd 格式的时间字符串
     */
    public static Date strConvertDate(String timeStr){
        timeStr = timeStr.substring(timeStr.indexOf("e=")+2, timeStr.indexOf(", minutes"));
        long longDate = Long.valueOf(timeStr);
        return timeStampToDate(longDate);
    }

    /**
     * 得到当前时间的自定义格式的字符串
     * @param format 显示格式
     */
    public static String getNowDateStr(String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 将时间格式转换为 自定义 格式显示 的字符串
     * @param date 时间
     * @param format 转换格式
     */
    public static String dateConvertString(Date date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static void main(String[] args) {
        String a = "a";
        int b = 2;
        long l = 3l;
        changed(a,b, l);
        System.out.println(a);
        System.out.println(b);
        System.out.println(l);
    }
    public static void changed(String a, int b, long l){
        a = "aa";
        b = 5;
        l = 9;

    }


}
