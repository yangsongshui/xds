package com.pgt.xds.club.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pgt.xds.connector.OnItemCheckListener;
import com.pgt.xds.R;

import java.util.List;

/**
 * Created by omni20170501 on 2017/6/6.
 */

public class TroopsAdapter extends RecyclerView.Adapter<TroopsAdapter.ViewHolder> {
    private List<String> data;
    private Context context;
    private OnItemCheckListener onItemCheckListener;

    public TroopsAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.troops_item, parent, false);
        return new TroopsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.troops_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCheckListener!=null)
                        onItemCheckListener.OnItemCheck(holder,position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView troops_pic;
        private TextView troops_name, troops_lv, troops_id,troops_people,troops_introduce;
        private LinearLayout troops_item;

        public ViewHolder(View itemView) {
            super(itemView);
            troops_pic = (ImageView) itemView.findViewById(R.id.troops_pic);
            troops_name = (TextView) itemView.findViewById(R.id.troops_name);
            troops_lv = (TextView) itemView.findViewById(R.id.troops_lv);
            troops_id = (TextView) itemView.findViewById(R.id.troops_id);
            troops_people = (TextView) itemView.findViewById(R.id.troops_people);
            troops_introduce = (TextView) itemView.findViewById(R.id.troops_introduce);
            troops_item = (LinearLayout) itemView.findViewById(R.id.troops_item);


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
