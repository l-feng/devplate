package com.example.administrator.materialdesign.controller.base;

import android.content.Context;


import com.example.core.activity.BaseActivity;
import com.example.core.context.AppContext;
import com.example.core.http.HttpGet;
import com.example.core.http.HttpJsonArray;
import com.example.core.http.HttpJsonObject;
import com.example.core.http.HttpPost;
import com.example.core.http.HttpPostBytes;
import com.example.core.http.model.ComRetCode;
import com.example.core.http.model.CommonRequest;
import com.example.core.http.model.CommonResult;
import com.example.core.listener.CompleteListener;
import com.example.core.listener.ServiceListener;
import com.example.core.utils.EncryptUtil;
import com.example.core.utils.JsonUtil;
import com.example.core.utils.LoggerUtil;
import com.example.core.utils.ViewUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController implements ServiceListener {

    private static final String COMMON_ACTION = "common_action";
    private boolean requesting;
    protected CompleteListener completeListener;

    protected void sendRequest(CommonRequest request, String url, CommonResult result) {
        if (!request.isLock() || request.getListener().lockRequest()) {
            try {
                request.setUrl(url);
                if (CommonRequest.GET_METHOD.equals(request.getMethod())) {
                    new HttpGet(request, result).send();
                } else if (CommonRequest.JSON_OBJECT_METHOD.equals(request.getMethod())) {
                    new HttpJsonObject(request, result).send();
                } else if (CommonRequest.JSON_ARRAY_METHOD.equals(request.getMethod())) {
                    new HttpJsonArray(request, result).send();
                } else if (CommonRequest.POST_BYTES_METHOD.equals(request.getMethod())) {
                    new HttpPostBytes(request, result).send();
                } else {
                    new HttpPost(request, result).send();
                }

            } catch (Exception e) {
                LoggerUtil.error("Send Request", e);
                request.getListener().unlockRequest();
            }
        }
    }

    @Override
    public void complete(CommonResult result, String action) {
        try {
            if (result.getRetCode() == null) {
                ViewUtils.showToast(ComRetCode.FAIL_DESC);
                return;
            }
            if (result.getRetCode() != ComRetCode.SUCCESS) {
                onError(result, action);
                return;
            }
            onComplete(result, action);
        } catch (Exception e) {
            LoggerUtil.error("Request", e);
        } finally {
            unlockRequest();
        }
    }

    public boolean lockRequest() {
        if (requesting) {
            return false;
        }
        requesting = true;
        return true;
    }

    public void unlockRequest() {
        requesting = false;
    }

    protected abstract void onComplete(CommonResult result, String action);

    protected void onError(CommonResult result, String action) {
        ViewUtils.showToast(result.getRetDesc());
        Context context = getContext();
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).onError();
        }
        if (completeListener != null) {
            completeListener.onError(result, action);
        }
    }

    protected abstract Context getContext();

    public CompleteListener getCompleteListener() {
        return completeListener;
    }

    public void setCompleteListener(CompleteListener completeListener) {
        this.completeListener = completeListener;
    }

    public CommonRequest encryptRequest(String action, Map<String, Object> parameters) {
        Map<String, String> result = new HashMap<String, String>();
        result.put(HttpConstants.PARAMETER_APP_ID, AppContext.getInstance().getCachedAppId());
        result.put(HttpConstants.PARAMETER_PARAMS, EncryptUtil.encryptParameters(AppContext.getInstance().getCachedAppKey(), parameters));
        return CommonRequest.post(getContext(), this, action, result);
    }

    public CommonRequest encryptRequest(Map<String, Object> parameters) {
        return encryptRequest(COMMON_ACTION, parameters);
    }

    public CommonRequest getRequest(String action, String parameters) {
        return CommonRequest.get(getContext(), this, action, parameters).setToken(AppContext.getInstance().getCachedLoginToken());
    }

    public CommonRequest getRequest(String action, Map<String, Object> parameters) {
        return CommonRequest.get(getContext(), this, action, parameters).setToken(AppContext.getInstance().getCachedLoginToken());
    }

    public CommonRequest getRequest(String parameters) {
        return getRequest(COMMON_ACTION, parameters);
    }

    public CommonRequest postRequest(String action, Map<String, String> parameters) {
        return CommonRequest.post(getContext(), this, action, parameters).setToken(AppContext.getInstance().getCachedLoginToken());
    }

    public CommonRequest postRequest(Map<String, String> parameters) {
        return postRequest(COMMON_ACTION, parameters);
    }

    public CommonRequest jsonRequest(String action, Object object) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters = JsonUtil.getInstance().transferObjectToMap(object);
        return CommonRequest.json(getContext(), this, action, parameters).setToken(AppContext.getInstance().getCachedLoginToken());
    }

    public CommonRequest jsonRequest(Object object) {
        return jsonRequest(COMMON_ACTION, object);
    }

    public CommonRequest jsonArrayRequest(String action, Object object) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters = JsonUtil.getInstance().transferObjectToMap(object);
        return CommonRequest.jsonArray(getContext(), this, action, parameters).setToken(AppContext.getInstance().getCachedLoginToken());
    }

    public CommonRequest jsonArrayRequest(Object object) {
        return jsonArrayRequest(COMMON_ACTION, object);
    }


    public CommonRequest postBytesRequest(String action, File file) {
        return CommonRequest.postBytes(getContext(), this, action, file).setToken(AppContext.getInstance().getCachedLoginToken());
    }
}
