package com.example.core.http.model;


import com.example.core.utils.JsonUtil;

import java.util.Collection;
import java.util.Map;

public class CommonResult {
    private Integer retCode;
    private String retDesc;
    private Object ret;
    private Map result;
    private Map<String, Object> controllerVariables;

    public CommonResult() {

    }

    public CommonResult(Map map) {
        this.result = map;
        try {
            this.retCode = (Integer) map.get("retCode");
            this.retDesc = (String) map.get("retDesc");
            this.ret = map.get("ret");
        } catch (Exception e) {
        }
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetDesc() {
        return retDesc;
    }

    public void setRetDesc(String retDesc) {
        this.retDesc = retDesc;
    }

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }

    public Map<String, Object> getControllerVariables() {
        return controllerVariables;
    }

    public void setControllerVariables(Map<String, Object> controllerVariables) {
        this.controllerVariables = controllerVariables;
    }

    public boolean getBooleanControllerVariable(String key, boolean defaultValue) {
        Object object = controllerVariables.get(key);
        if (object == null || !(object instanceof Boolean)) {
            return defaultValue;
        }
        return (Boolean) object;
    }

    public boolean success() {
        return retCode != null && retCode == ComRetCode.SUCCESS;
    }


    public <C> C getRet(Class<C> clazz) {
        return JsonUtil.getInstance().convert(getRet(), clazz);
    }

    public <C> C getRet(String key, Class<C> clazz) {
        return JsonUtil.getInstance().convert(result.get(key), clazz);
    }

    public <C extends Collection<?>, V> Object getRet(Class<C> collection, Class<V> clazz) {
        String content = JsonUtil.getInstance().serialize(getRet());
        return JsonUtil.getInstance().deserialize(content, collection, clazz);
    }

    public <C extends Collection<?>, V> Object getRet(String key, Class<C> collection, Class<V> clazz) {
        String content = JsonUtil.getInstance().serialize(result.get(key));
        return JsonUtil.getInstance().deserialize(content, collection, clazz);
    }

}
