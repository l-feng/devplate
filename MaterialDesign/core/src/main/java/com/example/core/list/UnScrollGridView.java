package com.example.core.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class UnScrollGridView extends CommonGridView {
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public UnScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
