package com.example.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.core.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/30 0030.
 */
public class CommonCheckboxView extends RelativeLayout {

    private View contentView;
    private String[] choices;
    private String title;
    private TextView titleView;
    private LinearLayout checkboxArea;
    private List<CheckBox> checkboxList = new ArrayList<CheckBox>();

    public CommonCheckboxView(Context context) {
        super(context);
        init();
    }

    public CommonCheckboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVariables(context, attrs, 0);
        init();
    }

    public CommonCheckboxView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVariables(context, attrs, defStyle);
        init();
    }

    private void init() {
        contentView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.widget_common_checkbox, this);
        titleView = (TextView) contentView.findViewById(R.id.widget_common_checkbox_title);
        titleView.setText(title);

        checkboxArea = (LinearLayout) contentView.findViewById(R.id.widget_common_checkbox_area);

    }

    private void initVariables(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.CommonCheckbox, defStyle, 0);
        title = a.getString(R.styleable.CommonCheckbox_checkboxTitle);
        a.recycle();
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
        for (String choice : choices) {
            createCheckbox(choice);
        }
    }

    private void createCheckbox(String content) {
        LinearLayout checkBoxItem = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.widget_checkbox_item, null);
        CheckBox checkBox = (CheckBox) checkBoxItem.findViewById(R.id.checkbox);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                Tools.getPixelByDip(getContext(), 60));
        checkBox.setText(content);
//        checkBox.setLayoutParams(params);
//        checkBox.setBackgroundResource(R.drawable.checkbox_style);
        checkboxArea.addView(checkBoxItem);
        checkboxList.add(checkBox);
    }

    public List<String> getSelectContent() {
        List<String> resultList = new ArrayList<>();
        for (CheckBox checkBox : checkboxList) {
            if (checkBox.isChecked()) {
                resultList.add(checkBox.getText().toString());
            }
        }
        return resultList;
    }

    @Override
    public Object getTag(int key) {
        if (key == IntentUtils.COMMON_WIDGET_VALUE_TAG_ID) {
            String content = "";
            List<String> selectContent = getSelectContent();
            for (String s : selectContent) {
                content += s + ",";
            }
            return content;
        }
        return super.getTag(key);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }
}
