package com.pgt.xds.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by omni20170501 on 2017/6/5.
 */

public class BicycleAdapter extends RecyclerView.Adapter<BicycleAdapter.ViewHolder> {
    private List<String> data;
    private Context context;

    public BicycleAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView time_marker, itme_line;
        private TextView item_examine_tv, item_time_tv;

        public ViewHolder(View itemView) {
            super(itemView);


        }
    }
}
