package com.example.administrator.materialdesign.fragment;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.materialdesign.R;
import com.example.administrator.materialdesign.adapter.recyclelist.RecycleViewAdapter1;
import com.example.administrator.materialdesign.adapter.recyclelist.RecycleViewGridAdapter;
import com.example.administrator.materialdesign.adapter.tab.TabFragmentAdapter;
import com.example.administrator.materialdesign.constant.ActivityConstants;
import com.example.administrator.materialdesign.model.Picture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private View layout;
    private int requestCode;
    private static final int REQUEST_CODE_AMOUNT = 5;
    private ImageView imageView;
    private ViewPager viewPager;
    private TabLayout tablayout;
    private RecyclerView recyclerView;
    private RecycleViewAdapter1 adapter;
    private List<Picture> fruitList = new ArrayList<>();

    private List<Fragment> mFragmentArrays = new ArrayList<>();
    private List<String> mTabs = new ArrayList<>();
    private View view;
    private  SwipeRefreshLayout swipeRefreshLayout;


    private Picture[] pictures = {new Picture("大吉大利今晚吃鸡", R.mipmap.aa), new Picture("大吉大利今晚吃鸡", R.mipmap.bb),
            new Picture("大吉大利今晚吃鸡", R.mipmap.cc), new Picture("大吉大利今晚吃鸡", R.mipmap.dd),
            new Picture("大吉大利今晚吃鸡", R.mipmap.ee), new Picture("大吉大利今晚吃鸡 ", R.mipmap.ff),
            new Picture("大吉大利今晚吃鸡", R.mipmap.xx), new Picture("大吉大利今晚吃鸡", R.mipmap.yy),
            new Picture("大吉大利今晚吃鸡", R.mipmap.zz)};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout != null) {
            // 防止多次new出片段对象，造成图片错乱问题
            return layout;
        }
        requestCode = ActivityConstants.REQUEST_CODE_MAINACTIVITY++;
        layout = inflater.inflate(R.layout.fragment_home, null, false);
        initDatas();
        initView();
        initDate();
        return layout;
    }



    private void initView() {
        imageView=layout.findViewById(R.id.iv_fenlei);
        tablayout=layout.findViewById(R.id.tablayout);
        viewPager=layout.findViewById(R.id.tab_viewpager);
        recyclerView=layout.findViewById(R.id.recycler_view);
        swipeRefreshLayout=layout.findViewById(R.id.swipe_refresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecycleViewAdapter1(fruitList);
        recyclerView.setAdapter(adapter);

        imageView.setOnClickListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDatas();
            }
        });
    }



    private void refreshDatas() {

        //网络获取数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);

    }

    private void initDate() {
        tablayout.removeAllTabs();
        viewPager.removeAllViews();
        if (mFragmentArrays != null) {
            mFragmentArrays.clear();
            mTabs.clear();
        }
        mTabs.add("推荐");
        mTabs.add("手机");
        mTabs.add("智能");
        mTabs.add("电视");
        mTabs.add("电脑");
        mTabs.add("双摄");
        mTabs.add("生活周边");
        mTabs.add("盒子");
        mTabs.add("推荐");
        mTabs.add("手机");
        mTabs.add("智能");
        mTabs.add("电视");
        mTabs.add("电脑");
        mTabs.add("双摄");
        mTabs.add("生活周边");
        mTabs.add("盒子");


        for (int i = 0; i < mTabs.size(); i++) {

            Fragment fragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            fragment.setArguments(bundle);
            mFragmentArrays.add(fragment);
        }




        viewPager.setAdapter(new TabFragmentAdapter(getFragmentManager(), mFragmentArrays, mTabs));
        tablayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fenlei:
                startPopuwindows(view);
                break;
        }
    }

    private void startPopuwindows(View view1) {
        View view=LayoutInflater.from(getActivity()).inflate(R.layout.layout_main_popuwindows,null);
        RecyclerView recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        RecycleViewGridAdapter gridAdapter=new RecycleViewGridAdapter(R.layout.item_gride_fenlei,mTabs);
        recyclerView.setAdapter(gridAdapter);

        final PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.showAsDropDown(view1);

        gridAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(),"点击了"+mTabs.get(position),Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(position);
                popupWindow.dismiss();
            }
        });
        gridAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                viewPager.setCurrentItem(position);
                popupWindow.dismiss();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }




    private void initDatas() {

        for (int i = 0; i < pictures.length; i++) {

            fruitList.add(pictures[i]);

        }
    }

}

