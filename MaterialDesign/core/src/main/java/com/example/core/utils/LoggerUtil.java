package com.example.core.utils;

import android.util.Log;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 */
public class LoggerUtil {

    public static void info(Object msg) {
        /** 信息logger */
        info(LoggerUtil.class.getName(), msg.toString());
    }

    public static void info(String tag, Object msg) {
        /** 信息logger */
        Log.i(tag, msg.toString());
    }

    public static void access(Object msg) {
        /** 信息logger */
        Logger.getLogger("access.log").info(msg);
    }

    /**
     * debugLogger
     */
    public static void debug(Object msg) {
        debug(LoggerUtil.class.getName(), (String) msg);
    }

    public static void debug(String tag, Object msg) {
        Log.d(tag, (String) msg);
    }

    public static void alarmInfo(String msg, Exception e) {
        alarmInfo(LoggerUtil.class.getName(), (String) msg, e);
    }

    public static void alarmInfo(String tag, String msg, Exception e) {
        Log.w(LoggerUtil.class.getName(), (String) msg, e);
    }

    /**
     * errorLogger
     */
    public static void error(String msg, Throwable e) {
        error(LoggerUtil.class.getName(), msg, e);
    }

    public static void error(String tag, String msg, Throwable e) {
        Log.e(tag, msg, e);
    }

}
