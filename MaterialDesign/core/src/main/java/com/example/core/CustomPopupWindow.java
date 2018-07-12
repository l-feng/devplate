package com.example.core;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.core.utils.ViewUtils;


public class CustomPopupWindow implements DialogInterface {
    protected PopupWindow window;
    protected Context context;
    protected View contentView;
    protected WindowManager windowManager;
    protected int animationStyle;
    protected int offsetX = 0, offsetY = 0;
    private boolean outsideDismiss = true;

    private PopupWindowEventListener onEventListener;
    private PopupWindowDismissListener onDismissListener;

    public void setAnimStyle(int animStyle) {
        animationStyle = animStyle;
    }

    public CustomPopupWindow(Context context) {
        this.context = context;
        window = createPopupWindow(context, null);
        windowManager = (WindowManager) this.context
                .getSystemService(Context.WINDOW_SERVICE);
    }

    public CustomPopupWindow(View contentView) {
        this.contentView = contentView;
        context = contentView.getContext();
        window = createPopupWindow(context, contentView);
        windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
    }

    public void setHeight(int height) {
        if (window != null) {
            window.setHeight(height);
        }
    }

    public void setWidth(int width) {
        if (window != null) {
            window.setWidth(width);
        }
    }

    public void setOnEventListener(PopupWindowEventListener onEventListener) {
        this.onEventListener = onEventListener;
    }

    public void setOutsideDismiss(boolean outsideDismiss) {
        this.outsideDismiss = outsideDismiss;
        window.setFocusable(outsideDismiss);
        window.setOutsideTouchable(outsideDismiss);

    }

    public void setBackgroundDrawable(Drawable drawable) {
        if (window != null) {
            window.setBackgroundDrawable(drawable);
        }
    }

    private PopupWindow createPopupWindow(Context context, View contentView) {
        final PopupWindow popupWindow;
        if (contentView != null) {
            popupWindow = new PopupWindow(contentView);
        } else {
            popupWindow = new PopupWindow(context);
        }
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                boolean isOutside = false;
                Rect rc = ViewUtils.getViewBoundRect(view);
                if (rc.left > event.getRawX() || rc.right < event.getRawX()
                        || rc.top > event.getRawY() || rc.bottom < event.getRawY()) {
                    isOutside = true;
                }
                if (isOutside && outsideDismiss) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        dismiss();
                        if (onEventListener != null) {
                            onEventListener.onOutsideDismiss(CustomPopupWindow.this);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }
            }
        });
        return popupWindow;
    }

    public final void showAtLocation(View anchor, int gravity, int xoff, int yoff) {
        if (contentView == null) {
            return;
        }
        window.showAtLocation(anchor, gravity, xoff, yoff);
    }

    public final void showAsDropDown(View anchor, int xoff, int yoff) {
        if (contentView == null) {
            return;
        }
        window.showAsDropDown(anchor, xoff, yoff);
    }

    public void setItems(CharSequence[] items, OnClickListener listener) {
        final ListView listView = (ListView) LayoutInflater.from(context).inflate(
                R.layout.alert_dialog_listview, null);
        PopupListAdapter adapter;
        listView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        adapter = new PopupListAdapter(context, items, this);
        adapter.setOnClickListener(listener);
        listView.setAdapter(adapter);
        setContentView(listView);
    }

    public final void setContentView(View contentView) {
        this.contentView = contentView;
        window.setContentView(contentView);

    }

    @Override
    public void dismiss() {
        try {
            window.dismiss();
        } catch (Exception e) {
        }
    }

    @Override
    public void cancel() {
        dismiss();
    }

    public void setOnDismissListener(PopupWindowDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public static interface PopupWindowEventListener {
        public void onOutsideDismiss(CustomPopupWindow window);
    }

    public interface PopupWindowDismissListener {
        void onDismiss();
    }

    public boolean isShowing() {
        if (window != null && window.isShowing()) {
            return true;
        }
        return false;
    }
}
