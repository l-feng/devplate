package com.example.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.core.utils.IntentUtils;
import com.example.core.utils.ViewUtils;


/**
 * Created by Administrator on 2016/1/30 0030.
 */
public class CommonTextEdit extends RelativeLayout implements View.OnClickListener, View.OnFocusChangeListener {

    public static final String TYPE_PASSWORD = "password";
    public static final String TYPE_NUMBER = "number";

    private View contentView;
    private EditText editText;
    private View arrowView;
    private View clearTextView;
    private String title;
    private String hint;
    private int titleWidth;
    private String inputType;
    private TextView textView;
    private ImageView titleImage;
    private Drawable image;
    private View layoutCommonText;

    public CommonTextEdit(Context context) {
        super(context);
        init();
    }

    public CommonTextEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVariables(context, attrs, 0);
        init();
    }

    public CommonTextEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVariables(context, attrs, defStyle);
        init();
    }

    private void init() {
        contentView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.widget_common_text_edit, this);
        textView = (TextView) contentView.findViewById(R.id.widget_common_text);
        textView.setText(title);
        editText = (EditText) contentView.findViewById(R.id.widget_common_edit_text);
        editText.setHint(hint);
        if (TYPE_PASSWORD.equals(inputType)) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else if (TYPE_NUMBER.equals(inputType)) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }
        if (titleWidth > 0) {
            LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
            layoutParams.setMargins(((LayoutParams) textView.getLayoutParams()).leftMargin + titleWidth, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
        }
        editText.clearFocus();
        arrowView = contentView.findViewById(R.id.widget_common_edit_text_arrow);
        clearTextView = contentView.findViewById(R.id.widget_common_edit_clear_text);
        titleImage = (ImageView) findViewById(R.id.widget_common_image);
        titleImage.setBackgroundDrawable(image);
//        layoutCommonText = contentView.findViewById(R.id.layout_common_text);
//        if (title == null) {
//            textView.setVisibility(View.GONE);
//        }
        if(image==null){
            final LayoutParams lp = (LayoutParams) titleImage.getLayoutParams();
            lp.width = 0;
            lp.height=20;//lp.height=LayoutParams.WRAP_CONTENT;
            titleImage.setLayoutParams(lp);
        }
        textView.setOnClickListener(this);
        clearTextView.setOnClickListener(this);
        editText.setOnFocusChangeListener(this);


    }

    private void initVariables(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.CommonTextEdit, defStyle, 0);
        title = a.getString(R.styleable.CommonTextEdit_textTitle);
        hint = a.getString(R.styleable.CommonTextEdit_textHint);
        titleWidth = a.getDimensionPixelSize(R.styleable.CommonTextEdit_textTitleWidth, 0);
        inputType = a.getString(R.styleable.CommonTextEdit_textInputType);
        image = a.getDrawable(R.styleable.CommonTextEdit_beforeTextImage);
        a.recycle();
    }

    public void setText(String content) {
        editText.setText(content);
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setHint(String content) {
        editText.setHint(content);
    }

    public void setInputType(int type) {
        editText.setInputType(type);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.widget_common_edit_clear_text) {
            editText.setText("");
        } else if (v.getId() == R.id.widget_common_text) {
            if (editText.getVisibility() == INVISIBLE) {
                textView.setVisibility(GONE);
                editText.setVisibility(VISIBLE);
                editText.setSelection(editText.length());
                ViewUtils.showKeyboards(getContext(), v);
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
//            arrowView.setVisibility(GONE);
            clearTextView.setVisibility(VISIBLE);
        } else {
//            arrowView.setVisibility(VISIBLE);
            clearTextView.setVisibility(GONE);
        }
    }

    @Override
    public Object getTag(int key) {
        if (key == IntentUtils.COMMON_WIDGET_VALUE_TAG_ID) {
            return getText();
        }
        return super.getTag(key);
    }

    public void setEnabled(boolean enable) {
        editText.setEnabled(enable);
    }

    public EditText getEditText() {
        return editText;
    }

}
