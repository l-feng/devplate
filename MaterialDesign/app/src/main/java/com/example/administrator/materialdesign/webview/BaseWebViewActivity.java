package com.example.administrator.materialdesign.webview;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.administrator.materialdesign.R;
import com.example.core.CustomAlertDialog;
import com.example.core.activity.BaseActivity;
import com.example.core.context.AppContext;
import com.example.core.device.DeviceInfo;
import com.example.core.device.Repository;
import com.example.core.device.Server;
import com.example.core.utils.Tools;

import java.util.Locale;

public abstract class BaseWebViewActivity extends BaseActivity implements
        DownloadListener, OnClickListener {
    protected WebView webView;
    protected MyWebViewClient client = new MyWebViewClient();
    protected WebChromeClient chromeClient = new MyChromeClient();
    protected View toolBar, safeLoading;
    protected ImageView btnBack, btnForward, btnRefresh;
    protected FrameLayout frameLayout;
    protected WebChromeClient.CustomViewCallback myCallBack = null;
    private View myView = null;

    protected String jsCallBack;
    protected String curHttpUrl;
    protected JsBridgeHelper jsBridgeHelper;
    protected boolean isWebViewFirstLaunch = true;
    protected boolean pageStarted;// 4.4手机执行核心JS时会触发onPageFinish，引起死循环，所以加了此变量加以控制
    protected JsResult jsResult = null;
    protected ProgressBar pageProgress;
    protected String htmlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        if (Build.VERSION.SDK_INT >= 11) {// 4.0以上，打开硬件加速才可以显示视频
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        frameLayout = (FrameLayout) findViewById(R.id.web_frameview);
        initWebView();
        showCloseTitle();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (webView != null && webView.canGoBack()) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    webView.goBack();
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    protected void showCloseTitle() {
//    if (getLeftTitleButton() != null) {
//      getLeftTitleButton().setVisibility(View.GONE);
//      getLeftTitleDivider().setVisibility(View.GONE);
//      getLeftCloseButton().setVisibility(View.VISIBLE);
//      getLeftCloseButton().setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//          finish();
//        }
//      });
//    }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnBack) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
            }
        } else if (v == btnForward) {
            if (webView != null && webView.canGoForward()) {
                webView.goForward();
            }
        } else if (v == btnRefresh) {
            if (webView != null) {
                webView.reload();
            }
        }
    }

    public void showToolbar() {
        if (toolBar != null) {
            toolBar.setVisibility(View.VISIBLE);
        }
    }

    public void hidenToolbar() {
        if (toolBar != null) {
            toolBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint("JavascriptInterface")
    protected void initWebView() {
        // 该工具条默认隐藏
        toolBar = findViewById(R.id.wap_toolbar);
        if (toolBar != null) {
            btnBack = (ImageView) findViewById(R.id.wap_back);
            btnForward = (ImageView) findViewById(R.id.wap_forward);
            btnRefresh = (ImageView) findViewById(R.id.wap_refresh);
            btnBack.setOnClickListener(this);
            btnForward.setOnClickListener(this);
            btnRefresh.setOnClickListener(this);
        }
        pageProgress = (ProgressBar) findViewById(R.id.progressline);
        if (pageProgress != null) {
            pageProgress.setMax(100);
            pageProgress.setVisibility(View.VISIBLE);
        }
        safeLoading = findViewById(R.id.safe_loading);
        webView = (WebView) findViewById(R.id.webview);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), Repository.APP_NAME + "Js");
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setDomStorageEnabled(true);
        String ua = webView.getSettings().getUserAgentString();
        if (ua != null && !ua.contains(" " + Repository.APP_NAME + "_client/")) {
//            int netStatus = Tools.currentNetworkStatus(getApplicationContext());
            ua += " " + Repository.APP_NAME + "_client_android/" + Repository.getVersion() + "/" + Repository.getVersionCode()
                    + "/";
        }
        webView.getSettings().setUserAgentString(ua);
        // webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.clearCache(false);
        DeviceInfo deviceInfo = AppContext.getInstance().getDeviceInfo();
        if (deviceInfo.isWapApn()) {
            Server proxy = deviceInfo.getApn().getProxyServer();
            webView.setHttpAuthUsernamePassword(proxy.getAddress(), proxy.getPort()
                    + "", "", "");
        } else {
            webView.setHttpAuthUsernamePassword("", "", "", "");
        }
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(client);
        webView.setDownloadListener(this);
        webView.setWebChromeClient(chromeClient);

        // 注册插件服务
//        if (jsBridgeService == null) {
//            jsBridgeService = new LDJSService(webView);
//        }
        jsBridgeHelper = new JsBridgeHelper(this, webView);
        jsBridgeHelper.registBroadCast();
    }

    protected void resetToolbar() {

        if (webView != null) {
            if (webView.canGoBack()) {
                btnBack.setEnabled(true);
            } else {
                btnBack.setEnabled(false);
            }
            if (webView.canGoForward()) {
                btnForward.setEnabled(true);
            } else {
                btnForward.setEnabled(false);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= 11) {
            webView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 11) {
            webView.onResume();
        }
        try {
            WebView.class.getMethod("enablePlatformNotifications").invoke(null);
        } catch (Exception ignore) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            WebView.class.getMethod("disablePlatformNotifications").invoke(null);
        } catch (Exception ignore) {
        }
    }

    @Override
    protected void onDestroy() {
        if (jsResult != null) {
            jsResult.cancel();
        }
        jsResult = null;
        super.onDestroy();
        if (myView != null && myCallBack != null) { // 当2.3播放视频未结束直接关闭activity时，由于没有调用onCustomViewHidden方法，会导致下次进入时2.3系统无法打开视频
            myCallBack.onCustomViewHidden();
        }
        if (jsBridgeHelper != null) {
            jsBridgeHelper.unRegistBroadCast();
        }
        myView = null;
        myCallBack = null;
    }

    protected boolean onUrlLoading(WebView view, String url) {
        return false;
    }

    protected void onUrlLoadingFinish(WebView view, String url) {

    }

    protected void onLocalJump(String url) {

    }

    public class MyChromeClient extends WebChromeClient {
        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {
            jsResult = result;
            CustomAlertDialog dialog = new CustomAlertDialog.Builder(
                    BaseWebViewActivity.this).setTitle("系统提示").setMessage(message)
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                            jsResult = null;
                        }
                    }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface di, int which) {
                            result.confirm();
                            jsResult = null;
                        }
                    }).createDialog();
            dialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) { // TODO
                    result.cancel();
                    jsResult = null;
                }
            });

            dialog.show();

            return true;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            jsResult = result;
            CustomAlertDialog dialog = new CustomAlertDialog.Builder(
                    BaseWebViewActivity.this).setTitle("系统提示").setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface di, int which) {
                            result.confirm();
                            jsResult = null;
                        }
                    }).createDialog();
            dialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO

                    result.cancel();
                    jsResult = null;
                }
            });

            dialog.show();
            return true;
            // return super.onJsAlert(view, url, message, result);

        }

        @Override
        public void onReceivedTitle(WebView paramWebView, String paramString) {
            super.onReceivedTitle(paramWebView, paramString);
            // iContext.setAddTitle(paramString);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (pageProgress != null) {
                pageProgress.setProgress(newProgress);
                pageProgress.setVisibility(newProgress == 100 ? View.GONE
                        : View.VISIBLE);
            }
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean dialog,
                                      boolean userGesture, Message resultMsg) {
            super.onCreateWindow(view, dialog, userGesture, resultMsg);
            return false;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (Build.VERSION.SDK_INT < 14) {
                try {
                    if (myView != null) {
                        callback.onCustomViewHidden();
                        return;
                    }
                    frameLayout.addView(view);
                    myView = view;
                    myCallBack = callback;
                } catch (Exception e) {
                }
            } else {
                super.onShowCustomView(view, callback);
            }
        }

        @Override
        public void onHideCustomView() {
            if (Build.VERSION.SDK_INT < 14) {
                try {
                    if (myView == null) {
                        return;
                    }
                    frameLayout.removeView(myView);
                    myView = null;
                    myCallBack.onCustomViewHidden();
                    myCallBack = null;
                } catch (Exception e) {
                }
            } else {
                super.onHideCustomView();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT < 14) {
            try {
                if (myView == null) {
                    super.onBackPressed();
                } else {
                    chromeClient.onHideCustomView();
                }
            } catch (Exception e) {
            }
        } else {
            super.onBackPressed();
        }
    }

    String backUri;

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (TextUtils.isEmpty(url) || url.startsWith("about:")) {
                return false;
            }
//            if (url.startsWith(LDJSService.LDJSBRIDGE_SCHEME)) {
//                // 处理JSBridge特定的Scheme
//                if (jsBridgeService != null) {
//                    jsBridgeService.handleURLFromWebview(url);
//                }
//                return true;
//            }
            if (!TextUtils.isEmpty(url)
                    && Uri.parse(url).getScheme() != null
                    && !Uri.parse(url).getScheme().toLowerCase(Locale.US)
                    .startsWith("http")) {
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                }
                return true;
            }

            return false;
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
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            showProgressBar();
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
            curHttpUrl = url;
        }

        private int retry = 0;

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

        @Override
        public void onReceivedHttpAuthRequest(WebView view,
                                              HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

    }

    @Override
    public void onDownloadStart(String url, String userAgent,
                                String contentDisposition, String mimetype, long contentLength) {
        if (!Tools.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    class InJavaScriptLocalObj {
        public void setData(String content) {
            htmlContent = content;
        }
    }
}
