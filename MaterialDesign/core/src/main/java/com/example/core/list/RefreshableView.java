package com.example.core.list;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.core.R;
import com.example.core.utils.Tools;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class RefreshableView extends LinearLayout {
    private View refreshView;
    private ImageView refreshIndicator;
    private TextView refreshHint;
    private TextView refreshTimeView;
    private String downText;
    private String releaseText;
    private String refreshText;
    private String refreshTimeText;

    private int touchSlop;
    private int refreshViewHeight;
    private final static float MOVEMENT_FACTOR = (float) 0.5;

    private Scroller scroller;
    private RefreshListener refreshListener;
    private boolean isRefreshing;
    private boolean isRefreshEnabled;
    private Long refreshTime;
    private AnimationDrawable anim;


    public RefreshableView(Context context) {
        super(context);
        init();
    }

    public RefreshableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        scroller = new Scroller(getContext());
        refreshView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.refresh_item, this, false);
        setOrientation(VERTICAL);
        refreshIndicator = (ImageView) refreshView.findViewById(R.id.indicator);
        refreshIndicator.setBackgroundResource(R.drawable.refresh_down_icon);
        this.anim = (AnimationDrawable) refreshIndicator.getBackground();
        if (anim.isRunning()) {
            anim.stop();
        }
        anim.start();
        refreshHint = (TextView) refreshView.findViewById(R.id.refresh_hint);
        refreshTimeView = (TextView) refreshView.findViewById(R.id.refresh_time);
        refreshViewHeight = Tools.getPixelByDip(getContext(), 50);
        LayoutParams params = new LayoutParams(
                LayoutParams.FILL_PARENT, refreshViewHeight);
        params.topMargin = -refreshViewHeight;
        refreshView.setLayoutParams(params);
        params.gravity = Gravity.CENTER;
        addView(refreshView);
        // LayoutInflater.from(getContext()).inflate(R.layout.refreshable_listview,
        // this);
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        touchSlop = configuration.getScaledTouchSlop();
        downText = getContext().getString(R.string.refresh_down_text);
        releaseText = getContext().getString(R.string.refresh_release_text);
        refreshText = getContext().getString(R.string.refresh_text);
        scrollTo(0, 0);
    }

    public void finishRefresh() {
        scroller.abortAnimation();
        if (getScrollY() < 0) {
            scroller.startScroll(0, getScrollY(), 0, 0 - getScrollY(), 500);
            invalidate();

        }
        isRefreshing = false;
        this.refreshTime = System.currentTimeMillis();
    }

    public void setRefreshEnabled(boolean enabled) {
        isRefreshEnabled = enabled;
    }

    public void setRefreshTime(Long time) {
        this.refreshTimeText = getContext().getString(R.string.last_update_time) + "： ";
        this.refreshTimeText += Tools.getModifiedTimeText(getContext(), time);
        refreshTimeView.setText(refreshTimeText);
        refreshTimeView.setVisibility(View.VISIBLE);
    }

    public void setDownText(String text) {
        downText = text;
    }

    public void setReleaseText(String text) {
        releaseText = text;
    }

    public void setRefreshText(String text) {
        refreshText = text;
    }

    private void doMovement(int delta) {
        int sy = getScrollY();
        if (delta > 0) {
            // move down
            sy += -(delta * MOVEMENT_FACTOR);
            scrollTo(0, sy);
            // invalidate();
        } else if (delta < 0) {
            // move up
            if (sy < 0) {
                sy += -(delta);
                sy = Math.min(sy, 0);
                scrollTo(0, sy);
                invalidate();
            }
        }
        if (isRefreshing) {
            return;
        }
        if (getScrollY() < -refreshViewHeight) {
            refreshHint.setText(releaseText);
            this.anim.stop();
        } else {
            refreshHint.setText(downText);
        }
    }

    protected boolean canScroll() {
        if (getChildCount() > 1) {
            View child = getChildAt(1);

            if (child instanceof ScrollView) {
                int scrollY = ((ScrollView) child).getScrollY();
                if (scrollY != 0) {
                    return true;
                }
            } else if (child instanceof ListView) {
                ListView listView = (ListView) child;
                if (listView.getChildCount() > 0) {
                    View view = listView.getChildAt(0);
                    if (view.getTop() == listView.getListPaddingTop()
                            && listView.getFirstVisiblePosition() == 0) {
                        // the first child is on the top
                        return false;
                    } else {
                        return true;
                    }
                }
            } else if (child instanceof Refreshable) {
                return ((Refreshable) child).canFlickDown();
            }
        }
        return false;
    }

    public void refresh() {
        flingToRefresh();
        refreshTimeView.setVisibility(View.GONE);
        refreshHint.setText(refreshText);
        anim.start();
        if (refreshListener != null) {
            refreshListener.onRefresh(this);
        }
        isRefreshing = true;
    }

    private void flingToRefresh() {
        scroller.startScroll(0, getScrollY(), 0, -refreshViewHeight - getScrollY());
        invalidate();
    }

    private void fling() {
        int sy = getScrollY();
        if (sy < -refreshViewHeight) {
            if (isRefreshing) {
                flingToRefresh();
                return;
            }
            refresh();
        } else if (sy < 0) {
            scroller.startScroll(0, sy, 0, 0 - sy);

            invalidate();
        }
    }

    private int lastY = Integer.MIN_VALUE;
    private boolean isBeingDragged;
    private int startX, startY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isRefreshEnabled) {
            return super.onInterceptTouchEvent(ev);
        }

        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE)) {
            if (Math.abs(ev.getX() - startX) > Math.abs(ev.getY() - startY)) {
                return false;
            }

        }
        if ((action == MotionEvent.ACTION_MOVE) && (isBeingDragged)) {

            return true;
        }
        int y = (int) ev.getRawY();
        switch (action) {
            case MotionEvent.ACTION_MOVE:

                final int diff = Math.abs(y - lastY);
                if (diff > touchSlop) {
                    isBeingDragged = true;
                    if (y - lastY < 0) {
                        // move up
                        if (getScrollY() >= 0) {
                            isBeingDragged = false;
                        }
                    } else if (y - lastY > 0) {
                        // move down
                        if (canScroll()) {
                            isBeingDragged = false;
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_DOWN:
                requestFocus();
                lastY = y;
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                if (getScrollY() < 0) {
                    if (!scroller.isFinished()) {
                        scroller.abortAnimation();
                    }
                    isBeingDragged = true;
                } else {
                    isBeingDragged = false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
                break;
        }
        return isBeingDragged;
    }

    public boolean isBeingDragged() {
        if (getScrollY() == 0 || getScrollY() == refreshViewHeight) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean r = super.onTouchEvent(ev);
        if (!isRefreshEnabled) {
            return r;
        }
        int action = ev.getAction();
        int y = (int) ev.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (lastY != Integer.MIN_VALUE) {
                    int delta = y - lastY;
                    // move down should be harder than move up, so delta's threshold should
                    // not be the same
                    if ((delta >= 6 || delta < -2) && isBeingDragged) {
                        doMovement(delta);
                        if (moveListener != null) {
                            moveListener.onMove();
                        }
                        lastY = y;
                    }
                } else {
                    lastY = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                fling();
                break;
            }
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {

            scrollTo(0, scroller.getCurrY());
            if (moveListener != null) {
                moveListener.onMove();
            }

            invalidate();
        }
    }


    public void onScroll() {


    }


    public RefreshListener getRefreshListener() {
        return refreshListener;
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    private MoveListener moveListener;

    public MoveListener getMoveListener() {
        return moveListener;
    }

    public void setMoveListener(MoveListener moveListener) {
        this.moveListener = moveListener;
    }

    public interface RefreshListener {
        void onRefresh(RefreshableView view);
    }

    public interface MoveListener {
        void onMove();
    }

    public interface Refreshable {
        boolean canFlickDown();
    }
}







