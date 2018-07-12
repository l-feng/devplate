package com.example.core;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.core.utils.Tools;


public class CustomAlertDialog extends Dialog implements
        View.OnClickListener {
    private ListView listView;
    private BaseAdapter adapter;
    private View customView;
    private Drawable icon;
    private CharSequence title = "";
    private Window window;
    private CharSequence message;
    private Button positiveButton;
    private CharSequence positiveButtonText;
    private OnClickListener positiveButtonListener;
    private Button negativeButton;
    private CharSequence negativeButtonText;
    private OnClickListener negativeButtonListener;
    private Button neutralButton;
    private CharSequence neutralButtonText;
    private OnClickListener neutralButtonListener;
    private int backgroundId = -1;
    private int gravity = Gravity.CENTER;

    private int negativeButtonTextColor = -1;

    public CustomAlertDialog(Context context, boolean cancelable,
                             OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public CustomAlertDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    public CustomAlertDialog(Context context) {
        super(context, R.style.AlertDialogStyle);
        init();
    }

    public void init() {
        window = getWindow();
    }

    @Override
    public void show() {
        try {
            window.setGravity(gravity);
            super.show();
        } catch (Exception e) {
        }
    }

    public void dispose() {
        dismiss();
    }

    public void setPadding(int left, int top, int right, int bottom) {
        if (window != null) {
            window.getDecorView().setPadding(left, top, right, bottom);
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setIcon(int resId) {
        // setIcon(getContext().getResources().getDrawable(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public void setBackground(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    public void setView(View view) {
        customView = view;
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }

    public void setNegativeButton(CharSequence text, OnClickListener listener) {
        if (text != null) {
            negativeButtonText = text;
            negativeButtonListener = listener;
        }
    }

    public void setNegativeButtonTextColor(int color) {
        negativeButtonTextColor = color;
    }

    public void setNeutralButton(CharSequence text, OnClickListener listener) {
        if (text != null) {
            neutralButtonText = text;
            neutralButtonListener = listener;
        }
    }

    public void setPositiveButton(CharSequence text, OnClickListener listener) {
        if (text != null) {
            positiveButtonText = text;
            positiveButtonListener = listener;
        }
    }

    public void setMessage(CharSequence message) {
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window.setContentView(R.layout.alert_dialog);
        setupView();
    }

    private void setupContent(LinearLayout contentPanel) {
        if (message != null) {
            TextView messageView = (TextView) contentPanel.findViewById(R.id.message);
            messageView.setText(message);
            contentPanel.setVisibility(View.VISIBLE);
        } else {
            if (listView != null) {
                contentPanel.setVisibility(View.VISIBLE);
                View scrollView = window.findViewById(R.id.scrollView);
                scrollView.setVisibility(View.GONE);
                View parentView = window.getDecorView();
                int padding = Tools.getPixelByDip(getContext(), 50);
                parentView.setPadding(padding, 0, padding, 0);
                contentPanel.removeView(scrollView);
                contentPanel.addView(listView, new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT));
                contentPanel.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, 0, 1.0f));
                if (adapter != null) {
                    listView.setAdapter(adapter);
                }
            }
        }
    }

    private void setupCustomView(FrameLayout customPanel) {
        if (customView != null) {
            FrameLayout custom = (FrameLayout) customPanel.findViewById(R.id.custom);
            FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.FILL_PARENT,
                    FrameLayout.LayoutParams.FILL_PARENT);
            custom.addView(customView, param);
            customPanel.setVisibility(View.VISIBLE);
        }
    }

    private void setupButtons(LinearLayout buttonPanel) {
        if (positiveButtonText != null) {
            positiveButton = (Button) buttonPanel.findViewById(R.id.positiveButton);
            positiveButton.setText(positiveButtonText);
            positiveButton.setOnClickListener(this);
            positiveButton.setVisibility(View.VISIBLE);
            buttonPanel.setVisibility(View.VISIBLE);
        }
        if (negativeButtonText != null) {
            negativeButton = (Button) buttonPanel.findViewById(R.id.negativeButton);
            negativeButton.setText(negativeButtonText);
            if (negativeButtonTextColor != -1) {
                negativeButton.setTextColor(negativeButtonTextColor);
            }
            negativeButton.setOnClickListener(this);
            negativeButton.setVisibility(View.VISIBLE);
            buttonPanel.setVisibility(View.VISIBLE);
        }
        if (neutralButtonText != null) {
            neutralButton = (Button) buttonPanel.findViewById(R.id.neutralButton);
            neutralButton.setText(neutralButtonText);
            neutralButton.setOnClickListener(this);
            neutralButton.setVisibility(View.VISIBLE);
            buttonPanel.setVisibility(View.VISIBLE);
        }
    }

    private void setupTitle(RelativeLayout titlePanel) {
        if (icon != null) {
            ImageView view = (ImageView) titlePanel.findViewById(R.id.icon);
            view.setImageDrawable(icon);
            titlePanel.setVisibility(View.VISIBLE);
        }
        if (title != null) {
            TextView view = (TextView) titlePanel.findViewById(R.id.title);
            view.setText(title);
            titlePanel.setVisibility(View.VISIBLE);
        }
    }

    private void setupView() {
        // set background
        if (backgroundId >= 0) {
            window.findViewById(R.id.parentPanel).setBackgroundResource(backgroundId);
        }

        // title
        RelativeLayout titlePanel = (RelativeLayout) window
                .findViewById(R.id.title_panel);
        setupTitle(titlePanel);

        // content
        LinearLayout contentPanel = (LinearLayout) window
                .findViewById(R.id.contentPanel);
        setupContent(contentPanel);

        // button
        LinearLayout buttonPanel = (LinearLayout) window
                .findViewById(R.id.buttonPanel);
        setupButtons(buttonPanel);

        // custom view
        FrameLayout customPanel = (FrameLayout) window
                .findViewById(R.id.customPanel);
        setupCustomView(customPanel);

    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.positiveButton) {
            if (positiveButtonListener != null) {
                positiveButtonListener.onClick(this, BUTTON_POSITIVE);
            }
        } else if (v.getId() == R.id.negativeButton) {
            if (negativeButtonListener != null) {
                negativeButtonListener.onClick(this, BUTTON_NEGATIVE);
            }
        } else if (v.getId() == R.id.neutralButton) {
            if (neutralButtonListener != null) {
                neutralButtonListener.onClick(this, BUTTON_NEUTRAL);
            }
        }
        dismiss();
    }

    public static class Builder {
        private Context context;
        protected Params p;

        public Builder(Context context) {
            this.context = context;
            p = new Params(context);
        }

        public Builder setIcon(Drawable icon) {
            p.icon = icon;
            return this;
        }

        public Builder setIcon(int iconId) {
            return setIcon(context.getResources().getDrawable(iconId));
        }

        public Builder setBackground(int backgroundId) {
            p.backgroundId = backgroundId;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            p.title = title;
            return this;
        }

        public Builder setTitle(int titleId) {
            return setTitle(context.getString(titleId));
        }

        public Builder setMessage(CharSequence message) {
            p.message = message;
            return this;
        }

        public Builder setMessage(int messageId) {
            return setMessage(context.getString(messageId));
        }

        public Builder setNegativeButton(CharSequence text, OnClickListener listener) {
            p.negativeButtonText = text;
            p.negativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId, OnClickListener listener) {
            return setNegativeButton(context.getString(textId), listener);
        }

        public Builder setNeutralButton(CharSequence text, OnClickListener listener) {
            p.neutralButtonText = text;
            p.neutralButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(int textId, OnClickListener listener) {
            return setNeutralButton(context.getString(textId), listener);
        }

        public Builder setCancelable(boolean cancelable) {
            p.cancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            p.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            p.onKeyListener = onKeyListener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, OnClickListener listener) {
            p.positiveButtonText = text;
            p.positiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(int textId, OnClickListener listener) {
            return setPositiveButton(context.getString(textId), listener);
        }

        public Builder setItems(CharSequence[] items, OnClickListener listener) {
            p.items = items;
            p.onClickListener = listener;
            return this;
        }

        public Builder setView(View view) {
            p.view = view;
            return this;
        }

        public CustomAlertDialog createDialog() {
            CustomAlertDialog dialog = new CustomAlertDialog(context);
            p.apply(dialog);
            return dialog;
        }

        protected static class Params {
            public final Context context;
            public final LayoutInflater inflater;
            public Drawable icon;
            public CharSequence title;
            public CharSequence message;
            public CharSequence positiveButtonText;
            public OnClickListener positiveButtonListener;
            public CharSequence negativeButtonText;
            public OnClickListener negativeButtonListener;
            public CharSequence neutralButtonText;
            public OnClickListener neutralButtonListener;
            public boolean cancelable = true;
            public OnClickListener onClickListener;
            public OnCancelListener onCancelListener;
            public OnKeyListener onKeyListener;
            public View view;
            public CharSequence[] items;
            public int backgroundId = -1;

            public Params(Context context) {
                this.context = context;
                cancelable = true;
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            public void apply(CustomAlertDialog dialog) {
                if (onKeyListener != null) {
                    dialog.setOnKeyListener(onKeyListener);
                }
                if (backgroundId >= 0) {
                    dialog.setBackground(backgroundId);
                }
                dialog.setIcon(icon);
                dialog.setTitle(title);
                dialog.setMessage(message);
                dialog.setView(view);
                dialog.setCancelable(cancelable);
                dialog.setOnCancelListener(onCancelListener);
                if (positiveButtonText != null) {
                    dialog.setPositiveButton(positiveButtonText, positiveButtonListener);
                }
                if (negativeButtonText != null) {
                    dialog.setNegativeButton(negativeButtonText, negativeButtonListener);
                }
                if (neutralButtonText != null) {
                    dialog.setNeutralButton(neutralButtonText, neutralButtonListener);
                }
                if (items != null) {
                    createListView(dialog);
                }
            }

            private void createListView(final CustomAlertDialog dialog) {
//                final ListView listView = (ListView) inflater.inflate(
//                        R.layout.alert_dialog_listview, null);
//                PopupListAdapter adapter;
//                adapter = new PopupListAdapter(context, items, dialog);
//                dialog.adapter = adapter;
//                adapter.setOnClickListener(onClickListener);
//                dialog.listView = listView;
            }
        }
    }
}
