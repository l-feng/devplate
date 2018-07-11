package com.example.administrator.materialdesign.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.materialdesign.R;
import com.example.administrator.materialdesign.adapter.recyclelist.MyStickyListHeadersAdapter;
import com.example.administrator.materialdesign.constant.ActivityConstants;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class SecondFragment extends Fragment {
    private View layout;
    private int requestCode;
    private static final int REQUEST_CODE_AMOUNT = 5;
    private StickyListHeadersListView stickyListHeadersListView;
    private StickyListHeadersAdapter stickyListHeadersAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout != null) {
            // 防止多次new出片段对象，造成图片错乱问题
            return layout;
        }
        requestCode = ActivityConstants.REQUEST_CODE_MAINACTIVITY++;
        layout = inflater.inflate(R.layout.fragment_second, null, false);
        initView();
        initDate();
        return layout;
    }

    private void initView() {
        stickyListHeadersListView=layout.findViewById(R.id.stickyListHeadersListView);
        stickyListHeadersAdapter=new MyStickyListHeadersAdapter(getActivity(), Country());
        stickyListHeadersListView.setAdapter(stickyListHeadersAdapter);

    }
    private void initDate(){

    }


    private List Country() {
        List countries = new ArrayList();
        countries.add("Afghanistan");
        countries.add("Albania");
        countries.add("Bahrain");
        countries.add("Bangladesh");
        countries.add("Cambodia");
        countries.add("Cameroon");
        countries.add("Denmark");
        countries.add("Djibouti");
        countries.add("East Timor");
        countries.add("Ecuador");
        countries.add("Fiji");
        countries.add("Finland");
        countries.add("Gabon");
        countries.add("Georgia");
        countries.add("Haiti");
        countries.add("Holy See");
        countries.add("Iceland");
        countries.add("India");
        countries.add("Jamaica");
        countries.add("Japan");
        countries.add("Kazakhstan");
        countries.add("Kenya");
        countries.add("Laos");
        countries.add("Latvia");
        countries.add("Macau");
        countries.add("Macedonia");
        countries.add("Namibia");
        countries.add("Nauru");
        countries.add("Oman");
        countries.add("Pakistan");
        countries.add("Palau");
        countries.add("Qatar");
        countries.add("Romania");
        countries.add("Russia");
        countries.add("Saint Kitts and Nevis");
        countries.add("Saint Lucia");
        countries.add("Taiwan");
        countries.add("Tajikistan");
        countries.add("Uganda");
        countries.add("Ukraine");
        countries.add("Vanuatu");
        countries.add("Venezuela");
        countries.add("Yemen");
        countries.add("Zambia");
        countries.add("Zimbabwe");
        return countries;
    }
}
