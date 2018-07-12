package com.example.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.core.base.DatePicker;
import com.example.core.utils.DateUtil;
import com.example.core.utils.IntentUtils;
import com.example.core.utils.StringUtils;
import com.example.core.utils.Tools;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/1/30 0030.
 */
public class CommonDateView extends RelativeLayout implements View.OnClickListener {

    private View contentView;
    private TextView textView;
    private View arrowView;
    private String hint;
    private boolean hasDay;
    private String dateFormat;
    private CustomAlertDialog dialog;
    private Timestamp timestamp;
    private Timestamp initialTimestamp;
    private DatePicker selector;

    public CommonDateView(Context context) {
        super(context);
        init();
    }

    public CommonDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVariables(context, attrs, 0);
        init();
    }

    public CommonDateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVariables(context, attrs, defStyle);
        init();
    }

    private void init() {
        contentView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.widget_common_date, this);
        textView = (TextView) contentView.findViewById(R.id.widget_common_edit_text);
        textView.setHint(hint);
        arrowView = contentView.findViewById(R.id.widget_common_edit_text_arrow);

        contentView.setOnClickListener(this);
        textView.setOnClickListener(this);
        arrowView.setOnClickListener(this);

//        initDialog();
    }


    private void initDialog() {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.popup_date_layout, null);
        selector = (DatePicker) view
                .findViewById(R.id.wheel);
        if (!hasDay) {
            selector.hideDayView();
        }
        if (timestamp == null) {
            initialTimestamp = DateUtil.getCurrentTime();
        } else {
            initialTimestamp = timestamp;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(initialTimestamp.getTime());
        selector.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        TextView popupTitle = (TextView) view.findViewById(R.id.popup_title);
        if (StringUtils.isNotBlank(hint)) {
            popupTitle.setText(getResources().getString(R.string.selector_popup_title) + hint);
        }
//        selector.setMarkRes(R.drawable.bet_selector_mark_bar);
        dialog = new CustomAlertDialog.Builder(getContext())
                .setView(view).createDialog();
        view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                setDate(selector.getYear(), selector.getMonth(), selector.getDay());
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        int padding = Tools.getPixelByDip(getContext(), 8);
        dialog.setBackground(R.color.transparent);
        dialog.setPadding(padding, 0, padding, 0);
    }

    private void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        setTimestamp(new Timestamp(calendar.getTimeInMillis()));
    }

    private void popupSelectView() {
        initDialog();
        dialog.show();
    }

    private void initVariables(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.CommonDateView, defStyle, 0);
        hint = a.getString(R.styleable.CommonDateView_dateHint);
        hasDay = a.getBoolean(R.styleable.CommonDateView_hasDay, true);
        if (hasDay) {
            dateFormat = "yyyy年MM月dd日";
        } else {
            dateFormat = "yyyy年MM月";
        }
        a.recycle();
    }

    public void setHint(String content) {
        this.hint = content;
        textView.setHint(content);
    }

    @Override
    public void onClick(View v) {
        popupSelectView();
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        if (textView != null) {
            textView.setText(DateUtil.format(timestamp, dateFormat));
        }
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return textView.getText().toString();
    }

    public void initBirthday() {
        if (timestamp == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1995, 0, 1);
            initialTimestamp = new Timestamp(calendar.getTimeInMillis());
        }
    }

    public void setHasDay(boolean hasDay) {
        this.hasDay = hasDay;
    }

    @Override
    public Object getTag(int key) {
        if (key == IntentUtils.COMMON_WIDGET_VALUE_TAG_ID) {
            return textView.getText().toString();
        }
        return super.getTag(key);
    }
}
