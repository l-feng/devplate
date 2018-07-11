package com.example.administrator.materialdesign.adapter.recyclelist;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.materialdesign.R;

import java.util.List;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class RecycleViewGridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RecycleViewGridAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_tv, item);
        helper.addOnClickListener(R.id.item_tv);
    }
}