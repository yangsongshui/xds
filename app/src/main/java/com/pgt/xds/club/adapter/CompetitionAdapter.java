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

public class CompetitionAdapter extends RecyclerView.Adapter<CompetitionAdapter.ViewHolder> {
    private List<String> data;
    private Context context;

    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }

    private OnItemCheckListener onItemCheckListener;

    public CompetitionAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.competition_itme, parent, false);
        return new CompetitionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.competition_item.setOnClickListener(new View.OnClickListener() {
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

        private ImageView competition_pic;
        private TextView competition_name, competition_day, competition_hour, competition_min, competition_num;
        private LinearLayout competition_item;

        public ViewHolder(View itemView) {
            super(itemView);
            competition_pic = (ImageView) itemView.findViewById(R.id.competition_pic);
            competition_day = (TextView) itemView.findViewById(R.id.competition_day);
            competition_hour = (TextView) itemView.findViewById(R.id.competition_hour);
            competition_min = (TextView) itemView.findViewById(R.id.competition_min);
            competition_name = (TextView) itemView.findViewById(R.id.competition_name);
            competition_num = (TextView) itemView.findViewById(R.id.competition_num);
            competition_item = (LinearLayout) itemView.findViewById(R.id.competition_item);

        }
    }

    public void setItems(List<String> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }
}
