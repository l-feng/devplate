package com.example.core.list;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.core.R;

/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class EmptyView extends LinearLayout {

    private TextView textView;
    private ImageView iconView;
    // private int icon;
    private String text;

    /**
     * @param context
     */
    public EmptyView(Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        addView(LayoutInflater.from(getContext()).cloneInContext(getContext())
                        .inflate(R.layout.net_error_empty, null),
                new LayoutParams(-1, -1));
        findViewById(R.id.network_setting_empty).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        getContext().startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                });
        iconView = (ImageView) findViewById(R.id.net_error_empty_image);
        textView = (TextView) findViewById(R.id.neterror_hint_text);
    }

    public TextView getTextView() {
        return textView;
    }

    public ImageView getImageView() {
        return iconView;
    }

    public void setTopMargin(int top) {
        if (getChildCount() > 0) {
            LayoutParams lp = (LayoutParams) getChildAt(0)
                    .getLayoutParams();
            lp.topMargin = top;
            getChildAt(0).setLayoutParams(lp);
        }
    }

    public void showNetSetting(boolean show) {
        findViewById(R.id.network_setting_empty).setVisibility(
                show ? View.VISIBLE : View.GONE);
        if (show) {
            iconView.setImageResource(R.drawable.icon_empty_common);
            textView.setText(R.string.network_error_click);
        } else {
            if (text != null) {
                textView.setText(text);
            }
        }
    }

    public void showServerError(boolean show) {
        findViewById(R.id.network_setting_empty).setVisibility(
                show ? View.VISIBLE : View.GONE);
        if (show) {
            iconView.setImageResource(R.drawable.icon_empty_common);
            textView.setText(R.string.service_error_click);
        } else {

            if (text != null) {
                textView.setText(text);
            }
        }
    }

    public void setEmptyInfo(String text) {
        this.text = text;
    }
}
