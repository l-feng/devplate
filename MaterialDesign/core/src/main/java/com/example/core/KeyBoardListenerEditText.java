package com.example.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.EditText;


public class KeyBoardListenerEditText extends EditText {

    public KeyBoardListenerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public static final byte KEYBOARD_STATE_SHOW = -2;
    public static final byte KEYBOARD_STATE_HIDE = -1;
    private OnKybdsChangeListener mListener;
    private double showThreshold = 0.55;
    private double hideThreshold = 0.65;

    public void setOnkbdStateListener(OnKybdsChangeListener listener) {
        mListener = listener;
    }

    public void setShowThreshold(double showThreshold) {
        this.showThreshold = showThreshold;
    }

    public void setHideThreshold(double hideThreshold) {
        this.hideThreshold = hideThreshold;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int[] location = new int[2];
        getLocationOnScreen(location);
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int screenHeight = wm.getDefaultDisplay().getHeight();

        if (location[1] != 0 || screenHeight != 0) {
            if (location[1] > screenHeight * hideThreshold) {
                if (mListener != null) {
                    mListener.onKeyBoardStateChange(KEYBOARD_STATE_HIDE);
                }
            }
        }
        if (location[1] < screenHeight * showThreshold) {
            if (mListener != null) {
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_SHOW);
            }
        }
        super.onLayout(changed, l, t, r, b);
    }

    public interface OnKybdsChangeListener {
        void onKeyBoardStateChange(int state);
    }
}
