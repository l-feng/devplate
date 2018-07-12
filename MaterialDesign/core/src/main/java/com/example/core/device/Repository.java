package com.example.core.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.example.core.utils.LoggerUtil;
import com.example.core.utils.Tools;

import java.util.Hashtable;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;

public class Repository {
    public static final String APP_NAME = "xingdai";
    private static String version;
    private static String versionKey;
    private static String channel;
    private static String appId;
    private static String packageName;
    private static String curChannel;
    private static String versionCode;
    private static String deviceId;

    public final static int API_LEVEL = 1;// 供API做兼容时识别客户端的版本
    private static Map<String, String> packageNameMap;

    static {
        packageNameMap = new Hashtable<String, String>();
        packageNameMap.put("com.yuanshanbao.loan", Repository.APP_NAME + "_client");
    }

    public static void init(Context context) {
        try {
            packageName = context.getPackageName();
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_META_DATA);
//            appId = ai.metaData.get("AppKey").toString();
            // 如果末位是0,该API会忽略.比如 3.10 读入后是 3.1 ,所以XML中写成 3.10.0 样式
            version = ai.metaData.get("Version").toString();
            versionKey = ai.metaData.get("Version").toString();
            // 获取versionCode
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionCode = pi.versionCode + "";
//            int index = version.indexOf('.', 2);
//            if (index > 2) {
//                version = version.substring(0, index);
//            }
            channel = Tools.getChannel(context);// 优化先读取包中文件名
            if (TextUtils.isEmpty(channel)) {
                channel = ai.metaData.get("Channel").toString();
            }
            curChannel = channel;
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            String channelKey = "com." + Repository.APP_NAME + ".common.channel";
            channel = prefs.getString(channelKey, channel);
            if (Tools.isEmpty(channel)) {
                channel = APP_NAME;
            }
            Editor editor = prefs.edit();
            editor.putString(channelKey, channel);
            editor.commit();

            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } catch (Exception e) {
        }
    }

    /**
     * 软件版本号
     */
    public static String getVersion() {
        if (Tools.isEmpty(version)) {
            // it sucks to reach this case
            return "unknown";
        }
        return version;
    }

    /**
     * API version
     */
    public static String getApiVersion() {
        return "1.1";
    }

    /**
     * 软件版本
     */
    public static Version getTVersion() {
        if (Tools.isEmpty(version)) {
            return null;
        }
        Version tv = new Version();
        String r[] = version.split("\\.");
        tv.setMajor(Integer.parseInt(r[0]));
        tv.setMinor(Integer.parseInt(r[1]));
        if (r.length > 2) {
            tv.setBuild(Integer.parseInt(r[2]));
        }
        LoggerUtil.debug("GetTVersion", tv.getMajor() + "." + tv.getMinor());
        return tv;
    }

    /**
     * 设备类型
     */
    public static String getMobileType() {
        return "android";
    }

    /**
     * 软件推广渠道
     */
    public static String getChannel() {
        if (Tools.isEmpty(channel)) {
            channel = APP_NAME;
        }
        return channel;
    }

    /**
     * 软件推广渠道
     */
    public static String getCurChannel() {
        if (Tools.isEmpty(curChannel)) {
            curChannel = APP_NAME;
        }
        return curChannel;
    }

    /**
     */
    public static String getAppId() {
        return appId;
    }

    public static String getProductIdByPacName() {
        String productId = packageNameMap.get(packageName);
        if (!TextUtils.isEmpty(productId)) {
            return productId;
        } else {
            return APP_NAME + "_client";
        }
    }

    public static String getProductId() {
        return APP_NAME + "_client";
    }

    public static String getVersionCode() {
        return versionCode;
    }

    public static String getUserAgent() {
        String ua = APP_NAME + "(android " + Build.VERSION.RELEASE // + " " +
                // Build.DISPLAY
                + ";";
        if (!"unknown".equals(Build.MANUFACTURER)
                && !Tools.isEmpty(Build.MANUFACTURER)) {
            ua += Build.MANUFACTURER + " ";
        }

        ua += Build.MODEL + ";";
        DeviceInfo deviceInfo = new DeviceInfo();
        ua += deviceInfo.getWidthPixels() + "*" + deviceInfo.getHeightPixels();
        ua += ") " + Repository.APP_NAME + "/" + Repository.getVersion();
        ua += "_" + Repository.getVersionCode();
        return ua;
    }

    public static String getVersionKey() {
        if (Tools.isEmpty(versionKey)) {
            // it sucks to reach this case
            return "unknown";
        }
        return versionKey;
    }

    public static String getDeviceId() {
        return deviceId;
    }
}
