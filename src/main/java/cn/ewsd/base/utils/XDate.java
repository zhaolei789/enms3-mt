/******************************************************************
 系统名称: J2EE通用底层
 模块名称: 公共
 软件版权: krwz
 功能说明: 日期函数
 系统版本: 1.0
 开发人员: stone
 开发时间: 2020-11-25
 审核人员:
 相关文档:
 修改记录: 修改日期 修改人员 修改说明
 ******************************************************************/
package cn.ewsd.base.utils;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class XDate {

    /**
     * 日期格式化。
     * <br>10位yyyy/MM/dd 转换为8位yyyyMMdd
     * @param date - 要格式化的日期字符串: 10位 yyyy/MM/dd或yyyy-MM-dd
     * @return String - 返回格式化后的日期
     * <br>若date长度不为10，即格式不为yyyy/MM/dd形式的日期，则直接返回date。
     * <br>若date为null, 则返回""
     */
    public static String dateTo8(String date) {

        if(date == null) return "";
        if (date.length()==4) return date;
        if (date.length()==7) return date.substring(0,4) + date.substring(5,7);
        if (date.length()==10) return date.substring(0,4) + date.substring(5,7) + date.substring(8,10);
        return "";
    }

    /**
     * 日期格式化。
     * <br>8位yyyyMMdd 转换为10位yyyy/MM/dd
     * @param date - 要格式化的日期字符串: 8位 yyyyMMdd
     * @return String - 返回格式化后的日期
     * <br>若date长度不为8，即格式不为yyyyMMdd形式的日期，则直接返回date。
     * <br>若date为null, 则返回""
     */
    public static String dateTo10(String date) {
        return dateTo10(date,"-");
    }

    public static String fomatXGDate(String day) {
        try {

            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
            String now = new SimpleDateFormat("yyyy/M/d").format(date);

            return now;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期格式化。
     * <br>8位yyyyMMdd 转换为10位yyyy/MM/dd
     * @param date - 要格式化的日期字符串: 8位 yyyyMMdd
     * @return String - 返回格式化后的日期
     * <br>若date长度不为8，即格式不为yyyyMMdd形式的日期，则直接返回date。
     * <br>若date为null, 则返回""
     */
    public static String dateTo10(String date, String flag) {
        if(date == null) return "";
        if (date.length()==4) return date;
        if (date.length()==6) return date.substring(0,4) + flag + date.substring(4,6);
        if (date.length()==8) return date.substring(0,4) + flag + date.substring(4,6) + flag + date.substring(6,8);
        if (date.length()==10) return date;
        return "";
    }

    /**
     * 时间格式化。
     * <br>8位(HH:mm:ss)或7位(H:mm:ss)的时间转换为6位(HHmmss)或5位(Hmmss)
     * <br>时间的分隔字符可以是任意字符，一般为冒号(:)
     * @param time - 要格式化的时间字符串:8位(HH:mm:ss)或7位(H:mm:ss)
     * @return String - 返回格式化后的时间
     * <br>若time长度不为8或7，即格式不为8位(HH:mm:ss)或7位(H:mm:ss)形式的时间，则直接返回date。
     * <br>若time为null, 则返回""
     */
    public static String timeTo6(String time) {
        if (time == null) return "";
        if (time.length()==2) return time;
        if (time.length()==5) return time.substring(0, 2)+time.substring(3, 5);
        if (time.length()==8) return time.substring(0, 2)+time.substring(3, 5)+time.substring(6, 8);
        return "";
    }

    /**
     * 时间格式化。
     * <br>6位(HHmmss)或5位(Hmmss)的时间转换为8位(HH:mm:ss)或7位(H:mm:ss)
     * @param time - 要格式化的时间字符串: 6位(HHmmss)或5位(Hmmss)
     * @return String - 返回格式化后的时间
     * <br>若time长度不为5或6，即格式不为6位(HHmmss)或5位(Hmmss)形式的时间，则直接返回date。
     * <br>若time为null, 则返回""
     */
    public static String timeTo8(String time) {
        return timeTo8(time, ":");
    }

    /**
     * 时间格式化。
     * <br>6位(HHmmss)或5位(Hmmss)的时间转换为8位(HH:mm:ss)或7位(H:mm:ss)
     * <br>时间的分隔字符是自定义的字符(flag)
     * @param time - 要格式化的时间字符串: 6位(HHmmss)或5位(Hmmss)
     * @param flag - 时间的分隔字符
     * @return String - 返回格式化后的时间
     * <br>若time长度不为5或6，即格式不为6位(HHmmss)或5位(Hmmss)形式的时间，则直接返回date。
     * <br>若time为null, 则返回""
     */
    public static String timeTo8(String time, String flag) {
        if(time == null) return "";
        if (time.length()==2) return time;
        if (time.length()==4) return time.substring(0,2) + flag + time.substring(2,4);
        if (time.length()==6) return time.substring(0,2) + flag + time.substring(2,4) + flag + time.substring(4,6);
        if (time.length()==8) return time;
        return "";
    }

    // 传入yyyyMMdd HHmmss格式的日期时间
    // 返回yyyy-MM-dd HH:mm:ss格式的日期时间
    public static String formatTime(String time) {
        SimpleDateFormat timeFormt = new SimpleDateFormat("yyyyMMdd HHmmss");
        SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date pracupwelltime = timeFormt.parse(time);
            time = formt.format(pracupwelltime);
        } catch (ParseException e) {
            return "";
        }
        return time;
    }

    /**
     * 获取当前日期或时间。
     * <br>根据不同的格式化字符串获取不同格式的日期或时间
     * <br>具体用法参见类java.text.SimpleDateFormat
     * <br>例如: format="yyyy" 则为获取当前年份
     * <br>      format="MM"   则为获取当前月份
     * <br>      format="dd"   则为获取当前日期号day
     * <br>      ...
     * @param format - 指定获取当前日期或时间格式的字符串
     * @return String - 返回指定格式的日期或时间字符串
     * <br>若格式字符串非法，则返回"";
     */
    public static String getDateTime(String format) {
        SimpleDateFormat dataFormat = null;
        try {
            dataFormat = new SimpleDateFormat(format);
        } catch (Exception e) {
            return "";
        }
        Date date = new Date();
        String dateString = dataFormat.format(date);
        return dateString;
    }

    /**
     * 获取当前日期。
     * <br>获取的日期格式为yyyyMMdd
     * @return String - 返回当前日期
     */
    // 注意：确保不抛出自定义异常
    public static String getDate() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String dateString = dataFormat.format(date);
        return dateString;
    }

    public static String getMonth() {
        return getDate().substring(0,6);
    }

    public static String getMon() {
        return getDate().substring(4,6);
    }

    public static String getYear() {
        return getDate().substring(0,4);
    }

    public static String getYear(String scanDate) {
        return scanDate.substring(0,4);
    }

    public static String getMon(String scanDate) {
        return scanDate.substring(4,6);
    }

    public static String getDay(String scanDate) {
        return scanDate.substring(6,8);
    }


    /**
     * 获取当前是星期几。
     * <br>0为星期天、1为星期一、以此类推。
     * @return String - 返回当前星期几
     */
    public static int getWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int posOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        posOfWeek--; //Calendar格式转化成普通格式  0星期天， 1 星期一...
        return posOfWeek;
    }

    /**
     * 获取当前时间。
     * <br>获取的时间的格式为6位(HHmmss)或5位(Hmmss)
     * @return String - 返回当前时间
     */
    public static String getTime() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }

    /**
     * 月份相加运算。
     * <br>指定日期(yyyyMM) 加上月份数(月份数正负都可以)，计算出新的日期(yyyyMM)
     * <br>例如  指定日期为200307, 加上月数为-8, 则计算得到日期为 200211
     * @param date - 指定日期,格式为yyyyMM
     * @param months - 指定相加月数
     * @return String - 返回计算后的日期
     */
    public static String addMonth(String date, int months) {
        if (date.length() != 6)
            return "";
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        month += months;
        while (month <= 0) {
            year--;
            month += 12;
        }
        while (month > 12) {
            year++;
            month -= 12;
        }
        String ret = "" + year;
        if (month >= 10)
            ret += month;
        else
            ret += "0" + month;
        return ret;
    }

    /**
     * 月份相加运算。
     * <br>指定日期(yyyyMMdd) 加上月份数(月份数正负都可以)，计算出新的日期(yyyyMMdd)
     * <br>例如  指定日期为20030708, 加上月数为-8, 则计算得到日期为 20021108
     * @param date - 指定日期,格式为yyyyMM
     * @param months - 指定相加月数
     * @return String - 返回计算后的日期
     */
    public static String addMonth8(String date, int months) {
        if (date.length() != 8)
            return "";
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        String day = date.substring(6, 8);
        month += months;
        while (month <= 0) {
            year--;
            month += 12;
        }
        while (month > 12) {
            year++;
            month -= 12;
        }
        String ret = "" + year;
        if (month >= 10)
            ret += month;
        else
            ret += "0" + month;

        int iDay = Integer.parseInt(day);
        if (month == 2) {
            if (( (year % 4) == 0) && ( (year % 100) != 0)) {
                if(iDay>29) day = "29";
            }
            else {
                if(iDay>28) day = "28";
            }
        }
        else if(month == 4 || month == 6 || month == 9 || month == 11) {
            if(iDay>30) day = "30";
        }
        ret += day;

        return ret;
    }

    // 取下月
    public static String getNextMonth() {
        return addMonth(getMonth(), 1);
    }

    public static String getNextMonth(String month) {
        return addMonth(month, 1);
    }

    // 取上月
    public static String getLastMonth() {
        return addMonth(getMonth(), -1);
    }

    public static String getLastMonth(String month) {
        return addMonth(month, -1);
    }

    /**
     * 日期相加运。
     * <br>指定日期(yyyyMMdd) 加上天数(天数正负都可以)，计算出新的日期(yyyyMMdd)
     * <br>例如  指定日期为20030702, 加上月数为-3, 则计算得到日期为 20030629
     * @param date - 指定日期,格式为yyyyMMdd
     * @param days - 指定相加天数
     * @return String - 返回计算后的日期
     */
    public static String addDate(String date, int days) {
        if (Long.parseLong(date) < 19010101)
            return date;
        String str = date;
        int year = Integer.parseInt(str.substring(0, 4));
        int month = Integer.parseInt(str.substring(4, 6));
        int day = Integer.parseInt(str.substring(6, 8));
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        calendar.add(Calendar.DATE, days);
        long newDate = calendar.get(Calendar.YEAR) * 10000 +
                (calendar.get(Calendar.MONTH) + 1) * 100 +
                calendar.get(Calendar.DAY_OF_MONTH);
        return "" + newDate;
    }

    /**
     * 时间相加运算。
     * <br>指定时间(HHmmss或Hmmss) 加上秒数(秒数正负都可以)，计算出新的时间
     * <br>例如  指定时间为203001, 加上秒数为-60, 则计算得到日期为 202901
     * @param time - 指定时间,格式为HHmmss或Hmmss
     * @param seconds - 指定相加秒数
     * @return String - 返回计算后的时间
     */
    public static String addTime(String time, int seconds) {
        int len = time.length();
        if (len < 5 || len > 6)
            return "";
        int hh = Integer.parseInt(time.substring(0, len - 4));
        int mm = Integer.parseInt(time.substring(len - 4, len - 2));
        int ss = Integer.parseInt(time.substring(len - 2, len));

        int s = seconds % 60;
        int m = seconds / 60;

        mm += m;
        ss += s;

        //处理秒
        if (ss < 0) {
            mm--;
            ss += 60;
        }
        if (ss >= 60) {
            mm++;
            ss -= 60;
        }

        //处理分
        hh += mm / 60;
        mm = mm % 60;
        if (mm >= 60) {
            hh++;
            mm -= 60;
        }
        if (mm < 0) {
            hh--;
            mm += 60;
        }

        //处理小时
        hh = hh % 24;
        if (hh < 0) {
            hh += 24;
        }

        //组返回字符串
        String newTime = "" + hh;
        if (mm < 10)
            newTime += "0" + mm;
        else
            newTime += mm;
        if (ss < 10)
            newTime += "0" + ss;
        else
            newTime += ss;
        return newTime;
    }

    /**
     * 计算两个日期相隔天数。
     * <br>计算结果统一成正数
     * @param date1 - 指定日期(yyyyMMdd)的其中一个
     * @param date2 - 指定日期(yyyyMMdd)的另外一个
     * @return int - 返回计算后的天数  失败返回-1
     */
    public static int diffDate(String date1, String date2) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
            Date d1 = simpledateformat.parse(date1);
            Date d2 = simpledateformat.parse(date2);

            long tmp = d1.getTime() - d2.getTime();
            if(tmp < 0) tmp = -tmp;

            int diff = (int)(tmp/(24*60*60*1000));

            return diff;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 计算两个日期时间相隔秒数。
     * <br>第一个减去第二个，允许返回负数
     * @param date1 - 指定日期(yyyyMMddhhmmss)的其中一个
     * @param date2 - 指定日期(yyyyMMddhhmmss)的另外一个
     * @return int - 返回计算后的天数  失败返回-1
     */
    public static long diffDateTime(String date1, String date2) throws Exception {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date d1 = simpledateformat.parse(date1);
            Date d2 = simpledateformat.parse(date2);

            long diff = d1.getTime() - d2.getTime();
            return diff;
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     * 计算两个日期相隔月数。
     * <br>计算结果统一成正数
     * <br>未实现   ---不好描述，看实际情况是否需要
     * @param date1 - 指定日期(yyyyMMdd)的其中一个
     * @param date2 - 指定日期(yyyyMMdd)的另外一个
     * @return int - 返回计算后的月数
     */
    public static int diffMonth(String date1, String date2) {
        if (!validDate8(date1) || !validDate8(date2)) {
            return -1;
        }

        if (Integer.parseInt(date1) > Integer.parseInt(date2)) {
            String str = date2;
            date2 = date1;
            date1 = str;
        }

        int year1 = Integer.parseInt(date1.substring(0,4));
        int mon1  = Integer.parseInt(date1.substring(4,6));
        int year2 = Integer.parseInt(date2.substring(0,4));
        int mon2  = Integer.parseInt(date2.substring(4,6));

        if (year1 == year2) {
            return mon2 - mon1;
        } else {
            return (year2-year1-1)*12 + mon2 + 12-mon1-1;
        }
    }

    /**
     * 获取一个月的最后一天。
     * <br>例如20030111,即一月份的最后一天是20030131
     * @param date - 指定日期(yyyyMMdd)
     * @return String - 返回计算后的日期
     */
    public static String getLastDayOfMonth(String date) {
        if (date.length() != 8)
            return "";
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 ||
                month == 10 || month == 12) {
            day = 31;
        }
        else if (month == 2) {
            if ( ( (year % 4) == 0) && ( (year % 100) != 0)) {
                day = 29;
            }
            else {
                day = 28;
            }
        }
        else {
            day = 30;
        }

        String newDate = "" + year;
        if (month < 10) {
            newDate += "0" + month + day;
        }
        else {
            newDate += "" + month + day;
        }
        return newDate;
    }

    /**
     * 比较两个日期前后
     * @param todaystring String   时间串1
     * @param compareString String  时间串2
     * @return 时间1  早于 时间2  返回true
     */
    public static boolean beforeDate(String todaystring, String compareString) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd HHmmss");
            Date d1 = simpledateformat.parse(todaystring);
            Date d2 = simpledateformat.parse(compareString);
            return d1.before(d2);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 比较两个日期前后
     * @param todaystring String   时间串1
     * @param compareString String  时间串2
     * @return 时间1  晚于 时间2  返回true
     */
    public static boolean afterDate(String todaystring, String compareString) {
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd HHmmss");
            Date d1 = simpledateformat.parse(todaystring);
            Date d2 = simpledateformat.parse(compareString);
            return d1.after(d2);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断时间是否在后两个时间之间
     * @param todaystring String 时间串1
     * @param startdate String 时间串2
     * @param enddate String 时间串3
     * @return boolean
     */
    public static boolean betweenDate(String todaystring, String startdate, String enddate) {
        return (beforeDate(todaystring, enddate) && afterDate(todaystring, startdate));
    }

    /**
     * 获取当前是星期几。
     * <br>0为星期天、1为星期一、以此类推。
     * @return String - 返回当前星期几
     */
    public static String getWeek(String scanDate) {
        Date date = null;
        if (scanDate.length() != 8)
            return "";

        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
        try{
            date = dataFormat.parse(scanDate);
        }catch(Exception e){
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int posOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        posOfWeek--;

        return ""+posOfWeek;
    }

    public static String getCWeek(String scanDate) {
        String posOfWeek = getWeek(scanDate);

        if ("".equals(posOfWeek)) return "未知日";

        switch(Integer.parseInt(posOfWeek)) {
            case 0: return "星期日";
            case 1: return "星期一";
            case 2: return "星期二";
            case 3: return "星期三";
            case 4: return "星期四";
            case 5: return "星期五";
            case 6: return "星期六";
        }

        return "未知日";
    }

    public static String getCDate8(String date8) {
        if (date8.length() == 8) {
            return date8.substring(0,4)+"年"+date8.substring(4,6)+"月"+date8.substring(6,8)+"日";
        } else if (date8.length() == 6) {
            return date8.substring(0,4)+"年"+date8.substring(4,6)+"月";
        } else if (date8.length() == 4) {
            return date8.substring(0,4)+"年";
        }
        return date8;
    }

    /**
     * 校验输入的8位日期是否合法
     *
     * @return
     */
    public static boolean validDate8(String strDate, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern(pattern);
            dateFormat.setLenient(false); 		//严格校验
            Date date = dateFormat.parse(strDate);
            if (date == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 校验输入的8位日期是否合法
     *
     * @return
     */
    public static boolean validDate8(String strDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("yyyyMMdd");
            dateFormat.setLenient(false); 		//严格校验
            Date date = dateFormat.parse(strDate);
            if (date == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 校验输入的6位月份是否合法
     *
     * @return
     */
    public static boolean validMonth6(String strDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("yyyyMM");
            dateFormat.setLenient(false); 		//严格校验
            Date date = dateFormat.parse(strDate);
            if (date == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 校验输入的6位时间是否合法
     *
     * @return
     */
    public static boolean validTime6(String strTime) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("HHmmss");
            dateFormat.setLenient(false); 		//严格校验
            Date time = dateFormat.parse(strTime);
            if (time == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    // 取当前旬 1-上旬  2-中旬  3-下旬
    public static String getXTenDay() {
        String date = getDate();
        int day = Integer.parseInt(date.substring(6, 8));

        if (day > 20) return "3";
        if (day > 10) return "2";
        return "1";
    }

}
