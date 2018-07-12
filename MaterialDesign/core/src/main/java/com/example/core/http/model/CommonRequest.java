package com.example.core.http.model;

import android.content.Context;


import com.example.core.context.AppContext;
import com.example.core.listener.ServiceListener;
import com.example.core.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CommonRequest {

    public static final String GET_METHOD = "get";
    public static final String POST_METHOD = "post";
    public static final String JSON_OBJECT_METHOD = "json";
    public static final String JSON_ARRAY_METHOD = "jsonArray";
    public static final String POST_BYTES_METHOD = "postBytes";

    private Context context;
    private String parameters;
    private Map<String, String> parametersMap;
    private String method;
    private ServiceListener listener;
    private String url;
    private String action;
    private Map<String, Object> controllerVariables = new HashMap<String, Object>();
    private String token;
    private File file;
    private boolean lock = false;

    public CommonRequest(Context context, ServiceListener listener, String action, String parameters, Map<String, String> parametersMap, String method) {
        this.context = context;
        this.listener = listener;
        this.action = action;
        this.parameters = parameters;
        this.parametersMap = parametersMap;
        this.method = method;
        setAppIdAndToken();
    }

    private void setAppIdAndToken() {
        String parameters = "appId=" + AppContext.getInstance().getCachedAppId() + "&token=" + AppContext.getInstance().getCachedLoginToken();
        if (StringUtils.isNotBlank(this.parameters)) {
            this.parameters = parameters + "&" + this.parameters;
        } else {
            this.parameters = parameters;
        }
        if (parametersMap != null) {
            this.parametersMap.put("appId", AppContext.getInstance().getCachedAppId());
            this.parametersMap.put("token", AppContext.getInstance().getCachedLoginToken());
        }
    }

    public static CommonRequest get(Context context, ServiceListener listener, String action, String parameters) {
        return new CommonRequest(context, listener, action, parameters, null, GET_METHOD);
    }

    public static CommonRequest get(Context context, ServiceListener listener, String action, Map<String, Object> parametersMap) {
        return new CommonRequest(context, listener, action, getParameterContent(parametersMap), null, GET_METHOD);
    }


    public static CommonRequest post(Context context, ServiceListener listener, String action, String parameters) {
        return new CommonRequest(context, listener, action, null, getParameterMap(parameters), POST_METHOD);
    }

    public static CommonRequest post(Context context, ServiceListener listener, String action, Map<String, String> parametersMap) {
        return new CommonRequest(context, listener, action, null, parametersMap, POST_METHOD);
    }

    public static CommonRequest json(Context context, ServiceListener listener, String action, Map<String, String> parametersMap) {
        return new CommonRequest(context, listener, action, null, parametersMap, JSON_OBJECT_METHOD);
    }

    public static CommonRequest jsonArray(Context context, ServiceListener listener, String action, Map<String, String> parametersMap) {
        return new CommonRequest(context, listener, action, null, parametersMap, JSON_ARRAY_METHOD);
    }

    public static CommonRequest postBytes(Context context, ServiceListener listener, String action, File file) {
        CommonRequest commonRequest = new CommonRequest(context, listener, action, null, null, POST_BYTES_METHOD);
        commonRequest.setFile(file);
        return commonRequest;
    }

    private static String getParameterContent(Map<String, Object> parametersMap) {
        String parameters = "";
        for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
            parameters += entry.getKey() + "=" + entry.getValue() + "&";
        }
        if (parameters.length() > 0) {
            return parameters.substring(0, parameters.length() - 1);
        }
        return "";
    }


    private static Map<String, String> getParameterMap(String parameters) {
        Map<String, String> map = new HashMap<String, String>();
        String[] segs = parameters.split("&");
        for (String seg : segs) {
            String[] items = seg.split("=");
            if (items.length == 2) {
                map.put(items[0], items[1]);
            }
        }
        return map;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getParametersMap() {
        return parametersMap;
    }

    public void setParametersMap(Map<String, String> parametersMap) {
        this.parametersMap = parametersMap;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ServiceListener getListener() {
        return listener;
    }

    public void setListener(ServiceListener listener) {
        this.listener = listener;
    }

    public String getUrl() {
        if (GET_METHOD.equals(method) && StringUtils.isNotBlank(parameters)) {
            if (parameters.startsWith("?")) {
                return url + parameters;
            } else {
                return url + "?" + parameters;
            }
        }
        return url;
    }



    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public Map<String, Object> getControllerVariables() {
        return controllerVariables;
    }

    public void setControllerVariables(Map<String, Object> controllerVariables) {
        this.controllerVariables = controllerVariables;
    }

    public CommonRequest addControllerVariable(String key, Object value) {
        this.controllerVariables.put(key, value);
        return this;
    }

    public CommonRequest addControllerVariable1(String key, Object value) {
        this.controllerVariables.put(key, value);
        return this;
    }

    public CommonRequest setLock() {
        lock = true;
        return this;
    }

    public boolean isLock() {
        return lock;
    }

    public CommonRequest setToken(String token) {
        this.token = token;
        return this;
    }

    public String getToken() {
        return token;
    }
}
