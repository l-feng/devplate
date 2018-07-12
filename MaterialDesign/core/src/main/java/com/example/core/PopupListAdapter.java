package com.example.core;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class PopupListAdapter extends BaseAdapter {

    private Context context;
    private CharSequence[] items;
    private LayoutInflater inflater;
    private OnClickListener onClickListener = null;
    private int layoutId;
    private DialogInterface popup;

    public PopupListAdapter(Context context, CharSequence[] items,
                            DialogInterface popup) {
        this(context, items, popup, R.layout.alert_dialog_list_item);
    }

    public PopupListAdapter(Context context, CharSequence[] items,
                            DialogInterface popup, int layoutId) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(this.context);
        this.popup = popup;
        this.layoutId = layoutId;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public int getCount() {
        return items == null ? 0 : items.length;
    }

    public Object getItem(int position) {
        return items == null ? null : items[position];
    }

    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        CharSequence text;
        int position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (items == null || position < 0 || position >= items.length) {
            return null;
        }
        ViewHolder holder;
        if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
            convertView = inflater.inflate(layoutId, null);
            holder = new ViewHolder();
            holder.text = items[position];
            convertView.setTag(holder);
            holder.position = position;
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(holder.text);
        if (position == 0) {
            textView.setBackgroundResource(R.drawable.alert_dialog_list_item_top_bg);
        } else if (position == items.length - 1) {
            textView
                    .setBackgroundResource(R.drawable.alert_dialog_list_item_bottom_bg);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = 0;
                if (v.getTag() != null) {
                    position = ((ViewHolder) v.getTag()).position;
                }
                if (onClickListener != null) {
                    onClickListener.onClick(popup, position);
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        popup.dismiss();
                    }
                }, 300);

            }
        });
        return convertView;
    }

}
