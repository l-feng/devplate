package com.example.core.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.core.CommonHeadBar;
import com.example.core.CustomAlertDialog;
import com.example.core.LoadingProgress;
import com.example.core.R;
import com.example.core.context.AppContext;
import com.example.core.router.UIRouter;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public abstract class BaseActivityWithoutTitleBar extends Activity {

    protected EventBroadcastReceiver broadcastReceiver;
    protected CommonHeadBar headBar;
    protected Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastReceiver = new EventBroadcastReceiver();
        broadcastReceiver.register();
//        PushAgent.getInstance(this).onAppStart();
//        AppContext.getInstance().checkLogin();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        headBar = (CommonHeadBar) findViewById(R.id.activity_head_bar);
        progressDialog = new ProgressDialog(this);
        progressDialog= LoadingProgress.createLoadingDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            broadcastReceiver.unregister();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void initTitleView(String title, boolean hasBack) {
        TextView titleView = (TextView) findViewById(R.id.head_bar_title);
        titleView.setText(title);
        View backView = findViewById(R.id.head_bar_back_btn);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void checkLogin() {
        if (!AppContext.getInstance().isLogin()) {
            AppContext.getInstance().getUiBus().openUri(UIRouter.HOST + UIRouter.URI_LOGIN, null);
        }
    }

    public void jumpToActivity(Class<?> toPage) {
        Intent intent = new Intent(this, toPage);
        startActivity(intent);
    }

    protected void jumpToActivity(Class<?> toPage, int requsetCode) {
        Intent intent = new Intent(this, toPage);
        startActivityForResult(intent, requsetCode);
    }

    public void onSuccess() {
        progressDialog.hide();
        finish();
    }

    public void onSuccess(String action) {
        onSuccess();
    }

    public void onError() {
        progressDialog.hide();
    }

    public void alertBack() {
        new CustomAlertDialog.Builder(this)
                .setMessage(R.string.alert_page_back_content)
                .setNegativeButton(R.string.alert_page_back_cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        })
                .setPositiveButton(R.string.alert_page_back_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).createDialog().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                handleBack();
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void handleBack() {

    }

    protected class EventBroadcastReceiver extends BroadcastReceiver {

        public void register() {
            IntentFilter intentFilter = new IntentFilter();
            for (String intent : getBroadcastReceiverIntents()) {
                intentFilter.addAction(intent);
            }
            registerReceiver(this, intentFilter);
        }

        public void unregister() {
            unregisterReceiver(this);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            onBroadcastReceive(context, intent, action);
        }
    }

    protected void onBroadcastReceive(Context context, Intent intent, String action) {

    }

    protected String[] getBroadcastReceiverIntents() {
        return new String[0];
    }

    protected void sendBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        sendBroadcast(intent);
    }
}
