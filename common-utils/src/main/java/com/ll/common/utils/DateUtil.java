package com.ll.common.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

    public static Date current() {
        return new Date();
    }

    private static final String RFC3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

//    public static void main(String[] args) {
////        Integer dayOfWeek = getDayOfWeek(new Date());
////        System.out.println(dayOfWeek);
//        String s = toRfc3339(new Date());
//        System.out.println(s);
//    }

    public static String toRfc3339(Date date) {
        return new SimpleDateFormat(RFC3339_PATTERN).format(date);
    }

    public static Date fromRfc3339(String date) {
        try {
            return new SimpleDateFormat(RFC3339_PATTERN).parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("解析日期异常");
        }
    }

    /**
     * 获取指定日期的零点
     *
     * @param date
     * @return
     */
    public static Date toZeroClock(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date can not be null.");
        }
        return new DateTime(date).withMillisOfDay(0).toDate();
    }

    public static Date toTheDayAfter(Date beginDate, int days) {
        return new DateTime(beginDate).plusDays(days - 1).toDate();
    }

    public static Integer getDayOfWeek(Date date) {
        return new DateTime(date).getDayOfWeek();
    }

    public static ImmutableSet<Date> fillAfterDays(Date beginDate, int totalDays) {
        ImmutableSet.Builder<Date> datesBuilder = ImmutableSet.builder();
        datesBuilder.add(beginDate);
        DateTime dateTime = new DateTime(beginDate);
        for (int i = 1; i < totalDays; i++) {
            datesBuilder.add(dateTime.plusDays(i).toDate());
        }
        return datesBuilder.build();
    }

    public static ImmutableList<Date> fillDays(Date beginDate, Date endDate) {
        ImmutableList.Builder<Date> datesBuilder = ImmutableList.builder();
        datesBuilder.add(beginDate);
        DateTime basic = new DateTime(beginDate);
        DateTime limit = new DateTime(endDate);
        while ((basic = basic.plusDays(1)).compareTo(limit) <= 0) {
            datesBuilder.add(basic.toDate());
        }
        return datesBuilder.build();
    }

    public static LocalTime parseTime(String clock) {
        return LocalTime.parse(clock);
    }

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式(HH:mm:ss)
     */
    public final static String TIME_PATTERN = "HH:mm:ss";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date    日期
     * @param pattern 格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }


    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }
}
