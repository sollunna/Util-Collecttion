package sx.nine.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Author: NineEr
 * @Date: 2020/3/17 14:25
 * @Description:
 * 时间日期处理工具类
 */
public class DateUtil {

    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static DateFormat TIME_FORMAT_HM = new SimpleDateFormat("HH:mm");
    public static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat DATE_TIME_FORMAT_SHORT = new SimpleDateFormat("yyyyMMddHHmmss");
    public static String DAY_START_TIME = "00:00:00";
    public static String DAY_END_TIME = "23:59:59";

    /**
     * 取得服务器当前日期
     *
     * @return 当前日期：格式（yyyy-MM-dd）
     */
    static public String currentDate() {
        Date curDate = new Date();
        return DATE_FORMAT.format(curDate);
    }

    /**
     * 取得服务器当前时间
     *
     * @return 当前日期：格式（hh:mm:ss）
     */
    static public String currentTime() {
        Date curDate = new Date();
        return TIME_FORMAT.format(curDate);
    }

    /**
     * 取得服务器当前日期和时间
     *
     * @return 当前日期：格式（yyyy-MM-dd hh:mm:ss）
     */
    static public String currentDateTime() {
        Date curDate = new Date();
        return DATE_TIME_FORMAT.format(curDate);
    }

    static public String currentDateShortTime() {
        Date curDate = new Date();
        return DATE_TIME_FORMAT_SHORT.format(curDate);
    }

    /**
     * 比较两个日期大小
     *
     * @param date1
     * @param date2
     * @return date1>date2返回1;date1=date2返回0;date1<date2返回-1
     * @throws ParseException
     */
    public static byte dateCompare(String date1, String date2) throws ParseException {

        Date d1 = DATE_FORMAT.parse(date1);
        Date d2 = DATE_FORMAT.parse(date2);
        if (d1.before(d2)) {
            return -1;
        } else if (d1.equals(d2)) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 比较两个时间大小
     *
     * @param time1
     * @param time2
     * @return date1>date2返回1;date1=date2返回0;date1<date2返回-1
     * @throws ParseException
     */
    public static byte timeHmCompare(String time1, String time2) throws ParseException {

        Date t1 = TIME_FORMAT_HM.parse(time1);
        Date t2 = TIME_FORMAT_HM.parse(time2);
        if (t1.before(t2)) {
            return -1;
        } else if (t1.equals(t2)) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 日期字符串转date
     *
     * @param dateStr
     * @throws ParseException
     */
    public static Date str2date(String dateStr) throws ParseException {
        Date date = DATE_FORMAT.parse(dateStr);
        return date;
    }


    public static Date str3date(String dateStr) throws ParseException {
        Date date = DATE_TIME_FORMAT.parse(dateStr);
        return date;
    }

    public static Date str4date(String dateStr) throws ParseException {
        Date date = DATE_TIME_FORMAT_SHORT.parse(dateStr);
        return date;
    }

    /**
     * 日期字符串转date
     *
     * @param date
     * @throws ParseException
     */
    public static String date2str(Date date) throws ParseException {
        if (date != null) {
            return DATE_FORMAT.format(date);
        } else {
            return "null";
        }
    }

    /**
     * 日期字符串转date
     *
     * @param date
     * @return Date
     * @throws ParseException
     */
    public static String dateTime2str(Date date) throws ParseException {
        if (date != null) {
            return DATE_TIME_FORMAT.format(date);
        } else {
            return "null";
        }
    }

    /**
     * 获取本周的日期
     *
     * @param dayOfWeek :可用值:Calendar.SUNDAY,Calendar.MONDAY,
     *                  Calendar.TUESDAY,Calendar.WEDNESDAY,Calendar.THURSDAY,
     *                  Calendar.FRIDAY,Calendar.SATURDAY
     * @return
     */
    public static String getCurrentWeek(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        if (Calendar.SUNDAY == dayOfWeek) {
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
        }
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return DATE_FORMAT.format(calendar.getTime());
    }

    /**
     * 两个日期相减
     *
     * @param date1
     * @param date2
     * @param flag  : 'y':得出相差年数,'M':相差月数,'d':相差天数,'h':相差小时数
     *              'm':相差分钟数,'s':相差秒数,其他:相差毫秒数
     * @return
     */
    public static long dateMinus(Date date1, Date date2, char flag) {
        long msMinus = date1.getTime() - date2.getTime();
        switch (flag) {
            case 'y':
                return msMinus / (365L * 24L * 60L * 60L * 1000L);
            case 'M':
                return msMinus / (30L * 24L * 60L * 60L * 1000L);
            case 'd':
                return msMinus / (24L * 60L * 60L * 1000L);
            case 'h':
                return msMinus / (60L * 60L * 1000L);
            case 'm':
                return msMinus / (60L * 1000L);
            case 's':
                return msMinus / 1000L;
            default:
                return msMinus;
        }
    }

    /**
     * 一个日期加上xx天,返回加法之后的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date datePlus(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
        return calendar.getTime();
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;
        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * date类型转字符串
     *
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }


    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }
    /**
     * 获取前一天
     * @return
     */
    public static Date getFirstDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date first = c.getTime();
        return first;
    }
    /**
     * 处理时间  20190506----2019-05-06
     * @param date
     * @return
     */
    public static String revent(String date) {
        StringBuilder dateBuilder = new StringBuilder(date);
        date = dateBuilder.substring(0, 4) + "-" + dateBuilder.substring(4, 6) + "-" + dateBuilder.substring(6, 8);
        return date;
    }
    /**
     *
     * @param year
     * @param month
     * @return
     */
    public static Date setDay(String year,String month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
        return calendar.getTime();
    }
    /**
     * 获取本月的开始时间
     * @return
     */
    public static Date getBeginDayOfMonth(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , 1);
        return calendar.getTime();
    }
    /**
     * 获取本月的结束时间
     * @return
     */
    public static Date getEndDayOfMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 获取当前年份
     * @return
     */
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }
}
