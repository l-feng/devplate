package com.example.administrator.materialdesign.constant;

import android.view.View;
import android.view.ViewParent;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class ActivityConstants {
    public static final String APP_NAME = "plate";
    public static int REQUEST_CODE_MAINACTIVITY = 101;

    public static final String VARIABLE_SELECT_CITY = "variable_select_city";
    public static final String VARIABLE_SELECT_EDUCATION = "variable_select_education";
    public static final String VARIABLE_SELECT_INDUSTRY = "variable_select_industry";
    public static final String VARIABLE_SELECT_CATEGORY = "variable_select_category";

    public static final String VARIABLE_SELECTOR_KEY = "variable_selector";
    public static final String VARIABLE_SELECTOR_VALUE = "variable_selector_value";
    public static final String VARIABLE_SELECTOR_CONTENT = "variable_selector_content";

    public static final String VARIABLE_JOB_ID = "variable_job_id";
    public static final String VARIABLE_COMPANY_ID = "variable_company_id";
    public static final String VARIABLE_COMPANY_INFO = "variable_company_info";
    public static final String VARIABLE_RESUME_ITEM_ID = "variable_resume_item_id";
    public static final String VARIABLE_REGISTER_MOBILE = "variable_register_mobile";
    public static final String VARIABLE_FORGET_PASSWORD_MOBILE = "variable_forget_password_mobile";

    public static void transferToParentHandelClick(View v) {
        ViewParent viewParent = v.getParent();
        if (viewParent instanceof View) {
            View view = (View) viewParent;
            boolean clickable = view.hasOnClickListeners();
            if (clickable) {
                view.performClick();
            } else {
                transferToParentHandelClick(view);
            }
        }
    }
}
