package com.example.core.activity;

import android.os.Bundle;

import com.example.core.R;
import com.example.core.utils.ImmersionMode;
import com.example.core.utils.ViewUtils;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public abstract class BaseActivity extends BaseActivityWithoutTitleBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionMode.setStatusBarTransparent(this);
        setTitleBarColor(R.color.green);
    }

    protected void setHeadTitle(String title) {
        headBar.setHeadTitle(title);
    }

    protected void setTitleBarColor(int color) {
        ViewUtils.setTitleBarColor(this,color);
    }

}