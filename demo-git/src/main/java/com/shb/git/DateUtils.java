package com.shb.git;

/**
 * @author songhaibo
 * @description
 * @date 2023-01-10 下午5:43
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
public class DateUtils {

    public static SimpleDateFormat getFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static SimpleDateFormat getFormats() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    }

    public static SimpleDateFormat getFormatStr() {
        return new SimpleDateFormat("yyyy年MM月dd日");
    }

    public static DateTimeFormatter getDetailFormat() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public static SimpleDateFormat getyyyy_mm_dd_format() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat getyyyymmddformat() {
        return new SimpleDateFormat("yyyy.MM.dd");
    }

    public static SimpleDateFormat getbase_format() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    public static SimpleDateFormat getyyyymmdd_format() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    public static SimpleDateFormat getyyyymm_format() {
        return new SimpleDateFormat("yyyyMM");
    }

    public static SimpleDateFormat getyyyy_mm_format() {
        return new SimpleDateFormat("yyyy-MM");
    }

    public static SimpleDateFormat getHH_format() {
        return new SimpleDateFormat("HH");
    }

    public static Date toDate(String dateString) throws ParseException {
        return getFormat().parse(dateString);
    }

    public static boolean beforeNow(String date) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(LocalDateTime.parse(date, getDetailFormat()));
    }

    public static String toString(Date date) throws ParseException {
        return getFormat().format(date);
    }

    public static String nowString() {
        return getFormat().format(new Date());
    }

    public static Date now() {
        return new Date();
    }

    public static String today() {
        return getyyyymmdd_format().format(new Date());
    }

    public static String getDetailDate(LocalDateTime date) {
        return getDetailFormat().format(date);
    }

    public static Date nowDateTime(Date date) {
        return Timestamp.valueOf(
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE).format(date)
        );
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * Date转LocalDate
     *
     * @param date
     */
    public static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 获得某天最大时间
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();
    }

    /**
     * 获得某天最大时间
     *
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(date);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        return calendarStart.getTime();
    }

    /**
     * Date转LocalDate
     *
     * @param date
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 时间之间的相差天数
     *
     * @return
     */
    public static long betweenDays(Date begin, Date end) {
        if (null == begin || null == end) {
            return 0;
        }

        LocalDate beginDate = DateUtils.date2LocalDate(begin);
        LocalDate endDate = DateUtils.date2LocalDate(end);
        return beginDate.until(endDate, ChronoUnit.DAYS);
    }

    /**
     * 将带有T的时间字符串yyyy-MM-dd'T'HH:mm:ss 转换成yyyy-MM-dd HH:mm:ss
     */
    public static String convertDate(String strDate) {
        String str = "";
        try {
            String fmt = "yyyy-MM-dd HH:mm:ss";
            strDate = strDate.replace("T", " ");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(fmt);
            return dateTimeFormatter.format(dateTimeFormatter.parse(strDate));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }

    public static String plusDay(int num, String newDate, SimpleDateFormat format) throws ParseException {
        Date currdate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        return enddate;

    }

    public static String plusDay(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currdate = format.parse(newDate);
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;

    }

    public static String plusDays(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date currdate = format.parse(newDate);
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.DATE, num);
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;

    }

    public static String subDay(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currDate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currDate);
        ca.add(Calendar.DAY_OF_MONTH, -num);
        currDate = ca.getTime();
        return format.format(currDate);

    }

    public static String yyyyMMddPlusDay(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currDate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currDate);
        ca.add(Calendar.DATE, num);
        currDate = ca.getTime();
        return format.format(currDate);

    }

    public static Date yyyyMMddPlusDayReturnDate(int num, String newDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currDate = format.parse(newDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currDate);
        ca.add(Calendar.DATE, num);
        currDate = ca.getTime();
        return currDate;

    }

    /**
     * 获取当天00：00：00的时间戳
     *
     * @return 时间戳
     */
    public static Date getStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天23：59：59的时间戳
     *
     * @return 时间戳
     */
    public static Date getEndTime() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取本月开始日期
     *
     * @return String
     **/
    public static String getMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 00:00:00";
    }

    /**
     * 获取本月最后一天
     *
     * @return String
     **/
    public static String getMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    /**
     * 获取上月第一天
     *
     * @return String
     **/
    public static String getLastMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
    }

    /**
     * 获取上月最后一天
     *
     * @return String
     **/
    public static String getLastMonthEnd() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    /**
     * 获取前月第一天
     *
     * @return String
     **/
    public static String getPrecedingMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
    }

    /**
     * 获取前月最后一天
     *
     * @return String
     **/
    public static String getPrecedingMonthEnd() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time) + " 23:59:59";
    }

    /**
     * 获取昨天
     *
     * @return
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        String sceneTime = DateFormatUtils.format(date, "yyyy-MM-dd");
        return sceneTime;
    }

    /**
     * 获取今天
     *
     * @return
     */
    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 0);
        Date date = calendar.getTime();
        String sceneTime = DateFormatUtils.format(date, "yyyy-MM-dd");
        return sceneTime;
    }

    public static String getHours(String time) {
        BigDecimal hours = BigDecimal.valueOf(Long.parseLong(time))
            .divide(new BigDecimal("60"), 2, BigDecimal.ROUND_HALF_UP)
            .divide(new BigDecimal("60"), 2, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
        return hours.toString();
    }

    /**
     * 比较两个时间的大小
     *
     * @throws ParseException
     */
    public static boolean compareDate(Date nowBegin, Date nowEnd) {
        return nowBegin.getTime() < nowEnd.getTime();
    }

    public static String toUTC(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        long timeInMillis = calendar.getTimeInMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return df.format(timeInMillis);
    }

    /**
     * 在给定的日期加上或减去指定月份后的日期
     *
     * @param sourceDate 原始时间
     * @param month      要调整的月份，向前为负数，向后为正数
     * @return
     */
    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 判断该日期是否是该月的第一天
     *
     * @param date 需要判断的日期
     * @return
     */
    public static boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //月（必须要+1）
//        System.out.println(calendar.get(Calendar.MONTH) +1);
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }

    public static Date plusHour(int num, Date date) throws ParseException {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR, num);
        date = ca.getTime();
        return date;

    }

    public static Date plusDays(int num, Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, num);
        ca.set(Calendar.SECOND, 0); //这是将当天的【秒】设置为0
        ca.set(Calendar.MINUTE, 0); //这是将当天的【分】设置为0
        ca.set(Calendar.HOUR_OF_DAY, 0); //这是将当天的【时】设置为0
        date = ca.getTime();
        return date;

    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStampToDate(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.parseLong(seconds + "000")));
    }

    /**
     * 获取前月第一天
     *
     * @return String
     **/
    public static String getMonthStartDay(Integer month) {
        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.MONTH, -2);
        cal.set(Calendar.DAY_OF_MONTH, month);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
    }

    /**
     * 获取上个月的同一时间
     *
     * @param date
     * @return
     */
    public static String getLastMounthCurrentDate(String date) {
        String resultString = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(getyyyy_mm_dd_format().parse(date));
            java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
            cal.add(Calendar.MONTH, -1);
            String lastMonthStart = format.format(cal.getTime());//上月开始
            cal.clear();
            resultString = lastMonthStart;
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 分钟
     *
     * @param num
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date plusMinute(int num, Date date) throws ParseException {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MINUTE, num);
        date = ca.getTime();
        return date;
    }

    /**
     * 转换时间格式
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date toDateUZ(String date) throws ParseException {
        date = date.replace("Z", " UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = format.parse(date);
        return d;
    }

    /**
     * 两个日期转成yyyyMMdd比较大小，
     */
    public static int dateCompare(Date date1, Date date2) {
        String dateFirst = getyyyymmdd_format().format(date1);
        String dateLast = getyyyymmdd_format().format(date2);
        int dateFirstIntVal = Integer.parseInt(dateFirst);
        int dateLastIntVal = Integer.parseInt(dateLast);
        if (dateFirstIntVal > dateLastIntVal) {
            return 1;
        } else if (dateFirstIntVal < dateLastIntVal) {
            return -1;
        }
        return 0;
    }

    /**
     * 比较日期大小
     *
     * @param date1 第一个时间
     * @param date2 第二个时间
     * @param df    日期格式
     * @return Integer null日期格式有误，1：第一个日期大，0：两个日期一样，-1：第二个日期大
     */
    public static Integer compareDate(String date1, String date2, SimpleDateFormat df) throws ParseException {
        Date dt1 = df.parse(date1);
        Date dt2 = df.parse(date2);
        return Long.compare(dt1.getTime(), dt2.getTime());
    }

    public static Date plusYear(int num, Date currdate) throws ParseException {
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate);
        ca.add(Calendar.YEAR, num);
        currdate = ca.getTime();
        return currdate;

    }

    /**
     * @param end  结束时间 格式同上
     * @param date 格式类型 Calendar.DATE，Calendar.MONTH
     * @return java.util.List<java.lang.String>
     * @Description 获取两个时间段内的所有日期/月份
     * @Author tgh
     * @Param begin 开始时间 日期格式：yyyy-MM-dd  月份格式：yyyy-MM
     * @Date 2022/9/5 15:00
     **/
    public static List<String> getBetweenDateAndMonth(String begin, String end, int date) throws Exception {
        String formatStr = "yyyy-MM-dd";
        if (Calendar.MONTH == date) formatStr = "yyyy-MM";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        List<String> betweenList = new ArrayList<String>();
        Calendar startDay = Calendar.getInstance();
        startDay.setTime(format.parse(begin));
        startDay.add(date, -1);
        while (true) {
            startDay.add(date, 1);
            Date newDate = startDay.getTime();
            String newend = format.format(newDate);
            betweenList.add(newend);
            if (end.equals(newend)) {
                break;
            }
        }
        return betweenList;
    }

    /**
     * 获取两年前的一月一号
     *
     * @return 获取两年前的一月一号
     */
    public static String getTwoYearOneMonthOneDayDate() {
        String resultString = null;
        try {
            Calendar cal = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            cal.setTime(new Date());
            cal.add(Calendar.YEAR, -2);
            cal.set(Calendar.MONTH, 0); // 设置为1月
            cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号
            String lastYearStart = format.format(cal.getTime());
            cal.clear();
            resultString = lastYearStart;
        } catch (NumberFormatException e) {
            log.error("getTwoYearOneMonthOneDayDate error {}", e.getMessage());
        }
        return resultString;
    }

    /**
     * 返回两个时间相差多少个月
     * 日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static long getBetweenMonth(String startstr, String endstr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(startstr, formatter);
        LocalDateTime endDate = LocalDateTime.parse(endstr, formatter);
        return ChronoUnit.MONTHS.between(startDate, endDate);
    }

    /**
     * @Description 获取本季度的第一天或最后一天
     * @Param: [today, isFirst: true 表示开始时间，false表示结束时间]
     * @return: java.lang.String
     * @Exception: ParseException
     */
    public static String getStartOrEndDayOfQuarter(Date day, Boolean isFirst) throws ParseException {
        Instant instant = day.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        LocalDate resDate = LocalDate.now();
        if (localDate == null) {
            localDate = resDate;
        }
        Month month = localDate.getMonth();
        Month firstMonthOfQuarter = month.firstMonthOfQuarter();
        Month endMonthOfQuarter = Month.of(firstMonthOfQuarter.getValue() + 2);
        if (isFirst) {
            resDate = LocalDate.of(localDate.getYear(), firstMonthOfQuarter, 1);
        } else {
            resDate = LocalDate.of(localDate.getYear(), endMonthOfQuarter, endMonthOfQuarter.length(localDate.isLeapYear()));
        }
        return resDate.toString();
    }

    /**
     * 获取上个年的同一时间
     *
     * @param date
     * @return
     */
    public static String getLastYearCurrentDate(String date) {
        String resultString = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(getyyyy_mm_dd_format().parse(date));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            cal.add(Calendar.YEAR, -1);
            String lastYearStart = format.format(cal.getTime());
            cal.clear();
            resultString = lastYearStart;
        } catch (NumberFormatException | ParseException e) {
            log.error("getLastYearCurrentDate error {}", e.getMessage());
        }
        return resultString;
    }

    /**
     * 是否同一年，只校验年份不管天
     *
     * @param date1 date1
     * @param date2 date2
     * @return t
     */
    public static boolean isSameYear(Date date1, Date date2) {
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        } catch (Exception e) {
            log.error("isSameYear error {}", e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(getStartOrEndDayOfQuarter(getyyyy_mm_dd_format().parse("2022-08-31"), false));

    }

    /**
     * 获取某月的最后一天
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取一年期的同一天
     */
    public static String getLastYearOfDay(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(getFormat().parse(date));
        cal.add(Calendar.YEAR, -1);
        String lastYearStart = format.format(cal.getTime());
        cal.clear();
        return lastYearStart;
    }

    /**
     * 获取上个月的同一时间
     *
     * @param date
     * @return
     */
    public static String getLastMonthOfDate(String date) throws ParseException {
        String resultString = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(getFormat().parse(date));
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONTH, -1);
        String lastMonthStart = format.format(cal.getTime());//上月开始
        cal.clear();
        resultString = lastMonthStart;
        return resultString;
    }

    /**
     * 获取上个月的最后一天
     *
     * @param date
     * @return
     */
    public static String getLastMonthOfMaxDate(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getFormat().parse(date));
        java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONTH, -1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        String lastMonthStart = format.format(cal.getTime());//上月开始
        cal.clear();
        return lastMonthStart;
    }

    /**
     * 获取本月的第一天
     *
     * @param date
     * @return
     */
    public static String getMonthOfMinDate(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String lastMonthStart = format.format(cal.getTime());
        cal.clear();
        return lastMonthStart;
    }

    /**
     * 获取本月的最后一天
     *
     * @param date
     * @return
     */
    public static String getMonthOfMaxDate(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONTH, 0);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        String lastMonthStart = format.format(cal.getTime());
        cal.clear();
        return lastMonthStart;
    }

    public static int getQuarter(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getyyyy_mm_dd_format().parse(date));
        int month = cal.get(Calendar.MONTH) + 1;// 获取的实际月份是从0开始的
        switch (Month.of(month)) {
            case JANUARY:
            case FEBRUARY:
            case MARCH:
            default:
                return 1;
            case APRIL:
            case MAY:
            case JUNE:
                return 2;
            case JULY:
            case AUGUST:
            case SEPTEMBER:
                return 3;
            case OCTOBER:
            case NOVEMBER:
            case DECEMBER:
                return 4;
        }
    }

    public static String getYYYYQuarter(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getyyyy_mm_dd_format().parse(date));
        int i = cal.get(Calendar.YEAR);
        int quarter = getQuarter(date);
        return i + "-Q" + quarter;
    }
}


