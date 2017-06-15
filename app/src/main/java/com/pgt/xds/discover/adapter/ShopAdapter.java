package com.pgt.xds.discover.adapter;

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

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<String> data;
    private Context context;


    private OnItemCheckListener onItemCheckListener;

    public ShopAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item, parent, false);
        return new ShopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.shop_item.setOnClickListener(new View.OnClickListener() {
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

        private ImageView inquire_pic;
        private TextView inquire_name, inquire_phone, inquire_address;
        private LinearLayout shop_item;

        public ViewHolder(View itemView) {
            super(itemView);
            inquire_pic = (ImageView) itemView.findViewById(R.id.inquire_pic);
            inquire_name = (TextView) itemView.findViewById(R.id.inquire_name);
            inquire_phone = (TextView) itemView.findViewById(R.id.inquire_phone);
            inquire_address = (TextView) itemView.findViewById(R.id.inquire_address);
            shop_item = (LinearLayout) itemView.findViewById(R.id.shop_item);


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
