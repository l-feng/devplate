package com.example.core.http;


import com.example.core.http.model.BaseHttp;
import com.example.core.http.model.CommonRequest;
import com.example.core.http.model.CommonResult;

public class HttpPostBytes extends BaseHttp {

    public HttpPostBytes(CommonRequest request, CommonResult type) {
        super(request, type);
    }

    public void send() {
        HttpUtils.postBytesVolley(request.getContext(), request.getUrl(), request.getToken(),
                request.getFile(), request.getParametersMap(), this);
    }
}
