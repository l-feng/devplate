/**
 * DeviceConfig.java
 *
 * @author tianli
 * @date 2011-3-24
 * <p/>
 * Copyright 2011 netease. All rights reserved.
 */
package com.example.core.device;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;


import com.example.core.utils.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

/**
 * @author tianli
 */
public class DeviceInfo {
    public static final String CTWAP = "ctwap";
    public static final String CMWAP = "cmwap";
    public static final String WAP_3G = "3gwap";
    public static final String UNIWAP = "uniwap";
    public static final int NETWORK_TYPE_WIFI = 0;
    public static final int NETWORK_TYPE_MOBILE = 1;

    private String deviceId;
    // private String macAddress;
    private int widthPixels;
    private int heightPixels;
    private String path = "";

    public String getDeviceId() {

        return deviceId;
    }

    private int networkState;
    private APN apn = new APN();

    public String getOperatorName(Context context) {
        if (context != null) {
            TelephonyManager fg = (TelephonyManager) context
                    .getSystemService(Activity.TELEPHONY_SERVICE);
            String imsi = fg.getSimOperator();
            String name = fg.getSimOperatorName();// .getSimOperator();

            if (Tools.isEmpty(imsi)) {
                return "unkown";
            } else if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                return "移动";// 中国移动
            } else if (imsi.startsWith("46001")) {
                return "联通";// 中国联通
            } else if (imsi.startsWith("46003")) {
                return "电信";// 中国电信
            }
        }
        return "";
    }

    // 返回一个33位的字符串 r+32位MD5码
    private String getRandom() {
        StringBuffer buffer = new StringBuffer(
                "0123456789abcdefghijklmnopqrstuvwxyz");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < 32; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return "r"
                + Tools.getMD5(System.currentTimeMillis() + sb.toString()).toLowerCase(
                Locale.US);
    }

    private boolean isValidID(String str) {
        if (str == null || str.length() < 10) {
            return false;
        }
        boolean t = false;
        for (int i = 0; i < str.length(); i++) {
            // 避免全0
            if (str.charAt(i) != '0' && str.charAt(i) != ':') {
                t = true;
                break;
            }
        }
        return t;

    }

    private String getLocalRandomString() {
        String imei = "";
        File file = new File(path + "imei");
        if (!file.exists()) {
            try {
                file.createNewFile();
                imei = getRandom();
                FileOutputStream out = new FileOutputStream(file);
                out.write(imei.getBytes("utf-8"));
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                FileInputStream in = new FileInputStream(file);
                byte[] data = new byte[33];
                int l = in.read(data);
                imei = new String(data, 0, l, "utf-8").trim();
                in.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {

            }

        }
        return imei;
    }

    public void setNetworkConnectivity(Context context) {
        // 优先写到SD卡
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {
                path = Environment.getExternalStorageDirectory() + File.separator
                        + Repository.APP_NAME + File.separator;
            } else {
                path = context.getFilesDir().getPath() + File.separator
                        + Repository.APP_NAME + File.separator;
            }
        } catch (RuntimeException e) {
            path = context.getFilesDir().getPath() + File.separator + Repository.APP_NAME + File.separator;
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    /*
     * // get imei TelephonyManager telephonyManager = (TelephonyManager)
     * context .getSystemService(Context.TELEPHONY_SERVICE); if
     * (telephonyManager != null) { deviceId = telephonyManager.getDeviceId();
     * 
     * } if(!isValidID(deviceId)) { deviceId=""; // get device mac address
     * WifiManager wifi =
     * (WifiManager)context.getSystemService(Context.WIFI_SERVICE); if(wifi !=
     * null) { try { WifiInfo info = wifi.getConnectionInfo(); if(info != null)
     * { deviceId = info.getMacAddress(); } } catch(Exception e) { deviceId =
     * ""; } } else deviceId = "";
     * 
     * if(!isValidID(deviceId)) { deviceId=getLocalRandomString(); } }
     */
        // check network status
        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = conManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo.getState() == NetworkInfo.State.CONNECTED
                || wifiInfo.getState() == NetworkInfo.State.CONNECTING) {
            networkState = NETWORK_TYPE_WIFI;
        } else {
            networkState = NETWORK_TYPE_MOBILE;
        }

        // NetworkInfo ni = conManager.getActiveNetworkInfo();
        // String apnName = ni.getExtraInfo();//获取网络接入点，这里一般为cmwap和cmnet
    /*
     * if(apnName.contains("ctwap")) { apn.setName("ctwap");
     * apn.setApnType("ctwap"); apn.setProxyServer(new Server("10.0.0.172",80));
     * } else if(apnName.contains("wap")) { apn.setName("cmwap");
     * apn.setApnType("cmwap"); apn.setProxyServer(new Server("10.0.0.172",80));
     * } else { apn.setName("cmnet"); apn.setApnType("cmnet");
     * apn.setProxyServer(new Server(null,80)); }
     */
        NetworkInfo mobileInfo = conManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileInfo != null) {

            String extra = mobileInfo.getExtraInfo();
            if (extra != null) {
                extra = extra.toLowerCase(Locale.US);
            } else {
                extra = "";
            }
            if (extra.contains(CMWAP) || extra.contains(WAP_3G)
                    || extra.contains(UNIWAP)) {
                apn.setName(extra);
                apn.setApnType(extra);
                apn.setProxyServer(new Server("10.0.0.172", 80));
            } else if (extra.contains(CTWAP)) {
                apn.setName(extra);
                apn.setApnType(extra);
                apn.setProxyServer(new Server("10.0.0.200", 80));
            } else {
                apn = new APN();
                Cursor cursor = null;
            }

        }
    }

    public int getNetworkState() {
        return networkState;
    }

    public void setNetworkState(int networkState) {
        this.networkState = networkState;
    }

    public APN getApn() {
        return apn;
    }

    public void setApn(APN apn) {
        if (apn != null) {
            this.apn = apn;
        }
    }

    public boolean isWifiConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = conManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null
                && (wifiInfo.getState() == NetworkInfo.State.CONNECTED || wifiInfo
                .getState() == NetworkInfo.State.CONNECTING)) {
            networkState = NETWORK_TYPE_WIFI;
        } else {
            networkState = NETWORK_TYPE_MOBILE;
        }

        return networkState == NETWORK_TYPE_WIFI;
    }

    public boolean isWapApn() {
        try {
            if (networkState != NETWORK_TYPE_WIFI && apn != null) {
                if (Tools.isEmpty(apn.getApnType()) || apn.getApnType().contains("wap")) {
                    Server proxy = apn.getProxyServer();
                    if (proxy != null && !Tools.isEmpty(proxy.getAddress())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public int getWidthPixels() {
        return widthPixels;
    }

    public void setWidthPixels(int widthPixels) {
        this.widthPixels = widthPixels;
    }

    public int getHeightPixels() {
        return heightPixels;
    }

    public void setHeightPixels(int heightPixels) {
        this.heightPixels = heightPixels;
    }

    public static boolean isC2DMSupported() {
        int sdkLevel = Build.VERSION.SDK_INT;
        if (sdkLevel >= 8) {
            return true;
        }
        return false;
    }

}
