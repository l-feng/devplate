package com.example.administrator.materialdesign.webview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.webkit.WebView;


import com.example.administrator.materialdesign.controller.base.HttpConstants;

import java.lang.ref.WeakReference;

public class JsBridgeHelper {
    public static final String HONGHONGCAI_HOST = HttpConstants.HOST;
    public static final String AUTO_LOGIN_STRING = "autologin=yes";
    private WeakReference<Context> mContext;
    private WebView mWebView;
    private String mBackUrl;
    private String mJsCallBack;
    private EventBroadcastReceiver mReceiver;

    public JsBridgeHelper(Context context, WebView webview) {
        mWebView = webview;
        mContext = new WeakReference<Context>(context);
        mReceiver = new EventBroadcastReceiver();
    }

    public void registBroadCast() {
        mReceiver.register();
    }

    public void unRegistBroadCast() {
        mReceiver.unregister();
    }

    public void setBackUrl(String url) {
        mBackUrl = url;
    }

    public void setJsCallBack(String jsCallback) {
        mJsCallBack = jsCallback;
    }

    public static String exchangeCookieUrl(String id, String token,
                                           String internalCallback) {
        return "javascript:" + "var iframe = document.createElement('iframe'); "
                + "iframe.id='__newsapp_loginredirect';iframe.style.display = 'none'; "
                + "iframe.src = 'http://" + HttpConstants.HOST + "/m/redirectWapUrl.html?"
                + "token=" + token + "&id=" + id + "&redirectUrl='; "
                + "document.body.appendChild(iframe);"
                + "iframe.onload = iframe.onreadystatechange ="
                + "function(){document.body.removeChild(iframe);"
                + (internalCallback == null ? "" : internalCallback) + "};";
    }

    public static boolean isInsertIframeForAutoLogin(String url) {
        boolean condition = false;
        if (url != null) {
            Uri uri = Uri.parse(url);
            if (uri != null) {
                if (uri.getHost().toLowerCase().trim().contains(HONGHONGCAI_HOST)
                        || uri.getPath().toLowerCase().trim().contains(AUTO_LOGIN_STRING)) {
                    condition = true;
                }
            }
        }
        return condition;
    }

    private class EventBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }

        private void dojsCallBack(String errStr) {
            StringBuilder builder = new StringBuilder();
            // 这里和前端约定好回调方法放到window中所以才这样调
            builder.append("javascript:window.Methods.");
            builder.append(mJsCallBack);
            builder.append("('");
            builder.append("shareType=");
            builder.append(errStr);
//      if (!Tools.isEmpty(nameString)) {
//        builder.append("&username=");
//        builder.append(nameString);
//      }
            builder.append("')");
            mWebView.loadUrl(builder.toString());
        }

        public void register() {
            IntentFilter intentFilter = new IntentFilter();
            if (mContext.get() != null) {
                mContext.get().registerReceiver(this, intentFilter);
            }
        }

        public void unregister() {
            if (mContext.get() != null) {
                mContext.get().unregisterReceiver(this);
            }
        }
    }
}
