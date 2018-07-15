package com.example.administrator.materialdesign.controller.base;

/**
 * Created by Administrator on 2016/1/18 0018.
 */
public class HttpConstants {
    /**
     * Host
     */
    public static final String HOST = "https://www.xingdk.com1";

    /**
     * Test
     */
   // public static final String HOST = "http://test.banmabaoxian.cn";

    /**
     * confirm
     */

    // public static final String HOST = "http://con.xingdk.com";
    /**
     * Dev
     */
   //  public static final String HOST = "http://192.168.1.125";

    public static final String API_HOST = HOST + "/i";

    /**
     * APP
     */
    public static final String INITIAL = API_HOST + "/common/initial";
    public static final String PARAMETER_APP_ID = "appId";
    public static final String PARAMETER_PARAMS = "params";

    public static final String CHECK_VERSION = API_HOST + "/common/checkAppUpdater";

    public static final String COMMIT_FEEDBACK = API_HOST + "/common/feedback/commit";
    /**
     * Index
     */
    public static final String SPLASH = API_HOST + "/index/splash";
    public static final String HOME = API_HOST + "/index/home";

    /**
     * Product
     */
    public static final String PRODUCT_DETAIL = API_HOST + "/product/detail";
    public static final String PRODUCT_ADDCLICK = API_HOST + "/product/detail/addClickCount";
    public static final String PRODUCT_PROMOTIONAD = API_HOST + "/product/getPromotionAd";

    /**
     * User
     */
    public static final String SEND_SMS = API_HOST + "/sms/send";

    public static final String LOGIN = API_HOST + "/user/login";
    public static final String WEIXIN_LOGIN = API_HOST + "/user/weixinLogin";
    public static final String LOGIN_BY_TOKEN = API_HOST + "/user/tokenLogin";
    public static final String GET_TEMP_TOKEN = API_HOST + "/user/getTempToken";
    public static final String REGISTER = API_HOST + "/user/register";
    public static final String FORGET_PASSWORD = API_HOST + "/user/password/forget";
    public static final String CHANGE_PASSWORD = API_HOST + "/user/password/change";

    public static final String UPLOAD_AVATAR = API_HOST + "/user/avatar";
    public static final String COMMIT_BASE_INFO = API_HOST + "/user/commitInfo";

    public static final String SMSLOGIN = API_HOST + "/user/smsLogin";


    /**
     * Apply
     */
    public static final String APPLY_LIST = API_HOST + "/apply/list";
    public static final String APPLY_COMMIT = API_HOST + "/apply/commit";
    public static final String APPLY_LUCKLIST = API_HOST + "/apply/getLuckList";

    /**
     * WebView
     */
    public static final String ABOUT_URL = HOST + "/m/aboutr";
    public static final String AGREEMENT_URL = HOST + "/m/agreement";

    /**
     * article
     */
    public static final String ARTICLE_LIST = HOST + "/article/list";
    public static final String ARTICLE = HOST + "/m/article/detail?articleId=";


    /**
     * welfare
     */
    public static final String WELFARE_INDEX = HOST + "/welfare/index";
    public static final String WELFARE_LIST = HOST + "/welfare/list";

    /**
     * statistics
     */
    public static final String STATISTICS = HOST + "/statistics/app";

    /**
     * comments
     */
    public static final String COMMENT_PRE = API_HOST + "/comment/pre";
    public static final String COMMENT_LIST = API_HOST + "/comment/list";
    public static final String COMMENT_COMMIT = API_HOST + "/comment/commit";



    /**
     * comments
     */
    public static final String INTELLIGENT_MATCH = API_HOST + "/filter/constants";
    public static final String INTELLIGENT_MATCH_LIST = API_HOST + "/product/list";



    /**
     * loan
     */
    public static final String LOAN_FiLTER = API_HOST + "/filter/condition";
    public static final String LOAN_HOME = API_HOST + "/product/list";



}
