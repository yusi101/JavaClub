package com.JavaClub.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtil {
	
	protected static Logger logger = Logger.getLogger(DateUtil.class);
	
    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
            "yyyy-MM-dd");

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
            "yyyyMMdd");

    private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 获取YYYY格式
     * 
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     * 
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     * 
     * @return
     */
    public static String getDays(){
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * 
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }

    /**
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @param s
     * @param e
     * @return boolean  
     * @throws
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if(fomatDate(s)==null||fomatDate(e)==null){
            return false;
        }
        return fomatDate(s).getTime() >=fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     * 
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
        	logger.error(e.toString(),e);
            return null;
        }
    }

    /**
     * 校验日期是否合法
     * 
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }
    public static int getDiffYear(String startTime,String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }
    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);
        } catch (ParseException e) {
        	logger.error(e.toString(),e);
        }
        day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        //System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }


    /**
     * 根据开始时间和结束时间返回时间段内的时间集合   小时
     * @param beginDate
     * @param endDate
     * @return List<Date>
     * @throws ParseException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<String> getHoursBetweenTwoDate(String start, String end) throws ParseException {    
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH"); 
        start=StringUtil.isEmpty(start) ? DateFormatUtils.format(new Date(), "yyyy-MM-dd 00") : start ;
        end=StringUtil.isEmpty(end) ? sdf.format(new Date()) : end ;
        Date beginDate =sdf.parse(start); 
        Date endDate = sdf.parse(end); 
        List lDate = new ArrayList();
        lDate.add(sdf.format(beginDate));//把开始时间加入集合

        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.HOUR_OF_DAY, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(sdf.format(cal.getTime()));
            } else {
                break;
            }
        }
        if (endDate.after(beginDate)) {
            lDate.add(sdf.format(endDate));//把结束时间加入集合
        }
        return lDate;
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合   小时
     * @param beginDate
     * @param endDate
     * @return List<Date>
     * @throws ParseException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<String> getDaysBetweenTwoDate(String start, String end) throws ParseException {    
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        cal.add(Calendar.DATE, - 7);  
        start=StringUtil.isEmpty(start) ? sdf.format(cal.getTime()) : start ;
        end=StringUtil.isEmpty(end) ? sdf.format(new Date()) : end ;
        Date beginDate =sdf.parse(start); 
        Date endDate = sdf.parse(end); 
        List lDate = new ArrayList();
        lDate.add(sdf.format(beginDate));//把开始时间加入集合

        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(sdf.format(cal.getTime()));
            } else {
                break;
            }
        }
        if (endDate.after(beginDate)) {
            lDate.add(sdf.format(endDate));//把结束时间加入集合
        }
        return lDate;
    }

    /**
     * 字符串转换日期格式
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String str) throws ParseException{
        Date date = sdfTime.parse(str);
        return date;
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合   小时
     * @param beginDate
     * @param endDate
     * @return List<Date>
     * @throws ParseException 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<String> getMonthBetweenTwoDate(String start, String end) throws ParseException {    
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
        cal.add(Calendar.MONTH, - 7);  
        start=StringUtil.isEmpty(start) ? sdf.format(cal.getTime()) : start ;
        end=StringUtil.isEmpty(end) ? sdf.format(new Date()) : end ;
        Date beginDate =sdf.parse(start); 
        Date endDate = sdf.parse(end); 
        List lDate = new ArrayList();
        lDate.add(sdf.format(beginDate));//把开始时间加入集合

        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(sdf.format(cal.getTime()));
            } else {
                break;
            }
        }
        if (endDate.after(beginDate)) {
            lDate.add(sdf.format(endDate));//把结束时间加入集合
        }

        return lDate;
    }
    /*public static  ChangeDateToString(){

	}*/
    /** 
     * 两个时间相差距离多少天多少小时多少分多少秒 
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
     * @return String 返回值为：xx天xx小时xx分xx秒 
     */  
    public static String getDistanceTime(String str1, String str2) {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
        Date one;  
        Date two;  
        long day = 0;  
        long hour = 0;  
        long min = 0;  
        long sec = 0;
        long millisecond=0;
        try {  
            one = df.parse(str1);  
            two = df.parse(str2);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            if(time1<time2) {  
                diff = time2 - time1;  
            } else {
                diff = time1 - time2;  
            }  
            day = diff / (24 * 60 * 60 * 1000);  
            hour = (diff / (60 * 60 * 1000) - day * 24);  
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
            millisecond=diff-day*24*60*60*1000-hour*60*60*1000-min*60*1000-sec*1000;
        } catch (ParseException e) {  
        	logger.error(e.toString(),e);
        }  
        return day + "天" + hour + "小时" + min + "分" + sec + "秒"+millisecond+"毫秒";  
    }
    /** 
     * 两个时间相差距离多少天多少小时多少分多少秒 
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
     * @return String 返回值为：xx天xx小时xx分xx秒 
     */  
    public static String getDistanceTime(Date date1, Date date2) {  
        long day = 0;  
        long hour = 0;  
        long min = 0;  
        long sec = 0;
        long millisecond=0;
        try {  
            long time1 = date1.getTime();  
            long time2 = date2.getTime();  
            long diff ;  
            if(time1<time2) {  
                diff = time2 - time1;  
            } else {
                diff = time1 - time2;  
            }  
            day = diff / (24 * 60 * 60 * 1000);  
            hour = (diff / (60 * 60 * 1000) - day * 24);  
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
            millisecond=diff-day*24*60*60*1000-hour*60*60*1000-min*60*1000-sec*1000;
        } catch (Exception e) {  
        	logger.error(e.toString(),e);
        }  
        return day + "天" + hour + "小时" + min + "分" + sec + "秒"+millisecond+"毫秒";  
    }  
    public static void main(String[] args) throws ParseException {

        System.out.println(getDistanceTime("2016-07-22 17:22:32.123", "2016-07-22 17:24:32.500"));

    }

    /**
     * 时间比较 24小时
     * 
     * @param sendtime
     * @return
     */
    public static int compareTime(Date date) {
        // 字符串转换日期格式
        // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

        // 创建Calendar 对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 时间 + 1天
        calendar.add(Calendar.HOUR, 24);

        // 创建一个当前时间的calendar用来比较
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(new Date());

        // 前者大，返回 1； 后者大，返回 -1
        return currentCalendar.compareTo(calendar);
    }

    /**
     * 时间比较 10分钟
     * 
     * @param sendtime
     * @return
     */
    public static int compareMinute(Date date) {
        // 字符串转换日期格式
        // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        // 创建Calendar 对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 时间 + 1天
        calendar.add(Calendar.MINUTE, 10);
        // 创建一个当前时间的calendar用来比较
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(new Date());

        // 前者大，返回 1； 后者大，返回 -1
        return currentCalendar.compareTo(calendar);
    }
    public static int getDayByYearAndMonth(String YearAndMonth) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR,Integer.parseInt(YearAndMonth.split("-")[0]));
            cal.set(Calendar.MONTH, Integer.parseInt(YearAndMonth.split("-")[1]) - 1);//Java月份才0开始算
            int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
            return dateOfMonth; 
        } catch (Exception e) {
           return 0;
        }
        
    }
    /*
     * 获取当天的起始时间
     */
	public static Date getStartTime() {  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTime();  
    }  
    /*
     * 获取当天的终止时间
     */
    public static Date getEndTime() {  
        Calendar todayEnd = Calendar.getInstance();  
        todayEnd.set(Calendar.HOUR, 23);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
        todayEnd.set(Calendar.MILLISECOND, 999);  
        return todayEnd.getTime();  
    }  
}
