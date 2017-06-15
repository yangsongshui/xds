package com.pgt.xds.club.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pgt.xds.connector.OnItemCheckListener;
import com.pgt.xds.R;
import com.pgt.xds.app.MyApplication;

import java.util.List;

/**
 * Created by omni20170501 on 2017/6/6.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<String> data;
    private Context context;
    private OnItemCheckListener onItemCheckListener;

    public PhotoAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.photo_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCheckListener!=null)
                        onItemCheckListener.OnItemCheck(holder,position);
                }
            });
        MyApplication.newInstance().getGlide().load(data.get(position)).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.photo_pic);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo_pic;

        private LinearLayout photo_item;

        public ViewHolder(View itemView) {
            super(itemView);
            photo_pic = (ImageView) itemView.findViewById(R.id.photo_pic);
            photo_item = (LinearLayout) itemView.findViewById(R.id.photo_item);



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
