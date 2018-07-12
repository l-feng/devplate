package com.example.core.utils;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

public class SysApplication extends Application {
    private ArrayList<Activity> mList = new ArrayList<Activity>();
    private ArrayList<Activity> detailList = new ArrayList<Activity>();
    private static SysApplication instance;

    private SysApplication() {
    }
    public synchronized static SysApplication getInstance() {
        if (null == instance) {
            instance = new SysApplication();
        }
        return instance;
    }
    // add Activity  
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void addDetailActivity(Activity activity) {
        detailList.add(activity);
    }

    /**
     * 关闭多余的Detail页面，使其总数不超过2个
     */
    public void removeExtraActivity() {
        if (detailList.size() > 2) {
            detailList.get(0).finish();
            detailList.remove(0);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
