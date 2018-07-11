package com.example.administrator.materialdesign.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class NetWorkManager{
        private boolean isHasNet = true;

        private static NetWorkManager singleton = null;
        public static long netChangeTime = 0;//网络变化的时间
        private NetWorkManager(){}
        public static NetWorkManager getInstance(){
            if(singleton == null){
                synchronized(NetWorkManager.class){
                    if(singleton == null){
                        singleton = new NetWorkManager();
                    }
                }
            }
            return singleton;
        }

        /**
         * 初始化刚进应用时用没有网络
         */
        public void init(Context context){
            if(isNetworkAvailable(context)){
                isHasNet = true;
            }else{
                isHasNet = false;
            }
        }

        /**
         * 检查网络是否可用
         */
        private boolean isNetworkAvailable(Context context) {

            ConnectivityManager manager = (ConnectivityManager) context
                    .getApplicationContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            if (manager == null) {
                return false;
            }
            NetworkInfo networkinfo = manager.getActiveNetworkInfo();
            if (networkinfo == null || !networkinfo.isAvailable()) {
                return false;
            }
            return true;
        }
        /**
         * 设置是否有网络
         * @param isHasNet
         */
        public void setNetWorkState(boolean isHasNet){
            this.isHasNet = isHasNet;
            MessageEvent event = new MessageEvent();
            if(isHasNet){
                event.type = MessageEvent.EventType.HAS_NET;
            }else{
                event.type = MessageEvent.EventType.NO_NET;
            }
            RxBus.get().post(event);
        }

        /**
         * 获取是否有网络
         * @return
         */
        public boolean getIsHasNet(){
            return isHasNet;
        }
}

