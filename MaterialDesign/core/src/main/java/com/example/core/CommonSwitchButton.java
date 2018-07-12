package com.example.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/1/30 0030.
 */
public class CommonSwitchButton extends RelativeLayout implements View.OnClickListener {

    private View contentView;
    private TextView textView;
    private ImageView switchButtonView;
    private String title;

    private boolean switchOn;

    private OnClickListener clickListener;

    public CommonSwitchButton(Context context) {
        super(context);
        init();
    }

    public CommonSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVariables(context, attrs, 0);
        init();
    }

    public CommonSwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVariables(context, attrs, defStyle);
        init();
    }

    private void init() {
        contentView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.widget_common_switch_button, this);
        textView = (TextView) contentView.findViewById(R.id.widget_switch_button_title);
        switchButtonView = (ImageView) contentView.findViewById(R.id.widget_switch_button);

        contentView.setOnClickListener(this);
        textView.setOnClickListener(this);
        switchButtonView.setOnClickListener(this);

    }

    private void initVariables(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.CommonSwitchButton, defStyle, 0);
        title = a.getString(R.styleable.CommonSwitchButton_switchTitle);
        a.recycle();
    }

    public void setText(String content) {
        textView.setText(content);
    }

    @Override
    public void onClick(View v) {
        setSwitchValue(!this.switchOn);
        if (clickListener != null) {
            clickListener.onClick(v);
        }
    }

    public void setSwitchValue(boolean value) {
        this.switchOn = value;
        if (this.switchOn) {
            switchButtonView.setImageResource(R.drawable.icon_on_switch);
        } else {
            switchButtonView.setImageResource(R.drawable.icon_off_switch);
        }
    }

    public boolean getSwitchValue() {
        return this.switchOn;
    }

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
