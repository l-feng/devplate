package com.example.core;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.core.utils.StringUtils;


/**
 * Created by Administrator on 2016/1/30 0030.
 */
public class CommonHeadBar extends LinearLayout {

    private View contentView;
    private View backButton;
    private TextView titleView;
    private Button rightButton;
    private String headTitle;
    private String rightButtonTitle;
    private int rightButtonImageId;
    private boolean hasEmptyContent;
    private boolean hasBackButton;

    private OnClickListener backListener;
    private OnClickListener rightListener;
    private View emptyView;
    private ImageView rightImageButton;
    private View layoutHomeTopbar;

    public CommonHeadBar(Context context) {
        super(context);
        init();
    }

    public CommonHeadBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVariables(context, attrs, 0);
        init();
    }

    public CommonHeadBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initVariables(context, attrs, defStyle);
        init();
    }


    private void initVariables(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.CommonHeadBar, defStyle, 0);
        if (a != null) {
            headTitle = a.getString(R.styleable.CommonHeadBar_headTitle);
            rightButtonTitle = a.getString(R.styleable.CommonHeadBar_rightButton);
            rightButtonImageId = a.getResourceId(R.styleable.CommonHeadBar_rightButtonImage, -1);
            hasEmptyContent = a.getBoolean(R.styleable.CommonHeadBar_hasEmptyContent, false);
            hasBackButton = a.getBoolean(R.styleable.CommonHeadBar_hasBackButton, true);
            a.recycle();
        }
    }


    private void init() {
        if (isInEditMode()) {
            return;
        }
        contentView = LayoutInflater.from(getContext())
                .cloneInContext(getContext())
                .inflate(R.layout.widget_common_head_bar, this);
        titleView = (TextView) contentView.findViewById(R.id.head_bar_title);
        titleView.setText(headTitle);
        backButton = contentView.findViewById(R.id.head_bar_back_btn);
        layoutHomeTopbar=contentView.findViewById(R.id.layout_home_topbar);
        if (hasBackButton) {
            backButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleBackButton(v);
                }
            });
        } else {
            backButton.setVisibility(INVISIBLE);
        }
        rightButton = (Button) contentView.findViewById(R.id.head_bar_right_btn);
        rightImageButton = (ImageView) contentView.findViewById(R.id.head_bar_right_image);
        if (StringUtils.isNotBlank(rightButtonTitle)) {
            rightButton.setText(rightButtonTitle);
            rightButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleRightButton(v);
                }
            });
        } else {
            rightButton.setVisibility(INVISIBLE);
        }
        if (rightButtonImageId > 0) {
            rightImageButton.setImageResource(rightButtonImageId);
            contentView.findViewById(R.id.head_bar_right_image_btn).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleRightButton(v);
                }
            });
        } else {
            rightImageButton.setVisibility(INVISIBLE);
        }

        emptyView = contentView.findViewById(R.id.head_bar_empty_text);
        if (hasEmptyContent) {
            emptyView.setVisibility(VISIBLE);
        }
    }

    private void handleRightButton(View v) {
        if (rightListener != null) {
            rightListener.onClick(v);
        }
    }

    private void handleBackButton(View v) {
        if (backListener != null) {
            backListener.onClick(v);
        } else if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    public void setBackListener(OnClickListener backListener) {
        this.backListener = backListener;
    }

    public void setRightListener(OnClickListener rightListener) {
        this.rightListener = rightListener;
    }

    public void hideEmptyView() {
        this.emptyView.setVisibility(GONE);
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
        titleView.setText(headTitle);
    }

    public void setRightButtonState(boolean enable) {
        this.rightButton.setClickable(enable);
        if (enable) {
            this.rightButton.setTextColor(getResources().getColor(R.color.white));
        } else {
            this.rightButton.setTextColor(getResources().getColor(R.color.border_color));
        }
        this.rightImageButton.setClickable(enable);
    }

    public Button getRightButton() {
        return rightButton;
    }

    public ImageView getRightImageButton() {
        return rightImageButton;
    }

    public void setRightButtonImage(int id) {
        this.rightImageButton.setImageResource(id);
        this.rightImageButton.setVisibility(VISIBLE);
        this.rightButton.setVisibility(GONE);
        contentView.findViewById(R.id.head_bar_right_image_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRightButton(v);
            }
        });
    }
    public void setLayoutHomeTopbarColor(int color){
        layoutHomeTopbar.setBackgroundColor(color);
    }
}
