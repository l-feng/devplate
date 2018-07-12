package com.example.core.list;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class CommonGridView extends GridViewWithHeaderAndFooter {

    public interface OnGridScroll2TopListener{
        void scroll2Top();
    }

    private OnGridScroll2TopListener listener;

    public void setOnGridScroll2TopListener(OnGridScroll2TopListener listener) {
        this.listener = listener;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                   int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (listener != null) {
            listener.scroll2Top();
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.e("MyListView", " t: " + t
                + " oldt: " + oldt + ": " +
                getScrollY());
    }

    public CommonGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
