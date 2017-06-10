package com.pgt.xds.club.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pgt.xds.OnItemCheckListener;
import com.pgt.xds.R;
import com.pgt.xds.app.MyApplication;

import java.util.List;

/**
 * Created by omni20170501 on 2017/6/6.
 */

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> {
    private List<String> data;
    private Context context;
    private OnItemCheckListener onItemCheckListener;

    public TopAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_item, parent, false);
        return new TopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.top_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemCheckListener != null)
                    onItemCheckListener.OnItemCheck(holder, position);
            }
        });
        holder.top_num.setText((position + 1) + "");
        MyApplication.newInstance().getGlide().load(data.get(position)).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.top_pic);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView top_pic;
        private TextView top_name, top_km, top_num;
        private LinearLayout top_item;

        public ViewHolder(View itemView) {
            super(itemView);
            top_pic = (ImageView) itemView.findViewById(R.id.top_pic);
            top_name = (TextView) itemView.findViewById(R.id.top_name);
            top_km = (TextView) itemView.findViewById(R.id.top_km);
            top_num = (TextView) itemView.findViewById(R.id.top_num);
            top_item = (LinearLayout) itemView.findViewById(R.id.top_item);


        }
    }

    public void setItems(List<String> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }
}
