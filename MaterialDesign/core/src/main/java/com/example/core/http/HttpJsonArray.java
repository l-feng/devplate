package com.example.core.http;


import com.example.core.http.model.BaseHttp;
import com.example.core.http.model.CommonRequest;
import com.example.core.http.model.CommonResult;

public class HttpJsonArray extends BaseHttp {

    public HttpJsonArray(CommonRequest request, CommonResult type) {
        super(request, type);
    }

    public void send() {
        HttpUtils.jsonArrayVolley(request.getContext(), request.getUrl(), request.getToken(), request.getParametersMap(), this);
    }
}
