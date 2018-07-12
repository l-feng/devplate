package com.example.core.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.R;


/**
 *
 */
public class AlertMessage {

    public static void show(Context context, String message) {
        show(context, message, false);
    }

    public static void show(Context context, int resId) {
        if (context == null) {
            return;
        }
        show(context, context.getString(resId), false);
    }

    public static void show(Context context, String message, boolean isLong) {
        try {
            if (Tools.isEmpty(message)) {
                return;
            }
            ViewUtils.showToast(message);
        } catch (Exception e) {
        }
    }

    public static void show(Context context, int resId, boolean isLong) {
        if (context == null) {
            return;
        }
        show(context, context.getString(resId), isLong);
    }

    public static void show(Context context, View view, boolean isLong) {
        try {
            Toast toast = new Toast(context);
            toast.setView(view);
            toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0); // 居中显示
            toast.show();
        } catch (Exception e) {
        }
    }

    public static void show(Context context, View view, boolean isLong,
                            boolean isCenter) {
        try {
            Toast toast = new Toast(context);
            toast.setView(view);
            toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            if (isCenter) {
                toast.setGravity(Gravity.CENTER, 0, 0); // 居中显示
            }
            toast.show();
        } catch (Exception e) {
        }
    }

    public static void show(Context context, String message, boolean isLong,
                            int bitmapId) {
        try {
            if (Tools.isEmpty(message)) {
                return;
            }
            TextView textView;

            textView = new TextView(context);
            int padding = Tools.getPixelByDip(context, 15);
            textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            textView.setBackgroundResource(R.drawable.background_toast);
            textView.setPadding(padding, padding, padding, padding);
            textView.setText(message);
            textView.setTextSize(18.0f);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(context.getResources().getColor(R.color.white));
            textView.setCompoundDrawablesWithIntrinsicBounds(context.getResources()
                    .getDrawable(bitmapId), null, null, null);
            textView.setCompoundDrawablePadding(6);

            Toast toast = new Toast(context);
            textView.setText(message);
            toast.setView(textView);
            toast.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0); // 居中显示
            toast.show();
        } catch (Exception e) {
        }
    }

}
