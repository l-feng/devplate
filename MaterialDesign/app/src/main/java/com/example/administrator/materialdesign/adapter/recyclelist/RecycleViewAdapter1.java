package com.example.administrator.materialdesign.adapter.recyclelist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.materialdesign.activity.picture.PictureActivity;
import com.example.administrator.materialdesign.R;
import com.example.administrator.materialdesign.model.Picture;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6 0006.
 */

public class RecycleViewAdapter1 extends RecyclerView.Adapter<RecycleViewAdapter1.ViewHolder>{

    private Context mContext;

    private List<Picture> mPictureList;

    public RecycleViewAdapter1(List<Picture> pictureList) {
        mPictureList = pictureList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.picture_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Picture picture = mPictureList.get(position);
                Intent intent = new Intent(mContext, PictureActivity.class);
                intent.putExtra(PictureActivity.PICTURE_NAME, picture.getName());
                intent.putExtra(PictureActivity.PICTURE_ID, picture.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picture picture = mPictureList.get(position);
        holder.pictureName.setText(picture.getName());
        // Glide.with(mContext).load(picture.getImageId()).into(holder.pictureImage);
        Picasso.with(mContext).load(picture.getImageId()).into(holder.pictureImage);
    }
    @Override
    public int getItemCount() {
        return mPictureList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView pictureImage;
        TextView pictureName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            pictureImage = (ImageView) view.findViewById(R.id.picture_image);
            pictureName = (TextView) view.findViewById(R.id.picture_name);
        }
    }

}

