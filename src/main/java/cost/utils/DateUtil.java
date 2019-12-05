package cost.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class DateUtil {

    /** yyyyMMdd */
    public static final String mFormat8chars = "yyyyMMdd";

    /** yyyy-MM-dd */
    public static final String mFormatIso8601Day = "yyyy-MM-dd";

    /** yyyy-MM-dd */
    public static final String mFormatIso8601Day2 = "yyyy/MM/dd";
    /** yyyy年MM月dd日 */
    public static final String mFormatCn8601Day = "yyyy年MM月dd日 ";

    /** yyyy-MM-dd HH:mm:ss */
    public static final String mFormatIso8601Daytime = "yyyy-MM-dd HH:mm:ss";

    /** yyyy-MM-dd HH:mm:ss */
    public static final String mFormatIso8601DaytimeS = "yyyy-MM-dd HH:mm:ss:SSS";

    /** yyyy-MM-dd HH:mm:ss:sss */
    public static final String mFormatIso8602Daytime = "yyyyMMddHHmmss";

    public static final String mFormatIsoGMTDaytimeS = "EEE MMM dd HH:mm:ss Z yyyy";
    public static final String mFormatIsoDaytimeS2 = "MMM dd,yyyy HH:mm:ss aa";//

    /** yyyy-MM-dd HH:mm:ss:sss */
    public static final String mFormatIso8602DaytimeS = "yyyyMMddHHmmssSSS";


    static {
        Properties props = System.getProperties();
        props.setProperty("user.timezone", "GMT+8");
    }

    /**
     * 得到今天的时间,并格式化.
     *
     * @param format
     *            格式
     * @return 今天的日期。
     */
    public static String today(String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static Date nowDay(String format) {
        DateFormat df = new SimpleDateFormat(format);
        String date = df.format(new Date());
        return getFormatDate(date, format);
    }

    public static String today() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public static long todayTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return getTime(df.format(new Date()));
    }

    public static String getUnixTime() {
        return String.valueOf(new Date().getTime() / 1000);
    }

    public static String yesterday(String format) {
        Date date = new Date();
        date.setTime(date.getTime() - 86400000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String yesterday() {
        Date date = new Date();
        date.setTime(date.getTime() - 86400000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String tomorrow() {
        Date date = new Date();
        date.setTime(date.getTime() + 86400000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String tomorrow(String format) {
        Date date = new Date();
        date.setTime(date.getTime() + 86400000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 根据一个时间戳(长整形字符串)生成指定格式时间字符串
     *
     * @param time
     *            时间戳(长整形字符串)
     * @param format
     *            格式字符串如yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getDate(long time, String format) {
        Date d = new Date();
        d.setTime(time);
        DateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }

    /**
     * 字符型时间变成时间类型
     *
     * @param date
     *            字符型时间 例如: "2008-08-08"
     * @param format
     *            格式化形式 例如: "yyyy-MM-dd"
     * @return 出现异常时返回null
     */
    public static Date getFormatDate(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Date getFormatDate2(String date, String format)
            throws Exception {

        DateFormat df = new SimpleDateFormat(format, Locale.US);
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {

            throw e;
        }
        return d;
    }


    /**
     * 根据一个时间戳(长整形字符串)生成指定格式时间字符串
     *
     * @param time
     *            时间戳(长整形字符串)
     * @param format
     *            格式字符串如yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getDate(String time, String format) {
        Date d = new Date();
        long t = Long.parseLong(time);
        d.setTime(t);
        DateFormat df = new SimpleDateFormat(format);
        return df.format(d);
    }

    /**
     * 根据一个时间戳(长整形字符串)生成指定格式时间字符串
     *
     * @param time
     *            时间戳(长整形字符串)
     * @param format
     *            格式字符串如yyyy-MM-dd
     * @return 时间字符串
     */
    public static String getDate(Date date, String format) {
        String formatDate = "";
        if (date != null) {
            DateFormat df = new SimpleDateFormat(format);
            formatDate = df.format(date);
        }
        return formatDate;
    }

    /**
     * 得到日期的时间戳
     *
     * @param date
     *            八位或十位日期，格式：yyyy-MM-dd或yyyyMMdd
     * @return 时间戳
     */
    public static long getTime(String date) {
        long time = 0;
        if (date == null || date.length() < 8) {
            return 0;
        }
        if (date.length() == 8) {
            date = DateUtil.format(date);
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            time = df.parse(date).getTime();
        } catch (ParseException e) {
            System.out.println("parse error " + e.getMessage());
        }
        return time;
    }

    /**
     * 得到日期的时间戳
     *
     * @param date
     *            八位或十位日期，格式：yyyy-MM-dd或yyyyMMdd
     * @return 时间戳
     */
    public static Date getDate(String date) {
        Date returnDate = null;
        if (date == null || date.length() < 8) {
            return null;
        }
        if (date.length() == 8) {
            date = DateUtil.format(date);
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            returnDate = df.parse(date);
        } catch (ParseException e) {
            System.out.println("parse error " + e.getMessage());
        }
        return returnDate;
    }

    /**
     * 得到日期的时间戳
     *
     * @param date
     *            日期
     * @param format
     *            日期格式
     * @return 时间戳
     */
    public static long getTime(String date, String format) {
        long time = 0;
        if (date == null || date.length() < 8) {
            return 0;
        }
        if (date.length() == 8) {
            date = DateUtil.format(date);
        }
        DateFormat df = new SimpleDateFormat(format);
        try {
            time = df.parse(date).getTime();
        } catch (ParseException e) {
            System.out.println("parse error " + e.getMessage());
        }
        return time;
    }

    /**
     * 日期的指定域加减
     *
     * @param time
     *            时间戳(长整形字符串)
     * @param field
     *            加减的域,如date表示对天进行操作,month表示对月进行操作,其它表示对年进行操作
     * @param num
     *            加减的数值
     * @return 操作后的时间戳(长整形字符串)
     */
    public static String addDate(String time, String field, int num) {
        int fieldNum = Calendar.YEAR;
        if (field.equals("month")) {
            fieldNum = Calendar.MONTH;
        }
        if (field.equals("date")) {
            fieldNum = Calendar.DATE;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(time));
        cal.add(fieldNum, num);
        return String.valueOf(cal.getTimeInMillis());
    }

    /**
     * 日期的指定域加减
     *
     * @param time
     *            时间戳(长整形字符串)
     * @param field
     *            加减的域,如date表示对天进行操作,month表示对月进行操作,其它表示对年进行操作
     * @param num
     *            加减的数值
     * @return 操作后的时间戳(长整形字符串)
     */
    public static Date starttime(Date time, String field, int num) {
        int fieldNum = Calendar.YEAR;
        if (field.equals("month")) {
            fieldNum = Calendar.MONTH;
        }
        if (field.equals("date")) {
            fieldNum = Calendar.DATE;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());
        cal.add(fieldNum, num);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 日期的指定域加减
     *
     * @param time
     *            时间戳(长整形字符串)
     * @param field
     *            加减的域,如date表示对天进行操作,month表示对月进行操作,其它表示对年进行操作
     * @param num
     *            加减的数值
     * @return 操作后的时间戳(长整形字符串)
     */
    public static long addDate(String field, int num) {
        int fieldNum = Calendar.YEAR;
        if (field.equals("month")) {
            fieldNum = Calendar.MONTH;
        }
        if (field.equals("date")) {
            fieldNum = Calendar.DATE;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(fieldNum, num);

        return cal.getTimeInMillis();
    }

    /**
     * 日期的指定域加减
     *
     * @param time
     * @param field
     * @param num
     * @return
     */
    public static String addTime(String time, String field, int amount, String format) {
        Calendar cal;
        try {
            cal = Calendar.getInstance();

            cal.setTime(getFormatDate(time, format));
            int fieldNum = Calendar.YEAR;
            if (field.equals("month")) {
                fieldNum = Calendar.MONTH;
            } else if (field.equals("date")) {
                fieldNum = Calendar.DATE;
            } else if (field.equals("hour")) {
                fieldNum = Calendar.HOUR_OF_DAY;
            } else if (field.equals("minute")) {
                fieldNum = Calendar.MINUTE;
            }
            cal.add(fieldNum, amount);

            return getDate(cal.getTime(), format);
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }

    }

    /**
     * 日期的指定域加减
     *
     * @param time
     * @param field
     * @param num
     * @return
     */
    public static Date addTime(Date time, String field, int amount) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(time);

        int fieldNum = Calendar.YEAR;
        if (field.equals("month")) {
            fieldNum = Calendar.MONTH;
        } else if (field.equals("date")) {
            fieldNum = Calendar.DATE;
        } else if (field.equals("hour")) {
            fieldNum = Calendar.HOUR_OF_DAY;
        } else if (field.equals("minute")) {
            fieldNum = Calendar.MINUTE;
        } else if (field.equals("year")) {
            fieldNum = Calendar.YEAR;
        }
        cal.add(fieldNum, amount);

        return cal.getTime();

    }

    /**
     * 得到现在的时间，格式时：分：秒
     *
     * @param format
     *            格式,如HH:mm:ss
     * @return 返回现在的时间可用于插入数据库和显示
     */
    public static String getNowTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 得到今天的星期
     *
     * @return 今天的星期
     */
    public static String getWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        return sdf.format(new Date());
    }

    /**
     * 得到今天的星期
     *
     * @return 今天的星期
     */
    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        return sdf.format(date);
    }

    /**
     * 得到一个日期是否是上午
     *
     * @param date
     *            日期
     * @return 日期为上午时返回true
     */
    public static boolean isAm(Date date) {
        boolean flag = false;
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        if (sdf.format(date).compareTo("12") < 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 把八位的格式为yyyyMMdd的日期转换为十位的yyyy-MM-dd格式
     *
     * @param date
     *            八位的格式为yyyyMMdd的日期
     * @return 十位的yyyy-MM-dd格式
     */
    public static String format(String date) {
        String returnDate = null;
        if (date.length() == 8) {
            returnDate = date.substring(0, 4) + "-" + date.substring(4, 6)
                    + "-" + date.substring(6, 8);
        } else {
            returnDate = DateUtil.today("yyyy-MM-dd");
        }
        return returnDate;
    }

    /**
     * 把八位的格式为yyyyMMdd的日期转换为十位的yyyy-MM-dd格式
     *
     * @param date
     *            八位的格式为yyyyMMdd的日期
     * @return 十位的yyyy-MM-dd格式
     */
    public static String format(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static final Date convertStrtoDateIsss(String dateStr) {
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = f.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static final Date convertStrtoDateIsss(String dateStr, String format) {
        DateFormat f = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = f.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获得当前日期周的日期
     *
     * @param date
     *            当前周的日期
     * @param week
     *            星期，0表示星期日，1表示星期一，6表示星期六。
     * @return 星期的日期
     */
    public static Date getWeek(Date date, int week) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, week + 1);
        String dateFmt = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return new Date(DateUtil.getTime(dateFmt));
    }

    /**
     * 获得下个星期的日期
     *
     * @param date
     *            当前周的日期
     * @return 星期的日期
     */
    public static Date getNextWeek(Date date) {
        date = new Date(date.getTime() + 604800000);
        String dateFmt = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return new Date(DateUtil.getTime(dateFmt));
    }

    /**
     *
     */
    public static int getLongTime(String time) {
        int longTime = 0;
        String[] tmp = time.split(":");
        if (tmp.length > 1) {
            longTime += Integer.parseInt(tmp[0]) * 3600000;
            longTime += Integer.parseInt(tmp[1]) * 60000;
        }
        return longTime;
    }

    /**
     * 根据类型返回 当前时间的前dateNum天 或者 当前时间的后dateNum天的时间字符串
     *
     * @param format
     *            表示需要返回的时间字符串的格式, 可以为空 , 默认为：yyyy-MM-dd HH:mm:ss 可参考是时间格式有：
     *            yyyy-MM-dd, yy-MM-dd
     * @param type
     *            0 表示当前时间的前dateNum天 1 表示当前时间的后dateNum天
     * @return 返回format 时间格式的串
     */
    public static final String getBeforeOrAfterDate(String format, int type,
                                                    int dateNum) {
        Calendar now = Calendar.getInstance();
        Date date = now.getTime();
        String result = "";
        format = format != null ? format.trim() : null;
        format = (format == null || "".equals(format)) ? "yyyy-MM-dd HH:mm:ss"
                : format;
        if (type == 0) {
            now.add(Calendar.DATE, -(dateNum));
            date = now.getTime();
            result = getDate(date, format);
        } else if (type == 1) {
            now.add(Calendar.DATE, dateNum);
            date = now.getTime();
            result = getDate(date, format);
        } else {
            result = getDate(date, format);
        }
        return result;
    }

    /**
     * 返回一个指定数字后的时间
     *
     * @param x
     *            指定几个小时
     * @return
     */
    public static String getTime1(int x) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
        // SimpleDateFormat format = new
        // SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制
        Date date = null;
        String day = today("yyyy-MM-dd HH:mm:ss");
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, x);// 24小时制
        // cal.add(Calendar.HOUR, x);12小时制
        date = cal.getTime();
        System.out.println("front:" + date);
        cal = null;
        return getDate(date, "HH");
    }

    /**
     * 时间比较 之后
     *
     * @param date2
     * @return
     */
    public static boolean isDateBefore(String date2) {
        try {
            Date date1 = new Date();
            Date date3 = getDates(date2, "yyyy-MM-dd HH:mm:ss");
            return date1.before(date3);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }
    }

    /**
     * 时间比较
     *
     * @param date2
     * @return
     */
    public static boolean isDateAfter(String date2) {
        try {
            Date date1 = new Date();
            Date date3 = getDates(date2, "yyyy-MM-dd HH:mm:ss");
            return date1.after(date3);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }
    }

    public static Date getDates(String date, String f) {
        Date returnDate = null;
        if (date == null || date.length() < 8) {
            return null;
        }
        if (date.length() == 8) {
            date = DateUtil.format(date);
        }
        DateFormat df = new SimpleDateFormat(f);
        try {
            returnDate = df.parse(date);
        } catch (ParseException e) {
            System.out.println("parse error " + e.getMessage());
        }
        return returnDate;
    }

    // 获得周一的日期
    public static String getMonday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 根据当前日期获取周一到周日的时间
     *
     * @return
     */
    public static String[] getWeek1to7() {
        String[] tmp = new String[] { "", "" };
        Calendar current = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Calendar begin = Calendar.getInstance();
        int a = current.get(Calendar.DAY_OF_WEEK) + 7;
        begin.add(Calendar.DAY_OF_YEAR, -a + 2);
        end.add(Calendar.DAY_OF_YEAR, 8 - a);
        // System.out.println(begin.getTime());
        // System.out.println(end.getTime());
        tmp[0] = getDate(begin.getTime(), "yyyy-MM-dd");
        tmp[1] = getDate(end.getTime(), "yyyy-MM-dd");
        return tmp;
    }

    // 产生周序列,上一周序列
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR) - 2);
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + "-" + week;

    }

    /**
     * 根据当前日期获取周一到周日的时间
     *
     * @return
     */
    public static String[] getNowWeek() {
        String[] tmp = new String[] { "", "" };
        Calendar current = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Calendar begin = Calendar.getInstance();
        int a = current.get(Calendar.DAY_OF_WEEK);
        begin.add(Calendar.DAY_OF_YEAR, -a + 2);
        end.add(Calendar.DAY_OF_YEAR, 8 - a);
        // System.out.println(begin.getTime());
        // System.out.println(end.getTime());
        tmp[0] = getDate(begin.getTime(), "yyyy-MM-dd");
        tmp[1] = getDate(end.getTime(), "yyyy-MM-dd");
        return tmp;
    }

    public static long getDeltaT(String date) {
        Date date1 = nowDay("yyyy-MM-dd");

        Date date2 = null;
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // long l = date1.getTime() - date2.getTime() > 0 ? date1.getTime() -
        // date2.getTime() :
        //
        // date2.getTime() - date1.getTime();
        //
        // System.out.println(l / 1000 + "秒");

        // 日期相减得到相差的日期

        long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    public static String getDeltaTime(String date) {

        Date date1 = nowDay("yyyy-MM-dd HH:mm");

        Date date2 = null;
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long l = date2.getTime() - date1.getTime() > 0 ? date2.getTime()
                - date1.getTime() :

                date2.getTime() - date1.getTime();
        //
        // System.out.println(l + "毫秒");

        // 日期相减得到相差的日期

        long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
        long hh = (date2.getTime() - date1.getTime()) / (60 * 60 * 1000);
        long mm = (date2.getTime() - date1.getTime()) / (60 * 1000);
        String result = "";
        if (day >= 0 && hh >= 0 && mm >= 0) {
            result = day + "天" + hh + "小时" + mm + "分";
        } else {
            result = "0天0小时0分";
        }
        // System.out.println();
        return result;
    }

    public static long getDeltaLong(String date) {
        Date date1 = nowDay("yyyy-MM-dd HH:mm");

        Date date2 = null;
        try {
            date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long l = date2.getTime() - date1.getTime() > 0 ? date2.getTime()
                - date1.getTime() :

                date2.getTime() - date1.getTime();
        //
        // System.out.println(l + "毫秒");
        return l;
    }

    private static Date StringToDate(String dateStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDates1(String date, String f) throws ParseException {
        Date returnDate = null;
        if (date == null || date.length() < 8) {
            return null;
        }
        if (date.length() == 8) {
            date = DateUtil.format(date);
        }
        DateFormat df = new SimpleDateFormat(f);
        returnDate = df.parse(date);
        return returnDate;
    }

    /**
     * 比较日期
     *
     * @author liuxiaofeng
     * @param enddate
     * @return date1 < date2 return true
     */
    public static boolean beforeNowDate(String date1, String date2) {
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        try {
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            boolean flag = d1.before(d2);
            return flag;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 时间比较
     *
     * @param date2
     * @return
     */
    public static boolean compareTime(String time1, String time2, String format)
            throws Exception {
        try {
            long timel1 = DateUtil.StringToDate(time1, format).getTime();
            long timel2 = DateUtil.StringToDate(time2, format).getTime();

            return (timel1 > timel2);
        } catch (Exception e) {
            throw new Exception("时间比较异常！");
        }
    }

    /**
     *
     * @description:获取当日零时零分零秒
     * @return Date
     */
    public static Date getToday() {

        Calendar calendar = Calendar.getInstance();

        // 将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        // 将秒至0
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();

    }

    /**
     *
     * @description:获取昨日零时零分零秒
     * @return Date
     */
    public static Date getYestoday() {

        Calendar calendar = Calendar.getInstance();

        // 将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        // 将秒至0
        calendar.set(Calendar.SECOND, 0);

        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();

    }

    /**
     *
     * @description:获取前日零时零分零秒
     * @return Date
     */
    public static Date getDayBeforeYestoday() {

        Calendar calendar = Calendar.getInstance();

        // 将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        // 将秒至0
        calendar.set(Calendar.SECOND, 0);

        calendar.add(Calendar.DATE, -2);

        return calendar.getTime();

    }

    /**
     *
     * @description:获取前两日零时零分零秒
     * @return Date
     */
    public static Date getDayBefore2days() {

        Calendar calendar = Calendar.getInstance();

        // 将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        // 将秒至0
        calendar.set(Calendar.SECOND, 0);

        calendar.add(Calendar.DATE, -3);

        return calendar.getTime();

    }

    /**
     *
     * @description:获取前两日零时零分零秒
     * @return Date
     */
    public static Date getDayBeforeNdays(int n) {

        Calendar calendar = Calendar.getInstance();

        // 将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        // 将秒至0
        calendar.set(Calendar.SECOND, 0);

        calendar.add(Calendar.DATE, -(n + 1));

        return calendar.getTime();

    }



    /**
     * get add later minutes
     *
     * @author CJP get the previous date to later date of difference
     */
    public static Date addMinute(long minutes, long nowTimes) {
        long nowMinute = 0l;
        Date date = null;
        try {
            if (nowTimes == 0l) {
                nowMinute = System.currentTimeMillis() + (1000 * 60 * minutes);
            } else {
                nowMinute = nowTimes + (1000 * 60 * minutes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        date = new Date(nowMinute);
        return date;
    }

    /**
     * 判断日期是否为当月第一天
     * @param date
     * @return
     */
    public static boolean isFirstDayCurMonth(Date date){

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int dayInt = cal.get(Calendar.DAY_OF_MONTH);

        if(dayInt ==1){

            return true;
        }else{

            return false;
        }


    }

    /**
     * 获取当前时间指定月数之前或之后的日期
     * @param month
     * @return
     */
    public static Map<String, Object> getStartEnd(int month){

        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.MONTH, month);
        String endDate = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        String startDate = DateUtil.format(ca.getTime(), "yyyy-MM-dd HH:mm:ss");

        Map<String, Object> startEnd = new HashMap<String, Object>();
        if(month<=0){
            startEnd.put("startDate", startDate);
            startEnd.put("endDate", endDate);
        }else{
            startEnd.put("startDate", endDate);
            startEnd.put("endDate", startDate);
        }
        return startEnd;
    }

    public static void main(String[] args) {


		/*Calendar cal = Calendar.getInstance();

		cal.setTime(DateUtil.getDates("2016-11-01", DateUtil.mFormatIso8601Day));

		int dayInt = cal.get(Calendar.DAY_OF_MONTH);

		System.out.println(isFirstDayCurMonth(DateUtil.getDates("2016-11-30 00:00:00", DateUtil.mFormatIso8601Day)));
		*/
        System.out.println(getStartEnd(-3));
    }

}
