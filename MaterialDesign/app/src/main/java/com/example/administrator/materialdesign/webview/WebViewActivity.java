package com.example.administrator.materialdesign.webview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.example.core.context.AppContext;
import com.example.core.http.model.CommonResult;
import com.example.core.listener.CompleteListener;
import com.example.core.utils.LoggerUtil;
import com.example.core.utils.StringUtils;
import com.example.core.utils.Tools;

import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends BaseWebViewActivity implements
        DownloadListener, CompleteListener {
    public static final String DATA_URL = "url";
    public static final String DATA_TITLE = "title";
    public static final String DATA_PAY_TAG = "isPaying";
    public static final String DATA_EPAY_ACTIVE_TAG = "epayActive";
    public static final String DATA_SINGLE_TOP = "single_top";
    public final static String DATA_PAY_ACTIVITY = "payActivity";
    public final static String DATA_JUMP_JS = "jump_js";
    public final static String DATA_TAKE_OUT = "takeout";
    public final static String DATA_AD_PAGE = "ad_page";// 是否为广告页,如果是广告页会做一些特殊处理
    public final static String DATA_CHANGE_WORD = "changeWord";
    public String TITLE = null;
    private String url;
    private  boolean isPaying;
    private  boolean isSingleTop;
    private  boolean isPayForActivity;
    private  boolean isActiveEpay;
    private  boolean isTakeout;
    private  boolean isAdPage;
    private  boolean isChangePassword;

    private static String title;
    private static String currentUrl;


    private EventBroadcastReceiver eventListener;
   // private LoginController loginController;
    private String jsCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isPaying = getIntent().getBooleanExtra(DATA_PAY_TAG, false);
        isSingleTop = getIntent().getBooleanExtra(DATA_SINGLE_TOP, true);
        isPayForActivity = getIntent().getBooleanExtra(DATA_PAY_ACTIVITY, false);
        isActiveEpay = getIntent().getBooleanExtra(DATA_EPAY_ACTIVE_TAG, false);
        isTakeout = getIntent().getBooleanExtra(DATA_TAKE_OUT, false);
        isAdPage = getIntent().getBooleanExtra(DATA_AD_PAGE, false);
        isChangePassword = getIntent().getBooleanExtra(DATA_CHANGE_WORD, false);
        String title = getIntent().getStringExtra(DATA_TITLE);
        if (!Tools.isEmpty(title)) {
            setTitle(title);
        }
        jsCode = getIntent().getStringExtra(DATA_JUMP_JS);
        url = getIntent().getStringExtra(DATA_URL);

        eventListener = new EventBroadcastReceiver();
        eventListener.register();

        if (isAdPage) {// 如果是广告页则监听登录成功事件
            // showToolbar();

            // 对于内嵌支付页做特殊处理
            webView.setWebViewClient(new AdWebViewClient());
        }
        headBar.setRightListener(shareListener);

        if (url != null) {
            if (AppContext.getInstance().isLogin()) {
//                loginController = new LoginController(this);
//                loginController.setCompleteListener(this);
//                loginController.queryTempToken();
            } else {
                requestUrl();
            }
        }

        headBar.setBackListener(backListener);
    }

    @Override
    protected void onLocalJump(String url) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isSingleTop && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventListener != null) {
            eventListener.unregister();
            eventListener = null;
        }

    }

    @Override
    public void onComplete(CommonResult result, String action) {
        String tempToken = result.getRet("tempToken", String.class);
        if (StringUtils.isNotBlank(tempToken)) {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("token", tempToken);
           // url = UrlTool.addParams(url, parameters);
        }
        requestUrl();
    }

    private void requestUrl() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
                if (!TextUtils.isEmpty(jsCode)) {
                    webView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            webView.loadUrl("javascript:window.location.hash=\"" + jsCode
                                    + "\"");
                        }
                    }, 200);
                }
            }
        });
    }

    @Override
    public void onError(CommonResult result, String action) {

    }

    class EventBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        }

        private void dojsCallBack(String errStr) {
            String nameString = null;
            if (AppContext.getInstance().isLogin()) {
                nameString = AppContext.getInstance().getUser().getName();
            }
            StringBuilder builder = new StringBuilder();
            builder.append("javascript:window.Methods.");
            builder.append(jsCallBack);
            builder.append("('");
            builder.append("shareType=");
            builder.append(errStr);
            if (!Tools.isEmpty(nameString)) {
                builder.append("&username=");
                builder.append(nameString);
            }
            builder.append("')");
            webView.loadUrl(builder.toString());

        }

        public void register() {
            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(IntentUtils.INTENT_ACTION_LOGGED_IN);
//            intentFilter.addAction(IntentUtils.INTENT_ACTION_WEIXIN_SHARE_RESULT);
//            intentFilter.addAction(IntentUtils.INTENT_ACTION_YIXIN_SHARE_RESULT);
            registerReceiver(this, intentFilter);
        }

        public void unregister() {
            unregisterReceiver(this);
        }
    }

    private String backUri;

    public class AdWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = null;
            if (url == null || (uri = Uri.parse(url)) == null) {
                return false;
            }
            if (StringUtils.isNotBlank(AppContext.getInstance().getPayReturnUrl()) && url.startsWith(AppContext.getInstance().getPayReturnUrl())) {
                finish();
                return false;
            }

            if (url.startsWith("alipays:")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    startActivityForResult(intent, 1);
                    return true;
                } catch (Exception e) {
                    LoggerUtil.error("Open alipay", e);
                }
            }

            return onUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//            hideProgressBar();
//            if (jsBridgeService != null && pageStarted) {
//                jsBridgeService.onWebPageFinished();
//                jsBridgeService.readyWithEventName(LDJSService.EVENT_READY);
//            }
            pageStarted = false;
            super.onPageFinished(view, url);
            onUrlLoadingFinish(view, url);
            resetToolbar();
            view.loadUrl("javascript:window.local_obj.read('<head>'+" +
                    "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            if (StringUtils.isNotBlank(view.getTitle())) {
                headBar.setHeadTitle(view.getTitle());
                title = view.getTitle();
            }
            currentUrl = url;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            showProgressBar();
            super.onPageStarted(view, url, favicon);
            pageStarted = true;
            super.onPageStarted(view, url, favicon);
            if (isWebViewFirstLaunch) {
                if (JsBridgeHelper.isInsertIframeForAutoLogin(url)) {
                    webView.loadUrl(JsBridgeHelper.exchangeCookieUrl(AppContext
                            .getInstance().getCachedAppId(), AppContext.getInstance()
                            .getCachedLoginToken(), null));
                }
                isWebViewFirstLaunch = false;
            }
            if (url != null && url.startsWith("http")) {
                curHttpUrl = url;
            }
        }

        private int retry;

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            retry++;
            if (retry <= 3) {
                view.loadUrl(failingUrl); // fix a bug that CMWAP(some ROM) could not be
                // connected, retry to load failing URL
            } else {
                retry = 0;
            }
            resetToolbar();
        }

        // @TargetApi(8)
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();
        }

    }

    private View.OnClickListener shareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (StringUtils.isNotBlank(title)) {
//                ShareModel shareModel = new ShareModel();
//                shareModel.setTitle(title);
//                shareModel.setUrl(currentUrl);
//                shareModel.setText(htmlContent);
//                new SharePopupWindow(WebViewActivity.this, shareModel).show(headBar);
            }
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(webView.canGoBack()){
                webView.goBack();
            }else{
                finish();
            }
        }
    };



}
