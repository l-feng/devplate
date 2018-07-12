package com.example.administrator.materialdesign.adapter.recyclelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.materialdesign.R;
import com.example.administrator.materialdesign.model.DataBean;
import com.example.administrator.materialdesign.model.Datas;
import com.example.administrator.materialdesign.weight.SwipeMenuLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class BacklashRecycleAdapter extends RecyclerView.Adapter<BacklashRecycleAdapter.MyViewHolder> {
    private List<DataBean> list;

    public BacklashRecycleAdapter(List<DataBean> Datelist) {
        list = Datelist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_view, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DataBean dataBean = list.get(position);
        if(dataBean.getImgUrl()!=null){
            Picasso.with(holder.iv_avatar.getContext()).load(dataBean.getImgUrl()).into(holder.iv_avatar);
        }
        holder.tv_title.setText(dataBean.getName());
        holder.tv_subTitle.setText(dataBean.getContent());

        holder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(holder.itemView.getContext(), dataBean.getContent(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.view_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.tv_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBean data = list.get(position);
                list.remove(position);
                list.add(0, data);
                notifyDataSetChanged();


            }
        });
        holder.view_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.swipeMenuLayout.collapseSmooth();
                Toast.makeText(holder.itemView.getContext(), "编辑", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_subTitle;
        TextView view_del;
        TextView view_edit;
        TextView tv_top;
        CircleImageView iv_avatar;
        SwipeMenuLayout swipeMenuLayout;
        View contentView;

        private MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.item_tv_title);
            tv_subTitle = (TextView) itemView.findViewById(R.id.item_tv_subTitle);
            tv_top = (TextView) itemView.findViewById(R.id.item_tv_top);
            contentView = itemView.findViewById(R.id.item_content);

            view_del = (TextView) itemView.findViewById(R.id.item_tv_del);
            view_edit = (TextView) itemView.findViewById(R.id.item_tv_edit);
            swipeMenuLayout = (SwipeMenuLayout) itemView.findViewById(R.id.item_layout_swip);
            iv_avatar = (CircleImageView) itemView.findViewById(R.id.item_avatar);
        }
    }
}

