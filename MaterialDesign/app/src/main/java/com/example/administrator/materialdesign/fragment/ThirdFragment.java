package com.example.administrator.materialdesign.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.materialdesign.R;

import com.example.administrator.materialdesign.constant.ActivityConstants;
import com.example.administrator.materialdesign.okhttp.CallBackUtil;
import com.example.administrator.materialdesign.okhttp.OkHttpUtil;


import okhttp3.Call;


/**
 * Created by Administrator on 2018/7/11 0011.
 */

public class ThirdFragment extends Fragment implements View.OnClickListener {
    private View layout;
    private int requestCode;
    private static final int REQUEST_CODE_AMOUNT = 5;
    private Button searchHttpButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout != null) {
            // 防止多次new出片段对象，造成图片错乱问题
            return layout;
        }
        requestCode = ActivityConstants.REQUEST_CODE_MAINACTIVITY++;
        layout = inflater.inflate(R.layout.fragment_third, null, false);
        initView();
        initDate();
        return layout;
    }

    private void initView() {
        searchHttpButton=layout.findViewById(R.id.http_search);
        searchHttpButton.setOnClickListener(this);

    }
    private void initDate(){

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.http_search){
            httpSearch();
        }
    }

    public void httpSearch(){
        String url = "https://www.baidu.com/";
        OkHttpUtil.okHttpGet(url, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {}

            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                Log.d("lunatic",response);
            }
        });

    }
}
