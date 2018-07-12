package com.example.administrator.materialdesign.context;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.example.core.context.AppContext;
import com.example.core.device.DeviceInfo;
import com.example.core.device.Repository;
import com.example.core.http.model.CommonResult;
import com.example.core.listener.CompleteListener;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class HomeApplication   extends MultiDexApplication {


    private static final String TAG = Repository.APP_NAME;

    private final static HandlerThread MY_HANDLER_THREAD = new HandlerThread(
            Repository.APP_NAME + "threand");
    public static Handler sSyncHandler;
    private AppContext appContext;
    private UIBusService uiBusService;


    @Override
    public void onCreate() {
        super.onCreate();
        // 避免非主进程的第三方SDK重复初始化

//        if (!isProcess()) {
//            return;
//        }


        initialize();
    }

    /**
     * 初始化一些必须在程序一开始就初始化的东西
     */
    public void initialize() {
        MY_HANDLER_THREAD.start();
        sSyncHandler = new Handler(MY_HANDLER_THREAD.getLooper());
        // 初始化intent actions
        String packageName = getPackageName();
//        IntentUtils.initAction(packageName + "_");

        uiBusService = new UIBusService();
        uiBusService.register(new HostUIRouter());
        // AppContext 初始化
        appContext = AppContext.getInstance();
        appContext.setContext(this);
        appContext.setSyncHandler(sSyncHandler);
        appContext.setUiBus(uiBusService);
        appContext.initialPreference(new CompleteListener() {
            @Override
            public void onComplete(CommonResult result, String action) {
                if (!AppContext.getInstance().isLogin()) {
                }
            }

            @Override
            public void onError(CommonResult result, String action) {

            }
        });
        initDeviceInfo();

    }

    /**
     * 判断是否是主进程
     *
     * @return 是否是主进程
     */
    private boolean isProcess() {
        String pname = getCurProcessName();
        return pname.equals(getPackageName());
    }

    private String getCurProcessName() {
        int pid = android.os.Process.myPid();
        String processNameString = "";
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processNameString = appProcess.processName;
            }
        }
        return processNameString;
    }

    private void initDeviceInfo() {
        DeviceInfo deviceInfo = AppContext.getInstance().getDeviceInfo();
        deviceInfo.setNetworkConnectivity(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        if (dm != null) {
            deviceInfo.setWidthPixels(dm.widthPixels);
            deviceInfo.setHeightPixels(dm.heightPixels);
        }
    }



}
