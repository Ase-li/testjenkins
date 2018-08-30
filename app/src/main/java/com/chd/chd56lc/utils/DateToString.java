package com.chd.chd56lc.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateToString {
    /**
     * @param afterDay n天后，0就为今天，其他自己想
     * @return 格式 yy.MM.dd
     */
    public static String getDay(int afterDay) {
        String month, day;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + afterDay);
        if (calendar.get(Calendar.MONTH) + 1 < 10)
            month = "0" + (calendar.get(Calendar.MONTH) + 1);
        else
            month = calendar.get(Calendar.MONTH) + 1 + "";
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10)
            day = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        else
            day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        return (calendar.get(Calendar.YEAR) + "").substring(2) + "." + (month) + "." + day;
    }

    /**
     * @param millis 毫秒值
     * @return
     */
    public static String formalDateString(long millis, String pattern) {
        Date date = new Date(millis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * @param date
     * @return
     */
    public static String formalDateString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取年、月或者日
     *
     * @param dateStr
     * @param pattern
     * @param calendarType
     * @return
     */
    public static String getYMD(String dateStr, String pattern, int calendarType) {
        Calendar calendar = Calendar.getInstance();
        if (!StringUtils.isEmpty(dateStr))
            calendar.setTime(DateToString.transStringToDate(dateStr, pattern));
        String returnStr;
        switch (calendarType) {
            case Calendar.YEAR:
                return calendar.get(calendarType) + "";
            case Calendar.MONTH:
                int month = calendar.get(calendarType) + 1;
                return (month) < 10 ? "0" + month : month + "";
            case Calendar.DAY_OF_MONTH:
                return calendar.get(calendarType) < 10 ? "0" + calendar.get(calendarType) : calendar.get(calendarType) + "";
        }
        return "";
    }

    /**
     * 将格式化日期字符串转为date
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date transStringToDate(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getHourMoment() {
        Date date = new Date();
        int hours = date.getHours();
        if (hours < 6) {
            return 0;
        } else if (hours < 12)
            return 1;
        else if (hours < 14)
            return 2;
        else if (hours < 18)
            return 3;
        else
            return 4;
    }
}
