package com.example.administrator.materialdesign.utils.logUtils;

import com.example.administrator.materialdesign.common.NetWorkManager;
import com.example.administrator.materialdesign.utils.ContextUtil;
import com.example.administrator.materialdesign.utils.dateUtils.DateUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

public class SaveLogs2FileUtil  {

    private static String fileName = "lunatic"+"_"+ DateUtils.getCurrentDate()+".txt";
    public static String uploadLogs(final String fileLog){

        final String terCode = "lunatic"; //终端号
        //日志生成的日期
        final String createTime = DateUtils.getStringDate(System.currentTimeMillis());
        //将写入文件的信息 添加上终端号和生成日期 文件名
        final JSONObject jObj = new JSONObject();
        String newFileLog = null;
        newFileLog = "网络是否连接:"+ NetWorkManager.getInstance().getIsHasNet()
                +","+fileLog + ",Version:" + ContextUtil.getVersionName();
        try {
            jObj.put("terCode", terCode);
            jObj.put("createTime", createTime);
            jObj.put("fileLog", newFileLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        writerLogToFile(fileName, jObj.toString());
        return null;
    }
    /**
     * 将内容保存到文件中
     * @author	gt
     * @since	2015-9-22
     * @param filename
     * @param logStr
     */
    private static void writerLogToFile(String filename ,String logStr) {
        try {
            File targetDir = new File(FilePathManager.UP_LOG_DIR);
            if(!targetDir.exists()){
                targetDir.mkdirs();
            }
            if(!filename.equals("lunatic"+"_"+DateUtils.getCurrentDate())){//不是今天的日期就从新命名
                filename = "lunatic"+"_"+ DateUtils.getCurrentDate()+".txt";
            }
            File targetFile = new File(targetDir,filename);
            if(!targetFile.exists()){
                targetFile.createNewFile();
            }
            RandomAccessFile logFile = new RandomAccessFile(targetFile, "rw");
            logFile.seek(targetFile.length());
            logFile.write(logStr.getBytes());
            logFile.write("\r\n".getBytes());
            logFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
