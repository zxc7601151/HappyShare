package com.happy.share.tools;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * desc: 日期工具类 <br/>
 * time: 2018/8/1 15:27 <br/>
 * author: 义仍 <br/>
 * since V 1.1 <br/>
 */
public interface ToolDate {

    /**
     * 获取指定时期模版的时间格式
     * 默认指定{@link Locale#US}统一输出为阿拉伯数字
     *
     * @param timeStamp 时间戳 单位秒数
     * @param pattern   例如yyyy/MM/dd 参考 {https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html}
     * @return 格式字符串如：2016/12/13 日期或pattern有问题可能返回""
     */
    @NonNull
    static String getFormatDate(long timeStamp, @NonNull String pattern) {
        return getFormatDate(new Date(timeStamp * 1000), pattern, Locale.US);
    }

    /**
     * 获取指定时期模版的时间格式
     * Locale指定了US统一输出阿拉伯数字
     *
     * @param date    日期
     * @param pattern 例如yyyy/MM/dd 参考 {https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html}
     * @return 格式字符串如：2016/12/13 日期或pattern有问题可能返回""
     * @see ToolDate#getFormatDate(Date, String, Locale)
     */
    static String getFormatDate(@NonNull Date date, @NonNull String pattern) {
        return getFormatDate(date, pattern, Locale.US);
    }

    /**
     * 获取指定时期模版的时间格式
     *
     * @param date    日期
     * @param pattern 例如yyyy/MM/dd 参考 {https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html}
     * @param locale  区域 如果要对输出日期本地化处理可以指定locale为 {@link Locale#getDefault}
     * @return 格式字符串如：2016/12/13 日期或pattern有问题可能返回""
     */
    @NonNull
    static String getFormatDate(@NonNull Date date, @NonNull String pattern, @NonNull Locale locale) {
        try {
            return new SimpleDateFormat(pattern, locale).format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据秒数获取时分秒
     *
     * @param second 秒数
     * @return 返回大小为3的int数组 int[0]-时 int[1]-分 int[2]-秒
     */
    @NonNull
    static int[] getHourMinSecond(long second) {
        int numHour = 60 * 60;
        long timeMin = second % numHour;
        return new int[]{
                // 时
                (int) (second / numHour)
                // 分
                , (int) (timeMin / 60)
                // 秒
                , (int) (timeMin % 60)};
    }

    /**
     * 根据秒数获取日时分秒
     *
     * @param second 秒数
     * @return 返回大小为4的int数组 int[0]-天 int[1]-时 int[2]-分 int[3]-秒
     */
    @NonNull
    static int[] getDayHourMinSecond(long second) {
        int numDay = 60 * 60 * 24;
        // 日
        int timeDay = (int) (second / numDay);
        int[] srcArr = getHourMinSecond(second % numDay);
        int[] timeArray = new int[4];
        timeArray[0] = timeDay;
        System.arraycopy(srcArr, 0, timeArray, 1, 3);
        return timeArray;
    }

    /**
     * 判断两个时间戳是否处于同一天
     *
     * @param timeMillis1 之前的时间 单位为毫秒
     * @param timeMillis2 之后的时间 单位为毫秒
     * @return true:是
     */
    static boolean isSameDay(long timeMillis1, long timeMillis2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date(timeMillis1));
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(timeMillis2));
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        return isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据秒数获取指定时分秒格式模板的字符串
     *
     * @param second  秒数
     * @param pattern 例如HH/MM/SS
     * @return 格式字符串如：22/12/33
     * String format方法使用参考{https://www.cnblogs.com/travellife/p/Java-zi-fu-chuan-ge-shi-hua-xiang-jie.html}
     */
    @NonNull
    static String getFormatHourMinSecond(long second, @NonNull String pattern) {
        if (!pattern.contains("HH") && !pattern.contains("MM") && !pattern.contains("SS")) {
            return pattern;
        } else if (!pattern.contains("MM") && !pattern.contains("SS")) {
            int hour = (int) (second / 3600);
            return pattern.replace("HH", String.format(Locale.US, "%02d", hour));
        } else if (!pattern.contains("HH") && !pattern.contains("SS")) {
            int min = (int) (second / 60);
            return pattern.replace("MM", String.format(Locale.US, "%02d", min));
        } else if (!pattern.contains("HH") && !pattern.contains("MM")) {
            return pattern.replace("SS", String.valueOf(second));
        } else if (!pattern.contains("HH")) {
            int min = (int) (second / 60);
            int sec = (int) (second % 60);
            return pattern.replace("MM", String.format(Locale.US, "%02d", min))
                    .replace("SS", String.format(Locale.US, "%02d", sec));
        } else if (!pattern.contains("MM")) {
            int hour = (int) (second / 3600);
            int sec = (int) (second % 3600);
            return pattern.replace("HH", String.format(Locale.US, "%02d", hour))
                    .replace("SS", String.format(Locale.US, "%02d", sec));
        } else if (!pattern.contains("SS")) {
            int hour = (int) (second / 3600);
            int min = (int) (second % 3600 / 60);
            return pattern.replace("HH", String.format(Locale.US, "%02d", hour))
                    .replace("MM", String.format(Locale.US, "%02d", min));
        } else {
            int hour = (int) (second / 3600);
            int min = (int) (second % 3600 / 60);
            int sec = (int) (second % 3600 % 60);
            return pattern.replace("HH", String.format(Locale.US, "%02d", hour))
                    .replace("MM", String.format(Locale.US, "%02d", min))
                    .replace("SS", String.format(Locale.US, "%02d", sec));
        }
    }


    /**
     * 将时间秒数转化为 hh:mm:ss格式 如果不到1小时 展示mm:ss格式
     *
     * @param time 需要转换的时间 单位s
     * @return 转化完成的时间
     */
    static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 时间位数转化
     *
     * @param time 需要转换的时间 假如1位数 则自动前面加上0
     * @return 返回格式化的时间
     */
    @NonNull
    static String unitFormat(int time) {
        String retStr = null;
        if (time >= 0 && time < 10) {
            retStr = "0" + Integer.toString(time);
        } else {
            retStr = "" + time;
        }
        return retStr;
    }

    /**
     * 时间转化成具体的秒数
     *
     * @param time time 只支持mm:ss
     * @return 返回总时间s数
     */
    static int time2Sec(@NonNull String time) {
        String[] times = time.split(":");
        int min = ToolNumber.toInt(times[0]);
        int sec = ToolNumber.toInt(times[1]);
        return min * 60 + sec;
    }

    /**
     * 将毫秒转换成秒
     *
     * @param time 毫秒时间
     */
    static int convertToSecond(Long time) {
        Date date = new Date();
        date.setTime(time);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, ToolNumber.toInt(time));
        return calendar.get(Calendar.SECOND);
    }
}