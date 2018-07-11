package com.example.administrator.materialdesign.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.administrator.materialdesign.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Administrator on 2018/7/11 0011.
 */

public class ThoastUtils {
    public static void setTitleBarColor(Activity activity) {
        setTitleBarColor(activity, R.color.common_color);
    }

    public static void setTitleBarColor(Activity activity, int colorId) {
//        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.my_toolbar);
//        activity.setSupportActionBar(toolbar);
        //透明状态栏
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            WindowManager.LayoutParams winParams = window.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            window.setAttributes(winParams);

            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            //此处可以重新指定状态栏颜色
            tintManager.setStatusBarTintResource(colorId);
        }
    }



    public static void setToggleButtonText(ToggleButton toggleButton,
                                           CharSequence text) {
        toggleButton.setText(text);
        toggleButton.setTextOn(text);
        toggleButton.setTextOff(text);
    }

    public static Rect getViewBoundRect(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new Rect(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }

    public static boolean toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            return false;
        }
        view.setVisibility(View.VISIBLE);
        return true;
    }

    // public static void loadImage(ImageView imageView, String url) {
    // imageView.setImageResource(R.drawable.image_loading_icon);
    // imageView.setTag(url);
    // ImageManager.getImage(url, new ImageReceiver(imageView, url));
    // }

    public static void setVisibility(View view, boolean flag, int hideOption) {
        if (flag) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(hideOption);
        }
    }

    public static void setTextIfNonEmpty(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
    }

    public static void setTextWithDefault(TextView textView, String text,
                                          String defaultText) {
        if (TextUtils.isEmpty(text)) {
            textView.setText(defaultText);
        } else {
            textView.setText(text);
        }
    }

    public static boolean isListViewAtTop(ListView listView) {
        return (listView.getChildCount() == 0 || (listView
                .getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0));
    }

    public static boolean isScrollViewAtTop(ScrollView scrollView) {
        return (scrollView.getChildCount() == 0 || (scrollView.getScrollY() <= 0 && scrollView
                .getChildAt(0).getTop() == 0));
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void showKeyboards(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void hideKeyboards(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static String getShowCount(int count) {
        if (count > 100000) {
            return String.valueOf(count / 10000) + "万";
        }
        return String.valueOf(count);
    }


    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

}

