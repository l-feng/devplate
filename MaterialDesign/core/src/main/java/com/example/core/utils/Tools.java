package com.example.core.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;


import com.example.core.R;
import com.example.core.device.Repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 */
public class Tools {
    public final static String TAG = Tools.class.getName();
    public static final SimpleDateFormat SDF_SS = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SDF_DD = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static int getAppInfo(Context context, String data) {
        int result = 0;
        try {
            String[] t1 = data.split("\\|");
            for (int i = 0; i < t1.length; i++) {
                String[] t2 = t1[i].split("\\,");
                boolean isContain = false;
                for (int j = 0; j < t2.length; j++) {
                    try {
                        context.getPackageManager().getPackageInfo(t2[j], 0);
                        isContain = true;
                        break;
                    } catch (NameNotFoundException e) {
                    }
                }
                if (isContain) {
                    result |= (1 << i);
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static boolean isInteger(float number) {
        return Math.round(number) == number;
    }

    public static boolean checkInstalledApp(Context context, String appName) {
        if (TextUtils.isEmpty(appName)) {
            return false;
        }
        PackageInfo packageInfo;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(appName, 0);

        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height")
                        .get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    public static String getDeviceId(Context context) {
        String value0 = Settings.System.getString(context.getContentResolver(),
                Repository.APP_NAME + "_imei");
        if (!TextUtils.isEmpty(value0)) {
            try {
                byte[] v = AES.decrypt(Tools.getMD5Bytes(Repository.APP_NAME + "_imei"),
                        Base64.decode(value0, 0));
                String value = new String(v);
                return value;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int computeInitialSampleSize(BitmapFactory.Options options,
                                               int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1
                : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128
                : (int) Math.min(Math.floor(w / minSideLength),
                Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    public static int parseInt(String src, int defaultValue) {
        if (TextUtils.isEmpty(src)) {
            return defaultValue;
        }
        int index = src.indexOf(".");
        if (index > 0) {
            src = src.substring(0, index);
        }
        try {
            return Integer.parseInt(src);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String addSign(String s) {
        if (s.startsWith("+")) {
            return s;
        }
        if (Float.parseFloat(s) > 0) {
            return "+" + s;
        }
        return s;
    }

    public static long parseLong(String src, long defaultValue) {
        if (TextUtils.isEmpty(src)) {
            return defaultValue;
        }

        try {
            return Long.parseLong(src);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getMD5(String message) {
        String digest = message;
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(message.getBytes());
            digest = toHexString(algorithm.digest());
        } catch (Exception e) {
        }
        return digest;
    }

    public static byte[] getMD5Bytes(String message) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(message.getBytes());
            return algorithm.digest();
        } catch (Exception e) {
        }
        return null;
    }

    public static String getUserToken(String userId) {
        String userToken = "";
        if (!TextUtils.isEmpty(userId)) {
            String src = "" + userId + Repository.APP_NAME + "_user_info";
            userToken = getMd5ForCircle(src);
        }
        return userToken;
    }

    public static String getMd5ForCircle(String s) {
        byte abyte0[] = null;
        MessageDigest messagedigest;
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            throw new IllegalArgumentException("no md5 support");
        }
        messagedigest.update(s.getBytes());
        abyte0 = messagedigest.digest();
        return byte2hex(abyte0);
    }

    public static String byte2hex(byte abyte0[]) {
        StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
        for (int i = 0; i < abyte0.length; i++) {
            if ((abyte0[i] & 0xff) < 16) {
                stringbuffer.append("0");
            }
            stringbuffer.append(Long.toString((long) abyte0[i] & (long) 255, 16));
        }

        return stringbuffer.toString().toUpperCase();
    }

    public static String getChannel(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        System.out.println("channel=" + sourceDir);
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith("META-INF")
                        && entryName.contains("channel_")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        if (split != null && split.length >= 2) {
            return ret.substring(split[0].length() + 1);

        } else {

            return "";
        }
    }

    /**
     * split number with ',', for example 1000000.00 = 1,000,000.00
     */
    public static String splitNumber(String number) {
        if (!TextUtils.isEmpty(number)) {
            String[] array = number.split("\\.");
            number = "";
            if (array.length > 0) {
                int digits = array[0].length();
                for (int d = 0; d < digits; d++) {
                    if (d != 0 && d % 3 == 0) {
                        number = "," + number;
                    }
                    number = array[0].charAt(digits - 1 - d) + number;
                }
            }
            if (array.length > 1) {
                number += "." + array[1];
            }
        }
        return number;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp,
                                        final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.JPEG, 50, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // public static String getExpireText(Context context, Long time) {
    // long currentTime = System.currentTimeMillis();
    // long diff = time - currentTime;
    // if (diff <= 0) {
    // return context.getString(R.string.expired);
    // }
    // String result = "";
    // long minuteMs = 60 * 1000;
    // long hourMs = 60 * minuteMs;
    // long dayMs = 24 * hourMs;
    // long monthMs = 30 * dayMs;
    // long yearMs = 365 * dayMs;
    // if (diff < minuteMs) {
    // result += context.getString(R.string.within_one_minute);
    // } else if (diff < hourMs) {
    // result += diff / minuteMs + context.getString(R.string.after_minute);
    // } else if (diff < dayMs) {
    // result += diff / hourMs + context.getString(R.string.after_hour);
    // } else if (diff < monthMs) {
    // result += diff / dayMs + context.getString(R.string.after_day);
    // } else if (diff < yearMs) {
    // result += diff / monthMs + context.getString(R.string.after_month);
    // } else {
    // long years = diff / yearMs;
    // if (years >= 20) {
    // return context.getString(R.string.no_expired);
    // }
    // result += years + context.getString(R.string.after_year);
    // }
    // result += context.getString(R.string.expire);
    // return result;
    // }

    public static boolean isTablet(Context context) {
        // if(Build.VERSION.SDK_INT>=13)
        {
            int w = context.getResources().getDisplayMetrics().widthPixels;
            int h = context.getResources().getDisplayMetrics().heightPixels;
            int d = context.getResources().getDisplayMetrics().densityDpi;
            int s = w > h ? h : w;
            return s * 160 / d >= 600;
        }
        // return false;
    }

    public static boolean isHuawei(Context context) {
        int w = context.getResources().getDisplayMetrics().widthPixels;
        int h = context.getResources().getDisplayMetrics().heightPixels;
        int d = context.getResources().getDisplayMetrics().densityDpi;
        int s = w > h ? h : w;
        return (d <= 240 && s >= 720);
    }

    public static String getModifiedTimeText(Context context, Long time) {
        long currentTime = System.currentTimeMillis();
        if (time == null || time == 0 || currentTime - time < 0) {
            return context.getString(R.string.never);
        }
        long diff = currentTime - time;
        long minuteMs = 60 * 1000;
        long hourMs = 60 * minuteMs;
        long dayMs = 24 * hourMs;
        long monthMs = 30 * dayMs;
        long yearMs = 365 * dayMs;
        if (diff < minuteMs) {
            return context.getString(R.string.just_now);
        } else if (diff < hourMs) {
            return diff / minuteMs + context.getString(R.string.before_minute);
        } else if (diff < dayMs) {
            return diff / hourMs + context.getString(R.string.before_hour);
        } else if (diff < monthMs) {
            return diff / dayMs + context.getString(R.string.before_day);
        } else if (diff < yearMs) {
            return diff / monthMs + context.getString(R.string.before_month);
        } else {
            return diff / yearMs + context.getString(R.string.before_year);
        }
    }

    public static void updatePostCreateTime(Context context, String time,
                                            TextView tv) {
        Date date;
        try {
            date = SDF_SS.parse(time);
            // long stamp = date.getTime();
            String postTimeText = Tools.getPostTimeText(context, date);
            tv.setText(postTimeText);
        } catch (Exception e) {
            e.printStackTrace();
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public static String formatNumber(int num) {
        String s = "";
        if (num >= 0 && num < 10) {
            s = "0" + num;
        } else if (num >= 10) {
            s = "" + num;
        }
        return s;
    }

    public static String getPostTimeText(Context context, Date date) {

        long time = date.getTime();
        long currentTime = System.currentTimeMillis();
        if (time == 0 || currentTime - time < 0) {
            return context.getString(R.string.just_now);
        }
        long diff = currentTime - time;
        long minuteMs = 60 * 1000;
        long hourMs = 60 * minuteMs;
        long dayMs = 24 * hourMs;
        long yearMs = 365 * dayMs;
        Calendar calTime = Calendar.getInstance();
        Calendar calCurrent = Calendar.getInstance();
        calTime.setTimeInMillis(time);
        calCurrent.setTimeInMillis(currentTime);
        if (diff < minuteMs) {
            return context.getString(R.string.just_now);
        } else if (diff < hourMs) {
            return diff / minuteMs + context.getString(R.string.before_minute);
        } else if (isInSameDay(calTime, calCurrent)) {
            return Tools.formatNumber(date.getHours()) + ":"
                    + Tools.formatNumber(date.getMinutes());
        } else if (diff < yearMs) {
            long baseDays = diff / dayMs;
            long remainder = diff % dayMs;
            long ss = currentTime - remainder;
            if (isYesterday(new Date(ss))) {
                baseDays = baseDays + 1;
            }
            return baseDays + context.getString(R.string.before_day);
        } else {
            return 1 + context.getString(R.string.before_year);
        }

        // else if (isInSameMonth(calTime, calCurrent)) {
        // return (calCurrent.get(Calendar.DAY_OF_MONTH) - calTime
        // .get(Calendar.DAY_OF_MONTH)) + context.getString(R.string.before_day);
        // } else if (isInThreeMonth(calTime, calCurrent)) {
        // return getDeltaMonth(calTime, calCurrent)
        // + context.getString(R.string.before_month);
        // } else {
        // return "3" + context.getString(R.string.before_month);
        // }
    }

    // 同一天
    private static boolean isInSameDay(Calendar calTime1, Calendar calTime2) {
        if (calTime1.get(Calendar.YEAR) == calTime2.get(Calendar.YEAR) && calTime1
                .get(Calendar.DAY_OF_YEAR) == calTime2.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }
        return false;
    }

    // 同一个月
    private static boolean isInSameMonth(Calendar calTime1, Calendar calTime2) {
        if (calTime1.get(Calendar.YEAR) == calTime2.get(Calendar.YEAR)
                && calTime1.get(Calendar.MONTH) == calTime2.get(Calendar.MONTH)) {
            return true;
        }
        return false;
    }

    // 相差月数
    private static int getDeltaMonth(Calendar calTime1, Calendar calTime2) {

        if (calTime1.get(Calendar.YEAR) == calTime2.get(Calendar.YEAR)) {
            return calTime2.get(Calendar.MONTH) - calTime1.get(Calendar.MONTH);
        } else if (calTime2.get(Calendar.YEAR) - calTime1.get(Calendar.YEAR) >= 1) {
            return calTime2.get(Calendar.MONTH) - calTime1.get(Calendar.MONTH)
                    + 12 * (calTime2.get(Calendar.YEAR) - calTime1.get(Calendar.YEAR));
        } else {
            return 0;
        }

    }

    // 三个月之内
    private static boolean isInThreeMonth(Calendar calTime1, Calendar calTime2) {
        Calendar calCurrent = Calendar.getInstance();
        calCurrent.setTime(calTime2.getTime());
        calCurrent.add(Calendar.MONTH, -3);
        if (calCurrent.before(calTime1)) {
            return true;
        }
        return false;
    }

    public static boolean isToday(Date date) {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        return SDF_DD.format(today).equals(SDF_DD.format(date));
    }

    public static boolean isYesterday(Date date) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date yesterday = c.getTime();
        return SDF_DD.format(yesterday).equals(SDF_DD.format(date));
    }

    // public static CharSequence getWeekdaysStr(Context context, Calendar
    // calendar) {
    // CharSequence[] array = context.getResources()
    // .getTextArray(R.array.weekdays);
    // switch (calendar.get(Calendar.DAY_OF_WEEK)) {
    // case Calendar.MONDAY:
    // return array[0];
    // case Calendar.TUESDAY:
    // return array[1];
    // case Calendar.WEDNESDAY:
    // return array[2];
    // case Calendar.THURSDAY:
    // return array[3];
    // case Calendar.FRIDAY:
    // return array[4];
    // case Calendar.SATURDAY:
    // return array[5];
    // case Calendar.SUNDAY:
    // return array[6];
    // }
    // return "";
    // }

    private static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String str = Integer.toHexString(0xFF & b);
            while (str.length() < 2) {
                str = "0" + str;
            }
            hexString.append(str);
        }
        return hexString.toString();
    }

    /**
     * get C(m,n)
     */
    public static long getCombination(int m, int n) {
        if (n < m) {
            return 0;
        }
        long result = 1;
        int max, min;
        if (m > n - m) {
            max = m;
            min = n - m;
        } else {
            max = n - m;
            min = m;
        }
        for (int i = n; i > max; i--) {
            result *= i;
            System.out.println(System.currentTimeMillis() + result);
        }
        result = result / getFactorial(min);
        return result;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        if (connectivity.getNetworkInfo(0)
                .getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        NetworkInfo wifi_network = connectivity.getNetworkInfo(1);
        return wifi_network.getState() == NetworkInfo.State.CONNECTED;
    }

    public static boolean isLoadingPics(Context context) {
        return true;
        // ConnectivityManager connectivity = (ConnectivityManager) context
        // .getSystemService("connectivity");
        // if (connectivity == null) {
        // return false;
        // }
        //
        // SharedPreferences prefs = PreferenceManager
        // .getDefaultSharedPreferences(context);
        // boolean isEnable = prefs.getBoolean("key_3g_no_pic", true);
        //
        // NetworkInfo wifi_network = connectivity.getNetworkInfo(1);
        // if (wifi_network == null) {
        // return !isEnable;
        // }
        // boolean isWifi = wifi_network.getState() == NetworkInfo.State.CONNECTED;
        // return isWifi || !isEnable;
    }

    /**
     * convert dip to pixels
     */
    public static int getPixelByDip(Context context, int dip) {
        return (int) (context.getResources().getDisplayMetrics().density * dip
                + 0.5f);
    }

    public static int getPixelByDip(Context context, float dip) {
        return (int) (context.getResources().getDisplayMetrics().density * dip
                + 0.5f);
    }

    // 通过pixel获得字体大小的sp值
    public static int getTextSpByPixel(Context context, int pixel) {
        return (int) (pixel
                / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static long getFactorial(int m) {
        long result = 1;
        for (int i = 1; i <= m; i++) {
            result *= i;
            System.out.println(System.currentTimeMillis() + result);
        }
        return result;
    }

    public static byte[] inputStreamToBytes(InputStream stream) {
        if (stream == null) {
            return null;
        }
        ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
        final int blockSize = 512;
        byte[] buffer = new byte[blockSize];
        int count = 0;
        long size = 0;
        try {
            while ((count = stream.read(buffer, 0, blockSize)) > 0) {
                bytesStream.write(buffer, 0, count);
                size += count;
            }
        } catch (IOException e) {
            LoggerUtil.error(TAG, e.getMessage(), e);
            if (size == 0) {
                return null;
            }
        }
        return bytesStream.toByteArray();
    }

    private static Bitmap recordIcon = null;

    // public static Bitmap getRecordBitmap(Context context, int aRecord[]) {
    //
    // int sum = 0;
    // for (int i = 0; i < aRecord.length; i++) {
    // if (aRecord[i] > 0 && aRecord[i] < 10) {
    // sum++;
    // }
    // }
    //
    // if (sum == 0) {
    // return null;
    // }
    // if (recordIcon == null) {
    // recordIcon = BitmapFactory.decodeResource(context.getResources(),
    // R.drawable.sun_moon_star);
    // }
    //
    // Bitmap src = recordIcon;
    //
    // //
    // int srcW = src.getWidth() / 9;
    // int srcH = src.getHeight() / 8;
    // float picW = srcW;
    // float picH = srcH;
    // float dpi = context.getResources().getDisplayMetrics().density;
    //
    // picW *= dpi / 1.5f;
    // picH *= dpi / 1.5f;
    //
    // Bitmap bitmap = Bitmap.createBitmap((int) (picW * sum), (int) picW,
    // src.getConfig());
    // sum = 0;
    // Canvas cv = new Canvas(bitmap);
    // Paint paint = new Paint();
    //
    // for (int i = 0; i < aRecord.length; i++) {
    // if (aRecord[i] > 0) {
    // Rect srcRect = new Rect(aRecord[i] * srcW - srcW, i * srcH, aRecord[i]
    // * srcW, i * srcH + srcH);
    //
    // Rect desRect = new Rect((int) (sum * picW), 0,
    // (int) (sum * picW + picW), (int) picH);
    //
    // cv.drawBitmap(src, srcRect, desRect, paint);
    // sum++;
    // }
    // }
    // return bitmap;
    // }

    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    public static String getStackTrace(Throwable t) {
        if (t == null || t.getStackTrace() == null) {
            return null;
        }
        StackTraceElement[] stack = t.getStackTrace();
        String trace = "";
        for (StackTraceElement element : stack) {
            trace += element.toString();
            trace += "\n";
        }
        return trace;
    }

    public static Date getDate(String sValue) {
        int year = 0, month = 1, day = 1, hour = 0, minute = 0, second = 0;
        try {
            year = Integer.parseInt(sValue.substring(0, 4));
            month = Integer.parseInt(sValue.substring(5, 7));
            day = Integer.parseInt(sValue.substring(8, 10));
            hour = Integer.parseInt(sValue.substring(11, 13));
            minute = Integer.parseInt(sValue.substring(14, 16));
            second = Integer.parseInt(sValue.substring(17, 19));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Date date = calendar.getTime();
        return date;
    }

    // public static String[] Split(String src,int split)
    // {
    // Vector<String> temp=new Vector<String>();
    // int index=-1;
    // String tsrc=src;
    // while(true)
    // {
    // index=tsrc.indexOf(split);
    // if(index>0)
    // {
    // String t=tsrc.substring(0,index);
    // temp.addElement(t);
    // tsrc=tsrc.substring(index+1);
    // }
    // else if(index==0)
    // {
    // temp.addElement("");
    // tsrc=tsrc.substring(index+1);
    // }
    // else
    // {
    // temp.addElement(tsrc);
    // break;
    // }
    // }
    // String []r=new String[temp.size()];
    // temp.copyInto(r);
    // return r;
    // }

    public static long hexStringToLong(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            return 0;
        }
        long value = 0;
        hexString = hexString.toLowerCase(Locale.US);
        for (int i = 0; i < hexString.length(); i++) {
            char c = hexString.charAt(i);
            if (c >= '0' && c <= '9') {
                value = value * 16 + c - '0';
            } else if (c >= 'a' && c <= 'f') {
                value = value * 16 + 10 + c - 'a';
            }
        }
        return value;
    }

    public static long getRandomSeed(String message) {
        if (TextUtils.isEmpty(message)) {
            return System.currentTimeMillis();
        }
        long seed = 0;
        String md5 = getMD5(message);
        for (int i = 0; i < md5.length(); i += 8) {
            String hexString = md5.substring(i, Math.min(md5.length(), i + 8));
            long n = hexStringToLong(hexString);
            if (i == 0) {
                seed = n;
            }
            Random random = new Random(seed);
            seed = random.nextInt() + (n << 32);
        }
        Random random = new Random(seed);
        return random.nextLong();
    }

    public static int parseInt(String src) {
        return parseInt(src, -1);

    }

    public static float parseFloat(String src) {
        float result = -1f;
        if (!TextUtils.isEmpty(src)) {
            try {
                result = Float.parseFloat(src);
            } catch (NumberFormatException e) {
                e.printStackTrace();

            }
        }
        return result;
    }

    public static ArrayList<Integer> randomPick(int total, int count) {
        return randomPick(null, total, count);
    }

    public static ArrayList<Integer> randomPickNoSort(Long seed, int total,
                                                      int count) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (int i = 0; i < total; i++) {
            array.add(i);
        }
        return randomPick(seed, array, count, false);
    }

    public static ArrayList<Integer> randomPick(Long seed, int total, int count) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (int i = 0; i < total; i++) {
            array.add(i);
        }
        return randomPick(seed, array, count, true);
    }

    public static ArrayList<Integer> randomPick(ArrayList<Integer> array,
                                                int count) {
        return randomPick(null, array, count, true);
    }

    /*
     * public static ArrayList<Integer> randomPickNo(Long seed, ArrayList<Integer>
     * array, int count) { if(array == null) { return new ArrayList<Integer>(); }
     * if(count > array.size()) { return array; } Integer[] indices = new
     * Integer[array.size()]; for(int i = 0; i < indices.length; i++) { indices[i]
     * = i; } int size = indices.length; Random random; if(seed != null) { random
     * = new Random(seed); } else { random = new Random(); } // shuffle algorithm
     * for(int i = 0; i < size; i++) { int rand = random.nextInt(size - i); //
     * swap array[i] and array[rand + i] int temp = indices[i + rand]; indices[i +
     * rand] = indices[i]; indices[i] = temp; } ArrayList<Integer> list = new
     * ArrayList<Integer>(); for(int i = 0; i < count; i++) {
     * list.add(array.get(indices[i])); } Collections.sort(list,new
     * Comparator<Integer>() { public int compare(Integer object1, Integer
     * object2) { if(object1 < object2) { return -1; } else
     * if(object1.equals(object2)) { return 0; } return 1; } }); return list; }
     */
    public static ArrayList<Integer> randomPick(Long seed,
                                                ArrayList<Integer> array, int count, boolean sortResult) {
        if (array == null) {
            return new ArrayList<Integer>();
        }
        if (count > array.size()) {
            return array;
        }
        Integer[] indices = new Integer[array.size()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }
        int size = indices.length;
        Random random;
        if (seed != null) {
            random = new Random(seed);
        } else {
            random = new Random();
        }
        // shuffle algorithm
        for (int i = 0; i < size; i++) {
            int rand = random.nextInt(size - i);
            // swap array[i] and array[rand + i]
            int temp = indices[i + rand];
            indices[i + rand] = indices[i];
            indices[i] = temp;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < count; i++) {
            list.add(array.get(indices[i]));
        }
        if (sortResult) {
            Collections.sort(list, new Comparator<Integer>() {
                @Override
                public int compare(Integer object1, Integer object2) {
                    if (object1 < object2) {
                        return -1;
                    } else if (object1.equals(object2)) {
                        return 0;
                    }
                    return 1;
                }
            });
        }
        return list;
    }

    public static String getLatLon(double loc, int accuracy) {
        if (accuracy < 0 || accuracy > 6) {
            return loc + "";
        }
        String strLoc = loc + "";
        String[] parts = strLoc.split("\\.");
        strLoc = parts[0];
        if (parts.length > 0) {
            String part1 = "";
            for (int i = Math.min(parts[1].length(), accuracy) - 1; i >= 0; i--) {
                char c = parts[1].charAt(i);
                if (c == '0') {
                    if (TextUtils.isEmpty(part1)) {
                        continue;
                    }
                }
                part1 = c + part1;
            }
            if (!TextUtils.isEmpty(part1)) {
                strLoc = strLoc + "." + part1;
            }
        }
        return strLoc;
    }

    // 如果小数部分为0则不显示小数位
    @SuppressLint("NewApi")
    public static String formatMoneyIgnoreZero(float money) {
        if (money - (int) money != 0.0f) {
            DecimalFormat d = new DecimalFormat("##0.00");
            if (Build.VERSION.SDK_INT >= 9) {
                d.setRoundingMode(RoundingMode.DOWN);
            }
            return d.format(money);
        } else {
            return ((int) money) + "";
        }
    }

    public static String formatOdds(CharSequence in) {
        if (TextUtils.isEmpty(in)) {
            return "0.00";
        }
        return String.format(Locale.US, "%.2f", Float.parseFloat(in.toString()));
    }

    public static String formatMoney(double money) {
        if (money < 0) {
            return "" + money; // this case should not happen.
        }
        int n = (int) Math.round(money * 1000) / 10;
        // String number = n + "";
        if (n == 0) {
            return "0.0";
        }
        DecimalFormat df = new DecimalFormat("#0.00");// 个别手机转换后.会变成,
        return df.format(money);
    /*
     * StringBuilder result = new StringBuilder(); if(number.length() <= 2) {
     * result.append("0."); if(number.length() < 2) { result.append("0"); }
     * result.append(number); return result.toString(); }
     * result.append(number.substring(0, number.length() - 2));
     * result.append("."); String decimal = number.substring(number.length() -
     * 2, number.length()); if(decimal.charAt(decimal.length() - 1) == '0') {
     * result.append(decimal.charAt(0)); } else { result.append(decimal); }
     * return result.toString();
     */

    }

    // 四舍六入五成双规则
    public static float formatMoneySave2(float money) {

        if (money < 0) {
            return money; // this case should not happen.
        }

        int n = (int) (money * 1000);
        int n1 = n % 10;
        int n2 = 0;
        if (n1 > 5) {
            n2 = n / 10 + 1;
        } else if (n1 < 5) {
            n2 = n / 10;
        } else if (n1 == 5) {
            int n3 = n / 10;
            int n4 = n3 % 10;
            if (n4 % 2 == 0) {
                n2 = n3;
            } else {
                n2 = n3 + 1;
            }
        }
        float n5 = ((float) n2) / 100;

        return n5;

    }

    // public static List<List<MatchInfo>> classifyMatches(List<MatchInfo>
    // matches) {
    // List<List<MatchInfo>> classes = new ArrayList<List<MatchInfo>>();
    // if (matches != null) {
    // String matchDay = "";
    // List<MatchInfo> items = new ArrayList<MatchInfo>();
    // for (int i = 0; i < matches.size(); i++) {
    // MatchInfo match = matches.get(i);
    // if (i == 0 || !matchDay.equals(match.getMatchDay())) {
    // items = new ArrayList<MatchInfo>();
    // matchDay = match.getMatchDay();
    // classes.add(items);
    // }
    // items.add(match);
    // }
    // }
    // return classes;
    // }

    // public static List<List<MatchInfo>> classifyRecommendMatches(
    // List<MatchInfo> matches) {
    // List<List<MatchInfo>> classes = new ArrayList<List<MatchInfo>>();
    // if (matches != null) {
    // String matchDay = "";
    // int isTop = 0;
    // List<MatchInfo> items = new ArrayList<MatchInfo>();
    // for (int i = 0; i < matches.size(); i++) {
    // MatchInfo match = matches.get(i);
    // if (i == 0 || !matchDay.equals(match.getMatchDay())
    // || isTop != match.getIfTop()) {
    // items = new ArrayList<MatchInfo>();
    // matchDay = match.getMatchDay();
    // isTop = match.getIfTop();
    // classes.add(items);
    // }
    // items.add(match);
    // }
    // }
    // return classes;
    // }

    // public static List<List<WorldCupLiveMatchInfo>> classifyWorldCupMatches(
    // List<WorldCupLiveMatchInfo> matches) {
    // List<List<WorldCupLiveMatchInfo>> classes = new
    // ArrayList<List<WorldCupLiveMatchInfo>>();
    // if (matches != null) {
    // String matchDay = "";
    // List<WorldCupLiveMatchInfo> items = new ArrayList<WorldCupLiveMatchInfo>();
    // for (int i = 0; i < matches.size(); i++) {
    // WorldCupLiveMatchInfo match = matches.get(i);
    // if (match != null && match.getMatchDay() != null) {
    // if (i == 0 || !matchDay.equals(match.getMatchDay())) {
    // items = new ArrayList<WorldCupLiveMatchInfo>();
    // matchDay = match.getMatchDay();
    // classes.add(items);
    // }
    // items.add(match);
    // }
    // }
    // }
    // return classes;
    // }
    //
    // public static List<List<LiveScoreMatchInfo>> classifyLiveMatches(
    // List<LiveScoreMatchInfo> matches, boolean sortPositive) {
    // List<List<LiveScoreMatchInfo>> classes = new
    // ArrayList<List<LiveScoreMatchInfo>>();
    // TreeMap<String, List<LiveScoreMatchInfo>> map = new TreeMap<String,
    // List<LiveScoreMatchInfo>>();
    // if (matches != null) {
    // List<LiveScoreMatchInfo> items = new ArrayList<LiveScoreMatchInfo>();
    // for (int i = 0; i < matches.size(); i++) {
    // LiveScoreMatchInfo match = matches.get(i);
    // if (i == 0 || !map.containsKey(match.getMatchDay())) {
    // items = new ArrayList<LiveScoreMatchInfo>();
    // String key = match.getMatchDay();
    // items.add(match);
    // map.put(key, items);
    // } else {
    // map.get(match.getMatchDay()).add(match);
    // }
    // }
    // }
    // if (map.size() > 0) {
    // Set<String> keys = map.keySet();
    // for (String key : keys) {
    // if (map.get(key) != null && map.get(key).size() > 0) {
    // if (sortPositive) {
    // classes.add(map.get(key));
    // } else {
    // classes.add(0, map.get(key));
    // }
    // }
    // }
    // }
    // return classes;
    // }

    // fuwen add 20140804
    // public static List<List<LiveScoreMatchInfo>> classifyArenaLiveMatches(
    // List<LiveScoreMatchInfo> matches, boolean sortPositive) {
    // List<List<LiveScoreMatchInfo>> classes = new
    // ArrayList<List<LiveScoreMatchInfo>>();
    // TreeMap<String, List<LiveScoreMatchInfo>> map = new TreeMap<String,
    // List<LiveScoreMatchInfo>>();
    // if (matches != null) {
    // if (sortPositive) {
    // List<LiveScoreMatchInfo> items = new ArrayList<LiveScoreMatchInfo>();
    // for (int i = 0; i < matches.size(); i++) {
    // LiveScoreMatchInfo match = matches.get(i);
    // if (i == 0 || !map.containsKey(match.getMatchDay())) {
    // items = new ArrayList<LiveScoreMatchInfo>();
    // String key = match.getMatchDay();
    // items.add(match);
    // map.put(key, items);
    // } else {
    // map.get(match.getMatchDay()).add(match);
    // }
    // }
    // } else {
    // List<LiveScoreMatchInfo> items = new ArrayList<LiveScoreMatchInfo>();
    // for (int i = matches.size() - 1; i >= 0; i--) {
    // LiveScoreMatchInfo match = matches.get(i);
    // if (i == matches.size() - 1 || !map.containsKey(match.getMatchDay())) {
    // items = new ArrayList<LiveScoreMatchInfo>();
    // String key = match.getMatchDay();
    // items.add(match);
    // map.put(key, items);
    // } else {
    // map.get(match.getMatchDay()).add(match);
    // }
    // }
    // }
    // }
    // if (map.size() > 0) {
    // Set<String> keys = map.keySet();
    // for (String key : keys) {
    // if (map.get(key) != null && map.get(key).size() > 0) {
    // if (sortPositive) {
    // classes.add(map.get(key));
    // } else {
    // classes.add(0, map.get(key));
    // }
    // }
    // }
    // }
    // return classes;
    // }

    // fuwen add 20141008
    // public static List<List<BasketballMatchInfo>>
    // classifyArenaBasketballLiveMatches(
    // List<BasketballMatchInfo> matches, boolean sortPositive) {
    // List<List<BasketballMatchInfo>> classes = new
    // ArrayList<List<BasketballMatchInfo>>();
    // TreeMap<String, List<BasketballMatchInfo>> map = new TreeMap<String,
    // List<BasketballMatchInfo>>();
    // if (matches != null) {
    // if (sortPositive) {
    // List<BasketballMatchInfo> items = new ArrayList<BasketballMatchInfo>();
    // for (int i = 0; i < matches.size(); i++) {
    // BasketballMatchInfo match = matches.get(i);
    // if (i == 0 || !map.containsKey(match.getMatchDay())) {
    // items = new ArrayList<BasketballMatchInfo>();
    // String key = match.getMatchDay();
    // items.add(match);
    // map.put(key, items);
    // } else {
    // map.get(match.getMatchDay()).add(match);
    // }
    // }
    // } else {
    // List<BasketballMatchInfo> items = new ArrayList<BasketballMatchInfo>();
    // for (int i = matches.size() - 1; i >= 0; i--) {
    // BasketballMatchInfo match = matches.get(i);
    // if (i == matches.size() - 1 || !map.containsKey(match.getMatchDay())) {
    // items = new ArrayList<BasketballMatchInfo>();
    // String key = match.getMatchDay();
    // items.add(match);
    // map.put(key, items);
    // } else {
    // map.get(match.getMatchDay()).add(match);
    // }
    // }
    // }
    // }
    // if (map.size() > 0) {
    // Set<String> keys = map.keySet();
    // for (String key : keys) {
    // if (map.get(key) != null && map.get(key).size() > 0) {
    // if (sortPositive) {
    // classes.add(map.get(key));
    // } else {
    // classes.add(0, map.get(key));
    // }
    // }
    // }
    // }
    // return classes;
    // }

    // fuwen add 20140731
    // public static List<List<ArenaStoreRankTeaminfo>> classifyArenaMatches(
    // List<ArenaStoreRankTeamItem> matches) {
    // List<List<ArenaStoreRankTeaminfo>> list = new
    // ArrayList<List<ArenaStoreRankTeaminfo>>();
    // if (matches != null) {
    // for (int i = 0; i < matches.size(); i++) {
    // list.add(matches.get(i).getTeamInfoArray());
    // }
    // }
    // return list;
    // }

    // fuwen add 20140731
    // public static List<List<ArenaStoreAgendaTeaminfo>> classifyArenaAgendas(
    // List<ArenaStoreAgendaTeamItem> agendas) {
    // List<List<ArenaStoreAgendaTeaminfo>> list = new
    // ArrayList<List<ArenaStoreAgendaTeaminfo>>();
    // if (agendas != null) {
    // for (int i = 0; i < agendas.size(); i++) {
    // list.add(agendas.get(i).getTeamInfoArray());
    // }
    // }
    // return list;
    // }

    // public static List<List<MatchInfo>> classifyOrderMatches(
    // List<MatchInfo> matches) {
    // List<List<MatchInfo>> classes = new ArrayList<List<MatchInfo>>();
    // if (matches != null) {
    // String matchDay = "";
    // List<MatchInfo> items = new ArrayList<MatchInfo>();
    // for (int i = 0; i < matches.size(); i++) {
    // MatchInfo match = matches.get(i);
    // if (i == 0 || !matchDay.equals(match.getMatchDay())) {
    // items = new ArrayList<MatchInfo>();
    // matchDay = match.getMatchDay();
    //
    // String matchDayTemp = null;
    // int j;
    // for (j = classes.size() - 1; j >= 0; --j) {
    // matchDayTemp = classes.get(j).get(0).getMatchDay();
    // if (TimeUtils.isFirstAfterSecond(matchDayTemp, matchDay)) {
    // break;
    // }
    // }
    // j++;
    //
    // classes.add(j, items);
    // }
    // items.add(match);
    // }
    // }
    // return classes;
    // }
    //
    // public static List<List<Clearance>> classifyOrderClearances(
    // List<Clearance> clearances) {
    // Collections.sort(clearances);
    // List<List<Clearance>> classes = new ArrayList<List<Clearance>>();
    // if (clearances != null && clearances.size() > 0) {
    // int size = clearances.size();
    // Clearance cTemp = null;
    // List<Clearance> listTemp = new ArrayList<Clearance>();
    // classes.add(listTemp);
    // int i = 0;
    // for (; i < size; ++i) {
    // cTemp = clearances.get(i);
    // if (cTemp.getJoin() == 1) {
    // listTemp.add(cTemp);
    // } else {
    // break;
    // }
    // }
    //
    // listTemp = new ArrayList<Clearance>();
    // classes.add(listTemp);
    // for (; i < size; ++i) {
    // cTemp = clearances.get(i);
    // listTemp.add(cTemp);
    // }
    // }
    // return classes;
    // }

    public static String getElementsStr(ArrayList<String> elementList) {
        if (elementList != null && elementList.size() > 0) {
            String elementStr = "";
            int size = elementList.size();
            for (int i = 0; i < size - 1; ++i) {
                elementStr += elementList.get(i) + ",";
            }
            elementStr += elementList.get(size - 1);
            return elementStr;
        }
        return null;
    }

    public static ArrayList<String> getElemntList(String elementsStr) {
        if (!TextUtils.isEmpty(elementsStr)) {
            String[] elementArray = elementsStr.split(",");
            ArrayList<String> elementList = new ArrayList<String>();
            int size = elementArray.length;
            for (int i = 0; i < size; ++i) {
                elementList.add(elementArray[i]);
            }
            if (elementList.size() > 0) {
                return elementList;
            }
        }
        return new ArrayList<String>();
    }

    public static ArrayList<String> getOtherList(ArrayList<String> all,
                                                 ArrayList<String> sub) throws Exception {
        if (all == null) {
            return null;
        }
        if (sub == null) {
            return all;
        }
        int allSize = all.size();
        int subSize = sub.size();
        if (allSize < sub.size()) {
            throw new Exception("the sub list is not a part of the all list");
        }
        ArrayList<String> otherList = new ArrayList<String>();
        for (int i = 0; i < allSize; ++i) {
            String item = all.get(i);
            if (!sub.contains(item)) {
                otherList.add(item);
            }
        }
        if (otherList.size() + subSize > allSize) {
            throw new Exception("the sub list is not a part of the all list");
        }
        return otherList;
    }

    /**
     * 以亿或万表达数据
     *
     * @param src
     * @return 1.2亿 或 34万
     */
    public static String formatMoneyNumber(String src) {
        if (isEmpty(src)) {
            return "";
        }
        Double d = Double.valueOf(src.trim());
        if (d >= 100000000) {
            int n1 = (int) (d / 100000000);
            int n2 = (int) (d % 100000000) / 10000;
            String t = n1 + "亿";
            if (n2 > 0) {
                t += n2 + "万";
            }
            return t;
        } else if (d >= 10000) {
            int n1 = (int) (d / 10000);

            return n1 + "万";
        } else if (d < 0.01) {
            return "";
        } else {
            return src;
        }
    }

    /**
     * 每3位加,分隔
     *
     * @param money
     * @return
     */
    public static String formatMoney2(String money) {
        if (isEmpty(money)) {
            return "";
        }
        Double d = Double.valueOf(money.trim());
        if (d < 1.0) {
            return "";
        }
        int index = money.indexOf(".");
        if (index > 0) {
            money = money.substring(0, index);
        }
        StringBuffer buffer = new StringBuffer();
        int sp = 0;
        for (int i = money.length() - 1; i >= 0; i--) {
            buffer.insert(0, money.charAt(i));
            sp++;
            if (sp == 3) {
                sp = 0;
                if (i != 0) {
                    buffer.insert(0, ",");
                }
            }
        }
        return buffer.toString();
    }

    public static String formatFloat2(float value) {
        boolean isBigDecimal = false;
        DecimalFormat df = null;
        if (value > 1000000) {
            value = Math.round(value / 10000);
            isBigDecimal = true;
            df = new DecimalFormat("#0");
        } else {
            df = new DecimalFormat("#0.00");
        }

        String result = df.format(value);
        if (isBigDecimal) {
            result += "万";
        }
        return result;
    }

    // 保留两位小数
    public static String formatFloat3(float value) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String result = df.format(value);
        return result;
    }

    public static String formatNumberOfWinners(int number) {
        if (number < 10000) {
            return String.valueOf(number);
        }
        DecimalFormat df = new DecimalFormat("#0.0");
        String result = df.format(number / 10000f);
        if (result.endsWith(".0")) {
            result = result.substring(0, result.length() - 2);
        }
        return result + "万";
    }

    public static String formatFloat(double money) {
        if (money < 0) {
            return "" + money; // this case should not happen.
        }
        long n = Math.round(money * 1000) / 10;
        String number = n + "";
        if (n == 0) {
            return "0.0";
        }
        StringBuilder result = new StringBuilder();
        if (number.length() <= 2) {
            result.append("0.");
            if (number.length() < 2) {
                result.append("0");
            }
            result.append(number);
            return result.toString();
        }
        result.append(number.substring(0, number.length() - 2));
        result.append(".");
        String decimal = number.substring(number.length() - 2, number.length());
        if (decimal.charAt(decimal.length() - 1) == '0') {
            result.append(decimal.charAt(0));
        } else {
            result.append(decimal);
        }
        return result.toString();
    }

    public static ArrayList<String> getUniqueList(ArrayList<String> list) {
        ArrayList<String> tempList = new ArrayList<String>();
        for (String item : list) {
            if (!tempList.contains(item)) {
                tempList.add(item);
            }
        }
        return tempList;
    }

    public static HashMap<String, String> parseParams(Uri uri) {
        if (uri == null) {
            return new HashMap<String, String>();
        }
        HashMap<String, String> temp = new HashMap<String, String>();
        Set<String> keys = getQueryParameterNames(uri);
        for (String key : keys) {
            temp.put(key, uri.getQueryParameter(key));
        }
        return temp;
    }

    public static Set<String> getQueryParameterNames(Uri uri) {
        String query = uri.getEncodedQuery();
        if (query == null) {
            return Collections.emptySet();
        }

        Set<String> names = new LinkedHashSet<String>();
        int start = 0;
        do {
            int next = query.indexOf('&', start);
            int end = (next == -1) ? query.length() : next;

            int separator = query.indexOf('=', start);
            if (separator > end || separator == -1) {
                separator = end;
            }

            String name = query.substring(start, separator);
            names.add(URLDecoder.decode(name));

            start = end + 1;
        } while (start < query.length());

        return Collections.unmodifiableSet(names);
    }

    // 截取期次的部分期次数字
    // public static String cutPeriod(String gameEn, String period) {
    // if (TextUtils.isEmpty(period)) {
    // return "";
    // }
    // String cutPeriod = "";
    // try {
    // if (LotteryType.isY11(gameEn) || LotteryType.isKuai3(gameEn)
    // || LotteryType.LOTTERY_TYPE_KLPK.equals(gameEn)
    // || LotteryType.LOTTERY_TYPE_JXSSC.equals(gameEn)
    // || LotteryType.LOTTERY_TYPE_KLSF_GD.equals(gameEn)) {
    // cutPeriod = period.substring(period.length() - 2);
    // } else if (LotteryType.LOTTERY_TYPE_SSC.equals(gameEn)) {
    // cutPeriod = period.substring(period.length() - 3);
    // } else if (LotteryType.LOTTERY_TYPE_KL8.equals(gameEn)) {
    // cutPeriod = period;
    // } else {
    // cutPeriod = period.substring(period.length() - 3);
    // }
    // } catch (Exception e) {
    // if (AppContext.getInstance().getAppConfig().isTesting()) {
    // e.printStackTrace();
    // }
    // }
    // return cutPeriod;
    // }
    //
    // public static ArrayList<TrendGraphInfo> trendinfoArray2List(
    // TrendGraphInfo[] array) {
    // ArrayList<TrendGraphInfo> list = new ArrayList<TrendGraphInfo>();
    // if (array != null && array.length > 0) {
    // for (int i = 0; i < array.length; i++) {
    // list.add(array[i]);
    // }
    // }
    // return list;
    // }

    /**
     * @param str "xx.xx"
     * @return "xx.xx元"，小数点后内容变小。
     */
    public static CharSequence decoratePrize(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        CharSequence result = str;
        String[] arr = Tools.formatFloat3(Float.valueOf(str)).split("\\.");
        if (arr.length == 2) {
            result = Html.fromHtml(arr[0] + "<small>." + arr[1] + "元</small>");
        }

        return result;
    }

    /**
     * @param str      "已派xx.xx"
     * @param partSend "部分派奖"
     * @return str部分标红，数字和部分派奖部分缩小
     */
    public static CharSequence decorateSendPrize(String str, String partSend) {

        if (TextUtils.isEmpty(str)) {
            return "";
        }

        String result = "";
        String[] arr = str.split("\\.");
        if (arr.length == 2) {
            result = "<font color='#C33F51'>" + arr[0] + "<small>." + arr[1]
                    + "元</small></font>";
        } else {
            result = "<font color='#C33F51'>" + str + "<small>元</small></font>";
        }

        if (!TextUtils.isEmpty(partSend)) {
            result += "<font color='#8d7d65'><small>&nbsp;" + partSend
                    + "</small></font>";
        }

        return Html.fromHtml(result);
    }

    public static int[] getYiAndWanNumber(String number) {
        int[] result = new int[2];
        int index = number.indexOf(".");
        if (index == -1) {
            number = number.substring(0, number.length() - 4);
        } else {
            number = number.substring(0, index - 4);
        }

        String yi = number.substring(0, number.length() - 4);
        String wan = number.substring(yi.length(), number.length());
        result[0] = Integer.parseInt(yi);
        result[1] = Integer.parseInt(wan);
        return result;
    }

    // public static ArrayList<CombineMissNumberType>
    // combineMissNumberTypeArray2List(
    // CombineMissNumberType[] array) {
    // ArrayList<CombineMissNumberType> list = new
    // ArrayList<CombineMissNumberType>();
    // if (array != null && array.length > 0) {
    // for (int i = 0; i < array.length; i++) {
    // list.add(array[i]);
    // }
    // }
    // return list;
    // }

    public static Bitmap getDIPImage(Bitmap bitmap, Context context, int wdp) {
        int width = Tools.getPixelByDip(context, wdp);
        if (width > bitmap.getWidth()) {
            return bitmap;
        }
        int height = (int) (width * 1.0f / bitmap.getWidth() * bitmap.getHeight());
        Bitmap temp = Bitmap.createScaledBitmap(bitmap, width, height, true);
        if (temp != bitmap) {
            bitmap.recycle();
        }
        return temp;
    }

    public static boolean isEqual(float a, float b) {
        a = a - b;
        float EPSINON = 0.0000001f;
        if (Math.abs(a) > EPSINON) {
            return false;
        }
        return true;
    }

    public static String trimOdds(String odds) {
        try {
            if (Float.parseFloat(odds) > 0) {
                return odds;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static int getUserId(String iMUserId) {
        String userId = "";
        for (int i = 0; i < iMUserId.length(); i++) {
            try {
                Integer.parseInt(String.valueOf(iMUserId.charAt(i)));
                userId += iMUserId.charAt(i);
            } catch (NumberFormatException e) {
            }
        }
        return Integer.parseInt(userId);
    }
}
