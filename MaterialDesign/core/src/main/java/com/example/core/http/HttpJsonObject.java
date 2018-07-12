package com.example.core.http;


import com.example.core.http.model.BaseHttp;
import com.example.core.http.model.CommonRequest;
import com.example.core.http.model.CommonResult;

public class HttpJsonObject extends BaseHttp {

    public HttpJsonObject(CommonRequest request, CommonResult type) {
        super(request, type);
    }

    public void send() {
        HttpUtils.jsonVolley(request.getContext(), request.getUrl(), request.getToken(), request.getParametersMap(), this);
    }
}
