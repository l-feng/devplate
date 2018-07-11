package com.example.administrator.materialdesign.utils.logUtils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class FilePathManager  {
    private static FilePathManager singleton = null;
        private FilePathManager(){}
        public static FilePathManager getInstance(){
            if(singleton == null){
                synchronized(FilePathManager.class){
                    if(singleton == null){
                        singleton = new FilePathManager();
                    }
                }
            }
            return singleton;
        }
        public static final String appHome = Environment.getExternalStorageDirectory()
                .getAbsolutePath()+"/lunatic";//文件统一放在这个目录下
        public static final String MNT_SDCARD_PHOTO = appHome + "/photo";
        public static final String MNT_SDCARD_SOUND = appHome+ "/soundremark";
        public static String UP_LOG_DIR = appHome+"/logs";
        public static String DOWMNLOADAPK = appHome+"/"+"smartupdate/patch";
        /**
         * 初始化文件目录
         */
        public void init(){

            File mRecAudioPath = new File(MNT_SDCARD_SOUND);
            if (!mRecAudioPath.exists()){
                mRecAudioPath.mkdirs();
            }

            File photoPath = new File(MNT_SDCARD_PHOTO);
            if (!photoPath.exists()){
                photoPath.mkdirs();
            }

            File upLogDir = new File(UP_LOG_DIR);
            if (!upLogDir.exists()){
                upLogDir.mkdirs();
            }

            File update = new File(DOWMNLOADAPK);
            if (!update.exists()){
                update.mkdirs();
            }
        }
}
