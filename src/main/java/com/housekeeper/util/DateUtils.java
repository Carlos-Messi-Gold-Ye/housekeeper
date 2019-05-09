package com.housekeeper.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static final String DATA_FORMAT_DATETIME_SLASH = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将字符串转为Date类型
     *
     * @deprecated 转换异常的时候会返回null，而不是抛出异常，不建议使用
     */
    @Deprecated
    public static Date string2Date(String dateString, String format) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            LOGGER.error("date format exception: ", e.getMessage());
        }
        return date;
    }

    public static Calendar getDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    public static String currentDateToString(String format) {
        return dateToString(new Date(), format);
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    public static String dateToString(Timestamp date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    public static Calendar getThisMothBegin() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now;
    }

    public static Calendar getThisMothEnd() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            now.set(Calendar.DAY_OF_MONTH, 31);
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            now.set(Calendar.DAY_OF_MONTH, 30);
        } else {
            int year = now.get(Calendar.YEAR);
            if (leapYear(year)) {
                now.set(Calendar.DAY_OF_MONTH, 29);
            } else {
                now.set(Calendar.DAY_OF_MONTH, 28);
            }
        }
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 999);
        return now;
    }

    private static boolean leapYear(int year) {
        boolean leap;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0 ) {
            leap = true;
        } else {
            leap = false;
        }
        return leap;
    }

    public static Boolean isOutOfDate(Date endDate, Integer day) {
        if (endDate != null && day != null) {
            Calendar now = DateUtils.getDate(new Date());
            Calendar end = DateUtils.getDate(endDate);
            now.add(Calendar.DAY_OF_MONTH, day);
            return now.after(end);
        }
        return false;
    }

    public static Date getDateAfterDay(Date now, Integer day) {
        Date refer = null == now ? new Date() : now;
        Calendar calendar = getDate(refer);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date getDateAfterDayWithTime(Date now, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static Integer genPartionMonth(Date actionTime) {
        return actionTime.getMonth() + 12 * (actionTime.getYear() % 2);
    }

    /**
     * 按RFC1123格式返回带预时区的时间戳
     * @param time
     * @return
     */
    public static String formatDateRfc1123(Date time) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(time);
    }
}
