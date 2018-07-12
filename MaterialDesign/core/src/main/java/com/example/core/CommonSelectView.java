package com.example.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.core.base.SelectorView;
import com.example.core.utils.IntentUtils;
import com.example.core.utils.StringUtils;
import com.example.core.utils.Tools;


/**
 * Created by Administrator on 2016/1/30 0030.
 */
public class CommonSelectView extends RelativeLayout implements View.OnClickListener {

    private View contentView;
    private TextView textView;
    private View arrowView;
    private String hint;
    private String selectContent;
    private String[] choices;
    private CustomAlertDialog dialog;
    private int selection = -1;

    private OnClickListener clickListener;

    public CommonSelectView(Context context) {
        super(context);
        init();
    }

    public CommonSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVariables(context, attrs, 0);
        init();
    }

    public CommonSelectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVariables(context, attrs, defStyle);
        init();
    }

    private void init() {
        contentView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.widget_common_select, this);
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
                R.layout.popup_selector_layout, null);
        final SelectorView selector = (SelectorView) view
                .findViewById(R.id.wheel);
        if (selection > -1) {
            selector.setSelection(selection);
        }
        TextView popupTitle = (TextView) view.findViewById(R.id.popup_title);
        if (StringUtils.isNotBlank(hint)) {
            popupTitle.setText(getResources().getString(R.string.selector_popup_title) + hint);
        }
        dialog = new CustomAlertDialog.Builder(getContext())
                .setView(view).createDialog();
        view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                setSelection(selector.getSelection());
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

    private void popupSelectView() {
        initDialog();
        dialog.show();
    }


    public void setSelection(int selection) {
        if (choices != null) {
            if (selection >= 0 && selection < choices.length) {
                setSelectContent(choices[selection]);
            }
        }
        if (clickListener != null && !clickListener.equals(this)) {
            clickListener.onClick(null);
        }
    }

    private void initVariables(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.CommonSelectView, defStyle, 0);
        hint = a.getString(R.styleable.CommonSelectView_selectorHint);
        a.recycle();
    }

    public void setText(String content) {
        textView.setText(content);
    }

    public String getText() {
        return textView.getText().toString();
    }

    public void setHint(String content) {
        this.hint = content;
        textView.setHint(content);
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    @Override
    public void onClick(View v) {
        popupSelectView();
    }

    @Override
    public boolean performClick() {
        popupSelectView();
        return super.performClick();
    }

    public String getSelectContent() {
        return selectContent;
    }

    public void setSelectContent(String selectContent) {
        if (choices == null) {
            return;
        }
        this.selectContent = selectContent;
        for (int i = 0; i < choices.length; i++) {
            if (choices[i].equals(selectContent)) {
                this.selection = i;
                textView.setText(selectContent);
            }
        }
    }

    @Override
    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public Object getTag(int key) {
        if (key == IntentUtils.COMMON_WIDGET_VALUE_TAG_ID) {
            if (StringUtils.isNotBlank(selectContent)) {
                return getSelectContent();
            }
        }
        return super.getTag(key);
    }

}
