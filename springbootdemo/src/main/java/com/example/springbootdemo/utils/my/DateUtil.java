package com.example.springbootdemo.utils.my;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期比较
     *
     * @return true: t1 > t2
     */
    public static boolean compare(Date t1, Date t2, String formatPattern) {
        String format1 = dateToStr(t1);
        String format2 = dateToStr(t2);
        DateTimeFormatter formatter = DateTimeFormat.forPattern(formatPattern);
        DateTime season1 = DateTime.parse(format1, formatter);
        DateTime season2 = DateTime.parse(format2, formatter);
        return season1.isAfter(season2);
    }

    /**
     * date类型 -> string类型
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    /**
     * date类型 -> string类型
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static String dateToStr(Date date, String formatPattern) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatPattern);
    }

    /**
     * string类型 -> date类型
     *
     * @param timeStr
     * @return
     */
    public static Date strToDate(String timeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(timeStr);
        return dateTime.toDate();
    }

    /**
     * 判断date日期是否过期(与当前时刻比较)
     *
     * @param date
     * @return
     */
    public static boolean isTimeExpired(Date date) {
        if (null == date) {
            return true;
        }
        String timeStr = dateToStr(date);
        return isBeforeNow(timeStr);
    }

    /**
     * 判断date日期是否过期(与当前时刻比较)
     *
     * @param timeStr
     * @return
     */
    public static boolean isTimeExpired(String timeStr) {
        if (StringUtils.isBlank(timeStr)) {
            return true;
        }
        return isBeforeNow(timeStr);
    }

    /**
     * 判断timeStr是否在当前时刻之前
     *
     * @param timeStr
     * @return
     */
    private static boolean isBeforeNow(String timeStr) {
        DateTimeFormatter format = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = DateTime.parse(timeStr, format);
        return dateTime.isBeforeNow();
    }

    /**
     * 日期加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date plusDays(Date date, Integer days) {
        return plusOrMinusDays(date, days, 0);
    }

    /**
     * 日期减天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date minusDays(Date date, Integer days) {
        return plusOrMinusDays(date, days, 1);
    }

    /**
     * 加减天数
     *
     * @param date
     * @param days
     * @param type 0:加天数 1:减天数
     * @return
     */
    private static Date plusOrMinusDays(Date date, Integer days, Integer type) {
        if (null == date) {
            return null;
        }
        days = null == days ? 0 : days;

        DateTime dateTime = new DateTime(date);
        if (type == 0) {
            dateTime = dateTime.plusDays(days);
        } else {
            dateTime = dateTime.minusDays(days);
        }

        return dateTime.toDate();
    }

    /**
     * 日期加分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date plusMinutes(Date date, Integer minutes) {
        return plusOrMinusMinutes(date, minutes, 0);
    }

    /**
     * 日期减分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date minusMinutes(Date date, Integer minutes) {
        return plusOrMinusMinutes(date, minutes, 1);
    }

    /**
     * 加减分钟
     *
     * @param date
     * @param minutes
     * @param type    0:加分钟 1:减分钟
     * @return
     */
    private static Date plusOrMinusMinutes(Date date, Integer minutes, Integer type) {
        if (null == date) {
            return null;
        }
        minutes = null == minutes ? 0 : minutes;

        DateTime dateTime = new DateTime(date);
        if (type == 0) {
            dateTime = dateTime.plusMinutes(minutes);
        } else {
            dateTime = dateTime.minusMinutes(minutes);
        }

        return dateTime.toDate();
    }

    /**
     * 日期加月份
     *
     * @param date
     * @param months
     * @return
     */
    public static Date plusMonths(Date date, Integer months) {
        return plusOrMinusMonths(date, months, 0);
    }

    /**
     * 日期减月份
     *
     * @param date
     * @param months
     * @return
     */
    public static Date minusMonths(Date date, Integer months) {
        return plusOrMinusMonths(date, months, 1);
    }

    /**
     * 加减月份
     *
     * @param date
     * @param months
     * @param type   0:加月份 1:减月份
     * @return
     */
    private static Date plusOrMinusMonths(Date date, Integer months, Integer type) {
        if (null == date) {
            return null;
        }
        months = null == months ? 0 : months;

        DateTime dateTime = new DateTime(date);
        if (type == 0) {
            dateTime = dateTime.plusMonths(months);
        } else {
            dateTime = dateTime.minusMonths(months);
        }

        return dateTime.toDate();
    }

    /**
     * 判断target是否在开始和结束时间之间
     *
     * @param target
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isBetweenStartAndEndTime(Date target, Date startTime, Date endTime) {
        if (null == target || null == startTime || null == endTime) {
            return false;
        }
        DateTime dateTime = new DateTime(target);
        return dateTime.isAfter(startTime.getTime()) && dateTime.isBefore(endTime.getTime());
    }

    //1、获取简单日期
    //方法一：取系统点间
    DateTime dt1 = new DateTime();

    //方法二：通过java.util.Date对象生成
    DateTime dt2 = new DateTime(new Date());

    //方法三：指定年月日点分秒生成(参数依次是：年,月,日,时,分,秒,毫秒)
    DateTime dt3 = new DateTime(2012, 5, 20, 13, 14, 0, 0);

    //方法四：ISO8601形式生成
    DateTime dt4 = new DateTime("2012-05-20");
    DateTime dt5 = new DateTime("2012-05-20T13:14:00");

    //只需要年月日的时候
    LocalDate localDate = new LocalDate(2009, 9, 6);// September 6, 2009

    //只需要时分秒毫秒的时候
    LocalTime localTime = new LocalTime(13, 30, 26, 0);// 1:30:26PM
    //2、获取年月日点分秒
    DateTime dt = new DateTime();
    //年
    int year = dt.getYear();
    //月
    int month = dt.getMonthOfYear();
    //日
    int day = dt.getDayOfMonth();
    //星期
    int week = dt.getDayOfWeek();
    //点
    int hour = dt.getHourOfDay();
    //分
    int min = dt.getMinuteOfHour();
    //秒
    int sec = dt.getSecondOfMinute();
    //毫秒
    int msec = dt.getMillisOfSecond();

    //    3、星期的特殊处理
    DateTime dt13 = new DateTime();
    //星期
 /*   switch(dt13.getDayOfWeek())
        {
            case DateTimeConstants.SUNDAY:
                System.out.println("星期日");
                break;
            case DateTimeConstants.MONDAY:
                System.out.println("星期一");
                break;
            case DateTimeConstants.TUESDAY:
                System.out.println("星期二");
                break;
            case DateTimeConstants.WEDNESDAY:
                System.out.println("星期三");
                break;
            case DateTimeConstants.THURSDAY:
                System.out.println("星期四");
                break;
            case DateTimeConstants.FRIDAY:
                System.out.println("星期五");
                break;
            case DateTimeConstants.SATURDAY:
                System.out.println("星期六");
                break;
        }*/

    //4、与JDK日期对象的转换
    DateTime dt14 = new DateTime();
    //转换成java.util.Date对象
    Date d1 = new Date(dt14.getMillis());
    Date d2 = dt14.toDate();

    //转换成java.util.Calendar对象
    Calendar c1 = Calendar.getInstance();
    //c1.setTimeInMillis(dt14.getMillis());
    Calendar c2 = dt14.toCalendar(Locale.getDefault());
    //5、日期前后推算

    DateTime dt15 = new DateTime();

    //昨天
    DateTime yesterday = dt15.minusDays(1);
    //明天
    DateTime tomorrow = dt15.plusDays(1);
    //1个月前
    DateTime before1month = dt15.minusMonths(1);
    //3个月后
    DateTime after3month = dt15.plusMonths(3);
    //2年前
    DateTime before2year = dt15.minusYears(2);
    //5年后
    DateTime after5year = dt15.plusYears(5);
    //6、取特殊日期
    DateTime dt16 = new DateTime();
    //月末日期
    DateTime lastday = dt16.dayOfMonth().withMaximumValue();
    //90天后那周的周一
    DateTime firstday = dt16.plusDays(90).dayOfWeek().withMinimumValue();

    //8、计算区间

    DateTime begin = new DateTime("2012-02-01");
    DateTime end = new DateTime("2012-05-01");

    //计算区间毫秒数
    Duration d = new Duration(begin, end);
    long time = d.getMillis();

    //计算区间天数
    Period p = new Period(begin, end, PeriodType.days());
    int days = p.getDays();

    //计算特定日期是否在该区间内
    Interval i = new Interval(begin, end);
    boolean contained = i.contains(new DateTime("2012-03-01"));

    //9、日期比较
    DateTime d19 = new DateTime("2012-02-01");
    DateTime d29 = new DateTime("2012-05-01");

    //和系统时间比
    boolean b1 = d19.isAfterNow();
    boolean b2 = d19.isBeforeNow();
    boolean b3 = d19.isEqualNow();

    //和其他日期比
    boolean f1 = d19.isAfter(d29);
    boolean f2 = d19.isBefore(d29);
    boolean f3 = d19.isEqual(d29);

    //10、格式化输出
    DateTime dateTime = new DateTime();
    String s1 = dateTime.toString("yyyy/MM/dd hh:mm:ss.SSSa");
    String s2 = dateTime.toString("yyyy-MM-dd HH:mm:ss");
    String s3 = dateTime.toString("EEEE dd MMMM, yyyy HH:mm:ssa");
    String s4 = dateTime.toString("yyyy/MM/dd HH:mm ZZZZ");
    String s5 = dateTime.toString("yyyy/MM/dd HH:mm Z");
}