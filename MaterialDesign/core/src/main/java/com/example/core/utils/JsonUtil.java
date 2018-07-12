package com.example.core.utils;


import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 序列化规则 查找 get is 开头的public且没有输入参数的方法和public 变量 反序列化时通过字串名+set 来实现 public
 * String getABC(){return "XXX";} public void setABC(int a){} 以上代码因参数类型不同会崩溃
 */
public class JsonUtil {
    private static final String TAG = JsonUtil.class.getName();
    private static JsonUtil instance = new JsonUtil();

    private ObjectMapper impl;

    public static JsonUtil getInstance() {
        return instance;
    }

    private JsonUtil() {
        impl = new ObjectMapper();
        impl.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
    }

    public String serialize(Object object) {
        try {
            return impl.writeValueAsString(object);
        } catch (Exception e) {
            LoggerUtil.alarmInfo(TAG, e.getMessage(), e);
            return null;
        }
    }

    public <T> T convert(Object object, Class<T> clazz) {
        String content = JsonUtil.getInstance().serialize(object);
        return JsonUtil.getInstance().deserialize(content, clazz);
    }

    public <T> T deserialize(String json, Class<T> clazz) {
        try {
            return impl.readValue(json, clazz);
        } catch (Exception e) {
            LoggerUtil.alarmInfo(TAG, e.getMessage(), e);
            return null;
        }
    }

    public <T extends Collection<?>, V> Object deserialize(String json,
                                                           Class<T> collection, Class<V> data) {
        try {
            return impl.readValue(json, impl.getTypeFactory()
                    .constructCollectionType(collection, data));
        } catch (Exception e) {
            LoggerUtil.alarmInfo(TAG, e.getMessage(), e);
            return null;
        }
    }

    public Map<String, String> transferObjectToMap(Object object) {
        Map<String, String> map = new HashMap<String, String>();
        map = deserialize(serialize(object), map.getClass());
        return map;
    }
}
