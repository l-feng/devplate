package com.example.core.listener;


import com.example.core.http.model.CommonResult;

/**
 * Created by Administrator on 2016/1/19 0019.
 */
public interface ServiceListener {
    public void complete(CommonResult result, String action);

    public boolean lockRequest();

    public void unlockRequest();
}
