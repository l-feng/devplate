package com.example.administrator.materialdesign.activity.picture;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.materialdesign.R;
import com.example.administrator.materialdesign.utils.ActivityUtils;
import com.example.administrator.materialdesign.utils.view.ImmersionMode;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class PictureActivity extends AppCompatActivity {

    public static final String PICTURE_NAME = "picture_name";

    public static final String PICTURE_ID = "picture_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionMode.setStatusBarTransparent(this);
        setContentView(R.layout.activity_picture);
        Intent intent = getIntent();
        String pictureNmae = intent.getStringExtra(PICTURE_NAME);
        int pictureId = intent.getIntExtra(PICTURE_ID, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView pictureImageView = (ImageView) findViewById(R.id.image_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        collapsingToolbar.setTitle(pictureNmae);
        Glide.with(this).load(pictureId).into(pictureImageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}