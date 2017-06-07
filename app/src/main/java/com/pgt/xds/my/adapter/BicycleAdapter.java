package com.pgt.xds.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pgt.xds.OnItemCheckListener;
import com.pgt.xds.R;

import java.util.List;

/**
 * Created by omni20170501 on 2017/6/5.
 */

public class BicycleAdapter extends RecyclerView.Adapter<BicycleAdapter.ViewHolder> {
    private List<String> data;
    private Context context;
    private OnItemCheckListener onItemCheckListener;

    public BicycleAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bicycle_item, parent, false);
        return new BicycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bicycle_type.setText(data.get(position));
        holder.bicycle_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemCheckListener != null)
                    onItemCheckListener.OnItemCheck(holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView bicycle_pic;
        private TextView bicycle_mileage, bicycle_type, bicycle_name;
        private CheckBox bicycle_check;
        private LinearLayout bicycle_item;

        public ViewHolder(View itemView) {
            super(itemView);
            bicycle_pic = (ImageView) itemView.findViewById(R.id.bicycle_pic);
            bicycle_mileage = (TextView) itemView.findViewById(R.id.bicycle_mileage);
            bicycle_type = (TextView) itemView.findViewById(R.id.bicycle_type);
            bicycle_name = (TextView) itemView.findViewById(R.id.bicycle_name);
            bicycle_check = (CheckBox) itemView.findViewById(R.id.bicycle_check);
            bicycle_item = (LinearLayout) itemView.findViewById(R.id.bicycle_item);

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
