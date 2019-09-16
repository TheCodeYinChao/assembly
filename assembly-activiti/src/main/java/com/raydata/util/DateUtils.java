package com.raydata.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

// +----------------------------------------------------------------------
// | ProjectName: usercenter
// +----------------------------------------------------------------------
// | Date: 2018/8/6
// +----------------------------------------------------------------------
// | Time: 10:43
// +----------------------------------------------------------------------
// | Author: yinshuang.meng <yinshuang.meng@raykite.com>
// +----------------------------------------------------------------------
public class DateUtils {
    public static final String YMD = "yyyy-MM-dd";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式yyyy-MM-dd
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 订单格式
     */
    public final static String ORDER_PATTERN = "yyMMdd";
    /**
     *
     */
    public final static String yyyyMMdd = "yyyyMMdd";

    /**
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 构造函数
     */
    private DateUtils() {
        super();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date Date对象
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param dateTime LocalDateTime对象
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 格式化时间-默认yyyy-MM-dd HH:mm:ss格式
     *
     * @param date Date对象
     * @return
     */
    public static String dateToStr(Date date) {
        LocalDateTime localDateTime = DateUtils.dateToLocalDateTime(date);
        return formatDateTime(localDateTime);
    }

    /**
     * 格式化时间-默认yyyy-MM-dd HH:mm:ss格式
     *
     * @param dateTime LocalDateTime对象
     * @return
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateTime(dateTime, DATE_TIME_PATTERN);
    }

    /**
     * 按pattern格式化时间-默认yyyy-MM-dd HH:mm:ss格式
     *
     * @param date    Date对象
     * @param pattern 要格式化的字符串
     * @return
     */
    public static String dateToStr(Date date, String pattern) {
        LocalDateTime dateTime = dateToLocalDateTime(date);
        if (date == null) {
            return null;
        }
        if (pattern == null || pattern.isEmpty()) {
            pattern = DATE_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 按pattern格式化时间-默认yyyy-MM-dd HH:mm:ss格式
     *
     * @param dateTime LocalDateTime对象
     * @param pattern  要格式化的字符串
     * @return
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        if (pattern == null || pattern.isEmpty()) {
            pattern = DATE_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateTime(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (pattern == null || pattern.isEmpty()) {
            pattern = DATE_TIME_PATTERN;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * 获取今天的00:00:00
     *
     * @return
     */
    public static String getDayStart() {
        return getDayStart(LocalDateTime.now());
    }

    /**
     * 获取今天的23:59:59
     *
     * @return
     */
    public static String getDayEnd() {
        return getDayEnd(LocalDateTime.now());
    }

    /**
     * 获取某天的00:00:00
     *
     * @param dateTime
     * @return
     */
    public static String getDayStart(LocalDateTime dateTime) {
        return formatDateTime(dateTime.with(LocalTime.MIN));
    }

    /**
     * 获取某天的23:59:59
     *
     * @param dateTime
     * @return
     */
    public static String getDayEnd(LocalDateTime dateTime) {
        return formatDateTime(dateTime.with(LocalTime.MAX));
    }

    /**
     * 获取本月第一天的00:00:00
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        return getFirstDayOfMonth(LocalDateTime.now());
    }

    /**
     * 获取本月最后一天的23:59:59
     *
     * @return
     */
    public static String getLastDayOfMonth() {
        return getLastDayOfMonth(LocalDateTime.now());
    }

    /**
     * 获取某月第一天的00:00:00
     *
     * @param dateTime LocalDateTime对象
     * @return
     */
    public static String getFirstDayOfMonth(LocalDateTime dateTime) {
        return formatDateTime(dateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN));
    }

    /**
     * 获取某月最后一天的23:59:59
     *
     * @param dateTime LocalDateTime对象
     * @return
     */
    public static String getLastDayOfMonth(LocalDateTime dateTime) {
        return formatDateTime(dateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX));
    }

    /**
     * description: 获取当前时间的时间戳单位（s）
     *
     * @param: []
     * @return: java.lang.Long
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/6 10:44
     * @updateBy:
     * @updateTime:
     */
    public static Long getNowTimestamps() {
        return getNowTimestamp() / 1000;
    }

    /**
     * 安全的str to date
     * 年月日时分秒
     * @param time
     * @param formart
     * @return
     */
    public static Date strToDate(String time, String formart) {
        if (StringUtils.isEmpty(time) || StringUtils.isEmpty(formart)) {
            return null;
        }
        LocalDateTime parse = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(formart));
        return localDateTimeToDate(parse);
    }

    /**
     * 年月日
     *
     * @param time
     * @param formart
     * @return
     */
    public static Date str2Date(String time, String formart) {
        if (StringUtils.isEmpty(time) || StringUtils.isEmpty(formart)) {
            return null;
        }
        LocalDate parse = LocalDate.parse(time, DateTimeFormatter.ofPattern(formart));
        return localDateTimeToDate(parse.atStartOfDay());
    }

    /**
     * description: 当前时间戳，单位ms
     *
     * @param: []
     * @return: java.lang.Long
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/7 20:11
     * @updateBy:
     * @updateTime:
     */
    public static Long getNowTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * description:获取string类型的当前时间戳(s)
     *
     * @param: []
     * @return: java.lang.String
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/6 10:49
     * @updateBy:
     * @updateTime:
     */
    public static String getNowTimestampSecondString() {

        return String.valueOf(getNowTimestamps());
    }

    /**
     * description:获取string类型的当前时间戳(ms)
     *
     * @param: []
     * @return: java.lang.String
     * @author: yinshuang.meng <yinshuang.meng@raykite.com>
     * @createTime: 2018/8/6 10:49
     * @updateBy:
     * @updateTime:
     */
    public static String getNowTimestampString() {

        return String.valueOf(getNowTimestamp());
    }

    /**
     * 取几天前时间
     *
     * @param format
     * @param day    默认取前一天
     * @return
     */
    public static Date getBeforDateFormat(String format, Integer day) {
        String timeFormat = YMDHMS;
        if (StringUtils.isNotEmpty(format)) {
            timeFormat = format;
        }
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (day == null) {
            day = -1;
        }
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();

        SimpleDateFormat sf = new SimpleDateFormat(timeFormat);
        String d = sf.format(date);
        try {
            return sf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        System.out.println(getNowTimestampString());
//        System.out.println(getDayStart());
//        System.out.println(getDayEnd());
//        System.out.println(getFirstDayOfMonth());
//        System.out.println(getLastDayOfMonth());


//        System.out.println(formatDateTime(new Date(), DateUtils.yyyyMMdd));

//        Date date = strToDate("20140603", DateUtils.yyyyMMdd);
//        System.out.println(date.toString());
        Date date1 = strToDate("2019-08-20 23:59:59", "yyyy-MM-dd HH:mm:ss");
        System.out.println(date1);


        /*Long timestamp = getTimestamp(-1, DateUtils.YMD).getTime();

        Date dateByTimestamp = getDateByTimestamp(1558972800000l, "yyyy-MM-dd ");
        System.out.println(timestamp);
        System.out.println(dateByTimestamp);*/
    }

    /**
     * 根据格式获取几天前，几天后的时间戳
     *
     * @param day
     * @param ymd
     * @return
     */
    public static Date getTimestamp(Integer day, String ymd) {
        Date beforDateFormat = getBeforDateFormat(ymd, day);
        return beforDateFormat;
    }

    /**
     * 根据格式时间戳 获取时间
     *
     * @param timstamp
     * @param ymd
     * @return
     */
    public static Date getDateByTimestamp(Long timstamp, String ymd) {
        Date date = new Date(timstamp);
        SimpleDateFormat sf = new SimpleDateFormat(ymd);
        return strToDate(sf.format(date), ymd);
    }
}
