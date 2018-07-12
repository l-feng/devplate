package com.example.core.router;


import android.net.Uri;

import com.example.core.listener.CompleteListener;


public interface UIRouter {

    public final static String HOST = "xingdai://";

    public final static String URI_INITIAL = "initial";

    public final static String URI_HOME = "home";
    public final static String URI_LOGIN = "login";
    public final static String URI_LOGOUT = "logout";
    public final static String URI_CHANGE_BASE_INFO = "changeBaseInfo";
    public final static String URI_BET = "bet";
    public final static String URI_CHARGE = "charge";
    public final static String URI_ARTICLE = "article";
    public final static String URI_PRODUCT_DETAIL = "productDetail";
    public final static String URI_WELFARE_LIST = "welfareList";


    public boolean openUri(String url, CompleteListener completeListener);

    public boolean openUri(String url);

    public boolean openUri(Uri url, CompleteListener completeListener);

    public boolean verifyUri(Uri url);

}
