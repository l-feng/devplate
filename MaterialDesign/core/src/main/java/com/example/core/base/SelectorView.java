package com.example.core.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.core.R;
import com.example.core.utils.StringUtils;
import com.example.core.utils.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class SelectorView extends ViewGroup {
    private static final int DEFAULT_VISIBLE_COUNT = 5;

    private VelocityTracker velocityTracker;
    private boolean isBeingDragged;
    private Scroller scroller;

    private int minimumVelocity;
    private int maximumVelocity;

    private int visibleCount = DEFAULT_VISIBLE_COUNT;
    private int itemHeight;
    private int defaultItemHeight;
    private int defaultItemWidth;

    private float startMotionY;
    private int lastTopIndex;

    private float movementOffset;
    private SelectionChangedListener selectionListener;

    private View markView;
    private View maskTop;
    private View maskBottom;

    protected List<CharSequence> items = new ArrayList<CharSequence>();
    protected List<TextView> contentViews = new ArrayList<TextView>();

    private int selection = -1;
    private int textSize = 0;
    private ColorStateList textColor;

    private float defaultTextSize = 20;

    private boolean loop = true;
    private float scaleNormal = (float) 0.8;
    private float scaleForce = (float) 0.5;

    public SelectorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SelectorView, defStyle, 0);
        if (a != null) {
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.SelectorView_visibleCount) {
                    visibleCount = a.getInteger(attr, DEFAULT_VISIBLE_COUNT);
                } else if (attr == R.styleable.SelectorView_textSize) {
                    textSize = a.getDimensionPixelSize(attr, textSize);
                } else if (attr == R.styleable.SelectorView_textColor) {
                    textColor = a.getColorStateList(attr);
                } else if (attr == R.styleable.SelectorView_loop) {
                    loop = a.getBoolean(attr, loop);
                }
            }
        }
        a.recycle();
        init();
    }

    public SelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.selectorViewStyle);
    }

    public SelectorView(Context context) {
        super(context);
        init();
    }

    public List<CharSequence> getItems() {
        return items;
    }

    public void setItems(List<CharSequence> items) {
        if (items != null) {
            this.items = items;
            initItemViews();
            invalidate();
        }
    }

    public void setItems(CharSequence[] items) {
        if (items != null && items.length > 0) {
            this.items.clear();
            for (CharSequence item : items) {
                this.items.add(item);
            }
            initItemViews();
            invalidate();
        }
    }

    public void setVisibleCount(int count) {
        visibleCount = count;
        initItemViews();
        invalidate();
    }

    public SelectionChangedListener getSelectionListener() {
        return selectionListener;
    }

    public void setSelectionListener(SelectionChangedListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @TargetApi(Build.VERSION_CODES.DONUT)
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void init() {
        scroller = new Scroller(getContext());
        setWillNotDraw(false);
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        minimumVelocity = configuration.getScaledMinimumFlingVelocity();
        maximumVelocity = configuration.getScaledMaximumFlingVelocity();
        defaultItemHeight = Tools.getPixelByDip(getContext(), 60);
        defaultItemWidth = Tools.getPixelByDip(getContext(), 30);
        movementOffset = 0;
        lastTopIndex = 0;
        ImageView markView = new ImageView(getContext());
        markView.setImageDrawable(getResources().getDrawable(
                R.drawable.border_select_view_item));
        markView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.markView = markView;
//        maskTop = new View(getContext());
//        maskTop.setBackgroundColor(getResources().getColor(R.color.ground_color));
//        maskBottom = new View(getContext());
//        maskBottom.setBackgroundColor(getResources().getColor(R.color.ground_color));
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
        if (items != null && items.size() > 0) {
            initItemViews();
            invalidate();
        }
    }

    private void initItemViews() {
        contentViews.clear();
        removeAllViews();
        if (items.size() < visibleCount) {
            loop = false;
        }
        int count = visibleCount + 1;
        for (int i = 0; i < count; i++) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTextSize(defaultTextSize);
            if (textSize > 0) {
                textView.setTextSize(textSize);
            }
            if (textColor != null) {
                textView.setTextColor(textColor);
            } else {
                textView.setTextColor(getContext().getResources().getColor(
                        R.color.blue));
            }
            contentViews.add(textView);
            addView(textView);
        }
        if (markView != null) {
            addView(markView);
        }
        if (items.size() > 0) {
            setSelection(0);
        }

//        addView(maskTop);
//        addView(maskBottom);
    }

    public View getMarkView() {
        return markView;
    }

    public void setMarkView(View markView) {
        if (markView != null) {
            if (this.markView != null) {
                removeView(this.markView);
            }
            this.markView = markView;
            addView(markView);
        }
    }

    public void setMarkRes(int resId) {
        ImageView markView = new ImageView(getContext());
        markView.setImageResource(resId);
        markView.setScaleType(ImageView.ScaleType.FIT_XY);
        setMarkView(markView);
    }

    private int getVisibleCount() {
        return visibleCount;
    }

    public void setSelection(int selection) {
        if (selection >= 0 && selection < items.size()) {
            resetLayout();
            changeSelection(selection);
            int count = getVisibleCount();
            lastTopIndex = selection - count / 2;
            if (loop) {
                lastTopIndex = (lastTopIndex + items.size()) % items.size();
            }
            requestLayout();
            invalidate();
        }
    }

    private void resetLayout() {
        movementOffset = 0;
        scroller.forceFinished(true);
        int offset = 0;
        for (TextView childView : contentViews) {
            int height = childView.getBottom() - childView.getTop();
            childView.layout(childView.getLeft(), offset, childView.getRight(),
                    offset + height);
            offset += height;
        }
    }

    public int getSelection() {
        return selection;
    }

    private int getSelectionInternal() {
        if (items.size() == 0) {
            return -1; // this case should not be reached
        }
        int visible = getVisibleCount();
        int s = lastTopIndex + visible / 2;
        if (loop) {
            while (s < 0) {
                s += items.size();
            }
            s %= items.size();
        }
        return s;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        if ((action == MotionEvent.ACTION_MOVE) && (isBeingDragged)) {
            return true;
        }

        if (!canScroll()) {
            isBeingDragged = false;
            return false;
        }

        final float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                isBeingDragged = true;
                break;

            case MotionEvent.ACTION_DOWN:
                startMotionY = y;
                isBeingDragged = !scroller.isFinished();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
                break;
        }
        return isBeingDragged;
    }

    private void adjustOffset() {
        if (items.size() > 0) {
            movementOffset = 0;
            TextView topView = contentViews.get(0);
            int delta = 0;
            int selection = getSelectionInternal();
            int offset = -topView.getTop();
            if (offset >= itemHeight / 2 && offset < itemHeight) {
                delta = offset - itemHeight;
                selection++;
            } else if (offset > 0 && offset < itemHeight / 2) {
                delta = offset;
            }
            if (selection < 0) {
                delta += selection * itemHeight;
            }
            if (selection >= items.size()) {
                delta += (selection - items.size() + 1) * itemHeight;
            }
            if (delta != 0) {
                int startY = topView.getTop();
                startMotionY = startY;
                scroller.startScroll(0, startY, 0, delta);
                invalidate();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canScroll()) {
            return false;
        }
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        final int action = event.getAction();
        final float y = event.getRawY();
        int selection = getSelectionInternal();
        final boolean isOverflow = selection < 0 || selection >= items.size();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                startMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = y - startMotionY;
                isBeingDragged = true;
                if (isOverflow) {
                    deltaY *= scaleForce;
                } else {
                    deltaY *= scaleNormal;
                }
                if (Math.abs(deltaY) >= 1) {
                    startMotionY = y;
                    movementOffset += deltaY;
                    requestLayout();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
                float initialVelocity = velocityTracker.getYVelocity();
                startMotionY = y;
                isBeingDragged = false;
                if ((Math.abs(initialVelocity) > minimumVelocity) && !isOverflow) {
                    fling((int) (initialVelocity));
                } else {
                    adjustOffset();
                }
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
        }
        return true;
    }

    private void fling(int velocityY) {
        scroller.fling(0, (int) startMotionY, 0, velocityY, 0, 0,
                -Integer.MAX_VALUE, Integer.MAX_VALUE);
        requestLayout();
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            final float deltaY = scroller.getCurrY() - startMotionY;
            startMotionY = scroller.getCurrY();
            movementOffset += deltaY;
            requestLayout();
            invalidate();
        } else {
            if (!isBeingDragged) {
                adjustOffset();
            }
        }
    }

    private void layoutChildrenViews() {
        int count = items.size();
        if (count == 0) {
            return;
        }
        int layoutTop;
        TextView topView = contentViews.get(0);
        layoutTop = topView.getTop() + (int) movementOffset;
        if (movementOffset >= 0) {
            // move down
            if (layoutTop > 0) {
                lastTopIndex = lastTopIndex
                        - (int) Math.ceil((layoutTop) / (float) itemHeight);
            }
        } else {
            // move up
            if (layoutTop + itemHeight <= 0) {
                lastTopIndex = lastTopIndex
                        + 1
                        + (int) (Math.floor((0 - (layoutTop + itemHeight))
                        / (float) itemHeight));
            }
        }
        if (loop) {
            while (lastTopIndex < 0) {
                lastTopIndex += count;
            }
            lastTopIndex %= count;
        }
        while (layoutTop > 0) {
            layoutTop -= itemHeight;
        }
        while (layoutTop <= -itemHeight) {
            layoutTop += itemHeight;
        }
        int topIndex = lastTopIndex;
        int left = 0;
        int right = left + getMeasuredWidth();
        int offset = layoutTop;
        for (TextView childView : contentViews) {
            childView.layout(left, offset, right, offset + itemHeight);
            childView.setText(" ");
            if (loop) {
                topIndex = topIndex % count;
            }
            if (topIndex >= 0 && topIndex < items.size()) {
                childView.setText(items.get(topIndex));
            }
            childView.setGravity(Gravity.CENTER);
            offset += itemHeight;
            topIndex++;
        }
        int top = getVisibleCount() / 2 * itemHeight;
        if (markView != null) {
            markView.layout(left, top, right, top + itemHeight);
        }
        if (maskTop != null && maskBottom != null) {
            maskTop.layout(left, 0, right, top);
            maskBottom.layout(left, top + itemHeight, right, getMeasuredHeight());
        }
        int selection = getSelectionInternal();
        changeSelection(selection);
        boolean isOverflow = false;
        if (selection < 0 && movementOffset > 0 || selection >= items.size()
                && movementOffset < 0) {
            isOverflow = true;
        }
        movementOffset = 0;
        if (isOverflow && !isBeingDragged) {
            scroller.forceFinished(true);
            adjustOffset();
        }
    }

    private void changeSelection(int newSelection) {
        if (newSelection >= 0 && newSelection < items.size()) {
            if (selection != newSelection) {
                changeSelectionColor();
                selection = newSelection;
                if (selectionListener != null) {
                    selectionListener.onSelectionChanged(this, selection);
                }
            }
        }
    }

    private void changeSelectionColor() {
        int select = visibleCount / 2;
        for (TextView textView : contentViews) {
            textView.setTextColor(getResources().getColor(R.color.yellow));
        }
        if (contentViews.size() > select) {
            contentViews.get(select).setTextColor(getResources().getColor(R.color.font_text_color));
        }
    }

    private TextView getTextView(int selection) {
        int index = 0;
        for (TextView textView : contentViews) {
            if (StringUtils.isNotBlank(textView.getText().toString())) {
                if (index++ == selection) {
                    return textView;
                }
            }
        }
        return null;
    }

    private boolean canScroll() {
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildrenViews();
    }

    private void measureItemChild(View child, int parentWidthMeasureSpec,
                                  int parentHeightMeasureSpec) {
        final LayoutParams lp = child.getLayoutParams();
        int padding = getPaddingLeft() + getPaddingRight();
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                padding, lp.width);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(itemHeight,
                MeasureSpec.EXACTLY);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int visibleCount = getVisibleCount();
        if (visibleCount == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            itemHeight = defaultItemHeight;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            itemHeight = Math.min(
                    (getMeasuredHeight() - getPaddingTop() - getPaddingBottom())
                            / visibleCount, defaultItemHeight);
        } else if (heightMode == MeasureSpec.EXACTLY) {
            itemHeight = (getMeasuredHeight() - getPaddingTop() - getPaddingBottom())
                    / visibleCount;
        }
        if (itemHeight <= 0) {
            itemHeight = defaultItemHeight;
        }
        int width = 0;
        int height = (visibleCount * itemHeight) + getPaddingTop()
                + getPaddingBottom();
        for (TextView itemView : contentViews) {
            measureItemChild(itemView, widthMeasureSpec, heightMeasureSpec);
            width = Math.max(width, itemView.getMeasuredWidth());
        }
        // final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // if(widthMode != MeasureSpec.AT_MOST)
        {
            width = Math.max(Math.max(getMeasuredWidth(), defaultItemWidth), width);
        }
        setMeasuredDimension(width, height);
    }

    public interface SelectionChangedListener {
        void onSelectionChanged(SelectorView view, int selection);
    }

    public void npScrollToNumber(int what) {
        int speed = new Random().nextInt(10) * 5000;
        // scroller.setFinalY((what-getSelection())*itemHeight);
        // scroller.fling(0, scroller.getCurrX(), 0, speed, 0, 0,
        // -Integer.MAX_VALUE,
        // Integer.MAX_VALUE);
        fling(speed);
        requestLayout();
        invalidate();
    }

    public void setTextColor(ColorStateList textColor) {
        this.textColor = textColor;
    }
}

