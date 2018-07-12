package com.example.core.list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class GridViewWithoutScroll extends GridView {
    public GridViewWithoutScroll(Context context) {
        super(context);
    }

    public GridViewWithoutScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewWithoutScroll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }

}

