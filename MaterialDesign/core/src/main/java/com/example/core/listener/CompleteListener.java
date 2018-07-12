package com.example.core.listener;


import com.example.core.http.model.CommonResult;

/**
 * Created by Administrator on 2016/1/19 0019.
 */
public interface CompleteListener {
    public void onComplete(CommonResult result, String action);

    public void onError(CommonResult result, String action);
}
