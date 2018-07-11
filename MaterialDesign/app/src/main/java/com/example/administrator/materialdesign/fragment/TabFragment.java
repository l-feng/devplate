package com.example.administrator.materialdesign.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.materialdesign.R;
import com.example.administrator.materialdesign.adapter.picture.PictureAdapter;
import com.example.administrator.materialdesign.adapter.recyclelist.RecycleViewAdapter;
import com.example.administrator.materialdesign.constant.ActivityConstants;
import com.example.administrator.materialdesign.model.Picture;
import com.example.administrator.materialdesign.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class TabFragment extends Fragment {
    private RecyclerView recyclerView;
    private View layout;
    private int requestCode;
    private static final int REQUEST_CODE_AMOUNT = 5;
    int mPosition;
    private RecycleViewAdapter mAdapter;
    private Banner mBanner;
    private List<String> mdata = new ArrayList<>();
    private List<String> imageUrl = new ArrayList<>();
    private List<Picture> fruitList = new ArrayList<>();
    private PictureAdapter adapter;

    private Picture[] pictures = {new Picture("大吉大利今晚吃鸡", R.mipmap.aa), new Picture("大吉大利今晚吃鸡", R.mipmap.bb),
            new Picture("大吉大利今晚吃鸡", R.mipmap.cc), new Picture("大吉大利今晚吃鸡", R.mipmap.dd),
            new Picture("大吉大利今晚吃鸡", R.mipmap.ee), new Picture("大吉大利今晚吃鸡 ", R.mipmap.ff),
            new Picture("大吉大利今晚吃鸡", R.mipmap.xx), new Picture("大吉大利今晚吃鸡", R.mipmap.yy),
            new Picture("大吉大利今晚吃鸡", R.mipmap.zz)};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layout != null) {
            // 防止多次new出片段对象，造成图片错乱问题
            return layout;
        }
        requestCode = ActivityConstants.REQUEST_CODE_MAINACTIVITY++;
        layout = inflater.inflate(R.layout.fragment_tab, null, false);
        mPosition = getArguments().getInt("position");

        initView();
        initDate();
        return layout;
    }

    private void initView() {
        recyclerView = layout.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecycleViewAdapter(R.layout.home_item_view, mdata);

        View top = getLayoutInflater().inflate(R.layout.layout_banner, (ViewGroup) recyclerView.getParent(), false);
        mBanner = top.findViewById(R.id.banner);
        mAdapter.addHeaderView(top);
        recyclerView.setAdapter(mAdapter);
    }

    private void initDate() {
        for (int i = 0; i < 20; i++) {
            mdata.add("lunatic" + mPosition + "_" + i);
        }
        mAdapter.setNewData(mdata);
        if (mPosition == 0) {
            imageUrl.clear();
            imageUrl.add("http://img0.imgtn.bdimg.com/it/u=4240594450,1970408670&fm=27&gp=0.jpg");
            imageUrl.add("http://img1.imgtn.bdimg.com/it/u=1650757138,35349171&fm=27&gp=0.jpg");
            imageUrl.add("http://img2.imgtn.bdimg.com/it/u=1896350020,485179954&fm=27&gp=0.jpg");
            initBanner(imageUrl);
        } else {
            mBanner.setVisibility(View.GONE);
        }
        for (int i = 0; i < pictures.length; i++) {

            fruitList.add(pictures[i]);

        }
    }


    private void initBanner(List<String> imageUrl) {
        mBanner.setImages(imageUrl)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(3000)
                .start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }
}

