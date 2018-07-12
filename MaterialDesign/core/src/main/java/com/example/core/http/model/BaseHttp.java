package com.example.core.http.model;

import com.android.volley.VolleyError;
import com.example.core.http.util.VolleyListener;
import com.example.core.utils.JsonUtil;
import com.example.core.utils.LoggerUtil;


import java.util.Map;

public abstract class BaseHttp implements VolleyListener {
    protected CommonRequest request;
    protected CommonResult type;

    public BaseHttp(CommonRequest request, CommonResult type) {
        this.request = request;
        this.type = type;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        handleErrorResponse(volleyError);
    }

    private void handleErrorResponse(VolleyError volleyError) {
        LoggerUtil.error("Http Request", volleyError);
        CommonResult result = new CommonResult();
        result.setRetCode(ComRetCode.FAIL);
        result.setRetDesc(ComRetCode.FAIL_DESC);
        request.getListener().complete(result, request.getAction());
    }

    @Override
    public void onResponse(String content) {
        try {
            CommonResult result = null;
            if (type.getClass().equals(CommonResult.class)) {
                Map map = JsonUtil.getInstance().deserialize(content, Map.class);
                result = new CommonResult(map);
            } else {
                result = JsonUtil.getInstance().deserialize(content, type.getClass());
            }
            result.setControllerVariables(request.getControllerVariables());
            request.getListener().complete(result, request.getAction());
        } catch (Exception e) {
            LoggerUtil.error("Response", e);
            handleErrorResponse(new VolleyError(e));
        }
    }
}
