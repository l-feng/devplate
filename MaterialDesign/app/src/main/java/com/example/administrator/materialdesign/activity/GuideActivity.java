package com.example.administrator.materialdesign.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.materialdesign.R;
import com.example.core.activity.BaseActivity;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

/**
 * Created by Administrator on 2018/7/11 0011.
 */

public class GuideActivity extends  Activity{

    private boolean isFromSplash;
    private  BGABanner mBackgroundBanner;
    private   BGABanner mForegroundBanner;
    private Button btnGuideCenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题，在setContentView之前设置
        setContentView(R.layout.activity_guide);
        initView();
        initDate();
    }

    private void initView() {
        mBackgroundBanner=findViewById(R.id.banner_guide_background);
        mForegroundBanner=findViewById(R.id.banner_guide_foreground);
        btnGuideCenter=findViewById(R.id.btn_guide_enter);
    }

    private void initDate() {
        isFromSplash = getIntent().getBooleanExtra("isFromSplash", false);
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.uoko_guide_background_1,
                R.drawable.uoko_guide_background_2,
                R.drawable.uoko_guide_background_3);

        mForegroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.uoko_guide_foreground_1,
                R.drawable.uoko_guide_foreground_2,
                R.drawable.uoko_guide_foreground_3);

        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                close();
            }
        });
    }

    private void close() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        ActivityCompat.finishAfterTransition(GuideActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //不允许返回
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
