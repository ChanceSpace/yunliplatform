package com.yajun.green.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:37
 */
public class CHDateUtils {

    public static String getSimpleDateFormat(Date date){
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd");
        String formatString=time.format(date);
        return formatString;
    }

    public static String getSimpleDateFormatForFile(Date date){
        SimpleDateFormat time=new SimpleDateFormat("yyyy_MM_dd");
        String formatString=time.format(date);
        return formatString;
    }

    public static String getFullDateFormat(Date date){
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formatString=time.format(date);
        return formatString;
    }

    public static String getFullDateFormatWithoutLine(Date date){
        SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss");
        String formatString=time.format(date);
        return formatString;
    }

    public static String getFullDateFormatUUID(Date date){
        SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd_HHmm");
        String formatString=time.format(date);
        return formatString;
    }

    public static String getFullTimeFormatUUID(Date date){
        SimpleDateFormat time=new SimpleDateFormat("yyyyMMdd_HHmmss");
        String formatString=time.format(date);
        return formatString;
    }

    public static String getWeekOfDate(Date date) {
        Date test = new Date();
        SimpleDateFormat time=new SimpleDateFormat("EEEE");
        String formatString=time.format(date);
        return formatString;
    }

    public static String getJustTimeFormat(Date date){
        SimpleDateFormat time=new SimpleDateFormat("HH:mm");
        String formatString=time.format(date);
        return formatString;

    }

    public static int getCurrentYear() {
        Date current = new Date();
        return current.getYear() + 1900;
    }

    public static int getCurrentMonth() {
        Date current = new Date();
        return current.getMonth() + 1;
    }

    public static int getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentHours(){
        Date current = new Date();
        return current.getHours();
    }


    public static Date getFirstDateOfMonth() {
        Date current = new Date();
        int year = current.getYear();
        int month = current.getMonth();
        return new Date(year, month, 1);
    }

    public static Date getFirstDateOfMonth(int year, int month) {
        year = year - 1900;
        month = month - 1;
        return new Date(year, month, 1);
    }

    public static Date getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getFirstDateOfNextMonth(year, month));
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static Date getDayOfWeek(int week) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DAY_OF_WEEK, week);
        if (currentWeek == 1) {
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
        }
        return calendar.getTime();
    }

    public static Date getPrevMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -3);
        return calendar.getTime();
    }

    public static Date getCurrentSunday() {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //增加一周
        if (currentWeek != 1) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }
        return calendar.getTime();
    }

    public static Date getBeforeDate(int beforeDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -beforeDay);
        return getSimpleDateParse(getSimpleDateFormat(calendar.getTime()));
    }

    public static Date getFirstDateOfNextMonth() {
        Date current = new Date();
        int year = current.getYear();
        int month = current.getMonth();
        return new Date(year, month + 1, 1);
    }

    public static Date getFirstDateOfNextMonth(int year, int month) {
        year = year - 1900;
        month = month - 1;
        return new Date(year, month + 1, 1);
    }

    public static Date getSimpleDateParse() {
        return getSimpleDateParse(getSimpleDateFormat(new Date()));
    }

    public static Date getSimpleDateParse(String date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        Date simpleDate = null;
        try {
            simpleDate = time.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return simpleDate;
    }

    public static Date getFullDateParse(String date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date simpleDate = null;
        try {
            simpleDate = time.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return simpleDate;
    }

    public static int getTotalDaysForOneMonth(int year, int month) {
        if (year % 4 == 0 && month == 2) {
            return 29;
        } else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                return 31;
            } else if (month == 2) {
                return 28;
            }
        }
        return 30;
    }

    public static int compareDate(Date date1, Date date2) {
        long dateInt1 = date1.getTime();
        long dateInt2 = date2.getTime();
        if (dateInt1 > dateInt2) {
            return 1;
        } else if (dateInt1 < dateInt2) {
            return -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(getFirstDateOfMonth());
        System.out.println(getFirstDateOfNextMonth());
        System.out.println(getFirstDateOfMonth(2014, 05));
        System.out.println(getFirstDateOfNextMonth(2014, 05));
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        cal.set(2016, 3, 10);
        System.out.println(getWeekOfDate(cal.getTime()));

        getDayOfWeek(Calendar.MONDAY);

        System.out.println(getBeforeDate(1));
    }
}
