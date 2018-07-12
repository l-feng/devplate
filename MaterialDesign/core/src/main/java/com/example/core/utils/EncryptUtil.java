package com.example.core.utils;

import java.util.Map;

public class EncryptUtil {

    public static String encryptParameters(String key, Map<String, Object> parameters) {
        try {
            String result = "";
            for (String paramKey : parameters.keySet()) {
                result += paramKey + "=" + parameters.get(paramKey) + "&";
            }
            result = result.substring(0, result.length() - 1);
            String encrypt = AES.encrypt(key, result);
            LoggerUtil.debug(encrypt);
            return encrypt;
        } catch (Exception e) {
            LoggerUtil.error("Encrypt Parameters", e);
        }
        return "";
    }
}
