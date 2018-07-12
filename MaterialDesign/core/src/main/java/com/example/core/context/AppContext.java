package com.example.core.context;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;


import com.example.core.device.DeviceInfo;
import com.example.core.device.Repository;
import com.example.core.device.Version;
import com.example.core.device.VersionHelper;
import com.example.core.http.model.CommonResult;
import com.example.core.listener.CompleteListener;
import com.example.core.model.User;
import com.example.core.router.UIRouter;
import com.example.core.utils.JsonUtil;
import com.example.core.utils.LoggerUtil;
import com.example.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/1/28 0028.
 */
public class AppContext {
    private static AppContext instance = new AppContext();
    private String packageName;

    private SharedPreferences prefs;
    private DeviceInfo deviceInfo = new DeviceInfo();
    private Context context;
    private UIRouter uiBus;
    private String cachedAppId;
    private String cachedAppKey;
    private String cachedLoginToken;
    private Handler syncHandler;

    private boolean isApkDownloaded = false;
    private VersionHelper version;
    private String appId;
    private User user;
    private int messageUnreadCount;
    private int imUnreadCount;
    private CompleteListener completeListener;
    private boolean receivePush = true;
    private Activity currentActivity;
    private String payReturnUrl;
    public static  String distinguishRuidai="r";

    private Map<String, String> advertisementShowTypeMap = new HashMap<>();

    public static AppContext getInstance() {
        return instance;
    }


    public void initialPreference(CompleteListener completeListener) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        cachedAppId = prefs.getString(PreferenceConstants.PREFERENCE_KEY_APP_ID, "");
        cachedAppKey = prefs.getString(PreferenceConstants.PREFERENCE_KEY_APP_KEY, "");
        cachedLoginToken = prefs.getString(PreferenceConstants.PREFERENCE_KEY_LOGIN_TOKEN, "");
        receivePush = !"false".equals(prefs.getString(PreferenceConstants.PREFERENCE_KEY_PUSH, ""));
        LoggerUtil.info("initial_appId=" + cachedAppId);
        LoggerUtil.info("initial_appKey=" + cachedAppKey);
        LoggerUtil.info("initial_loginToken=" + cachedLoginToken);
        if (StringUtils.isBlank(cachedAppId)) {
            uiBus.openUri(UIRouter.HOST + UIRouter.URI_INITIAL, completeListener);
        }
        Repository.init(context);
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getCachedAppId() {
        return cachedAppId;
    }

    public String getCachedLoginToken() {
        return cachedLoginToken;
    }

    public void setCachedAppId(String cachedAppId) {
        this.cachedAppId = cachedAppId;
        prefs.edit().putString(PreferenceConstants.PREFERENCE_KEY_APP_ID, cachedAppId).commit();
    }

    public void setCachedLoginToken(String cachedLoginToken) {
        this.cachedLoginToken = cachedLoginToken;
        prefs.edit().putString(PreferenceConstants.PREFERENCE_KEY_LOGIN_TOKEN, cachedLoginToken).commit();
    }

    public String getCachedAppKey() {
        return cachedAppKey;
    }

    public void setCachedAppKey(String cachedAppKey) {
        this.cachedAppKey = cachedAppKey;
        prefs.edit().putString(PreferenceConstants.PREFERENCE_KEY_APP_KEY, cachedAppKey).commit();
    }

    public void setPreference(String key, String value) {
        prefs.edit().putString(key, value).commit();
    }

    public String getPreference(String key) {
        return prefs.getString(key, "");
    }

    public UIRouter getUiBus() {
        return uiBus;
    }

    public void setUiBus(UIRouter uiBus) {
        this.uiBus = uiBus;
    }

    public void setSyncHandler(Handler syncHandler) {
        this.syncHandler = syncHandler;
    }

    public Handler getSyncHandler() {
        return syncHandler;
    }

    public boolean isApkDownloaded() {
        return isApkDownloaded;
    }

    public void setApkDownloaded(boolean isApkDownloaded) {
        this.isApkDownloaded = isApkDownloaded;
    }


    public VersionHelper getVersion() {
        if (version == null) {
            String versionJson = prefs.getString(PreferenceConstants.PREFERENCE_KEY_APP_VERSION, null);
            if (versionJson != null) {
                version = JsonUtil.getInstance().deserialize(versionJson,
                        VersionHelper.class);
            }
        }
        if (version == null) {
            version = new VersionHelper();
            version.setLastVersion(new Version(Repository.getVersion()));
        }
        return version;
    }

    public synchronized void storeVersion(VersionHelper version) {
        this.version = version;
        SharedPreferences.Editor editor = prefs.edit();
        if (version == null) {
            editor.putString(PreferenceConstants.PREFERENCE_KEY_APP_VERSION, null);
        } else {
            editor.putString(PreferenceConstants.PREFERENCE_KEY_APP_VERSION, JsonUtil.getInstance()
                    .serialize(version));
        }
        editor.commit();
    }

    public Version getLastVersion() {
        if (getVersion() != null) {
            return version.getLastVersion();
        }
        return null;
    }

    public void setMessageUnreadCount(int messageUnreadCount) {
        this.messageUnreadCount = messageUnreadCount;
    }

    public void setIMUnreadCount(int imUnreadCount) {
        this.imUnreadCount = imUnreadCount;
    }

    public int getMessageUnreadCount() {
        return messageUnreadCount;
    }

    public int getIMUnreadCount() {
        return imUnreadCount;
    }

    public void callback(CommonResult result, String action) {
        if (completeListener != null) {
            completeListener.onComplete(result, action);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogin() {
        return user != null;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public String getPayReturnUrl() {
        return payReturnUrl;
    }

    public void setPayReturnUrl(String payReturnUrl) {
        this.payReturnUrl = payReturnUrl;
    }

    public Map<String, String> getAdvertisementShowTypeMap() {
        return advertisementShowTypeMap;
    }
}
