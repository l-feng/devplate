package com.example.core.http;


import com.example.core.http.model.BaseHttp;
import com.example.core.http.model.CommonRequest;
import com.example.core.http.model.CommonResult;

/**
 * Created by Administrator on 2016/1/19 0019.
 */
public class HttpGet extends BaseHttp {
    public HttpGet(CommonRequest request, CommonResult type) {
        super(request, type);
    }

    public void send() {
        HttpUtils.getVolley(request.getContext(), request.getUrl(), request.getToken(), this);
    }
}
