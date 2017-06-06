package com.pgt.xds.club.adapter;

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
 * Created by omni20170501 on 2017/6/6.
 */

public class MyCompetitionAdapter extends RecyclerView.Adapter<MyCompetitionAdapter.ViewHolder> {
    private List<String> data;
    private Context context;

    public MyCompetitionAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_competition_item, parent, false);
        return new MyCompetitionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView compile_pic;
        private TextView compile_name, compile_year, compile_month,compile_day,compile_am,compile_address;


        public ViewHolder(View itemView) {
            super(itemView);
            compile_pic = (ImageView) itemView.findViewById(R.id.compile_pic);
            compile_name = (TextView) itemView.findViewById(R.id.compile_name);
            compile_year = (TextView) itemView.findViewById(R.id.compile_year);
            compile_month = (TextView) itemView.findViewById(R.id.compile_month);
            compile_day = (TextView) itemView.findViewById(R.id.compile_day);
            compile_am = (TextView) itemView.findViewById(R.id.compile_am);
            compile_address = (TextView) itemView.findViewById(R.id.compile_address);


        }
    }

    public void setItems(List<String> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }
}
