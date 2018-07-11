package com.example.administrator.materialdesign.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class ContextUtil {
        //正在下载编制任务
        public static boolean fetchTaskIsRunning = false;
        public static Context mainContext;
        public static void setContext(Context context){
            mainContext = context;
        }

        /**
         * 获取版本号
         * @return
         */
        public static String getVersionName(){
            // 获取packagemanager的实例
            if(mainContext != null){
                PackageManager packageManager = mainContext.getPackageManager();
                // getPackageName()是你当前类的包名，0代表是获取版本信息
                PackageInfo packInfo;
                try {
                    packInfo = packageManager.getPackageInfo(mainContext.getPackageName(),0);
                    String version = packInfo.versionName;
                    return version;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    return "应用未安装";
                }
            }
            return "应用未安装";
        }
}
