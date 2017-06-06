package com.pgt.xds.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pgt.xds.R;

import java.util.List;

/**
 * Created by omni20170501 on 2017/6/5.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<String> data;
    private Context context;

    public HistoryAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_child, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView child_pic;
        private TextView child_time, child_linear, child_cadence,child_mileage,child_cal;


        public ViewHolder(View itemView) {
            super(itemView);
            child_pic = (ImageView) itemView.findViewById(R.id.child_pic);
            child_time = (TextView) itemView.findViewById(R.id.child_time);
            child_linear = (TextView) itemView.findViewById(R.id.child_linear);
            child_cadence = (TextView) itemView.findViewById(R.id.child_cadence);
            child_mileage = (TextView) itemView.findViewById(R.id.child_mileage);
            child_cal = (TextView) itemView.findViewById(R.id.child_cal);


        }
    }

    public void setItems(List<String> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

}
