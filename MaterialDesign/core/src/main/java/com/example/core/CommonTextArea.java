package com.example.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.core.utils.IntentUtils;
import com.example.core.utils.StringUtils;


/**
 * Created by Administrator on 2016/1/30 0030.
 */
public class CommonTextArea extends LinearLayout {

    private View contentView;
    private TextView titleView;
    private EditText editText;
    private String title;
    private String hint;
    private Boolean hasTitle=false;
    private RelativeLayout layoutTitle;

    public CommonTextArea(Context context) {
        super(context);
        init();
    }

    public CommonTextArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVariables(context, attrs, 0);
        init();
    }

    public CommonTextArea(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVariables(context, attrs, defStyle);
        init();
    }

    private void init() {
        contentView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.widget_common_textarea, this);
        layoutTitle= (RelativeLayout) contentView.findViewById(R.id.widget_common_textarea_title_area);

        titleView = (TextView) contentView.findViewById(R.id.widget_common_textarea_title);
        titleView.setText(title);
        editText = (EditText) contentView.findViewById(R.id.widget_common_edit_text);
        if (StringUtils.isNotBlank(hint)) {
            editText.setHint(hint);
        }
        if (!hasTitle){
            layoutTitle.setVisibility(INVISIBLE);
        }
    }

    private void initVariables(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.CommonTextArea, defStyle, 0);
        title = a.getString(R.styleable.CommonTextArea_textAreaTitle);
        hint = a.getString(R.styleable.CommonTextArea_textAreaHint);
        a.recycle();
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setText(String content) {
        editText.setText(content);
    }

    public String getText() {
        return editText.getText().toString();
    }

    @Override
    public Object getTag(int key) {
        if (key == IntentUtils.COMMON_WIDGET_VALUE_TAG_ID) {
            return getText();
        }
        return super.getTag(key);
    }
}
