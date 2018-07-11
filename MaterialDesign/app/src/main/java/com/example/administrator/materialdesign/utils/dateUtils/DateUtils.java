package com.example.administrator.materialdesign.utils.dateUtils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class DateUtils {
    public static String getTodayOrTomorrow(long time) {
        Calendar calToday = Calendar.getInstance();
        calToday.setTimeInMillis(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        int dayDiff = cal.get(Calendar.DAY_OF_MONTH) - calToday.get(Calendar.DAY_OF_MONTH);

        if (dayDiff == 1) {
            return "明日";
        } else if (dayDiff == 2) {
            return "后天";
        } else if (dayDiff == 0) {
            return "今日";
        }
        return cal.get(Calendar.DAY_OF_MONTH) + "日";
    }

    /**
     * 获得今天0点的毫秒时间
     *
     * @return
     * @author huangxj
     * @since 2014-5-27
     */
    public static long getTodayMillisTime() {
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        long todayTime = cal.getTimeInMillis();
        return todayTime;
    }

    /**
     * 获得明天0点的毫秒时间
     *
     * @return
     * @author wbg
     * @since 2014-5-27
     */
    public static long getTomorrowMillisTime() {
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.add(Calendar.DAY_OF_MONTH, 1);

        long tomorrowTime = cal.getTimeInMillis();
        return tomorrowTime;
    }

    /**
     * 获得昨天0点的毫秒时间
     *
     * @return
     * @author huangxj
     * @since 2014-5-27
     */
    public static long getYesterdayMillisTime() {
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        long yesterdayTime = cal.getTimeInMillis();
        return yesterdayTime;
    }

    public static long getDayMillisTime(int day) {
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.add(Calendar.DAY_OF_MONTH, day);

        return cal.getTimeInMillis();
    }

    /**
     * 获取秒
     *
     * @param time
     * @return
     * @author hujinqiang
     * @since 2016-07-21 10:50:42
     */
    public static int getSeconds(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取当前的时间 格式 xxxx-xx-xx xx:xx:xx
     *
     * @return
     * @author gt
     * @since 2015-9-22
     */
    public static String getStringDate(long timeMill) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(timeMill);
        return dateStr;
    }

    /**
     * 获取当前的时间 格式 xxxx-xx-xx xx:xx:xx
     *
     * @return
     * @author gt
     * @since 2015-9-22
     */
    public static String getStringDateNoHour(long timeMill) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(timeMill);
        return dateStr;
    }

    /**
     * 获取格式化的日期
     */
    public static String getStringDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(calendar.getTimeInMillis());
        return dateStr;
    }

    /**
     * 获取格式化的日期
     */
    public static String getStringDateNoHour(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(calendar.getTimeInMillis());
        return dateStr;
    }

    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateStr = format.format(new Date());
        return dateStr;
    }

    /**
     * 将格式化时间转换成毫秒值
     * 2018-01-01 00:00:00
     *
     * @return
     */
    public static long getTimeMill(String date) {
        long time = 0;
        if (TextUtils.isEmpty(date)) {
            return time;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
            time = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}

